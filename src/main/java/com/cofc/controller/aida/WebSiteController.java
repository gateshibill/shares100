package com.cofc.controller.aida;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cofc.pojo.ApplicationCommon;
import com.cofc.pojo.BackUser;
import com.cofc.pojo.GoodsType;
import com.cofc.pojo.aida.WebSite;
import com.cofc.service.ApplicationCommonService;
import com.cofc.service.aida.WebSiteService;
import com.cofc.util.BaseUtil;
import com.cofc.util.JsonUtil;
/**
 * 官网后台编辑
 * @author 46678
 *
 */
@Controller
@RequestMapping("/website")
public class WebSiteController extends BaseUtil{
	@Resource
	private WebSiteService webSiteService;
	@Resource
	private ApplicationCommonService applicationService;
	public  static Logger log = Logger.getLogger("WebSiteController");
	@RequestMapping("/index")
	public ModelAndView index(HttpServletRequest request,ModelAndView mv,WebSite web){
		BackUser bu = (BackUser) request.getSession().getAttribute("loginer");
		List<ApplicationCommon> appList = new ArrayList<ApplicationCommon>();
		if (bu.getLoginPlat() == null || bu.getLoginPlat().equals("")) {
			appList = applicationService.getNewApplicationList(null);
		} else {
			String[] idStrings = bu.getLoginPlat().split(",");
			List<String> loginPlatList = Arrays.asList(idStrings);
			appList = applicationService.getApplicationByLoginPlat(loginPlatList);
		}
		mv.addObject("appList", appList);
		mv.addObject("web", web);
		mv.setViewName("website/index");
		return mv;
	}
	/**
	 * 获取官网数据
	 * @param response
	 * @param web
	 * @param page
	 * @param limit
	 */
	@RequestMapping("/getwebsitelist")
	public void getWebSiteList(HttpServletRequest request,HttpServletResponse response,WebSite web,Integer page,Integer limit){
		BackUser bu = (BackUser) request.getSession().getAttribute("loginer");
		if(page == null){
			page = 1;
		}
		if(limit == null){
			limit = 12;
		}
		int totalCount = 0;
		List<WebSite> list = new ArrayList<WebSite>();

		if(bu.getLoginPlat() == null || bu.getLoginPlat().equals("")){
			 list = webSiteService.getWebSiteList(web, (page-1)*limit, limit);
			 totalCount = webSiteService.getWebSiteCount(web);
		}else{
			 if(web.getAppId() == null){ //应用
				 String[] idStrings = bu.getLoginPlat().split(",");
				 List<String> loginPlatList = Arrays.asList(idStrings);
				 totalCount = webSiteService.getWebSiteCountByLoginPlat(loginPlatList, web);
				 list = webSiteService.getWebSiteListByLoginPlat(loginPlatList, web, (page-1)*limit, limit);
			 }else{
				 list = webSiteService.getWebSiteList(web, (page-1)*limit, limit);
				 totalCount = webSiteService.getWebSiteCount(web);
			 }
		}
		
		output(response,JsonUtil.buildJsonByTotalCount(list, totalCount));
	}
	/**
	 * 增加
	 * @param request
	 * @param mv
	 * @return
	 */
	@RequestMapping("/add")
	public ModelAndView add(HttpServletRequest request,ModelAndView mv){
		BackUser bu = (BackUser) request.getSession().getAttribute("loginer");
		List<ApplicationCommon> appList = new ArrayList<ApplicationCommon>();
		if (bu.getLoginPlat() == null || bu.getLoginPlat().equals("")) {
			appList = applicationService.findApplicationsByCriteria(null, null, null, null, null, null, null, null,
					null, null, null);
		} else {
			String[] idStrings = bu.getLoginPlat().split(",");
			List<String> loginPlatList = Arrays.asList(idStrings);
			appList = applicationService.getApplicationByLoginPlat(loginPlatList);
		}
		mv.addObject("appList", appList);
		mv.setViewName("website/add");
		return mv;
	}
	/**
	 * 执行新增
	 * @param response
	 * @param webSite
	 */
	@RequestMapping("/doadd")
	public void doAdd(HttpServletResponse response,WebSite webSite){
		if(webSite.getAppId() == null){
			output(response,JsonUtil.buildFalseJson("1","系统无法识别该应用"));
		}else{
			WebSite web = webSiteService.getWebSiteInfo(null, webSite.getAppId());
			if(web == null){
				webSite.setIsEffect(1);
				webSite.setCreateTime(new Date());
				webSiteService.addWebSite(webSite);
				output(response,JsonUtil.buildFalseJson("0","添加成功"));
			}else{
				output(response,JsonUtil.buildFalseJson("1","该应用已存在官网数据，请执行编辑"));
			}
		}
	}
	/**
	 * 编辑
	 * @param request
	 * @param mv
	 * @param id
	 */
	@RequestMapping("/edit")
	public ModelAndView edit(HttpServletRequest request,ModelAndView mv,Integer id){
		BackUser bu = (BackUser) request.getSession().getAttribute("loginer");
		List<ApplicationCommon> appList = new ArrayList<ApplicationCommon>();
		if (bu.getLoginPlat() == null || bu.getLoginPlat().equals("")) {
			appList = applicationService.findApplicationsByCriteria(null, null, null, null, null, null, null, null,
					null, null, null);
		} else {
			String[] idStrings = bu.getLoginPlat().split(",");
			List<String> loginPlatList = Arrays.asList(idStrings);
			appList = applicationService.getApplicationByLoginPlat(loginPlatList);
		}
		mv.addObject("appList", appList);
		WebSite webSite = webSiteService.getWebSiteInfo(id, null);
		mv.addObject("web", webSite);
		mv.setViewName("website/edit");
		return mv;
	}
	/**
	 * 执行修改
	 * @param response
	 * @param webSite
	 */
	@RequestMapping("/doedit")
	public void doedit(HttpServletResponse response,WebSite webSite){
		webSiteService.updateWebSite(webSite);
		output(response,JsonUtil.buildFalseJson("0","编辑成功"));
	}
	/**
	 * 删除
	 * @param response
	 * @param id
	 */
	@RequestMapping("/delWebSite")
	public void delWebSite(HttpServletResponse response,Integer id){
		webSiteService.delWebSite(id);
		output(response,JsonUtil.buildFalseJson("0","删除成功"));
	}
}
