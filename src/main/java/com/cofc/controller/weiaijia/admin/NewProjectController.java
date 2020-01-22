package com.cofc.controller.weiaijia.admin;

import java.math.BigInteger;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.cofc.pojo.vij.Adviser;
import com.cofc.pojo.vij.DecorateForecast;
import com.cofc.pojo.vij.ModelRoom;
import com.cofc.pojo.vij.Project;
import com.cofc.pojo.vij.ProjectCheckView;
import com.cofc.pojo.vij.ProjectDesign;
import com.cofc.pojo.vij.ProjectOffer;
import com.cofc.pojo.vij.ProjectOfferDetail;
import com.cofc.pojo.vij.ProjectView;
import com.cofc.pojo.vij.ProjectWorkBigPlan;
import com.cofc.pojo.vij.ProjectWorkPlan;
import com.cofc.pojo.vij.ProjectWorkPlanType;
import com.cofc.service.ApplicationCommonService;
import com.cofc.service.UserCommonService;
import com.cofc.service.vij.AdviserService;
import com.cofc.service.vij.DecorateForecastService;
import com.cofc.service.vij.ModelRoomService;
import com.cofc.service.vij.ProjectCheckViewService;
import com.cofc.service.vij.ProjectDesignService;
import com.cofc.service.vij.ProjectOfferDetailService;
import com.cofc.service.vij.ProjectOfferService;
import com.cofc.service.vij.ProjectService;
import com.cofc.service.vij.ProjectViewService;
import com.cofc.service.vij.ProjectWorkBigPlanService;
import com.cofc.service.vij.ProjectWorkPlanService;
import com.cofc.service.vij.ProjectWorkPlanTypeService;
import com.cofc.util.BaseUtil;
import com.cofc.util.JsonUtil;
import com.cofc.util.SendMsgUtil;

@Controller
@RequestMapping("/newProject")
public class NewProjectController extends BaseUtil{
	@Resource
	private ProjectService projectService; // 总施工项目
	@Resource
	private AdviserService adviserService; //顾问 / 设计师
	@Resource
	private ApplicationCommonService applicationService; //应用
	@Resource
	private UserCommonService uCommonService;//用户
	@Resource
	private ProjectDesignService projectDesignService;//设计稿
	@Resource
	private ProjectViewService projectViewService;//用户反馈
	@Resource
	private ProjectOfferService projectOfferService;//报价
	@Resource
	private ProjectOfferDetailService projectOfferDetailService;//报价详情
	@Resource
	private ProjectWorkPlanService projectWorkPlanService;//施工计划
	@Resource
	private ProjectWorkPlanTypeService projectWorkPlanTypeService;//施工计划类别
	@Resource
	private ProjectWorkBigPlanService projectWorkBigPlanService;
	@Resource
	private DecorateForecastService decorateForecastService;
	@Resource
	private ModelRoomService modelRoomService;
	@Resource 
	private ProjectCheckViewService pCheckViewService;		//验收意见
	public static Logger log = Logger.getLogger("NewProjectController");
	
	/**
	 * 进入选顾问/设计师
	 * 
	 * @param request
	 * @param mv
	 * @return
	 */
	@RequestMapping("/goSelectAdviser")
	public ModelAndView goSelectAdviser(HttpServletRequest request, ModelAndView mv, Integer projectId,Integer advType) {
		Project project = projectService.getInfoById(projectId);
		Adviser adviser = new Adviser();
		if(advType == 1){
			if(project.getWaiterId() != null){//顾问
				Adviser guwen = adviserService.getInfoById(project.getWaiterId());
				mv.addObject("guwen", guwen);
			}
		}
		if(advType == 2){
			if(project.getDesignerId() != null){//设计师
				Adviser guwen = adviserService.getInfoById(project.getWaiterId());
				mv.addObject("guwen", guwen);
			}
		}
		adviser.setAdvType(advType);
		List<Adviser> guwenList = adviserService.getAdviserList(adviser, null, null);
		mv.addObject("project", project);
		mv.addObject("guwenList", guwenList);
		mv.addObject("advType", advType);
		mv.setViewName("weiaijia/project/selectAdviser");
		return mv;
	}
	
	
	/**
	 * 选顾问/设计师
	 * 
	 * @param request
	 * @param mv
	 * @return
	 */
	@RequestMapping("/selectAdviser")
	public void SelectAdviser(HttpServletRequest request,HttpServletResponse response, ModelAndView mv, Integer adviserId,Project project,Integer advType) {
		DecorateForecast decorateForecast = decorateForecastService.getRoomByProjectId(project.getId());
		Adviser adviser = adviserService.getInfoById(adviserId);
		if(adviserId==null){
			output(response, JsonUtil.buildFalseJson("1", "请选择顾问或设计师"));
		}
		else{
			if(advType == 1){
				project.setWaiterId(adviserId);
			}
			if(advType == 2){
				project.setDesignerId(adviserId);
			}
			projectService.updateProject(project);
			if(decorateForecast != null && adviser != null){
				if(decorateForecast.getMobilePhoneNo() != null && !decorateForecast.getMobilePhoneNo().equals("") && decorateForecast.getLfUserName() != null && !decorateForecast.getLfUserName().equals("")){
					if(adviser.getPhone() !=null && !adviser.getPhone().equals("") && adviser.getRealName() !=null && !adviser.getRealName().equals("")){
						if(advType == 1 && adviser.getAdvType() ==1){
							SendMsgUtil.sendMsg(decorateForecast.getMobilePhoneNo(), "亲爱的"+decorateForecast.getLfUserName()+",我们已为您安排了专属顾问:"+adviser.getRealName()+"("+adviser.getPhone()+"),赶快联系他（她）吧！");
							SendMsgUtil.sendMsg(adviser.getPhone(), "亲爱的"+adviser.getRealName()+",你已经被:"+decorateForecast.getLfUserName()+"("+decorateForecast.getMobilePhoneNo()+")选择为顾问了,赶快联系他（她）吧！");
						}
						if(advType == 2 && adviser.getAdvType() ==2){
							SendMsgUtil.sendMsg(decorateForecast.getMobilePhoneNo(), "亲爱的"+decorateForecast.getLfUserName()+",我们已为您安排了设计师:"+adviser.getRealName()+"("+adviser.getPhone()+"),赶快联系他（她）吧！");
							SendMsgUtil.sendMsg(adviser.getPhone(), "亲爱的"+adviser.getRealName()+",你已经被:"+decorateForecast.getLfUserName()+"("+decorateForecast.getMobilePhoneNo()+")选择为顾问了,赶快联系他（她）吧！");
						}
					}
					
				}
			}
			output(response, JsonUtil.buildFalseJson("0", "选择成功"));
		}
	}
	
