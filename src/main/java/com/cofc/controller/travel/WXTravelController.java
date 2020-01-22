package com.cofc.controller.travel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cofc.pojo.TravelComment;
import com.cofc.pojo.TravelCommon;
import com.cofc.pojo.TravelJoin;
import com.cofc.pojo.TravelRecord;
import com.cofc.pojo.TravelView;
import com.cofc.service.TravelCommentService;
import com.cofc.service.TravelCommonService;
import com.cofc.service.TravelJoinService;
import com.cofc.service.TravelRecordService;
import com.cofc.service.TravelViewService;
import com.cofc.util.BaseUtil;
import com.cofc.util.JsonUtil;

/**
 * 旅游有关的前端调用接口文件：时间紧迫接口没有做一些限制，有时间慢慢修改
 * 
 * @author 46678
 *
 */
@Controller
@RequestMapping("/wx/travel")
public class WXTravelController extends BaseUtil {
	@Resource
	private TravelCommonService travelCommonService;
	@Resource
	private TravelJoinService travelJoinService;
	@Resource
	private TravelViewService viewService;
	@Resource
	private TravelRecordService recordService;
	@Resource
	private TravelCommentService commentService;
	public static Logger log = Logger.getLogger("WXTravelController");

	/**
	 * 添加行程接口
	 * 
	 * @param response
	 * @param travel
	 */
	@RequestMapping("/addtravel")
	public void addTravel(HttpServletResponse response, TravelCommon travel, String viewlist) {
		if (travel.getTravelPublisherId() == null) {
			log.info("新增行程失败：发布者id不能为空");
			output(response, JsonUtil.buildFalseJson("1", "新增行程失败：发布者id不能为空"));
			return;
		}
		if (travel.getLoginPlat() == null) {
			log.info("新增行程失败：平台id不能为空");
			output(response, JsonUtil.buildFalseJson("1", "新增行程失败:平台id不能为空"));
			return;
		}
		if (travel.getTravelTitle().equals("")) {
			log.info("新增行程失败：行程标题不能为空");
			output(response, JsonUtil.buildFalseJson("1", "新增行程失败:行程标题不能为空"));
			return;
		}
		travel.setCreateTime(new Date());
		travel.setTravelGuiderId(travel.getTravelPublisherId());
		travel.setIsHot(0);
		travel.setIsRecommend(0);
		travel.setTravelJoinPeople(0);
		travel.setIsEffect(1);
		travel.setState(1);
		travelCommonService.addTravelInfo(travel);
		int travelId = travel.getTravelId();
		try {
			log.info("新增行程成功");
			TravelView view = new TravelView();
			JSONArray jsonArray = JSON.parseArray(viewlist);
			for (Object object : jsonArray) {
				Map<String, Object> map = JSONObject.parseObject(object.toString(), Map.class);
				view.setTravelId(travelId);
				view.setIsOrder(1);
				view.setQqmapViewId(map.get("id").toString());
				view.setLatitude(map.get("lat").toString());
				view.setLongitude(map.get("lng").toString());
				view.setLocation(map.get("name").toString());
				viewService.addTravelViewInfo(view);
			}

			output(response, JsonUtil.buildFalseJson("0", String.valueOf(travelId)));
		} catch (Exception e) {
			// TODO: handle exception
			log.info("新增行程失败");
			output(response, JsonUtil.buildFalseJson("1", "新增行程失败，失败原因：" + e));
		}
	}

	/**
	 * 获取行程列表
	 * 
	 * @param response
	 * @param loginPlat
	 * @param pageNo
	 * @param pageSize
	 */
	@RequestMapping("/gettravellist")
	public void getTravleList(HttpServletResponse response, Integer loginPlat, Integer pageNo, Integer pageSize) {
		if (pageNo == null) {
			pageNo = 1;
		}
		if (pageSize == null) {
			pageSize = 5;
		}
		if (loginPlat == null) {
			log.info("获取行程列表失败：平台id为空");
			output(response, JsonUtil.buildFalseJson("1", "获取行程列表失败：平台id为空"));
			return;
		}
		List<TravelCommon> travelList = travelCommonService.getTravelListInfo(loginPlat, (pageNo - 1) * pageSize,
				pageSize);
		output(response, JsonUtil.buildJson(travelList));
	}

