package com.cofc.controller.descovery;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cofc.pojo.ApplicationCommon;
import com.cofc.pojo.BackUser;
import com.cofc.pojo.DescoveryCommon;
import com.cofc.service.ApplicationCommonService;
import com.cofc.service.DescoveryCommonService;
import com.cofc.service.RecommendService;
import com.cofc.util.BaseUtil;
import com.cofc.util.JsonUtil;

@Controller
@RequestMapping("/wx/descovery2")
public class WXDescoveryController extends BaseUtil{
	@Resource
	private DescoveryCommonService descoveryService;
	@Resource
	private ApplicationCommonService applicationService;
	
	private HttpSession session;
	
	public static Logger log = Logger.getLogger("DescoveryController");
	
	@Autowired
	private RecommendService recommendService;
	
	@RequestMapping("/goList")
	private ModelAndView goDescoveryListJsp(ModelAndView modelView){
		List<ApplicationCommon> appList = applicationService.findApplicationsByCriteria(null, null, null, null, null, null, 0,
				10,null, null, null);
		log.info("进入发现列表的jsp页面");
		modelView.addObject("appList", appList);
		modelView.setViewName("/descoveryManage/descoveryList");
		return modelView;
	}
	
	@RequestMapping("/goAddDescovery")
	public ModelAndView goAddDescovery(ModelAndView modelView, Integer id) {

		modelView.setViewName("descoveryManage/addDescovery");
		modelView.addObject("id", id);
		
		return modelView;
	}
	
	@RequestMapping("/showDataList")
	private void showDescoveryList(HttpServletRequest request,HttpServletResponse response,Integer loginPlat,DescoveryCommon descovery,String userKeyWords,String searchBeginDate,String searchEndDate,Integer pageNo,Integer pageSize) throws ParseException{
		if(pageNo==null){
			pageNo=1;
		}
		if(pageSize==null){
			pageSize=20;
		}
		request.getSession().setAttribute("currdescoPage", pageNo);
//		goods.setGoodsPlat(1);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat startSdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		SimpleDateFormat endSdf = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
		String startTime = null;
		String endTime = null;
		if (searchBeginDate != null && searchBeginDate != "") {
			startTime = startSdf.format(sdf.parse(searchBeginDate));
		}
		if (searchEndDate != null && searchEndDate != "") {
			endTime = endSdf.format(sdf.parse(searchEndDate));
		}
		int count = descoveryService.getDescoveryCount(descovery,userKeyWords,startTime,endTime);
		List<DescoveryCommon> dcList = descoveryService.findDescveryCommonInfo(descovery,userKeyWords,startTime,endTime,(pageNo-1)*pageSize,pageSize);
		output(response, JsonUtil.buildBackJson(dcList, count));
	}
	
	@RequestMapping("/sxjDescovery")
	public String shangxiajiaDescovery(DescoveryCommon descov){
		descoveryService.updateDescoveryCommonInfo(descov);
		return "redirect:./showDataList.do";
	}
	
