package com.cofc.pojo;

import java.util.Date;

public class AfficheCommon {
	private Integer afficheId;
	private String afficheTitle;
	private String afficheDetails;
	private String afficheImage;
	private Integer publisherId;
	private Integer afficheType;
	private Integer loginPlat;
	private Date createTime;
	private Integer relatedActivity;
	private Date afficheBeginTime;
	private Date afficheEndTime;
	private Integer readCount;
	private Integer praiseCount;
	private Integer joinCount;
	private Integer isRemove;
	private String subjectType;
	private String backgroundUrl;
	private UserCommon afficheComUser;

	public Integer getAfficheId() {
		return afficheId;
	}

	public void setAfficheId(Integer afficheId) {
		this.afficheId = afficheId;
	}

	public String getAfficheTitle() {
		return afficheTitle;
	}

	public void setAfficheTitle(String afficheTitle) {
		this.afficheTitle = afficheTitle;
	}

	public String getAfficheDetails() {
		return afficheDetails;
	}

	public void setAfficheDetails(String afficheDetails) {
		this.afficheDetails = afficheDetails;
	}

	public String getAfficheImage() {
		return afficheImage;
	}

	public void setAfficheImage(String afficheImage) {
		this.afficheImage = afficheImage;
	}

	public Integer getPublisherId() {
		return publisherId;
	}

	public void setPublisherId(Integer publisherId) {
		this.publisherId = publisherId;
	}

	public Integer getAfficheType() {
		return afficheType;
	}

	public void setAfficheType(Integer afficheType) {
		this.afficheType = afficheType;
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

	public Integer getRelatedActivity() {
		return relatedActivity;
	}

	public void setRelatedActivity(Integer relatedActivity) {
		this.relatedActivity = relatedActivity;
	}

	public Date getAfficheBeginTime() {
		return afficheBeginTime;
	}

	public void setAfficheBeginTime(Date afficheBeginTime) {
		this.afficheBeginTime = afficheBeginTime;
	}

	public Date getAfficheEndTime() {
		return afficheEndTime;
	}

	public void setAfficheEndTime(Date afficheEndTime) {
		this.afficheEndTime = afficheEndTime;
	}

	public Integer getReadCount() {
		return readCount;
	}

	public void setReadCount(Integer readCount) {
		this.readCount = readCount;
	}

	public Integer getPraiseCount() {
		return praiseCount;
	}

	public void setPraiseCount(Integer praiseCount) {
		this.praiseCount = praiseCount;
	}

	public Integer getJoinCount() {
		return joinCount;
	}

	public void setJoinCount(Integer joinCount) {
		this.joinCount = joinCount;
	}

	public Integer getIsRemove() {
		return isRemove;
	}

	public void setIsRemove(Integer isRemove) {
		this.isRemove = isRemove;
	}

	public String getSubjectType() {
		return subjectType;
	}

	public void setSubjectType(String subjectType) {
		this.subjectType = subjectType;
	}

	public String getBackgroundUrl() {
		return backgroundUrl;
	}

	public void setBackgroundUrl(String backgroundUrl) {
		this.backgroundUrl = backgroundUrl;
	}

	public UserCommon getAfficheComUser() {
		return afficheComUser;
	}

	public void setAfficheComUser(UserCommon afficheComUser) {
		this.afficheComUser = afficheComUser;
	}

	@Override
	public String toString() {
		return "AfficheCommon [afficheId=" + afficheId + ", afficheTitle=" + afficheTitle + ", afficheDetails="
				+ afficheDetails + ", afficheImage=" + afficheImage + ", publisherId=" + publisherId + ", afficheType="
				+ afficheType + ", loginPlat=" + loginPlat + ", createTime=" + createTime + ", relatedActivity="
				+ relatedActivity + ", afficheBeginTime=" + afficheBeginTime + ", afficheEndTime=" + afficheEndTime
				+ ", readCount=" + readCount + ", praiseCount=" + praiseCount + ", joinCount=" + joinCount
				+ ", isRemove=" + isRemove + ", afficheComUser=" + afficheComUser + "]";
	}

}
