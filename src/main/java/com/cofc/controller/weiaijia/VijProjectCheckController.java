package com.cofc.controller.weiaijia;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cofc.pojo.vij.ProjectCheck;
import com.cofc.pojo.vij.ProjectCheckItem;
import com.cofc.service.vij.ProjectCheckItemService;
import com.cofc.service.vij.ProjectCheckService;
import com.cofc.util.BaseUtil;
import com.cofc.util.JsonUtil;
import com.thoughtworks.xstream.mapper.Mapper.Null;

@Controller
@RequestMapping("/pCheck")
public class VijProjectCheckController extends BaseUtil{

	@Resource 
	private ProjectCheckService pCheckService;
	@Resource
	private ProjectCheckItemService pCheckItemService;
	public static Logger log = Logger.getLogger("ProjectCheckController");
	
	/***
	 *查询方法
	 * @param response
	 * @param pjCheck
	 * @param page
	 * @param limit
	 */
	@RequestMapping("/queryProjectCheckList")
	public void queryProjectCheckList(HttpServletResponse response,ProjectCheck pjCheck,Integer page,Integer limit) {
		if (page == null) {
			page =1;
		}
		if (limit ==null) {
			limit =20;
		}
		int count=pCheckService.getProjectCheckCount(pjCheck);
		List<ProjectCheck> list = pCheckService.queryProjectCheckList(pjCheck, (page-1)*limit, limit);
		output(response, JsonUtil.buildJsonByTotalCount(list, count));
	}
	/**
	 * 添加
	 * @param response
	 * @param pjCheck
	 */
		@RequestMapping("/addProjectCheck")
	public void addProjectCheck(HttpServletResponse response,ProjectCheck pjCheck) {
		pjCheck.setCheckTime(new Date());
		pCheckService.addProjectCheck(pjCheck);
		output(response, JsonUtil.buildFalseJson("0", "添加成功"));
	}
		/***
		 * 修改
		 * @param response
		 * @param pjCheck
		 * @param checkId
		 */
		@RequestMapping("/updateProjectCheck")
		public void updateProjectCheck(HttpServletResponse response,ProjectCheck pjCheck,Integer checkId) {
			pCheckService.updateProjectCheck(pjCheck);
			output(response, JsonUtil.buildFalseJson("0", "修改成功"));
			}
		/***
		 * 删除方法
		 * @param response
		 * @param checkId
		 */
			@RequestMapping("/deleteProjectCheck")
		public void deleteProjectCheck(HttpServletResponse response,Integer checkId) {
			pCheckService.deleteProjectCheck(checkId);
			output(response, JsonUtil.buildFalseJson("0", "删除成功"));
		}
			/***
			 * 详情
			 * @param response
			 * @param checkId
			 */
			@RequestMapping("/getInfoByid")
		public void getInfoByid(HttpServletResponse response,Integer checkId) {
			ProjectCheck pjCheck = pCheckService.getinfoByid(checkId);
			List<ProjectCheck> list = new ArrayList<>();
			if (pjCheck != null) {
				list.add(pjCheck);
			}
			output(response, JsonUtil.buildJson(list));
		}
			
			/****
			 * 验收明细表
			 */
			
			/***
			 * 明细表添加
			 * @param response
			 * @param pCheckItem
			 */
			@RequestMapping("/addProjectCheckItem")
			public void addProjectCheckItem(HttpServletResponse response,ProjectCheckItem pCheckItem) {
				pCheckItemService.addProjectCheckItem(pCheckItem);
				output(response, JsonUtil.buildFalseJson("0", "添加成功"));
			}
			/***
			 * 修改
			 * @param response
			 * @param pCheckItem
			 */
			@RequestMapping("/updateProjectCheckItem")
		 public void updateProjectCheckItem(HttpServletResponse response,ProjectCheckItem pCheckItem) {
			 pCheckItemService.updateProjectCheckItem(pCheckItem);
			 output(response, JsonUtil.buildFalseJson("0", "修改成功"));
		 }
			/***
			 * 删除方法
			 * @param response
			 * @param itemId
			 */
		 @RequestMapping("/deleteProjectCheckItem")
		 public void deleteProjectCheckItem(HttpServletResponse response,Integer itemId) {
			 pCheckItemService.deleteProjectCheckItem(itemId);
			 output(response, JsonUtil.buildFalseJson("0", "删除成功"));
		 }
		 /***
		  * 搜索
		  * @param response
		  * @param pCheckItem
		  * @param page
		  * @param limit
		  */
		 @RequestMapping("/getProjectCheckItemList")
		 public void getProjectCheckItemList(HttpServletResponse response, ProjectCheckItem pCheckItem,Integer page,Integer limit) {
			 if (page == null) {
				page =1;
			}
			 if (limit ==null) {
				limit =20;
			}
			 int count = pCheckItemService.getProjectCheckItemCount(pCheckItem);
			 List<ProjectCheckItem> list = pCheckItemService.getProjectCheckItemList(pCheckItem, (page-1)*limit, limit);
			 output(response, JsonUtil.buildJsonByTotalCount(list, count));
		 }
		 /***
		  * 详情
		  */
		 @RequestMapping("/getInfoById")
		 public void getInfoById(HttpServletResponse response,Integer itemId) {
			 ProjectCheckItem pCheckItem = pCheckItemService.getInfoById(itemId);
			 List<ProjectCheckItem> list = new ArrayList<>();
			 if (pCheckItem !=null) {
				list.add(pCheckItem);
			}
			 output(response, JsonUtil.buildJson(list));
		 }
}
