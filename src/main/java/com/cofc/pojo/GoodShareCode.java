package com.cofc.pojo;

import java.util.Date;

/**
 * 商品二维码表
 * @author 46678
 *
 */
public class GoodShareCode {
	private Integer codeId;
	private Integer goodId;
	private Integer userId;
	private Integer appId;
	private String goodCodeUrl;
	private Date createTime;
	private Integer salerId;
	public Integer getCodeId() {
		return codeId;
	}
	public void setCodeId(Integer codeId) {
		this.codeId = codeId;
	}
	public Integer getGoodId() {
		return goodId;
	}
	public void setGoodId(Integer goodId) {
		this.goodId = goodId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getAppId() {
		return appId;
	}
	public void setAppId(Integer appId) {
		this.appId = appId;
	}
	public String getGoodCodeUrl() {
		return goodCodeUrl;
	}
	public void setGoodCodeUrl(String goodCodeUrl) {
		this.goodCodeUrl = goodCodeUrl;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Integer getSalerId() {
		return salerId;
	}
	public void setSalerId(Integer salerId) {
		this.salerId = salerId;
	}
}
