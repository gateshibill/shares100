package com.cofc.service.aida;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.aida.SalesCustomer;

public interface SalesCustomerService {
	public void addSalesCustomer(SalesCustomer salesCustomer);

	public void updateSalesCustomer(SalesCustomer salesCustomer);
	public void updateSalesCustomerBatch(List<SalesCustomer> list);

	public int getCustomerCount(@Param("appId") Integer appId, @Param("salesPersonId") Integer salesPersonId);

	// 按照时间和销售ID过滤,如果ID为0则取所有，时间为空取所有,status为客户状态。
	public int getCustomerCountEx(@Param("appId") Integer appId, @Param("salesPersonId") Integer salesPersonId,
			@Param("startTime") String startTime, @Param("endTime") String endTime, @Param("status") Integer status);

	public List<SalesCustomer> getCustomerList(@Param("appId") Integer appId,
			@Param("salesPersonId") Integer salesPersonId, @Param("pageNo") Integer pageNo,
			@Param("pageSize") Integer pageSize);
	
	public List<SalesCustomer> getFollowingCustomerList(@Param("appId") Integer appId,
			@Param("salesPersonId") Integer salesPersonId, @Param("pageNo") Integer pageNo,
			@Param("pageSize") Integer pageSize);
	
	public SalesCustomer getCustomer(@Param("appId") Integer appId, @Param("salesPersonId") Integer salesPersonId,
			@Param("userId") Integer userId);

	// 查询用户所对应的销售
	public List<SalesCustomer> getCustomerByUserId(@Param("appId") Integer appId, @Param("userId") Integer userId,
			@Param("page") Integer page, @Param("limit") Integer limit);
	
	// 查询用户所对应的销售
	public List<SalesCustomer> getAllCustomer();
	
	// 更加消息时间获取客户
	public List<SalesCustomer> getCustomerByiMessage(@Param("appId") Integer appId,
			@Param("salesPersonId") Integer salesPersonId, @Param("page") Integer page, @Param("limit") Integer limit);
}