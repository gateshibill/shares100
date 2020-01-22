package com.cofc.controller.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cofc.pojo.ConfigScore;
import com.cofc.pojo.UserCommon;
import com.cofc.pojo.UserConcern;
import com.cofc.pojo.UserMessage;
import com.cofc.pojo.UserOrder;
import com.cofc.pojo.vij.CardTicket;
import com.cofc.pojo.vij.UserTicket;
import com.cofc.service.ConfigScoreService;
import com.cofc.service.UserCommonService;
import com.cofc.service.UserConcernService;
import com.cofc.service.UserMessageService;
import com.cofc.service.UserOrderService;
import com.cofc.service.UserShoppingAddressService;
import com.cofc.service.vij.CardTicketService;
import com.cofc.service.vij.UserTicketService;
import com.cofc.util.BaseUtil;
import com.cofc.util.JsonUtil;

/**
 * 前端用户相关接口
 * 
 * @author chen
 *
 */
@Controller
@RequestMapping("/wx/user")
public class WXUserController extends BaseUtil {
	@Resource
	private UserCommonService ucommonService;
	@Resource
	private UserShoppingAddressService usaService;
	@Resource
	private UserConcernService ucService;
	@Resource
	private UserOrderService orderService;
	@Resource
	private UserMessageService userMessageService;
	@Resource
	private ConfigScoreService configScoreService;
	@Resource
	private UserTicketService userTicketService;//我的优惠券
	public static Logger log = Logger.getLogger("WXUserController");

	@RequestMapping("/userInfo")
	public void showUserInfo(HttpServletResponse response, UserCommon comuser, Integer curUserId, Integer loginPlat) {
		List<UserCommon> ucommonList = new ArrayList<UserCommon>();
		UserCommon cuser = ucommonService.getUserByUserId(comuser.getUserId());
		// UserShangHui ushanghui =
		// ushanghuiService.getUserByUId(comuser.getUserId());
		UserConcern uConcern = ucService.ConfirmIConcernedHim(curUserId, comuser.getUserId());
		if (uConcern == null) {
			cuser.setIsConcerned(0);
		} else {
			 if (uConcern.getIsRemove() == 1) {
				 cuser.setIsConcerned(0);
			} else{
				cuser.setIsConcerned(1);
			}
		}
		if(cuser.getUserId() != null){
			UserTicket ticket = new UserTicket();
			ticket.setUserId(cuser.getUserId());
			ticket.setIsUse(0);
			int ticketCount = userTicketService.getUserTicketCount(ticket);
			cuser.setTicketCardCount(ticketCount);
		}
		ucommonList.add(cuser);
		output(response, JsonUtil.buildJson(ucommonList));

	}
	
	/**
	 * 修改个人资料数据回显
	 * @param request
	 * @param mv
	 * @return
	 */
	@RequestMapping("/getUserinfo")
	public void getUserinfo(HttpServletResponse response, HttpServletRequest request,ModelAndView mv){
		UserCommon user = (UserCommon) request.getSession().getAttribute("vijMallUserSession");
		UserCommon thisUser = ucommonService.getUserByUserId(user.getUserId());
		output(response, JsonUtil.bulidObjectJson(thisUser));
	}

