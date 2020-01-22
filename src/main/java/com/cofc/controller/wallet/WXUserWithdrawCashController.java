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

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cofc.pojo.ApplicationCommon;
import com.cofc.pojo.UserCommon;
import com.cofc.pojo.UserWalletDiary;
import com.cofc.pojo.UserWithdrawCash;
import com.cofc.pojo.WithdrawReturnCode;
import com.cofc.service.ApplicationCommonService;
import com.cofc.service.AutoWithdrawSetService;
import com.cofc.service.GainSharingCommonService;
import com.cofc.service.UserCommonService;
import com.cofc.service.UserWalletDiaryService;
import com.cofc.service.UserWithdrawCashService;
import com.cofc.service.WithdrawReturnCodeService;
import com.cofc.util.BaseUtil;
import com.cofc.util.JsonUtil;
import com.cofc.util.wxpay.ClientCustomSSL;

/**
 * 用户提现 < 100 元时自动提现
 * @author admin
 *
 */
@Controller
@RequestMapping("/wx/withdrawCash")
public class WXUserWithdrawCashController extends BaseUtil {
	@Resource
	private UserWithdrawCashService withdrawService;
	@Resource
	private UserCommonService userService;
	@Resource
	private ApplicationCommonService appService;
	@Resource
	private AutoWithdrawSetService awSetService;
	@Resource
	private WithdrawReturnCodeService wreturnService;
	@Resource
	private GainSharingCommonService gainSharingService;
	@Resource
	private UserWalletDiaryService walletdiaryService;

	public static Logger log = Logger.getLogger("WXUserWithdrawCashController");

	@RequestMapping("/apply")
	public synchronized void userApplyWithdrawCash(HttpServletRequest request, HttpServletResponse response, Integer userId,
			double fee, Integer loginPlat, String realName) {
		log.info("接收到的信息：userId=" + userId + "fee=" + fee + "loginPlat=" + loginPlat + "realName=" + realName);
		String ip = request.getRemoteAddr();
		if (ip == null || "".equals(ip)) {
			ip = "127.0.0.1";
		}
		UserCommon currUser = userService.getUserByUserId(userId);
		ApplicationCommon currapp = appService.getApplicationById(loginPlat);
		if (currapp.getSmallRoutine() != null) {
			currapp = appService.getApplicationById(currapp.getSmallRoutine());
		}
		Map<String, String> map = new HashMap<String, String>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String path = request.getServletContext().getRealPath("/");
		if (currUser.getLoginPlat().equals(currapp.getApplicationId())) {// 应用id正确
			SimpleDateFormat startSdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
			SimpleDateFormat endSdf = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
			// startSdf.format(new Date());
			// endSdf.format(new Date());
			List<UserWithdrawCash> userwclist = withdrawService.confirmCurrUserHasApplyedToday(currUser.getUserId(),
					currapp.getApplicationId(), startSdf.format(new Date()), endSdf.format(new Date()));
			if (userwclist.size() < 5) {
				if (fee >= 10) {
					if (currUser.getWalletBalance() >= fee) {
						UserWithdrawCash withdraw = new UserWithdrawCash();
						withdraw.setCreateTime(new Date());
						withdraw.setWithdrawFee(fee);
						withdraw.setWithdrawState(1);// 处理中
						withdraw.setWithdrawType(1);// 提现
						withdraw.setUserId(userId);
						withdraw.setLoginPlat(loginPlat);
						withdraw.setWithdrawIp(ip);
						withdraw.setWithdrawRealName(realName);
						try {
							withdrawService.userWantWithdrawCash(withdraw);
							String outTradeNo = withdraw.getWithdrawId() + sdf.format(new Date());
							withdraw.setOutTradeNo(outTradeNo);
							withdrawService.updateWithDrawInfo(withdraw);
							log.info("用户" + userId + "申请提现记录成功!");
							
							//钱包日志加日志
//							log.info("用户" + userId + "申请提现已自动处理！");
							UserWalletDiary witherDiary = new UserWalletDiary();
							witherDiary.setCreateTime(new Date());
							witherDiary.setDiaryTitle("提现");
							witherDiary.setDiaryTitle("您在系统中申请提现" + fee + "元，请等待管理员处理");
							witherDiary.setIncomeExpend(0);// 支出
							witherDiary.setLoginPlat(loginPlat);
							witherDiary.setTotalFee(fee);
							witherDiary.setUserId(userId);
							walletdiaryService.addNewDiary(witherDiary);
							log.info("用户" + userId + "申请提现自动处理后，钱包日志添加成功!");
							
							List<UserWithdrawCash> currrecharge = new ArrayList<UserWithdrawCash>();
							currrecharge.add(withdraw);
							currUser.setWalletBalance(currUser.getWalletBalance() - fee);
							userService.commonInfoUpdate(currUser);
							
							/**
							 * 以下是自动提现操作
							 */
//							AutoWithdrawSet autowset = awSetService.getCurrentApplicationSet(loginPlat);
//							if (autowset != null && autowset.getIsAuto() == 1 && autowset.getAutoFee() >= fee) {
//								GainSharingCommon currsharing = gainSharingService.getTheAcqierementSharing();
//								double realFee = (1 - currsharing.getWithdrawPersent()) * fee;
//								map = ClientCustomSSL.companyPay(path, outTradeNo, currUser.getOpenId(), realFee, ip,
//										realName, currapp.getAppId(), currapp.getMchid());
//								String result = addReturnCodeFunction(map);
//								log.info("返回的result是:" + result);
//								try {
//									if ("SUCCESS".equals(result)) {
//										withdraw.setDealTime(new Date());
//										withdraw.setWithdrawState(2);// 已处理
//										withdrawService.updateWithDrawInfo(withdraw);
//										log.info("用户" + userId + "申请提现已自动处理！");
//										UserWalletDiary witherDiary = new UserWalletDiary();
//										witherDiary.setCreateTime(new Date());
//										witherDiary.setDiaryTitle("提现");
//										witherDiary.setDiaryTitle("您在系统中提现" + fee + "元，系统扣除手续费"
//												+ currsharing.getWithdrawPersent() * 100 + "%");
//										witherDiary.setIncomeExpend(0);// 支出
//										witherDiary.setTotalFee(fee);
//										witherDiary.setUserId(userId);
//										walletdiaryService.addNewDiary(witherDiary);
//										log.info("用户" + userId + "申请提现自动处理后，钱包日志添加成功!");
//									} else {
//										log.info(withdraw.getWithdrawId() + "自动处理提现失败,失败原因" + result);
//
//										output(response, JsonUtil.buildFalseJson("2", "自动处理失败，该次提现已转入人工客服"));
//									}
//								} catch (Exception e) {
//									log.info(withdraw.getWithdrawId() + "自动处理流程异常" + e);
//								}
//							}
							output(response, JsonUtil.buildJson(currrecharge));

						} catch (Exception e) {
							log.info("应用" + loginPlat + "的用户" + userId + "申请提现" + fee + "失败，失败原因" + e);
							output(response, JsonUtil.buildFalseJson("1", "申请失败"));
						}
					} else {
						output(response, JsonUtil.buildFalseJson("3", "您的余额不足"));
					}
				} else {
					output(response, JsonUtil.buildFalseJson("4", "提现金额必须在10元以上"));
				}
			} else {
				output(response, JsonUtil.buildFalseJson("5", "每天只能提现五次哦"));
			}
		} else {
			output(response, JsonUtil.buildFalseJson("6", "您所在的应用和提现应用不一致!"));
		}
	}

