package com.cofc.controller.recommend;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cofc.pojo.ApplicationCommon;
import com.cofc.pojo.DescoveryCommon;
import com.cofc.pojo.DescoveryRecommend;
import com.cofc.pojo.UserOrder;
import com.cofc.pojo.UserOrderException;
import com.cofc.service.ApplicationCommonService;
import com.cofc.service.ApplicationContextService;
import com.cofc.service.CommonService;
import com.cofc.service.DescoveryCommonService;
import com.cofc.service.DescoveryRecommendService;
import com.cofc.service.RecommendService;
import com.cofc.service.SystemSettingsService;
import com.cofc.service.TestService;
import com.cofc.service.UserOrderService;
import com.cofc.service.RecommendService.MarkResult;
import com.cofc.util.BaseUtil;
import com.cofc.util.JsonUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/*
 * 参照WXPublishDescoveryController
 */

@Controller
@RequestMapping("/wx/recommend")
public class WXRecommendController extends BaseUtil{

	public static Map<Integer, Integer> displayCount = new HashMap<Integer, Integer>();
	
	@Autowired
	private ApplicationContextService applicationContextService;
	
	@Autowired
	private TestService testService;
	
	@Autowired
	private RecommendService recommendService;
	
	@Autowired
	private SystemSettingsService systemSettingsService;
	
	@Autowired
	private DescoveryRecommendService descoveryRecommendService;
	
	@Autowired
	private DescoveryCommonService descoveryCommonService;
	
	@Autowired
	private ApplicationCommonService applicationCommonService;
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private UserOrderService userOrderService;
	
	@RequestMapping("/markItemList")
	public void markItemList(HttpServletResponse response) {
		List list = new ArrayList();
		for(MarkItem item : recommendService.getMarkItemList()){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("name", item.getName());
			map.put("weight", item.getWeight());
			list.add(map);
		}
		output(response, JsonUtil.buildJson(list)); 
	}
	
	@RequestMapping("/recommendDescoveryList")
	public void recommendDescoveryList(HttpServletResponse response, Integer applicationID, Integer pageNo, Integer pageSize) {
		if (pageNo == null) {
			pageNo = 1;
		}
		if (pageSize == null) {
			pageSize = 10;
		}
		List<DescoveryCommon> dcList = descoveryCommonService.findRecommendDecoveryByApplication2(applicationID, (pageNo - 1) * pageSize, pageSize);
		
		for(DescoveryCommon descovery : dcList){
			int id = descovery.getDescoveryId();
			Integer count = displayCount.get(id);
			
			if(count==null){
				displayCount.put(id, descovery.getReadCount());
			}else{
				displayCount.put(id, count + 1);
			}
		}
		output(response, JsonUtil.buildJson(dcList));
	}
	
	@RequestMapping("/test")
	public void showPublishedProjectList(HttpServletResponse response) {
		
		System.out.println("recommend_test");
		//output(response, JsonUtil.buildJson(dcList));
		Map map = applicationContextService.test();
		//output(response, "ok");
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
		Set<String> set = map.keySet();
		StringBuffer buffer = new StringBuffer();
		for(String key : set){
			buffer.append(key + "\n");
		}
		//output(response, buffer.toString());
		
		
/*		List list = testService.getAll(2);
		output(response, JsonUtil.buildJson(list));  */
		
		//MarkResult result = recommendService.mark(34, 34);
		//output(response, "评份：" + result.mark); 
	}
	
	
	@RequestMapping("/test2")
	public void test2(HttpServletResponse response) {
		systemSettingsService.put("123", 222, "333");
	}
	
	@RequestMapping("/test3")
	public void test3(HttpServletResponse response) {
		DescoveryRecommend descoveryRecommend = new DescoveryRecommend();
		descoveryRecommend.setId("id5566");
		descoveryRecommend.setDescoveryID(123);
		descoveryRecommend.setApplicationID(456);
		descoveryRecommend.setMark(100);
		descoveryRecommend.setDesc("ok");
		descoveryRecommendService.addDescoveryRecommend(descoveryRecommend);
	}
	
	@RequestMapping("/test4")
	public void test4(HttpServletResponse response) {
		List list = descoveryRecommendService.findAllDescoveryID();
		System.out.println(list.size());
		//recommendService.updateDescoveryRecommend(9, 4);
	}
	
	@RequestMapping("/test5")
	public synchronized void test5(HttpServletResponse response) {
		//List list = descoveryRecommendService.findAllDescoveryID();
		//System.out.println(list.size());
		//recommendService.updateDescoveryRecommend(9, 4);
		//WXRecommendController dd = new WXRecommendController();
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		output(response, this.toString()); 
	}
	
	@RequestMapping("/test6")
	public void test6(HttpServletResponse response) {
		//List<Integer> list = descoveryRecommendService.findAllDescoveryID();
		//output(response, this.toString()); 
		recommendService.updateAllDescoveryRecommend();
	}
	
	@RequestMapping("/test7")
	public void test7(HttpServletResponse response) {
		output(response, "test7"); 
	}
	
	@RequestMapping("/test8")
	public void test8(HttpServletResponse response) {
		DescoveryCommon descovery = descoveryCommonService.getDescoveryById(54);
		ApplicationCommon application = applicationCommonService.getApplicationById(1);
		recommendService.updateDescoveryRecommend(descovery, application);
		output(response, "test8"); 
	}
	
	@RequestMapping("/test9")
	public void test9(HttpServletResponse response) {
		//List<Map> dcList = commonService.findAllTagMap();
		//List<UserOrder> dcList = getObjectNumberInOrder.findAllUserOrder();
		//int count = getObjectNumberInOrder.getObjectNumberInOrder(1, 1);
		//output(response, JsonUtil.buildJson(dcList)); 
		UserOrderException exception = new UserOrderException();
		exception.setType(2);
		exception.setContent("123测试");
		exception.setOrderID("1");
		exception.setUserid(1);
		exception.setCreateTime(new Date());
		userOrderService.log(exception);
		output(response, "ok"); 
	}
	
	@RequestMapping("/test10")
	public void test10(HttpServletResponse response) {
		List<UserOrder> dcList = userOrderService.findMyOrder(40, 0, 5, 0, 5);
		output(response, JsonUtil.buildJson(dcList)); 
	}
}
