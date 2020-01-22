package com.cofc.pojo;

import java.util.Date;

public class UserCommon {
	private Integer userId;
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
	private Double walletBalance;
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
	private String tokenCode;
	private String userPosition;
	private Integer introducer;
	private Integer isAgent;//是否代理：0：否，1：是
	private Double userToken; //用户token值
	private String userCodeUrl;//用户二维码
	private String country; //国家
	private String province;//省份
	private String city;//城市;
	private String bankCard;//银行卡
	private String userName;//用户账号
	private String password;//用户密码
	private Integer sysMes;//是否接受系统推送
	private String birthday;//生日
	private Integer rank; //积分排名
	private String qyUserId;//企业号用户id
	private String qyOpenId;//备用，暂时用不到
	private String personLabel;//个性标签
	private Integer isConcerned;// 用于查看用户信息是显示当前用户是否关注他
	private Integer concernedUserId;
	
	private int ticketCardCount; //可用优惠券数量
	
	//QQ 微信第三方登陆
	private String qqOpenId;
	private String wxOpenId;
	private Integer regSource;//注册来源
	private Integer userRole;//用户角色 
	
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
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

	public String getCardImage() {
		return cardImage;
	}

	public void setCardImage(String cardImage) {
		this.cardImage = cardImage;
	}

	public Double getWalletBalance() {
		return walletBalance;
	}

	public void setWalletBalance(Double walletBalance) {
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

	public Integer getIsConcerned() {
		return isConcerned;
	}

	public void setIsConcerned(Integer isConcerned) {
		this.isConcerned = isConcerned;
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

	public String getHeadCard() {
		return headCard;
	}

	public void setHeadCard(String headCard) {
		this.headCard = headCard;
	}

	public Integer getConcernedUserId() {
		return concernedUserId;
	}

	public void setConcernedUserId(Integer concernedUserId) {
		this.concernedUserId = concernedUserId;
	}

	public String getTokenCode() {
		return tokenCode;
	}

	public void setTokenCode(String tokenCode) {
		this.tokenCode = tokenCode;
	}

	@Override
	public String toString() {
		return "UserCommon [userId=" + userId + ", nickName=" + nickName + ", realName=" + realName + ", phone=" + phone
				+ ", headImage=" + headImage + ", email=" + email + ", userSex=" + userSex + ", compony=" + compony
				+ ", componyIntroduce=" + componyIntroduce + ", unionId=" + unionId + ", headCard=" + headCard
				+ ", cardImage=" + cardImage + ", walletBalance=" + walletBalance + ", createTime=" + createTime
				+ ", updateTime=" + updateTime + ", userLevel=" + userLevel + ", integral=" + integral + ", isManager="
				+ isManager + ", openId=" + openId + ", concernedCount=" + concernedCount + ", isMember=" + isMember
				+ ", userStatus=" + userStatus + ", loginPlat=" + loginPlat + ", accessTime=" + accessTime
				+ ", isRenzheng=" + isRenzheng + ", tokenCode=" + tokenCode + ", isConcerned=" + isConcerned
				+ ", concernedUserId=" + concernedUserId + "]";
	}

	public String getUserPosition() {
		return userPosition;
	}

	public void setUserPosition(String userPosition) {
		this.userPosition = userPosition;
	}

	public Integer getIntroducer() {
		return introducer;
	}

	public void setIntroducer(Integer introducer) {
		this.introducer = introducer;
	}

	public Integer getIsAgent() {
		return isAgent;
	}

	public void setIsAgent(Integer isAgent) {
		this.isAgent = isAgent;
	}

	public Double getUserToken() {
		return userToken;
	}

	public void setUserToken(Double userToken) {
		this.userToken = userToken;
	}

	public String getUserCodeUrl() {
		return userCodeUrl;
	}

	public void setUserCodeUrl(String userCodeUrl) {
		this.userCodeUrl = userCodeUrl;
	}
	
	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getQyUserId() {
		return qyUserId;
	}

	public void setQyUserId(String qyUserId) {
		this.qyUserId = qyUserId;
	}

	public String getQyOpenId() {
		return qyOpenId;
	}

	public void setQyOpenId(String qyOpenId) {
		this.qyOpenId = qyOpenId;
	}

	public String getBankCard() {
		return bankCard;
	}

	public void setBankCard(String bankCard) {
		this.bankCard = bankCard;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getSysMes() {
		return sysMes;
	}

	public void setSysMes(Integer sysMes) {
		this.sysMes = sysMes;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public Integer getRank() {
		return rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}

	public String getPersonLabel() {
		return personLabel;
	}

	public void setPersonLabel(String personLabel) {
		this.personLabel = personLabel;
	}

	public String getQqOpenId() {
		return qqOpenId;
	}

	public void setQqOpenId(String qqOpenId) {
		this.qqOpenId = qqOpenId;
	}

	public String getWxOpenId() {
		return wxOpenId;
	}

	public void setWxOpenId(String wxOpenId) {
		this.wxOpenId = wxOpenId;
	}

	public Integer getRegSource() {
		return regSource;
	}

	public void setRegSource(Integer regSource) {
		this.regSource = regSource;
	}

	public int getTicketCardCount() {
		return ticketCardCount;
	}

	public void setTicketCardCount(int ticketCardCount) {
		this.ticketCardCount = ticketCardCount;
	}

	public Integer getUserRole() {
		return userRole;
	}

	public void setUserRole(Integer userRole) {
		this.userRole = userRole;
	}
}
