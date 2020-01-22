package com.cofc.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.ApplicationManage;

public interface ApplicationManageService {

	public int addApplicationManage(ApplicationManage manage);

	public List<ApplicationManage> getManageLoginPlatList(@Param("loginPlat")Integer appId, @Param("page")int i, @Param("limit")Integer limit);

	public int getManageLoginPlatCount(@Param("loginPlat")Integer loginPlat);

	public ApplicationManage getIsManageUser(@Param("manageUser")Integer manageUser, @Param("loginPlat")Integer loginPlat, @Param("status")Integer status);

	public void updateManageStatus(ApplicationManage app);

	public void updateApplicationManage(ApplicationManage manage);

	public ApplicationManage getManageById(@Param("manageId")Integer manageId);

}
