package com.cofc.pojo;

import java.util.Date;


public class ApplicationManage {
	private Integer manageId;
	private Integer loginPlat;
	private Integer manageStatus;
	private Integer manageUser;
	private Date createTime;
	private Date updateTime;
	private UserCommon user;
	
	public Integer getManageId() {
		return manageId;
	}
	public void setManageId(Integer manageId) {
		this.manageId = manageId;
	}
	public Integer getLoginPlat() {
		return loginPlat;
	}
	public void setLoginPlat(Integer loginPlat) {
		this.loginPlat = loginPlat;
	}
	public Integer getManageStatus() {
		return manageStatus;
	}
	public void setManageStatus(Integer manageStatus) {
		this.manageStatus = manageStatus;
	}
	public Integer getManageUser() {
		return manageUser;
	}
	public void setManageUser(Integer manageUser) {
		this.manageUser = manageUser;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public UserCommon getUser() {
		return user;
	}
	public void setUser(UserCommon user) {
		this.user = user;
	}
}
