package com.cofc.pojo;

import java.util.Date;

public class ErweimaCode {
    private Integer codeId;
    private Integer deskId;
    private Integer loginPlat;
    private String codePath;
    private String path;
    private Integer useCount;
    private Integer downloadCount;
    private Integer createUserId;
    private Integer isEffect;
    private Date createTime;
	public Integer getCodeId() {
		return codeId;
	}
	public void setCodeId(Integer codeId) {
		this.codeId = codeId;
	}
	public Integer getDeskId() {
		return deskId;
	}
	public void setDeskId(Integer deskId) {
		this.deskId = deskId;
	}
	public Integer getLoginPlat() {
		return loginPlat;
	}
	public void setLoginPlat(Integer loginPlat) {
		this.loginPlat = loginPlat;
	}
	public String getCodePath() {
		return codePath;
	}
	public void setCodePath(String codePath) {
		this.codePath = codePath;
	}
	
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public Integer getUseCount() {
		return useCount;
	}
	public void setUseCount(Integer useCount) {
		this.useCount = useCount;
	}
	public Integer getDownloadCount() {
		return downloadCount;
	}
	public void setDownloadCount(Integer downloadCount) {
		this.downloadCount = downloadCount;
	}
	public Integer getCreateUserId() {
		return createUserId;
	}
	public void setCreateUserId(Integer createUserId) {
		this.createUserId = createUserId;
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
    
}
