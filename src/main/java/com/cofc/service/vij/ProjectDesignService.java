package com.cofc.service.vij;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.vij.ProjectDesign;

public interface ProjectDesignService {

	public void addOfferDesign(ProjectDesign designId);
	public void updateOfferDesign(ProjectDesign designId);
	public void delOfferDesign(@Param("designId")Integer designId);
	public List<ProjectDesign> getDesignByProjectId(@Param("projectId")Integer projectId);
	public ProjectDesign getOfferDesignById(@Param("designId")Integer designId);
	public int getDesignCountByProjectId(@Param("projectId")Integer projectId,@Param("designType")Integer designType);
	public ProjectDesign getDesignByProjectIdType(@Param("projectId")Integer projectId,@Param("designType")Integer designType);
	
}

