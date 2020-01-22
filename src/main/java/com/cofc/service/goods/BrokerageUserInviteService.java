package com.cofc.service.goods;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.goods.BrokerageUserInvite;

public interface BrokerageUserInviteService {
	public void addBrokerageUserInvite(BrokerageUserInvite BrokerageUserInvite);

	public void updateBrokerageUserInvite(BrokerageUserInvite BrokerageUserInvite);

	public BrokerageUserInvite getBrokerageUserInvite(@Param("appId") Integer appId, @Param("goodsId") Integer goodsId,
			@Param("userId") Integer userId, @Param("inviteUserId") Integer inviteUserId);

	public BrokerageUserInvite getBrokerageUserInvite(@Param("appId") Integer appId, @Param("goodsId") Integer goodsId,
			@Param("userId") Integer userId);
}