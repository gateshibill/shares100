package com.cofc.controller.wallet;

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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cofc.pojo.RechargeRecord;
import com.cofc.pojo.ApplicationCommon;
import com.cofc.pojo.UserCommon;
import com.cofc.service.RechargeRecordService;
import com.cofc.service.ApplicationCommonService;
import com.cofc.service.UserCommonService;
import com.cofc.util.BaseUtil;
import com.cofc.util.JsonUtil;
import com.cofc.util.MD5Util;
import com.cofc.util.wxpay.WXPreOrderUtil;
import com.cofc.util.wxpay.WeiXinPayConfig;

@Controller
@RequestMapping("/wx/recharge")
public class WXUserRechargeController extends BaseUtil {
	@Resource
	private RechargeRecordService apayService;
	@Resource
	private UserCommonService userService;
	@Resource
	private ApplicationCommonService appService;

	public static Logger log = Logger.getLogger("WXUserRechargeController");

	// 下单
	@RequestMapping("/createPayInfo")
	public void createPreparePayOrder(HttpServletResponse response, Integer userId,double totalFee,
			Integer loginPlat) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		RechargeRecord newRecord = new RechargeRecord();
		Date now = new Date();
		newRecord.setCreateTime(now);
		newRecord.setRealFee(0.00);
		newRecord.setPayStatus(0);
		newRecord.setTotalFee(totalFee);
		newRecord.setLoginPlat(loginPlat);
		newRecord.setUserId(userId);
		apayService.userWantToRecharge(newRecord);
		newRecord.setOutTradeNumber(String.valueOf(newRecord.getPayId()) + sdf.format(now));
		apayService.updatePayRecordInfo(newRecord);
		List<RechargeRecord> resultList = new ArrayList<RechargeRecord>();
		resultList.add(newRecord);
		output(response, JsonUtil.buildJson(resultList));
	}

	// 微信统一下单
	@RequestMapping("/pay")
	public void createPayOrder(HttpServletRequest request,HttpServletResponse response, Integer userId, String userIp, int prePayId,
			String payName) throws Exception {
		String contextPath = request.getContextPath();//项目名
		String port = request.getServerPort() == 80 ? "" : ":" + request.getServerPort();
		String weburl = request.getScheme() + "://" + request.getServerName() + port + contextPath;
		WeiXinPayConfig config = new WeiXinPayConfig();
		RechargeRecord newRecord = new RechargeRecord();
		double fee = 0.00;
		newRecord = apayService.getPayRecordByPayId(prePayId);
		if (newRecord != null && newRecord.getUserId().equals(userId)) {
			fee = newRecord.getTotalFee();
			UserCommon payUser = userService.getUserByUserId(userId);
//			ApplicationCommon currapp = appService.getApplicationById(newRecord.getLoginPlat());
			if (newRecord.getPayStatus() == 0) {
				Map<String, String> map = new HashMap<String, String>();
				config.setNotify_url(weburl+"/wx/rechargeNotify/notify.do");
				ApplicationCommon currapp = appService.getApplicationById(newRecord.getLoginPlat());
				if (currapp.getSmallRoutine() == null) {
					config.setAppid(currapp.getAppId());
				} else {
					ApplicationCommon parentapp = appService.getApplicationById(currapp.getSmallRoutine());
					config.setAppid(parentapp.getAppId());
				}
				map = WXPreOrderUtil.getPrepayInfo(newRecord.getOutTradeNumber(), payUser.getOpenId(), fee,
						userIp, payName,config);
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
}
