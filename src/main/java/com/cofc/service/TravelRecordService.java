package com.cofc.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.TravelRecord;

public interface TravelRecordService {
     public void addTravelRecordInfo(TravelRecord record);
     public TravelRecord getisAlready(@Param("userId")Integer userId,@Param("travelId")Integer travelId,
    		 @Param("viewId")Integer viewId);
     public int getRedbagCountByUserId(@Param("travelId")Integer travelId,@Param("userId")Integer userId);//我抢的红包总数
     public List<TravelRecord> getRobRecordListByUserId(@Param("userId")Integer userId,@Param("loginPlat")Integer loginPlat,
    		 @Param("pageNo")Integer pageNo,@Param("pageSize")Integer pageSize); 
}
