package com.cofc.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.TaskCommon;

public interface TaskCommonService {
	public int publishNewTask(TaskCommon task);

	int getTaskCount(Map<String, Object> map);

	public List<TaskCommon> getTaskList(Map<String, Object> map);

	public TaskCommon getTaskById(Integer taskId);

	public List<TaskCommon> findTaskByLoginPlat(@Param("loginPlat") Integer loginPlat, @Param("pageNo") Integer pageNo,
			@Param("pageSize") Integer pageSize);

	public void changeTaskInfo(TaskCommon currtask);

	public List<TaskCommon> findMyPublishedTasks(@Param("userId") Integer userId, @Param("loginPlat") Integer loginPlat,
			@Param("taskStatus") Integer taskStatus, @Param("pageNo") Integer pageNo,
			@Param("pageSize") Integer pageSize);

	public List<TaskCommon> findMyCompetedTasks(@Param("userId") Integer userId, @Param("loginPlat") Integer loginPlat,
			@Param("competeStatus") Integer competeStatus, @Param("pageNo") Integer pageNo,
			@Param("pageSize") Integer pageSize);

	public List<TaskCommon> getTaskIds(@Param("ids")List<String> asList);

	public void deleteTask(@Param("taskId")Integer taskId);

}
