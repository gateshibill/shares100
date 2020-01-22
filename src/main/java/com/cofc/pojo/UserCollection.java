package com.cofc.pojo;

import java.util.Date;

public class UserCollection {
	private Integer collectionId;
	private Integer goodsId;
	private Integer userId;
	private Date createTime;
	private Integer descoveryId;
	private Integer isCancel;
	private Integer loginPlat;
	private DescoveryCommon descoveryCommon;
	private GoodsCommon goodsCommon;

	public Integer getCollectionId() {
		return collectionId;
	}

	public void setCollectionId(Integer collectionId) {
		this.collectionId = collectionId;
	}

	public Integer getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(Integer goodsId) {
		this.goodsId = goodsId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getDescoveryId() {
		return descoveryId;
	}

	public void setDescoveryId(Integer descoveryId) {
		this.descoveryId = descoveryId;
	}

	public Integer getIsCancel() {
		return isCancel;
	}

	public void setIsCancel(Integer isCancel) {
		this.isCancel = isCancel;
	}

	public Integer getLoginPlat() {
		return loginPlat;
	}

	public void setLoginPlat(Integer loginPlat) {
		this.loginPlat = loginPlat;
	}

	public DescoveryCommon getDescoveryCommon() {
		return descoveryCommon;
	}

	public void setDescoveryCommon(DescoveryCommon descoveryCommon) {
		this.descoveryCommon = descoveryCommon;
	}

	public GoodsCommon getGoodsCommon() {
		return goodsCommon;
	}

	public void setGoodsCommon(GoodsCommon goodsCommon) {
		this.goodsCommon = goodsCommon;
	}

	@Override
	public String toString() {
		return "UserCollection [collectionId=" + collectionId + ", goodsId=" + goodsId + ", userId=" + userId
				+ ", createTime=" + createTime + ", descoveryId=" + descoveryId + ", isCancel=" + isCancel
				+ ", loginPlat=" + loginPlat + ", descoveryCommon=" + descoveryCommon + ", goodsCommon=" + goodsCommon
				+ "]";
	}
}
