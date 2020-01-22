package com.cofc.service;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.UserActivity;

public interface UserActivityService {
	public void UserJoinActivity(UserActivity ua);

	public UserActivity confirmLeftCountAndacstatus(@Param("userId") Integer userId,
			@Param("activityId") Integer activityId);

	public void updateUserActivityInfo(UserActivity usac);
}
