package com.cofc.pojo;

import java.util.Date;

public class ApplicationColumn {

	private Integer typeId;
	private String typeName;
	private String loginPlat;
	private Integer orderNo;
	private Integer isEffect;
	private Date createTime;
	private Integer appType;
	
	public Integer getTypeId() {
		return typeId;
	}
	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getLoginPlat() {
		return loginPlat;
	}
	public void setLoginPlat(String loginPlat) {
		this.loginPlat = loginPlat;
	}
	public Integer getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}
	public Integer getIsEffect() {
		return isEffect;
	}
	public void setIsEffect(Integer isEffect) {
		this.isEffect = isEffect;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Integer getAppType() {
		return appType;
	}
	public void setAppType(Integer appType) {
		this.appType = appType;
	}
}
