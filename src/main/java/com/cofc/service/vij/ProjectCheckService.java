package com.cofc.service.vij;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.vij.ProjectCheck;

public interface ProjectCheckService {

	void addProjectCheck(ProjectCheck pjCheck);
	
	void updateProjectCheck(ProjectCheck pjCheck);
	
	void deleteProjectCheck(@Param("checkId")Integer checkId);
	
	ProjectCheck getinfoByid(@Param("checkId")Integer checkId);
	
	ProjectCheck getProjectCheckByid(@Param("projectId")Integer projectId);
	
	int getProjectCheckCount(@Param("pjCheck")ProjectCheck pjCheck);
	
	List<ProjectCheck> queryProjectCheckList(@Param("pjCheck")ProjectCheck pjCheck,
			@Param("page")Integer page,@Param("limit")Integer limit);
}
