package com.cofc.controller.recommend;

/**
 * 推荐评分项目
 * @author Administrator
 *
 */
public abstract class MarkItem {

	protected int weight = 10;
	
	public String getName(){
		return "未命名评分项";
	}
	
	public int getWeight(){
		return weight;
	}
	
	public void setWeight(int weight){
		this.weight = weight;
	}
	
	public int mark(MarkContext context){
		return 0;
	}
	
}
