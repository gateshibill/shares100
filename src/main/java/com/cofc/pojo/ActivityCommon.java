package com.cofc.pojo;

import java.util.Date;

public class ActivityCommon {
	private Integer activityId;
	private String activityName;
	private String activityImage;
	private String activityDesc;
	private Integer type;
	private Integer count;
	private Integer status;
	private Date createTime;
	private Date updateTime;
	private Integer createUser;
	private Integer loginPlat;
	private Integer parentId;
	private Integer raffleRule;

	private UserCommon activityUser;

	public Integer getActivityId() {
		return activityId;
	}

	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public String getActivityDesc() {
		return activityDesc;
	}

	public void setActivityDesc(String activityDesc) {
		this.activityDesc = activityDesc;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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

	public Integer getCreateUser() {
		return createUser;
	}

	public void setCreateUser(Integer createUser) {
		this.createUser = createUser;
	}

	public Integer getLoginPlat() {
		return loginPlat;
	}

	public void setLoginPlat(Integer loginPlat) {
		this.loginPlat = loginPlat;
	}

	public String getActivityImage() {
		return activityImage;
	}

	public void setActivityImage(String activityImage) {
		this.activityImage = activityImage;
	}

	public UserCommon getActivityUser() {
		return activityUser;
	}

	public void setActivityUser(UserCommon activityUser) {
		this.activityUser = activityUser;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public Integer getRaffleRule() {
		return raffleRule;
	}

	public void setRaffleRule(Integer raffleRule) {
		this.raffleRule = raffleRule;
	}

	@Override
	public String toString() {
		return "ActivityCommon [activityId=" + activityId + ", activityName=" + activityName + ", activityImage="
				+ activityImage + ", activityDesc=" + activityDesc + ", type=" + type + ", count=" + count + ", status="
				+ status + ", createTime=" + createTime + ", updateTime=" + updateTime + ", createUser=" + createUser
				+ ", loginPlat=" + loginPlat + ", parentId=" + parentId + ", raffleRule=" + raffleRule
				+ ", activityUser=" + activityUser + "]";
	}

}
