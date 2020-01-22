package com.cofc.controller.mall;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cofc.pojo.ApplicationCommon;
import com.cofc.pojo.BackUser;
import com.cofc.pojo.GoodsCommon;
import com.cofc.pojo.GoodsType;
import com.cofc.pojo.UserOrder;
import com.cofc.pojo.UserRole;
import com.cofc.service.ApplicationCommonService;
import com.cofc.service.ContractRecordService;
import com.cofc.service.GoodsCommonService;
import com.cofc.service.GoodsTypeService;
import com.cofc.service.TaskPayOrderService;
import com.cofc.service.UserBackuserRelationService;
import com.cofc.service.UserOrderService;
import com.cofc.service.UserRoleService;
import com.cofc.util.BaseUtil;
import com.cofc.util.JsonUtil;

/**
 * 商城统计
 * @author admin
 *
 */

@Controller
@RequestMapping("/mallcount")
public class GoodsMallController extends BaseUtil{
	
	@Resource
	private GoodsCommonService goodsService;
	@Resource
	private GoodsTypeService gtypeService;
	@Resource
	private ContractRecordService recordService;
	@Resource
	private TaskPayOrderService taskPayOrderService;
	@Resource
	private ApplicationCommonService applicationService;
	@Resource
	private UserBackuserRelationService uburelaService;
	@Resource
	private UserRoleService userroleService;
	@Resource
	private UserOrderService orderService;

	
	@RequestMapping("/goodsCount")
	public ModelAndView goodsCount(ModelAndView modelAndView,HttpServletRequest request){
		BackUser bu = (BackUser) request.getSession().getAttribute("loginer");
		UserRole userRole = userroleService.getUserRoleById(bu.getUserId());
		String[] rolesarr = userRole.getRoleId().split(",");
//		boolean isSuperm = false;
//		for(String role:rolesarr){
//			while("1".equals(role)){
//				isSuperm = true;
//				break;
//			}
//		}
		List<ApplicationCommon> appList = new ArrayList<ApplicationCommon>();
		List<GoodsType> typeList = new ArrayList<GoodsType>();
//		if (isSuperm) {
//			typeList = gtypeService.findAllGoodsType(null, null, null, null, null,null);
//			appList = applicationService.findApplicationsByCriteria(null, null, null, null, null, null, null, null,null,null, null);
//		} else {
//			typeList = gtypeService.findAllGoodsType(null, null, null, null, bu.getUserId(),null);
//			List<UserBackuserRelation> userbackList = uburelaService.getUserBackuserList(bu.getUserId());
//			if (!userbackList.isEmpty()) {
//				for (UserBackuserRelation user:userbackList) {
//					appList = applicationService.findApplicationsByCriteria(null, null, null, user.getUserId(), null, null, null, null,null, null, null);
//				}
//			}
//		}
		if(bu.getLoginPlat() == null || bu.getLoginPlat().equals("")){
			appList = applicationService.getNewApplicationList(null);
			typeList = gtypeService.getNewTypeList(null);
         }else{
			String[] idStrings = bu.getLoginPlat().split(",");
			List<String> loginPlatList = Arrays.asList(idStrings);
			appList = applicationService.getApplicationByLoginPlat(loginPlatList);
			typeList = gtypeService.getNewTypeList(loginPlatList);
		}
		modelAndView.addObject("appList", appList);
		modelAndView.addObject("typeList", typeList);
		modelAndView.setViewName("/mallManage/goodsCount");
		return modelAndView;
	}
	@RequestMapping("/orderCount")
	public ModelAndView orderCount(ModelAndView modelAndView,HttpServletRequest request) throws ParseException{
		BackUser bu = (BackUser) request.getSession().getAttribute("loginer");
		List<ApplicationCommon> appList = new ArrayList<ApplicationCommon>();
		List<GoodsType> typeList = new ArrayList<GoodsType>();
		if(bu.getLoginPlat() == null || bu.getLoginPlat().equals("")){
			appList = applicationService.getNewApplicationList(null);
			typeList = gtypeService.getNewTypeList(null);
         }else{
			String[] idStrings = bu.getLoginPlat().split(",");
			List<String> loginPlatList = Arrays.asList(idStrings);
			appList = applicationService.getApplicationByLoginPlat(loginPlatList);
			typeList = gtypeService.getNewTypeList(loginPlatList);
		}
		modelAndView.addObject("appList", appList);
		modelAndView.addObject("typeList", typeList);
		modelAndView.setViewName("/mallManage/orderCount");
		return modelAndView;
	}
	@RequestMapping("/visitorCount")
	public ModelAndView visitorCount(ModelAndView modelAndView,HttpServletRequest request){
		BackUser bu = (BackUser) request.getSession().getAttribute("loginer");
		List<ApplicationCommon> appList = new ArrayList<ApplicationCommon>();
		List<GoodsType> typeList = new ArrayList<GoodsType>();
		if(bu.getLoginPlat() == null || bu.getLoginPlat().equals("")){
			appList = applicationService.getNewApplicationList(null);
			typeList = gtypeService.getNewTypeList(null);
         }else{
			String[] idStrings = bu.getLoginPlat().split(",");
			List<String> loginPlatList = Arrays.asList(idStrings);
			appList = applicationService.getApplicationByLoginPlat(loginPlatList);
			typeList = gtypeService.getNewTypeList(loginPlatList);
		}
		modelAndView.addObject("appList", appList);
		modelAndView.addObject("typeList", typeList);
		modelAndView.setViewName("/mallManage/visitorCount");
		return modelAndView;
	}
	@RequestMapping("/visitorSource")
	public ModelAndView visitorSource(ModelAndView modelAndView){
		modelAndView.setViewName("/mallManage/visitorSource");
		return modelAndView;
	}
	
	
	@RequestMapping("/showGoodsList")
	public void showGoodsList(HttpServletRequest request,HttpServletResponse response,GoodsCommon goods,Integer page,Integer limit,String dateRange) throws ParseException{
		BackUser bu = (BackUser) request.getSession().getAttribute("loginer");
		if (page == null) {
			page = 1;
		}
		if (limit == null) {
			limit = 20;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat startSdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		SimpleDateFormat endSdf = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
		String startTime = null;
		String endTime = null;
		if (dateRange != null && !dateRange.equals("")) {
			String[] myData = dateRange.split(" -| ");
			startTime = startSdf.format(sdf.parse(myData[0]));
			endTime = endSdf.format(sdf.parse(myData[2]));
		}
		int count = 0;
		List<GoodsCommon> goodsList = new ArrayList<GoodsCommon>();
		String selled_count = "0";
		if(bu.getLoginPlat() == null || bu.getLoginPlat().equals("")){
			count = goodsService.getCountGoods(null,goods, startTime, endTime,null, null);
			goodsList = goodsService.findGoodsList2(null,goods, startTime, endTime, (page - 1) * limit,
					limit,null,null);
			selled_count = goodsService.getLoginPlatSelledCount(goods, startTime,endTime);
			
		}else{
			if(goods.getLoginPlat() == null){ //应用
				String[] idStrings = bu.getLoginPlat().split(",");
				List<String> loginPlatList = Arrays.asList(idStrings);
				count = goodsService.getCountGoodsByLoginPlat(loginPlatList, goods, startTime, endTime,null,null);
				goodsList = goodsService.getGoodsByLoginPlat(loginPlatList, goods, startTime, endTime,(page-1) * page, limit, null , null);
				selled_count = goodsService.getSelledCountByLoginPlat(loginPlatList, goods, startTime, endTime);
			}else{
				count = goodsService.getCountGoods(null,goods, startTime, endTime,null, null);
				goodsList = goodsService.findGoodsList2(null,goods, startTime, endTime, (page - 1) * limit,
						limit,null,null);
				selled_count = goodsService.getLoginPlatSelledCount(goods, startTime,endTime);
			}
		}
		
		output(response, JsonUtil.buildBackJsonCountPlat(goodsList,count,selled_count));
	}
	
	@RequestMapping("/showOrderList")
	public void showOrderList(HttpServletRequest request,HttpServletResponse response,UserOrder order,Integer page,Integer limit,String dateRange) throws ParseException{
		BackUser bu = (BackUser) request.getSession().getAttribute("loginer");
		if (page == null) {
			page = 1;
		}
		if (limit == null) {
			limit = 20;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat startSdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		SimpleDateFormat endSdf = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
		String startTime = null;
		String endTime = null;
		if (dateRange != null && !dateRange.equals("")) {
			String[] myData = dateRange.split(" -| ");
			startTime = startSdf.format(sdf.parse(myData[0]));
			endTime = endSdf.format(sdf.parse(myData[2]));
		}
		
		List<UserOrder> orderList = new ArrayList<UserOrder>();
		int count = 0;
		String real_fee = "0.0";
		String rebateMoneyCount = "0.0";
		if(bu.getLoginPlat() == null || bu.getLoginPlat().equals("")){ //百享园
			orderList = orderService.getUserOrderList(order, (page - 1) * limit, limit,startTime,endTime);
		    count = orderService.getUserOrderCount(order,startTime,endTime);
			real_fee=  orderService.getOrderRealFeeCount(order, startTime, endTime);
		}else{
			if(order.getLoginPlat() == null){ //应用
				String[] idStrings = bu.getLoginPlat().split(",");
				List<String> loginPlatList = Arrays.asList(idStrings);
				count = orderService.getUserOrderCountByLoginPlat(loginPlatList, order, startTime, endTime);
				orderList = orderService.getUserOrderListByLoginPlat(loginPlatList, order,(page-1) * limit, limit, startTime, endTime);
			    real_fee = orderService.getOrderRealFeeCountByLoginPlat(loginPlatList, order, startTime, endTime);
			    rebateMoneyCount = orderService.getOrderRebateMoneyCountByLoginPlat(loginPlatList, order, startTime, endTime);
			}else{
				orderList = orderService.getUserOrderList(order, (page - 1) * limit, limit,startTime,endTime);
			    count = orderService.getUserOrderCount(order,startTime,endTime);
				real_fee=  orderService.getOrderRealFeeCount(order, startTime, endTime);
				rebateMoneyCount = orderService.getOrderRebateMoneyCount(order, startTime, endTime);
			}
		}
		
		
		output(response, JsonUtil.buildBackJsonCountPlat5(orderList, count, real_fee,rebateMoneyCount));
	}
	
	//应用访客、商品浏览量列表
	@RequestMapping("/loginPlatGoodsList")
	public void loginPlatGoodsList (HttpServletResponse response,GoodsCommon goods,Integer page,Integer limit,String dateRange) throws ParseException{
		if (page == null) {
			page = 1;
		}
		if (limit == null) {
			limit = 20;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat startSdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		SimpleDateFormat endSdf = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
		String startTime = null;
		String endTime = null;
		if (dateRange != null && !dateRange.equals("")) {
			String[] myData = dateRange.split(" -| ");
			startTime = startSdf.format(sdf.parse(myData[0]));
			endTime = endSdf.format(sdf.parse(myData[2]));
		}
		int count = goodsService.getCountGoods(null,goods, startTime, endTime,null, null);
		List<GoodsCommon> goodsList = goodsService.findGoodsList2(null,goods, startTime, endTime, (page - 1) * limit,
				limit,null, null);
		Integer browse = goodsService.getgoodsBrowseCount(goods,startTime,endTime);
		Integer visitor = applicationService.getAppVisitorCount(goods.getLoginPlat());
		output(response, JsonUtil.buildBackJsonBrowseVisitor(goodsList, count, browse,visitor));
	}
}
