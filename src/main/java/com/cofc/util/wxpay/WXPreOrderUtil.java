package com.cofc.util.wxpay;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WXPreOrderUtil {
	/**
	 * 获取微信的预支付订单
	 * @param userIP     用户IP
	 * @param orderNo    订单号
	 * @param fee   总金额，单位分
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public static Map<String,String> getPrepayInfo(String orderNo,String openid,double fee,String ip,String payName,WeiXinPayConfig config) throws UnsupportedEncodingException{
		Logger log =LoggerFactory.getLogger("getPrepayInfo");
		String characterEncoding="utf-8";
		SortedMap<String,Object> parameters = new TreeMap<String,Object>();
//		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyyMMddHHmmssSSS");
//		SimpleDateFormat dFormat=new SimpleDateFormat("yyyyMMddHHmmss");
		//Calendar canlender=Calendar.getInstance();
		//Date timeStart=canlender.getTime();
		
		//String timeStartStr=dFormat.format(timeStart);
		
		//canlender.add(Calendar.MINUTE, 10);//10分钟失效时间
		//String timeExpireStr=dateFormat.format(canlender.getTime());
		
		String noncestr=RandomCode.getUUID();
		parameters.put("appid", config.getAppid());//公众号ID
		parameters.put("mch_id", config.getMch_id());//商户号
		parameters.put("nonce_str", noncestr);//随机字符串

//		String payedName = URLEncoder.encode(payName, "utf-8");
		parameters.put("body",payName);//商品描述
		parameters.put("out_trade_no",orderNo);//任务订单号
		parameters.put("fee_type", "CNY");//货币类型
		//金额只能传int
		int totalFee = (int)(fee*100);
		parameters.put("total_fee", totalFee);//金额    ,调用接口时传入
		parameters.put("spbill_create_ip", ip);//终端IP,调用接口时传入
//		parameters.put("time_start", timeStartStr);//交易开始时间
//		parameters.put("time_expire", timeExpireStr);//交易失效时间
		parameters.put("notify_url", config.getNotify_url());
		parameters.put("trade_type", config.getTrade_type());
		parameters.put("openid", openid);
		
		//获取签名
		String sign = PayCommonUtil.createSign(config, characterEncoding, parameters);
		parameters.put("sign", sign);
		log.info(parameters.toString());
		//转成xml格式
		String xmlResult = "";
        StringBuffer sb = new StringBuffer();
        sb.append("<xml>");
        for (String key : parameters.keySet()) {
            System.out.println(key + "========" + parameters.get(key));
            sb.append("<" + key + ">" + parameters.get(key) + "</" + key + ">");
        }
        sb.append("</xml>");
        xmlResult = sb.toString();
//        xmlResult = new String(sb);  
        log.info("统一下单为："+xmlResult);
        String content = HttpClientUtil.httpsRequest("https://api.mch.weixin.qq.com/pay/unifiedorder", "POST", xmlResult);
//        String content = HttpClientUtil.sendPost("https://api.mch.weixin.qq.com/pay/unifiedorder", xmlResult);
   
        //把返回的XML转换成map
        Map<String,String> rmessage= new HashMap<String,String>();
		try {
			rmessage = WXPayUtil.xmlToMap(content);
			log.info(content);
		} catch (Exception e) {
			e.printStackTrace();
		}
		log.info("返回结果:"+rmessage.get("return_msg"));
		return rmessage;
	}
	
	/**
	 * 获取微信的预支付订单
	 * @param userIP     用户IP
	 * @param orderNo    订单号
	 * @param fee   总金额，单位分
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public static Map<String,String> getPCPrepayInfo(String orderNo,double fee,String ip,String payName,WeiXinPayConfig config){
		Logger log =LoggerFactory.getLogger("getPCPrepayInfo");
		String characterEncoding="utf-8";
		SortedMap<String,Object> parameters = new TreeMap<String,Object>();
//		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyyMMddHHmmssSSS");
//		SimpleDateFormat dFormat=new SimpleDateFormat("yyyyMMddHHmmss");
		//Calendar canlender=Calendar.getInstance();
		//Date timeStart=canlender.getTime();
		
		//String timeStartStr=dFormat.format(timeStart);
		
		//canlender.add(Calendar.MINUTE, 10);//10分钟失效时间
		//String timeExpireStr=dateFormat.format(canlender.getTime());
		
		String noncestr=RandomCode.getUUID();
		parameters.put("appid", config.getAppid());//公众号ID
		parameters.put("mch_id", config.getMch_id());//商户号
		parameters.put("nonce_str", noncestr);//随机字符串

//		String payedName = URLEncoder.encode(payName, "utf-8");
		parameters.put("body",payName);//商品描述
		parameters.put("out_trade_no",orderNo);//任务订单号
		parameters.put("fee_type", "CNY");//货币类型
		//金额只能传int
		int totalFee = (int)(fee*100);
		parameters.put("total_fee", totalFee);//金额    ,调用接口时传入
		parameters.put("spbill_create_ip", ip);//终端IP,调用接口时传入
//		parameters.put("time_start", timeStartStr);//交易开始时间
//		parameters.put("time_expire", timeExpireStr);//交易失效时间
		parameters.put("notify_url", config.getNotify_url());
		parameters.put("trade_type", config.getTrade_type());
		//parameters.put("openid", openid);
		
		//获取签名
		String sign = PayCommonUtil.createSign(config, characterEncoding, parameters);
		parameters.put("sign", sign);
		log.info(parameters.toString());
		//转成xml格式
		String xmlResult = "";
        StringBuffer sb = new StringBuffer();
        sb.append("<xml>");
        for (String key : parameters.keySet()) {
            System.out.println(key + "========" + parameters.get(key));
            sb.append("<" + key + ">" + parameters.get(key) + "</" + key + ">");
        }
        sb.append("</xml>");
        xmlResult = sb.toString();
//        xmlResult = new String(sb);  
        log.info("统一下单为："+xmlResult);
        String content = HttpClientUtil.httpsRequest("https://api.mch.weixin.qq.com/pay/unifiedorder", "POST", xmlResult);
//        String content = HttpClientUtil.sendPost("https://api.mch.weixin.qq.com/pay/unifiedorder", xmlResult);
   
        //把返回的XML转换成map
        Map<String,String> rmessage= new HashMap<String,String>();
		try {
			rmessage = WXPayUtil.xmlToMap(content);
			log.info(content);
		} catch (Exception e) {
			e.printStackTrace();
		}
		log.info("返回结果:"+rmessage.get("return_msg"));
		return rmessage;
	}
}