	//前端修改用户信息
	@RequestMapping("/changeInformation")
	public void changeInformation(HttpServletResponse response, UserCommon user) {
		UserCommon thisUser = ucommonService.getUserByUserId(user.getUserId());
		log.info("要修改信息的用户id是【" + user.getUserId() + "】");
		if (thisUser == null) {
			output(response, JsonUtil.buildFalseJson("1", "该用户不存在!"));
		} else {
			try {

				//比较字符串长度,效率高,是最好的一个方法;
				if (user.getRealName() == null || user.getRealName().length() <= 0) {  
					output(response,JsonUtil.buildFalseJson("1","名字不能为空"));
					return;
				}
				if(user.getPhone() == null || user.getPhone().length() <= 0){
					output(response,JsonUtil.buildFalseJson("1","联系方式不能为空"));
					return;
				}
				if(user.getEmail() == null || user.getEmail().length() <= 0){
					output(response,JsonUtil.buildFalseJson("1","邮箱不能为空"));
					return;
				}
				thisUser.setOpenId(thisUser.getOpenId());
				thisUser.setNickName(user.getNickName());
				thisUser.setEmail(user.getEmail());
				thisUser.setCompony(user.getCompony());
				thisUser.setComponyIntroduce(user.getComponyIntroduce());
				thisUser.setRealName(user.getRealName());
				thisUser.setHeadImage(user.getHeadImage());
				thisUser.setCardImage(user.getCardImage());
				thisUser.setUpdateTime(new Date());
				thisUser.setPhone(user.getPhone());
				thisUser.setUserSex(user.getUserSex());
				thisUser.setBankCard(user.getBankCard());
				if (user.getUserPosition() != null) {
					thisUser.setUserPosition(user.getUserPosition());
				}
				ucommonService.commonInfoUpdate(thisUser);
				ConfigScore score = configScoreService.getConfigScoreByLoginPlat(user.getLoginPlat());
				if(score != null){
					if(score.getMyInfoScore() != null){
						//查看是否已经存在
						UserMessage m = new UserMessage();
						m.setUserId(thisUser.getUserId());
						m.setLoginPlat(score.getLoginPlat());
						m.setIsRemove(0);
						m.setType(4);
						int count = userMessageService.getMessageCount(m);
						if(count <= 0){
							//更新积分
							UserCommon u = new UserCommon();
							u.setUserId(thisUser.getUserId());
							u.setIntegral(thisUser.getIntegral()+score.getMyInfoScore());
							ucommonService.commonInfoUpdate(u);
							//插入记录
							UserMessage m2 = new UserMessage();
							m2.setContent("完善资料获取积分");
							m2.setCreateTime(new Date());
							m2.setIsRead(0);
							m2.setIsRemove(0);
							m2.setIsTag(1);
							m2.setType(4);
							m2.setLoginPlat(thisUser.getLoginPlat());
							m2.setScore(score.getMyInfoScore());
							m2.setUserId(thisUser.getUserId());
							userMessageService.addMessage(m2);
						}
					}
				}
				output(response, JsonUtil.buildFalseJson("0", "修改成功"));
			} catch (Exception e) {
				log.error("编辑用户信息失败："+e.getMessage());;
				output(response,JsonUtil.buildFalseJson("1","修改失败"));
			}
			
		}

	}
	/**
	 * 修改个人信息
	 * @param response
	 * @param user
	 */
	@RequestMapping("/updateUserById")
	public void updateUserById(HttpServletResponse response,UserCommon user){
		if(user.getUserId() == null){
			output(response,JsonUtil.buildFalseJson("1", "用户不存在或已被删除"));
		}else{
			if(user.getPhone() != null && !user.getPhone().equals("")){
				UserCommon info = ucommonService.getPhoneIsAlready(user.getPhone(), user.getUserId(), user.getLoginPlat());
				if(info != null){
					output(response, JsonUtil.buildFalseJson("1","手机号码已存在,请换一个"));
					return;
				}
			}
			ucommonService.commonInfoUpdate(user);
			output(response,JsonUtil.buildFalseJson("0", "修改信息成功"));
		}
	}
	@RequestMapping("/changeToken")
	public void changeToken(HttpServletResponse response,UserCommon user){
		if(user.getUserId() != null){
			UserCommon info = ucommonService.getUserByUserId(user.getUserId());
			if(info == null){
				output(response,JsonUtil.buildFalseJson("1","该用户不存在"));
			}else{
				if(user.getIntegral() > info.getIntegral()){
					output(response, JsonUtil.buildFalseJson("1", "兑换积分已经超过用户拥有的"));
				}else{
					UserCommon u = new UserCommon();
					u.setUserId(info.getUserId());
					u.setIntegral(info.getIntegral()-user.getIntegral());
					if(info.getUserToken() != null){
						u.setUserToken(info.getUserToken()+user.getUserToken());
					}else{
						u.setUserToken(user.getUserToken());
					}
					ucommonService.commonInfoUpdate(u);
					//插入积分消耗
					UserMessage m1 = new UserMessage();
					m1.setContent("积分转换token");
					m1.setScore(user.getIntegral());
					m1.setCreateTime(new Date());
					m1.setLoginPlat(info.getLoginPlat());
					m1.setIsRead(0);
					m1.setIsRemove(0);
					m1.setIsTag(0);
					m1.setType(11);
					m1.setUserId(info.getUserId());
				    userMessageService.addMessage(m1);
					//token新增
					UserMessage m2 = new UserMessage();
					m2.setContent("积分转换token");
					m2.setIsTag(2);
					m2.setIsRead(0);
					m2.setIsRemove(0);
					m2.setLoginPlat(info.getLoginPlat());
					m2.setUserId(info.getUserId());
					m2.setType(20);
					m2.setToken(user.getUserToken());
					m2.setCreateTime(new Date());
					userMessageService.addMessage(m2);
					output(response,JsonUtil.buildFalseJson("0","修改成功"));
				}
			}
		}else{
			 output(response,JsonUtil.buildFalseJson("1","该用户不存在"));
		}
	}
	// @RequestMapping("/changeInformation")
	// public void changeInformation(HttpServletResponse response, UserCommon
	// comuser, UserFeiKanYun ufky,
	// UserBaiXiangYuan ubxy, UserShangHui ush, Integer loginPlat) {
	// log.info("要修改信息的用户id是【" + comuser.getUserId() + "】");
	// UserCommon commonInfo =
	// ucommonService.getUserByUserId(comuser.getUserId());
	// if (commonInfo == null) {
	// output(response, JsonUtil.buildFalseJson("1", "该用户不存在!"));
	// } else {
	// if (loginPlat == 1) {// 商会
	// UserShangHui ushanghui =
	// ushanghuiService.getThisUserByUserId(comuser.getUserId());
	// if (ushanghui == null) {
	// output(response, JsonUtil.buildFalseJson("1", "该用户不存在!"));
	// } else {
	// commonInfo.setNickName(comuser.getNickName());
	// commonInfo.setRealName(comuser.getRealName());
	// commonInfo.setCardImage(comuser.getCardImage());
	// commonInfo.setCompony(comuser.getCompony());
	// commonInfo.setComponyIntroduce(comuser.getComponyIntroduce());
	// commonInfo.setEmail(comuser.getEmail());
	// commonInfo.setHeadImage(comuser.getHeadImage());
	// commonInfo.setPhone(comuser.getPhone());
	// commonInfo.setUserSex(comuser.getUserSex());
	// commonInfo.setUpdateTime(new Date());
	// try {
	// ucommonService.commonInfoUpdate(commonInfo);
	// output(response, JsonUtil.buildFalseJson("0", "修改成功!"));
	// } catch (Exception e) {
	// output(response, JsonUtil.buildFalseJson("2", "修改失败!"));
	// log.info("用户【" + comuser.getUserId() + "】修改信息失败，失败原因" + e);
	// }
	// }
	// } else if (loginPlat == 2) {// 百享园
	// UserBaiXiangYuan ubaixiangyuan =
	// ubaixiangyuanService.getThisUserByUserId(comuser.getUserId());
	// if (ubaixiangyuan == null) {
	// output(response, JsonUtil.buildFalseJson("1", "该用户不存在!"));
	// } else {
	// commonInfo.setNickName(comuser.getNickName());
	// commonInfo.setRealName(comuser.getRealName());
	// commonInfo.setCardImage(comuser.getCardImage());
	// commonInfo.setCompony(comuser.getCompony());
	// commonInfo.setComponyIntroduce(comuser.getComponyIntroduce());
	// commonInfo.setEmail(comuser.getEmail());
	// commonInfo.setHeadImage(comuser.getHeadImage());
	// commonInfo.setPhone(comuser.getPhone());
	// commonInfo.setUserSex(comuser.getUserSex());
	// commonInfo.setUpdateTime(new Date());
	// try {
	// ucommonService.commonInfoUpdate(commonInfo);
	// output(response, JsonUtil.buildFalseJson("0", "修改成功!"));
	// } catch (Exception e) {
	// output(response, JsonUtil.buildFalseJson("2", "修改失败!"));
	// log.info("用户【" + comuser.getUserId() + "】修改信息失败，失败原因" + e);
	// }
	// }
	// } else if (loginPlat == 3) {// 飞看云
	// UserFeiKanYun ufeikanyun =
	// ufeikanyunService.getThisUserByUserId(comuser.getUserId());
	// if (ufeikanyun == null) {
	// output(response, JsonUtil.buildFalseJson("1", "该用户不存在!"));
	// } else {
	// commonInfo.setNickName(comuser.getNickName());
	// commonInfo.setRealName(comuser.getRealName());
	// commonInfo.setCardImage(comuser.getCardImage());
	// commonInfo.setCompony(comuser.getCompony());
	// commonInfo.setComponyIntroduce(comuser.getComponyIntroduce());
	// commonInfo.setEmail(comuser.getEmail());
	// commonInfo.setHeadImage(comuser.getHeadImage());
	// commonInfo.setPhone(comuser.getPhone());
	// commonInfo.setUserSex(comuser.getUserSex());
	// commonInfo.setUpdateTime(new Date());
	// try {
	// ucommonService.commonInfoUpdate(commonInfo);
	// output(response, JsonUtil.buildFalseJson("0", "修改成功!"));
	// } catch (Exception e) {
	// output(response, JsonUtil.buildFalseJson("2", "修改失败!"));
	// log.info("用户【" + comuser.getUserId() + "】修改信息失败，失败原因" + e);
	// }
	// }
	// }
	// }

