package com.cofc.pojo;

import java.util.Date;

public class ConcernedUser {
	private Integer concernedUserId;
	private String concernedNickName;
	private String concernedRealName;
	private String concernedphone;
	private String concernedHeadImage;
	private String concernedEmail;
	private Integer concernedSex;
	private Date concernedCreateTime;
	private String concernedCompony;
	private String concernedComponyIntroduce;

	public Integer getConcernedUserId() {
		return concernedUserId;
	}

	public void setConcernedUserId(Integer concernedUserId) {
		this.concernedUserId = concernedUserId;
	}

	public String getConcernedNickName() {
		return concernedNickName;
	}

	public void setConcernedNickName(String concernedNickName) {
		this.concernedNickName = concernedNickName;
	}

	public String getConcernedRealName() {
		return concernedRealName;
	}

	public void setConcernedRealName(String concernedRealName) {
		this.concernedRealName = concernedRealName;
	}

	public String getConcernedphone() {
		return concernedphone;
	}

	public void setConcernedphone(String concernedphone) {
		this.concernedphone = concernedphone;
	}

	public String getConcernedHeadImage() {
		return concernedHeadImage;
	}

	public void setConcernedHeadImage(String concernedHeadImage) {
		this.concernedHeadImage = concernedHeadImage;
	}

	public String getConcernedEmail() {
		return concernedEmail;
	}

	public void setConcernedEmail(String concernedEmail) {
		this.concernedEmail = concernedEmail;
	}

	public Integer getConcernedSex() {
		return concernedSex;
	}

	public void setConcernedSex(Integer concernedSex) {
		this.concernedSex = concernedSex;
	}

	public Date getConcernedCreateTime() {
		return concernedCreateTime;
	}

	public void setConcernedCreateTime(Date concernedCreateTime) {
		this.concernedCreateTime = concernedCreateTime;
	}

	public String getConcernedCompony() {
		return concernedCompony;
	}

	public void setConcernedCompony(String concernedCompony) {
		this.concernedCompony = concernedCompony;
	}

	public String getConcernedComponyIntroduce() {
		return concernedComponyIntroduce;
	}

	public void setConcernedComponyIntroduce(String concernedComponyIntroduce) {
		this.concernedComponyIntroduce = concernedComponyIntroduce;
	}

	@Override
	public String toString() {
		return "ConcernedUser [concernedUserId=" + concernedUserId + ", concernedNickName=" + concernedNickName
				+ ", concernedRealName=" + concernedRealName + ", concernedphone=" + concernedphone
				+ ", concernedHeadImage=" + concernedHeadImage + ", concernedEmail=" + concernedEmail
				+ ", concernedSex=" + concernedSex + ", concernedCreateTime=" + concernedCreateTime
				+ ", concernedCompony=" + concernedCompony + ", concernedComponyIntroduce=" + concernedComponyIntroduce
				+ "]";
	}
}
