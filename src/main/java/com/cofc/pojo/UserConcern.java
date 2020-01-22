package com.cofc.pojo;

import java.util.Date;

public class UserConcern {
	private Integer concernId;
	private Integer userId;
	private Integer concernedUserId;
	private Date createTime;
	private Integer isRemove;
	private UserCommon concernedUser;

	public Integer getConcernId() {
		return concernId;
	}

	public void setConcernId(Integer concernId) {
		this.concernId = concernId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getConcernedUserId() {
		return concernedUserId;
	}

	public void setConcernedUserId(Integer concernedUserId) {
		this.concernedUserId = concernedUserId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getIsRemove() {
		return isRemove;
	}

	public void setIsRemove(Integer isRemove) {
		this.isRemove = isRemove;
	}

	public UserCommon getConcernedUser() {
		return concernedUser;
	}

	public void setConcernedUser(UserCommon concernedUser) {
		this.concernedUser = concernedUser;
	}

	@Override
	public String toString() {
		return "UserConcern [concernId=" + concernId + ", userId=" + userId + ", concernedUserId=" + concernedUserId
				+ ", createTime=" + createTime + ", isRemove=" + isRemove + ", concernedUser=" + concernedUser + "]";
	}
}
