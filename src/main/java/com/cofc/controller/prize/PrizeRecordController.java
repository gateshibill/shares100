package com.cofc.controller.prize;


import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cofc.pojo.BackUser;
import com.cofc.pojo.OperateLog;
import com.cofc.pojo.PrizeRecord;
import com.cofc.pojo.UserCommon;
import com.cofc.service.OperateLogService;
import com.cofc.service.PrizeRecordService;
import com.cofc.service.UserCommonService;
import com.cofc.util.BaseUtil;
import com.cofc.util.JsonUtil;

@Controller
@RequestMapping("/prize")
public class PrizeRecordController extends BaseUtil{

	@Resource
	private PrizeRecordService prizeRecordService;
	@Resource
	private OperateLogService logService;
	@Resource
	private UserCommonService userService;
	
	
	//中奖列表页面
	@RequestMapping("/winningList")
	public ModelAndView winningList(ModelAndView modelAndView){
		modelAndView.setViewName("/prizeMange/winningList");
		return modelAndView;
	}
	
	//处理中奖申请列表
	@RequestMapping("/handlePrizeList")
	public ModelAndView handlePrizeList(ModelAndView modelAndView){
		modelAndView.setViewName("/prizeMange/handlePrizeList");
		return modelAndView;
	}
	
	//ajax查中奖记录
	@RequestMapping("/showPrizeList")
	public void showPrizeList(HttpServletRequest request,HttpServletResponse response,
			String userKeyWords, String dataRange,Integer type ,Integer page, Integer limit,PrizeRecord record) throws Exception{
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
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("page", (page-1)*limit);
		map.put("limit", limit);
		map.put("userKeyWords", userKeyWords);
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		map.put("record", record);
		map.put("type", type);
		List<PrizeRecord> records = prizeRecordService.getshowWinningList(map);
		int count = prizeRecordService.getgetshowWinningCount(map);
		output(response, JsonUtil.buildJsonByTotalCount(records, count));
	}
	//处理中奖
	@RequestMapping("/updatePeizeId")
	public void updatePeizeId(HttpServletResponse response,PrizeRecord record,HttpServletRequest request){
		BackUser bu = (BackUser) request.getSession().getAttribute("loginer");
		PrizeRecord pr = prizeRecordService.getPrizeRecordById(record.getRecordId());
		UserCommon user = userService.getUserByUserId(pr.getUserId());
		OperateLog log = new OperateLog();
		log.setOperateName("【"+bu.getUserName()+"】"+"处理用户"+"【"+user.getNickName()+"中奖的奖品名为"+record.getPrizeName());
		log.setOperateTime(new Date());
		log.setOperateUser(bu.getUserName());
		try {
			log.setOperateResult(1);
			logService.addOperateLog(log);
			prizeRecordService.updatePrizeRecord(record);
			output(response, JsonUtil.buildFalseJson("0", "处理成功!"));
		} catch (Exception e) {
			log.setOperateResult(2);
			logService.addOperateLog(log);
			output(response, JsonUtil.buildFalseJson("1", "处理失败!"));
		}
	}
	//删除中奖人记录
	@RequestMapping("/deletePeizeId")
	public void deletePeizeId(HttpServletResponse response,Integer recordId,HttpServletRequest request){
		BackUser bu = (BackUser) request.getSession().getAttribute("loginer");
		PrizeRecord record = prizeRecordService.getPrizeRecordById(recordId);
		UserCommon user = userService.getUserByUserId(record.getUserId());
		OperateLog log = new OperateLog();
		log.setOperateName("【"+bu.getUserName()+"】"+"删除用户"+"【"+user.getNickName()+"】中奖的奖品名为"+record.getPrizeName());
		log.setOperateTime(new Date());
		log.setOperateUser(bu.getUserName());
		if (record.getStatus() !=3) {
			log.setOperateResult(2);
			logService.addOperateLog(log);
			output(response, JsonUtil.buildFalseJson("1", "该用户还未兑换奖品，暂时不能删除!"));
		} else {
			prizeRecordService.deletePrizeRecord(record);
			log.setOperateResult(1);
			logService.addOperateLog(log);
			output(response, JsonUtil.buildFalseJson("0", "删除成功!"));
		}
	}
	
	//批量处理奖品
	@RequestMapping("/batchDelPrize")
	public void batchDelPrize(HttpServletResponse response,String recordIds){
		List<PrizeRecord> prizeList = prizeRecordService.getRecordIds(Arrays.asList(recordIds.split(",")));
		if (prizeList != null && !prizeList.isEmpty()) {
			for (PrizeRecord pr:prizeList) {
				pr.setStatus(3);
				prizeRecordService.updatePrizeRecord(pr);
			}
			output(response, JsonUtil.buildFalseJson("0", "处理成功!"));
		} else {
			output(response, JsonUtil.buildFalseJson("1", "没有要处理的数据!"));
		}
	}
}
