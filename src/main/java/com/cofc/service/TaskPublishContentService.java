package com.cofc.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.TaskPublishContent;

public interface TaskPublishContentService {
	public List<TaskPublishContent> findAllContent();
	int getTaskContentCount(Map<String, Object> map);
	public List<TaskPublishContent> getAllContent(Map<String, Object> map);
	//判断内容是否存在
    TaskPublishContent isAlreadyName(@Param("content")String contentText);
	//添加内容
	int addTaskContent(TaskPublishContent content);
	//删除内容
	void delContent(@Param("contentId")Integer contentId);
	//获取信息
	TaskPublishContent getContentDetail(@Param("contentId")Integer contentId);
	//修改内容
	void updateContent(@Param("contentName")String contentName,@Param("contentId")Integer contentId);
}
