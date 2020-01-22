package com.cofc.pojo.activity;

import java.util.Date;


public class PinOrder {
	private int id;
	private int appId;
	private int userId;
	private int goodsId;
	private double price;
	private int role;//0为主动发起者，依次变换：1、2、3
	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	private Date createTime;
	private Date endTime;
	private int status;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getAppId() {
		return appId;
	}

	public void setAppId(int appId) {
		this.appId = appId;
	}

	public int getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(int goodsId) {
		this.goodsId = goodsId;
	}

	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
