package com.cofc.controller.user;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cofc.pojo.UserCommon;
import com.cofc.pojo.UserConcern;
import com.cofc.service.UserCommonService;
import com.cofc.service.UserConcernService;
import com.cofc.service.UserShareGoodsService;
import com.cofc.util.BaseUtil;
import com.cofc.util.JsonUtil;

/**
 * 前端用户关注相关接口
 * 
 * @author chen
 *
 */
@Controller
@RequestMapping("/wx/userConcern")
public class WXUserConcernController extends BaseUtil {
	@Resource
	private UserConcernService ucService;
	@Resource
	private UserCommonService userService;
	@Resource
	private UserShareGoodsService usgService;
	@Resource
	private UserCommonService ucommonService;

	private static Logger log = Logger.getLogger("WXUserConcernController");

	//关注列表
	@RequestMapping("/myConcernedUser")
	public void showMyConcernedUser(HttpServletResponse response, Integer userId, Integer loginPlat, Integer pageNo,
			Integer pageSize,Integer type) {
		if (pageNo == null) {
			pageNo = 1;
		}
		if (pageSize == null) {
			pageSize = 20;
		}
		List<UserConcern> ucList  = ucService.findSHUserConcernedUsers(userId,(pageNo - 1) * pageSize, pageSize,type);
		output(response, JsonUtil.buildJson(ucList));
	}

	//关注用户、取消关注用户
	@RequestMapping("/concernUser")
	public void userConcernUser(HttpServletResponse response, UserConcern uc, Integer loginPlat) {
		if (uc.getConcernedUserId() == null) {
			output(response, JsonUtil.buildFalseJson("2", "您要关注的用户不存在!"));
		} else {
			UserCommon ushanghui = ucommonService.getUserByUserId(uc.getUserId());
			if (ushanghui == null) {
				output(response, JsonUtil.buildFalseJson("2", "您要关注的用户不存在!"));
			} else {
				UserConcern concerned = ucService.ConfirmIConcernedHim(uc.getUserId(), uc.getConcernedUserId());
				if (concerned == null) {// 若没关注，则关注
					try {
						uc.setCreateTime(new Date());
						uc.setIsRemove(0);
						ucService.addNewUserConcern(uc);
						ushanghui.setConcernedCount(
								(ushanghui.getConcernedCount() == null ? 0 : ushanghui.getConcernedCount()) + 1);
						ucommonService.commonInfoUpdate(ushanghui);
						output(response, JsonUtil.buildFalseJson("0", "关注成功!"));
					} catch (Exception e) {
						log.info("用户" + uc.getUserId() + "关注用户" + uc.getConcernedUserId() + "失败，失败原因" + e);
						output(response, JsonUtil.buildFalseJson("1", "关注失败!"));
					}
				} else if (concerned != null && concerned.getIsRemove() == 1) {// 若曾今取消关注，则重新关注
					try {
						concerned.setCreateTime(new Date());
						concerned.setIsRemove(0);
						ucService.updateConcernUser(concerned);
						ushanghui.setConcernedCount(
								(ushanghui.getConcernedCount() == null ? 0 : ushanghui.getConcernedCount()) + 1);
						ucommonService.commonInfoUpdate(ushanghui);
						output(response, JsonUtil.buildFalseJson("0", "关注成功!"));
					} catch (Exception e) {
						log.info("用户" + uc.getUserId() + "关注用户" + uc.getConcernedUserId() + "失败，失败原因" + e);
						output(response, JsonUtil.buildFalseJson("1", "关注失败!"));
					}
				} else {// 若已关注，则取消关注
					try {
						// concerned.setCreateTime(new Date());
						concerned.setIsRemove(1);
						ucService.updateConcernUser(concerned);
						ushanghui.setConcernedCount(
								(ushanghui.getConcernedCount() == null ? 0 : ushanghui.getConcernedCount()) - 1);
						ucommonService.commonInfoUpdate(ushanghui);
						output(response, JsonUtil.buildFalseJson("0", "取消关注成功!"));
					} catch (Exception e) {
						log.info("用户" + uc.getUserId() + "关注用户" + uc.getConcernedUserId() + "失败，失败原因" + e);
						output(response, JsonUtil.buildFalseJson("1", "取消关注失败!"));
					}
				}
			}
		}
	}

	//取消关注用户
	@RequestMapping("/cancelConcern")
	public void userCancelConcernUser(HttpServletResponse response, UserConcern uc) {
		if (uc.getConcernedUserId() == null) {
			output(response, JsonUtil.buildFalseJson("2", "您要取消关注的用户不存在!"));
		} else {
			UserCommon concernedUser = userService.getUserByUserId(uc.getConcernedUserId());
			if (concernedUser == null) {
				output(response, JsonUtil.buildFalseJson("2", "您要取消关注的用户不存在!"));
			} else {
				UserConcern concerned = ucService.ConfirmIConcernedHim(uc.getUserId(), uc.getConcernedUserId());
				if (concerned != null) {
					try {

					} catch (Exception e) {
						log.info("用户" + uc.getUserId() + "取消关注用户" + uc.getConcernedUserId() + "失败，失败原因" + e);
						output(response, JsonUtil.buildFalseJson("1", "取消关注失败!"));
					}
				} else {
					output(response, JsonUtil.buildFalseJson("3", "您未关注该用户!"));
				}
			}
		}
	}
	
	
}
