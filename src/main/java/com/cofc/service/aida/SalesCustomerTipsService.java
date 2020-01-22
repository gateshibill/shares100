package com.cofc.service.aida;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.aida.SalesCustomerTips;

public interface SalesCustomerTipsService {

	public List<SalesCustomerTips> getSalesCustomerTipsList(@Param("type") Integer type,
			@Param("objectType") Integer objectType);

}