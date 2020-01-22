package com.cofc.controller.sharetask;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cofc.pojo.TaskCommon;
import com.cofc.pojo.TaskCompete;
import com.cofc.pojo.TaskPayOrder;
import com.cofc.pojo.TaskPublishContent;
import com.cofc.pojo.TaskPublishReason;
import com.cofc.pojo.TaskSatisfiedService;
import com.cofc.service.TaskCommonService;
import com.cofc.service.TaskCompeteService;
import com.cofc.service.TaskPayOrderService;
import com.cofc.service.TaskPublishContentService;
import com.cofc.service.TaskPublishReasonService;
import com.cofc.service.TaskSatisfiedServiceService;
import com.cofc.util.BaseUtil;
import com.cofc.util.JsonUtil;

@Controller
@RequestMapping("/sharetask")
public class ShareTaskController extends BaseUtil {

	@Resource
	private TaskCommonService taskservice;
	@Resource
	private TaskPublishContentService contentservice;
	@Resource
	private TaskPublishReasonService reasonservice;
	@Resource
	private TaskSatisfiedServiceService serviceservice;
	@Resource
	private TaskCompeteService taskcompeteservice;
	@Resource
	private TaskPayOrderService payorderservice;

	/**
	 * 进入任务列表页面
	 * 
	 * @param mv
	 * @param request
	 * @return
	 */
	@RequestMapping("gotasklist")
	public ModelAndView gotaskList(ModelAndView mv, HttpServletRequest request) {
		mv.setViewName("/taskManage/tasklist");
		return mv;
	}

