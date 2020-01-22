package com.cofc.controller.weiaijia;

import java.util.Date;
import java.util.Random;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.cofc.pojo.UserCommon;
import com.cofc.pojo.vij.SmsRecord;
import com.cofc.pojo.vij.SystemUpset;
import com.cofc.service.ApplicationCommonService;
import com.cofc.service.UserCommonService;
import com.cofc.service.vij.SmsRecordService;
import com.cofc.service.vij.SystemUpsetService;
import com.cofc.util.BaseUtil;
import com.cofc.util.JsonUtil;
import com.cofc.util.MD5Util;
import com.cofc.util.SendMsgUtil;

/**
 * 登陆注册相关类
 * 
 * @author 46678
 *
 */
@Controller
@RequestMapping("/vij/account")
public class VijAccountController extends BaseUtil {
	@Resource
	private UserCommonService userCommonService;
	@Resource
	private ApplicationCommonService applicationCommonService;// 小程序应用表
	@Resource
	private SmsRecordService smsRecordService;// 记录短信发送记录
	@Resource
	private SystemUpsetService systemUpsetService;
	public static Logger log = Logger.getLogger("VijAccountController");

	/**
	 * 登陆页面
	 * 
	 * @param request
	 * @param mv
	 * @return
	 */
	@RequestMapping("/login")
	public ModelAndView login(HttpServletRequest request, ModelAndView mv) {
		UserCommon user = (UserCommon) request.getSession().getAttribute("vijMallUserSession");
		if (user == null) { // 未登录
			SystemUpset systemUpset_1 = new SystemUpset();
			systemUpset_1.setAppId(709);
			//获取公共信息
			SystemUpset systemUpset = systemUpsetService.getInfoById(systemUpset_1);
			mv.addObject("systemUpset", systemUpset);
			mv.setViewName("vij/user_login");
		} else {// 已登陆
			mv.setViewName("vij/index");
		}
		return mv;
	}

	/**
	 * 注册页面
	 * 
	 * @param request
	 * @param mv
	 * @return
	 */
	@RequestMapping("register")
	public ModelAndView register(HttpServletRequest request, ModelAndView mv) {
		UserCommon user = (UserCommon) request.getSession().getAttribute("vijMallUserSession");
		if (user == null) { // 未登录
			SystemUpset systemUpset_1 = new SystemUpset();
			systemUpset_1.setAppId(709);
			//获取公共信息
			SystemUpset systemUpset = systemUpsetService.getInfoById(systemUpset_1);
			mv.addObject("systemUpset", systemUpset);
			mv.setViewName("vij/user_register");
		} else {// 已登陆
			mv.setViewName("vij/index");
		}
		return mv;
	}

	/**
	 * 执行登陆操作
	 * 
	 * @param request
	 * @param response
	 * @param account
	 *            账号
	 * @param password
	 *            密码
	 * @param LoginPlat
	 *            平台Id
	 */
	@RequestMapping("/dologin")
	public void dologin(HttpServletRequest request, HttpServletResponse response, String account, String password,
			Integer LoginPlat) {
		UserCommon user = (UserCommon) request.getSession().getAttribute("vijMallUserSession");
		if (user == null) {
			if (account == null || account.equals("")) {
				log.info("请输入账号");
				output(response, JsonUtil.buildFalseJson("1", "请输入账号"));
			} else {
				if (password == null || password.equals("")) {
					log.info("请输入密码");
					output(response, JsonUtil.buildFalseJson("1", "请输入密码"));
				} else {
					UserCommon userinfo = userCommonService.getUserByAccount(account, null, LoginPlat);
					if (userinfo == null) {
						log.info("该账号不存在,请重新输入");
						output(response, JsonUtil.buildFalseJson("1", "该账号不存在,请重新输入"));
					} else {
						if (userinfo.getPassword() == null
								|| !userinfo.getPassword().equals(MD5Util.MD5Encode(password, "utf-8"))) {
							log.info("密码不正确");
							output(response, JsonUtil.buildFalseJson("1", "密码不正确"));
						} else {
							// 登陆成功
							log.info("登陆成功");
							//更新登陆时间
							userinfo.setAccessTime(new Date());
							userCommonService.commonInfoUpdate(userinfo);
							request.getSession().setAttribute("vijMallUserSession", userinfo);
							output(response, JsonUtil.buildFalseJson("0", "登陆成功"));
						}
					}
				}
			}
		} else {
			log.info("你已登陆无需重新登陆");
			output(response, JsonUtil.buildFalseJson("2", "你已登陆无需重新登陆"));
		}

	}

