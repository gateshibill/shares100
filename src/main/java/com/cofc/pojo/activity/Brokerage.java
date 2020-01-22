package com.cofc.pojo.activity;

import java.util.Date;

//佣金
public class Brokerage {
	private int id;
	private int appId;
	private int userId;
	private int type;// 0为销售佣金，1为分红；
	private String source;// 佣金来源
	private double price;// 佣金价值
	private Date createTime;
	// 0为不可用，说明佣金还没有确定;1可用，已经到余额
	private int status;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAppId() {
		return appId;
	}

	public void setAppId(int appId) {
		this.appId = appId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public double getPrice() {
		return price;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public void setPrice(double price) {
		this.price = price;
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
