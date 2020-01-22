package com.cofc.service.vij;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.vij.ProjectCommentTag;

public interface ProjectCommentTagService {
	public void addCommentTag(ProjectCommentTag tag);
	public void updateCommentTag(ProjectCommentTag tag);
	public void delCommentTaq(@Param("tagId")Integer tagId);
	public ProjectCommentTag getInfoById(@Param("tagId")Integer tagId);
	public int getCommentTagCount(@Param("tag")ProjectCommentTag tag);
	public List<ProjectCommentTag> getCommentTagList(@Param("tag")ProjectCommentTag tag,
			@Param("page")Integer page,@Param("limit")Integer limit);
	public int checkIsAlready(@Param("tagName")String tagName,@Param("tagId")Integer tagId,@Param("loginPlat")Integer loginPlat);
}
