package com.cofc.controller.sharetask;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
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
import com.cofc.pojo.GainSharingCommon;
import com.cofc.pojo.TaskCommon;
import com.cofc.pojo.TaskCompete;
import com.cofc.pojo.TaskPayOrder;
import com.cofc.pojo.TaskPublishContent;
import com.cofc.pojo.TaskPublishReason;
import com.cofc.pojo.TaskSatisfiedService;
import com.cofc.pojo.UserCommon;
import com.cofc.pojo.UserWalletDiary;
import com.cofc.service.ApplicationCommonService;
import com.cofc.service.GainSharingCommonService;
import com.cofc.service.TaskCommonService;
import com.cofc.service.TaskCompeteService;
import com.cofc.service.TaskPayOrderService;
import com.cofc.service.TaskPublishContentService;
import com.cofc.service.TaskPublishReasonService;
import com.cofc.service.TaskSatisfiedServiceService;
import com.cofc.service.UserCommonService;
import com.cofc.service.UserWalletDiaryService;
import com.cofc.util.BaseUtil;
import com.cofc.util.JsonUtil;
import com.cofc.util.MD5Util;
import com.cofc.util.wxpay.WXPreOrderUtil;
import com.cofc.util.wxpay.WeiXinPayConfig;

@Controller
@RequestMapping("/wx/task")
public class WXShareTaskController extends BaseUtil {
	@Resource
	private TaskCommonService taskService;
	@Resource
	private TaskPublishReasonService reasonService;
	@Resource
	private TaskPublishContentService contentService;
	@Resource
	private TaskSatisfiedServiceService satisfiedsService;
	@Resource
	private TaskCompeteService competeService;
	@Resource
	private UserCommonService userService;
	@Resource
	private ApplicationCommonService appService;
	@Resource
	private TaskPayOrderService taskorderService;
	@Resource
	private UserWalletDiaryService udService;
	@Resource
	private GainSharingCommonService gsharingService;
	public static Logger log = Logger.getLogger("WXShareTaskController");

	/**
	 * 发布任务的原因，内容以及奖赏
	 * 
	 * @param response
	 */
	@RequestMapping("/contentsList")
	public void show(HttpServletResponse response) {
		List<TaskPublishReason> reasonList = reasonService.findAllReason();
		List<TaskPublishContent> contentList = contentService.findAllContent();
		List<TaskSatisfiedService> serviceList = satisfiedsService.findAllServices(1);
		List<Object> returnResult = new ArrayList<Object>();
		returnResult.add(reasonList);
		returnResult.add(contentList);
		returnResult.add(serviceList);
		output(response, JsonUtil.buildJson(returnResult));
	}

