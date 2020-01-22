package com.cofc.pojo.aida;

import java.util.Date;

//客户跟进记录
public class OrderFollow {
  private int id;
  private int appId;
  private int orderNo;
  //用来做查表关联返回给前端的
  private String userName;
  private String headImage;
  private int salesPersonId;
  private int userId;
  private String content;//
  private String location;//地点
  private int status;
  private Date createTime=new Date();
  private String note;
	

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






public String getLocation() {
	return location;
}



public String getUserName() {
	return userName;
}



public void setUserName(String userName) {
	this.userName = userName;
}



public String getHeadImage() {
	return headImage;
}



public void setHeadImage(String headImage) {
	this.headImage = headImage;
}



public void setLocation(String location) {
	this.location = location;
}



public int getOrderNo() {
	return orderNo;
}



public void setOrderNo(int orderNo) {
	this.orderNo = orderNo;
}



public int getSalesPersonId() {
	return salesPersonId;
}



public void setSalesPersonId(int salesPersonId) {
	this.salesPersonId = salesPersonId;
}



public int getUserId() {
	return userId;
}



public void setUserId(int userId) {
	this.userId = userId;
}

public String getContent() {
	return content;
}



public void setContent(String content) {
	this.content = content;
}



public int getStatus() {
	return status;
}



public void setStatus(int status) {
	this.status = status;
}



public Date getCreateTime() {
	return createTime;
}



public void setCreateTime(Date createTime) {
	this.createTime = createTime;
}



public String getNote() {
	return note;
}



public void setNote(String note) {
	this.note = note;
}



	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