	/**
	 * 进入项目设计图
	 * 
	 * @param request
	 * @param mv
	 * @return
	 */
	@RequestMapping("/goProjectDesign")
	public ModelAndView goProjectDesign(HttpServletRequest request, ModelAndView mv, Integer projectId) {
		Project project = projectService.getInfoById(projectId);
		//设计图
		int projectDesignC1 = projectDesignService.getDesignCountByProjectId(projectId, 1);
		if(projectDesignC1 >= 1){
			mv.addObject("projectDesignC1", "t");
		}
		else{
			mv.addObject("projectDesignC1", "f");
		}
		//效果图
		int projectDesignC2 = projectDesignService.getDesignCountByProjectId(projectId, 2);
		if(projectDesignC2 >= 1){
			mv.addObject("projectDesignC2", "t");
		}
		else{
			mv.addObject("projectDesignC2", "f");
		}
		//用户反馈
		ProjectView projectView = new ProjectView();
		projectView.setProjectId(projectId);
		projectView.setViewType(1);
		int projectViewC = projectViewService.getProjectViewCount(projectView);
		if(projectViewC >= 1){
			mv.addObject("projectViewC", "t");
		}
		else{
			mv.addObject("projectViewC", "f");
		}
		mv.addObject("project", project);
		mv.setViewName("weiaijia/project/projectDesign");
		return mv;
	}
	
	/**
	 * 进入上传设计图/效果图
	 * 
	 * @param request
	 * @param mv
	 * @return
	 */
	@RequestMapping("/goUploadDesign")
	public ModelAndView goUploadDesign(HttpServletRequest request, ModelAndView mv, Integer projectId, Integer designType) {
		Project project = projectService.getInfoById(projectId);
		ProjectDesign projectDesign = new ProjectDesign();
		projectDesign = projectDesignService.getDesignByProjectIdType(projectId, designType);
		
		mv.addObject("projectDesign", projectDesign);
		mv.addObject("project", project);
		mv.addObject("designType", designType);
		mv.setViewName("weiaijia/project/uploadDesign");
		return mv;
	}
	
	/**
	 * 添加设计图/效果图
	 * 
	 * @param request
	 * @param mv
	 * @return
	 */
	@RequestMapping("/addProjectDesign")
	public void addProjectDesign(HttpServletResponse response, HttpServletRequest request,
			ProjectDesign projectDesign) {
		int projectDesignC = projectDesignService.getDesignCountByProjectId(projectDesign.getProjectId(), projectDesign.getDesignType());
		DecorateForecast decorateForecast = decorateForecastService.getRoomByProjectId(projectDesign.getProjectId());
		System.out.println(decorateForecast);
		if(projectDesignC >= 1){
			projectDesignService.updateOfferDesign(projectDesign);
		}
		else{
			projectDesign.setCreateTime(new Date());
			projectDesignService.addOfferDesign(projectDesign);
		}
		if(decorateForecast != null){
			if(decorateForecast.getMobilePhoneNo() != null && !decorateForecast.getMobilePhoneNo().equals("") && decorateForecast.getLfUserName() != null && !decorateForecast.getLfUserName().equals("")){
				if(projectDesign.getDesignType() == 1){
					SendMsgUtil.sendMsg(decorateForecast.getMobilePhoneNo(), "亲爱的"+decorateForecast.getLfUserName()+",我们已为您上传设计图，赶快上唯爱家app查看吧!");
				}
				if(projectDesign.getDesignType() == 2){
					SendMsgUtil.sendMsg(decorateForecast.getMobilePhoneNo(), "亲爱的"+decorateForecast.getLfUserName()+"，我们已为您上传效果图，赶快上唯爱家app查看吧!");
				}
				
			}
		}
		
		output(response, JsonUtil.buildFalseJson("0", "添加成功"));
	}
	
	/**
	 * 删除设计图/效果图
	 * 
	 * @param request
	 * @param mv
	 * @return
	 */
	@RequestMapping("/delProjectDesign")
	public void delProjectDesign(HttpServletResponse response, HttpServletRequest request, ModelAndView mv,
			ProjectDesign projectDesign) {
		ProjectDesign projectDesign_1 = projectDesignService.getDesignByProjectIdType(projectDesign.getProjectId(), projectDesign.getDesignType());
		if(projectDesign_1 == null){
			output(response, JsonUtil.buildFalseJson("0", "删除成功"));
		}else{
			projectDesignService.delOfferDesign(projectDesign_1.getDesignId());
			output(response, JsonUtil.buildFalseJson("0", "删除成功"));
		}
	}
	
