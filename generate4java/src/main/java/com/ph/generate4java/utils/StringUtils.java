package com.ph.generate4java.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @PACKAGE_NAME: com.ph.generate4java.utils
 * @NAME: StringUtils
 * @Description:
 * @Author ph
 * @DATE: 2019-08-23 15:37
 **/
public class StringUtils {

	/**
	 * 下划线转驼峰法(默认小驼峰)
	 *
	 * @param line       源字符串
	 * @param smallCamel 大小驼峰,是否为小驼峰(驼峰，第一个字符是大写还是小写)
	 * @return 转换后的字符串
	 */
	public static String underline2Camel(String line, boolean... smallCamel) {
		if (line == null || "".equals(line)) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		Pattern pattern = Pattern.compile("([A-Za-z\\d]+)(_)?");
		Matcher matcher = pattern.matcher(line);
		//匹配正则表达式
		while (matcher.find()) {
			String word = matcher.group();
			//当是true 或则是空的情况
			if ((smallCamel.length == 0 || smallCamel[0]) && matcher.start() == 0) {
				sb.append(Character.toLowerCase(word.charAt(0)));
			} else {
				sb.append(Character.toUpperCase(word.charAt(0)));
			}

			int index = word.lastIndexOf('_');
			if (index > 0) {
				sb.append(word.substring(1, index).toLowerCase());
			} else {
				sb.append(word.substring(1).toLowerCase());
			}
		}
		return sb.toString();
	}

	public static void main(String[] args) {
		System.out.println(StringUtils.underline2Camel("jxc_site_user", false));
	}
}
