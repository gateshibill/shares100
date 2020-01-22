package com.cofc.controller.weiaijia.admin;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.cofc.pojo.UserCommon;
import com.cofc.pojo.vij.Adviser;
import com.cofc.pojo.vij.DecorateForecast;
import com.cofc.pojo.vij.Project;
import com.cofc.pojo.vij.ProjectCheck;
import com.cofc.pojo.vij.ProjectCheckItem;
import com.cofc.pojo.vij.ProjectComment;
import com.cofc.pojo.vij.ProjectDesign;
import com.cofc.pojo.vij.ProjectLog;
import com.cofc.pojo.vij.ProjectOffer;
import com.cofc.pojo.vij.ProjectOrder;
import com.cofc.pojo.vij.ProjectView;
import com.cofc.pojo.vij.ProjectWorkBigPlan;
import com.cofc.pojo.vij.ProjectWorkPlan;
import com.cofc.pojo.vij.ProjectWorkProcess;
import com.cofc.pojo.vij.StyleSubjectAnswer;
import com.cofc.service.ApplicationCommonService;
import com.cofc.service.UserCommonService;
import com.cofc.service.vij.AdviserService;
import com.cofc.service.vij.ChooseStyleService;
import com.cofc.service.vij.DecorateForecastService;
import com.cofc.service.vij.ProjectCheckItemService;
import com.cofc.service.vij.ProjectCheckService;
import com.cofc.service.vij.ProjectCommentService;
import com.cofc.service.vij.ProjectDesignService;
import com.cofc.service.vij.ProjectLogService;
import com.cofc.service.vij.ProjectOfferService;
import com.cofc.service.vij.ProjectOrderService;
import com.cofc.service.vij.ProjectService;
import com.cofc.service.vij.ProjectViewService;
import com.cofc.service.vij.ProjectWorkBigPlanService;
import com.cofc.service.vij.ProjectWorkPlanService;
import com.cofc.service.vij.ProjectWorkProcessService;
import com.cofc.service.vij.StyleSubjectAnswerService;
import com.cofc.util.BaseUtil;
import com.cofc.util.JsonUtil;
import com.cofc.util.SendMsgUtil;


/**
 * 后台施工方法接口
 * 
 * @author menghaoran
 *
 */
@Controller
@RequestMapping("/project")
public class ProjectController extends BaseUtil {
	@Resource
	private ProjectService projectService; // 总施工项目
	@Resource
	private AdviserService adviserService; //顾问 / 设计师
	@Resource
	private ApplicationCommonService applicationService; //应用
	@Resource
	private UserCommonService uCommonService;//用户
	@Resource
	private DecorateForecastService roomService; //量房数据
	@Resource
	private ChooseStyleService chooseStyleService;//用户选择风格
	@Resource
	private ProjectCheckService pCheckService;	//大类验收
	@Resource
	private ProjectCheckItemService pItemService;//验收水电
	@Resource
	private ProjectWorkProcessService processService;//施工
	@Resource
	private ProjectOrderService pOrderService;	//定金
	@Resource
	private ProjectCommentService pCommentService;//评论
	@Resource
	private ProjectLogService pLogService;//日志
	@Resource
	private ProjectWorkBigPlanService pbigplanService;//施工大项
	@Resource
	private ProjectWorkPlanService ProjectWorkPlanService; //施工小项（验收）
	@Resource
	private ProjectDesignService projectDesignService;	//设计稿
	@Resource
	private ProjectOfferService projectOfferService;	//软装/硬装报价
	@Resource
	private ProjectViewService pViewService;	//调整设计/还是报价
	@Resource
	private StyleSubjectAnswerService styleSubjectAnswerService;	//选择风格
	
	public static Logger log = Logger.getLogger("ProjectController");

	/**
	 * 进入列表页面
	 * 
	 * @param request
	 * @param mv
	 * @return
	 */
	@RequestMapping("/index")
	public ModelAndView index(HttpServletResponse response, ModelAndView mv,Project project,Integer userId) {
		mv.addObject("project",project);
		mv.addObject("userId",userId);
		mv.setViewName("weiaijia/project/index");
		return mv;
	}

	/**
	 * @param 搜索功能
	 * @param request
	 * @param response
	 * @param vijProject
	 * @return
	 */
	@RequestMapping("/queryProject")
	public void queryProject(HttpServletRequest request, HttpServletResponse response,Project vProject, Integer page,
			Integer limit) {
		if (page == null) {
			page = 1;
		}
		if (limit == null) {
			limit = 20;
		}
		int count = projectService.getProjectCount(vProject);
		// 查詢方法
		List<Project> list = projectService.queryProject(vProject, (page-1)*limit,limit);
		output(response, JsonUtil.buildJsonByTotalCount(list, count));
	}

	/**
	 * 进入新增页面
	 * 
	 * @param request
	 * @param mv
	 * @return
	 */
	
