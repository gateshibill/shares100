package com.cofc.controller.weiaijia.admin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cofc.pojo.ApplicationCommon;
import com.cofc.pojo.BackUser;
import com.cofc.pojo.vij.CardTicket;
import com.cofc.pojo.vij.UserTicket;
import com.cofc.service.ApplicationCommonService;
import com.cofc.service.vij.CardTicketService;
import com.cofc.service.vij.UserTicketService;
import com.cofc.util.BaseUtil;
import com.cofc.util.JsonUtil;
@Controller
@RequestMapping("/cardticket")
public class CardTicketController extends BaseUtil{
	@Resource
	private CardTicketService cardTicketService;//卡券
	@Resource
	private UserTicketService userTicketService;//用户的卡券
	@Resource
	private ApplicationCommonService applicationService;//应用
	public static Logger log = Logger.getLogger("CardTicketController");
	/**
	 * 卡券列表
	 * @param request
	 * @param mv
	 * @return
	 */
	@RequestMapping("/index")
	public ModelAndView index(HttpServletRequest request,ModelAndView mv,CardTicket ticket){
		mv.addObject("ticket", ticket);
		mv.setViewName("weiaijia/cardticket/index");
		return mv;
	}
	/**
	 * 获取卡券列表数据
	 * @param response
	 * @param ticket
	 * @param page
	 * @param limit
	 */
	@RequestMapping("/getCardTicketList")
	public void getCardTicketList(HttpServletRequest request,HttpServletResponse response,CardTicket ticket,Integer page,Integer limit){
		BackUser bu = (BackUser) request.getSession().getAttribute("loginer");
		List<CardTicket> lists = new ArrayList<CardTicket>();
		int totalCount = 0;
		if(bu == null){
			output(response,JsonUtil.buildJsonByTotalCount(lists, 0));
		}else{
			if(page == null){
				page = 1;
			}
			if(limit == null){
				limit = 10;
			}
			if(bu.getLoginPlat() == null){
				totalCount = cardTicketService.getCardTicketCountByLoginPlat(ticket, null);
				lists = cardTicketService.getCardTicketListByLoginPlat(ticket, null, (page-1)*limit, limit);
			}else{
				String[] idStrings = bu.getLoginPlat().split(",");
				List<String> loginPlatList = Arrays.asList(idStrings);
				totalCount = cardTicketService.getCardTicketCountByLoginPlat(ticket, loginPlatList);
				lists = cardTicketService.getCardTicketListByLoginPlat(ticket, loginPlatList, (page-1)*limit, limit);
			}
			output(response,JsonUtil.buildJsonByTotalCount(lists, totalCount));
		}
		
		
	}
	/**
	 * 添加卡券页面
	 * @param request
	 * @param mv
	 * @return
	 */
	@RequestMapping("/addCardTicket")
	public ModelAndView addCardTicket(HttpServletRequest request,ModelAndView mv){
		BackUser bu = (BackUser) request.getSession().getAttribute("loginer");
		List<ApplicationCommon> commList = new ArrayList<ApplicationCommon>();
		if(bu.getLoginPlat() == null || bu.getLoginPlat().equals("")){
			commList = applicationService.findApplicationCommon(null);
		}else{
			String[] idStrings = bu.getLoginPlat().split(",");
			List<String> loginPlatList = Arrays.asList(idStrings);
			commList = applicationService.getApplicationByLoginPlat(loginPlatList);
		}
		mv.addObject("commList", commList);
		mv.setViewName("weiaijia/cardticket/add");
		return mv;
	}
	/**
	 * 执行添加
	 * @param response
	 * @param ticket
	 */
	@RequestMapping("/doAddCardTicket")
	public void doAddCardTicket(HttpServletResponse response,CardTicket ticket){
		if(ticket.getLoginPlat() == null){
			output(response,JsonUtil.buildFalseJson("1", "应用id不能为空"));
		}else{
			ticket.setCreateTime(new Date());
			ticket.setIsEffect(1);
			cardTicketService.addCardTicket(ticket);
			output(response,JsonUtil.buildFalseJson("0", "添加卡券成功"));
		}
	}
	/**
	 * 编辑卡券页面
	 * @param request
	 * @param mv
	 * @return
	 */
	@RequestMapping("/editCardTicket")
	public ModelAndView editCardTicket(HttpServletRequest request,ModelAndView mv,Integer ticketId){
		CardTicket ticket = cardTicketService.getInfoById(ticketId);
		mv.addObject("ticket", ticket);
		mv.setViewName("weiaijia/cardticket/edit");
		return mv;
	}
	/**
	 * 执行编辑
	 * @param response
	 * @param ticket
	 */
	@RequestMapping("/doEditCardTicket")
	public void doEditCardTicket(HttpServletResponse response,CardTicket ticket){
		if(ticket.getTicketId() == null){
			output(response,JsonUtil.buildFalseJson("1", "参数非法"));
		}else{
			cardTicketService.updateCardTicket(ticket);
			output(response,JsonUtil.buildFalseJson("0", "编辑成功"));
		}
	}
	/**
	 * 删除卡券
	 * @param response
	 * @param ticketId
	 */
	@RequestMapping("/delCardTicket")
	public void delCardTicket(HttpServletResponse response,Integer ticketId){
		if(ticketId == null){
			output(response,JsonUtil.buildFalseJson("1", "参数非法"));
		}else{
			cardTicketService.delCardTicket(ticketId);
			output(response,JsonUtil.buildFalseJson("0", "删除成功"));
		}
	}
	/**
	 * 给指定用户分发卡券
	 * @param response
	 * @param userTicket
	 */
	@RequestMapping("/giveCardTicketforUser")
	public void giveCardTicketforUser(HttpServletResponse response,UserTicket userTicket){
		if(userTicket.getTicketId() == null){
			output(response,JsonUtil.buildFalseJson("1", "非法卡券"));
		}else{
			if(userTicket.getUserId() == null){
				output(response,JsonUtil.buildFalseJson("1", "非法用户"));
			}else{
				//检测该用户是否存在有这张卡券
				int count = userTicketService.checkHasCardCount(userTicket.getUserId(), userTicket.getTicketId(), null);
				if(count > 0){
					output(response,JsonUtil.buildFalseJson("1", "该用户已经拥有该卡券"));
				}else{
					//执行插入
					userTicket.setIsUse(0);
					userTicketService.addUserTicket(userTicket);
					output(response, JsonUtil.buildFalseJson("0", "分发卡券成功"));
				}
			}
		}
	}
	/**
	 * 卡券记录
	 * @param request
	 * @param mv
	 * @param userTicket
	 * @return
	 */
	@RequestMapping("/userTicketIndex")
	public ModelAndView userTicketIndex(HttpServletRequest request,ModelAndView mv,UserTicket userTicket){
		mv.addObject("userTicket", userTicket);
		mv.setViewName("weiaijia/cardticket/ticketindex");
		return mv;
	}
	/**
	 * 卡券记录列表
	 * @param response
	 * @param ticket
	 * @param page
	 * @param limit
	 */
	@RequestMapping("/getUserTicketList")
	public void getUserTicketList(HttpServletResponse response,UserTicket ticket,Integer page,Integer limit){
		if(page == null){
			page = 1;
		}
		if(limit == null){
			limit = 10;
		}
		int totalCount = userTicketService.getUserTicketCount(ticket);
		List<UserTicket> lists = userTicketService.getUserTicketList(ticket,(page-1)*limit,limit);
		output(response, JsonUtil.buildJsonByTotalCount(lists, totalCount));
	}
			
}
