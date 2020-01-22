package com.cofc.pojo.vij;

import java.util.Date;

/**
 * 
 * @author 46678
 * 广告
 *
 */
public class Adv {
	private Integer advId;
	private String advName;
	private String advUrl;
	private String advJumpUrl;
	private Integer advLocation;//具体位置(广告位置【1：首页顶部广告，2：首页轮播图，3:首页秒杀广告，4：首页新品广告】)
	private Integer source;//页面：1：首页  2：新品页面  3：搭配  4：顾问 5：我们
	private Integer isEffect;
	private Date createTime;
	private Integer advOrder;//排序
	private Integer appId;//应用id
	public Integer getAdvId() {
		return advId;
	}
	public void setAdvId(Integer advId) {
		this.advId = advId;
	}
	public String getAdvName() {
		return advName;
	}
	public void setAdvName(String advName) {
		this.advName = advName;
	}
	public String getAdvUrl() {
		return advUrl;
	}
	public void setAdvUrl(String advUrl) {
		this.advUrl = advUrl;
	}
	public String getAdvJumpUrl() {
		return advJumpUrl;
	}
	public void setAdvJumpUrl(String advJumpUrl) {
		this.advJumpUrl = advJumpUrl;
	}
	public Integer getAdvLocation() {
		return advLocation;
	}
	public void setAdvLocation(Integer advLocation) {
		this.advLocation = advLocation;
	}
	
	public Integer getSource() {
		return source;
	}
	public void setSource(Integer source) {
		this.source = source;
	}
	public Integer getIsEffect() {
		return isEffect;
	}
	public void setIsEffect(Integer isEffect) {
		this.isEffect = isEffect;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Integer getAdvOrder() {
		return advOrder;
	}
	public void setAdvOrder(Integer advOrder) {
		this.advOrder = advOrder;
	}
	public Integer getAppId() {
		return appId;
	}
	public void setAppId(Integer appId) {
		this.appId = appId;
	}
	@Override
	public String toString() {
		return "Adv [advId=" + advId + ", advName=" + advName + ", advUrl=" + advUrl + ", advJumpUrl=" + advJumpUrl
				+ ", advLocation=" + advLocation + ", source=" + source + ", isEffect=" + isEffect + ", createTime="
				+ createTime + ", advOrder=" + advOrder + ", appId=" + appId + "]";
	}

}
