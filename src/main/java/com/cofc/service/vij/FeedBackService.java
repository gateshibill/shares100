package com.cofc.service.vij;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.vij.FeedBack;

public interface FeedBackService {
	public void addFeedBack(FeedBack back);
	public void updateFeedBack(FeedBack back);
	public void delFeebBack(@Param("id")Integer id);
	public FeedBack getInfoById(@Param("id")Integer id);
	public int getFeedBackCount(@Param("back")FeedBack back);
	public List<FeedBack> getFeedBackList(@Param("back")FeedBack back,
			@Param("page")Integer page,@Param("limit")Integer limit);
}
