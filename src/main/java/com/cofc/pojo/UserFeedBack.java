package com.cofc.pojo;

import java.util.Date;

public class UserFeedBack {
	private Integer feedId;
	private Integer feedUser;
	private String feederPhone;
	private String feederEmail;
	private String feedDetail;
	private Date feedTime;

	public Integer getFeedId() {
		return feedId;
	}

	public void setFeedId(Integer feedId) {
		this.feedId = feedId;
	}

	public Integer getFeedUser() {
		return feedUser;
	}

	public void setFeedUser(Integer feedUser) {
		this.feedUser = feedUser;
	}

	public String getFeederPhone() {
		return feederPhone;
	}

	public void setFeederPhone(String feederPhone) {
		this.feederPhone = feederPhone;
	}

	public String getFeederEmail() {
		return feederEmail;
	}

	public void setFeederEmail(String feederEmail) {
		this.feederEmail = feederEmail;
	}

	public String getFeedDetail() {
		return feedDetail;
	}

	public void setFeedDetail(String feedDetail) {
		this.feedDetail = feedDetail;
	}

	public Date getFeedTime() {
		return feedTime;
	}

	public void setFeedTime(Date feedTime) {
		this.feedTime = feedTime;
	}

	@Override
	public String toString() {
		return "UserFeedBack [feedId=" + feedId + ", feedUser=" + feedUser + ", feederPhone=" + feederPhone
				+ ", feederEmail=" + feederEmail + ", feedDetail=" + feedDetail + ", feedTime=" + feedTime + "]";
	}
	
}
