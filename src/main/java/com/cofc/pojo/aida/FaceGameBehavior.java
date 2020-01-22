package com.cofc.pojo.aida;

import java.util.Date;

public class FaceGameBehavior {
	private int id;
	private String name="";//分配给客户或者终端的名称
	private String ip="";
	private String gameversion="";
	private String type="";
	private String time = "";
	private String content = "";
	private Date createTime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIp() {
		return ip;
	}


	public String getGameversion() {
		return gameversion;
	}

	public void setGameversion(String gameversion) {
		this.gameversion = gameversion;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
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
