package com.cofc.util.alipay;
/**
 * 
 * @author menghaoran
 *
 */
public class AliPayConfig {
	private String appId="2019022063270276";//正式
	//private String appId = "2016092700610491"; //沙箱环境
	private String method="alipay.trade.app.pay";
	private String format="JSON";
	private String returnUrl="https://openapi.alipay.com/gateway.do"; //同步通知：该步骤也是做验证用，具体要流程图
	//private String returnUrl = "https://openapi.alipaydev.com/gateway.do";//沙箱网关
	private String charset="utf-8";
	private String signType="RSA2";
	private String sign;
	private String timestamp;
	private String version="1.0";
	private String notifyUrl="http://vij365.com/aliPay/aliPayNotify/zfbPayNotify.do"; //异步通知路径
	private String bizContent;
	private String alipayPublicKey="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAhdf1c7jvBU9IWC8OZomI4kKWZr0t26g+9zYLWHrEPPk7TfdLOgUhHAZMQyrdXoGdEoaJc1WllSuncbfFvbtzqvnCXG3m2HXyqlsoOf0eC//yA3T1KOMzGvvtrvMcNZXOfWnEuFD78sij0cNWRZiDPDWltuziBQOXOROjdH5z+Ou09Q2KbRXy6frajNsvtG4pJ6g88OjRWjrp+/dngpk3ZFrJLJamN7CLV1j9gMwu6bdZVMSF/wok3rdWoqfpLPdfeLh8F4u33qBvVu5gVEgGRLjPG3q9mRSy2Pt9POqSzmn5gP/nsJFV0/X2ZogwHry7jRHXWBvJEHlYlDqiRgRYBwIDAQAB"; //正式：公钥
	//private String alipayPublicKey="MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAqW9mlXqJ5yC34sXVgcdh4k4EiW4s6pk1b03qdA3dyORnghQQ1VRLiukqnGXkceExzqxOaCJPp1/6JZAudZAuvzN4RnE/O0LUh4TMpjwfUuwCTeEVMG/W4Nn/Ka+wzQM430bi3s2tQCcv/IjllGIxzl+GwzhiMD9gr1Cl/oETqpYkiGFMC7V3gXUFH4wrKJJAx6XmBbERQa4FyyILGgFDZjcnaHgwz8wnQo8xzlfrweTPwwpZOdQvW/8vQyv/D0RQixaUM34pepwmfHiHA4VjLXv9zf8BZfd+lvxKhsfaP+vUVVp4VgTaO+k96ORI2QJnWey1GISWt6GJgHH3nmmhmwIDAQAB";//沙箱
	private String privateKey="MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCNV84w2K5FC2wPXdlwr4iqZvzmMn2FBNjw8Je8Ns6EH+G8gtilM0LbVGaEpiIbpZa9KZ8Yey/xKNdqLOJ3m4Df/+J8aa8jcZ0CbONplQlQc22Wa9lCuYoxWJrVzbXNUudP0bmAUqWoPhyp48nNvGpd9Cg2oHUgZ4RnwsMLfhzHk84XiRGyaMkajkqbdQ8Smak0BC9LlyJNXceAlDGAgNegfrLJkbdhvDWXrmSYdF9kjewNOoM3MmcGXbK8yYP9kkMQbB+vwEUAIoPWeN/D59V7kW1u4/DZY8f5Z2XPgZ/FhxlNAJfLF5bhHHlPlcv5ews4cx7JAr8TAnr2gjlbGcM1AgMBAAECggEAVjjpLH0SGkZ0fg9HNOmv2huHTTohvgKOALUUKxnX81urjh7X4DccIsYi3+qkxe7GvNBmID4NqfS7LCcDY+l1va/QTwr9bwbAvBkGxYLIMs6nXGqgF4ct2RpvIdwieoWHW/bZTYGdUrvJKo4trhaxYG0wFtrdeI4NhGqYJyugjNIe07cee1lWS2gnZ4E92P8y1wa8Sn7lihwgALpFjvrIw1L5qLMta8rsHzM4PiJvr6jbzBzB5soaMtkzhoRfMXEm32vi3dWirWPPzf5QiyFpfZXPaNw4boU3pwcjuTrswGexDsoX5sbVP8rPLRdKya+vGY6Gd+j+vM3wUGr6VChC4QKBgQDjRlkvOGZ8qEzRCvCipdyxIbi7opiUChoJU6aD14mKBwiSKHR0mioUjXHBIoaAwJkYLWjdyE5Awr/WDYnDaKFKVbN8JuBg//qHfQ5dmO8FjVxc+gdLxcPAXSjfmBzatSDd3VxKLNHXj1ZxrPTzIRXhU+vv2oqhgwWl3p5FnJVFWQKBgQCfNREo+yPsycLgDXFW2a2iT08F2O9kb5qIh3HQ+ROH4dV64wZwQaYR1m510laQ2GwJPXbsXblJErfP/rI6HmmxMZnoQDzvQe9O70eRe1qGB4FKOSwQWPGZ3jGcPDRuUMuMsrWrzLsEsiMqu5RHLOQSv9h5TcR4rv6pujul1WmFPQKBgCjkr4dfjpE/dxLl9QZO8batap/YUbvRVX34Hy35yjWbrl8eCFYypv+rs5wcqjbgcKt5ADVnpSzh3cKWDQOr/9lwMitUk2rgpCNczQjqS5ekJ8pS8p0fqGkkWgZhSE/R52gEPGbcSaWPlO4/QJO+kEFK6Fjv6aHGAW4m8DN2SSWBAoGBAJNAGtIDjlOovNRLuqNwi9cOcXFgFvKXAp/6XjvA096e8rtJFSBop7fh06Kn26b2dN4K+l5dxUyfwmDfGmsEzA62aLpWB8Xm/vbL+y7En5JNiLhkrqukCDmfN9VgHcJWeh11APCYV/Vc6YfRnLBEBeKNjCON2QUiHapP9HFKNOGlAoGBALxmTJdOyn02FShJs5bLtg2VTvEHwZ6NfSbFZlZeOj5+CbYSWSdqMHHWTzJ78FQgkjCuy3bCvD98hKnN4YuyZEIbHgIhFTqZoVFDdmzmbbV8ghr46VbUpVD0QPoUVSmA+mJsqeOVY18MLhubzUWEvTybE2QZfL5Rlx+t3spvIfOi"; //私钥
	private String sellerId;//收款支付宝用户ID
	private String productCode="QUICK_MSECURITY_PAY";//销售产品码.为固定值QUICK_MSECURITY_PAY
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}
	public String getReturnUrl() {
		return returnUrl;
	}
	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}
	public String getCharset() {
		return charset;
	}
	public void setCharset(String charset) {
		this.charset = charset;
	}
	public String getSignType() {
		return signType;
	}
	public void setSignType(String signType) {
		this.signType = signType;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getNotifyUrl() {
		return notifyUrl;
	}
	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}
	public String getBizContent() {
		return bizContent;
	}
	public void setBizContent(String bizContent) {
		this.bizContent = bizContent;
	}
	public String getAlipayPublicKey() {
		return alipayPublicKey;
	}
	public void setAlipayPublicKey(String alipayPublicKey) {
		this.alipayPublicKey = alipayPublicKey;
	}
	public String getPrivateKey() {
		return privateKey;
	}
	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}
	public String getSellerId() {
		return sellerId;
	}
	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
}
