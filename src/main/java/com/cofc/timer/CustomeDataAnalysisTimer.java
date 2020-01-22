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

public class CustomeDataAnalysisTimer {

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
		// 1.读原始数据，结构化数据

		// 2.分析数据；

		// 3.发送通知消息
	}

	public static void main(String[] args) {
	}
}
