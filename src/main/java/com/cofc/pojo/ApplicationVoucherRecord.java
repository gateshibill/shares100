package com.cofc.pojo;

import java.util.Date;

public class ApplicationVoucherRecord {
	
	private Integer recordId;
	private Integer userId;
	private Integer loginPlat;
	private Date createTime;
	private String recordDesc;
	
	private UserCommon user;
	private GroupUsers group;
	
	public Integer getRecordId() {
		return recordId;
	}
	public void setRecordId(Integer recordId) {
		this.recordId = recordId;
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
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getRecordDesc() {
		return recordDesc;
	}
	public void setRecordDesc(String recordDesc) {
		this.recordDesc = recordDesc;
	}
	public UserCommon getUser() {
		return user;
	}
	public void setUser(UserCommon user) {
		this.user = user;
	}
	public GroupUsers getGroup() {
		return group;
	}
	public void setGroup(GroupUsers group) {
		this.group = group;
	}
	
}
