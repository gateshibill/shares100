package com.cofc.controller.user;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cofc.controller.file.ErWeiCodeImageContoller;
import com.cofc.pojo.ApplicationCommon;
import com.cofc.pojo.ConfigScore;
import com.cofc.pojo.GroupUsers;
import com.cofc.pojo.UserCommon;
import com.cofc.pojo.UserMessage;
import com.cofc.service.ApplicationCommonService;
import com.cofc.service.ConfigScoreService;
import com.cofc.service.GroupUsersService;
import com.cofc.service.SystemSetService;
import com.cofc.service.UserCommonService;
import com.cofc.service.UserMessageService;
import com.cofc.util.AESOperator;
import com.cofc.util.BaseUtil;
import com.cofc.util.EmojiFilterUtils;
import com.cofc.util.JsonUtil;
import com.cofc.util.WeiXinSessionUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
//import org.jivesoftware.smack.XMPPException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/wx/weixinLogin")
public class WXLoginController extends BaseUtil {
	@Resource
	private UserCommonService userComService;
	@Resource
	private ApplicationCommonService appService;
	@Resource
	private SystemSetService ssService;
	@Resource
	private GroupUsersService groupUService;
	@Resource
	private UserMessageService userMessageService;// 发送消息
	@Resource
	private ConfigScoreService configScoreService;// 获取积分参数
	public static Logger log = Logger.getLogger("WXLoginController");

