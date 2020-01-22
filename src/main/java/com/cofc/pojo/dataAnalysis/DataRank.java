package com.cofc.pojo.dataAnalysis;

//用于计算AI能力值
public class DataRank {
	private int id;
	private int appId;
	private int userId;
	private int times;// 分析项
	private int rank;

	/*
	 * 1.查出一项的DR值；计算出一项的值；
	 * 2.然后查出第二项；直到结束；
	 * 3.每天晚上4点计算一点；
	 * 4.计算放在定时分析类里面进行
	 */
	
	
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

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}


	public int getTimes() {
		return times;
	}

	public void setTimes(int times) {
		this.times = times;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public static void main(String[] args) {
		System.out.println((int) (50 + 50 / Math.pow(1, 1.0/8)));
		System.out.println((int) (50 + 50 / Math.pow(2, 1.0/8)));
		System.out.println((int) (50 + 50 / Math.pow(3, 1.0/8)));
		System.out.println((int) (50 + 50 / Math.pow(10, 1.0/8)));
		System.out.println((int) (50 + 50 / Math.pow(20, 1.0/8)));
		System.out.println((int) (50 + 50 / Math.pow(100, 1.0/8)));
	}

}
