package com.cofc.service.activity;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.activity.TurnTableRecord;

public interface TurnTableRecordService {

	public void addTurnTableRecord(TurnTableRecord TurnTableRecord);

	public List<TurnTableRecord> getTurnTableRecordList(@Param("appId") Integer appId,@Param("turntableId") Integer turntableId,@Param("page") Integer page,
			@Param("limit") Integer limit);
}