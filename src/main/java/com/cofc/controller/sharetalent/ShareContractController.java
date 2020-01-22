package com.cofc.controller.sharetalent;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cofc.pojo.BackUser;
import com.cofc.pojo.ContractContent;
import com.cofc.pojo.ContractPunishment;
import com.cofc.pojo.ContractReason;
import com.cofc.pojo.ContractRecord;
import com.cofc.pojo.ShareContract;
import com.cofc.service.ContractContentService;
import com.cofc.service.ContractPunishmentService;
import com.cofc.service.ContractReasonService;
import com.cofc.service.ContractRecordService;
import com.cofc.service.ShareContractService;
import com.cofc.util.BaseUtil;
import com.cofc.util.JsonUtil;

@Controller
@RequestMapping("/shareContract")
public class ShareContractController extends BaseUtil {

	@Resource
	ShareContractService shareContractService;
	@Resource
	private ContractContentService contentService;
	@Resource
	private ContractReasonService reasonService;
	@Resource
	private ContractPunishmentService punishmentService;
	@Resource
	private ContractRecordService contractRecordService;
	public static Logger log = Logger.getLogger("ShareContractController");

	// 合约列表
	@RequestMapping("/goContractList")
	private ModelAndView goContractListJsp(ModelAndView modelView, HttpServletRequest request) {
		modelView.setViewName("/shareTaskManage/shareContractList");
		return modelView;
	}

	// 合约原因列表
	@RequestMapping("/goReasonList")
	public ModelAndView goReasonList(ModelAndView modelView, HttpServletRequest request) {
		modelView.setViewName("/shareTaskManage/shareReasonList");
		return modelView;
	}

	// 合约内容列表
	@RequestMapping("/goContentList")
	public ModelAndView goContentList(ModelAndView modelView, HttpServletRequest request) {
		modelView.setViewName("/shareTaskManage/shareContentList");
		return modelView;
	}

	// 合约惩罚列表
	@RequestMapping("/goPunishment")
	public ModelAndView goPunishment(ModelAndView modelView, HttpServletRequest request) {
		modelView.setViewName("/shareTaskManage/sharePunishmentList");
		return modelView;
	}

	// 合约惩罚列表
	@RequestMapping("/goRecordList")
	public ModelAndView goRecordList(ModelAndView modelView, HttpServletRequest request) {
		modelView.setViewName("/shareTaskManage/shareRecordList");
		return modelView;
	}
    
	//添加合约原因页面
	@RequestMapping("/addReason")
	public ModelAndView addReason(ModelAndView modelView) {
			modelView.setViewName("/shareTaskManage/addReason");
			return modelView;
			
	}
	
	@RequestMapping("/doaddreason")
	public void doAddReason(HttpServletResponse response,String reason,HttpServletRequest request){
		BackUser buser = (BackUser) request.getSession().getAttribute("loginer");
		ContractReason Reason = reasonService.getReasonTitle(reason);
		if(Reason != null){
			output(response, JsonUtil.buildFalseJson("1", "该合约内容已存在"));
		}else{
			ContractReason re = new ContractReason();
			re.setCreateTime(new Date());
			re.setContractTitle(reason);
			re.setUsedCount(0);
			re.setCreateUser(buser.getUserId());
			reasonService.addContractReason(re);
			output(response, JsonUtil.buildFalseJson("0", "添加合约内容成功"));
		}
	}
	
	
	//添加合约内容页面
	@RequestMapping("/addContent")
	public ModelAndView addContent(ModelAndView modelView) {
		modelView.setViewName("/shareTaskManage/addContent");
		return modelView;		
	}
	@RequestMapping("/doaddcontent")
	public void doAddContent(HttpServletResponse response,String content,HttpServletRequest request){
		BackUser buser = (BackUser) request.getSession().getAttribute("loginer");
		ContractContent Content = contentService.getContentTitle(content);
		if(Content != null){
			output(response, JsonUtil.buildFalseJson("1", "该合约内容已存在"));
		}else{
			ContractContent co = new ContractContent();
			co.setCreateTime(new Date());
			co.setContentTitle(content);
			co.setUsedCount(0);
			co.setCreateUser(buser.getUserId());
			contentService.addContractContent(co);
			output(response, JsonUtil.buildFalseJson("0", "添加合约内容成功"));
		}
	}
	//添加惩罚页面
	@RequestMapping("/addPublisher")
	public ModelAndView addPublisher(ModelAndView modelView) {
		modelView.setViewName("/shareTaskManage/addPublisher");
		return modelView;		
	}
	@RequestMapping("/doaddpublisher")
	public void doAddPublisher(HttpServletResponse response,String content,HttpServletRequest request){
		BackUser buser = (BackUser) request.getSession().getAttribute("loginer");
		ContractPunishment Content = punishmentService.getPunishmentTitle(content);
		if(Content != null){
			output(response, JsonUtil.buildFalseJson("1", "该合约惩罚已存在"));
		}else{
			ContractPunishment co = new ContractPunishment();
			co.setCreateTime(new Date());
			co.setPunishmentContent(content);
			co.setUsedCount(0);
			co.setCreateUser(buser.getUserId());
			punishmentService.addContractPunishment(co);
			output(response, JsonUtil.buildFalseJson("0", "添加合约惩罚成功"));
		}
	}
	//AJAX查询合约
	@RequestMapping("/showSharetList")
	public void showSharetList(HttpServletResponse response,Integer page,Integer limit,ShareContract contract){
		List<ShareContract> contracts  = shareContractService.getShareContractList(contract.getSharedUserId(), (page-1)*limit, limit);
		int rowsCount = shareContractService.getShareContractCount(contract);
		output(response, JsonUtil.buildJsonByTotalCount(contracts, rowsCount));
	}
	
