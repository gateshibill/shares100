package com.cofc.pojo.vij;

import java.util.Date;

/**
 * 
 * @author 46678
 * 家居设计
 *
 */
public class HomeSetup {
	private Integer id;
	private String city;
	private String phone;
	private String callName;
	private String homeType;
	private String homeTime;
	private Integer homeArea;
	private String homeAddress;
	private Date createTime;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getCallName() {
		return callName;
	}
	public void setCallName(String callName) {
		this.callName = callName;
	}
	public String getHomeType() {
		return homeType;
	}
	public void setHomeType(String homeType) {
		this.homeType = homeType;
	}
	public String getHomeTime() {
		return homeTime;
	}
	public void setHomeTime(String homeTime) {
		this.homeTime = homeTime;
	}
	public Integer getHomeArea() {
		return homeArea;
	}
	public void setHomeArea(Integer homeArea) {
		this.homeArea = homeArea;
	}
	public String getHomeAddress() {
		return homeAddress;
	}
	public void setHomeAddress(String homeAddress) {
		this.homeAddress = homeAddress;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	@Override
	public String toString() {
		return "HomeSetup [id=" + id + ", city=" + city + ", phone=" + phone + ", callName=" + callName + ", homeType="
				+ homeType + ", homeTime=" + homeTime + ", homeArea=" + homeArea + ", homeAddress=" + homeAddress
				+ ", createTime=" + createTime + "]";
	}
	
}