	/**
	 * 共享任务列表
	 * 
	 * @param response
	 * @param page
	 * @param limit
	 * @param task
	 */
	@RequestMapping("/tasklist")
	public void taskList(HttpServletResponse response, Integer page, Integer limit, TaskCommon task) {
		if (page == null) {
			page = 1;
		}
		if (limit == 1) {
			limit = 10;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("page", ((page - 1) * limit));
		map.put("limit", limit);
		map.put("task", task);
		int rowcount = taskservice.getTaskCount(map);
		List<TaskCommon> tasks = taskservice.getTaskList(map);
		output(response, JsonUtil.buildJsonByTotalCount(tasks, rowcount));
	}

	/**
	 * 进入任务内容列表
	 * 
	 * @param mv
	 * @param request
	 * @return
	 */
	@RequestMapping("gotaskcontentlist")
	public ModelAndView gotaskcontentList(ModelAndView mv, HttpServletRequest request) {
		mv.setViewName("/taskManage/taskcontentlist");
		return mv;
	}

	/**
	 * 任务内容列表
	 * 
	 * @param response
	 * @param page
	 * @param limit
	 * @param content
	 */
	@RequestMapping("/taskcontentlist")
	public void taskContentList(HttpServletResponse response, Integer page, Integer limit, TaskPublishContent content) {
		if (page == null) {
			page = 1;
		}
		if (limit == null) {
			limit = 15;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("page", (page - 1) * limit);
		map.put("limit", limit);
		map.put("content", content);
		int rowcount = contentservice.getTaskContentCount(map);
		List<TaskPublishContent> contents = contentservice.getAllContent(map);
		output(response, JsonUtil.buildJsonByTotalCount(contents, rowcount));
	}

	/**
	 * 进入任务原因列表页面
	 * 
	 * @param mv
	 * @param request
	 * @return
	 */
	@RequestMapping("/gotaskreasonlist")
	public ModelAndView gotaskreasonList(ModelAndView mv, HttpServletRequest request) {
		mv.setViewName("/taskManage/taskreasonlist");
		return mv;
	}

	/**
	 * 任务原因列表
	 * 
	 * @param response
	 * @param page
	 * @param limit
	 * @param reason
	 */
	@RequestMapping("/taskreasonlist")
	public void taskReasonList(HttpServletResponse response, Integer page, Integer limit, TaskPublishReason reason) {
		if (page == null) {
			page = 1;
		}
		if (limit == null) {
			limit = 15;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("page", ((page - 1) * limit));
		map.put("limit", limit);
		map.put("reason", reason);
		int rowcount = reasonservice.getTaskReasonCount(map);
		List<TaskPublishReason> reasons = reasonservice.getAllReasonList(map);
		output(response, JsonUtil.buildJsonByTotalCount(reasons, rowcount));

	}

	/**
	 * 进入额外服务列表页面
	 * 
	 * @param mv
	 * @param request
	 * @return
	 */
	@RequestMapping("/gotaskservicelist")
	public ModelAndView gotaskserviceList(ModelAndView mv, HttpServletRequest request) {
		mv.setViewName("/taskManage/taskservicelist");
		return mv;
	}

	/**
	 * 额外服务列表
	 * 
	 * @param response
	 * @param page
	 * @param limit
	 * @param service
	 */
	@RequestMapping("/taskservicelist")
	public void taskServiceList(HttpServletResponse response, Integer page, Integer limit,
			TaskSatisfiedService service) {
		if (page == null) {
			page = 1;
		}
		if (limit == null) {
			limit = 15;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("page", ((page - 1) * limit));
		map.put("limit", limit);
		map.put("service", service);
		int rowcount = serviceservice.getTaskServiceCount(map);
		List<TaskSatisfiedService> services = serviceservice.getTaskServiceList(map);
		output(response, JsonUtil.buildJsonByTotalCount(services, rowcount));
	}

	/**
	 * 进入添加任务内容页面
	 * 
	 * @param mv
	 * @return
	 */
	@RequestMapping("/addtaskcontent")
	public ModelAndView addTaskContent(ModelAndView mv) {
		mv.setViewName("/taskManage/addtaskcontent");
		return mv;
	}

	/**
	 * 实现添加任务内容
	 * 
	 * @param response
	 * @param content
	 */
	@RequestMapping("/doaddtaskcontent")
	public void doaddTaskContent(HttpServletResponse response, String content) {
		// 判断是否已经存在
		TaskPublishContent contents = contentservice.isAlreadyName(content);
		if (contents != null) {
			output(response, JsonUtil.buildFalseJson("1", "任务内容已经存在"));
		} else {
			TaskPublishContent co = new TaskPublishContent();
			co.setContentText(content);
			contentservice.addTaskContent(co);
			output(response, JsonUtil.buildFalseJson("0", "添加任务内容成功"));

		}
	}

	/**
	 * 进入添加任务原因页面
	 * 
	 * @param mv
	 * @return
	 */
	@RequestMapping("/addtaskreason")
	public ModelAndView addTaskReason(ModelAndView mv) {
		mv.setViewName("/taskManage/addtaskreason");
		return mv;
	}

	/**
	 * 实现添加任务原因
	 * 
	 * @param response
	 * @param reason
	 */
	@RequestMapping("doaddtaskreason")
	public void doaddTaskReason(HttpServletResponse response, String reason) {
		TaskPublishReason reasons = reasonservice.isAlreadyName(reason);
		if (reasons != null) {
			output(response, JsonUtil.buildFalseJson("1", "该任务原因已经存在"));
		} else {
			TaskPublishReason r = new TaskPublishReason();
			r.setReasonName(reason);
			reasonservice.addTaskReason(r);
			output(response, JsonUtil.buildFalseJson("0", "添加任务原因成功"));

		}
	}

	/**
	 * 进入额外服务页面
	 * 
	 * @param mv
	 * @return
	 */
	@RequestMapping("/addtaskservice")
	public ModelAndView addTaskService(ModelAndView mv) {
		mv.setViewName("/taskManage/addtaskservice");
		return mv;
	}

	/**
	 * 实现添加额外服务
	 * 
	 * @param response
	 * @param service
	 * @param type
	 */
	@RequestMapping("/doaddtaskservice")
	public void doaddTaskService(HttpServletResponse response, String service, Integer type) {
		TaskSatisfiedService ser = serviceservice.isAlreadyName(service);
		if (ser != null) {
			output(response, JsonUtil.buildFalseJson("1", "该服务已存在"));
		} else {
			TaskSatisfiedService s = new TaskSatisfiedService();
			s.setServiceName(service);
			s.setServiceType(type);
			serviceservice.addService(s);
			output(response, JsonUtil.buildFalseJson("0", "添加服务成功"));
		}
	}

	/**
	 * 进入任务详情以及查看抢单列表
	 * 
	 * @param mv
	 * @param request
	 * @param taskId
	 * @return
	 */
	@RequestMapping("/gotaskdetail")
	public ModelAndView goTaskDetail(ModelAndView mv, HttpServletRequest request, Integer taskId) {
		TaskCommon taskCommon = taskservice.getTaskById(taskId);
		List<TaskCompete> taskCompete = taskcompeteservice.findAllCompetedRecord(taskId);
		mv.addObject("task", taskCommon);
		mv.addObject("compete", taskCompete);
		mv.setViewName("/taskManage/taskdetail");
		return mv;
	}

	/**
	 * 删除任务内容
	 * 
	 * @param response
	 * @param contentId
	 */
	@RequestMapping("/deltaskcontent")
	public void delTaskContent(HttpServletResponse response, Integer contentId) {
		if (contentId == null) {
			output(response, JsonUtil.buildFalseJson("1", "传递参数非法"));
		} else {
			contentservice.delContent(contentId);
			output(response, JsonUtil.buildFalseJson("0", "删除成功"));
		}
	}

	/**
	 * 删除任务原因
	 * 
	 * @param response
	 * @param reasonId
	 */
	@RequestMapping("/deltaskreason")
	public void delTaskReason(HttpServletResponse response, Integer reasonId) {
		if (reasonId == null) {
			output(response, JsonUtil.buildFalseJson("1", "传递参数非法"));
		} else {
			reasonservice.delReason(reasonId);
			output(response, JsonUtil.buildFalseJson("0", "删除任务原因成功"));
		}
	}

	/**
	 * 删除任务服务
	 * 
	 * @param response
	 * @param serviceId
	 */
	@RequestMapping("/deltaskservice")
	public void delTaskService(HttpServletResponse response, Integer serviceId) {
		if (serviceId == null) {
			output(response, JsonUtil.buildFalseJson("1", "传递参数非法"));
		} else {
			serviceservice.delService(serviceId);
			output(response, JsonUtil.buildFalseJson("0", "删除服务成功"));
		}
	}

	/**
	 * 进入修改任务内容页面
	 * 
	 * @param mv
	 * @param request
	 * @return
	 */
	@RequestMapping("/updatetaskcontent")
	public ModelAndView updateTaskContent(ModelAndView mv, HttpServletRequest request, Integer contentId) {
		TaskPublishContent content = contentservice.getContentDetail(contentId);
		mv.addObject("content", content);
		mv.setViewName("/taskManage/updatetaskcontent");
		return mv;
	}

	/**
	 * 实现修改任务内容
	 * 
	 * @param response
	 * @param contentName
	 * @param contentId
	 */
	@RequestMapping("/doupdatetaskcontent")
	public void doupdateTaskContent(HttpServletResponse response, String contentName, Integer contentId) {
		// 判断是否存在
		TaskPublishContent content = contentservice.isAlreadyName(contentName);
		if (content != null) {
			output(response, JsonUtil.buildFalseJson("1", "该任务内容已经存在"));
		} else {
			contentservice.updateContent(contentName, contentId);
			output(response, JsonUtil.buildFalseJson("0", "修改任务内容成功"));
		}
	}

	/**
	 * 进入修改任务原因页面
	 * 
	 * @param mv
	 * @param request
	 * @return
	 */
	@RequestMapping("updatetaskreason")
	public ModelAndView updateTaskReason(ModelAndView mv, HttpServletRequest request, Integer reasonId) {
		TaskPublishReason reason = reasonservice.getReasonDetail(reasonId);
		mv.addObject("reason", reason);
		mv.setViewName("/taskManage/updatetaskreason");
		return mv;
	}

	/**
	 * 实现修改任务原因
	 * 
	 * @param response
	 * @param reasonName
	 * @param reasonId
	 */
	@RequestMapping("/doupdatetaskreason")
	public void doupdateTaskReason(HttpServletResponse response, String reasonName, Integer reasonId) {
		TaskPublishReason reason = reasonservice.isAlreadyName(reasonName);
		if (reason != null) {
			output(response, JsonUtil.buildFalseJson("1", "该任务原因已经存在"));
		} else {
			reasonservice.updataReason(reasonName, reasonId);
			output(response, JsonUtil.buildFalseJson("0", "编辑任务原因成功"));
		}
	}

	/**
	 * 进入修改服务页面
	 * 
	 * @param mv
	 * @param request
	 * @param serviceId
	 * @return
	 */
	@RequestMapping("/updatetaskservice")
	public ModelAndView updateTaskService(ModelAndView mv, HttpServletRequest request, Integer serviceId) {
		TaskSatisfiedService service = serviceservice.getServiceDetail(serviceId);
		mv.addObject("service", service);
		mv.setViewName("/taskManage/updatetaskservice");
		return mv;
	}

	/**
	 * 实现修改服务
	 * 
	 * @param response
	 * @param serviceName
	 * @param serviceId
	 * @param serviceType
	 */
	@RequestMapping("doupdatetaskservice")
	public void doupdateTaskService(HttpServletResponse response, String serviceName, Integer serviceId,
			Integer serviceType) {
		TaskSatisfiedService service = serviceservice.isOnlyOneService(serviceName, serviceType);
		if (service != null) {
			output(response, JsonUtil.buildFalseJson("1", "该服务已经存在"));
		} else {
			serviceservice.updateService(serviceName, serviceType, serviceId);
			output(response, JsonUtil.buildFalseJson("0", "修改成功"));
		}
	}

	/**
	 * 进入任务支付列表
	 * 
	 * @param mv
	 * @return
	 */
	@RequestMapping("/gotaskpaylist")
	public ModelAndView gotaskPayList(ModelAndView mv) {
		mv.setViewName("/taskManage/taskpaylist");
		return mv;
	}

	/**
	 * 获取支付列表
	 * 
	 * @param response
	 * @param page
	 * @param limit
	 * @param payorder
	 */
	@RequestMapping("/gettaskpaylist")
	public void gettaskPayList(HttpServletResponse response, Integer page, Integer limit, TaskPayOrder payorder) {
		if (page == null) {
			page = 1;
		}
		if (limit == null) {
			limit = 15;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("page", (page - 1) * limit);
		map.put("limit", limit);
		map.put("payorder", payorder);
		int rowcount = payorderservice.getPayCount(map);
		List<TaskPayOrder> payorderlist = payorderservice.getAllTaskPayList(map);
		output(response, JsonUtil.buildJsonByTotalCount(payorderlist, rowcount));
	}

	@RequestMapping("/batchDelTask")
	public void batchDelTask(HttpServletResponse response, String taskIds) {
		List<TaskCommon> taskList = taskservice.getTaskIds(Arrays.asList(taskIds.split(",")));
		if (taskList != null && !taskList.isEmpty()) {
			for (TaskCommon ta : taskList) {
				taskservice.deleteTask(ta.getTaskId());
			}
			output(response, JsonUtil.buildFalseJson("0", "删除成功!"));
		} else {
			output(response, JsonUtil.buildFalseJson("1", "没有要删除的数据!"));
		}
	}
}
