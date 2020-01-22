package com.cofc.pojo.vij;

public class GoodColl {
	private Integer goodCollId;
	private Integer goodId;//关联商品Id
	private Integer collId;//搭配id
	private Integer orderStatus;//排序
	
	//关联商品表
	private String goodsName;//商品名称
	
	private String sellPrice; //售价
	
	private String goodsImage;//商品图片
	
	//关联类型
	private String typeName;
	
	
	public Integer getGoodCollId() {
		return goodCollId;
	}
	public void setGoodCollId(Integer goodCollId) {
		this.goodCollId = goodCollId;
	}
	public Integer getGoodId() {
		return goodId;
	}
	public void setGoodId(Integer goodId) {
		this.goodId = goodId;
	}
	public Integer getCollId() {
		return collId;
	}
	public void setCollId(Integer collId) {
		this.collId = collId;
	}
	public Integer getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public String getSellPrice() {
		return sellPrice;
	}
	public void setSellPrice(String sellPrice) {
		this.sellPrice = sellPrice;
	}
	public String getGoodsImage() {
		return goodsImage;
	}
	public void setGoodsImage(String goodsImage) {
		this.goodsImage = goodsImage;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
}
