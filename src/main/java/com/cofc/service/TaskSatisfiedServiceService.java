package com.cofc.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.TaskSatisfiedService;

public interface TaskSatisfiedServiceService {
	public List<TaskSatisfiedService> findAllServices(Integer type);
	int getTaskServiceCount(Map<String,Object> map);
	List<TaskSatisfiedService> getTaskServiceList(Map<String,Object> map);
	TaskSatisfiedService isAlreadyName(@Param("service") String serviceName);
	int addService(TaskSatisfiedService service);
	void delService(@Param("serviceId")Integer serviceId);
	TaskSatisfiedService isOnlyOneService(@Param("serviceName")String serviceName,@Param("serviceType")Integer serviceType);
	TaskSatisfiedService getServiceDetail(@Param("serviceId")Integer serviceId);
	void updateService(@Param("serviceName")String serviceName,@Param("serviceType")Integer serviceType,@Param("serviceId")Integer serviceId);
}
