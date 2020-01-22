package com.cofc.service.aida;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.aida.WorkWeixin;

public interface WorkWeixinService {
	public void addWorkWeixin(WorkWeixin work);
	public void updateWorkWeixin(WorkWeixin work);
	public void delWork(@Param("workId")Integer workId);
	public WorkWeixin getInfoById(@Param("id") Integer id);
	public WorkWeixin getInfoByWorkId(@Param("workId")Integer workId,@Param("appId")Integer appId);
	public int getWorkCount(@Param("work")WorkWeixin work);
	public List<WorkWeixin> getWorkList(@Param("work")WorkWeixin work,
			@Param("page")Integer page,@Param("limit")Integer limit);
}
