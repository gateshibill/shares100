package com.cofc.controller.alipay;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.domain.AlipayTradePagePayModel;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.alipay.api.response.AlipayTradePagePayResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.alipay.api.response.AlipayTradeWapPayResponse;
import com.cofc.pojo.UserOrder;
import com.cofc.service.UserOrderService;
import com.cofc.util.BaseUtil;
import com.cofc.util.JsonUtil;
import com.cofc.util.alipay.AliPayConfig;

/**
 * 支付宝支付
 * @author menghaoran
 *
 */
@Controller
@RequestMapping("/wx/alipay")
public class AliPayController extends BaseUtil{
	@Resource
	private UserOrderService userOrderService;//订单
	public static Logger log = Logger.getLogger("AliPayController");
	/**
	 * app支付
	 * 统一收单交易支付接口 : alipay.trade.pay
	 * @param response
	 * @param request
	 * @param orderId:订单id
	 * @param payName：支付类型名称：如商品
	 * @param ticketMoney：优惠券优惠金额
	 * @throws AlipayApiException 
	 */
	@RequestMapping("/alipay")
	public void alipay(HttpServletResponse response,Integer orderId,
			String payName,int ticketMoney) throws AlipayApiException{
		if(orderId == null){
			log.info("alipay():orderIs is null");
			output(response,JsonUtil.buildFalseJson("1", "非法订单"));
		}else{
			UserOrder order = userOrderService.findByID(orderId);
			if(order == null){
				log.info("alipay():order is null");
				output(response,JsonUtil.buildFalseJson("1", "非法订单"));
			}else{
				if(payName.equals("") || payName == null){
					payName = "商品";
				}
				Double fee = 0.00;
				fee = order.getRealFee();
				if(ticketMoney > 0){ //减去优惠券
					fee = fee - ticketMoney;
				}
				AliPayConfig alipay = new AliPayConfig();
				AlipayClient alipayClient = new DefaultAlipayClient(alipay.getReturnUrl(), 
						alipay.getAppId(),alipay.getPrivateKey(), alipay.getFormat(), 
						alipay.getCharset(), alipay.getAlipayPublicKey(), alipay.getSignType());
				AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
				
				AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
				model.setBody(payName);//对一笔交易的具体描述信息。如果是多种商品，请将商品描述字符串累加传给body。
				model.setTotalAmount(fee.toString());
				model.setOutTradeNo(order.getOrderNo());
				model.setTimeoutExpress("24h");//该笔订单允许的最晚付款时间，逾期将关闭交易。取值范围：1m～15d。m-分钟，h-小时，d-天，1c-当天（1c-当天的情况下，无论交易何时创建，都在0点关闭）。 该参数数值不接受小数点， 如 1.5h，可转换为 90m。
				model.setSubject(payName);
				model.setGoodsType("1");
				request.setBizModel(model);
				request.setNotifyUrl(alipay.getNotifyUrl());
				AlipayTradeAppPayResponse response2 = alipayClient.sdkExecute(request);
				System.out.println("response2--"+response2.getBody());
				//pageExecute 是PC和手机网站 有页面输出;sdkExecute app
//				String timeoutExPress = "24h";
//				String totalAmount = fee.toString();
//				String outTradeNo = order.getOrderNo();
//				request.setBizContent("{" +
//						"\"timeout_express\":\""+timeoutExPress+"\"," +
//						"\"total_amount\":\""+totalAmount+"\"," +
//						"\"body\":\""+payName+"\"," +
//						"\"subject\":\""+payName+"\"," +
//						"\"out_trade_no\":\""+outTradeNo+"\"," +
//						"\"goods_type\":\"1\"" +
//						"}");
//				AlipayTradeAppPayResponse response2 = alipayClient.pageExecute(request);
				if(response2.isSuccess()){
					log.info("alipay():body:"+response2.getBody());
					output(response,JsonUtil.buildFalseJson("0",response2.getBody()));
				}else{
					log.info("alipay():response2:"+response2);
					output(response,JsonUtil.buildFalseJson("1", "支付失败"));
				}

			}
		}
	}
	
