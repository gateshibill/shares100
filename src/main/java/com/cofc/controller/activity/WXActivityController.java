package com.cofc.controller.activity;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.cofc.pojo.ActivityCommon;
import com.cofc.pojo.PrizeCommon;
import com.cofc.pojo.PrizeRecord;
import com.cofc.pojo.UserActivity;
import com.cofc.pojo.UserCommon;
import com.cofc.service.ActivityCommonService;
import com.cofc.service.PrizeCommonService;
import com.cofc.service.PrizeRecordService;
import com.cofc.service.UserActivityService;
import com.cofc.service.UserCommonService;
import com.cofc.util.BaseUtil;
import com.cofc.util.JsonUtil;

@Controller
@RequestMapping("/wx/activity")
public class WXActivityController extends BaseUtil {
	@Resource
	private ActivityCommonService activityService;
	@Resource
	private UserActivityService userAcService;
	@Resource
	private UserCommonService userService;
	@Resource
	private PrizeRecordService przRecordService;
	@Resource
	private PrizeCommonService prizeService;

	public static Logger log = Logger.getLogger("WXActivityController");

	@RequestMapping("/activityList")
	public void showActivitysByCriteria(HttpServletResponse response, ActivityCommon acyCommon, Integer pageNo,
			Integer pageSize) {
		List<ActivityCommon> actyList = activityService.findActivityByCriteria(acyCommon, (pageNo - 1) * pageSize,
				pageSize);
		output(response, JsonUtil.buildJson(actyList));
	}

	@RequestMapping("/createActivity")
	public void CreateNewActivity(HttpServletResponse response, ActivityCommon acyCommon) {
		acyCommon.setCreateTime(new Date());
		try {
			activityService.createNewActivity(acyCommon);
			Map<String, Object> resultM = new HashMap<String, Object>();
			resultM.put("activityId", resultM);
			output(response, JsonUtil.MapToJson(resultM));
		} catch (Exception e) {
			log.info("用户" + acyCommon.getCreateUser() + "创建游戏活动" + acyCommon.getActivityName() + "失败，失败原因" + e);
			output(response, JsonUtil.buildFalseJson("1", "创建失败!"));
		}
		// output(response, JsonUtil.buildJson(actyList));
	}

	// 给抽奖活动设置奖品
	@RequestMapping("/setPrizes")
	public void setPrizesForActivity(HttpServletResponse response, String prizesList) {
		List<PrizeCommon> inserList = new ArrayList<PrizeCommon>();
		List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
		listMap = JSON.parseObject("[" + prizesList + "]", new TypeReference<List<Map<String, Object>>>() {
		});
		for (int i = 0; i < listMap.size(); i++) {
			PrizeCommon curPrize = new PrizeCommon();
			curPrize.setCreateTime(new Date());
			curPrize.setPrizeName(listMap.get(i).get("prizeName").toString());
			curPrize.setPrizeImage(listMap.get(i).get("prizeImage").toString());
			curPrize.setGetPrize(Integer.parseInt(listMap.get(i).get("getPrize").toString()));
			curPrize.setPrizeCount(Integer.parseInt(listMap.get(i).get("prizeCount").toString()));
			curPrize.setPrizePersent(Double.valueOf(listMap.get(i).get("isPrized").toString()));
			curPrize.setOrderId(Integer.parseInt(listMap.get(i).get("orderId").toString()));
			curPrize.setActivityId(Integer.parseInt(listMap.get(i).get("activityId").toString()));
			curPrize.setIsPrized(Integer.parseInt(listMap.get(i).get("isPrized").toString()));
			curPrize.setPrizeType(Integer.parseInt(listMap.get(i).get("prize_type").toString()));
			inserList.add(curPrize);
		}
		try {
			prizeService.addBatchPrizes(inserList);
			output(response, JsonUtil.buildFalseJson("0", "添加成功!"));
		} catch (Exception e) {
			log.info("转盘游戏活动" + listMap.get(0).get("activityId") + "设置奖品失败,失败原因" + e);
			output(response, JsonUtil.buildFalseJson("1", "添加奖品失败!"));
		}
	}
//
//	@RequestMapping("/userJoinActivity")
//	public void showActivityStatusAndUserLeftCount(HttpServletResponse response, Integer activityId, Integer userId) {
//		ActivityCommon actcommon = activityService.getActivityById(activityId);
//		UserActivity userac = userAcService.confirmLeftCountAndacstatus(userId, activityId);
//		List<UserActivity> jsonList = new ArrayList<UserActivity>();
//		try {
//			if (userac == null) {
//				userac = new UserActivity();
//				if (actcommon.getRaffleRule() != null && actcommon.getRaffleRule() == 1) {
//					userac.setActivityCount(actcommon.getCount());
//					userac.setLeftCount(actcommon.getCount());
//				}else{
//					userac.setActivityCount(0);
//					userac.setLeftCount(0);
//				}
//				userac.setJoinTime(new Date());
//				userac.setRecoredUser(userId);
//				userac.setActivityId(activityId);
//				userAcService.UserJoinActivity(userac);
//				userac.setActivityStatus(actcommon.getStatus());
//				jsonList.add(userac);
//			} else {
//				jsonList.add(userac);
//			}
//			output(response, JsonUtil.buildJson(jsonList));
//		} catch (Exception e) {
//			output(response, JsonUtil.buildFalseJson("1", "加入失败"));
//		}
//	}

