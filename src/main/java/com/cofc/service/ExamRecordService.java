package com.cofc.service;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.ExamRecord;

public interface ExamRecordService {
	public void addExamRecord(ExamRecord record);
	public int getExamRecordCount(@Param("record")ExamRecord record);
	
}
