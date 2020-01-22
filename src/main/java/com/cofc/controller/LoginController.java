package com.cofc.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cofc.pojo.ApplicationCommon;
import com.cofc.pojo.BackRole;
import com.cofc.pojo.BackUser;
import com.cofc.pojo.UserRole;
import com.cofc.service.ApplicationCommonService;
import com.cofc.service.BackRoleService;
import com.cofc.service.BackUserService;
import com.cofc.service.UserRoleService;
import com.cofc.util.BaseUtil;
import com.cofc.util.JsonUtil;
import com.cofc.util.MD5Util;

@Controller
@RequestMapping("/")
public class LoginController extends BaseUtil {
	@Resource
	private BackUserService bUserService;
	@Resource
	private UserRoleService userRoleService;
	@Resource
	private BackRoleService backRoleService;
	@Resource
	private ApplicationCommonService applicationService;
	@RequestMapping("/login")
	public ModelAndView adminUserLogin(HttpServletRequest request, ModelAndView modelView, BackUser bu)
			throws IOException {
		BackUser loginedUser = (BackUser) request.getSession().getAttribute("loginer");
		if (loginedUser == null) {
			if (bu.getUserName() == null || "".equals(bu.getUserName().trim()) || bu.getPassword() == null
					|| "".equals(bu.getPassword().trim())) {
				modelView.setViewName("../../login");
				modelView.addObject("errorMsg", "用户名和密码不能为空!");
			} else {
				BackUser loginer = bUserService.getUserByUserName(bu.getUserName());
				if (loginer == null) {// 该用户不存在
					modelView.setViewName("../../login");
					modelView.addObject("errorMsg", "该用户不存在");
				} else {// 用户存在,则判断密码
					UserRole userRole = userRoleService.getUserRoleById(loginer.getUserId());
					BackRole role = backRoleService.getRoleStringById(userRole.getRoleId());
					if (!loginer.getPassword().equals(MD5Util.MD5Encode(bu.getPassword(), "utf-8"))) {
						modelView.setViewName("../../login");
						modelView.addObject("errorMsg", "密码不正确");
					} else {
					   if(loginer.getIsEffect() == 0 || loginer.getIsEffect() == null){
						  modelView.setViewName("../../login");
					      modelView.addObject("errorMsg", "该账号被禁用,请联系管理员");
					   }else{
						    loginer.setRoleName(role.getRoleName());
							request.getSession().setAttribute("loginer", loginer);
							if(loginer.getLoginPlat() == null || loginer.getLoginPlat().equals("")){
								modelView.addObject("appName", "飞看云平台");
							}else{
								String[] idStrings = loginer.getLoginPlat().split(",");
								List<String> loginPlatList = Arrays.asList(idStrings);
								List<ApplicationCommon> appList = applicationService.getNewApplicationList(loginPlatList);
								modelView.addObject("app", appList.get(0));
								
							}
							modelView.setViewName("../../index"); 
					   }						
					}
				}
			}
		} else {			
			if(loginedUser.getLoginPlat() == null || loginedUser.getLoginPlat().equals("")){
				modelView.addObject("appName", "飞看云平台");
			}else{
				String[] idStrings = loginedUser.getLoginPlat().split(",");
				List<String> loginPlatList = Arrays.asList(idStrings);
				List<ApplicationCommon> appList = applicationService.getNewApplicationList(loginPlatList);
				modelView.addObject("appName", appList.get(0).getApplicationName());
			}
			modelView.setViewName("../../index");
		}
		
		return modelView;
	}

	@RequestMapping("/logout")
	public void adminUserLogout(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.getSession().invalidate();
		String path = request.getContextPath();
		String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path
				+ "/";
		response.sendRedirect(basePath);
	}

	// 修改密码
	@RequestMapping("/updatePassword")
	public void updatePassword(HttpServletResponse response, String password, String confirmPassword,
			HttpServletRequest request, String modifyPassword) {
		BackUser bu = (BackUser) request.getSession().getAttribute("loginer");
		BackUser user = bUserService.getBackUserById(bu.getUserId());
		if (user == null) {
			output(response, JsonUtil.buildFalseJson("1", "该用户不存在"));
		} else {
			if (MD5Util.MD5Encode(password, "utf-8").equals(user.getPassword())
					&& confirmPassword.equals(modifyPassword)) {
				bUserService.updatePassword(user.getUserId(), MD5Util.MD5Encode(modifyPassword, "utf-8"));
				output(response, JsonUtil.buildFalseJson("0", "修改密码成功,请牢记你的密码!"));

			} else if (!MD5Util.MD5Encode(password, "utf-8").equals(user.getPassword())) {

				output(response, JsonUtil.buildFalseJson("2", "原始密码输入错误，请重新输入!"));
			} else {
				output(response, JsonUtil.buildFalseJson("3", "确认密码与和新密码不匹配"));
			}
		}
	}

}
