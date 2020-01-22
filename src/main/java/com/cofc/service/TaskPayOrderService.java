package com.cofc.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.TaskPayOrder;

public interface TaskPayOrderService {
	public int userSeletedCompete(TaskPayOrder taskorder);

	public TaskPayOrder cofirmSelectedThisOrder(@Param("competeId") Integer competeId);

	public void updateTaskOrderInfo(TaskPayOrder taskOrder);

	public TaskPayOrder getOrderById(Integer orderId);
	
	int getPayCount(Map<String,Object> map);
	
	List<TaskPayOrder> getAllTaskPayList(Map<String,Object> map);

	public String getCountOfTradeMoney(@Param("startTime")String startTime, @Param("endTime")String endTime);
}