	/**
	 * app登陆接口
	 * 
	 * @param response
	 * @param account
	 * @param password
	 * @param LoginPlat
	 */
	@RequestMapping("/doappLogin")
	public void doapplogin(HttpServletResponse response, String account, String password, Integer LoginPlat) {
		if (account == null || account.equals("")) {
			log.info("请输入账号");
			output(response, JsonUtil.buildFalseJson("1", "请输入账号"));
		} else {
			if (password == null || password.equals("")) {
				log.info("请输入密码");
				output(response, JsonUtil.buildFalseJson("1", "请输入密码"));
			} else {
				UserCommon userinfo = userCommonService.getUserByAccount(account, null, LoginPlat);
				if (userinfo == null) {
					log.info("该账号不存在,请重新输入");
					output(response, JsonUtil.buildFalseJson("1", "该账号不存在,请重新输入"));
				} else {
					if (userinfo.getPassword() == null
							|| !userinfo.getPassword().equals(MD5Util.MD5Encode(password, "utf-8"))) {
						log.info("密码不正确");
						output(response, JsonUtil.buildFalseJson("1", "密码不正确"));
					} else {
						// 登陆成功
						log.info("登陆成功");
						//更新登陆时间
						userinfo.setAccessTime(new Date());
						userCommonService.commonInfoUpdate(userinfo);
						output(response, JsonUtil.buildFalseJson("0", userinfo.getUserId().toString()));
					}
				}
			}
		}
	}

	/**
	 * 执行用户注册
	 * 
	 * @param request
	 * @param response
	 * @param user
	 */
	@RequestMapping("/doregister")
	public void doregister(HttpServletRequest request, HttpServletResponse response, UserCommon user,
			String confirmPwd) {
		UserCommon olduser = (UserCommon) request.getSession().getAttribute("vijMallUserSession");
		if (olduser == null) {
			if (user.getUserName() == null || user.getUserName() == "") {
				log.info("请输入账号");
				output(response, JsonUtil.buildFalseJson("1", "请输入账号"));
			} else {
				if (user.getPhone() == null || user.getPhone() == "") {
					log.info("请输入手机号码");
					output(response, JsonUtil.buildFalseJson("1", "请输入手机号码"));
				} else {
					if (user.getPassword() == null || user.getPassword().equals("")) {
						log.info("请输入密码");
						output(response, JsonUtil.buildFalseJson("1", "请输入密码"));
					} else {
						if (!user.getPassword().equals(confirmPwd)) {
							log.info("密码和确认密码不一致");
							output(response, JsonUtil.buildFalseJson("1", "密码和确认密码不一致"));
						} else {
							// 判断账号是否已存在
							int count = userCommonService.checkAccount(user.getUserName(), null, user.getLoginPlat());
							if (count > 0) {
								log.info("账号已经注册,请换一个账号");
								output(response, JsonUtil.buildFalseJson("1", "账号已经注册,请换一个账号"));
							} else {
								int samecount = userCommonService.checkAccount(null, user.getPhone(),
										user.getLoginPlat());
								if (samecount > 0) {
									log.info("手机号码已存注册,请换一个手机号码");
									output(response, JsonUtil.buildFalseJson("1", "手机号码已存注册,请换一个手机号码"));
								} else {
									user.setRegSource(1);
									user.setCreateTime(new Date());
									user.setPassword(MD5Util.MD5Encode(user.getPassword(), "utf-8"));
									userCommonService.addNewUserCommon(user);
									UserCommon userinfo = userCommonService.getUserByUserId(user.getUserId());
									request.getSession().setAttribute("vijMallUserSession", userinfo);
									log.info("注册成功");
									output(response, JsonUtil.buildFalseJson("0", user.getUserName()));
								}
							}
						}
					}
				}
			}
		} else {
			log.info("已有账号登陆,请直接登陆或退出后在重新注册");
			output(response, JsonUtil.buildFalseJson("2", "已有账号登陆,请直接登陆或退出后在重新注册"));
		}

	}

