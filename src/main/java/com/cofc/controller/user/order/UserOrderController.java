package com.cofc.controller.user.order;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.alipay.api.AlipayApiException;
import com.cofc.controller.alipay.AliPayController;
import com.cofc.pojo.ApplicationCommon;
import com.cofc.pojo.BackUser;
import com.cofc.pojo.OperateLog;
import com.cofc.pojo.UserOrder;
import com.cofc.pojo.UserRole;
import com.cofc.pojo.WeiXinRefundResult;
import com.cofc.pojo.vij.Express;
import com.cofc.service.ApplicationCommonService;
import com.cofc.service.OperateLogService;
import com.cofc.service.UserBackuserRelationService;
import com.cofc.service.UserCommonService;
import com.cofc.service.UserOrderService;
import com.cofc.service.UserRoleService;
import com.cofc.service.WeiXinRefundResultService;
import com.cofc.service.vij.ExpressService;
import com.cofc.util.BaseUtil;
import com.cofc.util.JsonUtil;
import com.cofc.util.wxpay.ClientCustomSSL;
import com.cofc.util.wxpay.WeiXinRefundUtil;

@Controller
@RequestMapping("/userorder1")
public class UserOrderController extends BaseUtil {

	@Resource
	private UserOrderService userOrderService;
	@Resource
	private UserRoleService userroleService;
	@Resource
	private ApplicationCommonService applicationService;
	@Resource
	private UserBackuserRelationService uburelaService;
	@Resource
	private OperateLogService logService;
	@Resource
	private UserCommonService userService;
	@Resource
	private ExpressService expressService;//快递
	@Resource
	private WeiXinRefundResultService refundResultService;//微信退款
	public static Logger log = Logger.getLogger("UserOrderController");

	// 全部订单
	@RequestMapping("/userOrderList")
	public ModelAndView userOrderList(ModelAndView modelAndView, HttpServletRequest request) {
		BackUser bu = (BackUser) request.getSession().getAttribute("loginer");
		UserRole userRole = userroleService.getUserRoleById(bu.getUserId());
		String[] rolesarr = userRole.getRoleId().split(",");
		boolean isSuperm = false;
		for (String role : rolesarr) {
			while ("1".equals(role)) {
				isSuperm = true;
				break;
			}
		}
		List<ApplicationCommon> appList = new ArrayList<ApplicationCommon>();
//		if (isSuperm) {
//			appList = applicationService.findApplicationsByCriteria(null, null, null, null, null, null, null, null,
//					null, null, null);
//		} else {
//			List<UserBackuserRelation> userbackList = uburelaService.getUserBackuserList(bu.getUserId());
//			if (!userbackList.isEmpty()) {
//				for (UserBackuserRelation user : userbackList) {
//					appList = applicationService.findApplicationsByCriteria(null, null, null, user.getUserId(), null,
//							null, null, null, null, null, null);
//				}
//			}
//		}
		if (bu.getLoginPlat() == null || bu.getLoginPlat().equals("")) {
			appList = applicationService.getNewApplicationList(null);
		} else {
			String[] idStrings = bu.getLoginPlat().split(",");
			List<String> loginPlatList = Arrays.asList(idStrings);
			appList = applicationService.getApplicationByLoginPlat(loginPlatList);
		}
		modelAndView.addObject("appList", appList);
		modelAndView.setViewName("/userorderManage/userorderList");
		return modelAndView;
	}

