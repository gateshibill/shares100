package com.cofc.service.aida;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.aida.CustomerLabel;
import com.cofc.pojo.aida.CustomerLabelRecord;

public interface CustomerLabelService {

	public void addCustomerLabel(CustomerLabelRecord CustomerLabelRecord);

	public void delCustomerLabel(@Param("appId") Integer appId, @Param("id") Integer id);

	// 参数为0获得一级标签
	public List<CustomerLabel> getCustomerUpperLabelList(@Param("upperId") Integer upperId);

	public List<CustomerLabel> getCustomerLabelList(@Param("appId") Integer appId,
			@Param("salesPersonId") Integer salesPersonId, @Param("upperId") Integer upperId);
	
	// 获得一个客户的所有标签
	public List<CustomerLabel> getOneCustomerLabelList(@Param("appId") Integer appId,@Param("salesPersonId") Integer salesPersonId,@Param("userId") Integer userId);
}