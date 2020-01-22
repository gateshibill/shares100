package com.cofc.pojo.aida;

//采用定时分析计算一次；
//销售人员能力评估模型
public class SalesAbility {
	private int id;
	private int appId;
	private int salesPersonId;
	private int getCustomerBt = 20;// 获客能力：客户数量、加入公司时间；10+20*客户量/时间*排名系数+20/排名+50*最大客户量/自己客户量
	//50+50/排名，浏览量；
	private int charmBt = 40;// 魅力：点赞、名片分发次数：10+20*点赞数/时间*排名系数+20/排名+50*最大销售点赞数/自己点赞量
	//50+点赞排名；
	private int spreadProductBt = 30;// 推广产品：产品分享、查看次数；与加入时间：
	//产品访问量
	private int spreadWebsiteBt = 36;// 推广网站：分享、查看，与加入时间；
	//官网访问量
	private int customerInteractBt = 38;// 客户互动：咨询、交流、拨打电话
	//产品咨询
	private int salesInitiativeBt = 20;// 销售主动性：自己分享次数、带来客户数量
	
	
	public int getSumSorce()
	{
		return getCustomerBt+charmBt+spreadProductBt+spreadWebsiteBt+customerInteractBt+salesInitiativeBt;
	}

	public SalesAbility() {
	}

	public void analyse() {

	}

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

	public int getSalesPersonId() {
		return salesPersonId;
	}

	public void setSalesPersonId(int salesPersonId) {
		this.salesPersonId = salesPersonId;
	}

	public int getGetCustomerBt() {
		return getCustomerBt;
	}

	public void setGetCustomerBt(int getCustomerBt) {
		this.getCustomerBt = getCustomerBt;
	}

	public int getCharmBt() {
		return charmBt;
	}

	public void setCharmBt(int charmBt) {
		this.charmBt = charmBt;
	}

	public int getSpreadProductBt() {
		return spreadProductBt;
	}

	public void setSpreadProductBt(int spreadProductBt) {
		this.spreadProductBt = spreadProductBt;
	}


	public int getSpreadWebsiteBt() {
		return spreadWebsiteBt;
	}

	public void setSpreadWebsiteBt(int spreadWebsiteBt) {
		this.spreadWebsiteBt = spreadWebsiteBt;
	}

	public int getCustomerInteractBt() {
		return customerInteractBt;
	}

	public void setCustomerInteractBt(int customerInteractBt) {
		this.customerInteractBt = customerInteractBt;
	}

	public int getSalesInitiativeBt() {
		return salesInitiativeBt;
	}

	public void setSalesInitiativeBt(int salesInitiativeBt) {
		this.salesInitiativeBt = salesInitiativeBt;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