	// 我的奖品(中奖纪录)
	@RequestMapping("/myPrize")
	public void showMyPrizeList(HttpServletResponse response, Integer userId, Integer activityId, Integer pageNo,
			Integer pageSize) {
		List<PrizeRecord> precList = przRecordService.findMygetPrizeRecord(userId, activityId, (pageNo - 1) * pageSize,
				pageSize);
		output(response, JsonUtil.buildJson(precList));
	}

	// 活动的中奖纪录（中奖用户）
	@RequestMapping("/acPrizedList")
	public void showMyActivityPrizedList(HttpServletResponse response, Integer activityId, Integer pageNo,
			Integer pageSize) {
		List<PrizeRecord> precList = przRecordService.findPrizeRecordByActivity(activityId, (pageNo - 1) * pageSize,
				pageSize);
		output(response, JsonUtil.buildJson(precList));
	}

	@RequestMapping("/sxjActivity")
	public void startOrStopActivity(HttpServletResponse response, Integer activityId, Integer loginPlat,
			Integer userId) {
		ActivityCommon currActivity = activityService.getActivityById(activityId);
		if (currActivity != null) {
			UserCommon currUser = userService.getUserByUserId(userId);
			if (currUser.getIsManager() != 1) {
				output(response, JsonUtil.buildFalseJson("2", "您不是管理员,不能操作!"));
			} else {
				if (currActivity.getStatus() == 1) {// 活动中，则暂停
					currActivity.setStatus(2);
					currActivity.setUpdateTime(new Date());
					activityService.updateActivityInfo(currActivity);
				} else if (currActivity.getStatus() == 2) {// 暂停中，开启活动
					currActivity.setStatus(1);
					currActivity.setUpdateTime(new Date());
					activityService.updateActivityInfo(currActivity);
				}
				output(response, JsonUtil.buildFalseJson("0", "操作成功!"));
			}
		} else {
			output(response, JsonUtil.buildFalseJson("1", "该活动不存在!"));
		}
	}

	/**
	 * 用户分享活动
	 * 
	 * @param response
	 * @param activityId
	 * @param userId
	 */
	@RequestMapping("/shareActivity")
	public void userShareActivity(HttpServletResponse response, Integer activityId, Integer userId) {
		ActivityCommon confirmShared = activityService.confirmSharedThisActivity(activityId, userId);
		String result = findTheFirstActivity(activityId, userId);
		String[] arr = result.split(",");
		if (Integer.parseInt(arr[1]) < 1) {
			ActivityCommon wantshare = activityService.getActivityById(activityId);
			if (wantshare.getCreateUser() != userId && Integer.parseInt(arr[2]) == 1 && confirmShared != null) {
				wantshare.setParentId(activityId);
				wantshare.setCreateUser(userId);
				wantshare.setCreateTime(new Date());
				try {
					activityService.createNewActivity(wantshare);
					log.info("用户" + userId + "分享活动Id" + activityId + "成功!");
					List<ActivityCommon> returnList = new ArrayList<ActivityCommon>();
					returnList.add(wantshare);
					output(response, JsonUtil.buildJson(returnList));
				} catch (Exception e) {
					log.info("用户" + userId + "分享活动Id" + activityId + "失败，失败原因" + e);
					output(response, JsonUtil.buildFalseJson("1", "分享失败!"));
				}
			} else {
				output(response, JsonUtil.buildFalseJson("3", "您已经创建或分享过该活动,请在活动列表查看!"));
			}
		} else {
			output(response, JsonUtil.buildFalseJson("2", "根据中华人民共和国相反法律规定，您分享的活动的分享等级已达到上限!"));
		}
	}

	// 找到当前活动的最高级活动id以及活动是第几级分享
	private String findTheFirstActivity(Integer activityId, Integer userId) {
		ActivityCommon activity = activityService.getActivityById(activityId);
		int i = 0;
		int j = 0;
		if (activity != null) {
			if (activity.getParentId() != null) {
				if (activity.getCreateUser() == userId) {
					j = 1;
				}
				findTheFirstActivity(activity.getParentId(), userId);
				i++;
			} else {
				return activity.getActivityId() + "," + i + "," + j;
			}
		} else {
			return "0" + i + j;
		}
		return "0" + "," + i + "," + j;
	}
}
