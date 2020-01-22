package com.cofc.pojo;

import java.util.Date;

public class UserFootMark {
	private Integer footmarkId;
	private Integer userId;
	private Integer markedUserId;
	private Integer markedGoodsId;
	private Date markedTime;
	private UserCommon markedUser;
	private UserShareGoods markedGoods;

	public Integer getFootmarkId() {
		return footmarkId;
	}

	public void setFootmarkId(Integer footmarkId) {
		this.footmarkId = footmarkId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getMarkedUserId() {
		return markedUserId;
	}

	public void setMarkedUserId(Integer markedUserId) {
		this.markedUserId = markedUserId;
	}

	public Integer getMarkedGoodsId() {
		return markedGoodsId;
	}

	public void setMarkedGoodsId(Integer markedGoodsId) {
		this.markedGoodsId = markedGoodsId;
	}

	public Date getMarkedTime() {
		return markedTime;
	}

	public void setMarkedTime(Date markedTime) {
		this.markedTime = markedTime;
	}

	public UserCommon getMarkedUser() {
		return markedUser;
	}

	public void setMarkedUser(UserCommon markedUser) {
		this.markedUser = markedUser;
	}

	public UserShareGoods getMarkedGoods() {
		return markedGoods;
	}

	public void setMarkedGoods(UserShareGoods markedGoods) {
		this.markedGoods = markedGoods;
	}

	@Override
	public String toString() {
		return "UserFootMark [footmarkId=" + footmarkId + ", userId=" + userId + ", markedUserId=" + markedUserId
				+ ", markedGoodsId=" + markedGoodsId + ", markedTime=" + markedTime + ", markedUser=" + markedUser
				+ ", markedGoods=" + markedGoods + "]";
	}

}
