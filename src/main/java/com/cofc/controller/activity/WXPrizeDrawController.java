package com.cofc.controller.activity;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cofc.pojo.ActivityCommon;
import com.cofc.pojo.PrizeCommon;
import com.cofc.pojo.PrizeRecord;
import com.cofc.pojo.UserActivity;
import com.cofc.pojo.UserCommon;
import com.cofc.pojo.UserWalletDiary;
import com.cofc.service.ActivityCommonService;
import com.cofc.service.PrizeCommonService;
import com.cofc.service.PrizeRecordService;
import com.cofc.service.UserActivityService;
import com.cofc.service.UserCommonService;
import com.cofc.service.UserWalletDiaryService;
import com.cofc.util.BaseUtil;
import com.cofc.util.JsonUtil;

@Controller
@RequestMapping("/wx/prizedraw")
public class WXPrizeDrawController extends BaseUtil {
	@Resource
	private PrizeCommonService prizecService;
	@Resource
	private PrizeRecordService precordService;
	@Resource
	private UserActivityService useracService;
	@Resource
	private ActivityCommonService accService;
	@Resource
	private UserCommonService userService;
	@Resource
	private UserWalletDiaryService walletdService;
	public static Logger log = Logger.getLogger("WXPrizeDrawController");

	/**
	 * 返回奖项(前端奖品列表)
	 */
	@RequestMapping("/prizeList")
	public void findPrizesByCritera(HttpServletResponse response, Integer activityId) {
		String result = findTheFirstActivity(activityId);
		String[] arr = result.split(",");
		if (!"0".equals(arr[0])) {
			List<PrizeCommon> prizeList = prizecService.findPrizeByCriteria(Integer.parseInt(arr[0]));
			if (prizeList != null && !prizeList.isEmpty()) {
				output(response, JsonUtil.buildJson(prizeList));
			} else {
				output(response, JsonUtil.buildFalseJson("1", "没有数据!"));
			}
		} else {
			output(response, JsonUtil.buildFalseJson("1", "没有数据!"));
		}
	}

	// 找到当前活动的最高级活动id以及活动是第几级分享
	private String findTheFirstActivity(Integer activityId) {
		ActivityCommon activity = accService.getActivityById(activityId);
		int i = 0;
		if (activity != null) {
			if (activity.getParentId() != null) {
				findTheFirstActivity(activity.getParentId());
				i++;
			} else {
				return activity.getActivityId() + "," + i;
			}
		} else {
			return "0,0";
		}
		return activity.getParentId() + "," + i;
	}

	/**
	 * 抽奖 未兼容所有模式，目前只兼容限定每人每天的抽奖次数，每次都需要消耗金额
	 * 
	 * @return
	 */
	@RequestMapping("/getprize")
	public synchronized void doPrizeDraw(HttpServletResponse response, Integer activityId, Integer userId,
			double prizeFee) {
		ActivityCommon activityc = accService.getActivityById(activityId);
		UserActivity userac = useracService.confirmLeftCountAndacstatus(userId, activityId);
		UserCommon currUser = userService.getUserByUserId(userId);
		PrizeCommon returnPrize = new PrizeCommon();
		if (activityc.getStatus() != 1) {
			output(response, JsonUtil.buildFalseJson("2", "该抽奖活动已结束。"));
		} else {
			if (activityc.getRaffleRule() == 1) {// 消耗次数抽奖
				if (userac.getLeftCount() <= 0) {// 剩余次数小于等于0
					output(response, JsonUtil.buildFalseJson("3", "抽奖剩余次数不足"));
				} else {// 还有剩余次数
					returnPrize = drawPrizeRuleCode(activityc, userac, currUser, prizeFee);
					if (returnPrize == null) {
						output(response, JsonUtil.buildFalseJson("1", "抽奖失败"));
					} else {
						if (returnPrize.getGetPrize() != 1) {// 是扣次数的类型平且没有中免费再来一次
							userac.setLeftCount(userac.getLeftCount() - 1);
							userac.setUpdateTime(new Date());
							useracService.updateUserActivityInfo(userac);
							log.info("用户" + currUser.getUserId() + "的剩余抽奖次数修改成功");
						}
						List<PrizeCommon> returnList = new ArrayList<PrizeCommon>();
						returnPrize.setLeftTimes(userac.getLeftCount());
						returnPrize.setUserBalance(currUser.getWalletBalance());
						returnList.add(returnPrize);
						output(response, JsonUtil.buildJson(returnList));
					}
				}
			} else {// 不消耗次数，直接消耗资金的，每人每天限制20次
				SimpleDateFormat startSdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
				SimpleDateFormat endSdf = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
				List<PrizeRecord> todayRecords = precordService.findMyTodayAllRecord(activityId, userId,
						startSdf.format(new Date()), endSdf.format(new Date()));
				int totalCount = todayRecords.size();
				int againCount = 0;
				for (PrizeRecord precord : todayRecords) {
					if (precord.getPrizeInfo().getGetPrize() == 1) {
						againCount++;
					}
				}
				if (totalCount - againCount < 20) {
					returnPrize = drawPrizeRuleCode(activityc, userac, currUser, prizeFee);
					List<PrizeCommon> returnList = new ArrayList<PrizeCommon>();
					returnPrize.setUserBalance(currUser.getWalletBalance());
					returnList.add(returnPrize);
					output(response, JsonUtil.buildJson(returnList));
				} else {
					output(response, JsonUtil.buildFalseJson("4", "今天小百被您抽了20次，省省力明天再抽！"));
				}
			}
		}

	}

