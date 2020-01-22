package com.cofc.controller.recommend;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cofc.service.CommonService;


@Service
public class TagMarkItem extends MarkItem{

	@Autowired
	private CommonService commonService;
	
	private static Map<String, String> mMap;
	private static Map<String, String> groupTypes;
	
	@Override
	public String getName() {
		return "标签关联";
	}

	public int mark(MarkContext context){
		if(context.appObject==null||context.descoveryObject==null){
			return 0;
		}
		if(mMap==null){
			mMap = new HashMap<String, String>();
			List<Map> list = commonService.findAllTagMap();
			for(Map map : list){
				mMap.put(map.get("tag_name").toString(), map.get("group_type_name").toString());
			}
		}
		if(groupTypes==null){
			groupTypes = new HashMap<String, String>();
			List<Map> list = commonService.findAllGroupTypes();
			for(Map map : list){
				groupTypes.put(map.get("type_id").toString(), map.get("type_name").toString());
			}
		}
		String ts = context.descoveryObject.getInquiryTags();
		if(ts==null){
			return 0;
		}
		String[] tss = StringUtils.split(ts, ",");
		for(String tagID : tss){
			String tagName = groupTypes.get(tagID);
			if(tagName!=null){
				String groupName = mMap.get(tagName);  //标签到分类的映射，tb_tag_map
				String temp = context.appObject.getGroupType()==null?"":context.appObject.getGroupType().toString();
				String groupName2 = groupTypes.get(temp);
				if(StringUtils.equalsIgnoreCase(groupName, groupName2)){
					return weight;
				}
			}
		}
		return 0;
	}
}
