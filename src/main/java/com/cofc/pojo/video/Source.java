package com.cofc.pojo.video;

import java.util.Date;

public class Source {
	private int id;
	private String ip;
	private String name;
	private int vodId;
	private String vodName;
	private int subId;
	private String subName;
	private Date createTime;
	private Date lastTime;
	private int status;//0可用，1关闭；2为无效；3为过期

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getVodId() {
		return vodId;
	}

	public void setVodId(int vodId) {
		this.vodId = vodId;
	}

	public String getVodName() {
		return vodName;
	}

	public void setVodName(String vodName) {
		this.vodName = vodName;
	}


	public int getSubId() {
		return subId;
	}

	public void setSubId(int subId) {
		this.subId = subId;
	}

	public String getSubName() {
		return subName;
	}

	public void setSubName(String subName) {
		this.subName = subName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getLastTime() {
		return lastTime;
	}

	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}
