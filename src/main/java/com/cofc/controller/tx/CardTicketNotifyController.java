package com.cofc.controller.tx;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cofc.pojo.tx.Agent;
import com.cofc.pojo.tx.CardOrder;
import com.cofc.pojo.tx.CardOrderBehavior;
import com.cofc.service.tx.AgentService;
import com.cofc.service.tx.CardOrderBehaviorService;
import com.cofc.service.tx.CardOrderService;
import com.cofc.util.BaseUtil;
import com.cofc.util.HttpPost;
import com.cofc.util.JsonUtil;
import com.cofc.util.MD5Util;

/**
 * 数字商品平台--卡券
 * @author menghaoran
 *
 */
@Controller
@RequestMapping("/tx")
public class CardTicketNotifyController extends BaseUtil{
	public final static String AGENCY_CODE = "SZGFCZTEST";//代理商编码
	public final static String AUTH_CODE = "2fa7b69c14d1b7e21c64f9640e72fc36";//授权码
	@Resource
	private AgentService agentService;//代理
	@Resource
	private CardOrderService cardOrderService;//卡券订单
	@Resource
	private CardOrderBehaviorService cardOrderBehaviorService;//卡券请求行为
	public static Logger log = Logger.getLogger("CardTicketNotifyController");
	/**
	 * 回调接口
	 * @param response
	 * @param request
	 * @throws Exception 
	 */
	@RequestMapping("/videoCardOrderNotify")
	public void  videoCardOrderNotify(HttpServletResponse response,HttpServletRequest request) throws Exception{
		//获取回调的值
		InputStream is = request.getInputStream();
		String result = IOUtils.toString(is, "UTF-8");
		//String result = "{\"code\":0,\"msg\":\"提取成功\",\"goodsName\":\"测试商品\",\"price\":9.5,\"orderNum\":\"201901101902050863725112\",\"platOrderNum\":\"UD2201901101902055654838982\",\"cardList\":[{\"cardPwd\":\"Wt167oPTuvMkSWxh8pEXJw==\",\"expiryDate\":\"2018-10-10\"}]}";
		log.info("videoCardOrderNotify(): result = "+result);
		JSONObject obj = JSONObject.parseObject(result);
		String code = obj.getInteger("code").toString();
		if(code.equals("0")){//获取数据成功
			String orderNum = obj.getString("orderNum");
			//根据订单查询行为
			CardOrderBehavior behavior = cardOrderBehaviorService.getBehaviorInfo(orderNum, null, null);
			Agent agent = agentService.getAgentInfo(null, behavior.getAgentCode(), null, null);
			String authCode = agent.getAuthCode();//授权码
			String agentauth1 = authCode.substring(0, 16);
			log.info("agentauth1:"+agentauth1);
			String agentauth2 = authCode.substring(16, 32);
			log.info("agentauth2:"+agentauth2);
			String auth1 = AUTH_CODE.substring(0, 16);
			String auth2 = AUTH_CODE.substring(16, 32);
			System.out.println(auth1+"----"+auth2);
			String cardList = obj.getString("cardList");
			JSONArray array = JSONArray.parseArray(cardList);
			JSONObject object = null;
			for (int i = 0; i < array.size(); i++) {
				object = array.getJSONObject(i);
				String cardPwd = object.getString("cardPwd");
				//解密
				String jiemiPwd = AESCBC.getInstance().decrypt(cardPwd, "utf-8", auth1, auth2);
				//加密
				String  jiamiPwd = AESCBC.getInstance().encrypt(jiemiPwd, "utf-8", agentauth1, agentauth2);
				object.put("cardPwd", jiamiPwd);
			}
			CardOrder order = new CardOrder();
			order.setAgentCode(agent.getAgentCode());
			order.setQuantity(behavior.getQuantity());
			order.setAgentOrderNum(behavior.getAgentOrderNum());
			order.setOrderNum(orderNum);
			order.setCardList(array.toString());
			order.setGoodsCode(behavior.getGoodsCode());
			order.setGoodsName(obj.getString("goodsName"));
			order.setPlatOrderNum(obj.getString("platOrderNum"));
			order.setPrice(obj.getDouble("price"));
			order.setCreateTime(new Date());
			order.setIsUse(0);
			cardOrderService.addCardData(order);
			output(response, JsonUtil.buildFalseJson("0", "请求成功"));
		}else{
			output(response,JsonUtil.buildFalseJson("1", result));
		}
	   
	}
	/**
	 * 获取卡券信息
	 * @param response
	 * @param agencyCode:代理商编号
	 * @param orderNum：代理商订单号（最大长度32,不允许重复）
	 * @param goodsCode:商品编码
	 * @param quantity:商品数量
	 */
	@RequestMapping("/getCardInfo")
	public void getCardInfo(HttpServletResponse response,String orderNum,String goodsCode,
			int quantity){
			if(orderNum == null || orderNum.equals("")){
				log.error("getCardInfo():orderNum is null");
				output(response,JsonUtil.buildFalseJson("1", "代理商订单号非法"));
			}else{
				if(goodsCode == null || goodsCode.equals("")){
					log.error("getCardInfo():goodsCode is null");
					output(response,JsonUtil.buildFalseJson("1", "商品编码非法"));
				}else{
					if(quantity > 0){
						//组装数据
						long time = new Date().getTime()/1000;
						String timeStr = String.valueOf(time);
						log.info("getCardInfo():timeStr = "+timeStr);
						String str = AGENCY_CODE+AUTH_CODE+goodsCode+orderNum+timeStr;
						String sign = MD5Util.MD5Encode(str,"utf-8");
						log.info("getCardInfo():sign = "+sign);
					    JSONObject json = new JSONObject();
					    json.put("agencyCode", AGENCY_CODE);
					    json.put("timestamp", timeStr);
					    json.put("orderNum", orderNum);
					    json.put("goodsCode", goodsCode);
					    json.put("sign", sign);
					    json.put("quantity", quantity);
					    String url = "http://recharge.masxr.cn/api/recharge/getCards";
					    String result = HttpPost.sendPost(url, json.toJSONString());
					    log.info("getCardInfo():result = "+result);
					    JSONObject obj = JSONObject.parseObject(result);
						String code = obj.getInteger("code").toString();
						if(code.equals("0")){
						    log.info("getCardInfo():代理商获取卡券商品信息");
							output(response,JsonUtil.buildFalseJson("0", "代理商获取卡券商品信息成功"));
						}else{
							output(response, JsonUtil.buildFalseJson("1", "代理商获取卡券商品信息失败"));
						}
					    
					}else{
						log.error("getCardInfo():quantity is null or quantity 不是正整数");
						output(response,JsonUtil.buildFalseJson("1", "商品数量非法"));
					}
				}
		}
	}
	
