package com.cofc.pojo;

import java.util.List;

public class AllParentTag {
	private Integer tagType;
	private List<TagCommon> tagList;

	public Integer getTagType() {
		return tagType;
	}

	public void setTagType(Integer tagType) {
		this.tagType = tagType;
	}

	public List<TagCommon> getTagList() {
		return tagList;
	}

	public void setTagList(List<TagCommon> tagList) {
		this.tagList = tagList;
	}

	@Override
	public String toString() {
		return "AllParentTag [tagType=" + tagType + ", tagList=" + tagList + "]";
	}
}
