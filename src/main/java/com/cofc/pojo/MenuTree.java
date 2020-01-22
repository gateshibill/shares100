package com.cofc.pojo;

import java.util.List;

public class MenuTree {
	private Integer menuId;
	private String title;
	private String href;
	private String icon;
	private Integer parentId;
	private Integer menuLevel;
	private Integer orderId;
	private List<MenuTree> children;
	private Integer hasAuthority;
	
	public Integer getMenuId() {
		return menuId;
	}

	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public Integer getMenuLevel() {
		return menuLevel;
	}

	public void setMenuLevel(Integer menuLevel) {
		this.menuLevel = menuLevel;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public List<MenuTree> getChildren() {
		return children;
	}

	public void setChildren(List<MenuTree> children) {
		this.children = children;
	}
	@Override
	public String toString() {
		return "MenuTree [menuId=" + menuId + ", title=" + title + ", href=" + href + ", icon=" + icon + ", parentId="
				+ parentId + ", menuLevel=" + menuLevel + ", orderId=" + orderId + ", children=" + children + "]";
	}

	public Integer getHasAuthority() {
		return hasAuthority;
	}

	public void setHasAuthority(Integer hasAuthority) {
		this.hasAuthority = hasAuthority;
	}
}
