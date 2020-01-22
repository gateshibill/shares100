package com.cofc.pojo;

import java.util.Date;

public class ShareActivity {
	private Integer activityId;
	private String activityDesc;
	private Integer publisherId;
	private Integer loginPlat;
	private Integer activityStatus;
	private String activityImage;
	private Date createTime;
	private Date updateTime;
	private Integer shareNumber;
	private Integer isPublic;
	
	//连表
	private UserCommon user;
	private ApplicationCommon application;

	//临时数据
	private String userNickName;
	private String headImage; 
	
	public Integer getActivityId() {
		return activityId;
	}
	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
	}
	public String getActivityDesc() {
		return activityDesc;
	}
	public void setActivityDesc(String activityDesc) {
		this.activityDesc = activityDesc;
	}
	public Integer getPublisherId() {
		return publisherId;
	}
	public void setPublisherId(Integer publisherId) {
		this.publisherId = publisherId;
	}
	public Integer getLoginPlat() {
		return loginPlat;
	}
	public void setLoginPlat(Integer loginPlat) {
		this.loginPlat = loginPlat;
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
	
	public Integer getActivityStatus() {
		return activityStatus;
	}
	public void setActivityStatus(Integer activityStatus) {
		this.activityStatus = activityStatus;
	}
	public String getActivityImage() {
		return activityImage;
	}
	public void setActivityImage(String activityImage) {
		this.activityImage = activityImage;
	}
	
	public String getHeadImage() {
		return headImage;
	}
	public void setHeadImage(String headImage) {
		this.headImage = headImage;
	}
	public String getUserNickName() {
		return userNickName;
	}
	public void setUserNickName(String userNickName) {
		this.userNickName = userNickName;
	}
	public UserCommon getUser() {
		return user;
	}
	public void setUser(UserCommon user) {
		this.user = user;
	}
	public ApplicationCommon getApplication() {
		return application;
	}
	public void setApplication(ApplicationCommon application) {
		this.application = application;
	}
	public Integer getShareNumber() {
		return shareNumber;
	}
	public void setShareNumber(Integer shareNumber) {
		this.shareNumber = shareNumber;
	}
	public Integer getIsPublic() {
		return isPublic;
	}
	public void setIsPublic(Integer isPublic) {
		this.isPublic = isPublic;
	}
}
