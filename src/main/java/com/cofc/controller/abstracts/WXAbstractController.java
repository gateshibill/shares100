package com.cofc.controller.abstracts;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cofc.pojo.ApplicationCommon;
import com.cofc.pojo.CompanyAbstract;
import com.cofc.service.ApplicationCommonService;
import com.cofc.service.CompanyAbstractService;
import com.cofc.util.BaseUtil;
import com.cofc.util.JsonUtil;

@Controller
@RequestMapping("/wx/abstract")
public class WXAbstractController extends BaseUtil {
	@Resource
	private CompanyAbstractService comabsService;
	@Resource
	private ApplicationCommonService applicationService;
	
	public static Logger log = Logger.getLogger("WXAbstractController");

	//获取应用或社区的logo图
	@RequestMapping("/getAbstract")
	public void createNewCompanyAbstract(HttpServletResponse response, Integer loginPlat) {
		CompanyAbstract curabs = comabsService.getAbstractByLoginPlat(loginPlat);
		List<CompanyAbstract> comabsList = new ArrayList<CompanyAbstract>();
		comabsList.add(curabs);
		output(response, JsonUtil.buildJson(comabsList));
	}

	//修改公告标题图片同时修改应用图片
	@RequestMapping("/updateAbstract")
	public void updateNewCompanyAbstract(HttpServletResponse response, Integer abstractId, String abstractContent,
			String pictures, String companyAddress, String companyPhone,String titleImage,Integer loginPlat) {
		List<CompanyAbstract> comabsList = new ArrayList<CompanyAbstract>();
		ApplicationCommon app = applicationService.getApplicationById(loginPlat);
		CompanyAbstract curabs = comabsService.getAbstractById(abstractId);
		curabs.setAbstractContent(abstractContent);
		curabs.setAbstractPictures(pictures);
		curabs.setCompanyAddress(companyAddress);
		if (titleImage != null) {
			curabs.setTitleImage(titleImage);
		}
		curabs.setCompanyPhone(companyPhone);
		curabs.setUpdateTime(new Date());
		try {
			if (app != null) {
				app.setAppUpdateTime(new Date());
				app.setGroupCard(titleImage);
				applicationService.updateApplicationStatus(app);
			}
			comabsService.updateAbstractContent(curabs);
			comabsList.add(curabs);
			output(response, JsonUtil.buildJson(comabsList));
		} catch (Exception e) {
			output(response, JsonUtil.buildFalseJson("1", "修改失败"));
			log.info("社区" + curabs.getLoginPlat() + "修改企业简介" + curabs.getAbstractId() + "--" + curabs.getAbstractTitle()
					+ "失败，失败原因" + e);
		}
	}
	@RequestMapping("/test")
	public void testPostTime(HttpServletResponse response,String sendTime){
		output(response, JsonUtil.buildFalseJson("0","我接收到的字符串是"+sendTime));
	}
}
