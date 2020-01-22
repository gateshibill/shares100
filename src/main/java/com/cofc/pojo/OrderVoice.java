package com.cofc.pojo;

import java.util.Date;

public class OrderVoice {
    private Integer voiceId;
    private Integer orderId;
    private Integer loginPlat;
    private Integer isPlay;
    private Date createTime;
	public Integer getVoiceId() {
		return voiceId;
	}
	public void setVoiceId(Integer voiceId) {
		this.voiceId = voiceId;
	}
	public Integer getLoginPlat() {
		return loginPlat;
	}
	public void setLoginPlat(Integer loginPlat) {
		this.loginPlat = loginPlat;
	}
	public Integer getIsPlay() {
		return isPlay;
	}
	public void setIsPlay(Integer isPlay) {
		this.isPlay = isPlay;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Integer getOrderId() {
		return orderId;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	
    
}