	/*******************************下级代理商请求********************************************/
	/**
	 * 代理商请求卡券，保存在数据库
	 * @param request
	 * @param response
	 {
		"agencyCode": " XXXXXX",
		"orderNum": " TKWEN00112313123",
		"goodsCode": "test1",
        "quantity": 1,
        "timestamp":"15233",
        "sign":"xxxxxx"
     }
	 * @throws IOException 
	 */
	@RequestMapping("/agentCardList")
	public void agentCardList(HttpServletRequest request,HttpServletResponse response) throws IOException{
		//获取值
		InputStream is = request.getInputStream();
	    String result = IOUtils.toString(is, "UTF-8");
	    log.info("agentCardList():代理商传递的值,result="+result);
	    if(result == null || result.equals("")){
		    log.info("agentCardList():代理商传递的值,result is null");
	    	output(response,JsonUtil.buildFalseJson("10003", "参数值有误"));
	    }else{
		    JSONObject obj = JSONObject.parseObject(result);
		    String agentCode = obj.getString("agencyCode");
		    if(agentCode == null || agentCode.equals("")){
			    log.info("agentCardList():代理商传递的值,agentCode is null");
		    	output(response,JsonUtil.buildFalseJson("10002", "缺少必要的参数"));
		    }else{
		    	String agentOrderNum = obj.getString("orderNum");
		    	if(agentOrderNum == null || agentOrderNum.equals("")){
				    log.info("agentCardList():代理商传递的值,orderNum is null");
		    		output(response,JsonUtil.buildFalseJson("10002", "缺少必要的参数"));
		    	}else{
		    		String goodsCode = obj.getString("goodsCode");
		    		if(goodsCode == null || goodsCode.equals("")){
		    			log.info("agentCardList():代理商传递的值,goodsCode is null");
			    		output(response,JsonUtil.buildFalseJson("10007", "商品类型错误"));
		    		}else{
		    			int quantity = obj.getIntValue("quantity");
			    		if(quantity > 0){
			    			String timestamp = obj.getString("timestamp");
			    			if(timestamp == null || timestamp.equals("")){
			    				log.info("agentCardList():代理商传递的值,timestamp is null");
					    		output(response,JsonUtil.buildFalseJson("10002", "缺少必要的参数"));
			    			}else{
			    				String agentSign = obj.getString("sign");
			    				log.info("签名---agentSize="+agentSign);
			    				if(agentSign == null || agentSign.equals("")){
			    					log.info("agentCardList():代理商传递的值,sign is null");
						    		output(response,JsonUtil.buildFalseJson("10002", "缺少必要的参数"));
			    				}else{
			    					//判断代理商的合法性
					    			Agent agent = agentService.getAgentInfo(null, agentCode, 1, null);
					    			if(agent == null){
					    				log.info("agentCardList():代理商不存在,agent is null");
							    		output(response,JsonUtil.buildFalseJson("10004", "代理商不存在"));
					    			}else{
					    				//校验数据完整性：签名
					    				String newsignstr = agentCode+agent.getAuthCode()+goodsCode+agentOrderNum+timestamp;
					    				String md5AgentSign = MD5Util.MD5Encode(newsignstr, "utf-8");
					    				log.info("校验签名---md5AgentSign="+md5AgentSign);
					    				if(!md5AgentSign.equals(agentSign)){//校验+
					    					log.info("agentCardList():签名错误");
								    		output(response,JsonUtil.buildFalseJson("10006", "签名错误"));
					    				}else{
					    					if(quantity > agent.getMaxQuantity()){
						    					log.info("agentCardList():请求数量超过限制,quantity = "+quantity+",maxQuantity = ");
									    		output(response,JsonUtil.buildFalseJson("10010", "请求数量超过限制"));
							    			}else{
							    				//IP鉴权
							    				String IP = request.getRemoteAddr();
							    				if(agent.getAgentIP() == null || agent.getAgentIP().equals("")){
							    					log.info("agentCardList():agent.getAgentIP() is null");
										    		output(response,JsonUtil.buildFalseJson("10005", "IP鉴权失败"));
							    				}else{
							    					 if(!agent.getAgentIP().equals(IP)){
							    						log.info("agentCardList():agent.getAgentIP() neq request.getRemoteAddr()");
												    	output(response,JsonUtil.buildFalseJson("10005", "IP鉴权失败"));
							    					 }else{
							    						 CardOrder order = new CardOrder();
							    						 order.setAgentCode(agentCode);
							    						 order.setAgentOrderNum(agentOrderNum);
							    						 List<CardOrder> lists = cardOrderService.getCardDataList(order, null, null);
							    						 if(lists.size() > 0){
									    					 log.info("agentCardList():订单号重复");
							    							 output(response,JsonUtil.buildFalseJson("10009", "订单号重复"));
							    						 }else{
							    							    
							    							    String orderNum = getOrderNumber();//一级代理商的订单号
							    							   //插入数据行为数据
							    							    CardOrderBehavior behavior = new CardOrderBehavior();   
							    							    behavior.setAgentCode(agentCode);
							    							    behavior.setAgentOrderNum(agentOrderNum);
							    							    behavior.setCreateTime(new Date());
							    							    behavior.setOrderNum(orderNum);
							    							    behavior.setQuantity(quantity);
							    							    behavior.setGoodsCode(goodsCode);
							    							    cardOrderBehaviorService.addCardOrderBehavior(behavior);
							    								long time = new Date().getTime()/1000;
							    								String timeStr = String.valueOf(time);
							    								log.info("agentCardList():timeStr = "+timeStr);
							    								String str = AGENCY_CODE+AUTH_CODE+goodsCode+orderNum+timeStr;
							    								String sign = MD5Util.MD5Encode(str,"utf-8");
							    								log.info("agentCardList():sign = "+sign);
							    							    JSONObject json = new JSONObject();
							    							    json.put("agencyCode", AGENCY_CODE);
							    							    json.put("timestamp", timeStr);
							    							    json.put("orderNum", orderNum);
							    							    json.put("goodsCode", goodsCode);
							    							    json.put("sign", sign);
							    							    json.put("quantity", quantity);
							    							    String url = "http://recharge.masxr.cn/api/recharge/getCards";
							    							    String returndata = HttpPost.sendPost(url, json.toJSONString());
							    							    log.info("agentCardList():returndata = "+returndata);
							    							    JSONObject newobj = JSONObject.parseObject(returndata);
							    								String code = newobj.getInteger("code").toString();
							    								if(code.equals("0")){
							    									output(response,JsonUtil.buildFalseJson("0", "提交成功"));
							    								}else{
							    									output(response, JsonUtil.buildFalseJson("1", "提交失败"));
							    								}
							    						 }
							    					 }
							    				}
							    			}
					    				}
					    			}	
			    				}
			    			}
			    		}else{
			    			log.info("agentCardList():代理商传递的值,quanlity is 0");
				    		output(response,JsonUtil.buildFalseJson("10002", "缺少必要的参数"));
			    		}
		    		}
		    	}
		    }
	    }
	}
	
