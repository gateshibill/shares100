package com.cofc.controller.recommend;

import org.springframework.stereotype.Service;


@Service
public class KeyWordMarkItem extends MarkItem{

	@Override
	public String getName() {
		return "关键字匹配";
	}
	
	@Override
	public int mark(MarkContext context) {
		if(context.descoveryObject==null||context.descoveryObject.getDescoveryTitle()==null){
			return 0;
		}
		if(context.descoveryObject.getDescoveryTitle().indexOf(context.appObject.getApplicationName())>=0){
			return weight;
		}else{
			return 0;
		}
	}
}
