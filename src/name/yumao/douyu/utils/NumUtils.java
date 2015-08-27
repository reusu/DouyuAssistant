package name.yumao.douyu.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NumUtils {
	public static String strNum2Utf8(String numbers){
		String u8s = "";
		for(int i=0;i<numbers.length();i++){
			u8s = u8s+ "3" + numbers.charAt(i);
		}
		return u8s;
	}
	public static boolean isNumeric(String str){ 
		   Pattern pattern = Pattern.compile("[0-9]*"); 
		   Matcher isNum = pattern.matcher(str);
		   if( !isNum.matches() ){
		       return false; 
		   } 
		   return true; 
		}
}
