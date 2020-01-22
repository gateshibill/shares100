package com.cofc.pojo.aida;

import java.util.Date;

public class WorkWeixin {
	private Integer id;
	private Integer workId;
	private String workName;//企业号名称
	private String qyId;//企业id
	private String appName;//应用名称
	private String agentId;//应用id
	private String appSecret;//应用密钥
	private Date createTime;//应用创建时间
	private Integer isEffect;//应用是否有效
	private Integer xcxAppId;//对应小程序的Id
	
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getWorkId() {
		return workId;
	}
	public void setWorkId(Integer workId) {
		this.workId = workId;
	}
	public String getWorkName() {
		return workName;
	}
	public void setWorkName(String workName) {
		this.workName = workName;
	}
	public String getQyId() {
		return qyId;
	}
	public void setQyId(String qyId) {
		this.qyId = qyId;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public String getAgentId() {
		return agentId;
	}
	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}
	public String getAppSecret() {
		return appSecret;
	}
	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
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
	public Integer getXcxAppId() {
		return xcxAppId;
	}
	public void setXcxAppId(Integer xcxAppId) {
		this.xcxAppId = xcxAppId;
	}
}
