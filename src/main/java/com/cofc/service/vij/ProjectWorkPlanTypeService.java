package com.cofc.service.vij;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.vij.ProjectWorkPlanType;

public interface ProjectWorkPlanTypeService {
	public void addPlanType(ProjectWorkPlanType type);
	public void updatePlanType(ProjectWorkPlanType type);
	public void delPlanType(@Param("id")Integer id);
	public int getPlanTypeCount(@Param("type")ProjectWorkPlanType type);
	public List<ProjectWorkPlanType> getPlanTypeList(@Param("type")ProjectWorkPlanType type,
			@Param("page")Integer page,@Param("limit")Integer limit);
}