	/**
	 * 查看是否调整设计/报价
	 * 
	 * @param request
	 * @param mv
	 * @return
	 */
	@RequestMapping("/findProjectView")
	public ModelAndView findProjectView(HttpServletRequest request, ModelAndView mv, Integer projectId, Integer viewType) {
		Project project = projectService.getInfoById(projectId);
		ProjectView projectView = new ProjectView();
		projectView.setProjectId(projectId);
		projectView.setViewType(viewType);
		List<ProjectView> projectViewList =  projectViewService.getProjectViewList(projectView, null, null);
		mv.addObject("projectViewList", projectViewList);
		mv.addObject("project", project);
		mv.addObject("viewType", viewType);
		mv.setViewName("weiaijia/project/findProjectView");
		return mv;
	}
	
	/**
	 * 处理调整设计/报价
	 * 
	 * @param request
	 * @param mv
	 * @return
	 */
	@RequestMapping("/updateProjectView")
	public void updateProjectView(HttpServletResponse response, HttpServletRequest request, ModelAndView mv, Integer projectId, Integer viewType) {
		ProjectView projectView = new ProjectView();
		projectView.setProjectId(projectId);
		projectView.setViewType(viewType);
		projectView.setIsDeal(1);
		projectViewService.updateProjectView(projectView);
		output(response, JsonUtil.buildFalseJson("0", "处理成功"));
	}
	
	/**
	 * 进入项目报价
	 * 
	 * @param request
	 * @param mv
	 * @return
	 */
	@RequestMapping("/goProjectOffer")
	public ModelAndView goProjectOffer(HttpServletRequest request, ModelAndView mv, Integer projectId) {
		Project project = projectService.getInfoById(projectId);
		
		ProjectView projectView = new ProjectView();
		projectView.setProjectId(projectId);
		projectView.setViewType(2);
		
		mv.addObject("project", project);
		mv.setViewName("weiaijia/project/projectOffer");
		return mv;
	}
	
	/**
	 * 进入上传硬装报价/软装
	 * 
	 * @param request
	 * @param mv
	 * @return
	 */
	@RequestMapping("/goUploadOffer")
	public ModelAndView goUploadOffer(HttpServletRequest request, ModelAndView mv, Integer projectId, Integer offerType) {
		Project project = projectService.getInfoById(projectId);
		ProjectOffer projectOffer = projectOfferService.getOfferByProjectIdType(projectId, offerType);
		ProjectOfferDetail projectOfferDetails = null;
		if(projectOffer != null){
			projectOfferDetails = projectOfferDetailService.getOfferDetailById(projectOffer.getPofferId());
		}
		mv.addObject("projectOfferDetails", projectOfferDetails);
		mv.addObject("project", project);
		mv.addObject("offerType", offerType);
		mv.setViewName("weiaijia/project/uploadOffer");
		return mv;
	}
	
	/**
	 * 添加硬装/软装报价
	 * 
	 * @param request
	 * @param mv
	 * @return
	 */
	@RequestMapping("/addProjectOffer")
	public void addProjectOrder(HttpServletResponse response, HttpServletRequest request, ProjectOfferDetail projectOfferDetail, Integer projectId, Integer offerType) {
		ProjectOffer projectOffer = projectOfferService.getOfferByProjectIdType(projectId, offerType);
		if(projectOffer != null){
			ProjectOfferDetail detail = projectOfferDetailService.getOfferDetailById(projectOffer.getPofferId());
			if(detail == null){
				projectOfferDetail.setPofferId(projectOffer.getPofferId());
				projectOfferDetailService.addOfferDetail(projectOfferDetail);
			}else{
				projectOfferDetail.setOfferId(detail.getOfferId());
				projectOfferDetailService.updateOfferDetail(projectOfferDetail);
			}
		}
		else{
			ProjectOffer projectOffer_1 = new ProjectOffer();
			projectOffer_1.setProjectId(projectId);
			projectOffer_1.setOfferTypeId(offerType);
			projectOfferService.addOffer(projectOffer_1);
			ProjectOffer projectOffer_2 = projectOfferService.getOfferByProjectIdType(projectId, offerType);
			projectOfferDetail.setPofferId(projectOffer_2.getPofferId());
			projectOfferDetailService.addOfferDetail(projectOfferDetail);
		}
		DecorateForecast decorateForecast = decorateForecastService.getRoomByProjectId(projectId);
		if(decorateForecast != null){
			if(decorateForecast.getMobilePhoneNo() != null && !decorateForecast.getMobilePhoneNo().equals("") && decorateForecast.getLfUserName() != null && !decorateForecast.getLfUserName().equals("")){
				if(offerType == 1){
					SendMsgUtil.sendMsg(decorateForecast.getMobilePhoneNo(), "亲爱的"+decorateForecast.getLfUserName()+"，我们已为您上传硬装报价，赶快上唯爱家app查看吧!");
				}
				if(offerType == 2){
					SendMsgUtil.sendMsg(decorateForecast.getMobilePhoneNo(), "亲爱的"+decorateForecast.getLfUserName()+"，我们已为您上传软装报价，赶快上唯爱家app查看吧!");
				}
			}
		}
		output(response, JsonUtil.buildFalseJson("0", "添加成功"));
	}
	/**
	 * 进入编辑硬装报价/软装
	 * 
	 * @param request
	 * @param mv
	 * @return
	 */
	@RequestMapping("/goUpdateUploadOffer")
	public ModelAndView goUpdateUploadOffer(HttpServletRequest request, ModelAndView mv, ProjectOfferDetail projectOfferDetail) {
		ProjectOfferDetail projectOfferDetail_1 = projectOfferDetailService.getOfferDetailByOfferId(projectOfferDetail.getOfferId());
		mv.addObject("projectOfferDetail", projectOfferDetail_1);
		mv.setViewName("weiaijia/project/updateUploadOffer");
		return mv;
	}
	/**
	 * 编辑硬装/软装报价
	 * 
	 * @param request
	 * @param mv
	 * @return
	 */
	@RequestMapping("/updateProjectOffer")
	public void updateProjectOrder(HttpServletResponse response, HttpServletRequest request, ProjectOfferDetail projectOfferDetail) {
		projectOfferDetailService.updateOfferDetail(projectOfferDetail);
		output(response, JsonUtil.buildFalseJson("0", "编辑成功"));
	}
	/**
	 * 删除硬装/软装报价
	 * 
	 * @param request
	 * @param mv
	 * @return
	 */
	@RequestMapping("/delProjectOffer")
	public void delProjectOrder(HttpServletResponse response, HttpServletRequest request, ProjectOfferDetail projectOfferDetail) {
		System.out.println(projectOfferDetail.getPofferId());
		ProjectOfferDetail projectOfferDetails = projectOfferDetailService.getOfferDetailById(projectOfferDetail.getPofferId());
		projectOfferDetailService.delOfferDetail(projectOfferDetail.getOfferId());
		if(projectOfferDetails!= null){
			projectOfferService.delOffer(projectOfferDetail.getPofferId());
		}
		output(response, JsonUtil.buildFalseJson("0", "删除成功"));
	}
	/**
	 * 进入大类施工
	 * 
	 * @param request
	 * @param mv
	 * @return
	 */
	@RequestMapping("/goProjectWork")
	public ModelAndView goProjectWork(HttpServletRequest request, ModelAndView mv, Integer projectId) {
		Project project = projectService.getInfoById(projectId);
		mv.addObject("project", project);
		mv.setViewName("weiaijia/project/projectWork");
		return mv;
	}
	
