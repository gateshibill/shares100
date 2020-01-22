package com.cofc.pojo;

public class Buyer {
	private Integer buyerId;
	private String buyerNickName;
	private String buyerRealName;
	private String buyerHeadImage;

	public Integer getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(Integer buyerId) {
		this.buyerId = buyerId;
	}

	public String getBuyerNickName() {
		return buyerNickName;
	}

	public void setBuyerNickName(String buyerNickName) {
		this.buyerNickName = buyerNickName;
	}

	public String getBuyerRealName() {
		return buyerRealName;
	}

	public void setBuyerRealName(String buyerRealName) {
		this.buyerRealName = buyerRealName;
	}

	public String getBuyerHeadImage() {
		return buyerHeadImage;
	}

	public void setBuyerHeadImage(String buyerHeadImage) {
		this.buyerHeadImage = buyerHeadImage;
	}

	@Override
	public String toString() {
		return "Buyer [buyerId=" + buyerId + ", buyerNickName=" + buyerNickName + ", buyerRealName=" + buyerRealName
				+ ", buyerHeadImage=" + buyerHeadImage + "]";
	}

}
