package com.cofc.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.SkillTags;

public interface SkillTagsService {

	public List<SkillTags> findSkillTagsByOrder(@Param("orderId")Integer orderId, @Param("rowsId")Integer rowsId, @Param("pageSize")Integer pageSize);

	public void addNewSkillTags(SkillTags skt);

	public SkillTags getTagByName(String tagName);

	public List<SkillTags> findTagsByIds(@Param("ids")List ids);

}
