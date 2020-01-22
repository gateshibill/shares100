package com.cofc.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.TravelView;

public interface TravelViewService {
   public void addTravelViewInfo(TravelView view);
   public List<TravelView> getTravelViewByTravelId(@Param("travelId")Integer travelId);
   public TravelView getTravelViewByqqViewId(@Param("travelId")Integer travelId,@Param("viewId")Integer viewId);
   public void updateRedbag(TravelView v);
   public void updateViewInfo(@Param("viewId")Integer viewId);
   public void updateAllRedbag(TravelView v);
   public int getIsOrderViewByTravelId(@Param("travelId")Integer travelId); //已浏览的景点总数
   public int getViewCount(@Param("travelId")Integer travelId);
   public List<TravelView> getViewList(@Param("travelId")Integer travelId,@Param("page")Integer page,@Param("limit")Integer limit);
}