	/**
	 * 发布任务
	 * 
	 * @param response
	 * @param task
	 * @throws ParseException
	 */
	@RequestMapping("/createTask")
	public void createNewTask(HttpServletResponse response, TaskCommon task, String endTime) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		task.setTaskStartTime(new Date());
		task.setTaskStatus(0);
		task.setIsAnonymous(0);
		task.setTaskEndTime(sdf.parse(endTime));
		task.setCompetedCount(0);
		try {
			taskService.publishNewTask(task);
			List<TaskCommon> returnList = new ArrayList<TaskCommon>();
			returnList.add(task);
			output(response, JsonUtil.buildJson(returnList));
			log.info("用户" + task.getPublisherId() + "创建任务" + task.getTaskContent() + "成功，id为" + task.getTaskId());
		} catch (Exception e) {
			e.printStackTrace();
			log.info("用户" + task.getPublisherId() + "创建任务" + task.getTaskContent() + "失败。");
			output(response, JsonUtil.buildFalseJson("1", "创建失败!"));
		}
	}

	/**
	 * 任务列表
	 * 
	 * @param response
	 * @param loginPlat
	 * @param pageNo
	 * @param pageSize
	 */
	@RequestMapping("/taskList")
	public void showTaskList(HttpServletResponse response, Integer loginPlat, Integer pageNo, Integer pageSize) {
		ApplicationCommon currApp = appService.getApplicationById(loginPlat);
		List<TaskCommon> taskList = new ArrayList<>();
		if (currApp.getParentId() == null) {
			taskList = taskService.findTaskByLoginPlat(null, (pageNo - 1) * pageSize, pageSize);
		} else {
			taskList = taskService.findTaskByLoginPlat(loginPlat, (pageNo - 1) * pageSize, pageSize);
		}
		output(response, JsonUtil.buildJson(taskList));
	}

	/**
	 * 任务详情
	 * 
	 * @param response
	 * @param userId
	 * @param taskId
	 */
	@RequestMapping("/taskDetails")
	public void showTaskDetails(HttpServletResponse response, Integer userId, Integer taskId) {
		TaskCommon currTask = taskService.getTaskById(taskId);
		if (currTask == null) {
			output(response, JsonUtil.buildFalseJson("3", "该任务没有找到!"));
		} else {
			List<TaskCompete> peteList = competeService.findAllCompetedRecord(taskId);
			currTask.setCompeteList(peteList);
			if (userId != 0) {
				UserCommon currUser = userService.getUserByUserId(userId);
				if (currUser == null) {
					currTask.setHasCompeted(0);
				} else {
					for (TaskCompete taskcom : peteList) {
						if (taskcom.getCompetitorUser() == userId) {
							currTask.setHasCompeted(1);
							break;
						} else {
							currTask.setHasCompeted(0);
						}
					}
				}
			} else {
				currTask.setHasCompeted(0);
			}
			List<TaskCommon> resultList = new ArrayList<TaskCommon>();
			resultList.add(currTask);
			output(response, JsonUtil.buildJson(resultList));

		}
	}

	/**
	 * 用户抢单
	 * 
	 * @param response
	 * @param taskcompete
	 */
	@RequestMapping("/competeTask")
	public void userCompeteTask(HttpServletResponse response, TaskCompete taskcompete) {
		TaskCommon currTask = taskService.getTaskById(taskcompete.getCompeteTask());
		UserCommon currUser = userService.getUserByUserId(taskcompete.getCompetitorUser());
		if (currUser == null) {
			output(response, JsonUtil.buildFalseJson("2", "您没有登录!"));
		} else if (currTask == null) {
			output(response, JsonUtil.buildFalseJson("3", "该任务没有找到!"));
		} else {
			List<TaskCompete> peteList = competeService.findAllCompetedRecord(taskcompete.getCompeteTask());
			if (peteList.size() >= 10) {
				output(response, JsonUtil.buildFalseJson("5", "该任务抢单人数已满，请下次尽快哦!"));
			} else {
				TaskCompete hascompeted = competeService.confirmCompetedThisTask(taskcompete.getCompeteTask(),
						taskcompete.getCompetitorUser());
				if (hascompeted != null) {
					output(response, JsonUtil.buildFalseJson("4", "该任务您已经抢过，请等待雇主选择!"));
				} else if (currTask.getPublisherId() == taskcompete.getCompetitorUser()) {
					output(response, JsonUtil.buildFalseJson("6", "不能抢自己的单!"));
				} else {
					taskcompete.setCompeteTime(new Date());
					taskcompete.setCompeteStatus(0);
					try {
						competeService.userCompeteTask(taskcompete);
						currTask.setCompetedCount(
								currTask.getCompetedCount() == null ? 0 : currTask.getCompetedCount() + 1);
						taskService.changeTaskInfo(currTask);
						output(response, JsonUtil.buildFalseJson("0", "抢单成功!"));
						log.info("用户" + taskcompete.getCompetitorUser() + "抢单任务" + taskcompete.getCompeteTask()
								+ "成功，抢单id为" + taskcompete.getCompeteId());
					} catch (Exception e) {
						output(response, JsonUtil.buildFalseJson("1", "抢单失败!"));
						log.info("用户" + taskcompete.getCompetitorUser() + "抢单任务" + taskcompete.getCompeteTask()
								+ "失败，失败原因" + e);
						log.info(e);
					}
				}
			}
		}
	}

	/**
	 * 选择抢单用户
	 * 
	 * @param response
	 * @param competeId
	 * @param userId
	 */
	@RequestMapping("/chooseCompete")
	public void publisherChooseCompeter(HttpServletRequest request, HttpServletResponse response, Integer competeId,
			Integer userId) {
		TaskCompete currcompete = competeService.getCompeteRecordById(competeId);
		if (currcompete == null) {
			output(response, JsonUtil.buildFalseJson("2", "该抢单记录不存在!"));
		} else {
			TaskCommon currtask = taskService.getTaskById(currcompete.getCompeteTask());
			if (currtask == null || !currtask.getPublisherId().equals(userId)) {// 当前任务没有找到或者当前任务的发布人不是该用户
				output(response, JsonUtil.buildFalseJson("3", "该任务没有找到!"));
			} else if (currtask.getTaskStatus() == 1) {
				output(response, JsonUtil.buildFalseJson("1", "该任务已选择了用户,不能更改!"));
			} else {// 选人即要下单支付
				TaskPayOrder taskOrder = taskorderService.cofirmSelectedThisOrder(competeId);
				ApplicationCommon currapp = appService.getApplicationById(currtask.getLoginPlat());
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
				if (taskOrder != null && taskOrder.getPayStatus() != 1) {// 之前下过未支付的订单
					String outtradeno = taskOrder.getOrderId() + sdf.format(new Date());
					taskOrder.setOutTradeNumber(outtradeno);
					taskOrder.setOrderTime(new Date());
					taskOrder.setPayStatus(0);
					taskOrder.setSmallRoutine(
							currapp.getParentId() == null ? currapp.getApplicationId() : currapp.getSmallRoutine());
					taskorderService.updateTaskOrderInfo(taskOrder);
				} else {
					taskOrder = new TaskPayOrder();
					taskOrder.setCompeteId(competeId);
					taskOrder.setLoginPlat(currtask.getLoginPlat());
					taskOrder.setSmallRoutine(
							currapp.getParentId() == null ? currapp.getApplicationId() : currapp.getSmallRoutine());
					taskOrder.setOrderFee(currcompete.getCompeteFee());
					taskOrder.setOrderTime(new Date());
					taskOrder.setPartnerId(currcompete.getPartnerId());
					taskOrder.setPayStatus(0);
					taskOrder.setBuyerId(userId);
					taskorderService.userSeletedCompete(taskOrder);
					String outtradeno = taskOrder.getOrderId() + sdf.format(new Date());
					taskOrder.setOutTradeNumber(outtradeno);
					taskorderService.updateTaskOrderInfo(taskOrder);
				}
				List<TaskPayOrder> returnList = new ArrayList<TaskPayOrder>();
				returnList.add(taskOrder);
				output(response, JsonUtil.buildJson(returnList));
			}
		}
	}

	/**
	 * 选择抢单用户后支付费用
	 * 
	 * @param request
	 * @param response
	 * @param orderId
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping("/pay")
	public void publisherPayForOrder(HttpServletRequest request, HttpServletResponse response, Integer orderId)
			throws UnsupportedEncodingException {
		String userIp = request.getRemoteAddr();
		if (userIp == null || "".equals(userIp)) {
			userIp = "127.0.0.1";
		}
		String contextPath = request.getContextPath();// 项目名
		String port = request.getServerPort() == 80 ? "" : ":" + request.getServerPort();
		String weburl = request.getScheme() + "://" + request.getServerName() + port + contextPath;
		WeiXinPayConfig config = new WeiXinPayConfig();
		// config.setNotify_url("https://www.ailefeigou.com/cofC2/wx/competePayNotify/notify.do");
		config.setNotify_url(weburl + "/wx/competePayNotify/notify.do");
		TaskPayOrder taskOrder = taskorderService.getOrderById(orderId);
		if (taskOrder != null) {
			double fee = taskOrder.getOrderFee();
			UserCommon payUser = userService.getUserByUserId(taskOrder.getBuyerId());
			ApplicationCommon currapp = appService.getApplicationById(taskOrder.getSmallRoutine());
			config.setAppid(currapp.getAppId());
			config.setMch_id(currapp.getMchid());
			if (taskOrder.getPayStatus() == 0) {
				Map<String, String> map = new HashMap<String, String>();
				map = WXPreOrderUtil.getPrepayInfo(taskOrder.getOutTradeNumber(), payUser.getOpenId(), fee, userIp,
						"选中抢单", config);
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
						log.info(orderId + "下单成功,订单号" + prepayId + "!");
					}
				} else {
					output(response, JsonUtil.buildFalseJson("3", "统一下单失败！"));
					log.info(orderId + "下单失败!");
				}
			} else {
				output(response, JsonUtil.buildFalseJson("4", "您已支付，请勿重复支付!"));
			}
		} else {
			output(response, JsonUtil.buildFalseJson("1", "订单不存在!"));
		}
	}

	@RequestMapping("/complete")
	public void userCompleteTask(HttpServletResponse response, Integer userId, Integer taskId) {
		TaskCompete currcompete = competeService.confirmCompetedThisTask(taskId, userId);
		if (currcompete != null && currcompete.getCompeteStatus() == 1) {// 是已被选中的用户
			currcompete.setCompeteStatus(2);// 已完成
			competeService.publisherChooseCompeter(currcompete);
			TaskCommon currTask = taskService.getTaskById(taskId);
			currTask.setTaskStatus(2);
			taskService.changeTaskInfo(currTask);
			output(response, JsonUtil.buildFalseJson("0", "提交成功,请等待发布者确认!"));
			log.info("用户" + userId + "完成任务" + taskId + "，正在等待发布者确认");
		} else if (currcompete == null || currcompete.getCompetitorUser() != userId
				|| currcompete.getCompeteStatus() == 10 || currcompete.getCompeteStatus() == 0) {
			output(response, JsonUtil.buildFalseJson("1", "您不是该任务被选中的用户"));
			log.info("用户" + userId + "想完成任务" + taskId + "，但他不是被选中的人");
		} else if (currcompete.getCompeteStatus() == 2 || currcompete.getCompeteStatus() == 3
				|| currcompete.getCompeteStatus() == 4) {
			output(response, JsonUtil.buildFalseJson("2", "该任务已完成，请勿重复提交"));
			log.info("用户" + userId + "想完成任务" + taskId + "，但他已经完成过了");
		}
	}

	// 计算分成
	@RequestMapping("/confirmComplete")
	public void userConfirmCompleteTask(HttpServletResponse response, Integer userId, Integer taskId) {
		TaskCommon currTask = taskService.getTaskById(taskId);
		if (currTask != null && currTask.getPublisherId().equals(userId)) {
			TaskCompete currcompete = competeService.getCurrTaskSelected(taskId, 2);// 该任务已完成的记录
			if (currcompete != null && currcompete.getCompeteStatus() == 2) {
				currcompete.setCompeteStatus(3);
				competeService.publisherChooseCompeter(currcompete);
				log.info("抢单号" + currcompete.getCompeteId() + "的状态已变成确认完成。");
				currTask.setTaskStatus(3);// 确认完成
				taskService.changeTaskInfo(currTask);
				log.info("任务" + taskId + "的状态已变成确认完成。");
				GainSharingCommon gainsharing = gsharingService.getTheAcqierementSharing();
				// 分享者加钱(分成)
				if (currcompete.getPartnerId() != null && currcompete.getPartnerId() != currTask.getPublisherId()) {
					UserCommon partner = userService.getUserByUserId(currcompete.getPartnerId());
					if (partner != null) {
						partner.setWalletBalance(partner.getWalletBalance()
								+ (currcompete.getCompeteFee() * gainsharing.getShareuserPersent()));
						userService.commonInfoUpdate(partner);
						log.info("分享者" + currcompete.getPartnerId() + "的钱包金额已增加。");
						UserWalletDiary partnerDiary = new UserWalletDiary();
						partnerDiary.setCreateTime(new Date());
						partnerDiary.setDiaryTitle("分享的任务被完成");
						partnerDiary.setDiaryDetails("您分享的任务被完成，获得" + currcompete.getCompeteFee() * gainsharing.getShareuserPersent() + "元的收入");
						partnerDiary.setIncomeExpend(1);// 收入
						partnerDiary.setLoginPlat(1); //先百享园
						partnerDiary.setTotalFee(currcompete.getCompeteFee() * gainsharing.getShareuserPersent());
						partnerDiary.setUserId(currcompete.getPartnerId());
						udService.addNewDiary(partnerDiary);
						log.info("分享者" + currcompete.getPartnerId() + "的钱包日志已添加。");
					}
				}
				// 抢单者加钱(拿全部)
				if (currcompete.getCompetitorUser() != null) {
					UserCommon robUser = userService.getUserByUserId(currcompete.getCompetitorUser());
					if (robUser != null) {
						robUser.setWalletBalance(robUser.getWalletBalance() + currcompete.getCompeteFee());
						userService.commonInfoUpdate(robUser);
						log.info("抢单者" + currcompete.getPartnerId() + "的钱包金额已增加。");
						UserWalletDiary proberDiary = new UserWalletDiary();
						proberDiary.setCreateTime(new Date());
						proberDiary.setDiaryTitle("抢单的任务被完成");
						proberDiary.setDiaryDetails("您抢单的任务被完成，获得" + currcompete.getCompeteFee() + "元的收入");
						proberDiary.setIncomeExpend(1);// 收入
						proberDiary.setLoginPlat(1); //先百享园
						proberDiary.setTotalFee(currcompete.getCompeteFee());
						proberDiary.setUserId(currcompete.getCompetitorUser());
						udService.addNewDiary(proberDiary);
						log.info("抢单者" + currcompete.getCompetitorUser() + "的钱包日志已添加。");
					}
				}
				ApplicationCommon currapp = appService.getApplicationById(currTask.getLoginPlat());
				if (currapp.getParentId() != null) {// 不是小程序级别，群主需要参与分成
					UserCommon appowner = userService.getUserByUserId(currapp.getApplicationOwner());
					if (appowner != null) {
						appowner.setWalletBalance(appowner.getWalletBalance()
								+ (currcompete.getCompeteFee() * gainsharing.getGroupownerPersent()));
						userService.commonInfoUpdate(appowner);
						log.info("分享者" + currcompete.getPartnerId() + "的钱包金额已增加。");
						UserWalletDiary appownerDiary = new UserWalletDiary();
						appownerDiary.setCreateTime(new Date());
						appownerDiary.setDiaryTitle("您社区内的任务被完成");
						appownerDiary.setDiaryDetails("您创建的社区中有任务被完成了，获得"
								+ currcompete.getCompeteFee() * gainsharing.getGroupownerPersent() + "元的收入");
						appownerDiary.setIncomeExpend(1);// 收入
						appownerDiary.setLoginPlat(1);
						appownerDiary.setTotalFee(currcompete.getCompeteFee() * gainsharing.getGroupownerPersent());
						appownerDiary.setUserId(appowner.getUserId());
						udService.addNewDiary(appownerDiary);
						log.info("群主" + appowner.getUserId() + "的钱包日志已添加。");
					}
				}
				output(response, JsonUtil.buildFalseJson("0", "确认成功，您的任务已被完成!"));
			} else {
				output(response, JsonUtil.buildFalseJson("2", "该任务没有被完成，请耐心等待"));
			}
		} else {
			output(response, JsonUtil.buildFalseJson("1", "您不是该任务的发布者!"));
		}
	}

	/**
	 * 我发布的任务
	 * 
	 * @param response
	 * @param userId
	 * @param loginPlat
	 * @param taskStatus
	 * @param pageNo
	 * @param pageSize
	 */
	@RequestMapping("/mypublished")
	public void showMyPublishedTasks(HttpServletResponse response, Integer userId, Integer loginPlat,
			Integer taskStatus, Integer pageNo, Integer pageSize) {
		List<TaskCommon> tasklist = taskService.findMyPublishedTasks(userId, loginPlat, taskStatus,
				(pageNo - 1) * pageSize, pageSize);
		output(response, JsonUtil.buildJson(tasklist));
	}

	/**
	 * 我接受的任务
	 * 
	 * @param response
	 * @param userId
	 * @param loginPlat
	 * @param competeStatus
	 * @param pageNo
	 * @param pageSize
	 */
	@RequestMapping("/mycompeted")
	public void showMyCompetedTasks(HttpServletResponse response, Integer userId, Integer loginPlat,
			Integer competeStatus, Integer pageNo, Integer pageSize) {
		List<TaskCommon> tasklist = taskService.findMyCompetedTasks(userId, loginPlat, competeStatus,
				(pageNo - 1) * pageSize, pageSize);
		output(response, JsonUtil.buildJson(tasklist));
	}

	@RequestMapping("/extraService")
	public void showExtraServices(HttpServletResponse response) {
		List<TaskSatisfiedService> serviceList = satisfiedsService.findAllServices(2);
		output(response, JsonUtil.buildJson(serviceList));
	}
}
