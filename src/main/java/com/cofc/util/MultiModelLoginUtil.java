package com.cofc.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * PC端通过微信的记录标识
 * 
 * @author chenxiangyou
 *
 */
public final class MultiModelLoginUtil {

	private static Map<String, String> userLoginMap = null;
	public static Logger log = Logger.getLogger("MultiModelLoginUtil");

	private MultiModelLoginUtil() {
	};

	private static volatile MultiModelLoginUtil instance;

	public static MultiModelLoginUtil getInstance() {
		if (instance == null) {
			synchronized (MultiModelLoginUtil.class) {
				if (instance == null) {
					instance = new MultiModelLoginUtil();
					userLoginMap = new HashMap<String, String>();
				}
			}
		}
		return instance;
	}

	// 插入openId
	public synchronized String putLoginUUID(String uuid, String openId) {
		log.debug(String.format("putLoginUUID():uuid:%s|openId:%s", uuid, openId));
		log.debug(String.format("before  putLoginUUID userLoginMap.size:%s", userLoginMap.size()));
		if (userLoginMap.size() > 3000) {
			userLoginMap.clear();
		}
		userLoginMap.put(uuid, openId);
		log.debug(String.format("after  putLoginUUID userLoginMap.size:%s", userLoginMap.size()));
		log.debug(String.format("putLoginUUID() end:uuid:%s|openId:%s", uuid, openId));
		return openId;
	}

	// 查看是否登陆
	public synchronized String getLoginUUID(String uuid) {
		log.debug(String.format("getLoginUUID():uuid:%s", uuid));
		log.debug(String.format("after get getLoginUUID userLoginMap.size:%s", userLoginMap.size()));
		String openId = userLoginMap.get(uuid);
		if (openId == null || openId.equals("")) {
			return "";
		}
		log.debug(String.format("getLoginUUID() end:uuid:%s", uuid));
		return openId;
	}

	// 移除对应的
	public synchronized boolean removeLoginUUID(String uuid) {
		log.debug(String.format("removeLoginUUID():uuid:%s", uuid));
		userLoginMap.remove(uuid);
		return true;
	}

	public synchronized Map<String, String> getMap() {
		log.debug(String.format("before getMap userLoginMap.size:%s", userLoginMap.size()));
		return userLoginMap;
	}

	public static void main(String[] args) {
		String uuid = "3e69bd5f1e564987";
		String openId = "oUEnr4gHPZkjbf4P-drM_1LS1h30";
	    System.out.println(MultiModelLoginUtil.getInstance().getMap().size());
		MultiModelLoginUtil.getInstance().putLoginUUID(uuid, openId);
		String ip = MultiModelLoginUtil.getInstance().getLoginUUID(uuid);
		if (ip == null || ip.equals("")) {
			System.out.println("ddd");
		}
		System.out.println(MultiModelLoginUtil.getInstance().getLoginUUID(uuid));
		System.out.println(MultiModelLoginUtil.getInstance().getMap().size());
	}
}
