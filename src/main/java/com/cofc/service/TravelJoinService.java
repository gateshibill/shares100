package com.cofc.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.TravelJoin;

public interface TravelJoinService {
   public void addTravelJoin(TravelJoin tjoin); 
   public List<TravelJoin> getTravelJoinListInfo(@Param("travelId")Integer travelId,@Param("pageNo")Integer pageNo,@Param("pageSize")Integer pageSize);
   public TravelJoin getAlreadyJoin(@Param("travelId")Integer travelId,@Param("userId")Integer userId);
   public List<TravelJoin> getMyJoinTravel(@Param("userId")Integer userId,
		   @Param("loginPlat")Integer loginPlat,@Param("pageNo")Integer pageNo,@Param("pageSize")Integer pageSize);
   public void quitTravel(@Param("travelId")Integer travelId,@Param("userId")Integer userId);
   public int getJoinPeopleCountByTravelId(@Param("travelId")Integer travelId);
}
