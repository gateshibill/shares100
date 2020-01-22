package com.cofc.controller.shopping;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cofc.pojo.UserShoppingAddress;
import com.cofc.service.UserShoppingAddressService;
import com.cofc.util.BaseUtil;
import com.cofc.util.JsonUtil;

/**
 * 购物的收货地址接口
 * @author chen
 *
 */
@Controller
@RequestMapping("/wx/shoppingAddress")
public class WXShoppingAddressController extends BaseUtil {
	@Resource
	private UserShoppingAddressService usaService;
	public static Logger log = Logger.getLogger("WXShoppingAddressController");

	@RequestMapping("/addAddress")
	public void addShoppingAddress(HttpServletResponse response, UserShoppingAddress ushpAddress) {
		ushpAddress.setCreateTime(new Date());
		try {
			if(ushpAddress.getIsDefault() != null){
				if(ushpAddress.getIsDefault() == 1){
					//把其他地址修改为不是默认的
					usaService.updateIsDefault(ushpAddress.getUserId(), 0);
				}
			}
			usaService.addNewShoppingAddress(ushpAddress);
			output(response, JsonUtil.buildFalseJson("0", "添加成功!"));
		} catch (Exception e) {
			output(response, JsonUtil.buildFalseJson("1", "添加失败!"));
			log.info(ushpAddress.getUserId() + "添加地址" + ushpAddress.getDeliveryName() + "失败,失败原因" + e);
		}
	}

	// 查找用户收货地址
	@RequestMapping("/findShoppingAddr")
	public void findShoppingAddress(HttpServletResponse response, Integer userId) {
		List<UserShoppingAddress> usaList = usaService.findShoppingAddress(userId);
		output(response, JsonUtil.buildJson(usaList));
	}

	// 修改用户收货地址
	@RequestMapping("/upShoppingAddr")
	public void updateShoppingAddress(HttpServletResponse response, UserShoppingAddress usa) {
		try {
			if(usa.getIsDefault() != null){
				if(usa.getIsDefault() == 1){
					//把其他地址修改为不是默认的
					usaService.updateIsDefault(usa.getUserId(), 0);
				}
			}
			usaService.updateShoppingAddress(usa);
			output(response, JsonUtil.buildFalseJson("0", "修改成功!"));
		} catch (Exception e) {
			output(response, JsonUtil.buildFalseJson("1", "修改失败!"));
			log.info("修改地址编号为" + usa.getAddressId() + "的信息失败!失败原因" + e);
		}
	}

	@RequestMapping("/findMyAddress")
	public void findMyShoppingAddress(HttpServletResponse response, UserShoppingAddress ushpAddress) {
		List<UserShoppingAddress> usaList = usaService.findShoppingAddress(ushpAddress.getUserId());
		output(response, JsonUtil.buildJson(usaList));
	}

	@RequestMapping("/deleteAddress")
	public void deleteShoppingAddress(HttpServletResponse response, UserShoppingAddress ushpAddress) {
		try {
			usaService.deleteAddress(ushpAddress);
			output(response, JsonUtil.buildFalseJson("0", "删除成功!"));
		} catch (Exception e) {
			output(response, JsonUtil.buildFalseJson("1", "删除失败!"));
			log.info("删除地址" + ushpAddress.getAddressId() + "失败,失败原因" + e);
		}
	}
	/**
	 * 获取用户的一条地址
	 * @param response
	 * @param userId
	 */
	@RequestMapping("/selectOneAddressList")
	public void selectOneAddressList(HttpServletResponse response,Integer userId){
		if(userId != null){
			List<UserShoppingAddress> lists = new ArrayList<UserShoppingAddress>();
			lists = usaService.selectOneAddressList(1, userId);
			if(lists.size() > 0){
				output(response,JsonUtil.buildJson(lists));
			}else{
				lists = usaService.selectOneAddressList(null, userId);
				if(lists.size() > 0){
					output(response,JsonUtil.buildJson(lists));
				}else{
					output(response,JsonUtil.buildFalseJson("1", "你没有填写收货地址"));
				}
			}
		}else{
			output(response,JsonUtil.buildFalseJson("1", "用户不存在"));
		}
	}
}
