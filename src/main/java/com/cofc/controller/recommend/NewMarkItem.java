package com.cofc.controller.recommend;

import org.springframework.stereotype.Service;

@Service
public class NewMarkItem extends MarkItem{

	private static long time = 7*24*60*60*1000;  //7天的毫秒数
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "新上架特权";
	}

	@Override
	public int mark(MarkContext context) {
		if(context.descoveryObject.getPublishTime()!=null&&(System.currentTimeMillis() - context.descoveryObject.getPublishTime().getTime())<time){
			//7天内
			return weight;
		}else{
			return 0;
		}
	}

}
