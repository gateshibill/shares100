package com.cofc.pojo;


import java.util.Date;
import java.util.List;

import com.cofc.pojo.goods.GoodsSpec;
import com.cofc.pojo.goods.GoodsSpecType;

public class GoodsCommon {
	
	private Integer goodsId;
	private String goodsName;
	private Integer isHot;
	private Integer isCut; //是否砍
	private Integer isPin; //是否
	private Integer isDtbt;//是否秒杀
	private Double firstCost;
	private Integer goodsStock;
	private Date createTime;
	private Integer publisherId;
	private String publisherName;//后台发布者名称
	private String sellPrice;
	private Integer isSelled;
	private String goodsDetails;
	private String goodsImage;
	private Integer goodsType;
	private Date updateTime;
	private Integer selledCount;
	private Integer loginPlat;
	private Integer sellOrRent;
	private Integer isPassSell;
	private String smallImage;
	private String detailsImage;
	private Integer recommendWay;
	private Integer goodsStatus;
	private Integer isRecommend;
	private Integer goodsBrowse;
	private Double vipPrice;
	private Double goodsRebate;//商品返点
	private Integer isCancel;
	private Integer isScore; //是否允许积分兑换
	private Integer scoreCount;//所需积分
	private Integer parentId;//父级应用id
	private Integer isMoreSpec;//是否多规格：0：否，1：是
	private Integer goodSort; //商品排序
	private String goodOneImage;//商品图片
	private Integer isNew;//是否新品
	private Integer goodChildType; //二级分类
	private Integer msTime;//秒杀有效时间
	private Long seckillStartTime;//保存秒杀时间戳
	private Integer specId; //多规格id
	private String specTypeString;//多规格字符串
	private String goodRecommendPic;//商品推荐图片
	private Integer brandId;//商品品牌id
	
	//评价数量
	private int haoCount;
	private int zhongCount;
	private int chaCount;
	
	private UserCommon goodsUser;
	private GoodsType gType;
	private RecommendCommon recommend;
	private ApplicationCommon app;
	private Integer buyNumber;  //这个字段，只作为生成order的一个临时中转属性，数据库中不必有对应字段
	private Integer number; 
	private Integer carId;
	private GoodsCommon carGoods;
	
	private List<GoodsSpec> specList; //属性具体
	
	private List<GoodsSpecType> spectypeList;//属性
	
	public Integer getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(Integer goodsId) {
		this.goodsId = goodsId;
	}

	public Integer getIsCut() {
		return isCut;
	}

	public void setIsCut(Integer isCut) {
		this.isCut = isCut;
	}

	public Integer getIsPin() {
		return isPin;
	}

	public void setIsPin(Integer isPin) {
		this.isPin = isPin;
	}

	public Integer getIsDtbt() {
		return isDtbt;
	}

