package com.cofc.pojo;

import java.util.Date;

public class CompanyAbstract {
	public Integer abstractId;
	public String abstractTitle;
	public String abstractContent;
	public Date createTime;
	public Date updateTime;
	public Integer loginPlat;
	private String abstractPictures;
	private String companyAddress;
	private String companyPhone;
	private String titleImage;

	public Integer getAbstractId() {
		return abstractId;
	}

	public void setAbstractId(Integer abstractId) {
		this.abstractId = abstractId;
	}

	public String getAbstractTitle() {
		return abstractTitle;
	}

	public void setAbstractTitle(String abstractTitle) {
		this.abstractTitle = abstractTitle;
	}

	public String getAbstractContent() {
		return abstractContent;
	}

	public void setAbstractContent(String abstractContent) {
		this.abstractContent = abstractContent;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getLoginPlat() {
		return loginPlat;
	}

	public void setLoginPlat(Integer loginPlat) {
		this.loginPlat = loginPlat;
	}

	public String getAbstractPictures() {
		return abstractPictures;
	}

	public void setAbstractPictures(String abstractPictures) {
		this.abstractPictures = abstractPictures;
	}

	public String getCompanyAddress() {
		return companyAddress;
	}

	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}

	public String getCompanyPhone() {
		return companyPhone;
	}

	public void setCompanyPhone(String companyPhone) {
		this.companyPhone = companyPhone;
	}

	@Override
	public String toString() {
		return "CompanyAbstract [abstractId=" + abstractId + ", abstractTitle=" + abstractTitle + ", abstractContent="
				+ abstractContent + ", createTime=" + createTime + ", updateTime=" + updateTime + ", loginPlat="
				+ loginPlat + ", abstractPictures=" + abstractPictures + ", companyAddress=" + companyAddress
				+ ", companyPhone=" + companyPhone + "]";
	}

	public String getTitleImage() {
		return titleImage;
	}

	public void setTitleImage(String titleImage) {
		this.titleImage = titleImage;
	}
}
