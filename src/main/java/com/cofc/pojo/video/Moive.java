package com.cofc.pojo.video;

import java.util.Date;

public class Moive {
   private int id;
   private int appId;
   private int assetId;
   private int columnId;//海报，多个用‘;’隔开
   private int isOnline;
   private int order;//排序
   public int getOrder() {
	return order;
}

public void setOrder(int order) {
	this.order = order;
}

private int isHot;
   private int isRecommend;
   private int isAdTurns;//是否加入轮播广告
   
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

public int getAssetId() {
	return assetId;
}

public void setAssetId(int assetId) {
	this.assetId = assetId;
}

public int getColumnId() {
	return columnId;
}

public void setColumnId(int columnId) {
	this.columnId = columnId;
}

public int getIsOnline() {
	return isOnline;
}

public void setIsOnline(int isOnline) {
	this.isOnline = isOnline;
}

public int getIsHot() {
	return isHot;
}

public void setIsHot(int isHot) {
	this.isHot = isHot;
}

public int getIsRecommend() {
	return isRecommend;
}

public void setIsRecommend(int isRecommend) {
	this.isRecommend = isRecommend;
}

public int getIsAdTurns() {
	return isAdTurns;
}

public void setIsAdTurns(int isAdTurns) {
	this.isAdTurns = isAdTurns;
}

public Date getCreateTime() {
	return createTime;
}

public void setCreateTime(Date createTime) {
	this.createTime = createTime;
}

}