	@RequestMapping("/addProject")
	public ModelAndView addProject(HttpServletRequest request, HttpServletResponse response, ModelAndView mv,Adviser adviser,
			Integer userId,String phone) {
		List<Adviser> list = adviserService.getAdviserList(adviser, null, null);
		mv.addObject("list",list);
		mv.addObject("userId",userId);
		mv.setViewName("weiaijia/project/add");
		return mv;
	}

	/**
	 * 执行添加操作
	 * 
	 * @param response
	 * @param project
	 */
	@RequestMapping("/doAddProject")
	public void doAddProject(HttpServletResponse response, Project project, String projectNo) {
			if (project.getId() == null) {
				output(response, JsonUtil.buildFalseJson("1", "非法参数"));
			}else {
				// 随机生成时间加随机5位数
				SimpleDateFormat sDateFormat;
				sDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
				Date date = new Date();
				String str = sDateFormat.format(date);
				Random random = new Random();
				int rannum = (int) ((random.nextDouble() * (99999 - 10000 + 1)) + 10000); // 获取5为随机数
				projectNo = str + rannum; // s生成19位随机数
				project.setProjectNo(projectNo);
				
				project.setCreateTime(new Date());
				project.setLastTime(new Date());
				projectService.addProject(project);
				output(response, JsonUtil.buildFalseJson("0", "添加成功"));
			}
	}

	/**
	 * 进入编辑页面
	 * 
	 * @param request
	 * @param mv
	 * @param id
	 * @return
	 */
	@RequestMapping("/updateProject")
	public ModelAndView updateProject(HttpServletRequest request, ModelAndView mv, Integer id, UserCommon uCommon, 
			 Adviser adviser,String phone) {
		Project project = projectService.getInfoById(id);
		/*UserCommon userCommon = uCommonService.getUserByKeyWord(keyword,
	    		List<String> loginPlatList);
		mv.addObject("userCommon", userCommon);*/
		List<Adviser> list = adviserService.getAdviserList(adviser, null, null);
		mv.addObject("list",list);
		mv.addObject("project", project);
		mv.setViewName("weiaijia/project/edit");
		return mv;
	}

	/***
	 * 执行编辑
	 * 
	 * @param request
	 * @param response
	 * @param vProject
	 */
	@RequestMapping("/subjectProject")
	public void subjectsProject(HttpServletRequest request, HttpServletResponse response, Project vProject) {
		if (vProject == null) {
			output(response, JsonUtil.buildFalseJson("1", "你填写必填字段"));
		} else {
			if (vProject.getId()== null) {
				output(response,JsonUtil.buildFalseJson("1", "系统未能识别到该应用"));
				return;
			} else {
				if (vProject.getName() == null || vProject.getName().equals("")) {
					output(response, JsonUtil.buildFalseJson("1", "请输入项目名称"));
					return;
				} else {
					if (vProject.getStatus() == null) {
						output(response, JsonUtil.buildFalseJson("1", "请输入管理者"));
						return;
					} else {
						int count = projectService.checkIsAlready(vProject.getName(), vProject.getStatus(),
								vProject.getId());
						if (count > 0) {
							output(response, JsonUtil.buildFalseJson("1", "项目已存在，请重新输入"));
							return;
						}
					}
					projectService.updateProject(vProject);
					output(response, JsonUtil.buildFalseJson("0", "修改成功"));
				}
			}
		}
	}

	/***
	 * 删除功能
	 * 
	 * @param request
	 * @param mv
	 * @param id
	 * @return
	 */
	@RequestMapping("/delProiect")
	public void delProiect(HttpServletRequest request, HttpServletResponse response, Project vijProject, Integer id) {
			projectService.delectproject(id);
			output(response, JsonUtil.buildFalseJson("0", "删除成功"));
	}
	
	
	
	/***************************************完善项目流程步骤***********************************************/
	/**
	 * 去项目流程
	 * @param request
	 * @param mv
	 * @return
	 */
	@RequestMapping("/godetail")
	public ModelAndView godetail(HttpServletRequest request,ModelAndView mv,Integer projectId){
		Project project = projectService.getInfoById(projectId);
		if(project.getWaiterId() != null){//顾问
			Adviser guwen = adviserService.getInfoById(project.getWaiterId());
			mv.addObject("guwen", guwen);
		}
		if(project.getDesignerId() != null){//设计师
			Adviser design = adviserService.getInfoById(project.getDesignerId());
			mv.addObject("design", design);
		}
		//获取用户选择的风格
		StyleSubjectAnswer style = styleSubjectAnswerService.getInfoByProjectId(projectId);//每个项目只能对应一条数据
		mv.addObject("style", style);
		DecorateForecast room = roomService.getRoomByProjectId(projectId);
		mv.addObject("room", room);
		mv.addObject("project", project);

		ProjectOrder pOrder = pOrderService.getProjectOrderByid(projectId,1);
		mv.addObject("pOrder",pOrder);
		ProjectComment pComment = pCommentService.getProjectCommentByid(projectId);
		mv.addObject("pComment", pComment);
	
		//日志
		ProjectLog log1 = new ProjectLog();
		log1.setProjectId(projectId);
		int  logCount = pLogService.getProjectLogCount(log1);
		mv.addObject("logCount", logCount);
		//查询是否有调整设计稿/报价
		//用户反馈
		ProjectView projectView = new ProjectView();
		projectView.setProjectId(projectId);
		projectView.setViewType(1);
		int projectViewC = pViewService.getProjectViewCount(projectView);
		mv.addObject("projectViewC", projectViewC);
		projectView.setViewType(2);
		int projectViewB = pViewService.getProjectViewCount(projectView);
		mv.addObject("projectViewB", projectViewB);
		//是否上传设计稿
		List<ProjectDesign> projectDesign = new ArrayList<>();
		projectDesign = projectDesignService.getDesignByProjectId(projectId);
		mv.addObject("projectDesign", projectDesign);
		//是否上传报价
		List<ProjectOffer> offers = projectOfferService.getOfferByProjectId(projectId);
		mv.addObject("offers", offers);
		mv.setViewName("weiaijia/project/detail");
		return mv;
	}
	
