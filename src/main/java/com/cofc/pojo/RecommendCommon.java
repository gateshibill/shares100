package com.cofc.pojo;

import java.util.Date;

public class RecommendCommon {
	private Integer recommendId;
	private Integer loginPlat;
	private Integer isEndRecommend;
	private Date createTime;
	private Integer goodsId;
	private Integer descoveryId;
	
	public Integer getRecommendId() {
		return recommendId;
	}
	public void setRecommendId(Integer recommendId) {
		this.recommendId = recommendId;
	}
	public Integer getLoginPlat() {
		return loginPlat;
	}
	public void setLoginPlat(Integer loginPlat) {
		this.loginPlat = loginPlat;
	}
	public Integer getIsEndRecommend() {
		return isEndRecommend;
	}
	public void setIsEndRecommend(Integer isEndRecommend) {
		this.isEndRecommend = isEndRecommend;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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
}
