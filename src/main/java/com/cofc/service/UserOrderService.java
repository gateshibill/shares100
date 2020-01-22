package com.cofc.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import com.cofc.pojo.UserOrder;
import com.cofc.pojo.UserOrderException;


@Service
public interface UserOrderService {

	public List<UserOrder> findAllUserOrder();
	
	public Integer getObjectNumberInOrder(@Param("id")int id, @Param("type")int type);  //查询用户订了多少商品（或其它的）了
	
	public void addUserOrder(UserOrder order);

	public void updateUserOrder(UserOrder userOrder);

	public UserOrder getIsexistUserOrder(@Param("objectID")int objectID, @Param("loginPlat")int loginPlat, @Param("userId")int userid,@Param("type") int orderType);

	public void updateStatus(@Param("orderID")int orderID, 
			@Param("orderStatus")Integer orderStatus,
			@Param("payStatus")Integer payStatus,@Param("payType")Integer payType,
			@Param("orderSource")Integer orderSource);
	
	public UserOrder findByID(@Param("orderID")int orderID);
	
	public void log(UserOrderException exception);  //记录订单异常的

	public List<UserOrder> getUserOrderList(@Param("order")UserOrder order, @Param("page")int page,@Param("limit") Integer limit, @Param("startTime") String startTime, @Param("endTime") String endTime);

	public int getUserOrderCount(@Param("order")UserOrder order, @Param("startTime")String startTime, @Param("endTime")String endTime);

	public void deleteUserOrderById(UserOrder order);
	
	public List<UserOrder> findMyOrder(@Param("userid")Integer userid, @Param("orderType")Integer orderType, @Param("orderStatus")Integer orderStatus,  @Param("rowsId")Integer rowsId, @Param("pageSize")Integer pageSize);

	public String getOrderRealFeeCount(@Param("order")UserOrder order,@Param("startTime") String startTime, @Param("endTime")String endTime);

	public List<UserOrder> findUserloginPlatList(@Param("loginPlat")Integer loginPlat,@Param("pageNo") int i,@Param("pageSize") Integer pageSize, @Param("userId")Integer userId, @Param("orderStatus")Integer orderStatus);
    
	public int getUserLoginPlatCount(@Param("loginPlat")Integer loginPlat,@Param("userId")Integer userId, 
			@Param("orderStatus")Integer orderStatus);
	
	//区分应用
	public int getUserOrderCountByLoginPlat(@Param("loginPlatList")List<String> loginPlatList,
			@Param("order")UserOrder order, @Param("startTime")String startTime, 
			@Param("endTime")String endTime);
	public List<UserOrder> getUserOrderListByLoginPlat(@Param("loginPlatList")List<String> loginPlatList,
			@Param("order")UserOrder order, @Param("page")int page,
			@Param("limit") Integer limit, @Param("startTime") String startTime,
			@Param("endTime") String endTime);
	public String getOrderRealFeeCountByLoginPlat(@Param("loginPlatList")List<String> loginPlatList,
			@Param("order")UserOrder order,@Param("startTime") String startTime, 
			@Param("endTime")String endTime);
    //交易记录
	public int getPayCount(@Param("order")UserOrder order, @Param("startTime")String startTime, 
			@Param("endTime")String endTime);
	public List<UserOrder> getPayList(@Param("order")UserOrder order, @Param("page")int page,@Param("limit") Integer limit, @Param("startTime") String startTime, @Param("endTime") String endTime);
	public int getPayCountByLoginPlat(@Param("loginPlatList")List<String> loginPlatList,
			@Param("order")UserOrder order, @Param("startTime")String startTime, 
			@Param("endTime")String endTime);
	public List<UserOrder> getPayListByLoginPlat(@Param("loginPlatList")List<String> loginPlatList,
			@Param("order")UserOrder order, @Param("page")int page,@Param("limit") Integer limit, 
			@Param("startTime") String startTime, @Param("endTime") String endTime);
	//获取订单数量
	public int getOrderOfPayCount(@Param("loginPlat")Integer loginPlat,
			@Param("payStatus")Integer payStatus);
	public String getOrderOfMoneyCount(@Param("loginPlat")Integer loginPlat,
			@Param("orderType")Integer orderType);
	public int getOrderOfPayCountByLoginPlat(@Param("loginPlatList")List<String> loginPlatList,
			@Param("payStatus")Integer payStatus);
	public String getOrderOfMoneyCountByLoginPlat(@Param("loginPlatList")List<String> loginPlatList,
			@Param("orderType")Integer orderType);
	//获取返点总额
	public String getOrderRebateMoneyCount(@Param("order")UserOrder order,@Param("startTime") String startTime, @Param("endTime")String endTime);
	public String getOrderRebateMoneyCountByLoginPlat(@Param("loginPlatList")List<String> loginPlatList,
			@Param("order")UserOrder order,@Param("startTime") String startTime, 
			@Param("endTime")String endTime);
	public String userVisitMoneyCount(@Param("agentId")Integer agentId,@Param("loginPlat")Integer loginPlat);
	public List<UserOrder> userVisitOrder(@Param("agentId")Integer agentId,@Param("loginPlat")Integer loginPlat
			,@Param("pageNo")Integer pageNo,@Param("pageSize")Integer pageSize);
	//后台代理
	public int userVisitOrderCount(@Param("agentId")Integer agentId,@Param("loginPlat")Integer loginPlat);
	
