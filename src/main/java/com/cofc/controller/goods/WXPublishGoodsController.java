package com.cofc.controller.goods;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cofc.controller.file.ErWeiCodeImageContoller;
import com.cofc.pojo.ApplicationCommon;
import com.cofc.pojo.GoodShareCode;
import com.cofc.pojo.GoodsCommon;
import com.cofc.pojo.GoodsType;
import com.cofc.pojo.UserBrowseRecord;
import com.cofc.pojo.UserCollection;
import com.cofc.pojo.UserCommon;
import com.cofc.pojo.UserShoppingCar;
import com.cofc.pojo.goods.GoodsSpec;
import com.cofc.pojo.goods.GoodsSpecType;
import com.cofc.service.ApplicationCommonService;
import com.cofc.service.GoodShareCodeService;
import com.cofc.service.GoodsCommonService;
import com.cofc.service.GoodsTypeService;
import com.cofc.service.UserBrowseRecordService;
import com.cofc.service.UserCollectionService;
import com.cofc.service.UserCommonService;
import com.cofc.service.UserShoppingCarService;
import com.cofc.service.goods.GoodsSpecService;
import com.cofc.service.goods.GoodsSpecTypeService;
import com.cofc.service.vij.GoodCommentService;
import com.cofc.util.BaseUtil;
import com.cofc.util.JsonUtil;
import com.cofc.util.WeiXinSessionUtils;

@Controller
@RequestMapping("/wx/goods")
public class WXPublishGoodsController extends BaseUtil {
	@Resource
	private GoodsCommonService goodsService;
	@Resource
	private UserCommonService userService;
	@Resource
	private UserCollectionService ucService;
	@Resource
	private GoodsTypeService gtypeService;
	@Resource
	private ApplicationCommonService appService;
	@Resource
	private UserCollectionService collectionService;
	@Resource
	private UserBrowseRecordService userBrowseRecordService;
	@Resource
    private UserShoppingCarService carService;
	//产品规格
	@Resource
	private GoodsSpecTypeService goodsSpecTypeService;
	@Resource
	private GoodsSpecService goodsSpecService;
	//生成商品二维码
	@Resource
	private GoodShareCodeService codeService;
	@Resource
	private ApplicationCommonService applicationCommonService;// 小程序应用表
	@Resource
	private GoodCommentService commentService;//商品评价
	public static Logger log = Logger.getLogger("WXGoodsController");

	// 前端发布商品
	@RequestMapping("/publishGoods")
	public void userPublishGoods(HttpServletResponse response, GoodsCommon goods) {
		goods.setCreateTime(new Date());
		goods.setIsSelled(1);
		goods.setSelledCount(0);
		// goods.setIsHot(0); //需要接收前端来的值
		goods.setIsPassSell(1);
		// goods.setIsRecommend(0); //需要接收前端来的值
		try {
			goodsService.addNewGoods(goods);
			// GoodsCommon currGods =goodsService.getGoodsById(goods.getGoodsId());
			List<GoodsCommon> returnl = new ArrayList<GoodsCommon>();
			returnl.add(goods);
			output(response, JsonUtil.buildJson(returnl));
			log.info("用户" + goods.getPublisherId() + "发布商品" + goods.getGoodsName() + "成功!");
		} catch (Exception e) {
			output(response, JsonUtil.buildFalseJson("1", "发布失败!"));
			log.info("用户" + goods.getPublisherId() + "发布商品" + goods.getGoodsName() + "失败,失败原因" + e);
		}
	}

	// 获取商品详情
	@RequestMapping("/goodsDetails")
	public void userPublishGoods(HttpServletResponse response, Integer goodsid, Integer userId) {

		if (goodsid == null || goodsid <= 0) {
			output(response, JsonUtil.buildFalseJson("1", "goodsid值非法!"));
			return;
		}
		GoodsCommon goods = goodsService.getGoodsById(goodsid);
		UserCollection coll = collectionService.comfirmUserCollected(null, goodsid, userId, null);
		if (goods == null) {
			output(response, JsonUtil.buildFalseJson("1", "商品详情为空!"));
			return;
		}
		if (coll != null) {
			goods.setIsCancel(coll.getIsCancel());
		} else {
			goods.setIsCancel(null);
		}
		if(goods.getIsMoreSpec() == 1){
			List<GoodsSpec> speclist = goodsSpecService.getGoodsSpec(null, goodsid);
			if(speclist.size() > 0){
				goods.setSpecList(speclist);
			}
			List<GoodsSpecType> typelist = goodsSpecTypeService.getGoodsSpecType(null, goodsid);
			if(typelist.size() > 0){
				goods.setSpectypeList(typelist);
			}
		}
		//获取商品评价
		int chaCount = commentService.getCommentCount(null, 1);
		int zhongCount = commentService.getCommentCount(null, 2);
		int haoCount = commentService.getCommentCount(null, 3);
		goods.setHaoCount(haoCount);
		goods.setZhongCount(zhongCount);
		goods.setChaCount(chaCount);
		output(response, JsonUtil.objectToJson(goods));
	}

