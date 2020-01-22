package com.cofc.pojo;

import java.util.Date;

public class AfficheJoin {
	private Integer joinId;
	private Integer afficheId;
	private Integer joinerId;
	private Date applyTime;
	private Date joinTime;
	private Integer isJoin;

	public Integer getJoinId() {
		return joinId;
	}

	public void setJoinId(Integer joinId) {
		this.joinId = joinId;
	}

	public Integer getAfficheId() {
		return afficheId;
	}

	public void setAfficheId(Integer afficheId) {
		this.afficheId = afficheId;
	}

	public Integer getJoinerId() {
		return joinerId;
	}

	public void setJoinerId(Integer joinerId) {
		this.joinerId = joinerId;
	}

	public Date getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}

	public Date getJoinTime() {
		return joinTime;
	}

	public void setJoinTime(Date joinTime) {
		this.joinTime = joinTime;
	}

	public Integer getIsJoin() {
		return isJoin;
	}

	public void setIsJoin(Integer isJoin) {
		this.isJoin = isJoin;
	}

	@Override
	public String toString() {
		return "AfficheJoin [joinId=" + joinId + ", afficheId=" + afficheId + ", joinerId=" + joinerId + ", applyTime="
				+ applyTime + ", joinTime=" + joinTime + ", isJoin=" + isJoin + "]";
	}

}