	@RequestMapping("/analysisCode")
	public synchronized void analysisWXCode(HttpServletResponse response, String code, Integer loginPlat,
			Integer introducer, Integer type) throws ParseException {
		if (type == null) {
			type = 1;
		}
		ApplicationCommon appl = appService.getApplicationsByCriteria(loginPlat);
		WeiXinSessionUtils.DEFAULT_APP_ID = appl.getAppId();
		WeiXinSessionUtils.DEFAULT_APP_SECRET = appl.getAppSecret();
		GsonBuilder gsonBuilder = new GsonBuilder().serializeNulls();
		String authJson = WeiXinSessionUtils.getAccessTokenByCode(code);
		Gson gson = gsonBuilder.create();
		AuthInfo info = gson.fromJson(authJson, AuthInfo.class);
		if (info == null) {
			output(response, JsonUtil.buildFalseJson("1", "登陆失败"));
			return;
		}
		UserCommon comUser = userComService.getUserByOpenId(info.openid);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		List<UserCommon> returnuser = new ArrayList<UserCommon>();
		if (comUser == null) {
			try {
				ConfigScore score = configScoreService.getConfigScoreByLoginPlat(loginPlat);
				//如果没有就默认
				if(null==score){
					score = new ConfigScore();
				}
				String insertNickName = "用户" + Long.toHexString(new Date().getTime());
				comUser = new UserCommon();
				comUser.setNickName(insertNickName);
				comUser.setCreateTime(new Date());
				comUser.setWalletBalance(0.00);
				comUser.setUserToken(0.000);
				comUser.setUserLevel(0);
				comUser.setIsManager(0);
				comUser.setRegSource(2);
				int registerScore = 0;
				int visitScore = 0;
				int twoVisitScore = 0;
				if (score != null) {
					if (score.getRegisterScore() != null) {
						registerScore = score.getRegisterScore();
					}
					if (score.getVisitScore() != null) {
						visitScore = score.getVisitScore();
					}
					if (score.getTwoVisitScore() != null) {
						twoVisitScore = score.getTwoVisitScore();
					}
				}
				comUser.setIntegral(registerScore);// 注册获取的积分
				comUser.setConcernedCount(0);
				comUser.setIsMember(1);
				comUser.setIsRenzheng(0);
				comUser.setLoginPlat(loginPlat);

				log.info("新用户注册：" + comUser.getUserId() + "(" + comUser.getNickName() + ")" + "推荐人ID:" + introducer);
				if (introducer != null) {
					comUser.setIntroducer(introducer);// 推荐人编号
					if (visitScore > 0) {
						// 更新一级邀请人积分
						UserCommon u1 = new UserCommon();
						UserCommon u1Info = userComService.getUserByUserId(introducer);
						u1.setUserId(introducer);
						int u1score = 0;
						if (u1Info.getIntegral() != null) {
							u1score = u1Info.getIntegral(); // 获取用户的积分
						}

						u1.setIntegral(u1score + visitScore);
						userComService.commonInfoUpdate(u1);
						// 发送消息
						UserMessage m1 = new UserMessage();
						m1.setContent("一级邀请用户奖励");
						m1.setLoginPlat(loginPlat);
						m1.setUserId(introducer);
						m1.setType(1);
						m1.setIsRead(0);
						m1.setIsRemove(0);
						m1.setScore(visitScore);
						m1.setCreateTime(new Date());
						m1.setIsTag(2);
						userMessageService.addMessage(m1);
						if (twoVisitScore > 0) {
							if (u1Info.getIntroducer() != null) {
								// 更新二级邀请人积分
								UserCommon u2 = new UserCommon();
								UserCommon u2Info = userComService.getUserByUserId(u1Info.getIntroducer());
								u2.setUserId(u1Info.getIntroducer());
								int u2score = 0;
								if (u2Info.getIntegral() != null) {
									u1score = u1Info.getIntegral(); // 获取用户的积分
								}
								u2.setIntegral(u2score + twoVisitScore);
								userComService.commonInfoUpdate(u2);
								// 发送消息
								UserMessage m2 = new UserMessage();
								m2.setContent("二级邀请用户奖励");
								m2.setLoginPlat(loginPlat);
								m2.setUserId(introducer);
								m2.setType(1);
								m2.setIsRead(0);
								m2.setIsRemove(0);
								m2.setScore(twoVisitScore);
								m2.setCreateTime(new Date());
								m2.setIsTag(3);
								userMessageService.addMessage(m2);
							}
						}
					}
				}
				comUser.setOpenId(info.openid);
				comUser.setComponyIntroduce("The more you share, the more you succeed。");
				comUser.setUserStatus(1);
				comUser.setHeadCard("http://www.ailefeigou.com/cofC2/userImage/defaultHeadCard.jpg");
				if (loginPlat == 3) {
					comUser.setCardImage(
							"http://www.haoshi360.com/xcximages/person1.jpg,http://www.haoshi360.com/xcximages/person2.jpg");
				} else {
					comUser.setCardImage(
							"http://www.ailefeigou.com/cofC2/userImage/defaultCardImage1.jpg,http://www.ailefeigou.com/cofC2/userImage/defaultCardImage2.jpg");
				}
				comUser.setAccessTime(new Date());
				userComService.addNewUserCommon(comUser);
				// 注册到即时通讯,暂时用不上
				// registToIM(comUser);

				String noEntry = "userId=" + comUser.getUserId() + "&timeStamp=" + sdf.format(new Date())
						+ "&loginPlat=" + comUser.getLoginPlat();
				String token = AESOperator.getInstance().encrypt(noEntry);
				comUser.setTokenCode(token);
				userComService.commonInfoUpdate(comUser);
				if (registerScore > 0) {
					// 发送消息
					UserMessage m3 = new UserMessage();
					m3.setContent("注册奖励");
					m3.setLoginPlat(loginPlat);
					m3.setUserId(comUser.getUserId());
					m3.setType(1);
					m3.setIsRead(0);
					m3.setIsRemove(0);
					m3.setScore(registerScore);
					m3.setCreateTime(new Date());
					m3.setIsTag(1);
					userMessageService.addMessage(m3);
				}
				returnuser.add(comUser);
				output(response, JsonUtil.buildJson(returnuser));
			} catch (Exception e) {
				log.error("注册失败,失败原因：" + e.getMessage());
			}
		} else {
			returnuser.add(comUser);
			UserCommon u = new UserCommon();
			u.setUserId(comUser.getUserId());
			u.setAccessTime(new Date());
			userComService.commonInfoUpdate(u); // 更新访问时间
			output(response, JsonUtil.buildJson(returnuser));
			// Date now = new Date();
			// comUser.setAccessTime(new Date());
			// String noEntry = "userId=" + info.userId + "&timeStamp=" +
			// sdf.format(now) + "&loginPlat=" + loginPlat;
			// String token = AESOperator.getInstance().encrypt(noEntry);
			// comUser.setTokenCode(token);
			// userComService.commonInfoUpdate(comUser);
			// log.info("用户" + comUser.getUserId() + "的token信息更新成功。");
			// returnuser.add(comUser);
			// if (childPlat != null) {// 如果需要再登录子应用id，则直接成为子应用的用户
			// ApplicationCommon childapp =
			// appService.getApplicationsByCriteria(childPlat);
			// if (childapp != null) {
			// GroupUsers groupU =
			// groupUService.comfirmIsJoinThisGroup(comUser.getUserId(),
			// childPlat);
			// if (groupU == null) {// 用户没有加入该社区
			// groupU = new GroupUsers();
			// groupU.setGroupId(childPlat);
			// groupU.setUserId(comUser.getUserId());
			// groupU.setJoinTime(now);
			// groupU.setIsCreater(0);
			// groupUService.UserJoinGroup(groupU);
			// log.info("用户" + comUser.getUserId() + "已加入社区" + childPlat);
			// } else {
			// log.info("用户" + comUser.getUserId() + "已加入过社区" + childPlat);
			// }
			// } else {
			// log.info("社区" + childPlat + "没有找到");
			// }
			// }
			// output(response, JsonUtil.buildJson(returnuser));// 返回更新后的用户信息
		}
	}

//	private void registToIM(UserCommon comUser) throws XMPPException {
//		// 1.注册到即时通讯
//		if (IMUtils.getInstance().regist(comUser.getUserId().toString(), comUser.getUserId().toString())) {
//			log.info(comUser.getUserId() + "注册IM成功！");
//
//			// 2.把头像消息和用户名设置到IM
//			if (IMUtils.getInstance().updateUserInfo(comUser.getUserId().toString(), comUser.getUserId().toString(),
//					comUser.getHeadImage(), comUser.getNickName())) {
//				log.info(comUser.getUserId() + "用户信息设置到IM成功！");
//			} else {
//				log.info(comUser.getUserId() + "用户信息设置到IM失败！");
//			}
//
//			// 2.发送消息
//			if (IMUtils.getInstance().sendMessage("feikan", "feikan", "欢迎加入飞看", comUser.getUserId().toString())) {
//				log.info(comUser.getUserId() + "系统IM发送成功");
//			} else {
//				log.info(comUser.getUserId() + "系统IM发送失败");
//			}
//
//		} else {
//			log.info(comUser.getUserId() + "注册IM失败！");
//		}
//	}

