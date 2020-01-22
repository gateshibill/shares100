package com.cofc.service.activity;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.cofc.pojo.activity.Coupon;



public interface CouponService {
	public List< Coupon> getCouponList(@Param("appId") Integer appId,@Param("userId") Integer userId);
}