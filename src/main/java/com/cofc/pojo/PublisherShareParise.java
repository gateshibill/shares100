package com.cofc.pojo;

import java.util.Date;

public class PublisherShareParise {
	private Integer pariseId;
	private Integer userId;
	private Integer shareId;
	private Integer pariseStatus;
	private Date pariseTime;
	private Date updateTime;
	
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
	public Integer getShareId() {
		return shareId;
	}
	public void setShareId(Integer shareId) {
		this.shareId = shareId;
	}
	public Integer getPariseStatus() {
		return pariseStatus;
	}
	public void setPariseStatus(Integer pariseStatus) {
		this.pariseStatus = pariseStatus;
	}
	public Date getPariseTime() {
		return pariseTime;
	}
	public void setPariseTime(Date pariseTime) {
		this.pariseTime = pariseTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
}