	/**
	 * @param 获取大类计划
	 * @param request
	 * @param response
	 * @param vijProject
	 * @return
	 */
	@RequestMapping("/getProjectBigWorkPlan")
	public void getProjectBigWorkPlan(HttpServletRequest request, HttpServletResponse response, ProjectWorkBigPlan projectWorkBigPlan, Integer page,
			Integer limit) {
		if (page == null) {
			page = 1;
		}
		if (limit == null) {
			limit = 20;
		}
		int count = projectWorkBigPlanService.getBigPlanCount(projectWorkBigPlan);
		// 查詢方法
		List<ProjectWorkBigPlan> list = projectWorkBigPlanService.getBigPlanList(projectWorkBigPlan, (page-1)*limit,limit);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for(int i=0; i<list.size(); i++){
			String planStartTime1 = String.valueOf(list.get(i).getBigPlanStartTime());
			String planEndTime1 = String.valueOf(list.get(i).getBigPlanEndTime());
	        long planStartTime2 = new Long(planStartTime1);
	        long planEndTime2 = new Long(planEndTime1);
	        list.get(i).setBigPlanStartTimeS(simpleDateFormat.format(new Date(planStartTime2)));
	        list.get(i).setBigPlanEndTimeS(simpleDateFormat.format(new Date(planEndTime2)));
	        if(list.get(i).getBigRealStartTime()!= null){
	        	String realStartTime1 = String.valueOf(list.get(i).getBigRealStartTime());
	        	long realStartTime2 = new Long(realStartTime1);
	        	list.get(i).setBigRealStartTimeS(simpleDateFormat.format(new Date(realStartTime2)));
	        }
			if(list.get(i).getBigRealEndTime()!= null){
				String realEndTime1 = String.valueOf(list.get(i).getBigRealEndTime());
				long realEndTime2 = new Long(realEndTime1);
				list.get(i).setBigRealEndTimeS(simpleDateFormat.format(new Date(realEndTime2)));
	        }
		}
        output(response, JsonUtil.buildJsonByTotalCount(list, count));
	}
	/**
	 * 进入添加大类施工计划
	 * 
	 * @param request
	 * @param mv
	 * @return
	 */
	@RequestMapping("/goAddWorkBigPlan")
	public ModelAndView goAddWorkBigPlan(HttpServletRequest request, ModelAndView mv, Integer projectId) {
		ProjectWorkPlanType projectWorkPlanType = new ProjectWorkPlanType();
		List<ProjectWorkPlanType> tList = projectWorkPlanTypeService.getPlanTypeList(projectWorkPlanType, null, null);
		Project project = projectService.getInfoById(projectId);
		mv.addObject("project", project);
		mv.addObject("tList", tList);
		mv.setViewName("weiaijia/project/addProjectWorkPlan");
		return mv;
	}
	
