package com.cofc.pojo.goods;

import java.util.Date;

/**
 * 记录产品佣金等级和价格
 * @author chenxiangyou
 *
 */
public class BrokerageGoods {
	private int id;
	private int appId;
	private int type;
	private float fristClass;
	private float fristClsssPrice;
	private float secondClass;
	private float secondClassPrice;
	private Date createTime;

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

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public float getFristClass() {
		return fristClass;
	}

	public void setFristClass(float fristClass) {
		this.fristClass = fristClass;
	}

	public float getFristClsssPrice() {
		return fristClsssPrice;
	}

	public void setFristClsssPrice(float fristClsssPrice) {
		this.fristClsssPrice = fristClsssPrice;
	}

	public float getSecondClass() {
		return secondClass;
	}

	public void setSecondClass(float secondClass) {
		this.secondClass = secondClass;
	}

	public float getSecondClassPrice() {
		return secondClassPrice;
	}

	public void setSecondClassPrice(float secondClassPrice) {
		this.secondClassPrice = secondClassPrice;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
