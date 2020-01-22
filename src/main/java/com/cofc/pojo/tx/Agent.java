package com.cofc.pojo.tx;

import java.util.Date;

public class Agent {
	private Integer agentId;
	private String agentName;
	private String agentCode;
	private String authCode;
	private Integer isEffect;
	private Date createTime;
	private String agentNotifyUrl;
	private String agentIP;
	private String agentDomain;
	private Integer maxQuantity;
	public Integer getAgentId() {
		return agentId;
	}
	public void setAgentId(Integer agentId) {
		this.agentId = agentId;
	}
	public String getAgentName() {
		return agentName;
	}
	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}
	public String getAgentCode() {
		return agentCode;
	}
	public void setAgentCode(String agentCode) {
		this.agentCode = agentCode;
	}
	public String getAuthCode() {
		return authCode;
	}
	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}
	public Integer getIsEffect() {
		return isEffect;
	}
	public void setIsEffect(Integer isEffect) {
		this.isEffect = isEffect;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getAgentNotifyUrl() {
		return agentNotifyUrl;
	}
	public void setAgentNotifyUrl(String agentNotifyUrl) {
		this.agentNotifyUrl = agentNotifyUrl;
	}
	public String getAgentIP() {
		return agentIP;
	}
	public void setAgentIP(String agentIP) {
		this.agentIP = agentIP;
	}
	public String getAgentDomain() {
		return agentDomain;
	}
	public void setAgentDomain(String agentDomain) {
		this.agentDomain = agentDomain;
	}
	public Integer getMaxQuantity() {
		return maxQuantity;
	}
	public void setMaxQuantity(Integer maxQuantity) {
		this.maxQuantity = maxQuantity;
	}
}
