package com.cofc.pojo.activity;

import java.util.Date;

public class CutRecord {
	private int id;
	private int appId;
    private int orderId;
    private int userId;
	private double cutPrice;//看了多少价
	private int cout;
	private Date createTime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public double getCutPrice() {
		return cutPrice;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public void setCutPrice(double cutPrice) {
		this.cutPrice = cutPrice;
	}

	public int getAppId() {
		return appId;
	}

	public void setAppId(int appId) {
		this.appId = appId;
	}

	public int getCout() {
		return cout;
	}

	public void setCout(int cout) {
		this.cout = cout;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
