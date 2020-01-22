package com.cofc.service.aida;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.aida.OrderFollowTips;

public interface OrderFollowTipsService {

	public void addOrderFollowTips(OrderFollowTips orderFollowTips);

	public void updateOrderFollowTips(OrderFollowTips orderFollowTips);

	public void delOrderFollowTips(OrderFollowTips orderFollowTips);

	public OrderFollowTips getOrderFollowTips(@Param("appId") Integer appId, @Param("userId") Integer userId, Integer id);

	public List<OrderFollowTips> getOrderFollowTipsList(@Param("appId") Integer appId,
			@Param("userId") Integer userId, @Param("type") Integer type, @Param("page") Integer page, @Param("limit") Integer limit);

}