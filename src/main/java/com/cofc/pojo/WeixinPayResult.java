package com.cofc.pojo;

import java.util.Date;

public class WeixinPayResult {
	private Integer resultId;
	private String appId;
	private String attach;
	private String bankType;
	private Integer cashFee;
	private String cashFeeType;
	private Integer couponCount;
	private Integer couponFee;
	private Integer couponFeeOne;
	private String couponId;
	private String couponType;
	private String deviceInfo;
	private String errCode;
	private String errCodeDes;
	private String feeType;
	private String isSubscribe;
	private String mchId;
	private String nonceStr;
	private String openId;
	private String outTradeNo;
	private String resultCode;
	private Integer settlementTotalFee;
	private String sign;
	private Date timeEnd;
	private Integer totalFee;
	private String tradeType;
	private String transactionId;

	public Integer getResultId() {
		return resultId;
	}

	public void setResultId(Integer resultId) {
		this.resultId = resultId;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAttach() {
		return attach;
	}

	public void setAttach(String attach) {
		this.attach = attach;
	}

	public String getBankType() {
		return bankType;
	}

	public void setBankType(String bankType) {
		this.bankType = bankType;
	}

	public Integer getCashFee() {
		return cashFee;
	}

	public void setCashFee(Integer cashFee) {
		this.cashFee = cashFee;
	}

	public String getCashFeeType() {
		return cashFeeType;
	}

	public void setCashFeeType(String cashFeeType) {
		this.cashFeeType = cashFeeType;
	}

	public Integer getCouponCount() {
		return couponCount;
	}

	public void setCouponCount(Integer couponCount) {
		this.couponCount = couponCount;
	}

	public Integer getCouponFee() {
		return couponFee;
	}

	public void setCouponFee(Integer couponFee) {
		this.couponFee = couponFee;
	}

	public Integer getCouponFeeOne() {
		return couponFeeOne;
	}

	public void setCouponFeeOne(Integer couponFeeOne) {
		this.couponFeeOne = couponFeeOne;
	}

	public String getCouponId() {
		return couponId;
	}

	public void setCouponId(String couponId) {
		this.couponId = couponId;
	}

	public String getCouponType() {
		return couponType;
	}

	public void setCouponType(String couponType) {
		this.couponType = couponType;
	}

	public String getDeviceInfo() {
		return deviceInfo;
	}

	public void setDeviceInfo(String deviceInfo) {
		this.deviceInfo = deviceInfo;
	}

	public String getErrCode() {
		return errCode;
	}

	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}

	public String getErrCodeDes() {
		return errCodeDes;
	}

	public void setErrCodeDes(String errCodeDes) {
		this.errCodeDes = errCodeDes;
	}

	public String getFeeType() {
		return feeType;
	}

	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}

	public String getIsSubscribe() {
		return isSubscribe;
	}

	public void setIsSubscribe(String isSubscribe) {
		this.isSubscribe = isSubscribe;
	}

	public String getMchId() {
		return mchId;
	}

	public void setMchId(String mchId) {
		this.mchId = mchId;
	}

	public String getNonceStr() {
		return nonceStr;
	}

	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getOutTradeNo() {
		return outTradeNo;
	}

	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public Integer getSettlementTotalFee() {
		return settlementTotalFee;
	}

	public void setSettlementTotalFee(Integer settlementTotalFee) {
		this.settlementTotalFee = settlementTotalFee;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public Date getTimeEnd() {
		return timeEnd;
	}

	public void setTimeEnd(Date timeEnd) {
		this.timeEnd = timeEnd;
	}

	public Integer getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(Integer totalFee) {
		this.totalFee = totalFee;
	}

	public String getTradeType() {
		return tradeType;
	}

	public void setTradeType(String tradeType) {
		this.tradeType = tradeType;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	@Override
	public String toString() {
		return "WeixinPayResult [resultId=" + resultId + ", appId=" + appId + ", attach=" + attach + ", bankType="
				+ bankType + ", cashFee=" + cashFee + ", cashFeeType=" + cashFeeType + ", couponCount=" + couponCount
				+ ", couponFee=" + couponFee + ", couponFeeOne=" + couponFeeOne + ", couponId=" + couponId
				+ ", couponType=" + couponType + ", deviceInfo=" + deviceInfo + ", errCode=" + errCode + ", errCodeDes="
				+ errCodeDes + ", feeType=" + feeType + ", isSubscribe=" + isSubscribe + ", mchId=" + mchId
				+ ", nonceStr=" + nonceStr + ", openId=" + openId + ", outTradeNo=" + outTradeNo + ", resultCode="
				+ resultCode + ", settlementTotalFee=" + settlementTotalFee + ", sign=" + sign + ", timeEnd=" + timeEnd
				+ ", totalFee=" + totalFee + ", tradeType=" + tradeType + ", transactionId=" + transactionId + "]";
	}

}
