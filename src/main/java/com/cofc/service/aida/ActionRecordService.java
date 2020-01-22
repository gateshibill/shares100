package com.cofc.service.aida;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.aida.ActionRecord;

public interface ActionRecordService {
	public void addActionRecord(ActionRecord actionRecord);

	public ActionRecord getlistByActionRecordId(@Param("id") Integer id);

	public void updateActionRecord(ActionRecord actionRecord);

	public int getActionRecordCount(@Param("appId") Integer appId);

	// 一个人查看一个销售名片次数
	public int getSinlgeActionRecordCount(@Param("appId") Integer appId, @Param("salesPersonId") Integer salesPersonId,
			@Param("userId") Integer userId, @Param("type") Integer type, @Param("objectType") Integer objectType,
			@Param("objectId") Integer objectId);

	public List<ActionRecord> getActionRecordList(@Param("appId") Integer appId, @Param("pageNo") Integer pageNo,
			@Param("pageSize") Integer pageSize);

	// 用户个人，单个行为查询
	public Integer getActionRecordCountByTypeAndObjectType(@Param("appId") Integer appId,
			@Param("salesPersonId") Integer salesPersonId, @Param("userId") Integer userId, @Param("type") Integer type,
			@Param("objectType") Integer objectType);

	public List<ActionRecord> getActionRecordListBySalesId(@Param("appId") Integer appId,
			@Param("salesId") Integer salesId, @Param("lastdays") Integer actionObjectId,
			@Param("pageNo") Integer pageNo, @Param("pageSize") Integer pageSize);

	// 销售人员对于一个行为下所有人,用于首页里面查看名片的详细
	public List<ActionRecord> getActionRecordListBySpAndType(@Param("appId") Integer appId,
			@Param("salesPersonId") Integer salesPersonId, @Param("type") Integer type,
			@Param("objectType") Integer objectType, @Param("objectId") Integer objectId, @Param("page") Integer pageNo,
			@Param("pageSize") Integer pageSize);

	// 个人用户行为信息，按照时间维度显示，用户查看个人.互动。
	public List<ActionRecord> getCustomerActionRecordList(@Param("appId") Integer appId,
			@Param("salesCustomerId") Integer salesCustomerId, @Param("page") Integer pageNo,
			@Param("pageSize") Integer pageSize);

	// 总的访问量
	public int getSalesPersonVisitedCount(@Param("appId") Integer appId, @Param("salesPersonId") Integer salesPersonId);

	// 单类访问量
	public int getVisitedCount(@Param("appId") Integer appId, @Param("salesPersonId") Integer salesPersonId,
			@Param("type") Integer type, @Param("objectType") Integer objectType);

	// 单类访问量,增加时间过滤
	public int getVisitedCountEx(@Param("appId") Integer appId, @Param("salesPersonId") Integer salesPersonId,
			@Param("type") Integer type, @Param("objectType") Integer objectType, @Param("startTime") String startTime,
			@Param("endTime") String endTime);
}