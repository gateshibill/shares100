package com.cofc.controller.goods;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cofc.pojo.ApplicationCommon;
import com.cofc.pojo.BackUser;
import com.cofc.pojo.GoodsCommon;
import com.cofc.pojo.GoodsType;
import com.cofc.pojo.UserBrowseRecord;
import com.cofc.pojo.UserRole;
import com.cofc.pojo.activity.CutGoods;
import com.cofc.pojo.activity.PinGoods;
import com.cofc.pojo.goods.GoodsSpec;
import com.cofc.pojo.goods.GoodsSpecType;
import com.cofc.pojo.goods.MoreSpec;
import com.cofc.pojo.vij.GoodBrand;
import com.cofc.service.ApplicationCommonService;
import com.cofc.service.GoodsCommonService;
import com.cofc.service.GoodsTypeService;
import com.cofc.service.UserBackuserRelationService;
import com.cofc.service.UserBrowseRecordService;
import com.cofc.service.UserRoleService;
import com.cofc.service.activity.CutGoodsService;
import com.cofc.service.activity.DtbtGoodsService;
import com.cofc.service.activity.PinGoodsService;
import com.cofc.service.goods.GoodsSpecService;
import com.cofc.service.goods.GoodsSpecTypeService;
import com.cofc.service.goods.MoreSpecService;
import com.cofc.service.vij.GoodBrandService;
import com.cofc.util.BaseUtil;
import com.cofc.util.JsonUtil;

@Controller
@RequestMapping("/goods")
public class GoodsController extends BaseUtil {
	@Resource
	private GoodsCommonService goodsService;
	@Resource
	private GoodsTypeService gtypeService;
	@Resource
	private ApplicationCommonService applicationService;
	@Resource
	private UserBackuserRelationService uburelaService;
	@Resource
	private UserRoleService userroleService;
	@Resource
	private CutGoodsService cutGoodsService;
	@Resource
	private PinGoodsService pinGoodsService;
	@Resource
	private DtbtGoodsService DtbtGoodsService;
	@Resource
    private GoodBrandService brandService; //商品品牌
	@Resource
	private UserBrowseRecordService userRecordService;
    //产品规格
	@Resource
	private GoodsSpecTypeService goodsSpecTypeService;
	@Resource
	private GoodsSpecService goodsSpecService;
	@Resource
	private  MoreSpecService moreSpecService;
	public static Logger log = Logger.getLogger("GoodsController");

	@RequestMapping("/goGoodsList")
	public ModelAndView toShowGoodsList(HttpServletRequest request, ModelAndView modelView, GoodsCommon goods,
			String userKeyWords, String dateRange, String appName) {
		log.info("进入商品列表的jsp页面");
		BackUser bu = (BackUser) request.getSession().getAttribute("loginer");
		List<ApplicationCommon> appList = new ArrayList<ApplicationCommon>();
		List<GoodsType> typeList = new ArrayList<GoodsType>();
		if (bu.getLoginPlat() == null || bu.getLoginPlat().equals("")) {
			appList = applicationService.getNewApplicationList(null);
			typeList = gtypeService.getNewTypeList(null);
		} else {
			String[] idStrings = bu.getLoginPlat().split(",");
			List<String> loginPlatList = Arrays.asList(idStrings);
			appList = applicationService.getNewApplicationList(loginPlatList);
			typeList = gtypeService.getNewTypeList(loginPlatList);
		}
		log.info("进入后台用户列表的jsp页面");
		modelView.addObject("appList", appList);
		modelView.addObject("typeList", typeList);
		modelView.addObject("goods", goods);
		modelView.addObject("appName", appName);
		modelView.addObject("userKeyWords", userKeyWords);
		modelView.addObject("dateRange", dateRange);
		modelView.setViewName("goodsManage/goodsList");
		return modelView;
	}

