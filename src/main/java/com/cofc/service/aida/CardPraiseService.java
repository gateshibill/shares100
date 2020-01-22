package com.cofc.service.aida;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.aida.CardPraise;

public interface CardPraiseService {
	public void addPraise(CardPraise praise);
	public void updatePraise(CardPraise praise);
	public void delPraise(@Param("id")Integer id);
	public List<CardPraise> getPraiseList(@Param("praise")CardPraise praise,@Param("page")Integer page,
			@Param("limit")Integer limit);
	public CardPraise getInfoByuserId(@Param("userId")Integer userId,@Param("cardId")Integer cardId);
	public int getPraiseCount(@Param("cardId")Integer cardId);
}
