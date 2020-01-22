package com.cofc.pojo.aida;

import java.util.Date;

import com.cofc.pojo.UserCommon;

/**
 * 记录用户行为 1.分析用户名片访问情况 2.用户分享记录； 可以通过这张表分析出个人对公司、产品,还是个人感兴趣；
 * 
 * @author Administrator
 *
 */
public class ActionRecord {
	private int id;
	private int appId;// 应用ID；
	private int userId;
	private int salesPersonId;//
	private int sourceId;// 这里记录，用户是自己进来点击的，还是通过分享进来的，这里可以分享客户的主动性
	private int gateway;//0为小程序，1为网站，2为APP,3为公众号
	private int type;// 0为访问，1为分享;2为点赞，3.咨询，4.购买下单,5.点击(按钮),6.保存；7,拨打,8.查看（比如需要统计图片停在上面），9评论,10:复制;11:授权 ；12：收藏;13:加入购物车
	
	// 0为官网，1为名片，2为产品,包括商城,分类，3为动态，4为游戏，5为广告活动,6，电话；7,微信;8：邮箱：9：公司名；10：地址
	// ,11.贺卡;12.印象;13：微信登陆:;14:首页：15：新品；16：搭配；17：顾问；18：我们 ；19：购物车
	private int objectType;
	private String typeName;
	private String objectTypeName;
	private int objectId;// 如果是名片，传销售员用户id，如果是产品传产品ID，如果是活动传活动ID，如果是官网，销售ID；
	private String objectName;
	private String title;
	private String content;
	private String url;
	private Date createTime = new Date();
	private String note;
	private int times = 1;// 从1开始，记录用户累计同类型的次数；
	private UserCommon user;// 记录行为用户
	private String formId;

	public void initName() {
		switch (type) {
		case 0:
			typeName = "访问";
			break;
		case 1:
			typeName = "分享";
			break;
		case 2:
			typeName = "点赞";
			break;
		case 3:
			typeName = "咨询";
			break;
		case 4:
			typeName = "购买";// 0为访问，1为分享;2为点赞，3.咨询，4.购买下单,5.点击(按钮),6.保存；7,拨打,8.查看（比如需要统计图片停在上面），9评论,10:复制
			break;
		case 5:
			typeName = "点击";
			break;
		case 6:
			typeName = "保存";
			break;
		case 7:
			typeName = "拨打";
			break;
		case 8:
			typeName = "查看";
			break;
		case 9:
			typeName = "评论";
			break;
		case 10:
			typeName = "复制";
			break;
		case 11:
			typeName = "授权";
			break;
		default:
			typeName = "签到";
			break;
		}
		switch (objectType) {
		case 0:
			objectTypeName = "官网";
			break;
		case 1:
			objectTypeName = "名片";
			break;
		case 2:
			objectTypeName = "产品";
			break;
		case 3:
			objectTypeName = "动态";
			break;
		case 4://// 0为官网，1为名片，2为产品,包括商城，3为动态，4为游戏，5为广告活动,6，电话；7,微信;8：邮箱：9：公司；10：地址
			objectTypeName = "游戏";
			break;
		case 5:
			objectTypeName = "活动";
			break;
		case 6:
			objectTypeName = "电话";
			break;
		case 7:
			objectTypeName = "微信";
			break;
		case 8:
			objectTypeName = "邮箱";
			break;
		case 9:
			objectTypeName = "公司";
			break;
		case 10:
			objectTypeName = "地址";
			break;
		case 11:
			objectTypeName = "贺卡";
			break;
		case 12:
			objectTypeName = "印象";
			break;
		case 13:
			objectTypeName = "微信登陆";
			break;
		default:
			objectTypeName = "小程序";
			break;
		}
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

	public String getFormId() {
		return formId;
	}

	public void setFormId(String formId) {
		this.formId = formId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getSalesPersonId() {
		return salesPersonId;
	}

	public void setSalesPersonId(int salesPersonId) {
		this.salesPersonId = salesPersonId;
	}

	public int getSourceId() {
		return sourceId;
	}

	public int getGateway() {
		return gateway;
	}

	public void setGateway(int gateway) {
		this.gateway = gateway;
	}

	public void setSourceId(int sourceId) {
		this.sourceId = sourceId;
	}

	public String getObjectName() {
		return objectName;
	}

	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getObjectType() {
		return objectType;
	}

	public void setObjectType(int objectType) {
		this.objectType = objectType;
	}

	public int getObjectId() {
		return objectId;
	}

	public void setObjectId(int objectId) {
		this.objectId = objectId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getObjectTypeName() {
		return objectTypeName;
	}

	public void setObjectTypeName(String objectTypeName) {
		this.objectTypeName = objectTypeName;
	}

	public int getTimes() {
		return times;
	}

	public void setTimes(int times) {
		this.times = times;
	}

	public UserCommon getUser() {
		return user;
	}

	public void setUser(UserCommon user) {
		this.user = user;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
