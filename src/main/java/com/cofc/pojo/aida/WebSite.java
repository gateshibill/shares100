package com.cofc.pojo.aida;

import java.util.Date;
import java.util.List;

public class WebSite {
	private Integer id;
	private String cname;
	private String banner;	
	private String cintro;
	private String pintro;
	private String cfind;
	private String kehu;
	private String team;
	private String cphone;
	private String caddress;
	private Integer appId;
	private Integer isEffect;
	private Date createTime;
	private String logo;
	private String companyDetail;
	private List<Position> positionlist;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public String getBanner() {
		return banner;
	}
	public void setBanner(String banner) {
		this.banner = banner;
	}
	public String getCintro() {
		return cintro;
	}
	public void setCintro(String cintro) {
		this.cintro = cintro;
	}
	public String getPintro() {
		return pintro;
	}
	public void setPintro(String pintro) {
		this.pintro = pintro;
	}
	public String getCfind() {
		return cfind;
	}
	public void setCfind(String cfind) {
		this.cfind = cfind;
	}
	public String getKehu() {
		return kehu;
	}
	public void setKehu(String kehu) {
		this.kehu = kehu;
	}
	public String getTeam() {
		return team;
	}
	public void setTeam(String team) {
		this.team = team;
	}
	public String getCphone() {
		return cphone;
	}
	public void setCphone(String cphone) {
		this.cphone = cphone;
	}
	public String getCaddress() {
		return caddress;
	}
	public void setCaddress(String caddress) {
		this.caddress = caddress;
	}
	public Integer getAppId() {
		return appId;
	}
	public void setAppId(Integer appId) {
		this.appId = appId;
	}
	public Integer getIsEffect() {
		return isEffect;
	}
	public void setIsEffect(Integer isEffect) {
		this.isEffect = isEffect;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public List<Position> getPositionlist() {
		return positionlist;
	}
	public void setPositionlist(List<Position> positionlist) {
		this.positionlist = positionlist;
	}
	public String getCompanyDetail() {
		return companyDetail;
	}
	public void setCompanyDetail(String companyDetail) {
		this.companyDetail = companyDetail;
	}
}
