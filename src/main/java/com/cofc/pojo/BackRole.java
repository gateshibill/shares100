package com.cofc.pojo;

import java.util.Date;

public class BackRole {
	private Integer roleId;
	private String roleName;
	private String rolePermission;
	private Date createTime;
	private Integer createUser;
	
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getRolePermission() {
		return rolePermission;
	}
	public void setRolePermission(String rolePermission) {
		this.rolePermission = rolePermission;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Integer getCreateUser() {
		return createUser;
	}
	public void setCreateUser(Integer createUser) {
		this.createUser = createUser;
	}
}
