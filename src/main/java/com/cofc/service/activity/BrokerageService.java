package com.cofc.service.activity;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.cofc.pojo.activity.Brokerage;

/**
 * 
 * @author chenxiangyou
 *
 */
public interface BrokerageService {

	// 添加砍价商品
	public void addBrokerage(Brokerage brokerage);

	// 更新砍价商品
	public void updateBrokerage(Brokerage brokerage);

	public Brokerage getBrokerage(@Param("appId") Integer appId, @Param("userId") Integer userId,
			@Param("orderId") Integer orderId);

	public List<Brokerage> getBrokerageList(@Param("appId") Integer appId, @Param("userId") Integer userId);

	public List<Brokerage> getBrokerageListByStatus(@Param("appId") Integer appId, @Param("userId") Integer userId,
			@Param("status") Integer status);
}