package com.cofc.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.TaskPublishReason;

public interface TaskPublishReasonService {
	public List<TaskPublishReason> findAllReason();
	int getTaskReasonCount(Map<String,Object> map);
	List<TaskPublishReason> getAllReasonList(Map<String,Object> map);
	//判断原因是否存在
    TaskPublishReason isAlreadyName(@Param("reason")String reasonName);
	//添加内容
	int addTaskReason(TaskPublishReason reason);
	void delReason(@Param("reasonId")Integer reasonId);
	//获取信息
	TaskPublishReason getReasonDetail(@Param("reasonId")Integer reasonId);
	//修改
	void updataReason(@Param("reasonName")String ReasonName,@Param("reasonId")Integer reasonId);
	
}
