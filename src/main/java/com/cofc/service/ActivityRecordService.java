package com.cofc.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.ActivityRecord;

public interface ActivityRecordService {

	void addActivityRecord(ActivityRecord record);

	List<ActivityRecord> getActivityIdList(@Param("userId")Integer userId, @Param("pageNo")int pageNo,@Param("pageSize") Integer pageSize);

	ActivityRecord getActivityRecordById(@Param("userId")Integer userId);

	ActivityRecord confirmSeeMyShared(@Param("publisherId")Integer publisherId, @Param("userId")Integer userId);

	public int getCountOfRecord(@Param("userId")Integer userId);
}