	/**
	 * 添加大类施工计划
	 * 
	 * @param request
	 * @param mv
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping("/addWorkBigPlan")
	public void addWorkBigPlan(HttpServletResponse response, HttpServletRequest request, ProjectWorkBigPlan projectWorkBigPlan) throws ParseException {
		DateFormat df = DateFormat.getDateInstance();
		DecorateForecast dForecast = decorateForecastService.getDecorateForecastById(projectWorkBigPlan.getProjectId());
		System.out.println(dForecast);
		String planStartTime2 = String.valueOf(df.parse(projectWorkBigPlan.getBigPlanStartTimeS()).getTime());
		String planEndTime2 = String.valueOf(df.parse(projectWorkBigPlan.getBigPlanEndTimeS()).getTime());
		projectWorkBigPlan.setCheckStatus(0);
		projectWorkBigPlan.setIsFinish(0);
		projectWorkBigPlan.setBigPlanStartTime(new BigInteger(planStartTime2));
		projectWorkBigPlan.setBigPlanEndTime(new BigInteger(planEndTime2));
		projectWorkBigPlanService.addBigPlan(projectWorkBigPlan);
		if (dForecast !=null) {
			if (dForecast.getLfUserName() !=null && !dForecast.getLfUserName().equals("") && dForecast.getMobilePhoneNo() !=null && !dForecast.getMobilePhoneNo().equals("")) {
				SendMsgUtil.sendMsg(dForecast.getMobilePhoneNo(), "亲爱的"+dForecast.getLfUserName()+"施工项目已更新，请注意查看！");
			}
		}
		output(response, JsonUtil.buildFalseJson("0", "添加成功"));
	}
	
	/**
	 * 进入编辑大类施工计划
	 * 
	 * @param request
	 * @param mv
	 * @return
	 */
	@RequestMapping("/goUpdateWorkBigPlan")
	public ModelAndView updateAddWorkBigPlan(HttpServletRequest request, ModelAndView mv, ProjectWorkBigPlan projectWorkBigPlan) {
		ProjectWorkPlanType projectWorkPlanType = new ProjectWorkPlanType();
		List<ProjectWorkPlanType> tList = projectWorkPlanTypeService.getPlanTypeList(projectWorkPlanType, null, null);
		projectWorkBigPlan = projectWorkBigPlanService.getInfoById(projectWorkBigPlan.getId());
		
		String planStartTime1 = String.valueOf(projectWorkBigPlan.getBigPlanStartTime());
		String planEndTime1 = String.valueOf(projectWorkBigPlan.getBigPlanEndTime());
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long planStartTime2 = new Long(planStartTime1);
        long planEndTime2 = new Long(planEndTime1);
        projectWorkBigPlan.setBigPlanStartTimeS(simpleDateFormat.format(new Date(planStartTime2)));
        projectWorkBigPlan.setBigPlanEndTimeS(simpleDateFormat.format(new Date(planEndTime2)));
        if(projectWorkBigPlan.getBigRealStartTime()!= null){
        	String realStartTime1 = String.valueOf(projectWorkBigPlan.getBigRealStartTime());
        	long realStartTime2 = new Long(realStartTime1);
        	projectWorkBigPlan.setBigRealStartTimeS(simpleDateFormat.format(new Date(realStartTime2)));
        }
		if(projectWorkBigPlan.getBigRealEndTime()!= null){
			String realEndTime1 = String.valueOf(projectWorkBigPlan.getBigRealEndTime());
			long realEndTime2 = new Long(realEndTime1);
			projectWorkBigPlan.setBigRealEndTimeS(simpleDateFormat.format(new Date(realEndTime2)));
        }
		mv.addObject("projectWorkBigPlan", projectWorkBigPlan);
		mv.addObject("tList", tList);
		mv.setViewName("weiaijia/project/updateProjectWorkPlan");
		return mv;
	}
	/**
	 * 编辑大类施工计划
	 * 
	 * @param request
	 * @param mv
	 * @return
	 * @throws ParseException 
	 */ 
	@RequestMapping("/updateWorkBigPlan")   
	public void updateWorkBigPlan(HttpServletResponse response, HttpServletRequest request, ProjectWorkBigPlan projectWorkBigPlan) throws ParseException {
		DateFormat df = DateFormat.getDateInstance();
		System.out.println(projectWorkBigPlan.getBigPlanStartTimeS());
		String planStartTime2 = String.valueOf(df.parse(projectWorkBigPlan.getBigPlanStartTimeS()).getTime());
		String planEndTime2 = String.valueOf(df.parse(projectWorkBigPlan.getBigPlanEndTimeS()).getTime());
		projectWorkBigPlan.setBigPlanStartTime(new BigInteger(planStartTime2));
		projectWorkBigPlan.setBigPlanEndTime(new BigInteger(planEndTime2));
		if(!projectWorkBigPlan.getBigRealStartTimeS().equals("") && projectWorkBigPlan.getBigRealStartTimeS()!=null){
			String realStartTime2 = String.valueOf(df.parse(projectWorkBigPlan.getBigRealStartTimeS()).getTime());
			projectWorkBigPlan.setBigRealStartTime(new BigInteger(realStartTime2));
		}
		if(!projectWorkBigPlan.getBigRealEndTimeS().equals("") && projectWorkBigPlan.getBigRealEndTimeS()!=null){
			String realEndTime2 = String.valueOf(df.parse(projectWorkBigPlan.getBigRealEndTimeS()).getTime());
			projectWorkBigPlan.setBigRealEndTime(new BigInteger(realEndTime2));
		}
		
		projectWorkBigPlanService.updateBigPlan(projectWorkBigPlan);
		output(response, JsonUtil.buildFalseJson("0", "编辑成功"));
	}
	/**
	 * 删除大类施工计划
	 * 
	 * @param request
	 * @param mv
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping("/deleteWorkBigPlan")
	public void deleteWorkBigPlan(HttpServletResponse response, HttpServletRequest request, ProjectWorkBigPlan projectWorkBigPlan) throws ParseException {
		projectWorkBigPlanService.delBigPlan(projectWorkBigPlan.getId());
		projectWorkPlanService.delWorkPlanByType(projectWorkBigPlan.getId());
		output(response, JsonUtil.buildFalseJson("0", "删除成功"));
	}
	/**
	 * 进入小类
	 * 
	 * @param request
	 * @param mv
	 * @return
	 */
	@RequestMapping("/goProjectMinWork")
	public ModelAndView goProjectMinWork(HttpServletRequest request, ModelAndView mv, ProjectWorkPlan projectWorkPlan) {
		mv.addObject("projectWorkPlan", projectWorkPlan);
		mv.setViewName("weiaijia/project/projectMinWork");
		return mv;
	}
	/**
	 * @param 获取小类计划
	 * @param request
	 * @param response
	 * @param vijProject
	 * @return
	 */
	@RequestMapping("/getProjectWorkPlan")
	public void getProjectWorkPlan(HttpServletRequest request, HttpServletResponse response, ProjectWorkPlan projectWorkPlan, Integer page,
			Integer limit) {
		if (page == null) {
			page = 1;
		}
		if (limit == null) {
			limit = 20;
		}
		
		int count = projectWorkPlanService.getWorkPlanCountByProId(projectWorkPlan);
		// 查詢方法
		List<ProjectWorkPlan> list = projectWorkPlanService.getWorkPlanList(projectWorkPlan, (page-1)*limit,limit);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for(int i=0; i<list.size(); i++){
			String planStartTime1 = String.valueOf(list.get(i).getPlanStartTime());
			String planEndTime1 = String.valueOf(list.get(i).getPlanEndTime());
	        long planStartTime2 = new Long(planStartTime1);
	        long planEndTime2 = new Long(planEndTime1);
	        list.get(i).setPlanStartTimeS(simpleDateFormat.format(new Date(planStartTime2)));
	        list.get(i).setPlanEndTimeS(simpleDateFormat.format(new Date(planEndTime2)));
	        if(list.get(i).getRealStartTime()!= null){
	        	String realStartTime1 = String.valueOf(list.get(i).getRealStartTime());
	        	long realStartTime2 = new Long(realStartTime1);
	        	list.get(i).setRealStartTimeS(simpleDateFormat.format(new Date(realStartTime2)));
	        }
			if(list.get(i).getRealEndTime()!= null){
				String realEndTime1 = String.valueOf(list.get(i).getRealEndTime());
				long realEndTime2 = new Long(realEndTime1);
				list.get(i).setRealEndTimeS(simpleDateFormat.format(new Date(realEndTime2)));
	        }
		}
		output(response, JsonUtil.buildJsonByTotalCount(list, count));
	}
	
