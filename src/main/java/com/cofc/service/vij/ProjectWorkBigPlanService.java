package com.cofc.service.vij;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.vij.ProjectWorkBigPlan;

public interface ProjectWorkBigPlanService {
	public void addBigPlan(ProjectWorkBigPlan bigPlan);
	public void updateBigPlan(ProjectWorkBigPlan bigPlan);
	public void delBigPlan(@Param("id")Integer id);
	public ProjectWorkBigPlan getInfoById(@Param("id")Integer id);
	public int getBigPlanCount(@Param("big")ProjectWorkBigPlan big);
	public List<ProjectWorkBigPlan> getBigPlanList(@Param("big")ProjectWorkBigPlan big,
			@Param("page")Integer page,@Param("limit")Integer limit);
	public int getProjectWorkBigPlayCount(@Param("isFinish")Integer isFinish,@Param("id")Integer id);
	
}
