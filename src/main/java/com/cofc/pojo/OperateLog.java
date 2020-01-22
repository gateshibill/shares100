package com.cofc.pojo;

import java.util.Date;

public class OperateLog {
	private Integer logId;
	private String operateName;
	private String operateUser;
	private Date operateTime;
	private Date updateTime;
	private Integer operateResult;
	
	public Integer getLogId() {
		return logId;
	}
	public void setLogId(Integer logId) {
		this.logId = logId;
	}
	public String getOperateName() {
		return operateName;
	}
	public void setOperateName(String operateName) {
		this.operateName = operateName;
	}
	public String getOperateUser() {
		return operateUser;
	}
	public void setOperateUser(String operateUser) {
		this.operateUser = operateUser;
	}
	public Date getOperateTime() {
		return operateTime;
	}
	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Integer getOperateResult() {
		return operateResult;
	}
	public void setOperateResult(Integer operateResult) {
		this.operateResult = operateResult;
	}
}
