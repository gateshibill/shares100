package com.cofc.pojo;

import java.util.Date;

public class GroupUsers {
	private Integer tbId;
	private Integer groupId;
	private Integer userId;
	private Date joinTime;
	private Integer isCreater;
	private Integer isExamined;
	private String nickName;
	private String realName;
	private String phone;
	private String headImage;
	private String email;
	private Integer userSex;
	private String compony;
	private String componyIntroduce;
	private String unionId;
	private String headCard;
	private String cardImage;
	private double walletBalance;
	private Date createTime;
	private Date updateTime;
	private Integer userLevel;
	private Integer integral;
	private Integer isManager;
	private String openId;
	private Integer concernedCount;
	private Integer isMember;
	private Integer userStatus;
	private Integer loginPlat;
	private Date accessTime;
	private Integer isRenzheng;
	private Integer giveVoucher;
	private String address;//地址：临时
	private Integer money;//充值金额：临时

	public Integer getTbId() {
		return tbId;
	}

	public void setTbId(Integer tbId) {
		this.tbId = tbId;
	}

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Date getJoinTime() {
		return joinTime;
	}

	public void setJoinTime(Date joinTime) {
		this.joinTime = joinTime;
	}

	public Integer getIsCreater() {
		return isCreater;
	}

	public void setIsCreater(Integer isCreater) {
		this.isCreater = isCreater;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
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

	public String getHeadImage() {
		return headImage;
	}

	public void setHeadImage(String headImage) {
		this.headImage = headImage;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getUserSex() {
		return userSex;
	}

	public void setUserSex(Integer userSex) {
		this.userSex = userSex;
	}

	public String getCompony() {
		return compony;
	}

	public void setCompony(String compony) {
		this.compony = compony;
	}

	public String getComponyIntroduce() {
		return componyIntroduce;
	}

	public void setComponyIntroduce(String componyIntroduce) {
		this.componyIntroduce = componyIntroduce;
	}

	public String getUnionId() {
		return unionId;
	}

	public void setUnionId(String unionId) {
		this.unionId = unionId;
	}

	public String getHeadCard() {
		return headCard;
	}

	public void setHeadCard(String headCard) {
		this.headCard = headCard;
	}

	public String getCardImage() {
		return cardImage;
	}

	public void setCardImage(String cardImage) {
		this.cardImage = cardImage;
	}

	public double getWalletBalance() {
		return walletBalance;
	}

	public void setWalletBalance(double walletBalance) {
		this.walletBalance = walletBalance;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getUserLevel() {
		return userLevel;
	}

	public void setUserLevel(Integer userLevel) {
		this.userLevel = userLevel;
	}

	public Integer getIntegral() {
		return integral;
	}

	public void setIntegral(Integer integral) {
		this.integral = integral;
	}

	public Integer getIsManager() {
		return isManager;
	}

	public void setIsManager(Integer isManager) {
		this.isManager = isManager;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public Integer getConcernedCount() {
		return concernedCount;
	}

	public void setConcernedCount(Integer concernedCount) {
		this.concernedCount = concernedCount;
	}

	public Integer getIsMember() {
		return isMember;
	}

	public void setIsMember(Integer isMember) {
		this.isMember = isMember;
	}

	public Integer getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(Integer userStatus) {
		this.userStatus = userStatus;
	}

	public Integer getLoginPlat() {
		return loginPlat;
	}

	public void setLoginPlat(Integer loginPlat) {
		this.loginPlat = loginPlat;
	}

	public Date getAccessTime() {
		return accessTime;
	}

	public void setAccessTime(Date accessTime) {
		this.accessTime = accessTime;
	}

	public Integer getIsRenzheng() {
		return isRenzheng;
	}

	public void setIsRenzheng(Integer isRenzheng) {
		this.isRenzheng = isRenzheng;
	}

	public Integer getIsExamined() {
		return isExamined;
	}

	public void setIsExamined(Integer isExamined) {
		this.isExamined = isExamined;
	}

	@Override
	public String toString() {
		return "GroupUsers [tbId=" + tbId + ", groupId=" + groupId + ", userId=" + userId + ", joinTime=" + joinTime
				+ ", isCreater=" + isCreater + ", isExamined=" + isExamined + ", nickName=" + nickName + ", realName="
				+ realName + ", phone=" + phone + ", headImage=" + headImage + ", email=" + email + ", userSex="
				+ userSex + ", compony=" + compony + ", componyIntroduce=" + componyIntroduce + ", unionId=" + unionId
				+ ", headCard=" + headCard + ", cardImage=" + cardImage + ", walletBalance=" + walletBalance
				+ ", createTime=" + createTime + ", updateTime=" + updateTime + ", userLevel=" + userLevel
				+ ", integral=" + integral + ", isManager=" + isManager + ", openId=" + openId + ", concernedCount="
				+ concernedCount + ", isMember=" + isMember + ", userStatus=" + userStatus + ", loginPlat=" + loginPlat
				+ ", accessTime=" + accessTime + ", isRenzheng=" + isRenzheng + "]";
	}

	public Integer getGiveVoucher() {
		return giveVoucher;
	}

	public void setGiveVoucher(Integer giveVoucher) {
		this.giveVoucher = giveVoucher;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getMoney() {
		return money;
	}

	public void setMoney(Integer money) {
		this.money = money;
	}
	
}
