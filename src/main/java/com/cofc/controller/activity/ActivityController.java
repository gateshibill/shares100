package com.cofc.controller.activity;

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
import com.cofc.pojo.ActivityCommon;
import com.cofc.pojo.ApplicationCommon;
import com.cofc.pojo.BackUser;
import com.cofc.service.ActivityCommonService;
import com.cofc.service.ApplicationCommonService;
import com.cofc.service.UserBackuserRelationService;
import com.cofc.service.UserRoleService;
import com.cofc.util.BaseUtil;
import com.cofc.util.JsonUtil;

//活动
@Controller
@RequestMapping("/activity")
public class ActivityController extends BaseUtil {
	@Resource
	private ActivityCommonService activityService;
	@Resource
	private ApplicationCommonService applicationService;
	@Resource
	private UserRoleService userroleService;
	@Resource
	private UserBackuserRelationService uburService;

	public static Logger log = Logger.getLogger("ActivityController");
	@RequestMapping("/goActivityList")
	public ModelAndView goActivityListJsp(HttpServletRequest request,ModelAndView modelView) {
		BackUser bu = (BackUser) request.getSession().getAttribute("loginer");
//		UserRole userRole = userroleService.getUserRoleById(currUser.getUserId());
//		List<UserBackuserRelation> idlist = uburService.getUserBackuserList(currUser.getUserId());
//		String[] rolesarr = userRole.getRoleId().split(",");
//		boolean isSuperm = false;
//		for (String role : rolesarr) {
//			while ("1".equals(role)) {
//				isSuperm = true;
//				break;
//			}
//		}
//		List<ApplicationCommon> appList = new ArrayList<ApplicationCommon>();
//		if (isSuperm) {
//			appList = applicationService.findApplicationCommon(null);
//		} else {
//			List<String> idsl = new ArrayList<String>();
//			for (UserBackuserRelation cuid : idlist) {
//				idsl.add(cuid.getUserId().toString());
//			}
//			if (idsl != null && !idsl.isEmpty()) {
//				appList = applicationService.findMyCreatedAllAppications(idsl);
//			} 
////			appList = applicationService.findMyCreatedAllAppications(idsl);
//		}
		List<ApplicationCommon> appList = new ArrayList<ApplicationCommon>();
		if(bu.getLoginPlat() == null || bu.getLoginPlat() == ""){
			appList = applicationService.getNewApplicationList(null);
		}else{
			String[] idStrings = bu.getLoginPlat().split(",");
			List<String> loginPlatList = Arrays.asList(idStrings);
			appList = applicationService.getApplicationByLoginPlat(loginPlatList);
		}
		modelView.addObject("appList", appList);
		modelView.setViewName("activityManage/activityList");
		return modelView;
	}

	// $.ajax查询活动列表
	@RequestMapping("/showActivityList")
	public void showActivityList(HttpServletRequest request,HttpServletResponse response, ActivityCommon act, String userKeyWords,
			String dateRange, Integer page, Integer limit) throws ParseException {
		BackUser bu = (BackUser) request.getSession().getAttribute("loginer");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat startSdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		SimpleDateFormat endSdf = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
		String startTime = null;
		String endTime = null;
		if (dateRange != null && !dateRange.equals("")) {
			String[] myDate = dateRange.split(" -| ");
			startTime = startSdf.format(sdf.parse(myDate[0]));
			endTime = endSdf.format(sdf.parse(myDate[2]));
		}
		List<ActivityCommon> actList = new ArrayList<ActivityCommon>();
		int rowsCount = 0;
		if(bu.getLoginPlat() == null || bu.getLoginPlat().equals("")){ //百享园
			rowsCount = activityService.getCountActivity(act, userKeyWords, startTime, endTime);
			actList = activityService.findActivityList(act, userKeyWords, startTime, endTime, (page - 1) * limit,
					limit);
		}else{
			 if(act.getLoginPlat() == null){ //应用
				 String[] idStrings = bu.getLoginPlat().split(",");
				 List<String> loginPlatList = Arrays.asList(idStrings);
				 rowsCount = activityService.getCountActivityByLoginPlat(loginPlatList, act, userKeyWords, startTime, endTime); 
			     actList = activityService.findActivityListByLoginPlat(loginPlatList, act, userKeyWords, startTime, endTime,(page-1) * limit, limit);
			 }else{ //搜索
				 rowsCount = activityService.getCountActivity(act, userKeyWords, startTime, endTime);
				 actList = activityService.findActivityList(act, userKeyWords, startTime, endTime, (page - 1) * limit,
							limit);
			 }
		}
		
		output(response, JsonUtil.buildJsonByTotalCount(actList, rowsCount));
	}

	@RequestMapping("/activityDetails")
	public ModelAndView showActivityDetails(ModelAndView modelView, Integer activityId) {
		ActivityCommon currAct = activityService.getActivityById(activityId);
		modelView.setViewName("activityManage/activityDetails");
		modelView.addObject("activity", currAct);
		return modelView;
	}

	@RequestMapping("/updateInfo")
	public void updateActivityInfo(HttpServletResponse response, ActivityCommon currActivity) {
		try {
			currActivity.setUpdateTime(new Date());
			activityService.updateActivityInfo(currActivity);
			output(response, JsonUtil.buildFalseJson("0", "修改活动" + currActivity.getActivityId() + "成功"));
		} catch (Exception e) {
			log.info("活动" + currActivity.getActivityId() + "修改失败，失败原因"+e);
			output(response, JsonUtil.buildFalseJson("1", "修改活动" + currActivity.getActivityId() + "失败"));
		}
	}
	
	//删除活动
	@RequestMapping("/deleteActivity")
	public void deleteActivity(HttpServletResponse response,Integer activityId){
		try {
			activityService.deleteActivity(activityId);
			output(response, JsonUtil.buildFalseJson("0", "删除成功!"));
		} catch (Exception e) {
		output(response, JsonUtil.buildFalseJson("1", "删除失败!"));
		}
	}
}
