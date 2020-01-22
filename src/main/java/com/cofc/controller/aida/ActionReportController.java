package com.cofc.controller.aida;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cofc.controller.pushmessage.CorpPushMessageController;
import com.cofc.pojo.DescoveryCommon;
import com.cofc.pojo.GoodsCommon;
import com.cofc.pojo.UserCommon;
import com.cofc.pojo.aida.ActionRecord;
import com.cofc.pojo.aida.BossDashboard;
import com.cofc.pojo.aida.CustomerDetail;
import com.cofc.pojo.aida.IMessage;
import com.cofc.pojo.aida.SalesAbility;
import com.cofc.pojo.aida.SalesCustomer;
import com.cofc.pojo.aida.SalesPerson;
import com.cofc.pojo.aida.UserFormId;
import com.cofc.pojo.aida.WorkWeixin;
import com.cofc.pojo.dataAnalysis.AiDashboard;
import com.cofc.pojo.dataAnalysis.DataRank;
import com.cofc.pojo.goods.BrokerageUserInvite;
import com.cofc.service.DescoveryCommonService;
import com.cofc.service.GoodsCommonService;
import com.cofc.service.UserCommonService;
import com.cofc.service.aida.ActionRecordService;
import com.cofc.service.aida.CustomerDetailService;
import com.cofc.service.aida.IMessageService;
import com.cofc.service.aida.SalesAbilityService;
import com.cofc.service.aida.SalesCustomerService;
import com.cofc.service.aida.SalesPersonService;
import com.cofc.service.aida.UserFormIdService;
import com.cofc.service.aida.WorkWeixinService;
import com.cofc.service.dataAnalysis.DataRankService;
import com.cofc.service.goods.BrokerageUserInviteService;
import com.cofc.timer.BossDataAnalysisTimer;
import com.cofc.util.BaseUtil;
import com.cofc.util.JsonUtil;

@Controller
@RequestMapping("/aida")
public class ActionReportController extends BaseUtil {
	@Resource
	private ActionRecordService actionRecordService;
	@Resource
	private SalesPersonService salesPersonService;
	@Resource
	private SalesCustomerService salesCustomerService;
	@Resource
	CustomerDetailService customerDetailService;
	@Resource
	GoodsCommonService goodsCommonService;
	@Resource
	DescoveryCommonService descoveryCommonService;
	@Resource
	private UserFormIdService userFormIdService;
	@Autowired
	private DataRankService dataRankService;
	@Autowired
	private SalesAbilityService salesAbilityService;
	@Autowired
	UserCommonService userCommonService;
	@Resource
	private WorkWeixinService workWeixinService;
	@Resource
	private IMessageService iMessageService;
	@Resource
	private BrokerageUserInviteService brokerageUserInviteService;

	public static Logger log = Logger.getLogger("ActionReportController");

