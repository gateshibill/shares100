package com.cofc.service.aida;


import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.aida.SalesAbility;


public interface SalesAbilityService {

	public void addSalesAbility(SalesAbility SalesAbility);
	public void updateSalesAbility(SalesAbility SalesAbility);
	public SalesAbility getSalesAbility(@Param("appId") Integer appId,@Param("salesPersonId") Integer salesPersonId);
	public List<SalesAbility> getSalesAbilityList(@Param("appId") Integer appId);
}