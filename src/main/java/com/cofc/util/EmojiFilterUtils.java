package com.cofc.util;


import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;



public class EmojiFilterUtils {
 	 
	 /**
     * 将emoji表情替换成*
     * 
     * @param source
     * @return 过滤后的字符串
     */
    public static String filterEmoji(String source) {
        if(StringUtils.isNotBlank(source)){
            return source.replaceAll("[\\ud800\\udc00-\\udbff\\udfff\\ud800-\\udfff]", "*");
        }else{
            return source;
        }
    }
    /**
     * 过滤掉emoji符号
     * @param source
     * @return
     */
    public static String cutEmoji(String source){
    	if(StringUtils.isNotBlank(source)){
    		String regex="[[a-zA-Z0-9][\\u4e00-\\u9fa5]-&+*%,，.。:：_——=（）()$￥@!！~`'?？]";
    		int count = 0;
    		String stra="";
    		ArrayList<String> strArray = new ArrayList<String> ();
    		Matcher matcher = Pattern.compile(regex).matcher(source);
    		while(matcher.find()){
    			count = count + 1;
    			//System.out.println(matcher.groupCount());  
    			//System.out.print(matcher.group());
    			
    			strArray.add(matcher.group());
                
    		}
    		//System.out.println(strArray);
    		for(String str:strArray){
    			stra+=str;
    		}
    		//System.out.println("共有 " + count + "个 ");    
    		return stra;
    	}else{
    		return source;
    	}
    }
//    public static void main(String[] arg ){
//        try{
//            String text = "This is a smiley \uD83C\uDFA6 face\uD860\uDD5D \uD860\uDE07 \uD860\uDEE2 \uD863\uDCCA \uD863\uDCCD \uD863\uDCD2 \uD867\uDD98 ";
//            System.out.println(text);
//            System.out.println(text.length());
//            System.out.println(text.replaceAll("[\\ud83c\\udc00-\\ud83c\\udfff]|[\\ud83d\\udc00-\\ud83d\\udfff]|[\\u2600-\\u27ff]", "*"));
//            System.out.println(filterEmoji(text));
//        }catch (Exception ex){
//            ex.printStackTrace();
//        }
//        String p = "  ";
//        System.out.print(cutEmoji(p));
//    }
 	}