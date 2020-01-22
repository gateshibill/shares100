package com.cofc.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.BackRole;

public interface BackRoleService {

	List<BackRole> getBackRoleRoleIds(@Param("ids")List roleIds);

	BackRole getRoleMenuIds(@Param("roleId")Integer roleId);

	List<BackRole> findBackRoleList();

	int getBackRoleCount(Map<String, Object> map);

	List<BackRole> showBackRoleList(Map<String, Object> map);

	BackRole getRoleStringById(@Param("roleId")String roleId);

	BackRole getBackRoleName(@Param("roleName")String roleName);

	void updateBackRole(BackRole backRole);

	void deleteRoleId(@Param("roleId")Integer roleId);

	void addBackRole(BackRole role);

}
