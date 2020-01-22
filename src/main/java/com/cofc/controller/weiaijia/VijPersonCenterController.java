package com.cofc.controller.weiaijia;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cofc.pojo.UserCommon;
import com.cofc.pojo.UserShoppingAddress;
import com.cofc.pojo.vij.SystemUpset;
import com.cofc.service.UserCommonService;
import com.cofc.service.UserOrderService;
import com.cofc.service.UserShoppingAddressService;
import com.cofc.service.vij.SystemUpsetService;
import com.cofc.util.BaseUtil;
/**
 * 个人中心页面渲染类
 * @author 46678
 *
 */
@Controller
@RequestMapping("/vij/person")
public class VijPersonCenterController extends BaseUtil{
	@Resource
	private UserCommonService userCommonService;
	@Resource
	private UserOrderService orderService;
	@Resource
	private UserShoppingAddressService addressService; //地址类
	@Resource
	private SystemUpsetService systemUpsetService;
	public static Logger log = Logger.getLogger("VijPersonCenterController");
	/**
	 * 个人中心公共部分
	 * @param request
	 * @param mv
	 * @return
	 */
	@RequestMapping("/common")
	public ModelAndView common(HttpServletRequest request,ModelAndView mv){
		SystemUpset systemUpset_1 = new SystemUpset();
		systemUpset_1.setAppId(709);
		//获取公共信息
		SystemUpset systemUpset = systemUpsetService.getInfoById(systemUpset_1);
		mv.addObject("systemUpset", systemUpset);
		mv.setViewName("vij/person/person_common");
		return mv;
	}
	/**
	 * 个人中心首页
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/index")
	public ModelAndView index(HttpServletRequest request,ModelAndView mv){
		UserCommon user = (UserCommon) request.getSession().getAttribute("vijMallUserSession");
		if(user == null){ //登陆
			mv.setViewName("vij/user_login");
		}else{
			UserCommon info = userCommonService.getUserByUserId(user.getUserId());
			mv.addObject("user", info);
			mv.setViewName("vij/person/index");
		}
		return mv;
	}
	/**
	 * 我的订单
	 * @param request
	 * @param mv
	 * @return
	 */
	@RequestMapping("/myorder")
	public ModelAndView myorder(HttpServletRequest request,ModelAndView mv){
		UserCommon user = (UserCommon) request.getSession().getAttribute("vijMallUserSession");
		if(user == null){ //登陆
			mv.setViewName("vij/user_login");
		}else{
			
		}
		return mv;
	}
	/**
	 * 我的收藏
	 * @param request
	 * @param mv
	 * @return
	 */
	@RequestMapping("/mycollect")
	public ModelAndView mycollect(HttpServletRequest request,ModelAndView mv){
		UserCommon user = (UserCommon) request.getSession().getAttribute("vijMallUserSession");
		if(user == null){ //登陆
			mv.setViewName("vij/user_login");
		}else{
			
		}
		return mv;
	}
	/**
	 * 我的购物车
	 * @param request
	 * @param mv
	 * @return
	 */
	@RequestMapping("/mycar")
	public ModelAndView mycar(HttpServletRequest request,ModelAndView mv){
		UserCommon user = (UserCommon) request.getSession().getAttribute("vijMallUserSession");
		if(user == null){ //登陆
			mv.setViewName("vij/user_login");
		}else{
			
		}
		return mv;
	}
	/**
	 * 我的地址
	 * @param request
	 * @param mv
	 * @return
	 */
	@RequestMapping("/myaddress")
	public ModelAndView myaddress(HttpServletRequest request,ModelAndView mv){
		UserCommon user = (UserCommon) request.getSession().getAttribute("vijMallUserSession");
		if(user == null){ //登陆
			mv.setViewName("vij/user_login");
		}else{
			UserCommon info = userCommonService.getUserByUserId(user.getUserId());
			List<UserShoppingAddress> address = addressService.findShoppingAddress(info.getUserId());
			mv.addObject("address", address);
			mv.setViewName("vij/person/user_address");
		}
		return mv;
	}
	/**
	 * 我的优惠券
	 * @param request
	 * @param mv
	 * @return
	 */
	@RequestMapping("/mycoupon")
	public ModelAndView mycoupon(HttpServletRequest request,ModelAndView mv){
		UserCommon user = (UserCommon) request.getSession().getAttribute("vijMallUserSession");
		if(user == null){ //登陆
			mv.setViewName("vij/user_login");
		}else{
			
		}
		return mv;
	}
	/**
	 * 我的资料
	 * @param request
	 * @param mv
	 * @return
	 */
	@RequestMapping("/myinfo")
	public ModelAndView myinfo(HttpServletRequest request,ModelAndView mv){
		UserCommon user = (UserCommon) request.getSession().getAttribute("vijMallUserSession");
		if(user == null){ //登陆
			mv.setViewName("vij/user_login");
		}else{
			UserCommon info = userCommonService.getUserByUserId(user.getUserId());
			mv.addObject("info", info);
			mv.setViewName("vij/person/user_info");
		}
		return mv;
	}
	
	/**
	 * 账号设置
	 * @param request
	 * @param mv
	 * @return
	 */
	@RequestMapping("/account")
	public ModelAndView account(HttpServletRequest request,ModelAndView mv){
		UserCommon user = (UserCommon) request.getSession().getAttribute("vijMallUserSession");
		if(user == null){ //登陆
			mv.setViewName("vij/user_login");
		}else{
			mv.setViewName("vij/person/safe_setup");
		}
		return mv;
	}
	/**
	 * 系统消息
	 * @param request
	 * @param mv
	 * @return
	 */
	@RequestMapping("/message")
	public ModelAndView message(HttpServletRequest request,ModelAndView mv){
		UserCommon user = (UserCommon) request.getSession().getAttribute("vijMallUserSession");
		if(user == null){ //登陆
			mv.setViewName("vij/user_login");
		}else{
			mv.setViewName("vij/person/system_news");
		}
		return mv;
	}
	/**
	 * 设置
	 * @param request
	 * @param mv
	 * @return
	 */
	@RequestMapping("/setup")
	public ModelAndView setup(HttpServletRequest request,ModelAndView mv){
		UserCommon user = (UserCommon) request.getSession().getAttribute("vijMallUserSession");
		if(user == null){ //登陆
			mv.setViewName("vij/user_login");
		}else{
			mv.setViewName("vij/person/setup");
		}
		return mv;
	}
	
}