	// 修改用户信息
	@RequestMapping("/updateUserdata")
	public void updateUserdata(HttpServletResponse response, UserCommon user) {
		UserCommon common = userComService.getUserByUserId(user.getUserId());
		List<UserCommon> userList = new ArrayList<UserCommon>();
		if (common == null) {
			output(response, JsonUtil.buildFalseJson("1", "用户不存在!"));
		} else {
			// if(common.getUserCodeUrl().equals("") || common.getUserCodeUrl()
			// == null){
			// String path = getCode(common.getUserId(), request);
			// user.setUserCodeUrl(path);
			// }
			user.setUpdateTime(new Date());
			user.setAccessTime(new Date());
			userComService.commonInfoUpdate(user);
			UserCommon common1 = userComService.getUserByUserId(user.getUserId());
			userList.add(common1);

			// 2.把头像消息和用户名设置到IM
//			if (IMUtils.getInstance().updateUserInfo(user.getUserId().toString(), user.getUserId().toString(),
//					user.getHeadImage(), user.getNickName())) {
//				log.info(user.getUserId() + "用户信息设置到IM成功！");
//			} else {
//				log.info(user.getUserId() + "用户信息设置到IM失败！");
//			}
			output(response, JsonUtil.buildJson(userList));
		}
	}

	/**
	 * 新增用户
	 * 
	 * @param response
	 * @param userInfo
	 * @param childPlat
	 * @throws Exception
	 */
	@RequestMapping("/login")
	public void wxLogin(HttpServletResponse response, UserCommon userInfo, Integer childPlat) throws Exception {
		if (userInfo.getOpenId() != null && !"undefined".equals(userInfo.getOpenId())) {
			UserCommon currUser = userComService.getUserByOpenId(userInfo.getOpenId());
			if (currUser == null) {
				List<UserCommon> returnl = new ArrayList<UserCommon>();
				Date now = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
				String insertNickName = EmojiFilterUtils.cutEmoji(userInfo.getNickName());
				if (StringUtils.isBlank(insertNickName)) {
					insertNickName = "用户" + Long.toHexString(new Date().getTime());
				}
				userInfo.setNickName(insertNickName);
				userInfo.setCreateTime(now);
				userInfo.setWalletBalance(0.00);
				userInfo.setUserLevel(0);
				userInfo.setIsManager(0);
				userInfo.setIntegral(0);
				userInfo.setConcernedCount(0);
				userInfo.setIsMember(1);
				userInfo.setIsRenzheng(0);
				userInfo.setComponyIntroduce("The more you share, the more you succeed。");
				userInfo.setUserStatus(1);
				userInfo.setHeadCard("http://www.ailefeigou.com/cofC2/userImage/defaultHeadCard.jpg");
				userInfo.setCardImage(
						"http://www.ailefeigou.com/cofC2/userImage/defaultCardImage1.jpg,http://www.ailefeigou.com/cofC2/userImage/defaultCardImage2.jpg");
				userInfo.setAccessTime(now);
				try {
					userComService.addNewUserCommon(userInfo);
					String noEntry = "userId=" + userInfo.getUserId() + "&timeStamp=" + sdf.format(now) + "&loginPlat="
							+ userInfo.getLoginPlat();
					String token = AESOperator.getInstance().encrypt(noEntry);
					userInfo.setTokenCode(token);
					userComService.commonInfoUpdate(userInfo);
					log.info("用户" + userInfo.getUserId() + "的token信息更新成功。");
					returnl.add(userInfo);
					if (childPlat != null) {// 如果需要再登录子应用id，则直接成为子应用的用户
						ApplicationCommon childapp = appService.getApplicationsByCriteria(childPlat);
						if (childapp != null) {
							GroupUsers groupU = groupUService.comfirmIsJoinThisGroup(userInfo.getUserId(), childPlat);
							if (groupU == null) {// 用户没有加入该社区
								groupU = new GroupUsers();
								groupU.setGroupId(childPlat);
								groupU.setUserId(userInfo.getUserId());
								groupU.setJoinTime(now);
								groupU.setIsCreater(0);
								groupUService.UserJoinGroup(groupU);
								log.info("用户" + userInfo.getUserId() + "已加入社区" + childPlat);
							} else {
								log.info("用户" + userInfo.getUserId() + "已加入过社区" + childPlat);
							}
						} else {
							log.info("社区" + childPlat + "没有找到");
						}
					}
					output(response, JsonUtil.buildJson(returnl));// 返回插入后的用户信息
				} catch (Exception e) {
					log.info("微信用户" + userInfo.getNickName() + ",openid:" + userInfo.getOpenId() + "登录插入失败，失败原因" + e);
					output(response, JsonUtil.buildFalseJson("1", "用户登录失败"));
				}
			}
		}
	}

