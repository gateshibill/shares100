package com.cofc.pojo;

public class DescoveryRecommend {

	private String id;  //id = descoveryID + "_" + applicationID;
	private int descoveryID;
	private int applicationID;
	private int descoveryType;
	private int mark;
	private String desc;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getDescoveryID() {
		return descoveryID;
	}
	public void setDescoveryID(int descoveryID) {
		this.descoveryID = descoveryID;
	}
	public int getApplicationID() {
		return applicationID;
	}
	public void setApplicationID(int applicationID) {
		this.applicationID = applicationID;
	}
	public int getDescoveryType() {
		return descoveryType;
	}
	public void setDescoveryType(int descoveryType) {
		this.descoveryType = descoveryType;
	}
	public int getMark() {
		return mark;
	}
	public void setMark(int mark) {
		this.mark = mark;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
}
