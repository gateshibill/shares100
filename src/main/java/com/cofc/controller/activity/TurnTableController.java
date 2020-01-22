package com.cofc.controller.activity;

//大转盘抽奖
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cofc.pojo.activity.TurnTable;
import com.cofc.pojo.activity.TurnTableRecord;
import com.cofc.service.activity.TurnTableRecordService;
import com.cofc.service.activity.TurnTableService;
import com.cofc.util.BaseUtil;
import com.cofc.util.JsonUtil;

//活动
@Controller
@RequestMapping("/wx/activity")
public class TurnTableController extends BaseUtil {
	@Resource
	private TurnTableService turnTableService;
	@Resource
	private TurnTableRecordService turnTableRecordService;

	public static Logger log = Logger.getLogger("TurnTableController");

	@RequestMapping("/getTurnTableList")
	public void getTurnTableList(HttpServletResponse response, Integer appId) {
		List<TurnTable> list = turnTableService.getTurnTableList(appId);
		output(response, JsonUtil.buildCustomJson("1", "success", list));
	}

	@RequestMapping("/playTurnTable")
	public void playTurnTable(HttpServletResponse response, Integer appId, Integer userId, Integer turnTableId) {
		TurnTable turntale = turnTableService.getTurnTable(appId, turnTableId);

		TurnTableRecord turnTableRecord = new TurnTableRecord();
		turnTableRecord.setAppId(appId);
		turnTableRecord.setUserId(userId);
		turnTableRecord.setTurntableId(turnTableId);
		turnTableRecord.setPrice(turntale.getPrice());
		turnTableRecord.setCreateTime(new Date());
		
		turntale.play(turnTableRecord);

		turnTableRecordService.addTurnTableRecord(turnTableRecord);

		output(response, JsonUtil.bulidObjectJson(turnTableRecord));
	}

	@RequestMapping("/getTurnTableRecordList")
	public void getTurnTableRecordList(HttpServletResponse response, Integer appId, Integer turnTableId,Integer page, Integer limit) {

		List<TurnTableRecord> list = turnTableRecordService.getTurnTableRecordList(appId, turnTableId,(page - 1) * limit,
				limit);
		output(response, JsonUtil.bulidObjectJson(list));
	}

}