	// 退货订单
	@RequestMapping("/retreatOrderList")
	public ModelAndView retreatOrderList(ModelAndView modelAndView, HttpServletRequest request) {
		BackUser bu = (BackUser) request.getSession().getAttribute("loginer");
		UserRole userRole = userroleService.getUserRoleById(bu.getUserId());
		String[] rolesarr = userRole.getRoleId().split(",");
		boolean isSuperm = false;
		for (String role : rolesarr) {
			while ("1".equals(role)) {
				isSuperm = true;
				break;
			}
		}
		List<ApplicationCommon> appList = new ArrayList<ApplicationCommon>();
	//	if (isSuperm) {
//			appList = applicationService.findApplicationsByCriteria(null, null, null, null, null, null, null, null,
//					null, null, null);
//		} else {
//			List<UserBackuserRelation> userbackList = uburelaService.getUserBackuserList(bu.getUserId());
//			if (!userbackList.isEmpty()) {
//				for (UserBackuserRelation user : userbackList) {
//					appList = applicationService.findApplicationsByCriteria(null, null, null, user.getUserId(), null,
//							null, null, null, null, null, null);
//				}
//			}
//		}
		if (bu.getLoginPlat() == null || bu.getLoginPlat().equals("")) {
			appList = applicationService.getNewApplicationList(null);
		} else {
			String[] idStrings = bu.getLoginPlat().split(",");
			List<String> loginPlatList = Arrays.asList(idStrings);
			appList = applicationService.getApplicationByLoginPlat(loginPlatList);
		}
		modelAndView.addObject("appList", appList);
		modelAndView.setViewName("/userorderManage/retreatOrderList");
		return modelAndView;
	}

	// 发货订单
	@RequestMapping("/publicOrderList")
	public ModelAndView publicOrderList(ModelAndView modelAndView, HttpServletRequest request) {
		BackUser bu = (BackUser) request.getSession().getAttribute("loginer");
		UserRole userRole = userroleService.getUserRoleById(bu.getUserId());
		String[] rolesarr = userRole.getRoleId().split(",");
		boolean isSuperm = false;
		for (String role : rolesarr) {
			while ("1".equals(role)) {
				isSuperm = true;
				break;
			}
		}
		List<ApplicationCommon> appList = new ArrayList<ApplicationCommon>();
//		if (isSuperm) {
//			appList = applicationService.findApplicationsByCriteria(null, null, null, null, null, null, null, null,
	//				null, null, null);
//		} else {
//			List<UserBackuserRelation> userbackList = uburelaService.getUserBackuserList(bu.getUserId());
//			if (!userbackList.isEmpty()) {
//				for (UserBackuserRelation user : userbackList) {
//					appList = applicationService.findApplicationsByCriteria(null, null, null, user.getUserId(), null,
//							null, null, null, null, null, null);
//				}
//			}
//		}
		if (bu.getLoginPlat() == null || bu.getLoginPlat().equals("")) {
			appList = applicationService.getNewApplicationList(null);
		} else {
			String[] idStrings = bu.getLoginPlat().split(",");
			List<String> loginPlatList = Arrays.asList(idStrings);
			appList = applicationService.getApplicationByLoginPlat(loginPlatList);
		}
		modelAndView.addObject("appList", appList);
		modelAndView.setViewName("/userorderManage/publicOrderList");
		return modelAndView;
	}

