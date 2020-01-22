package com.cofc.pojo.vij;


public class ProjectOffer {

	private Integer pofferId;
	private Integer projectId;
	private Integer offerTypeId;
	//关联分类表
	private String offerTypeName;
	private String coverImage;
	private Integer orderStatus;
	
	public Integer getPofferId() {
		return pofferId;
	}
	public void setPofferId(Integer pofferId) {
		this.pofferId = pofferId;
	}
	public Integer getProjectId() {
		return projectId;
	}
	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
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
}
