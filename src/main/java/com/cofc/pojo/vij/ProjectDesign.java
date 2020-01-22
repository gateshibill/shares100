package com.cofc.pojo.vij;

import java.util.Date;

public class ProjectDesign {

	private Integer designId;
	private Integer projectId;
	private String imageList;
	private Date createTime;
	private Integer designType;
	private String designIntro;
	
	public Integer getDesignType() {
		return designType;
	}
	public void setDesignType(Integer designType) {
		this.designType = designType;
	}
	public Integer getDesignId() {
		return designId;
	}
	public void setDesignId(Integer designId) {
		this.designId = designId;
	}
	public Integer getProjectId() {
		return projectId;
	}
	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}
	public String getImageList() {
		return imageList;
	}
	public void setImageList(String imageList) {
		this.imageList = imageList;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getDesignIntro() {
		return designIntro;
	}
	public void setDesignIntro(String designIntro) {
		this.designIntro = designIntro;
	}
}
