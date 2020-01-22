package com.cofc.pojo;

import java.util.Date;

public class VerificationCode {
	private Integer codeId;
	private String phone;
	private String messageCode;
	private Date createTime;

	public Integer getCodeId() {
		return codeId;
	}

	public void setCodeId(Integer codeId) {
		this.codeId = codeId;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMessageCode() {
		return messageCode;
	}

	public void setMessageCode(String messageCode) {
		this.messageCode = messageCode;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Override
	public String toString() {
		return "VerificationCode [codeId=" + codeId + ", phone=" + phone + ", messageCode=" + messageCode
				+ ", createTime=" + createTime + "]";
	}

}
