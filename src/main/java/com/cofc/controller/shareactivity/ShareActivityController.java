package com.cofc.controller.shareactivity;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cofc.pojo.ActivityContent;
import com.cofc.pojo.BackUser;
import com.cofc.pojo.ShareActivity;
import com.cofc.service.ActivityContentService;
import com.cofc.service.ApplicationCommonService;
import com.cofc.service.ShareActivityService;
import com.cofc.util.BaseUtil;
import com.cofc.util.JsonUtil;

@Controller
@RequestMapping("/shareActivity")
public class ShareActivityController extends BaseUtil{
	
	@Resource
	private ShareActivityService activityService;
	@Resource
	private ApplicationCommonService applicationService;
	@Resource
	private ActivityContentService activityContentService;
	
	//活动列表
	@RequestMapping("/activityList")
	public ModelAndView activityList(ModelAndView mView){
		mView.setViewName("/shareActivityMap/activityList");
		return mView;
	}
	
	//ajax查看活动列表
	@RequestMapping("/showActivityList")
	public void showActivityList(HttpServletResponse response,Integer limit,Integer page,Integer publisherId){
		if (page == null) {
			page = 1;
		}
		if (limit == null) {
			limit = 20;
		}
		List<ShareActivity> activities = activityService.getShareActivityList(publisherId, (page-1)*limit, limit);
		int rowsCount = activityService.getShareActivityCount(publisherId);
		output(response, JsonUtil.buildJsonByTotalCount(activities, rowsCount));
	}
	
	
	//ajax查看活动内容列表
	@RequestMapping("/showContentList")
	public void showContentList(Integer limit,Integer page,Integer keyword,HttpServletResponse response){
		if (page == null) {
			page = 1;
		}
		if (limit == null) {
			limit = 20;
		}
		List<ActivityContent> contents = activityContentService.getActivityContentList((page-1)*page, limit,keyword);
		int rowsCount = activityContentService.getActivityContentCount(keyword);
		output(response, JsonUtil.buildJsonByTotalCount(contents, rowsCount));
	}
	
	// 添加活动内容页面
	@RequestMapping("/addActivity")
	public ModelAndView addActivity(ModelAndView modelView) {
		modelView.setViewName("/shareActivityMap/addActivity");
//		 List<ApplicationCommon> appList =applicationService.findApplicationsByCriteria(null, null, null, null,
//				 null,null, null, null);
//		 modelView.addObject("appList", appList);
		return modelView;
	}
	
	//添加活动
	@RequestMapping("/doAddActivity")
	public void doAddActivity(HttpServletResponse response,ShareActivity activity,HttpServletRequest request){
		BackUser buser = (BackUser) request.getSession().getAttribute("loginer");
		try {
			activity.setCreateTime(new Date());
			activity.setPublisherId(buser.getUserId());
			activity.setShareNumber(0);
			activity.setActivityStatus(1);
			activity.setIsPublic(1);
			activityService.addShareActivity(activity);
			output(response, JsonUtil.buildFalseJson("0", "添加成功!"));
		} catch (Exception e) {
			output(response, JsonUtil.buildFalseJson("1", "添加失败!"));
		}
	}
	
