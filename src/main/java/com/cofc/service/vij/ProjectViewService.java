package com.cofc.service.vij;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import com.cofc.pojo.vij.ProjectView;

public interface ProjectViewService {
	public void addProjecView(ProjectView view);
	public void updateProject(ProjectView view);
	public void delProjectView(@Param("viewId")Integer viewId);
	public int getProjectViewCount(@Param("view")ProjectView view);
	public List<ProjectView> getProjectViewList(@Param("view")ProjectView view,
			@Param("page")Integer page,@Param("limit")Integer limit);
	public void updateProjectView(ProjectView view);
	public ProjectView getProjectViewById(@Param("projectId")Integer projectId);
	
}
