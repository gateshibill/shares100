package com.cofc.util;

import java.io.Serializable;
/**
 * 分页工具类
 * @author Jerry
 * 
 */
public class PageUtil implements Serializable {

private static final long serialVersionUID = 1L;
	
	private int pageNo;//当前页数
	private int pageSize;//每页显示的条数
	private int totalCount;//总的记录数
	private int totalPageCount;//总的页数
	private int startPos;//开始位置
	
	private PageUtil(){}
	
	/**
	 * 传入总记录数和当前页，和每页显示条数
	 * @param totalCount
	 * @param pageNo
	 */
	public PageUtil(int totalCount,int pageNo,int pageSize) {
		this.pageNo = pageNo;
		this.pageSize = pageSize;
		this.totalCount = totalCount;
	}
	
	/*
	 * 取得总页数，即总记录数/每页显示条数
	 */
	public int getTotalPageCount() {
		totalPageCount = getTotalCount()/getPageSize();
		return (totalCount%pageSize==0)?totalPageCount:totalPageCount+1;
	}
	
	public void setTotalPageCount(int totalpageCount) {
		this.totalPageCount = totalpageCount;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	/**
	 * 取起始位置
	 * @return
	 */
	public int getStartPos() {
		return (pageNo - 1) * pageSize;
	}

	public void setStartPos(int startPos) {
		this.startPos = startPos;
	}
	
	
}
