package com.cofc.pojo;

import java.util.Date;

public class UserOrder {

	public final static int STATUS_WAIT_CONFIRM_RECEIPT = 10;  //待收货
	public final static int STATUS_CONFIRM_RECEIPT = 14;  //确认收货
	public final static int STATUS_WAIT_PAY = 3; //待付款
	public final static int STATUS_QUIT_ORDER = -1;//已取消，交易关闭
	public final static int STATUS_DELETE_ORDER = -4;//已删除

	
	private Integer orderID;  //订单id
	private Integer objectID;  //对象id,可能是商品、任务等，由order_type决定
	private String multiID;  //产品包的时候存多个id
	private Integer orderType;  //对象类型，0商品 1任务
	private String objects;  //对象的json数组[{...},{...}]
	private Integer userid;
	private int number;
	private double totalFee;  //总计
	private double realFee;  //实际支付
	/*
		0  未审核确认订单
		3  确认订单(有效订单)
		5  已付款
		7  待发货()
		10  已发货（待确认中）
		14  确认收货
		16  待评价
		20  交易完成
		
		-1  交易关闭 
		-2  退款中
		-3  退货中
		-4  删除
		-5 已退款
		
		无效的订单都是负数
	*/
	
	private Integer orderStatus;
	private Date updateTime;
	private Date createTime;
	private Integer loginPlat;  //订单平台【1.商会，2.百享园，3.飞看云】
	private String consignee;  //收货人姓名
	private String phone;
	private String address;
	private String remarks;//备注
	private Integer logisticsTypes;//物流类型【比如：1.韵达，2.顺丰，3.申通，4.圆通】
	private String logisticsOrder;//物流单号
	private Integer payStatus;
	private Integer isOnline;
	private Double rebateMoney; //返点金额
	private Integer agentId;//代理用户id
	private Integer deskId;//桌子id
	private Integer consumeType; //消费方式
	private Integer payType; //付款方式： 1：微信  / 2.余额 /3.积分
	private Integer scoreCount;//所需积分
	private Integer isInvoice;//是否开发票
	private String invoiceStr;//发票信息集合
	private Integer isTicket;//是否使用优惠券
	private Integer discountMoney;//优惠金额
	private String ticketInfo;//优惠券详情
	private String orderNo;//订单号
	private Integer orderSource;//订单支付来源 【用户退款】 1:小程序 2：PC 3 ：APP
	private ApplicationCommon app;
	
	public Integer getOrderID() {
		return orderID;
	}
	public void setOrderID(Integer orderID) {
		this.orderID = orderID;
	}
	public Integer getObjectID() {
		return objectID;
	}
	public void setObjectID(Integer objectID) {
		this.objectID = objectID;
	}
	public String getMultiID() {
		return multiID;
	}
	public void setMultiID(String multiID) {
		this.multiID = multiID;
	}
	public Integer getOrderType() {
		return orderType;
	}
	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}
	public String getObjects() {
		return objects;
	}
	public void setObjects(String objects) {
		this.objects = objects;
	}
	public Integer getUserid() {
		return userid;
	}
	public void setUserid(Integer userid) {
		this.userid = userid;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public double getTotalFee() {
		return totalFee;
	}
	public void setTotalFee(double totalFee) {
		this.totalFee = totalFee;
	}
	public double getRealFee() {
		return realFee;
	}
	public void setRealFee(double realFee) {
		this.realFee = realFee;
	}
	public Integer getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Integer getLoginPlat() {
		return loginPlat;
	}
	public void setLoginPlat(Integer loginPlat) {
		this.loginPlat = loginPlat;
	}
	public String getConsignee() {
		return consignee;
	}
	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public ApplicationCommon getApp() {
		return app;
	}
	public void setApp(ApplicationCommon app) {
		this.app = app;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public Integer getLogisticsTypes() {
		return logisticsTypes;
	}
	public void setLogisticsTypes(Integer logisticsTypes) {
		this.logisticsTypes = logisticsTypes;
	}
	public String getLogisticsOrder() {
		return logisticsOrder;
	}
	public void setLogisticsOrder(String logisticsOrder) {
		this.logisticsOrder = logisticsOrder;
	}
	public Integer getPayStatus() {
		return payStatus;
	}
	public void setPayStatus(Integer payStatus) {
		this.payStatus = payStatus;
	}
	public Integer getIsOnline() {
		return isOnline;
	}
	public void setIsOnline(Integer isOnline) {
		this.isOnline = isOnline;
	}
	public Double getRebateMoney() {
		return rebateMoney;
	}
	public void setRebateMoney(Double rebateMoney) {
		this.rebateMoney = rebateMoney;
	}
	public Integer getDeskId() {
		return deskId;
	}
	public Integer getIsInvoice() {
		return isInvoice;
	}
	public void setIsInvoice(Integer isInvoice) {
		this.isInvoice = isInvoice;
	}
	public String getInvoiceStr() {
		return invoiceStr;
	}
	public void setInvoiceStr(String invoiceStr) {
		this.invoiceStr = invoiceStr;
	}
	public void setDeskId(Integer deskId) {
		this.deskId = deskId;
	}
	public Integer getConsumeType() {
		return consumeType;
	}
	public void setConsumeType(Integer consumeType) {
		this.consumeType = consumeType;
	}
	public Integer getAgentId() {
		return agentId;
	}
	public void setAgentId(Integer agentId) {
		this.agentId = agentId;
	}
	public Integer getPayType() {
		return payType;
	}
	public void setPayType(Integer payType) {
		this.payType = payType;
	}
	public Integer getScoreCount() {
		return scoreCount;
	}
	public void setScoreCount(Integer scoreCount) {
		this.scoreCount = scoreCount;
	}
	public Integer getIsTicket() {
		return isTicket;
	}
	public void setIsTicket(Integer isTicket) {
		this.isTicket = isTicket;
	}
	public Integer getDiscountMoney() {
		return discountMoney;
	}
	public void setDiscountMoney(Integer discountMoney) {
		this.discountMoney = discountMoney;
	}
	public String getTicketInfo() {
		return ticketInfo;
	}
	public void setTicketInfo(String ticketInfo) {
		this.ticketInfo = ticketInfo;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public static int getStatusWaitConfirmReceipt() {
		return STATUS_WAIT_CONFIRM_RECEIPT;
	}
	public static int getStatusConfirmReceipt() {
		return STATUS_CONFIRM_RECEIPT;
	}
	public static int getStatusWaitPay() {
		return STATUS_WAIT_PAY;
	}
	public static int getStatusQuitOrder() {
		return STATUS_QUIT_ORDER;
	}
	public static int getStatusDeleteOrder() {
		return STATUS_DELETE_ORDER;
	}
	public Integer getOrderSource() {
		return orderSource;
	}
	public void setOrderSource(Integer orderSource) {
		this.orderSource = orderSource;
	}
}
