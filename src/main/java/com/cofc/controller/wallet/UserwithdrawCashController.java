package com.cofc.controller.wallet;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cofc.pojo.ApplicationCommon;
import com.cofc.pojo.BackUser;
import com.cofc.pojo.GainSharingCommon;
import com.cofc.pojo.UserCommon;
import com.cofc.pojo.UserWalletDiary;
import com.cofc.pojo.UserWithdrawCash;
import com.cofc.pojo.WithdrawReturnCode;
import com.cofc.service.ApplicationCommonService;
import com.cofc.service.GainSharingCommonService;
import com.cofc.service.UserBackuserRelationService;
import com.cofc.service.UserCommonService;
import com.cofc.service.UserRoleService;
import com.cofc.service.UserWalletDiaryService;
import com.cofc.service.UserWithdrawCashService;
import com.cofc.service.WithdrawReturnCodeService;
import com.cofc.util.BaseUtil;
import com.cofc.util.JsonUtil;
import com.cofc.util.wxpay.ClientCustomSSL;

@Controller
@RequestMapping("/withdraw")
public class UserwithdrawCashController extends BaseUtil {
	@Resource
	private UserWithdrawCashService withdrawService;
	@Resource
	private ApplicationCommonService appService;
	@Resource
	private UserRoleService userroleService;
	@Resource
	private UserCommonService userService;
	@Resource
	private UserBackuserRelationService uburService;
	@Resource
	private WithdrawReturnCodeService wreturnService;
	@Resource
	private GainSharingCommonService gainSharingService;
	@Resource
	private UserWalletDiaryService walletdiaryService;

	public static Logger log = Logger.getLogger("UserwithdrawCashController");

	@RequestMapping("/goWithdrawList")
	public ModelAndView goWithdrawListjsp(HttpServletRequest request, ModelAndView modelView) {
		BackUser bu = (BackUser) request.getSession().getAttribute("loginer");
		List<ApplicationCommon> appList = new ArrayList<ApplicationCommon>();
		if (bu.getLoginPlat() == null || bu.getLoginPlat().equals("")) {
			appList = appService.getNewApplicationList(null);
		} else {
			String[] idStrings = bu.getLoginPlat().split(",");
			List<String> loginPlatList = Arrays.asList(idStrings);
			appList = appService.getApplicationByLoginPlat(loginPlatList);
		}
		modelView.addObject("appList", appList);
		modelView.setViewName("capitalManage/withdrawList");
		return modelView;
	}

	@RequestMapping("/withdrawList")
	public void showWithdrawListData(HttpServletRequest request, HttpServletResponse response, UserWithdrawCash uwcash,
			String userKeyWords, Integer loginPlat2, String applyDateRange, String dealDateRange, Integer page,
			Integer limit) throws ParseException {
		BackUser bu = (BackUser) request.getSession().getAttribute("loginer");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat startSdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		SimpleDateFormat endSdf = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
		String applyStartTime = null;
		String applyEndTime = null;
		String dealStartTime = null;
		String dealEndTime = null;
		if (applyDateRange != null && !applyDateRange.equals("")) {
			String[] myDate = applyDateRange.split(" -| ");
			applyStartTime = startSdf.format(sdf.parse(myDate[0]));
			applyEndTime = endSdf.format(sdf.parse(myDate[2]));
		}
		if (dealDateRange != null && !dealDateRange.equals("")) {
			String[] myDate = dealDateRange.split(" -| ");
			dealStartTime = startSdf.format(sdf.parse(myDate[0]));
			dealEndTime = endSdf.format(sdf.parse(myDate[2]));
		}
        int rowsCount = 0;
        List<UserWithdrawCash> dataList = new ArrayList<UserWithdrawCash>();
        if(bu.getLoginPlat() == null || bu.getLoginPlat().equals("")){
        	 rowsCount = withdrawService.countAllWithdrawRecord(uwcash, loginPlat2, userKeyWords, applyStartTime,
					applyEndTime, dealStartTime, dealEndTime);
			 dataList = withdrawService.findWithRecordByCriteria(uwcash, loginPlat2, userKeyWords,
					applyStartTime, applyEndTime, dealStartTime, dealEndTime, (page - 1) * limit, limit);
        }else{
        	 if(uwcash.getLoginPlat() == null){
        		 String[] idStrings = bu.getLoginPlat().split(",");
     			 List<String> loginPlatList = Arrays.asList(idStrings);
     			 rowsCount = withdrawService.countAllWithdrawRecordByLoginPlat(loginPlatList, uwcash, loginPlat2, userKeyWords, applyStartTime, applyEndTime, dealStartTime, dealEndTime);
     			 dataList = withdrawService.findWithRecordByCriteriaByLoginPlat(loginPlatList, uwcash, loginPlat2, userKeyWords, applyStartTime, applyEndTime, dealStartTime, dealEndTime,(page - 1) * limit, limit);
        	 }else{
        		 rowsCount = withdrawService.countAllWithdrawRecord(uwcash, loginPlat2, userKeyWords, applyStartTime,
     					applyEndTime, dealStartTime, dealEndTime);
     			 dataList = withdrawService.findWithRecordByCriteria(uwcash, loginPlat2, userKeyWords,
     					applyStartTime, applyEndTime, dealStartTime, dealEndTime, (page - 1) * limit, limit);
        	 }
        }
		output(response, JsonUtil.buildJson2(dataList, rowsCount, limit));
		/*UserRole hasrole = userroleService.getUserRoleById(curradmin.getUserId());
		String[] rolesarr = hasrole.getRoleId().split(",");
		boolean isSuperm = false;
		for (String role : rolesarr) {
			while ("1".equals(role)) {
				isSuperm = true;
				break;
			}
		}
		if (isSuperm) {// 是超级管理员
			int rowsCount = withdrawService.countAllWithdrawRecord(uwcash, loginPlat2, userKeyWords, applyStartTime,
					applyEndTime, dealStartTime, dealEndTime);
			List<UserWithdrawCash> dataList = withdrawService.findWithRecordByCriteria(uwcash, loginPlat2, userKeyWords,
					applyStartTime, applyEndTime, dealStartTime, dealEndTime, (page - 1) * limit, limit);
			output(response, JsonUtil.buildJson2(dataList, rowsCount, limit));
		} else {// 不是超级管理员
			if (loginPlat2 != null) {// 当输入的loginPlat2有值时，需要判断该应用是否属于他
				List<UserBackuserRelation> uburelist = uburService.getUserBackuserList(curradmin.getUserId());
				String ids = "";
				for (UserBackuserRelation ubur : uburelist) {
					ids += ubur.getUserId() + ",";
				}
				ApplicationCommon getapp = appService.confirmCurrAPPbelong(loginPlat2, Arrays.asList(ids.split(",")));
				if (getapp == null) {
					output(response, JsonUtil.buildFalseJson("1", "没有查看该应用的权限!"));
				} else {
					int rowsCount = withdrawService.countAllWithdrawRecord(uwcash, loginPlat2, userKeyWords,
							applyStartTime, applyEndTime, dealStartTime, dealEndTime);
					List<UserWithdrawCash> dataList = withdrawService.findWithRecordByCriteria(uwcash, loginPlat2,
							userKeyWords, applyStartTime, applyEndTime, dealStartTime, dealEndTime, (page - 1) * limit,
							limit);
					output(response, JsonUtil.buildJson2(dataList, rowsCount, limit));
				}
			}
		}*/
	}

