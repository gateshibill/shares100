package com.cofc.controller.aida;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.annotation.Resource;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cofc.controller.pushmessage.CorpPushMessageController;
import com.cofc.controller.pushmessage.WXPushMessageController;
import com.cofc.pojo.ApplicationCommon;
import com.cofc.pojo.PushMessage;
import com.cofc.pojo.UserCommon;
import com.cofc.pojo.aida.ActionColumn;
import com.cofc.pojo.aida.ActionUser;
import com.cofc.pojo.aida.CustomerDetail;
import com.cofc.pojo.aida.CustomerLabel;
import com.cofc.pojo.aida.CustomerLabelRecord;
import com.cofc.pojo.aida.IMessage;
import com.cofc.pojo.aida.OrderFollow;
import com.cofc.pojo.aida.OrderFollowTips;
import com.cofc.pojo.aida.SalesCustomer;
import com.cofc.pojo.aida.SalesCustomerTips;
import com.cofc.pojo.aida.SalesDashboard;
import com.cofc.pojo.aida.SalesFunnel;
import com.cofc.pojo.aida.SalesMessage;
import com.cofc.pojo.aida.SalesMessageType;
import com.cofc.pojo.aida.SalesPerson;
import com.cofc.pojo.aida.SalesReport;
import com.cofc.pojo.aida.UserCard;
import com.cofc.pojo.aida.UserFormId;
import com.cofc.pojo.aida.UserImpress;
import com.cofc.pojo.aida.WorkWeixin;
import com.cofc.pojo.dataAnalysis.AiDashboard;
import com.cofc.pojo.dataAnalysis.AiTrend;
import com.cofc.pojo.dataAnalysis.CustomerAction;
import com.cofc.pojo.goods.GoodsMyself;
import com.cofc.service.ApplicationCommonService;
import com.cofc.service.PushMessageService;
import com.cofc.service.UserCommonService;
import com.cofc.service.aida.ActionColumnService;
import com.cofc.service.aida.ActionRecordService;
import com.cofc.service.aida.ActionUserService;
import com.cofc.service.aida.CustomerDetailService;
import com.cofc.service.aida.CustomerLabelRecordService;
import com.cofc.service.aida.CustomerLabelService;
import com.cofc.service.aida.IMessageService;
import com.cofc.service.aida.OrderFollowService;
import com.cofc.service.aida.OrderFollowTipsService;
import com.cofc.service.aida.SalesCustomerService;
import com.cofc.service.aida.SalesCustomerTipsService;
import com.cofc.service.aida.SalesMessageService;
import com.cofc.service.aida.SalesMessageTypeService;
import com.cofc.service.aida.SalesPersonService;
import com.cofc.service.aida.UserCardService;
import com.cofc.service.aida.UserFormIdService;
import com.cofc.service.aida.UserImpressService;
import com.cofc.service.aida.WorkWeixinService;
import com.cofc.service.dataAnalysis.AiDashboardService;
import com.cofc.service.dataAnalysis.AiTrendService;
import com.cofc.service.dataAnalysis.CustomerActionService;
import com.cofc.util.BaseUtil;
import com.cofc.util.JsonUtil;

@Controller
@RequestMapping("/aida")
public class SaleAiController extends BaseUtil {
	public static Logger log = Logger.getLogger("SaleAiController");
	@Resource
	private ActionRecordService actionRecordService;
	@Resource
	private ActionColumnService actionColumnService;
	@Resource
	private ActionUserService actionUserService;
	@Resource
	private SalesPersonService salesPersonService;
	@Resource
	private SalesCustomerService salesCustomerService;
	@Resource
	private SalesMessageTypeService salesMessageTypeService;
	@Resource
	private SalesMessageService salesMessageService;
	@Resource
	private OrderFollowService orderFollowService;
	@Resource
	private UserImpressService userImpressService;
	@Resource
	private SalesCustomerTipsService salesCustomerTipsService;
	@Resource
	private CustomerLabelService customerLabelService;
	@Resource
	private CustomerLabelRecordService customerLabelRecordService;
	@Resource
	private CustomerDetailService customerDetailService;
	@Resource
	private IMessageService iMessageService;
	@Resource
	private UserCommonService userCommonService;
	@Resource
	private UserFormIdService userFormIdService;
	@Resource
	private AiDashboardService aiDashboardService;
	@Resource
	private OrderFollowTipsService orderFollowTipsService;
	@Resource
	private UserCardService userCardService;
	@Resource
	private ApplicationCommonService appService;
	@Resource
	private WorkWeixinService workService;
	@Resource
	private WorkWeixinService workWeixinService;
	@Resource
	private PushMessageService pushService;
	@Resource
	private AiTrendService aiTrendService;
	@Resource
	private CustomerActionService customerActionService;