	/**
	 * 编辑行程
	 * 
	 * @param response
	 * @param travel
	 */
	@RequestMapping("/updatetravelinfo")
	public void updateTravel(HttpServletResponse response, TravelCommon travel) {
		if (travel.getTravelId() == null) {
			log.info("修改行程：行程id不能为空");
			output(response, JsonUtil.buildFalseJson("1", "修改行程：行程id不能为空"));
			return;
		}
		travelCommonService.updateTravelInfo(travel);
		try {
			log.info("修改行程成功");
			if (travel.getRedBagCount() != null) {
				if (travel.getRedBagCount() > 0) {
					TravelView view = new TravelView();
					view.setTravelId(travel.getTravelId());
					view.setRedbagCount(travel.getRedBagCount());
					viewService.updateAllRedbag(view);
				}
			}
			output(response, JsonUtil.buildFalseJson("0", "修改行程成功"));
		} catch (Exception e) {
			log.info("修改行程失败，失败原因：" + e);
			output(response, JsonUtil.buildFalseJson("1", "修改行程失败"));
		}
	}

	/**
	 * 删除行程：假删除只是改变is_effect的值
	 * 
	 * @param response
	 * @param travelId
	 * @param userId
	 */
	@RequestMapping("/deletetravel")
	public void deleteTravel(HttpServletResponse response, Integer travelId, Integer userId) {
		if (travelId == null) {
			log.info("用户编号为：" + userId + ",删除行程失败,失败原因未传递travelId");
			output(response, JsonUtil.buildFalseJson("1", "删除行程失败：传递参数非法"));
			return;
		}
		TravelCommon travel = travelCommonService.getTravelDetaiInfo(travelId);
		if (travel.getState() == 2) {
			log.info("该行程正在进行中,不允许删除");
			output(response, JsonUtil.buildFalseJson("1", "该行程正在进行中,不允许删除"));
			return;
		}
		if (travel.getTravelJoinPeople() > 0 && travel.getState() == 1) {
			log.info("该行程已有旅客加入,不允许删除");
			output(response, JsonUtil.buildFalseJson("1", "该行程已有旅客加入,不允许删除"));
			return;
		}
		travelCommonService.deleteTravelInfo(travelId, userId);
		try {
			log.info("用户编号为：" + userId + ",删除行程成功");
			output(response, JsonUtil.buildFalseJson("0", "删除行程成功"));
		} catch (Exception e) {
			log.info("用户编号为：" + userId + ",删除行程失败,失败原因：" + e);
			output(response, JsonUtil.buildFalseJson("1", "删除行程失败"));
		}
	}

	/**
	 * 获取行程详情
	 * 
	 * @param response
	 * @param travelId
	 */
	@RequestMapping("/traveldetail")
	public void travelDetail(HttpServletResponse response, Integer travelId) {
		if (travelId == null) {
			log.info("获取行程详情失败,失败原因未传递travelId");
			output(response, JsonUtil.buildFalseJson("1", "获取行程详情失败"));
			return;
		}
		TravelCommon traveldetail = travelCommonService.getTravelDetaiInfo(travelId);
		output(response, JsonUtil.objectToJson(traveldetail));
	}

	/**
	 * 获取行程景点：为了便于做地图红包开发
	 * 
	 * @param response
	 * @param travelId
	 */
	@RequestMapping("/gettravelview")
	public void getTravelView(HttpServletResponse response, Integer travelId) {
		if (travelId == null) {
			log.info("获取行程景点失败,失败原因未传递travelId");
			output(response, JsonUtil.buildFalseJson("1", "获取景点详情失败"));
			return;
		}
		List<TravelView> viewlist = viewService.getTravelViewByTravelId(travelId);
		output(response, JsonUtil.buildJson(viewlist));
	}

