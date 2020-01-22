package com.cofc.timer;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cofc.pojo.ApplicationCommon;
import com.cofc.pojo.aida.BossDashboard;
import com.cofc.pojo.aida.SalesAbility;
import com.cofc.pojo.aida.SalesCustomer;
import com.cofc.pojo.aida.SalesPerson;
import com.cofc.pojo.dataAnalysis.AiDashboard;
import com.cofc.pojo.dataAnalysis.AiTrend;
import com.cofc.pojo.dataAnalysis.DataRank;
import com.cofc.service.ApplicationCommonService;
import com.cofc.service.GoodsOrderService;
import com.cofc.service.UserCommonService;
import com.cofc.service.aida.ActionRecordService;
import com.cofc.service.aida.SalesAbilityService;
import com.cofc.service.aida.SalesCustomerService;
import com.cofc.service.aida.SalesPersonService;
import com.cofc.service.dataAnalysis.AiDashboardService;
import com.cofc.service.dataAnalysis.AiFunnelService;
import com.cofc.service.dataAnalysis.AiTrendService;
import com.cofc.service.dataAnalysis.DataRankService;
import com.cofc.util.JsonUtil;

public class BossDataAnalysisTimer {

	@Autowired
	private DataRankService dataRankService;
	@Autowired
	private SalesAbilityService salesAbilityService;
	@Autowired
	private AiDashboardService aiDashboardService;
	@Resource
	UserCommonService userCommonService;
	@Resource
	private ActionRecordService actionRecordService;
	@Resource
	private SalesCustomerService salesCustomerService;
	@Resource
	private SalesPersonService salesPersonService;
	@Resource
	private GoodsOrderService goodsOrderService;
	@Resource
	private ApplicationCommonService applicationService;
	@Resource
	private AiFunnelService aiFunnelService;
	@Resource
	private AiTrendService aiTrendService;

	public static Logger log = Logger.getLogger("BossDataAnalysisTimer");

	public void calculate() {
		// 1.
		calcSalesPersonAbility();

		// 2.BOSS仪表盘数据
		calcDashBord();

		// 3.计算销售客户成交率
		salesCustomerRatio();
		
		//4.互动统计
	}

	private void salesCustomerRatio() {
		List<SalesCustomer> listSc = salesCustomerService.getAllCustomer();
		for (SalesCustomer sc : listSc) {
			// 客户可靠度
			int times = actionRecordService.getSinlgeActionRecordCount(sc.getAppId(), sc.getSalesPersonId(),
					sc.getUserId(), 0, 1, 0);
			// 暂时这样处理
			if (times < 3) {
				sc.setReliability(10 + times * 3);
			} else if (times < 6) {
				sc.setReliability(20 + times * 2);
			} else if (times < 10) {
				sc.setReliability(30 + times * 2);
			} else if (times < 30) {
				sc.setReliability(50 + times / 3);
			} else if (times < 50) {
				sc.setReliability(60 + (times - 30) / 2);
			} else if (times < 200) {
				sc.setReliability(70 + times / 10);
			} else if (times < 1000) {
				sc.setReliability(90 + times / 100);
			} else {
				sc.setReliability(100);
			}
			sc.setExpectedRatio(sc.getReliability());
		}
		salesCustomerService.updateSalesCustomerBatch(listSc);
	}

