package com.cofc.controller.weiaijia;

import java.util.Date;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cofc.pojo.vij.DecorateForecast;
import com.cofc.service.vij.DecorateForecastService;

/***
 * 
 * @author Administrator 算算我家装修多少钱
 */
@Controller
@RequestMapping("/vij/budget")
public class VijBudgetController {

	@Resource
	private DecorateForecastService vService;
	public static Logger logger = Logger.getLogger("VijBudgetController");
    
	/**
	 * pc端预约量房
	 * @param response
	 * @param cityCode
	 * @param buildingArea
	 * @param housingTypes
	 * @param houseDoorModel
	 * @param mobilePhoneNo
	 * @param decorateTime
	 * @param mv
	 * @return
	 */
	@RequestMapping("/budget")
	public ModelAndView budget(HttpServletRequest response, String cityCode,
			int buildingArea, String housingTypes, String houseDoorModel, String mobilePhoneNo,
			 Date decorateTime, ModelAndView mv) {

		DecorateForecast vijbudget = new DecorateForecast();
		vijbudget.setCityCode(cityCode);
		vijbudget.setBuildingArea(buildingArea);
		vijbudget.setHousingTypes(housingTypes);
		vijbudget.setHouseDoorModel(houseDoorModel);
		vijbudget.setMobilePhoneNo(mobilePhoneNo);
		vijbudget.setDecorateTime(decorateTime);
		
		vService.addVijbudget(vijbudget);
		double clmoney = getMoney(buildingArea, 1);
		double rgmoney = getMoney(buildingArea, 2);
		double sjmoney = getMoney(buildingArea, 3);
		double zjmoney = getMoney(buildingArea, 4);
		double grossPrice = clmoney + rgmoney + sjmoney + zjmoney;
		mv.addObject("clmoney", clmoney);
		mv.addObject("rgmoney", rgmoney);
		mv.addObject("sjmoney", sjmoney);
		mv.addObject("zjmoney", zjmoney);
		mv.addObject("grossPrice", grossPrice);
		mv.setViewName("vij/price_result");
		return mv;
	}
	
	public static Double getMoney(int mj,int type){
		double money = 0.0;
		if(type == 1){ //材料费
			money = mj * 500*1.5; 
		}else if(type == 2){//人工费
			money = mj/10*1000*1.5;
		}else if(type == 3){ //设计费
			money = mj*100*1.5;
		}else{ //质检费
			money = mj *35*1.8;
		}
		return money;
	}
	
}
