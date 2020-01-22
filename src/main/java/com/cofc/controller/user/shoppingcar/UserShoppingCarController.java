package com.cofc.controller.user.shoppingcar;


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
import com.cofc.pojo.UserBackuserRelation;
import com.cofc.pojo.UserRole;
import com.cofc.pojo.UserShoppingCar;
import com.cofc.service.ApplicationCommonService;
import com.cofc.service.UserBackuserRelationService;
import com.cofc.service.UserRoleService;
import com.cofc.service.UserShoppingCarService;
import com.cofc.util.BaseUtil;
import com.cofc.util.JsonUtil;

@Controller
@RequestMapping("/shoppingCar")
public class UserShoppingCarController extends BaseUtil{
	
	@Resource
	private UserShoppingCarService userShoppingCarService;
	@Resource
	private UserRoleService userroleService;
	@Resource
	private ApplicationCommonService applicationService;
	@Resource
	private UserBackuserRelationService uburelaService;
	
	
	
	@RequestMapping("/shoppingCarList")
	public ModelAndView shoppingCarList(ModelAndView modelAndView,HttpServletRequest request){
		BackUser bu = (BackUser) request.getSession().getAttribute("loginer");
		UserRole userRole = userroleService.getUserRoleById(bu.getUserId());
		String[] rolesarr = userRole.getRoleId().split(",");
		boolean isSuperm = false;
		for(String role:rolesarr){
			while("1".equals(role)){
				isSuperm = true;
				break;
			}
		}
		List<ApplicationCommon> appList = new ArrayList<ApplicationCommon>();
		if (isSuperm) {
			appList = applicationService.findApplicationsByCriteria(null, null, null, null, null, null, null, null,null, null, null);
		} else {
			List<UserBackuserRelation> userbackList = uburelaService.getUserBackuserList(bu.getUserId());
			if (!userbackList.isEmpty()) {
				for (UserBackuserRelation user:userbackList) {
					appList = applicationService.findApplicationsByCriteria(null, null, null, user.getUserId(), null, null, null, null,null, null, null);
				}
			}
		}
		modelAndView.addObject("appList", appList);
		modelAndView.setViewName("/shoppingcarManage/shoppingCarList");
		return modelAndView;
	}

	
	@RequestMapping("/showCarList")
	public void showCarList(HttpServletResponse response,String dateRange ,Integer page, Integer limit, String  userName) throws Exception{
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
			startTime = myData[0];
			endTime = myData[2];
		}
		if (startTime != null) {
			startTime = startSdf.format(sdf.parse(startTime));
		}
		if (endTime != null) {
			endTime = endSdf.format(sdf.parse(endTime));
		}
		List<UserShoppingCar> carList = userShoppingCarService.getUserShoppingCarList(userName,startTime,endTime,(page-1)*limit,limit);
		int count = userShoppingCarService.getUserShoppingCarCount(userName,startTime,endTime);
		output(response, JsonUtil.buildJsonByTotalCount(carList, count));
	}
	
	@RequestMapping("/deleteOrder")
	public void deleteOrder(HttpServletResponse response,Integer carId){
		try {
			userShoppingCarService.deleteUserShoppingCar(carId);
			output(response, JsonUtil.buildFalseJson("0", "删除成功!"));
		} catch (Exception e) {
			output(response, JsonUtil.buildFalseJson("1", "删除失败!"));
		}
	}
	
	//批量删除购物车
	@RequestMapping("/batchDelGoods")
	public void batchDelGoods(HttpServletResponse response,String carIds){
		List<UserShoppingCar> carList = userShoppingCarService.getcarByIds(Arrays.asList(carIds.split(",")));
		try {
			for (UserShoppingCar car:carList) {
				userShoppingCarService.deleteUserShoppingCar(car.getCarId());
			}
			output(response, JsonUtil.buildFalseJson("0", "批量删除成功!"));
		} catch (Exception e) {
			output(response, JsonUtil.buildFalseJson("1", "批量删除失败!"));
		}
	}
}
