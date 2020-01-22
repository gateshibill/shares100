package com.cofc.pojo;

import java.util.Date;

public class TravelJoin {
	private Integer joinId;
	private Integer userId;
	private Integer travelId;
	private Integer state;
	private Date createTime;
    private UserCommon joinUser;
    private TravelCommon joinTravel;
	public Integer getJoinId() {
		return joinId;
	}

	public void setJoinId(Integer joinId) {
		this.joinId = joinId;
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

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public UserCommon getJoinUser() {
		return joinUser;
	}

	public void setJoinUser(UserCommon joinUser) {
		this.joinUser = joinUser;
	}

	public TravelCommon getJoinTravel() {
		return joinTravel;
	}

	public void setJoinTravel(TravelCommon joinTravel) {
		this.joinTravel = joinTravel;
	}
	

}