	/**
	 * 手机网站支付
	 * @param response
	 * @param request
	 * @throws AlipayApiException 
	 * @throws IOException 
	 */
	@RequestMapping("/wapAliPay")
	public void wapAliPay(HttpServletResponse response,Integer orderId,
			String payName,int ticketMoney,String returnUrl) throws AlipayApiException,IOException{
		if(orderId == null){
			log.info("alipay():orderIs is null");
			output(response,JsonUtil.buildFalseJson("1", "非法订单"));
		}else{
			UserOrder order = userOrderService.findByID(orderId);
			if(order == null){
				log.info("alipay():order is null");
				output(response,JsonUtil.buildFalseJson("1", "非法订单"));
			}else{
				if(payName.equals("") || payName == null){
					payName = "商品";
				}
				Double fee = 0.00;
				fee = order.getRealFee();
				if(ticketMoney > 0){ //减去优惠券
					fee = fee - ticketMoney;
				}
				AliPayConfig alipay = new AliPayConfig();
				alipay.setMethod("alipay.trade.wap.pay");
				if(returnUrl != null && !returnUrl.equals("")){
					alipay.setReturnUrl(returnUrl);
				}
				AlipayClient alipayClient = new DefaultAlipayClient(alipay.getReturnUrl(), 
						alipay.getAppId(),alipay.getPrivateKey(), alipay.getFormat(), 
						alipay.getCharset(), alipay.getAlipayPublicKey(), alipay.getSignType());
				AlipayTradeWapPayRequest request = new AlipayTradeWapPayRequest();
				
				AlipayTradeWapPayModel model = new AlipayTradeWapPayModel();
				model.setBody(payName);//对一笔交易的具体描述信息。如果是多种商品，请将商品描述字符串累加传给body。
				model.setTotalAmount(fee.toString());
				model.setOutTradeNo(order.getOrderNo());
				model.setTimeoutExpress("24h");//该笔订单允许的最晚付款时间，逾期将关闭交易。取值范围：1m～15d。m-分钟，h-小时，d-天，1c-当天（1c-当天的情况下，无论交易何时创建，都在0点关闭）。 该参数数值不接受小数点， 如 1.5h，可转换为 90m。
				model.setSubject(payName);
				model.setGoodsType("1");
				request.setBizModel(model);
				request.setNotifyUrl(alipay.getNotifyUrl());
				AlipayTradeWapPayResponse wapPayResponse = alipayClient.pageExecute(request);
				if(wapPayResponse.isSuccess()){
					log.info("wapAliPay():wapPayResponse.getBody()="+wapPayResponse.getBody());
					Map<String,String> map = new HashMap<>();
					map.put("body", wapPayResponse.getBody());
					output(response,JsonUtil.MapToJsonString(map));//返回表单
				}else{
					log.info("wapAliPay():支付失败：wapPayResponse="+wapPayResponse);
					output(response,JsonUtil.buildFalseJson("1", "支付失败"));
				}
				
				
			}
		}
	}
	/**
	 * 支付宝pc端支付
	 * @param orderId
	 * @param payName
	 * @param ticketMoney
	 * @return
	 * @throws AlipayApiException 
	 */
	@RequestMapping("/pcAliPay")
	public void pcAliPay(HttpServletResponse response,Integer orderId,String payName,int ticketMoney) throws AlipayApiException{
		if(orderId == null){
			log.info("alipay():orderIs is null");
			output(response,JsonUtil.buildFalseJson("1", "非法订单"));
		}else{
			UserOrder order = userOrderService.findByID(orderId);
			if(order == null){
				log.info("alipay():order is null");
				output(response,JsonUtil.buildFalseJson("1", "非法订单"));
			}else{
				if(payName.equals("") || payName == null){
					payName = "商品";
				}
				Double fee = 0.00;
				fee = order.getRealFee();
				if(ticketMoney > 0){ //减去优惠券
					fee = fee - ticketMoney;
				}
				AliPayConfig alipay = new AliPayConfig();
				alipay.setMethod("alipay.trade.page.pay");
				AlipayClient alipayClient = new DefaultAlipayClient(alipay.getReturnUrl(), 
						alipay.getAppId(),alipay.getPrivateKey(), alipay.getFormat(), 
						alipay.getCharset(), alipay.getAlipayPublicKey(), alipay.getSignType());
				AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
				AlipayTradePagePayModel model = new AlipayTradePagePayModel();
				model.setBody(payName);//对一笔交易的具体描述信息。如果是多种商品，请将商品描述字符串累加传给body。
				model.setTotalAmount(fee.toString());
				model.setOutTradeNo(order.getOrderNo());
				model.setTimeoutExpress("24h");//该笔订单允许的最晚付款时间，逾期将关闭交易。取值范围：1m～15d。m-分钟，h-小时，d-天，1c-当天（1c-当天的情况下，无论交易何时创建，都在0点关闭）。 该参数数值不接受小数点， 如 1.5h，可转换为 90m。
				model.setSubject(payName);
				model.setGoodsType("1");
				request.setBizModel(model);
				request.setNotifyUrl(alipay.getNotifyUrl());
				AlipayTradePagePayResponse pagePayResponse = alipayClient.pageExecute(request);
				if(pagePayResponse.isSuccess()){
					output(response,JsonUtil.buildFalseJson("0", "支付成功"));
				}else{
					output(response,JsonUtil.buildFalseJson("1", "支付失败"));
				}
			}
		}
	}
	/**
	 * 支付宝退款
	 * @param map
	 * @return
	 * @throws AlipayApiException 
	 */
	public static String alipayRefund(Map<String,String> map) throws AlipayApiException{
		AliPayConfig config = new AliPayConfig();
		config.setMethod("alipay.trade.refund"); //退款
		AlipayClient alipayClient = new DefaultAlipayClient(config.getReturnUrl(),config.getAppId(),
				config.getPrivateKey(),config.getFormat(),config.getCharset(),config.getAlipayPublicKey(),config.getSignType());
		AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
		request.setBizContent("{" +
				"\"out_trade_no\":\""+map.get("order_no")+"\"," +
				"\"refund_amount\":"+map.get("real_fee")+"," +
				"\"refund_reason\":\"订单正常退款\"" +
				"  }");
		AlipayTradeRefundResponse response = alipayClient.execute(request);
		if(response.isSuccess()){
			return "success";
		}else{
			return "fail";
		}		
	}
	public static void main(String[] args) {
		String timeoutExPress = "24h";
		String totalAmount = "100";
		String outTradeNo = "123456";
		String payName = "商品";
		String a = "{" +
				"\"timeout_express\":\""+timeoutExPress+"\"," +
				"\"total_amount\":\""+totalAmount+"\"," +
				"\"body\":\""+payName+"\"," +
				"\"subject\":\""+payName+"\"," +
				"\"out_trade_no\":\""+outTradeNo+"\"," +
				"\"goods_type\":\"1\"" +
				"}";
		System.out.println(a);
	}
}
