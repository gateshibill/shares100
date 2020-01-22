package com.cofc.pojo.aida;

import java.util.Date;

public class UserRecommendGood {
	private Integer tjId;
	private Integer appId;
	private Integer userId;
	private Integer goodId;
	private Date createTime;
	
	//关联商品
	private String goodsName;
	private String sellPrice;
	private String goodsImage;
	
	public Integer getTjId() {
		return tjId;
	}
	public void setTjId(Integer tjId) {
		this.tjId = tjId;
	}
	public Integer getAppId() {
		return appId;
	}
	public void setAppId(Integer appId) {
		this.appId = appId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getGoodId() {
		return goodId;
	}
	public void setGoodId(Integer goodId) {
		this.goodId = goodId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public String getSellPrice() {
		return sellPrice;
	}
	public void setSellPrice(String sellPrice) {
		this.sellPrice = sellPrice;
	}
	public String getGoodsImage() {
		return goodsImage;
	}
	public void setGoodsImage(String goodsImage) {
		this.goodsImage = goodsImage;
	}
	
}
