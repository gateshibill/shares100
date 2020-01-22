package com.cofc.controller.recharge;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cofc.pojo.ApplicationCommon;
import com.cofc.pojo.RechargeAmount;
import com.cofc.pojo.RechargeOrder;
import com.cofc.pojo.UserCommon;
import com.cofc.service.ApplicationCommonService;
import com.cofc.service.RechargeAmountService;
import com.cofc.service.RechargeOrderService;
import com.cofc.service.UserCommonService;
import com.cofc.util.BaseUtil;
import com.cofc.util.JsonUtil;
import com.cofc.util.MD5Util;
import com.cofc.util.wxpay.WXPreOrderUtil;
import com.cofc.util.wxpay.WeiXinPayConfig;

@Controller
@RequestMapping("/wx/rechargeorder")
public class WXRechargeOrderController extends BaseUtil {
	 @Resource
     private RechargeOrderService rechargeOrderService;
	 @Resource
	 private RechargeAmountService amountService;
	 @Resource
	 private ApplicationCommonService appService;
	 @Resource
	 private UserCommonService userService;
	 public static Logger log = Logger.getLogger("WXRechargeOrderController");
	 /**
	  * 创建充值订单
	  * @param response
	  * @param order
	  */
	 @RequestMapping("/createrechargeorder")
	 public void createRechargeOrder(HttpServletResponse response,RechargeOrder order,Integer discountId){
		  if(order.getLoginPlat() == null){
			  output(response,JsonUtil.buildFalseJson("1","平台id为空"));
			  return;
		  }
		  if(order.getUserId() == null){
			  output(response,JsonUtil.buildFalseJson("1","用户id为空"));
			  return;
		  }
		  if(discountId != 0){ //优惠活动充值的
			  RechargeAmount amount = amountService.getDetailInfo(discountId);
			  order.setRechargeMoney(amount.getRechargeAmount());
			  order.setSendMoney(amount.getSendAmount());
			  order.setRemark("充值"+amount.getRechargeAmount()+"元送"+amount.getSendAmount()+"元");
		  }else{ //文本框充值的
			  if(order.getRechargeMoney()>0){
				  order.setRechargeMoney(order.getRechargeMoney());  
				  order.setRemark("充值"+order.getRechargeMoney()+"元");
			  }else{
				  output(response, JsonUtil.buildFalseJson("1","请输入合法的金额"));
			  }
		  }
		  order.setCreateTime(new Date());
		  order.setIsRemove(0);
		  order.setRechargeStatus(0);
		  rechargeOrderService.addRechargeOrder(order);
		    try {
		      log.info("下单成功");
		      Integer orderID = order.getRechargeId();//获取返回的ID
		      output(response, String.format("{\"code\":0, \"msg\":\"下单成功\", \"orderID\": %s}", orderID));
			} catch (Exception e) {
			  log.info("下单失败,失败原因："+e);
			  output(response,JsonUtil.buildFalseJson("1","下单失败"));
			}
	 }
	 
