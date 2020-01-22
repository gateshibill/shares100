package com.cofc.service.vij;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.vij.ModelRoom;

public interface ModelRoomService {
	public void addModelRoom(ModelRoom room);
	public void updateModelRoom(ModelRoom room);
	public void delModelRoom(@Param("id")Integer id);
	public ModelRoom getInfoById(@Param("id")Integer id);
	public int getModelRoomCount(@Param("room")ModelRoom room);
	public List<ModelRoom> getModelRoomList(@Param("room")ModelRoom room,@Param("page")Integer page,@Param("limit")Integer limit);
}
