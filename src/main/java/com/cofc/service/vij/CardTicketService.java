package com.cofc.service.vij;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.vij.CardTicket;

public interface CardTicketService {
	public void addCardTicket(CardTicket ticket);
	public void updateCardTicket(CardTicket ticket);
	public void delCardTicket(@Param("ticketId")Integer ticketId);
	public CardTicket getInfoById(@Param("ticketId")Integer ticketId);
	public int getCardTicketCount(@Param("ticket")CardTicket ticket);
	public List<CardTicket> getCardTicketList(@Param("ticket")CardTicket ticket,
			@Param("page")Integer page,@Param("limit")Integer limit);
	//多平台礼券兑换
	public int getCardTicketCountByLoginPlat(@Param("ticket")CardTicket ticket,@Param("loginPlatList")List<String> loginPlatList);
	public List<CardTicket> getCardTicketListByLoginPlat(@Param("ticket")CardTicket ticket,
			@Param("loginPlatList")List<String> loginPlatList,@Param("page")Integer page,
			@Param("limit")Integer limit);
}