	 /**
	  * 微信支付
	  * @param response
	  * @param request
	  * @param rechargeId
	  * @throws Exception
	  */
	 @RequestMapping("/payforrechargeorder")
	 public void payForRechargeOrder(HttpServletResponse response,HttpServletRequest request,Integer rechargeId,
			 String userIp,String payName) throws Exception{
		    WeiXinPayConfig config = new WeiXinPayConfig();
			String contextPath = request.getContextPath();// 项目名
			String port = request.getServerPort() == 80 ? "" : ":" + request.getServerPort();
			String weburl = request.getScheme() + "://" + request.getServerName() + port + contextPath;
			RechargeOrder order = rechargeOrderService.getlistByRechargeId(rechargeId);
			if(order == null){
				log.info("order值为null,统一下单失败");
				output(response,JsonUtil.buildFalseJson("1","统一下单失败"));
				return;
			}
			ApplicationCommon currapp = appService.getApplicationById(order.getLoginPlat());
			if(currapp == null){
				log.info("currapp值为null,统一下单失败");
				output(response,JsonUtil.buildFalseJson("1","统一下单失败"));
			}else{
				 if(currapp.getMchid() == null || currapp.getApiKey()== null || currapp.getAppId()== null){ //默认不为空
					 config.setAppid("wx474aed7d8ee915d9"); //百享园
					 config.setMch_id("1349602401");
					 config.setApiKey("FKjtLr490zzntD1IFy75EZ9zypTbhjWe");
					
				 }else{
					 config.setAppid(currapp.getAppId());
					 config.setMch_id(currapp.getMchid());
					 config.setApiKey(currapp.getApiKey());
				 }
				config.setNotify_url(weburl + "/wx/weixinPayNotify/notifyofrecharge.do");
				double rechargeMoney = 0.00;
				if(order.getRechargeStatus() == 0){
					UserCommon user = userService.getUserByUserId(order.getUserId());
					if(user == null){
						log.info("获取用户信息失败，user==null，统一下单失败");
						output(response,JsonUtil.buildFalseJson("1","获取用户信息失败，统一下单失败"));
						return;
					}
					rechargeMoney = order.getRechargeMoney();
					Map<String, String> map = WXPreOrderUtil.getPrepayInfo(order.getRechargeId()+"", user.getOpenId(),rechargeMoney,
							userIp, payName, config);
					String return_code = map.get("return_code");
					if (StringUtils.isNotBlank(return_code) && return_code.equals("SUCCESS")) {
						String return_msg = map.get("return_msg");
						if (StringUtils.isBlank(return_msg) && !("OK").equals(return_msg)) {
							output(response, JsonUtil.buildFalseJson("2", "统一下单错误,微信返回信息异常!"));
						} else {
							String prepayId = map.get("prepay_id");// 微信返回的支付交易会话ID
							String appId = map.get("appid");// 应用ID
							String partnerId = map.get("mch_id");// 微信支付分配的商户号
							String nonceStr = map.get("nonce_str");// 随机字符串
							Date nowTime = new Date();
							long timeStamp = (nowTime.getTime() / 1000);
							String sign = "appId=" + appId + "&" + "nonceStr=" + nonceStr + "&" + "package=prepay_id="
									+ prepayId + "&signType=MD5&" + "timeStamp=" + String.valueOf(timeStamp);
							String key = config.getApiKey();
							String SignTemp = sign + "&key=" + key;
							// System.out.println(sign);
							// System.out.println(SignTemp);
							String paySign = MD5Util.MD5Encode(SignTemp, "UTF-8").toUpperCase();
							Map<String, Object> wxMap = new HashMap<String, Object>();
							wxMap.put("appId", appId);
							wxMap.put("partnerId", partnerId);
							wxMap.put("prepayId", prepayId);
							wxMap.put("nonceStr", nonceStr);
							wxMap.put("timeStamp", String.valueOf(timeStamp));
							wxMap.put("paySign", paySign);
							// 发送通知前端
							String susscesJson = JsonUtil.MapToJson(wxMap);
							output(response, susscesJson);
							log.info(order.getRechargeId() + "下单成功,订单号" + prepayId + "!");
						}
					}else{
						output(response, JsonUtil.buildFalseJson("1", "统一下单失败！"));
						log.info("下单失败!");
					}
				}else{
					log.info("订单不在可支付状态，recharge_status=，"+order.getRechargeStatus()+"统一下单失败");
					output(response,JsonUtil.buildFalseJson("1","订单不在可支付状态，统一下单失败"));
				}
			}
	 }
	 
	 /**
	  * 前台获取充值记录
	  * @param response
	  * @param userId
	  * @param loginPlat
	  * @param pageNo
	  * @param pageSize
	  */
	 @RequestMapping("/getrechargelist")
	 public void getRechargeList(HttpServletResponse response,Integer userId,Integer loginPlat,Integer pageNo,Integer pageSize){
		   if(pageNo == null){
			   pageNo = 1;
		   }
		   if(pageSize == null){
			   pageSize = 10;
		   }
		   List<RechargeOrder> lists = rechargeOrderService.wxgetlistByUserId(userId, loginPlat,(pageNo-1) * pageSize, pageSize);
	       output(response,JsonUtil.buildJson(lists));
	 }
	 
	 /**
	  * 前端获取充值优惠列表
	  * @param response
	  * @param loginPlat
	  */
	 @RequestMapping("/getrechargeamountlist")
	 public void getRechargeAmountlist(HttpServletResponse response,Integer loginPlat){
		 if(loginPlat == null){
			 output(response,JsonUtil.buildFalseJson("1","应用id未传值"));
		 }
		 List<RechargeAmount> lists = amountService.getWxlist(loginPlat);
		 output(response,JsonUtil.buildJson(lists));
	 }

}