	/**
	 * app:注册
	 * 
	 * @param response
	 * @param user
	 * @param confirmPwd
	 */
	@RequestMapping("/doappregister")
	public void doappregister(HttpServletResponse response, UserCommon user, String confirmPwd, String code) {
//		if (user.getUserName() == null || user.getUserName().equals("")) {
//			log.info("请输入账号");
//			output(response, JsonUtil.buildFalseJson("1", "请输入账号"));
//		} else {
			if (user.getPhone() == null || user.getPhone().equals("")) {
				log.info("请输入手机号码");
				output(response, JsonUtil.buildFalseJson("1", "请输入手机号码"));
			} else {
				if (code == null || code.equals("")) {
					output(response, JsonUtil.buildFalseJson("1", "验证码不能为空"));
					return;
				}
				if (user.getPassword() == null || user.getPassword().equals("")) {
					log.info("请输入密码");
					output(response, JsonUtil.buildFalseJson("1", "请输入密码"));
				} else {
					if (!user.getPassword().equals(confirmPwd)) {
						log.info("密码和确认密码不一致");
						output(response, JsonUtil.buildFalseJson("1", "密码和确认密码不一致"));
					} else {
						/* 注册检测验证码和手机开始 */
						SmsRecord record = smsRecordService.getSms(user.getPhone(), 1, user.getLoginPlat(), code);
						if (record != null) {
							long nowDate = new Date().getTime() / 1000;
							long time = nowDate - record.getSendTime();
							if (time > 180) { // 重新发送
								output(response, JsonUtil.buildFalseJson("1", "验证码已过期请重新获取"));
							} else {
								// 判断账号是否已存在
//								int count = userCommonService.checkAccount(user.getUserName(), null,
//										user.getLoginPlat());
//								if (count > 0) {
//									log.info("账号已存在,请换一个");
//									output(response, JsonUtil.buildFalseJson("1", "账号已存在,请换一个"));
//								} else {
									int samecount = userCommonService.checkAccount(null, user.getPhone(),
											user.getLoginPlat());
									if (samecount > 0) {
										log.info("手机号码已存在,请换一个");
										output(response, JsonUtil.buildFalseJson("1", "手机号码已存在,请换一个"));
									} else {
										user.setNickName("vij"+getUUId());
										user.setUserName("vij"+getUUId());
										user.setCreateTime(new Date());
										user.setPassword(MD5Util.MD5Encode(user.getPassword(), "utf-8"));
										userCommonService.addNewUserCommon(user);
										log.info("注册成功");
										output(response, JsonUtil.buildFalseJson("0", user.getUserId().toString()));
									}
								}
							//}
						} else {
							output(response, JsonUtil.buildFalseJson("1", "验证码不正确或手机和验证码不匹配"));
						}
						/* 注册检测验证码和手机结束 */
					}
				}
			}
		//}
	}

