package com.cofc.service.vij;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.vij.ProjectOrder;

public interface ProjectOrderService {
	public void addProjectOrder(ProjectOrder order);
	public ProjectOrder getProjectOrderByid(@Param("projectId")Integer projectId,@Param("isPay")Integer isPay);
	public ProjectOrder getOrderByid(@Param("porderId")Integer porderId);
	public ProjectOrder getProjectOrderByOrderNo(@Param("projectOrderNo")String projectOrderNo);
	public void updateProjectOrder(ProjectOrder order);
	public void updateIsPay(@Param("isPay")Integer isPay,@Param("porderId")Integer porderId);
}
