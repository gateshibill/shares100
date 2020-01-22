package com.cofc.pojo;

public class UserBackuserRelation {
	private Integer buserId;
	private Integer userId;
	
	public Integer getBuserId() {
		return buserId;
	}
	public void setBuserId(Integer buserId) {
		this.buserId = buserId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	@Override
	public String toString() {
		return "UserBackuserRelation [buserId=" + buserId + ", userId=" + userId + "]";
	}
}
