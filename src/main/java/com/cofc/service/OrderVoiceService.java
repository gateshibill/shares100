package com.cofc.service;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.OrderVoice;

public interface OrderVoiceService {
   public void addOrderVoice(OrderVoice voice);
   public int getOrderVoiceCount(@Param("loginPlat")Integer loginPlat);
   public void updateOrderVoice(OrderVoice voice);
}