	private String addReturnCodeFunction(Map<String, String> map) {
		int id = 0;
		WithdrawReturnCode returncode = new WithdrawReturnCode();
		try {

			returncode.setDeviceInfo(map.get("device_info"));
			returncode.setErrCode(map.get("err_code"));
			returncode.setErrCodeDes(map.get("err_code_des"));
			returncode.setMchAppid(map.get("mch_appid"));
			returncode.setMchid(map.get("mchid"));
			returncode.setNonceStr(map.get("nonce_str"));
			returncode.setPartnerTradeNo(map.get("partner_trade_no"));
			returncode.setPaymentNo(map.get("payment_no"));
			returncode.setPaymentTime(map.get("payment_time"));
			returncode.setResultCode(map.get("result_code"));
			returncode.setReturnMsg(map.get("return_msg"));
			id = wreturnService.addWithdrawResultCode(returncode);
		} catch (

		Exception e) {
			log.info("添加微信处理返回码记录失败,异常信息：" + e);
		}
		if (id > 0 && "SUCCESS".equals(returncode.getResultCode()) && "SUCCESS".equals(map.get("return_code"))) {
			log.info("返回结果已作记录");
			return "SUCCESS";
		} else {
			log.info("微信处理提现失败，返回码" + map.get("err_code_des"));
			return map.get("err_code_des");
		}
	}

	@RequestMapping("/mywithdraw")
	public void showMyWithdrawHistory(HttpServletResponse response, Integer userId, Integer pageNo, Integer pageSize) {
		List<UserWithdrawCash> withlist = withdrawService.findMyWithdrawList(userId, (pageNo - 1) * pageSize, pageSize);
		output(response, JsonUtil.buildJson(withlist));
	}
}