	//AJAX查询合约内容
	@RequestMapping("/showContractList")
	public void showContractList(HttpServletResponse response,Integer page,Integer limit,ContractContent content){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("page", (page-1)*limit);
		map.put("limit", limit);
		map.put("content", content);
		List<ContractContent> contentList = contentService.getContractContentList(map);
		int rowsCount =  contentService.getContractContentCount(map);
		output(response, JsonUtil.buildJsonByTotalCount(contentList, rowsCount));
	}
	
	//AJAX查询合约原因
	@RequestMapping("/showReasonList")
	public void showReasonList(HttpServletResponse response,Integer page,Integer limit,ContractReason reason){		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("page", (page-1)*limit);
		map.put("limit", limit);
		map.put("reason", reason);
		List<ContractReason> reasons = reasonService.getContractReasonList(map);
		int rowsCount =  reasonService.getContractReasonCount(map);
		output(response, JsonUtil.buildJsonByTotalCount(reasons, rowsCount));
	}
	
	//AJAX查询合约惩罚
	@RequestMapping("/showPunishmentList")
	public void showPunishmentList(HttpServletResponse response,Integer page,Integer limit,ContractPunishment punishment){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("page", (page-1)*limit);
		map.put("limit", limit);
		map.put("punishment", punishment);
		List<ContractPunishment> punishments = punishmentService.getContractPunishmentList(map);
		int rowsCount =  punishmentService.getContractPunishmentCount(map);
		output(response, JsonUtil.buildJsonByTotalCount(punishments, rowsCount));
	}
	
	//打赏记录表
	@RequestMapping("/showRecordList")
	public void showRecordList(HttpServletResponse response,Integer page,Integer limit,ContractRecord record){	
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("page", (page-1)*limit);
		map.put("limit", limit);
		map.put("record", record);
		List<ContractRecord> records = contractRecordService.getContractRecordList(null, (page-1)*limit, limit,null,null);
		int rowsCount = contractRecordService.getgetContractRecordCount(map);
		output(response, JsonUtil.buildJsonByTotalCount(records, rowsCount));
	}
	
	/**
	 * 进入修改内容页面
	 * @param mv
	 * @param request
	 * @param contentId
	 * @return
	 */
	@RequestMapping("/goupdatecontent")
	public ModelAndView goupdateContent(ModelAndView mv,HttpServletRequest request,Integer contentId,String contentTitle){
		mv.addObject("contentId",contentId);
		mv.addObject("contentTitle",contentTitle);
		mv.setViewName("/shareTaskManage/updatecontent");
		return mv;
	}
	
	/**
	 * 实现修改内容操作
	 * @param response
	 * @param contentTitle
	 * @param contentId
	 */
	@RequestMapping("/doupdatecontent")
	public void doupdateContent(HttpServletResponse response,String contentTitle,Integer contentId){
		if(contentId == null){
			output(response,JsonUtil.buildFalseJson("1","传递参数非法"));
		}else{
			ContractContent content = contentService.getContentTitle(contentTitle);
			if(content != null){
				output(response,JsonUtil.buildFalseJson("1","内容已存在"));
			}else{
				ContractContent c = new ContractContent();
				c.setContentId(contentId);
				c.setContentTitle(contentTitle);
				c.setUpdateTime(new Date());
				contentService.updateContractContent(c);
				output(response,JsonUtil.buildFalseJson("0","修改内容成功"));
			}
		}
	}
	
