package com.cofc.controller.aida;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cofc.pojo.UserCommon;
import com.cofc.pojo.aida.CardPraise;
import com.cofc.pojo.aida.Position;
import com.cofc.pojo.aida.SalesCustomer;
import com.cofc.pojo.aida.SalesPerson;
import com.cofc.pojo.aida.UserCard;
import com.cofc.pojo.aida.UserImpress;
import com.cofc.pojo.aida.UserRecommendGood;
import com.cofc.pojo.aida.WebSite;
import com.cofc.service.UserCommonService;
import com.cofc.service.aida.ActionRecordService;
import com.cofc.service.aida.CardPraiseService;
import com.cofc.service.aida.PositionService;
import com.cofc.service.aida.SalesCustomerService;
import com.cofc.service.aida.SalesPersonService;
import com.cofc.service.aida.UserCardService;
import com.cofc.service.aida.UserImpressService;
import com.cofc.service.aida.UserRecommendGoodService;
import com.cofc.service.aida.WebSiteService;
import com.cofc.util.BaseUtil;
import com.cofc.util.JsonUtil;
@Controller
@RequestMapping("/aida/card")
public class WXUserCardController extends BaseUtil{
	@Resource
	private UserCardService cardService;
	@Resource
	private UserImpressService impressService;
	@Resource
	private UserRecommendGoodService recommendGoodService;//推荐商品
	@Resource
	private CardPraiseService praiseService;//记录点赞行为
	@Resource
	private UserCommonService userCommonService;//用户信息
	@Resource
	private ActionRecordService actionRecordService; //用户行为
	@Resource
	private WebSiteService webSiteService;//官网
	@Resource
	private SalesPersonService salesPersonService;
	@Resource
	SalesCustomerService salesCustomerService;
	@Resource
	private PositionService positionService; //职位信息
	public static Logger log = Logger.getLogger("WXUserCardController");
	/**
	 * 新增名片
	 * @param response
	 * @param card
	 */
	@RequestMapping("/addcard")
	public void addcard(HttpServletResponse response,UserCard card,String impresslist){
		try {
			card.setIsEffect(1);
			card.setCreateTime(new Date());
			cardService.addUserCard(card);
			Integer cardId = card.getCardId();
			UserCard cardInfo = cardService.getCardInfo(cardId);
			if(cardInfo !=null){
				//印象批量增加
				String[] impressArr = impresslist.split(",");
				List<UserImpress> imList = new ArrayList<UserImpress>();
				for (int i = 0; i < impressArr.length; i++) {
					UserImpress imp = new UserImpress();
					imp.setAppId(card.getAppId());
					imp.setIsEffect(1);
					imp.setNumber(0);
					imp.setTagName(impressArr[i]);
					imp.setUserId(cardInfo.getUserId());
					imList.add(imp);
				}
				impressService.addAllImpress(imList);
				output(response,JsonUtil.buildFalseJson("0","新增成功"));
			}else{
				output(response,JsonUtil.buildFalseJson("1","新增名片失败"));
			}
		} catch (Exception e) {
			log.error("新增名片失败,失败原因："+e.getMessage());
			output(response,JsonUtil.buildFalseJson("1","新增名片失败"));
		}
	}
	/**
	 * 编辑名片
	 * @param response
	 * @param card
	 */
	@RequestMapping("/updatecard")
	public void updatecard(HttpServletResponse response,UserCard card,String imageUrl,String impresslist){
		if(card.getUserId() == null){
			log.error("该用户不存在");
			output(response,JsonUtil.buildFalseJson("1","该用户不存在"));
		}else{
			UserCard  userCard = cardService.getUserCard(card.getUserId(),card.getAppId());
			if(userCard == null){ //新增
				card.setIsEffect(1);
				card.setCreateTime(new Date());
				cardService.addUserCard(card);
				output(response,JsonUtil.buildFalseJson("0","新增成功"));
			}else{ //修改
				card.setCardId(userCard.getCardId());
				cardService.updateUserCard(card);
				//修改用户表的昵称
				try {
					UserCommon user = new UserCommon();
					user.setUserId(userCard.getUserId());
					if(card.getRealName()!=null && !card.getRealName().equals("")){
						user.setNickName(card.getRealName());
					}
					if(card.getHeadImage()!=null && !card.getHeadImage().equals("")){
						user.setHeadImage(card.getHeadImage());
					}
					if(card.getHeadImage()!=null && !card.getHeadImage().equals("")){
						user.setHeadImage(card.getHeadImage());
					}
					if(card.getPosition()!=null && !card.getPosition().equals("")){
						user.setUserPosition(card.getPosition());
					}
					userCommonService.commonInfoUpdate(user);
				} catch (Exception e) {
					log.error("修改用户失败,失败原因："+e.getMessage());
				}
				output(response,JsonUtil.buildFalseJson("0","修改成功"));
			}
		}
	}
	
