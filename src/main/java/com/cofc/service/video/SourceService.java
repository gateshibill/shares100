package com.cofc.service.video;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.video.Source;

public interface SourceService {
	public void addSource(Source source);
	public void updateSource(Source source);
	public void delSource(@Param("id")Integer id);
	public Source getSourceById(@Param("id")Integer id);
	public int getSourceCount(@Param("Source")Source source);
	public List<Source> getSourceList(@Param("Source") Source source,@Param("page")Integer page,@Param("limit")Integer limit);
	public List<Source> getSourceListByVodIdAndSubId(@Param("vodId")Integer vodId,@Param("subId")Integer subId,@Param("page")Integer page,@Param("limit")Integer limit);
}