package com.cofc.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.ProjectTags;

public interface ProjectTagsService {
	public List<ProjectTags> findProjectTagsByOrder(@Param("orderId")Integer orderId,@Param("rowsId")Integer rowsId,@Param("pageSize")Integer pageSize);

	public void addNewProjectTag(ProjectTags pjt);

	public ProjectTags getTagByName(String tagName);

	public List<ProjectTags> findTagsByIds(@Param("ids")List<String> ids);

}