	public void setIsDtbt(Integer isDtbt) {
		this.isDtbt = isDtbt;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public Integer getIsHot() {
		return isHot;
	}

	public void setIsHot(Integer isHot) {
		this.isHot = isHot;
	}

	public Double getFirstCost() {
		return firstCost;
	}

	public void setFirstCost(Double firstCost) {
		this.firstCost = firstCost;
	}

	public Integer getGoodsStock() {
		return goodsStock;
	}

	public void setGoodsStock(Integer goodsStock) {
		this.goodsStock = goodsStock;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getPublisherId() {
		return publisherId;
	}

	public void setPublisherId(Integer publisherId) {
		this.publisherId = publisherId;
	}

//	public double getSellPrice() {
//		return sellPrice;
//	}
//
//	public void setSellPrice(double sellPrice) {
//		this.sellPrice = sellPrice;
//	}

	public Integer getIsSelled() {
		return isSelled;
	}

	public String getSellPrice() {
		return sellPrice;
	}

	public void setSellPrice(String sellPrice) {
		this.sellPrice = sellPrice;
	}

	public void setIsSelled(Integer isSelled) {
		this.isSelled = isSelled;
	}

	public String getGoodsDetails() {
		return goodsDetails;
	}

	public void setGoodsDetails(String goodsDetails) {
		this.goodsDetails = goodsDetails;
	}

	public String getGoodsImage() {
		return goodsImage;
	}

	public void setGoodsImage(String goodsImage) {
		this.goodsImage = goodsImage;
	}

	public UserCommon getGoodsUser() {
		return goodsUser;
	}

	public void setGoodsUser(UserCommon goodsUser) {
		this.goodsUser = goodsUser;
	}

	public Integer getLoginPlat() {
		return loginPlat;
	}

	public void setLoginPlat(Integer loginPlat) {
		this.loginPlat = loginPlat;
	}

	public Integer getSellOrRent() {
		return sellOrRent;
	}

	public void setSellOrRent(Integer sellOrRent) {
		this.sellOrRent = sellOrRent;
	}

	public Integer getIsPassSell() {
		return isPassSell;
	}

	public void setIsPassSell(Integer isPassSell) {
		this.isPassSell = isPassSell;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getSelledCount() {
		return selledCount;
	}

	public void setSelledCount(Integer selledCount) {
		this.selledCount = selledCount;
	}

	public Integer getGoodsType() {
		return goodsType;
	}

	public void setGoodsType(Integer goodsType) {
		this.goodsType = goodsType;
	}

	public GoodsType getgType() {
		return gType;
	}

	public void setgType(GoodsType gType) {
		this.gType = gType;
	}

	public Integer getIsRecommend() {
		return isRecommend;
	}

	public void setIsRecommend(Integer isRecommend) {
		this.isRecommend = isRecommend;
	}

	public RecommendCommon getRecommend() {
		return recommend;
	}

	public void setRecommend(RecommendCommon recommend) {
		this.recommend = recommend;
	}

	public String getSmallImage() {
		return smallImage;
	}

	public void setSmallImage(String smallImage) {
		this.smallImage = smallImage;
	}

	public Integer getRecommendWay() {
		return recommendWay;
	}

	public void setRecommendWay(Integer recommendWay) {
		this.recommendWay = recommendWay;
	}

	public Integer getGoodsStatus() {
		return goodsStatus;
	}

	public void setGoodsStatus(Integer goodsStatus) {
		this.goodsStatus = goodsStatus;
	}

	public String getDetailsImage() {
		return detailsImage;
	}

	public void setDetailsImage(String detailsImage) {
		this.detailsImage = detailsImage;
	}

	public ApplicationCommon getApp() {
		return app;
	}

	public void setApp(ApplicationCommon app) {
		this.app = app;
	}

	public Integer getGoodsBrowse() {
		return goodsBrowse;
	}

	public void setGoodsBrowse(Integer goodsBrowse) {
		this.goodsBrowse = goodsBrowse;
	}

	public Double getVipPrice() {
		return vipPrice;
	}

	public void setVipPrice(Double vipPrice) {
		this.vipPrice = vipPrice;
	}

	public Double getGoodsRebate() {
		return goodsRebate;
	}

	public void setGoodsRebate(Double goodsRebate) {
		this.goodsRebate = goodsRebate;
	}

	public Integer getBuyNumber() {
		return buyNumber;
	}

	public void setBuyNumber(Integer buyNumber) {
		this.buyNumber = buyNumber;
	}

	public Integer getIsCancel() {
		return isCancel;
	}

	public void setIsCancel(Integer isCancel) {
		this.isCancel = isCancel;
	}

	
	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public Integer getCarId() {
		return carId;
	}

	public void setCarId(Integer carId) {
		this.carId = carId;
	}

	public GoodsCommon getCarGoods() {
		return carGoods;
	}

	public void setCarGoods(GoodsCommon carGoods) {
		this.carGoods = carGoods;
	}

	public Integer getIsScore() {
		return isScore;
	}

	public void setIsScore(Integer isScore) {
		this.isScore = isScore;
	}

	public Integer getScoreCount() {
		return scoreCount;
	}

	public void setScoreCount(Integer scoreCount) {
		this.scoreCount = scoreCount;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public Integer getIsMoreSpec() {
		return isMoreSpec;
	}

	public void setIsMoreSpec(Integer isMoreSpec) {
		this.isMoreSpec = isMoreSpec;
	}

	public List<GoodsSpec> getSpecList() {
		return specList;
	}

	public void setSpecList(List<GoodsSpec> specList) {
		this.specList = specList;
	}

	public List<GoodsSpecType> getSpectypeList() {
		return spectypeList;
	}

	public void setSpectypeList(List<GoodsSpecType> spectypeList) {
		this.spectypeList = spectypeList;
	}

	public Integer getGoodSort() {
		return goodSort;
	}

	public void setGoodSort(Integer goodSort) {
		this.goodSort = goodSort;
	}

	public String getPublisherName() {
		return publisherName;
	}

	public void setPublisherName(String publisherName) {
		this.publisherName = publisherName;
	}

	public String getGoodOneImage() {
		return goodOneImage;
	}

	public void setGoodOneImage(String goodOneImage) {
		this.goodOneImage = goodOneImage;
	}

	public Integer getIsNew() {
		return isNew;
	}

	public void setIsNew(Integer isNew) {
		this.isNew = isNew;
	}

	public Integer getGoodChildType() {
		return goodChildType;
	}

	public void setGoodChildType(Integer goodChildType) {
		this.goodChildType = goodChildType;
	}

	public Integer getMsTime() {
		return msTime;
	}

	public void setMsTime(Integer msTime) {
		this.msTime = msTime;
	}

	public Integer getSpecId() {
		return specId;
	}

	public void setSpecId(Integer specId) {
		this.specId = specId;
	}

	public String getSpecTypeString() {
		return specTypeString;
	}

	public void setSpecTypeString(String specTypeString) {
		this.specTypeString = specTypeString;
	}

	public Long getSeckillStartTime() {
		return seckillStartTime;
	}

	public void setSeckillStartTime(Long seckillStartTime) {
		this.seckillStartTime = seckillStartTime;
	}

	public String getGoodRecommendPic() {
		return goodRecommendPic;
	}

	public void setGoodRecommendPic(String goodRecommendPic) {
		this.goodRecommendPic = goodRecommendPic;
	}

	public Integer getBrandId() {
		return brandId;
	}

	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}

	public int getHaoCount() {
		return haoCount;
	}

	public void setHaoCount(int haoCount) {
		this.haoCount = haoCount;
	}

	public int getZhongCount() {
		return zhongCount;
	}

	public void setZhongCount(int zhongCount) {
		this.zhongCount = zhongCount;
	}

	public int getChaCount() {
		return chaCount;
	}

	public void setChaCount(int chaCount) {
		this.chaCount = chaCount;
	}
}
