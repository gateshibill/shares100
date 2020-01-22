package com.cofc.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.ContractPunishment;

public interface ContractPunishmentService {

	List<ContractPunishment> findContractPunishmentList();

	int addContractPunishment(ContractPunishment punishment);

	ContractPunishment getContractPunishmentById(Integer punishmentId);

	int getContractPunishmentCount(Map<String, Object> map);

	List<ContractPunishment> getContractPunishmentList(Map<String, Object> map);

	ContractPunishment getPunishmentTitle(@Param("title")String punishmentTitle);

	void updateContractPunishment(ContractPunishment punishment);
	
	void delSharePunish(@Param("punishmentId")Integer punishmentId);

}
