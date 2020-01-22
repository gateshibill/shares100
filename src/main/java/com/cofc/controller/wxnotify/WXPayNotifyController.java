package com.cofc.controller.wxnotify;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;
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
import com.cofc.pojo.OrderVoice;
import com.cofc.pojo.RechargeOrder;
import com.cofc.pojo.UserCommon;
import com.cofc.pojo.UserOrder;
import com.cofc.pojo.UserOrderException;
import com.cofc.pojo.UserWalletDiary;
import com.cofc.pojo.vij.Project;
import com.cofc.pojo.vij.ProjectOrder;
import com.cofc.service.GoodsOrderService;
import com.cofc.service.OrderVoiceService;
import com.cofc.service.RechargeOrderService;
import com.cofc.service.ApplicationCommonService;
import com.cofc.service.GoodsCommonService;
import com.cofc.service.UserCommonService;
import com.cofc.service.UserOrderService;
import com.cofc.service.UserWalletDiaryService;
import com.cofc.service.WeixinPayResultService;
import com.cofc.service.activity.BrokerageService;
import com.cofc.service.goods.BrokerageGoodsService;
import com.cofc.service.goods.BrokerageUserInviteService;
import com.cofc.service.vij.ProjectOrderService;
import com.cofc.service.vij.ProjectService;
import com.cofc.util.BaseUtil;
import com.cofc.util.wxpay.PayCommonUtil;
import com.cofc.util.wxpay.WXPayUtil;
import com.cofc.util.wxpay.WeiXinPayConfig;
import com.cofc.util.wxpay.WeixinPayResultUtil;

/**
 * 用户支付商品费用-微信回调
 *
 */
@Controller
@RequestMapping("/wx/weixinPayNotify") // 微信支付回调接口
public class WXPayNotifyController extends BaseUtil {
	@Resource
	private WeixinPayResultService wxPayResultService;
	@Resource
	private UserWalletDiaryService udService;
	@Resource
	private GoodsOrderService goService;
	@Resource
	private GoodsCommonService goodsService;
	@Resource
	private UserCommonService uService;
	@Resource
	private ApplicationCommonService appService;
	@Resource
	private RechargeOrderService rechargeService;
	@Autowired
	private UserOrderService userOrderService;
	@Resource
	private OrderVoiceService voiceService; // 插进一条语音记
	@Resource
	private BrokerageGoodsService brokerageGoodsService;
	@Resource
	private BrokerageUserInviteService brokerageUserInviteService;
	@Resource
	private ProjectService projectService;//项目
	@Resource
	private ProjectOrderService projectOrderService;//项目订单
	@Resource
	BrokerageService brokerageService;

	public static Logger log = Logger.getLogger(WXPayNotifyController.class.getName());

	@RequestMapping("/notify")
	public synchronized void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.info("notify()：进入异步回调方法!");
		WeiXinPayConfig config = new WeiXinPayConfig();
		InputStream is = request.getInputStream();
		String body = IOUtils.toString(is, "UTF-8");
		Map<String, String> result = WXPayUtil.xmlToMap(body);

