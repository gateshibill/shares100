package com.cofc.controller.wxnotify;

import java.io.InputStream;
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cofc.pojo.ApplicationCommon;
import com.cofc.pojo.ContractRecord;
import com.cofc.pojo.GainSharingCommon;
import com.cofc.pojo.ShareContract;
import com.cofc.pojo.UserCommon;
import com.cofc.pojo.UserWalletDiary;
import com.cofc.service.ApplicationCommonService;
import com.cofc.service.ContractRecordService;
import com.cofc.service.GainSharingCommonService;
import com.cofc.service.ShareContractService;
import com.cofc.service.UserCommonService;
import com.cofc.service.UserWalletDiaryService;
import com.cofc.service.WeixinPayResultService;
import com.cofc.util.wxpay.PayCommonUtil;
import com.cofc.util.wxpay.WXPayUtil;
import com.cofc.util.wxpay.WeiXinPayConfig;
import com.cofc.util.wxpay.WeixinPayResultUtil;

/**
 * 用户支付悬赏-微信回调
 *
 */
@Controller
@RequestMapping("/wx/arewardNotify") // 微信支付回调接口
public class WXUserArewardNotifyController {
	@Resource
	private UserCommonService userService;
	@Resource
	private WeixinPayResultService wxPayResultService;
	@Resource
	private UserWalletDiaryService udService;
	@Resource
	private ContractRecordService recordService;
	@Resource
	private ShareContractService contractService;
	@Resource
	private GainSharingCommonService gainsharingService;
	@Resource
	private ApplicationCommonService appService;

	public static Logger log = Logger.getLogger("WXUserRechargeController");

	@RequestMapping("/notify")
	public synchronized void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.info("进入异步回调方法!");
		WeiXinPayConfig config = new WeiXinPayConfig();
		InputStream is = request.getInputStream();
		String body = IOUtils.toString(is, "UTF-8");
		Map<String, String> result = WXPayUtil.xmlToMap(body);

