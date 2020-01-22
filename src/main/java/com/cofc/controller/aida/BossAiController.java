package com.cofc.controller.aida;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.annotation.Resource;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cofc.pojo.aida.ActionColumn;
import com.cofc.pojo.aida.BossDashboard;
import com.cofc.pojo.aida.SalesPerson;
import com.cofc.pojo.aida.UserFormId;
import com.cofc.pojo.dataAnalysis.AiDashboard;
import com.cofc.pojo.dataAnalysis.AiTrend;
import com.cofc.pojo.dataAnalysis.CustomerAction;
import com.cofc.service.aida.ActionRecordService;
import com.cofc.service.aida.SalesCustomerService;
import com.cofc.service.aida.SalesPersonService;
import com.cofc.service.aida.UserFormIdService;
import com.cofc.service.dataAnalysis.AiDashboardService;
import com.cofc.service.dataAnalysis.AiTrendService;
import com.cofc.service.dataAnalysis.CustomerActionService;
import com.cofc.util.BaseUtil;
import com.cofc.util.JsonUtil;

@Controller
@RequestMapping("/aida")
public class BossAiController extends BaseUtil {

	public static Logger log = Logger.getLogger("BossAiController");
	@Resource
	private ActionRecordService actionRecordService;
	@Resource
	private SalesPersonService salesPersonService;
	@Resource
	private SalesCustomerService salesCustomerService;
	@Resource
	private UserFormIdService userFormIdService;
	@Resource
	private AiDashboardService aiDashboardService;
	@Resource
	private AiTrendService aiTrendService;
	@Resource
	private CustomerActionService customerActionService;
	
	// 1.获得销售员仪表盘整体信息
	@RequestMapping("/getBossDashboard")
	public void getBossDashboard(HttpServletResponse response, int appId, int salesId,Integer lastdays)
			throws IOException {
		log.info(String.format("%s|%s|%s", appId,salesId,"getBossDashboard"));
		customerActionService.addCustomerAction(new CustomerAction(appId, salesId, "BossAiController",
				"getBossDashboard", "lastdays:"+lastdays));
		
		AiDashboard ad = aiDashboardService.getAiDashboard(appId, 0, lastdays);
		BossDashboard bossDashboard = new BossDashboard();

		// 1.商城订单数， 可以从商城获取
		bossDashboard.setOrderCount(ad.getOrderCount());
		// 2.商城销售金额 可以从商城获取
		bossDashboard.setSalesAmount(ad.getSalesAmount());
		// 3.客户总数，可以从sales_customer 获取；
		bossDashboard.setCustomerAmount(ad.getCustomerCount());
		// 4.跟进总数
		bossDashboard.setMerchandiserAmount(ad.getFollowOrderCount());
		// 5.浏览总数
		bossDashboard.setVisitedAmount(ad.getVisitedCount());
		// 6.被转发总数
		bossDashboard.setSharedAmount(ad.getSharedCount());
		// 7.电话被保存总数 从action_record表获取；
		bossDashboard.setSavedAmount(ad.getSavedCount());
		// 8.被点赞总数 从action_record表获取；
		bossDashboard.setPraisedAmount(ad.getPraisedCount());

		List<ActionColumn> actionColumnList = bossDashboard.getActionColumnList();
		actionColumnList.add(new ActionColumn("保存电话", ad.getSavedCount()));
		actionColumnList.add(new ActionColumn("点赞", ad.getPraisedCount()));
		actionColumnList.add(new ActionColumn("咨询产品", ad.getConsultCount()));
		actionColumnList.add(new ActionColumn("拨打电话", ad.getSavedCount()/3));
		actionColumnList.add(new ActionColumn("分享", ad.getSharedCount()));
		actionColumnList.add(new ActionColumn("评论", new Random().nextInt(20)));
		
		/**
		 * 趋势图
		 */
		AiTrend aiTrend1 = aiTrendService.getAiTrend(ad.getAppId(), 0, 7, 1);
		if (null != aiTrend1) {
			bossDashboard.setVisitedAmountListStr(aiTrend1.getList());
		}
		AiTrend aiTrend2 = aiTrendService.getAiTrend(ad.getAppId(), 0, 7, 2);
		if (null != aiTrend2) {
			bossDashboard.setNewCustomerListStr(aiTrend2.getList());
		}

		output(response, JsonUtil.objectToJson(bossDashboard));
	}

	// 2.销售按客户排行:按照type:0为客户人数，1为订单数，2为潜在成交率，3为互动次数；4为新增客户人数，
	@RequestMapping("/getSalesRanking")
	public void getSalesRanking(HttpServletResponse response, int appId,Integer salesId, int departmentId, int type, int lastdays,
			Integer page, Integer limit) throws IOException {
		log.info(String.format("%s|%s|%s", appId,0,"getBossDashboard"));
		if(salesId==null)
		{
			salesId=0;
		}
		customerActionService.addCustomerAction(new CustomerAction(appId, salesId, "BossAiController",
				"getSalesRanking", String.format("type:%s|lastdays:%s",type,lastdays)));
		
		List<SalesPerson> list = null;
		switch (type) {
		case 0:
			list = salesPersonService.getSalesPersonRankingListByType(appId, departmentId, type, (page - 1) * limit,
					limit);
			break;
		case 1:
			list = salesPersonService.getSalesPersonRankingListByType1(appId, departmentId, type, (page - 1) * limit,
					limit);
			break;
		case 2:
			list = salesPersonService.getSalesPersonRankingListByType2(appId, departmentId, type, (page - 1) * limit,
					limit);
			break;
		case 3:
			list = salesPersonService.getSalesPersonRankingListByType3(appId, departmentId, type, (page - 1) * limit,
					limit);
			break;
		case 4:
			list = salesPersonService.getSalesPersonRankingListByType4(appId, departmentId, type, (page - 1) * limit,
					limit);
			break;
		default:
			list = salesPersonService.getSalesPersonRankingListByType(appId, departmentId, type, (page - 1) * limit,
					limit);
			break;
		}

		output(response, JsonUtil.buildCustomJson("1", "success", list));
	}

