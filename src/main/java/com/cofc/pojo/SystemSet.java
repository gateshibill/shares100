package com.cofc.pojo;

public class SystemSet {
	private Integer setId;
	private Integer isNeedApply;
	private Integer isAutoWithdraw;

	public Integer getSetId() {
		return setId;
	}

	public void setSetId(Integer setId) {
		this.setId = setId;
	}

	public Integer getIsNeedApply() {
		return isNeedApply;
	}

	public void setIsNeedApply(Integer isNeedApply) {
		this.isNeedApply = isNeedApply;
	}

	public Integer getIsAutoWithdraw() {
		return isAutoWithdraw;
	}

	public void setIsAutoWithdraw(Integer isAutoWithdraw) {
		this.isAutoWithdraw = isAutoWithdraw;
	}

	@Override
	public String toString() {
		return "SystemSet [setId=" + setId + ", isNeedApply=" + isNeedApply + ", isAutoWithdraw=" + isAutoWithdraw
				+ "]";
	}

}
