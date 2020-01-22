package com.cofc.service.vij;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.vij.UserTicket;

public interface UserTicketService {
	public void addUserTicket(UserTicket ticket);
	public void updateUserTicket(UserTicket ticket);
	public void delUserTicket(@Param("ticketId")Integer ticketId);
	public int getUserTicketCount(@Param("ticket")UserTicket ticket);
	public List<UserTicket> getUserTicketList(@Param("ticket")UserTicket ticket,
			@Param("page")Integer page,@Param("limit")Integer limit);
	public int checkHasCardCount(@Param("userId")Integer userId,
			@Param("ticketId")Integer ticketId,@Param("isUse")Integer isUse);
}
