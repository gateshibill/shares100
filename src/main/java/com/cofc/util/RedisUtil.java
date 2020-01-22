package com.cofc.util;

import com.alibaba.fastjson.JSONObject;

import redis.clients.jedis.Jedis;

public class RedisUtil {

	public static Jedis jedis = new Jedis("175.6.250.253");
	
	
	public static void lpush(String key, Object maket) {
		String json = JsonUtil.bulidObjectJson(maket);
		jedis.lpush(key, json);
		jedis.expire(key, 60*60*24*365);
	}
	
	public static void lpushK(String key, Object maket) {
		String json = JsonUtil.bulidObjectJson(maket);
		jedis.lpush(key, json);
		jedis.expire(key, 60*60*24*365);
	}

	public static void lpushT(String key, JSONObject obj) {
		String json = obj.toString();
		jedis.set(key, json);
		jedis.expire(key, 60*60*24*365);
	}
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String json="\"";
		String cmd=json.replaceAll("\\\\", "");
		System.out.println(cmd);
		
		
//		List<String> dlistStr=jedis.lrange("600036.SH" + "_D", 0, -1);
//		for(String msg:dlistStr)
//		{
//			System.out.println(msg);
//		}
		String ad = "?clientid=123,token=12313";
		json="{"+ad.replace("?", "") +"}";
		json=json.replace("=", ":");
		JSONObject jo = (JSONObject) JSONObject.parse(json);
		System.out.println(jo.get("clientid"));
		

		
	}
}
