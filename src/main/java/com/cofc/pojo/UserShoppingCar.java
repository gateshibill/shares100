package com.cofc.pojo;

import java.util.Date;

import com.cofc.pojo.goods.GoodsSpec;

public class UserShoppingCar {
	private Integer carId;
	private Integer userId;
	private Integer goodsId;
	private Integer number;
	private Integer status;
	private Date createTime;
	private Date transferTime;
	private Date removeTime;
	private Integer specId; //多规格id
	
	private GoodsSpec goodsSpec; //多规格
	
	private GoodsCommon carGoods;
	private UserCommon user;
	
	public Integer getCarId() {
		return carId;
	}
	public void setCarId(Integer carId) {
		this.carId = carId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(Integer goodsId) {
		this.goodsId = goodsId;
	}
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getTransferTime() {
		return transferTime;
	}
	public void setTransferTime(Date transferTime) {
		this.transferTime = transferTime;
	}
	public Date getRemoveTime() {
		return removeTime;
	}
	public void setRemoveTime(Date removeTime) {
		this.removeTime = removeTime;
	}
	public GoodsCommon getCarGoods() {
		return carGoods;
	}
	public void setCarGoods(GoodsCommon carGoods) {
		this.carGoods = carGoods;
	}
	@Override
	public String toString() {
		return "UserShoppingCar [carId=" + carId + ", userId=" + userId + ", goodsId=" + goodsId + ", createTime="
				+ createTime + ", number=" + number + ", carGoods=" + carGoods + "]";
	}
	public UserCommon getUser() {
		return user;
	}
	public void setUser(UserCommon user) {
		this.user = user;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getSpecId() {
		return specId;
	}
	public void setSpecId(Integer specId) {
		this.specId = specId;
	}
	public GoodsSpec getGoodsSpec() {
		return goodsSpec;
	}
	public void setGoodsSpec(GoodsSpec goodsSpec) {
		this.goodsSpec = goodsSpec;
	}
}
