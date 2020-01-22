package com.cofc.pojo;

import java.util.Date;

public class ContractReason {
	private Integer reasonId;
	private Integer createUser;
	private Integer usedCount;
	private String contractTitle;
	private Date createTime;
	private Date updateTime;
	
	private UserCommon user;
	
	public Integer getReasonId() {
		return reasonId;
	}
	public void setReasonId(Integer reasonId) {
		this.reasonId = reasonId;
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
	public String getContractTitle() {
		return contractTitle;
	}
	public void setContractTitle(String contractTitle) {
		this.contractTitle = contractTitle;
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
