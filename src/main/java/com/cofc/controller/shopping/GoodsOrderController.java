package com.cofc.controller.shopping;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cofc.pojo.ApplicationCommon;
import com.cofc.pojo.BackUser;
import com.cofc.pojo.GoodsCommon;
import com.cofc.pojo.GoodsOrder;
import com.cofc.pojo.UserCommon;
import com.cofc.pojo.UserOrder;
import com.cofc.pojo.activity.Brokerage;
import com.cofc.service.ApplicationCommonService;
import com.cofc.service.ContractRecordService;
import com.cofc.service.GoodsOrderService;
import com.cofc.service.TaskPayOrderService;
import com.cofc.service.UserCommonService;
import com.cofc.service.UserOrderService;
import com.cofc.service.activity.BrokerageService;
import com.cofc.util.BaseUtil;
import com.cofc.util.JsonUtil;

/**
 * 后台订单
 * 
 * @author chen
 *
 */
@Controller
@RequestMapping("/goodsOrder")
public class GoodsOrderController extends BaseUtil {
	@Resource
	private GoodsOrderService orderService;
	@Resource
	private ContractRecordService recordService;
	@Resource
	private TaskPayOrderService taskPayOrderService;
	@Resource
	private ApplicationCommonService applicationService;
	@Resource
	private UserOrderService userOrderService;
	@Resource
	private BrokerageService brokerageService;
	@Resource
	private UserCommonService userCommonService;

	public static Logger log = Logger.getLogger("GoodsOrderController");

	@RequestMapping("/goGoodsOrderList")
	public ModelAndView toShowGoodsOrderList(HttpServletRequest request, ModelAndView modelView) {
		BackUser bu = (BackUser) request.getSession().getAttribute("loginer");
		List<ApplicationCommon> appList = new ArrayList<ApplicationCommon>();
		if (bu.getLoginPlat() == null || bu.getLoginPlat().equals("")) {
			appList = applicationService.getNewApplicationList(null);
		} else {
			String[] idStrings = bu.getLoginPlat().split(",");
			List<String> loginPlatList = Arrays.asList(idStrings);
			appList = applicationService.getApplicationByLoginPlat(loginPlatList);
		}
		log.info("进入交易记录列表的jsp页面");
		modelView.addObject("appList", appList);
		modelView.setViewName("capitalManage/tradeList");
		return modelView;
	}

	// 交易记录列表
	@RequestMapping("/orderList")
	public void showGoodsList(HttpServletRequest request, HttpServletResponse response, ModelAndView modelView,
			GoodsOrder order, String searchBeginDate, String searchEndDate, Integer pageNo, GoodsCommon goods,
			String buyerWords, String sellerWords, Integer pageSize) throws ParseException {
		if (pageNo == null || pageNo == 0) {
			pageNo = 1;
		}
		if (pageSize == null || pageSize == 0) {
			pageSize = 20;
		}
		request.getSession().setAttribute("currGoodsPage", pageNo);
		// goods.setLoginPlat(1);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat startSdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		SimpleDateFormat endSdf = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
		String startTime = null;
		String endTime = null;
		if (searchBeginDate != null && searchBeginDate != "") {
			startTime = startSdf.format(sdf.parse(searchBeginDate));
		}
		if (searchEndDate != null && searchEndDate != "") {
			endTime = endSdf.format(sdf.parse(searchEndDate));
		}
		int rowsCount = orderService.getCountOrders(order, goods, sellerWords, buyerWords, startTime, endTime);
		List<GoodsOrder> orderList = orderService.findAllOrder(order, goods, sellerWords, buyerWords, startTime,
				endTime, (pageNo - 1) * pageSize, pageSize);

		output(response, JsonUtil.buildBackJson(orderList, rowsCount));
	}