	//通过应用id和价格去查询唯一的未支付订单
	public UserOrder getOrderByPrice(@Param("loginPlat")Integer loginPlat,@Param("realFee")double realFee);
	//检测这一些未支付的订单
	public List<UserOrder> getNotPayOrder(@Param("loginPlat")Integer loginPlat,@Param("goodId")Integer goodId,
			@Param("realFee")double realFee,@Param("payStatus")Integer payStatus,@Param("orderStatus")Integer orderStatus);
	//检测扫码订单
	public UserOrder getIsNotDealOrder(@Param("orderId")Integer orderId);
	
	public UserOrder getOrderByOrderNo(@Param("orderNo")String orderNo);
		
}





/*



public List<GoodsOrder> findMyGoodsOrder(@Param("go")GoodsOrder go,@Param("rowsId")Integer rowsId,@Param("pageSize")Integer pageSize);

public int addNewGoodsOrder(GoodsOrder go);

public void deleteGoodsOrder(GoodsOrder go);

public void insertOutTradeNo(GoodsOrder go);

public List<GoodsOrder> findThisGoodsOrder(Integer goodsId);

public GoodsOrder getOrderByPayId(int prepayId);

public void userPayOrder(GoodsOrder go);

public List<GoodsOrder> findAllOrder(@Param("order") GoodsOrder order, @Param("goods") GoodsCommon goods,@Param("sellerWords")String sellerWords,@Param("buyerWords")String buyerWords,
		@Param("startTime") String startTime, @Param("endTime") String endTime,@Param("rowsId") int rowsId,
		@Param("pageSize") Integer pageSize);

public int getCountOrders(@Param("order") GoodsOrder order, @Param("goods") GoodsCommon goods,@Param("sellerWords")String sellerWords,@Param("buyerWords")String buyerWords,@Param("startTime") String startTime,@Param("endTime") String endTime);

public String getCountOfTradeMoney(@Param("startTime")String startTime, @Param("endTime")String endTime);
public String getCountOfTradeGoods(@Param("startTime")String startTime, @Param("endTime")String endTime);

public List<GoodsOrder> findOrderInfoByGoodsId(@Param("goodsId")Integer goodsId,@Param("rowsId")Integer rowsId,@Param("pageSize")Integer pageSize);

public List<GoodsOrder> findOrderInfoBySellerId(@Param("sellerId")Integer sellerId, @Param("rowsId")int i, @Param("pageSize")Integer pageSize);

public void sellerSendGoods(@Param("orderId")Integer orderId,@Param("sendCode")String sendCode);

public void buyerTakeGoods(Integer orderId);



*/