package com.cofc.util;

import redis.clients.jedis.Jedis;

public class Tredis {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
        Jedis jedis = new Jedis("182.61.34.125");
        System.out.println("连接成功");
        //设置 redis 字符串数据
        jedis.set("runoobkey", "www.runoob.com");
        //jedis.set("d", "dd", "NX", "EX", 100);
        System.out.println("redis 存储的字符串为: "+ jedis.get("d"));
        // 获取存储的数据并输出
        System.out.println("redis 存储的字符串为: "+ jedis.get("runoobkey"));

	}

}