	// 商品类型列表(商品外面（商品分类表）列表)
	@RequestMapping("/goodsTypeList")
	public void goodsTypeList(HttpServletResponse response, Integer loginPlat, Integer userId) {
		// ApplicationCommon app = appService.getApplicationById(loginPlat);
		List<GoodsType> typeList = gtypeService.findAllGoodsType(loginPlat, null, null, null, null,null,0);
		if (typeList != null && !typeList.isEmpty()) {
			output(response, JsonUtil.buildJson(typeList));
		} else {
			output(response, JsonUtil.buildFalseJson("1", "没有数据!"));
		}
	}
	/**
	 * 获取一级分类
	 * @param response
	 * @param loginPlat
	 */
	@RequestMapping("/getGoodsTypeList")
	public void getGoodsTypeList(HttpServletResponse response,Integer loginPlat){
		List<GoodsType> lists = gtypeService.getVijParentList(loginPlat);
		output(response,JsonUtil.buildJson(lists));
	}
	
	// 前端展示商品列表
	@RequestMapping("/appGoodsList")
	public void appShowGoodsList(HttpServletResponse response, GoodsCommon goods, Integer pageNo, Integer pageSize) {
		if (pageNo == null || pageNo == 0) {
			pageNo = 1;
		}
		if (pageSize == null || pageSize == 0) {
			pageSize = 20;
		}
		List<GoodsCommon> goodsList = goodsService.findGoodsList(goods, null, null, (pageNo - 1) * pageSize, pageSize);
		if (goodsList != null && !goodsList.isEmpty()) {
			output(response, JsonUtil.buildJson(goodsList));
		} else {
			output(response, JsonUtil.buildFalseJson("1", "没有数据!"));
		}
	}
    
	/**
	 * 防止之前代码冲突：新开搜索方法
	 * @param response
	 * @param goods
	 * @param pageNo
	 * @param pageSize
	 */
	@RequestMapping("/newappGoodsList")
	public void newappShowGoodsList(HttpServletResponse response, GoodsCommon goods, Integer pageNo, Integer pageSize) {
		if (pageNo == null || pageNo == 0) {
			pageNo = 1;
		}
		if (pageSize == null || pageSize == 0) {
			pageSize = 20;
		}
		List<GoodsCommon> goodsList = goodsService.newfindGoodsList(goods, null, null, (pageNo - 1) * pageSize, pageSize);
		if (goodsList != null && !goodsList.isEmpty()) {
			output(response, JsonUtil.buildJson(goodsList));
		} else {
			output(response, JsonUtil.buildFalseJson("1", "没有数据!"));
		}
	}
	/**
	 * 餐饮模板拿值
	 * @param response
	 * @param goods
	 * @param pageNo
	 * @param pageSize
	 * @param userId
	 */
	@RequestMapping("/getgoodslist")
	public void getGoodsList(HttpServletResponse response,GoodsCommon goods, Integer pageNo, Integer pageSize,Integer userId){
		if (pageNo == null || pageNo == 0) {
			pageNo = 1;
		}
		if (pageSize == null || pageSize == 0) {
			pageSize = 20;
		}
		List<GoodsCommon> goodsList = goodsService.findGoodsList(goods, null, null, (pageNo - 1) * pageSize, pageSize);
		if (goodsList != null && !goodsList.isEmpty()) {
			for(GoodsCommon gcommon:goodsList){
				UserShoppingCar car = carService.findShoppingCarByOther(userId,gcommon.getGoodsId(),0);
				if(car!=null){
					gcommon.setNumber(car.getNumber());
					gcommon.setCarId(car.getCarId());
					gcommon.setCarGoods(car.getCarGoods());
					
				}
			}
			output(response, JsonUtil.buildJson(goodsList));
		} else {
			output(response, JsonUtil.buildFalseJson("1", "没有数据!"));
		}
	}
	
