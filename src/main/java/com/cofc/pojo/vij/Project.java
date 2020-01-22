package com.cofc.pojo.vij;

import java.util.Date;
public class Project {
	private Integer id;
	private Integer appId;
	private String name;
	private Integer ownerId;
	private Integer createUserId;
	private Integer waiterId;//顾问id
	private Integer status;
	private String detail;
	private Date createTime;
	private Date lastTime;
	private String projectNo;
	private Integer designerId;
	private Integer isPay;
	private String userName;
	private String realName;
	private String phone;
	private Adviser adviser; //顾问
	private Adviser designer;//设计师
	private DecorateForecast homeInfo;//量房信息
	private Double depositMoney; //设置定金
	
	//量房表
	private String lfUserName;	//量房姓名
	private String mobilePhoneNo;//联系方式
	
	public String getLfUserName() {
		return lfUserName;
	}
	public void setLfUserName(String lfUserName) {
		this.lfUserName = lfUserName;
	}
	public String getMobilePhoneNo() {
		return mobilePhoneNo;
	}
	public void setMobilePhoneNo(String mobilePhoneNo) {
		this.mobilePhoneNo = mobilePhoneNo;
	}
	public Double getDepositMoney() {
		return depositMoney;
	}
	public void setDepositMoney(Double depositMoney) {
		this.depositMoney = depositMoney;
	}
	public String getProjectNo() {
		return projectNo;
	}
	public void setProjectNo(String projectNo) {
		this.projectNo = projectNo;
	}
	public Integer getDesignerId() {
		return designerId;
	}
	public void setDesignerId(Integer designerId) {
		this.designerId = designerId;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getAppId() {
		return appId;
	}
	public void setAppId(Integer appId) {
		this.appId = appId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(Integer ownerId) {
		this.ownerId = ownerId;
	}
	public Integer getCreateUserId() {
		return createUserId;
	}
	public void setCreateUserId(Integer createUserId) {
		this.createUserId = createUserId;
	}
	public Integer getWaiterId() {
		return waiterId;
	}
	public void setWaiterId(Integer waiterId) {
		this.waiterId = waiterId;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getLastTime() {
		return lastTime;
	}
	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Integer getIsPay() {
		return isPay;
	}
	public void setIsPay(Integer isPay) {
		this.isPay = isPay;
	}
	public Adviser getAdviser() {
		return adviser;
	}
	public void setAdviser(Adviser adviser) {
		this.adviser = adviser;
	}
	public Adviser getDesigner() {
		return designer;
	}
	public void setDesigner(Adviser designer) {
		this.designer = designer;
	}
	public DecorateForecast getHomeInfo() {
		return homeInfo;
	}
	public void setHomeInfo(DecorateForecast homeInfo) {
		this.homeInfo = homeInfo;
	}
}
