package com.cofc.service.vij;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.vij.GoodCommentPraise;

public interface GoodCommentPraiseService {
	public void addPraise(GoodCommentPraise praise);
	public void updatePraise(GoodCommentPraise praise);
	public GoodCommentPraise getPraise(@Param("praise")GoodCommentPraise praise);
}
