package com.cofc.controller.alipay;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.cofc.pojo.GoodsCommon;
import com.cofc.pojo.UserOrder;
import com.cofc.pojo.vij.Project;
import com.cofc.pojo.vij.ProjectOrder;
import com.cofc.service.AliPayNotifyResultService;
import com.cofc.service.GoodsCommonService;
import com.cofc.service.UserOrderService;
import com.cofc.service.vij.ProjectOrderService;
import com.cofc.service.vij.ProjectService;
import com.cofc.util.alipay.AliPayConfig;
import com.cofc.util.alipay.AliPayNotifyUtil;
/**
 * 支付宝支付成功回调
 * @author menghaoran
 *
 */
@Controller
@RequestMapping("/aliPay/aliPayNotify")
public class AliPayNotifyController {
	@Resource
	private GoodsCommonService goodsService;
	@Resource
	private UserOrderService userOrderService;
	@Resource
	private ProjectService projectService;
	@Resource
	private ProjectOrderService projectOrderService;//项目订单
	@Resource
	private AliPayNotifyResultService aliPayNotifyResultService;
	public static Logger log = Logger.getLogger("AliPayNotifyController");
	@RequestMapping("/zfbPayNotify")
	public void zfbPayNotify(HttpServletResponse response,HttpServletRequest request) throws Exception{
		log.info("zfbPayNotify():支付宝支付进入回调");
		Map<String, String> result = new HashMap<String, String>();
		Map<String, String[]> requestParams = request.getParameterMap();
		log.info("zfbPayNotify():requestParams ="+requestParams);
		for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
			}
			// 乱码解决，这段代码在出现乱码时使用。
			// valueStr = new String(valueStr.getBytes("GBK"), "utf-8");
			result.put(name, valueStr);
		}
		log.info("zfbPayNotify():result="+result);
		AliPayConfig config = new AliPayConfig();
		/*
		 * 1.rsaCheckV1验签方法

		   rsaCheckV1验签方法主要用于支付接口的返回参数的验签比如：当面付，APP支付，手机网站支付，电脑网站支付 这些接口都是使用rsaCheckV1方法验签的
		
		   2.rsaCheckV2验签方法
		
		   rsaCheckV2验签方法主要是用于生活号相关的事件消息和口碑服务市场订购信息等发送到应用网关地址的异步信息的验签
		 * */
		boolean signVerified = AlipaySignature.rsaCheckV1(result, config.getAlipayPublicKey(),
				config.getCharset(), config.getSignType());
		log.info("zfbPayNotify():signVerified = "+signVerified);
		if(signVerified){
			String trade_status = result.get("trade_status");
			if (trade_status.equals("TRADE_SUCCESS")) { // 交易支付成功
				aliPayNotifyResultService.addAliPayNotifyResult(AliPayNotifyUtil.getNotifyResult(result));//插入记录
				String orderNo = result.get("out_trade_no");//订单号
				log.info("zfbPayNotify():订单号 orderNo="+orderNo);
				//做合法性判断
				if(orderNo == null || orderNo.equals("")){
					log.info("zfbPayNotify():orderNo is null");
					response.getWriter().write("failure");
				}else{
					UserOrder order = userOrderService.getOrderByOrderNo(orderNo);
					if(order == null){
						log.info("zfbPayNotify():order is null");
						response.getWriter().write("failure");
					}else{
						//更改订单状态
						// 修改库存
						JSONArray goodsArray = null;
						goodsArray = new JSONArray(order.getObjects());
						for (int i = 0; i < goodsArray.length(); i++) {
							JSONObject json = goodsArray.getJSONObject(i);
							int goodsid = json.optInt("goodsId");
							int buyNumber = json.optInt("buyNumber");
							GoodsCommon gc = goodsService.getGoodsById(goodsid);
							int goodsStock = gc.getGoodsStock() - buyNumber;
							goodsService.updateGoodsStock(goodsid, goodsStock);
							log.info("zfbPayNotify():修改库存");
						}
						userOrderService.updateStatus(order.getOrderID(), 5, 1, 4,3);
						log.info("zfbPayNotify()：支付宝支付回调成功,订单状态改变");
						response.getWriter().write("success");
					}
					
				}			
				
			}else{ //失败
				log.info("zfbPayNotify():trade_status = " + trade_status);
				response.getWriter().write("failure");
			}
		}else{
			log.info("zfbPayNotify():result = " + result);
			response.getWriter().write("failure");
		}
		
	}
	/**
	 * 项目支付回调
	 * @param request
	 * @param response
	 * @throws AlipayApiException 
	 * @throws IOException 
	 */
	@RequestMapping("/payProjectNotify")
	public void payProjectNotify(HttpServletRequest request,HttpServletResponse response) throws AlipayApiException, IOException{
		log.info("payProjectNotify():支付宝支付进入回调");
		Map<String, String> result = new HashMap<String, String>();
		Map<String, String[]> requestParams = request.getParameterMap();
		log.info("payProjectNotify():requestParams ="+requestParams);
		for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
			}
			// 乱码解决，这段代码在出现乱码时使用。
			// valueStr = new String(valueStr.getBytes("GBK"), "utf-8");
			result.put(name, valueStr);
		}
		log.info("payProjectNotify():result="+result);
		AliPayConfig config = new AliPayConfig();
		/*
		 * 1.rsaCheckV1验签方法

		   rsaCheckV1验签方法主要用于支付接口的返回参数的验签比如：当面付，APP支付，手机网站支付，电脑网站支付 这些接口都是使用rsaCheckV1方法验签的
		
		   2.rsaCheckV2验签方法
		
		   rsaCheckV2验签方法主要是用于生活号相关的事件消息和口碑服务市场订购信息等发送到应用网关地址的异步信息的验签
		 * */
		boolean signVerified = AlipaySignature.rsaCheckV1(result, config.getAlipayPublicKey(),
				config.getCharset(), config.getSignType());
		log.info("payProjectNotify():signVerified = "+signVerified);
		if(signVerified){
			String trade_status = result.get("trade_status");
			if (trade_status.equals("TRADE_SUCCESS")) { // 交易支付成功
				aliPayNotifyResultService.addAliPayNotifyResult(AliPayNotifyUtil.getNotifyResult(result));//插入记录
				String orderNo = result.get("out_trade_no");//订单号
				log.info("payProjectNotify():订单号 orderNo="+orderNo);
				//做合法性判断
				if(orderNo == null || orderNo.equals("")){
					log.info("payProjectNotify():orderNo is null");
					response.getWriter().write("failure");
				}else{
					ProjectOrder order = projectOrderService.getProjectOrderByOrderNo(orderNo);
					if(order == null){
						log.info("payProjectNotify():order is null");
						response.getWriter().write("failure");
					}else{
						//把项目表的支付状态改成已支付
						Project project = new Project();
						project.setIsPay(1);
						project.setId(order.getProjectId());
						projectService.updateProject(project);
						//把项目订单表修改成已支付
						projectOrderService.updateIsPay(1,order.getPorderId());
						log.info("payProjectNotify()：支付宝支付回调成功,订单状态改变");
						response.getWriter().write("success");
					}
				}				
			}else{ //失败
				log.info("payProjectNotify():trade_status = " + trade_status);
				response.getWriter().write("failure");
			}
		}else{
			log.info("payProjectNotify():result = " + result);
			response.getWriter().write("failure");
		}
	}
	
	@RequestMapping("/test")
	public void test(){
		Map<String, String> map = new HashMap<String,String>();
		map.put("notify_time", "1");
		
		map.put("notify_type", "1");
		map.put("notify_id", "1");
		map.put("app_id", "1");
		map.put("charset", "1");
		map.put("version", "1");
		map.put("sign_type", "1");
		map.put("sign", "1");
		map.put("trade_no", "1");
		map.put("out_trade_no", "1");
		map.put("out_biz_no", "1");
		
		map.put("buyer_id", "1");
		map.put("buyer_logon_id", "1");
		map.put("seller_id", "1");
		map.put("seller_email", "1");
		map.put("trade_status", "1");
		map.put("total_amount", "1");
		map.put("receipt_amount", "1");
		map.put("invoice_amount", "1");
		map.put("buyer_pay_amount", "1");
		map.put("point_amount", "1");
		
		map.put("refund_fee", "1");
		map.put("subject", "1");
		map.put("body", "1");
		map.put("gmt_create", "1");
		map.put("gmt_payment", "1");
		map.put("gmt_refund", "1");
		map.put("gmt_close", "1");
		map.put("fund_bill_list", "1");
		map.put("passback_params", "1");
		map.put("voucher_detail_list", "1");
		aliPayNotifyResultService.addAliPayNotifyResult(AliPayNotifyUtil.getNotifyResult(map));
		System.out.println("插入成功");
	}
	public static void main(String[] args) {
		String aString = "JnMJLmBD0JUYSN0xWOFDnoef7i/IPqyADWN7BUtCiMdw3weDdekHPqreChnOS+0FwbNVTT8abj+vSmWPL9uPduowjcfn0mrXr7U2gn92aTwWy7FDBfar6249etimMKU5nTPzxt4VpgqOOlv98KkerBdGTMsVmAu88pvtEzTRuUXo5+gFm2jFXOrySgu2UstbYyUbsLC8SVCaq1kZeiNdZr27YY4Zv5jkJihamCVrrxOP2LD7bYeX6c+Kyzh6fTQiMk0LH+ExyVa9DhFnfr+h19l2fxzAhEKnbtgYe97bTqQ3zqQdl0Xht5kdhmxmSGNOYOm02AESN/ZgemcqeqVquQ==";
		System.out.println(aString.length());
	}
}
