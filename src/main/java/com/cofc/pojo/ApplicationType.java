package com.cofc.pojo;

import java.util.Date;

public class ApplicationType {
	private Integer typeId;
	private String typeName;
	private Integer isUsing;
	private Integer orderNo;
	private Date createTime;
	private Integer modelId;
	
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
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Integer getModelId() {
		return modelId;
	}
	public void setModelId(Integer modelId) {
		this.modelId = modelId;
	}
}
