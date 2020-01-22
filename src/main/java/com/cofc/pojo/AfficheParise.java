package com.cofc.pojo;

import java.util.Date;

public class AfficheParise {
	private Integer pariseId;
	private Integer userId;
	private Integer afficheId;
	private Date pariseTime;

	public Integer getPariseId() {
		return pariseId;
	}

	public void setPariseId(Integer pariseId) {
		this.pariseId = pariseId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getAfficheId() {
		return afficheId;
	}

	public void setAfficheId(Integer afficheId) {
		this.afficheId = afficheId;
	}

	public Date getPariseTime() {
		return pariseTime;
	}

	public void setPariseTime(Date pariseTime) {
		this.pariseTime = pariseTime;
	}

	@Override
	public String toString() {
		return "AfficheParise [pariseId=" + pariseId + ", userId=" + userId + ", afficheId=" + afficheId
				+ ", pariseTime=" + pariseTime + "]";
	}

}