	/***
	 * 进入业主房产信息
	 * @param response
	 * @param dForecast
	 * @return
	 */
	@RequestMapping("/decorateForecast")
	public ModelAndView decorateForecast(HttpServletResponse response,ModelAndView mv,Integer projectId) {
		if (projectId == null) {
			output(response, JsonUtil.buildFalseJson("1", "非法参数"));
		}
		DecorateForecast room = roomService.getRoomByProjectId(projectId);
		room.setIsDeal(0);
		mv.addObject("room", room);
		mv.setViewName("weiaijia/project/realestate");
		return mv;
	}
	/***
	 * 确认信息
	 */
	@RequestMapping("/doDecorateForecast")
	public void doDecorateForecast(HttpServletResponse response,DecorateForecast dForecast) {
				if (dForecast.getId() != null) {
					roomService.updateDecorateForecast(dForecast);
			output(response, JsonUtil.buildFalseJson("0", "确认成功"));
		}else {
			output(response, JsonUtil.buildFalseJson("1", "参数非法"));
		}
}
	/***
	 * 进入上传户型图
	 * @param response
	 * @param mv
	 * @param projectId
	 * @return
	 */
	@RequestMapping("/goUploadDecorateForecast")
	public ModelAndView goUploadDecorateForecast(HttpServletResponse response,ModelAndView mv,Integer projectId) {
		mv.addObject("projectId", projectId);
		DecorateForecast dForecast = roomService.getRoomByProjectId(projectId);
		mv.addObject("dForecast",dForecast);
		mv.setViewName("weiaijia/project/uploadDecorateForecast");
		return mv;
		
	}
	/***
	 * 添加户型图
	 * @param response
	 * @param dForecast
	 * @param projectId
	 */
	@RequestMapping("/addDecorateForecast")
	public void addDecorateForecast(HttpServletResponse response,DecorateForecast dForecast) {
		if (dForecast.getProjectId() == null) {
			output(response, JsonUtil.buildFalseJson("1", "非法参数"));
		}else {
			roomService.updateDecorateForecast(dForecast);
			if (dForecast != null) {
				if (dForecast.getMobilePhoneNo() != null && !dForecast.getMobilePhoneNo().equals("") && dForecast.getLfUserName() !=null && !dForecast.getLfUserName().equals("")) {
					SendMsgUtil.sendMsg(dForecast.getMobilePhoneNo(), "亲爱的"+dForecast.getLfUserName()+",户型图已上传，请注意查看！");
				}
			}
			output(response, JsonUtil.buildFalseJson("0", "添加成功"));
		}
	}
	
