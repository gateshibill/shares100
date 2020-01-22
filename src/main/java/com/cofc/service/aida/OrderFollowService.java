package com.cofc.service.aida;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.aida.OrderFollow;


public interface OrderFollowService {

	public void addOrderFollow(OrderFollow orderFollow);

	public List<OrderFollow> getOrderFollowListBySalesPersonId(@Param("appId") Integer appId,
			@Param("salesPersonId") Integer salesPersonId, @Param("userId") Integer userId,@Param("page") Integer pageNo,
			@Param("pageSize") Integer pageSize);

}