	/**
	 * app:注册
	 * 
	 * @param response
	 * @param user
	 * @param confirmPwd
	 */
	@RequestMapping("/donewappregister")
	public void donewappregister(HttpServletResponse response, UserCommon user, String code) {
		if (user.getPhone() == null || user.getPhone().equals("")) {
			log.info("请输入手机号码");
			output(response, JsonUtil.buildFalseJson("1", "请输入手机号码"));
		} else {
			if (code == null || code.equals("")) {
				output(response, JsonUtil.buildFalseJson("1", "验证码不能为空"));
				return;
			}
			/* 注册检测验证码和手机开始 */
			SmsRecord record = smsRecordService.getSms(user.getPhone(), 1, user.getLoginPlat(), code);
			if (record != null) {
				long nowDate = new Date().getTime() / 1000;
				long time = nowDate - record.getSendTime();
				if (time > 180) { // 重新发送
					output(response, JsonUtil.buildFalseJson("1", "验证码已过期请重新获取"));
				} else {
					// 判断账号是否已存在
					int count = userCommonService.checkAccount(null, user.getPhone(), user.getLoginPlat());
					if (count > 0) {
						log.info("账号已存在,请换一个");
						output(response, JsonUtil.buildFalseJson("1", "账号已存在,请换一个"));
					} else {
						
						user.setNickName("vij"+getUUId());
						user.setCreateTime(new Date());
						userCommonService.addNewUserCommon(user);
						log.info("注册成功");
						output(response, JsonUtil.newBuildFalseJson("0","注册成功,请设置密码",user.getUserId().toString()));
					
					}
				}
			} else {
				output(response, JsonUtil.buildFalseJson("1", "验证码不正确或手机和验证码不匹配"));
			}
			/* 注册检测验证码和手机结束 */
		}
	}

	/**
	 * 退出系统
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/outlogin")
	public void outlogin(HttpServletRequest request, HttpServletResponse response) {
		UserCommon olduser = (UserCommon) request.getSession().getAttribute("vijMallUserSession");
		if (olduser == null) {
			log.info("你还未登陆");
			output(response, JsonUtil.buildFalseJson("1", "你还未登陆"));
		} else {
			log.info("退出成功");
			request.getSession().removeAttribute("vijMallUserSession");
			output(response, JsonUtil.buildFalseJson("0", "退出成功"));
		}
	}

	/**
	 * 发送短信验证码
	 * 
	 * @param request
	 * @param response
	 * @param phone
	 * @param type:
	 *            1 注册 2：忘记密码 3 ：绑定手机号码
	 */
	@RequestMapping("/sendmsg")
	public void sendmsg(HttpServletRequest request, HttpServletResponse response, String phone, Integer loginPlat,
			int type) {
		if (phone == null || phone.equals("")) {
			output(response, JsonUtil.buildFalseJson("1", "请输入合法的手机号码"));
		} else {
			if (type == 1) { // 注册
				int count = userCommonService.checkAccount(null, phone, loginPlat);
				if (count > 0) {
					output(response, JsonUtil.buildFalseJson("1", "该手机号码已存在"));
					return;
				}
			} else if (type == 2) {
				int count = userCommonService.checkAccount(null, phone, loginPlat);
				if (count == 0) {
					output(response, JsonUtil.buildFalseJson("1", "该手机号码未注册"));
					return;
				}
			}
			SmsRecord record = smsRecordService.getSms(phone, type, loginPlat, null);
			if (record != null) {
				long nowDate = new Date().getTime() / 1000;
				long time = nowDate - record.getSendTime();
				if (time > 180) { // 重新发送
					String code = randSixNumber();
					String errorCode = SendMsgUtil.sendCode(phone, code);
					SmsRecord r = new SmsRecord();
					r.setAppId(loginPlat);
					r.setCode(code);
					long sendTime = new Date().getTime() / 1000;
					r.setSendTime((int) sendTime);
					r.setErrorCode(errorCode);
					if (type == 3) {
						r.setType(1);
					} else {
						r.setType(type);
					}
					r.setPhone(phone);
					smsRecordService.addSms(r);
					if (errorCode.equals("0")) {
						output(response, JsonUtil.buildFalseJson("0", "发送验证码成功"));
					} else {
						output(response, JsonUtil.buildFalseJson("1", "发送验证码失败"));
					}
				} else {
					output(response, JsonUtil.buildFalseJson("1", "验证码3分钟内有效,请勿频繁操作"));
				}
			} else {// 直接发送
				String code = randSixNumber();
				String errorCode = SendMsgUtil.sendCode(phone, code);
				SmsRecord r = new SmsRecord();
				r.setAppId(loginPlat);
				r.setCode(code);
				long sendTime = new Date().getTime() / 1000;
				r.setSendTime((int) sendTime);
				r.setErrorCode(errorCode);
				if (type == 3) {
					r.setType(1);
				} else {
					r.setType(type);
				}
				r.setPhone(phone);
				smsRecordService.addSms(r);
				if (errorCode.equals("0")) {
					output(response, JsonUtil.buildFalseJson("0", "发送验证码成功"));
				} else {
					output(response, JsonUtil.buildFalseJson("1", "发送验证码失败"));
				}
			}
		}
	}

