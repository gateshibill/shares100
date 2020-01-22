package com.cofc.pojo;

import java.util.Date;

public class ShareContract {

	private Integer contractId;
	private Integer reasonId;
	private String contractImage;
	private Integer sharedUserId;
	private Integer contentId;
	private Integer punishmentId;
	private Double sharedDeposit;
	private Integer isClose;
	private Integer isPublic;
	private Date sharedTime;
	private Date createTime;
	private Date updateTime;
	private Double totalDeposit;
	private Integer totalNumber;
	private Integer loginPlat;

	// 连表
	private ContractContent content;
	private ContractReason reason;
	private ContractPunishment punishment;
	private UserCommon usercommon;
	// 临时数据
	private String nickName;
	private String realName;
	private String headImage;
	private String contentTitle;
	private String punishmentTitle;
	private String contractTitle;

	public Integer getContractId() {
		return contractId;
	}

	public void setContractId(Integer contractId) {
		this.contractId = contractId;
	}

	public Integer getReasonId() {
		return reasonId;
	}

	public void setReasonId(Integer reasonId) {
		this.reasonId = reasonId;
	}

	public String getContractImage() {
		return contractImage;
	}

	public void setContractImage(String contractImage) {
		this.contractImage = contractImage;
	}

	public Integer getSharedUserId() {
		return sharedUserId;
	}

	public void setSharedUserId(Integer sharedUserId) {
		this.sharedUserId = sharedUserId;
	}

	public Integer getContentId() {
		return contentId;
	}

	public void setContentId(Integer contentId) {
		this.contentId = contentId;
	}

	public Integer getPunishmentId() {
		return punishmentId;
	}

	public void setPunishmentId(Integer punishmentId) {
		this.punishmentId = punishmentId;
	}

	public Integer getIsClose() {
		return isClose;
	}

	public void setIsClose(Integer isClose) {
		this.isClose = isClose;
	}

	public Integer getIsPublic() {
		return isPublic;
	}

	public void setIsPublic(Integer isPublic) {
		this.isPublic = isPublic;
	}

	public Date getSharedTime() {
		return sharedTime;
	}

	public void setSharedTime(Date sharedTime) {
		this.sharedTime = sharedTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Double getSharedDeposit() {
		return sharedDeposit;
	}

	public void setSharedDeposit(Double sharedDeposit) {
		this.sharedDeposit = sharedDeposit;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getHeadImage() {
		return headImage;
	}

	public void setHeadImage(String headImage) {
		this.headImage = headImage;
	}

	public String getContentTitle() {
		return contentTitle;
	}

	public void setContentTitle(String contentTitle) {
		this.contentTitle = contentTitle;
	}

	public String getPunishmentTitle() {
		return punishmentTitle;
	}

	public void setPunishmentTitle(String punishmentTitle) {
		this.punishmentTitle = punishmentTitle;
	}

	public String getContractTitle() {
		return contractTitle;
	}

	public void setContractTitle(String contractTitle) {
		this.contractTitle = contractTitle;
	}

	public ContractContent getContent() {
		return content;
	}

	public void setContent(ContractContent content) {
		this.content = content;
	}

	public ContractReason getReason() {
		return reason;
	}

	public void setReason(ContractReason reason) {
		this.reason = reason;
	}

	public ContractPunishment getPunishment() {
		return punishment;
	}

	public void setPunishment(ContractPunishment punishment) {
		this.punishment = punishment;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public UserCommon getUsercommon() {
		return usercommon;
	}

	public void setUsercommon(UserCommon usercommon) {
		this.usercommon = usercommon;
	}

	public Double getTotalDeposit() {
		return totalDeposit;
	}

	public void setTotalDeposit(Double totalDeposit) {
		this.totalDeposit = totalDeposit;
	}

	public Integer getTotalNumber() {
		return totalNumber;
	}

	public void setTotalNumber(Integer totalNumber) {
		this.totalNumber = totalNumber;
	}

	public Integer getLoginPlat() {
		return loginPlat;
	}

	public void setLoginPlat(Integer loginPlat) {
		this.loginPlat = loginPlat;
	}

}
