package com.cofc.controller.operate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.w3c.dom.stylesheets.LinkStyle;

import com.cofc.pojo.OperateLog;
import com.cofc.service.OperateLogService;
import com.cofc.util.BaseUtil;
import com.cofc.util.JsonUtil;

@Controller
@RequestMapping("/log")
public class OperateLogController extends BaseUtil {

	@Resource
	private OperateLogService logService;
	
	//后台日志列表
	@RequestMapping("/operateLogList")
	public ModelAndView operateLogList(ModelAndView mView){
		mView.setViewName("operatelogManage/logList");
		return mView;
	}
	
	@RequestMapping("/shoeLogList")
	public void shoeLogList(HttpServletResponse response,Integer page,Integer limit,String dateRange,String operateUser) throws Exception{
		if (page == null) {
			page = 1;
		}
		if (limit == null) {
			limit = 20;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat startSdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		SimpleDateFormat endSdf = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
		String startTime = null;
		String endTime = null;
		if (dateRange != null && !dateRange.equals("")) {
			String[] myData = dateRange.split(" -| ");
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
		map.put("operateUser", operateUser);
		map.put("limit", limit);
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		List<OperateLog> logList = logService.getOperateLogList(map);
		int count = logService.getOperateLogCout(map);
		output(response, JsonUtil.buildJsonByTotalCount(logList, count));
	}
	//删除日志
	@RequestMapping("/deleteLog")
	public void deleteLog(HttpServletResponse response,Integer logId){
		try {
			logService.deleteLog(logId);
			output(response, JsonUtil.buildFalseJson("0", "删除日志成功!"));
		} catch (Exception e) {
			output(response, JsonUtil.buildFalseJson("1", "删除日志失败!"));
		}
	}
}
