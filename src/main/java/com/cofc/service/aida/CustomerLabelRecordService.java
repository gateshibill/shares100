package com.cofc.service.aida;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import com.cofc.pojo.aida.CustomerLabelRecord;

public interface CustomerLabelRecordService {

	public void addCustomerLabelRecord(CustomerLabelRecord CustomerLabelRecord);

	public CustomerLabelRecord getCustomerLabelRecord(@Param("appId") Integer appId, @Param("userId") Integer userId,
			@Param("labelId") Integer labelId);

	public void delCustomerLabelRecord(@Param("appId") Integer appId, @Param("id") Integer id);

	// 获得用户标签
	public List<CustomerLabelRecord> getCustomerLabelRecordList(@Param("appId") Integer appId,
			@Param("userId") Integer userId);
}