	// 0.获得销售员仪表盘整体信息
	@RequestMapping("/getSalesDashboard")
	public void getSalesDashboard(HttpServletResponse response, int appId, int salesId, int lastdays)
			throws IOException {
		log.info(String.format("%s|%s|%s", appId, salesId, "getSalesDashboard"));
		customerActionService.addCustomerAction(
				new CustomerAction(appId, salesId, "SaleAiController", "getSalesDashboard", "lastdays:" + lastdays));

		SalesDashboard salesDashboard = new SalesDashboard();
		int b=0;
		if(appId==701)
		{
			b=8;
		}
		// 1.客户数
		int customerCount = salesCustomerService.getCustomerCount(appId, salesId);
		salesDashboard.setCustomerCount(customerCount*b+customerCount);

		// 查看产品
		int visitProductTimes = actionRecordService.getActionRecordCountByTypeAndObjectType(appId, salesId, 0, 0, 2);
		salesDashboard.setVisitProductTimes(visitProductTimes*b+visitProductTimes);
		// 访问官网
		int visitOfficialWebsiteTimes = actionRecordService.getActionRecordCountByTypeAndObjectType(appId, salesId, 0,
				0, 0);
		salesDashboard.setVisitOfficialWebsiteTimes(visitOfficialWebsiteTimes*b+visitOfficialWebsiteTimes);
		// 5.分享次数
		int shareTimes = actionRecordService.getActionRecordCountByTypeAndObjectType(appId, salesId, 0, 1, 1);
		salesDashboard.setShareTimes(shareTimes*b+shareTimes);

		// 查看名片
		int visitCardTimes = actionRecordService.getActionRecordCountByTypeAndObjectType(appId, salesId, 0, 0, 1);
		salesDashboard.setVisitCardTimes(visitCardTimes*b+visitCardTimes);

		// 查动态
		int visitForumTimes = actionRecordService.getActionRecordCountByTypeAndObjectType(appId, salesId, 0, 0, 3);
		salesDashboard.setVisitForumTimes(visitForumTimes*b+visitForumTimes);

		// 咨询产品次数
		int consultTimes = actionRecordService.getActionRecordCountByTypeAndObjectType(appId, salesId, 0, 3, 2);
		salesDashboard.setConsultTimes(consultTimes*b+consultTimes);
		// 点赞名片
		int praiseTimes = actionRecordService.getActionRecordCountByTypeAndObjectType(appId, salesId, 0, 2, 1);
		salesDashboard.setPraiseTimes(praiseTimes*b+praiseTimes);

		// 潜在客户暂时用这个点赞数
		salesDashboard.setPotentialCustomerCount(praiseTimes*b+praiseTimes);
		int savePhoneTimes = actionRecordService.getActionRecordCountByTypeAndObjectType(appId, salesId, 0, 6, 6);
		// 保存电话号码
		salesDashboard.setSavePhoneTimes(savePhoneTimes*b+savePhoneTimes);
		int dialPhoneTimes = actionRecordService.getActionRecordCountByTypeAndObjectType(appId, salesId, 0, 7, 6);
		salesDashboard.setDialPhoneTimes(dialPhoneTimes*b+dialPhoneTimes);

		// 2.访问次数
		int visitTimes = visitProductTimes + visitOfficialWebsiteTimes + visitCardTimes;
		salesDashboard.setVisitTimes(visitTimes);

		// 新增消息
		int newMessageCount = iMessageService.getUnreadIMessageCount(appId, 0, salesId);
		salesDashboard.setNewMessageCount(newMessageCount);

		output(response, JsonUtil.objectToJson(salesDashboard));
	}

	// 获得销售报表
	@RequestMapping("/getSalesReport")
	public void getSalesReport(HttpServletResponse response, int appId, int salesId, Integer lastdays)
			throws IOException {
		log.info(String.format("%s|%s|%s", appId, salesId, "getSalesReport"));
		customerActionService.addCustomerAction(
				new CustomerAction(appId, salesId, "SaleAiController", "getSalesReport", "lastdays:" + lastdays));

		AiDashboard ad = aiDashboardService.getAiDashboard(appId, salesId, lastdays);

		SalesReport salesReport = new SalesReport();
		// 1.客户数
		salesReport.setCustomerCount(ad.getCustomerCount());
		// 2.跟踪客户数
		salesReport.setFollowCustomerCount(ad.getFollowOrderCount());
		// 3.浏览总数
		salesReport.setVisitTimes(ad.getVisitedCount());
		// 4.分享次数
		salesReport.setShareTimes(ad.getSharedCount());
		// 5.保存电话
		salesReport.setSavePhoneTimes(ad.getSavedCount());
		// 6.被点赞数
		salesReport.setPraiseTimes(ad.getPraisedCount());

		// 客户兴趣.饼图
		// 对我感兴趣:查看我的名片次数；
		salesReport.setInterestMeCount(ad.getVisitedCount());
		// 对产品兴趣：查看产品次数；
		salesReport.setInterestProductCount(ad.getVisitedProductCount());
		// 对公司感兴趣：查看官网的次数；
		salesReport.setInterestCompany(ad.getVisitedWebsiteCount());

		List<ActionColumn> actionColumnList = salesReport.getActionColumnList();
		actionColumnList.add(new ActionColumn("保存电话", ad.getSavedCount()));
		actionColumnList.add(new ActionColumn("点赞", ad.getPraisedCount()));
		actionColumnList.add(new ActionColumn("咨询产品", ad.getConsultCount()));
		actionColumnList.add(new ActionColumn("拨打电话", new Random().nextInt(20)));
		actionColumnList.add(new ActionColumn("分享", ad.getSharedCount()));
		actionColumnList.add(new ActionColumn("评论", new Random().nextInt(20)));

		/**
		 * 趋势图
		 */
		AiTrend aiTrend1 = aiTrendService.getAiTrend(ad.getAppId(), 0, 7, 1);
		if (null != aiTrend1) {
			salesReport.setVisitedAmountListStr(aiTrend1.getList());
		}
		AiTrend aiTrend2 = aiTrendService.getAiTrend(ad.getAppId(), 0, 7, 2);
		if (null != aiTrend2) {
			salesReport.setNewCustomerListStr(aiTrend2.getList());
		}

		output(response, JsonUtil.objectToJson(salesReport));
	}

	// 1.获得销售员,非登陆用户，用于BOSS查看每个销售情况
	@RequestMapping("/getSalesPerson")
	public void getSalesPerson(HttpServletResponse response, int appId, int salesPersonId) throws IOException {
		log.info(String.format("%s|%s|%s", appId, salesPersonId, "getSalesReport"));
		customerActionService
				.addCustomerAction(new CustomerAction(appId, salesPersonId, "SaleAiController", "getSalesPerson", ""));

		SalesPerson salesPerson = salesPersonService.getSalesPersonById(salesPersonId);
		output(response, JsonUtil.objectToJson(salesPerson));
	}

