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

import com.cofc.pojo.RechargeRecord;
import com.cofc.pojo.UserCommon;
import com.cofc.pojo.UserWalletDiary;
import com.cofc.service.RechargeRecordService;
import com.cofc.service.UserActivityService;
import com.cofc.service.UserCommonService;
import com.cofc.service.UserWalletDiaryService;
import com.cofc.service.WeixinPayResultService;
import com.cofc.util.wxpay.PayCommonUtil;
import com.cofc.util.wxpay.WXPayUtil;
import com.cofc.util.wxpay.WeiXinPayConfig;
import com.cofc.util.wxpay.WeixinPayResultUtil;

/**
 * 用户充值-微信回调
 *
 */
@Controller
@RequestMapping("/wx/rechargeNotify") // 微信支付回调接口
public class WXUserRechargeNotifyController {
	@Resource
	private UserCommonService userService;
	@Resource
	private RechargeRecordService apayService;
	@Resource
	private WeixinPayResultService wxPayResultService;
	@Resource
	private UserWalletDiaryService udService;
	@Resource
	private UserActivityService useracService;
	
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
			UserWalletDiary wdiaryP = new UserWalletDiary();
			log.info("进入支付流程!");
			RechargeRecord apayRecord = apayService.getPayRecordByPayId(prepayId);
			UserCommon buyer = userService.getUserByUserId(apayRecord.getUserId());
			if (apayRecord.getPayStatus() == 0 && apayRecord.getRealFee() != apayRecord.getTotalFee()) {
				
				// 把订单表的支付状态改成已支付
				apayRecord.setUpdateTime(new Date());
				apayRecord.setPayStatus(1);// 已支付
				apayRecord.setRealFee(Double.parseDouble(result.get("total_fee")) / 100);
				try {
					apayService.updatePayRecordInfo(apayRecord);
					log.info("活动订单" + apayRecord.getPayId() + "已改为已支付");
					log.info("record update success");
				} catch (Exception e) {
					log.info("活动支付订单"+apayRecord.getPayId()+"支付失败，失败原因"+e);
					log.info("record update default");
					log.info(e);
				}
				// 给支付用户增加支付成功的日志
				wdiaryP.setCreateTime(new Date());
				wdiaryP.setTotalFee(apayRecord.getRealFee());
				wdiaryP.setUserId(apayRecord.getUserId());
				wdiaryP.setIncomeExpend(1);// 支出
				wdiaryP.setLoginPlat(1);
				wdiaryP.setDiaryTitle("充值");
				wdiaryP.setDiaryDetails("充值"+apayRecord.getRealFee()+"元");
				try{
				udService.addNewDiary(wdiaryP);
				log.info("活动订单" + apayRecord.getPayId() + "的支付信息已存入钱包日志");
				log.info("diary update success");
				}catch(Exception e){
					e.printStackTrace();
					log.info("diary update fault");
				}
				//给该用户的钱包余额加上金额
				buyer.setWalletBalance(buyer.getWalletBalance()+apayRecord.getRealFee());
				userService.commonInfoUpdate(buyer);
				log.info("用户"+apayRecord.getUserId()+"的钱包余额增加"+apayRecord.getRealFee()+"元");
				log.info("user balance update success");
				response.getWriter().write(PayCommonUtil.setXML("SUCCESS", "OK"));
			}
			log.info("回调成功,相关用户的用户操作完成!");
		} else {
			response.getWriter().write(PayCommonUtil.setXML("FAIL", "sorry,pay failed!")); // 告诉微信服务器，支付失败
			log.info("回调失败,相关用户的用户操作未完成!");
		}
	}
}
