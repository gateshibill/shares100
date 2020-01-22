package com.cofc.controller.weiaijia;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cofc.pojo.DescoveryCommon;
import com.cofc.pojo.DescoveryType;
import com.cofc.service.DescoveryCommonService;
import com.cofc.service.DescoveryTypeService;
import com.cofc.util.BaseUtil;
import com.cofc.util.JsonUtil;
/**
 * 2018-12-13
 * app:发现分类
 * @author menghaoran
 *
 */
@Controller
@RequestMapping("/vij/app/descovery")
public class VijDescoveryController extends BaseUtil{
	@Resource
	private DescoveryCommonService descoveryService;
	@Resource
	private DescoveryTypeService descoveryTypeService;
	public static Logger log = Logger.getLogger("VijDescoveryController");
	/**
	 * 唯爱分类接口
	 * @param response
	 * @param loginPlat
	 */
	@RequestMapping("/getDescoveryType")
	public void getDescoveryType(HttpServletResponse response,Integer loginPlat){
		if(loginPlat == null){
			output(response,JsonUtil.buildFalseJson("1", "传递参数非法"));
		}else{
			List<DescoveryType> types = descoveryTypeService.getListByAppId(loginPlat);
			output(response,JsonUtil.buildJson(types));
		}
	}
	//前端发布唯爱秀家
	@RequestMapping("/publishDescovery")
	public void publishDescovery(HttpServletResponse response,DescoveryCommon des){
		if(des.getLoginPlat() == null){
			log.error("publishDescovery():loginPlat is null");
			output(response,JsonUtil.buildFalseJson("1","非法应用"));	
		}else{
			if(des.getPublisherId() == null){
				log.error("publishDescovery():publisherId is null");
				output(response,JsonUtil.buildFalseJson("1", "非法用户"));
			}else{
				if(des.getDescoveryTitle() == null || des.getDescoveryTitle().equals("")){
					log.error("publishDescovery():DescoveryTitle is null");
					output(response,JsonUtil.buildFalseJson("1", "请填写内容"));
				}else{
					des.setPariseCount(0);
					des.setReadCount(0);
					des.setJoinedCount(0);
					des.setIsPass(1);
					des.setIsShangJia(1);
					des.setCollectCount(0);
					des.setPublishTime(new Date());
					descoveryService.addNewDesCommon(des);
					output(response,JsonUtil.buildFalseJson("0","发布秀家成功"));
				}
			}
		}
	}
}
