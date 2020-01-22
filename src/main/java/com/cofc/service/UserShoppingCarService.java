package com.cofc.service;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cofc.pojo.UserShoppingCar;

public interface UserShoppingCarService {
	
	public List<UserShoppingCar> findHisShoppingCar(@Param("userId")Integer userId,@Param("pageNo")Integer pageNo,@Param("pageSize")Integer pageSize);
	
	public UserShoppingCar findShoppingCar(@Param("userid")Integer userid, @Param("goodsID")Integer goodsID, @Param("status")Integer status);
	
	public UserShoppingCar findSpecShoppingCar(@Param("userid")Integer userid, @Param("goodsID")Integer goodsID,@Param("specId")Integer specId,@Param("status")Integer status);
	
	public UserShoppingCar findShoppingCarByOther(@Param("userid")Integer userid, @Param("goodsID")Integer goodsID, @Param("status")Integer status);
	
	public void deleteGoodsInCar(@Param("carId")Integer carId, @Param("removeTime")Date removeTime);
	
	public void addGoodsInCar(UserShoppingCar usc);
	
	public void updateNumberByID(@Param("carId")int carId, @Param("number")int number);
	
	public void transfer(@Param("carId")int carId, @Param("transferTime")Date transferTime);  //购物车里的商品已经提交到订单里，这里标记为已转移，前端不可见。
	
	public List<UserShoppingCar> getUserShoppingCarList(@Param("userName")String car, @Param("startTime")String startTime,@Param("endTime") String endTime,  @Param("page")int i,
			 @Param("limit")Integer limit);
	
	public int getUserShoppingCarCount(@Param("userName")String car, @Param("startTime")String startTime, @Param("endTime")String endTime);

	public void deleteUserShoppingCar(@Param("carId")Integer carId);

	public List<UserShoppingCar> getcarByIds(@Param("ids")List<String> asList);
	
	public UserShoppingCar getCarDetail(@Param("carId")Integer carId,@Param("status")Integer status);
    
	public void deleteAllGoodsInCar(@Param("ids")List<String> ids,@Param("removeTime")Date removeTime);
	
	//获取购物车数量
	public int getCarCount(@Param("userId")Integer userId,@Param("status")Integer status);
}
