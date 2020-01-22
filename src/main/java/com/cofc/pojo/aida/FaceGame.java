package com.cofc.pojo.aida;


import java.util.Date;


public class FaceGame {
	private int id;
	private String name="";//分配给客户或者终端的名称
	private String hostname = "";
	private String encrypt = "";	
	private String ip = "";
	private String gameversion = "";	
	private String osversion="";
	private String osuser = "";
	private int online;//0离线，1在线；
	private int status;//0未启用，1正常使用;2异常，3欠费
	private Date expire;
	private Date lastTime;
	private Date createTime;
	private String note;
	private String notice="";

	public String getNotice() {
		return notice;
	}


	public void setNotice(String notice) {
		this.notice = notice;
	}


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


	public String getHostname() {
		return hostname;
	}


	public void setHostname(String hostname) {
		this.hostname = hostname;
	}


	public String getEncrypt() {
		return encrypt;
	}


	public void setEncrypt(String encrypt) {
		this.encrypt = encrypt;
	}


	public String getIp() {
		return ip;
	}


	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getGameversion() {
		return gameversion;
	}


	public void setGameversion(String gameversion) {
		this.gameversion = gameversion;
	}


	public String getOsversion() {
		return osversion;
	}


	public void setOsversion(String osversion) {
		this.osversion = osversion;
	}


	public String getOsuser() {
		return osuser;
	}


	public void setOsuser(String osuser) {
		this.osuser = osuser;
	}


	public int getOnline() {
		return online;
	}


	public void setOnline(int online) {
		this.online = online;
	}


	public int getStatus() {
		return status;
	}


	public void setStatus(int status) {
		this.status = status;
	}


	public Date getExpire() {
		return expire;
	}


	public void setExpire(Date expire) {
		this.expire = expire;
	}


	public Date getLastTime() {
		return lastTime;
	}


	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}


	public Date getCreateTime() {
		return createTime;
	}


	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}


	public String getNote() {
		return note;
	}


	public void setNote(String note) {
		this.note = note;
	}


	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}

}
