package com.cofc.service.vij;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.vij.StyleSubjectAnswer;

public interface StyleSubjectAnswerService {
	public void addAnswer(StyleSubjectAnswer answer);
	public void updateAnswer(StyleSubjectAnswer answer);
	public void delAnswer(@Param("id")Integer id);
	public StyleSubjectAnswer getInfoByProjectId(@Param("projectId")Integer projectId);
	public StyleSubjectAnswer getInfoById(@Param("id")Integer id);
}
