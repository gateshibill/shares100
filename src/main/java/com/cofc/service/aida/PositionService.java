package com.cofc.service.aida;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.aida.Position;

public interface PositionService {
	public void addPosition(Position position);
	public void updatePosition(Position position);
	public void delPosition(@Param("positionId")Integer positionId);
	public int getPositionCount(@Param("pos")Position pos);
	public List<Position> getPositionList(@Param("pos")Position pos,@Param("page")Integer page,
			@Param("limit")Integer limit);
	public Position getPositionInfo(@Param("positionId")Integer positionId);
}
