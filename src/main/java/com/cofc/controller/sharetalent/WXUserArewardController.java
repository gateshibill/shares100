package com.cofc.controller.sharetalent;

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

import com.cofc.pojo.ApplicationCommon;
import com.cofc.pojo.ContractRecord;
import com.cofc.pojo.ShareContract;
import com.cofc.pojo.UserCommon;
import com.cofc.service.ApplicationCommonService;
import com.cofc.service.ContractRecordService;
import com.cofc.service.RechargeRecordService;
import com.cofc.service.ShareContractService;
import com.cofc.service.UserCommonService;
import com.cofc.util.BaseUtil;
import com.cofc.util.JsonUtil;
import com.cofc.util.MD5Util;
import com.cofc.util.wxpay.WXPreOrderUtil;
import com.cofc.util.wxpay.WeiXinPayConfig;

@Controller
@RequestMapping("/wx/userShare")
public class WXUserArewardController extends BaseUtil {

	@Resource
	private RechargeRecordService apayService;
	@Resource
	private UserCommonService userService;
	@Resource
	private ApplicationCommonService appService;
	@Resource
	private ShareContractService contractService;
	@Resource
	private ContractRecordService recordService;

	public static Logger log = Logger.getLogger("WXUserArewardController");

	// 下单
	@RequestMapping("/createPayInfo")
	public void createPayInfo(HttpServletResponse response, Integer userId, Integer partnerId, Integer contractId) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		ShareContract currContract = contractService.getShareContractById(contractId);
		if (currContract == null) {
			output(response, JsonUtil.buildFalseJson("1", "没有找到该共享!"));
		} else {
			ContractRecord record = new ContractRecord();
			Date now = new Date();
			record.setCreateTime(now);
			record.setContractId(contractId);
			record.setRealFee(0.00);
			record.setPayStatus(0);
			if (partnerId != userId) {
				record.setPartnerId(partnerId);
			}
			record.setTotalFee(currContract.getSharedDeposit());
			record.setRewardUser(userId);
			record.setPublisherId(currContract.getSharedUserId());
			record.setLoginPlat(currContract.getLoginPlat());
			recordService.addContractRecord(record);
			record.setOutTradeNumber(String.valueOf(record.getRecordId()) + sdf.format(now));
			System.out.println(String.valueOf(record.getRecordId()) + sdf.format(now));
			recordService.updateContractRecord(record);
			List<ContractRecord> resultList = new ArrayList<ContractRecord>();
			resultList.add(record);
			output(response, JsonUtil.buildJson(resultList));
		}
	}

	// 微信统一下单
	@RequestMapping("/payContract")
	public void createPayOrder(HttpServletRequest request, HttpServletResponse response, Integer userId, int recordId,
			String payName) throws Exception {
		String userIp = request.getRemoteAddr();
		if (userIp == null || "".equals(userIp)) {
			userIp = "127.0.0.1";
		}
		String contextPath = request.getContextPath();// 项目名
		String port = request.getServerPort() == 80 ? "" : ":" + request.getServerPort();
		String weburl = request.getScheme() + "://" + request.getServerName() + port + contextPath;
		WeiXinPayConfig config = new WeiXinPayConfig();
		ContractRecord record = new ContractRecord();
		double fee = 0.00;
		record = recordService.getContractRecordById(recordId);
		if (record != null && record.getRewardUser().equals(userId)) {
			fee = record.getTotalFee();
			UserCommon payUser = userService.getUserByUserId(userId);
			ApplicationCommon currapp = appService.getApplicationById(record.getLoginPlat());
			if (record.getPayStatus() == 0) {
				Map<String, String> map = new HashMap<String, String>();
//				config.setNotify_url("https://www.ailefeigou.com/cofC2/wx/arewardNotify/notify.do");
				config.setNotify_url(weburl + "/wx/arewardNotify/notify.do");
				if (currapp.getParentId() == null) {
					config.setAppid(currapp.getAppId());
				} else {
					ApplicationCommon parentapp = appService.getApplicationById(currapp.getSmallRoutine());
					config.setAppid(parentapp.getAppId());
				}
				map = WXPreOrderUtil.getPrepayInfo(record.getOutTradeNumber(), payUser.getOpenId(), fee, userIp,
						payName, config);
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
						log.info(recordId + "下单成功,订单号" + prepayId + "!");
					}
				} else {
					output(response, JsonUtil.buildFalseJson("3", "统一下单失败！"));
					log.info(recordId + "下单失败!");
				}
			} else {
				output(response, JsonUtil.buildFalseJson("4", "您已支付，请勿重复支付!"));
			}
		} else {
			output(response, JsonUtil.buildFalseJson("1", "订单不存在!"));
		}
	}
	// public static void main(String[] args) {
	// System.out.println(StringUtils.isNotBlank("SUCCESS"));
	// }
}
