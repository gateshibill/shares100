package com.cofc.pojo;

import java.util.Date;

public class UserOrderException {

	private int id;
	private int type;
	/*
		1    签名错误
	    2    订单不存在
	    3    订单状况异常
	    4    微信返回的现金支付金额和订单金额不相等
	    5   订单金额和支付金额不对
	*/
	private String content;
	private String orderID;
	private int userid;
	private Date createTime;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	public String getOrderID() {
		return orderID;
	}
	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
