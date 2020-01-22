package com.cofc.service.aida;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.aida.UserImpress;

public interface UserImpressService {
	public void addImpress(UserImpress impress);
	public void addAllImpress(@Param("impresslist")List<UserImpress> impresslist);
	public void updateImpress(UserImpress impress);
	public void delImpress(@Param("tagId")Integer tagId);
	public List<UserImpress> getImpressList(@Param("impress")UserImpress impress);
	public UserImpress getImpressById(@Param("tagId")Integer tagId);
}
