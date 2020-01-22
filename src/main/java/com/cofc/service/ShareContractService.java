package com.cofc.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.ShareContract;

public interface ShareContractService {

	int addShareContract(ShareContract contract);

	ShareContract getShareContractById(@Param("contractId")Integer contractId);

	ShareContract getContractDetailsById(@Param("contractId")Integer contractId, @Param("userId")Integer userId);

	List<ShareContract> getShareContractList(@Param("userId")Integer userId,@Param("pageNo") Integer pageNo, @Param("pageSize")Integer pageSize);
    List<ShareContract> getAllShareContract(@Param("loginPlat")Integer loginPlat,@Param("pageNo") Integer pageNo, @Param("pageSize")Integer pageSize);
	int getShareContractCount(@Param("contract")ShareContract contract);

	void updateShareContract(ShareContract contract);

	void deleteShareContract(@Param("contractId")Integer contractId);

}
