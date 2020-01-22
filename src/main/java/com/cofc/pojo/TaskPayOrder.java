package com.cofc.pojo;

import java.util.Date;

public class TaskPayOrder {
	private Integer orderId;
	private Integer competeId;
	private double orderFee;
	private double realFee;
	private Date orderTime;
	private Date payTime;
	private Integer payStatus;
	private Integer partnerId;
	private String outTradeNumber;
	private Integer loginPlat;
	private Integer smallRoutine;
	private Integer buyerId;

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Integer getCompeteId() {
		return competeId;
	}

	public void setCompeteId(Integer competeId) {
		this.competeId = competeId;
	}

	public double getOrderFee() {
		return orderFee;
	}

	public void setOrderFee(double orderFee) {
		this.orderFee = orderFee;
	}

	public double getRealFee() {
		return realFee;
	}

	public void setRealFee(double realFee) {
		this.realFee = realFee;
	}

	public Date getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}

	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

	public Integer getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(Integer payStatus) {
		this.payStatus = payStatus;
	}

	public Integer getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(Integer partnerId) {
		this.partnerId = partnerId;
	}

	public String getOutTradeNumber() {
		return outTradeNumber;
	}

	public void setOutTradeNumber(String outTradeNumber) {
		this.outTradeNumber = outTradeNumber;
	}

	public Integer getLoginPlat() {
		return loginPlat;
	}

	public void setLoginPlat(Integer loginPlat) {
		this.loginPlat = loginPlat;
	}

	public Integer getSmallRoutine() {
		return smallRoutine;
	}

	public void setSmallRoutine(Integer smallRoutine) {
		this.smallRoutine = smallRoutine;
	}

	public Integer getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(Integer buyerId) {
		this.buyerId = buyerId;
	}

	@Override
	public String toString() {
		return "TaskPayOrder [orderId=" + orderId + ", competeId=" + competeId + ", orderFee=" + orderFee + ", realFee="
				+ realFee + ", orderTime=" + orderTime + ", payTime=" + payTime + ", payStatus=" + payStatus
				+ ", partnerId=" + partnerId + ", outTradeNumber=" + outTradeNumber + ", loginPlat=" + loginPlat
				+ ", smallRoutine=" + smallRoutine + ", buyerId=" + buyerId + "]";
	}

}
