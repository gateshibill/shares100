package com.cofc.pojo;

import java.util.Date;

public class UserDiary {
	private Integer diaryId;
	private Integer userId;
	private Date createTime;
	private String diaryTitle;
	private String diaryDetails;
	
	public Integer getDiaryId() {
		return diaryId;
	}
	public void setDiaryId(Integer diaryId) {
		this.diaryId = diaryId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getDiaryTitle() {
		return diaryTitle;
	}
	public void setDiaryTitle(String diaryTitle) {
		this.diaryTitle = diaryTitle;
	}
	public String getDiaryDetails() {
		return diaryDetails;
	}
	public void setDiaryDetails(String diaryDetails) {
		this.diaryDetails = diaryDetails;
	}
	@Override
	public String toString() {
		return "UserDiary [diaryId=" + diaryId + ", userId=" + userId + ", createTime=" + createTime + ", diaryTitle="
				+ diaryTitle + ", diaryDetails=" + diaryDetails + "]";
	}
	
}