	// 我发布的商品列表
	@RequestMapping("/myGoodsList")
	public void goUserGoodsList(HttpServletResponse response, GoodsCommon goods, Integer pageNo, Integer pageSize) {
		if (pageNo == null || pageNo == 0) {
			pageNo = 1;
		}
		if (pageSize == null || pageSize == 0) {
			pageSize = 20;
		}
		List<GoodsCommon> goodsList = goodsService.getMyGoodsList(goods, (pageNo - 1) * pageSize, pageSize);
		if (goodsList != null && !goodsList.isEmpty()) {
			output(response, JsonUtil.buildJson(goodsList));
		} else {
			output(response, JsonUtil.buildFalseJson("1", "没有数据!"));
		}
	}

	// 前端删除和修改商品信息
	@RequestMapping("/updateGoods")
	public void appUpdateMyGoods(HttpServletResponse response, GoodsCommon goods) {
		GoodsCommon returnG = goodsService.getGoodsById(goods.getGoodsId());
		if (returnG == null) {
			output(response, JsonUtil.buildFalseJson("2", "该商品不存在!"));
		} else {
			// if (!returnG.getPublisherId().equals(goods.getPublisherId())) {
			// output(response, JsonUtil.buildFalseJson("3",
			// "您不是该商品的发布者，不能修改!"));
			// } else {
			try {
				// goods.setIsPassSell(0);// 是否上下架
				goods.setUpdateTime(new Date());
				goodsService.updateGoodsInfo(goods);
				output(response, JsonUtil.buildFalseJson("0", "修改成功!"));
			} catch (Exception e) {
				output(response, JsonUtil.buildFalseJson("1", "修改失败!"));
				log.info("商品" + goods.getGoodsId() + "修改信息失败，失败原因" + e);
			}
			// }
		}
	}

	// 前端审核商品
	@RequestMapping("/passGoods")
	public void managerDoPassGoods(HttpServletResponse response, Integer loginPlat, Integer managerId,
			Integer goodsId) {
		ApplicationCommon currapp = appService.getApplicationById(loginPlat);
		UserCommon currUser = userService.getUserByUserId(managerId);
		if (currUser != null) {
			if (currapp.getParentId() == null) {// 是小程序
				// if (currUser.getIsManager() != 1) {
				// output(response, JsonUtil.buildFalseJson("1",
				// "您不是管理员,不能审核该商品!"));
				// } else {
				try {
					goodsService.managerShenheGoods(goodsId);
					output(response, JsonUtil.buildFalseJson("0", "审核成功!"));
				} catch (Exception e) {
					output(response, JsonUtil.buildFalseJson("2", "审核失败!"));
					log.info("用户" + currUser + "审核商品" + goodsId + "失败 ，失败原因:" + e);
				}
				// }
			} else {
				if (currUser.getUserId() == currapp.getApplicationOwner()) {
					try {
						goodsService.managerShenheGoods(goodsId);
						output(response, JsonUtil.buildFalseJson("0", "审核成功!"));
					} catch (Exception e) {
						output(response, JsonUtil.buildFalseJson("2", "审核失败!"));
						log.info("用户" + currUser + "审核商品" + goodsId + "失败 ，失败原因:" + e);
					}
				} else {
					output(response, JsonUtil.buildFalseJson("1", "您不是管理员,不能审核该商品!"));
				}
			}
		} else {
			output(response, JsonUtil.buildFalseJson("3", "该用户不存在!"));
		}

	}

