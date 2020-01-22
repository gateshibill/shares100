


package com.cofc.controller.shopping;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cofc.pojo.GoodsCommon;
import com.cofc.pojo.UserShoppingCar;
import com.cofc.pojo.goods.GoodsSpec;
import com.cofc.service.GoodsCommonService;
import com.cofc.service.UserShoppingCarService;
import com.cofc.service.goods.GoodsSpecService;
import com.cofc.util.BaseUtil;
import com.cofc.util.JsonUtil;
/**
 * 购物车接口
 * @author chen
 *
 */
@Controller
@RequestMapping("/wx/shoppingCar")
public class WXShoppingCarController extends BaseUtil {
	@Resource
	private UserShoppingCarService uscService;
	@Resource
    private GoodsCommonService goodsCommonService;
	@Resource
	private GoodsSpecService specService; //多规格
	public static Logger log = Logger.getLogger("WXShoppingCarController");

	@RequestMapping("/addGoodsToCar")
	public synchronized void addGoodsToShoppingCar(HttpServletResponse response, 
			Integer goodsId, Integer userId, Integer number,Integer specId) {
		
		if(number==null||number<=0){
			output(response, JsonUtil.buildFalseJson("1", "商品数量错误!"));
			return;
		}
		UserShoppingCar car = null;
		if(specId == null){
			car = uscService.findShoppingCar(userId, goodsId, 0);
		}else{
			car = uscService.findSpecShoppingCar(userId, goodsId, specId, 0);
		}
		if(car != null){
			GoodsCommon goods = goodsCommonService.getGoodsById(goodsId);
			int mecount = car.getNumber()+number;
			if(mecount > goods.getGoodsStock()){
				output(response, JsonUtil.buildFalseJson("1", "库存不足"));
				return;
			}
		}
		
		try {			
			if(car==null){
				UserShoppingCar usc = new UserShoppingCar();
				usc.setCreateTime(new Date());
				usc.setGoodsId(goodsId);
				usc.setNumber(number);
				usc.setUserId(userId);
				usc.setSpecId(specId);
				uscService.addGoodsInCar(usc);
			}else{
				int nb = car.getNumber() + number;
				int carID = car.getCarId();
				uscService.updateNumberByID(carID, nb);
			}
			output(response, JsonUtil.buildFalseJson("0", "添加成功!"));
		} catch (Exception e) {
			output(response, JsonUtil.buildFalseJson("1", "添加失败!"));
			log.info("商品" + goodsId + "添加到" + userId + "的购物车失败，失败原因" + e);
		}
	}
	@RequestMapping("/myShoppingCar")
	public void showGoodsInMyCar(HttpServletResponse response,UserShoppingCar usc,Integer pageNo,Integer pageSize){
		if(pageNo == null){
			pageNo = 1;
		}
		if(pageSize == null){
			pageSize = 10;
		}
		List<UserShoppingCar> uscList =uscService.findHisShoppingCar(usc.getUserId(),(pageNo-1)*pageSize,pageSize);
		for (UserShoppingCar car : uscList) {  //返回多规格字段
			if(car.getSpecId() != null){
				GoodsSpec spec = specService.getGoodsSpecById(car.getSpecId());
				car.setGoodsSpec(spec);
			}
		}
		output(response, JsonUtil.buildJson(uscList));
	}
	
	@RequestMapping("/removeGoods")
	public void removeGoods(HttpServletResponse response, Integer carId){
		if(carId==null||carId<=0){
			output(response, JsonUtil.buildFalseJson("1", "carId非法!"));
			return;
		}
		try{
			uscService.deleteGoodsInCar(carId, new Date());
			output(response, JsonUtil.buildFalseJson("0", "处理成功!"));
		}catch(Exception e){
			output(response, JsonUtil.buildFalseJson("1", "处理失败!"));
			e.printStackTrace();
		}
	}
	/**
	 * 批量删除购物车
	 * @param response
	 * @param ids
	 */
	@RequestMapping("/removeAllGoods")
	public void removeAllGoods(HttpServletResponse response,String ids){
		if(ids.equals("") || ids == null){
			output(response,JsonUtil.buildFalseJson("1","请选择要移除的商品"));
		}else{
			try {
		        List<String> lis = Arrays.asList(ids.split(","));
				uscService.deleteAllGoodsInCar(lis,new Date());
				output(response, JsonUtil.buildFalseJson("0", "处理成功!"));
			} catch (Exception e) {
				log.info("移除购物车失败，失败原因："+e.getMessage());
				output(response,JsonUtil.buildFalseJson("1","删除购物车失败"));
			}
		}
	}
	@RequestMapping("/updateShopCarNumber")
	public void updateShopCarNumber(HttpServletResponse response, Integer carId, Integer number){
		if(carId==null||carId<=0){
			output(response, JsonUtil.buildFalseJson("1", "carId非法!"));
			return;
		}
		if(number==null||number<0){
			output(response, JsonUtil.buildFalseJson("2", "商品数量number非法!"));
			return;
		}
		UserShoppingCar car = uscService.getCarDetail(carId, 0);
		if(car != null){
			GoodsCommon goods =goodsCommonService.getGoodsById(car.getGoodsId());
			if(number > goods.getGoodsStock()){
				output(response, JsonUtil.buildFalseJson("1", "库存不足"));
				return;
			}
		}
		
		try{
			if(number == 0){//执行移除该条数据操作
				uscService.deleteGoodsInCar(carId, new Date());
				output(response,JsonUtil.buildFalseJson("0","删除成功"));
			}else{
				uscService.updateNumberByID(carId, number);
				output(response, JsonUtil.buildFalseJson("0", "处理成功!"));
			}		
		}catch(Exception e){
			output(response, JsonUtil.buildFalseJson("1", "处理失败!"));
			e.printStackTrace();
		}
	}	
}
