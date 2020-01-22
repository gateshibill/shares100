package com.cofc.pojo.vij;

public class SmsRecord {
	private Integer id;
	private String phone;
	private String code;
	private Integer appId;
	private Integer sendTime;
	private String errorCode;
	private Integer type;//短信类型
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Integer getAppId() {
		return appId;
	}
	public void setAppId(Integer appId) {
		this.appId = appId;
	}
	public Integer getSendTime() {
		return sendTime;
	}
	public void setSendTime(Integer sendTime) {
		this.sendTime = sendTime;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	@Override
	public String toString() {
		return "SmsRecord [id=" + id + ", phone=" + phone + ", code=" + code + ", appId=" + appId + ", sendTime="
				+ sendTime + ", errorCode=" + errorCode + ", type=" + type + "]";
	}
}