	/**
	 * 发布文章
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/addNewDescovery", method = RequestMethod.POST)
	@ResponseBody
	public void addNewDescovery(HttpServletRequest request, HttpServletResponse response,DescoveryCommon dc) {
		session = request.getSession();
		BackUser user = (BackUser) session.getAttribute("loginer");

		String descoveryTitle = request.getParameter("descoveryTitle");
		Integer id = Integer.parseInt(request.getParameter("id"));
		
		if (descoveryTitle != null && descoveryTitle != "") {
			dc.setDescoveryTitle(descoveryTitle);
		}
		dc.setDescoveryImage("");
		dc.setDescoveryType(0);
		dc.setIsPass(-1);
		dc.setIsShangJia(-1);
		
		dc.setPublishTime(new Date());
		if (user != null) {
			dc.setPublisherId(user.getUserId());
		}
		if (id != null && id != -1) {
			dc.setLoginPlat(id);
		}
		
		dc.setPariseCount(0);
		dc.setReadCount(0);
		dc.setJoinedCount(0);
		dc.setCollectCount(0);
		
		descoveryService.addNewDesCommon(dc);

		DescoveryCommon dc2 = descoveryService.getDescoveryById(dc.getDescoveryId());

		if (dc2 == null) {
			output(response, JsonUtil.buildFalseJson("1", "新增失败"));
		} else {
			output(response, JsonUtil.buildFalseJson("0", "新增成功"));
		}
	}
	//前端发布资讯
	@RequestMapping("/addInformation")
	public void addInformation(HttpServletResponse response,DescoveryCommon descovery){
		try {
			log.info("进入发布咨询");
			descovery.setPublishTime(new Date());
			if (descovery.getDescoveryType() == null) {
				descovery.setDescoveryType(descovery.getDescoveryType());
			}
			descovery.setIsPass(1);
			descovery.setIsShangJia(1);
			descovery.setPariseCount(0);
			descovery.setReadCount(0);
			//descovery.setIsTop(1);  //新版有推荐系统，不用自动置顶
			descovery.setJoinedCount(0);
			descovery.setCollectCount(0);
			descovery.setIsRecommend(0);
			if (descovery.getDescoveryImage() != null && !descovery.getDescoveryImage().equals("")) {
				log.info("进入发布资讯拿图片"+descovery.getDescoveryDetails());
				String [] my = descovery.getDescoveryImage().split(",");
				String myImage = "";
				for (int i = 0; i < my.length; i++) {
					myImage += "<img src='"+my[i]+"'>"+"<br>";
				}
				descovery.setDescoveryDetails(descovery.getDescoveryDetails()+"<br>"+ myImage);
			} else {
				log.info("进入发布资讯拿详情"+descovery.getDescoveryDetails());
				descovery.setDescoveryDetails(descovery.getDescoveryDetails());
			}
			descoveryService.addNewDesCommon(descovery);
			
			DescoveryCommon desc = descoveryService.getDescoveryById(descovery.getDescoveryId());
			
			List<DescoveryCommon> descList = new ArrayList<DescoveryCommon>();
			descList.add(desc);
			
			
			//更新推荐表
			Thread thread = new Thread(new Runnable() {	
				@Override
				public void run() {
					recommendService.updateDescoveryRecommendByDescoveryID(desc);
				}
			});
			thread.start();
			
			output(response, JsonUtil.buildJson(descList));
			log.info("发布资讯成功");
		} catch (Exception e) {
			log.info("发布资讯失败");
			output(response, JsonUtil.buildFalseJson("1", "新增失败!"));
		}
	}
	
	
	//发布问答
	@RequestMapping("/publisherAskanswer")
	public void publisherAskanswer(HttpServletResponse response,DescoveryCommon descovery){
		log.info("进入发布咨询");
		descovery.setPublishTime(new Date());
		if (descovery.getDescoveryType() == null) {
			descovery.setDescoveryType(descovery.getDescoveryType());
		}
		descovery.setIsPass(1);
		descovery.setIsShangJia(1);
		descovery.setPariseCount(0);
		descovery.setReadCount(0);
		descovery.setDescoveryTitle("问答");
		descovery.setJoinedCount(0);
		descovery.setCollectCount(0);
		descovery.setIsRecommend(0);
		descoveryService.addNewDesCommon(descovery);
		
		DescoveryCommon desc = descoveryService.getDescoveryById(descovery.getDescoveryId());
		
		List<DescoveryCommon> descList = new ArrayList<DescoveryCommon>();
		descList.add(desc);
		output(response, JsonUtil.buildJson(descList));
	}
	public static void main(String[] args) {
		String aString = "1234";
		String b = "122435";
		System.out.println("aergetrgw"+aString+"\n"+"<img src='"+b+"'>");
	}
}
