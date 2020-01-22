package com.cofc.pojo;

import java.util.Date;

public class SkillTags {
	private Integer childtagId;
	private String childtagName;
	private Integer childtagOrder;
	private Integer childtagCreateUser;
	private Date childtagCreateTime;
	private Integer isUsing;

	public Integer getChildtagId() {
		return childtagId;
	}

	public void setChildtagId(Integer childtagId) {
		this.childtagId = childtagId;
	}

	public String getChildtagName() {
		return childtagName;
	}

	public void setChildtagName(String childtagName) {
		this.childtagName = childtagName;
	}

	public Integer getChildtagOrder() {
		return childtagOrder;
	}

	public void setChildtagOrder(Integer childtagOrder) {
		this.childtagOrder = childtagOrder;
	}

	public Integer getChildtagCreateUser() {
		return childtagCreateUser;
	}

	public void setChildtagCreateUser(Integer childtagCreateUser) {
		this.childtagCreateUser = childtagCreateUser;
	}

	public Date getChildtagCreateTime() {
		return childtagCreateTime;
	}

	public void setChildtagCreateTime(Date childtagCreateTime) {
		this.childtagCreateTime = childtagCreateTime;
	}

	public Integer getIsUsing() {
		return isUsing;
	}

	public void setIsUsing(Integer isUsing) {
		this.isUsing = isUsing;
	}

	@Override
	public String toString() {
		return "SkillTags [childtagId=" + childtagId + ", childtagName=" + childtagName + ", childtagOrder="
				+ childtagOrder + ", childtagCreateUser=" + childtagCreateUser + ", childtagCreateTime="
				+ childtagCreateTime + ", isUsing=" + isUsing + "]";
	}

}