	/**
	 * 加入行程接口
	 * 
	 * @param response
	 * @param tjoin
	 */
	@RequestMapping("/jointravel")
	public void joinTravel(HttpServletResponse response, TravelJoin tjoin) {
		if (tjoin.getUserId() == null) {
			log.info("加入行程失败,失败原因未传递userId");
			output(response, JsonUtil.buildFalseJson("1", "加入行程失败：传递参数非法"));
			return;
		}
		if (tjoin.getTravelId() == null) {
			log.info("加入行程失败,失败原因未传递travelId");
			output(response, JsonUtil.buildFalseJson("1", "加入行程失败：传递参数非法"));
			return;
		}
		TravelCommon travel = travelCommonService.getTravelDetaiInfo(tjoin.getTravelId());
		if (travel.getIsEffect() != 1) {
			output(response, JsonUtil.buildFalseJson("1", "该行程已失效，不允许加入"));
			return;
		}
		if (travel.getState() != 1) {
			output(response, JsonUtil.buildFalseJson("1", "该行程处于不可加入状态"));
			return;
		}
		TravelJoin tlist = travelJoinService.getAlreadyJoin(tjoin.getTravelId(), tjoin.getUserId());
		if (tlist != null) {
			output(response, JsonUtil.buildFalseJson("1", "您已加入该行程"));
			return;
		}
		tjoin.setCreateTime(new Date());
		tjoin.setState(1);
		travelJoinService.addTravelJoin(tjoin);
		try {
			log.info("加入行程成功");
			TravelCommon t = new TravelCommon();
			t.setTravelId(travel.getTravelId());
			t.setTravelJoinPeople(travel.getTravelJoinPeople() + 1);
			travelCommonService.updateTravelInfo(t);
			output(response, JsonUtil.buildFalseJson("0", "加入行程成功"));
		} catch (Exception e) {
			log.info("加入行程失败,失败原因：" + e);
			output(response, JsonUtil.buildFalseJson("1", "加入行程失败,失败原因：" + e));
		}
	}

	/**
	 * 获取加入行程旅客列表
	 * 
	 * @param response
	 * @param travelId
	 * @param pageNo
	 * @param pageSize
	 */
	@RequestMapping("/getjoinlist")
	public void getJoinList(HttpServletResponse response, Integer travelId, Integer pageNo, Integer pageSize) {
		if (pageNo == null) {
			pageNo = 1;
		}
		if (pageSize == null) {
			pageSize = 10;
		}
		if (travelId == null) {
			log.info("获取行程列表失败：行程id为空");
			output(response, JsonUtil.buildFalseJson("1", "获取行程列表失败：行程id为空"));
			return;
		}
		List<TravelJoin> traveljoinlist = travelJoinService.getTravelJoinListInfo(travelId, (pageNo - 1) * pageSize,
				pageSize);
		output(response, JsonUtil.buildJson(traveljoinlist));
	}

	/**
	 * 用户抢红包
	 * 
	 * @param response
	 * @param userId
	 * @param travelId
	 * @param qqmapViewId
	 */
	@RequestMapping("/usergetredbag")
	public void userGetRedBag(HttpServletResponse response, Integer userId, Integer travelId, Integer viewId) {
		// 1.验证传值的合法性
		if (userId == null) {
			log.info("抢红包失败：用户id为空");
			output(response, JsonUtil.buildFalseJson("1", "抢红包失败：参数传递有误"));
			return;
		}
		if (travelId == null) {
			log.info("抢红包失败：行程id为空");
			output(response, JsonUtil.buildFalseJson("1", "抢红包失败：参数传递有误"));
			return;
		}
		if (viewId == null) {
			log.info("抢红包失败：景点id为空");
			output(response, JsonUtil.buildFalseJson("1", "抢红包失败：参数传递有误"));
			return;
		}
		// 2.验证行程是否进行中状态
		TravelCommon travelCommon = travelCommonService.getTravelDetaiInfo(travelId);
		if (travelCommon.getState() != 1) {
			log.info("该行程不在可抢红包状态");
			output(response, JsonUtil.buildFalseJson("1", "该行程不在可抢红包状态"));
			return;
		}
		// 加入
		TravelJoin join = travelJoinService.getAlreadyJoin(travelId, userId);
		if (join == null) {
			log.info("您还未加入行程不允许抢红包");
			output(response, JsonUtil.buildFalseJson("1", "您还未加入行程不允许抢红包"));
			return;
		}
		// 3.验证是否有红包可以抢,你是否抢过了
		TravelView view = viewService.getTravelViewByqqViewId(travelId, viewId);
		if (view.getRedbagCount() <= 0) {
			log.info("对不起您来晚了，红包已被抢完");
			output(response, JsonUtil.buildFalseJson("1", "红包已被抢完"));
			return;
		}
		TravelRecord record = recordService.getisAlready(userId, travelId, viewId);
		if (record != null) {
			log.info("您已 抢过改红包啦");
			output(response, JsonUtil.buildFalseJson("1", "亲，不要太贪心了，你已经抢过该景点的红包啦"));
			return;
		}
		// 4.插入抢红包记录
		TravelRecord r = new TravelRecord();
		TravelView v = new TravelView();
		r.setCreateTime(new Date());
		r.setUserId(userId);
		r.setIsEffect(1);
		r.setViewId(viewId);
		r.setTravelId(travelId);
		recordService.addTravelRecordInfo(r);
		try {
			log.info("插入抢红包记录数据成功");
			// 5.相应景点红包个数减去1
			v.setRedbagCount(view.getRedbagCount() - 1);
			v.setTravelId(travelId);
			v.setViewId(viewId);
			viewService.updateRedbag(v);
			log.info("更新景点红包数量成功");
			output(response, JsonUtil.buildFalseJson("0", "恭喜您抢了个大红包"));
		} catch (Exception e) {
			log.info("抢红包失败，失败原因：" + e);
			output(response, JsonUtil.buildFalseJson("1", "很遗憾抢红包失败"));
		}

	}

