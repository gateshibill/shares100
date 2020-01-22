package com.cofc.service.vij;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.vij.ProjectLog;

public interface ProjectLogService {
	public void addProjectLog(ProjectLog log);
	public void updateProjectLog(ProjectLog log);
	public void delProjectLog(@Param("logId")Integer logId);
	public int getProjectLogCount(@Param("log")ProjectLog log);
	public List<ProjectLog> getProjectLogList(@Param("log")ProjectLog log,
			@Param("page")Integer page,@Param("limit")Integer limit);
	public ProjectLog getProjectLogByid(@Param("projectId")Integer projectId);
}
