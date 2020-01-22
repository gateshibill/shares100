package com.cofc.pojo;

import java.util.Date;

public class ApplicationCommon {
	private Integer applicationId;
	private Integer applicationOwner;
	private Integer modelId;
	private String applicationName;
	private Integer applicationStatus;
	private Date appCreateTime;
	private Date appUpdateTime;
	private String appId; //微信小程序  :小程序支付用的APPId
	private String appSecret;
	private Date planStopTime;
	private Integer parentId;
	private Integer applicationType;
	private Integer groupType;
	private String createrName;
	private String createrPhone;
	private Integer isRecommend;
	private String groupCard;
	private Integer smallRoutine;
	private String mchid;
	private Integer visitorCount;
	private String saleStatus;
	private Integer applicationCreator;
	private String apiKey; 
	private String createQQ;
	private String appLogo;
	private String wxOpenAppId; //微信开放平台appid:APP支付用的appId
	private String wxServiceAppId;//微信服务号appid：PC网站支付用的appId
	private String wxPublicAppId;//微信公众号appid
	private UserCommon appOwner;
	private ApplicationModel appModel;
	private GroupTypes types;
	private ApplicationType apptype;

	public Integer getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(Integer applicationId) {
		this.applicationId = applicationId;
	}

	public Integer getApplicationOwner() {
		return applicationOwner;
	}

	public void setApplicationOwner(Integer applicationOwner) {
		this.applicationOwner = applicationOwner;
	}

	public Integer getModelId() {
		return modelId;
	}

	public void setModelId(Integer modelId) {
		this.modelId = modelId;
	}

	public Integer getApplicationStatus() {
		return applicationStatus;
	}

	public void setApplicationStatus(Integer applicationStatus) {
		this.applicationStatus = applicationStatus;
	}

	public Date getAppCreateTime() {
		return appCreateTime;
	}

	public void setAppCreateTime(Date appCreateTime) {
		this.appCreateTime = appCreateTime;
	}

	public Date getAppUpdateTime() {
		return appUpdateTime;
	}

	public void setAppUpdateTime(Date appUpdateTime) {
		this.appUpdateTime = appUpdateTime;
	}

	public UserCommon getAppOwner() {
		return appOwner;
	}

	public void setAppOwner(UserCommon appOwner) {
		this.appOwner = appOwner;
	}

	public ApplicationModel getAppModel() {
		return appModel;
	}

	public void setAppModel(ApplicationModel appModel) {
		this.appModel = appModel;
	}

	public String getApplicationName() {
		return applicationName;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAppSecret() {
		return appSecret;
	}

	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}

	public Date getPlanStopTime() {
		return planStopTime;
	}

	public void setPlanStopTime(Date planStopTime) {
		this.planStopTime = planStopTime;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public Integer getApplicationType() {
		return applicationType;
	}

	public void setApplicationType(Integer applicationType) {
		this.applicationType = applicationType;
	}

	public Integer getGroupType() {
		return groupType;
	}

	public void setGroupType(Integer groupType) {
		this.groupType = groupType;
	}

	public String getCreaterName() {
		return createrName;
	}

	public void setCreaterName(String createrName) {
		this.createrName = createrName;
	}

	public String getCreaterPhone() {
		return createrPhone;
	}

	public void setCreaterPhone(String createrPhone) {
		this.createrPhone = createrPhone;
	}

	public Integer getIsRecommend() {
		return isRecommend;
	}

	public void setIsRecommend(Integer isRecommend) {
		this.isRecommend = isRecommend;
	}

	public String getGroupCard() {
		return groupCard;
	}

	public void setGroupCard(String groupCard) {
		this.groupCard = groupCard;
	}

	public Integer getSmallRoutine() {
		return smallRoutine;
	}

	public void setSmallRoutine(Integer smallRoutine) {
		this.smallRoutine = smallRoutine;
	}

	public String getMchid() {
		return mchid;
	}

	public void setMchid(String mchid) {
		this.mchid = mchid;
	}

	@Override
	public String toString() {
		return "ApplicationCommon [applicationId=" + applicationId + ", applicationOwner=" + applicationOwner
				+ ", modelId=" + modelId + ", applicationName=" + applicationName + ", applicationStatus="
				+ applicationStatus + ", appCreateTime=" + appCreateTime + ", appUpdateTime=" + appUpdateTime
				+ ", appId=" + appId + ", appSecret=" + appSecret + ", planStopTime=" + planStopTime + ", parentId="
				+ parentId + ", applicationType=" + applicationType + ", groupType=" + groupType + ", createrName="
				+ createrName + ", createrPhone=" + createrPhone + ", isRecommend=" + isRecommend + ", groupCard="
				+ groupCard + ", smallRoutine=" + smallRoutine + ", mchid=" + mchid + ", appOwner=" + appOwner
				+ ", appModel=" + appModel + "]";
	}

	public GroupTypes getTypes() {
		return types;
	}

	public void setTypes(GroupTypes types) {
		this.types = types;
	}

	public Integer getVisitorCount() {
		return visitorCount;
	}

	public void setVisitorCount(Integer visitorCount) {
		this.visitorCount = visitorCount;
	}

	public String getSaleStatus() {
		return saleStatus;
	}

	public void setSaleStatus(String saleStatus) {
		this.saleStatus = saleStatus;
	}

	public ApplicationType getApptype() {
		return apptype;
	}

	public void setApptype(ApplicationType apptype) {
		this.apptype = apptype;
	}

	public Integer getApplicationCreator() {
		return applicationCreator;
	}

	public void setApplicationCreator(Integer applicationCreator) {
		this.applicationCreator = applicationCreator;
	}

	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	public String getCreateQQ() {
		return createQQ;
	}

	public void setCreateQQ(String createQQ) {
		this.createQQ = createQQ;
	}

	public String getAppLogo() {
		return appLogo;
	}

	public void setAppLogo(String appLogo) {
		this.appLogo = appLogo;
	}

	public String getWxOpenAppId() {
		return wxOpenAppId;
	}

	public void setWxOpenAppId(String wxOpenAppId) {
		this.wxOpenAppId = wxOpenAppId;
	}

	public String getWxServiceAppId() {
		return wxServiceAppId;
	}

	public void setWxServiceAppId(String wxServiceAppId) {
		this.wxServiceAppId = wxServiceAppId;
	}

	public String getWxPublicAppId() {
		return wxPublicAppId;
	}

	public void setWxPublicAppId(String wxPublicAppId) {
		this.wxPublicAppId = wxPublicAppId;
	}
	
}
