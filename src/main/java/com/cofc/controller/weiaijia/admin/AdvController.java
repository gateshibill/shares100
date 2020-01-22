package com.cofc.controller.weiaijia.admin;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cofc.pojo.ApplicationCommon;
import com.cofc.pojo.BackUser;
import com.cofc.pojo.vij.Adv;
import com.cofc.service.ApplicationCommonService;
import com.cofc.service.vij.AdvService;
import com.cofc.util.BaseUtil;
import com.cofc.util.JsonUtil;
/***
 * 后台—-广告图
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/adv")
public class AdvController extends BaseUtil{

	@Resource 
	private AdvService advService;
	@Resource
	private ApplicationCommonService aCommonService;
	@Resource
	private ApplicationCommonService applicationService;
	public static Logger log = Logger.getLogger("AdvController");
	
	/***
	 * 进入页面
	 * @param response
	 * @param mv
	 */
	@RequestMapping("/index")
	public ModelAndView index(HttpServletResponse response,ModelAndView mv) {
		mv.setViewName("weiaijia/adv/index");
		return mv;
	}
	/***
	 * 页面信息
	 * @param response
	 * @param adv
	 * @param page
	 * @param limit
	 * @throws InterruptedException 
	 */
	@RequestMapping("/getAdvList")
	public void getAdvList(HttpServletResponse response,Adv adv,Integer page,Integer limit) {
		if (page !=null) {
			 page=1;
		}
		if (limit != null) {
			limit =20;
		}
		int count = advService.getAdvCount(adv);
		List<Adv> list = advService.getAdvList(adv, (page-1)*limit, limit);
		output(response, JsonUtil.buildJsonByTotalCount(list, count));
	}

	/***
	 * 进入添加页面
	 * @param response
	 * @param adv
	 * @param mv
	 * @return
	 */
	@RequestMapping("/addAdv")
	public ModelAndView addAdv(HttpServletResponse response, HttpServletRequest request,Adv adv,ModelAndView mv){
		adv.setCreateTime(new Date());
		BackUser bu = (BackUser) request.getSession().getAttribute("loginer");
		List<ApplicationCommon> appList = new ArrayList<ApplicationCommon>();
		if (bu.getLoginPlat() == null || bu.getLoginPlat().equals("")) {
			appList = applicationService.getNewApplicationList(null);
		} else {
			String[] idStrings = bu.getLoginPlat().split(",");
			List<String> loginPlatList = Arrays.asList(idStrings);
			appList = applicationService.getNewApplicationList(loginPlatList);
		}
		mv.addObject("appList", appList);
		mv.setViewName("weiaijia/adv/add");
		return mv;
	}
	
	/***
	 * 执行添加
	 * @param response
	 * @param adv
	 */
	@RequestMapping("/doaddAdv")
	public void doaddAdv(HttpServletResponse response,Adv adv) {
		adv.setCreateTime(new Date());
		advService.addAdv(adv);
		output(response, JsonUtil.buildFalseJson("0", "添加成功"));
	}
	
	/***
	 * 进入修改页面
	 * @param response
	 * @param mv
	 * @param advId
	 * @return
	 */
	@RequestMapping("/updateAdv")
	public ModelAndView updateAdv(HttpServletResponse response,HttpServletRequest request,ModelAndView mv,@Param("advId")Integer advId) {
		Adv adv = advService.getInfoById(advId);
		mv.addObject("adv",adv);
		mv.setViewName("weiaijia/adv/edit");
		return mv;
	}
	/***
	 * 执行修改功能
	 * @param response
	 * @param adv
	 */
	@RequestMapping("/doupdateAdv")
	public void doupdateAdv(HttpServletResponse response,Adv adv) {
			advService.updateAdv(adv);
			output(response, JsonUtil.buildFalseJson("0", "修改成功"));
	}
	/***
	 * 删除图片
	 * @param response
	 * @param advId
	 */
	@RequestMapping("/delAdv")
	public void delAdv(HttpServletResponse response,Integer advId) {
		advService.delAdv(advId);
		output(response, JsonUtil.buildFalseJson("0", "删除成功"));
	}
}
