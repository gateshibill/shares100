package com.cofc.pojo;

import java.util.Date;

public class ActivityContent {
	private Integer contentId;
	private String contentDesc;
	private Date createTime;
	private Integer createUser;
	private Integer usedCount;
	private Date updateTime;
	
	private UserCommon user;
	
	
	public Integer getContentId() {
		return contentId;
	}
	public void setContentId(Integer contentId) {
		this.contentId = contentId;
	}
	public String getContentDesc() {
		return contentDesc;
	}
	public void setContentDesc(String contentDesc) {
		this.contentDesc = contentDesc;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Integer getCreateUser() {
		return createUser;
	}
	public void setCreateUser(Integer createUser) {
		this.createUser = createUser;
	}
	public Integer getUsedCount() {
		return usedCount;
	}
	public void setUsedCount(Integer usedCount) {
		this.usedCount = usedCount;
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
