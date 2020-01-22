package com.cofc.controller.weiaijia;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cofc.pojo.vij.GoodBrand;
import com.cofc.service.vij.GoodBrandService;
import com.cofc.util.BaseUtil;
import com.cofc.util.JsonUtil;

/**
 * 商品品牌
 * 
 * @author menghaoran
 *
 */
@Controller
@RequestMapping("/vij/app/brand")
public class VijGoodBrandController extends BaseUtil {
	@Resource
	private GoodBrandService brandService;
	public static Logger log = Logger.getLogger("VijGoodBrandController");

	@RequestMapping("/getBrand")
	public void getBrand(HttpServletResponse response, GoodBrand brand) {
		brand.setIsRemove(0);
		List<GoodBrand> brandlist = brandService.getGoodBrandList(brand, null, null);
		Map map = sort(brandlist);
		System.out.println(map.get("A"));
		output(response, JsonUtil.MapToJson(map));
	}

	// 字母Z使用了两个标签，这里有２７个值
	// i, u, v都不做声母, 跟随前面的字母
	private char[] chartable = { '啊', '芭', '擦', '搭', '蛾', '发', '噶', '哈', '哈', '击', '喀', '垃', '妈', '拿', '哦', '啪', '期',
			'然', '撒', '塌', '塌', '塌', '挖', '昔', '压', '匝', '座' };
	private char[] alphatableb = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q',
			'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };
	private char[] alphatables = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q',
			'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };
	private int[] table = new int[27]; // 初始化

	{
		for (int i = 0; i < 27; ++i) {
			table[i] = gbValue(chartable[i]);
		}
	}

	// 主函数,输入字符,得到他的声母,
	// 英文字母返回对应的大小写字母
	// 其他非简体汉字返回 '0' 按参数
	public char Char2Alpha(char ch, String type) {
		if (ch >= 'a' && ch <= 'z')
			return (char) (ch - 'a' + 'A');// 为了按字母排序先返回大写字母
		// return ch;
		if (ch >= 'A' && ch <= 'Z')
			return ch;
		int gb = gbValue(ch);
		if (gb < table[0])
			return '0';

		int i;
		for (i = 0; i < 26; ++i) {
			if (match(i, gb))
				break;
		}

		if (i >= 26) {
			return '0';
		} else {
			if ("b".equals(type)) {// 大写
				return alphatableb[i];
			} else {// 小写
				return alphatables[i];
			}
		}

	}

	private boolean match(int i, int gb) {
		if (gb < table[i])
			return false;
		int j = i + 1;

		// 字母Z使用了两个标签
		while (j < 26 && (table[j] == table[i]))
			++j;
		if (j == 26)
			return gb <= table[j];
		else
			return gb < table[j];
	}

	// 取出汉字的编码
	private int gbValue(char ch) {
		String str = new String();
		str += ch;
		try {
			byte[] bytes = str.getBytes("GBK");
			if (bytes.length < 2)
				return 0;
			return (bytes[0] << 8 & 0xff00) + (bytes[1] & 0xff);
		} catch (Exception e) {
			return 0;
		}

	}

	// 根据一个包含汉字的字符串返回一个汉字拼音首字母的字符串
	public String String2Alpha(String SourceStr, String type) {
		String Result = "";
		int StrLength = SourceStr.length();
		int i;
		try {
			for (i = 0; i < StrLength; i++) {
				Result += Char2Alpha(SourceStr.charAt(i), type);
			}
		} catch (Exception e) {
			Result = "";
		}
		return Result;
	}

	// 根据一个包含汉字的字符串返回第一个汉字拼音首字母的字符串
	public String String2AlphaFirst(String SourceStr, String type) {
		String Result = "";
		try {
			Result += Char2Alpha(SourceStr.charAt(0), type);
		} catch (Exception e) {
			Result = "";
		}
		return Result;

	}

	public Map sort(List<GoodBrand> list) {
		Map map = new HashMap();
		ArrayList<GoodBrand> arraylist = new ArrayList<GoodBrand>();
		String[] alphatableb = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q",
				"R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
		for (String a : alphatableb) {
			for (int i = 0; i < list.size(); i++) {// 为了排序都返回大写字母
					if (a.equals(String2AlphaFirst(list.get(i).getCnBrandName().toString(), "b"))) {
						GoodBrand bb = new GoodBrand();
						bb.setBrandId(list.get(i).getBrandId());
						bb.setCnBrandName(list.get(i).getCnBrandName());
						bb.setBrandLogo(list.get(i).getBrandLogo());
						arraylist.add(bb);
					}
			
			}
			map.put(a, arraylist);
			arraylist = new ArrayList<GoodBrand>();
		}
		ArrayList<GoodBrand> noarraylist = new ArrayList<GoodBrand>();
		for (int i = 0; i < list.size(); i++) {//数字和一些图标
			String str = list.get(i).getCnBrandName().substring(0, 1);
			if(!isChinese(str) && !isEnglish(str)){
				GoodBrand b = new GoodBrand();
				b.setBrandId(list.get(i).getBrandId());
				b.setCnBrandName(list.get(i).getCnBrandName());
				b.setBrandLogo(list.get(i).getBrandLogo());
				noarraylist.add(b);
			}	
		}
		map.put("#", noarraylist);
		return map;
	}
	
	
	  /**

	   * 是否是英文

	   * @param c

	   * @return

	   */

	   public static boolean isEnglish(String charaString){

	      return charaString.matches("^[a-zA-Z]*");

	    }
      /**
       * 是否是中文
       * @param str
       * @return
       */
	  public static boolean isChinese(String str){

	      String regEx = "[\\u4e00-\\u9fa5]+";

	      Pattern p = Pattern.compile(regEx);

	      Matcher m = p.matcher(str);

	     if(m.find())

	       return true;

	     else

	       return false;

	   }
	  public static void main(String[] args) {
		String aString = "361";
		System.out.println(aString.substring(0, 1));
		System.out.println(isChinese(aString.substring(0, 1)));
		System.out.println(isEnglish(aString.substring(0, 1)));
	}
}
