package com.cofc.controller.activity;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cofc.pojo.activity.Coupon;
import com.cofc.service.activity.CouponService;
import com.cofc.util.BaseUtil;
import com.cofc.util.JsonUtil;

//活动
@Controller
@RequestMapping("/wx/activity")
public class CouponController extends BaseUtil {
	@Resource
	private CouponService CouponService;
	public static Logger log = Logger.getLogger("CouponController");

	@RequestMapping("/getCouponList")
	public void getCouponList(HttpServletResponse response, Integer appId, Integer userId) {
		List<Coupon> list = CouponService.getCouponList(appId, userId);
		output(response, JsonUtil.buildCustomJson("0", "success", list));
	}

}
