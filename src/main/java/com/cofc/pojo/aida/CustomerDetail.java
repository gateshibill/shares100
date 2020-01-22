package com.cofc.pojo.aida;

import java.util.Date;

public class CustomerDetail {
	private int id;
	private int appId;
	private Integer userId;//是对应的销售ID
	private int salesPersonId;
	private String source="扫名片";//来源
	private String name;
	private String nickName;
	private Integer sex;
	private String phone;
	private String email;
	private String company;
	private String position;//职位
	private String address;
	private String birthday;
	private Integer isBlock;//是否屏蔽消息，0为正常，1为屏蔽；
	private String note;//备注
	private Date createTime;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	public int getSalesPersonId() {
		return salesPersonId;
	}
	public void setSalesPersonId(int salesPersonId) {
		this.salesPersonId = salesPersonId;
	}
	public int getAppId() {
		return appId;
	}
	public void setAppId(int appId) {
		this.appId = appId;
	}

	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public Integer getSex() {
		return sex;
	}
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}

	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public Integer getIsBlock() {
		return isBlock;
	}
	public void setIsBlock(Integer isBlock) {
		this.isBlock = isBlock;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	

}
