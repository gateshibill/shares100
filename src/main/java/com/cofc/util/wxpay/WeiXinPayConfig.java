package com.cofc.util.wxpay;

import java.util.Date;

public class WeiXinPayConfig {

	 //private String appid="wxdcd185c33e24e45d";//AppID(商会社区小程序ID)
     private String appid = "wx474aed7d8ee915d9";//AppID（百享园小程序APPID）
	 private String mch_id="1349602401";//支付商户id
	 private String apiKey="FKjtLr490zzntD1IFy75EZ9zypTbhjWe";//API密钥
	 private String nonce_str;//随机字符串
	 private String sign;//签名
	 private String body;//商品描述
	 private String attach;//附加数据（非必填）
	 private String out_trade_no;//商户订单号            完成的订单号+当前时间
	 private int total_fee;//金额
	 private String spbill_create_ip;//终端ip   	 用户端IP
	 private Date start_time;//交易发起时间
	 private Date time_expire;//交易结束时间
//	 private String notify_url="http://www.tripdidi.com/didi_trip/weixinPayNotify";// 正式回调地址
	 private String notify_url="https://www.ailefeigou.com/cofC2/wx/weixinPayNotify/notify.do";// 测试回调地址
	 private String trade_type="JSAPI";//交易类型
	 private String openid;
	 
	 
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public String getMch_id() {
		return mch_id;
	}
	public void setMch_id(String mch_id) {
		this.mch_id = mch_id;
	}
	public String getNonce_str() {
		return nonce_str;
	}
	public void setNonce_str(String nonce_str) {
		this.nonce_str = nonce_str;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getAttach() {
		return attach;
	}
	public void setAttach(String attach) {
		this.attach = attach;
	}
	public String getOut_trade_no() {
		return out_trade_no;
	}
	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}
	public int getTotal_fee() {
		return total_fee;
	}
	public void setTotal_fee(int total_fee) {
		this.total_fee = total_fee;
	}
	public String getSpbill_create_ip() {
		return spbill_create_ip;
	}
	public void setSpbill_create_ip(String spbill_create_ip) {
		this.spbill_create_ip = spbill_create_ip;
	}
	public Date getStart_time() {
		return start_time;
	}
	public void setStart_time(Date start_time) {
		this.start_time = start_time;
	}
	public Date getTime_expire() {
		return time_expire;
	}
	public void setTime_expire(Date time_expire) {
		this.time_expire = time_expire;
	}
	public String getNotify_url() {
		return notify_url;
	}
	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
	}
	public String getTrade_type() {
		return trade_type;
	}
	public void setTrade_type(String trade_type) {
		this.trade_type = trade_type;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getApiKey() {
		return apiKey;
	}
	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}
	
}
