package com.cofc.util.wxpay;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.log4j.Logger;

import com.cofc.util.MD5Util;

public class PayCommonUtil {
    public static Logger log = Logger.getLogger("PayCommonUtil"); 
	/****
	 * 生成微信签名
	 * 
	 * @param config
	 * @param characterEncoding
	 * @param parameters
	 * @return
	 */
	public static String createSign(WeiXinPayConfig config, String characterEncoding,
			SortedMap<String, Object> parameters) {
		StringBuffer sb = new StringBuffer();

		Set es = parameters.entrySet();
		Iterator it = es.iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String k = (String) entry.getKey();
			Object v = entry.getValue();
			if (null != v && !"".equals(v) && !"sign".equals(k) && !"key".equals(k)) {
				sb.append(k + "=" + v + "&");
			}
		}
		log.info("apikey==="+config.getApiKey());
		sb.append("key="+config.getApiKey());
		log.info("signstr---"+sb.toString());
		String sign = MD5Util.MD5Encode(sb.toString(), characterEncoding).toUpperCase();
		return sign;
	}

//	public static String createSign(WeiXinConfig config, String characterEncoding, Map<String, String> parameters) {
//		StringBuffer sb = new StringBuffer();
//
//		Set es = parameters.entrySet();
//		Iterator it = es.iterator();
//		while (it.hasNext()) {
//			Map.Entry entry = (Map.Entry) it.next();
//			String k = (String) entry.getKey();
//			Object v = entry.getValue();
//			if (null != v && !"".equals(v) && !"sign".equals(k) && !"key".equals(k)) {
//				sb.append(k + "=" + v + "&");
//			}
//		}
//		sb.append("key=" + config.getApiKey());
//		String sign = MD5Util.MD5Encode(sb.toString(), characterEncoding).toUpperCase();
//		return sign;
//	}


	/*****
	 * 生成xml格式参数
	 * 
	 * @param parameters
	 * @return
	 */
	public static String getRequestXml(SortedMap<Object, Object> parameters) {
		StringBuffer sb = new StringBuffer();
		sb.append("<xml>");
		Set es = parameters.entrySet();
		Iterator it = es.iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String k = (String) entry.getKey();
			String v = (String) entry.getValue();
			if ("attach".equalsIgnoreCase(k) || "body".equalsIgnoreCase(k) || "sign".equalsIgnoreCase(k)) {
				sb.append("<" + k + ">" + "<![CDATA[" + v + "]]></" + k + ">");
			} else {
				sb.append("<" + k + ">" + v + "</" + k + ">");
			}
		}
		sb.append("</xml>");
		return sb.toString();
	}

	/****
	 * 生成xml
	 * 
	 * @param return_code
	 * @param return_msg
	 * @return
	 */
	public static String setXML(String return_code, String return_msg) {
		return "<xml><return_code><![CDATA[" + return_code + "]]></return_code><return_msg><![CDATA[" + return_msg
				+ "]]></return_msg></xml>";

	}
    public static void logResult(String sWord,String path) {
    	BufferedWriter bw = null;
        try {
            bw = new BufferedWriter (new OutputStreamWriter (new FileOutputStream (path + "wxpay_log_.txt", true), "UTF-8"));
            bw.write("\n"+sWord);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bw != null) {
                try {
                	bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    /** 
     * 除去数组中的空值和签名参数
     * @param sArray 签名参数组
     * @return 去掉空值与签名参数后的新签名参数组
     */
    public static SortedMap<String, Object> paraFilter(Map<String, String> sArray) {

        SortedMap<String, Object> result = new TreeMap<String, Object>();

        if (sArray == null || sArray.size() <= 0) {
            return result;
        }

        for (String key : sArray.keySet()) {
            String value = sArray.get(key);
            if (value == null || value.equals("") || key.equalsIgnoreCase("sign")
                || key.equalsIgnoreCase("sign_type")|| key.equalsIgnoreCase("key")) {
                continue;
            }
            result.put(key, value);
        }

        return result;
    }
    public static void main(String[] args) throws IOException {
		String path ="C:\\Users\\wang\\Desktop\\新建文本文档.txt";
		BufferedReader r = new BufferedReader(new FileReader(path));
		BufferedWriter bw = new BufferedWriter (new OutputStreamWriter (new FileOutputStream (path, true), "UTF-8"));
	}
}