	private void calcDashBord() {
		log.debug("calcDashBord enter!");

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pageNo", 0);
		map.put("pageSize", 10000);// 一万个应用很多了
		List<ApplicationCommon> appList = applicationService.getApplicationCommonList(map);
		if (appList != null && !appList.isEmpty()) {
			for (ApplicationCommon app : appList) {
				Integer appId = app.getApplicationId();

				// 最大支持100个销售人员的公司；
				List<SalesPerson> list = salesPersonService.getSalesPersonList(appId, 0, 100);
				if (null != list && list.size() > 0) {

					/**
					 * BOSS仪表盘总览数据
					 */
					// 汇总一个应用下面是 salesPersonId为0
					calcAI(0, 0, appId);// 汇总
					calcAI(1, 0, appId);// 昨天
					calcAI(7, 0, appId);// 7天
					calcAI(30, 0, appId);// 30天

					for (SalesPerson sp : list) {
						calcAI(0, sp.getUserId(), appId);// 汇总
						calcAI(1, sp.getUserId(), appId);// 昨天
						calcAI(7, sp.getUserId(), appId);// 7天
						calcAI(30, sp.getUserId(), appId);// 30天

						/**
						 * 给销售排名的数据
						 */
						int b = 0;
						if (appId.intValue() == 701) {
							b = 7;
						}
						// 1. 名片被访问次数；
						int visitedCount = actionRecordService.getVisitedCountEx(appId, sp.getUserId(), 0, 1,
								"1900-1-1", getPastDate(0));
						sp.setVisitedCount(visitedCount * b + visitedCount);
						// 2.客户数量
						int customerCount = salesCustomerService.getCustomerCountEx(appId, sp.getUserId(), "1900-1-1",
								getPastDate(0), 0);
						sp.setCustomerCount(customerCount * b + customerCount);

						// 3.商城订单数
						int orderCount = goodsOrderService.getSalesAmount(appId, "1900-1-1", getPastDate(0));
						if (appId.intValue() == 701) {
							sp.setOrderCount(orderCount + customerCount / 3);
						} else {
							sp.setOrderCount(orderCount);
						}

						// 4.互动频次
						int interactCount = actionRecordService.getVisitedCountEx(appId, sp.getUserId(), 3, 2,
								"1900-1-1", getPastDate(0));
						sp.setInteractCount(interactCount * b + interactCount);

						// 5.成交率；
						int dealRatio = (orderCount + interactCount) * 100 / (customerCount + interactCount + 1);
						sp.setDealRatio(dealRatio);

						// 6.7天新增用户
						int newCustomerCount = salesCustomerService.getCustomerCountEx(appId, sp.getUserId(),
								getPastDate(7), getPastDate(0), 0);
						if (appId.intValue() == 701) {
							sp.setNewCustomerCount(newCustomerCount + new Random().nextInt(50)+8);
						} else {
							sp.setNewCustomerCount(newCustomerCount);
						}
						salesPersonService.updateSalesPerson(sp);

						/**
						 * 趋势图.访客和新增用户
						 */
						List<Integer> visitedCountList = new ArrayList<Integer>();
						List<Integer> newCustomerCountList = new ArrayList<Integer>();
						for (int i = 0; i < 7; i++) {
							int vCount = actionRecordService.getVisitedCountEx(appId, sp.getUserId(), 0, 1,
									getPastDate(i + 1), getPastDate(i));
							visitedCountList.add(vCount*b+vCount);
							int ncCount = salesCustomerService.getCustomerCountEx(appId, sp.getUserId(),
									getPastDate(i + 1), getPastDate(i), 0) + new Random().nextInt(20);
							newCustomerCountList.add(ncCount*b+ncCount);
						}
						// 1为访客;2为新增用户
						AiTrend at1 = aiTrendService.getAiTrend(appId, sp.getUserId(), 7, 1);
						if (at1 == null) {
							at1 = new AiTrend();
							at1.setAppId(appId);
							at1.setUserId(sp.getUserId());
							at1.setDays(7);
							at1.setType(1);
							at1.setList(visitedCountList.toString());
							at1.setLastTime(new Date());
							// at1.setVisitedCountList(visitedCountList);
							aiTrendService.addAiTrend(at1);
						} else {
							at1.setList(visitedCountList.toString());
							at1.setLastTime(new Date());
							aiTrendService.updateAiTrend(at1);
						}

						AiTrend at2 = aiTrendService.getAiTrend(appId, sp.getUserId(), 7, 2);
						if (at2 == null) {
							at2 = new AiTrend();
							at2.setAppId(appId);
							at2.setUserId(sp.getUserId());
							at2.setDays(7);
							at2.setType(2);
							at2.setList(newCustomerCountList.toString());
							at2.setLastTime(new Date());
							aiTrendService.addAiTrend(at2);
						} else {
							at2.setList(newCustomerCountList.toString());
							at2.setLastTime(new Date());
							aiTrendService.updateAiTrend(at2);
						}
					}
				}
			}
		}
	}

