package com.cofc.controller.travel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cofc.util.BaseUtil;
import com.cofc.util.JsonUtil;

@Controller
@RequestMapping("/wx/getdistance")
public class WXGetDistanceController extends BaseUtil{
	private static final double EARTH_RADIUS = 6378137;//地球平均半径
    /**
     * 将经纬度转为度（°）
     * @param d
     * @return
     */
    private static double getRad(double d){
    	return d * Math.PI / 180.0;
    }
    /**
     * 根据两点间经纬度坐标（double值），计算两点间距离，单位为米  
     * @param lng1
     * @param lat1
     * @param lng2
     * @param lat2
     * @return
     */
    @RequestMapping("/getmapdistance")
    public void getMapDistance(HttpServletResponse response,double lng1,double lat1,double lng2,double lat2){
    	 double radLat1 = getRad(lat1);  
         double radLat2 = getRad(lat2);  
         double a = radLat1 - radLat2;  
         double b = getRad(lng1) - getRad(lng2);  
         double s = 2 * Math.asin(  
              Math.sqrt(  
                  Math.pow(Math.sin(a/2),2)   
                  + Math.cos(radLat1)*Math.cos(radLat2)*Math.pow(Math.sin(b/2),2)  
              )  
          );  
         s = s * EARTH_RADIUS;  
         s = Math.round(s * 10000) / 10000; 
         String distance = String.valueOf(s);
         output(response, JsonUtil.buildFalseJson("0",distance));
    }
    
    public static String getDistance(double lng1,double lat1,double lng2,double lat2){
   	    double radLat1 = getRad(lat1);  
        double radLat2 = getRad(lat2);  
        double a = radLat1 - radLat2;  
        double b = getRad(lng1) - getRad(lng2);  
        double s = 2 * Math.asin(  
             Math.sqrt(  
                 Math.pow(Math.sin(a/2),2)   
                 + Math.cos(radLat1)*Math.cos(radLat2)*Math.pow(Math.sin(b/2),2)  
             )  
         );  
        s = s * EARTH_RADIUS;  
        s = Math.round(s * 10000) / 10000; 
        String distance = String.valueOf(s);
        return distance;
   }
    /**
     * 根据经纬度获取距离
     * @param response
     * @param clng,clat:中心点
     * @param poliArr
     */
    @RequestMapping("/getmaxdistance")
    public void getMaxDistance(HttpServletResponse response,String poliArr,double clng,double clat){
		JSONArray jsonArray = JSON.parseArray(poliArr);
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject object = (JSONObject) jsonArray.get(i);
            String distance = getDistance(clng,clat, Double.valueOf(object.getString("longitude")),Double.valueOf(object.getString("latitude")));
            System.out.println("distance=="+distance);
            list.add(distance);
		}
      output(response,JsonUtil.buildJson(list));
    }
    
    /**
     *  [{"a":1,"b":"小明"},{"a":2,"b":"小花"},{"a":3,"b":"小陈"}]
     * @param args
     */
    public static void main(String[] args) {
		
	}
}
