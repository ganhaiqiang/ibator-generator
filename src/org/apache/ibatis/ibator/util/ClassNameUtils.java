package org.apache.ibatis.ibator.util;

public class ClassNameUtils {

	/**
	 * 将字符串的首字母转小写
	 * 
	 * @param str 需要转换的字符串
	 * @return
	 */
	public static String captureName(String str) {
		char[] cs = str.toCharArray();
		cs[0] += 32;
		return String.valueOf(cs);
	}
}
