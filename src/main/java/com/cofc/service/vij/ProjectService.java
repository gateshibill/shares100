package com.cofc.service.vij;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.vij.Project;

public interface ProjectService {
	//整个项目的添加
	void addProject(Project vProject);
	//获取详情
	Project getInfoById(@Param("id")Integer id);
	//获取总数
	int getProjectCount(@Param("vProject")Project vProject);
	//整个项目的查找
	List<Project> queryProject(@Param("vProject")Project vProject,@Param("page")Integer page,
			@Param("limit")Integer limit);
	//整个项目的修改
	void updateProject(Project vProject);
	//整个项目的删除
	int delectproject(@Param("id")Integer id);
	
	int checkIsAlready(@Param("name")String name,@Param("status")Integer status, @Param("id")Integer id);
	
	Project getProjectByUserId(@Param("userId")Integer userId,@Param("appId")Integer appId,@Param("status")Integer status);
}
