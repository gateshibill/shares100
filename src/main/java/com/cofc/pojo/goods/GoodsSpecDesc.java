package com.cofc.pojo.goods;

import java.util.List;

public class GoodsSpecDesc {
	private int goodsId;
	private String name;
	private String goodsUrl;
	private double minPrice=0;
	private double maxPrice=0;
	private List<Specification> specList;
	private List<GoodsSpec> goodsSpecList;

	public int getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(int goodsId) {
		this.goodsId = goodsId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGoodsUrl() {
		return goodsUrl;
	}

	public void setGoodsUrl(String goodsUrl) {
		this.goodsUrl = goodsUrl;
	}



	public double getMinPrice() {
		return minPrice;
	}

	public void setMinPrice(double minPrice) {
		this.minPrice = minPrice;
	}

	public double getMaxPrice() {
		return maxPrice;
	}

	public void setMaxPrice(double maxPrice) {
		this.maxPrice = maxPrice;
	}

	public List<Specification> getSpecList() {
		return specList;
	}

	public void setSpecList(List<Specification> specList) {
		this.specList = specList;
	}

	public List<GoodsSpec> getGoodsSpecList() {
		return goodsSpecList;
	}

	public void setGoodsSpecList(List<GoodsSpec> goodsSpecList) {
		this.goodsSpecList = goodsSpecList;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
