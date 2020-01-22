package com.cofc.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.TravelCommon;

public interface TravelCommonService {
  public int addTravelInfo(TravelCommon travel);//新增行程
  public void updateTravelInfo(TravelCommon travel);//编辑行程
  public List<TravelCommon> getTravelListInfo(@Param("loginPlat")Integer loginPlat,@Param("pageNo")Integer pageNo,@Param("pageSize")Integer pageSize);
  public int getTravelCountInfo(@Param("loginPlat")Integer loginPlat);//获取总数
  public void deleteTravelInfo(@Param("travelId")Integer travelId,@Param("userId")Integer userId);
  public TravelCommon getTravelDetaiInfo(@Param("travelId")Integer travelId);
  public void updateState(@Param("travelId")Integer travelId,@Param("state")Integer state);
  //后台区分应用
  public int getTravelCountByBxy(@Param("travel")TravelCommon travel);
  public List<TravelCommon> getTravelListByBxy(@Param("travel")TravelCommon travel,@Param("page")Integer page,
		  @Param("limit")Integer limit);
  public int getTravelCountByLoginPlat(@Param("loginPlatList")List<String> loginPlatList,@Param("travel")TravelCommon travel);
  public List<TravelCommon> getTravelListByLoginPlat(@Param("loginPlatList")List<String> loginPlatList,
		  @Param("travel")TravelCommon travel,@Param("page")Integer page,
		  @Param("limit")Integer limit);
}
