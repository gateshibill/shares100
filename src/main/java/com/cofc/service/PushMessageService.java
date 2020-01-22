package com.cofc.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.PushMessage;

public interface PushMessageService {
  public PushMessage getTemplateInfo(@Param("loginPlat")Integer loginPlat,@Param("tempType")Integer tempType);
  public PushMessage getTemplateDetail(@Param("id")Integer id);
  public int isAreadyTemplateCount(@Param("loginPlat")Integer loginPlat,@Param("tempType")Integer tempType,@Param("id")Integer id);
  public void addTemplateInfo(PushMessage push);
  public void updateTemplateInfo(PushMessage push);
  public void delTemplateInfo(@Param("id")Integer id);
  public int getTemplateCount(@Param("push")PushMessage push);
  public List<PushMessage> getTemplateList(@Param("push")PushMessage push,@Param("page")Integer page,@Param("limit")Integer limit);
  //区分应用
  public int getTemplateCountByLoginPlat(@Param("loginPlatList")List<String> loginPlatList,@Param("push")PushMessage push);
  public List<PushMessage>getTemplateListByLoginPlat(@Param("loginPlatList")List<String> loginPlatList,@Param("push")PushMessage push,@Param("page")Integer page,@Param("limit")Integer limit);
}
