package com.cofc.pojo;

import java.util.Date;

public class RechargeRecord {
	private Integer payId;
	private Integer userId;
	private double totalFee;
	private double realFee;
	private Integer loginPlat;
	private String outTradeNumber;
	private Integer payStatus;
	private Date createTime;
	private Date updateTime;

	public Integer getPayId() {
		return payId;
	}

	public void setPayId(Integer payId) {
		this.payId = payId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public double getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(double totalFee) {
		this.totalFee = totalFee;
	}

	public double getRealFee() {
		return realFee;
	}

	public void setRealFee(double realFee) {
		this.realFee = realFee;
	}

	public Integer getLoginPlat() {
		return loginPlat;
	}

	public void setLoginPlat(Integer loginPlat) {
		this.loginPlat = loginPlat;
	}

	public String getOutTradeNumber() {
		return outTradeNumber;
	}

	public void setOutTradeNumber(String outTradeNumber) {
		this.outTradeNumber = outTradeNumber;
	}

	public Integer getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(Integer payStatus) {
		this.payStatus = payStatus;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Override
	public String toString() {
		return "RechargeRecord [payId=" + payId + ", userId=" + userId + ", totalFee=" + totalFee + ", realFee="
				+ realFee + ", loginPlat=" + loginPlat + ", outTradeNumber=" + outTradeNumber + ", payStatus="
				+ payStatus + ", createTime=" + createTime + ", updateTime=" + updateTime + "]";
	}

}