	@RequestMapping("/showUserorderList")
	public void showOrderList(HttpServletRequest request,HttpServletResponse response, String dateRange, Integer page, Integer limit,
			UserOrder order) throws Exception {
		BackUser bu = (BackUser) request.getSession().getAttribute("loginer");
		if (page == null) {
			page = 1;
		}
		if (limit == null) {
			limit = 20;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat startSdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		SimpleDateFormat endSdf = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
		String startTime = null;
		String endTime = null;
		if (dateRange != null && !dateRange.equals("")) {
			String[] myData = dateRange.split(" -| ");
			startTime = myData[0];
			endTime = myData[2];
		}
		if (startTime != null) {
			startTime = startSdf.format(sdf.parse(startTime));
		}
		if (endTime != null) {
			endTime = endSdf.format(sdf.parse(endTime));
		}
		List<UserOrder> orderList = new ArrayList<UserOrder>();
		int count = 0;
		if(bu.getLoginPlat() == null || bu.getLoginPlat().equals("")){
			 orderList = userOrderService.getUserOrderList(order, (page - 1) * limit, limit, startTime,
						endTime);
			 count = userOrderService.getUserOrderCount(order, startTime, endTime);
		}else{
			 if(order.getLoginPlat() == null){ //应用
				 String[] idStrings = bu.getLoginPlat().split(",");
				 List<String> loginPlatList = Arrays.asList(idStrings);
				 count = userOrderService.getUserOrderCountByLoginPlat(loginPlatList, order, startTime, endTime);
				 orderList = userOrderService.getUserOrderListByLoginPlat(loginPlatList, order, (page-1) * limit, limit, startTime, endTime);
			 }else{
				 orderList = userOrderService.getUserOrderList(order, (page - 1) * limit, limit, startTime,
							endTime);
				 count = userOrderService.getUserOrderCount(order, startTime, endTime); 
			 }
		}
		
		output(response, JsonUtil.buildJsonByTotalCount(orderList, count));
	}

	@RequestMapping("/deleteUserOrder")
	public void deleteUserOrder(HttpServletResponse response, Integer orderID, HttpServletRequest request) {
		UserOrder order = userOrderService.findByID(orderID);
		BackUser bu = (BackUser) request.getSession().getAttribute("loginer");
		OperateLog log = new OperateLog();
		log.setOperateName("【" + bu.getUserName() + "】" + "删除用户" + "【" + order.getConsignee() + "】的订单");
		log.setOperateTime(new Date());
		log.setOperateUser(bu.getUserName());
		try {
			log.setOperateResult(1);
			logService.addOperateLog(log);
			order.setUpdateTime(new Date());
			order.setOrderStatus(-4);
			userOrderService.updateUserOrder(order);
			output(response, JsonUtil.buildFalseJson("0", "删除订单成功!"));
		} catch (Exception e) {
			log.setOperateResult(2);
			logService.addOperateLog(log);
			output(response, JsonUtil.buildFalseJson("1", "删除订单失败!"));
		}
	}

	@RequestMapping("/userOrderDateils")
	public ModelAndView userOrderDateils(ModelAndView modelAndView, Integer orderID) {
		UserOrder order = userOrderService.findByID(orderID);
		modelAndView.addObject("order", order);
		Express exp = new Express();
		exp.setIsEffect(1);
		exp.setIsRemove(0);
		exp.setLoginPlat(order.getLoginPlat());
		List<Express> lists = expressService.getExpressList(exp, null, null);
		modelAndView.addObject("expresslist", lists);
		modelAndView.setViewName("/userorderManage/userorderDateils");
		return modelAndView;
	}

	@RequestMapping("/updateUserOrder")
	public void updateUserOrder(HttpServletResponse response, UserOrder order) {
		UserOrder order2 = userOrderService.findByID(order.getOrderID());
		if (order2 == null) {
			output(response, JsonUtil.buildFalseJson("1", "该订单不存在!"));
		}
//		else if (order2.getOrderStatus() != 7 && order2.getOrderStatus() != 5) {
//			output(response, JsonUtil.buildFalseJson("2", "该订单暂时不能修改!"));
//		} 
		else {
			order.setUpdateTime(new Date());
			userOrderService.updateUserOrder(order);
			output(response, JsonUtil.buildFalseJson("0", "修改成功!"));
		}
	}
   
	@RequestMapping("/showRetrearOrderList")
	public void showRetrearOrderList(HttpServletRequest request,HttpServletResponse response, String dateRange, Integer page, Integer limit,
			UserOrder order) throws Exception {
		BackUser bu = (BackUser) request.getSession().getAttribute("loginer");
		if (page == null) {
			page = 1;
		}
		if (limit == null) {
			limit = 20;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat startSdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		SimpleDateFormat endSdf = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
		String startTime = null;
		String endTime = null;
		if (dateRange != null && !dateRange.equals("")) {
			String[] myData = dateRange.split(" -| ");
			startTime = myData[0];
			endTime = myData[2];
		}
		if (startTime != null) {
			startTime = startSdf.format(sdf.parse(startTime));
		}
		if (endTime != null) {
			endTime = endSdf.format(sdf.parse(endTime));
		}
		List<UserOrder> orderList = new ArrayList<UserOrder>();
		int count = 0;
		if(bu.getLoginPlat() == null || bu.getLoginPlat().equals("")){
			 orderList = userOrderService.getUserOrderList(order, (page - 1) * limit, limit, startTime,
						endTime);
			 count = userOrderService.getUserOrderCount(order, startTime, endTime);
		}else{
			 if(order.getLoginPlat() == null){ //应用
				 String[] idStrings = bu.getLoginPlat().split(",");
				 List<String> loginPlatList = Arrays.asList(idStrings);
				 count = userOrderService.getUserOrderCountByLoginPlat(loginPlatList, order, startTime, endTime);
				 orderList = userOrderService.getUserOrderListByLoginPlat(loginPlatList, order, (page-1) * limit, limit, startTime, endTime);
			 }else{
				 orderList = userOrderService.getUserOrderList(order, (page - 1) * limit, limit, startTime,
							endTime);
				 count = userOrderService.getUserOrderCount(order, startTime, endTime); 
			 }
		}
		
		output(response, JsonUtil.buildJsonByTotalCount(orderList, count));
	}
	
	/*=================================订单和交易金额统计=============================================*/
	
	/**
	 * 获取
	 * @param request
	 * @param response
	 * @param loginPlat
	 * @param startTime
	 * @param endTime
	 */
	@RequestMapping("/showtraderecord")
	public void showTradeRecored(HttpServletRequest request,HttpServletResponse response, String dateRange, Integer page, Integer limit,
			UserOrder order) throws Exception {
		BackUser bu = (BackUser) request.getSession().getAttribute("loginer");
		if (page == null) {
			page = 1;
		}
		if (limit == null) {
			limit = 20;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat startSdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		SimpleDateFormat endSdf = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
		String startTime = null;
		String endTime = null;
		if (dateRange != null && !dateRange.equals("")) {
			String[] myData = dateRange.split(" -| ");
			startTime = myData[0];
			endTime = myData[2];
		}
		if (startTime != null) {
			startTime = startSdf.format(sdf.parse(startTime));
		}
		if (endTime != null) {
			endTime = endSdf.format(sdf.parse(endTime));
		}
		List<UserOrder> orderList = new ArrayList<UserOrder>();
		int count = 0;
		if(bu.getLoginPlat() == null || bu.getLoginPlat().equals("")){
			 orderList = userOrderService.getPayList(order, (page-1) * limit, limit, startTime, endTime);
			 count = userOrderService.getPayCount(order, startTime, endTime);
		}else{
			 if(order.getLoginPlat() == null){ //应用
				 String[] idStrings = bu.getLoginPlat().split(",");
				 List<String> loginPlatList = Arrays.asList(idStrings);
				 count = userOrderService.getPayCountByLoginPlat(loginPlatList, order, startTime, endTime);
				 orderList = userOrderService.getPayListByLoginPlat(loginPlatList, order,(page-1)*limit, limit, startTime, endTime);
			 }else{
				 orderList = userOrderService.getPayList(order, (page-1) * limit, limit, startTime, endTime);
				 count = userOrderService.getPayCount(order, startTime, endTime);
			 }
		}
		
		output(response, JsonUtil.buildJsonByTotalCount(orderList, count));
	}
	
	/******************************微信退款*******************************************/
	/**
	 * 微信退款
	 * @param request
	 * @param response
	 * @param orderId
	 * @throws Exception 
	 */
	@RequestMapping("/wxrefund")
	public void wxrefund(HttpServletRequest request,HttpServletResponse response,String orderNo) throws Exception{
		BackUser bu = (BackUser) request.getSession().getAttribute("loginer");
		if(bu == null){
			log.info("wxrefund():bu is null");
			output(response,JsonUtil.buildFalseJson("1", "请先登录"));
		}else{
			UserOrder order = userOrderService.getOrderByOrderNo(orderNo);
			if(order == null){
				log.info("wxrefund():order is null");
				output(response,JsonUtil.buildFalseJson("1", "订单不存在"));
			}else{
				if(order.getOrderStatus() != -2){
					log.info("wxrefund(): orderStatus = "+order.getOrderStatus());
					output(response,JsonUtil.buildFalseJson("1", "该条订单不允许退款"));
				}else{
					if(order.getLoginPlat() == null){
						log.info("wxrefund(): loginPlat is "+order.getLoginPlat());
						output(response,JsonUtil.buildFalseJson("1", "未知应用"));
					}else{
						ApplicationCommon appinfo = applicationService.getApplicationCommonById(order.getLoginPlat());
						if(appinfo == null){
							log.info("wxrefund(): appinfo is null");
							output(response,JsonUtil.buildFalseJson("1", "未知应用"));
						}else{
							Integer orderSource = 1;
							if(order.getOrderSource() != null){
								orderSource = order.getOrderSource();
							}
							//退款证书位置
							String path = request.getServletContext().getRealPath("/")+"/weiaijiarefundcertificate/weiaijiatuikuanshuangxiangzhengshu.p12";
							Map<String, String> result = ClientCustomSSL.refund(path,order.getOrderNo(), 
									order.getRealFee(),appinfo,orderSource);
							log.info("wxrefund(): 退款返回的结果 result = "+result);
							if(result == null){
								log.info("wxrefund(): result is null");
								output(response,JsonUtil.buildFalseJson("1", "退款失败"));
							}else{
								String returnCode = result.get("return_code");
								String returnMsg = result.get("return_msg");
								if("SUCCESS".equals(returnCode) && "OK".equals(returnMsg)){//退款成功
									//插入退款成功结果
									WeiXinRefundResult refundResult = WeiXinRefundUtil.addWeiXinRefundResult(result);
									try {
										refundResultService.addRefundResult(refundResult);
									} catch (Exception e) {
										log.info("插入退款通知异常:"+e.getMessage());
									}
									//更改订单状态
									UserOrder neworder = new UserOrder();
									neworder.setOrderID(order.getOrderID());
									neworder.setOrderStatus(-5);
									userOrderService.updateUserOrder(neworder);
									//是否需要发送短信
									
									output(response,JsonUtil.buildFalseJson("0", "退款成功"));
								}else{
									log.info("wxrefund():result_code = "+returnCode+",return_msg = "+returnMsg);
									output(response,JsonUtil.buildFalseJson("1", "退款失败"));
								}
							}
						}
					}
				}
			}
		}
	}
	
	/****************************支付宝 退款*************************************/
	/**
	 * 统一收单交易退款接口:alipay.trade.refund  用订单编号退款
	 * @param response
	 * @throws AlipayApiException 
	 */
	@RequestMapping("/aliRefund")
	public void aliRefund(HttpServletResponse response,HttpServletRequest request,String orderNo) throws AlipayApiException{
		BackUser bu = (BackUser) request.getSession().getAttribute("loginer");
		if(bu == null){
			log.info("aliRefund():bu is null");
			output(response,JsonUtil.buildFalseJson("1", "请先登录"));
		}else{
			if(orderNo == null || orderNo.equals("")){
				log.info("aliRefund():orderNo is null");
				output(response,JsonUtil.buildFalseJson("1", "订单号为空"));
			}else{
				UserOrder order = userOrderService.getOrderByOrderNo(orderNo);
				if(order == null){
					log.info("aliRefund():order is null");
					output(response,JsonUtil.buildFalseJson("1", "订单不存在"));
				}else{
					if(order.getOrderStatus() != -2){
						log.info("aliRefund(): orderStatus = "+order.getOrderStatus());
						output(response,JsonUtil.buildFalseJson("1", "该条订单不允许退款"));
					}else{
						Map<String,String> map = new HashMap<String,String>();
						map.put("order_no",order.getOrderNo());
						map.put("real_fee", String.valueOf(order.getRealFee()));
						String result = AliPayController.alipayRefund(map);
						if(result == "success"){
							//插入通知
							
							//更改订单状态
							UserOrder neworder = new UserOrder();
							neworder.setOrderID(order.getOrderID());
							neworder.setOrderStatus(-5);
							userOrderService.updateUserOrder(neworder);
							output(response,JsonUtil.buildFalseJson("0", "退款成功"));
						}else{
							output(response,JsonUtil.buildFalseJson("1", "退款失败"));
						}
					}
				}
			}
		}
	}
	/**
	 * 统一收单交易关闭接口:alipay.trade.pay
	 * @param response
	 */
	public void aliCloseOrder(HttpServletResponse response){
		
	}
}
