package com.cofc.pojo;

import java.util.Date;

public class TravelCommon {
	private Integer travelId;
	private String travelTitle;
	private String travelContent;
	private String travelImageList;
	private String travelViewList;
	private Integer travelViewCount;
	private Integer travelPublisherId;
	private Integer travelGuiderId;
	private Integer travelJoinPeople;
	private Integer isHot;
	private Integer isRecommend;
	private Integer redBagCount;
	private Integer moneyCount;
	private Integer couponCount;
	private Integer loginPlat;
	private Integer state;
	private String score;
	private String startPlace;
	private String endPlace;
	private Date startTime;
	private Date endTime;
	private Date createTime;
	private Integer isEffect;
	private Integer travelPrice;
    private UserCommon pUser; //获取发布者信息
	public Integer getIsEffect() {
		return isEffect;
	}

	public void setIsEffect(Integer isEffect) {
		this.isEffect = isEffect;
	}

	public Integer getTravelId() {
		return travelId;
	}

	public void setTravelId(Integer travelId) {
		this.travelId = travelId;
	}

	public String getTravelTitle() {
		return travelTitle;
	}

	public void setTravelTitle(String travelTitle) {
		this.travelTitle = travelTitle;
	}

	public String getTravelContent() {
		return travelContent;
	}

	public void setTravelContent(String travelContent) {
		this.travelContent = travelContent;
	}

	public String getTravelImageList() {
		return travelImageList;
	}

	public void setTravelImageList(String travelImageList) {
		this.travelImageList = travelImageList;
	}

	public String getTravelViewList() {
		return travelViewList;
	}

	public void setTravelViewList(String travelViewList) {
		this.travelViewList = travelViewList;
	}

	public Integer getTravelViewCount() {
		return travelViewCount;
	}

	public void setTravelViewCount(Integer travelViewCount) {
		this.travelViewCount = travelViewCount;
	}

	public Integer getTravelPublisherId() {
		return travelPublisherId;
	}

	public void setTravelPublisherId(Integer travelPublisherId) {
		this.travelPublisherId = travelPublisherId;
	}

	public Integer getTravelGuiderId() {
		return travelGuiderId;
	}

	public void setTravelGuiderId(Integer travelGuiderId) {
		this.travelGuiderId = travelGuiderId;
	}

	public Integer getTravelJoinPeople() {
		return travelJoinPeople;
	}

	public void setTravelJoinPeople(Integer travelJoinPeople) {
		this.travelJoinPeople = travelJoinPeople;
	}

	public Integer getIsHot() {
		return isHot;
	}

	public void setIsHot(Integer isHot) {
		this.isHot = isHot;
	}

	public Integer getIsRecommend() {
		return isRecommend;
	}

	public void setIsRecommend(Integer isRecommend) {
		this.isRecommend = isRecommend;
	}

	public Integer getRedBagCount() {
		return redBagCount;
	}

	public void setRedBagCount(Integer redBagCount) {
		this.redBagCount = redBagCount;
	}

	public Integer getMoneyCount() {
		return moneyCount;
	}

	public void setMoneyCount(Integer moneyCount) {
		this.moneyCount = moneyCount;
	}

	public Integer getCouponCount() {
		return couponCount;
	}

	public void setCouponCount(Integer couponCount) {
		this.couponCount = couponCount;
	}

	public Integer getLoginPlat() {
		return loginPlat;
	}

	public void setLoginPlat(Integer loginPlat) {
		this.loginPlat = loginPlat;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public String getStartPlace() {
		return startPlace;
	}

	public void setStartPlace(String startPlace) {
		this.startPlace = startPlace;
	}

	public String getEndPlace() {
		return endPlace;
	}

	public void setEndPlace(String endPlace) {
		this.endPlace = endPlace;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public UserCommon getpUser() {
		return pUser;
	}

	public void setpUser(UserCommon pUser) {
		this.pUser = pUser;
	}

	public Integer getTravelPrice() {
		return travelPrice;
	}

	public void setTravelPrice(Integer travelPrice) {
		this.travelPrice = travelPrice;
	}
	
	

}
