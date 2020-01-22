package com.cofc.util;

import com.alibaba.fastjson.JSON;
import com.cofc.util.chuanglan.sms.request.SmsSendRequest;
import com.cofc.util.chuanglan.sms.response.SmsSendResponse;
import com.cofc.util.chuanglan.sms.util.ChuangLanSmsUtil;

/**
 * 创蓝发送短信类
 * 
 * @author menghaoran
 *
 */
public class SendMsgUtil {
	public final static String charset = "utf-8";// 字符集
	public static String account = "N7055155";// 账号
	public static String password = "oPYzcdxiKWf3a9";// 密码
	public static String smsurl = "http://smssh1.253.com/msg/send/json";// 短信发送url
	public static String report = "true";// 状态报告

	/**
	 * 发送验证码:采用普通短信发送
	 * 
	 * @param phone
	 * @return
	 */
	public static String sendCode(String phone, String code) {
		String msg = "【唯爱家】您好,欢迎使用唯爱家平台,您的验证码是:" + code + ",5分钟内有效。如非您本人操作,请忽略该短信";
		SmsSendRequest smsSingleRequest = new SmsSendRequest(account, password, msg, phone, report);
		String requestJson = JSON.toJSONString(smsSingleRequest);
		System.out.println("before request string is: " + requestJson);
		String response = ChuangLanSmsUtil.sendSmsByPost(smsurl, requestJson);
		System.out.println("response after request result is :" + response);
		SmsSendResponse smsSingleResponse = JSON.parseObject(response, SmsSendResponse.class);
		System.out.println("response  toString is :" + smsSingleResponse);
		return smsSingleResponse.getCode();
	}
	/**
	 * 发送项目处理后步骤的接口
	 * @param phone
	 * @param content
	 * @return
	 */
	public static String sendMsg(String phone,String content){
		String msg = "【唯爱家】"+content;
		SmsSendRequest smsSingleRequest = new SmsSendRequest(account, password, msg, phone, report);
		String requestJson = JSON.toJSONString(smsSingleRequest);
		System.out.println("before request string is: " + requestJson);
		String response = ChuangLanSmsUtil.sendSmsByPost(smsurl, requestJson);
		System.out.println("response after request result is :" + response);
		SmsSendResponse smsSingleResponse = JSON.parseObject(response, SmsSendResponse.class);
		System.out.println("response  toString is :" + smsSingleResponse);
		return smsSingleResponse.getCode();
	}
}
