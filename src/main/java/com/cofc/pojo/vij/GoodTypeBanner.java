package com.cofc.pojo.vij;


import java.util.Date;

public class GoodTypeBanner {

	private Integer id;
	private String imgUrl;
	private String imgTitle;
	private Integer isStatus;
	private Date createTime;
	private Integer imgOrder;
	private Integer appId;
	private Integer goodTypeId;
	
	private  String typeName; //类型名称
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public String getImgTitle() {
		return imgTitle;
	}
	public void setImgTitle(String imgTitle) {
		this.imgTitle = imgTitle;
	}
	public Integer getIsStatus() {
		return isStatus;
	}
	public void setIsStatus(Integer isStatus) {
		this.isStatus = isStatus;
	}
	
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Integer getImgOrder() {
		return imgOrder;
	}
	public Integer getAppId() {
		return appId;
	}
	public void setAppId(Integer appId) {
		this.appId = appId;
	}
	public Integer getGoodTypeId() {
		return goodTypeId;
	}
	public void setGoodTypeId(Integer goodTypeId) {
		this.goodTypeId = goodTypeId;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public void setImgOrder(Integer imgOrder) {
		this.imgOrder = imgOrder;
	}
}
