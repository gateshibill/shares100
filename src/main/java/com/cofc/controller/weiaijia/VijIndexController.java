package com.cofc.controller.weiaijia;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cofc.pojo.CarouselPicture;
import com.cofc.pojo.GoodsCommon;
import com.cofc.pojo.GoodsType;
import com.cofc.pojo.UserCommon;
import com.cofc.pojo.vij.Adv;
import com.cofc.pojo.vij.Adviser;
import com.cofc.pojo.vij.Collocation;
import com.cofc.service.CarouselPictureService;
import com.cofc.service.GoodsCommonService;
import com.cofc.service.GoodsTypeService;
import com.cofc.service.vij.AdvService;
import com.cofc.service.vij.AdviserService;
import com.cofc.service.vij.CollocationService;
import com.cofc.util.BaseUtil;
import com.cofc.util.JsonUtil;
/**
 * 
 * @author hao
 *
 */
@Controller 
@RequestMapping("/vij/index")
public class VijIndexController extends BaseUtil{
	//public static final int APP_ID = 724;//88商城
	public  static final int  APP_ID = 709; //唯爱家正式
	//public  static final int  APP_ID = 704; //体验
	@Resource
	private AdvService advService;//首页广告
	@Resource
	private GoodsTypeService typeService;//商品分类
	@Resource
	private GoodsCommonService goodsService;//商品
	@Resource
	private CollocationService collocationService;//搭配
	@Resource
	private AdviserService adviserService;//顾问
	@Resource
	private CarouselPictureService carouselPictureService;//轮播图
	public static Logger log = Logger.getLogger("VijIndexController");
	/**
	 * 首页
	 * @param request
	 * @param mv
	 * @return
	 */
	@RequestMapping("/index")
	public ModelAndView index(HttpServletRequest request,ModelAndView mv){
		List<Adv> lists = advService.getlistByLocation(null, 1, 1,APP_ID);//查收首页所有的广告图片
		List<Adv> toplist = new ArrayList<Adv>();
		List<Adv> bannerlist = new ArrayList<Adv>();
		List<Adv> miaoshalist = new ArrayList<Adv>();
		List<Adv> xinpinlist = new ArrayList<Adv>();
		if(lists.size() > 0){
			for (Adv adv : lists) {
				if(adv.getAdvLocation() == 1){
					toplist.add(adv);
				}else if(adv.getAdvLocation() == 3){
					miaoshalist.add(adv);
				}else if(adv.getAdvLocation() == 4){
					xinpinlist.add(adv);
				}else{
					bannerlist.add(adv);
				}
			}
		}
		List<GoodsType> typelist = new ArrayList<GoodsType>();
		typelist = typeService.getVijTypeList(APP_ID);
		typelist = TypeTreeUtil.typeTreeList(typelist);//递归
		
		//限时秒杀
		GoodsCommon goods = new GoodsCommon();
		goods.setLoginPlat(APP_ID);
		goods.setIsPassSell(1);
		goods.setIsSelled(1);
		goods.setIsDtbt(1);
		List<GoodsCommon> goodslist = goodsService.getMyGoodsList(goods, 0,10);
		for (GoodsCommon goodsCommon : goodslist) {
			if(goodsCommon.getGoodsImage() != null || !goodsCommon.getGoodsImage().equals("")){
				String[] imageArr = goodsCommon.getGoodsImage().split(",");
				goodsCommon.setGoodOneImage(imageArr[0]);
			}
		}
		
		//新品推出:又是新品又是推荐就放在首页
		GoodsCommon xpgoods = new GoodsCommon();
		xpgoods.setLoginPlat(APP_ID);
		xpgoods.setIsPassSell(1);
		xpgoods.setIsSelled(1);
		xpgoods.setIsNew(1);
		xpgoods.setIsRecommend(1);
		List<GoodsCommon> xpgoodslist = goodsService.getMyGoodsList(xpgoods, 0, 15);
		for (GoodsCommon goodsCommon : xpgoodslist) {
			if(goodsCommon.getGoodsImage() != null || !goodsCommon.getGoodsImage().equals("")){
				String[] imageArr = goodsCommon.getGoodsImage().split(",");
				goodsCommon.setGoodOneImage(imageArr[0]);
			}
		}
		//主流搭配
		Collocation coll=new Collocation();
		coll.setCollType(1);
		coll.setLoginPlat(APP_ID);
		List<Collocation> collList = collocationService.getCollList(coll, 0,8);
		//计算图片张数
		for(int i=0;i<collList.size();i++){
			Collocation coll_1=collList.get(i);
			coll_1.setImagesCount(coll_1.getCollImages().split(",").length);
			collList.set(i, coll_1);
		}
		mv.addObject("collList", collList);
		
		//装修风格
		Collocation coll1 =new Collocation();
		coll1.setCollType(2);
		coll1.setLoginPlat(APP_ID);
		List<Collocation> collList1 = collocationService.getCollList(coll1, 0,8);
		//计算图片张数
		for(int i=0;i<collList1.size();i++){
			Collocation coll_2=collList1.get(i);
			coll_2.setImagesCount(coll_2.getCollImages().split(",").length);
			collList1.set(i, coll_2);
		}
		mv.addObject("collList1", collList1);
		
		//推荐
		Collocation coll2 =new Collocation();
		coll2.setIsRecommend(1);
		coll2.setLoginPlat(APP_ID);
		List<Collocation> collList2 = collocationService.getCollList(coll2, 0, 8);
		//计算图片张数
		for(int i=0;i<collList2.size();i++){
			Collocation coll_3=collList2.get(i);
			coll_3.setImagesCount(coll_3.getCollImages().split(",").length);
			collList2.set(i, coll_3);
		}
		mv.addObject("collList2", collList2);
		
		//顾问
		Adviser adviser=new Adviser();
		List<Adviser> adviserList = adviserService.getAdviserList(adviser, 0, 15);
		mv.addObject("adviserList", adviserList);
		//轮播图
		List<CarouselPicture> lbtlist = carouselPictureService.findCurrAppCarousels(APP_ID, null, null);
		mv.addObject("lbtlist", lbtlist);//轮播图
		mv.addObject("goodslist", goodslist); //秒杀商品
		mv.addObject("xpgoodslist", xpgoodslist);
		mv.addObject("typelist", typelist);
		mv.addObject("topadvlist",toplist);
		mv.addObject("bannerlist",bannerlist);
		mv.addObject("msadvlist",miaoshalist);
		mv.addObject("xpadvlist",xinpinlist);
		mv.setViewName("vij/index");
		return mv;
	}
	/**
	 * 新品
	 * @param request
	 * @param response
	 * @param mv
	 * @return
	 */
	@RequestMapping("/good")
	public ModelAndView good(HttpServletRequest request,HttpServletResponse response,ModelAndView mv){
		GoodsCommon goods = new GoodsCommon();
		goods.setLoginPlat(APP_ID);
		goods.setIsPassSell(1);
		goods.setIsNew(1);
		goods.setIsSelled(1);
		List<GoodsType> typelist = new ArrayList<GoodsType>();
		typelist = typeService.getVijTypeList(APP_ID);
		typelist = TypeTreeUtil.typeTreeList(typelist);//递归
		List<GoodsCommon> goodslist = goodsService.getMyGoodsList(goods,null,null);
		for (GoodsCommon goodsCommon : goodslist) {
			if(goodsCommon.getGoodsImage() != null || !goodsCommon.getGoodsImage().equals("")){
				String[] imageArr = goodsCommon.getGoodsImage().split(",");
				goodsCommon.setGoodOneImage(imageArr[0]);
			}
		}
		mv.addObject("typelist", typelist);//分类
		//所有新品商品
		mv.addObject("goodslist", goodslist);
		mv.setViewName("vij/good/xinpin_tuichu");
		return mv;
	}
	/**
	 * 商品列表
	 * @param request
	 * @param mv
	 * @return
	 */
	@RequestMapping("/list")
	public ModelAndView list(HttpServletRequest request,ModelAndView mv,GoodsCommon good,String typeName){
		mv.addObject("typeName", typeName);
		mv.addObject("good", good);
		mv.setViewName("vij/good/product_list");
		return mv;
	}
	/**
	 * 获取商品列表
	 * @param response
	 * @param good
	 * @param page
	 * @param limit
	 */
	@RequestMapping("/getGoodList")
	public void getGoodList(HttpServletResponse response,GoodsCommon good,Integer page,Integer limit){
		good.setLoginPlat(APP_ID);
		good.setIsSelled(1);
		good.setIsPassSell(1);
		if(page == null){
			page = 1;
		}
		if(limit == null){
			limit = 20;
		}
		int totalCount = goodsService.getMyGoodsCount(good);
		List<GoodsCommon> goodslist = goodsService.getMyGoodsList(good, (page-1) * limit, limit);
		for (GoodsCommon goodsCommon : goodslist) {
			if(goodsCommon.getGoodsImage() != null || !goodsCommon.getGoodsImage().equals("")){
				String[] imageArr = goodsCommon.getGoodsImage().split(",");
				goodsCommon.setGoodOneImage(imageArr[0]);
			}
		}
		output(response,JsonUtil.buildJsonByTotalCount(goodslist, totalCount));
	}
	/**
	 * 商品详情
	 * @param request
	 * @param response
	 * @param mv
	 * @return
	 */
	@RequestMapping("/gooddetail")
	public ModelAndView gooddetail(HttpServletRequest request,HttpServletResponse response,ModelAndView mv,
			Integer goodsId){
		mv.addObject("goodsId", goodsId);
		mv.setViewName("vij/good/product_detail");
		return mv;
	}
	/**
	 * 搭配
	 * @param request
	 * @param response
	 * @param mv
	 * @return
	 */
	@RequestMapping("/coll")
	public ModelAndView coll(HttpServletRequest request,HttpServletResponse response,ModelAndView mv,Collocation coll,Integer page,Integer limit){
		if (page == null) {
			page = 1;
		}
		if (limit == null) {
			limit = 100;
		}
		coll.setLoginPlat(APP_ID);
		List<Collocation> lists = collocationService.getCollList(coll, (page-1) * limit, limit);
		//计算图片张数
		for(int i=0;i<lists.size();i++){
			Collocation coll_1=lists.get(i);
			coll_1.setImagesCount(coll_1.getCollImages().split(",").length);
			lists.set(i, coll_1);
		}
		mv.addObject("coll", coll);
		mv.addObject("collList", lists);
		mv.setViewName("vij/good/dapei");
		return mv;
	}
	/**
	 * 搭配详情
	 * @param request
	 * @param response
	 * @param mv
	 * @return
	 */
	@RequestMapping("/collDetail")
	public ModelAndView collDetail(HttpServletRequest request,HttpServletResponse response,ModelAndView mv,Integer id){
		Collocation coll=collocationService.getInfoById(id);
		//分解图片集合
		String images[]=coll.getCollImages().split(",");
		List<String> imageList=new ArrayList<String>(); 
		for(int i=0;i<images.length;i++){
			imageList.add(images[i]);
		}
		coll.setImage(imageList);
		mv.addObject("coll", coll);
		mv.setViewName("vij/good/dapei_detail");
		return mv;
	}
	
