package com.cofc.pojo;

import java.util.Date;

public class DescoveryReader {
	private Integer dreadId;
	private Integer descoveryId;
	private Integer readerId;
	private Date readTime;
	private String readerHeadImage;
	private String readerNickName;
	private String readerRealName;
	
	public Integer getDreadId() {
		return dreadId;
	}

	public void setDreadId(Integer dreadId) {
		this.dreadId = dreadId;
	}

	public Integer getDescoveryId() {
		return descoveryId;
	}

	public void setDescoveryId(Integer descoveryId) {
		this.descoveryId = descoveryId;
	}


	public Integer getReaderId() {
		return readerId;
	}

	public void setReaderId(Integer readerId) {
		this.readerId = readerId;
	}

	public Date getReadTime() {
		return readTime;
	}

	public void setReadTime(Date readTime) {
		this.readTime = readTime;
	}

	public String getReaderHeadImage() {
		return readerHeadImage;
	}

	public void setReaderHeadImage(String readerHeadImage) {
		this.readerHeadImage = readerHeadImage;
	}

	public String getReaderNickName() {
		return readerNickName;
	}

	public void setReaderNickName(String readerNickName) {
		this.readerNickName = readerNickName;
	}

	public String getReaderRealName() {
		return readerRealName;
	}

	public void setReaderRealName(String readerRealName) {
		this.readerRealName = readerRealName;
	}

	@Override
	public String toString() {
		return "DescoveryReader [dreadId=" + dreadId + ", descoveryId=" + descoveryId + ", readerId=" + readerId
				+ ", readTime=" + readTime + ", readerHeadImage=" + readerHeadImage + ", readerNickName="
				+ readerNickName + ", readerRealName=" + readerRealName + "]";
	}

}
