package com.cofc.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.ContractRecord;

public interface ContractRecordService {

	public int addContractRecord(ContractRecord record);

	public List<ContractRecord> getContractRecordList(@Param("userId")Integer userId, @Param("pageNo")int pageNo, @Param("pageSize")Integer pageSize,@Param("contractId") Integer contractId,@Param("type") Integer type);

	public void updateContractRecord(ContractRecord record);

	public ContractRecord getContractRecordById(@Param("recordId")int recordId);

	public int getgetContractRecordCount(Map<String, Object> map);

	public void deleteContractRecord(@Param("recordId")Integer recordId);

	public String getCountOfTradeMoney(@Param("startTime")String startTime,@Param("endTime")String endTime);

}