	// else {
	// thisUser.setOpenId(thisUser.getOpenId());
	// thisUser.setNickName(user.getNickName());
	// thisUser.setEmail(user.getEmail());
	// thisUser.setCompony(user.getCompony());
	// thisUser.setComponyIntroduce(user.getComponyIntroduce());
	// thisUser.setRealName(user.getRealName());
	// thisUser.setHeadImage(user.getHeadImage());
	// thisUser.setUserImage(user.getUserImage());
	// thisUser.setUpdateTime(new Date());
	// thisUser.setPhone(user.getPhone());
	// thisUser.setSex(user.getSex());
	// userService.updateUser(thisUser);
	// output(response, JsonUtil.buildFalseJson("0", "修改成功"));
	// }

	// }

	@RequestMapping("/userList")
	public void userList(HttpServletResponse response, UserCommon comuser, Integer pageNo, Integer pageSize) {
		if (pageNo == null || pageNo == 0) {
			pageNo = 1;
		}
		if (pageSize == null || pageSize == 0) {
			pageSize = 20;
		}
		List<UserCommon> userList = ucommonService.findUserList(comuser, null, null, (pageNo - 1) * pageSize, pageSize,null);
		output(response, JsonUtil.buildJson(userList));
	}

	@RequestMapping("/renzhengUser")
	public void managerRenzhengUser(HttpServletResponse response, Integer managerId, Integer userId,
			Integer loginPlat) {
		UserCommon manager = ucommonService.getUserByUserId(managerId);
		if (manager == null || manager.getIsManager() != 1) {
			output(response, JsonUtil.buildFalseJson("1", "您不是管理员,不能认证该用户!"));
		} else {
			try {
				ucommonService.changeUserRenzheng(userId, 1);
				output(response, JsonUtil.buildFalseJson("0", "认证成功!"));
			} catch (Exception e) {
				output(response, JsonUtil.buildFalseJson("2", "认证失败!"));
				log.info("用户" + managerId + "认证用户" + userId + "失败，失败原因:" + e);
			}
		}
	}
	