	// 后台商品列表
	@RequestMapping("/goodsList")
	public void showGoodsList(HttpServletRequest request, HttpServletResponse response, ModelAndView modelView,
			GoodsCommon goods, String dateRange, Integer loginPlat, String appName, Integer page, Integer limit,
			String userName) throws ParseException {
		BackUser bu = (BackUser) request.getSession().getAttribute("loginer");
		if (page == null || page == 0) {
			page = 1;
		}
		if (limit == null || limit == 0) {
			limit = 20;
		}
		request.getSession().setAttribute("currGoodsPage", page);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat startSdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		SimpleDateFormat endSdf = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
		String startTime = null;
		String endTime = null;
		if (dateRange != null && !dateRange.equals("")) {
			String[] myData = dateRange.split(" -| ");
			startTime = startSdf.format(sdf.parse(myData[0]));
			endTime = endSdf.format(sdf.parse(myData[2]));
		}
		int count = 0;
		List<GoodsCommon> goodsList = new ArrayList<GoodsCommon>();
		if (bu.getLoginPlat() == null || bu.getLoginPlat().equals("")) { // 百享园
			count = goodsService.getCountGoods(loginPlat, goods, startTime, endTime, userName, appName);
			goodsList = goodsService.findGoodsList2(loginPlat, goods, startTime, endTime, (page - 1) * limit, limit,
					userName, appName);
		} else {
			if (loginPlat == null) {
				String[] idStrings = bu.getLoginPlat().split(",");
				List<String> loginPlatList = Arrays.asList(idStrings);
				count = goodsService.getCountGoodsByLoginPlat(loginPlatList, goods, startTime, endTime, userName,
						appName);
				goodsList = goodsService.getGoodsByLoginPlat(loginPlatList, goods, startTime, endTime,
						(page - 1) * limit, limit, userName, appName);
			} else {
				count = goodsService.getCountGoods(loginPlat, goods, startTime, endTime, userName, appName);
				goodsList = goodsService.findGoodsList2(loginPlat, goods, startTime, endTime, (page - 1) * limit, limit,
						userName, appName);
			}

		}

		output(response, JsonUtil.buildJsonByTotalCount(goodsList, count));
	}

	/**
	 * 添加商品页面
	 * 
	 * @param modelView
	 * @return
	 */
	@RequestMapping("/goAddGoods")
	public ModelAndView goAddGoods(HttpServletRequest request, ModelAndView modelView) {
		BackUser bu = (BackUser) request.getSession().getAttribute("loginer");
		List<GoodsType> gTypeList = new ArrayList<GoodsType>();
		List<ApplicationCommon> commList = new ArrayList<ApplicationCommon>();
		if (bu.getLoginPlat() == null || bu.getLoginPlat() == "") {
			gTypeList = gtypeService.getNewTypeList(null);
			commList = applicationService.findApplicationCommon(null);
		} else {
			String[] idStrings = bu.getLoginPlat().split(",");
			List<String> loginPlatList = Arrays.asList(idStrings);
			gTypeList = gtypeService.getNewTypeList(loginPlatList);
			commList = applicationService.getApplicationByLoginPlat(loginPlatList);
		}
		List<MoreSpec> lists = moreSpecService.getMoreSpecList(null);
		modelView.addObject("lists",lists);
		modelView.addObject("goodsTList", gTypeList);
		modelView.addObject("commList", commList);
		modelView.setViewName("goodsManage/addGoods");
		return modelView;
	}

	// 后台添加商品
	@RequestMapping("/addGoods")
	public void addGoods(HttpServletRequest request, HttpServletResponse response, GoodsCommon goods,
			String moreGGStr,String ggStr) {
		BackUser currLoginer = (BackUser) request.getSession().getAttribute("loginer");
		ApplicationCommon currapp = applicationService.getApplicationById(goods.getLoginPlat());
		if(currLoginer == null){
			output(response,JsonUtil.buildFalseJson("1", "请先登录"));
			return;
		}
		goods.setCreateTime(new Date());
		goods.setSelledCount(0);
		goods.setIsHot(0);
		if (goods.getIsRecommend() == 1) {
			goods.setRecommendWay(goods.getRecommendWay());
		}
		goods.setPublisherId(currapp.getApplicationOwner());
		if(goods.getIsDtbt() == 1){
			//获取当前时间戳
			long times = (System.currentTimeMillis() / 1000);
			goods.setSeckillStartTime(times);

		}
		goodsService.addNewGoods(goods);
		Integer goodsId = goods.getGoodsId();
		//多规格
		if(goodsId != null){
			if(goods.getIsMoreSpec() == 1){ //执行添加多规格
				try {
					JSONArray arraytype = new JSONArray(ggStr);
					JSONObject objecttype = null; 
					int arrtypeCount = arraytype.length();
					if(arrtypeCount > 0){
						for (int j = 0; j < arrtypeCount; j++) {
							 objecttype = arraytype.getJSONObject(j);
							 Integer type = objecttype.getInt("id");
							 String name = objecttype.getString("name");
							 if(!name.equals("")){
								 GoodsSpecType specType =new GoodsSpecType();
								 specType.setAppId(goods.getLoginPlat());
								 specType.setGoodsId(goodsId);
								 specType.setType(type);
								 specType.setName(name);
								 goodsSpecTypeService.addGoodsSpecType(specType);
							 }
						}
					}
					
					
					JSONArray array = new JSONArray(moreGGStr);
					JSONObject object = null;
					int arrCount = array.length();
					if(arrCount > 0){
						for (int i = 0; i < arrCount; i++) {
							 object = array.getJSONObject(i);
							 String type1 = object.getString("type1");
							 String type2 = object.getString("type2");
							 Double price = object.getDouble("price");
							// int stock = object.getInt("kucun");//库存先放着
							 GoodsSpec spec = new GoodsSpec();
							 spec.setAppId(goods.getLoginPlat());
							 spec.setGoodsId(goodsId);
							 spec.setCreateTime(new Date());
							 spec.setType1(type1);
							 spec.setType2(type2);
							 spec.setPrice(price);
							 goodsSpecService.addGoodsSpec(spec);						
						}
					}
				} catch (Exception e) {
					log.info("添加多规格失败");
				}
			}
		}
		output(response, JsonUtil.buildFalseJson("0", "添加成功"));
	}

