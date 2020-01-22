package com.cofc.service.vij;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.vij.StyleSubject;

public interface StyleSubjectService {
	public void addStyleSubject(StyleSubject subject);
	public void updateStyleSubject(StyleSubject subject);
	public int getStyleSubjectCount(@Param("subject")StyleSubject subject);
	public List<StyleSubject> getStyleSubjectList(@Param("subject")StyleSubject subject,
			@Param("page")Integer page,@Param("limit")Integer limit);
}
