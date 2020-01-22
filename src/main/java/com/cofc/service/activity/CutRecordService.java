package com.cofc.service.activity;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.activity.CutRecord;




public interface CutRecordService {
	public void addCutRecord(CutRecord cutRecord);
	public CutRecord getCutRecord(@Param("appId") Integer appId,@Param("id") Integer id);
	public List<CutRecord> getCutRecordList(@Param("appId") Integer appId,@Param("orderId") Integer orderId);
}