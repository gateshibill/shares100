package com.cofc.controller.shopping;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cofc.pojo.ApplicationCommon;
import com.cofc.pojo.GoodsCommon;
import com.cofc.pojo.GoodsOrder;
import com.cofc.pojo.GroupUsers;
import com.cofc.pojo.OrderVoice;
import com.cofc.pojo.UserCommon;
import com.cofc.pojo.UserMessage;
import com.cofc.pojo.UserOrder;
import com.cofc.pojo.UserShoppingAddress;
import com.cofc.pojo.UserWalletDiary;
import com.cofc.service.GoodsOrderService;
import com.cofc.service.GroupUsersService;
import com.cofc.service.OrderVoiceService;
import com.cofc.service.UserCommonService;
import com.cofc.service.UserMessageService;
import com.cofc.service.UserOrderService;
import com.cofc.service.UserShoppingAddressService;
import com.cofc.service.UserShoppingCarService;
import com.cofc.service.UserWalletDiaryService;
import com.cofc.service.ApplicationCommonService;
import com.cofc.service.GoodsCommonService;
import com.cofc.util.BaseUtil;
import com.cofc.util.JsonUtil;
import com.cofc.util.MD5Util;
import com.cofc.util.wxpay.WXPreOrderUtil;
import com.cofc.util.wxpay.WeiXinPayConfig;

/**
 * 下单接口
 * 
 * @author chen
 *
 */
@Controller
@RequestMapping("/wx/pay")
public class WXCreateOrderController extends BaseUtil {
	@Resource
	private GoodsOrderService goService;
	@Resource
	private GoodsCommonService goodsService;
	@Resource
	private UserCommonService userService;
	@Resource
	private GroupUsersService guserService;
	@Resource
	private ApplicationCommonService applicationService;
	@Resource
	private UserShoppingAddressService addressService;
	@Resource
	private UserWalletDiaryService udService;
	@Resource
	private OrderVoiceService voiceService; //插进一条语音记录
	@Resource
	private UserMessageService userMessageService;
	public static Logger log = Logger.getLogger("WXCreateOrderController");
	
	@Autowired
	private UserOrderService userOrderService;
	
	@Autowired
	private UserShoppingCarService userShoppingCarService;

