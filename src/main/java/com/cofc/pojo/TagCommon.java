package com.cofc.pojo;

import java.util.Date;

public class TagCommon {
	private Integer tagId;
	private String tagName;
	private Integer createUser;
	private Date createTime;
	private Integer tagType;
	private Integer tagStatus;
	private UserCommon tagUser;
	
	public Integer getTagId() {
		return tagId;
	}

	public void setTagId(Integer tagId) {
		this.tagId = tagId;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public Integer getCreateUser() {
		return createUser;
	}

	public void setCreateUser(Integer createUser) {
		this.createUser = createUser;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getTagType() {
		return tagType;
	}

	public void setTagType(Integer tagType) {
		this.tagType = tagType;
	}

	public Integer getTagStatus() {
		return tagStatus;
	}

	public void setTagStatus(Integer tagStatus) {
		this.tagStatus = tagStatus;
	}

	public UserCommon getTagUser() {
		return tagUser;
	}

	public void setTagUser(UserCommon tagUser) {
		this.tagUser = tagUser;
	}

	@Override
	public String toString() {
		return "TagCommon [tagId=" + tagId + ", tagName=" + tagName + ", createUser=" + createUser + ", createTime="
				+ createTime + ", tagType=" + tagType + ", tagStatus=" + tagStatus + ", tagUser=" + tagUser + "]";
	}
}
