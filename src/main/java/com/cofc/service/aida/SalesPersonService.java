package com.cofc.service.aida;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.aida.SalesPerson;

public interface SalesPersonService {
	public void addSalesPerson(SalesPerson SalesPerson);

	public SalesPerson getSalesPersonById(@Param("id") Integer id);
	public SalesPerson getSalesPersonByUserId(@Param("userId") Integer userId);
	public void updateSalesPerson(SalesPerson SalesPerson);

	public int getSalesPersonCount(@Param("appId") Integer appId);

	public List<SalesPerson> getSalesPersonList(@Param("appId") Integer appId, @Param("page") Integer pageNo,
			@Param("pageSize") Integer pageSize);
	
	public List<SalesPerson> getSalesPersonListOrderByBt(@Param("appId") Integer appId, @Param("page") Integer pageNo,
			@Param("pageSize") Integer pageSize);

	// 用于排名，需要获取所有的销售
	public List<SalesPerson> getRankingSalesPersonList(@Param("appId") Integer appId);

	public List<SalesPerson> getSalesPersonRankingListByType(@Param("appId") Integer appId,
			@Param("apartmentId") Integer apartmentId, @Param("type") Integer type, @Param("page") Integer pageNo,
			@Param("pageSize") Integer pageSize);

	public List<SalesPerson> getSalesPersonRankingListByType1(@Param("appId") Integer appId,
			@Param("apartmentId") Integer apartmentId, @Param("type") Integer type, @Param("page") Integer pageNo,
			@Param("pageSize") Integer pageSize);

	public List<SalesPerson> getSalesPersonRankingListByType2(@Param("appId") Integer appId,
			@Param("apartmentId") Integer apartmentId, @Param("type") Integer type, @Param("page") Integer pageNo,
			@Param("pageSize") Integer pageSize);

	public List<SalesPerson> getSalesPersonRankingListByType3(@Param("appId") Integer appId,
			@Param("apartmentId") Integer apartmentId, @Param("type") Integer type, @Param("page") Integer pageNo,
			@Param("pageSize") Integer pageSize);

	public List<SalesPerson> getSalesPersonRankingListByType4(@Param("appId") Integer appId,
			@Param("apartmentId") Integer apartmentId, @Param("type") Integer type, @Param("page") Integer pageNo,
			@Param("pageSize") Integer pageSize);

	// 获取默认销售
	public List<SalesPerson> getDefaultSalerList(@Param("isdefault") Integer isdefault, @Param("appId") Integer appId);
	//根据应用获取销售列表
	public int getSalerCount(@Param("loginPlatList")List<String> loginPlatList,@Param("sale")SalesPerson sale);
	public List<SalesPerson> getSalerList(@Param("loginPlatList")List<String> loginPlatList,@Param("sale")SalesPerson sale,
			@Param("page")Integer page,@Param("limit")Integer limit);

}