	/***
	 * 查看选择风格
	 * @param response
	 * @param mv
	 * @param projectId
	 * @return
	 */
	@RequestMapping("/getChooseStyle")
	public ModelAndView getChooseStyle(HttpServletResponse response,ModelAndView mv,Integer projectId) {
		if (projectId ==null) {
			output(response, JsonUtil.buildFalseJson("1", "非法参数"));
		}else {
			StyleSubjectAnswer style = styleSubjectAnswerService.getInfoByProjectId(projectId);//每个项目只能对应一条数据
			mv.addObject("style", style);
			mv.setViewName("weiaijia/project/intention");
		}
		return mv;
		}
	/***
	 * 确认风格
	 * @param response
	 * @param style
	 */
	@RequestMapping("/dogetChooseStyle")
	public void dogetChooseStyle(HttpServletResponse response,StyleSubjectAnswer style) {
		if (style.getId() != null) {
			styleSubjectAnswerService.updateAnswer(style);
			//更新项目状态为预交定金
			Project project = new Project();
			project.setId(style.getProjectId());
			project.setStatus(4);
			projectService.updateProject(project);
			Adviser adviser = adviserService.getInfoById(style.getId());
			System.out.println(adviser);
			if (adviser != null) {
				if (adviser.getRealName() !=null && !adviser.getRealName().equals("") && adviser.getPhone()!=null && !adviser.getPhone().equals("")) {
					if (adviser.getAdvType() == 1) {
						SendMsgUtil.sendMsg(adviser.getPhone(), "亲爱的"+adviser.getRealName()+"房主上传了风格，赶快联系他(她)吧!");
					}
					if (adviser.getAdvType() == 2) {
						SendMsgUtil.sendMsg(adviser.getPhone(), "亲爱的"+adviser.getRealName()+"房主上传了风格，赶快联系他(她)吧!");
					}
				}
			}
			output(response, JsonUtil.buildFalseJson("0", "确认成功"));
		}else {
			output(response, JsonUtil.buildFalseJson("1", "非法参数"));
		}
	}
	/***
	 * 定金是否交
	 * @param response
	 * @param mv
	 * @param projectId
	 * @return
	 */
	@RequestMapping("/getProjectOrderByid")
	public ModelAndView getOrderByid(HttpServletResponse response,ModelAndView mv,Integer projectId) {
			if (projectId == null) {
				output(response, JsonUtil.buildFalseJson("1", "参数非法"));
			}else {
				ProjectOrder pOrder = pOrderService.getProjectOrderByid(projectId,1);
				mv.addObject("pOrder", pOrder); 
				mv.setViewName("weiaijia/project/deposits");
			}
			return mv;
		}
	
	/***
	 * 设置定金
	 * @param response
	 * @param mv
	 * @param projectId
	 * @return
	 */
	@RequestMapping("/AddProjectSetUp")
	public ModelAndView AddProjectSetUp(HttpServletResponse response,ModelAndView mv,Integer id) {
		if (id == null) {
			output(response, JsonUtil.buildFalseJson("0", "非法参数"));
		}else {
			Project project = projectService.getInfoById(id);
			mv.addObject("project",project);
			mv.setViewName("weiaijia/project/projectOrderSetup");
		}
		return mv;
	}

	/***
	 * 确定设置金额
	 * @param response
	 * @param project
	 */
	@RequestMapping("/uploadProjectList")
	public void uploadProjectList(HttpServletResponse response,Project project) {
		if (project.getId() == null) {
			output(response, JsonUtil.buildFalseJson("1", "非法参数"));
		}else {
			projectService.updateProject(project);
			output(response, JsonUtil.buildFalseJson("0", "设置成功"));
		}
	}
	/***
	 * 确认定金数
	 * @param response
	 * @param pOrder
	 */
	@RequestMapping("/dogetProjectOrderList")
	public void dogetProjectOrderList(HttpServletResponse response,ProjectOrder pOrder) {
		if (pOrder.getPorderId() == null) {
			output(response, JsonUtil.buildFalseJson("1","参数非法"));
		}else {
			Project info = projectService.getInfoById(pOrder.getProjectId());
			//提交定金可能会有多条数据，支付成功后这一条
			if (info.getIsPay() == 1) {
					pOrderService.updateProjectOrder(pOrder);
					//更新项目状态为预交定金
					Project project = new Project();
					project.setId(pOrder.getProjectId());
					project.setStatus(5);
					projectService.updateProject(project);
					Adviser adviser = adviserService.getInfoById(pOrder.getPorderId());
					System.out.println(adviser);
					if (adviser !=null) {
						if (adviser.getRealName() !=null && !adviser.getRealName().equals("") && adviser.getPhone() !=null && !adviser.getPhone().equals("")) {
							if (adviser.getAdvType() == 1) {
								SendMsgUtil.sendMsg(adviser.getPhone(), "亲爱的"+adviser.getRealName()+"定金已交，赶快联系他(她)吧!");
							}
							if (adviser.getAdvType() == 2) {
								SendMsgUtil.sendMsg(adviser.getPhone(), "亲爱的"+adviser.getRealName()+"定金已交，赶快联系他(她)吧!");
							}
						}
					}
					output(response, JsonUtil.buildFalseJson("0", "确认成功"));
			}else {
				output(response, JsonUtil.buildFalseJson("1", "确认失败"));
			}
		}
	}
	