	/**
	 * 进入添加小类施工计划
	 * 
	 * @param request
	 * @param mv
	 * @return
	 */
	@RequestMapping("/goAddWorkPlan")
	public ModelAndView goAddWorkPlan(HttpServletRequest request, ModelAndView mv, ProjectWorkPlan projectWorkPlan) {
		mv.addObject("projectWorkPlan", projectWorkPlan);
		mv.setViewName("weiaijia/project/addProjectWorkMinPlan");
		return mv;
	}
	
	/**
	 * 添加小类施工计划
	 * 
	 * @param request
	 * @param mv
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping("/addWorkPlan")
	public void addWorkPlan(HttpServletResponse response, HttpServletRequest request, ProjectWorkPlan projectWorkPlan) throws ParseException {
		DateFormat df = DateFormat.getDateInstance();
		
		String planStartTime2 = String.valueOf(df.parse(projectWorkPlan.getPlanStartTimeS()).getTime());
		String planEndTime2 = String.valueOf(df.parse(projectWorkPlan.getPlanEndTimeS()).getTime());
		projectWorkPlan.setPlanStartTime(new BigInteger(planStartTime2));
		projectWorkPlan.setPlanEndTime(new BigInteger(planEndTime2));
		projectWorkPlan.setCheckStatus(0);
		projectWorkPlan.setIsFinish(0);
		projectWorkPlanService.addWorkPlan(projectWorkPlan);
		output(response, JsonUtil.buildFalseJson("0", "添加成功"));
	}
	/**
	 * 进入编辑小类施工计划
	 * 
	 * @param request
	 * @param mv
	 * @return
	 */
	@RequestMapping("/goUpdateWorkPlan")
	public ModelAndView goUpdateWorkPlan(HttpServletRequest request, ModelAndView mv, ProjectWorkPlan projectWorkPlan) {
		List<ProjectWorkPlan> projectWorkPlans = projectWorkPlanService.getWorkPlanList(projectWorkPlan, null, null);
		projectWorkPlan = projectWorkPlans.get(0);
		String planStartTime1 = String.valueOf(projectWorkPlan.getPlanStartTime());
		String planEndTime1 = String.valueOf(projectWorkPlan.getPlanEndTime());
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long planStartTime2 = new Long(planStartTime1);
        long planEndTime2 = new Long(planEndTime1);
        projectWorkPlan.setPlanStartTimeS(simpleDateFormat.format(new Date(planStartTime2)));
        projectWorkPlan.setPlanEndTimeS(simpleDateFormat.format(new Date(planEndTime2)));
        if(projectWorkPlan.getRealStartTime()!= null){
        	String realStartTime1 = String.valueOf(projectWorkPlan.getRealStartTime());
        	long realStartTime2 = new Long(realStartTime1);
        	projectWorkPlan.setRealStartTimeS(simpleDateFormat.format(new Date(realStartTime2)));
        }
		if(projectWorkPlan.getRealEndTime()!= null){
			String realEndTime1 = String.valueOf(projectWorkPlan.getRealEndTime());
			long realEndTime2 = new Long(realEndTime1); 
			projectWorkPlan.setRealEndTimeS(simpleDateFormat.format(new Date(realEndTime2)));
        }
        
        mv.addObject("projectWorkPlan", projectWorkPlan);
		mv.setViewName("weiaijia/project/updateProjectWorkMinPlan");
		return mv;
	}
	/**
	 * 编辑小类施工计划
	 * 
	 * @param request
	 * @param mv
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping("/updateWorkPlan")
	public void updateWorkPlan(HttpServletResponse response, HttpServletRequest request, ProjectWorkPlan projectWorkPlan) throws ParseException {
		DateFormat df = DateFormat.getDateInstance();
		
		String planStartTime2 = String.valueOf(df.parse(projectWorkPlan.getPlanStartTimeS()).getTime());
		String planEndTime2 = String.valueOf(df.parse(projectWorkPlan.getPlanEndTimeS()).getTime());
		projectWorkPlan.setPlanStartTime(new BigInteger(planStartTime2));
		projectWorkPlan.setPlanEndTime(new BigInteger(planEndTime2));
		if(!projectWorkPlan.getRealStartTimeS().equals("") && projectWorkPlan.getRealStartTimeS()!=null){
			String realStartTime2 = String.valueOf(df.parse(projectWorkPlan.getRealStartTimeS()).getTime());
			projectWorkPlan.setRealStartTime(new BigInteger(realStartTime2));
		}
		if(!projectWorkPlan.getRealEndTimeS().equals("") && projectWorkPlan.getRealEndTimeS()!=null){
			String realEndTime2 = String.valueOf(df.parse(projectWorkPlan.getRealEndTimeS()).getTime());
			projectWorkPlan.setRealEndTime(new BigInteger(realEndTime2));
		}
		projectWorkPlanService.updateWorkPlan(projectWorkPlan);
		output(response, JsonUtil.buildFalseJson("0", "编辑成功"));
	}
	
	/**
	 * 删除小类施工计划
	 * 
	 * @param request
	 * @param mv
	 * @return  
	 * @throws ParseException 
	 */
	@RequestMapping("/deleteWorkPlan")
	public void deleteWorkPlan(HttpServletResponse response, HttpServletRequest request, ProjectWorkPlan projectWorkPlan) throws ParseException {
		projectWorkPlanService.delWorkPlan(projectWorkPlan.getPlanId());
		output(response, JsonUtil.buildFalseJson("0", "删除成功"));
	}
	/**
	 * 施工进度
	 * 
	 * @param request
	 * @param mv
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping("/goWorkJindu")
	public ModelAndView goWorkJindu(HttpServletRequest request, ModelAndView mv, Integer projectId) {
		ProjectWorkBigPlan projectWorkBigPlan = new ProjectWorkBigPlan();
		projectWorkBigPlan.setProjectId(projectId);
		List<ProjectWorkBigPlan> tList = projectWorkBigPlanService.getBigPlanList(projectWorkBigPlan, null, null);
		for (int i=0;i<tList.size();i++) {
			tList.get(i).setPlanList(projectWorkPlanService.getWorkPlanListByType(tList.get(i).getId()));
		} 
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for(int i=0; i<tList.size(); i++){
			if(tList.get(i).getBigRealStartTime()!=null && tList.get(i).getBigRealEndTime()!=null){
				String RealStartTime1 = String.valueOf(tList.get(i).getBigRealStartTime());
				String RealEndTime1 = String.valueOf(tList.get(i).getBigRealEndTime());
		        long RealStartTime2 = new Long(RealStartTime1);
		        long RealEndTime2 = new Long(RealEndTime1);
		        tList.get(i).setBigRealStartTimeS(simpleDateFormat.format(new Date(RealStartTime2)));
		        tList.get(i).setBigRealEndTimeS(simpleDateFormat.format(new Date(RealEndTime2)));
		        for(int j=0; j<tList.get(i).getPlanList().size(); j++){
		        	if(tList.get(i).getPlanList().get(j).getRealStartTime()!=null && tList.get(i).getPlanList().get(j).getRealEndTime()!=null){
			        	String planStartTime11 = String.valueOf(tList.get(i).getPlanList().get(j).getRealStartTime());
						String planEndTime11 = String.valueOf(tList.get(i).getPlanList().get(j).getRealEndTime());
				        long planStartTime22 = new Long(planStartTime11);
				        long planEndTime22 = new Long(planEndTime11);
				        tList.get(i).getPlanList().get(j).setRealStartTimeS(simpleDateFormat.format(new Date(planStartTime22)));
				        tList.get(i).getPlanList().get(j).setRealEndTimeS(simpleDateFormat.format(new Date(planEndTime22)));
		        	}
		        }
			}
		}
		mv.addObject("tList", tList);
		mv.setViewName("weiaijia/project/projectWorkJindu");
		return mv;
	}
	/**
	 * 进入样板间列表
	 * 
	 * @param request
	 * @param mv
	 * @return
	 */
	@RequestMapping("/goModelRoom")
	public ModelAndView goModelRoom(HttpServletRequest request, ModelAndView mv) {
		mv.setViewName("weiaijia/project/modelRoomList");
		return mv;
	}
	/**
	 * @param 获取大类计划
	 * @param request
	 * @param response
	 * @param vijProject
	 * @return
	 */
	@RequestMapping("/getModelRoomList")
	public void getModelRoomList(HttpServletRequest request, HttpServletResponse response, ModelRoom room, Integer page,
			Integer limit) {
		if (page == null) {
			page = 1;
		}
		if (limit == null) {
			limit = 20;
		}
		int count = modelRoomService.getModelRoomCount(room);
		// 查詢方法
		List<ModelRoom> list = modelRoomService.getModelRoomList(room, (page-1)*limit,limit);
		
        output(response, JsonUtil.buildJsonByTotalCount(list, count));
	}
	/**
	 * 进入样板间添加
	 * 
	 * @param request
	 * @param mv
	 * @return
	 */
	@RequestMapping("/goAddModelRoom")
	public ModelAndView goAddModelRoom(HttpServletRequest request, ModelAndView mv) {
		mv.setViewName("weiaijia/project/addModelRoom");
		return mv;
	}
	