		String resultCode = result.get("result_code");
		if ("SUCCESS".equals(resultCode) && PayCommonUtil.createSign(config, "utf-8", PayCommonUtil.paraFilter(result))
				.equals(result.get("sign"))) {// 异步回掉成功
			log.info("进入异步回调处理步骤!");
			String outTradeNo = result.get("out_trade_no").substring(0, result.get("out_trade_no").length() - 17);// 商户订单号

			int prepayId = Integer.parseInt(outTradeNo);
			/**
			 * 保存返回的日志，用户资金变动，增加用户钱包日志，增加用户日志
			 */
			// 增加支付返回的字段记录
			log.info(result);
			wxPayResultService.addNewPayResult(WeixinPayResultUtil.addPayResult(result));
			log.info("wxPayResultService.addNewPayResult(WeixinPayResultUtil.addPayResult(result));");
			// 打赏记录表
			log.info("进入支付流程!");
			ContractRecord record = recordService.getContractRecordById(prepayId);
			UserCommon sharedUser = userService.getUserByUserId(record.getPublisherId());
			ShareContract contract = contractService.getShareContractById(record.getContractId());
			if (record.getPayStatus() == 0 && record.getRealFee() != record.getTotalFee()) {
				// 把订单表的支付状态改成已支付
				record.setUpdateTime(new Date());
				record.setPayStatus(1);// 已支付
				record.setRealFee(Double.parseDouble(result.get("total_fee")) / 100);
				contract.setTotalNumber(contract.getTotalNumber() + 1);
				contract.setTotalDeposit(contract.getTotalDeposit() + record.getRealFee());
				try {
					recordService.updateContractRecord(record);
					contractService.updateShareContract(contract);
					log.info("打赏订单" + record.getRecordId() + "已改为已支付");
					//钱包日志
					UserWalletDiary publisherDiary = new UserWalletDiary();
					publisherDiary.setCreateTime(new Date());
					publisherDiary.setDiaryTitle("合约被打赏");
					publisherDiary.setDiaryDetails("您合约被打赏。");
					publisherDiary.setTotalFee(record.getRealFee());
					publisherDiary.setIncomeExpend(1);
					publisherDiary.setLoginPlat(1);
					publisherDiary.setUserId(sharedUser.getUserId());
					udService.addNewDiary(publisherDiary);
					log.info("打赏订单" + record.getRecordId() + "的信息已存入发布用户钱包日志");
					// 给合约发布者的钱包余额加上金额
					sharedUser.setWalletBalance(sharedUser.getWalletBalance() + record.getRealFee());
					userService.commonInfoUpdate(sharedUser);
					log.info("合约发布用户" + record.getPublisherId() + "的钱包余额增加" + record.getRealFee() + "元");
				} catch (Exception e) {
					log.info("打赏订单" + record.getRecordId() + "支付失败，失败原因" + e);
					log.info(e);
				}
				GainSharingCommon gainshare = gainsharingService.getTheAcqierementSharing();
				if (record.getPartnerId() != null && !"".equals(record.getPartnerId())) {
					log.info("current dingdan de fenxiang yonghu shi "+record.getPartnerId());
					UserCommon partnerUser = userService.getUserByUserId(record.getPartnerId());
					if (partnerUser != null&&record.getPublisherId()!=record.getPartnerId()) {
						UserWalletDiary ownerDiary = new UserWalletDiary();
						ownerDiary.setCreateTime(new Date());
						ownerDiary.setDiaryTitle("打赏合约分成");
						ownerDiary.setDiaryDetails("通过您分享出去的合约被打赏。");
						ownerDiary.setTotalFee(gainshare.getShareuserPersent()* Double.parseDouble(result.get("total_fee")) / 100);
						ownerDiary.setIncomeExpend(1);
						ownerDiary.setLoginPlat(1);
						ownerDiary.setUserId(partnerUser.getUserId());
						udService.addNewDiary(ownerDiary);
						log.info("打赏订单" + record.getRecordId() + "的分成信息已存入分享用户钱包日志");
						partnerUser.setWalletBalance(partnerUser.getWalletBalance()
								+ gainshare.getShareuserPersent() * Double.parseDouble(result.get("total_fee")) / 100);
						userService.commonInfoUpdate(partnerUser);
						log.info("分享分成用户" + partnerUser.getUserId() + "的钱包余额增加"
								+ gainshare.getShareuserPersent() * Double.parseDouble(result.get("total_fee")) / 100
								+ "元");
					}
				}
				ApplicationCommon currApp = appService.getApplicationById(record.getLoginPlat());
				if (currApp != null && currApp.getSmallRoutine() != null) {
					UserCommon groupOwner = userService.getUserByUserId(currApp.getApplicationOwner());
					if (groupOwner != null&&groupOwner.getUserId()!=record.getPublisherId()) {
						try {
							UserWalletDiary ownerDiary = new UserWalletDiary();
							ownerDiary.setCreateTime(new Date());
							ownerDiary.setDiaryTitle("打赏合约分成");
							ownerDiary.setDiaryDetails("您创建的应用【"+currApp.getApplicationName()+"】中，有合约被打赏。");
							ownerDiary.setTotalFee(gainshare.getGroupownerPersent()* Double.parseDouble(result.get("total_fee")) / 100);
							ownerDiary.setIncomeExpend(1);
							ownerDiary.setLoginPlat(1);
							ownerDiary.setUserId(groupOwner.getUserId());
							udService.addNewDiary(ownerDiary);
							log.info("打赏订单" + record.getRecordId() + "的分成信息已存入群主钱包日志");
							groupOwner.setWalletBalance(groupOwner.getWalletBalance() + gainshare.getGroupownerPersent()
									* Double.parseDouble(result.get("total_fee")) / 100);
							userService.commonInfoUpdate(groupOwner);
							log.info("群主分成用户" + groupOwner.getUserId() + "的钱包余额增加" + gainshare.getGroupownerPersent()
									* Double.parseDouble(result.get("total_fee")) / 100 + "元");
						} catch (Exception e) {
							e.printStackTrace();
							log.info("diary update fault");
						}
					}
				}
				response.getWriter().write(PayCommonUtil.setXML("SUCCESS", "OK"));
				log.info("回调成功,相关用户的用户操作完成!");
			}
		} else {
			response.getWriter().write(PayCommonUtil.setXML("FAIL", "sorry,pay failed!")); // 告诉微信服务器，支付失败
			log.info("回调失败,相关用户的用户操作未完成!");
		}
	}
}
