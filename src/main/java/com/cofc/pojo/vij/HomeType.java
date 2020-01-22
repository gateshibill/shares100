package com.cofc.pojo.vij;

import java.util.Date;

public class HomeType {

	private Integer id;
	private String homeTypeName;	
	private Integer orderStatus;
	private String homeCoverImage;
	private String homeImageList;
	private Date createTime;
	private Integer isShow;
	private String content;
	private Integer loginPlat;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getHomeTypeName() {
		return homeTypeName;
	}
	public void setHomeTypeName(String homeTypeName) {
		this.homeTypeName = homeTypeName;
	}
	public Integer getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getHomeCoverImage() {
		return homeCoverImage;
	}
	public void setHomeCoverImage(String homeCoverImage) {
		this.homeCoverImage = homeCoverImage;
	}
	public String getHomeImageList() {
		return homeImageList;
	}
	public void setHomeImageList(String homeImageList) {
		this.homeImageList = homeImageList;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Integer getIsShow() {
		return isShow;
	}
	public void setIsShow(Integer isShow) {
		this.isShow = isShow;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getLoginPlat() {
		return loginPlat;
	}
	public void setLoginPlat(Integer loginPlat) {
		this.loginPlat = loginPlat;
	}
}