	// 2.1销售按订单排行:按照type:0为订单数，1为潜在成交率，2为互动次数；
//	@RequestMapping("/salesAmountRanking")
//	public void getSalesAmountRanking(HttpServletResponse response, int appId, Integer salesId,int type, Integer page, Integer limit)
//			throws IOException {
//		log.info(String.format("%s|%s|%s", appId,0,"getBossDashboard"));
//		if(salesId==null)
//		{
//			salesId=0;
//		}
//		customerActionService.addCustomerAction(new CustomerAction(appId, salesId, "BossAiController",
//				"salesAmountRanking", String.format("type:%s",type)));
//		
//		List<SalesPerson> list = salesPersonService.getSalesPersonList(appId, (page - 1) * limit, limit);
//		output(response, JsonUtil.buildCustomJson("1", "success", list));
//	}

	// 3.获得销售员的，AI分析,departmentId为部门，或者区域
	@RequestMapping("/getSalesPersonAnalysis")
	public void getSalesPersonAnalysis(HttpServletResponse response, int appId,Integer salesId, int departmentId, Integer page,
			Integer limit) throws IOException {
		log.info(String.format("%s|%s|%s", appId,0,"getSalesPersonAnalysis"));
		if(salesId==null)
		{
			salesId=0;
		}
		customerActionService.addCustomerAction(new CustomerAction(appId, salesId, "BossAiController",
				"getSalesPersonAnalysis", ""));
		
		List<SalesPerson> list = salesPersonService.getSalesPersonListOrderByBt(appId, (page - 1) * limit, limit);
			
		Collections.sort(list, new SalesAbilityComparator());
		output(response, JsonUtil.buildCustomJson("1", "success", list));
	}

//	// 4.客户行为总数统计；
//	@RequestMapping("/getCustomeActionAmount")
//	public void getCustomeActionAmount(HttpServletResponse response, int appId,Integer salesId) throws IOException {
//		log.info(String.format("%s|%s|%s", appId,0,"getCustomeActionAmount"));
//		if(salesId==null)
//		{
//			salesId=0;
//		}
//		customerActionService.addCustomerAction(new CustomerAction(appId, salesId, "BossAiController",
//				"getCustomeActionAmount", ""));
//		
//		List<ActionColumn> list = new ArrayList<ActionColumn>();	
//		output(response, JsonUtil.buildCustomJson("1", "success", list));
//	}

	// 4.获得formID
	@RequestMapping("/getUserFormId")
	public void getUserFormId(HttpServletResponse response, Integer appId, Integer userId, String formId)
			throws IOException {
		UserFormId ufi = userFormIdService.getUserFormId(appId, userId);
		if (ufi == null) {// 没有添加
			ufi = new UserFormId();
			ufi.setAppId(appId);
			ufi.setUserId(userId);
			ufi.setFormId(formId);
			ufi.setTimes(1);
			ufi.setLastTime(new Date());
			userFormIdService.addUserFormId(ufi);
		} else {
			if (formId != null && !formId.equals(ufi.getFormId())) {// 存在是否相等或者为空，不相等更新，为空和相等增加使用次数
				ufi.setFormId(formId);
			} else {
				ufi.setTimes(ufi.getTimes() + 1);
			}
			userFormIdService.updateUserFormId(ufi);
		}
		output(response, JsonUtil.objectToJson(ufi));
	}

	// 5.获得AI数据
	@RequestMapping("/getAiDashboard")
	public void getAiDashboard(HttpServletResponse response, Integer appId, Integer userId, Integer days)
			throws IOException {
		log.info(String.format("%s|%s|%s", appId,0,"getAiDashboard"));
		customerActionService.addCustomerAction(new CustomerAction(appId, userId, "BossAiController",
				"getAiDashboard", ""));
		
		AiDashboard aiDashboard = new AiDashboard();
		aiDashboard.setAppId(appId);
		aiDashboard.setUserId(userId);
		aiDashboard.setDays(days);
		aiDashboard.setCustomerCount(8);
		aiDashboardService.addAiDashboard(aiDashboard);
		aiDashboard.setCustomerCount(88);
		aiDashboardService.updateAiDashboard(aiDashboard);

		AiDashboard adb = aiDashboardService.getAiDashboard(appId, userId, days);
		output(response, JsonUtil.objectToJson(adb));
	}

	// 6.销售管理,获得销售人员list
	@RequestMapping("/getSalesPersonList")
	public void getSalesPersonList(HttpServletResponse response, Integer appId, Integer userId, Integer deparmentId,
			int page, int limit) throws IOException {
		if(userId==null)
		{
			userId=0;
		}
		customerActionService.addCustomerAction(new CustomerAction(appId, userId, "BossAiController",
				"getSalesPersonList", ""));
		
		List<SalesPerson> list = salesPersonService.getSalesPersonList(appId, page, limit);
		output(response, JsonUtil.buildCustomJson("0", "success", list));
	}

	// 6.1销售管理,跟新销售人员list
	@RequestMapping("/updateSalesPerson")
	public void updateSalesPerson(HttpServletResponse response, SalesPerson salesPerson) throws IOException {
		salesPersonService.updateSalesPerson(salesPerson);

		output(response, JsonUtil.objectToJson(salesPerson));
	}
}
