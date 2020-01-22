package com.cofc.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cofc.pojo.GoodsCommon;
import com.cofc.pojo.UserCommon;
import com.cofc.pojo.UserOrder;
import com.cofc.service.GoodsCommonService;
import com.cofc.service.UserOrderService;
import com.cofc.util.BaseUtil;
import com.cofc.util.JsonUtil;
@Controller
@RequestMapping("/wx/personalpay")
public class PersonalPayController extends BaseUtil{
	@Resource
	private UserOrderService orderService;
	@Resource
	private GoodsCommonService goodsService;
	public static Logger log = Logger.getLogger("PersonalPayController");
	@RequestMapping("/getResult")
	public void getResult(HttpServletRequest request,HttpServletResponse response,Integer shopId,
			double value){
		if(shopId == null){
			log.error("商家不存在，商家标识："+shopId);
			output(response,JsonUtil.buildFalseJson("1","识别不到商家"));
		}else{
			if(value >= 0 ){
				//通过商家id和价格 去查出未支付的订单
                try {
					 //检测符合的订单
                	 UserOrder order = orderService.getOrderByPrice(shopId, value);
                	 if(order == null){
                		 output(response,JsonUtil.buildFalseJson("1","订单不存在")); 
                	 }else{
                		 if(order.getPayStatus() == 1){
                			 output(response,JsonUtil.buildFalseJson("1","订单状态已改"));
                		 }else{
                			 orderService.updateStatus(order.getOrderID(),5,1,1,null);
                             output(response,JsonUtil.buildFalseJson("0","操作成功"));
                		 }
                	 }
				} catch (Exception e) {
					log.error("产生错误,错误原因:"+e.getMessage());
					output(response,JsonUtil.buildFalseJson("1","系统错误"));
				}
				
			}else{
				log.error("价格非法："+value);
				output(response,JsonUtil.buildFalseJson("1","价格非法"));
			}
		}
	}
	/**
	 * 添加订单
	 * @param response
	 * @param order
	 */
//	@RequestMapping("/addOrder")
//	public void addOrder(HttpServletResponse response,Integer goodId,Integer userId,double price){
//		  if(userId == null){
//			  output(response,JsonUtil.buildFalseJson("1","用户不存在"));
//		  }else{	
//			  UserCommon user = new UserCommon();
//			  if(goodId == null){
//				  output(response,JsonUtil.buildFalseJson("1","商品不存在"));
//			  }else{
//				  GoodsCommon good = goodsService.getGoodsById(goodId);
//				  if(good == null){
//					  output(response, JsonUtil.buildFalseJson("1","商品不存在"));
//				  }else{
//					 try {
//						 //检测该条订单是否存在未支付的订单
//						  List<UserOrder> lists = orderService.getNotPayOrder(good.getLoginPlat(),goodId,price,0,3);
//						  if(lists.size() > 0){
//							   for (UserOrder userOrder : lists) {
//								   //更改其他订单状态
//								   orderService.updateStatus(userOrder.getOrderID(), 
//										   -1, 0, 1);
//							   }
//						  }
//						  List<GoodsCommon> goodlist = new ArrayList<GoodsCommon>();
//						  goodlist.add(good);
//						  //插入订单
//						  UserOrder order = new UserOrder();
//						  order.setObjectID(goodId);
//						  //order.setObjects(goodlist.toString());
//						  order.setOrderType(0);
//						  order.setUserid(userId);
//						  order.setNumber(1);
//						  order.setTotalFee(good.getSelledCount());
//						  order.setRealFee(good.getSelledCount());
//						  order.setOrderStatus(3);
//						  order.setUpdateTime(new Date());
//						  order.setCreateTime(new Date());
//						  order.setLoginPlat(good.getLoginPlat());
//						  order.setPayStatus(0);
//						  order.setConsignee(user.getNickName());
//						  order.setPhone(user.getPhone());
//						  orderService.addUserOrder(order);
//						  output(response,JsonUtil.buildFalseJson("0","创建订单成功"));
//					 } catch (Exception e) {
//						log.error("添加订单失败,失败原因:"+e.getMessage());
//						output(response,JsonUtil.buildFalseJson("1","创建订单失败"));
//					 }
//				  } 
//			  }
//		  }
//	}
	//明天优化
	@RequestMapping("/addOrder")
	public void addOrder(HttpServletResponse response,Integer goodId,Integer userId,double price,String goods){
		  if(userId == null){
			  output(response,JsonUtil.buildFalseJson("1","用户不存在"));
		  }else{	
			  UserCommon user = new UserCommon();
			  if(goodId == null){
				  output(response,JsonUtil.buildFalseJson("1","商品不存在"));
			  }else{
				  GoodsCommon good = goodsService.getGoodsById(goodId);
				  if(good == null){
					  output(response, JsonUtil.buildFalseJson("1","商品不存在"));
				  }else{
					 try {
						 //检测该条订单是否存在未支付的订单
						  List<UserOrder> lists = orderService.getNotPayOrder(good.getLoginPlat(),goodId,price,0,3);
						  if(lists.size() > 0){
							   for (UserOrder userOrder : lists) {
								   //更改其他订单状态
								   orderService.updateStatus(userOrder.getOrderID(), 
										   -1, 0, 1,null);
							   }
						  }
						  JSONArray goodsArray = null;
						  try{
								goodsArray = new JSONArray(goods);
							}catch(Exception e){
								e.printStackTrace();
							}
							if(goodsArray==null){
								output(response, JsonUtil.buildFalseJson("4", "goods不符合json格式"));
								return;
						  }
							List<Integer> ids = new ArrayList<Integer>();
							List<Integer> carIds = new ArrayList<Integer>();
							double toalFee = 0;
							double toalRealFee = 0;
						  List<GoodsCommon> goodsList = new ArrayList<GoodsCommon>();
						  for(int i=0;i<goodsArray.length();i++){
								JSONObject json = goodsArray.getJSONObject(i);
								int goodsid = json.optInt("goodsid");
								GoodsCommon gc = goodsService.getGoodsById(goodsid);
								if(gc==null){
									output(response, JsonUtil.buildFalseJson("5", "商品(" + goodsid + ")不存在!"));
									return;
								}
								int buyNumber = json.optInt("buyNumber");
								if(buyNumber<=0){
									output(response, JsonUtil.buildFalseJson("6", "商品(" + goodsid + ")的购买数量buyNumber需要大于0"));
									return;
								}
								gc.setBuyNumber(buyNumber);
								ids.add(gc.getGoodsId());
								toalFee = toalFee + gc.getFirstCost()*buyNumber;					
								toalRealFee = toalRealFee + Double.valueOf(gc.getSellPrice())*buyNumber;
								goodsList.add(gc);
								int carId = json.optInt("carId");
								if(carId>0){
									carIds.add(carId);
								}
								

							}
						  //插入订单
						  UserOrder order = new UserOrder();
						  order.setObjectID(goodId);
						  order.setObjects(JsonUtil.stringify(goodsList));
						  order.setOrderType(0);
						  order.setUserid(userId);
						  order.setNumber(ids.size());
						  order.setTotalFee(good.getSelledCount());
						  order.setRealFee(good.getSelledCount());
						  order.setTotalFee(toalFee);
						  order.setRealFee(toalRealFee);
						  order.setOrderStatus(3);
						  order.setUpdateTime(new Date());
						  order.setCreateTime(new Date());
						  order.setLoginPlat(good.getLoginPlat());
						  order.setPayStatus(0);
						  order.setConsignee(user.getNickName());
						  order.setPhone(user.getPhone());
						  order.setIsOnline(3);
						  orderService.addUserOrder(order);
						  Integer orderId = order.getOrderID();
						  output(response,JsonUtil.buildFalseJson("0",orderId.toString()));
					 } catch (Exception e) {
						log.error("添加订单失败,失败原因:"+e.getMessage());
						output(response,JsonUtil.buildFalseJson("1","创建订单失败"));
					 }
				  } 
			  }
		  }
	}
	@RequestMapping("/checkIsAlreadyOrder")
	public void checkIsAlreadyOrder(HttpServletResponse response,Integer orderId){
		if(orderId == null){
			output(response,JsonUtil.buildFalseJson("1", "未知用户"));
		}else{
				UserOrder order = orderService.getIsNotDealOrder(orderId);
				if(order != null){
					output(response, JsonUtil.buildFalseJson("0","支付成功"));
				}else{
					output(response,JsonUtil.buildFalseJson("1","支付失败"));
				}
			}
	
	}
}