	// 2.销售排行旁.个人中心排行,是按照访客排行,暂时不按照时间；
	@RequestMapping("/salesRanking")
	public void salesRanking(HttpServletResponse response, int appId, Integer userId, int lastdays, Integer page,
			Integer limit) throws IOException {
		log.info(String.format("%s|%s|%s", appId, userId, "salesRanking"));
		customerActionService
				.addCustomerAction(new CustomerAction(appId, userId, "SaleAiController", "salesRanking", ""));

		List<SalesPerson> list = salesPersonService.getRankingSalesPersonList(appId);
		SalesPerson myself = null;
		for (SalesPerson sp : list) {
			int eVisitedCount = actionRecordService.getSalesPersonVisitedCount(appId, sp.getUserId());
			sp.seteVisitedCount(eVisitedCount);
			if (userId != null && userId == sp.getUserId()) {
				myself = sp;
			}
		}
		Collections.sort(list, new AISpVisitedCountComparator());
		int rank = 0;
		for (SalesPerson sp : list) {
			sp.setRank(++rank);
		}

		// 为了做分页处理
		if (page < 1)// 防止传参数异常
		{
			page = 1;
		}
		int formIndex = (page - 1) * limit;
		int toIndex = page * limit;

		if (formIndex > list.size() - 1) {
			List<SalesPerson> newList = new ArrayList<SalesPerson>();
			newList.add(myself);
			output(response, JsonUtil.buildCustomJson("2", "end", newList));
			return;
		}
		if (toIndex > list.size()) {
			toIndex = list.size();
		}
		List<SalesPerson> newList = list.subList(formIndex, toIndex);
		newList.add(myself);
		output(response, JsonUtil.buildCustomJson("1", "success", newList));
	}