	// 申请兑奖
	@RequestMapping("/cashprize")
	public void userWanttoCashprize(HttpServletResponse response, Integer userId, Integer recordId) {
		PrizeRecord prirecord = precordService.getPrizeRecordById(recordId);
		UserCommon currUser = userService.getUserByUserId(userId);
		if (prirecord == null || prirecord.getUserId() != userId) {
			output(response, JsonUtil.buildFalseJson("2", "该中奖信息未找到!"));
		} else if (prirecord.getStatus() == 3) {
			output(response, JsonUtil.buildFalseJson("3", "该奖品已兑换!"));
		} else {
			if (prirecord.getPrizeInfo().getPrizeType() == 5) {
				prirecord.setStatus(3);
				precordService.changePrizeRecordStatus(prirecord);
				output(response, JsonUtil.buildFalseJson("0", "兑换成功!"));
				String prizeName = prirecord.getPrizeInfo().getPrizeName();
				currUser.setIntegral(
						currUser.getIntegral() + Integer.parseInt(prizeName.substring(0, prizeName.length() - 2)));
				userService.commonInfoUpdate(currUser);
				log.info("用户" + userId + "的中奖纪录" + recordId + "兑奖成功，增加" + prizeName.substring(0, prizeName.length() - 2)
						+ "积分");
			} else {
				prirecord.setStatus(2);
				try {
					precordService.changePrizeRecordStatus(prirecord);
					output(response, JsonUtil.buildFalseJson("0", "申请成功，请等待处理"));
					log.info("用户" + userId + "的中奖纪录" + recordId + "成功申请兑奖");
				} catch (Exception e) {
					output(response, JsonUtil.buildFalseJson("1", "申请失败"));
					log.info("用户" + userId + "的中奖纪录" + recordId + "申请兑奖失败，失败原因" + e);
				}
			}
		}
	}

	// 获取2个值之间带三位小数的随机数
	public double randomDouble(double min, double max) {
		int scl = 3; // 小数最大位数
		int pow = (int) Math.pow(10, scl); // 用于提取指定小数位
		double one;
		one = Math.floor((Math.random() * (max - min) + min) * pow) / pow;
		return one;
	}