	/**
	 * 检测验证码是否正确
	 * 
	 * @param response
	 * @param phone
	 * @param code
	 */
	@RequestMapping("/checkCode")
	public void checkCode(HttpServletResponse response, String phone, String code, Integer type, Integer loginPlat) {
		if (phone == null || phone.equals("")) {
			output(response, JsonUtil.buildFalseJson("1", "参数非法"));
		} else {
			if (code == null || code.equals("")) {
				output(response, JsonUtil.buildFalseJson("1", "验证码不能为空"));
			} else {
				SmsRecord record = smsRecordService.getSms(phone, type, loginPlat, code);
				if (record != null) {
					long nowDate = new Date().getTime() / 1000;
					long time = nowDate - record.getSendTime();
					if (time > 180) { // 重新发送
						output(response, JsonUtil.buildFalseJson("1", "验证码已过期请重新获取"));
					} else {
						if (type == 2) {
							UserCommon user = userCommonService.getUserByPhone(phone);
							output(response, JsonUtil.buildFalseJson("0", user.getUserName()));
						} else {
							output(response, JsonUtil.buildFalseJson("0", "验证码正确"));
						}
					}
				} else {
					output(response, JsonUtil.buildFalseJson("1", "您输入的验证码不正确"));
				}
			}
		}
	}

	/**
	 * 随机生成六位数的验证码
	 * 
	 * @return
	 */
	public static String randSixNumber() {
		Random random = new Random();
		String result = "";
		for (int i = 0; i < 6; i++) {
			result += random.nextInt(10);
		}
		return result;
	}

	/************************ 第三方登陆 *****************************/

	/**
	 * QQ登陆 :APP
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/thirdlogin")
	public void thirdlogin(HttpServletResponse response, String openId, Integer loginPlat, Integer type) {
		if (openId == null || openId.equals("")) {
			output(response, JsonUtil.buildFalseJson("1", "授权失败"));
		} else {
			if (loginPlat == null) {
				output(response, JsonUtil.buildFalseJson("1", "未识别的应用"));
			} else {
				UserCommon info = null;
				if (type == 1) {// QQ登陆
					info = userCommonService.getUserInfoByQQOrWXOpenId(openId, null, loginPlat);
				} else {
					info = userCommonService.getUserInfoByQQOrWXOpenId(null, openId, loginPlat);
				}
				if (info != null) {// 登陆成功
					//更新登陆时间
					info.setAccessTime(new Date());
					userCommonService.commonInfoUpdate(info);
					output(response, JsonUtil.buildFalseJson("0", info.getUserId().toString()));
				} else { // 去注册
					output(response, JsonUtil.buildFalseJson("2", "授权成功,去绑定手机号码"));
				}
			}
		}
	}

	/**
	 * 微信登陆：APP
	 * 
	 * @param response
	 * @param openId
	 */
	@RequestMapping("/wxlogin")
	public void wxlogin(HttpServletResponse response, String openId) {

	}