	//添加活动内容
	@RequestMapping("/doAddContent")
	public void doAddContent(HttpServletResponse response,HttpServletRequest request,ActivityContent content){
		BackUser buser = (BackUser) request.getSession().getAttribute("loginer");
		ActivityContent content2 = activityContentService.getActivityContentName(content.getContentDesc());
		if (content2 != null) {
			output(response, JsonUtil.buildFalseJson("1", "该活动标题已存在，请换一个!"));
		}else {
			content.setCreateTime(new Date());
			content.setUsedCount(0);
			content.setCreateUser(buser.getUserId());
			activityContentService.addActivityContent(content);
			output(response, JsonUtil.buildFalseJson("0", "创建活动成功!"));
		}
	}
	//修改活动
	@RequestMapping("/updateActivity")
	public void updateActivity(HttpServletResponse response,ShareActivity activity){
		ShareActivity activity2 = activityService.getShareActivityById(activity.getActivityId());
		if (activity2 == null) {
			output(response, JsonUtil.buildFalseJson("1", "该活动不存在!"));
		} else {
			activity.setUpdateTime(new Date());
			activityService.updateShareActivity(activity);
			output(response, JsonUtil.buildFalseJson("0", "修改活动成功!"));
		}
	}
	//删除活动
	@RequestMapping("/deleteActivity")
	public void deleteActivity(HttpServletResponse response,Integer activityId){
		ShareActivity activity = activityService.getShareActivityById(activityId);
		if (activity == null) {
			output(response, JsonUtil.buildFalseJson("1", "该活动不存在!"));
		}else {
			activityService.deleteShareActivity(activityId);
			output(response, JsonUtil.buildFalseJson("0", "删除活动成功!"));
		}
	}
	//查看活动页面
	@RequestMapping("/goSeeActivity")
	public ModelAndView goSeeActivity(Integer activityId, ModelAndView mAndView) {
		ShareActivity activity = activityService.getShareActivityById(activityId);
		mAndView.addObject("activity", activity);
		mAndView.setViewName("/shareActivityMap/updateActivity");
		return mAndView;
	}
	//结束活动
	@RequestMapping("/updateStatus")
	public void updateStatus(HttpServletResponse response,Integer activityId){
		ShareActivity activity = activityService.getShareActivityById(activityId);
		 if (activity.getActivityStatus() == 2) {
			output(response, JsonUtil.buildFalseJson("2", "该活动已结束!"));
		} else {
			activity.setUpdateTime(new Date());
			activity.setActivityStatus(2);
			activityService.updateShareActivity(activity);
			output(response, JsonUtil.buildFalseJson("0", "结束成功!"));
		}
	}
	//添加活动内容列表
	@RequestMapping("/contentList")
	public ModelAndView contentList(ModelAndView modelAndView){
		modelAndView.setViewName("/shareActivityMap/contentList");
		return modelAndView;
	}
	//删除活动内容
	@RequestMapping("/deleteContent")
	public void deleteContent(HttpServletResponse response,Integer contentId){
		try {
			activityContentService.deleteActivityContent(contentId);
			output(response, JsonUtil.buildFalseJson("0", "删除活动内容成功!"));
		} catch (Exception e) {
			output(response, JsonUtil.buildFalseJson("1", "删除活动内容失败!"));
		}
	}
	
	//查看活动内容页面
	@RequestMapping("/goSeeContent")
	public ModelAndView goSeeContent(ModelAndView modelAndView,Integer contentId){
		ActivityContent content = activityContentService.getActivityContentById(contentId);
		modelAndView.addObject("content", content);
		modelAndView.setViewName("/shareActivityMap/updateContent");
		return modelAndView;
	}
	//修改活动内容
	@RequestMapping("/updateContent")
	public void updateContent(HttpServletResponse response,ActivityContent content){
		try {
			content.setUpdateTime(new Date());
			activityContentService.updateActivityContent(content);
			output(response, JsonUtil.buildFalseJson("0", "修改成功"));
		} catch (Exception e) {
			output(response, JsonUtil.buildFalseJson("1", "修改失败"));
		}
	}
	//批量删除活动
	@RequestMapping("/batchDelActivity")
	public void batchDelActivity(HttpServletResponse response,String activityIds){
		List<ShareActivity> acList = activityService.getActivityIds(Arrays.asList(activityIds.split(",")));
		if (acList != null && !acList.isEmpty()) {
			for (ShareActivity ac:acList) {
				activityService.deleteShareActivity(ac.getActivityId());
			}
			output(response, JsonUtil.buildFalseJson("0", "删除成功!"));
		} else {
			output(response, JsonUtil.buildFalseJson("1", "没有要删除的数据!"));
		}
	}
}