	/**
	 * 检测用户是否加入该行程
	 * 
	 * @param response
	 * @param travelId
	 * @param userId
	 */
	@RequestMapping("/checkuserjoin")
	public void checkUserJoin(HttpServletResponse response, Integer travelId, Integer userId) {
		if (userId == null) {
			log.info("用户id为空");
			output(response, JsonUtil.buildFalseJson("1", "参数传递有误：用户id为空"));
			return;
		}
		if (travelId == null) {
			log.info("行程id为空");
			output(response, JsonUtil.buildFalseJson("1", "参数传递有误"));
			return;
		}
		TravelJoin join = travelJoinService.getAlreadyJoin(travelId, userId);
		if (join == null) {
			output(response, JsonUtil.buildFalseJson("0", "未加入"));
		} else {
			output(response, JsonUtil.buildFalseJson("1", "已加入"));
		}
	}

	/**
	 * 获取当个景点信息
	 * 
	 * @param response
	 * @param qqmapViewId
	 * @param travelId
	 */
	@RequestMapping("/getviewlocation")
	public void getViewLocation(HttpServletResponse response,Integer viewId, Integer travelId) {
		TravelView view = viewService.getTravelViewByqqViewId(travelId,viewId);
		if (view != null) {
			output(response, JsonUtil.objectToJson(view));
		} else {
			output(response, JsonUtil.buildFalseJson("1", "获取景点信息失败"));
		}
	}

	/**
	 * 获取用户行程列表 【我的行程列表】
	 * 
	 * @param response
	 * @param userId
	 * @param pageNo
	 * @param pageSize
	 */
	@RequestMapping("/getmyselfjointravel")
	public void getMyselfJoinTravel(HttpServletResponse response, Integer userId, Integer loginPlat, Integer pageNo,
			Integer pageSize) {
		if (userId == null) {
			log.info("用户id为空");
			output(response, JsonUtil.buildFalseJson("1", "参数传递有误：用户id为空"));
		} else {
			List<TravelJoin> joinlist = travelJoinService.getMyJoinTravel(userId, loginPlat, (pageNo - 1) * pageSize,
					pageSize);
			if (joinlist == null) {
				output(response, JsonUtil.buildFalseJson("1", "暂无加入行程信息"));
			} else {
				output(response, JsonUtil.buildJson(joinlist));
			}
		}
	}

