package com.cofc.controller.recharge;

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
import com.cofc.pojo.RechargeAmount;
import com.cofc.service.ApplicationCommonService;
import com.cofc.service.RechargeAmountService;
import com.cofc.util.BaseUtil;
import com.cofc.util.JsonUtil;

@Controller
@RequestMapping("/rechargeamount")
public class RechargeAmountController extends BaseUtil {
	@Resource
	private RechargeAmountService amountService;
	@Resource
	private ApplicationCommonService appService;
	public static Logger log = Logger.getLogger("RechargeAmountController");
	/**
	 * 
	 * @param request
	 * @param mv
	 * @return
	 */
	@RequestMapping("/gorechargeamount")
	public ModelAndView goRechargeAmount(HttpServletRequest request, ModelAndView mv) {
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
		mv.setViewName("capitalManage/rechargeamountList");
		return mv;
	}
	/**
	 * 获取充值列表
	 * @param response
	 * @param request
	 * @param recharge
	 * @param page
	 * @param limit
	 */
	@RequestMapping("/getrechargelist")
	public void getRechargeList(HttpServletResponse response,HttpServletRequest request,
			RechargeAmount recharge,Integer page,Integer limit){
		BackUser bu = (BackUser) request.getSession().getAttribute("loginer");
		if(page == null){
			page = 1;
		}
		if(limit == null){
			limit = 15;
		}
		int rowcount = 0;
		List<RechargeAmount> lists = new ArrayList<RechargeAmount>();
		if(bu.getLoginPlat() == null || bu.getLoginPlat().equals("")){
			rowcount = amountService.getRechargeCount(recharge);
			lists = amountService.getRechargeInfo(recharge, (page-1) * limit, limit);
		}else{
			if(recharge.getLoginPlat() == null){
				 String[] idStrings = bu.getLoginPlat().split(",");
				 List<String> loginPlatList = Arrays.asList(idStrings);
				 rowcount = amountService.getRechargeCountByLoginPlat(loginPlatList, recharge);
				 lists = amountService.getRechargeInfoByLoginPlat(loginPlatList, recharge,(page-1) * limit, limit);
			}else{
				rowcount = amountService.getRechargeCount(recharge);
				lists = amountService.getRechargeInfo(recharge, (page-1) * limit, limit);
			}
		}
		
		output(response,JsonUtil.buildJsonByTotalCount(lists, rowcount));
	}
	
	/**
	 * 
	 * @param request
	 * @param mv
	 * @return
	 */
	@RequestMapping("/goaddpage")
	public ModelAndView goAddPage(HttpServletRequest request,ModelAndView mv,Integer loginPlat){
		mv.addObject("loginPlat",loginPlat);
		mv.setViewName("capitalManage/addrechargeamount");
		return mv;
	}
	@RequestMapping("/doaddamount")
	public void doAddAmount(HttpServletResponse response,RechargeAmount recharge){
		if(recharge.getLoginPlat() == null){
			output(response,JsonUtil.buildFalseJson("1","应用id必填"));
			return;
		}
		recharge.setCreateTime(new Date());
		amountService.addRechargeInfo(recharge);
		try {
			log.info("添加充值优惠成功");
			output(response,JsonUtil.buildFalseJson("0","添加充值优惠成功"));
		} catch (Exception e) {
			log.info("添加充值优惠失败，失败原因："+e);
			output(response,JsonUtil.buildFalseJson("1","添加充值优惠失败"));
		}
	}
	/**
	 * 进入修改页面
	 * @param request
	 * @param mv
	 */
	@RequestMapping("/goupdatepage")
	public ModelAndView goUpdatePage(HttpServletRequest request,ModelAndView mv,Integer discountId){
	    RechargeAmount info = amountService.getDetailInfo(discountId);
	    mv.addObject("amount",info);
		mv.setViewName("capitalManage/updaterechargeamount");
		return mv;
	}
	/**
	 * 
	 * @param response
	 * @param recharge
	 */
	@RequestMapping("/doupdateamount")
	public void doUpdateAmount(HttpServletResponse response,RechargeAmount recharge){
		if(recharge.getDiscountId() == null){
			output(response,JsonUtil.buildFalseJson("1","id必填"));
			return;
		}
		amountService.updateRechargeInfo(recharge);
		try {
			log.info("编辑充值优惠成功");
			output(response,JsonUtil.buildFalseJson("0","编辑充值优惠成功"));
		} catch (Exception e) {
			log.info("编辑充值优惠失败，失败原因："+e);
			output(response,JsonUtil.buildFalseJson("1","编辑充值优惠失败"));
		}
	}
	@RequestMapping("/dodeleamount")
	public void doDeleteAmount(HttpServletResponse response,Integer discountId){
		if(discountId == null){
			output(response,JsonUtil.buildFalseJson("1","传参数有误"));
			return;
		}
		amountService.deleteRecharInfo(discountId);
		try {
			log.info("删除充值优惠成功");
			output(response,JsonUtil.buildFalseJson("0","删除充值优惠成功"));
		} catch (Exception e) {
			log.info("删除充值优惠失败，失败原因："+e);
			output(response,JsonUtil.buildFalseJson("1","删除充值优惠失败"));
		}
	}
}
