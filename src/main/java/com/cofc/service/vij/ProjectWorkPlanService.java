package com.cofc.service.vij;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.vij.ProjectWorkPlan;

public interface ProjectWorkPlanService {
	public void addWorkPlan(ProjectWorkPlan pWorkPlan);
	public void updateWorkPlan(ProjectWorkPlan pWorkPla);
	public void delWorkPlan(@Param("planId")Integer planId);
	public void delWorkPlanByType(@Param("planTypeId")Integer planTypeId);
	public ProjectWorkPlan getInfoById(@Param("planId")Integer planId);
	public int getWorkPlanCount(@Param("pWorkPla")ProjectWorkPlan pWorkPla);
	public List<ProjectWorkPlan> getWorkPlanList(@Param("pWorkPla")ProjectWorkPlan pWorkPla,@Param("page")Integer page,@Param("limit")Integer limit);
	public int getWorkPlanCountByProId(@Param("pWorkPla")ProjectWorkPlan pWorkPla);
	public List<ProjectWorkPlan> getWorkPlanListByType(@Param("planTypeId")Integer planTypeId);
	public List<ProjectWorkPlan> getProjectWorkPlayList(@Param("planTypeId")Integer planTypeId,
			@Param("checkStatus")Integer checkStatus);
	int projectWorkPlayAll(@Param("checkStatus")Integer checkStatus,@Param("planTypeId")Integer planTypeId);
}
