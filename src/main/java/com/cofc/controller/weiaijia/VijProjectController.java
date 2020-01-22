package com.cofc.controller.weiaijia;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cofc.pojo.vij.Project;
import com.cofc.pojo.vij.ProjectCheck;
import com.cofc.pojo.vij.ProjectCheckItem;
import com.cofc.pojo.vij.ProjectCheckView;
import com.cofc.pojo.vij.ProjectComment;
import com.cofc.pojo.vij.ProjectCommentTag;
import com.cofc.pojo.vij.ProjectConstructTime;
import com.cofc.pojo.vij.ProjectDesign;
import com.cofc.pojo.vij.ProjectLog;
import com.cofc.pojo.vij.ProjectOffer;
import com.cofc.pojo.vij.ProjectOfferDetail;
import com.cofc.pojo.vij.ProjectOrder;
import com.cofc.pojo.vij.ProjectView;
import com.cofc.pojo.vij.ProjectWorkBigPlan;
import com.cofc.pojo.vij.ProjectWorkPlan;
import com.cofc.pojo.vij.ProjectWorkProcess;
import com.cofc.pojo.vij.StyleSubject;
import com.cofc.pojo.vij.StyleSubjectAnswer;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.cofc.pojo.vij.Adviser;
import com.cofc.pojo.vij.ChooseStyle;
import com.cofc.pojo.vij.DecorateForecast;
import com.cofc.pojo.vij.ModelRoom;
import com.cofc.service.vij.ProjectService;
import com.cofc.service.vij.ProjectViewService;
import com.cofc.service.vij.ProjectWorkBigPlanService;
import com.cofc.service.vij.ProjectWorkPlanService;
import com.cofc.service.vij.ProjectWorkPlanTypeService;
import com.cofc.service.vij.ProjectWorkProcessService;
import com.cofc.service.vij.StyleSubjectAnswerService;
import com.cofc.service.vij.StyleSubjectService;
import com.cofc.service.vij.AdviserService;
import com.cofc.service.vij.ChooseStyleService;
import com.cofc.service.vij.DecorateForecastService;
import com.cofc.service.vij.ModelRoomService;
import com.cofc.service.vij.ProjectCheckItemService;
import com.cofc.service.vij.ProjectCheckService;
import com.cofc.service.vij.ProjectCheckViewService;
import com.cofc.service.vij.ProjectCommentService;
import com.cofc.service.vij.ProjectCommentTagService;
import com.cofc.service.vij.ProjectConstructTimeService;
import com.cofc.service.vij.ProjectDesignService;
import com.cofc.service.vij.ProjectLogService;
import com.cofc.service.vij.ProjectOfferDetailService;
import com.cofc.service.vij.ProjectOfferService;
import com.cofc.service.vij.ProjectOrderService;
import com.cofc.util.BaseUtil;
import com.cofc.util.JsonUtil;
import com.cofc.util.MD5Util;
import com.cofc.util.SendMsgUtil;
import com.cofc.util.alipay.AliPayConfig;
import com.cofc.util.wxpay.WXPreOrderUtil;
import com.cofc.util.wxpay.WeiXinPayConfig;

