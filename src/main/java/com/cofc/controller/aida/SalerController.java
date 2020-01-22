package com.cofc.controller.aida;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cofc.pojo.ApplicationCommon;
import com.cofc.pojo.BackUser;
import com.cofc.pojo.aida.SalesPerson;
import com.cofc.service.ApplicationCommonService;
import com.cofc.service.aida.SalesPersonService;
import com.cofc.util.BaseUtil;
import com.cofc.util.JsonUtil;
/**
 * 后台销售管理
 * @author 46678
 *
 */
@Controller
@RequestMapping("/saler")
public class SalerController extends BaseUtil{
	@Resource
	private SalesPersonService salersPersonService;
	@Resource
	private ApplicationCommonService applicationService;
	@RequestMapping("/index")
	public ModelAndView index(HttpServletRequest request,ModelAndView mv){
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
		mv.setViewName("saler/index");
		return mv;
	}
	@RequestMapping("/getSalerList")
	public void getSalerList(HttpServletRequest request,HttpServletResponse response,SalesPerson sales,
			Integer page,Integer limit){
		BackUser bu = (BackUser) request.getSession().getAttribute("loginer");
		if(page == null){
			page = 1;
		}
		if(limit == null){
			limit = 12;
		}
		List<SalesPerson> lists = new ArrayList<SalesPerson>();
		int totalCount = 0;
		if (bu.getLoginPlat() == null || bu.getLoginPlat().equals("")) { // 百享园
			totalCount = salersPersonService.getSalerCount(null, sales);
			lists = salersPersonService.getSalerList(null, sales, (page-1) * limit, limit);
		}else{
			if(sales.getAppId() > 0){
				totalCount = salersPersonService.getSalerCount(null, sales);
				lists = salersPersonService.getSalerList(null, sales, (page-1) * limit, limit);
			}else{
				String[] idStrings = bu.getLoginPlat().split(",");
				List<String> loginPlatList = Arrays.asList(idStrings);
				totalCount = salersPersonService.getSalerCount(loginPlatList, sales);
				lists = salersPersonService.getSalerList(loginPlatList, sales, (page-1) * limit, limit);
			}
		}
		output(response,JsonUtil.buildJsonByTotalCount(lists, totalCount));
	}
	@RequestMapping("/updateSaler")
	public void updateSaler(HttpServletResponse response,SalesPerson saler){
		salersPersonService.updateSalesPerson(saler);
		output(response,JsonUtil.buildFalseJson("0","设置成功"));
	}
	@RequestMapping("/editSaler")
	public ModelAndView editSaler(ModelAndView mv,Integer id){
		SalesPerson saler = salersPersonService.getSalesPersonById(id);
		mv.addObject("saler", saler);
		mv.setViewName("saler/edit");
		return mv;
	}
	
}
