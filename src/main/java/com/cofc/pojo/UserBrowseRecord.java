package com.cofc.pojo;

import java.util.Date;

public class UserBrowseRecord {
	private Integer id;
	private Integer userId;
	private Integer loginPlat;
	private Integer goodsId;
	private Integer descoveryId;
	private Integer browseCount;
	private Integer platformId;
	private Date browseTime;
	
	private UserCommon user;
	private GoodsCommon goods;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getLoginPlat() {
		return loginPlat;
	}
	public void setLoginPlat(Integer loginPlat) {
		this.loginPlat = loginPlat;
	}
	public Integer getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(Integer goodsId) {
		this.goodsId = goodsId;
	}
	public Integer getDescoveryId() {
		return descoveryId;
	}
	public void setDescoveryId(Integer descoveryId) {
		this.descoveryId = descoveryId;
	}
	public Integer getBrowseCount() {
		return browseCount;
	}
	public void setBrowseCount(Integer browseCount) {
		this.browseCount = browseCount;
	}
	public Integer getPlatformId() {
		return platformId;
	}
	public void setPlatformId(Integer platformId) {
		this.platformId = platformId;
	}
	public Date getBrowseTime() {
		return browseTime;
	}
	public void setBrowseTime(Date browseTime) {
		this.browseTime = browseTime;
	}
	public UserCommon getUser() {
		return user;
	}
	public void setUser(UserCommon user) {
		this.user = user;
	}
	public GoodsCommon getGoods() {
		return goods;
	}
	public void setGoods(GoodsCommon goods) {
		this.goods = goods;
	} 
}
