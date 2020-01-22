package com.cofc.controller.application;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cofc.pojo.ApplicationVoucherRecord;
import com.cofc.pojo.GroupUsers;
import com.cofc.pojo.UserCommon;
import com.cofc.service.ApplicationVoucherRecordService;
import com.cofc.service.GroupUsersService;
import com.cofc.service.UserCommonService;
import com.cofc.util.BaseUtil;
import com.cofc.util.JsonUtil;

@Controller
@RequestMapping("wx/apprecord")
public class WXApplicationRecordController extends BaseUtil {

	@Resource
	private ApplicationVoucherRecordService appRecordService;
	@Resource
	private GroupUsersService groupUsersService;
	@Resource
	private UserCommonService userCommonService;

	// 应用详情
	@RequestMapping("/appDetails")
	private void appDetails(HttpServletResponse response, Integer userId, Integer loginPlat) {
		GroupUsers record = groupUsersService.comfirmIsJoinThisGroup(userId, loginPlat);
		UserCommon user = userCommonService.getUserByUserId(userId);
		List<GroupUsers> list = new ArrayList<GroupUsers>();
		record.setNickName(user.getNickName());
		if (user.getPhone() != null) {
			record.setPhone(user.getPhone());
		}
		if (user.getRealName() != null) {
			record.setRealName(user.getRealName());
		}
		if(user.getUserLevel() != null){
			record.setUserLevel(user.getUserLevel());
		}
		list.add(record);
		output(response, JsonUtil.buildJson(list));
	}

	// 充消费剩余消费券列表
	@RequestMapping("/appRecordList")
	public void appRecordList(HttpServletResponse response, Integer pageNo, Integer pageSize,
			ApplicationVoucherRecord record) {
		if (pageNo == null) {
			pageNo = 1;
		}
		if (pageSize == null) {
			pageSize = 20;
		}
		List<ApplicationVoucherRecord> appList = appRecordService.findUserAppRecordList(record, (pageNo - 1) * pageSize,
				pageSize);
		output(response, JsonUtil.buildJson(appList));
	}

	// 修改消费剩余次数
	@RequestMapping("/updateGivevoucher")
	public void updateGivevoucher(HttpServletResponse response, Integer userId, Integer loginPlat, Integer giveVoucher,
			Integer type,String recordDesc) {
		ApplicationVoucherRecord record = new ApplicationVoucherRecord();
		GroupUsers user = groupUsersService.comfirmIsJoinThisGroup(userId, loginPlat);
		if (type == 1) {// 添加次数
			user.setGiveVoucher(user.getGiveVoucher() + giveVoucher);
			//int num = user.getGiveVoucher() + giveVoucher;
			if(recordDesc.trim().equals("")){
				recordDesc = "充值";
			}
			record.setRecordDesc(recordDesc + giveVoucher + "次【充值】");
		} else {// 减少次数
			//int num = 0;
			if (user.getGiveVoucher() != 0) {
				//num = user.getGiveVoucher() - giveVoucher;
				user.setGiveVoucher(user.getGiveVoucher() - giveVoucher);
			} else {
				user.setGiveVoucher(0);
			}
			if(recordDesc.trim().equals("")){
				recordDesc = "消费";
			}
			record.setRecordDesc(recordDesc + giveVoucher + "次【消费】");
		}
		groupUsersService.updateGiveVoucher(user);
		record.setCreateTime(new Date());
		record.setLoginPlat(loginPlat);
		record.setUserId(userId);
		appRecordService.addApplicationVoucher(record);
		output(response, JsonUtil.buildFalseJson("0", "修改成功!"));
	}
	
	//修改消费者信息
	@RequestMapping("/updateuserinfo")
	public void updateUserInfo(HttpServletResponse response,Integer userId,Integer loginPlat,
			String nickName,String phone,String address,Integer userLevel,Integer money,Integer giveVoucher){
		if(userId == null){
			output(response, JsonUtil.buildFalseJson("2", "操作失败：用户id为空!"));	
		}
		if(loginPlat == null){
			output(response, JsonUtil.buildFalseJson("3", "操作失败:平台id为空!"));		
		}
		GroupUsers guser = groupUsersService.comfirmIsJoinThisGroup(userId, loginPlat);
		guser.setAddress(address);
        guser.setMoney(money);
        guser.setGiveVoucher(giveVoucher);
        guser.setUserId(userId);
        guser.setGroupId(loginPlat);
        groupUsersService.updateInfoMes(guser);
		UserCommon user = userCommonService.getUserByUserId(userId);
		user.setUserId(userId);
		user.setUserLevel(userLevel);
		user.setNickName(nickName);
		user.setPhone(phone);
		userCommonService.commonInfoUpdate(user);	
		try {
			output(response, JsonUtil.buildFalseJson("0", "操作成功!"));		
		} catch (Exception e) {
			output(response, JsonUtil.buildFalseJson("1", "操作失败!"));		
		}
	}
}