	/***
	 * 进入验收环节
	 * @param response
	 * @param mv
	 * @param projectId
	 * @return
	 */
	@RequestMapping("/getCheckList")
	public ModelAndView getCheckList(HttpServletResponse response,ModelAndView mv,/*ProjectWorkBigPlan pBigPlan*/Integer projectId) {
		/*Project project = projectService.getInfoById(pBigPlan.getProjectId());
		if(project.getWaiterId() != null){//顾问
			Adviser guwen = adviserService.getInfoById(project.getWaiterId());
			mv.addObject("guwen", guwen);
		}
		if(project.getDesignerId() != null){//设计师
			Adviser design = adviserService.getInfoById(project.getDesignerId());
			mv.addObject("design", design);
		}
		//那项目的地址
		if (pBigPlan.getProjectId() != null) {
			DecorateForecast room = roomService.getRoomByProjectId(pBigPlan.getProjectId());
			mv.addObject("room", room);
		}*/
		/*mv.addObject("pBigPlan", pBigPlan);
		mv.addObject("projectId",pBigPlan.getProjectId());*/
		Project project = projectService.getInfoById(projectId);
		mv.addObject("project", project);
		mv.addObject("projectId",projectId);
		mv.setViewName("weiaijia/project/categories");
		return mv;
		
	}
	/***
	 * 大项验收
	 * @param response
	 * @param pBigPlan
	 */
	@RequestMapping("/doProjectWorkPlay")
	public void doProjectWorkPlay(HttpServletResponse response,ProjectWorkBigPlan pBigPlan) {
		int count = pbigplanService.getBigPlanCount(pBigPlan);
		List<ProjectWorkBigPlan> list = pbigplanService.getBigPlanList(pBigPlan, null, null);
		output(response, JsonUtil.buildJsonByTotalCount(list, count));
	}
	/***
	 * 删除大项验收(假删除)
	 * @param response
	 * @param pBigPlan
	 */
	@RequestMapping("/delProjectWorkBigPlan")
	public void delProjectWorkBigPlan(HttpServletResponse response,ProjectWorkBigPlan pBigPlan) {
		if (pBigPlan.getId() == null) {
			output(response, JsonUtil.buildFalseJson("1", "删除失败"));
		}
		pBigPlan.setIsRemove(1);
		pbigplanService.updateBigPlan(pBigPlan);
		output(response, JsonUtil.buildFalseJson("0", "删除成功"));
	}
	/***
	 * 进入添加大项 :废除
	 * @param response
	 * @param mv
	 * @param pCheck
	 * @return
	 *//*
	@RequestMapping("/addProjectCheck")
	public ModelAndView addProjectCheck(HttpServletResponse response,ModelAndView mv,Integer projectId) {
		mv.addObject("projectId", projectId);
		mv.setViewName("weiaijia/project/projectCheckAdd");
		return mv;
	}*/
	/***
	 * 编辑添加大项：旧接口，废弃
	 * @param response
	 * @param pjCheck
	 * @param projectId
	 */
	@RequestMapping("/doAddProjectCheck")
	public void doAddProjectCheck(HttpServletResponse response,ProjectWorkBigPlan pBigPlan) {
		if (pBigPlan.getProjectId() == null) {
			output(response, JsonUtil.buildFalseJson("1", "非法参数"));
		}else {
			pBigPlan.setCheckStatus(0);
			pbigplanService.addBigPlan(pBigPlan);
			output(response, JsonUtil.buildFalseJson("0", "添加成功"));	
		}
	}
	/*** 
	 *确定大项：废弃
	 * @param response
	 * @param pCheck
	 */
	@RequestMapping("/getProjectCheckList")
	public void getProjectCheckList(HttpServletResponse response,ProjectCheck pjCheck) {
		if (pjCheck.getProjectId() == null) {
			output(response, JsonUtil.buildFalseJson("1", "非法参数"));
		}else {
			pCheckService.updateProjectCheck(pjCheck);
			output(response, JsonUtil.buildFalseJson("0", "确认成功"));
		}
	}
	
