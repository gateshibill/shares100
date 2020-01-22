package com.cofc.pojo;

import java.util.Date;
import java.util.List;

public class DescoveryCommon {
	private Integer descoveryId;
	private String descoveryTitle;
	private String descoveryImage;
	private Integer publisherId;
	private Integer descoveryType;
	private Integer pariseCount;
	private Integer joinedCount;
	private Integer readCount;
	private Integer collectCount;
	private String descoveryDetails;
	private Date publishTime;
	private Integer isPass;
	private Integer isShangJia;
	private Date updateTime;
	private Integer loginPlat;
	private Integer isRecommend;
	private String photosBook;
	private String projectType;
	private Integer projectStatus;
	private String hasResourceTag;
	private String needResourceTag;
	private String projectPay;
	private String expiryDate;
	private String productType;
	private double productPrice;
	private Integer freePost;
	private String bidWay;
	private String skillType;
	private Date startTime;
	private Date endTime;
	private double servicePrice;
	private String explanatoryService;
	private String serviceWay;
	private String wantedType;
	private Integer minSalary;
	private Integer maxSalary;
	private String workCity;
	private String welfare;
	private String wantedSkill;
	private String wantedDesc;
	private String placeName;
	private String inquiryTags;
	private Integer recommendWay;
	private Integer isTop;
    private String address;
    private String coverImage;
	private UserCommon dscvPublisher;
	private RecommendCommon recommend;
	private List<DescoveryReader> readerList;
	private List<DescoveryLeaveMessage> leaveList;
	private int commentCount;
	private ApplicationCommon app;
	
	private String projectTypeName;
	private String hasResourceName;
	private String needResourceName;
	private String projectPayName;
	private Integer isCollected;
	private Integer isParised;
	private String productTypeName;
	private String bidWayName;
	private String skillTypeName;
	private String explanatoryServiceName;
	private String serviceWayName;
	private String wantedTypeName;
	private String welfareName;
	private String publisherName;
	
	
	//关联发现类型
	private String typeName;
	
	public Integer getDescoveryId() {
		return descoveryId;
	}

	public void setDescoveryId(Integer descoveryId) {
		this.descoveryId = descoveryId;
	}

	public String getDescoveryTitle() {
		return descoveryTitle;
	}

	public void setDescoveryTitle(String descoveryTitle) {
		this.descoveryTitle = descoveryTitle;
	}

	public String getDescoveryImage() {
		return descoveryImage;
	}

	public void setDescoveryImage(String descoveryImage) {
		this.descoveryImage = descoveryImage;
	}

	public Integer getPublisherId() {
		return publisherId;
	}

	public void setPublisherId(Integer publisherId) {
		this.publisherId = publisherId;
	}

	public Integer getDescoveryType() {
		return descoveryType;
	}

	public void setDescoveryType(Integer descoveryType) {
		this.descoveryType = descoveryType;
	}

	public Integer getPariseCount() {
		return pariseCount;
	}

	public void setPariseCount(Integer pariseCount) {
		this.pariseCount = pariseCount;
	}

	public Integer getJoinedCount() {
		return joinedCount;
	}

	public void setJoinedCount(Integer joinedCount) {
		this.joinedCount = joinedCount;
	}

	public Integer getReadCount() {
		return readCount;
	}

	public void setReadCount(Integer readCount) {
		this.readCount = readCount;
	}

	public String getDescoveryDetails() {
		return descoveryDetails;
	}

	public void setDescoveryDetails(String descoveryDetails) {
		this.descoveryDetails = descoveryDetails;
	}