	// 后台商品详情
	@RequestMapping("/goodsDetails")
	public ModelAndView showGoodsDetails(HttpServletResponse response, ModelAndView modelView, Integer goodsId,
			Integer limit) {
		GoodsCommon currGoods = goodsService.getGoodsById(goodsId);
		GoodsType type = gtypeService.getGoodsTypeById(currGoods.getGoodsType());
		if (currGoods.getGoodsImage() != null && !currGoods.getGoodsImage().equals("")) {
			String[] myImage = currGoods.getGoodsImage().split(",");
			modelView.addObject("myImage", myImage);
			System.out.println("====" + myImage);
			modelView.addObject("length", myImage.length);
		}

		if (currGoods.getDetailsImage() != null) {
			String[] myImage1 = currGoods.getDetailsImage().split(",");
			modelView.addObject("myImage1", myImage1);
			modelView.addObject("length1", myImage1.length);
		}
		if(currGoods.getIsMoreSpec() != null){
			if(currGoods.getIsMoreSpec() == 1){
				List<GoodsSpec> speclist = goodsSpecService.getGoodsSpec(null, goodsId); 
				List<GoodsSpecType> spectypelist = goodsSpecTypeService.getGoodsSpecType(null, goodsId);
				modelView.addObject("speclist", speclist);
				modelView.addObject("spectypelist", spectypelist);
			}
		}
		if(currGoods.getLoginPlat() != null){
			GoodBrand brand = new GoodBrand();
			brand.setLoginPlat(currGoods.getLoginPlat());
			brand.setIsRemove(0);
			List<GoodBrand> brands = brandService.getGoodBrandList(brand, null, null);
			modelView.addObject("brands", brands);
		}	
		List<MoreSpec> morelists = moreSpecService.getMoreSpecList(null);
		modelView.addObject("morelists", morelists);
		List<GoodsType> lists = gtypeService.getVijParentList(currGoods.getLoginPlat());
		modelView.addObject("plists", lists);
		modelView.setViewName("goodsManage/goodsDetails");
		modelView.addObject("type", type);
		modelView.addObject("goods", currGoods);
		return modelView;
	}

	// 修改商品信息
	@RequestMapping("/updateGoods")
	public void updateGoods(HttpServletResponse response, GoodsCommon goods,HttpServletRequest request) {
		BackUser bu = (BackUser) request.getSession().getAttribute("loginer");
		if(bu == null){
			output(response,JsonUtil.buildFalseJson("1", "请先登录"));
		}else{
			goods.setUpdateTime(new Date());
			goodsService.updateGoodsInfo(goods);
			output(response, JsonUtil.buildFalseJson("0", "修改商品成功!"));
		}
		
	}
	
	// 修改商品信息--砍价
	@RequestMapping("/updateCutGoods")
	public void updateCutGoods(HttpServletResponse response,CutGoods cutGoods, HttpServletRequest request) {
		GoodsCommon goodsCommon = goodsService.getGoodsById(cutGoods.getGoodsId());
		ApplicationCommon currapp = applicationService.getApplicationById(goodsCommon.getLoginPlat());
	
		//设置商品表
		GoodsCommon goods=new GoodsCommon();
		goods.setUpdateTime(new Date());
		goods.setGoodsId(cutGoods.getGoodsId());
		goods.setIsCut(cutGoods.getStatus());
		goodsService.updateGoodsInfo(goods);
		//更新设置砍价
		CutGoods cutGoods_1=cutGoodsService.getCutByGoodsId(cutGoods.getGoodsId());
		if(cutGoods_1!=null){
			cutGoodsService.updateCutByCutGoods(cutGoods);
		}else{
			cutGoods.setAppId(currapp.getApplicationId());
			cutGoodsService.addCutGoods_1(cutGoods);
		}
		output(response, JsonUtil.buildFalseJson("0", "修改商品成功!"));
	}
	
