package com.cofc.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.PrizeRecord;

public interface PrizeRecordService {
	public int addNewPrizeRecord(PrizeRecord precord);

	public List<PrizeRecord> findMygetPrizeRecord(@Param("userId") Integer userId,
			@Param("activityId") Integer activityId, @Param("rowsId") Integer rowsId,
			@Param("pageSize") Integer pageSize);

	public List<PrizeRecord> findPrizeRecordByActivity(@Param("activityId") Integer activityId,
			@Param("pageNo") Integer pageNo, @Param("pageSize") Integer pageSize);

	public PrizeRecord getPrizeRecordById(Integer recordId);

	public void changePrizeRecordStatus(PrizeRecord prirecord);

	public List<PrizeRecord> findMyTodayAllRecord(@Param("activityId") Integer activityId,
			@Param("userId") Integer userId, @Param("startTime") String startTime, @Param("endTime") String endTime);

	public List<PrizeRecord> getshowWinningList(Map<String, Object> map);

	public int getgetshowWinningCount(Map<String, Object> map);

	public void updatePrizeRecord(PrizeRecord record);

	public void deletePrizeRecord(PrizeRecord record);

	public List<PrizeRecord> getRecordIds(@Param("ids")List<String> asList);
}