	// 1.用户点击名片事件上报
	@RequestMapping("/reportUserCardBehavior")
	public void reportUserCardBehavior(HttpServletResponse response, ActionRecord actionRecord) throws IOException {
		// type:0为访问，1为分享;2为点赞，3.评论，4.购买下单,5.点击(按钮),6.保存；7,拨打,8.查看（比如需要统计图片停在上面）
		// ObjectType:0为官网，1为名片，2为产品,包括商城，3为活动，4为游戏，5为广告；
		UserCommon userCommon = userCommonService.getUserByUserId(actionRecord.getUserId());

		log.info(userCommon.getUserId() + "(" + userCommon.getNickName() + ")" + "上报:" + actionRecord.getType() + "|"
				+ actionRecord.getObjectType() + "|" + actionRecord.getObjectId() + "|" + "formId:"
				+ actionRecord.getFormId() + "|" + "salesperson:" + actionRecord.getSalesPersonId());

		int appId = actionRecord.getAppId();
		int salesPersonId = actionRecord.getSalesPersonId();
		int salesCutomerId = actionRecord.getUserId();
		SalesCustomer customer = salesCustomerService.getCustomer(appId, salesPersonId, salesCutomerId);
		int times = actionRecordService.getSinlgeActionRecordCount(appId, salesPersonId, actionRecord.getUserId(),
				actionRecord.getType(), actionRecord.getObjectType(), actionRecord.getObjectId());// 后面两个参数不需要先
		actionRecord.setTimes(times + 1);

		Boolean isFirst = (customer == null ? true : false);
		log.info("新用户：" + userCommon.getUserId() + "(" + userCommon.getNickName() + ")");

		switch (actionRecord.getObjectType()) {
		case 0:// 官网
			switch (actionRecord.getType()) {
			case 0:
				if (isFirst) {
					customer = new SalesCustomer();
					customer.setVisitedWebsiteCount(1);
				} else {
					customer.setVisitedWebsiteCount(customer.getVisitedWebsiteCount() + 1);
				}
				break;
			case 1:
				break;
			default:
				break;
			}
			break;
		case 1:// 名片
			switch (actionRecord.getType()) {
			case 0:
				if (isFirst) {
					customer = new SalesCustomer();
					customer.setVisitedCardCount(1);
				} else {
					customer.setVisitedCardCount(customer.getVisitedCardCount() + 1);
				}
				break;
			case 1:
				break;
			default:
				break;
			}
			break;
		case 2:// 商城和产品
			switch (actionRecord.getType()) {
			case 0:
				if (isFirst) {
					customer = new SalesCustomer();
					customer.setVisitedShopCount(1);
				} else {
					customer.setVisitedShopCount(customer.getVisitedShopCount() + 1);
				}
				break;
			case 1:
				// 需要记录分享产品人之间关系和时间
				Integer goodsId = actionRecord.getObjectId();
				Integer inviteUserId = actionRecord.getSourceId();
				if (goodsId != null && inviteUserId != null) {
					BrokerageUserInvite brokerageUserInvite = brokerageUserInviteService.getBrokerageUserInvite(appId,
							goodsId, actionRecord.getUserId());
					if (brokerageUserInvite == null) {
						brokerageUserInvite = new BrokerageUserInvite();
						brokerageUserInvite.setAppId(appId);
						brokerageUserInvite.setUserId(actionRecord.getUserId());
						brokerageUserInvite.setInviteUserId(inviteUserId);
						brokerageUserInvite.setCreateTime(new Date());
						brokerageUserInviteService.addBrokerageUserInvite(brokerageUserInvite);
						
						log.debug(String.format("inviteUserId:%s邀请userId:%s(%s)访问了产品goodsId:%s", 
								brokerageUserInvite.getInviteUserId(), userCommon.getUserId(),userCommon.getNickName(),goodsId));
					} else {
						// 这里有两种情况，一是之前有人邀请过，另外是同一个邀请需要需要更新。
						if (brokerageUserInvite.getInviteUserId() == inviteUserId.intValue()) {
							brokerageUserInvite.setLastTime(new Date());
							brokerageUserInviteService.updateBrokerageUserInvite(brokerageUserInvite);
						} else {
							log.debug(String.format("产品goodsId:%s|已经被inviteUserId:%s邀请过了,userId:%s来晚了", goodsId,
									brokerageUserInvite.getInviteUserId(), inviteUserId));
						}
					}
				} else {
					log.debug(String.format("goodsId:%s|inviteUserId:%s", goodsId, inviteUserId));
				}
				break;
			default:
				break;
			}
			break;
		case 3:// 咨询
			switch (actionRecord.getType()) {
			case 0:
				if (isFirst) {
					customer = new SalesCustomer();
					customer.setVisitedOtherCount(1);
				} else {
					customer.setVisitedOtherCount(customer.getVisitedOtherCount() + 1);
				}
				break;
			case 1:
				if (isFirst) {
					customer = new SalesCustomer();
				}
				customer.setLastFormId(actionRecord.getFormId());
				break;
			default:
				break;
			}
			break;
		case 4:// 游戏
			switch (actionRecord.getType()) {
			case 0:
				if (isFirst) {
					customer = new SalesCustomer();
					customer.setVisitedGameCount(1);
				} else {
					customer.setVisitedGameCount(customer.getVisitedGameCount() + 1);
				}
				break;
			case 1:
				break;
			default:
				break;
			}
			break;
		case 5:// 广告
			switch (actionRecord.getType()) {
			case 0:
				break;
			case 1:
				break;
			case 2:
				break;
			case 3:
				break;
			case 4:
				break;
			case 5:
				break;
			case 6:
				break;
			case 7:
				break;
			case 8:
				break;
			default:
				break;
			}
			break;
		default:
			if (isFirst) {
				customer = new SalesCustomer();
				customer.setVisitedOtherCount(1);
			} else {
				customer.setVisitedOtherCount(customer.getVisitedOtherCount() + 1);
			}
			break;
		}
		if (isFirst) {
			if (customer == null) {
				customer = new SalesCustomer();
			}
			customer.setAppId(appId);
			customer.setUserId(salesCutomerId);
			customer.setSalesPersonId(salesPersonId);
			customer.setLastChatTime(new Date());
			salesCustomerService.addSalesCustomer(customer);

			// 首次创建客户与销售关系时，创建客户详情表；
			CustomerDetail customerDetail = new CustomerDetail();
			customerDetail.setAppId(appId);
			customerDetail.setSalesPersonId(salesPersonId);
			customerDetail.setUserId(customer.getUserId());
			customerDetail.setCreateTime(new Date());
			if(userCommon.getCompony() != null && !userCommon.getCompony().equals("")){
				customerDetail.setCompany(userCommon.getCompony());
			}
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
			customerDetailService.addCustomerDetail(customerDetail);
			// String corpUserId = userCommon.getQyUserId();
			String cname = userCommon.getNickName();
			if (cname != null && cname.length() > 9) {
				cname = userCommon.getNickName().substring(0, 9);
			}
			WorkWeixin workWeixin = workWeixinService.getInfoByWorkId(1, customer.getAppId());
			String corpId = workWeixin.getQyId();
			String agentId = workWeixin.getAgentId();
			String corpSecret = workWeixin.getAppSecret();

			// 消息发给销售，所以需要销售的企业ID
			String corpUserId = userCommonService.getUserByUserId(customer.getSalesPersonId()).getQyUserId();
			CorpPushMessageController.sendCorpMessage(corpId, corpSecret, agentId, corpUserId, null,
					"新客户:" + cname + "来了，赶快联系TA吧!");

			// 给销售添加一条信息在即时通讯里面
			IMessage im = new IMessage();
			im.setAppId(appId);
			im.setContent("小二,来杯咖啡,了解下您们的产品和服务！");
			im.setFromId(customer.getUserId());
			im.setUserId(customer.getSalesPersonId());
			im.setModel(3);
			im.setType(0);
			im.setCreateTime(new Date());
			iMessageService.addIMessage(im);

			// SalesCustomer sc =
			// salesCustomerService.getCustomer(im.getAppId(), im.getUserId(),
			// im.getFromId());
			// sc.setLastChatTime(new Date());
			// salesCustomerService.updateSalesCustomer(sc);

		} else {
			// 临时处理,更新用户最新登陆时间。
			customer.setCreateTime(null);
			customer.setExpectedDate(null);
			customer.setLastTime(new Date());
			salesCustomerService.updateSalesCustomer(customer);
		}
		actionRecord.initName();
		if (actionRecord.getObjectType() == 2) {// 补充访问具体什么产品
			GoodsCommon goods = goodsCommonService.getGoodsById(actionRecord.getObjectId());
			if (goods != null && goods.getGoodsName() != null) {
				actionRecord.setObjectName(goods.getGoodsName());
			}
		} else if (actionRecord.getObjectType() == 3)// 补充访问具体动态
		{
			DescoveryCommon dc = descoveryCommonService.getDescoveryById(actionRecord.getObjectId());
			if (dc != null && dc.getDescoveryTitle() != null) {
				actionRecord.setObjectName(dc.getDescoveryTitle());
			}
		}
		log.debug("保存行为前");
		actionRecordService.addActionRecord(actionRecord);
		log.debug("保存行为后");
		// 更新formID;
		String formId = actionRecord.getFormId();
		if (formId != null && formId != "") {
			int userId = actionRecord.getUserId();
			UserFormId ufi = userFormIdService.getUserFormId(appId, userId);
			if (ufi == null) {// 没有添加
				ufi = new UserFormId();
				ufi.setAppId(appId);
				ufi.setUserId(userId);
				ufi.setFormId(formId);
				ufi.setTimes(0);
				ufi.setLastTime(new Date());
				userFormIdService.addUserFormId(ufi);
			} else {
				if (formId != ufi.getFormId()) {// 存在是否相等或者为空，不相等更新，为空和相等增加使用次数
					ufi.setFormId(formId);
					userFormIdService.updateUserFormId(ufi);
				}
			}
		}
		output(response, JsonUtil.buildFalseJson(isFirst + "", "数据上报成功"));
	}

