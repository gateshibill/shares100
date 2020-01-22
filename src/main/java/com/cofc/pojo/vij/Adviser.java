package com.cofc.pojo.vij;

import java.util.Date;

/**
 * 顾问/设计师/工长
 * @author 46678
 *
 */
public class Adviser {
	private Integer id;
	private String realName;
	private String nickName;
	private String position;
	private String introduce;
	private String phone;
	private String email;
	private String qq;
	private String images;
	private String wechat;
	private String isRecommend;
	private Date createTime;
	private Integer orderStatus;//排序
	private String city;//城市
	private Integer isFree;
	private Integer userId;	//关联用户Id
	private Integer advType;	//1:顾问 2:设计师 3：工长
	private String advSay;
	private Integer loginPlat;
	private String appImageUrl;
	private String advImages;	//设计师图片
	
	
	public String getAdvImages() {
		return advImages;
	}
	public void setAdvImages(String advImages) {
		this.advImages = advImages;
	}
	public String getAppImageUrl() {
		return appImageUrl;
	}
	public void setAppImageUrl(String appImageUrl) {
		this.appImageUrl = appImageUrl;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getIntroduce() {
		return introduce;
	}
	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getQq() {
		return qq;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}
	public String getImages() {
		return images;
	}
	public void setImages(String images) {
		this.images = images;
	}
	public String getWechat() {
		return wechat;
	}
	public void setWechat(String wechat) {
		this.wechat = wechat;
	}
	public String getIsRecommend() {
		return isRecommend;
	}
	public void setIsRecommend(String isRecommend) {
		this.isRecommend = isRecommend;
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
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public Integer getIsFree() {
		return isFree;
	}
	public void setIsFree(Integer isFree) {
		this.isFree = isFree;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getAdvType() {
		return advType;
	}
	public void setAdvType(Integer advType) {
		this.advType = advType;
	}
	public String getAdvSay() {
		return advSay;
	}
	public void setAdvSay(String advSay) {
		this.advSay = advSay;
	}
	public Integer getLoginPlat() {
		return loginPlat;
	}
	public void setLoginPlat(Integer loginPlat) {
		this.loginPlat = loginPlat;
	}
}
