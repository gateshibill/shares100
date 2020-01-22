package com.cofc.pojo.aida;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserCard {
	private Integer cardId;
	private Integer appId;// 应用id
	private Integer userId;
	private String realName;
	private Integer gender;// 性别： 1:男 2：女 0：保密
	private String position = "产品经理";
	private String phone;
	private String sitPhone;// 座机
	private String wechat;
	private String email;
	private String company;
	private String address;
	private String headImage;
	private String codeUrl;
	private String introduce = "我是飞看科技产品经理，提供好产品是我的使命！";
	private Date createTime;
	private String voice;
	private String vedio;
	private Integer isEffect;
	private int praiseCount;// 点赞数
	private String photoList = "http://www.haoshi360.com/cofC/aidaImage/20181001171129c3725.png,http://www.haoshi360.com/cofC/aidaImage/20181001171138f40bf.png,http://www.haoshi360.com/cofC/aidaImage/201810011711511a7aa.png,http://www.haoshi360.com/cofC/aidaImage/201810011712008101a.png,http://www.haoshi360.com/cofC/aidaImage/2018100117120699d36.png";// 图片集合，用逗号隔开
	private List<UserImpress> impressList = new ArrayList<UserImpress>();// 印象集合
	private String shareTitle;// 为了节日修改
	private int isShare = 0;

	// 获取销售对应的小程序id
	private Integer salerMiniUserId;

	public UserCard() {

	}

	public void initIntroduce() {
		introduce = String.format("您好！很高兴您能在这里看到我的名片，大千世界，相识是偶然和缘分。我是%s%s,祝您生活愉快！", this.company,
				this.getRealName());
	}

	public int getIsShare() {
		return isShare;
	}

	public void setIsShare(int isShare) {
		this.isShare = isShare;
	}

	public Integer getCardId() {
		return cardId;
	}

	public void setCardId(Integer cardId) {
		this.cardId = cardId;
	}

	public Integer getAppId() {
		return appId;
	}

	public void setAppId(Integer appId) {
		this.appId = appId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getSitPhone() {
		return sitPhone;
	}

	public void setSitPhone(String sitPhone) {
		this.sitPhone = sitPhone;
	}

	public String getWechat() {
		return wechat;
	}

	public void setWechat(String wechat) {
		this.wechat = wechat;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getHeadImage() {
		return headImage;
	}

	public void setHeadImage(String headImage) {
		this.headImage = headImage;
	}

	public String getCodeUrl() {
		return codeUrl;
	}

	public void setCodeUrl(String codeUrl) {
		this.codeUrl = codeUrl;
	}

	public String getIntroduce() {
		return introduce;
	}

	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getVoice() {
		return voice;
	}

	public void setVoice(String voice) {
		this.voice = voice;
	}

	public String getVedio() {
		return vedio;
	}

	public void setVedio(String vedio) {
		this.vedio = vedio;
	}

	public Integer getIsEffect() {
		return isEffect;
	}

	public void setIsEffect(Integer isEffect) {
		this.isEffect = isEffect;
	}

	public String getPhotoList() {
		return photoList;
	}

	public void setPhotoList(String photoList) {
		this.photoList = photoList;
	}

	public List<UserImpress> getImpressList() {
		return impressList;
	}

	public void setImpressList(List<UserImpress> impressList) {
		this.impressList = impressList;
	}

	public int getPraiseCount() {
		return praiseCount;
	}

	public void setPraiseCount(int praiseCount) {
		this.praiseCount = praiseCount;
	}

	public String getShareTitle() {
		return shareTitle;
	}

	public void setShareTitle(String shareTitle) {
		this.shareTitle = shareTitle;
	}

	public Integer getSalerMiniUserId() {
		return salerMiniUserId;
	}

	public void setSalerMiniUserId(Integer salerMiniUserId) {
		this.salerMiniUserId = salerMiniUserId;
	}
}