	/**
	 * 获取名片详情
	 * @param response
	 * @param userId
	 */
	@RequestMapping("/getcardinfo")
	public void getcardinfo(HttpServletResponse response,Integer userId,Integer appId){
		if(userId == null){
			log.error("该用户不存在");
			output(response,JsonUtil.buildFalseJson("1","该用户不存在"));
		}else{
			UserCard card = cardService.getUserCard(userId,appId);
		    SalesPerson sale = salesPersonService.getSalesPersonByUserId(userId);
            if(sale != null){
            	card.setSalerMiniUserId(sale.getRelatedUserId());
            }
		    List<UserCard> lists = new ArrayList<UserCard>();
		    if(card != null){
		    	int count = praiseService.getPraiseCount(card.getCardId());
		    	card.setPraiseCount(count);
		    	UserImpress imp = new UserImpress();//印象集合
		    	imp.setAppId(appId);
		    	imp.setIsEffect(1);
		    	imp.setUserId(userId);
		    	List<UserImpress> imList = impressService.getImpressList(imp);
		    	card.setImpressList(imList);
		    	lists.add(card);
		    }
		    
		    output(response,JsonUtil.buildJson(lists));
		}
	}
	/**
	 * 获取印象
	 * @param response
	 * @param impress
	 */
	@RequestMapping("/getimpresslist")
	public void getImpressList(HttpServletResponse response,UserImpress impress){
		List<UserImpress> lists = impressService.getImpressList(impress);
		output(response,JsonUtil.buildJson(lists));
	}
	/**
	 * 删除印象标签
	 * @param response
	 * @param tagId
	 */
	@RequestMapping("/delImpress")
	public void delImpress(HttpServletResponse response,Integer tagId,Integer userId){
		if(tagId == null){
			output(response,JsonUtil.buildFalseJson("1","提交参数非法"));
		}else{
			if(userId == null){
				output(response,JsonUtil.buildFalseJson("1","系统无法识别该用户"));
			}else{
				UserImpress impress = impressService.getImpressById(tagId);
				if(impress.getUserId() == null){
					output(response,JsonUtil.buildFalseJson("1","你无删除该条标签的权限"));
				}else{
					impressService.delImpress(tagId);
					output(response,JsonUtil.buildFalseJson("0","删除印象标签成功"));
				}
			}
		}
	}
	/**
	 * 新增印象标签
	 * @param response
	 * @param impress
	 */
	@RequestMapping("/addImpress")
	public void addImpress(HttpServletResponse response,UserImpress impress){
		if(impress.getUserId() == null){
			output(response,JsonUtil.buildFalseJson("1","系统无法识别该用户"));
		}else{
			if(impress.getTagName() == null || impress.getTagName().equals("")){
				output(response,JsonUtil.buildFalseJson("1","标签不能为空"));
			}else{
				impress.setIsEffect(1);
				impress.setNumber(0);
				impressService.addImpress(impress);
				output(response,JsonUtil.buildFalseJson("0","添加标签成功"));
			}
		}
	}
	/**
	 * 获取推荐商品
	 * @param response
	 * @param re
	 * @param page
	 * @param limit
	 */
	@RequestMapping("/getRecommendGood")
	public void getRecommendGood(HttpServletResponse response,UserRecommendGood re,Integer page,Integer limit){
		List<UserRecommendGood> lists = recommendGoodService.getRecommendList(re, (page-1)*limit, limit);
		output(response,JsonUtil.buildJson(lists));
	}
	
