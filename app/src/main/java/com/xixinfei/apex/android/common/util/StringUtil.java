/**
 * @author xixinfei
 * @date 2013-10-8 下午5:49:26
 */

package com.xixinfei.apex.android.common.util;


import com.xixinfei.apex.android.common.Constants;

public class StringUtil {
	public static boolean isStrEmpty(String str) {//常量.equals（"变量"）
		if (str != null && !Constants.NULL_STRING.equals(str)&&!"null".equals(str)) {
			return false;
		} else {
			return true;
		}
	}
	
	public static String ToDBC(String input) {
		if (isStrEmpty(input)) {
			return input;
		}else {
			char[] c = input.toCharArray();
			for (int i = 0; i < c.length; i++) {
				if (c[i] == 12288) {
					c[i] = (char) 32;
					continue;
				}
				if (c[i] > 65280 && c[i] < 65375)
					c[i] = (char) (c[i] - 65248);
			}
			return new String(c);
		}

	}
	
	
	public static boolean isNumber(String name){
		 String tem = name.substring(name.length()-1, name.length());
		 char[] temC = tem.toCharArray();
		 int mid = temC[0];
		 if(mid>=48&&mid<=57){//数字
			 return false;
		 }else{
			 return true;
		 }
		 
	} 
}