	// 前端商品发布者或者管理员下架商品
	@RequestMapping("/stopSellGoods")
	public void stopSellGoods(HttpServletResponse response, Integer userId, Integer goodsId) {
		UserCommon currUser = userService.getUserByUserId(userId);
		GoodsCommon currGoods = goodsService.getGoodsById(goodsId);
		if (currUser == null) {
			output(response, JsonUtil.buildFalseJson("1", "该用户没有找到!"));
		} else if (currGoods == null) {
			output(response, JsonUtil.buildFalseJson("2", "该商品没有找到!"));
		} else {
			if (currUser.getUserId() == currGoods.getPublisherId()) {
				if (currGoods.getIsSelled() == 0) {
					currGoods.setIsSelled(1);
					currGoods.setUpdateTime(new Date());
					goodsService.updateGoodsInfo(currGoods);
					output(response, JsonUtil.buildFalseJson("0", "上架成功!"));
					log.info("商品id" + goodsId + "上架成功，是发布者" + userId + "主动上架!");
				} else {
					currGoods.setIsSelled(0);
					currGoods.setUpdateTime(new Date());
					goodsService.updateGoodsInfo(currGoods);
					output(response, JsonUtil.buildFalseJson("0", "下架成功!"));
					log.info("商品id" + goodsId + "下架成功，是发布者" + userId + "主动下架!");
				}
			} else
			// if (currUser.getIsManager() == 1)
			{
				if (currGoods.getIsSelled() == 0) {
					currGoods.setIsSelled(1);
					currGoods.setUpdateTime(new Date());
					goodsService.updateGoodsInfo(currGoods);
					output(response, JsonUtil.buildFalseJson("0", "上架成功!"));
					log.info("商品id" + goodsId + "上架成功，是管理员" + userId + "操作上架!");
				} else {
					currGoods.setIsSelled(0);
					currGoods.setUpdateTime(new Date());
					goodsService.updateGoodsInfo(currGoods);
					output(response, JsonUtil.buildFalseJson("0", "下架成功!"));
					log.info("商品id" + goodsId + "下架成功，是管理员" + userId + "操作下架!");
				}
			}
			// else {
			// output(response, JsonUtil.buildFalseJson("3", "您没有权限进行该操作!"));
			// }
		}
	}

	@RequestMapping("/showgType")
	public void showGoodsTypes(HttpServletResponse response, int loginPlat, Integer pageNo, Integer pageSize) {
		List<GoodsType> gtypeList = gtypeService.findAllGoodsType(loginPlat, null, null, null, null,null,0);
		output(response, JsonUtil.buildJson(gtypeList));
	}

	// 添加商品类型(返回添加成功的类型名称)
	@RequestMapping("/addGoodsType")
	public synchronized void addGoodsType(HttpServletResponse response, GoodsType type, HttpServletRequest request, Integer userId) {
		ApplicationCommon apli = appService.getApplicationsByCriteria(type.getLoginPlat());
		GoodsType goodstype = gtypeService.getLoginPlatTypeName(type.getTypeName(), type.getLoginPlat(),
				apli.getApplicationOwner());
		if (goodstype != null) {
			output(response, JsonUtil.buildFalseJson("1", "此应用该类型名称已存在，请更换类型名称!"));
		} else if (type.getTypeName() == null || type.getTypeName().equals("")) {
			output(response, JsonUtil.buildFalseJson("2", "请输入商品类型名称!"));
		}
		// else if (userId == null || apli.getApplicationOwner() != userId) {
		// output(response, JsonUtil.buildFalseJson("2", "您没有添加商品类型的权限!"));
		// }
		else {
			type.setCreateTime(new Date());
			type.setCreateUser(apli.getApplicationOwner());
			type.setOrderNo(1);
			type.setIsRemove(0);
			gtypeService.addNewGoodsType(type);
			GoodsType type2 = gtypeService.getGoodsTypeById(type.getTypeId());
			List<GoodsType> typeList = new ArrayList<GoodsType>();
			typeList.add(type2);
			output(response, JsonUtil.buildJson(typeList));
		}
	}

	@RequestMapping("/showGoodsTypeList")
	public void showGoodsTypeList(HttpServletResponse response, Integer loginPlat, Integer userId) {
		List<GoodsType> typeList = gtypeService.showGoodsTypeList(loginPlat, userId);
		if (typeList != null && !typeList.isEmpty()) {
			output(response, JsonUtil.buildJson(typeList));
		} else {
			output(response, JsonUtil.buildFalseJson("1", "没有数据!"));
		}
	}

	// 前端删除商品
	@RequestMapping("/deleteGoods")
	public void deleteGoods(HttpServletResponse response, Integer goodsId) {
		GoodsCommon goods = goodsService.getGoodsById(goodsId);
		if (goods == null) {
			output(response, JsonUtil.buildFalseJson("1", "该商品不存在!"));
		} else {
			goodsService.deleteGoodsCommon(goods);
			output(response, JsonUtil.buildFalseJson("0", "删除商品成功!"));
		}
	}

