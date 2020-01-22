package com.cofc.pojo;

import java.util.Date;

public class DescoveryParise {
	private Integer pariseId;
	private Integer parisedUserId;
	private Integer parisedDescoveryId;
	private Date pariseTime;
	private Integer isCancel;
	private Date cancelTime;

	public Integer getPariseId() {
		return pariseId;
	}

	public void setPariseId(Integer pariseId) {
		this.pariseId = pariseId;
	}

	public Integer getParisedUserId() {
		return parisedUserId;
	}

	public void setParisedUserId(Integer parisedUserId) {
		this.parisedUserId = parisedUserId;
	}

	public Integer getParisedDescoveryId() {
		return parisedDescoveryId;
	}

	public void setParisedDescoveryId(Integer parisedDescoveryId) {
		this.parisedDescoveryId = parisedDescoveryId;
	}

	public Date getPariseTime() {
		return pariseTime;
	}

	public void setPariseTime(Date pariseTime) {
		this.pariseTime = pariseTime;
	}

	public Integer getIsCancel() {
		return isCancel;
	}

	public void setIsCancel(Integer isCancel) {
		this.isCancel = isCancel;
	}

	public Date getCancelTime() {
		return cancelTime;
	}

	public void setCancelTime(Date cancelTime) {
		this.cancelTime = cancelTime;
	}

	@Override
	public String toString() {
		return "DescoveryParise [pariseId=" + pariseId + ", parisedUserId=" + parisedUserId + ", parisedDescoveryId="
				+ parisedDescoveryId + ", pariseTime=" + pariseTime + ", isCancel=" + isCancel + ", cancelTime="
				+ cancelTime + "]";
	}

}