	/**
	 * 微博登陆 ：APP
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/weibologin")
	public void weibologin(HttpServletRequest request, HttpServletResponse response) {

	}

	/**
	 * 修改密码
	 * 
	 * @param request
	 * @param response
	 * @param oldPwd
	 * @param newPwd
	 * @param confirmPwd
	 */
	@RequestMapping("/updatePwd")
	public void updatePwd(HttpServletRequest request, HttpServletResponse response, String oldPwd, String newPwd,
			String confirmPwd) {
		UserCommon user = (UserCommon) request.getSession().getAttribute("vijMallUserSession");
		if (user == null) {
			log.info("请先登录");
			output(response, JsonUtil.buildFalseJson("1", "请先登录"));
		} else {
			if (oldPwd.equals("")) {
				log.info("原始密码不能为空");
				output(response, JsonUtil.buildFalseJson("1", "原始密码不能为空"));
			} else {
				String md5oldPwd = MD5Util.MD5Encode(oldPwd, "utf-8");
				if (!md5oldPwd.equals(user.getPassword())) {
					log.info("原始密码不正确");
					output(response, JsonUtil.buildFalseJson("1", "原始密码不正确"));
				} else {
					if (newPwd.equals("")) {
						log.info("新密码不能为空");
						output(response, JsonUtil.buildFalseJson("1", "新密码不能为空"));
					} else {
						if (!newPwd.equals(confirmPwd)) {
							log.info("两次密码不一致");
							output(response, JsonUtil.buildFalseJson("1", "两次密码不一致"));
						} else {
							// 执行修改
							UserCommon info = new UserCommon();
							info.setUserId(user.getUserId());
							info.setPassword(MD5Util.MD5Encode(newPwd, "utf-8"));
							userCommonService.commonInfoUpdate(info);
							log.info("修改密码成功");
							output(response, JsonUtil.buildFalseJson("0", "修改密码成功"));
						}
					}
				}
			}
		}
	}

	/**
	 * 
	 * 找回密码页面
	 * 
	 * @param request
	 * @param mv
	 * @return
	 */
	@RequestMapping("/forgetPassword")
	public ModelAndView forgetPassword(HttpServletRequest request, ModelAndView mv) {
		UserCommon user = (UserCommon) request.getSession().getAttribute("vijMallUserSession");
		if (user == null) { // 未登录
			mv.setViewName("vij/forget_password");
		} else {// 已登陆
			mv.setViewName("vij/index");
		}
		return mv;
	}

	@RequestMapping("/findPwd")
	public void findPwd(HttpServletRequest request, HttpServletResponse response, String newPwd, String phone,
			Integer loginPlat) {
		if (phone.equals("")) {
			output(response, JsonUtil.buildFalseJson("1", "手机号码不能为空"));
		} else {
			if (newPwd.equals("")) {
				log.info("请输入新密码");
				output(response, JsonUtil.buildFalseJson("1", "请输入新密码"));
			} else {
				UserCommon user = userCommonService.findPwd(phone, loginPlat);
				if (user == null) {
					output(response, JsonUtil.buildFalseJson("1", "你还未注册,请注册"));
				} else {
					UserCommon info = new UserCommon();
					info.setPassword(MD5Util.MD5Encode(newPwd, "utf-8"));
					info.setUserId(user.getUserId());
					userCommonService.commonInfoUpdate(info);
					log.info("重设密码成功");
					output(response, JsonUtil.buildFalseJson("0", "重设密码成功"));
				}
			}
		}
	}