	@RequestMapping("/dealWithdraw")
	public void ManagerdealWithdraw(HttpServletRequest request, HttpServletResponse response, Integer withdrawId) {
		UserWithdrawCash currwithdraw = withdrawService.getWithdrawRecordById(withdrawId);
		if (currwithdraw == null) {
			output(response, JsonUtil.buildFalseJson("1", "该提现不存在!"));
		} else {
			if (currwithdraw.getWithdrawState() == 1) {
				try {
					ApplicationCommon currapp = appService.getApplicationById(currwithdraw.getLoginPlat());
					UserCommon withdrawUser = userService.getUserByUserId(currwithdraw.getUserId());
					String path = request.getServletContext().getRealPath("/")+"/certificate/apiclient_cert.p12";
					GainSharingCommon currsharing = gainSharingService.getTheAcqierementSharing();
					double realFee = (1 - currsharing.withdrawPersent) * currwithdraw.getWithdrawFee();
					Map<String, String> resultmap = ClientCustomSSL.companyPay(path, currwithdraw.getOutTradeNo(),
							withdrawUser.getOpenId(), realFee, currwithdraw.getWithdrawIp(),
							currwithdraw.getWithdrawRealName(), currapp.getAppId(), currapp.getMchid());
					String result = addReturnCodeFunction(resultmap);
					if ("SUCCESS".equals(result)) {
						currwithdraw.setWithdrawState(2);
						currwithdraw.setDealTime(new Date());
						withdrawService.updateWithDrawInfo(currwithdraw);
						withdrawUser.setWalletBalance(withdrawUser.getWalletBalance() - currwithdraw.getWithdrawFee());
						userService.commonInfoUpdate(withdrawUser);
						UserWalletDiary witherDiary = new UserWalletDiary();
						witherDiary.setCreateTime(new Date());
						witherDiary.setDiaryTitle("提现");
						witherDiary.setDiaryTitle("您在系统中提现" + currwithdraw.getWithdrawFee() + "元，系统扣除手续费"
								+ currsharing.getWithdrawPersent() * 100 + "%");
						witherDiary.setIncomeExpend(0);// 支出
						witherDiary.setTotalFee(currwithdraw.getWithdrawFee());
						witherDiary.setUserId(currwithdraw.getUserId());
						walletdiaryService.addNewDiary(witherDiary);
						output(response, JsonUtil.buildFalseJson("0", "处理成功!"));
					} else {
						log.info(currwithdraw.getWithdrawId() + "处理提现失败,失败原因" + result);
						output(response, JsonUtil.buildFalseJson("4", "处理失败,失败原因:" + result));
					}
				} catch (Exception e) {
					output(response, JsonUtil.buildFalseJson("2", "处理失败，请稍后重试!"));
				}
			} else {
				output(response, JsonUtil.buildFalseJson("3", "该提现已处理!"));
			}
		}
	}