/**
 * pc施工圖縂方法
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/vij/project")
public class VijProjectController extends BaseUtil{

	@Resource
	private ProjectService projectService;//项目
	@Resource
	private AdviserService adviserSerive;//顾问/设计师
	@Resource
	private DecorateForecastService roomService;//量房
	@Resource
	private ChooseStyleService styleService;//风格
	@Resource
	private ProjectDesignService pDesignService;//设计稿
	@Resource
	private ProjectOfferService pOfferService;//报价
	@Resource
	private ProjectViewService viewService;//调整内容
	@Resource
	private ProjectOfferDetailService offerDetailService;
	@Resource
	private ProjectOrderService pOrderService;//定金记录
	@Resource
	private ProjectWorkProcessService processService;//施工流程
	@Resource
	private ProjectLogService projectLogService;//项目日志
	@Resource
	private ProjectWorkPlanTypeService workPlanTypeService;//计划分类
	@Resource
	private ProjectWorkBigPlanService bigPlanService;//施工大计划;
	@Resource
	private ProjectWorkPlanService planService;//施工计划
	@Resource
	private ProjectCheckService checkService; //大项验收
	@Resource
	private ProjectCheckItemService checkItemService;//小项验收
	@Resource
	private ProjectCommentTagService tagService;//评论标签
	@Resource
	private ProjectCommentService commentService;//评论
	@Resource
	private ProjectConstructTimeService constructTimeService;//施工开始-结束时间
	@Resource
	private ProjectCheckViewService projectCheckViewService;//验收意见
	@Resource
	private ModelRoomService modelRoomService;//样板间
	@Resource
	private StyleSubjectAnswerService answerService;//新的选择风格
	@Resource
	private StyleSubjectService subjectService;//题目
	public static Logger log = Logger.getLogger("VijProjectController");
	
	/**
	 * 返回用户的项目数据
	 * @param response
	 * @param userId
	 */
	@RequestMapping("/getProjectByUserId")
	public void getProjectByUserId(HttpServletResponse response,Integer userId,Integer loginPlat){
		List<Project> lists = new ArrayList<Project>();
		if(userId == null){
			log.error("getProjectByUserId():userId  is null");
			output(response,JsonUtil.buildFalseJson("1", "请先登录"));
		}else{
			Project project = projectService.getProjectByUserId(userId, loginPlat,10); //10 表示已结束
			if(project == null){
				output(response,JsonUtil.buildFalseJson("1", "暂无进行中的项目"));
			}else{
				if(project.getWaiterId() != null){
					Adviser adviser = adviserSerive.getInfoById(project.getWaiterId());
					project.setAdviser(adviser);
				}
				if(project.getDesignerId() != null){
					Adviser designer = adviserSerive.getInfoById(project.getDesignerId());
					project.setDesigner(designer);
				}
				if (project.getDepositMoney() != null) {
					project.setDepositMoney(project.getDepositMoney());
				}
				DecorateForecast homeInfo = roomService.getRoomByProjectId(project.getId());
				project.setHomeInfo(homeInfo);
				lists.add(project);
				output(response,JsonUtil.buildJson(lists));
			}
		}
	}
	/**
	 * 获取顾问列表
	 * @param response
	 * @param adviser
	 * @param page
	 * @param limit
	 */
	@RequestMapping("/getAdviserList")
	public void getAdviserList(HttpServletResponse response,Adviser adviser,Integer page,Integer limit){
		if(page == null){
			page = 1;
		}
		if(limit == null){
			limit = 10;
		}
		List<Adviser> lists = adviserSerive.getAdviserList(adviser, (page-1)*limit, limit);
		output(response,JsonUtil.buildJson(lists));
	}
	/**
	 * 获取选择顾问信息
	 * @param response
	 * @param projectId
	 */
	@RequestMapping("/getAdviserById")
	public void getAdviserById(HttpServletResponse response,Integer projectId){
		if(projectId == null){
			log.error("getAdviserById():projectId  is null");
			output(response,JsonUtil.buildFalseJson("1", "非法项目"));
		}else{
			Project project = projectService.getInfoById(projectId);
			if(project == null){
				log.error("getAdviserById():project  is null");
				output(response,JsonUtil.buildFalseJson("1", "项目不存在"));
			}else{
				if(project.getWaiterId() == null){
					log.error("getAdviserById():project.getWaiterId()  is null");
					output(response,JsonUtil.buildFalseJson("1", "该项目暂无分配顾问"));
				}else{
					List<Adviser> lists = new ArrayList<Adviser>();
					Adviser adviser = adviserSerive.getInfoById(project.getWaiterId());
					if(adviser != null){
						lists.add(adviser);
					}
					output(response,JsonUtil.buildJson(lists));
				}
			}	
		}
	}
	/**
	 * 预约量房,同时生成项目 ：   先生成项目,后插入数据
	 * @param response
	 * @param room
	 * @param userId
	 */
	@RequestMapping("/ruleRoom")
	public void  ruleRoom(HttpServletResponse response,DecorateForecast room,Integer userId,Integer loginPlat){
		if(userId == null){
			log.error("ruleRoom():userId  is null");
			output(response,JsonUtil.buildFalseJson("1", "请先登录"));
		}else{
			if(room.getMobilePhoneNo() == null || room.getMobilePhoneNo().equals("")){
				log.error("ruleRoom():room.getMobilePhoneNo()  is null");
				output(response,JsonUtil.buildFalseJson("1", "请输入你的手机号码"));
			}else{
				//插入项目表
				Project project = new Project();
				SimpleDateFormat sDateFormat;
				sDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
				Date date = new Date();
				String str = sDateFormat.format(date);
				Random random = new Random();
				int rannum = (int) ((random.nextDouble() * (999999 - 100000 + 1)) + 100000); // 获取5为随机数
				String projectNo = str + rannum; // s生成19位随机数
				project.setProjectNo(projectNo);
				project.setOwnerId(userId);
				project.setCreateUserId(userId);
				project.setIsPay(0);
				project.setCreateTime(new Date());
				if(room.getLfUserName() == null || room.getLfUserName().equals("")){
					project.setName("用户-"+room.getMobilePhoneNo()+"项目");
				}else{
					project.setName("用户-"+room.getLfUserName()+"项目");
				}
				project.setAppId(loginPlat);
				project.setStatus(3);//默认一提交量房数据就到选择风格
				project.setDepositMoney(0.01); //默认定金
				projectService.addProject(project);
				Integer projectId = project.getId();//获取项目id
				room.setProjectId(projectId);
				room.setSource(2);//来源app
				room.setIsDeal(0);
				room.setDecorateTime(new Date());//创建时间
				roomService.addVijbudget(room);
				output(response,JsonUtil.buildFalseJson("0", "提交成功"));
			}
		}
	}
	/**
	 * 获取量房信息
	 * @param response
	 * @param projectId
	 */
	@RequestMapping("/getRoomById")
	public void getRoomById(HttpServletResponse response,Integer projectId){
		if(projectId == null){
			log.error("getRoomById():projectId  is null");
			output(response, JsonUtil.buildFalseJson("1", "非法项目"));
		}else{
			List<DecorateForecast> lists = new ArrayList<DecorateForecast>();
			DecorateForecast data = roomService.getRoomByProjectId(projectId);
			if(data != null){
				lists.add(data);
			}
			output(response,JsonUtil.buildJson(lists));
		}
	}
	/**
	 * 选择风格 后 把项目置为 预交定金状态 ： 已废弃
	 * @param response
	 * @param style
	 */
	@RequestMapping("/chooseStyle")
	public void chooseStyle(HttpServletResponse response,ChooseStyle style){
		if(style.getUserId() == null){
			log.error("chooseStyle():style.getUserId()  is null");
			output(response,JsonUtil.buildFalseJson("1","请先登录"));
		}else{
			if(style.getProjectId() == null){
				log.error("chooseStyle():style.getProjectId()  is null");
				output(response,JsonUtil.buildFalseJson("1", "参数非法"));
			}else{
				Project info = projectService.getInfoById(style.getProjectId());
				style.setIsDeal(0);
				style.setCreateTime(new Date());
				styleService.addStyle(style);
				if(style.getStyleId() != null){
					//项目置为预交定金   ------------- old
					Project p = new Project();
					p.setId(style.getProjectId());
					p.setStatus(3);//选择风格，等后台确定
					projectService.updateProject(p);
					if(info.getWaiterId() != null){
							Adviser adviser = adviserSerive.getInfoById(info.getWaiterId());
							if (adviser !=null) { //当有分配顾问的时候发给顾问,没有顾问的时候省去发送逻辑
								if (adviser.getRealName() !=null && !adviser.getRealName().equals("") && adviser.getPhone() !=null && !adviser.getPhone().equals("")) {
										SendMsgUtil.sendMsg(adviser.getPhone(),"亲爱的"+adviser.getRealName()+",房主已经选择了房屋风格，请确认！");
								}
							}
					}					
					//后台确认风格后把状态置为预交定金状态------- new
					output(response,JsonUtil.buildFalseJson("0", "提交风格成功"));
				}else{
					log.error("chooseStyle():style.getStyleId() is null");
					output(response,JsonUtil.buildFalseJson("1", "提交风格失败,请联系你的顾问"));
				}
			}
		}
	}
	/**
	 * 获取客户风格信息  ： 已废弃
	 * @param response
	 * @param projectId
	 */
	@RequestMapping("/getChooseById")
	public void getChooseById(HttpServletResponse response,Integer projectId){
		if(projectId == null){
			log.error("getChooseById():projectId is null");
			output(response,JsonUtil.buildFalseJson("1", "参数非法"));
		}else{
			List<ChooseStyle> lists = new ArrayList<ChooseStyle>();
			ChooseStyle style = styleService.getStyleById(projectId);
			if(style != null){
				lists.add(style);
			}
			output(response,JsonUtil.buildJson(lists));
		}
	}
	/**
	 * 交定金 ：
	 * @param response
	 * @param projectId
	 * @param  payType : 1 微信  2支付宝
	 * @throws AlipayApiException 
	 */
	@RequestMapping("/depositMoney")
	public void depositMoney(HttpServletResponse response,HttpServletRequest request,ProjectOrder order) throws AlipayApiException{
		if(order.getProjectId() == null){
			output(response,JsonUtil.buildFalseJson("1", "未知项目"));
		}else{
			if(order.getUserId() == null){
				output(response,JsonUtil.buildFalseJson("1", "未知用户"));
			}else{
				Project info = projectService.getInfoById(order.getProjectId());
				if(info == null){
					output(response,JsonUtil.buildFalseJson("1", "项目不存在"));
				}else{
					if(info.getIsPay() == 1){
						output(response,JsonUtil.buildFalseJson("1", "已支付定金"));
					}else{
						order.setCreateTime(new Date());
						order.setProjectOrderNo(getOrderIdByUUId());
						pOrderService.addProjectOrder(order);
						if(order.getPorderId() == null){
							output(response,JsonUtil.buildFalseJson("1", "交定金失败"));
						}else{
							ProjectOrder norder = pOrderService.getOrderByid(order.getPorderId());
							if(order.getPayType() == 2){ //支付宝
								AliPayConfig alipay = new AliPayConfig();
								AlipayClient alipayClient = new DefaultAlipayClient(alipay.getReturnUrl(), 
										alipay.getAppId(),alipay.getPrivateKey(), alipay.getFormat(), 
										alipay.getCharset(), alipay.getAlipayPublicKey(), alipay.getSignType());
								AlipayTradeAppPayRequest httprequest = new AlipayTradeAppPayRequest();
								
								AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
								model.setBody("项目支付定金");//对一笔交易的具体描述信息。如果是多种商品，请将商品描述字符串累加传给body。
								model.setTotalAmount(norder.getRealMoney().toString());
								model.setOutTradeNo(norder.getProjectOrderNo());
								model.setTimeoutExpress("24h");//该笔订单允许的最晚付款时间，逾期将关闭交易。取值范围：1m～15d。m-分钟，h-小时，d-天，1c-当天（1c-当天的情况下，无论交易何时创建，都在0点关闭）。 该参数数值不接受小数点， 如 1.5h，可转换为 90m。
								model.setSubject("项目支付定金");
								model.setGoodsType("1");
								httprequest.setBizModel(model);
								String port = request.getServerPort() == 80 ? "" : ":" + request.getServerPort();
								String weburl = request.getScheme() + "://" + request.getServerName() + port ;

								httprequest.setNotifyUrl(weburl+"/aliPay/aliPayNotify/payProjectNotify.do");
								AlipayTradeAppPayResponse response2 = alipayClient.sdkExecute(httprequest);
								System.out.println("response2--"+response2.getBody());
								if(response2.isSuccess()){
									output(response,JsonUtil.buildFalseJson("0", response2.getBody()));
								}else{
									log.info("支付宝支付失败,失败原因："+response2);
									output(response,JsonUtil.buildFalseJson("1", "支付宝支付失败"));
								}
							}else{ //微信支付
									//执行下单操作
									WeiXinPayConfig config = new WeiXinPayConfig();
									//唯爱家开放平台 ：old
									//config.setAppid("wx203885df636adbbf");
									//config.setMch_id("1521955461");
									//config.setApiKey("ZBmOT9EXdTieYlzq5hUDtbdFms39TYUi");
									
									//三端统一支付
									config.setAppid("wx203885df636adbbf"); //唯爱家开发平台
									config.setMch_id("1518014341");//唯爱家App
									config.setApiKey("ZbI5FW3x2BD5Nr9cb4PrTbnR4E9OLqWg");//唯爱家支付密钥
		
							        String trade_type = "APP";	//PC扫码
							        config.setTrade_type(trade_type);  
									String contextPath = request.getContextPath();// 项目名
									String port = request.getServerPort() == 80 ? "" : ":" + request.getServerPort();
									String weburl = request.getScheme() + "://" + request.getServerName() + port + contextPath;
									config.setNotify_url(weburl+"/wx/weixinPayNotify/payProjectDepositNotify.do");
									Double fee = norder.getRealMoney();
									String ip = request.getRemoteAddr();
									Map<String, String> map = WXPreOrderUtil.getPCPrepayInfo(norder.getProjectOrderNo(), fee, ip,"唯爱家商城-支付项目定金", config);
									String returnCode = map.get("return_code");
									String returnMsg = map.get("return_msg");
									if(returnCode.equals("SUCCESS") && returnMsg.equals("OK")){
										long timestamp = (new Date().getTime()/1000);
										map.put("timestamp",Long.toString(timestamp));
										map.put("orderId", norder.getProjectOrderNo());
										log.info("depositMoney():支付回掉完成返回的结果--"+map);
										//用预支付订单id重新加密
										String nonceStr = map.get("nonce_str");
										String prepay_id = map.get("prepay_id");
										String newSign = "appid=wx203885df636adbbf&noncestr="+nonceStr+"&package=Sign=WXPay&partnerid=1518014341&prepayid=" + prepay_id+ "&timestamp=" + Long.toString(timestamp)+"&key=ZbI5FW3x2BD5Nr9cb4PrTbnR4E9OLqWg";
										String paysign = MD5Util.MD5Encode(newSign,"utf-8").toUpperCase();
								        map.put("paySign",paysign);
								        if(info.getWaiterId() != null){
											Adviser adviser = adviserSerive.getInfoById(info.getWaiterId());
											if (adviser !=null) { //当有分配顾问的时候发给顾问,没有顾问的时候省去发送逻辑
												if (adviser.getRealName() !=null && !adviser.getRealName().equals("") && adviser.getPhone() !=null && !adviser.getPhone().equals("")) {
														SendMsgUtil.sendMsg(adviser.getPhone(),"亲爱的"+adviser.getRealName()+",房主已经支付了定金，请确认！");
												}
											}
										}
										output(response,JsonUtil.MapToJsonString(map));
									}else{
										log.info("depositMoney():微信支付失败--"+map);
										output(response,JsonUtil.buildFalseJson("1", "微信支付失败"));
									}
							}
							
						}
					}
				}
			}
		}
	}
	/**
	 * 获取设计师
	 * @param response
	 * @param projectId
	 */
	@RequestMapping("/getDesignerById")
	public void getDesignerById(HttpServletResponse response,Integer projectId){
		if (projectId == null) {
			log.error("getDesignerById():projectId is null");
			output(response, JsonUtil.buildFalseJson("1", "参数非法"));
		}else {
			List<Adviser> list = new ArrayList<Adviser>();
			Project project = projectService.getInfoById(projectId);
			if(project == null){
				log.error("getDesignerById():project is null");
				output(response, JsonUtil.buildFalseJson("1", "项目非法"));
			}else{
				if(project.getDesignerId() != null){
					Adviser adviser = adviserSerive.getInfoById(project.getDesignerId());
					if (adviser !=null) {
						list.add(adviser);
						output(response, JsonUtil.buildJson(list));
					}
				}else{
					log.error("getDesignerById():project.getDesignerId() is null");
					output(response,JsonUtil.buildFalseJson("1", "该项目暂无分配设计师"));
				}
			}
			
 		}
	}
	/**
	 * 获取设计稿
	 * @param response
	 * @param projectId
	 */
	@RequestMapping("/getDesignList")
	public void getDesignList(HttpServletResponse response,Integer projectId){
		if(projectId == null){
			log.error("getDesignList():projectId is null");
			output(response,JsonUtil.buildFalseJson("1", "参数非法"));
		}else{
			List<ProjectDesign> designs = pDesignService.getDesignByProjectId(projectId);
			output(response,JsonUtil.buildJson(designs));
		}
	}
	/**
	 * 获取设计稿详情
	 */
	@RequestMapping("/getDesignById")
	public void getDesignById(HttpServletResponse response,Integer designId){
		if(designId == null){
			output(response,JsonUtil.buildFalseJson("1", "非法参数"));
		}else{
			List<ProjectDesign> lists = new ArrayList<ProjectDesign>();
			ProjectDesign list = pDesignService.getOfferDesignById(designId);
			if(list != null){
				lists.add(list);
			}
			output(response,JsonUtil.buildJson(lists));
		}
	}
	/**
	 * 调整设计或报价
	 * @param response
	 * @param view
	 */
	@RequestMapping("/sendProjectView")
	public void sendProjectView(HttpServletResponse response,ProjectView view){
		if(view.getProjectId() == null){
			log.error("sendProjectView():view.getProjectId() is null");
			output(response,JsonUtil.buildFalseJson("1", "传递参数非法"));
		}else{
			if(view.getContent() == null || view.getContent().equals("")){
				log.error("sendProjectView():view.getContent() is null");
				output(response,JsonUtil.buildFalseJson("1", "传递内容不能为空"));
			}else{
				view.setCreateTime(new Date());
				view.setIsDeal(0);
				viewService.addProjecView(view);
				Project info = projectService.getInfoById(view.getProjectId());
				if(info.getWaiterId() != null){
						Adviser adviser = adviserSerive.getInfoById(info.getWaiterId());
						if (adviser !=null) { //当有分配顾问的时候发给顾问,没有顾问的时候省去发送逻辑
							if (adviser.getRealName() !=null && !adviser.getRealName().equals("") && adviser.getPhone() !=null && !adviser.getPhone().equals("")) {
								String msString = "";
								if(view.getViewType() == 1){
									msString = "设计";
								}else{
									msString = "报价";
								}
								SendMsgUtil.sendMsg(adviser.getPhone(),"亲爱的"+adviser.getRealName()+",房主已经调整了"+msString+"，请确认！");
							}
						}
				}
				output(response,JsonUtil.buildFalseJson("0", "发送成功"));
			}
		}
	}
	/**
	 * 确认设计  / 确认报价
	 * @param response
	 * @param projectId
	 */
	@RequestMapping("/confirmProject")
	public void confirmDesign(HttpServletResponse response,Project project){
		if (project.getId() == null) {
			log.error("confirmDesign():project.getId() is null");
			output(response, JsonUtil.buildFalseJson("1", "非法参数"));
		}else {
			project.setLastTime(new Date());
			projectService.updateProject(project);
			if(project.getWaiterId() != null){
				Adviser adviser = adviserSerive.getInfoById(project.getWaiterId());
				if (adviser !=null) { //当有分配顾问的时候发给顾问,没有顾问的时候省去发送逻辑
					if (adviser.getRealName() !=null && !adviser.getRealName().equals("") && adviser.getPhone() !=null && !adviser.getPhone().equals("")) {
							SendMsgUtil.sendMsg(adviser.getPhone(),"亲爱的"+adviser.getRealName()+"房主已经上传了设计稿，请注意查看！");
					}
				}
			}
			output(response,JsonUtil.buildFalseJson("0", "操作成功"));
		}
	}
	
	/**
	 * 获取报价 --外层 ： 软装/硬装
	 * @param response
	 * @param projectId
	 */
	@RequestMapping("/getOfferList")
	public void getOfferList(HttpServletResponse response,Integer projectId){
		if (projectId == null) {
			log.error("getOfferList():projectId is null");
			output(response, JsonUtil.buildFalseJson("1", "非法参数"));
		}else {
			List<ProjectOffer> list = pOfferService.getOfferByProjectId(projectId);
			output(response, JsonUtil.buildJson(list));
		}
	}
	/**
	 * 获取软装 / 硬装 点击里面的详情
	 * @param response
	 * @param pofferId
	 */
	@RequestMapping("/getOfferDetail")
	public void getOfferDetail(HttpServletResponse response,Integer pofferId){
		if(pofferId == null){
			log.error("getOfferDetail():pofferId is null");
			output(response, JsonUtil.buildFalseJson("1", "非法参数"));
		}else{
			List<ProjectOfferDetail> lists = new ArrayList<ProjectOfferDetail>();
			ProjectOfferDetail list = offerDetailService.getOfferDetailById(pofferId);
			if(list != null){
				lists.add(list);
			}
			output(response, JsonUtil.buildJson(lists));
		}
	}
	
	/**
	 * 施工流程 / 工艺标准
	 * @param response
	 * @param process
	 */
	@RequestMapping("/getProjectWork")
	public void getProjectWork(HttpServletResponse response,ProjectWorkProcess process){
		List<ProjectWorkProcess> lists = processService.getWorkProcessList(process);
		output(response,JsonUtil.buildJson(lists));
	}
	/**
	 * 获取施工计划
	 * @param response
	 * @param projectId
	 */
	@RequestMapping("/getProjectWorkPlan")
	public void getProjectWorkPlan(HttpServletResponse response,ProjectWorkBigPlan bigplan,Integer page,Integer limit){
		if (bigplan.getProjectId() == null) {
			log.error("getProjectWorkPlan():bigplan.getProjectId() is null");
			output(response, JsonUtil.buildFalseJson("1", "非法参数"));
		}else {
			  //old
			 //List<ProjectWorkPlan> list = planService.getWorkPlanList(pWorkPla, null, null);
			 //output(response, JsonUtil.buildJson(list));
			List<ProjectWorkBigPlan> bigPlans = bigPlanService.getBigPlanList(bigplan, null, null);
			for (ProjectWorkBigPlan p : bigPlans) {
				ProjectWorkPlan plan = new ProjectWorkPlan();
				plan.setPlanTypeId(p.getId());
				List<ProjectWorkPlan> list = planService.getWorkPlanList(plan, null, null);
				p.setPlanList(list);
			}
			output(response, JsonUtil.buildJson(bigPlans));  
		}
	}
	/**
	 * 获取项目的开始施工 时间和结束时间
	 * @param response
	 * @param projectId
	 */
	@RequestMapping("/getProjectConstructTime")
	public void getProjectConstructTime(HttpServletResponse response,ProjectWorkBigPlan bigplan){
		if(bigplan.getProjectId() == null){
			log.error("getProjectConstructTime():bigplan.getProjectId() is null");
			output(response, JsonUtil.buildFalseJson("1", "非法参数"));
		}else{
			List<ProjectWorkBigPlan> bigPlans = bigPlanService.getBigPlanList(bigplan, null, null);
			if(bigPlans.size() > 0){
				List<BigInteger> startRealTimeArr = new ArrayList<BigInteger>();
				List<BigInteger> endRealTimeArr = new ArrayList<BigInteger>();
				for (ProjectWorkBigPlan big : bigPlans) {
					if(big.getBigRealStartTime() != null){
						startRealTimeArr.add(big.getBigRealStartTime());
					}
					if(big.getBigRealEndTime() != null){
						endRealTimeArr.add(big.getBigRealEndTime());
					}
				}
				Map<String, Object> map = new HashMap<String, Object>();
				if(startRealTimeArr.size() > 0){
					map.put("startTime",Collections.max(startRealTimeArr));
				}else{
					map.put("startTime",0);
				}
				if(endRealTimeArr.size() > 0){
					map.put("endTime",Collections.min(endRealTimeArr));
				}else{
					map.put("endTime",0);
				}
				output(response,JsonUtil.MapToJson(map));
			}else{
				output(response,JsonUtil.buildFalseJson("1", "项目未施工"));
			}
		}
	}
	/**根据施工项目获取验收项目**/
	/**
	 * 获取验收大项
	 * @param response
	 * @param request
	 * @param bigplan
	 */
	@RequestMapping("/getcheckWorkPlan")
	public void getcheckBigWorkPlan(HttpServletResponse response,HttpServletRequest request,ProjectWorkBigPlan bigplan){
		if (bigplan.getProjectId() == null) {
			log.error("getcheckBigWorkPlan():bigplan.getProjectId() is null");
			output(response, JsonUtil.buildFalseJson("1", "非法参数"));
		}else {
			bigplan.setIsCheck(1);
			List<ProjectWorkBigPlan> bigPlans = bigPlanService.getBigPlanList(bigplan, null, null);
			output(response,JsonUtil.buildJson(bigPlans));
		}
	}
	/**
	 * 获取验收小项
	 * @param response
	 * @param plan
	 */
	@RequestMapping("/getCheckSmallWorkPlan")
	public void getCheckSmallWorkPlan(HttpServletResponse response,Integer planTypeId){
		if (planTypeId == null) {
			log.error("getCheckSmallWorkPlan():planTypeId is null");
			output(response, JsonUtil.buildFalseJson("1", "非法参数"));
		}else {
			List<ProjectWorkPlan> lists = planService.getWorkPlanListByType(planTypeId);
			output(response,JsonUtil.buildJson(lists));
		}
	}
	/**
	 * 执行验收,验收不通过需要填写意见
	 * @param response
	 * @param plan
	 */
	@RequestMapping("/doCheckSmallWorkPlan")
	public void doCheckSmallWorkPlan(HttpServletResponse response,Integer planId,ProjectCheckView view,Integer isPass){
		if(planId == null){
			log.error("doCheckSmallWorkPlan():planId is null");
			output(response, JsonUtil.buildFalseJson("1", "非法参数"));
		}else{
			ProjectWorkPlan plan = planService.getInfoById(planId);
			if(plan == null){
				log.error("doCheckSmallWorkPlan():plan is null");
				output(response, JsonUtil.buildFalseJson("1", "数据不存在"));
			}else{
				if(plan.getCheckStatus() == 2){ //待验收状态
					if(isPass != 1){//验收不通过需要插入意见
						view.setCreateTime(new Date());
						view.setIsDeal(0);
						view.setSmallPlanId(plan.getPlanId());
						projectCheckViewService.addCheckView(view);
						plan.setCheckStatus(1); //返回施工
					}else{
						plan.setCheckStatus(3);//验收通过
					}
					//更改小项状态
					
					planService.updateWorkPlan(plan);
					//检测大项
					int c=0,d=0;
					List<ProjectWorkPlan> lists = planService.getProjectWorkPlayList(plan.getPlanTypeId(),null);
					int count = lists.size();
					for (ProjectWorkPlan p : lists) {
						if(p.getCheckStatus() == 2){//待验收
							c++;
						}else if(p.getCheckStatus() == 3){//已通过
							d++;
						}	
					}
					int bigCheckStatus = 0;
					if(d == count){
						bigCheckStatus = 3;
					}else{
						if(c == count){
							bigCheckStatus = 1;
						}else{
							bigCheckStatus = 2;
						}
					}
					ProjectWorkBigPlan bigPlan = new ProjectWorkBigPlan();
					bigPlan.setCheckStatus(bigCheckStatus);
					bigPlan.setId(plan.getPlanTypeId());
					bigPlanService.updateBigPlan(bigPlan);
					output(response,JsonUtil.buildFalseJson("0", "验收成功"));
				}else{
					output(response,JsonUtil.buildFalseJson("1", "项目未处于待验收中,不能验收"));
				}
			}
			
		}
	}
	/**
	 * 获取施工日志
	 * @param response
	 * @param projectId
	 * @param page
	 * @param limit
	 */
	@RequestMapping("/getProjectLog")
	public void getProjectLog(HttpServletResponse response,ProjectLog nlog,Integer page,Integer limit){
		if(nlog.getProjectId() == null){
			log.error("getProjectLog():nlog.getProjectId() is null");
			output(response,JsonUtil.buildFalseJson("1", "参数非法"));
		}else{
			if(page == null){
				page = 1;
			}
			if(limit == null){
				limit = 10;
			}
			List<ProjectLog> lists = projectLogService.getProjectLogList(nlog, (page-1)*limit, limit);
			output(response,JsonUtil.buildJson(lists));
		}
	}
	
	
	
	/****************************************舍弃验收旧接口  start*********************************************************/
	/**
	 * 获取验收大项 : 旧接口废弃
	 * @param response
	 * @param projectId
	 */
	@RequestMapping("/getCheckList")
	public void getCheckList(HttpServletResponse response,ProjectCheck check){
		if(check.getProjectId() == null){
			log.error("getCheckList():check.getProjectId() is null");
			output(response, JsonUtil.buildFalseJson("1", "参数非法"));
		}else{
			List<ProjectCheck> lists = checkService.queryProjectCheckList(check, null, null);
			for (ProjectCheck projectCheck : lists) {
				int count = checkItemService.checkisAll(null, projectCheck.getCheckId());  //所有
				int checkcount = checkItemService.checkisAll(1, projectCheck.getCheckId());//通过
				int nocheckcount = checkItemService.checkisAll(3, projectCheck.getCheckId());//未通过
				if(nocheckcount > 0){ //验收未通过
					projectCheck.setCheckStatus(3);
				}else{
					if(count == checkcount){
						projectCheck.setCheckStatus(1);
						//把项目状态置为9
						Project project = new Project();
						project.setId(check.getProjectId());
						project.setStatus(9);
						projectService.updateProject(project);
					}else{
						if(checkcount > 0){
							projectCheck.setCheckStatus(2);
						}else{
							projectCheck.setCheckStatus(0);
						}
					}
				}
			}
			output(response,JsonUtil.buildJson(lists));
		}
	}
	
	/**
	 * 获取验收大项对应的小项 ： 旧接口废弃
	 * @param response
	 * @param checkId
	 */
	@RequestMapping("/getCheckDetail")
	public void getCheckDetail(HttpServletResponse response,ProjectCheckItem item){
		if(item.getCheckId() == null){
			log.error("getCheckDetail():item.getCheckId() is null");
			output(response,JsonUtil.buildFalseJson("1", "参数非法"));
		}else{
			List<ProjectCheckItem> lists = checkItemService.getProjectCheckItemList(item, null, null);
			output(response,JsonUtil.buildJson(lists));
		}
	}
	/**
	 * 用户确认验收小项   旧接口废弃
	 * @param response
	 * @param item
	 */
	@RequestMapping("/doCheckItem")
	public void doCheckItem(HttpServletResponse response,ProjectCheckItem item){
		if (item.getItemId() == null) {
			log.error("doCheckItem():item.getItemId() is null");
			output(response,JsonUtil.buildFalseJson("1", "参数非法"));
		}else {
			checkItemService.updateProjectCheckItem(item);
			output(response, JsonUtil.buildFalseJson("0", "操作成功"));
		}
	}
	/****************************************舍弃验收旧接口  end*********************************************************/

	
	/**
	 * 获取评论标签
	 * @param response
	 * @param tag
	 */
	@RequestMapping("/getCommentTagList")
	public void getCommentTagList(HttpServletResponse response,ProjectCommentTag tag){
		if(tag.getLoginPlat() == null){
			log.error("getCommentTagList():tag.getLoginPlat() is null");
			output(response,JsonUtil.buildFalseJson("1", "参数非法"));
		}else{
			List<ProjectCommentTag> lists = tagService.getCommentTagList(tag, null, null);
			output(response,JsonUtil.buildJson(lists));
		}
	}
	/**
	 * 用户评论
	 * @param response
	 * @param commentId
	 */
	@RequestMapping("/addComment")
	public void addComment(HttpServletResponse response,ProjectComment comment){
		if(comment.getProjectId() == null){
			log.error("addComment():comment.getProjectId() is null");
			output(response,JsonUtil.buildFalseJson("1", "参数非法"));
		}else{
			if(comment.getUserId() == null){
				log.error("addComment():comment.getUserId() is null");
				output(response,JsonUtil.buildFalseJson("1", "未知用户"));
			}else{
				if(comment.getCommentContent() == null || comment.getCommentContent().equals("")){
					log.error("addComment():comment.getCommentContent() is null");
					output(response,JsonUtil.buildFalseJson("1", "评论内容不能为空"));
				}else{
					ProjectComment com = new ProjectComment();
					com.setProjectId(comment.getProjectId());
					com.setUserId(comment.getUserId());
					int count = commentService.getCommentCount(com);
					if(count > 0){
						output(response,JsonUtil.buildFalseJson("1", "已评论"));
					}else{
						comment.setCreateTime(new Date());
						commentService.addComment(comment);
						Project project = new Project();
						project.setId(comment.getProjectId());
						project.setStatus(10);
						projectService.updateProject(project);
						output(response,JsonUtil.buildFalseJson("0", "评论成功"));
					}
					
				}
			}
		}
	}
	/***
	 * 户型图列表入口
	 * @param response
	 * @param dForecast
	 */
	@RequestMapping("/getdecorateForecastList")
	public void getdecorateForecastList(HttpServletResponse response,Integer projectId) {
		if (projectId==null) {
			log.error("getdecorateForecastList():projectId is null");
			output(response, JsonUtil.buildFalseJson("0", "参数非法"));
		}else {
			DecorateForecast decorateForecast = roomService.getRoomByProjectId(projectId);
			List<DecorateForecast> list = new ArrayList<>();

			if(decorateForecast != null){
				list.add(decorateForecast);
			}
			
			output(response, JsonUtil.buildJson(list));
		}
	}
	
	/**
	 * 我的私人顾问
	 * @param response
	 * @param userId
	 * @param loginPlat
	 */
	@RequestMapping("/getPrivateAdiser")
	public void getPrivateAdiser(HttpServletResponse response,Integer userId,Integer loginPlat){
		if(userId == null){
			log.error("getProjectByUserId():userId  is null");
			output(response,JsonUtil.buildFalseJson("1", "请先登录"));
		}else{
			Project project = projectService.getProjectByUserId(userId, loginPlat,10); //10 表示已结束
			if(project == null){
				output(response,JsonUtil.buildFalseJson("1", "暂无进行中的项目"));
			}else{
				if(project.getWaiterId() == null){
					log.error("getAdviserById():project.getWaiterId()  is null");
					output(response,JsonUtil.buildFalseJson("1", "该项目暂无分配顾问"));
				}else{
					List<Adviser> lists = new ArrayList<Adviser>();
					Adviser adviser = adviserSerive.getInfoById(project.getWaiterId());
					if(adviser != null){
						lists.add(adviser);
					}
					output(response,JsonUtil.buildJson(lists));
				}
			}
		}	
	}
	/**
	 * 获取选择风格的题目
	 * @param response
	 */
	@RequestMapping("/getStyleAnswer")
	public void getStyleAnswer(HttpServletResponse response,StyleSubject subject){
		List<StyleSubject> lists = subjectService.getStyleSubjectList(subject, null, null);
		output(response,JsonUtil.buildJson(lists));
	}
	/**
	 * 获取样板间
	 * @param response
	 * @param room
	 */
	@RequestMapping("/getModelRoomList")
	public void getModelRoomList(HttpServletResponse response,ModelRoom room){
		if(room.getTypeName() == null || room.getTypeName().equals("")){
			log.error("getModelRoomList():room.getTypeName()  is null");
			output(response,JsonUtil.buildFalseJson("1", "参数非法"));
		}else{
			room.setIsEffect(1);
			List<ModelRoom> lists = modelRoomService.getModelRoomList(room, null, null);
			output(response,JsonUtil.buildJson(lists));
		}
	}
	/**
	 * 选择风格
	 * @param response
	 * @param answer
	 */
	@RequestMapping("/addAnswer")
	public void addAnswer(HttpServletResponse response,StyleSubjectAnswer answer){
		if(answer.getProjectId() == null){
			log.error("addAnswer():answer.getProjectId()  is null");
			output(response,JsonUtil.buildFalseJson("1", "参数非法"));
		}else{
			StyleSubjectAnswer nAnswer = answerService.getInfoByProjectId(answer.getProjectId());
			if(nAnswer == null){
				answer.setCreateTime(new Date());
				answerService.addAnswer(answer);
				if(answer.getId() != null){
					//项目置为预交定金   ------------- old
					Project p = new Project();
					p.setId(answer.getProjectId());
					p.setStatus(3);//选择风格，等后台确定
					projectService.updateProject(p);
					output(response,JsonUtil.buildFalseJson("0", "操作成功"));					
				}else{
					output(response,JsonUtil.buildFalseJson("1", "操作失败"));
				}
			}else{
				log.error("addAnswer():已选择");
				output(response,JsonUtil.buildFalseJson("1", "已选择风格"));
			}
		}
	}
	/**
	 * 获取已选择的风格
	 * @param response
	 * @param projectId
	 */
	@RequestMapping("/getUserStyleChoose")
	public void getUserStyleChoose(HttpServletResponse response,Integer projectId){
		if(projectId == null){
			log.error("getUserStyleChoose():projectId  is null");
			output(response,JsonUtil.buildFalseJson("1", "参数非法"));
		}else{
			List<StyleSubjectAnswer> lists = new ArrayList<StyleSubjectAnswer>();
			StyleSubjectAnswer answers = answerService.getInfoByProjectId(projectId);
			if(answers != null){
				lists.add(answers);
			}
			output(response,JsonUtil.buildJson(lists));
		}
	}
	@RequestMapping("/test")
	public void test(HttpServletResponse response){
		List<ProjectOrder> list = new ArrayList<ProjectOrder>();
		ProjectOrder order = pOrderService.getProjectOrderByOrderNo("1000000721426763");
		list.add(order);
		output(response,JsonUtil.buildJson(list));
	}
	/**
	 * uuid 生成订单号
	 * @return
	 */
	public static String getOrderIdByUUId() {
         int machineId = 1;//最大支持1-9个集群机器部署
         int hashCodeV = UUID.randomUUID().toString().hashCode();
         if(hashCodeV < 0) {//有可能是负数
             hashCodeV = - hashCodeV;
         }
         // 0 代表前面补充0     
         // 4 代表长度为4     
         // d 代表参数为正数型
         return machineId + String.format("%015d", hashCodeV);
     }
}
