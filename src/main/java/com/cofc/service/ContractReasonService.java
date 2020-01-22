package com.cofc.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.ContractReason;

public interface ContractReasonService {

	List<ContractReason> findContractReasonList();

	int addContractReason(ContractReason reason);

	ContractReason getContractReasonById(@Param("reasonId")Integer reasonId);

	List<ContractReason> getContractReasonList(Map<String, Object> map);

	int getContractReasonCount(Map<String, Object> map);

	ContractReason getReasonTitle(@Param("title")String reasonTitle);

	void updateContractReason(ContractReason reason);
    
	void delShareReason(@Param("reasonId")Integer ReasonId);
}
