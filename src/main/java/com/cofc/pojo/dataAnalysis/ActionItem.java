package com.cofc.pojo.dataAnalysis;

public class ActionItem {
	private int id;
	private int appId;//不同的应用可能行为表达意思不一样
	private String name;//翻译出来的行为名称
	private String source;//函数名称
	private int type;//具体类型
	private String params;//参数集合，有点需要参数才能知道行为目标

	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public int getAppId() {
		return appId;
	}



	public void setAppId(int appId) {
		this.appId = appId;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getSource() {
		return source;
	}



	public void setSource(String source) {
		this.source = source;
	}



	public int getType() {
		return type;
	}



	public void setType(int type) {
		this.type = type;
	}



	public String getParams() {
		return params;
	}



	public void setParams(String params) {
		this.params = params;
	}



	public static void main(String[] args) {
	}

}