	// 测试AI能力计算是否正确
	@RequestMapping("/testSalesAbility")
	public void testSalesAbility(HttpServletResponse response, Integer appId) throws IOException {
		// 1.获客能力.取名片访问量
		List<DataRank> list1 = dataRankService.getDataRank(701, 0, 0, 0, 1);
		// 2.个人魅力.取点赞数
		List<DataRank> list2 = dataRankService.getDataRank(701, 0, 0, 2, 1);
		// 3.宣传产品.产品访问量
		List<DataRank> list3 = dataRankService.getDataRank(701, 0, 0, 0, 2);
		// 4.宣传公司.引流官网访问量
		List<DataRank> list4 = dataRankService.getDataRank(701, 0, 0, 0, 0);
		// 5.客户交流
		List<DataRank> list5 = dataRankService.getDataRank(701, 0, 0, 3, 2);
		// 6.主动性.分享次数
		List<DataRank> list6 = dataRankService.getDataRank(701, 0, 0, 1, 1);

		List<SalesAbility> listSa = salesAbilityService.getSalesAbilityList(701);
		for (SalesAbility sa : listSa) {
			for (DataRank dr : list1) {
				if (sa.getSalesPersonId() == dr.getUserId()) {
					sa.setGetCustomerBt((int) (50 + 50 / Math.pow(dr.getRank(), 1.0 / 8)));
				}
			}
			for (DataRank dr : list2) {
				if (sa.getSalesPersonId() == dr.getUserId()) {
					sa.setCharmBt((int) (50 + 50 / Math.pow(dr.getRank(), 1.0 / 8)));
				}
			}
			for (DataRank dr : list3) {
				if (sa.getSalesPersonId() == dr.getUserId()) {
					sa.setSpreadProductBt((int) (50 + 50 / Math.pow(dr.getRank(), 1.0 / 8)));
				}
			}
			for (DataRank dr : list4) {
				if (sa.getSalesPersonId() == dr.getUserId()) {
					sa.setSpreadWebsiteBt((int) (50 + 50 / Math.pow(dr.getRank(), 1.0 / 8)));
				}
			}
			for (DataRank dr : list5) {
				if (sa.getSalesPersonId() == dr.getUserId()) {
					sa.setCustomerInteractBt((int) (50 + 50 / Math.pow(dr.getRank(), 1.0 / 8)));
				}
			}
			for (DataRank dr : list6) {
				if (sa.getSalesPersonId() == dr.getUserId()) {
					sa.setSalesInitiativeBt((int) (50 + 50 / Math.pow(dr.getRank(), 1.0 / 8)));
				}
			}
			salesAbilityService.updateSalesAbility(sa);
		}

		output(response, JsonUtil.buildCustomJson("0", "success", listSa));
	}
}
