package com.cofc.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.ShareActivity;

public interface ShareActivityService {

	void addShareActivity(ShareActivity activity);

	ShareActivity getShareActivityById(Integer activityId);

	List<ShareActivity> getShareActivityList(@Param("userId")Integer userId, @Param("pageNo")int pageNo, @Param("pageSize")Integer pageSize);

	int getShareActivityCount(@Param("userId")Integer publisherId);

	void updateShareActivity(ShareActivity activity);

	void deleteShareActivity(@Param("activityId")Integer activityId);

	ShareActivity getShareActivityName(@Param("publisherId")Integer publisherId,@Param("activityDesc") String activityDesc);

	List<ShareActivity> getActivityIds(@Param("ids")List<String> asList);

}
