package com.cofc.pojo.vij;

import java.util.Date;

public class ModelRoom {
	private Integer id;
	private String typeName;
	private String keyWord;
	private String modelDesc;
	private String softImages;
	private String hardImages;
	private String homeImages;
	private Date createTime;
	private Integer orderStatus;
	private Integer isEffect;
	private String modelTitle;
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
	public String getKeyWord() {
		return keyWord;
	}
	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}
	public String getModelDesc() {
		return modelDesc;
	}
	public void setModelDesc(String modelDesc) {
		this.modelDesc = modelDesc;
	}
	public String getSoftImages() {
		return softImages;
	}
	public void setSoftImages(String softImages) {
		this.softImages = softImages;
	}
	public String getHardImages() {
		return hardImages;
	}
	public void setHardImages(String hardImages) {
		this.hardImages = hardImages;
	}
	public String getHomeImages() {
		return homeImages;
	}
	public void setHomeImages(String homeImages) {
		this.homeImages = homeImages;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Integer getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}
	public Integer getIsEffect() {
		return isEffect;
	}
	public void setIsEffect(Integer isEffect) {
		this.isEffect = isEffect;
	}
	public String getModelTitle() {
		return modelTitle;
	}
	public void setModelTitle(String modelTitle) {
		this.modelTitle = modelTitle;
	}
}