	/***
	 * 确定大项的状态 :废弃
	 * @param response
	 * @param pCheck
	 *//*
	@RequestMapping("projectCheckBig")
	public void projectCheckBig(HttpServletResponse response,Integer checkId) {
		if (checkId == null) {
			output(response, JsonUtil.buildFalseJson("1", "非法参数"));
		}else {
			int count = pItemService.checkisAll(null, checkId);	//拿到单个大项中的每一个小项
			int itemCount = pItemService.checkisAll(1, checkId);	//一个大项中所有通过的小项
			ProjectCheck pCheck = new ProjectCheck();
			if(count == itemCount) {
				pCheck.setCheckId(checkId);
				pCheck.setCheckStatus(1);
				pCheckService.updateProjectCheck(pCheck);
				output(response, JsonUtil.buildFalseJson("0", "大项验收通过"));
			}else{
				if(itemCount > 0) {
					pCheck.setCheckId(checkId);
					pCheck.setCheckStatus(2);
					pCheckService.updateProjectCheck(pCheck);
					output(response, JsonUtil.buildFalseJson("0", "大项验收进行中"));
				}else {
					pCheck.setCheckId(checkId);
					pCheck.setCheckStatus(2);
					pCheckService.updateProjectCheck(pCheck);
					output(response, JsonUtil.buildFalseJson("0", "未开始验收"));
				}
			}
			
		}
	}*/
	
	
	/****
	 * 进入验收大项对应的小项
	 * @param response
	 * @param mv
	 * @param checkId
	 * @return
	 */
	@RequestMapping("/projectCheckItem")
	public ModelAndView projectCheckItem(HttpServletResponse response,ModelAndView mv,ProjectWorkPlan projectWorkPlan) {
		mv.addObject("projectWorkPlan", projectWorkPlan);
			mv.setViewName("weiaijia/project/acceptance");
			return mv;
	}
	/***
	 * 大项对应的小项验收添加页面：废弃
	 * @param response
	 * @param mv
	 * @return
	 */
	@RequestMapping("/ProjectCheckItem")
	public ModelAndView ProjectCheckItem(HttpServletResponse response,ModelAndView mv,Integer planTypeId) {
		mv.addObject("planTypeId", planTypeId);
		mv.setViewName("weiaijia/project/projectItemAdd");
		return mv;
		
		
	}
		/***
		 * 添加小项验收:废弃
		 * @param response
		 * @param pCheckItem
		 */
	@RequestMapping("/doAddprojectCheckItem")
	public void doAddprojectCheckItem(HttpServletResponse response,ProjectCheckItem pCheckItem) {
		if (pCheckItem.getCheckId() == null) {
			output(response, JsonUtil.buildFalseJson("1","非法参数"));
		}else {
			pCheckItem.setIsDeal(0);
			pCheckItem.setItemStatus(0);
			pItemService.addProjectCheckItem(pCheckItem);
			output(response, JsonUtil.buildFalseJson("0", "添加成功"));
		}
	}
	/***
	 *确定小项验收
	 * @param response
	 * @param pItem
	 */
	@RequestMapping("/doProjectCheckItemlist")
	public void doProjectCheckItemlist(HttpServletResponse response,ProjectWorkPlan pWorkPlan) {
		if (pWorkPlan.getPlanTypeId() == null) {
			output(response, JsonUtil.buildFalseJson("1", "非法参数"));
		}else {
			ProjectWorkPlanService.updateWorkPlan(pWorkPlan);
			output(response, JsonUtil.buildFalseJson("0", "确认成功"));
		}
	}

	/***
	 * 验收进度：废弃
	 * @param response
	 * @param mv
	 * @param pCheck
	 * @return
	 */
	@RequestMapping("/projectCheckTheSchedule")
	public ModelAndView projectCheckTheSchedule(HttpServletResponse response,ModelAndView mv,ProjectWorkPlan pWorkPlan) {
		List<ProjectWorkPlan> list = ProjectWorkPlanService.getWorkPlanList(pWorkPlan, null, null);
		mv.addObject("list",list );
		mv.setViewName("weiaijia/project/acceptanceSchedule");
		return mv;
		
	}
	/***
	 * 查看评价
	 * @param response
	 * @param mv
	 * @param projectId
	 * @return
	 */
	@RequestMapping("/getProjectComment")
	public ModelAndView getProjectComment(HttpServletResponse response, ModelAndView mv,Integer projectId) {
			ProjectComment pComment =pCommentService.getProjectCommentByid(projectId);
			mv.addObject("pComment",pComment);
			mv.setViewName("weiaijia/project/commont");
		
		return mv;
	}
	/***
	 * 确认评价
	 * @param response
	 * @param pComment
	 */
	@RequestMapping("/getProjectCommentByid")
	public void getProjectCommentByid(HttpServletResponse response,ProjectComment pComment) {
		if (pComment.getCommentId() == null) {
			output(response, JsonUtil.buildFalseJson("1", "非法参数"));
		}else {
			pCommentService.updateComment(pComment);
			Adviser adviser = adviserService.getInfoById(pComment.getCommentId());
			if (pComment !=null) {
				if (adviser !=null) {
					System.out.println(adviser);
					if (adviser.getPhone() !=null && !adviser.getPhone().equals("") && adviser.getRealName() !=null && !adviser.getRealName().equals("")) {
						if (adviser.getAdvType() == 1) {
							SendMsgUtil.sendMsg(adviser.getPhone(), "亲爱的"+adviser.getRealName()+"房主以评价，请注意查看！");
						}
						if (adviser.getAdvType() ==2) {
							SendMsgUtil.sendMsg(adviser.getPhone(), "亲爱的"+adviser.getRealName()+"房主以评价，请注意查看！");
						}
					}
				}
			}
			output(response, JsonUtil.buildFalseJson("0", "确认成功"));
		}
	}
	