	@RequestMapping("/dealBatch")
	public void ManagerdealBatchWithdraw(HttpServletRequest request, HttpServletResponse response, String withdrawIds) {

		List<UserWithdrawCash> withdrawList = withdrawService
				.findWithdrawRecordById(Arrays.asList(withdrawIds.split(",")));
		String dealedIds = "";
		String dealFaultIds = "";
		String withdrawSuccessIds = "";
		String defaultResult = "";
		String notExistsresultMsg = "";
		String dealFaultresultMsg = "";
		String withdrawSuccessMsg = "";
		for (UserWithdrawCash currwithdraw : withdrawList) {
			if (currwithdraw.getWithdrawState() == 1) {
				try {
					GainSharingCommon currsharing = gainSharingService.getTheAcqierementSharing();
					ApplicationCommon currapp = appService.getApplicationById(currwithdraw.getLoginPlat());
					UserCommon withdrawUser = userService.getUserByUserId(currwithdraw.getUserId());
					String path = request.getServletContext().getRealPath("/")+"/certificate/apiclient_cert.p12";
					Map<String, String> resultmap = ClientCustomSSL.companyPay(path, currwithdraw.getOutTradeNo(),
							withdrawUser.getOpenId(), currwithdraw.getWithdrawFee(), currwithdraw.getWithdrawIp(),
							currwithdraw.getWithdrawRealName(), currapp.getAppId(), currapp.getMchid());
					String result = addReturnCodeFunction(resultmap);
					if ("SUCCESS".equals(result)) {
						currwithdraw.setWithdrawState(2);
						currwithdraw.setDealTime(new Date());
						withdrawService.updateWithDrawInfo(currwithdraw);
						UserWalletDiary witherDiary = new UserWalletDiary();
						witherDiary.setCreateTime(new Date());
						witherDiary.setDiaryTitle("提现");
						witherDiary.setDiaryTitle("您在系统中提现" + currwithdraw.getWithdrawFee() + "元，系统扣除手续费"
								+ currsharing.getWithdrawPersent() * 100 + "%");
						witherDiary.setIncomeExpend(0);// 支出
						witherDiary.setTotalFee(currwithdraw.getWithdrawFee());
						witherDiary.setUserId(currwithdraw.getUserId());
						walletdiaryService.addNewDiary(witherDiary);
						withdrawSuccessIds += currwithdraw.getWithdrawId() + ",";
					} else {
						dealFaultIds += currwithdraw.getWithdrawId() + ",";
						defaultResult = result;
						log.info(currwithdraw.getWithdrawId() + "处理提现失败,失败原因" + result);
					}
				} catch (Exception e) {
					dealFaultIds += currwithdraw.getWithdrawId() + ",";
				}
			} else {
				dealedIds += currwithdraw.getWithdrawId() + ",";
			}
		}
		if (!"".equals(dealedIds)) {
			notExistsresultMsg = dealedIds + "已处理过!";
		}
		if (!"".equals(dealFaultIds)) {
			dealFaultresultMsg = dealFaultIds + "提现失败,失败原因：" + defaultResult + "!";
		}
		if (!"".equals(withdrawSuccessIds)) {
			withdrawSuccessMsg = withdrawSuccessIds + "提现成功!";
		}
		// System.out.println(withdrawSuccessMsg + "\n" + notExistsresultMsg +
		// "\n" + dealFaultresultMsg);
		output(response, JsonUtil.buildFalseJson("0", withdrawSuccessMsg + notExistsresultMsg + dealFaultresultMsg));
	}

	private String addReturnCodeFunction(Map<String, String> map) {
		WithdrawReturnCode returncode = new WithdrawReturnCode();
		returncode.setDeviceInfo(map.get("device_info"));
		returncode.setErrCode(map.get("err_code"));
		returncode.setErrCodeDes(map.get("err_code_des"));
		returncode.setMchAppid(map.get("mch_appid"));
		returncode.setMchid(map.get("mchid"));
		returncode.setNonceStr(map.get("nonce_str"));
		returncode.setPartnerTradeNo(map.get("partner_trade_no"));
		returncode.setPaymentNo(map.get("payment_no"));
		returncode.setPaymentTime(map.get("payment_time"));
		returncode.setResultCode(map.get("result_code"));
		returncode.setReturnMsg(map.get("return_msg"));
		int id = wreturnService.addWithdrawResultCode(returncode);
		if (id > 0 && "SUCCESS".equals(returncode.getResultCode()) && "SUCCESS".equals(map.get("return_code"))) {
			return "SUCCESS";
		} else {
			return map.get("err_code_des");
		}
	}
}