	/**
	 * 添加样板间
	 * 
	 * @param request
	 * @param mv
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping("/addModelRoom")
	public void addModelRoom(HttpServletResponse response, HttpServletRequest request, ModelRoom room) throws ParseException {
		room.setIsEffect(1);
		room.setCreateTime(new Date());
		modelRoomService.addModelRoom(room);
		output(response, JsonUtil.buildFalseJson("0", "添加成功"));
	}
	/**
	 * 进入样板间编辑
	 * 
	 * @param request
	 * @param mv
	 * @return
	 */
	@RequestMapping("/goUpdateModelRoom")
	public ModelAndView goUpdateModelRoom(HttpServletRequest request, ModelAndView mv, ModelRoom room) {
		ModelRoom room_1 = modelRoomService.getInfoById(room.getId());
		mv.addObject("room", room_1);
		mv.setViewName("weiaijia/project/updateModelRoom");
		return mv;
	}
	/**
	 * 编辑样板间
	 * 
	 * @param request
	 * @param mv
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping("/updateModelRoom")
	public void updateModelRoom(HttpServletResponse response, HttpServletRequest request, ModelRoom room) throws ParseException {
		modelRoomService.updateModelRoom(room);
		output(response, JsonUtil.buildFalseJson("0", "编辑成功"));
	}
	/**
	 * 删除样板间
	 * 
	 * @param request
	 * @param mv
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping("/deleteModelRoom")
	public void deleteModelRoom(HttpServletResponse response, HttpServletRequest request, ModelRoom room) throws ParseException {
		modelRoomService.delModelRoom(room.getId());
		output(response, JsonUtil.buildFalseJson("0", "删除成功"));
	}
	
/*******************************************验收不通过意见*************************************************************************/	
	/***
	 * 进入页面：验收不通过意见是在每项验收后写的。
	 * @param response
	 * @param mv
	 * @param pCheckView
	 * @return
	 */
	@RequestMapping("/projectCheckView")
	public ModelAndView projectCheckView(HttpServletResponse response,ModelAndView mv,ProjectCheckView pCheckView) {
		mv.addObject("pCheckView", pCheckView);
		mv.setViewName("weiaijia/project/projectCheckView");
		return mv;
		
	}
	
