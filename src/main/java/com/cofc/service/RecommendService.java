package com.cofc.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import com.cofc.controller.recommend.MarkContext;
import com.cofc.controller.recommend.MarkItem;
import com.cofc.controller.recommend.ReadCountMarkItem;
import com.cofc.pojo.ApplicationCommon;
import com.cofc.pojo.DescoveryCommon;
import com.cofc.pojo.DescoveryRecommend;


@Service
public class RecommendService implements ApplicationContextAware{
	
	//public Map<String, MarkItem> markItemMap;
	private List<MarkItem> markItemList;
	private List<String> rs;
	private static int minRecommendValue;
	
	@Autowired
	private ApplicationCommonService applicationCommonService;
	@Autowired
	private DescoveryCommonService descoveryCommonService;
	@Autowired
	private SystemSettingsService systemSettingsService;
	@Autowired
	private DescoveryRecommendService descoveryRecommendService;
	
	private static Comparator markResultComparator = new Comparator<MarkResult>() {
		@Override
		public int compare(MarkResult o1, MarkResult o2) {
			return o2.mark - o1.mark;  //逆序
		}
	};
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		Map<String, MarkItem> map = applicationContext.getBeansOfType(MarkItem.class);
		map.remove("markItem");  //移除父类
		markItemList = new ArrayList<MarkItem>();
		Set<String> set = map.keySet();
		if(set!=null){
			for(String key : set){
				MarkItem item = map.get(key);
				markItemList.add(item);
				String storageKey = key + "_weight";
				if(systemSettingsService.containsKey(storageKey)){
					int weight = (int) systemSettingsService.get(storageKey);
					item.setWeight(weight);
				}else{
					String desc = item.getName() + "-权重";
					systemSettingsService.put(storageKey, item.getWeight(), desc);
				}
			}
		}
	}
	
	public MarkResult mark(MarkContext context){
		systemSettingsService.get("dfd");
		int mark = 0;
		List<MarkResult> markResultList = new ArrayList<MarkResult>();
		for(MarkItem markItem : markItemList){
			
			MarkResult markResult = new MarkResult();
			markResult.mark = markItem.mark(context);
			if(markResult.mark>0){
				markResult.desc = markResult.mark + "分来自" + markItem.getName();  //拼接例子：70%来自标签关联
				markResultList.add(markResult);
			}
			mark = mark + markResult.mark;  //算总分
		}
		markResultList.sort(markResultComparator);
		
		MarkResult ret = new MarkResult();
		ret.mark = mark;
		ret.desc = StringUtils.join(markResultList, ",");
		return ret;
	}
	
	public List<MarkItem> getMarkItemList(){
		return markItemList;
	}
	
/*	
	public void updateDescoveryRecommend(int descoveryID, int applicationID){
		DescoveryRecommend descoveryRecommend = new DescoveryRecommend();
		descoveryRecommend.setId(descoveryID + "_" + applicationID);
		descoveryRecommend.setDescoveryID(descoveryID);
		descoveryRecommend.setApplicationID(applicationID);
		MarkContext context = new MarkContext();
		context.descoveryObject = descoveryCommonService.getDescoveryById(descoveryID);
		context.appObject = applicationCommonService.getApplicationById(applicationID);
		descoveryRecommend.setDescoveryType(context.descoveryObject.getDescoveryType());
		MarkResult markResult = mark(context);
		descoveryRecommend.setMark(markResult.mark);
		descoveryRecommend.setDesc(markResult.desc);
		if(rs.contains(descoveryID + "_" + applicationID)){
			descoveryRecommendService.updateDescoveryRecommend(descoveryRecommend);
		}else{
			if(descoveryRecommend.getMark()>minRecommendValue){
				descoveryRecommendService.addDescoveryRecommend(descoveryRecommend);
			}
		}
	}
*/
	public void updateDescoveryRecommend(DescoveryCommon descovery, ApplicationCommon application){
		String Recommend = descovery.getDescoveryId() + "_" + application.getApplicationId();
		DescoveryRecommend descoveryRecommend = new DescoveryRecommend();
		descoveryRecommend.setId(Recommend);
		descoveryRecommend.setDescoveryID(descovery.getDescoveryId());
		descoveryRecommend.setApplicationID(application.getApplicationId());
		MarkContext context = new MarkContext();
		context.descoveryObject = descovery;
		context.appObject = application;
		descoveryRecommend.setDescoveryType(context.descoveryObject.getDescoveryType());
		MarkResult markResult = mark(context);
		descoveryRecommend.setMark(markResult.mark);
		descoveryRecommend.setDesc(markResult.desc);
		if(rs==null){
			rs = descoveryRecommendService.findAllDescoveryRecommend();
		}
		if(rs.contains(Recommend)){
			descoveryRecommendService.updateDescoveryRecommend(descoveryRecommend);
		}else{
			if(descoveryRecommend.getMark()>minRecommendValue){
				descoveryRecommendService.addDescoveryRecommend(descoveryRecommend);
			}
		}
	}
	
	public void updateAllDescoveryRecommend(){
		minRecommendValue = getMinRecommendValue();
		rs = descoveryRecommendService.findAllDescoveryRecommend();
		ReadCountMarkItem.calculate();
/*		
		List<Integer> ds = descoveryRecommendService.findAllDescoveryID();
		List<Integer> as = descoveryRecommendService.findAllApplicationID();
		for(int descoveryID : ds){
			for(int applicationID : as){
				updateDescoveryRecommend(descoveryID, applicationID);
			}
		}
*/
		int pageSize = 30;
		int pageIndex = 0;
		List<DescoveryCommon> ls;
		List<ApplicationCommon> ts;
		for(int i=0;i<Integer.MAX_VALUE;i++){
			ls = descoveryCommonService.findDecovery(i*pageSize, pageSize);
			if(ls.size()==0){
				break;
			}
			for(int j=0;j<Integer.MAX_VALUE;j++){
				ts = applicationCommonService.findApplication(j*pageSize, pageSize);
				if(ts.size()==0){
					break;
				}
				for(DescoveryCommon l : ls){
					for(ApplicationCommon t : ts){
						updateDescoveryRecommend(l, t);
					}
				}
			}
		}
	}
	
	public void updateDescoveryRecommendByDescoveryID(DescoveryCommon descovery){
		int pageSize = 30;
		int pageIndex = 0;
		List<ApplicationCommon> ts;
		for(int i=0;i<Integer.MAX_VALUE;i++){
			ts = applicationCommonService.findApplication(i*pageSize, pageSize);
			if(ts.size()==0){
				break;
			}
			for(ApplicationCommon t : ts){
				updateDescoveryRecommend(descovery, t);
			}
		}
	}
	
	public static class MarkResult{
		public int mark;
		public String desc;
		@Override
		public String toString(){
			return desc;
		}
	}
	
	private int getMinRecommendValue(){
		Integer min = (Integer) systemSettingsService.get("minRecommendValue");
		if(min==null){
			min = 10;
			systemSettingsService.put("minRecommendValue", min, "最小推荐值，低于它就不推荐了。");
		}
		return min;
	}
}
