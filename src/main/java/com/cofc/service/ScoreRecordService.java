package com.cofc.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.ScoreRecord;

public interface ScoreRecordService {
	public void addScoreRecord(ScoreRecord record);
	public int getScoreRecordCount(@Param("record")ScoreRecord record);
	public List<ScoreRecord> getScoreRecordList(@Param("record")ScoreRecord record,
			@Param("page")Integer page,@Param("limit")Integer limit); 
}
