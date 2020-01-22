package com.cofc.pojo;

import java.util.Date;
import java.util.List;

import com.cofc.pojo.vij.GoodTypeBanner;

public class GoodsType {
	private Integer typeId;
	private String typeName;
	private Date createTime;
	private Integer createUser;
	private Integer loginPlat;
	private Integer orderNo;
	private String typeIcon;
	private Integer parentId;//类型父Id
	private Integer isRemove; //上下架类型
	private String enTypeName;//英文名称
	
	private List<GoodsType> childList;
	private List<GoodTypeBanner> bannerList;
	private ApplicationCommon application;
	private UserCommon user;
	
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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getCreateUser() {
		return createUser;
	}

	public void setCreateUser(Integer createUser) {
		this.createUser = createUser;
	}

	public Integer getLoginPlat() {
		return loginPlat;
	}

	public void setLoginPlat(Integer loginPlat) {
		this.loginPlat = loginPlat;
	}

	@Override
	public String toString() {
		return "GoodsType [typeId=" + typeId + ", typeName=" + typeName + ", createTime=" + createTime + ", createUser="
				+ createUser + ", loginPlat=" + loginPlat + "]";
	}

	public ApplicationCommon getApplication() {
		return application;
	}

	public void setApplication(ApplicationCommon application) {
		this.application = application;
	}

	public Integer getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}

	public UserCommon getUser() {
		return user;
	}

	public void setUser(UserCommon user) {
		this.user = user;
	}

	public String getTypeIcon() {
		return typeIcon;
	}

	public void setTypeIcon(String typeIcon) {
		this.typeIcon = typeIcon;
	}

	public Integer getIsRemove() {
		return isRemove;
	}

	public void setIsRemove(Integer isRemove) {
		this.isRemove = isRemove;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public List<GoodsType> getChildList() {
		return childList;
	}

	public void setChildList(List<GoodsType> childList) {
		this.childList = childList;
	}

	public String getEnTypeName() {
		return enTypeName;
	}

	public void setEnTypeName(String enTypeName) {
		this.enTypeName = enTypeName;
	}

	public List<GoodTypeBanner> getBannerList() {
		return bannerList;
	}

	public void setBannerList(List<GoodTypeBanner> bannerList) {
		this.bannerList = bannerList;
	}
}
