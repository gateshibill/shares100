package com.cofc.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.RechargeOrder;

public interface RechargeOrderService {
   public void addRechargeOrder(RechargeOrder rechargeOrder);
   public RechargeOrder getlistByRechargeId(@Param("rechargeId")Integer rechargeId);
   public void updateRechargeOrder(RechargeOrder rechargeOrder);
   public List<RechargeOrder> wxgetlistByUserId(@Param("userId")Integer userId,@Param("loginPlat")Integer loginPlat,
		   @Param("pageNo")Integer pageNo,@Param("pageSize")Integer pageSize);
   //后台
   public int getRechargeCount(@Param("order")RechargeOrder order);
   public List<RechargeOrder> getAllRechargeList(@Param("order")RechargeOrder order,@Param("page")Integer page,@Param("limit")Integer limit);
   //区分应用
   public int getRechargeCountByLoginPlat(@Param("loginPlatList")List<String> loginPlatList,@Param("order")RechargeOrder order);
   public List<RechargeOrder> getAllRechargeListByLoginPlat(@Param("loginPlatList")List<String> loginPlatList,
		   @Param("order")RechargeOrder order,@Param("page")Integer page,@Param("limit")Integer limit);
}