	// 修改商品信息--拼团
	@RequestMapping("/updatePinGoods")
	public void updatePinGoods(HttpServletResponse response,PinGoods pinGoods, HttpServletRequest request) {
		GoodsCommon goodsCommon = goodsService.getGoodsById(pinGoods.getGoodsId());
		ApplicationCommon currapp = applicationService.getApplicationById(goodsCommon.getLoginPlat());
		//设置商品表
		GoodsCommon goods=new GoodsCommon();
		goods.setUpdateTime(new Date());
		goods.setGoodsId(pinGoods.getGoodsId());
		goods.setIsPin(pinGoods.getStatus());
		goodsService.updateGoodsInfo(goods);
		//更新设置砍价
		PinGoods pinGoods_1=pinGoodsService.getPinByGoodsId(goods.getGoodsId());
		if(pinGoods_1!=null){
			pinGoodsService.updatePinByPinGoods(pinGoods);
		}else{
			pinGoods.setAppId(currapp.getApplicationId());
			pinGoodsService.addPinGoods_1(pinGoods);
		}
		output(response, JsonUtil.buildFalseJson("0", "修改商品成功!"));
	}
	
	/**
	 * 设置秒杀页面
	 * @param request
	 * @param mv
	 * @param goodsId
	 * @return
	 */
	@RequestMapping("/goDtbtGoods")
	public  ModelAndView goDtbtGoods(HttpServletRequest request,ModelAndView mv,Integer goodsId){
		GoodsCommon good = goodsService.getGoodsById(goodsId);
		mv.addObject("good", good);
		mv.setViewName("/goodsManage/updateGoodsDtbtGoods");
		return mv;
	}
	
	// 修改商品信息--秒杀
	@RequestMapping("/updateDtbtGoods")
	public void updateDtbtGoods(HttpServletResponse response,HttpServletRequest request,GoodsCommon good) {
		if(good.getIsDtbt() == 1){
			//获取当前时间戳
			long times = (System.currentTimeMillis() / 1000);
			good.setSeckillStartTime(times);
		}
		goodsService.updateGoodsInfo(good);
		output(response, JsonUtil.buildFalseJson("0", "修改商品成功!"));
	}