	// 用户收藏商品
	@RequestMapping("/goodsCollection")
	public void goodsCollection(HttpServletResponse response, UserCollection collection) {
		UserCollection user = collectionService.comfirmUserCollected(null, collection.getGoodsId(),
				collection.getUserId(), collection.getLoginPlat());
		GoodsCommon goods = goodsService.getGoodsById(collection.getGoodsId());
		if (goods == null) {
			output(response, JsonUtil.buildFalseJson("1", "该商品已不存在!"));
		} else {
			if (user == null) {
				collection.setCreateTime(new Date());
				collection.setIsCancel(0);// 收藏成功
				collectionService.addNewCollection(collection);
				output(response, JsonUtil.buildFalseJson("0", "收藏成功!"));
			} else {
				if (user.getIsCancel() == 0) {// 准备取消收藏
					user.setCreateTime(new Date());
					user.setIsCancel(1);
					user.setCollectionId(user.getCollectionId());
					collectionService.cancelCollection(user);
					output(response, JsonUtil.buildFalseJson("1", "取消收藏成功!"));
				} else {// 收藏
					user.setCreateTime(new Date());
					user.setIsCancel(0);
					user.setCollectionId(user.getCollectionId());
					collectionService.cancelCollection(user);
					output(response, JsonUtil.buildFalseJson("0", "收藏成功!"));
				}
			}
		}
	}

	// 收藏商品列表
	@RequestMapping("/goodsCollList")
	public void goodsCollList(HttpServletResponse response, Integer loginPlat, Integer userId, Integer pageNo,
			Integer pageSize) {
		if (pageNo == null || pageNo == 0) {
			pageNo = 1;
		}
		if (pageSize == null || pageSize == 0) {
			pageSize = 20;
		}
		List<UserCollection> collList = collectionService.findMyGoodsCollectionList(userId, loginPlat,
				(pageNo - 1) * pageSize, pageSize);
		output(response, JsonUtil.buildJson(collList));
	}

	// 修改商品类型图标、名称
	@RequestMapping("/updateLogo")
	public synchronized void updateLogo(HttpServletResponse response, GoodsType type) {
		GoodsType type1 = gtypeService.getGoodsTypeById(type.getTypeId());
		if (type1 == null) {
			output(response, JsonUtil.buildFalseJson("1", "该类型不存在!"));
		} else {
			GoodsType name = gtypeService.getLoginPlatTypeName(type.getTypeName(), type1.getLoginPlat(), null);
			if (name != null && !type.getTypeName().equals(type1.getTypeName())) {
				output(response, JsonUtil.buildFalseJson("2", "该类型名称已存在!"));
			} else {
				type.setCreateTime(new Date());
				gtypeService.updateGoodsType(type);
				output(response, JsonUtil.buildFalseJson("0", "修改成功!"));
			}
		}
	}

