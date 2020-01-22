package com.cofc.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.TagCommon;
import com.cofc.pojo.UserCommon;

public interface TagCommonService {
	
	public List<TagCommon> showTagsByqualification(@Param("tagType")Integer tagType,@Param("creater")Integer creater, @Param("rowsId")Integer rowsId, @Param("pageSize")Integer pageSize);
	
	public TagCommon findTagIsExist(TagCommon tc);
	
	public void addNewTag(TagCommon tc);
	
	public int getTagCount(@Param("tag")TagCommon tag, @Param("tagUser")UserCommon tagUser, @Param("startTime")String startTime, @Param("endTime")String endTime);
	
	public List<TagCommon> showAllTag(@Param("tag")TagCommon tag, @Param("tagUser")UserCommon tagUser, @Param("startTime")String startTime, @Param("endTime")String endTime, @Param("rowsId")Integer rowsId, @Param("pageSize")Integer pageSize);
	public void updateTagInfo(TagCommon tag);
	
	public TagCommon getCommonTagById(String tagId);
	
	public void addBatchTags(List<TagCommon> theseTags);
	
	public List<TagCommon> findTagCommonList(@Param("ids")List hasTagList);
	
	public TagCommon getTagCommonName(@Param("tagName")String tagName,@Param("tagType") Integer tagType);

	public void deleteTag(@Param("tagId")Integer tagId);
}
