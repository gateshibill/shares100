package com.cofc.service.video;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.video.Column;

public interface ColumnService {
	public void addColumn(Column Column);
	public void updateColumn(Column Column);
	public void delColumn(@Param("id")Integer id);
	public Column getInfoById(@Param("id")Integer id);
	public int getColumnCount(@Param("column")Column column);
	public List<Column> getList(@Param("column")Column column,@Param("page")Integer page,
			@Param("limit")Integer limit);
	public List<Column> getColumnList(@Param("type")Integer type);//0为电影，1为应用音乐
}