	// 统计商品访客量(用户浏览记录)
	@RequestMapping("/goodsVisitors")
	public void goodsVisitors(HttpServletResponse response, Integer goodsId, int count, Integer userId) {
		GoodsCommon goods = goodsService.getGoodsById(goodsId);
		ApplicationCommon app = appService.getApplicationById(goods.getLoginPlat());
		goods.setUpdateTime(new Date());
		goods.setGoodsBrowse(count);
		goodsService.updateGoodsInfo(goods);
		UserBrowseRecord record = userBrowseRecordService.getUserBrowseUserById(userId, null, null, goodsId, null);
		if (record == null) {
			record = new UserBrowseRecord();
			record.setBrowseCount(1);
			record.setBrowseTime(new Date());
			record.setUserId(userId);
			record.setLoginPlat(goods.getLoginPlat());
			record.setPlatformId(app.getParentId());
			userBrowseRecordService.addUserBrowseRecord(record);
		} else {
			record.setBrowseCount(record.getBrowseCount() + 1);
			record.setId(record.getId());
			userBrowseRecordService.updateUserBrowseRecord(record);
		}
		output(response, JsonUtil.buildFalseJson("1", "商品浏览量增加成功!"));
	}
	/**
	 * @author 46678
	 * 用户访问商品：生成对应的商品信息二维码
	 * @param response
	 * @param request
	 * @param code
	 * @throws Exception 
	 */
	@RequestMapping("/markGoodCode")
	public void makeGoodCode(HttpServletResponse response,HttpServletRequest request,GoodShareCode code) throws Exception{
		GoodShareCode shareCode = codeService.getGoodCode(code.getUserId(), code.getAppId(), code.getGoodId());
		if(null == shareCode){ //执行插入数据
			//生成二维码
			String codepath = "";
			String wxappId="";
			String appSecret = "3b2f0416e80ed4821958ee7da189cf81";
			ApplicationCommon appinfo = applicationCommonService.getApplicationById(code.getAppId());
			if (appinfo.getAppId() != null && appinfo.getAppSecret() != null) {
				wxappId=appinfo.getAppId();
				appSecret = appinfo.getAppSecret();
			}
			String token = WeiXinSessionUtils.getAccessToken(wxappId, appSecret);
			log.info("生成二维码token--"+token);
			String page = "pages/mall/detail/detail";
			//备注 scene最多不超过32个字符
			//String scene = "introducer/" + code.getUserId() + "*salerId/" + code.getSalerId()+"*appId/"+code.getAppId()+"*goodId/"+code.getGoodId();
			//String scene = "introducer/" + code.getUserId()+"*goodId/"+code.getGoodId();
			String scene = "i/" + code.getUserId()+"*s/"+code.getSalerId()+"*g/"+code.getGoodId();
			Integer width = 430;
			codepath = ErWeiCodeImageContoller.getGoodShareQR(page, width, scene, token, request);
			code.setGoodCodeUrl(codepath);
			code.setCreateTime(new Date());
			codeService.addGoodCode(code);
			output(response,JsonUtil.buildFalseJson("0", "插入数据成功"));
		}else{
			output(response,JsonUtil.buildFalseJson("1", "数据已存在"));
		}
	}
	
	/**
	 * @author 46678
	 * 用户访问商品：生成对应的商品信息二维码
	 * @param response
	 * @param request
	 * @param code
	 * @throws Exception 
	 */
	@RequestMapping("/newMakeGoodCode")
	public void newMakeGoodCode(HttpServletResponse response,HttpServletRequest request,GoodShareCode code) throws Exception{
		GoodShareCode shareCode = codeService.getGoodCode(code.getUserId(), code.getAppId(), code.getGoodId());
		if(null == shareCode){ //执行插入数据
			//生成二维码
			String codepath = "";
			String wxappId="";
			String appSecret = "3b2f0416e80ed4821958ee7da189cf81";
			ApplicationCommon appinfo = applicationCommonService.getApplicationById(code.getAppId());
			if (appinfo.getAppId() != null && appinfo.getAppSecret() != null) {
				wxappId=appinfo.getAppId();
				appSecret = appinfo.getAppSecret();
			}
			String token = WeiXinSessionUtils.getAccessToken(wxappId, appSecret);
			log.info("生成二维码token--"+token);
			String page = "pages/mall/detail/detail";
			//备注 scene最多不超过32个字符
			//String scene = "introducer/" + code.getUserId() + "*salerId/" + code.getSalerId()+"*appId/"+code.getAppId()+"*goodId/"+code.getGoodId();
			//String scene = "introducer/" + code.getUserId()+"*goodId/"+code.getGoodId();
			String scene = "i/" + code.getUserId()+"*g/"+code.getGoodId();
			Integer width = 430;
			codepath = ErWeiCodeImageContoller.getGoodShareQR(page, width, scene, token, request);
			code.setGoodCodeUrl(codepath);
			code.setCreateTime(new Date());
			codeService.addGoodCode(code);
			output(response,JsonUtil.buildFalseJson("0", "插入数据成功"));
		}else{
			output(response,JsonUtil.buildFalseJson("1", "数据已存在"));
		}
	}
	
	/**
	 * 获取用户对应的商品二维码
	 * @param response
	 * @param userId
	 * @param appId
	 * @param goodId
	 */
	@RequestMapping("/getShareGoodCode")
	public void getShareGoodCode(HttpServletResponse response,Integer userId,Integer appId,Integer goodId){
		GoodShareCode code = codeService.getGoodCode(userId, appId, goodId);
		List<GoodShareCode> lists = new ArrayList<GoodShareCode>();
		if(code != null){
			lists.add(code);
		}
		output(response,JsonUtil.buildJson(lists));
	}
}
