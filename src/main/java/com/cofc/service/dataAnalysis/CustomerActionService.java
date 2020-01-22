package com.cofc.service.dataAnalysis;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.dataAnalysis.CustomerAction;

public interface CustomerActionService {
	public void addCustomerAction(CustomerAction CustomerAction);

	public List<CustomerAction> getCustomerActionList(@Param("appId") Integer appId, @Param("userId") Integer userId,
			@Param("className") String className, @Param("methodName") String methodName);

}