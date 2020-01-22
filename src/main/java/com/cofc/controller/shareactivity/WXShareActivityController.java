package com.cofc.controller.shareactivity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cofc.pojo.ActivityContent;
import com.cofc.pojo.ActivityRecord;
import com.cofc.pojo.ShareActivity;
import com.cofc.pojo.UserCommon;
import com.cofc.service.ActivityContentService;
import com.cofc.service.ActivityRecordService;
import com.cofc.service.ShareActivityService;
import com.cofc.service.UserCommonService;
import com.cofc.util.BaseUtil;
import com.cofc.util.JsonUtil;


@Controller
@RequestMapping("/wx/shareActivity")
public class WXShareActivityController extends BaseUtil{

	@Resource
	private ShareActivityService activityService;
	@Resource
	private ActivityRecordService recordService;
	@Resource
	private UserCommonService userService;
	@Resource
	private ActivityContentService contentService;
	
	public static Logger log = Logger.getLogger("WXShareActivityController");
	
	
	//前端创建活动
	@RequestMapping("/addActivity")
	public void addActivity(HttpServletResponse response,ShareActivity activity){
		try {
			ActivityContent content = contentService.getActivityContentName(activity.getActivityDesc());
			if (content != null) {
				content.setUpdateTime(new Date());
				content.setUsedCount(content.getUsedCount()+1);
				contentService.updateActivityContent(content);
			}
			activity.setCreateTime(new Date());
			activity.setShareNumber(0);
			activity.setActivityStatus(1);//创建活动默认开始
			activity.setIsPublic(1);
			activityService.addShareActivity(activity);
			output(response, JsonUtil.buildFalseJson("0", "创建活动成功!"));
		} catch (Exception e) {
			output(response, JsonUtil.buildFalseJson("1", "创建活动失败!"));
		}
	}
	
	
	//活动详情
	@RequestMapping("/activityDetails")
	public void activityDetails(HttpServletResponse response, Integer activityId) {
		ShareActivity activity = activityService.getShareActivityById(activityId);
		if (activity == null) {
			output(response, JsonUtil.buildFalseJson("1", "该活动不存在!"));
		} else {
			UserCommon user = userService.getUserByUserId(activity.getPublisherId());
			List<ShareActivity> activities = new ArrayList<ShareActivity>();
			activity.setHeadImage(user.getHeadImage());
			if (user.getRealName() != null) {
				activity.setUserNickName(user.getRealName());
			} else {
				activity.setUserNickName(user.getNickName());
			}
			activities.add(activity);
			output(response, JsonUtil.buildJson(activities));
		}
	}
	
	//活动列表
	@RequestMapping("/activityList")
	public void activityList(HttpServletResponse response,Integer userId,Integer paeNo,Integer pageSize){
		if (paeNo == null) {
			paeNo = 1;
		}
		if (pageSize == null) {
			pageSize = 10;
		}
		List<ShareActivity> activities = activityService.getShareActivityList(userId,(paeNo-1)*pageSize,pageSize);
		if (activities != null && !activities.isEmpty()) {
			output(response, JsonUtil.buildJson(activities));
		}else {
			output(response, JsonUtil.buildFalseJson("1", "没有数据!"));
		}
	}
	
	//活动内容列表
	@RequestMapping("/contentList")
	public void contentList(HttpServletResponse response,Integer pageNo,Integer pageSize){
		if (pageNo == null) {
			pageNo = 1;
		}
		if (pageSize == null) {
			pageSize = 10;
		}
		List<ActivityContent> contents = contentService.getActivityContentList((pageNo-1)*pageSize,pageSize,null);
		if (contents != null && !contents.isEmpty()) {
			output(response, JsonUtil.buildJson(contents));
		}else {
			output(response, JsonUtil.buildFalseJson("1", "没有数据"));
		}
	}
	
	//分享活动，用户查看时添加数据
	@RequestMapping("/addRecord")
	public void addRecord(HttpServletResponse response, ActivityRecord record) {
		if (record.getUserId()!=null&&record.getPublisherId()!=null) {// 查看该记录的用户不为空，检查该用户有没有查看过
			ActivityRecord record2 = recordService.confirmSeeMyShared(record.getPublisherId(),record.getUserId());
			if (record2 == null) {// 没有查看过则添加
				record.setCreateTime(new Date());
				recordService.addActivityRecord(record);
				log.info("新增数据");
			}else
			{
			     log.info("没有新增数据");
			}
		}
		output(response, JsonUtil.buildFalseJson("0", "添加成功!"));
	}
	
	//分享活动用户查看记录列表
	@RequestMapping("/activtyRecordList")
	public void activtyRecordList(HttpServletResponse response,Integer userId,Integer pageNo,Integer pageSize){
		if (pageNo == null) {
			pageNo = 1;
		}
		if (pageSize == null) {
			pageSize = 10;
		}
		int count =recordService.getCountOfRecord(userId);
		List<ActivityRecord> records = recordService.getActivityIdList(userId,(pageNo -1)*pageSize,pageSize);
		output(response, JsonUtil.buildJsonByTotalCount1(records, count));
	}
}