	// ajax查询应用
	@RequestMapping("/loginPlatList")
	public void loginPlatList(HttpServletResponse response, Integer pageNo, Integer pageSize) {
		if (pageNo == null) {
			pageNo = 1;
		}
		if (pageSize == null) {
			pageSize = 6;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pageNo", (pageNo - 1) * pageSize);
		map.put("pageSize", pageSize);
		List<ApplicationCommon> appList = applicationService.getApplicationCommonList(map);
		if (!appList.isEmpty() && appList != null) {
			output(response, JsonUtil.buildJson(appList));
		} else {
			output(response, JsonUtil.buildFalseJson("1", "没有更多应用了!"));
		}
	}

	// 删除商品
	@RequestMapping("/deleteGoodsId")
	public void deleteGoodsId(HttpServletResponse response, Integer goodsId, HttpServletRequest request) {
		GoodsCommon goods = goodsService.getGoodsById(goodsId);
		goodsService.deleteGoodsCommon(goods);
		output(response, JsonUtil.buildFalseJson("0", "删除商品成功!"));
	}

	// ajax查看商品类型
	@RequestMapping("/showGoodsType")
	public void showGoodsType(HttpServletResponse response, Integer page, Integer limit, Integer loginPlat,
			HttpServletRequest request) {
		if (page == null) {
			page = 1;
		}
		if (limit == null) {
			limit = 6;
		}
		
		List<GoodsType> typeList = new ArrayList<GoodsType>();
		int rowsCount = 0;
		typeList = gtypeService.findAllGoodsType(loginPlat, (page - 1) * limit, limit, null, null, null,null);
		rowsCount = gtypeService.findAllGoodsTypeCount(loginPlat, null, null, null,null);
		output(response, JsonUtil.buildJsonByTotalCount(typeList, rowsCount));
	}
	/**
	 * 获取商品品牌
	 * @param response
	 * @param loginPlat
	 */
	@RequestMapping("/getAllBrand")
	public void getAllBrand(HttpServletResponse response,Integer loginPlat){
		if(loginPlat == null){
			output(response,JsonUtil.buildFalseJson("1", "请选择应用"));
		}else{
			GoodBrand brand = new GoodBrand();
			brand.setIsRemove(0);
			brand.setLoginPlat(loginPlat);
			List<GoodBrand> brandlist = brandService.getGoodBrandList(brand, null, null);
			output(response,JsonUtil.buildJson(brandlist));
		}
	}
	/**
	 * 获取父级分类
	 * @param response
	 * @param loginPlat
	 */
	@RequestMapping("/getParentType")
	public void getParentType(HttpServletResponse response,Integer loginPlat){
		if(loginPlat == null){
			output(response,JsonUtil.buildFalseJson("1", "请选择应用"));
		}else{
			List<GoodsType> lists = gtypeService.getVijParentList(loginPlat);
			output(response,JsonUtil.buildJson(lists));
		}
	}
	/**
	 * 二级分类页面
	 * @param response
	 * @param mv
	 * @param parentId
	 * @return
	 */
	@RequestMapping("/childGoodsType")
	public ModelAndView childGoodsType(HttpServletRequest request,ModelAndView mv,Integer parentId){
		mv.addObject("parentId", parentId);
		mv.setViewName("goodsManage/childgoodsTypeList");
		return mv;
	}
	/**
	 * 添加二级分类
	 * @param request
	 * @param mv
	 * @param parentId
	 * @return
	 */
	@RequestMapping("/addChildType")
	public ModelAndView addChildType(HttpServletRequest request,ModelAndView mv,Integer parentId){
		mv.addObject("parentId", parentId);
        GoodsType type = gtypeService.getGoodsTypeById(parentId);
        mv.addObject("loginPlat", type.getLoginPlat());
		mv.setViewName("goodsManage/addChildGoodsType");
		return mv;
	}
	/**
	 * 区别下面的方法，返回数据格式不同
	 * @param response
	 * @param parentId
	 */
	@RequestMapping("/getChildGoodsTypeList")
	public void getChildGoodsTypeList(HttpServletResponse response,Integer parentId,Integer page,Integer limit){
		if(page == null){
			page = 1;
		}
		if(limit == null){
			limit = 15;
		}
		int totalCount = gtypeService.getVijChildCount(parentId);
		List<GoodsType> lists = gtypeService.getVijChildList(parentId, (page-1) * limit, limit);
		output(response,JsonUtil.buildJsonByTotalCount(lists, totalCount));
	}
	/**
	 * 获取子集分类
	 * @param response
	 * @param parentId
	 */
	@RequestMapping("/getChildType")
	public void getChildType(HttpServletResponse response,Integer parentId){
		if(parentId == null){
			output(response,JsonUtil.buildFalseJson("1", "请选择一级分类"));
		}else{
			List<GoodsType> lists = gtypeService.getVijChildList(parentId,null,null);
			output(response,JsonUtil.buildJson(lists));
		}
	}
	
	// 添加商品类型
	@RequestMapping("/addGoodsType")
	public void addGoodsType(HttpServletResponse response, GoodsType type, HttpServletRequest request) {
		ApplicationCommon currapp = applicationService.getApplicationById(type.getLoginPlat());
		GoodsType goodstype = gtypeService.getLoginPlatTypeName(type.getTypeName(), type.getLoginPlat(),
				currapp.getApplicationOwner());
		if (goodstype != null) {
			output(response, JsonUtil.buildFalseJson("1", "此应用该类型名称已存在，请更换类型名称!"));
		} else {
			type.setCreateTime(new Date());
			type.setCreateUser(currapp.getApplicationOwner());
			gtypeService.addNewGoodsType(type);
			output(response, JsonUtil.buildFalseJson("0", type.getTypeId().toString()));
		}
	}

	// 商品类型列表
	@RequestMapping("/goodsTypeList")
	public ModelAndView goodsTypeList(ModelAndView mView, HttpServletRequest request, String name,
			String applicationName, Integer loginPlat) {
		BackUser bu = (BackUser) request.getSession().getAttribute("loginer");
		List<ApplicationCommon> appList = new ArrayList<ApplicationCommon>();
		if (bu.getLoginPlat() == null || bu.getLoginPlat() == "") {
			appList = applicationService.getNewApplicationList(null);
		} else {
			String[] idStrings = bu.getLoginPlat().split(",");
			List<String> loginPlatList = Arrays.asList(idStrings);
			appList = applicationService.getNewApplicationList(loginPlatList);
		}
		log.info("进入后台商品类型列表的jsp页面");
		mView.addObject("name", name);
		mView.addObject("applicationName", applicationName);
		mView.addObject("appList", appList);
		mView.addObject("loginPlat", loginPlat);
		mView.setViewName("goodsManage/goodsTypeList");
		return mView;
	}

	// ajax查看商品类型
	@RequestMapping("/showGoodsTypeList")
	public void showGoodsTypeList(HttpServletResponse response, Integer page, Integer limit, String typeName,
			String applicationName, HttpServletRequest request, Integer loginPlat,Integer isRemove) {
		if (page == null) {
			page = 1;
		}
		if (limit == null) {
			limit = 20;
		}
		BackUser bu = (BackUser) request.getSession().getAttribute("loginer");

		List<GoodsType> typeList = null;
		int count = 0;
		if (bu.getLoginPlat() == null || bu.getLoginPlat() == "") { // 百享园
			typeList = gtypeService.findAllGoodsType(loginPlat, (page - 1) * limit, limit, typeName, null,
					applicationName,isRemove);
			count = gtypeService.findAllGoodsTypeCount(loginPlat, typeName, null, applicationName,isRemove);
		} else {
			if (loginPlat == null) {
				String[] idStrings = bu.getLoginPlat().split(",");
				List<String> loginPlatList = Arrays.asList(idStrings);
				typeList = gtypeService.getGoodTypeByLoginPlat(loginPlatList, loginPlat, (page - 1) * limit, limit,
						typeName, null, applicationName,isRemove);
				count = gtypeService.getGoodTypeCountByLoginPlat(loginPlatList, loginPlat, typeName, null,
						applicationName,isRemove);

			} else {
				typeList = gtypeService.findAllGoodsType(loginPlat, (page - 1) * limit, limit, typeName, null,
						applicationName,isRemove);
				count = gtypeService.findAllGoodsTypeCount(loginPlat, typeName, null, applicationName,isRemove);
			}
		}

		output(response, JsonUtil.buildJsonByTotalCount(typeList, count));
	}

	@RequestMapping("/addGootype")
	public ModelAndView addGootype(HttpServletRequest request, ModelAndView mView) {
		BackUser bu = (BackUser) request.getSession().getAttribute("loginer");
		List<ApplicationCommon> commList = new ArrayList<ApplicationCommon>();
		if (bu.getLoginPlat() == null || bu.getLoginPlat().equals("")) {
			commList = applicationService.findApplicationCommon(null);
		} else {
			String[] idStrings = bu.getLoginPlat().split(",");
			List<String> loginPlatList = Arrays.asList(idStrings);
			commList = applicationService.getApplicationByLoginPlat(loginPlatList);
		}
		mView.addObject("commList", commList);
		mView.setViewName("goodsManage/addGoodsType");
		return mView;
	}

	// 添加商品类型
	@RequestMapping("/saveGoodsType")
	public void saveGoodsType(HttpServletResponse response, HttpServletRequest request, GoodsType type) {
		ApplicationCommon currapp = applicationService.getApplicationById(type.getLoginPlat());

		GoodsType type2 = gtypeService.getLoginPlatTypeName(type.getTypeName(), type.getLoginPlat(),
				currapp.getApplicationOwner());
		if (type2 != null) {
			output(response, JsonUtil.buildFalseJson("1", "此应用该类型名称已存在，请更换类型名称!"));
		} else {
			type.setIsRemove(0);
			type.setCreateTime(new Date());
			type.setCreateUser(currapp.getApplicationOwner());
			type.setOrderNo(1);// 新增的商品类型排列默认未1
			gtypeService.addNewGoodsType(type);
			output(response, JsonUtil.buildFalseJson("0", "添加成功!"));
		}
	}

	// 删除商品类型
	@RequestMapping("/deleteGoodsType")
	public void deleteGoodsType(HttpServletResponse response, Integer typeId, HttpServletRequest request) {

		GoodsType type = gtypeService.getGoodsTypeById(typeId);
		List<GoodsCommon> goodsList = goodsService.findGoodsTypeIsNull(typeId, type.getLoginPlat());
		if (goodsList != null && !goodsList.isEmpty()) {
			output(response, JsonUtil.buildFalseJson("1", "该商品类型已被使用，暂不能删除!"));
		}

		else {
			int count = gtypeService.getVijChildCount(typeId);
			if(count > 0){
				output(response,JsonUtil.buildFalseJson("1", "该分类存在子分类,请先删除子分类"));
			}else{
				try {
					gtypeService.deleteGoodsType(typeId);
					output(response, JsonUtil.buildFalseJson("0", "删除成功!"));
				} catch (Exception e) {
					output(response, JsonUtil.buildFalseJson("3", "删除失败!"));
				}
			}
			
		}
	}

	// 修改商品类型页面
	@RequestMapping("/updaGoodsType")
	public ModelAndView updaGoodsType(ModelAndView mView, Integer typeId) {
		GoodsType type = gtypeService.getGoodsTypeById(typeId);
		List<GoodsType> plists = gtypeService.getVijParentList(type.getLoginPlat());
		mView.addObject("plists", plists);
		mView.addObject("type", type);
		mView.setViewName("goodsManage/updateGoodsType");
		return mView;
	}

	@RequestMapping("/updateTypeName")
	public void updateTypeName(HttpServletResponse response, GoodsType type, HttpServletRequest request) {
		BackUser bu = (BackUser) request.getSession().getAttribute("loginer");
		GoodsType type2 = gtypeService.getLoginPlatTypeName(type.getTypeName(), type.getLoginPlat(), bu.getUserId());
		GoodsType type3 = gtypeService.getGoodsTypeById(type.getTypeId());
		if (type2 != null && !type.getTypeName().equals(type3.getTypeName())) {
			output(response, JsonUtil.buildFalseJson("1", "此应用该类型名称已存在，请更换类型名称!"));
		} else {
			type.setCreateTime(new Date());
			gtypeService.updateGoodsType(type);
			output(response, JsonUtil.buildFalseJson("0", "修改成功!"));
		}
	}

	// 修改商品类型排列
	@RequestMapping("/updateGoodsTypeOrder")
	public void updateGoodsTypeOrder(HttpServletResponse response, GoodsType type) {
		GoodsType goodsType = gtypeService.getGoodsTypeById(type.getTypeId());
		// if (goodsType.getOrderNo() == 0) {
		// output(response, JsonUtil.buildFalseJson("1", "此排列不能修改!"));
		// } else
		if (goodsType == null) {
			output(response, JsonUtil.buildFalseJson("1", "该商品类型不存在!"));
		} else {
			if (type.getOrderNo() == null || type.getOrderNo() < 0) {
				output(response, JsonUtil.buildFalseJson("2", "请输入正确的排列数字!"));
			} else {
				if (type.getOrderNo() != null) {
					type.setCreateTime(new Date());
					gtypeService.updateGoodsType(type);
				}
				output(response, JsonUtil.buildFalseJson("0", "修改成功!"));
			}
		}
	}

	// 商品统计
	@RequestMapping("/goGoodsCount")
	public ModelAndView goGoodsCount(ModelAndView mView) {
		mView.setViewName("goodsManage/goodsCount");
		return mView;
	}

	@RequestMapping("/goodsrecoveryList")
	public ModelAndView goodsrecoveryList(ModelAndView modelAndView, HttpServletRequest request) {
		BackUser bu = (BackUser) request.getSession().getAttribute("loginer");
		
		List<ApplicationCommon> appList = new ArrayList<ApplicationCommon>();
		List<GoodsType> typeList = new ArrayList<GoodsType>();
		if (bu.getLoginPlat() == null || bu.getLoginPlat().equals("")) {
			appList = applicationService.getNewApplicationList(null);
			typeList = gtypeService.getNewTypeList(null);
		} else {
			String[] idStrings = bu.getLoginPlat().split(",");
			List<String> loginPlatList = Arrays.asList(idStrings);
			appList = applicationService.getApplicationByLoginPlat(loginPlatList);
			typeList = gtypeService.getNewTypeList(loginPlatList);
		}
		log.info("进入后台用户列表的jsp页面");
		modelAndView.addObject("appList", appList);
		modelAndView.addObject("typeList", typeList);
		modelAndView.setViewName("goodsManage/recoveryList");
		return modelAndView;
	}

	@RequestMapping("/goodsClassifyList")
	public ModelAndView goodsClassifyList(ModelAndView modelAndView, Integer loginPlat, Integer goodsType) {
		modelAndView.addObject("goodsType", goodsType);
		modelAndView.addObject("loginPlat", loginPlat);
		modelAndView.setViewName("goodsManage/goodsClassifyList");
		return modelAndView;
	}

	// 删除商品图片
	@RequestMapping("/updateGoodsImage")
	public void updateGoodsImage(HttpServletResponse response, Integer goodsId, int index, int type) {
		GoodsCommon goods = goodsService.getGoodsById(goodsId);
		if (type == 1) {// 外观图
			String[] myImage = goods.getGoodsImage().split(",");
			myImage[index - 1] = myImage[myImage.length - 1];
			// 数组缩容
			myImage = Arrays.copyOf(myImage, myImage.length - 1);
			String my = "";
			for (int i = 0; i < myImage.length; i++) {
				my += myImage[i] + ",";
			}
			if (myImage.length > 0) {
				my.substring(0, my.length() - 1);
			}
			goods.setUpdateTime(new Date());
			goods.setGoodsImage(my);
			goodsService.updateGoodsInfo(goods);
			output(response, JsonUtil.buildFalseJson("0", "修改成功!"));
			System.out.println("==============" + myImage);
		} else {// 详情图
			String[] my1 = goods.getDetailsImage().split(",");
			my1[index - 1] = my1[my1.length - 1];
			// 数组缩容
			my1 = Arrays.copyOf(my1, my1.length - 1);
			String my = "";
			for (int i = 0; i < my1.length; i++) {
				my += my1[i] + ",";
			}
			if (my1.length > 0) {
				my.substring(0, my.length() - 1);
			}
			goods.setUpdateTime(new Date());
			goods.setDetailsImage(my);
			goodsService.updateGoodsInfo(goods);
			output(response, JsonUtil.buildFalseJson("0", "修改成功!"));
		}
	}

	// 批量真删除
	@RequestMapping("/batchDelGoods")
	public void batchDelGoods(HttpServletResponse response, String goodsIds, int type) {
		List<GoodsCommon> goodsList = goodsService.getGoodsByIds(Arrays.asList(goodsIds.split(",")));
		try {
			if (type == 1) {
				for (GoodsCommon goods : goodsList) {
					goods.setGoodsId(goods.getGoodsId());
					goodsService.deleteGoodsCommon(goods);
				}
				output(response, JsonUtil.buildFalseJson("0", "批量删除成功!"));
			} else {
				for (GoodsCommon goods : goodsList) {
					goods.setGoodsId(goods.getGoodsId());
					goods.setUpdateTime(new Date());
					goods.setGoodsStatus(1);
					goodsService.updateGoodsInfo(goods);
				}
				output(response, JsonUtil.buildFalseJson("0", "批量恢复成功!"));
			}
		} catch (Exception e) {
			output(response, JsonUtil.buildFalseJson("1", "批量操作失败!"));
		}
	}

	// 商品浏览情况
	@RequestMapping("/goBrowseList")
	public ModelAndView goodsIdBrowseList(ModelAndView modelAndView, Integer goodsId) {
		modelAndView.addObject("goodsId", goodsId);
		modelAndView.setViewName("goodsManage/goodsCount");
		return modelAndView;
	}

	// 商品浏览情况
	@RequestMapping("/showBrowseList")
	public void showBrowseList(HttpServletResponse response, Integer goodsId, Integer page, Integer limit,
			String userName, String goodsName, String dateRange) throws ParseException {
		if (page == null) {
			page = 1;
		}
		if (limit == null) {
			limit = 20;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat startSdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		SimpleDateFormat endSdf = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
		String startTime = null;
		String endTime = null;
		if (dateRange != null && !dateRange.equals("")) {
			String[] myData = dateRange.split(" -| ");
			startTime = startSdf.format(sdf.parse(myData[0]));
			endTime = endSdf.format(sdf.parse(myData[2]));
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userName", userName);
		map.put("goodsId", goodsId);
		map.put("goodsName", goodsName);
		map.put("startTime", startTime);
		map.put("endTime", endTime);
		map.put("limit", limit);
		map.put("page", (page - 1) * limit);
		List<UserBrowseRecord> recordList = userRecordService.getUserBrowseRecordList(map);
		int count = userRecordService.getUserBrowseRecordCount(map);
		output(response, JsonUtil.buildJsonByTotalCount(recordList, count));
	}
	/**
	 * 是否允许积分兑换
	 * @param response
	 * @param goodId
	 * @param isScore
	 */
	@RequestMapping("/updateIsScore")
	public void updateIsScore(HttpServletResponse response,Integer goodId,Integer isScore){
		if(goodId == null){
			output(response,JsonUtil.buildFalseJson("1","系统不存在该商品"));
		}else{
			try {
				goodsService.updateIsScore(goodId, isScore);
				output(response,JsonUtil.buildFalseJson("0","操作成功"));
			} catch (Exception e) {
				log.error("设置商品积分兑换失败,失败原因："+e.getMessage());
				output(response,JsonUtil.buildFalseJson("1","操作失败"));
			}
		}
	}
}