	private PrizeCommon drawPrizeRuleCode(ActivityCommon activityc, UserActivity useract, UserCommon currUser,
			double prizeFee) {
		List<PrizeCommon> prizeList = prizecService.findPrizeByCriteria(activityc.getActivityId());// 所有奖品
		List<PrizeCommon> myList = new ArrayList<PrizeCommon>();// 我可抽的奖品
		double persentCount = 0;
		DecimalFormat df = new DecimalFormat("0.000");
		for (PrizeCommon perc : prizeList) {
			if (perc.getPrizeCount() > 0) {
				persentCount += perc.getPrizePersent();
				myList.add(perc);
			}
		}
		double roandom = randomDouble(0.001, persentCount);
		PrizeCommon mygetPrize = null;
		// 抽奖主要逻辑
		double line = 0;
		double temp = 0;
		for (int i = 0; i < myList.size(); i++) {
			PrizeCommon currPrize = myList.get(i);
			double c = currPrize.getPrizePersent();
			temp = temp + c;
			line = Double.parseDouble(df.format(persentCount)) - temp;
			if (c != 0) {
				if (roandom > line && roandom <= (line + c) && currPrize.getPrizeCount() > 0) {
					mygetPrize = currPrize;
					break;
				}
			}
		}
		PrizeRecord record = new PrizeRecord();
		if (activityc.getType() == 3) {// 抽红包的转盘
			record.setPrizeFee(prizeFee * mygetPrize.getMultiple());
			record.setStatus(3);
			record.setPrizeId(mygetPrize.getPrizeId());
			record.setPrizeName(mygetPrize.getPrizeName());
			record.setPrizeTime(new Date());
			record.setUserId(useract.getRecoredUser());
			record.setActivityId(activityc.getActivityId());
			record.setCurrFee(prizeFee);
			try {
				precordService.addNewPrizeRecord(record);
				log.info("用户" + currUser.getUserId() + "的抽奖记录添加成功");
				double currBlance = currUser.getWalletBalance();
				double gettotalFee = record.getPrizeFee() - prizeFee;
				currUser.setWalletBalance(currBlance + gettotalFee);
				log.info("用户" + currUser.getUserId() + "的钱包余额由" + currBlance + "元变为" + currUser.getWalletBalance()
						+ "元");
				userService.commonInfoUpdate(currUser);
				return mygetPrize;
			} catch (Exception e) {
				log.info("用户" + currUser.getUserId() + "的抽奖失败，失败原因" + e);
				e.printStackTrace();
				return null;
			}
		} else if (activityc.getType() == 1) {// 普通转盘
			record.setStatus(1);
			record.setPrizeId(mygetPrize.getPrizeId());
			record.setPrizeName(mygetPrize.getPrizeName());
			record.setPrizeTime(new Date());
			record.setUserId(currUser.getUserId());
			record.setActivityId(activityc.getActivityId());
			record.setCurrFee(prizeFee);
			try {
				precordService.addNewPrizeRecord(record);
				log.info("用户" + currUser.getUserId() + "的抽奖记录添加成功");
				if (mygetPrize.getIsPrized() != 1) {
					double currBlance = currUser.getWalletBalance();
					currUser.setWalletBalance(currBlance - prizeFee);
					log.info("用户" + currUser.getUserId() + "的钱包余额由" + currBlance + "元变为" + currUser.getWalletBalance()
							+ "元");
					userService.commonInfoUpdate(currUser);
					UserWalletDiary userdiary = new UserWalletDiary();
					userdiary.setCreateTime(new Date());
					userdiary.setDiaryTitle("抽奖消费");
					userdiary.setDiaryDetails("您抽奖消费" + prizeFee + "元");
					userdiary.setIncomeExpend(0);// 支出
					userdiary.setTotalFee(prizeFee);
					userdiary.setUserId(currUser.getUserId());
					userdiary.setLoginPlat(1);//百享园先写死后面优化
					walletdService.addNewDiary(userdiary);
					log.info("用户参与抽奖的资金日志已添加");
					if(mygetPrize.getIsPrized() == 2){
						mygetPrize.setPrizeCount(mygetPrize.getPrizeCount()-1);
						prizecService.updatePrizeInfo(mygetPrize);
						log.info("抽中奖项的剩余奖品数量已减去1");
					}
				}
				return mygetPrize;
			} catch (Exception e) {
				log.info("用户" + currUser.getUserId() + "的抽奖失败，失败原因" + e);
				e.printStackTrace();
				return null;
			}
		}
		return null;
	}

	public static void main(String[] args) {
		String c = "100积分";
		String b = c.substring(0, c.length() - 2);
		System.out.println(b);
	}
}
