package com.cofc.service.aida;



import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.aida.CustomerDetail;

public interface CustomerDetailService {
	public void addCustomerDetail(CustomerDetail CustomerDetail);
	public void updateCustomerDetail(CustomerDetail CustomerDetail);
	public CustomerDetail getCustomerDetail(@Param("appId") Integer appId, @Param("userId") Integer userId, @Param("salesPersonId") Integer salesPersonId);
}