package com.cofc.pojo;

import java.util.Date;

public class ApplicationCode {
	
	private Integer codeId;
	private Integer loginPlat;
	private Integer loginSubplat;
	private String downloadUrl;
	private Date createTime;
	private String path;
	
	public Integer getCodeId() {
		return codeId;
	}
	public void setCodeId(Integer codeId) {
		this.codeId = codeId;
	}
	public Integer getLoginPlat() {
		return loginPlat;
	}
	public void setLoginPlat(Integer loginPlat) {
		this.loginPlat = loginPlat;
	}
	public Integer getLoginSubplat() {
		return loginSubplat;
	}
	public void setLoginSubplat(Integer loginSubplat) {
		this.loginSubplat = loginSubplat;
	}
	public String getDownloadUrl() {
		return downloadUrl;
	}
	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
}
