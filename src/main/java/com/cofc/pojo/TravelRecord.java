package com.cofc.pojo;

import java.util.Date;

public class TravelRecord {
	private Integer recordId;
	private Integer userId;
	private Integer travelId;
	private Integer viewId;
	private Date createTime;
	private Integer isEffect;
	private UserCommon user;
	private TravelCommon travel;
    private TravelView view; 
	public Integer getRecordId() {
		return recordId;
	}

	public void setRecordId(Integer recordId) {
		this.recordId = recordId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getTravelId() {
		return travelId;
	}

	public void setTravelId(Integer travelId) {
		this.travelId = travelId;
	}

	public Integer getViewId() {
		return viewId;
	}

	public void setViewId(Integer viewId) {
		this.viewId = viewId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getIsEffect() {
		return isEffect;
	}

	public void setIsEffect(Integer isEffect) {
		this.isEffect = isEffect;
	}

	public UserCommon getUser() {
		return user;
	}

	public void setUser(UserCommon user) {
		this.user = user;
	}

	public TravelCommon getTravel() {
		return travel;
	}

	public void setTravel(TravelCommon travel) {
		this.travel = travel;
	}

	public TravelView getView() {
		return view;
	}

	public void setView(TravelView view) {
		this.view = view;
	}
	

}
