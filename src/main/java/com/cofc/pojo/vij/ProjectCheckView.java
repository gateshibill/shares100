package com.cofc.pojo.vij;

import java.util.Date;

public class ProjectCheckView {
	private Integer id;
	private Integer smallPlanId;
	private String content;
	private String imageList;
	private Date createTime;
	private Integer isDeal;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getSmallPlanId() {
		return smallPlanId;
	}
	public void setSmallPlanId(Integer smallPlanId) {
		this.smallPlanId = smallPlanId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getImageList() {
		return imageList;
	}
	public void setImageList(String imageList) {
		this.imageList = imageList;
	}
	public Integer getIsDeal() {
		return isDeal;
	}
	public void setIsDeal(Integer isDeal) {
		this.isDeal = isDeal;
	}
}