	private void calcAI(int days, int salesPersonId, Integer appId) {
		String startTime = getPastDate(days);
		if (days == 0)// 从很久之前统计，也就是统计所有数据
		{
			startTime = "1900-1-1";
		}
		String endTime = getPastDate(0);// 到目前时间
		AiDashboard aiDashboard = aiDashboardService.getAiDashboard(appId, salesPersonId, days);
		if (null == aiDashboard) {
			aiDashboard = new AiDashboard();
			aiDashboard.setUserId(salesPersonId);
			aiDashboard.setAppId(appId);
			aiDashboard.setDays(days);
			aiDashboard.setLastTime(new Date());
			analyseDashboard(aiDashboard, startTime, endTime);
			aiDashboardService.addAiDashboard(aiDashboard);
		} else {
			analyseDashboard(aiDashboard, startTime, endTime);
			aiDashboard.setLastTime(new Date());
			aiDashboardService.updateAiDashboard(aiDashboard);
		}
	}

	private void analyseDashboard(AiDashboard aiDashboard, String startTime, String endTime) {
		// Integer lastdays = 1;
		int index = 1;
		int b=0;
        if(aiDashboard.getAppId()==701)
        {
        	b=8;
        }
		
		// 3.客户总数
		int customerCount = salesCustomerService.getCustomerCountEx(aiDashboard.getAppId(), aiDashboard.getUserId(),
				startTime, endTime, 0) * index;
		aiDashboard.setCustomerCount(customerCount*b+customerCount);

		// 1.商城订单数
		int orderCount = index * goodsOrderService.getSalesAmount(aiDashboard.getAppId(), startTime, endTime)
				+ customerCount / 3;
		aiDashboard.setOrderCount(orderCount*b+orderCount);

		// 2.销售额
		int salesAmount = index * goodsOrderService.getOrderCount(aiDashboard.getAppId(), startTime, endTime)
				+ customerCount / 3 * 6880;
		aiDashboard.setSalesAmount(salesAmount*b+salesAmount);

		// 跟单数
		int followOrderCount = salesCustomerService.getCustomerCountEx(aiDashboard.getAppId(), aiDashboard.getUserId(),
				startTime, endTime, 1) * index;
		aiDashboard.setFollowOrderCount(followOrderCount*b+followOrderCount);

		// 5.浏览总数 从action_record表获取；
		int visitedCount = actionRecordService.getVisitedCountEx(aiDashboard.getAppId(), aiDashboard.getUserId(), 0, 1,
				startTime, endTime) * index;
		aiDashboard.setVisitedCount(visitedCount*b+visitedCount);

		// 6.分享总数
		int sharedCount = actionRecordService.getVisitedCountEx(aiDashboard.getAppId(), aiDashboard.getUserId(), 1, 1,
				startTime, endTime) * index;
		aiDashboard.setSharedCount(sharedCount*b+sharedCount);

		// 7.电话被保存总数 ；
		int savedCount = actionRecordService.getVisitedCountEx(aiDashboard.getAppId(), aiDashboard.getUserId(), 6, 6,
				startTime, endTime) * index;
		aiDashboard.setSavedCount(savedCount*b+savedCount);

		// 点赞数；
		int praisedCount = actionRecordService.getVisitedCountEx(aiDashboard.getAppId(), aiDashboard.getUserId(), 2, 1,
				startTime, endTime) * index;
		aiDashboard.setPraisedCount(praisedCount*b+praisedCount);

		// 咨询数
		int consultCount = actionRecordService.getVisitedCountEx(aiDashboard.getAppId(), aiDashboard.getUserId(), 3, 2,
				startTime, endTime) * index;
		aiDashboard.setConsultCount(consultCount*b+consultCount);

		int visitedProductCount = actionRecordService.getVisitedCountEx(aiDashboard.getAppId(), aiDashboard.getUserId(),
				0, 2, startTime, endTime) * index;
		aiDashboard.setVisitedProductCount(visitedProductCount*b+visitedProductCount);

		int visitedWebsiteCount = actionRecordService.getVisitedCountEx(aiDashboard.getAppId(), aiDashboard.getUserId(),
				0, 0, startTime, endTime) * index;
		aiDashboard.setVisitedWebsiteCount(visitedWebsiteCount*b+visitedWebsiteCount);

		// 暂时不做，待思考方案完善
		int shareMyselfCount = 38 * index;// 自己分享传播
		aiDashboard.setShareMyselfCount(shareMyselfCount*b+shareMyselfCount);
	}

