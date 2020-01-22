package com.cofc.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.ActivityCommon;

public interface ActivityCommonService {

	public List<ActivityCommon> findActivityByCriteria(@Param("acyCommon") ActivityCommon acyCommon,
			@Param("rowsId") Integer rowsId, @Param("pageSize") Integer pageSize);

	public int createNewActivity(ActivityCommon acyCommon);

	public ActivityCommon getActivityById(Integer activityId);

	public int getCountActivity(@Param("act")ActivityCommon act,@Param("userKeyWords")String userKeyWords, @Param("startTime")String startTime, @Param("endTime")String endTime);

	public List<ActivityCommon> findActivityList(@Param("act")ActivityCommon act,@Param("userKeyWords")String userKeyWords, @Param("startTime")String startTime, @Param("endTime")String endTime, @Param("pageNo")int i,
			@Param("pageSize")Integer pageSize);

	public void updateActivityInfo(ActivityCommon currActivity);

	public ActivityCommon confirmSharedThisActivity(@Param("parentId")Integer parentId, @Param("userId")Integer userId);

	public void deleteActivity(@Param("activityId")Integer activityId);
	
	//区分应用
	public int getCountActivityByLoginPlat(@Param("loginPlatList")List<String> loginPlatList,@Param("act")ActivityCommon act,@Param("userKeyWords")String userKeyWords, @Param("startTime")String startTime, @Param("endTime")String endTime);

	public List<ActivityCommon> findActivityListByLoginPlat(@Param("loginPlatList")List<String> loginPlatList,@Param("act")ActivityCommon act,@Param("userKeyWords")String userKeyWords, @Param("startTime")String startTime, @Param("endTime")String endTime, @Param("pageNo")int i,
			@Param("pageSize")Integer pageSize);

}
