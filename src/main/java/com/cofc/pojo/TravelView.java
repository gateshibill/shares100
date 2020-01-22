package com.cofc.pojo;

public class TravelView {
	private Integer viewId;
	private Integer travelId;
	private String qqmapViewId;
	private String location;
	private String latitude;
	private String longitude;
	private Integer redbagCount;
	private Integer isOrder;
	private UserCommon user;
	private TravelCommon travel;

	public Integer getViewId() {
		return viewId;
	}

	public void setViewId(Integer viewId) {
		this.viewId = viewId;
	}

	public Integer getTravelId() {
		return travelId;
	}

	public void setTravelId(Integer travelId) {
		this.travelId = travelId;
	}

	public String getQqmapViewId() {
		return qqmapViewId;
	}

	public void setQqmapViewId(String qqmapViewId) {
		this.qqmapViewId = qqmapViewId;
	}

	public String getLatitude() {
		return latitude;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public Integer getRedbagCount() {
		return redbagCount;
	}

	public void setRedbagCount(Integer redbagCount) {
		this.redbagCount = redbagCount;
	}

	public Integer getIsOrder() {
		return isOrder;
	}

	public void setIsOrder(Integer isOrder) {
		this.isOrder = isOrder;
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
	

}