	//审核用户是否是会员(用户发起申请成为会员时调用)
	@RequestMapping("/examineUser")
	public void examineUser(HttpServletResponse response,Integer userId){
		UserCommon manager = ucommonService.getUserByUserId(userId);
		if (manager == null) {
			output(response, JsonUtil.buildFalseJson("1", "用户不存在!"));
		}else {
			manager.setUpdateTime(new Date());
			manager.setIsMember(1);
			ucommonService.commonInfoUpdate(manager);
			output(response, JsonUtil.buildFalseJson("0", "申请成功!"));
		}
	}
	
	/**
	 * 前端用户邀请列表
	 * @param response
	 * @param userId
	 * @param loginPlat
	 * @param page
	 * @param limit
	 */
	@RequestMapping("/uservisitlist")
	public void userVisitList(HttpServletResponse response,Integer introducer,Integer loginPlat,Integer pageNo,Integer pageSize){
		if(pageNo == null){
			pageNo = 1;
		}
		if(pageSize == null){
			pageSize = 10;
		}
		List<UserCommon> lists = ucommonService.getVisitList(introducer, loginPlat,(pageNo-1) * pageSize, pageSize);
	    output(response,JsonUtil.buildJson(lists));
	}
	/**
	 * 获取邀请者购买列表
	 * @param response
	 * @param introducer
	 * @param loginPlat
	 * @param pageNo
	 * @param pageSize
	 */
	@RequestMapping("/uservisitorder")
	public void userVisitOrder(HttpServletResponse response,Integer agentId,Integer loginPlat,Integer pageNo,Integer pageSize){
		if(agentId == null){
			output(response,JsonUtil.buildFalseJson("1","邀请人id不能为空"));
			return;
		}
		if(loginPlat == null){
			output(response,JsonUtil.buildFalseJson("1","应用id不能为空"));
			return;
		}
		List<UserOrder> lists = orderService.userVisitOrder(agentId, loginPlat,(pageNo-1) * pageSize, pageSize); 
		output(response,JsonUtil.buildJson(lists));
	}
	/**
	 * 获取代理商通过代理获得的返点
	 * @param introducer
	 * @param loginPlat
	 */
	@RequestMapping("/uservisitmoneycount")
	public void userVisitMoneyCount(HttpServletResponse response,Integer agentId,Integer loginPlat){
		if(agentId == null){
			output(response,JsonUtil.buildFalseJson("1","邀请人id不能为空"));
			return;
		}
		if(loginPlat == null){
			output(response,JsonUtil.buildFalseJson("1","应用id不能为空"));
			return;
		}
		String totalmoney = orderService.userVisitMoneyCount(agentId, loginPlat);
		output(response,JsonUtil.buildFalseJson("0",totalmoney));
	}
	/**
	 * 前端更新积分
	 * @param response
	 * @param user
	 */
	@RequestMapping("/updateScore")
	public void updateScore(HttpServletResponse response,UserCommon user){
		if(user.getUserId() == null){
			output(response,JsonUtil.buildFalseJson("1","未知用户"));
		}else{
			try {
				ucommonService.commonInfoUpdate(user);
				output(response,JsonUtil.buildFalseJson("0","更新积分成功"));
			} catch (Exception e) {
				log.error("更新积分失败,失败原因:"+e.getMessage());
				output(response,JsonUtil.buildFalseJson("1","更新积分失败"));
			}			
		}
	}
	/**
	 * 
	 * @param response
	 * @param userId
	 * @param score
	 */
	@RequestMapping("/updateNewScore")
	public void updateNewScore(HttpServletResponse response,Integer userId,Integer score){
		UserCommon user = ucommonService.getUserByUserId(userId);
		if(user != null){
			if(score == null){
				output(response,JsonUtil.buildFalseJson("1", "积分未变动"));
			}else{
				UserCommon uinfo = new UserCommon();
				uinfo.setUserId(userId);
				uinfo.setIntegral(user.getIntegral()+score);
				ucommonService.commonInfoUpdate(uinfo);
				output(response,JsonUtil.buildFalseJson("0", "更新积分成功"));
			}
		}else{
			output(response,JsonUtil.buildFalseJson("1","用户不存在"));
		}
	}
	/**
	 * 一天登陆送积分
	 * @param response
	 * @param userId
	 */
	@RequestMapping("/checkNearlyLogin")
	public void checkNearlyLogin(HttpServletResponse response,Integer userId){
		if(userId == null){
			output(response,JsonUtil.buildFalseJson("1","非法用户"));
		}else{
			UserCommon user = ucommonService.nearlyLogin(userId);
			List<UserCommon> lists = new ArrayList<>();
			lists.add(user);
			output(response,JsonUtil.buildJson(lists));
		}
	}
	/**
	 * 是否接受消息
	 * @param response
	 * @param user
	 */
	@RequestMapping("/updateMes")
	public void updateMes(HttpServletResponse response,UserCommon user){
		ucommonService.commonInfoUpdate(user);
		output(response,JsonUtil.buildFalseJson("0","设置成功"));
	}
	/**
	 * 获取用户积分排名
	 * @param response
	 * @param user
	 */
	@RequestMapping("/getUserRank")
	public void getUserRank(HttpServletResponse response,UserCommon user,Integer page,Integer limit){
		if(page == null){
			page = 1;
		}
		if(limit == null){
			limit = 10;
		}
		List<UserCommon> lists = ucommonService.getUserRankList(user, (page-1)*limit, limit);
		output(response,JsonUtil.buildJson(lists));
	}
	/**
	 * 获取某个人的积分排名
	 * @param response
	 * @param userId
	 */
	@RequestMapping("/getRankByUserId")
	public void getRankByUsrId(HttpServletResponse response,Integer userId,Integer loginPlat){
		List<UserCommon> lists = new ArrayList<UserCommon>();
		UserCommon list = ucommonService.getRankByUserId(userId,loginPlat);
		if(list != null){
			lists.add(list);
		}
		output(response,JsonUtil.buildJson(lists));
	}
}
