package com.cofc.service.vij;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.vij.ProjectComment;

public interface ProjectCommentService {
	public void addComment(ProjectComment comment);
	public void updateComment(ProjectComment comment);
	public void delComment(@Param("commentId")Integer commentId);
	public int getCommentCount(@Param("comment")ProjectComment comment);
	public List<ProjectComment> getCommentList(@Param("comment")ProjectComment comment,
			Integer page,Integer limit);
	public ProjectComment getProjectCommentByid(@Param("projectId")Integer projectId);
}
