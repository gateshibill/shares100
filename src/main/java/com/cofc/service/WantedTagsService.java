package com.cofc.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.WantedTags;

public interface WantedTagsService {

	public List<WantedTags> findSkillTagsByOrder(@Param("orderId")Integer orderId, @Param("rowsId")Integer rowsId, @Param("pageSize")Integer pageSize);

	public void addNewWantedTags(WantedTags wtt);

	public WantedTags getTagByName(String tagName);

	public List<WantedTags> findWantedTagsByIds(@Param("ids")List<String> asList);
}
