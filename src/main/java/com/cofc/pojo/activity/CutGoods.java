package com.cofc.pojo.activity;

import com.cofc.pojo.GoodsCommon;

//转盘游戏
public class CutGoods {
	private int id;
	private int appId;
    private int goodsId;
	private int price;//有多少砍价空间
	private int num;//允许多少人参与砍价
	private int validate;//有效时间
	private int status;//状态  0：不砍 ， 1：砍   2:结束

	private GoodsCommon goodsCommon;

	public int getAppId() {
		return appId;
	}


	public void setAppId(int appId) {
		this.appId = appId;
	}


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


	public int getNum() {
		return num;
	}


	public void setNum(int num) {
		this.num = num;
	}


	public int getPrice() {
		return price;
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
