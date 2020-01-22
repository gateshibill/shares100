package com.cofc.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.TaskCompete;

public interface TaskCompeteService {
	public int userCompeteTask(TaskCompete taskCompete);

	public TaskCompete confirmCompetedThisTask(@Param("taskId")Integer taskId, @Param("userId")Integer userId);

	public List<TaskCompete> findAllCompetedRecord(Integer taskId);

	public TaskCompete getCompeteRecordById(Integer competeId);

	public void publisherChooseCompeter(TaskCompete currcompete);
	public void publisherDischooseCompeter(TaskCompete currcompete);

	public TaskCompete getCurrTaskSelected(@Param("taskId")Integer taskId, @Param("competeStatus")Integer competeStatus);
}
