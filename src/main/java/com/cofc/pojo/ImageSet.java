package com.cofc.pojo;

public class ImageSet {
	private Integer setId;
	private Integer headWidth;
	private Integer headHeight;
	private Integer uimageWidth;
	private Integer uimageHeight;

	public Integer getSetId() {
		return setId;
	}

	public void setSetId(Integer setId) {
		this.setId = setId;
	}

	public Integer getHeadWidth() {
		return headWidth;
	}

	public void setHeadWidth(Integer headWidth) {
		this.headWidth = headWidth;
	}

	public Integer getHeadHeight() {
		return headHeight;
	}

	public void setHeadHeight(Integer headHeight) {
		this.headHeight = headHeight;
	}

	public Integer getUimageWidth() {
		return uimageWidth;
	}

	public void setUimageWidth(Integer uimageWidth) {
		this.uimageWidth = uimageWidth;
	}

	public Integer getUimageHeight() {
		return uimageHeight;
	}

	public void setUimageHeight(Integer uimageHeight) {
		this.uimageHeight = uimageHeight;
	}

	@Override
	public String toString() {
		return "ImageSet [setId=" + setId + ", headWidth=" + headWidth + ", headHeight=" + headHeight + ", uimageWidth="
				+ uimageWidth + ", uimageHeight=" + uimageHeight + "]";
	}

}
