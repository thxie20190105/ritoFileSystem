package org.rito.filesystem.util;

import org.apache.commons.lang.StringUtils;
/**
 * @author yangbo
 *
 */
public class StringUtil extends StringUtils {
	/**
	 * 首字母大写
	 * 
	 * @return
	 */
	public static String firstUpperCase(String str) {
		str = StringUtil.stripToEmpty(str);
		return StringUtils.replaceChars(str, str.substring(0, 1), str.substring(0, 1).toUpperCase());
	}

	/**
	 * 首字母小写
	 * 
	 * @return
	 */
	public static String firstLowerCase(String str) {
		str = StringUtil.stripToEmpty(str);
		return StringUtils.replaceChars(str, str.substring(0, 1), str.substring(0, 1).toLowerCase());
	}
}