		String resultCode = result.get("result_code");
		if ("SUCCESS".equals(resultCode) && PayCommonUtil.createSign(config, "utf-8", PayCommonUtil.paraFilter(result))
				.equals(result.get("sign"))) {// 异步回掉成功
			log.info("notify()：进入异步回调处理步骤!");
			String outTradeNo = result.get("out_trade_no").substring(0, result.get("out_trade_no").length() - 17);// 商户订单号

			int prepayId = Integer.parseInt(outTradeNo);
			/**
			 * 保存返回的日志，用户资金变动，增加用户钱包日志，增加用户日志
			 */
			// 增加支付返回的字段记录
			log.info(result);
			wxPayResultService.addNewPayResult(WeixinPayResultUtil.addPayResult(result));
			log.info("notify()：返回信息加入微信支付信息日志表成功");
			UserWalletDiary wdiaryP = new UserWalletDiary();
			UserCommon buyer = new UserCommon();
			log.info("notify()：进入支付商品流程!");
			GoodsOrder gOrder = goService.getOrderByPayId(prepayId);
			buyer = uService.getUserByUserId(gOrder.getUserId());
			if (gOrder.getPayStatus() == 0 && gOrder.getRealFee() != gOrder.getTotalFee()) {
				GoodsCommon com = goodsService.getGoodsById(gOrder.getGoodsId());// 商品表
				com.setUpdateTime(new Date());
				com.setSelledCount(com.getSelledCount() + gOrder.getPayNumber());
				com.setGoodsStock(com.getGoodsStock() - gOrder.getPayNumber());
				if (com.getGoodsStock() <= 0) {// 商品库存小于等于0，自动下架
					com.setIsSelled(0);
				}
				goodsService.updateGoodsInfo(com);
				log.info("notify()：订单" + gOrder.getPayId() + "的商品数据已修改");
				// 把订单表的支付状态改成已支付
				gOrder.setUpdateTime(new Date());
				gOrder.setPayStatus(1);// 已支付
				userOrderService.updateStatus(gOrder.getUserOrderID(), 5, 1, 1,1); // 已付款
				gOrder.setRealFee(Double.parseDouble(result.get("total_fee")) / 100);
				try {
					goService.userPayOrder(gOrder);
				} catch (Exception e) {
					log.info(e);
				}
				log.info("notify()：订单" + gOrder.getPayId() + "已改为已支付");
				// 给支付用户增加支付成功的日志
				wdiaryP.setCreateTime(new Date());
				wdiaryP.setTotalFee(gOrder.getRealFee());
				wdiaryP.setUserId(gOrder.getUserId());
				wdiaryP.setGoodsId(gOrder.getGoodsId());
				wdiaryP.setIncomeExpend(0);// 支出
				wdiaryP.setLoginPlat(gOrder.getLoginPlat());
				wdiaryP.setDiaryDetails("用户" + buyer.getNickName() + "购买" + gOrder.getPayNumber() + "件商品【"
						+ com.getGoodsId() + "--" + com.getGoodsName() + "】共消费" + gOrder.getRealFee() + "元");
				wdiaryP.setDiaryTitle("购买" + gOrder.getPayNumber() + "件【" + com.getGoodsName() + "】");
				udService.addNewDiary(wdiaryP);
				log.info("notify()：订单" + gOrder.getPayId() + "的支付信息已存入钱包日志");
				UserCommon seller = uService.getUserByUserId(com.getPublisherId());// 该商品的卖家
				log.info("notify()：用户的钱包余额是" + seller.getWalletBalance());
				seller.setWalletBalance(seller.getWalletBalance() + gOrder.getRealFee());
				uService.commonInfoUpdate(seller);
				log.info("notify()："+gOrder.getGoodsId() + "号商品的成本价已经加入了卖家的钱包!");
				UserWalletDiary sellerWDiary = new UserWalletDiary();
				sellerWDiary.setCreateTime(new Date());
				sellerWDiary.setGoodsId(gOrder.getGoodsId());
				sellerWDiary.setDiaryDetails("用户" + buyer.getNickName() + "购买" + gOrder.getPayNumber() + "件商品【"
						+ com.getGoodsId() + "--" + com.getGoodsName() + "】共收入" + gOrder.getRealFee() + "元");
				sellerWDiary.setUserId(seller.getUserId());
				sellerWDiary.setIncomeExpend(1);// 收入
				sellerWDiary.setLoginPlat(gOrder.getLoginPlat());
				sellerWDiary.setDiaryTitle("卖出" + gOrder.getPayNumber() + "件【" + com.getGoodsName() + "】");
				udService.addNewDiary(sellerWDiary);
				log.info("notify()：商家钱包日志添加成功!");
				response.getWriter().write(returnNotify());
			}
			log.info("notify()：回调成功,相关用户的用户操作完成!");
		} else {
			response.getWriter().write(PayCommonUtil.setXML("FAIL", "sorry,pay failed!")); // 告诉微信服务器，支付失败
			log.info("notify()：回调失败,相关用户的用户操作未完成!");
		}
	}

	@RequestMapping("/notify2")
	public synchronized void execute2(HttpServletRequest request, HttpServletResponse response) throws Exception {

		log.info("notify2():进入异步回调方法!");
		// WeiXinPayConfig config = new WeiXinPayConfig();
		// log.info("apikey===="+config.getApiKey()+"appId==="+config.getAppid()+"==mcId==="+config.getMch_id());
		InputStream is = request.getInputStream();
		String body = IOUtils.toString(is, "UTF-8");
		Map<String, String> result = WXPayUtil.xmlToMap(body);

		String resultCode = result.get("result_code");
		if (!"SUCCESS".equals(resultCode)) {
			// 这个可能性很少吧，微信没必要返回错误的结果
			fail(response);
			return;
		}

		log.info(result);
		wxPayResultService.addNewPayResult(WeixinPayResultUtil.addPayResult(result));
		log.info("notify2():返回信息加入微信支付信息日志表成功");

		Date now = new Date();
		String outTradeNo = result.get("out_trade_no");// 商户订单号
		//UserOrder order = userOrderService.findByID(orderID);
		UserOrder order  = userOrderService.getOrderByOrderNo(outTradeNo);
		log.info("notify2():order==" + order);
		if (order == null) {
			// 记录错误信息
			UserOrderException exception = new UserOrderException();
			exception.setType(2);
			exception.setContent("订单(" + outTradeNo + ")不存在");
			exception.setOrderID(outTradeNo);
			exception.setCreateTime(now);
			userOrderService.log(exception);
			log.info("notify2():订单(" + outTradeNo + ")不存在");
			fail(response);
			return;
		}
		WeiXinPayConfig config = new WeiXinPayConfig();
		ApplicationCommon appInfo = appService.getApplicationCommonById(order.getLoginPlat());
		if (appInfo != null) {
			// 如果应用有支付信息，则自己独立
			if (appInfo.getAppId() != null && appInfo.getApiKey() != null && appInfo.getMchid() != null) {
				config.setAppid(appInfo.getAppId());
				config.setApiKey(appInfo.getApiKey());
				config.setMch_id(appInfo.getMchid());
			}
		}
		String sign = PayCommonUtil.createSign(config, "utf-8", PayCommonUtil.paraFilter(result));
		log.info("sign1===" + result.get("sign"));
		log.info("sign2===" + sign);
		if (!sign.equals(result.get("sign"))) {
			// 记录错误信息
			UserOrderException exception = new UserOrderException();
			exception.setType(1);
			exception.setContent("notify2():支付回调信息的签名错误");
			exception.setOrderID(outTradeNo);
			exception.setUserid(order.getUserid());
			exception.setCreateTime(now);
			userOrderService.log(exception);
			log.info("notify2():支付回调信息的签名错误");
			fail(response);
			return;
		}

		String cashFee = result.get("cash_fee");
		String totalFee = result.get("total_fee");
		if (!StringUtils.equals(cashFee, totalFee)) {// 这个也应该很少出现吧
			// 记录错误信息
			UserOrderException exception = new UserOrderException();
			exception.setType(4);
			exception.setContent(String.format("微信返回的现金支付金额(cash_fee=%s)和订单金额(total_fee=%s)不相等", cashFee, totalFee));
			exception.setOrderID(outTradeNo);
			exception.setUserid(order.getUserid());
			exception.setCreateTime(now);
			userOrderService.log(exception);
			log.info(String.format("notify2():微信返回的现金支付金额(cash_fee=%s)和订单金额(total_fee=%s)不相等", cashFee, totalFee));

			fail(response);
			return;
		}

		if (order.getOrderStatus() != 3) {
			// 记录错误信息
			UserOrderException exception = new UserOrderException();
			exception.setType(3);
			exception.setContent("订单状态异常，期望是status=3(待支付)，实际是status=" + order.getOrderStatus());
			exception.setOrderID(outTradeNo);
			exception.setUserid(order.getUserid());
			exception.setCreateTime(now);
			userOrderService.log(exception);
			log.info("notify2():订单状态异常，期望是status=3(待支付)，实际是status=" + order.getOrderStatus());
			fail(response);
			return;
		}

		double pay = Double.valueOf(cashFee);
		if (order.getRealFee() * 100 != pay) {
			// 记录错误信息
			UserOrderException exception = new UserOrderException();
			exception.setType(5);
			exception.setContent(String.format("订单金额(%s)和支付金额(%s)不对", order.getRealFee(), pay * 0.01));
			exception.setOrderID(outTradeNo);
			exception.setUserid(order.getUserid());
			exception.setCreateTime(now);
			userOrderService.log(exception);
			log.info(String.format("notify2():订单金额(%s)和支付金额(%s)不对", order.getRealFee(), pay * 0.01));
			fail(response);
			return;
		}

		log.info("进入异步回调处理步骤!");
		// 修改库存
		JSONArray goodsArray = null;
		goodsArray = new JSONArray(order.getObjects());
		for (int i = 0; i < goodsArray.length(); i++) {
			JSONObject json = goodsArray.getJSONObject(i);
			int goodsid = json.optInt("goodsId");
			int buyNumber = json.optInt("buyNumber");
			GoodsCommon gc = goodsService.getGoodsById(goodsid);
			int goodsStock = gc.getGoodsStock() - buyNumber;
			goodsService.updateGoodsStock(goodsid, goodsStock);
			log.info("notify2():修改库存");
		}
		// 把订单表的支付状态改成已支付
		userOrderService.updateStatus(order.getOrderID(), 5, 1, 1,1);
		log.info("notify2():订单" + order.getOrderID() + "已改为已支付");
		// 插入语音提醒
		OrderVoice voice = new OrderVoice();
		voice.setCreateTime(new Date());
		voice.setIsPlay(0);
		voice.setOrderId(order.getOrderID());
		voice.setLoginPlat(order.getLoginPlat());
		voiceService.addOrderVoice(voice);
		log.info("notify2():回调成功,相关用户的用户操作完成!,支付回调通知："+PayCommonUtil.setXML("SUCCESS", "OK"));
		response.getWriter().write(returnNotify());
	}
	
	
	
	/**
	 * chenxiangyou
	 * 
	 * @param order
	 */
	/*private void calcBrokerage(UserOrder order) {
		int appId = order.getLoginPlat();
		int goodsId = order.getObjectID();
		int userId = order.getUserid();
		BrokerageGoods bg = brokerageGoodsService.getBrokerageGoods(appId, goodsId);
		if (bg == null) {
			log.debug(String.format("appId:%s|goodsId:%s is not brokerage", appId, goodsId));
			return;
		}

		BrokerageUserInvite bui = brokerageUserInviteService.getBrokerageUserInvite(appId, goodsId, userId);
		if (bui != null) {
			Integer fristInviteUserId = bui.getInviteUserId();
			if (fristInviteUserId != null) {// 这个判断有多余，
				Brokerage fristInviteBrokerage = new Brokerage();
				fristInviteBrokerage.setAppId(appId);
				fristInviteBrokerage.setUserId(fristInviteUserId);
				fristInviteBrokerage.setPrice(order.getRealFee() * bg.getFristClass());
				fristInviteBrokerage.setCreateTime(new Date());
				brokerageService.addBrokerage(fristInviteBrokerage);

				// 判断是有二级上级
				BrokerageUserInvite bui2 = brokerageUserInviteService.getBrokerageUserInvite(appId, goodsId,
						fristInviteUserId);
				if (bui2 != null && bg.getSecondClass() > 0) {
					Integer secondInviteUserId = bui2.getInviteUserId();
					if (secondInviteUserId != null) {// 这个判断有多余，
						Brokerage secondInviteBrokerage = new Brokerage();
						secondInviteBrokerage.setAppId(appId);
						secondInviteBrokerage.setUserId(secondInviteUserId);
						secondInviteBrokerage.setPrice(order.getRealFee() * bg.getSecondClass());
						secondInviteBrokerage.setCreateTime(new Date());
						brokerageService.addBrokerage(secondInviteBrokerage);
					}
				}
			}
		}
	}*/

	@RequestMapping("/notifyofrecharge")
	public synchronized void execute3(HttpServletRequest request, HttpServletResponse response) throws Exception {

		log.info("notifyofrecharge():进入异步回调方法!");
		// WeiXinPayConfig config = new WeiXinPayConfig();
		// log.info("apikey===="+config.getApiKey()+"appId==="+config.getAppid()+"==mcId==="+config.getMch_id());
		InputStream is = request.getInputStream();
		String body = IOUtils.toString(is, "UTF-8");
		Map<String, String> result = WXPayUtil.xmlToMap(body);

		String resultCode = result.get("result_code");
		if (!"SUCCESS".equals(resultCode)) {
			// 这个可能性很少吧，微信没必要返回错误的结果
			fail(response);
			return;
		}

		log.info(result);
		wxPayResultService.addNewPayResult(WeixinPayResultUtil.addPayResult(result));
		log.info("notifyofrecharge():返回信息加入微信支付信息日志表成功");

		String outTradeNo = result.get("out_trade_no");// 商户订单号
		int orderID = Integer.parseInt(outTradeNo);
		// UserOrder order = userOrderService.findByID(orderID);
		RechargeOrder order = rechargeService.getlistByRechargeId(orderID);
		log.info("order==" + order);
		if (order == null) {

			log.info("notifyofrecharge():订单(" + orderID + ")不存在");
			fail(response);
			return;
		}
		WeiXinPayConfig config = new WeiXinPayConfig();
		ApplicationCommon appInfo = appService.getApplicationCommonById(order.getLoginPlat());
		if (appInfo != null) {
			// 如果应用有支付信息，则自己独立
			if (appInfo.getAppId() != null && appInfo.getApiKey() != null && appInfo.getMchid() != null) {
				config.setAppid(appInfo.getAppId());
				config.setApiKey(appInfo.getApiKey());
				config.setMch_id(appInfo.getMchid());
			}
		}
		String sign = PayCommonUtil.createSign(config, "utf-8", PayCommonUtil.paraFilter(result));

		if (!sign.equals(result.get("sign"))) {
			// 记录错误信息
			log.info("notifyofrecharge():支付回调信息的签名错误");
			fail(response);
			return;
		}

		String cashFee = result.get("cash_fee");
		String totalFee = result.get("total_fee");
		if (!StringUtils.equals(cashFee, totalFee)) {// 这个也应该很少出现吧
			// 记录错误信息
			log.info(String.format("notifyofrecharge():微信返回的现金支付金额(cash_fee=%s)和订单金额(total_fee=%s)不相等", cashFee, totalFee));

			fail(response);
			return;
		}

		if (order.getRechargeStatus() != 0) {
			// 记录错误信息
			log.info("notifyofrecharge():订单状态异常，期望是status=3(待支付)，实际是status=" + order.getRechargeStatus());
			fail(response);
			return;
		}

		double pay = Double.valueOf(cashFee);
		if (order.getRechargeMoney() * 100 != pay) {
			// 记录错误信息

			log.info(String.format("notifyofrecharge():订单金额(%s)和支付金额(%s)不对", order.getRechargeMoney(), pay * 0.01));
			fail(response);
			return;
		}

		log.info("notifyofrecharge():进入异步回调处理步骤!");

		// 把订单表的支付状态改成已支付
		// userOrderService.updateStatus(order.(),5,1);
		// log.info("订单"+order.getOrderID()+"已改为已支付");
		// 修改用户余额和修改当订单状态
		RechargeOrder r = new RechargeOrder();
		r.setRechargeId(order.getRechargeId());
		r.setPayTime(new Date());
		r.setRechargeStatus(1);
		rechargeService.updateRechargeOrder(r);
		UserCommon userinfo = uService.getUserByUserId(order.getUserId());
		UserCommon u = new UserCommon();
		u.setUserId(order.getUserId());
		double money = 0.0;
		if (order.getSendMoney() > 0) {
			money = order.getRechargeMoney() + order.getSendMoney();
		} else {
			money = order.getRechargeMoney();
		}
		u.setWalletBalance(userinfo.getWalletBalance() + money);
		uService.commonInfoUpdate(u);
		//response.getWriter().write(PayCommonUtil.setXML("SUCCESS", "OK"));
		response.getWriter().write(returnNotify());
		log.info("notifyofrecharge():回调成功,相关用户的用户操作完成!");
	}

	private void fail(HttpServletResponse response) {
		try {
			response.getWriter().write(PayCommonUtil.setXML("FAIL", "sorry,pay failed!")); // 告诉微信服务器，支付失败
			log.info("notifyofrecharge():回调失败,相关用户的用户操作未完成!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 唯爱家APP端支付回调方法
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws ParseException 
	 */
	@RequestMapping("/wxpayappcallback")
	public void wxpayappcallback(HttpServletRequest request,HttpServletResponse response) throws Exception{
		log.info("wxpayappcallback():进入异步回调方法!");
		// WeiXinPayConfig config = new WeiXinPayConfig();
		// log.info("apikey===="+config.getApiKey()+"appId==="+config.getAppid()+"==mcId==="+config.getMch_id());
		InputStream is = request.getInputStream();
		String body = IOUtils.toString(is, "UTF-8");
		Map<String, String> result = WXPayUtil.xmlToMap(body);
		log.info("wxpayappcallback():result"+result);
		String resultCode = result.get("result_code");
		if (!"SUCCESS".equals(resultCode)) {
			// 这个可能性很少吧，微信没必要返回错误的结果
			fail(response);
			return;
		}

		log.info(result);
		wxPayResultService.addNewPayResult(WeixinPayResultUtil.addPayResult(result));
		log.info("wxpayappcallback():返回信息加入微信支付信息日志表成功");

		Date now = new Date();
		String outTradeNo = result.get("out_trade_no");// 商户订单号
		//int orderID = Integer.parseInt(outTradeNo);
		//UserOrder order = userOrderService.findByID(orderID);
		UserOrder order = userOrderService.getOrderByOrderNo(outTradeNo);
		log.info("order==" + order);
		if (order == null) {
			// 记录错误信息
			UserOrderException exception = new UserOrderException();
			exception.setType(2);
			exception.setContent("订单(" + outTradeNo + ")不存在");
			exception.setOrderID(outTradeNo);
			exception.setCreateTime(now);
			userOrderService.log(exception);
			log.info("wxpayappcallback():订单(" + outTradeNo + ")不存在");
			fail(response);
			return;
		}
		WeiXinPayConfig config = new WeiXinPayConfig();
		//三端合一 [pc/小程序/APP]
		if(order.getLoginPlat() == null){
			config.setAppid("wx203885df636adbbf"); //唯爱家开发平台
			config.setMch_id("1518014341");//唯爱家App
			config.setApiKey("ZbI5FW3x2BD5Nr9cb4PrTbnR4E9OLqWg");//唯爱家支付密钥
		}else{
			ApplicationCommon appInfo = appService.getApplicationCommonById(order.getLoginPlat());
			if (appInfo != null) {
				// 如果应用有支付信息，则自己独立
				if (appInfo.getWxOpenAppId() != null && appInfo.getApiKey() != null && appInfo.getMchid() != null) {
					config.setAppid(appInfo.getWxOpenAppId());
					config.setApiKey(appInfo.getApiKey());
					config.setMch_id(appInfo.getMchid());
				}
			}
		}
        String trade_type = "APP";	//APP扫码
        config.setTrade_type(trade_type);  
		String sign = PayCommonUtil.createSign(config, "utf-8", PayCommonUtil.paraFilter(result));
		log.info("wxpayappcallback():sign1===" + result.get("sign"));
		log.info("wxpayappcallback():sign2===" + sign);
		if (!sign.equals(result.get("sign"))) {
			// 记录错误信息
			UserOrderException exception = new UserOrderException();
			exception.setType(1);
			exception.setContent("支付回调信息的签名错误");
			exception.setOrderID(outTradeNo);
			exception.setUserid(order.getUserid());
			exception.setCreateTime(now);
			userOrderService.log(exception);
			log.info("wxpayappcallback():支付回调信息的签名错误");
			fail(response);
			return;
		}

		log.info("wxpayappcallback():进入异步回调处理步骤!");
		// 修改库存
		JSONArray goodsArray = null;
		goodsArray = new JSONArray(order.getObjects());
		for (int i = 0; i < goodsArray.length(); i++) {
			JSONObject json = goodsArray.getJSONObject(i);
			int goodsid = json.optInt("goodsId");
			int buyNumber = json.optInt("buyNumber");
			GoodsCommon gc = goodsService.getGoodsById(goodsid);
			int goodsStock = gc.getGoodsStock() - buyNumber;
			goodsService.updateGoodsStock(goodsid, goodsStock);
			log.info("wxpayappcallback():修改库存");
		}
		// 把订单表的支付状态改成已支付
		userOrderService.updateStatus(order.getOrderID(), 5, 1, 1,3);
		log.info("订单" + order.getOrderID() + "已改为已支付");
		//response.getWriter().write(PayCommonUtil.setXML("SUCCESS", "OK"));
		response.getWriter().write(returnNotify());
		log.info("wxpayappcallback():回调成功,相关用户的用户操作完成!");

		// 佣金计算
		//calcBrokerage(order);
	}
	
	/**
	 * 唯爱家PC端支付回调方法
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws ParseException 
	 */
	@RequestMapping("/wxpaycallback")
	public void wxpaycallback(HttpServletRequest request,HttpServletResponse response) throws Exception{
		log.info("wxpaycallback():进入异步回调方法!");
		InputStream is = request.getInputStream();
		String body = IOUtils.toString(is, "UTF-8");
		Map<String, String> result = WXPayUtil.xmlToMap(body);

		String resultCode = result.get("result_code");
		if (!"SUCCESS".equals(resultCode)) {
			// 这个可能性很少吧，微信没必要返回错误的结果
			fail(response);
			return;
		}

		log.info(result);
		wxPayResultService.addNewPayResult(WeixinPayResultUtil.addPayResult(result));
		log.info("wxpaycallback():返回信息加入微信支付信息日志表成功");

		Date now = new Date();
		String outTradeNo = result.get("out_trade_no");// 商户订单号
		//int orderID = Integer.parseInt(outTradeNo);
		//UserOrder order = userOrderService.findByID(orderID);
		UserOrder order = userOrderService.getOrderByOrderNo(outTradeNo);
		log.info("wxpaycallback():order==" + order);
		if (order == null) {
			// 记录错误信息
			UserOrderException exception = new UserOrderException();
			exception.setType(2);
			exception.setContent("订单(" + outTradeNo + ")不存在");
			exception.setOrderID(outTradeNo);
			exception.setCreateTime(now);
			userOrderService.log(exception);
			log.info("wxpaycallback():订单(" + outTradeNo + ")不存在");
			fail(response);
			return;
		}
		WeiXinPayConfig config = new WeiXinPayConfig();
		if(order.getLoginPlat() == null){
			config.setAppid("wx847931f43f805bf0"); 
			config.setMch_id("1518014341");
			config.setApiKey("ZbI5FW3x2BD5Nr9cb4PrTbnR4E9OLqWg");
		}else{
			ApplicationCommon appInfo = appService.getApplicationCommonById(order.getLoginPlat());
			if (appInfo != null) {
				// 如果应用有支付信息，则自己独立
				if (appInfo.getWxServiceAppId() != null && appInfo.getApiKey() != null && appInfo.getMchid() != null) {
					config.setAppid(appInfo.getWxServiceAppId());
					config.setApiKey(appInfo.getApiKey());
					config.setMch_id(appInfo.getMchid());
				}
			}
		}
        String trade_type = "NATIVE";	//PC扫码
        config.setTrade_type(trade_type);  
		String sign = PayCommonUtil.createSign(config, "utf-8", PayCommonUtil.paraFilter(result));
		log.info("wxpaycallback():sign1===" + result.get("sign"));
		log.info("wxpaycallback():sign2===" + sign);
		if (!sign.equals(result.get("sign"))) {
			// 记录错误信息
			UserOrderException exception = new UserOrderException();
			exception.setType(1);
			exception.setContent("支付回调信息的签名错误");
			exception.setOrderID(outTradeNo);
			exception.setUserid(order.getUserid());
			exception.setCreateTime(now);
			userOrderService.log(exception);
			log.info("wxpaycallback():支付回调信息的签名错误");
			fail(response);
			return;
		}
		log.info("wxpaycallback():进入异步回调处理步骤!");
		// 修改库存
		JSONArray goodsArray = null;
		goodsArray = new JSONArray(order.getObjects());
		for (int i = 0; i < goodsArray.length(); i++) {
			JSONObject json = goodsArray.getJSONObject(i);
			int goodsid = json.optInt("goodsId");
			int buyNumber = json.optInt("buyNumber");
			GoodsCommon gc = goodsService.getGoodsById(goodsid);
			int goodsStock = gc.getGoodsStock() - buyNumber;
			goodsService.updateGoodsStock(goodsid, goodsStock);
			log.info("wxpaycallback():修改库存");
		}
		// 把订单表的支付状态改成已支付
		userOrderService.updateStatus(order.getOrderID(), 5, 1, 1,2);
		log.info("wxpaycallback():订单" + order.getOrderID() + "已改为已支付");
		//response.getWriter().write(PayCommonUtil.setXML("SUCCESS", "OK"));
		response.getWriter().write(returnNotify());
		log.info("wxpaycallback():回调成功,相关用户的用户操作完成!");

		// 佣金计算
		//calcBrokerage(order);
	}
	
	/**
	 * 项目支付定金
	 * @param request
	 * @param response
	 * @throws Exception 
	 */
	@RequestMapping("/payProjectDepositNotify")
	public void payProjectDepositNotify(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			log.info("payProjectDepositNotify():进入异步回调方法!");
			InputStream is = request.getInputStream();
			String body = IOUtils.toString(is, "UTF-8");
			Map<String, String> result = WXPayUtil.xmlToMap(body);
			log.info("payProjectDepositNotify():微信回调返回："+result);
			String resultCode = result.get("result_code");
			if (!"SUCCESS".equals(resultCode)) {
				// 这个可能性很少吧，微信没必要返回错误的结果
				fail(response);
				return;
			}
			wxPayResultService.addNewPayResult(WeixinPayResultUtil.addPayResult(result));
			log.info("payProjectDepositNotify():result="+result);
			String outTradeNo = result.get("out_trade_no");// 商户订单号
			log.info("payProjectDepositNotify():订单号--outTradeNo="+outTradeNo);
			ProjectOrder order = projectOrderService.getProjectOrderByOrderNo(outTradeNo);
			log.info("payProjectDepositNotify():order="+order);
			if (order == null) {
				// 记录错误信息
				fail(response);
				return;
			}
			WeiXinPayConfig config = new WeiXinPayConfig();
			//实现三端统一支付
			config.setAppid("wx203885df636adbbf"); //唯爱家开发平台
			config.setMch_id("1518014341");//唯爱家App
			config.setApiKey("ZbI5FW3x2BD5Nr9cb4PrTbnR4E9OLqWg");//唯爱家支付密钥
	        String trade_type = "APP";	//PC扫码
	        config.setTrade_type(trade_type);  
			String sign = PayCommonUtil.createSign(config, "utf-8", PayCommonUtil.paraFilter(result));
			log.info("payProjectDepositNotify():sign1===" + result.get("sign"));
			log.info("payProjectDepositNotify():sign2===" + sign);
			if (!sign.equals(result.get("sign"))) {
				fail(response);
				return;
			}
			log.info("payProjectDepositNotify():进入异步回调处理步骤!");
			//把项目表的支付状态改成已支付
			Project project = new Project();
			project.setIsPay(1);
			project.setId(order.getProjectId());
			projectService.updateProject(project);
			//把项目订单表修改成已支付
			projectOrderService.updateIsPay(1,order.getPorderId());
			log.info("payProjectDepositNotify():订单" + outTradeNo + "已改为已支付");
			//response.getWriter().write(PayCommonUtil.setXML("SUCCESS", "OK"));
			response.getWriter().write(returnNotify());
			log.info("payProjectDepositNotify():回调成功,相关用户的用户操作完成!");
		} catch (Exception e) {
			log.info("payProjectDepositNotify():回调失败,失败原因："+e.getMessage());
			fail(response);
			return;
		}
	}
	/**
	 * 微信支付支持成功返回通知
	 * @param resultCode
	 * @param resultMsg
	 * @return
	 */
	public static String returnNotify(){
		String xml = "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>";
		return xml;
	}
}
