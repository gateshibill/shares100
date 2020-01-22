package com.cofc.controller.weiaijia;

import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cofc.pojo.ApplicationCommon;
import com.cofc.pojo.UserCommon;
import com.cofc.pojo.UserOrder;
import com.cofc.service.ApplicationCommonService;
import com.cofc.service.UserCommonService;
import com.cofc.service.UserOrderService;
import com.cofc.util.BaseUtil;
import com.cofc.util.JsonUtil;
import com.cofc.util.MD5Util;
import com.cofc.util.wxpay.WXPreOrderUtil;
import com.cofc.util.wxpay.WeiXinPayConfig;
/**
 * 支付类
 * @author 46678
 *
 */
@Controller
@RequestMapping("/vij/pay")
public class VijPayController extends BaseUtil{
	@Resource
	private UserOrderService orderService;
	@Resource
	private UserCommonService userCommonService;
	@Resource
	private ApplicationCommonService applicationCommonService;
	public static Logger log = Logger.getLogger("VijPayController");
	/**
	 * 唯爱家PC端统一下单
	 * @param request
	 * @param response
	 * @param orderId
	 */
	@RequestMapping("/unifiedorder")
	public void  unifiedorder(HttpServletRequest request,HttpServletResponse response,Integer orderId,Integer userId){
		UserCommon user = userCommonService.getUserByUserId(userId);
		if(user == null){
			log.info("unifiedorder():用户未登录,不能执行统一下单");
			output(response,JsonUtil.buildFalseJson("1","请重新登录"));
		}else{
			if(orderId == null){
				log.info("unifiedorder():订单不存在");
				output(response,JsonUtil.buildFalseJson("1","该订单不存在或已被删除"));
			}else{
				UserOrder order = orderService.findByID(orderId);
				if(order == null){
					log.info("unifiedorder():订单不存在");
					output(response,JsonUtil.buildFalseJson("1","该订单不存在或已被删除"));
				}else{
					if(!user.getUserId().equals(order.getUserid())){
						log.info("unifiedorder():订单用户与当前登录用户不一致");
						output(response,JsonUtil.buildFalseJson("1","你不是该订单的拥有者,不权限操作"));
					}else{
						if(order.getPayStatus() == 1){
							log.info("unifiedorder():订单已支付或已被移出,请重新下单");
							output(response,JsonUtil.buildFalseJson("1","订单已支付或已被移出,请重新下单"));
						}else{
							//执行下单操作
							WeiXinPayConfig config = new WeiXinPayConfig();
							if(order.getLoginPlat() == null){
								log.info("unifiedorder():未识别应用");
								//唯爱家服务号
								config.setAppid("wx847931f43f805bf0");
								config.setMch_id("1518014341");//这个是服务商的商户号：需要修改为普通商户号，普通商户号关联公众号
								config.setApiKey("ZbI5FW3x2BD5Nr9cb4PrTbnR4E9OLqWg");
							}else{
								ApplicationCommon appInfo = applicationCommonService.getApplicationById(order.getLoginPlat());
								if(appInfo != null){
									if (appInfo.getWxServiceAppId() != null && appInfo.getApiKey() != null && appInfo.getMchid() != null) {
										config.setAppid(appInfo.getWxServiceAppId());
										config.setMch_id(appInfo.getMchid());
										config.setApiKey(appInfo.getApiKey());
									}else{
										output(response,JsonUtil.buildFalseJson("1", "支付参数配置有误"));
										return;
									}

								}else{
									log.info("unifiedorder():未识别应用 appInfo is null");
									output(response,JsonUtil.buildFalseJson("1", "未识别应用,暂不支持支付"));
									return;
								}
							}
							
					        String trade_type = "NATIVE";	//PC扫码
					        config.setTrade_type(trade_type);  
							String contextPath = request.getContextPath();// 项目名
							String port = request.getServerPort() == 80 ? "" : ":" + request.getServerPort();
							String weburl = request.getScheme() + "://" + request.getServerName() + port + contextPath;
							config.setNotify_url(weburl+"/wx/weixinPayNotify/wxpaycallback.do");
							double fee = order.getRealFee();
							String ip = request.getRemoteAddr();
							Map<String, String> map = WXPreOrderUtil.getPCPrepayInfo(order.getOrderNo(), fee, ip,"唯爱家商城-购买商品", config);
							log.info("unifiedorder():支付回掉完成返回的结果--"+map);
				
							String codeUrl = map.get("code_url");
							log.info("unifiedorder():生成二维码链接为--"+codeUrl);
							output(response,JsonUtil.buildFalseJson("0",codeUrl));
						}
					}
				}
			}	
		}
	}
	
	/**
	 * 检测订单是否被支付
	 * @param request
	 * @param response
	 * @param orderId
	 */
	@RequestMapping("/checkOrderPay")
	public void  checkOrderPay(HttpServletRequest request,HttpServletResponse response,Integer orderId){
		if(orderId == null){
			log.info("订单不存在");
			output(response,JsonUtil.buildFalseJson("1","该订单不存在或已被删除"));
		}else{
			UserOrder order = orderService.findByID(orderId);
			if(order == null){
				output(response,JsonUtil.buildFalseJson("1","该订单不存在或已被删除"));
			}else{
				if(order.getPayStatus() == 1){
					output(response,JsonUtil.buildFalseJson("0","订单支付成功"));
				}else{
					output(response,JsonUtil.buildFalseJson("1","请尽快支付"));
				}
			}
		}	
	}
	
