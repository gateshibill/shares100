package com.cofc.pojo;

import java.util.Date;

public class GoodsSharedHistory {
	private Integer sharedId;
	private Integer goodsId;
	private Integer userId;
	private Date sharedTime;
	private Integer isBack;

	public Integer getSharedId() {
		return sharedId;
	}

	public void setSharedId(Integer sharedId) {
		this.sharedId = sharedId;
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

	public Date getSharedTime() {
		return sharedTime;
	}

	public void setSharedTime(Date sharedTime) {
		this.sharedTime = sharedTime;
	}

	public Integer getIsBack() {
		return isBack;
	}

	public void setIsBack(Integer isBack) {
		this.isBack = isBack;
	}

	@Override
	public String toString() {
		return "GoodsSharedHistory [sharedId=" + sharedId + ", goodsId=" + goodsId + ", userId=" + userId
				+ ", sharedTime=" + sharedTime + ", isBack=" + isBack + "]";
	}
}
