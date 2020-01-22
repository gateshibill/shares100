package com.cofc.pojo.vij;

import java.util.Date;

public class ProjectOfferType {

	private Integer offerTypeId;
	private String offerTypeName;
	private Integer projectId;
	private String coverImage;
	private Integer orderStatus;
	private Date createTime;
	private Integer isEffect;
	private Integer loginPlat;
	
	public Integer getLoginPlat() {
		return loginPlat;
	}
	public void setLoginPlat(Integer loginPlat) {
		this.loginPlat = loginPlat;
	}
	public Integer getOfferTypeId() {
		return offerTypeId;
	}
	public void setOfferTypeId(Integer offerTypeId) {
		this.offerTypeId = offerTypeId;
	}
	public String getOfferTypeName() {
		return offerTypeName;
	}
	public void setOfferTypeName(String offerTypeName) {
		this.offerTypeName = offerTypeName;
	}
	public Integer getProjectId() {
		return projectId;
	}
	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}
	public String getCoverImage() {
		return coverImage;
	}
	public void setCoverImage(String coverImage) {
		this.coverImage = coverImage;
	}
	public Integer getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Integer getIsEffect() {
		return isEffect;
	}
	public void setIsEffect(Integer isEffect) {
		this.isEffect = isEffect;
	}

}