	/**
	 * 支付宝支付
	 * @param request
	 * @param response
	 */
	@RequestMapping("/alipay")
	public void alipay(HttpServletRequest request,HttpServletResponse response){
		
	}
	/**
	 * 退款：微信原路返回
	 * @param request
	 * @param response
	 * @param userId
	 * @param orderId
	 */
	@RequestMapping("/wxrefundByOrderId")
	public void wxrefundByOrderId(HttpServletRequest request,HttpServletResponse response,Integer userId,Integer orderId){
		
	}
	/**
	 * 退款：支付宝原路返回
	 * @param request
	 * @param response
	 * @param userId
	 * @param orderId
	 */
	@RequestMapping("/alirefundByOrderId")
	public void alirefundByOrderId(HttpServletRequest request,HttpServletResponse response,Integer userId,Integer orderId){
		
	}
	
	/**
	 * 唯爱家APP端统一下单
	 * @param request
	 * @param response
	 * @param orderId
	 */
	@RequestMapping("/unifiedorderApp")
	public void  unifiedorderApp(HttpServletRequest request,HttpServletResponse response,Integer orderId,Integer userId){
		UserCommon user = userCommonService.getUserByUserId(userId);
		if(user == null){
			log.info("unifiedorderApp():用户未登录,不能执行统一下单");
			output(response,JsonUtil.buildFalseJson("1","请重新登录"));
		}else{
			if(orderId == null){
				log.info("unifiedorderApp():订单不存在");
				output(response,JsonUtil.buildFalseJson("1","该订单不存在或已被删除"));
			}else{
				UserOrder order = orderService.findByID(orderId);
				if(order == null){
					log.info("unifiedorderApp():订单不存在");
					output(response,JsonUtil.buildFalseJson("1","该订单不存在或已被删除"));
				}else{
					if(!user.getUserId().equals(order.getUserid())){
						log.info("unifiedorderApp():订单用户与当前登录用户不一致");
						output(response,JsonUtil.buildFalseJson("1","你不是该订单的拥有者,不权限操作"));
					}else{
						if(order.getPayStatus() == 1){
							log.info("unifiedorderApp():订单已支付或已被移出,请重新下单");
							output(response,JsonUtil.buildFalseJson("1","订单已支付或已被移出,请重新下单"));
						}else{
							//执行下单操作
							WeiXinPayConfig config = new WeiXinPayConfig();
							//三个【pc/小程序/APP】端统一账户
							if(order.getLoginPlat() == null){
								config.setAppid("wx203885df636adbbf");
								config.setMch_id("1518014341");
								config.setApiKey("ZbI5FW3x2BD5Nr9cb4PrTbnR4E9OLqWg");
							}else{
								ApplicationCommon appInfo = applicationCommonService.getApplicationById(order.getLoginPlat());
								if(appInfo != null){
									// 如果应用有支付信息，则自己独立
									if (appInfo.getWxOpenAppId() != null && appInfo.getApiKey() != null && appInfo.getMchid() != null) {
										config.setAppid(appInfo.getWxOpenAppId());
										config.setApiKey(appInfo.getApiKey());
										config.setMch_id(appInfo.getMchid());
									}else{
										
									}
								}else{
									log.info("unifiedorder():未识别应用 appInfo is null");
									output(response,JsonUtil.buildFalseJson("1", "未识别应用,暂不支持支付"));
									return;
								}
							}
					        String trade_type = "APP";	//PC扫码
					        config.setTrade_type(trade_type);  
							String contextPath = request.getContextPath();// 项目名
							String port = request.getServerPort() == 80 ? "" : ":" + request.getServerPort();
							String weburl = request.getScheme() + "://" + request.getServerName() + port + contextPath;
							config.setNotify_url(weburl+"/wx/weixinPayNotify/wxpayappcallback.do");
							double fee = order.getRealFee();
							String ip = request.getRemoteAddr();
							Map<String, String> map = WXPreOrderUtil.getPCPrepayInfo(order.getOrderNo(), fee, ip,"唯爱家商城-购买商品", config);
							String returnCode = map.get("return_code");
							String returnMsg = map.get("return_msg");
							if(returnCode.equals("SUCCESS") && returnMsg.equals("OK")){
								long timestamp = (new Date().getTime()/1000);
								map.put("timestamp",Long.toString(timestamp));
								map.put("orderId", order.getOrderNo());
								log.info("unifiedorderApp():支付回掉完成返回的结果--"+map);
								//用预支付订单id重新加密
								String nonceStr = map.get("nonce_str");
								String prepay_id = map.get("prepay_id");
								
								String newSign = "appid=wx203885df636adbbf&noncestr="+nonceStr+"&package=Sign=WXPay&partnerid=1518014341&prepayid=" + prepay_id+ "&timestamp=" + Long.toString(timestamp)+"&key=ZbI5FW3x2BD5Nr9cb4PrTbnR4E9OLqWg";
								String paysign = MD5Util.MD5Encode(newSign,"utf-8").toUpperCase();
	                            map.put("paySign",paysign);
								output(response,JsonUtil.MapToJsonString(map));
							}else{
								output(response,JsonUtil.buildFalseJson("1", "支付失败"));
							}
						}
					}
				}
			}	
		}
	}
}
