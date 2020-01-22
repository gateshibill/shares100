package com.cofc.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.RechargeAmount;

public interface RechargeAmountService {
    public List<RechargeAmount> getRechargeInfo(@Param("recharge")RechargeAmount recharge,@Param("page")Integer page,@Param("limit")Integer limit);
    public int getRechargeCount(@Param("recharge")RechargeAmount recharge);
    public void addRechargeInfo(RechargeAmount rechargeAmount);
    public void updateRechargeInfo(RechargeAmount rechargeAmount);
    public void deleteRecharInfo(@Param("discountId")Integer discountId);
    public List<RechargeAmount> getRechargeInfoByLoginPlat(@Param("loginPlatList")List<String> loginPlatList,@Param("recharge")RechargeAmount recharge,@Param("page")Integer page,@Param("limit")Integer limit);
    public int getRechargeCountByLoginPlat(@Param("loginPlatList")List<String> loginPlatList,@Param("recharge")RechargeAmount recharge);
    public RechargeAmount getDetailInfo(@Param("discountId")Integer discountId);
    public List<RechargeAmount> getWxlist(@Param("loginPlat")Integer loginPlat);
}