	/**
	 * 用户退出行程
	 * 
	 * @param response
	 * @param userId
	 * @param travelId
	 */
	@RequestMapping("userquittravel")
	public void userQuitTravel(HttpServletResponse response, Integer userId, Integer travelId) {
		if (userId == null) {
			log.info("用户id为空");
			output(response, JsonUtil.buildFalseJson("1", "参数传递有误：用户id为空"));
		} else {
			if (travelId == null) {
				log.info("travelId为空");
				output(response, JsonUtil.buildFalseJson("1", "参数传递有误：行程id为空"));
			} else {
				TravelCommon travel = travelCommonService.getTravelDetaiInfo(travelId);
				if (travel == null) {
					log.info("该行程不存在或已被删除");
					output(response, JsonUtil.buildFalseJson("1", "该行程不存在"));
				} else {
					if (travel.getState() != 1) {
						log.info("该行程不在可退团状态");
						output(response, JsonUtil.buildFalseJson("1", "该行程不在可退团状态"));
					} else {
						travelJoinService.quitTravel(travelId, userId);
						try {
							TravelCommon t = new TravelCommon();
							t.setTravelId(travel.getTravelId());
							t.setTravelJoinPeople(travel.getTravelJoinPeople() - 1);
							travelCommonService.updateTravelInfo(t);
							output(response, JsonUtil.buildFalseJson("0", "退出行程成功"));
						} catch (Exception e) {
							log.info("退出行程失败，失败原因：" + e);
							output(response, JsonUtil.buildFalseJson("1", "退出行程失败"));
						}
					}
				}

			}
		}
	}
	
	/**
	 * 用户抢红包记录
	 * @param response
	 * @param userId
	 * @param loginPlat
	 * @param pageNo
	 * @param pageSize
	 */
	@RequestMapping("/getrobrecord")
	public void getRobRecord(HttpServletResponse response,Integer userId,Integer loginPlat,Integer pageNo,Integer pageSize){
		if(pageNo == null){
			pageNo = 1;
		}
		if(pageSize == null){
			pageSize = 10;
		}
		List<TravelRecord> lists = recordService.getRobRecordListByUserId(userId,loginPlat,(pageNo-1) * pageSize,pageSize);
		output(response,JsonUtil.buildJson(lists));
	}

	/**
	 * 用户留言接口
	 * 
	 * @param response
	 * @param comment
	 */
	@RequestMapping("/addcomment")
	public void addComment(HttpServletResponse response, TravelComment comment) {
		if (comment.getUserId() == null) {
			log.info("用户id为空");
			output(response, JsonUtil.buildFalseJson("1", "参数传递有误：用户id为空"));
			return;
		}
		if (comment.getTravelId() == null) {
			log.info("行程id为空");
			output(response, JsonUtil.buildFalseJson("1", "参数传递有误：行程id为空"));
			return;
		}

		comment.setCreateTime(new Date());
		comment.setIsEffect(1);
		commentService.addCommentInfo(comment);
		try {
			log.info("发布留言成功");
			output(response, JsonUtil.buildFalseJson("0", "发布留言成功"));
		} catch (Exception e) {
			log.info("发布留言失败,失败原因：" + e);
			output(response, JsonUtil.buildFalseJson("1", "发布留言失败"));
		}

	}

	/**
	 * 生成路书
	 * 
	 * @param response
	 * @param travelId
	 * @param pageNo
	 * @param pageSize
	 */
	@RequestMapping("/getcommentlist")
	public void getCommentList(HttpServletResponse response, Integer travelId, Integer pageNo, Integer pageSize) {
		if (pageNo == null) {
			pageNo = 1;
		}
		if (pageSize == null) {
			pageSize = 10;
		}
		if (travelId == null) {
			log.info("行程id为空");
			output(response, JsonUtil.buildFalseJson("1", "参数传递有误：行程id为空"));
			return;
		}
		List<TravelComment> cList = commentService.getCommentList(travelId, (pageNo - 1) * pageSize, pageSize);
		if (cList == null) {
			output(response, JsonUtil.buildFalseJson("1", "暂无留言信息"));
		} else {
			output(response, JsonUtil.buildJson(cList));
		}
	}

