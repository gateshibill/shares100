package com.cofc.pojo.vij;

import java.util.List;

public class ProjectWorkPlanType {
	private Integer id;
	private String typeName;
	private Integer orderStatus;
	private Integer loginPlat;
	private Integer decoType;//1：硬装  2：软装
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public Integer getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}
	public Integer getLoginPlat() {
		return loginPlat;
	}
	public void setLoginPlat(Integer loginPlat) {
		this.loginPlat = loginPlat;
	}
	public Integer getDecoType() {
		return decoType;
	}
	public void setDecoType(Integer decoType) {
		this.decoType = decoType;
	}
}
