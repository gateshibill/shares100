package com.cofc.util.wxpay;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WXOrderQueryUtil {
	public static Map<String,String> getPrepayInfo(String transactionId,String outTradeNo) throws UnsupportedEncodingException{
		Logger log =LoggerFactory.getLogger("getPrepayInfo");
		String characterEncoding="utf-8";
		SortedMap<String,Object> parameters = new TreeMap<String,Object>();
//		SimpleDateFormat dateFormat=new SimpleDateFormat("yyyyMMddHHmmssSSS");
//		SimpleDateFormat dFormat=new SimpleDateFormat("yyyyMMddHHmmss");
		//Calendar canlender=Calendar.getInstance();
		//Date timeStart=canlender.getTime();
		WeiXinPayConfig config = new WeiXinPayConfig();
		
		//String timeStartStr=dFormat.format(timeStart);
		
		//canlender.add(Calendar.MINUTE, 10);//10分钟失效时间
		//String timeExpireStr=dateFormat.format(canlender.getTime());
		
		String noncestr=RandomCode.getUUID();
		parameters.put("appid", config.getAppid());//公众号ID
		parameters.put("mch_id", config.getMch_id());//商户号
		parameters.put("nonce_str", noncestr);//随机字符串

		parameters.put("transaction_id ", transactionId);//微信的订单号，建议优先使用 
		parameters.put("out_trade_no",outTradeNo);//内部系统订单号
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
        System.out.println(xmlResult);
        log.info("查询单号的信息为："+sb);
        String content = HttpClientUtil.sendPost("https://api.mch.weixin.qq.com/pay/orderquery", xmlResult);
   
        //把返回的XML转换成map
        Map<String,String> rmessage= new HashMap<String,String>();
		try {
			rmessage = XMLUtil.doXMLParse(content);
		} catch (Exception e) {
			e.printStackTrace();
		}
		log.info(rmessage.toString());
		return rmessage;
	}
}