	/**
	 * app找回密码
	 * 
	 * @param response
	 * @param phone
	 * @param code
	 * @param newPwd
	 */
	@RequestMapping("/appfindPwd")
	public void appFindPwd(HttpServletResponse response, String phone, String code, String newPwd, Integer loginPlat) {
		if (phone == null || phone.equals("")) {
			output(response, JsonUtil.buildFalseJson("1", "参数非法"));
		} else {
			if (code == null || code.equals("")) {
				output(response, JsonUtil.buildFalseJson("1", "验证码不能为空"));
			} else {
				SmsRecord record = smsRecordService.getSms(phone, 2, loginPlat, code);
				if (record != null) {
					long nowDate = new Date().getTime() / 1000;
					long time = nowDate - record.getSendTime();
					if (time > 180) { // 重新发送
						output(response, JsonUtil.buildFalseJson("1", "验证码已过期请重新获取"));
					} else {
						if (newPwd.equals("")) {
							log.info("请输入新密码");
							output(response, JsonUtil.buildFalseJson("1", "请输入新密码"));
						} else {
							UserCommon user = userCommonService.findPwd(phone, loginPlat);
							if (user == null) {
								output(response, JsonUtil.buildFalseJson("1", "你还未注册,请注册"));
							} else {
								UserCommon info = new UserCommon();
								info.setPassword(MD5Util.MD5Encode(newPwd, "utf-8"));
								info.setUserId(user.getUserId());
								userCommonService.commonInfoUpdate(info);
								log.info("重设密码成功");
								output(response, JsonUtil.buildFalseJson("0", "重设密码成功"));
							}
						}
					}
				} else {
					output(response, JsonUtil.buildFalseJson("1", "您输入的验证码不正确"));
				}
			}
		}
	}

	/*********************************** 多端统一 ***************************************/
	/**
	 * 绑定账号
	 * 
	 * @param response
	 * @param phone
	 * @param code
	 * @Param type:1 qq 2 微信
	 * 
	 */
	@RequestMapping("/bangdingAccount")
	public void bangdingAccount(HttpServletResponse response, String code, UserCommon user, Integer type,
			String thirdOpenId) {
		if (user.getPhone() == null || user.getPhone().equals("")) {
			log.info("bangdingAccount():手机号码不能为空");
			output(response, JsonUtil.buildFalseJson("1", "手机号码不能为空"));
		} else {
			if (code == null || code.equals("")) {
				log.info("bangdingAccount():验证码不能为空");
				output(response, JsonUtil.buildFalseJson("1", "验证码不能为空"));
			} else {
				if (thirdOpenId == null || thirdOpenId.equals("")) {
					if (type == 1) {
						log.info("bangdingAccount():QQ授权失败");
						output(response, JsonUtil.buildFalseJson("1", "QQ授权失败"));
						return;
					} else {
						log.info("bangdingAccount():微信授权失败");
						output(response, JsonUtil.buildFalseJson("1", "微信授权失败"));
						return;
					}
				}
				/* 注册检测验证码和手机开始 */
				SmsRecord record = smsRecordService.getSms(user.getPhone(), 1, user.getLoginPlat(), code);
				if (record != null) {
					long nowDate = new Date().getTime() / 1000;
					long time = nowDate - record.getSendTime();
					if (time > 180) { // 重新发送
						log.info("bangdingAccount():1 验证码已过期请重新获取");
						output(response, JsonUtil.buildFalseJson("1", "验证码已过期请重新获取"));
					} else {
						// 判断账号是否已存在
						UserCommon info = userCommonService.findPwd(user.getPhone(), user.getLoginPlat());
						if (info == null) {// 注册
							if (type == 1) {
								user.setQqOpenId(thirdOpenId);
							} else {
								user.setWxOpenId(thirdOpenId);
							}
							user.setRegSource(3);
							user.setCreateTime(new Date());
							user.setIntegral(0);
							user.setWalletBalance(0.00);
							userCommonService.addNewUserCommon(user);
							log.info("bangdingAccount():02 添加成功,请设置密码");
							output(response,JsonUtil.newBuildFalseJson("2", "添加成功,请设置密码", user.getUserId().toString()));
						} else {// 绑定
							UserCommon uinfo = new UserCommon();
							if (type == 1) {
								uinfo.setQqOpenId(thirdOpenId);
							} else {
								uinfo.setWxOpenId(thirdOpenId);
							}
							uinfo.setHeadImage(user.getHeadImage());
							uinfo.setUserId(info.getUserId());
							uinfo.setNickName(user.getNickName());
							uinfo.setUserSex(user.getUserSex());
							userCommonService.commonInfoUpdate(uinfo);
							if (info.getPassword() == null || info.getPassword().equals("")) {// 需要跳转到设置密码页面
								log.info("bangdingAccount():12 绑定成功,请设置密码");
								output(response,JsonUtil.newBuildFalseJson("2", "绑定成功,请设置密码", uinfo.getUserId().toString()));
							} else {
								log.info("bangdingAccount():0 绑定成功");
								output(response, JsonUtil.newBuildFalseJson("0", "绑定成功", uinfo.getUserId().toString()));
							}
						}
					}
				} else {
					output(response, JsonUtil.buildFalseJson("1", "验证码不正确或手机和验证码不匹配"));
				}
				/* 注册检测验证码和手机结束 */
			}
		}
	}

