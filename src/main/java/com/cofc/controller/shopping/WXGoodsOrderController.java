package com.cofc.controller.shopping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.cofc.pojo.GoodsOrder;
import com.cofc.pojo.UserOrder;
import com.cofc.service.ApplicationCommonService;
import com.cofc.service.GoodsCommonService;
import com.cofc.service.GoodsOrderService;
import com.cofc.service.UserOrderService;
import com.cofc.util.BaseUtil;
import com.cofc.util.JsonUtil;

@Controller
@RequestMapping("/wx/goodsOrder")
public class WXGoodsOrderController extends BaseUtil {
	@Resource
	private GoodsOrderService goService;
	@Resource
	private GoodsCommonService goodsService;
	@Resource
	private ApplicationCommonService appService;
	public static Logger log = Logger.getLogger("WXGoodsOrderController");

	@Autowired
	private UserOrderService userOrderService;

	// 我的订单
	@RequestMapping("/findMyOrder")
	public void findMyGoodsOrder(HttpServletResponse response, Integer userid, Integer orderStatus, Integer pageNo,
			Integer pageSize) {
		int rowsId = (pageNo - 1) * pageSize;
		List<UserOrder> list = userOrderService.findMyOrder(userid, 0, orderStatus, rowsId, pageSize);
		output(response, JsonUtil.buildJson(list));
		/*
		 * 旧版 List<GoodsOrder> goList = goService.findMyGoodsOrder(go, (pageNo -
		 * 1) * pageSize, pageSize); output(response,
		 * JsonUtil.buildJson(goList));
		 */
	}

	// 删除订单
	@RequestMapping("/deleteOrder")
	public void deleteMyGoodsOrder(HttpServletResponse response, UserOrder order) {
		UserOrder gorder = userOrderService.findByID(order.getOrderID());
		if (gorder == null) {
			output(response, JsonUtil.buildFalseJson("1", "该订单已删除或不存在"));
		} else {
			if (!gorder.getUserid().equals(order.getUserid())) {
				output(response, JsonUtil.buildFalseJson("2", "该订单不属于该用户，没有权限删除"));
			} else {
				userOrderService.updateStatus(order.getOrderID(),-4,0,null,null);
				output(response, JsonUtil.buildFalseJson("0", "删除成功!"));
				log.info("用户" + order.getUserid() + "删除商品订单" +gorder.getOrderID() + "成功。");
			}
		}
		
//		if (gorder == null) {
//			output(response, JsonUtil.buildFalseJson("2", "该订单已删除或不存在"));
//		} else {
//			if (gorder.getUserId() != go.getUserId()) {
//				output(response, JsonUtil.buildFalseJson("3", "该订单不属于该用户，没有权限删除"));
//			} else {
//				try {
//					goService.deleteGoodsOrder(go);
//					output(response, JsonUtil.buildFalseJson("0", "删除成功!"));
//				} catch (Exception e) {
//					output(response, JsonUtil.buildFalseJson("1", "删除失败!"));
//					log.info("用户" + go.getUserId() + "删除商品订单" + go.getPayId() + "失败，失败原因" + e);
//				}
//			}
//		}
	}

	// 获取订单
	@RequestMapping("/getOrder")
	public void getGoodsOrder(HttpServletResponse response, Integer payId) {
		GoodsOrder gorder = goService.getOrderByPayId(payId);
		List<GoodsOrder> goList = new ArrayList<GoodsOrder>();
		goList.add(gorder);
		output(response, JsonUtil.buildJson(goList));
	}

	// 根据商品id返回该商品关联的订单信息（包含购买用户信息）
	@RequestMapping("/orderBygid")
	public void getOrderByGoodsId(HttpServletResponse response, Integer goodsId,Integer payStatus,Integer pageNo, Integer pageSize) {
		List<Map> orderList = goService.findOrderInfoByGoodsId2(goodsId,payStatus,(pageNo - 1) * pageSize, pageSize);
		output(response, JsonUtil.buildJson(orderList));
	}

	// 根据发布者id返回所有出售的订单（包含购买者信息）
	@RequestMapping("/orderBysid")
	public void getOrderBySellerId(HttpServletResponse response, Integer sellerId, Integer pageNo, Integer pageSize) {
		List<GoodsOrder> orderList = goService.findOrderInfoBySellerId(sellerId, (pageNo - 1) * pageSize, pageSize);
		output(response, JsonUtil.buildJson(orderList));
	}