	/***
	 * 施工日志
	 * @param response
	 * @param mv
	 * @param log
	 * @return
	 */
	@RequestMapping("/getLogList")
	public ModelAndView getLogList(HttpServletResponse response,ModelAndView mv,Integer projectId,ProjectLog log) {
			List<ProjectLog> tlist = pLogService.getProjectLogList(log, null, null);
			mv.addObject("tlist", tlist);
			mv.addObject("projectId", projectId);
			mv.setViewName("weiaijia/project/log");
		return mv;
	}
	/***
	 * 进入添加日志页面
	 * @param response
	 * @param mv
	 * @param projectId
	 * @return
	 */
	@RequestMapping("/addProjectLog")
	public ModelAndView addProjectLog(HttpServletResponse response,ModelAndView mv,Integer projectId) {
		mv.addObject("projectId", projectId);
		mv.setViewName("weiaijia/project/projectLogAdd");
		return mv;
	}
	/***
	 * 确认添加日志
	 * @param response
	 * @param log
	 */
	@RequestMapping("/doAddProjectLog")
	public void doAddProjectLog(HttpServletResponse response,ProjectLog log) {
		if (log.getProjectId() == null) {
			output(response, JsonUtil.buildFalseJson("1", "非法参数"));
		}else {
			log.setCreateTime(new Date());
			pLogService.addProjectLog(log);
			output(response, JsonUtil.buildFalseJson("0", "添加成功"));
		}
	}
	/***
	 * 确认日志
	 * @param response
	 * @param log
	 */
	@RequestMapping("/doLogByid")
	public void doLogByid(HttpServletResponse response,ProjectLog log) {
			if (log.getProjectId() == null) {
				output(response, JsonUtil.buildFalseJson("1", "非法参数"));
			}else {
				pLogService.updateProjectLog(log);
				output(response, JsonUtil.buildFalseJson("0", "确认成功"));
			}
	}
	/***
	 * 删除日志
	 * @param response
	 * @param plogId
	 */
	@RequestMapping("/delLogById")
	public void delLogById(HttpServletResponse response, Integer plogId) {
		if (plogId == null) {
			output(response, JsonUtil.buildFalseJson("1", "非法参数"));
		}else {
			pLogService.delProjectLog(plogId);
			output(response, JsonUtil.buildFalseJson("0", "删除成功"));
		}
	}
	/**
	 * 施工流程/工艺标准
	 * @param response
	 * @param mv
	 * @param projectId
	 * @return
	 */
	@RequestMapping("/getWorkProcessList")
	public ModelAndView getWorkProcessList(HttpServletResponse response,ModelAndView mv) {
		mv.setViewName("weiaijia/project/indexof");
		return mv;
		
	}
	/****
	 * 显示
	 * @param response
	 * @param work
	 */
	@RequestMapping("/getProjectWorkProcess")
	public void getProjectWorkProcess(HttpServletResponse response,ProjectWorkProcess pwork) {	
		int count = processService.getProjectWorkProcessCount(pwork);
		List<ProjectWorkProcess> list = processService.getWorkProcessList(pwork);
		output(response, JsonUtil.buildJsonByTotalCount(list, count));
	}
	/***
	 * 进入添加
	 * @param request
	 * @param mv
	 * @return
	 */
	@RequestMapping("/addProjectWorkProcess")
	public ModelAndView addProjectWorkProcess(HttpServletRequest request,ModelAndView mv,ProjectWorkProcess process) {
		mv.setViewName("weiaijia/project/addProcessWork");
		return mv;
	}
	/***
	 * 图片添加
	 * 
	 * @param request
	 * @param mv
	 * @param process
	 * @return
	 */
	@RequestMapping("/projectWorkProcess")
	public ModelAndView projectWorkProcess(HttpServletRequest request,ModelAndView mv,ProjectWorkProcess process) {
		mv.addObject("process", process);
		mv.setViewName("weiaijia/project/processPhoto");
		return mv;
	}
	/**
	 * 确认添加施工项
	 * @param response
	 * @param work
	 */
	@RequestMapping("/doAddProjectWorkProcess")
	public void doAddProjectWorkProcess(HttpServletResponse response,ProjectWorkProcess work) {
		work.setCreateTime(new Date());
		processService.addWorkProcess(work);
		output(response, JsonUtil.buildFalseJson("0", "添加成功"));
	}
	/***
	 * 进入编辑页面
	 * @param response
	 * @param mv
	 * @param workId
	 * @return
	 */
	@RequestMapping("/updateAddProjectWorkProcess")
	public ModelAndView updateAddProjectWorkProcess(HttpServletResponse response,ModelAndView mv,Integer workId) {
		ProjectWorkProcess process = processService.getConstructionById(workId);
		mv.addObject("process",process);
		mv.setViewName("weiaijia/project/updatework");
		return mv;
	}
	/***
	 * 执行施工编辑
	 * @param response
	 * @param work
	 */
	@RequestMapping("/doUpdateAddProjectWorkProcess")
	public void doUpdateAddProjectWorkProcess(HttpServletResponse response,ProjectWorkProcess work) {
		if (work.getWorkId() == null) {
			output(response, JsonUtil.buildFalseJson("1", "非法参数"));
		}else {
			processService.updateWorkProcess(work);
			output(response, JsonUtil.buildFalseJson("0", "编辑成功"));
		}
	}
	/***
	 * 删除
	 * @param response
	 * @param workId
	 */
	@RequestMapping("/deleteProjectWorkProcess")
	public void deleteProjectWorkProcess(HttpServletResponse response,Integer workId) {
		if (workId == null) {
			output(response, JsonUtil.buildFalseJson("1", "非法参数"));
		}else {
			processService.delWorkProcess(workId);
			output(response, JsonUtil.buildFalseJson("0", "删除成功"));
		}
	}
	
