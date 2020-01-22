package com.cofc.pojo.vij;

import java.util.Date;

public class GoodBrand {
	private Integer brandId;
	private String cnBrandName;
	private String enBrandName;
	private String brandLogo;
	private Integer isRemove;
	private Integer brandOrder;
	private Date createTime;
	private Integer loginPlat;
	public Integer getBrandId() {
		return brandId;
	}
	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}
	public String getCnBrandName() {
		return cnBrandName;
	}
	public void setCnBrandName(String cnBrandName) {
		this.cnBrandName = cnBrandName;
	}
	public String getEnBrandName() {
		return enBrandName;
	}
	public void setEnBrandName(String enBrandName) {
		this.enBrandName = enBrandName;
	}
	public String getBrandLogo() {
		return brandLogo;
	}
	public void setBrandLogo(String brandLogo) {
		this.brandLogo = brandLogo;
	}
	public Integer getIsRemove() {
		return isRemove;
	}
	public void setIsRemove(Integer isRemove) {
		this.isRemove = isRemove;
	}
	public Integer getBrandOrder() {
		return brandOrder;
	}
	public void setBrandOrder(Integer brandOrder) {
		this.brandOrder = brandOrder;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Integer getLoginPlat() {
		return loginPlat;
	}
	public void setLoginPlat(Integer loginPlat) {
		this.loginPlat = loginPlat;
	}
}
