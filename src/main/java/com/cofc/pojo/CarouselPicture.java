package com.cofc.pojo;

import java.util.Date;

public class CarouselPicture {
	private Integer pictureId;
	private String pictureUrl;
	private String pcPictureUrl;//pc端
	private Integer uploadUser;
	private Date uploadTime;
	private Integer orderId;
	private Integer isUsing;
	private String pictureName;
	private Integer sxjUser;
	private String hrefUrl;
	private Integer loginPlat;
	private UserCommon pictureUser;

	public Integer getPictureId() {
		return pictureId;
	}

	public void setPictureId(Integer pictureId) {
		this.pictureId = pictureId;
	}

	public String getPictureUrl() {
		return pictureUrl;
	}

	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}

	public Integer getUploadUser() {
		return uploadUser;
	}

	public void setUploadUser(Integer uploadUser) {
		this.uploadUser = uploadUser;
	}

	public Date getUploadTime() {
		return uploadTime;
	}

	public void setUploadTime(Date uploadTime) {
		this.uploadTime = uploadTime;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Integer getIsUsing() {
		return isUsing;
	}

	public void setIsUsing(Integer isUsing) {
		this.isUsing = isUsing;
	}

	public String getPictureName() {
		return pictureName;
	}

	public void setPictureName(String pictureName) {
		this.pictureName = pictureName;
	}

	public Integer getSxjUser() {
		return sxjUser;
	}

	public void setSxjUser(Integer sxjUser) {
		this.sxjUser = sxjUser;
	}

	public UserCommon getPictureUser() {
		return pictureUser;
	}

	public void setPictureUser(UserCommon pictureUser) {
		this.pictureUser = pictureUser;
	}

	public Integer getLoginPlat() {
		return loginPlat;
	}

	public void setLoginPlat(Integer loginPlat) {
		this.loginPlat = loginPlat;
	}

	public String getHrefUrl() {
		return hrefUrl;
	}

	public void setHrefUrl(String hrefUrl) {
		this.hrefUrl = hrefUrl;
	}

	public String getPcPictureUrl() {
		return pcPictureUrl;
	}

	public void setPcPictureUrl(String pcPictureUrl) {
		this.pcPictureUrl = pcPictureUrl;
	}

	@Override
	public String toString() {
		return "CarouselPicture [pictureId=" + pictureId + ", pictureUrl=" + pictureUrl + ", uploadUser=" + uploadUser
				+ ", uploadTime=" + uploadTime + ", orderId=" + orderId + ", isUsing=" + isUsing + ", pictureName="
				+ pictureName + ", sxjUser=" + sxjUser + ", hrefUrl=" + hrefUrl + ", loginPlat=" + loginPlat
				+ ", pictureUser=" + pictureUser + "]";
	}

}
