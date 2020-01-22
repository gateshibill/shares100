package com.cofc.controller.weiaijia;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cofc.pojo.UserCommon;
import com.cofc.pojo.vij.SystemUpset;
import com.cofc.service.UserShoppingCarService;
import com.cofc.service.vij.SystemUpsetService;
import com.cofc.util.BaseUtil;
/**
 * 唯爱家公共页面的提取
 * @author 46678
 *
 */
@Controller
@RequestMapping("/vij/publich")
public class VijPublichController extends BaseUtil{
	@Resource
	private UserShoppingCarService carService;
	@Resource
	private SystemUpsetService systemUpsetService;
	public  static final int  APP_ID = 709;
	public static Logger log = Logger.getLogger("VijPublichController");
	/**
	 * 头部公共部分
	 * @param request
	 * @param mv
	 * @return
	 */
	@RequestMapping("/header")
	public ModelAndView header(HttpServletRequest request,ModelAndView mv,String hiden){
		SystemUpset systemUpset_1 = new SystemUpset();
		systemUpset_1.setAppId(APP_ID);
		//获取公共信息
		SystemUpset systemUpset = systemUpsetService.getInfoById(systemUpset_1);
		String showPrice = (String)request.getSession().getAttribute("showPrice");
		UserCommon user = (UserCommon)request.getSession().getAttribute("vijMallUserSession");
		if(showPrice == null || showPrice.equals("")){
			showPrice = "t";
		}
		else{
			showPrice = "f";
		}
		request.getSession().setAttribute("showPrice", showPrice);
		int carCount = 0;
		if(user != null){
			carCount = carService.getCarCount(user.getUserId(), 0);
		}
		mv.addObject("systemUpset", systemUpset);
		mv.addObject("carCount", carCount);
		mv.addObject("showPrice", showPrice);
		mv.addObject("hiden", hiden);
		mv.setViewName("vij/header");
		return mv;
	}
	/**
	 * 尾部公共页面
	 * @param request
	 * @param mv
	 * @return
	 */
	@RequestMapping("/footer")
	public ModelAndView footer(HttpServletRequest request,ModelAndView mv){
		SystemUpset systemUpset_1 = new SystemUpset();
		systemUpset_1.setAppId(APP_ID);
		//获取公共信息
		SystemUpset systemUpset = systemUpsetService.getInfoById(systemUpset_1);
		mv.addObject("systemUpset", systemUpset);
		mv.setViewName("vij/copyright");
		return mv;
	}
}