	// 根据商家查看所有订单信息(用户查看订单，多传一个用户Id)
	@RequestMapping("/businessOrdersList")
	public void businessOrdersList(HttpServletResponse response, Integer pageNo, Integer pageSize, Integer loginPlat,
			Integer userId,Integer orderStatus) {
		List<UserOrder> userList = userOrderService.findUserloginPlatList(loginPlat, (pageNo - 1) * pageSize, pageSize,
				userId,orderStatus);
		output(response, JsonUtil.buildJson(userList));
	}
    
	/**
	 * 唯爱家pc端我的订单
	 * @param response
	 * @param pageNo
	 * @param pageSize
	 * @param loginPlat
	 * @param userId
	 * @param orderStatus
	 */
	@RequestMapping("/vijbusinessOrdersList")
	public void vijbusinessOrdersList(HttpServletResponse response, Integer pageNo, Integer pageSize, Integer loginPlat,
			Integer userId,Integer orderStatus) {
		int totalCount;
		List<UserOrder> userList;
		if(orderStatus==1){
			totalCount = userOrderService.getUserLoginPlatCount(loginPlat, userId, null);
			userList = userOrderService.findUserloginPlatList(loginPlat, (pageNo - 1) * pageSize, pageSize,
					userId,null);
		}
		else{
			totalCount = userOrderService.getUserLoginPlatCount(loginPlat, userId, orderStatus);
			userList = userOrderService.findUserloginPlatList(loginPlat, (pageNo - 1) * pageSize, pageSize,
					userId,orderStatus);
		}
		output(response,JsonUtil.buildJsonByTotalCount(userList, totalCount));
	}
	// 卖家发货
	@RequestMapping("/sendGoods")
	public void sellerSendGoodsByOrder(HttpServletResponse response, Integer sellerId, Integer orderId,
			String sendCode) {
		UserOrder order = userOrderService.findByID(orderId);
		if (order == null) {
			output(response, JsonUtil.buildFalseJson("2", "该订单不存在!"));
		} else if (order.getOrderStatus() == 0) {
			output(response, JsonUtil.buildFalseJson("5", "该订单未支付，不能发货!"));
		} else {
//			ApplicationCommon app = appService.getApplicationById(order.getLoginPlat());
//			if (!app.getApplicationOwner().equals(sellerId)) {
//				output(response, JsonUtil.buildFalseJson("3", "您不是卖家,不能进行该操作!"));
//			} else 
				if (StringUtils.isBlank(sendCode)) {
				output(response, JsonUtil.buildFalseJson("4", "请输入快递单号。"));
			} else {
				try {
					userOrderService.updateStatus(orderId, 10,0,null,null);
					output(response, JsonUtil.buildFalseJson("0", "操作成功!"));
				} catch (Exception e) {
					log.info("用户" + sellerId + "对订单" + orderId + "发货失败，失败原因" + e);
					output(response, JsonUtil.buildFalseJson("1", "操作失败!"));
				}
			}
		}
	}
	// 卖家发货
		@RequestMapping("/dealGoods")
		public void dealGoodsByOrder(HttpServletResponse response, Integer sellerId, Integer orderId,
				String sendCode) {
			UserOrder order = userOrderService.findByID(orderId);
			if (order == null) {
				output(response, JsonUtil.buildFalseJson("2", "该订单不存在!"));
			} else if (order.getOrderStatus() == 0) {
				output(response, JsonUtil.buildFalseJson("5", "该订单未支付，不能发货!"));
			} else {
					try {
						userOrderService.updateStatus(orderId, 10,0,null,null);
						output(response, JsonUtil.buildFalseJson("0", "操作成功!"));
					} catch (Exception e) {
						log.info("用户" + sellerId + "对订单" + orderId + "发货失败，失败原因" + e);
						output(response, JsonUtil.buildFalseJson("1", "操作失败!"));
					}
			}
		}
	// public void sellerSendGoodsByOrder(HttpServletResponse response, Integer
	// sellerId, Integer orderId,
	// String sendCode) {
	// GoodsOrder order = goService.getOrderByPayId(orderId);
	// if (order == null) {
	// output(response, JsonUtil.buildFalseJson("2", "该订单不存在!"));
	// } else if(order.getPayStatus()!=1){
	// output(response, JsonUtil.buildFalseJson("5", "该订单未支付，不能发货!"));
	// }else{
	// GoodsCommon goods = goodsService.getGoodsById(order.getGoodsId());
	// if (goods.getPublisherId() != sellerId) {
	// output(response, JsonUtil.buildFalseJson("3", "您不是卖家,不能进行该操作!"));
	// } else if (StringUtils.isBlank(sendCode)) {
	// output(response, JsonUtil.buildFalseJson("4", "请输入快递单号。"));
	// } else {
	// try {
	// goService.sellerSendGoods(orderId, sendCode);
	// output(response, JsonUtil.buildFalseJson("0", "操作成功!"));
	// } catch (Exception e) {
	// log.info("用户" + sellerId + "对订单" + orderId + "发货失败，失败原因" + e);
	// output(response, JsonUtil.buildFalseJson("1", "操作失败!"));
	// }
	// }
	// }
	// }