	/***
	 * 展示不通过意见
	 * @param response
	 * @param pWorkPlan
	 * @return
	 */
	@RequestMapping("/doProjectCheckViewList")
	public void doProjectCheckViewList(HttpServletResponse response,ProjectCheckView pCheckView,Integer page,Integer limit) {
		if (page == null) {
			page = 1;
		}
		if (limit == null) {
			limit = 20;
		}
		int count = pCheckViewService.getProjectCheckViewCount(pCheckView);
		List<ProjectCheckView> list =pCheckViewService.getProjectCheckViewList(pCheckView, (page-1)*limit, limit);
		output(response, JsonUtil.buildJsonByTotalCount(list, count));
	}
	
	/***
	 * 添加验收不通过意见:（不通过的意见是APP上传过来的，这里只展示）：废除
	 * @param response
	 * @param mv
	 * @param smallPlanId
	 * @return
	 */
	@RequestMapping("/addProjectCheckView")
	public ModelAndView addProjectCheckView(HttpServletResponse response,ModelAndView mv,Integer smallPlanId) {
		ProjectCheckView pCheckView = pCheckViewService.getProjectCheckViewById(smallPlanId);
		mv.addObject("pCheckView",pCheckView);
		mv.setViewName("weiaijia/project/acceptanceWorkPlay");
		return mv;
	}
	/***
	 * 确定验收不通过原因
	 * @param response
	 * @param pCheckView
	 */
	@RequestMapping("/uploadProjectCheckView")
	public void uploadProjectCheckView(HttpServletResponse response,ProjectCheckView pCheckView) {
		if (pCheckView.getSmallPlanId() == null) {
			output(response, JsonUtil.buildFalseJson("1", "非法参数"));
		}else{
			pCheckView.setCreateTime(new Date());
			pCheckViewService.uploadProjectCheckView(pCheckView);
			output(response, JsonUtil.buildFalseJson("0", "确定成功"));
		}
	}
	/***
	 * 删除验收小项
	 * @param response
	 * @param pWorkPlan
	 */
	@RequestMapping("/delProjectCheckView")
	public void delProjectCheckView(HttpServletResponse response,ProjectWorkPlan pWorkPlan) {
		if (pWorkPlan.getPlanId() ==null) {
			output(response, JsonUtil.buildFalseJson("1", "删除失败"));
		}else {
			if (pWorkPlan.getPlanId() != null) {
				projectWorkPlanService.delWorkPlan(pWorkPlan.getPlanId());
				output(response, JsonUtil.buildFalseJson("0", "删除成功"));
			}
		}
	}
}