	/**
	 * 进入修改原因页面
	 * @param mv
	 * @param request
	 * @param reasonId
	 * @return
	 */
	@RequestMapping("/goupdatereason")
	public ModelAndView goupdateReason(ModelAndView mv,HttpServletRequest request,Integer reasonId,String contractTitle){
		mv.addObject("reasonId",reasonId);
		mv.addObject("contractTitle",contractTitle);
		mv.setViewName("/shareTaskManage/updatereason");
		return mv;
	}
	
	/**
	 * 实现修改原因操作
	 * @param response
	 * @param reasonName
	 * @param reasonId
	 */
	@RequestMapping("/doupdatereason")
	public void doupdateReason(HttpServletResponse response,String contractTitle,Integer reasonId){
		if(reasonId == null){
			output(response,JsonUtil.buildFalseJson("1","传递参数非法"));
		}else{
			ContractReason reason = reasonService.getReasonTitle(contractTitle);
			if(reason != null){
				output(response,JsonUtil.buildFalseJson("1","内容已存在"));
			}else{
				ContractReason r = new ContractReason();
				r.setContractTitle(contractTitle);
				r.setUpdateTime(new Date());
				r.setReasonId(reasonId);
				reasonService.updateContractReason(r);
				output(response,JsonUtil.buildFalseJson("0","修改原因成功"));			
			}
		}
	}
	
	/**
	 * 进入修改惩罚页面
	 * @param mv
	 * @param request
	 * @param punishmentId
	 * @return
	 */
	@RequestMapping("/goupdatepunish")
	public ModelAndView goupdatePunish(ModelAndView mv,HttpServletRequest request,Integer punishmentId,String punishmentContent){
		mv.addObject("punishmentId",punishmentId);
		mv.addObject("punishmentContent",punishmentContent);
		mv.setViewName("/shareTaskManage/updatepunish");
		return mv;
	}
	
	/**
	 * 实现修改惩罚操作
	 * @param response
	 * @param punishmentContent
	 * @param punishmentId
	 */
	@RequestMapping("/doupdatepunish")
	public void doupdatePunish(HttpServletResponse response,String punishmentContent,Integer punishmentId){
		if(punishmentId == null){
			output(response,JsonUtil.buildFalseJson("1","传递参数非法"));
		}else{
			 ContractPunishment punishment = punishmentService.getPunishmentTitle(punishmentContent);
			if(punishment != null){
				output(response,JsonUtil.buildFalseJson("1","内容已存在"));
			}else{
				ContractPunishment p = new ContractPunishment();
				p.setPunishmentContent(punishmentContent);
				p.setPunishmentId(punishmentId);
				p.setUpdateTime(new Date());
				punishmentService.updateContractPunishment(p);
				output(response,JsonUtil.buildFalseJson("0","修改成功"));			
			}
		}
	}
	
	/**
	 * 删除内容
	 * @param response
	 * @param contentId
	 */
	@RequestMapping("/delcontent")
	public void delContent(HttpServletResponse response,Integer contentId){
		if(contentId == null){
			output(response,JsonUtil.buildFalseJson("1","传递参数非法"));
		}else{
			contentService.delShareContent(contentId);
			output(response,JsonUtil.buildFalseJson("0","删除成功"));
		}
	}
	
	/**
	 * 删除原因
	 * @param response
	 * @param reasonId
	 */
	@RequestMapping("/delreason")
	public void delReason(HttpServletResponse response,Integer reasonId){
		if(reasonId == null){
			output(response,JsonUtil.buildFalseJson("1","传递参数非法"));
		}else{
			reasonService.delShareReason(reasonId);
			output(response,JsonUtil.buildFalseJson("0","删除成功"));
		}
	}
	
	/**
	 * 删除惩罚
	 * @param response
	 * @param punishmentId
	 */
	@RequestMapping("delpunish")
	public void delPunish(HttpServletResponse response,Integer punishmentId){
		if(punishmentId == null){
			output(response,JsonUtil.buildFalseJson("1","传递参数非法"));
		}else{
			punishmentService.delSharePunish(punishmentId);
			output(response,JsonUtil.buildFalseJson("0","删除成功"));
		}
	}
	
	//删除合约
	@RequestMapping("/deleteContract")
	public void deleteContract(HttpServletResponse response,Integer contractId){
		shareContractService.deleteShareContract(contractId);
		output(response,JsonUtil.buildFalseJson("0","删除成功"));
	}
	//删除合约记录
	@RequestMapping("/deleteRecord")
	public void deleteRecord(HttpServletResponse response,Integer recordId){
		contractRecordService.deleteContractRecord(recordId);
		output(response,JsonUtil.buildFalseJson("0","删除成功"));
	}
}