	// 交易统计
	// @RequestMapping("/tradeCount")
	// public ModelAndView countTradeData(ModelAndView modelView,String
	// dateRange) throws ParseException{
	// DecimalFormat df = new DecimalFormat("0.00");
	// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	// SimpleDateFormat startSdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
	// SimpleDateFormat endSdf = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
	// String startTime = null;
	// String endTime = null;
	// if (dateRange != null && !dateRange.equals("")) {
	// String[] myData = dateRange.split(" -| ");
	// startTime = startSdf.format(sdf.parse(myData[0]));
	// endTime = endSdf.format(sdf.parse(myData[2]));
	// }
	// String countMoney = orderService.getCountOfTradeMoney(startTime,endTime);
	// if(countMoney==null){
	// countMoney="0.00";
	// }
	//// String countGoods =
	// orderService.getCountOfTradeGoods(startTime,endTime);
	//// if(countGoods==null){
	//// countGoods="0";
	//// }
	// String contractMoney =
	// recordService.getCountOfTradeMoney(startTime,endTime);
	// if (contractMoney == null) {
	// contractMoney="0.0";
	// }
	// String taskMoney =
	// taskPayOrderService.getCountOfTradeMoney(startTime,endTime);
	// if (taskMoney == null) {
	// taskMoney="0.0";
	// }
	// modelView.setViewName("capitalManage/tradeCount");
	// modelView.addObject("moneyCount",
	// df.format(Double.parseDouble(countMoney)));
	// modelView.addObject("contractMoney",
	// df.format(Double.parseDouble(contractMoney)));
	// modelView.addObject("taskMoney",
	// df.format(Double.parseDouble(taskMoney)));
	//// modelView.addObject("goodsCount", countGoods);
	// modelView.addObject("dateRange", dateRange);
	// return modelView;
	// }
	/**
	 * 交易统计
	 * 
	 * @param modelView
	 * @param dateRange
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/tradeCount")
	public ModelAndView countTradeData(HttpServletRequest request, ModelAndView modelView, Integer loginPlat)
			throws ParseException {
		BackUser bu = (BackUser) request.getSession().getAttribute("loginer");
		List<ApplicationCommon> appList = new ArrayList<ApplicationCommon>();
		int payordercount = 0;
		int nopayordercount = 0;
		String smoney = "";
		String othermoney = "";
		String totalmoney = "";
		if (bu.getLoginPlat() == null || bu.getLoginPlat().equals("")) {
			appList = applicationService.getNewApplicationList(null);
			payordercount = userOrderService.getOrderOfPayCount(loginPlat, 1);
			nopayordercount = userOrderService.getOrderOfPayCount(loginPlat, 0);
			smoney = userOrderService.getOrderOfMoneyCount(loginPlat, 0);
			othermoney = userOrderService.getOrderOfMoneyCount(loginPlat, 1);
			totalmoney = userOrderService.getOrderOfMoneyCount(loginPlat, null);
		} else {
			if (loginPlat == null) {
				String[] idStrings = bu.getLoginPlat().split(",");
				List<String> loginPlatList = Arrays.asList(idStrings);
				appList = applicationService.getApplicationByLoginPlat(loginPlatList);
				payordercount = userOrderService.getOrderOfPayCountByLoginPlat(loginPlatList, 1);
				nopayordercount = userOrderService.getOrderOfPayCountByLoginPlat(loginPlatList, 0);
				smoney = userOrderService.getOrderOfMoneyCountByLoginPlat(loginPlatList, 0);
				othermoney = userOrderService.getOrderOfMoneyCountByLoginPlat(loginPlatList, 1);
				totalmoney = userOrderService.getOrderOfMoneyCountByLoginPlat(loginPlatList, null);
			} else {
				String[] idStrings = bu.getLoginPlat().split(",");
				List<String> loginPlatList = Arrays.asList(idStrings);
				appList = applicationService.getApplicationByLoginPlat(loginPlatList);
				payordercount = userOrderService.getOrderOfPayCount(loginPlat, 1);
				nopayordercount = userOrderService.getOrderOfPayCount(loginPlat, 0);
				smoney = userOrderService.getOrderOfMoneyCount(loginPlat, 0);
				othermoney = userOrderService.getOrderOfMoneyCount(loginPlat, 1);
				totalmoney = userOrderService.getOrderOfMoneyCount(loginPlat, null);
			}

		}
		log.info("进入交易记录列表的jsp页面");
		modelView.addObject("appList", appList);
		modelView.addObject("smoney", smoney);
		modelView.addObject("othermoney", othermoney);
		modelView.addObject("payordercount", payordercount);
		modelView.addObject("nopayordercount", nopayordercount);
		modelView.addObject("totalorder", (payordercount + nopayordercount));
		modelView.addObject("totalmoney", totalmoney);
		modelView.setViewName("capitalManage/tradeCount");
		return modelView;
	}

	
	/**
	 * 关闭订单
	 * chenxiangyou 
	 */
	@RequestMapping("/closeOrder")
	public void closeOrder(HttpServletResponse response, Integer appId, Integer userId, UserOrder userOrder) {
		
		userOrderService.updateUserOrder(userOrder);
		//需要把佣金划到用户余额账上
		Brokerage brokerage=brokerageService.getBrokerage(appId, userId, userOrder.getOrderID());
		if(brokerage!=null){
			if(brokerage.getStatus()==0){
				brokerage.setStatus(1);
				UserCommon user=userCommonService.getUserByUserId(userId);
				
				//佣金到个人余额
				user.setWalletBalance(user.getWalletBalance()+brokerage.getPrice());
				
				userCommonService.commonInfoUpdate(user);
				brokerageService.updateBrokerage(brokerage);
				
				//这里最好加上消息提示用户，同时给用户发送短信；
			}
		}

		List<UserOrder> list = new ArrayList<UserOrder>();
		list.add(userOrder);
		output(response, JsonUtil.buildCustomJson("0", "success", list));
	}
}
