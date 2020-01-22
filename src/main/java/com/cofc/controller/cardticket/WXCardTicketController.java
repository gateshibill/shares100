package com.cofc.controller.cardticket;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cofc.pojo.UserOrder;
import com.cofc.pojo.vij.CardTicket;
import com.cofc.pojo.vij.UserTicket;
import com.cofc.service.UserOrderService;
import com.cofc.service.vij.CardTicketService;
import com.cofc.service.vij.UserTicketService;
import com.cofc.util.BaseUtil;
import com.cofc.util.JsonUtil;
/**
 * 卡券
 * @author menghaoran
 *
 */
@Controller
@RequestMapping("/wx/cardticket")
public class WXCardTicketController extends BaseUtil{
	@Resource
	private CardTicketService cardTicketService; //卡券列表
	@Resource
	private UserTicketService userTicketService;//用户持有的卡券
	@Resource
	private UserOrderService orderService;//订单
	/**
	 * 获取卡券列表
	 * @param response
	 * @param ticket
	 * @param page
	 * @param limit
	 */
	@RequestMapping("/getCardTicketList")
	public void getCardTicketList(HttpServletResponse response,CardTicket ticket,Integer userId,
			Integer page,Integer limit){
		if(page == null){
			page = 1;
		}
		if(limit == null){
			limit = 10;
		}
		List<CardTicket> lists = cardTicketService.getCardTicketList(ticket, (page-1)*limit, limit);
		if(lists.size() > 0){
			 for (CardTicket cardTicket : lists) {
				 int count = userTicketService.checkHasCardCount(userId, cardTicket.getTicketId(), null);
				 if(count > 0){
					 cardTicket.setCardStatus(1);
				 }else{
					 cardTicket.setCardStatus(0); 
				 }
			 }
		}
		output(response,JsonUtil.buildJson(lists));
	}
	/**
	 * 用户领取卡券
	 * @param response
	 * @param ticket
	 */
	@RequestMapping("/getCardTicketById")
	public void getCardTicketById(HttpServletResponse response,UserTicket ticket){
		if(ticket.getUserId() == null){
			output(response,JsonUtil.buildFalseJson("1", "请登录"));
		}else{
			if(ticket.getTicketId() == null){
				output(response,JsonUtil.buildFalseJson("1", "该优惠券不存在"));
			}else{
				int count = userTicketService.checkHasCardCount(ticket.getUserId(), ticket.getTicketId(), null);
				if(count > 0){
					output(response,JsonUtil.buildFalseJson("1", "你已领取该优惠券无需重复领取"));
				}else{
					ticket.setIsUse(0);
					userTicketService.addUserTicket(ticket);
					output(response,JsonUtil.buildFalseJson("0", "领取优惠券成功"));
				}
			}
		}
	}
	/**
	 * 用户卡券列表
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
		List<UserTicket> lists = userTicketService.getUserTicketList(ticket, (page-1)*limit, limit);
		output(response,JsonUtil.buildJson(lists));
	}
	/**
	 * 更新订单和优惠券状态
	 */
	@RequestMapping("/updateCardAndOrder")
	public void updateCardAndOrder(HttpServletResponse response,Integer id,UserOrder order){
		UserTicket ticket = new UserTicket();
		ticket.setId(id);
		ticket.setIsUse(1);
		userTicketService.updateUserTicket(ticket);
		orderService.updateUserOrder(order);
		output(response,JsonUtil.buildFalseJson("0", "更改成功"));
	}
}
