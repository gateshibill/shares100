package com.cofc.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.ContractContent;

public interface ContractContentService {

	List<ContractContent> getContractContentList(Map<String, Object> map);

	int getContractContentCount(Map<String, Object> map);

	List<ContractContent> findContractContentList();

	int addContractContent(ContractContent content);

	ContractContent getContractContentById(@Param("contentId")Integer contentId);

	ContractContent getContentTitle(@Param("title")String contentTitle);

	void updateContractContent(ContractContent content);
	
	void delShareContent(@Param("contentId")Integer contentId);

}
