package com.cofc.controller.user;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cofc.pojo.ApplicationCommon;
import com.cofc.pojo.BackUser;
import com.cofc.pojo.OperateLog;
import com.cofc.pojo.UserCommon;
import com.cofc.pojo.UserOrder;
import com.cofc.pojo.UserRole;
import com.cofc.service.ApplicationCommonService;
import com.cofc.service.GroupUsersService;
import com.cofc.service.OperateLogService;
import com.cofc.service.UserBackuserRelationService;
import com.cofc.service.UserCommonService;
import com.cofc.service.UserOrderService;
import com.cofc.service.UserRoleService;
import com.cofc.util.BaseUtil;
import com.cofc.util.JsonUtil;

/**
 * 后台用户相关接口
 * 
 * @author chen
 *
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseUtil {
	@Resource
	private UserCommonService usercService;
	@Resource
	private ApplicationCommonService applicationService;
	@Resource
	private GroupUsersService guserService;
	@Resource
	private UserRoleService userRoleService;
	@Resource
	private UserBackuserRelationService userBackService;
	@Resource
	private OperateLogService logService;
	@Resource
	private UserOrderService orderService;

	public static Logger log = Logger.getLogger("UserController");

	// 跳转后台用户列表
	@RequestMapping("/toUserList")
	public ModelAndView toShowUserList(ModelAndView modelView, HttpServletRequest request) throws ParseException {
		BackUser bu = (BackUser) request.getSession().getAttribute("loginer");
		UserRole userRole = userRoleService.getUserRoleById(bu.getUserId());
		String[] rolesarr = userRole.getRoleId().split(",");
		boolean isSuperm = false;
		for (String role : rolesarr) {
			while ("1".equals(role)) {
				isSuperm = true;
				break;
			}
		}
		List<ApplicationCommon> appList = null;
		if (bu.getLoginPlat() == null || bu.getLoginPlat().equals("")) {
			appList = applicationService.getNewApplicationList(null);
		} else {
			String[] idStrings = bu.getLoginPlat().split(",");
			List<String> loginPlatList = Arrays.asList(idStrings);
			appList = applicationService.getApplicationByLoginPlat(loginPlatList);
		}
		log.info("进入后台用户列表的jsp页面");
		//List<UserCommon> userList = usercService.getUserIdList();
		modelView.addObject("loginPlat", bu.getLoginPlat());// 传给前台模板：空值显示百享园，不空显示相对应的
		modelView.addObject("appList", appList);
		//modelView.addObject("userList", userList);
		modelView.setViewName("userManage/userList");
		return modelView;
	}

	// 跳转后台管理员列表
	@RequestMapping("/toManagerList")
	public ModelAndView toShowManagerList(ModelAndView modelView) throws ParseException {
		log.info("进入后台管理员列表的jsp页面");
		modelView.setViewName("userManage/managerList");
		return modelView;
	}

	// $.ajax查询用户列表
	@RequestMapping("/showUserList")
	public void showUserList(HttpServletRequest request, HttpServletResponse response, UserCommon user,
			Integer userStatus, String dataRange, Integer page, Integer limit, Integer loginPlat1)
					throws ParseException {
		BackUser bu = (BackUser) request.getSession().getAttribute("loginer"); // 获取当前登陆用户
		if (page == null || page == 0) {
			page = 1;
		}
		if (limit == null || limit == 0) {
			limit = 20;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat startSdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		SimpleDateFormat endSdf = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
		String startTime = null;
		String endTime = null;
		if (dataRange != null && !dataRange.equals("")) {
			String[] myData = dataRange.split(" -| ");
			startTime = myData[0];
			endTime = myData[2];
		}
		if (startTime != null) {
			startTime = startSdf.format(sdf.parse(startTime));
		}
		if (endTime != null) {
			endTime = endSdf.format(sdf.parse(endTime));
		}
		List<UserCommon> userList = new ArrayList<UserCommon>();
		int rowsCount = 0;
		if (bu.getLoginPlat() == null || bu.getLoginPlat().equals("")) { // 百享园
			ApplicationCommon currapp = null;
			//UserCommon common = usercService.getUserByUserId(user.getIntroducer());
//			if (common != null) {
//				user.setIntroducer(common.getUserId());
//			}
			if (user.getLoginPlat() != null) {
				currapp = applicationService.getApplicationById(user.getLoginPlat());
				if (currapp.getParentId() != null) {// 说明是某个应用下的子应用，用户列表则在group_users中
					rowsCount = guserService.getGroupUserCount(user.getLoginPlat());
					userList = guserService.backFindGroupUsersList(user, startTime, endTime, (page - 1) * limit, limit);
				} else {
					rowsCount = usercService.getCountUser(user, userStatus, startTime, endTime, loginPlat1);
					userList = usercService.findUserList(user, startTime, endTime, (page - 1) * limit, limit,
							loginPlat1);
				}
			} else {
				rowsCount = usercService.getCountUser(user, userStatus, startTime, endTime, loginPlat1);
				userList = usercService.findUserList(user, startTime, endTime, (page - 1) * limit, limit, loginPlat1);
			}
		} else { // 增量开发：管理别平台用户
			if (user.getLoginPlat() == null || user.getLoginPlat().equals("")) {
				String[] idStrings = bu.getLoginPlat().split(",");
				List<String> loginPlatList = Arrays.asList(idStrings);
				rowsCount = usercService.getUserCountByLoginPlat(loginPlatList, user, userStatus, startTime, endTime);
				userList = usercService.getUserListByLoginPlat(loginPlatList, user, userStatus, startTime, endTime,
						(page - 1) * limit, limit);
			} else {
				// 搜索单应用用的
				rowsCount = usercService.getCountUser(user, userStatus, startTime, endTime, null);
				userList = usercService.findUserList(user, startTime, endTime, (page - 1) * limit, limit, null);
			}

		}
		output(response, JsonUtil.buildJsonByTotalCount(userList, rowsCount));
	}

	@RequestMapping("/userDetails")
	public ModelAndView showUserDetails(HttpServletRequest request, ModelAndView modelView, Integer userId,
			Integer pageNo) {
		int loginPlat = 1;
		if (loginPlat == 1) {
			UserCommon user = usercService.getUserByUserId(userId);
			log.info("积分" + user.getIntegral());
			// UserCommon currUser = usercService.getUserByUserId(userId);
			String imgHtml = "";
			if (user.getCardImage() != null) {
				String[] imgArrray = user.getCardImage().split(",");
				for (String img : imgArrray) {
					imgHtml += "<img src='" + img + "' style='width:640px;height:320px;'/>";
				}
				if (imgHtml != "") {
					imgHtml += "<div style='font-size:30px;margin-top:5px;width:640px'><a href='../download.do?files="
							+ user.getCardImage() + "&nickName=" + user.getNickName() + "&realName="
							+ user.getRealName() + "' style='text-decoration:underline'>全部下载</a></div>";
				}
			}
			modelView.setViewName("userManage/userDetails");
			modelView.addObject("currUser", user);
			modelView.addObject("page", pageNo);
			modelView.addObject("imgHtml", imgHtml);
		}
		return modelView;
	}

	@RequestMapping("/deleteUser")
	public String deleteUser(HttpServletResponse response, String userIds) {
		String[] ids = userIds.split(",");
		List idList = Arrays.asList(ids);
		usercService.userUndercarriage(idList);
		return "redirect:./toUserList.do";
	}

	@RequestMapping("/recoveryUser")
	public String recoveryUser(HttpServletResponse response, String userIds) {
		String[] ids = userIds.split(",");
		List idList = Arrays.asList(ids);
		usercService.userRecovery(idList);
		return "redirect:./toUserList.do";
	}

	@RequestMapping("/updateStatus")
	public String updateUserStatus(HttpServletResponse response, Integer userId, Integer userStatus, Integer pageNo) {

		usercService.changeUserStatus(userId, userStatus);
		return "redirect:./toUserList.do?pageNo=" + pageNo;
	}

	@RequestMapping("/renzhenUser")
	public String renzhenUser(HttpServletResponse response, Integer userId, Integer isRenzheng) {
		usercService.changeUserRenzheng(userId, isRenzheng);
		return "redirect:./toUserList.do";
	}

	@RequestMapping("/setManager")
	public String setUserIsManager(HttpServletResponse response, Integer userId, Integer isManager) {
		usercService.changeUserIsManager(userId, isManager);
		return "redirect:./toUserList.do";
	}

	@RequestMapping("/gogogo")
	public ModelAndView gogo(ModelAndView modelView) {
		modelView.setViewName("activityManage/mytest");
		return modelView;
	}

	// 删除用户
	@RequestMapping("/deleteUserId")
	public void deleteUserId(HttpServletResponse response, Integer userId, HttpServletRequest request) {
		BackUser bu = (BackUser) request.getSession().getAttribute("loginer");
		UserCommon user = usercService.getUserByUserId(userId);
		OperateLog log = new OperateLog();
		log.setOperateUser(user.getNickName());
		log.setOperateTime(new Date());
		log.setOperateName("【" + bu.getUserName() + "】删除用户【" + user.getNickName() + "】");
		try {
			log.setOperateResult(1);
			logService.addOperateLog(log);
			usercService.deleteUserId(userId);
			output(response, JsonUtil.buildFalseJson("0", "删除成功"));
		} catch (Exception e) {
			log.setOperateResult(2);
			logService.addOperateLog(log);
			output(response, JsonUtil.buildFalseJson("1", "删除失败"));
		}
	}

	@RequestMapping("/updateUseer")
	public void updateUseer(HttpServletResponse response, UserCommon user) {
		user.setUpdateTime(new Date());
		usercService.commonInfoUpdate(user);
		output(response, JsonUtil.buildFalseJson("0", "修改成功"));
	}
	/**
	 * 设置代理
	 * @param response
	 * @param userId
	 * @param isAgent
	 */
	@RequestMapping("/setagentbyuserid")
	public void setAgentByUserId(HttpServletResponse response,Integer userId,Integer isAgent){
		usercService.setAgent(userId, isAgent);
		try {
			output(response,JsonUtil.buildFalseJson("0","设置代理成功"));
		} catch (Exception e) {
			output(response,JsonUtil.buildFalseJson("1","设置代理失败"));
		}
	}
	/**
	 * 进入代理列表页面
	 * @param request
	 * @param mv
	 * @return
	 */
	@RequestMapping("/goagentlist")
	public ModelAndView goAgentList(HttpServletRequest request,ModelAndView mv){
		BackUser bu = (BackUser) request.getSession().getAttribute("loginer");
		List<ApplicationCommon> appList = null;
		if (bu.getLoginPlat() == null || bu.getLoginPlat().equals("")) {
			appList = applicationService.getNewApplicationList(null);
		} else {
			String[] idStrings = bu.getLoginPlat().split(",");
			List<String> loginPlatList = Arrays.asList(idStrings);
			appList = applicationService.getApplicationByLoginPlat(loginPlatList);
		}
		mv.addObject("appList", appList);
		mv.setViewName("userManage/agentList");
		return mv;
	}
	/**
	 * 获取代理列表数据
	 * @param request
	 * @param response
	 * @param user
	 * @param page
	 * @param limit
	 */
	@RequestMapping("/getagentlist")
	public void getAgentList(HttpServletRequest request,HttpServletResponse response,
			UserCommon user,Integer page,Integer limit){
		BackUser bu = (BackUser) request.getSession().getAttribute("loginer");
		if(page == null){
			page =1;
		}
		if(limit == null){
			limit = 10;
		}
		int rowscount = 0;
		List<UserCommon> lists = new ArrayList<UserCommon>();
		if(bu.getLoginPlat() == null || bu.getLoginPlat().equals("")){
			rowscount = usercService.getAgentCount(user);
			lists = usercService.getAgentList(user,(page-1) * limit, limit);
		}else{
			if(user.getLoginPlat() == null){
				String[] idStrings = bu.getLoginPlat().split(",");
				List<String> loginPlatList = Arrays.asList(idStrings);
				rowscount = usercService.getAgentCountByLoginPlat(loginPlatList, user);
				lists = usercService.getAgentListByLoginPlat(loginPlatList, user,(page-1)*limit, limit);
			}else{
				rowscount = usercService.getAgentCount(user);
				lists = usercService.getAgentList(user,(page-1) * limit, limit);
			}
		}
		output(response,JsonUtil.buildJsonByTotalCount(lists, rowscount));
	}
	/**
	 * 进入邀请人列表
	 * @param request
	 * @param mv
	 * @return
	 */
	@RequestMapping("/govisitlist")
	public ModelAndView goVisitList(HttpServletRequest request,ModelAndView mv,Integer introducer,Integer loginPlat){
		mv.addObject("introducer",introducer);
		mv.addObject("loginPlat",loginPlat);
		mv.setViewName("userManage/visitList");
		return mv;
	}
	/**
	 * 获取邀请的人列表
	 * @param response
	 * @param userId
	 * @param page
	 * @param limit
	 */
	@RequestMapping("/getvisitlist")
	public void  getVisitList(HttpServletResponse response,Integer introducer,Integer loginPlat,Integer page,Integer limit){
		 if(page == null){
			 page = 1;
		 }
		 if(limit == null){
			 limit = 15;
		 }
		 int rowscount = usercService.getVisitCountByUserId(loginPlat,introducer);
		 List<UserCommon> lists = usercService.getVisitListByUserId(loginPlat, introducer,(page-1) * limit,limit);
		 output(response,JsonUtil.buildJsonByTotalCount(lists, rowscount));	 
	}
	/**
	 * 进入邀请者消费列表
	 * @param request
	 * @param mv
	 * @return
	 */
	@RequestMapping("/govisitconsumelist")
	public ModelAndView goVisitConsumeList(HttpServletRequest request,ModelAndView mv,Integer agentId,Integer loginPlat){
		mv.addObject("agentId",agentId);
		mv.addObject("loginPlat",loginPlat);
		mv.setViewName("userManage/consumeList");
		return mv;
	}
	/**
	 * 获取邀请人消费列表
	 * @param response
	 * @param userId
	 * @param loginPlat
	 * @param page
	 * @param limit
	 */
	@RequestMapping("/getvisitconsumelist")
	public void getVisitConsumeList(HttpServletResponse response,Integer agentId,Integer loginPlat,Integer page,Integer limit){
		if(page == null){
			page = 1;
		}
		if(limit == null){
			limit = 15;
		}
		int rowscount = orderService.userVisitOrderCount(agentId, loginPlat);
		List<UserOrder> lists = orderService.userVisitOrder(agentId, loginPlat,(page-1) * limit, limit);
	    output(response,JsonUtil.buildJsonByTotalCount(lists, rowscount));
	}
	/**
	 * 根据关键字搜索用户
	 * @param response
	 * @param keyWord
	 * @param loginPlat
	 */
	@RequestMapping("/searchUserProject")
	public void searchUserByProject(HttpServletRequest request,HttpServletResponse response,String keyWord){
		BackUser bu = (BackUser) request.getSession().getAttribute("loginer");
		if(bu == null){
			output(response,JsonUtil.buildFalseJson("1", "请先登录"));
		}else{
			List<UserCommon> lists = new ArrayList<UserCommon>();
			if(bu.getLoginPlat() == null || bu.getLoginPlat().equals("")){//所有
				lists = usercService.getUserByKeyWord(keyWord, null);
			}else{
				String[] idStrings = bu.getLoginPlat().split(",");
				List<String> loginPlatList = Arrays.asList(idStrings);
				lists = usercService.getUserByKeyWord(keyWord, loginPlatList);
			}
			
			output(response,JsonUtil.buildJson(lists));
		}
		
	}
}
