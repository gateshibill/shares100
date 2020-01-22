package com.cofc.pojo;

import java.util.Date;

public class PushMessage {
   private Integer id;
   private String templateId;
   private Integer loginPlat;
   private Integer isEffect;
   private Integer isRemove;
   private Integer tempType;
   private Date createTime;
public Integer getId() {
	return id;
}
public void setId(Integer id) {
	this.id = id;
}
public String getTemplateId() {
	return templateId;
}
public void setTemplateId(String templateId) {
	this.templateId = templateId;
}
public Integer getLoginPlat() {
	return loginPlat;
}
public void setLoginPlat(Integer loginPlat) {
	this.loginPlat = loginPlat;
}
public Integer getIsEffect() {
	return isEffect;
}
public void setIsEffect(Integer isEffect) {
	this.isEffect = isEffect;
}
public Integer getIsRemove() {
	return isRemove;
}
public void setIsRemove(Integer isRemove) {
	this.isRemove = isRemove;
}
public Integer getTempType() {
	return tempType;
}
public void setTempType(Integer tempType) {
	this.tempType = tempType;
}
public Date getCreateTime() {
	return createTime;
}
public void setCreateTime(Date createTime) {
	this.createTime = createTime;
}
   
   
}
