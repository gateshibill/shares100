package com.cofc.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.UserShoppingAddress;

public interface UserShoppingAddressService {
	public List<UserShoppingAddress> findShoppingAddress(Integer userId);
	public void addNewShoppingAddress(UserShoppingAddress usa);
	public void updateShoppingAddress(UserShoppingAddress usa);
	public void deleteAddress(UserShoppingAddress ushpAddress);
	public UserShoppingAddress getAddressById(Integer addressId);
	public void updateIsDefault(@Param("userId")Integer userId,@Param("isDefault")Integer isDefault);
	public List<UserShoppingAddress> selectOneAddressList(@Param("isDefault")Integer isDefault,
			@Param("userId")Integer userId);
}