	/**
	 * 绑定设置密码
	 * 
	 * @param response
	 * @param user
	 */
	@RequestMapping("/bindSetPwd")
	public void bindSetPwd(HttpServletResponse response, UserCommon user, String repassword) {
		if (user.getUserId() == null) {
			output(response, JsonUtil.buildFalseJson("1", "非法用户"));
		} else {
			if (user.getPassword() == null || user.getPassword().equals("")) {
				output(response, JsonUtil.buildFalseJson("1", "密码不能为空"));
			} else {
				String password = user.getPassword();
				if (!password.equals(repassword)) {
					output(response, JsonUtil.buildFalseJson("1", "两次密码不一致"));
				} else {
					user.setPassword(MD5Util.MD5Encode(password, "utf-8"));
					userCommonService.commonInfoUpdate(user);
					if (user.getUserId() == null) {
						output(response, JsonUtil.buildFalseJson("1", "设置密码失败"));
					} else {
						output(response, JsonUtil.newBuildFalseJson("0", "设置密码成功", user.getUserId().toString()));
					}
				}
			}
		}
	}
	
	/**
	 * 换绑手机：手机是没有注册过的新的
	 * @param response
	 * @param request
	 * @param phone
	 */
	@RequestMapping("/changePhone")
	public void changePhone(HttpServletResponse response,HttpServletRequest request,
			String phone,String code,Integer loginPlat,Integer userId){
		if(userId == null){
			output(response, JsonUtil.buildFalseJson("1", "请先登录"));
		}else{
			if(phone == null || phone.equals("")){
				output(response, JsonUtil.buildFalseJson("1", "手机号码不能为空"));
			}else{
				if(code == null || code.equals("")){
					output(response, JsonUtil.buildFalseJson("1", "验证码不能为空"));
				}else{
					if(loginPlat == null){
						output(response,JsonUtil.buildFalseJson("1", "非法应用"));
					}else{
						SmsRecord record = smsRecordService.getSms(phone,1, loginPlat, code);
						if(record == null){
							output(response,JsonUtil.buildFalseJson("1", "验证码与手机号码不匹配"));
						}else{
							long nowDate = new Date().getTime() / 1000;
							long time = nowDate - record.getSendTime();
							if (time > 180) { // 重新发送
								log.info("changePhone():1 验证码已过期请重新获取");
								output(response, JsonUtil.buildFalseJson("1", "验证码已过期请重新获取"));
							} else {
								UserCommon user = new UserCommon();
								user.setUserId(userId);
								user.setPhone(phone);
								userCommonService.commonInfoUpdate(user);
								output(response,JsonUtil.buildFalseJson("0", "操作成功"));
							}
						}
					}
	
				}
			}
		}
	}

	/**
	 * uuid 生成订单号
	 * 
	 * @return
	 */
	public static String getUUId() {
		int machineId = 1;// 最大支持1-9个集群机器部署
		int hashCodeV = UUID.randomUUID().toString().hashCode();
		if (hashCodeV < 0) {// 有可能是负数
			hashCodeV = -hashCodeV;
		}
		// 0 代表前面补充0
		// 4 代表长度为4
		// d 代表参数为正数型
		return machineId + String.format("%09d", hashCodeV);
	}
	public static void main(String[] args) {
		System.out.println(JsonUtil.newBuildFalseJson("0", "aaa", "1"));
	}
}