	// 确认收货
	@RequestMapping("/takeGoods")
	public void userTakeGoods(HttpServletResponse response, Integer userId, Integer orderId) {

		if (userId == null || userId <= 0) {
			output(response, JsonUtil.buildFalseJson("1", "userId非法"));
			return;
		}

		if (orderId == null || orderId <= 0) {
			output(response, JsonUtil.buildFalseJson("1", "orderId非法"));
			return;
		}

		UserOrder order = userOrderService.findByID(orderId);
		if (order == null) {
			output(response, JsonUtil.buildFalseJson("1", "订单不存在"));
			return;
		}

		if (!order.getUserid().equals(userId)) {
			output(response, JsonUtil.buildFalseJson("1", "您不是该订单的买家，无权限收货"));
			return;
		}

		if (order.getOrderStatus() != UserOrder.STATUS_WAIT_CONFIRM_RECEIPT) {
			output(response, JsonUtil.buildFalseJson("1", "该订单不在可收货状态"));
			return;
		}

		try {
			userOrderService.updateStatus(orderId, UserOrder.STATUS_CONFIRM_RECEIPT,0,null,null); // 确认收货
			output(response, JsonUtil.buildFalseJson("0", "收货成功"));
		} catch (Exception e) {
			e.printStackTrace();
			log.info("用户" + userId + "对订单" + orderId + "收货失败，失败原因" + e);
			output(response, JsonUtil.buildFalseJson("1", "收货失败"));
		}
		/*
		 * GoodsOrder currOrder = goService.getOrderByPayId(orderId); if
		 * (currOrder == null) { output(response, JsonUtil.buildFalseJson("3",
		 * "该订单不存在或已删除")); } else if(currOrder.getPayStatus()!=2){
		 * output(response, JsonUtil.buildFalseJson("4", "该订单不在可收货状态")); }else{
		 * if (currOrder.getUserId() != userId) { output(response,
		 * JsonUtil.buildFalseJson("2", "您不是该订单的买家，无权限收货")); } else { try {
		 * goService.buyerTakeGoods(orderId); output(response,
		 * JsonUtil.buildFalseJson("0", "收货成功")); } catch (Exception e) {
		 * log.info("用户" + userId + "对订单" + orderId + "收货失败，失败原因" + e);
		 * output(response, JsonUtil.buildFalseJson("1", "收货失败")); } } }
		 */
	}
	/**
	 * 取消订单
	 * @param response
	 * @param orderId
	 * @param userId
	 */
	@RequestMapping("/quitgoodorder")
	public void quitGoodOrder(HttpServletResponse response,Integer orderId,Integer userId){
		if (userId == null || userId <= 0) {
			output(response, JsonUtil.buildFalseJson("1", "userId非法"));
			return;
		}

		if (orderId == null || orderId <= 0) {
			output(response, JsonUtil.buildFalseJson("1", "orderId非法"));
			return;
		}

		UserOrder order = userOrderService.findByID(orderId);
		if (order == null) {
			output(response, JsonUtil.buildFalseJson("1", "订单不存在"));
			return;
		}
		if (!order.getUserid().equals(userId)) {
			output(response, JsonUtil.buildFalseJson("1", "您不是该订单的买家，无权限取消该订单"));
			return;
		}
		if (order.getOrderStatus() != UserOrder.STATUS_WAIT_PAY) {
			output(response, JsonUtil.buildFalseJson("1", "该订单不在可取消状态"));
			return;
		}
		
		try {
			userOrderService.updateStatus(orderId, UserOrder.STATUS_QUIT_ORDER,0,null,null); // 确认取消
			output(response, JsonUtil.buildFalseJson("0", "取消订单成功"));
		} catch (Exception e) {
			e.printStackTrace();
			log.info("用户" + userId + "对订单" + orderId + "取消订单失败，失败原因" + e);
			output(response, JsonUtil.buildFalseJson("1", "取消订单失败"));
		}
	}
	/**
	 * 删除订单
	 * @param response
	 * @param orderId
	 * @param userId
	 */
	@RequestMapping("/deletegoodorder")
	public void deleteGoodOrder(HttpServletResponse response,Integer orderId,Integer userId){
		if (userId == null || userId <= 0) {
			output(response, JsonUtil.buildFalseJson("1", "userId非法"));
			return;
		}

		if (orderId == null || orderId <= 0) {
			output(response, JsonUtil.buildFalseJson("1", "orderId非法"));
			return;
		}

		UserOrder order = userOrderService.findByID(orderId);
		if (order == null) {
			output(response, JsonUtil.buildFalseJson("1", "订单不存在"));
			return;
		}

		if (!order.getUserid().equals(userId)) {
			output(response, JsonUtil.buildFalseJson("1", "您不是该订单的买家，无权限删除该订单"));
			return;
		}
		if (order.getOrderStatus() != UserOrder.STATUS_QUIT_ORDER) {
			output(response, JsonUtil.buildFalseJson("1", "该订单不在可删除状态"));
			return;
		}
		
		try {
			userOrderService.updateStatus(orderId, UserOrder.STATUS_DELETE_ORDER,0,null,null); // 确认取消
			output(response, JsonUtil.buildFalseJson("0", "删除订单成功"));
		} catch (Exception e) {
			e.printStackTrace();
			log.info("用户" + userId + "对订单" + orderId + "删除订单失败，失败原因" + e);
			output(response, JsonUtil.buildFalseJson("1", "删除订单失败"));
		}
	}
	/**
	 * 退款
	 * @param response
	 * @param orderId
	 * @param userId
	 */
	@RequestMapping("/refundOrder")
	public void refundOrder(HttpServletResponse response,Integer orderId,Integer userId){
		if(userId == null){
			log.info("refundOrder(): userId is null");
			output(response,JsonUtil.buildFalseJson("1", "请先登录"));
		}else{
			if(orderId == null){
				log.info("refundOrder(): orderId is null");
				output(response,JsonUtil.buildFalseJson("1", "非法订单"));
			}else{
				UserOrder order = userOrderService.findByID(orderId);
				if(order == null){
					log.info("refundOrder(): order is null");
					output(response,JsonUtil.buildFalseJson("1", "订单不存在"));
				}else{
					if(order.getUserid().equals(userId)){
						Integer orderStatus = order.getOrderStatus();
						if(orderStatus != null){
							if(orderStatus >= 5 || orderStatus <= 16){ //这一些状态可以进行退款
								 UserOrder o = new UserOrder();
								 o.setOrderID(orderId);
								 o.setOrderStatus(-2);
								 userOrderService.updateUserOrder(o);
								 output(response,JsonUtil.buildFalseJson("0", "操作成功"));
							}else{
								log.info("refundOrder():orderStatus == "+orderStatus+",订单不在可退款状态");
								output(response,JsonUtil.buildFalseJson("1", "订单不在可退款状态,不能退款"));
							}
						}else{
							log.info("refundOrder():orderStatus is null");
							output(response,JsonUtil.buildFalseJson("1", "订单状态未知"));
						}
					}else{
						log.info("refundOrder():userId="+userId+" and orderUserId="+order.getUserid());
						output(response,JsonUtil.buildFalseJson("1", "当前用户不是该订单的拥有者"));
					}
				}
			}
		}
	}
}
