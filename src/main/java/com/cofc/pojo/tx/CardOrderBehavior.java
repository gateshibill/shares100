package com.cofc.pojo.tx;

import java.util.Date;
/**
 * 请求卡券行为
 * @author menghaoran
 *
 */
public class CardOrderBehavior {
	private Integer id;
	private String orderNum;
	private String agentOrderNum;
	private String agentCode;
	private Integer quantity;
	private String goodsCode;
	private Date createTime;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}
	public String getAgentCode() {
		return agentCode;
	}
	public void setAgentCode(String agentCode) {
		this.agentCode = agentCode;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public String getGoodsCode() {
		return goodsCode;
	}
	public void setGoodsCode(String goodsCode) {
		this.goodsCode = goodsCode;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getAgentOrderNum() {
		return agentOrderNum;
	}
	public void setAgentOrderNum(String agentOrderNum) {
		this.agentOrderNum = agentOrderNum;
	}
}
