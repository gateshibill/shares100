package com.cofc.pojo;

import java.util.Date;

public class GroupTypes {
	private Integer typeId;
	private String typeName;
	private Integer createUser;
	private Integer loginPlat;
	private Integer isUsing;
	private Date createTime;
	private Integer typeType;
	private String defaultPicture;
	
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

	public Integer getCreateUser() {
		return createUser;
	}

	public void setCreateUser(Integer createUser) {
		this.createUser = createUser;
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

	public Integer getTypeType() {
		return typeType;
	}

	public void setTypeType(Integer typeType) {
		this.typeType = typeType;
	}

	@Override
	public String toString() {
		return "GroupTypes [typeId=" + typeId + ", typeName=" + typeName + ", createUser=" + createUser + ", loginPlat="
				+ loginPlat + ", isUsing=" + isUsing + ", createTime=" + createTime + ", typeType=" + typeType
				+ ", defaultPicture=" + defaultPicture + "]";
	}

}