	@RequestMapping("/createUserOrder")  //生成订单，下订单。
	public void createUserOrder(HttpServletResponse response, Integer userid, Integer addressId, String goods, 
			Integer loginPlat, String remarks,Integer isOnline,Integer deskId,Integer consumeType,Integer isInvoice,
			String invoiceStr) {		
		if(userid==null||userid<=0){
			output(response, JsonUtil.buildFalseJson("1", "userid不能为空"));
			return;
		}
		if(goods==null||goods.trim().length()==0){
			output(response, JsonUtil.buildFalseJson("3", "商品列表为空"));
			return;
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
		int isExamined = 0; //验证是否买单用会员价
		if(loginPlat == 3){
			GroupUsers gUsers = guserService.comfirmIsJoinThisGroup(userid, loginPlat);
			if(gUsers == null){
				isExamined = 0;
			}else{
				if(gUsers.getIsExamined() == null){
					isExamined = 0;
				}else{
					if(gUsers.getIsExamined() == 1){
						isExamined = 1;
					}else{
						isExamined = 0;
					}
				}
				
			}
		}else{
			isExamined = 0;
		}
		List<Integer> ids = new ArrayList<Integer>();
		List<Integer> carIds = new ArrayList<Integer>();
		double toalFee = 0;
		double toalRealFee = 0;
		double totalgoodsRebate = 0;//返点
		int scoreCount = 0; // 积分
		List<GoodsCommon> goodsList = new ArrayList<GoodsCommon>();
		for(int i=0;i<goodsArray.length();i++){
			JSONObject json = goodsArray.getJSONObject(i);
			int goodsid = json.optInt("goodsid");		
			GoodsCommon gc = goodsService.getGoodsById(goodsid);		
			if(gc==null){
				output(response, JsonUtil.buildFalseJson("5", "商品(" + goodsid + ")不存在!"));
				return;
			}
		    //处理多规格
			try {
				int specId = json.optInt("specId");
				if(specId > 0){
				  gc.setSpecId(specId);
				}
				String specTypeString = json.getString("specTypeString");
				if(!specTypeString.equals("")){
					gc.setSpecTypeString(specTypeString);
				}
			} catch (Exception e) {
				
			}
			int buyNumber = json.optInt("buyNumber");
			if(buyNumber<=0){
				output(response, JsonUtil.buildFalseJson("6", "商品(" + goodsid + ")的购买数量buyNumber需要大于0"));
				return;
			}
			gc.setBuyNumber(buyNumber);
			/*Integer forSaleNumber = userOrderService.getObjectNumberInOrder(gc.getGoodsId(), 0);  //0是商品
			if(forSaleNumber==null){
				forSaleNumber = 0;
			}
			if(gc.getGoodsStock() - forSaleNumber < buyNumber) {
				output(response, JsonUtil.buildFalseJson("7", "该商品库存不足,剩余库存" + (gc.getGoodsStock() - forSaleNumber) + "件!"));
				return;
			}*/
			ids.add(gc.getGoodsId());
			if(gc.getFirstCost() != null){
				toalFee = toalFee + gc.getFirstCost()*buyNumber;
			}
			if(isExamined == 1){
				if(gc.getVipPrice()==0){
				  toalRealFee = toalRealFee + Double.valueOf(gc.getSellPrice())*buyNumber;
				  totalgoodsRebate = totalgoodsRebate + (Double.valueOf(gc.getSellPrice())*buyNumber*Double.valueOf(gc.getGoodsRebate()))/100;
				}else{
				  toalRealFee = toalRealFee + Double.valueOf(gc.getVipPrice())*buyNumber;	
				  totalgoodsRebate = totalgoodsRebate + (Double.valueOf(gc.getVipPrice())*buyNumber*Double.valueOf(gc.getGoodsRebate()))/100;
				}
			}else{
				toalRealFee = toalRealFee + Double.valueOf(gc.getSellPrice())*buyNumber;
				if(gc.getGoodsRebate() != null){
					totalgoodsRebate = totalgoodsRebate + (Double.valueOf(gc.getSellPrice())*buyNumber*Double.valueOf(gc.getGoodsRebate()))/100;
				}
			}
			//特别需要注意的是购物车同时存在积分兑换的商品和需支付的商品，默认都为需支付
			if(gc.getScoreCount() != null){  //积分兑换
				scoreCount = scoreCount + gc.getScoreCount()*buyNumber;
			}
			goodsList.add(gc);
			int carId = json.optInt("carId");
			if(carId>0){
				carIds.add(carId);
			}
			
			//修改库存
			//int kucun = gc.getGoodsStock() - buyNumber;
			//goodsService.updateGoodsStock(gc.getGoodsId(),kucun);
			
			
		}
		UserOrder order = new UserOrder();
		if(ids.size()<=0){
			output(response, JsonUtil.buildFalseJson("8", "没有可添加的商品"));
			return;
		}else if(ids.size()==1){
			order.setObjectID(ids.get(0));
		}else{
			order.setMultiID(StringUtils.join(ids, ","));
		}
		order.setObjects(JsonUtil.stringify(goodsList));
		order.setOrderType(0);  //商品
		order.setUserid(userid);
		order.setNumber(ids.size());
		order.setTotalFee(toalFee);
		order.setRealFee(toalRealFee);
		order.setRebateMoney(totalgoodsRebate);
		Date now = new Date();
		order.setOrderStatus(3);
		order.setCreateTime(now);
		order.setLoginPlat(loginPlat);
		UserCommon userinfo = userService.getUserByUserId(userid);
		if(consumeType == 1){
			UserShoppingAddress curraddress = addressService.getAddressById(addressId);
			if(curraddress==null){
				output(response, JsonUtil.buildFalseJson("2", "找不到该收货信息"));
				return;
			}else{
				order.setConsignee(curraddress.getDeliveryName());
				order.setPhone(curraddress.getDeliveryPhone());
				String province = "";
				String city = "";
				String area = "";
				if(curraddress.getProvince() != null && !curraddress.getProvince().equals("")){
					province = curraddress.getProvince();
				}
				if(curraddress.getCity() != null && !curraddress.getCity().equals("")){
					city = curraddress.getCity();
				}
				if(curraddress.getArea() != null && !curraddress.getArea().equals("")){
					area = curraddress.getArea();
				}
				order.setAddress(province+city+area+curraddress.getShoppingAddress());
			}
		}else{
			if(userinfo.getRealName()== null){
				order.setConsignee(userinfo.getNickName());
			}else{
				order.setConsignee(userinfo.getRealName());
			}
			order.setPhone(userinfo.getPhone());
		}
		if(userinfo != null){
			 if(userinfo.getIntroducer() != null){
				 order.setAgentId(userinfo.getIntroducer()); //代理
			 }
		}
		order.setScoreCount(scoreCount);
		order.setRemarks(remarks);
		order.setIsOnline(isOnline);
		order.setPayStatus(0);
		order.setDeskId(deskId);
		order.setConsumeType(consumeType);
		order.setIsInvoice(isInvoice);
		order.setInvoiceStr(invoiceStr);
		order.setOrderNo(GetOrderNo.getOrderIdByUUId());//生成订单号
		userOrderService.addUserOrder(order);
		Integer orderID = order.getOrderID();
		for(Integer carId : carIds){
			try{
				userShoppingCarService.transfer(carId, now);  //提交订单后，就从购物车中移除。
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		output(response, String.format("{\"code\":0, \"msg\":\"下单成功\", \"orderID\": %s}", orderID));
		/*
		GoodsCommon goods = goodsService.getGoodsById(go.getGoodsId());
		if(goods==null){
			output(response, JsonUtil.buildFalseJson("1", "该商品不存在!"));
			return;
		}
		int forSaleNumber = userOrderService.getObjectNumberInOrder(goods.getGoodsId(), 0);  //0是商品
		if(goods.getGoodsStock() - forSaleNumber < go.getPayNumber()) {
			output(response, JsonUtil.buildFalseJson("2", "该商品库存不足,剩余库存" + (goods.getGoodsStock() - forSaleNumber) + "件!"));
			return;
		}
		
		Date now = new Date();
		UserOrder order = new UserOrder();
		order.setObjectID(goods.getGoodsId());
		order.setOrderType(0);  //商品
		order.setUserid(go.getUserId());
		order.setNumber(go.getPayNumber());
		order.setTotalFee(goods.getFirstCost()*go.getPayNumber());
		order.setRealFee(goods.getSellPrice()*go.getPayNumber());
		order.setOrderStatus(3);
		order.setCreateTime(now);
		order.setLoginPlat(go.getLoginPlat());
		order.setConsignee(curraddress.getDeliveryName());
		order.setPhone(curraddress.getDeliveryPhone());
		order.setAddress(curraddress.getShoppingAddress());
		userOrderService.addUserOrder(order);
		output(response, JsonUtil.buildSuccessJson("0", "下单成功"));*/
	}
	
	
	// 下单
	@RequestMapping("/createPreOrder")
	public void createPreparePayOrder(HttpServletResponse response, GoodsOrder go, Integer addressId) {
/*		
		if(go.getUserId()<=0){
			output(response, JsonUtil.buildFalseJson("4", "userid不能为空"));
			return;
		}
		
		UserShoppingAddress curraddress = addressService.getAddressById(addressId);
		if(curraddress==null){
			output(response, JsonUtil.buildFalseJson("3", "找不到该收货信息"));
			return;
		}
		GoodsCommon goods = goodsService.getGoodsById(go.getGoodsId());
		if(goods==null){
			output(response, JsonUtil.buildFalseJson("1", "该商品不存在!"));
			return;
		}
		int forSaleNumber = userOrderService.getObjectNumberInOrder(goods.getGoodsId(), 0);  //0是商品
		if(goods.getGoodsStock() - forSaleNumber < go.getPayNumber()) {
			output(response, JsonUtil.buildFalseJson("2", "该商品库存不足,剩余库存" + (goods.getGoodsStock() - forSaleNumber) + "件!"));
			return;
		}
		
		Date now = new Date();
		UserOrder order = new UserOrder();
		order.setObjectID(goods.getGoodsId());
		order.setOrderType(0);  //商品
		order.setUserid(go.getUserId());
		order.setNumber(go.getPayNumber());
		order.setTotalFee(goods.getFirstCost()*go.getPayNumber());
		order.setRealFee(goods.getSellPrice()*go.getPayNumber());
		order.setOrderStatus(3);
		order.setCreateTime(now);
		order.setLoginPlat(go.getLoginPlat());
		order.setConsignee(curraddress.getDeliveryName());
		order.setPhone(curraddress.getDeliveryPhone());
		order.setAddress(curraddress.getShoppingAddress());
		userOrderService.addUserOrder(order);
		output(response, JsonUtil.buildSuccessJson("0", "下单成功"));
*/
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		UserShoppingAddress curraddress = addressService.getAddressById(addressId);
		if (curraddress == null) {
			output(response, JsonUtil.buildFalseJson("3", "找不到该收货信息"));
		} else {
			GoodsCommon goods = goodsService.getGoodsById(go.getGoodsId());
			List<GoodsOrder> thisGOrderList = goService.findThisGoodsOrder(go.getGoodsId());
			int nopayCount = 0;
			for (GoodsOrder order : thisGOrderList) {
				nopayCount += order.getPayNumber();
			}
			log.info("商品ID为" + go.getGoodsId() + "的商品未支付的总数是" + nopayCount + "件!");
			if (goods == null) {
				output(response, JsonUtil.buildFalseJson("1", "该商品不存在!"));
			} else {
				if (goods.getGoodsStock() - nopayCount < go.getPayNumber()) {
					output(response,
							JsonUtil.buildFalseJson("2", "该商品库存不足,剩余库存" + (goods.getGoodsStock() - nopayCount) + "件!"));
				} else {
					Date now = new Date();
					go.setCreateTime(now);
					go.setRealFee(0.00);
					go.setPayStatus(0);
					go.setTotalFee(Double.valueOf(goods.getSellPrice()) * go.getPayNumber());
					go.setIsSend(0);
					go.setDeliveryName(curraddress.getDeliveryName());
					go.setDeliveryPhone(curraddress.getDeliveryPhone());
					go.setPostCode(curraddress.getPostCode());
					go.setShoppingAddress(curraddress.getShoppingAddress());
					int new_orderID = addUserOrder(goods, curraddress, go);
					go.setUserOrderID(new_orderID);
					goService.addNewGoodsOrder(go);
					go.setOutTradeNumber(String.valueOf(go.getPayId()) + sdf.format(now));
					goService.insertOutTradeNo(go);
					List<GoodsOrder> goList = new ArrayList<GoodsOrder>();
					goList.add(go);
					output(response, JsonUtil.buildJson(goList));
				}
			}
		}
	}
	
	private int addUserOrder(GoodsCommon goods, UserShoppingAddress curraddress,  GoodsOrder go){  //这个是新表
		Date now = new Date();
		UserOrder order = new UserOrder();
		order.setObjects("[" + JsonUtil.stringify(goods) + "]");
		order.setObjectID(goods.getGoodsId());
		order.setOrderType(0);  //商品
		order.setUserid(go.getUserId());
		order.setNumber(go.getPayNumber());
		order.setTotalFee(goods.getFirstCost()*go.getPayNumber());
		order.setRealFee(Double.valueOf(goods.getSellPrice())*go.getPayNumber());
		order.setOrderStatus(3);
		order.setCreateTime(now);
		order.setLoginPlat(go.getLoginPlat());
		order.setConsignee(curraddress.getDeliveryName());
		order.setPhone(curraddress.getDeliveryPhone());
		order.setAddress(curraddress.getShoppingAddress());
		userOrderService.addUserOrder(order);
		return order.getOrderID();
	}

	// 微信统一下单
	@RequestMapping("/createOrder")
	public void createPayOrder(HttpServletRequest request, HttpServletResponse response, String userIp, int prePayId,
			String payName) throws Exception {
		WeiXinPayConfig config = new WeiXinPayConfig();
		String contextPath = request.getContextPath();// 项目名
		String port = request.getServerPort() == 80 ? "" : ":" + request.getServerPort();
		String weburl = request.getScheme() + "://" + request.getServerName() + port + contextPath;
		config.setNotify_url(weburl + "/wx/weixinPayNotify/notify.do");
		GoodsOrder gorder = new GoodsOrder();
		double fee = 0.00;
		int userId = 0;
		gorder = goService.getOrderByPayId(prePayId);
		if (gorder != null) {
			fee = gorder.getTotalFee();
			userId = gorder.getUserId();
			UserCommon shanghuiUser = userService.getUserByUserId(userId);
			ApplicationCommon currapp = applicationService.getApplicationById(gorder.getLoginPlat());
			if (currapp.getSmallRoutine() == null) {
				config.setAppid(currapp.getAppId());
				config.setMch_id(currapp.getMchid());
			} else {
				ApplicationCommon parentapp = applicationService.getApplicationById(currapp.getSmallRoutine());
				config.setAppid(parentapp.getAppId());
				config.setMch_id(parentapp.getMchid());
			}
			if (gorder.getPayStatus() == 0) {
				Map<String, String> map = new HashMap<String, String>();
				map = WXPreOrderUtil.getPrepayInfo(gorder.getOutTradeNumber(), shanghuiUser.getOpenId(), fee,
						userIp, payName, config);
				String return_code = map.get("return_code");
				if (StringUtils.isNotBlank(return_code) && return_code.equals("SUCCESS")) {
					String return_msg = map.get("return_msg");
					if (StringUtils.isBlank(return_msg) && !("OK").equals(return_msg)) {
						output(response, JsonUtil.buildFalseJson("2", "统一下单错误,微信返回信息异常!"));
					} else {
						String prepayId = map.get("prepay_id");// 微信返回的支付交易会话ID
						String appId = map.get("appid");// 应用ID
						String partnerId = map.get("mch_id");// 微信支付分配的商户号
						String nonceStr = map.get("nonce_str");// 随机字符串
						Date nowTime = new Date();
						long timeStamp = (nowTime.getTime() / 1000);
						String sign = "appId=" + appId + "&" + "nonceStr=" + nonceStr + "&" + "package=prepay_id="
								+ prepayId + "&signType=MD5&" + "timeStamp=" + String.valueOf(timeStamp);
						String key = config.getApiKey();
						String SignTemp = sign + "&key=" + key;
						// System.out.println(sign);
						// System.out.println(SignTemp);
						String paySign = MD5Util.MD5Encode(SignTemp, "UTF-8").toUpperCase();
						Map<String, Object> wxMap = new HashMap<String, Object>();
						wxMap.put("appId", appId);
						wxMap.put("partnerId", partnerId);
						wxMap.put("prepayId", prepayId);
						wxMap.put("nonceStr", nonceStr);
						wxMap.put("timeStamp", String.valueOf(timeStamp));
						wxMap.put("paySign", paySign);
						// 发送通知前端
						String susscesJson = JsonUtil.MapToJson(wxMap);
						System.out.println(susscesJson + "-===========================");
						output(response, susscesJson);
						log.info(prePayId + "下单成功,订单号" + prepayId + "!");
					}
				} else {
					output(response, JsonUtil.buildFalseJson("3", "统一下单失败！"));
					log.info(prePayId + "下单失败!");
				}
			} else {
				output(response, JsonUtil.buildFalseJson("4", "您已支付，请勿重复支付!"));
			}
		} else {
			output(response, JsonUtil.buildFalseJson("1", "订单不存在!"));
		}
	}
	
	// 微信统一下单
	@RequestMapping("/fetchPayInfo")
	public void fetchPayInfo(HttpServletRequest request, HttpServletResponse response, String userIp, int orderID,
			String payName,int ticketMoney) throws Exception {
		WeiXinPayConfig config = new WeiXinPayConfig();
		String contextPath = request.getContextPath();// 项目名
		String port = request.getServerPort() == 80 ? "" : ":" + request.getServerPort();
		String weburl = request.getScheme() + "://" + request.getServerName() + port + contextPath;
		double fee = 0.00;
		int userId = 0;
		UserOrder order = userOrderService.findByID(orderID);
		if (order != null) {
			fee = order.getRealFee();
			if(ticketMoney > 0){ //减去优惠券
				fee = fee - ticketMoney;
			}
			userId = order.getUserid();
			UserCommon shanghuiUser = userService.getUserByUserId(userId);
			ApplicationCommon currapp = applicationService.getApplicationById(order.getLoginPlat());
			if (currapp.getSmallRoutine() == null) {
				config.setAppid(currapp.getAppId());
				config.setMch_id(currapp.getMchid());
				config.setApiKey(currapp.getApiKey());
			} else {
				ApplicationCommon parentapp = applicationService.getApplicationById(currapp.getSmallRoutine());
				config.setAppid(parentapp.getAppId());
				config.setMch_id(parentapp.getMchid());
				config.setApiKey(parentapp.getApiKey());
			}
			config.setNotify_url(weburl + "/wx/weixinPayNotify/notify2.do");
			if (order.getOrderStatus() == 3) {
				log.info("订单号-"+order.getOrderNo());
				Map<String, String> map = WXPreOrderUtil.getPrepayInfo(order.getOrderNo()+"", shanghuiUser.getOpenId(), fee,
						userIp, payName, config);
				String return_code = map.get("return_code");
				if (StringUtils.isNotBlank(return_code) && return_code.equals("SUCCESS")) {
					String return_msg = map.get("return_msg");
					if (StringUtils.isBlank(return_msg) && !("OK").equals(return_msg)) {
						output(response, JsonUtil.buildFalseJson("2", "统一下单错误,微信返回信息异常!"));
					} else {
						String prepayId = map.get("prepay_id");// 微信返回的支付交易会话ID
						String appId = map.get("appid");// 应用ID
						String partnerId = map.get("mch_id");// 微信支付分配的商户号
						String nonceStr = map.get("nonce_str");// 随机字符串
						Date nowTime = new Date();
						long timeStamp = (nowTime.getTime() / 1000);
						String sign = "appId=" + appId + "&" + "nonceStr=" + nonceStr + "&" + "package=prepay_id="
								+ prepayId + "&signType=MD5&" + "timeStamp=" + String.valueOf(timeStamp);
						String key = config.getApiKey();
						String SignTemp = sign + "&key=" + key;
						// System.out.println(sign);
						// System.out.println(SignTemp);
						String paySign = MD5Util.MD5Encode(SignTemp, "UTF-8").toUpperCase();
						Map<String, Object> wxMap = new HashMap<String, Object>();
						wxMap.put("appId", appId);
						wxMap.put("partnerId", partnerId);
						wxMap.put("prepayId", prepayId);
						wxMap.put("nonceStr", nonceStr);
						wxMap.put("timeStamp", String.valueOf(timeStamp));
						wxMap.put("paySign", paySign);
						// 发送通知前端
						String susscesJson = JsonUtil.MapToJson(wxMap);
						System.out.println(susscesJson + "-===========================");
						output(response, susscesJson);
						log.info(order.getOrderNo() + "下单成功,订单号" + prepayId + "!");
					}
				} else {
					output(response, JsonUtil.buildFalseJson("3", "统一下单失败！"));
					log.info(order.getOrderNo() + "下单失败!");
				}
			} else {
				output(response, JsonUtil.buildFalseJson("4", "您已支付，请勿重复支付!"));
			}
		} else {
			output(response, JsonUtil.buildFalseJson("1", "订单不存在!"));
		}
	}
	
	//余额付款
	@RequestMapping("/payforbalance")
	public void payForBalance(HttpServletResponse response,Integer userId,Integer orderId){
		if(userId == null){
			log.info("userId为空");
			output(response,JsonUtil.buildFalseJson("1","该用户不存在"));
		}else{
			if(orderId == null){
				log.info("orderId为空");
				output(response,JsonUtil.buildFalseJson("1","订单不存在或已删除"));
			}else{
				//判断该订单是否存在
				UserOrder order = userOrderService.findByID(orderId);
				if(order == null){
					log.info("订单不存在或已删除");
					output(response,JsonUtil.buildFalseJson("1","订单不存在或已删除"));
				}else{
					if(!order.getUserid().equals(userId)){
						log.info("该条不是你的订单，你无权限支付,请重新下单");
						output(response,JsonUtil.buildFalseJson("1","该条不是你的订单，你无权限支付,请重新下单"));
					}else{
						if(order.getOrderStatus() != 3){
							log.info("该订单处于不可支付状态:orderStatus="+order.getOrderStatus());
							output(response,JsonUtil.buildFalseJson("1","该订单处于不可支付状态"));
						}else{
							if(order.getPayStatus() == 1){
								log.info("该订单已支付，请勿重复支付");
								output(response,JsonUtil.buildFalseJson("1","该订单已支付，请勿重复支付"));
							}else{
								UserCommon user = userService.getUserByUserId(userId);
								if(user.getWalletBalance() < order.getRealFee()){
									log.info("用户:"+user.getNickName()+"余额不足,请充值或换一种支付方式");
									output(response,JsonUtil.buildFalseJson("2","余额不足,请充值或换一种支付方式"));
								}else{
									//处理逻辑
									//1.需要减去用户余额金额，2.需要改变订单状态，3.需要插入钱包日志
									 UserCommon u = new UserCommon();
									 u.setWalletBalance(user.getWalletBalance()-order.getRealFee());
									 u.setUserId(userId);
									 userService.commonInfoUpdate(u); //改变钱包余额
									 try {
										userOrderService.updateStatus(orderId,5,1,2,1);//改变订单状态
										//插入钱包日志
										UserWalletDiary wallet = new UserWalletDiary();
										wallet.setUserId(userId);
										wallet.setLoginPlat(order.getLoginPlat());
										wallet.setCreateTime(new Date());
										wallet.setDiaryDetails("购买商品消费："+order.getRealFee()+"元");
										wallet.setTotalFee(order.getRealFee());
										wallet.setIncomeExpend(0);
										wallet.setDiaryTitle("购买商品");
										udService.addNewDiary(wallet);
										log.info("购买成功");
										
										JSONArray goodsArray = null;
										goodsArray = new JSONArray(order.getObjects());
										for(int i=0;i<goodsArray.length();i++){
											JSONObject json = goodsArray.getJSONObject(i);
											int goodsid = json.optInt("goodsId");
											int buyNumber = json.optInt("buyNumber");
											GoodsCommon gc = goodsService.getGoodsById(goodsid);
											int goodsStock =  gc.getGoodsStock() - buyNumber;
											goodsService.updateGoodsStock(goodsid,goodsStock);
											log.info("修改库存");
										}
										//插入语音提醒
										OrderVoice voice = new OrderVoice();
										voice.setCreateTime(new Date());
										voice.setIsPlay(0);
										voice.setOrderId(orderId);
										voice.setLoginPlat(order.getLoginPlat());
										voiceService.addOrderVoice(voice);
										
										output(response,JsonUtil.buildFalseJson("0","购买成功"));
									 } catch (Exception e) {
										log.info("购买失败，失败原因："+e);
										output(response,JsonUtil.buildFalseJson("1","购买失败"));
									 }
								}
							}
						}
					}
				}
			}
		}
	}
	/**
	 * 积分付款
	 * @param response
	 * @param userId
	 * @param orderId
	 */
	@RequestMapping("/payForScore")
	public void payForScore(HttpServletResponse response,Integer userId,Integer orderId){
		if(userId == null){
			log.info("userId为空");
			output(response,JsonUtil.buildFalseJson("1","该用户不存在"));
		}else{
			if(orderId == null){
				log.info("orderId为空");
				output(response,JsonUtil.buildFalseJson("1","订单不存在或已删除"));
			}else{
				//判断该订单是否存在
				UserOrder order = userOrderService.findByID(orderId);
				if(order == null){
					log.info("订单不存在或已删除");
					output(response,JsonUtil.buildFalseJson("1","订单不存在或已删除"));
				}else{
					if(!order.getUserid().equals(userId)){
						log.info("该条不是你的订单，你无权限支付,请重新下单");
						output(response,JsonUtil.buildFalseJson("1","该条不是你的订单，你无权限支付,请重新下单"));
					}else{
						if(order.getOrderStatus() != 3){
							log.info("该订单处于不可支付状态:orderStatus="+order.getOrderStatus());
							output(response,JsonUtil.buildFalseJson("1","该订单处于不可支付状态"));
						}else{
							if(order.getPayStatus() == 1){
								log.info("该订单已支付，请勿重复支付");
								output(response,JsonUtil.buildFalseJson("1","该订单已支付，请勿重复支付"));
							}else{
								UserCommon user = userService.getUserByUserId(userId);
								if(user.getIntegral() < order.getScoreCount()){
									log.info("用户:"+user.getNickName()+"积分不足，请换一个支付方式");
									output(response,JsonUtil.buildFalseJson("2","积分不足，请换一个支付方式"));
								}else{
									//临时代码：判断是否
								/*	JSONArray goodsArray1 = null;
									goodsArray1 = new JSONArray(order.getObjects());
									boolean  isScore = false;
									for(int i=0;i<goodsArray1.length();i++){
										JSONObject json = goodsArray1.getJSONObject(i);
										int goodsid1 = json.optInt("goodsId");
										GoodsCommon gc1 = goodsService.getGoodsById(goodsid1);
										if(gc1 == null){
											isScore = true;
										}else{
											if(gc1.getScoreCount() == null){
												isScore = true;
											}else{
												 if(gc1.getScoreCount() != 1){
													isScore = true; 
												 }
											}
										}											
									}
									if(isScore){
										output(response,JsonUtil.buildFalseJson("1","该订单存在不能积分兑换的产品"));
										return;
									}*/
									//处理逻辑
									//1.需要减去积分
									 UserCommon u = new UserCommon();
									 //u.setWalletBalance(user.getWalletBalance()-order.getRealFee());
									 u.setIntegral(user.getIntegral()-order.getScoreCount());
									 u.setUserId(userId);
									 userService.commonInfoUpdate(u); //改变积分
									 try {
										userOrderService.updateStatus(orderId,5,1,3,1);//改变订单状态
										//插入钱包日志
										UserWalletDiary wallet = new UserWalletDiary();
										wallet.setUserId(userId);
										wallet.setLoginPlat(order.getLoginPlat());
										wallet.setCreateTime(new Date());
										wallet.setDiaryDetails("购买商品消费："+order.getScoreCount()+"积分");
										wallet.setTotalFee(order.getRealFee());
										wallet.setIncomeExpend(0);
										wallet.setDiaryTitle("购买商品");
										udService.addNewDiary(wallet);
										log.info("购买成功");
										
										JSONArray goodsArray = null;
										goodsArray = new JSONArray(order.getObjects());
										for(int i=0;i<goodsArray.length();i++){
											JSONObject json = goodsArray.getJSONObject(i);
											int goodsid = json.optInt("goodsId");
											int buyNumber = json.optInt("buyNumber");
											GoodsCommon gc = goodsService.getGoodsById(goodsid);
											int goodsStock =  gc.getGoodsStock() - buyNumber;
											goodsService.updateGoodsStock(goodsid,goodsStock);
											log.info("修改库存");
										}
										//插入消耗积分
										UserMessage mes = new UserMessage();
										mes.setContent("购买商品消耗");
										mes.setCreateTime(new Date());
										mes.setIsRead(0);
										mes.setIsRemove(0);
										mes.setLoginPlat(order.getLoginPlat());
										mes.setScore(order.getScoreCount());
										mes.setType(10);
										mes.setUserId(userId);
										userMessageService.addMessage(mes);
										output(response,JsonUtil.buildFalseJson("0","积分购买成功"));
									} catch (Exception e) {
										log.info("购买失败，失败原因："+e);
										output(response,JsonUtil.buildFalseJson("1","积分购买失败"));
									 }
								  }		
							}
						}
					}
				}
			}
		}	
	}
	
}