	@RequestMapping("/confirmStoppService")
	public void confirmThisServiceStopDays(HttpServletResponse response, Integer loginPlat, Integer userId) {
		ApplicationCommon appl = appService.getApplicationsByCriteria(loginPlat);
		Date now = new Date();
		long lastTime = appl.getPlanStopTime().getTime() - now.getTime();
		if (lastTime < 0) {// 该应用已过期
			if (userId == appl.getApplicationOwner()) {
				output(response, JsonUtil.buildFalseJson("-1", "该应用已过期，当前登录用户是小程序的拥有者!"));
			} else {
				output(response, JsonUtil.buildFalseJson("-2", "该应用已过期，当前登录用户是小程序的普通用户!"));
			}
		} else if (lastTime > 0 && lastTime <= 10 * 1000 * 24 * 3600) {
			if (userId == appl.getApplicationOwner()) {
				String times = betweenTimes(appl.getPlanStopTime(), now);
				output(response, JsonUtil.buildFalseJson("-3", times));// 应用拥有者，弹框提示剩余天数
			} else {
				output(response, JsonUtil.buildFalseJson("0", "该应用剩余时间不足10天,但该用户不是拥有者,可以正常使用!"));
			}
		} else {
			output(response, JsonUtil.buildFalseJson("0", "该应用剩余时间超过10天,可以正常使用!"));
		}
	}

	/**
	 * @author Administrator 认证信息
	 */
	public static class AuthInfo {
		public int userId;
		public int expires_in;
		public String openid;
		public String session_key;
	}

	public String betweenTimes(Date endDate, Date nowDate) {
		long nd = 1000 * 24 * 60 * 60;
		long nh = 1000 * 60 * 60;
		long nm = 1000 * 60;
		// long ns = 1000;
		// 获得两个时间的毫秒时间差异
		long diff = endDate.getTime() - nowDate.getTime();
		// 计算差多少天
		long day = diff / nd;
		// 计算差多少小时
		long hour = diff % nd / nh;
		// 计算差多少分钟
		long min = diff % nd % nh / nm;
		// 计算差多少秒//输出结果
		// long sec = diff % nd % nh % nm / ns;
		return day + "天" + hour + "小时" + min + "分钟";
	}

