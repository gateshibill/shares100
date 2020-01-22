package com.cofc.service.vij;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.vij.GoodComment;

public interface GoodCommentService {
	public void addComment(GoodComment comment);
	public void updateComment(GoodComment comment);
	public void delComment(@Param("cid")Integer cid);
	public int getCommentCount(@Param("comment")GoodComment comment,@Param("status")Integer status);
	public List<GoodComment> getCommentList(@Param("comment")GoodComment comment,@Param("status")Integer status,
			@Param("page")Integer page,@Param("limit")Integer limit);
	public List<GoodComment> getChildList(@Param("parentId")Integer parentId);
}