	public Date getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}

	public Integer getIsPass() {
		return isPass;
	}

	public void setIsPass(Integer isPass) {
		this.isPass = isPass;
	}

	public Integer getIsShangJia() {
		return isShangJia;
	}

	public void setIsShangJia(Integer isShangJia) {
		this.isShangJia = isShangJia;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public UserCommon getDscvPublisher() {
		return dscvPublisher;
	}

	public void setDscvPublisher(UserCommon dscvPublisher) {
		this.dscvPublisher = dscvPublisher;
	}

	public Integer getCollectCount() {
		return collectCount;
	}

	public void setCollectCount(Integer collectCount) {
		this.collectCount = collectCount;
	}

	public Integer getLoginPlat() {
		return loginPlat;
	}

	public void setLoginPlat(Integer loginPlat) {
		this.loginPlat = loginPlat;
	}

	public Integer getIsRecommend() {
		return isRecommend;
	}

	public void setIsRecommend(Integer isRecommend) {
		this.isRecommend = isRecommend;
	}

	public String getPhotosBook() {
		return photosBook;
	}

	public void setPhotosBook(String photosBook) {
		this.photosBook = photosBook;
	}

	public String getProjectType() {
		return projectType;
	}

	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}

	public Integer getProjectStatus() {
		return projectStatus;
	}

	public void setProjectStatus(Integer projectStatus) {
		this.projectStatus = projectStatus;
	}

	public String getHasResourceTag() {
		return hasResourceTag;
	}

	public void setHasResourceTag(String hasResourceTag) {
		this.hasResourceTag = hasResourceTag;
	}

	public String getNeedResourceTag() {
		return needResourceTag;
	}

	public void setNeedResourceTag(String needResourceTag) {
		this.needResourceTag = needResourceTag;
	}

	public String getProjectPay() {
		return projectPay;
	}

	public void setProjectPay(String projectPay) {
		this.projectPay = projectPay;
	}

	public String getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public double getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(double productPrice) {
		this.productPrice = productPrice;
	}

	public Integer getFreePost() {
		return freePost;
	}

	public void setFreePost(Integer freePost) {
		this.freePost = freePost;
	}

	public String getBidWay() {
		return bidWay;
	}

	public void setBidWay(String bidWay) {
		this.bidWay = bidWay;
	}

	public String getSkillType() {
		return skillType;
	}

	public void setSkillType(String skillType) {
		this.skillType = skillType;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public double getServicePrice() {
		return servicePrice;
	}

	public void setServicePrice(double servicePrice) {
		this.servicePrice = servicePrice;
	}

	public String getExplanatoryService() {
		return explanatoryService;
	}

	public void setExplanatoryService(String explanatoryService) {
		this.explanatoryService = explanatoryService;
	}

	public String getServiceWay() {
		return serviceWay;
	}

	public void setServiceWay(String serviceWay) {
		this.serviceWay = serviceWay;
	}

	public String getWantedType() {
		return wantedType;
	}

	public void setWantedType(String wantedType) {
		this.wantedType = wantedType;
	}

	public Integer getMinSalary() {
		return minSalary;
	}

	public void setMinSalary(Integer minSalary) {
		this.minSalary = minSalary;
	}

	public Integer getMaxSalary() {
		return maxSalary;
	}

	public void setMaxSalary(Integer maxSalary) {
		this.maxSalary = maxSalary;
	}

	public String getWorkCity() {
		return workCity;
	}

	public void setWorkCity(String workCity) {
		this.workCity = workCity;
	}

	public String getWelfare() {
		return welfare;
	}

	public void setWelfare(String welfare) {
		this.welfare = welfare;
	}

	public String getWantedSkill() {
		return wantedSkill;
	}

	public void setWantedSkill(String wantedSkill) {
		this.wantedSkill = wantedSkill;
	}

	public String getWantedDesc() {
		return wantedDesc;
	}

	public void setWantedDesc(String wantedDesc) {
		this.wantedDesc = wantedDesc;
	}

	public String getPlaceName() {
		return placeName;
	}

	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}

	public List<DescoveryReader> getReaderList() {
		return readerList;
	}

	public void setReaderList(List<DescoveryReader> readerList) {
		this.readerList = readerList;
	}

	public String getProjectTypeName() {
		return projectTypeName;
	}

	public void setProjectTypeName(String projectTypeName) {
		this.projectTypeName = projectTypeName;
	}

	public String getHasResourceName() {
		return hasResourceName;
	}

	public void setHasResourceName(String hasResourceName) {
		this.hasResourceName = hasResourceName;
	}

	public String getNeedResourceName() {
		return needResourceName;
	}

	public void setNeedResourceName(String needResourceName) {
		this.needResourceName = needResourceName;
	}

	public String getProjectPayName() {
		return projectPayName;
	}

	public void setProjectPayName(String projectPayName) {
		this.projectPayName = projectPayName;
	}

	public Integer getIsCollected() {
		return isCollected;
	}

	public void setIsCollected(Integer isCollected) {
		this.isCollected = isCollected;
	}

	public Integer getIsParised() {
		return isParised;
	}

	public void setIsParised(Integer isParised) {
		this.isParised = isParised;
	}

	public String getProductTypeName() {
		return productTypeName;
	}

	public void setProductTypeName(String productTypeName) {
		this.productTypeName = productTypeName;
	}

	public String getBidWayName() {
		return bidWayName;
	}

	public void setBidWayName(String bidWayName) {
		this.bidWayName = bidWayName;
	}

	public String getSkillTypeName() {
		return skillTypeName;
	}

	public void setSkillTypeName(String skillTypeName) {
		this.skillTypeName = skillTypeName;
	}

	public String getExplanatoryServiceName() {
		return explanatoryServiceName;
	}

	public void setExplanatoryServiceName(String explanatoryServiceName) {
		this.explanatoryServiceName = explanatoryServiceName;
	}

	public String getServiceWayName() {
		return serviceWayName;
	}

	public void setServiceWayName(String serviceWayName) {
		this.serviceWayName = serviceWayName;
	}

	public String getWantedTypeName() {
		return wantedTypeName;
	}

	public void setWantedTypeName(String wantedTypeName) {
		this.wantedTypeName = wantedTypeName;
	}

	public String getWelfareName() {
		return welfareName;
	}

	public void setWelfareName(String welfareName) {
		this.welfareName = welfareName;
	}

	@Override
	public String toString() {
		return "DescoveryCommon [descoveryId=" + descoveryId + ", descoveryTitle=" + descoveryTitle
				+ ", descoveryImage=" + descoveryImage + ", publisherId=" + publisherId + ", descoveryType="
				+ descoveryType + ", pariseCount=" + pariseCount + ", joinedCount=" + joinedCount + ", readCount="
				+ readCount + ", collectCount=" + collectCount + ", descoveryDetails=" + descoveryDetails
				+ ", publishTime=" + publishTime + ", isPass=" + isPass + ", isShangJia=" + isShangJia + ", updateTime="
				+ updateTime + ", loginPlat=" + loginPlat + ", isRecommend=" + isRecommend + ", photosBook="
				+ photosBook + ", projectType=" + projectType + ", projectStatus=" + projectStatus + ", hasResourceTag="
				+ hasResourceTag + ", needResourceTag=" + needResourceTag + ", projectPay=" + projectPay
				+ ", expiryDate=" + expiryDate + ", productType=" + productType + ", productPrice=" + productPrice
				+ ", freePost=" + freePost + ", bidWay=" + bidWay + ", skillType=" + skillType + ", startTime="
				+ startTime + ", endTime=" + endTime + ", servicePrice=" + servicePrice + ", explanatoryService="
				+ explanatoryService + ", serviceWay=" + serviceWay + ", wantedType=" + wantedType + ", minSalary="
				+ minSalary + ", maxSalary=" + maxSalary + ", workCity=" + workCity + ", welfare=" + welfare
				+ ", wantedSkill=" + wantedSkill + ", wantedDesc=" + wantedDesc + ", placeName=" + placeName
				+ ", inquiryTags=" + inquiryTags + ", recommendWay=" + recommendWay + ", dscvPublisher=" + dscvPublisher
				+ ", recommend=" + recommend + ", readerList=" + readerList + ", projectTypeName=" + projectTypeName
				+ ", hasResourceName=" + hasResourceName + ", needResourceName=" + needResourceName
				+ ", projectPayName=" + projectPayName + ", isCollected=" + isCollected + ", isParised=" + isParised
				+ ", productTypeName=" + productTypeName + ", bidWayName=" + bidWayName + ", skillTypeName="
				+ skillTypeName + ", explanatoryServiceName=" + explanatoryServiceName + ", serviceWayName="
				+ serviceWayName + ", wantedTypeName=" + wantedTypeName + ", welfareName=" + welfareName + "]";
	}

	public String getInquiryTags() {
		return inquiryTags;
	}

	public void setInquiryTags(String inquiryTags) {
		this.inquiryTags = inquiryTags;
	}

	public RecommendCommon getRecommend() {
		return recommend;
	}

	public void setRecommend(RecommendCommon recommend) {
		this.recommend = recommend;
	}

	public Integer getRecommendWay() {
		return recommendWay;
	}

	public void setRecommendWay(Integer recommendWay) {
		this.recommendWay = recommendWay;
	}

	public Integer getIsTop() {
		return isTop;
	}

	public void setIsTop(Integer isTop) {
		this.isTop = isTop;
	}

	public String getPublisherName() {
		return publisherName;
	}

	public void setPublisherName(String publisherName) {
		this.publisherName = publisherName;
	}

	public ApplicationCommon getApp() {
		return app;
	}

	public void setApp(ApplicationCommon app) {
		this.app = app;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public List<DescoveryLeaveMessage> getLeaveList() {
		return leaveList;
	}

	public void setLeaveList(List<DescoveryLeaveMessage> leaveList) {
		this.leaveList = leaveList;
	}

	public int getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(int commentCount) {
		this.commentCount = commentCount;
	}

	public String getCoverImage() {
		return coverImage;
	}

	public void setCoverImage(String coverImage) {
		this.coverImage = coverImage;
	}
}
