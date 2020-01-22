package com.cofc.controller.recharge;

import java.util.ArrayList;
import java.util.Arrays;
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
import com.cofc.pojo.RechargeOrder;
import com.cofc.service.ApplicationCommonService;
import com.cofc.service.RechargeOrderService;
import com.cofc.service.UserCommonService;
import com.cofc.util.BaseUtil;
import com.cofc.util.JsonUtil;

@Controller
@RequestMapping("/rechargeorder")
public class RechargeOrderController extends BaseUtil {
	 @Resource
     private RechargeOrderService rechargeOrderService;
	 @Resource
	 private ApplicationCommonService appService;
	 @Resource
	 private UserCommonService userService;
	 public static Logger log = Logger.getLogger("RechargeOrderController");
	 /**
	  * 进入充值列表页面
	  * @return
	  */
	 @RequestMapping("/gorechargepage")
	 public ModelAndView goRechargeOrderPage(HttpServletRequest request,ModelAndView mv){
		BackUser bu = (BackUser) request.getSession().getAttribute("loginer");
		List<ApplicationCommon> appList = new ArrayList<ApplicationCommon>(); 
		if (bu.getLoginPlat() == null || bu.getLoginPlat().equals("")) {
				appList = appService.getNewApplicationList(null);
		} else {
				String[] idStrings = bu.getLoginPlat().split(",");
				List<String> loginPlatList = Arrays.asList(idStrings);
				appList = appService.getApplicationByLoginPlat(loginPlatList);
		}
		 mv.addObject("appList", appList);
		 mv.setViewName("capitalManage/rechargeList");
		 return mv;
		 
	 }
	 
	 /**
	  * 获取充值列表
	  * @param request
	  * @param response
	  * @param order
	  * @param page
	  * @param limit
	  */
	 @RequestMapping("/getrechargelist")
	 public void getRechargeList(HttpServletRequest request,HttpServletResponse response,RechargeOrder order,
			 Integer page,Integer limit){
		 BackUser bu = (BackUser) request.getSession().getAttribute("loginer");
		 if(page == null){
			 page = 1;
		 }
		 if(limit == null){
			 limit = 15;
		 }
		 int rowcount = 0;
		 List<RechargeOrder> lists = new ArrayList<RechargeOrder>();
		 if(bu.getLoginPlat() == null || bu.getLoginPlat().equals("")){
			 rowcount = rechargeOrderService.getRechargeCount(order);
			 lists = rechargeOrderService.getAllRechargeList(order,(page-1) * limit, limit);
		 }else{
			 if(order.getLoginPlat() == null){
				 String[] idStrings = bu.getLoginPlat().split(",");
				 List<String> loginPlatList = Arrays.asList(idStrings);
				 rowcount = rechargeOrderService.getRechargeCountByLoginPlat(loginPlatList, order);
				 lists = rechargeOrderService.getAllRechargeListByLoginPlat(loginPlatList, order, (page-1) * limit, limit);
			 }else{
				 rowcount = rechargeOrderService.getRechargeCount(order);
				 lists = rechargeOrderService.getAllRechargeList(order,(page-1) * limit, limit);
			 }
		 }
		 output(response,JsonUtil.buildJsonByTotalCount(lists, rowcount));
	 }
}