	/***
	 * 首页打印
	 * @param response
	 * @param mv
	 * @param projectId
	 * @return
	 */
	@RequestMapping("/queryAllPrint")
	public ModelAndView queryAllPrint(HttpServletResponse response,ModelAndView mv,Integer projectId) {
		Project project = projectService.getInfoById(projectId);
		if(project.getWaiterId() != null){//顾问
			Adviser guwen = adviserService.getInfoById(project.getWaiterId());
			mv.addObject("guwen", guwen);
		}
		if(project.getDesignerId() != null){//设计师
			Adviser design = adviserService.getInfoById(project.getDesignerId());
			mv.addObject("design", design);
		}
		//获取用户选择的风格
	/*	mv.addObject("style", style);*/
		//查看客户信息
		DecorateForecast room = roomService.getRoomByProjectId(projectId);
		mv.addObject("room", room);
		mv.addObject("project", project);
		//验收项目 ：旧的，废弃
		/*ProjectCheck pjCheck = new ProjectCheck();
		List<ProjectCheck> pCheck = pCheckService.queryProjectCheckList(pjCheck, null, null);
		mv.addObject("pCheck",pCheck);*/
		//验收
		ProjectWorkPlan pWorkPla = new ProjectWorkPlan();
		List<ProjectWorkPlan> tlist = ProjectWorkPlanService.getWorkPlanList(pWorkPla, null,null);
		mv.addObject("list",tlist);
		//定金
		ProjectOrder pOrder = pOrderService.getProjectOrderByid(projectId,1);
		mv.addObject("pOrder",pOrder);
		//评论
		ProjectComment pComment = pCommentService.getProjectCommentByid(projectId);
		mv.addObject("pComment", pComment);
		//查看设计稿有没有改变
		ProjectView projectView = new ProjectView();
		projectView.setProjectId(projectId);
		projectView.setViewType(1);
		List<ProjectView> lists = pViewService.getProjectViewList(projectView, null, null);
		if (lists == null) {
				mv.addObject("lists","f");
			}else {
				mv.addObject("lists","t");
				}
		mv.addObject("lists",lists);
		//查看报价有没有改变
		projectView.setProjectId(projectId);
		projectView.setViewType(2);
		List<ProjectView> list = pViewService.getProjectViewList(projectView, null, null);
		if (list == null) {
			mv.addObject("list","f");
		}else {
			mv.addObject("list", "t");
		}
		mv.addObject("list",list);
		//施工
		ProjectWorkBigPlan pBigPlan = new ProjectWorkBigPlan();
		List<ProjectWorkBigPlan> pList = pbigplanService.getBigPlanList(pBigPlan, null, null);
		//日志
		ProjectLog log1 = new ProjectLog();
		log1.setProjectId(projectId);
		List<ProjectLog> log = pLogService.getProjectLogList(log1, null, null);
		mv.addObject("log", log);
		mv.addObject("pList", pList);
		mv.setViewName("weiaijia/project/allPrint");
		return mv;
	}
	/***
	 *验收完成后跳转评价
	 * @param response
	 * @param pBigPlan
	 */
	@RequestMapping("/doAcceptance")
	public void doAcceptance(HttpServletResponse response,ProjectWorkBigPlan big) {
		if(big.getProjectId() == null){
			output(response,JsonUtil.buildFalseJson("1", "参数非法"));
			return;
		}
		big.setIsCheck(1);
		List<ProjectWorkBigPlan> list = pbigplanService.getBigPlanList(big, null, null);
		if(list.size() > 0){
			int bigPlanCount = list.size();
			int checkAllCount = 0;
			for (ProjectWorkBigPlan p : list) {
				if(p.getCheckStatus() == 3){
					checkAllCount++;
				}
			}
			if(checkAllCount == bigPlanCount){
				//项目状态改变
				Project project = new Project();
				project.setId(big.getProjectId());
				project.setStatus(9);
				projectService.updateProject(project);
				Adviser adviser = adviserService.getInfoById(big.getProjectId());
				System.out.println(adviser);
				if (adviser !=null) {
					if (adviser.getRealName()!=null && adviser.getRealName().equals("") && adviser.getPhone()!=null && !adviser.getPhone().equals("")) {
						if (adviser.getAdvType() == 1) {
							SendMsgUtil.sendMsg(adviser.getPhone(), "亲爱的"+adviser.getRealName()+"验收已经完成，请注意查看！");
						}
					}
				}
				output(response,JsonUtil.buildFalseJson("0", "操作成功"));
			}else{
				output(response,JsonUtil.buildFalseJson("1", "还有项目未验收,暂不能操作该功能"));
			}
		}else{
			output(response,JsonUtil.buildFalseJson("1", "该项目暂无施工项目"));
		}
		output(response, JsonUtil.buildJson(list));
	}
}
	
