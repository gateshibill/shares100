package com.cofc.service.vij;



import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.vij.ProjectWorkProcess;

public interface ProjectWorkProcessService {

	public void addWorkProcess(ProjectWorkProcess pWork);
	public void updateWorkProcess(ProjectWorkProcess pWork);
	public void delWorkProcess(@Param("workId")Integer workId);
	public List<ProjectWorkProcess> getWorkProcessList(@Param("work")ProjectWorkProcess work); 
	public ProjectWorkProcess getConstructionById(@Param("workId")Integer workId);
	public int getProjectWorkProcessCount(@Param("work")ProjectWorkProcess work);
	
}
