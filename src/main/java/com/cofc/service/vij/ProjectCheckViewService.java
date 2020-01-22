package com.cofc.service.vij;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.vij.ProjectCheckView;

public interface ProjectCheckViewService {
	public void addCheckView(ProjectCheckView view);
	public List<ProjectCheckView> getProjectCheckViewList(@Param("pCheckView")ProjectCheckView pCheckView,
			@Param("page")Integer page,@Param("limit")Integer limit);
	public int getProjectCheckViewCount(@Param("pCheckView")ProjectCheckView pCheckView);
	public void uploadProjectCheckView(@Param("pCheckView")ProjectCheckView pCheckView);
	public ProjectCheckView getProjectCheckViewById(@Param("smallPlanId")Integer smallPlanId);
}
