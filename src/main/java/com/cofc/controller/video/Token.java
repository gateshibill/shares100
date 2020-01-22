package com.cofc.controller.video;

import java.util.Date;


public class Token {
    private long time;
    private int expire;
    private String token;
    
    public  Token(long time,int expire,String token)
    {
    	this.time=time;
    	this.expire=expire;
    	this.token=token;
    }
    public Boolean isExpire()
    {
    	long now = new Date().getTime();
    	if(now>time+expire){
    		return true;
    	}else
    	{
    		return false;
    	}
    }
    
	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public int getExpire() {
		return expire;
	}

	public void setExpire(int expire) {
		this.expire = expire;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}



	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	

}
