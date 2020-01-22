package com.cofc.controller.weiaijia;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cofc.pojo.UserCommon;
import com.cofc.pojo.UserOrder;
import com.cofc.service.UserOrderService;
import com.cofc.service.UserShoppingCarService;
import com.cofc.util.BaseUtil;
import com.cofc.util.JsonUtil;
/**
 * 订单相关接口
 * @author 46678
 *
 */
@Controller
@RequestMapping("/vij/order")
public class VijOrderController extends BaseUtil{
	@Resource
	private UserOrderService orderService;
	@Resource
	private UserShoppingCarService carService;
	public static Logger log = Logger.getLogger("VijOrderController");
	@RequestMapping("/test")
	public ModelAndView test(HttpServletRequest request,ModelAndView mv){
		mv.setViewName("vij/order/test");
		return mv;
	}
	/**
	 * 根据订单id获取订单详情
	 * @param response
	 * @param orderId
	 */
	@RequestMapping("/getOrderById")
	public void getOrderById(HttpServletRequest request,HttpServletResponse response,Integer orderId){
		UserCommon user = (UserCommon) request.getSession().getAttribute("vijMallUserSession");
		if(user == null){
			log.info("用户未登录");
			output(response,JsonUtil.buildFalseJson("1","请先登录"));
		}else{
			List<UserOrder> lists = new ArrayList<UserOrder>();
			UserOrder list = orderService.findByID(orderId);
			if(list != null){
				lists.add(list);
			}
			output(response,JsonUtil.buildJson(lists));
		}		
	}
	/**
	 * 返回每种状态订单的数量
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getOrderCount")
	public void getOrderCount(HttpServletRequest request,HttpServletResponse response,Integer loginPlat){
		UserCommon user = (UserCommon) request.getSession().getAttribute("vijMallUserSession");
		if(user != null){
			int dfkCount = orderService.getUserLoginPlatCount(loginPlat, user.getUserId(),3);
			int dfhCount = orderService.getUserLoginPlatCount(loginPlat, user.getUserId(), 5);
			int dshCount = orderService.getUserLoginPlatCount(loginPlat, user.getUserId(), 10);
			int dpjCount = orderService.getUserLoginPlatCount(loginPlat, user.getUserId(), 14);
			int tkCount = orderService.getUserLoginPlatCount(loginPlat, user.getUserId(), -1);
			int allCount = dfkCount+dfhCount+dshCount+dpjCount+tkCount;
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("allCount", allCount);
			map.put("dfkCount", dfkCount);
            map.put("dfhCount", dfhCount);
            map.put("dshCount", dshCount);
            map.put("dpjCount", dpjCount);
            map.put("tkCount", tkCount);
            output(response,JsonUtil.MapToJson(map));
		}else{
			log.info("获取订单数量失败");
			output(response,JsonUtil.buildFalseJson("1","获取订单数量失败"));
		}
	}
	/**
	 * 删除购物车
	 * @param response
	 * @param carIds
	 */
	@RequestMapping("/delChooseCar")
	public void delChooseCar(HttpServletRequest request,HttpServletResponse response,String carIds){
		UserCommon user = (UserCommon) request.getSession().getAttribute("vijMallUserSession");
		if(user == null){
			log.info("用户未登录");
			output(response,JsonUtil.buildFalseJson("1","请先登录"));
		}else{
			if(carIds.equals("")){
				output(response,JsonUtil.buildFalseJson("1", "请选择要移除的商品"));
			}else{
				log.info("carIds--"+carIds);
				String[] arr = carIds.split(",");
				List<String> ids = Arrays.asList(arr);
				carService.deleteAllGoodsInCar(ids, new Date());
				output(response,JsonUtil.buildFalseJson("0", "移除成功"));
			}
		}	
	}
	
	/**
	 * 结算台
	 * @param request
	 * @param mv
	 * @return
	 */
	@RequestMapping("/counter")
	public ModelAndView counter(HttpServletRequest request,ModelAndView mv){
		UserCommon user = (UserCommon) request.getSession().getAttribute("vijMallUserSession");
		if(user == null){ //登陆
			mv.setViewName("vij/user_login");
		}else{
			mv.setViewName("vij/order/product_counter");
		}
		return mv;
	}
	/**
	 * 购物车
	 * @param request
	 * @param mv
	 * @return
	 */
	@RequestMapping("/cart")
	public ModelAndView cart(HttpServletRequest request,ModelAndView mv){
		UserCommon user = (UserCommon) request.getSession().getAttribute("vijMallUserSession");
		if(user == null){ //登陆
			mv.setViewName("vij/user_login");
		}else{
			mv.setViewName("vij/order/product_cart");
		}
		return mv;
	}
	/**
	 * 支付
	 * @param request
	 * @param mv
	 * @return
	 */
	@RequestMapping("/pay")
	public ModelAndView pay(HttpServletRequest request,ModelAndView mv){
		UserCommon user = (UserCommon) request.getSession().getAttribute("vijMallUserSession");
		if(user == null){ //登陆
			mv.setViewName("vij/user_login");
		}else{
			mv.setViewName("vij/order/product_pay");
		}
		return mv;
	}
}
