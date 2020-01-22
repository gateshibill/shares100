package com.cofc.pojo.aida;

import java.util.Date;

public class SalesMessageType {
	private int id;
	private int appId;//
	private int userId;
	private int type;// 
	private String title;
	private int orderId;
	private Date createTime;
	
	
	
	
	public String getTitle() {
		return title;
	}



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



	public int getType() {
		return type;
	}



	public void setType(int type) {
		this.type = type;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}



	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void setTitle(String title) {
		this.title = title;
	}



	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
