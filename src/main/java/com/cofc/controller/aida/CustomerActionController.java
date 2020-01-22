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
import com.cofc.service.aida.ActionRecordService;
import com.cofc.service.aida.SalesCustomerService;
import com.cofc.service.aida.SalesPersonService;
import com.cofc.service.aida.UserFormIdService;
import com.cofc.service.dataAnalysis.AiDashboardService;
import com.cofc.service.dataAnalysis.AiTrendService;
import com.cofc.util.BaseUtil;
import com.cofc.util.JsonUtil;

@Controller
@RequestMapping("/aida")
public class CustomerActionController extends BaseUtil {

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

	// 1.获得客户仪表盘整体信息
	@RequestMapping("/getCustomerDashboard")
	public void getCustomerDashboard(HttpServletResponse response, int appId, int salesId, Integer lastdays)
			throws IOException {
		log.info(String.format("%s|%s|%s", appId,salesId,"getBossDashboard"));
		
		AiDashboard ad = aiDashboardService.getAiDashboard(appId, salesId, lastdays);
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

		/**
		 * 趋势图
		 */
		AiTrend aiTrend1 = aiTrendService.getAiTrend(ad.getAppId(), ad.getUserId(), 7, 1);
		if (null != aiTrend1) {
			bossDashboard.setVisitedAmountListStr(aiTrend1.getList());
		}
		AiTrend aiTrend2 = aiTrendService.getAiTrend(ad.getAppId(), ad.getUserId(), 7, 2);
		if (null != aiTrend2) {
			bossDashboard.setNewCustomerListStr(aiTrend2.getList());
		}

		output(response, JsonUtil.objectToJson(bossDashboard));
	}

	//1.按照时间维度：天列表，子列表具体行为
	//2.按照公司维度：列表加条形图
	//3.按照个人维度：列表加条形图
	
	//4.具体每个行为时间情况
	//5.公司排名：访客、用户、使用人数、使用次数；
	//6.用户排名：使用次数
	//7.公司分析：饼图
	//8.趋势图：公司使用次数、人数；
	//9.使用偏好：整体、公司和个人；条形图
	//
	
}
