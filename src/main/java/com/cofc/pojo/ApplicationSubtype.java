package com.cofc.pojo;

import java.util.Date;

public class ApplicationSubtype {

	private Integer id;
	private Integer typeId;
	private String typeName;
	private Integer loginPlat;
	private Integer isUsing;
	private Integer orderNo;
	private Integer appSubtype;
	private Date createTime;
	private String defaultPicture;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
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
	public Integer getLoginPlat() {
		return loginPlat;
	}
	public void setLoginPlat(Integer loginPlat) {
		this.loginPlat = loginPlat;
	}
	public Integer getIsUsing() {
		return isUsing;
	}
	public void setIsUsing(Integer isUsing) {
		this.isUsing = isUsing;
	}
	public Integer getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}
	public Integer getAppSubtype() {
		return appSubtype;
	}
	public void setAppSubtype(Integer appSubtype) {
		this.appSubtype = appSubtype;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getDefaultPicture() {
		return defaultPicture;
	}
	public void setDefaultPicture(String defaultPicture) {
		this.defaultPicture = defaultPicture;
	}
}
