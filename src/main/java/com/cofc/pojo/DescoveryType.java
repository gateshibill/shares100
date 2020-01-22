package com.cofc.pojo;

import java.util.Date;
import java.util.List;

public class DescoveryType {
	private Integer typeId;
	private String typeName;
	private Date createTime;
	private String loginPlat;
	private Integer isEffect;
	private Integer orderNo;
	private List<TagCommon> comTagList;

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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getLoginPlat() {
		return loginPlat;
	}

	public void setLoginPlat(String loginPlat) {
		this.loginPlat = loginPlat;
	}

	public Integer getIsEffect() {
		return isEffect;
	}

	public void setIsEffect(Integer isEffect) {
		this.isEffect = isEffect;
	}

	public Integer getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}

	public List<TagCommon> getComTagList() {
		return comTagList;
	}

	public void setComTagList(List<TagCommon> comTagList) {
		this.comTagList = comTagList;
	}

	@Override
	public String toString() {
		return "DescoveryType [typeId=" + typeId + ", typeName=" + typeName + ", createTime=" + createTime
				+ ", loginPlat=" + loginPlat + ", isEffect=" + isEffect + ", orderNo=" + orderNo + ", comTagList="
				+ comTagList + "]";
	}

}
