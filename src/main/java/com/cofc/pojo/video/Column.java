package com.cofc.pojo.video;

import java.util.Date;

public class Column {
   private int id;
   private int appId;
   private String name;
   private int isOnline;
   private int order;
   private int type;
   private Date createTime;

public int getId() {
	return id;
}

public void setId(int id) {
	this.id = id;
}

public int getAppId() {
	return appId;
}

public void setAppId(int appId) {
	this.appId = appId;
}

public String getName() {
	return name;
}

public int getType() {
	return type;
}

public void setType(int type) {
	this.type = type;
}

public void setName(String name) {
	this.name = name;
}

public int getIsOnline() {
	return isOnline;
}

public int getOrder() {
	return order;
}

public void setOrder(int order) {
	this.order = order;
}

public void setIsOnline(int isOnline) {
	this.isOnline = isOnline;
}

public Date getCreateTime() {
	return createTime;
}

public void setCreateTime(Date createTime) {
	this.createTime = createTime;
}

}
