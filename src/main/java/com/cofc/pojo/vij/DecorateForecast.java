package com.cofc.pojo.vij;

import java.util.Date;

public class DecorateForecast {
	private Integer id;  
	private String cityCode;		//城市编码
	private String cityAreas;		//城市
	private Integer buildingArea;		//房屋面积
	private String housingTypes;	//房屋类型
	private String houseDoorModel;	//房屋户型
	private String mobilePhoneNo;	//手机号码
	private Integer grossPrice;			//总价格
	private String housingSituation;	//房屋现状
	private Date decorateTime;			//装修时间
	private String villageName;			//小区名
	private Integer projectId;//项目id,外键关联项目id
	private Integer isDeal;//是否处理
	private Integer source;//来源
	private String lfUserName;//名字
	private String homeNumber;//地址
	private String homeImages;	//户型图
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCityCode() {
		return cityCode;
	}
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	public String getCityAreas() {
		return cityAreas;
	}
	public void setCityAreas(String cityAreas) {
		this.cityAreas = cityAreas;
	}
	public Integer getBuildingArea() {
		return buildingArea;
	}
	public void setBuildingArea(Integer buildingArea) {
		this.buildingArea = buildingArea;
	}
	public String getHousingTypes() {
		return housingTypes;
	}
	public void setHousingTypes(String housingTypes) {
		this.housingTypes = housingTypes;
	}
	public String getHouseDoorModel() {
		return houseDoorModel;
	}
	public void setHouseDoorModel(String houseDoorModel) {
		this.houseDoorModel = houseDoorModel;
	}
	public String getMobilePhoneNo() {
		return mobilePhoneNo;
	}
	public void setMobilePhoneNo(String mobilePhoneNo) {
		this.mobilePhoneNo = mobilePhoneNo;
	}
	public Integer getGrossPrice() {
		return grossPrice;
	}
	public void setGrossPrice(Integer grossPrice) {
		this.grossPrice = grossPrice;
	}
	public String getHousingSituation() {
		return housingSituation;
	}
	public void setHousingSituation(String housingSituation) {
		this.housingSituation = housingSituation;
	}
	public Date getDecorateTime() {
		return decorateTime;
	}
	public void setDecorateTime(Date decorateTime) {
		this.decorateTime = decorateTime;
	}
	public String getVillageName() {
		return villageName;
	}
	public void setVillageName(String villageName) {
		this.villageName = villageName;
	}
	public Integer getProjectId() {
		return projectId;
	}
	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}
	public Integer getIsDeal() {
		return isDeal;
	}
	public void setIsDeal(Integer isDeal) {
		this.isDeal = isDeal;
	}
	public Integer getSource() {
		return source;
	}
	public void setSource(Integer source) {
		this.source = source;
	}
	public String getLfUserName() {
		return lfUserName;
	}
	public void setLfUserName(String lfUserName) {
		this.lfUserName = lfUserName;
	}
	public String getHomeNumber() {
		return homeNumber;
	}
	public void setHomeNumber(String homeNumber) {
		this.homeNumber = homeNumber;
	}
	public String getHomeImages() {
		return homeImages;
	}
	public void setHomeImages(String homeImages) {
		this.homeImages = homeImages;
	}
	
}
