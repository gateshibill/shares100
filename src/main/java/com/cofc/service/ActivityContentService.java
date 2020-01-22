package com.cofc.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.ActivityContent;

public interface ActivityContentService {

	List<ActivityContent> getActivityContentList(@Param("pageNo")int pageNo, @Param("pageSize")Integer pageSize, @Param("keyword")Integer keyword);

	ActivityContent getActivityContentName(@Param("contentDesc")String activityDesc);

	void updateActivityContent(ActivityContent content);

	void addActivityContent(ActivityContent content);

	int getActivityContentCount(@Param("keyword")Integer keyword);

	void deleteActivityContent(@Param("contentId")Integer contentId);

	ActivityContent getActivityContentById(@Param("contentId")Integer contentId);

}
