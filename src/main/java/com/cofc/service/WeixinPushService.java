package com.cofc.service;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.WeixinPush;

public interface WeixinPushService {
   public WeixinPush getWXConfig(@Param("loginPlat")Integer loginPlat);
} 