	/**
	 * 代理商获取卡券
	 * @param response
	 * @param request
	 {
		"agencyCode": " XXXXXX",
		"orderNum": " TKWEN00112313123",
		"timestamp":"xxxxx",
		"sign":"xxx"
     }
	 * @throws IOException 
	 */
	@RequestMapping("/getCardList")
	public void getCardList(HttpServletResponse response,HttpServletRequest request) throws IOException{
		InputStream is = request.getInputStream();
	    String result = IOUtils.toString(is, "UTF-8");
	    if(result == null || result.equals("")){
	    	log.info("getCardList():代理商传递的值,result is null");
	    	output(response,JsonUtil.buildFalseJson("10003", "参数值有误"));
	    }else{
	    	 JSONObject obj = JSONObject.parseObject(result);
			    String agentCode = obj.getString("agencyCode");
			    if(agentCode == null || agentCode.equals("")){
				    log.info("getCardList():代理商传递的值,agentCode is null");
				    output(response,JsonUtil.buildFalseJson("10002", "缺少必要的参数"));
			    }else{
			    	String agentOrderNum = obj.getString("orderNum");
			    	if(agentOrderNum == null || agentOrderNum.equals("")){
					    log.info("getCardList():代理商传递的值,agentOrderNum is null");
					    output(response,JsonUtil.buildFalseJson("10002", "缺少必要的参数"));
			    	}else{
			    		//判断代理商的合法性
		    			Agent agent = agentService.getAgentInfo(null, agentCode, 1, null);
		    			if(agent == null){
		    				log.info("getCardList():代理商不存在,agent is null");
		    				output(response,JsonUtil.buildFalseJson("10004", "代理商不存在"));
		    			}else{
		    				//IP鉴权
		    				String IP = request.getRemoteAddr();
		    				if(agent.getAgentIP() == null || agent.getAgentIP().equals("")){
		    					log.info("getCardList():agent.getAgentIP() is null");
		    					output(response,JsonUtil.buildFalseJson("10005", "IP鉴权失败"));
		    				}else{
		    					 if(!agent.getAgentIP().equals(IP)){
		    						log.info("getCardList():agent.getAgentIP() neq request.getRemoteAddr()");
		    						output(response,JsonUtil.buildFalseJson("10005", "IP鉴权失败"));
		    					 }else{
		    					    String timestamp = obj.getString("timestamp");
		    					    if(timestamp == null || timestamp.equals("")){
		    					    	 log.info("getCardList():代理商传递的值,timestamp is null");
		    							 output(response,JsonUtil.buildFalseJson("10002", "缺少必要的参数"));
		    					    }else{
			    					    String sign = obj.getString("sign");
			    					    log.info("getCardList():sign = "+sign);
			    					    if(sign == null || sign.equals("")){
			    					    	log.info("getCardList():代理商传递的值,sign is null");
			    							output(response,JsonUtil.buildFalseJson("10002", "缺少必要的参数"));
			    					    }else{
			    					    	String signstr = agentCode+agent.getAuthCode()+agentOrderNum+timestamp;
			    					    	String mdsSignStr = MD5Util.MD5Encode(signstr, "utf-8");
			    					    	if(!mdsSignStr.equals(sign)){
			    					    		log.info("getCardList():签名错误");
									    		output(response,JsonUtil.buildFalseJson("10006", "签名错误"));
			    					    	}else{
			    					    		CardOrder list = cardOrderService.getCardOrder(agentCode, agentOrderNum, 0);
					    						if(list == null){
					    							output(response,JsonUtil.buildFalseJson("10010", "订单号不存在或该订单号已被使用"));
					    						}else{	
					    							//将状态置为已使用
					    							CardOrder order = new CardOrder();
					    							order.setIsUse(1);
					    							order.setId(list.getId());
					    							cardOrderService.updateCardData(order);
						    						Map<String, Object> map = new HashMap<String, Object>();
						    						map.put("code", "0");
						    						map.put("msg", "查询成功");
						    						map.put("goodsName",list.getGoodsName());
						    						map.put("price", list.getPrice());
						    						map.put("orderNum", list.getAgentOrderNum());
						    						map.put("platOrderNum", list.getOrderNum());
						    						map.put("cardList",list.getCardList());
						    						map.put("goodsCode", list.getGoodsCode());
						    						map.put("quantity", list.getQuantity());
						    						map.put("agentCode",list.getAgentCode());
					    							output(response,JSONObject.toJSONString(map));				    											    
					    						}
			    					    	}
			    					    }

		    					    }
		    					 }
		    						 
		    			  }
			    	}
			   }  
			}
	    }
	}
	

