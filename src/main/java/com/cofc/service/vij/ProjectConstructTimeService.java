package com.cofc.service.vij;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.vij.ProjectConstructTime;

public interface ProjectConstructTimeService {
	public void addConstructTime(ProjectConstructTime time);
	public void updateConstuctTime(ProjectConstructTime time);
	public List<ProjectConstructTime> getInfoByProjectId(@Param("projectId")Integer projectId);
}
