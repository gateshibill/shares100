package com.cofc.controller.aida;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cofc.pojo.UserCommon;
import com.cofc.pojo.aida.SalesPerson;
import com.cofc.pojo.aida.UserCard;
import com.cofc.pojo.aida.UserImpress;
import com.cofc.pojo.aida.WorkWeixin;
import com.cofc.service.UserCommonService;
import com.cofc.service.aida.SalesPersonService;
import com.cofc.service.aida.UserCardService;
import com.cofc.service.aida.UserImpressService;
import com.cofc.service.aida.WorkWeixinService;
import com.cofc.util.BaseUtil;
import com.cofc.util.JsonUtil;
@Controller
@RequestMapping("/workweixin")
public class WorkWeiXinController extends BaseUtil{
	@Resource
	private WorkWeixinService workService;
	@Resource
	private SalesPersonService salesPersonService;// 插入销售表
	@Resource
	private UserCommonService userComService;
	@Resource
	private UserCardService userCardService;
	@Resource
	private UserImpressService impressService;
	
	@RequestMapping("/index")
	public ModelAndView index(ModelAndView mv,HttpServletRequest request,WorkWeixin work){
		mv.addObject("work", work);
		mv.setViewName("workWeixin/worklist");
		return mv;
	}
	@RequestMapping("/getworklist")
	public void getworklist(HttpServletResponse response,WorkWeixin work,Integer page,Integer limit){
		if(page == null){
			page = 1;
		}
		if(limit == null){
			limit = 12;
		}
		int totalCount = workService.getWorkCount(work);
		List<WorkWeixin> lists = workService.getWorkList(work, (page-1)*limit, limit);
		output(response,JsonUtil.buildJsonByTotalCount(lists, totalCount));
	}
	@RequestMapping("/addWork")
	public ModelAndView addWork(HttpServletRequest request,ModelAndView mv){
		mv.setViewName("workWeixin/addWork");
		return mv;
	}
	/**
	 * 后续优化
	 * @param response
	 * @param work
	 */
	@RequestMapping("/doAddWork")
	public void doAddWork(HttpServletResponse response,WorkWeixin work){
		work.setIsEffect(1);
		work.setCreateTime(new Date());
		
		work.setAgentId(work.getAgentId().trim());
		work.setAppName(work.getAppName().trim());
		work.setAppSecret(work.getAppSecret().trim());
		work.setQyId(work.getQyId().trim());
		work.setWorkName(work.getWorkName().trim());
		workService.addWorkWeixin(work);
		
		//1/增加默认销售用户
		UserCommon 	comUser = new UserCommon();
		comUser.setHeadImage("https://www.haoshi360.com/xcximages/personal/headimg.png");
		comUser.setNickName(work.getAppName());
		comUser.setRealName(work.getAppName());
		comUser.setCreateTime(new Date());
		comUser.setLoginPlat(work.getXcxAppId());
		comUser.setUserPosition("首席服务官");
		comUser.setQyOpenId(work.getQyId());
		comUser.setCompony(work.getAppName());
		userComService.addNewUserCommon(comUser);
		
		//2.同步增加一个默认销售
		SalesPerson sale = new SalesPerson();
		sale.setAppId(work.getXcxAppId());
		sale.setUserId(comUser.getUserId());
		sale.setPosition(comUser.getUserPosition());
		sale.setUserName(work.getAppName());
		sale.setCreateTime(new Date());
		sale.setHeadImage(comUser.getHeadImage());
		sale.setRole(0);
		sale.setIsdefault(1);//关键要设为默认
		salesPersonService.addSalesPerson(sale);
		
		//3.给默认销售插入一条名片记录
		UserCard card = new UserCard();
		card.setAppId(sale.getAppId());
		card.setEmail("Alice@feikantec.com");
		card.setRealName(comUser.getRealName());
		card.setCompany(work.getAppName());
		card.setPosition(sale.getPosition());
		card.setGender(0);
		card.setHeadImage(comUser.getHeadImage());
		card.setPhone("13480661077");
		card.setWechat("13480661077");
		card.setUserId(sale.getUserId());
		card.setIsEffect(1);
		card.setCreateTime(new Date());
		card.initIntroduce();
		userCardService.addUserCard(card);
		
		// 增加默认印象
		UserImpress impress1 = new UserImpress();
		impress1.setAppId(card.getAppId());
		impress1.setUserId(card.getUserId());
		impress1.setTagName("贴心服务");
		impress1.setIsEffect(1);
		impress1.setNumber(182);
		impressService.addImpress(impress1);
		
		// 增加默认印象
		UserImpress impress2 = new UserImpress();
		impress2.setAppId(card.getAppId());
		impress2.setUserId(card.getUserId());
		impress2.setTagName("文艺气质");
		impress2.setIsEffect(1);
		impress2.setNumber(268);
		impressService.addImpress(impress2);
		
		output(response,JsonUtil.buildFalseJson("0","添加成功"));
	}
	@RequestMapping("/updateWork")
	public ModelAndView updateWork(HttpServletRequest request,ModelAndView mv,Integer id){
		WorkWeixin work = workService.getInfoById(id);
		mv.addObject("work", work);
		mv.setViewName("workWeixin/updateWork");
		return mv;
	}
	@RequestMapping("/doUpdateWork")
	public void doUpdateWork(HttpServletResponse response,WorkWeixin work){
		work.setAgentId(work.getAgentId().trim());
		work.setAppName(work.getAppName().trim());
		work.setAppSecret(work.getAppSecret().trim());
		work.setQyId(work.getQyId().trim());
		work.setWorkName(work.getWorkName().trim());
			
		workService.updateWorkWeixin(work);
		output(response,JsonUtil.buildFalseJson("0","编辑成功"));
	}
	@RequestMapping("/delWork")
	public void delWork(HttpServletResponse response,Integer id){
		workService.delWork(id);
		output(response,JsonUtil.buildFalseJson("0","删除成功"));
	}
}