	/**
	 * 顾问
	 * @param request
	 * @param response
	 * @param mv
	 * @return
	 */
	@RequestMapping("/adviser")
	public ModelAndView adviser(HttpServletRequest request,HttpServletResponse response,ModelAndView mv,Adviser adviser,Integer page,Integer limit){
		if (page == null) {
			page = 1;
		}
		if (limit == null) {
			limit = 15;
		}
		List<Adviser> lists = adviserService.getAdviserList(adviser, (page-1) * limit, limit);
		mv.addObject("adviserList", lists);
		mv.setViewName("vij/guwen");
		return mv;
	}
	/**
	 * 我们
	 * @param request
	 * @param response
	 * @param mv
	 * @return
	 */
	@RequestMapping("/welcome")
	public ModelAndView welcome(HttpServletRequest request,HttpServletResponse response,ModelAndView mv){
		mv.setViewName("vij/about_me");
		return mv;
	}
	/**
	 * 购物车
	 * @param request
	 * @param mv
	 * @return
	 */
	@RequestMapping("/cart")
	public ModelAndView cart(HttpServletRequest request,ModelAndView mv){
		UserCommon user = (UserCommon) request.getSession().getAttribute("vijMallUserSession");
		if(user == null){ //登陆
			mv.setViewName("vij/user_login");
		}else{
			mv.setViewName("vij/good/product_cart");
		}
		return mv;
	}
	/**
	 * 免费报价
	 * @param request
	 * @param response
	 * @param mv
	 * @return
	 */
	@RequestMapping("/freePrice")
	public ModelAndView freePrice(HttpServletRequest request,HttpServletResponse response,ModelAndView mv){
		mv.setViewName("vij/free_price");
		return mv;
	}
	/**
	 * 家居设计
	 * @param request
	 * @param response
	 * @param mv
	 * @return
	 */
	@RequestMapping("/homeSetup")
	public ModelAndView homeSetup(HttpServletRequest request,HttpServletResponse response,ModelAndView mv){
		mv.setViewName("vij/home_setup");
		return mv;
	}
	
}