	/**
	 * 获取唯一订单号 : 24位
	 * @return
	 */
	public static String getOrderNumber(){
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		 String date = sdf.format(new Date());
         int hashCodeV = UUID.randomUUID().toString().hashCode();
         if(hashCodeV < 0) {//有可能是负数
             hashCodeV = - hashCodeV;
         }
         // 0 代表前面补充0     
         // 4 代表长度为4     
         // d 代表参数为正数型
         return date + String.format("%010d", hashCodeV);
	}
	
	public static void main(String[] args) throws Exception {
//	    JSONObject json = new JSONObject();
//		long time = new Date().getTime();
//	    json.put("aa", "bb");
//	    json.put("time", time);
//	    System.out.println(json.toJSONString());
		//long time = new Date().getTime()/1000;
		//String timeStr = String.valueOf(time);
		//System.out.println(timeStr);
		//base64解密
		//byte[] aString = Base64.decodeFast("YoC92ohz9uoUcsPCD7nC2Q==");
		//byte[] a = Base64.decodeFast("2fa7b69c14d1b7e2");
		//byte[] b = Base64.decodeFast("1c64f9640e72fc36");
		//byte[] c = 
		//String c = AESCBC.getInstance().decrypt("YoC92ohz9uoUcsPCD7nC2Q==", "utf-8", "2fa7b69c14d1b7e2", "1c64f9640e72fc36");
		//System.out.println(c);
//		String result = "{\"a\":1}";
//	    JSONObject obj = JSONObject.parseObject(result);
//		System.out.println(obj.getIntValue("quanlity"));	
		//System.out.println(getOrderNumber());
		//String  mwen = "card123";
		//String jiami = AESCBC.getInstance().encrypt(mwen, "utf-8", "abcdefghijklmnop", "1234567891234567");
		//System.out.println(jiami);
		//String jiemi = AESCBC.getInstance().decrypt(jiami, "utf-8","abcdefghijklmnop", "1234567891234567");
		//System.out.println(jiemi);
//		String cardList = "[{\"expiryDate\":\"123\",\"cardNum\":\"456789\",\"cardPwd\":\"mXeq+1PPZ+N5hWCF3AQOxQ==\"},{\"expiryDate\":\"123\",\"cardNum\":\"456789\",\"cardPwd\":\"mXeq+1PPZ+N5hWCF3AQOxQ==\"}]";
//		//JSONObject obj = JSONObject.parseObject(cardList);
//		JSONArray array = JSONArray.parseArray(cardList);
//		JSONObject object = null;
//		for (int i = 0; i < array.size(); i++) {
//			 object = array.getJSONObject(i);
//			 String cardPwd = object.getString("cardPwd");
//			 String jiemi = AESCBC.getInstance().decrypt(cardPwd, "utf-8","abcdefghijklmnop", "1234567891234567");
//			// String jiami = AESCBC.getInstance().encrypt(jiemi, "utf-8","abcdefghijklmnop", "1234567891234567");
//			 object.put("cardPwd", "card123");
//		}
//		System.out.println(array.toString());
		//String abc = "abcdefghijklmnopqrstuvwxyz123456";		
		//System.out.println(abc.substring(0, 16));
//		String miwen = "qjikGOVVrxwJCl0ulSwHUg==";
//		String auth1 = "abcdefghijklmnop";
//		String auth2 = "qrstuvwxyz123456";
		System.out.println(AESCBC.getInstance().decrypt("FFWTvJfcS/9FAdA2LV9kPw==", "utf-8", "abcdefghijklmnop", "qrstuvwxyz123456"));
		
	}
	/**
	 * 测试
	 * @param response
	 * @param request
	 */
	@RequestMapping("/test")
	public void test(HttpServletResponse response,HttpServletRequest request){
//		JSONObject json = new JSONObject();
//		json.put("agentCode", "huanyou");
//		json.put("agentOrderNum", "123456789");
//		String result = HttpPost.sendPost("http://192.168.10.161:8080/cofC/tx/getCardList.do",json.toJSONString());
//		output(response,result);
//		
//		{
//			"agenctCode": " XXXXXX",
//			"agentOrderNum": " TKWEN00112313123",
//			"goodsCode": "test1",
//	        "quantity": 1
//	     }
//		JSONObject json = new JSONObject();
//		json.put("agentCode", "huanyou");
//		json.put("agentOrderNum", "1234567891");
//		json.put("goodsCode", "123456");
//		json.put("quantity", 11);
//		String result = HttpPost.sendPost("http://192.168.10.161:8080/cofC/tx/agentCardList.do",json.toJSONString());
//		output(response,result);
		
		JSONObject json = new JSONObject();
		json.put("agencyCode", "HUANYOUTEC");
		json.put("orderNum","888888");
		long time = new Date().getTime()/1000;
		String timeStr = String.valueOf(time);
		json.put("timestamp", timeStr);
		String string = "HUANYOUTEC"+"abcdefghijklmnopqrstuvwxyz123456"+"888888"+timeStr;
		json.put("sign",MD5Util.MD5Encode(string, "utf-8"));
		String result = HttpPost.sendPost("http://192.168.10.161:8080/cofC/tx/getCardList.do", json.toJSONString());
   	 	JSONObject obj = JSONObject.parseObject(result);
   	 	System.out.println(obj);
   	 	System.out.println(obj.getString("cardList"));
		output(response, result);
	}
	
}
