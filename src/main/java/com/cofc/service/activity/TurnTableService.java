package com.cofc.service.activity;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.activity.TurnTable;
import com.cofc.pojo.activity.TurnTableRecord;

public interface TurnTableService {

	public void addTurnTabel(TurnTableRecord TurnTabel);
	public TurnTable getTurnTable(@Param("appId") Integer appId,@Param("turnTableId") Integer turnTableId);
	public List<TurnTable> getTurnTableList(@Param("appId") Integer appId);
}