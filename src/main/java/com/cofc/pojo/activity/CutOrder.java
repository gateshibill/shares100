package com.cofc.pojo.activity;

import java.util.Date;


//转盘游戏
public class CutOrder {
	private int id;
	private int appId;
	private int goodsId;
    private int orderId;
	private int userId;
	private double originalPrice;
	private double cutPrice;//砍的价
	private int validate;//
	private int times;//可以砍的总次数；
	private int status;//0，可以砍，1为结束；
	private int count;//当前人数
	private Date createTime;

	

	public int getId() {
		return id;
	}



	public int getGoodsId() {
		return goodsId;
	}



	public int getStatus() {
		return status;
	}



	public void setStatus(int status) {
		this.status = status;
	}



	public int getValidate() {
		return validate;
	}



	public void setValidate(int validate) {
		this.validate = validate;
	}



	public void setGoodsId(int goodsId) {
		this.goodsId = goodsId;
	}



	public void setId(int id) {
		this.id = id;
	}



	public int getOrderId() {
		return orderId;
	}



	public int getAppId() {
		return appId;
	}



	public void setAppId(int appId) {
		this.appId = appId;
	}



	public int getCount() {
		return count;
	}



	public void setCount(int count) {
		this.count = count;
	}




	public double getCutPrice() {
		return cutPrice;
	}



	public void setCutPrice(double cutPrice) {
		this.cutPrice = cutPrice;
	}



	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public int getUserId() {
		return userId;
	}

	public int getTimes() {
		return times;
	}

	public void setTimes(int times) {
		this.times = times;
	}

	public double getOriginalPrice() {
		return originalPrice;
	}



	public void setOriginalPrice(double originalPrice) {
		this.originalPrice = originalPrice;
	}



	public void setUserId(int userId) {
		this.userId = userId;
	}





	public void setCutPrice(int cutPrice) {
		this.cutPrice = cutPrice;
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