	public static Map<String, String> toMap(String tCode) {
		Map<String, String> rMap = new HashMap<String, String>();
		String[] stepOne = tCode.split("&");
		for (int i = 0; i < stepOne.length; i++) {
			String[] stepTwo = stepOne[i].split("=");
			if (rMap.get(stepTwo[0]) == null) {
				rMap.put(stepTwo[0], stepTwo[1]);
			} else {
				rMap.put(stepTwo[0], stepTwo[1] + "," + rMap.get(stepTwo[0]));
			}
		}
		return rMap;
	}

	/**
	 * 获取用户二维码:百享园测试
	 * 
	 * @param request
	 * @param response
	 * @param userId
	 */
	@RequestMapping("/getcode")
	public void getCode(Integer userId, HttpServletRequest request, HttpServletResponse response) {
		String codepath = "";
		if (userId != null) {
			try {
				UserCommon user = userComService.getUserByUserId(userId);
				if (user != null) {
					String appId = "wx474aed7d8ee915d9";
					String appSecret = "9b4dcb4fd032ae6cef2c25aae0aa1129";
					ApplicationCommon appinfo = appService.getApplicationById(user.getLoginPlat());
					if (appinfo.getAppId() != null && appinfo.getAppSecret() != null) {
						appId = appinfo.getAppId();
						appSecret = appinfo.getAppSecret();
					}
					String token = WeiXinSessionUtils.getAccessToken(appId, appSecret);
					String path = "/pages/index?introducer=" + userId;
					codepath = ErWeiCodeImageContoller.upload(token, path, request);
					UserCommon userCommon = new UserCommon();
					userCommon.setUserCodeUrl(codepath);
					userCommon.setUserId(userId);
					userComService.commonInfoUpdate(userCommon);
					output(response, JsonUtil.buildFalseJson("0", codepath));
				} else {
					log.error("系统找不到该用户");
					output(response, JsonUtil.buildFalseJson("1", "生成二维码失败"));
				}
			} catch (Exception e) {
				log.error("生成用户二维码失败,失败原因：" + e.getMessage());
				output(response, JsonUtil.buildFalseJson("1", "生成二维码失败"));
			}

		} else {
			log.error("系统找不到该用户");
			output(response, JsonUtil.buildFalseJson("1", "生成二维码失败"));
		}
	}

	/**
	 * 获取用户二维码:共享娱乐员
	 * 
	 * @param request
	 * @param response
	 * @param userId
	 */
	@RequestMapping("/getShareCode")
	public void getShareCode(Integer userId, HttpServletRequest request, HttpServletResponse response) {
		String codepath = "";
		if (userId != null) {
			try {
				UserCommon user = userComService.getUserByUserId(userId);
				if (user != null) {
					String appId = "wx90d383448c6398f7";
					String appSecret = "471940862356f7d6a16373d1cb372de0";
					ApplicationCommon appinfo = appService.getApplicationById(user.getLoginPlat());
					if (appinfo.getAppId() != null && appinfo.getAppSecret() != null) {
						appId = appinfo.getAppId();
						appSecret = appinfo.getAppSecret();
					}
					String token = WeiXinSessionUtils.getAccessToken(appId, appSecret);
					String path = "/pages/index/index?introducer=" + userId;
					codepath = ErWeiCodeImageContoller.upload(token, path, request);
					UserCommon userCommon = new UserCommon();
					userCommon.setUserCodeUrl(codepath);
					userCommon.setUserId(userId);
					userComService.commonInfoUpdate(userCommon);
					output(response, JsonUtil.buildFalseJson("0", codepath));
				} else {
					log.error("系统找不到该用户");
					output(response, JsonUtil.buildFalseJson("1", "生成二维码失败"));
				}
			} catch (Exception e) {
				log.error("生成用户二维码失败,失败原因：" + e.getMessage());
				output(response, JsonUtil.buildFalseJson("1", "生成二维码失败"));
			}

		} else {
			log.error("系统找不到该用户");
			output(response, JsonUtil.buildFalseJson("1", "生成二维码失败"));
		}
	}
	
}
