package com.cofc.pojo.activity;

import com.cofc.pojo.GoodsCommon;

public class PinGoods {
	private int id;
	private int appId;
    private int goodsId;
	private int price;
	private int validate;
	private int status;
	private GoodsCommon goodsCommon;
	

	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public int getGoodsId() {
		return goodsId;
	}


	public void setGoodsId(int goodsId) {
		this.goodsId = goodsId;
	}


	public int getPrice() {
		return price;
	}


	public int getAppId() {
		return appId;
	}


	public void setAppId(int appId) {
		this.appId = appId;
	}


	public void setPrice(int price) {
		this.price = price;
	}


	public int getValidate() {
		return validate;
	}


	public void setValidate(int validate) {
		this.validate = validate;
	}


	public int getStatus() {
		return status;
	}


	public void setStatus(int status) {
		this.status = status;
	}


	public GoodsCommon getGoodsCommon() {
		return goodsCommon;
	}


	public void setGoodsCommon(GoodsCommon goodsCommon) {
		this.goodsCommon = goodsCommon;
	}


	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
