package com.cofc.pojo;

import java.util.List;

public class ChildTags {
	private Integer orderId;
	private List<ProjectTags> pjtList;
	private List<ProductTags> pdtList;
	private List<SkillTags> sktList;
	private List<WantedTags> wttList;

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public List<ProjectTags> getPjtList() {
		return pjtList;
	}

	public void setPjtList(List<ProjectTags> pjtList) {
		this.pjtList = pjtList;
	}

	public List<ProductTags> getPdtList() {
		return pdtList;
	}

	public void setPdtList(List<ProductTags> pdtList) {
		this.pdtList = pdtList;
	}

	public List<SkillTags> getSktList() {
		return sktList;
	}

	public void setSktList(List<SkillTags> sktList) {
		this.sktList = sktList;
	}

	public List<WantedTags> getWttList() {
		return wttList;
	}

	public void setWttList(List<WantedTags> wttList) {
		this.wttList = wttList;
	}

	@Override
	public String toString() {
		return "ChildTags [orderId=" + orderId + ", pjtList=" + pjtList + ", pdtList=" + pdtList + ", sktList="
				+ sktList + ", wttList=" + wttList + "]";
	}

}
