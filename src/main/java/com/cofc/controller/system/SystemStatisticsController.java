package com.cofc.controller.system;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;
import javax.persistence.criteria.Order;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cofc.pojo.BackUser;
import com.cofc.service.ApplicationCommonService;
import com.cofc.service.ApplicationLeaveMessageService;
import com.cofc.service.DescoveryCommonService;
import com.cofc.service.GoodsCommonService;
import com.cofc.service.UserCommonService;
import com.cofc.service.UserOrderService;
import com.cofc.util.BaseUtil;

@Controller
@RequestMapping("/systemcount")
public class SystemStatisticsController extends BaseUtil {
	@Resource
    private UserCommonService userService; 
	@Resource
	private UserOrderService orderService;
	@Resource
	private GoodsCommonService goodsService;
	@Resource
	private DescoveryCommonService descoveryService;
	@Resource
	private ApplicationLeaveMessageService messageService;
	@Resource
	private ApplicationCommonService appService;
	@RequestMapping("/gomain")
	public ModelAndView goMain(HttpServletRequest request, ModelAndView mv) {
		BackUser bu = (BackUser) request.getSession().getAttribute("loginer");
		int userCount = 0;
		int orderCount = 0;
		int goodsCount = 0;
		int descoveryCount = 0;
		int messageCount = 0;
		String moneyCount = "";
		int appCount = 0;
		if(bu.getLoginPlat() == null || bu.getLoginPlat().equals("")){ //百享园
			userCount = userService.getCountUser(null, null, null, null,null);
			descoveryCount = descoveryService.getDescoveryCount(null, null, null, null);
			orderCount = orderService.getUserOrderCount(null, null, null);
			goodsCount = goodsService.getCountGoods(null, null, null, null, null, null);
			appCount = appService.getAllAppcalitionCount();
			moneyCount = orderService.getOrderOfMoneyCount(null, null);
			messageCount = messageService.getMessageCount(null);
		}else{
			String[] idStrings = bu.getLoginPlat().split(",");
			List<String> loginPlatList = Arrays.asList(idStrings);
			userCount = userService.getUserCountByLoginPlat(loginPlatList, null, null, null, null);
			descoveryCount = descoveryService.getDescoveryCountByLoginPlat(loginPlatList, null, null, null, null);
		    orderCount = orderService.getUserOrderCountByLoginPlat(loginPlatList, null, null, null);
		    goodsCount = goodsService.getCountGoodsByLoginPlat(loginPlatList, null, null, null, null, null);
		    moneyCount = orderService.getOrderOfMoneyCountByLoginPlat(loginPlatList, null);
		    messageCount = messageService.getMessageCountByLoginPlat(loginPlatList);
		}
     
		mv.addObject("userCount",userCount);
		mv.addObject("orderCount",orderCount);
		mv.addObject("goodsCount",goodsCount);
		mv.addObject("descoveryCount",descoveryCount);
		mv.addObject("messageCount",messageCount);
		mv.addObject("moneyCount",moneyCount);
		mv.addObject("appCount",appCount);
		mv.addObject("loginPlat",bu.getLoginPlat());
	    mv.setViewName("../../main");
		return mv;
	}
}
