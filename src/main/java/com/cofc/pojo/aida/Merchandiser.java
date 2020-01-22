package com.cofc.pojo.aida;

import java.util.Date;

//跟踪订单记录
public class Merchandiser {
    private int id;
    private int salesPersonId;
    private int salesCustomerId;
    private String content;
	private Date createTime;
    
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getSalesPersonId() {
		return salesPersonId;
	}

	public void setSalesPersonId(int salesPersonId) {
		this.salesPersonId = salesPersonId;
	}

	public int getSalesCustomerId() {
		return salesCustomerId;
	}

	public void setSalesCustomerId(int salesCustomerId) {
		this.salesCustomerId = salesCustomerId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
