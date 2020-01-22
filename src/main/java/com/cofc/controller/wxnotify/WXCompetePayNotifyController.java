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

import com.cofc.pojo.TaskCommon;
import com.cofc.pojo.TaskCompete;
import com.cofc.pojo.TaskPayOrder;
import com.cofc.pojo.UserCommon;
import com.cofc.pojo.UserWalletDiary;
import com.cofc.service.TaskCommonService;
import com.cofc.service.TaskCompeteService;
import com.cofc.service.TaskPayOrderService;
import com.cofc.service.UserCommonService;
import com.cofc.service.UserWalletDiaryService;
import com.cofc.service.WeixinPayResultService;
import com.cofc.util.wxpay.PayCommonUtil;
import com.cofc.util.wxpay.WXPayUtil;
import com.cofc.util.wxpay.WeiXinPayConfig;
import com.cofc.util.wxpay.WeixinPayResultUtil;

/**
 * 用户支付任务费用-微信回调。
 *
 */
@Controller
@RequestMapping("/wx/competePayNotify")
public class WXCompetePayNotifyController {
	@Resource
	private WeixinPayResultService wxPayResultService;
	@Resource
	private UserCommonService userService;
	@Resource
	private TaskPayOrderService payorderService;
	@Resource
	private TaskCommonService taskService;
	@Resource
	private TaskCompeteService competeService;
	@Resource
	private UserWalletDiaryService udService;
	public static Logger log = Logger.getLogger(WXPayNotifyController.class.getName());

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
			log.info("返回信息加入微信支付信息日志表成功");
			UserWalletDiary wdiaryP = new UserWalletDiary();
			UserCommon buyer = new UserCommon();
			log.info("进入支付商品流程!");
			TaskPayOrder taskOrder = payorderService.getOrderById(prepayId);
			buyer = userService.getUserByUserId(taskOrder.getBuyerId());
			if (taskOrder.getPayStatus() == 0 && taskOrder.getRealFee() != taskOrder.getOrderFee()) {
				TaskCompete currcompete = competeService.getCompeteRecordById(taskOrder.getCompeteId());
				TaskCommon currtask = taskService.getTaskById(currcompete.getCompeteTask());
				try {
					taskOrder.setPayStatus(1);// 已支付
					taskOrder.setPayTime(new Date());
					taskOrder.setRealFee(Double.parseDouble(result.get("total_fee")) / 100);
					payorderService.updateTaskOrderInfo(taskOrder);
					log.info("订单" + taskOrder.getOrderId() + "的支付状态已修改");
					currcompete.setCompeteStatus(1);
					currcompete.setSelectTime(new Date());
					competeService.publisherChooseCompeter(currcompete);
					currcompete.setCompeteStatus(10);
					competeService.publisherDischooseCompeter(currcompete);
					currtask.setTaskStatus(1);
					taskService.changeTaskInfo(currtask);
					log.info("修改任务状态和抢单状态成功!");
				} catch (Exception e) {
					log.info("用户给任务" + currtask.getTaskId() + "修改任务状态和抢单状态成功，失败原因" + e);
					log.info(e);
				}
				// 给支付用户增加支付成功的日志
				wdiaryP.setCreateTime(new Date());
				wdiaryP.setTotalFee(taskOrder.getRealFee());
				wdiaryP.setUserId(taskOrder.getBuyerId());
				wdiaryP.setIncomeExpend(0);// 支出
				wdiaryP.setLoginPlat(taskOrder.getLoginPlat());
				wdiaryP.setDiaryDetails("用户" + buyer.getNickName() + "给任务【" + currtask.getTaskContent() + "】选择抢单用户"
						+ currcompete.getCompetitorUser() + "共消费" + taskOrder.getRealFee() + "元");
				wdiaryP.setDiaryTitle("选择任务抢单人消费");
				udService.addNewDiary(wdiaryP);
				log.info("订单" + taskOrder.getOrderId() + "的支付信息已存入钱包日志");
				response.getWriter().write(PayCommonUtil.setXML("SUCCESS", "OK"));
			}
			log.info("回调成功,相关用户的用户操作完成!");
		} else {
			response.getWriter().write(PayCommonUtil.setXML("FAIL", "sorry,pay failed!")); // 告诉微信服务器，支付失败
			log.info("回调失败,相关用户的用户操作未完成!");
		}
	}
}
