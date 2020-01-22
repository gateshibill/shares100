package com.cofc.pojo;

import java.util.Date;

public class BackUser {
	private Integer userId;
	private String userName;
	private String password;
	private Date createTime;
	private String nickName;
	private String realName;
	private String headImage;
	//增量开发
	private Integer isEffect;
	private String loginPlat;
	
	//临时字段
	private String roleName;
	
	
	private UserRole userRole;
	private BackRole backRole;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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

	public String getHeadImage() {
		return headImage;
	}

	public void setHeadImage(String headImage) {
		this.headImage = headImage;
	}

	@Override
	public String toString() {
		return "BackUser [userId=" + userId + ", userName=" + userName + ", password=" + password + ", createTime="
				+ createTime + ", nickName=" + nickName + ", realName=" + realName + ", headImage=" + headImage + "]";
	}

	public UserRole getUserRole() {
		return userRole;
	}

	public void setUserRole(UserRole userRole) {
		this.userRole = userRole;
	}

	public BackRole getBackRole() {
		return backRole;
	}

	public void setBackRole(BackRole backRole) {
		this.backRole = backRole;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public Integer getIsEffect() {
		return isEffect;
	}

	public void setIsEffect(Integer isEffect) {
		this.isEffect = isEffect;
	}

	public String getLoginPlat() {
		return loginPlat;
	}

	public void setLoginPlat(String loginPlat) {
		this.loginPlat = loginPlat;
	}

	
	
}
