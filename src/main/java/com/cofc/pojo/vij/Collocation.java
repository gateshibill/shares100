package com.cofc.pojo.vij;

import java.util.Date;
import java.util.List;

/**
 * 搭配
 * @author 46678
 *
 */
public class Collocation {
	private Integer id;
	private String collTitle;
	private String collDesc;
	private String collDetail;
	private String collImages;
	private Integer isRecommend;
	private Integer collType;
	private Date createTime;
	private String collCover;
	private Integer orderStatus;
	private Integer loginPlat;
	private Integer imagesCount;
	private List<String> image;
	private List<GoodColl> goodCollList;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCollTitle() {
		return collTitle;
	}
	public void setCollTitle(String collTitle) {
		this.collTitle = collTitle;
	}
	public String getCollDesc() {
		return collDesc;
	}
	public void setCollDesc(String collDesc) {
		this.collDesc = collDesc;
	}
	public String getCollDetail() {
		return collDetail;
	}
	public void setCollDetail(String collDetail) {
		this.collDetail = collDetail;
	}
	public String getCollImages() {
		return collImages;
	}
	public void setCollImages(String collImages) {
		this.collImages = collImages;
	}
	public Integer getIsRecommend() {
		return isRecommend;
	}
	public void setIsRecommend(Integer isRecommend) {
		this.isRecommend = isRecommend;
	}
	public Integer getCollType() {
		return collType;
	}
	public void setCollType(Integer collType) {
		this.collType = collType;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getCollCover() {
		return collCover;
	}
	public void setCollCover(String collCover) {
		this.collCover = collCover;
	}
	public Integer getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}
	public Integer getImagesCount() {
		return imagesCount;
	}
	public void setImagesCount(Integer imagesCount) {
		this.imagesCount = imagesCount;
	}
	public List<String> getImage() {
		return image;
	}
	public void setImage(List<String> image) {
		this.image = image;
	}
	public Integer getLoginPlat() {
		return loginPlat;
	}
	public void setLoginPlat(Integer loginPlat) {
		this.loginPlat = loginPlat;
	}
	public List<GoodColl> getGoodCollList() {
		return goodCollList;
	}
	public void setGoodCollList(List<GoodColl> goodCollList) {
		this.goodCollList = goodCollList;
	}
}
