package com.cofc.controller.recommend;

import java.util.Collection;

import org.springframework.stereotype.Service;

import com.cofc.controller.descovery.WXPublishDescoveryController;


@Service
public class ReadCountMarkItem extends MarkItem{
	
	private static int max;
	private static int min;
	
	@Override
	public String getName() {
		return "阅读数反馈";
	}

	@Override
	public int mark(MarkContext context) {
		Integer descoveryId = context.descoveryObject.getDescoveryId();
		Integer read = WXRecommendController.displayCount.get(descoveryId);
		if(read==null){
			//read = context.descoveryObject.getReadCount();
			return this.weight;  //如果阅读量为0，就为新的。
		}
		int length = max-min;
		if(length==0){
			length=1;
		}
		if(context.descoveryObject.getReadCount()!=null&&context.descoveryObject.getReadCount()>0){
			read = (int)read/context.descoveryObject.getReadCount();
		}
		double result = (this.weight * (max-read))/length;
		if(result>this.weight){
			result = this.weight;
		}
		return (int)result;
	}
	
	public static void calculate(){
		max = 1;
		min = Integer.MAX_VALUE;
		Collection<Integer> ls = WXRecommendController.displayCount.values();
		for(int i : ls){
			if(i>max){
				max = i;
			}
			if(i<min){
				min = i;
			}
		}
	}
}
