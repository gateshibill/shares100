package com.cofc.service.aida;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.aida.UserCard;

public interface UserCardService {
	public void addUserCard(UserCard card);
	public void updateUserCard(UserCard card);
	public void delUserCard(@Param("cardId")Integer cardId);
	public UserCard getCardInfo(@Param("cardId")Integer cardId);
	public UserCard getUserCard(@Param("userId")Integer userId,@Param("appId")Integer appId);
}
