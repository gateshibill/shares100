package com.cofc.controller.examrecord;

import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cofc.pojo.ExamRecord;
import com.cofc.service.ExamRecordService;
import com.cofc.util.BaseUtil;
import com.cofc.util.JsonUtil;

@Controller
@RequestMapping("/wx/exam")
public class WXExamRecordController extends BaseUtil{
	@Resource
	private ExamRecordService recordService;
	@RequestMapping("/addRecord")
	public void addRecord(HttpServletResponse response,ExamRecord record){
		try {
			record.setCreateTime(new Date());
			record.setIsRemove(0);
			recordService.addExamRecord(record);
			output(response,JsonUtil.buildFalseJson("0","插入记录成功"));
		} catch (Exception e) {
			output(response,JsonUtil.buildFalseJson("1","插入记录失败"));
		}
		
	}
	@RequestMapping("/getRecord")
	public void getRecord(HttpServletResponse response,ExamRecord record){
		if(record.getActivityId() == null){
			output(response,JsonUtil.buildFalseJson("1","未知活动"));
		}else{
			if(record.getUserId() == null){
				output(response,JsonUtil.buildFalseJson("1","未知用户"));
			}else{
				if(record.getLoginPlat() == null){
					output(response,JsonUtil.buildFalseJson("1","未知应用"));
				}else{
					record.setIsRemove(0);
					int count = recordService.getExamRecordCount(record);
					if(count > 0){
						output(response,JsonUtil.buildFalseJson("1","已做过该活动了"));
					}else{
						output(response,JsonUtil.buildFalseJson("0","可以做活动"));
					}
				}
			}
		}
	}
}
