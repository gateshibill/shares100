package com.cofc.pojo.aida;

import java.util.Date;
import java.util.List;

public class SalesCustomer {
    //下面数据用户AI分析
	private int id;
	private int appId;
	private int userId;
	private int salesPersonId;
	private String salesPersonName;
	private int reliability;
	private int status;   //0为不活跃，1、已联系；2.正在跟进，3为成交；成交后为生成成交订单。状态0-1-2-3-1，用户二次购买 
	private int departmentId;// 区域和行业用户
    private Integer isTop;//置顶
    private Integer isPb;//屏蔽
	
	// 下面数据用户AI分析
	private int visitedCardCount;// 名片被访问次数；
	private int visitedWebsiteCount;
	private int visitedShopCount ;
	private int visitedGameCount ;
	private int visitedOtherCount ;
	private Date createTime = new Date();
	private Date lastTime =new Date();//用户显示通讯录
	private String  lastFormId ;

	private String userName;
	private String headImage;
	
	private Date expectedDate;//预计成交；
	private int expectedRatio;//预计成交率
	
	private List<CustomerLabel> labelList;
	private List<UserImpress> impressList;//销售印象
	private List<IMessage> iMessageList;//消息条数
	private int unReadMessageCount;
	private Date lastChatTime;
	private int isblock=0;
	
    private UserCard userCard;

	public SalesCustomer() {
	}
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getAppId() {
		return appId;
	}

	public void setAppId(int appId) {
		this.appId = appId;
	}


	public String getSalesPersonName() {
		return salesPersonName;
	}
	public void setSalesPersonName(String salesPersonName) {
		this.salesPersonName = salesPersonName;
	}
	public int getSalesPersonId() {
		return salesPersonId;
	}
	public void setSalesPersonId(int salesPersonId) {
		this.salesPersonId = salesPersonId;
	}
	public int getReliability() {
		return reliability;
	}

	public void setReliability(int reliability) {
		this.reliability = reliability;
	}

	public int getUnReadMessageCount() {
		return unReadMessageCount;
	}
	public void setUnReadMessageCount(int unReadMessageCount) {
		this.unReadMessageCount = unReadMessageCount;
	}
	public List<CustomerLabel> getLabelList() {
		return labelList;
	}
	public void setLabelList(List<CustomerLabel> labelList) {
		this.labelList = labelList;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public int getDepartmentId() {
		return departmentId;
	}

	public int getIsblock() {
		return isblock;
	}
	public void setIsblock(int isblock) {
		this.isblock = isblock;
	}
	public void setDepartmentId(int departmentId) {
		this.departmentId = departmentId;
	}

	public int getVisitedCardCount() {
		return visitedCardCount;
	}

	public Date getLastChatTime() {
		return lastChatTime;
	}
	public void setLastChatTime(Date lastChatTime) {
		this.lastChatTime = lastChatTime;
	}
	public void setVisitedCardCount(int visitedCardCount) {
		this.visitedCardCount = visitedCardCount;
	}

	public int getVisitedWebsiteCount() {
		return visitedWebsiteCount;
	}

	public void setVisitedWebsiteCount(int visitedWebsiteCount) {
		this.visitedWebsiteCount = visitedWebsiteCount;
	}

	public int getVisitedShopCount() {
		return visitedShopCount;
	}

	public void setVisitedShopCount(int visitedShopCount) {
		this.visitedShopCount = visitedShopCount;
	}

	public int getVisitedGameCount() {
		return visitedGameCount;
	}

	public void setVisitedGameCount(int visitedGameCount) {
		this.visitedGameCount = visitedGameCount;
	}

	public int getVisitedOtherCount() {
		return visitedOtherCount;
	}

	public void setVisitedOtherCount(int visitedOtherCount) {
		this.visitedOtherCount = visitedOtherCount;
	}

	public String getLastFormId() {
		return lastFormId;
	}
	public void setLastFormId(String lastFormId) {
		this.lastFormId = lastFormId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getHeadImage() {
		return headImage;
	}
	public void setHeadImage(String headImage) {
		this.headImage = headImage;
	}
	
	public Integer getIsTop() {
		return isTop;
	}
	public void setIsTop(Integer isTop) {
		this.isTop = isTop;
	}
	public Integer getIsPb() {
		return isPb;
	}
	public void setIsPb(Integer isPb) {
		this.isPb = isPb;
	}
	public Date getExpectedDate() {
		return expectedDate;
	}
	public void setExpectedDate(Date expectedDate) {
		this.expectedDate = expectedDate;
	}

	public int getExpectedRatio() {
		return expectedRatio;
	}
	public void setExpectedRatio(int expectedRatio) {
		this.expectedRatio = expectedRatio;
	}
	public List<UserImpress> getImpressList() {
		return impressList;
	}
	public void setImpressList(List<UserImpress> impressList) {
		this.impressList = impressList;
	}
	public UserCard getUserCard() {
		return userCard;
	}

	public void setUserCard(UserCard userCard) {
		this.userCard = userCard;
	}

	
	public Date getLastTime() {
		return lastTime;
	}
	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}
	public List<IMessage> getiMessageList() {
		return iMessageList;
	}
	public void setiMessageList(List<IMessage> iMessageList) {
		this.iMessageList = iMessageList;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
