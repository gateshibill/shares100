package com.cofc.controller.recommend;

import org.springframework.stereotype.Service;


@Service
public class TopMarkItem extends MarkItem{
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "置顶特权";
	}

	@Override
	public int mark(MarkContext context) {
		Integer top = context.descoveryObject.getIsTop();
		if(top!=null&&top==1){
			return this.weight;  //置顶
		}else{
			return 0;
		}
	}
}