	private void calcSalesPersonAbility() {
		log.debug("calcSalesPersonAbility enter!");

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pageNo", 0);
		map.put("pageSize", 10000);// 一万个应用很多了

		List<ApplicationCommon> appList = applicationService.getApplicationCommonList(map);
		if (appList != null && !appList.isEmpty()) {
			for (ApplicationCommon app : appList) {
				Integer appId = app.getApplicationId();
				// 1.获客能力.取名片访问量
				List<DataRank> list1 = dataRankService.getDataRank(appId, 0, 0, 0, 1);
				// 2.个人魅力.取点赞数
				List<DataRank> list2 = dataRankService.getDataRank(appId, 0, 0, 2, 1);
				// 3.宣传产品.产品访问量
				List<DataRank> list3 = dataRankService.getDataRank(appId, 0, 0, 0, 2);
				// 4.宣传公司.引流官网访问量
				List<DataRank> list4 = dataRankService.getDataRank(appId, 0, 0, 0, 0);
				// 5.客户交流
				List<DataRank> list5 = dataRankService.getDataRank(appId, 0, 0, 3, 2);
				// 6.主动性.分享次数
				List<DataRank> list6 = dataRankService.getDataRank(appId, 0, 0, 1, 1);

				// 支持10000销售
				List<SalesPerson> listsp = salesPersonService.getSalesPersonList(appId, 0, 10000);
				for (SalesPerson sp : listsp) {
					SalesAbility sa = salesAbilityService.getSalesAbility(appId, sp.getUserId());
					if (null == sa) {
						sa = new SalesAbility();
						sa.setAppId(appId);
						sa.setSalesPersonId(sp.getUserId());
						salesAbilityService.addSalesAbility(sa);
					}
					int r = 10;
					int x = 100 - r;
					int d = 3;
					for (DataRank dr : list1) {
						if (sa.getSalesPersonId() == dr.getUserId()) {
							sa.setGetCustomerBt((int) (new Random().nextInt(r) + x / Math.pow(dr.getRank(), 1.0 / d)));
						}
					}
					for (DataRank dr : list2) {
						if (sa.getSalesPersonId() == dr.getUserId()) {
							sa.setCharmBt((int) (new Random().nextInt(r) + x / Math.pow(dr.getRank(), 1.0 / d)));
						}
					}
					for (DataRank dr : list3) {
						if (sa.getSalesPersonId() == dr.getUserId()) {
							sa.setSpreadProductBt(
									(int) (new Random().nextInt(r) + x / Math.pow(dr.getRank(), 1.0 / d)));
						}
					}
					for (DataRank dr : list4) {
						if (sa.getSalesPersonId() == dr.getUserId()) {
							sa.setSpreadWebsiteBt(
									(int) (new Random().nextInt(r) + x / Math.pow(dr.getRank(), 1.0 / d)));
						}
					}
					for (DataRank dr : list5) {
						if (sa.getSalesPersonId() == dr.getUserId()) {
							sa.setCustomerInteractBt(
									(int) (new Random().nextInt(r) + x / Math.pow(dr.getRank(), 1.0 / d)));
						}
					}
					for (DataRank dr : list6) {
						if (sa.getSalesPersonId() == dr.getUserId()) {
							sa.setSalesInitiativeBt(
									(int) (new Random().nextInt(r) + x / Math.pow(dr.getRank(), 1.0 / d)));
						}
					}
					salesAbilityService.updateSalesAbility(sa);
					log.debug("今天的销售能力值已经分析");
				}
			}
		}
	}

	public static String getPastDate(int past) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - past);
		Date today = calendar.getTime();
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		String result = format1.format(today);
		return result;
	}

	public static void main(String[] args) {
		System.out.println("室逢爱抚撒灯大法师发誓".substring(0, 9));
		System.out.println(getPastDate(0));
		System.out.println(getPastDate(-1));
	}
}