	/**
	 * 删除自己的留言
	 * 
	 * @param response
	 * @param commentId
	 * @param userId
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	@RequestMapping("/deletecomment")
	public void deleteComment(HttpServletResponse response, Integer commentId, Integer userId) throws Exception {
		if (commentId == null) {
			output(response, JsonUtil.buildFalseJson("1", "参数传递有误"));
			return;
		}
		if (userId == null) {
			output(response, JsonUtil.buildFalseJson("1", "参数传递有误"));
			return;
		}
		TravelComment comment = commentService.getOneComment(commentId);
		long nowTime = System.currentTimeMillis() / 1000;
		long commentTime = dateToStamp(comment.getCreateTime()) / 1000;
		int jgTime = (int) (nowTime - commentTime);
		if (jgTime > 3600) {
			output(response, JsonUtil.buildFalseJson("1", "该条评论已发布超过一小时，不允许删除"));
			return;
		}
		if (comment == null) {
			output(response, JsonUtil.buildFalseJson("1", "该条评论不存在"));
		} else {
			if (!comment.getUserId().equals(userId)) {
				output(response, JsonUtil.buildFalseJson("1", "该条评论不是你发布的,你没有权限删除"));
			} else {
				try {
					commentService.delComment(commentId);
					log.info("删除评论commentId=" + commentId + "成功");
					output(response, JsonUtil.buildFalseJson("0", "删除评论成功"));
				} catch (Exception e) {
					log.info("删除评论commentId=" + commentId + "失败，失败原因：" + e);
					output(response, JsonUtil.buildFalseJson("1", "删除评论失败"));
				}
			}
		}

	}

	/**
	 * 更新行程状态
	 * 
	 * @param response
	 * @param travelId
	 * @param state
	 */
	@RequestMapping("/updatetravelstate")
	public void updateTravelState(HttpServletResponse response, Integer travelId, Integer state) {
		TravelCommon travel = travelCommonService.getTravelDetaiInfo(travelId);
		if (travel == null) {
			output(response, JsonUtil.buildFalseJson("1", "该行程不存在或已被删除"));
		} else {
			try {
				travelCommonService.updateState(travelId, state);
				log.info("更新行程travelId=" + travelId + "状态成功");
				output(response, JsonUtil.buildFalseJson("0", "更新行程状态成功"));
			} catch (Exception e) {
				log.info("更新行程travelId=" + travelId + "状态失败");
				output(response, JsonUtil.buildFalseJson("1", "更新行程状态失败"));
			}
		}
	}
    
	/**
	 * 行程统计
	 * @param response
	 * @param travelId
	 * @param userId
	 */
	@RequestMapping("/tongjitravel")
	public void tongjiTravel(HttpServletResponse response,Integer travelId,Integer userId){
		TravelCommon travel = travelCommonService.getTravelDetaiInfo(travelId);
		int redbagCount = 0;
		if(travel.getTravelViewCount() != null && travel.getRedBagCount() != null){
		    redbagCount = travel.getTravelViewCount()*travel.getRedBagCount();//红包总数 = 景点数*每个景点的红包数
		}
		int joinpeopleCount = travelJoinService.getJoinPeopleCountByTravelId(travelId); //加入行程人数
		int travelcommentCount = commentService.getCommentCountByTravelId(travelId); //行程收到的总评论数
		int usercommentCount = commentService.getCommentCountByUserId(travelId, userId); //某个用户在某个行程里面的总评论数
		int myrobredbagCount = recordService.getRedbagCountByUserId(travelId, userId); //我抢的红包数量
		int commentImageCount = commentService.getCommentImageCountByTravelId(travelId);//留言图片数量
		int mycommentimageCount = commentService.getCommentImageCountByUserId(travelId, userId);//我留言的图片数量
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("redbagCount",redbagCount);
		map.put("joinpeopleCount",joinpeopleCount);
		map.put("travelcommentCount",travelcommentCount);
		map.put("usercommentCount",usercommentCount);
		map.put("myrobredbagCount",myrobredbagCount);
		map.put("commentImageCount",commentImageCount);
		map.put("mycommentimageCount",mycommentimageCount);
		map.put("travelRoute",0);
		map.put("travelViewList",travel.getTravelViewList());
		output(response,JsonUtil.MapToJson(map));

	}
	
	/**
	 * 获取行程中的所有景点
	 * @param response
	 * @param travelId
	 */
	@RequestMapping("/gettravelviewlist")
	public void getTravelViewList(HttpServletResponse response,Integer travelId){
		List<TravelView> lists = viewService.getTravelViewByTravelId(travelId);
		output(response,JsonUtil.buildJson(lists));
	}
	
	/**
	 * 时间转时间戳方法
	 * @param s
	 * @return
	 * @throws Exception
	 */
	public static long dateToStamp(Date s) throws Exception {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String s1 = simpleDateFormat.format(s);
		Date date = simpleDateFormat.parse(s1);
		long ts = date.getTime();
		return ts;
	}

}