	/**
	 * 增加标签次数
	 */
	@RequestMapping("/updateImpressNumber")
	public void updateImpressNumber(HttpServletResponse response,UserImpress impress){
		impressService.updateImpress(impress);
		output(response,JsonUtil.buildFalseJson("0","操作成功"));
	}
	/**
	 * 用户点赞名片行为
	 * @param response
	 * @param userId
	 * @param cardId
	 */
	@RequestMapping("/updatePraise")
	public void  updatePraise(HttpServletResponse response,Integer userId,Integer cardId,Integer isPraise){
		if(userId == null){
			output(response,JsonUtil.buildFalseJson("1","参数有误"));
		}else{
			if(cardId == null){
				output(response,JsonUtil.buildFalseJson("1","参数有误"));
			}else{
				CardPraise praise = praiseService.getInfoByuserId(userId, cardId);
				CardPraise p = new CardPraise();
				if(praise == null){
					 UserCommon user = userCommonService.getUserByUserId(userId);
					 if(user == null){
						 output(response,JsonUtil.buildFalseJson("1","用户不存在"));
					 }else{
						 p.setCardId(cardId);
						 p.setCreateTime(new Date());
						 p.setHeadImage(user.getHeadImage());
						 p.setIsPraise(isPraise);
						 p.setUserId(userId);
						 praiseService.addPraise(p);
						 output(response,JsonUtil.buildFalseJson("0", "操作成功"));
					 }
				}else{
					//更新点赞状态
					p.setId(praise.getId());
					p.setIsPraise(isPraise);
					praiseService.updatePraise(p);
					output(response,JsonUtil.buildFalseJson("0","操作成功"));
				}
			}
		}
	}
	/**
	 * 检测点赞接口
	 * @param response
	 * @param userId
	 * @param cardId
	 */
	@RequestMapping("/checkIsPraise")
	public void checkIsPraise(HttpServletResponse response,Integer userId,Integer cardId){
		CardPraise praise = praiseService.getInfoByuserId(userId, cardId);
		if(praise == null){
			output(response,JsonUtil.buildFalseJson("1","未点赞"));
		}else{
			if(praise.getIsPraise() == 1){
				output(response,JsonUtil.buildFalseJson("0","已点赞"));
			}else{
				output(response,JsonUtil.buildFalseJson("1","未点赞"));
			}
		}
	}
	/**
	 * 获取点赞列表
	 * @param response
	 * @param praise
	 * @param page
	 * @param limit
	 */
	@RequestMapping("/getPraiseList")
	public void getPraiseList(HttpServletResponse response,CardPraise praise,Integer page,Integer limit,
			Integer appId,Integer salesPersonId,Integer type,Integer objectType){
		int count = actionRecordService.getVisitedCount(appId, salesPersonId, type, objectType);
		List<CardPraise> lists = praiseService.getPraiseList(praise, (page-1)*limit, limit);
		//output(response,JsonUtil.buildJson(lists));
		output(response,JsonUtil.buildJsonByTotalCount(lists, count));
	}
	/**
	 * 获取官网信息
	 * @param response
	 * @param appId
	 */
	@RequestMapping("/getWebSite")
	public void getWebSite(HttpServletResponse response,Integer appId){
		List<WebSite> lists = new ArrayList<WebSite>();
		WebSite list = webSiteService.getWebSiteInfo(null, appId);
		if(list != null){
			Position pos = new Position();
			pos.setAppId(appId);
			List<Position> positions = positionService.getPositionList(pos, null, null);
			list.setPositionlist(positions);
			lists.add(list);	
		}
		output(response, JsonUtil.buildJson(lists));
	}
	@RequestMapping("/getPositionInfo")
	public void getPositionInfo(HttpServletResponse response,Integer positionId){
		Position position = positionService.getPositionInfo(positionId);
		List<Position> lists = new ArrayList<Position>();
		if(position != null){
			lists.add(position);
		}
		output(response,JsonUtil.buildJson(lists));
	}
	/**
	 * 获取客户头像和浏览量
	 * @param response
	 * @param appId
	 */
	@RequestMapping("/getVistitedCustomer")
	public void getVistitedCustomer(HttpServletResponse response,Integer appId,Integer salesPersonId){
		List<SalesCustomer> list= salesCustomerService.getCustomerList(appId, salesPersonId, 1, 10);
		int visitedCount= actionRecordService.getSalesPersonVisitedCount(appId, salesPersonId);
		output(response, JsonUtil.buildJsonByTotalCount(list, visitedCount));
	}
}