	// 3.获得一个客户信息
	@RequestMapping("/getCustomer")
	public void getCustomer(HttpServletResponse response, int appId, int salesPersonId, int userId) throws IOException {
		log.info(String.format("%s|%s|%s", appId, userId, "getCustomer"));
		customerActionService.addCustomerAction(
				new CustomerAction(appId, salesPersonId, "SaleAiController", "getCustomer", "userId:" + userId));

		SalesCustomer salesCustomer = salesCustomerService.getCustomer(appId, salesPersonId, userId);
		if (null == salesCustomer) {
			output(response, JsonUtil.buildFalseJson("0", "找不到用户ID：userId"));
			return;
		}
		UserImpress impress = new UserImpress();
		impress.setUserId(userId);
		impress.setAppId(appId);
		impress.setIsEffect(1);

		salesCustomer.setImpressList(userImpressService.getImpressList(impress));
		
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + 30+new Random().nextInt(30));
		Date ed = calendar.getTime();
		if(salesCustomer.getExpectedDate()==null)
		{
			salesCustomer.setExpectedDate(ed);
		}
		List<CustomerLabel> labelList = customerLabelService.getOneCustomerLabelList(appId, salesPersonId, userId);
		salesCustomer.setLabelList(labelList);
		output(response, JsonUtil.objectToJson(salesCustomer));
	}

	// 获得客户详细信息
	@RequestMapping("/getCustomerDetail")
	public void getCustomerDetail(HttpServletResponse response, int appId, int userId, int salesPersonId)
			throws IOException {
		log.info(String.format("%s|%s|%s", appId, userId, "getCustomerDetail"));
		customerActionService.addCustomerAction(
				new CustomerAction(appId, salesPersonId, "SaleAiController", "getCustomerDetail", "userId:" + userId));

		CustomerDetail customerDetail = customerDetailService.getCustomerDetail(appId, userId, salesPersonId);
		if (null == customerDetail) {
			// 首次创建客户与销售关系时，创建客户详情表；
			customerDetail = new CustomerDetail();
			customerDetail.setAppId(appId);
			customerDetail.setUserId(userId);
			customerDetail.setSalesPersonId(salesPersonId);
			customerDetail.setCreateTime(new Date());
			customerDetailService.addCustomerDetail(customerDetail);
		}
		// 补上之前创建是没有填写的数据
		if (customerDetail.getNickName() == null || customerDetail.getNickName() == "") {
			UserCommon userCommon = userCommonService.getUserByUserId(userId);
			customerDetail.setCompany(userCommon.getCompony());
			customerDetail.setEmail(userCommon.getEmail());
			customerDetail.setNickName(userCommon.getNickName());
			customerDetail.setPhone(userCommon.getPhone());
			customerDetail.setSex(userCommon.getUserSex());
			customerDetail.setName(userCommon.getRealName());

			Integer introduceUserId = userCommon.getIntroducer();
			if (introduceUserId != null) {
				UserCommon introUser = userCommonService.getUserByUserId(introduceUserId);
				if (introUser.getRealName() != null && introUser.getRealName() == "") {
					customerDetail.setSource(introUser.getRealName());
				} else {
					customerDetail.setSource(introUser.getNickName());
				}
			}
		}

		output(response, JsonUtil.objectToJson(customerDetail));
	}

	// 编辑客户信息
	@RequestMapping("/editCustomerDetail")
	public void editCustomerDetail(HttpServletResponse response, CustomerDetail customerDetail) throws IOException {
		log.info(String.format("%s|%s|%s", customerDetail.getAppId(), customerDetail.getSalesPersonId(),
				"editCustomerDetail"));
		customerActionService.addCustomerAction(new CustomerAction(customerDetail.getAppId(),
				customerDetail.getSalesPersonId(), "SaleAiController", "editCustomerDetail", ""));

		customerDetailService.updateCustomerDetail(customerDetail);

		// 把是否屏蔽消息更新到销售信息里面
		SalesCustomer sc = salesCustomerService.getCustomer(customerDetail.getAppId(),
				customerDetail.getSalesPersonId(), customerDetail.getUserId());
		if (sc != null) {
			sc.setIsblock(customerDetail.getIsBlock());
		}

		salesCustomerService.updateSalesCustomer(sc);
		output(response, JsonUtil.objectToJson(customerDetail));
	}

	// 4.获得销售客户列表,包括人数
	//获取一个应用下所有销售是，salesPersonId为0,userId未使用用户ID；
	@RequestMapping("/getCustomerList")
	public void getCustomerList(HttpServletResponse response, int appId, int salesPersonId,int userId, Integer page, Integer limit)
			throws IOException {
		log.info(String.format("%s|%s|%s", appId, userId, "getCustomerList"));
		customerActionService
				.addCustomerAction(new CustomerAction(appId, userId, "SaleAiController", "getCustomerList", ""));

		List<SalesCustomer> list = salesCustomerService.getCustomerList(appId, salesPersonId, (page - 1) * limit,
				limit);

		for (SalesCustomer sc : list) {
			String spname = sc.getSalesPersonId() + "";
			UserCard uc = userCardService.getUserCard(sc.getSalesPersonId(), appId);
			if (null != uc) {
				spname = userCardService.getUserCard(sc.getSalesPersonId(), appId).getRealName();
				if (null != spname) {
					sc.setSalesPersonName(spname);
				}
			}
			sc.setSalesPersonName(spname);
		}

		int customerCount = salesCustomerService.getCustomerCount(appId, salesPersonId);
		output(response, JsonUtil.buildCustomJson("1", customerCount + "", list));
	}

	// 4.0获得正在跟进的销售客户列表,包括人数
	@RequestMapping("/getFollowingCustomerList")
	public void getFollowingCustomerList(HttpServletResponse response, int appId, int salesPersonId, Integer page,
			Integer limit) throws IOException {
		log.info(String.format("%s|%s|%s", appId, salesPersonId, "getFollowingCustomerList"));

		List<SalesCustomer> list = salesCustomerService.getFollowingCustomerList(appId, salesPersonId,
				(page - 1) * limit, limit);
		for (SalesCustomer sc : list) {
			String spname = sc.getSalesPersonId() + "";
			UserCard uc = userCardService.getUserCard(sc.getSalesPersonId(), appId);
			if (null != uc) {
				spname = userCardService.getUserCard(sc.getSalesPersonId(), appId).getRealName();
				if (null != spname) {
					sc.setSalesPersonName(spname);
				}
			}
			sc.setSalesPersonName(spname);
		}
		output(response, JsonUtil.buildCustomJson("1", "success", list));
	}

	// 4.1.获得最新消息的销售客户列表
	@RequestMapping("/getLastedImCustomerList")
	public void getLastedImCustomerList(HttpServletResponse response, int appId, int salesPersonId, Integer page,
			Integer limit) throws IOException {
		log.info(String.format("%s|%s|%s", appId, salesPersonId, "getLastedImCustomerList"));
		customerActionService.addCustomerAction(
				new CustomerAction(appId, salesPersonId, "SaleAiController", "getLastedImCustomerList", ""));

		List<SalesCustomer> list = salesCustomerService.getCustomerByiMessage(appId, salesPersonId, (page - 1) * limit,
				limit);
		for (SalesCustomer sc : list) {
			int unReadMessageCount = iMessageService.getUnreadIMessageCount(appId, sc.getUserId(),
					sc.getSalesPersonId());
			sc.setUnReadMessageCount(unReadMessageCount);

			List<IMessage> iMessageList = iMessageService.getIMessageList(appId, salesPersonId, sc.getUserId(), 0, 10);// 默认取10条
			sc.setiMessageList(iMessageList);
		}

		output(response, JsonUtil.buildCustomJson("1", "success", list));
	}

	// 4.2.增加最新消息的销售客户列表
	@RequestMapping("/addCustomerImessages")
	public void addCustomerImessages(HttpServletResponse response, List<IMessage> iMessageList) throws IOException {
		// 这里后续优化批量更新
		for (IMessage im : iMessageList) {
			iMessageService.addIMessage(im);
			// 更新最新联系状态
			SalesCustomer sc = salesCustomerService.getCustomer(im.getAppId(), im.getUserId(), im.getFromId());
			sc.setLastChatTime(new Date());
			salesCustomerService.updateSalesCustomer(sc);
		}
		output(response, JsonUtil.buildFalseJson("1", "success"));
	}

	@RequestMapping("/addCustomerImessage")
	public void addCustomerImessage(HttpServletResponse response, IMessage im) throws IOException {
		// 这里后续优化批量更新
		log.info(String.format("%s|%s|%s", im.getAppId(), im.getFromId(), "addCustomerImessage"));
		customerActionService.addCustomerAction(new CustomerAction(im.getAppId(), im.getFromId(), "SaleAiController",
				"addCustomerImessage", im.getContent()));

		im.setCreateTime(new Date());
		iMessageService.addIMessage(im);
		// 更新最新联系状态
		SalesCustomer sc = salesCustomerService.getCustomer(im.getAppId(), im.getUserId(), im.getFromId());
		if (sc == null) {
			sc = salesCustomerService.getCustomer(im.getAppId(), im.getFromId(), im.getUserId());
		}
		if (sc.getStatus() < 1) {
			sc.setStatus(1);
		}
		sc.setLastChatTime(new Date());
		salesCustomerService.updateSalesCustomer(sc);
		output(response, JsonUtil.bulidObjectJson(im));

		sendNotifyMessage(im, sc);
	}

	private void sendNotifyMessage(IMessage im, SalesCustomer sc) {
		new Thread() {
			public void run() {
				try {
					Thread.sleep(10000);// 30秒没有回复
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				// 临时方案，用来区分是销售还是用户发的消息。后面前端直接传值，不用查库。
				if (null != salesPersonService.getSalesPersonByUserId(im.getFromId())) {
					im.setModel(1);
				}
				if (im.getModel() == 0) {
					System.out.println("给对应销售发");//
					int salesPersonUserId = sc.getSalesPersonId();
					UserCommon userCommon = userCommonService.getUserByUserId(salesPersonUserId);
					String corpUserId = userCommon.getQyUserId();
					// 发送人名字
					UserCommon cuserCommon = userCommonService.getUserByUserId(sc.getUserId());
					String cname = cuserCommon.getNickName();
					if (cname != null && cname.length() > 9) {
						cname = userCommon.getNickName().substring(0, 9);
					}
					WorkWeixin workWeixin = workWeixinService.getInfoByWorkId(1, sc.getAppId());
					String corpId = workWeixin.getQyId();
					String agentId = workWeixin.getAgentId();
					String corpSecret = workWeixin.getAppSecret();
					CorpPushMessageController.sendCorpMessage(corpId, corpSecret, agentId, corpUserId, null,
							"你有" + cname + "未读消息:" + im.getContent());

				} else if (im.getModel() == 1) {
					System.out.println("给对应用户发");//
					int userId = sc.getUserId();
					UserCommon userCommon = userCommonService.getUserByUserId(userId);
					String cname = userCommon.getNickName();
					String openId = userCommon.getOpenId();
					UserCard uc = userCardService.getUserCard(sc.getSalesPersonId(), sc.getAppId());
					if (uc != null) {
						cname = uc.getRealName();
					}
					if (cname != null && cname.length() > 9) {
						cname = userCommon.getNickName().substring(0, 9);
					}
					UserFormId userFormId = userFormIdService.getUserFormId(sc.getAppId(), userId);
					if (null == userFormId) {
						log.error("userFormId is null,userId:" + userId);
					}

					// String appId = "wx93c16bc6111ff9e8";
					// String appSecret = "3b2f0416e80ed4821958ee7da189cf81";
					ApplicationCommon app = appService.getApplicationCommonById(sc.getAppId());
					String appId = app.getAppId();
					String appSecret = app.getAppSecret();
					PushMessage pm = pushService.getTemplateInfo(sc.getAppId(), 12);
					if (pm == null) {
						System.out.println("PushMessage is null,appId:" + sc.getAppId());
						return;
					}
					String tplId = pm.getTemplateId();

					System.out.println("appId:" + sc.getAppId());
					System.out.println("weixappId:" + appId);
					System.out.println("appSecret:" + appSecret);
					System.out.println("tplId:" + tplId);

					WXPushMessageController.sendWxMessageEx(appId, appSecret, openId, sc.getSalesPersonId(),
							userFormId.getFormId(), cname, im.getContent(),tplId);

				} else { // 系统消息
					System.out.println("自己用户发，告诉系统已经处理了");//
				}

			}
		}.start();
	}

	// 4.3.更新新消息的状态，未读到已读
	@RequestMapping("/updateCustomerImessages")
	public void updateCustomerImessages(HttpServletResponse response, List<IMessage> iMessageList) throws IOException {
		for (IMessage im : iMessageList) {
			im.setStatus(1);// 设置为已读
			im.setReadTime(new Date());
			iMessageService.updateIMessage(im);
		}

		output(response, JsonUtil.buildFalseJson("1", "success"));
	}

	// 4.3.更新新消息的状态，未读到已读
	@RequestMapping("/updateCustomerImessage")
	public void updateCustomerImessage(HttpServletResponse response, IMessage im) throws IOException {
		im.setStatus(1);// 设置为已读
		im.setReadTime(new Date());
		iMessageService.updateIMessage(im);
		output(response, JsonUtil.bulidObjectJson(im));
	}

	// 4.4.获得与通讯人的消息信息/unreadCount
	@RequestMapping("/getCustomerImessage")
	public void getCustomerImessage(HttpServletResponse response, Integer appId, Integer salesPersonId, Integer userId)
			throws IOException {
		List<IMessage> list = iMessageService.getIMessageList(appId, salesPersonId, userId, 0, 10);// 默认取10条

		int unreadCount = iMessageService.getUnreadIMessageCount(appId, salesPersonId, userId);
		if (list != null) {
			unreadCount = list.size();
		}
		output(response, JsonUtil.buildBackJson(list, unreadCount));
		if (unreadCount > 0) {
			for (IMessage im : list) {
				if (im.getFromId() != userId) {
					im.setStatus(1);
				}
			}
			iMessageService.updateIMessageBatch(list);
		}
	}

	// 4.4.1获得未读消息条数
	@RequestMapping("/getUnreadImessageCount")
	public void getUnreadImessageCount(HttpServletResponse response, Integer appId, Integer fromId, Integer userId)
			throws IOException {
		int newMessageCount = 0;
		// 新增消息
		if (fromId != null) {
			newMessageCount = iMessageService.getUnreadIMessageCount(appId, fromId, userId);
		} else {
			newMessageCount = iMessageService.getUnreadIMessageCount(appId, 0, userId);
		}
		output(response, JsonUtil.buildSuccessJsonCount("0", newMessageCount));
	}

	// 5.从客户人维度获得用户行为,用于首页.人//销售对应的所有客户
	@RequestMapping("/getActionUserList")
	public void getActionUserList(HttpServletResponse response, int appId, int salesPersonId, Integer page,
			Integer limit) throws IOException {
		log.info(String.format("%s|%s|%s", appId, salesPersonId, "getActionUserList"));
		customerActionService.addCustomerAction(
				new CustomerAction(appId, salesPersonId, "SaleAiController", "getActionUserList", ""));

		// 1.查找销售下面所有人，
		// 2.每个人的行为；
		List<ActionUser> list = actionUserService.getActionUserList(appId, salesPersonId, (page - 1) * limit, limit);
		for (ActionUser actionUser : list) {
			actionUser.setTypeName("互动");
			actionUser.setObjectTypeName("名片");
			int lookTime = actionRecordService.getActionRecordCountByTypeAndObjectType(appId, salesPersonId,
					actionUser.getUserId(), 0, 1);
			actionUser.getList().add(new ActionColumn("查看名片", lookTime));
			int praiseTime = actionRecordService.getActionRecordCountByTypeAndObjectType(appId, salesPersonId,
					actionUser.getUserId(), 2, 1);
			actionUser.getList().add(new ActionColumn("点赞", praiseTime));
			int copyPhoneTimes = actionRecordService.getActionRecordCountByTypeAndObjectType(appId, salesPersonId,
					actionUser.getUserId(), 6, 6);
			actionUser.getList().add(new ActionColumn("保存电话", copyPhoneTimes));
			int shareTimes = actionRecordService.getActionRecordCountByTypeAndObjectType(appId, salesPersonId,
					actionUser.getUserId(), 1, 1);
			actionUser.getList().add(new ActionColumn("分享名片", shareTimes));
			int visitedProductTimes = actionRecordService.getActionRecordCountByTypeAndObjectType(appId, salesPersonId,
					actionUser.getUserId(), 0, 2);
			actionUser.getList().add(new ActionColumn("查看产品", visitedProductTimes));
			int visitedVibsiteTimes = actionRecordService.getActionRecordCountByTypeAndObjectType(appId, salesPersonId,
					actionUser.getUserId(), 0, 0);
			actionUser.getList().add(new ActionColumn("访问官网", visitedVibsiteTimes));

			int times = lookTime + praiseTime + copyPhoneTimes + shareTimes + visitedProductTimes + visitedVibsiteTimes;
			actionUser.setTimes(times);
		}
		Collections.sort(list, new AIComparator());
		output(response, JsonUtil.buildCustomJson("1", "success", list));
	}

	// 6.从时间维度获得用户人行为, 用于首页.时间列表
	@RequestMapping("/getCustomerActionColumnList")
	public void getCustomerActionColumnList(HttpServletResponse response, int appId, int salesPersonId, Integer page,
			Integer limit) throws IOException {
		log.info(String.format("%s|%s|%s", appId, salesPersonId, "getCustomerActionColumnList"));
		customerActionService.addCustomerAction(
				new CustomerAction(appId, salesPersonId, "SaleAiController", "getCustomerActionColumnList", ""));

		List<ActionColumn> list = actionColumnService.getSalesCustomerActionColumnList(appId, salesPersonId,
				(page - 1) * limit, limit);
		List<SalesCustomerTips> tips = salesCustomerTipsService.getSalesCustomerTipsList(0, 1);
		for (ActionColumn ac : list) {
			// 测试代码，实际情况不会这样；
			if (ac.getTimes() < 1) {
				ac.setTimes(1);
			}
			if (ac.getTimes() > tips.size()) {// 如果次数大于设定，取最大；或者取最大随机，规则待完善；
				int index = new Random().nextInt(tips.size());
				ac.setTips(tips.get(index).getContent());
			} else {
				ac.setTips(tips.get(ac.getTimes() - 1).getContent());
			}

			String spname = ac.getSalesPersonId() + "";
			UserCard uc = userCardService.getUserCard(ac.getSalesPersonId(), appId);
			if (null != uc) {
				spname = userCardService.getUserCard(ac.getSalesPersonId(), appId).getRealName();
				if (null != spname) {
					ac.setSalesPersonName(spname);
				}
			}
			ac.setSalesPersonName(spname);
		}

		output(response, JsonUtil.buildCustomJson("1", "success", list));
	}

	// 6.1从时间维度获得用户人行为, 一个人
	@RequestMapping("/getOneCustomerActionColumnList")
	public void getOneCustomerActionColumnList(HttpServletResponse response, int appId, int salesPersonId, int userId,
			Integer page, Integer limit) throws IOException {
		log.info(String.format("%s|%s|%s", appId, salesPersonId, "getOneCustomerActionColumnList"));
		customerActionService.addCustomerAction(new CustomerAction(appId, salesPersonId, "SaleAiController",
				"getOneCustomerActionColumnList", "userId:" + userId));

		List<ActionColumn> list = actionColumnService.getOneCustomerActionColumnList(appId, salesPersonId, userId,
				(page - 1) * limit, limit);
		for (ActionColumn ac : list) {
			String spname = ac.getSalesPersonId() + "";
			UserCard uc = userCardService.getUserCard(ac.getSalesPersonId(), appId);
			if (null != uc) {
				spname = userCardService.getUserCard(ac.getSalesPersonId(), appId).getRealName();
				if (null != spname) {
					ac.setSalesPersonName(spname);
				}
			}
			ac.setSalesPersonName(spname);
		}
		output(response, JsonUtil.buildCustomJson("1", "success", list));
	}

	// 7.销售人员对于一个行为下所有人,用户与各种行为分类查询
	@RequestMapping("/getTypeActionList")
	public void getTypeActionList(HttpServletResponse response, int appId, int salesPersonId, int type, int objectType,
			Integer page, Integer limit) throws IOException {
		log.info(String.format("%s|%s|%s", appId, salesPersonId, "getTypeActionList"));
		customerActionService.addCustomerAction(new CustomerAction(appId, salesPersonId, "SaleAiController",
				"getTypeActionList", String.format("type:%s|objectType:%s", type, objectType)));

		List<ActionColumn> list = actionColumnService.getActionColumnListBySpAndType(appId, salesPersonId, type,
				objectType, (page - 1) * limit, limit);
		for (ActionColumn ac : list) {
			String spname = ac.getSalesPersonId() + "";
			UserCard uc = userCardService.getUserCard(ac.getSalesPersonId(), appId);
			if (null != uc) {
				spname = userCardService.getUserCard(ac.getSalesPersonId(), appId).getRealName();
				if (null != spname) {
					ac.setSalesPersonName(spname);
				}
			}
			ac.setSalesPersonName(spname);
		}
		output(response, JsonUtil.buildCustomJson("1", "success", list));
	}

	// 8.个人用户行为信息，按照时间维度显示，用户查看个人.互动。
	@RequestMapping("/getCustomerActionList")
	public void getCustomerActionList(HttpServletResponse response, int appId, int userId, Integer page, Integer limit)
			throws IOException {
		log.info(String.format("%s|%s|%s", appId, userId, "getCustomerActionList"));
		customerActionService
				.addCustomerAction(new CustomerAction(appId, userId, "SaleAiController", "getCustomerActionList", ""));

		List<ActionColumn> list = actionColumnService.getCustomerActionColumnList(appId, userId, (page - 1) * limit,
				limit);
		output(response, JsonUtil.buildCustomJson("1", "success", list));
	}

	// 9.获取客户信息
	@RequestMapping("/getCusTomerByUserId")
	public void getCusTomerByUserId(HttpServletResponse response, Integer appId, Integer userId, Integer page,
			Integer limit) {
		List<SalesCustomer> list = salesCustomerService.getCustomerByUserId(appId, userId, (page - 1) * limit, limit);
		output(response, JsonUtil.buildJson(list));
	}

	/**
	 * 更新销售和客户的关系
	 * 
	 * @param response
	 * @param sc
	 */
	@RequestMapping("/updateSaleCusStatus")
	public void updateSaleCusStatus(HttpServletResponse response, SalesCustomer sc) {
		salesCustomerService.updateSalesCustomer(sc);
		output(response, JsonUtil.buildFalseJson("0", "更新成功"));
	}

	// 获取默认的销售
	@RequestMapping("/getDefaultSalerList")
	public void getDefaultSalerList(HttpServletResponse response, Integer isdefault, Integer appId) {
		List<SalesPerson> lists = salesPersonService.getDefaultSalerList(isdefault, appId);
		output(response, JsonUtil.buildJson(lists));
	}

	// 9.获取话术类型
	@RequestMapping("/getSalesMessageType")
	public void getSalesMessageType(HttpServletResponse response, int appId, int userId) throws IOException {
		log.info(String.format("%s|%s|%s", appId, userId, "getSalesMessageType"));
		customerActionService
				.addCustomerAction(new CustomerAction(appId, userId, "SaleAiController", "getSalesMessageType", ""));

		List<SalesMessageType> list = salesMessageTypeService.getSalesMessageTypeList(appId, userId);
		output(response, JsonUtil.buildCustomJson("1", "success", list));
	}

	// 10.获取话术类容
	@RequestMapping("/getSalesMessage")
	public void getSalesMessage(HttpServletResponse response, int appId, int userId, int type) throws IOException {
		log.info(String.format("%s|%s|%s", appId, userId, "getSalesMessage"));
		List<SalesMessage> list = salesMessageService.getSalesMessageList(appId, userId, type);
		output(response, JsonUtil.buildCustomJson("1", "success", list));
	}

	// 编辑销售话术
	@RequestMapping("/editSalesMessage")
	public void editSalesMessage(HttpServletResponse response, SalesMessage message) throws IOException {
		salesMessageService.updateSalesMessage(message.getAppId(), message.getType(), message.getId(),
				message.getMessage(), new Date());

		output(response, JsonUtil.buildFalseJson("1", "保存成功"));
	}

	// 新增销售话术
	@RequestMapping("/addSalesMessage")
	public void addSalesMessage(HttpServletResponse response, SalesMessage message) throws IOException {
		salesMessageService.addSalesMessage(message);

		output(response, JsonUtil.buildFalseJson("1", "增加成功"));
	}

	// 删除销售话术
	@RequestMapping("/delSalesMessage")
	public void delSalesMessage(HttpServletResponse response, SalesMessage message) throws IOException {
		salesMessageService.delSalesMessage(message.getAppId(), message.getId());
		output(response, JsonUtil.buildFalseJson("1", "成功删除"));
	}

	// 获得销售人员跟进订单记录
	@RequestMapping("/getOrderFollowListBySalesPersonId")
	public void getOrderFollowListBySalesPersonId(HttpServletResponse response, int appId, int salesPersonId,
			Integer userId, Integer page, Integer limit) throws IOException {
		log.info(String.format("%s|%s|%s", appId, userId, "getOrderFollowListBySalesPersonId"));
		customerActionService.addCustomerAction(
				new CustomerAction(appId, userId, "SaleAiController", "getOrderFollowListBySalesPersonId", ""));

		if (userId == null) {
			userId = 0;
		}
		List<OrderFollow> list = orderFollowService.getOrderFollowListBySalesPersonId(appId, salesPersonId, userId,
				page, limit);
		output(response, JsonUtil.buildCustomJson("1", "success", list));
	}

	// 销售人员添加跟进信息
	@RequestMapping("/addOrderFollow")
	public void addOrderFollow(HttpServletResponse response, OrderFollow orderFollow) throws IOException {
		orderFollowService.addOrderFollow(orderFollow);
		SalesCustomer sc = salesCustomerService.getCustomer(orderFollow.getAppId(), orderFollow.getUserId(),
				orderFollow.getUserId());
		if (sc == null) {
			sc = salesCustomerService.getCustomer(orderFollow.getAppId(), orderFollow.getSalesPersonId(),
					orderFollow.getUserId());
		}
		if (sc.getStatus() < 1) {
			sc.setStatus(2);
		}
		sc.setLastChatTime(new Date());
		salesCustomerService.updateSalesCustomer(sc);
		output(response, JsonUtil.buildFalseJson("1", "数据上报成功"));
	}

	// 获取客户标签列表
	@RequestMapping("/getCustomerLabel")
	public void getCustomerLabel(HttpServletResponse response, int appId, int salesPersonId) throws IOException {
		List<CustomerLabel> list = customerLabelService.getCustomerUpperLabelList(0);

		for (CustomerLabel cl : list) {
			List<CustomerLabel> list1 = customerLabelService.getCustomerLabelList(appId, salesPersonId, cl.getId());
			cl.setCustomerLabelList(list1);
		}
		output(response, JsonUtil.buildCustomJson("1", "success", list));
	}

	// 获取单个客户标签列表
	@RequestMapping("/getOneCustomerLabel")
	public void getOneCustomerLabel(HttpServletResponse response, int appId, int salesPersonId, int userId)
			throws IOException {
		List<CustomerLabelRecord> list = customerLabelRecordService.getCustomerLabelRecordList(appId, userId);
		
		if(list==null||list.isEmpty())
		{
			CustomerLabelRecord customerLabelRecord = new CustomerLabelRecord();
			customerLabelRecord.setAppId(appId);
			customerLabelRecord.setLabelId(23);
			customerLabelRecord.setSalesPersonId(salesPersonId);
			customerLabelRecord.setUserId(userId);
			customerLabelRecord.setCreateTime(new Date());
			customerLabelRecordService.addCustomerLabelRecord(customerLabelRecord);
			
			CustomerLabelRecord customerLabelRecord1 = new CustomerLabelRecord();
			customerLabelRecord1.setAppId(appId);
			customerLabelRecord1.setLabelId(33);
			customerLabelRecord1.setSalesPersonId(salesPersonId);
			customerLabelRecord1.setUserId(userId);
			customerLabelRecord1.setCreateTime(new Date());
			customerLabelRecordService.addCustomerLabelRecord(customerLabelRecord1);
			list = customerLabelRecordService.getCustomerLabelRecordList(appId, userId);
		}
		
		output(response, JsonUtil.buildCustomJson("1", "success", list));
	}

	// 获取单个客户标签列表
	@RequestMapping("/modifyCustomerLabel")
	public void ModifyCustomerLabel(HttpServletResponse response, int appId, int salesPersonId, int userId, int labelId,
			int status) throws IOException {
		CustomerLabelRecord clr = customerLabelRecordService.getCustomerLabelRecord(appId, userId, labelId);
		if (status == 0 && clr != null) {
			customerLabelRecordService.delCustomerLabelRecord(appId, clr.getId());
			output(response, JsonUtil.buildFalseJson("1", "success del label"));
			return;
		}
		if (status == 1 && clr == null) {
			CustomerLabelRecord customerLabelRecord = new CustomerLabelRecord();
			customerLabelRecord.setAppId(appId);
			customerLabelRecord.setLabelId(labelId);
			customerLabelRecord.setSalesPersonId(salesPersonId);
			customerLabelRecord.setUserId(userId);
			customerLabelRecord.setCreateTime(new Date());
			customerLabelRecordService.addCustomerLabelRecord(customerLabelRecord);
		}
		output(response, JsonUtil.buildFalseJson("1", "success add"));
	}

	// 获取跟进客户常用户语
	@RequestMapping("/getOrderFollowTipsList")
	public void getOrderFollowTipsList(HttpServletResponse response, int appId, int userId, Integer type, Integer page,
			Integer limit) throws IOException {
		customerActionService.addCustomerAction(
				new CustomerAction(appId, userId, "SaleAiController", "getOrderFollowTipsList", "type:" + type));

		List<OrderFollowTips> list = orderFollowTipsService.getOrderFollowTipsList(appId, userId, type, page, limit);
		output(response, JsonUtil.buildCustomJson("1", "success", list));
	}

	// 增加跟进客户常用户语
	@RequestMapping("/addOrderFollowTips")
	public void addOrderFollowTips(HttpServletResponse response, Integer appId, Integer userId, String content) {
		OrderFollowTips orderFollowTips = new OrderFollowTips();
		orderFollowTips.setAppId(appId);
		orderFollowTips.setUserId(userId);
		orderFollowTips.setContent(content);
		orderFollowTips.setCreateTime(new Date());
		orderFollowTipsService.addOrderFollowTips(orderFollowTips);

		output(response, JsonUtil.bulidObjectJson(orderFollowTips));
	}

	// 更新跟进客户常用户语
	@RequestMapping("/updateOrderFollowTips")
	public void updateOrderFollowTips(HttpServletResponse response, Integer appId, Integer userId, Integer id,
			String content) {
		OrderFollowTips orderFollowTips = orderFollowTipsService.getOrderFollowTips(appId, userId, id);
		if (null == orderFollowTips) {
			output(response, JsonUtil.buildFalseJson("2", "不存在！"));
			return;
		} else {
			orderFollowTips.setContent(content);
		}
		orderFollowTipsService.updateOrderFollowTips(orderFollowTips);
		output(response, JsonUtil.bulidObjectJson(orderFollowTips));
	}

	// 删除跟进客户常用户语
	@RequestMapping("/delOrderFollowTips")
	public void delOrderFollowTips(HttpServletResponse response, Integer appId, Integer userId, Integer id) {
		OrderFollowTips orderFollowTips = orderFollowTipsService.getOrderFollowTips(appId, userId, id);
		if (null == orderFollowTips) {
			output(response, JsonUtil.buildFalseJson("2", "不存在！"));
			return;
		}
		orderFollowTipsService.delOrderFollowTips(orderFollowTips);
		output(response, JsonUtil.buildFalseJson("0", "删除成功！"));
	}
}
