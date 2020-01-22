/*
 * ====================================================================
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 *
 */
package com.cofc.util.wxpay;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.KeyStore;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import com.cofc.controller.shopping.GetOrderNo;
import com.cofc.pojo.ApplicationCommon;
import com.sun.xml.internal.bind.v2.schemagen.xmlschema.Appinfo;

/**
 * 微信退款
 * This example demonstrates how to create secure connections with a custom SSL
 * context.
 */
public class ClientCustomSSL {
	public static Logger log = Logger.getLogger("ClientCustomSSL");
    /**
     * 用于企业向微信用户个人付款 
     * @param path
     * @param orderNo
     * @param openid
     * @param fee
     * @param ip
     * @param realName
     * @param appId
     * @param mchId
     * @return
     * @throws Exception
     */
	public static Map<String, String> companyPay(String path, String orderNo, String openid, double fee,String ip,
			String realName, String appId, String mchId) throws Exception {
		Map<String, String> rmessage = new HashMap<String, String>();
		KeyStore keyStore = KeyStore.getInstance("PKCS12");
		FileInputStream instream = new FileInputStream(new File(path));
		try {
			keyStore.load(instream, mchId.toCharArray());
		} finally {
			instream.close();
		}

		// Trust own CA and all self-signed certs
		SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, mchId.toCharArray()).build();
		// Allow TLSv1 protocol only
		SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new String[] { "TLSv1" }, null,
				SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
		CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
		try {
			String characterEncoding = "utf-8";
			SortedMap<String, Object> parameters = new TreeMap<String, Object>();
			String noncestr = RandomCode.getUUID();
			parameters.put("mch_appid", appId);// 企业号
			parameters.put("mchid", mchId);// 商户号
			parameters.put("nonce_str", noncestr);// 随机字符串

			// String payedName = URLEncoder.encode(payName, "utf-8");
			parameters.put("desc", "提现");// 商品描述
			parameters.put("partner_trade_no", orderNo);// 商户订单号
			parameters.put("openid", openid);// 收款方openid
			parameters.put("check_name", "NO_CHECK");// NO_CHECK：不校验真实姓名，FORCE_CHECK：强校验真实姓名
//			parameters.put("re_user_name", URLEncoder.encode(realName, "UTF-8"));

			// 金额只能传int
			int totalFee = (int) (fee * 100);
			parameters.put("amount", totalFee);// 金额 ,调用接口时传入
			parameters.put("spbill_create_ip", ip);// 终端IP,调用接口时传入

			WeiXinPayConfig config = new WeiXinPayConfig();
			// 获取签名
			String sign = PayCommonUtil.createSign(config, characterEncoding, parameters);
			parameters.put("sign", sign);
			// log.info("最终参数是："+parameters.toString());
			// 转成xml格式
			String xmlResult = "";
			StringBuffer sb = new StringBuffer();
			sb.append("<xml>");
			for (String key : parameters.keySet()) {
				System.out.println(key + "========" + parameters.get(key));
				sb.append("<" + key + ">" + parameters.get(key) + "</" + key + ">");
			}
			sb.append("</xml>");
			// xmlResult = sb.toString();
			xmlResult = new String(sb.toString().getBytes("UTF-8"));
			// System.out.println(xmlResult);
			log.info("统一下单为：" + xmlResult);
			HttpPost httpget = new HttpPost("https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers");
			BasicHttpEntity postentity = new BasicHttpEntity();
			InputStream in_withcode = new ByteArrayInputStream(xmlResult.getBytes());
			postentity.setContent(in_withcode);
			httpget.setEntity(postentity);
			CloseableHttpResponse response = httpclient.execute(httpget);
			
			try {
				HttpEntity entity = response.getEntity();

				// System.out.println("----------------------------------------");
				// System.out.println(response.getStatusLine());
				if (entity != null) {
					// System.out.println("Response content length: " +
					// entity.getContentLength());
					BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(entity.getContent()));
					StringBuffer buffer = new StringBuffer();  
					String text = "";
					while ((text = bufferedReader.readLine()) != null) {
						buffer.append(text);
					}
//					System.out.println("text----" + new String(buffer.toString().getBytes(), "utf-8"));
//					System.out.println("rmessage----" + rmessage);
					rmessage = WXPayUtil.xmlToMap(new String(buffer.toString().getBytes(), "utf-8"));
					log.info("返回结果:"+rmessage);
				}
				EntityUtils.consume(entity);
			} finally {
				
				response.close();
			}
		} finally {
			
			httpclient.close();
		}
		return rmessage;
	}
	/**
	 * 微信原路退返
	 * @param path
	 * @param orderNo
	 * @param fee
	 * @param appId
	 * @param mchId
	 * @return
	 * @throws Exception
	 */
	public static Map<String, String> refund(String path, String orderNo,double fee,ApplicationCommon info,Integer orderSource) throws Exception{
		Map<String, String> rmessage = new HashMap<String, String>();
		KeyStore keyStore = KeyStore.getInstance("PKCS12");
		FileInputStream instream = new FileInputStream(new File(path));
		String mchId = info.getMchid();
		try {
			keyStore.load(instream, mchId.toCharArray());
		} finally {
			instream.close();
		}
		// Trust own CA and all self-signed certs
		SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, mchId.toCharArray()).build();
		// Allow TLSv1 protocol only
		SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new String[] { "TLSv1" }, null,
				SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
		CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
		try {
			String characterEncoding = "utf-8";
			SortedMap<String, Object> parameters = new TreeMap<String, Object>();
			String noncestr = RandomCode.getUUID();
			if(orderSource == 2){
				parameters.put("appid", info.getWxServiceAppId());// 微信服务号appid ： 用于官网订单 退款
			}else if(orderSource == 3){
				parameters.put("appid", info.getWxOpenAppId());// 微信开放平台appid ： 用于app订单 退款
			}else{
				parameters.put("appid", info.getAppId());// 微信小程序  ：用于小程序订单 退款
			}
			parameters.put("mch_id", mchId);// 商户号
			parameters.put("nonce_str", noncestr);// 随机字符串
			parameters.put("out_trade_no", orderNo);//商户订单号
			parameters.put("out_refund_no", GetOrderNo.getRefundNoByUUId("vijtk"));//退款单号

			// 金额只能传int
			int totalFee = (int) (fee * 100);
			parameters.put("total_fee", totalFee);// 订单金额 ,调用接口时传入
			parameters.put("refund_fee", totalFee);// 退款金额,调用接口时传入

			WeiXinPayConfig config = new WeiXinPayConfig();
			config.setApiKey(info.getApiKey());
			// 获取签名
			String sign = PayCommonUtil.createSign(config, characterEncoding, parameters);
			parameters.put("sign", sign);
			log.info("最终参数是："+parameters.toString());
			// 转成xml格式
			String xmlResult = "";
			StringBuffer sb = new StringBuffer();
			sb.append("<xml>");
			for (String key : parameters.keySet()) {
				System.out.println(key + "========" + parameters.get(key));
				sb.append("<" + key + ">" + parameters.get(key) + "</" + key + ">");
			}
			sb.append("</xml>");
			// xmlResult = sb.toString();
			xmlResult = new String(sb.toString().getBytes("UTF-8"));
			// System.out.println(xmlResult);
			log.info("统一下单为：" + xmlResult);
			HttpPost httpget = new HttpPost("https://api.mch.weixin.qq.com/secapi/pay/refund");
			BasicHttpEntity postentity = new BasicHttpEntity();
			InputStream in_withcode = new ByteArrayInputStream(xmlResult.getBytes());
			postentity.setContent(in_withcode);
			httpget.setEntity(postentity);
			CloseableHttpResponse response = httpclient.execute(httpget);
			
			try {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(entity.getContent()));
					StringBuffer buffer = new StringBuffer();  
					String text = "";
					while ((text = bufferedReader.readLine()) != null) {
						buffer.append(text);
					}
					rmessage = WXPayUtil.xmlToMap(new String(buffer.toString().getBytes(), "utf-8"));
					log.info("返回结果:"+rmessage);
				}
				EntityUtils.consume(entity);
			} finally {
				
				response.close();
			}
		} finally {
			
			httpclient.close();
		}
		return rmessage;
	}
	
}
