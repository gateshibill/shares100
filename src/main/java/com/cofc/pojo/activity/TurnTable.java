package com.cofc.pojo.activity;

import java.util.Date;
import java.util.List;
import java.util.Random;

//转盘游戏
public class TurnTable {
	private int id;
	private int appId;
	private String name;
	private String headImage;
	private int price;
	private int status;
	private Date createTime;
	private String note;
    private List<TurnTableColumn> list;
	
    public void  play(TurnTableRecord turnTableRecord)
    {
    	int total=0;
    	for(TurnTableColumn ttc:list){
    		ttc.setMin(total);
    		total+=ttc.getRate();
    		ttc.setMax(total);
    	}
    	int index = new Random().nextInt(total);
    	for(TurnTableColumn ttc:list){
    		if(index>=ttc.getMin()&&index<ttc.getMax()){
    			turnTableRecord.setTurntableId(this.id);
    			turnTableRecord.setPrizeType(ttc.getPrizeType());
    			turnTableRecord.setPrizeNum(ttc.getPrizeNum());
    			turnTableRecord.setWin(1);
    			turnTableRecord.setColumnId(ttc.getId());
    			return;
    		}
    	}
    }
    
    
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAppId() {
		return appId;
	}

	public void setAppId(int appId) {
		this.appId = appId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHeadImage() {
		return headImage;
	}

	public void setHeadImage(String headImage) {
		this.headImage = headImage;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public List<TurnTableColumn> getList() {
		return list;
	}

	public void setList(List<TurnTableColumn> list) {
		this.list = list;
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
