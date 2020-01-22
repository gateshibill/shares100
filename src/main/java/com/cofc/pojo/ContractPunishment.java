package com.cofc.pojo;

import java.util.Date;

public class ContractPunishment {
	private Integer punishmentId;
	private Integer createUser;
	private Integer usedCount;
	private String punishmentContent;
	private Date createTime;
	private Date updateTime;
	
	private UserCommon user;
	
	public Integer getPunishmentId() {
		return punishmentId;
	}
	public void setPunishmentId(Integer punishmentId) {
		this.punishmentId = punishmentId;
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
	public String getPunishmentContent() {
		return punishmentContent;
	}
	public void setPunishmentContent(String punishmentContent) {
		this.punishmentContent = punishmentContent;
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
