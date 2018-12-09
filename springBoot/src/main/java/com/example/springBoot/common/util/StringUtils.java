package com.example.springBoot.common.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 字符串工具类
 * 
 * @author swh
 * @date 2017-05-23
 * 
 */
public class StringUtils {

	private StringUtils() {
	}

	/**
	 * 检查字符是否为空(null/length=0)
	 * 
	 * @param cs
	 *            字符
	 * @return null/length=0 true
	 */
	public static boolean isEmpty(CharSequence cs) {
		return cs == null || cs.length() == 0;
	}

	/**
	 * 检查字符是否为空或者空格 *
	 * 
	 * <pre>
	 * StringUtils.isBlank(null)	= true
	 * StringUtils.isBlank("")	= true
	 * StringUtils.isBlank(" ")	= true
	 * StringUtils.isBlank("b")	= false
	 * StringUtils.isBlank("  b  ")	= false
	 * </pre>
	 * 
	 * @param cs
	 *            字符
	 * @return null/length=0/空格 true
	 */
	public static boolean isBlank(CharSequence cs) {
		int strLen;
		if (cs == null || (strLen = cs.length()) == 0) {
			return true;
		}
		for (int i = 0; i < strLen; i++) {
			if (Character.isWhitespace(cs.charAt(i)) == false) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 检查字符串是否为数字和逗号组成的字符串
	 * 
	 * @param str
	 *            字符串
	 * @return true:仅包含数字和英文逗号
	 */
	public static boolean isNumAndDot(String str) {
		if (null == str || str.isEmpty()) {
			return false;
		}
		return str.matches("^[0-9,]+$");
	}

	/**
	 * 检查字符串是否为数字(包含小数、正负符号)
	 * 
	 * @param str
	 *            字符串
	 * @return true:仅包含数字(或者小数点、正负符号)
	 */
	public static boolean isNum(String str) {
		return isNum(str, false);
	}

	/**
	 * 检查字符串是否为数字
	 * 
	 * @param str
	 *            字符串
	 * @param onlyNum
	 *            是否仅为数字字符(不包含小数点、正负符号)
	 * @return true:仅包含数字(或者小数点、正负符号)
	 */
	public static boolean isNum(String str, boolean onlyNum) {
		if (null == str || str.isEmpty()) {
			return false;
		}
		if (onlyNum) {
			return str.matches("^[0-9]+$");
		} else {
			return str.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");
		}
	}

	/**
	 * 检查字符串是否为数字和字母的组合
	 * 
	 * @param str
	 *            字符串
	 * @return true:仅包含字母和数字
	 */
	public static boolean isNumOrLet(String str) {
		if (null == str || str.isEmpty()) {
			return false;
		}
		return str.matches("^[A-Za-z0-9]+$");
	}

	/**
	 * 检查字符串是否为纯字母 【a-zA-Z】
	 * 
	 * @param str
	 *            字符串
	 * @return true:仅包含字母(大小写)
	 */
	public static boolean isLet(String str) {
		if (null == str || str.isEmpty()) {
			return false;
		}
		return str.matches("^[A-Za-z]+$");
	}

	/**
	 * 检查字符串是否正常字符
	 * 
	 * @param str
	 *            字符串
	 * @return 仅包含数字、字母、汉字、"."、"_" 返回true
	 */
	public static boolean isOrdinary(String str) {
		if (null == str || str.isEmpty()) {
			return false;
		}
		return str.matches("^[._0-9A-Za-z\u2E80-\u9FFF]+$");
	}

	/**
	 * 检查卡号是否格式正确
	 * 
	 * @param card
	 *            卡号
	 * @return true:格式正确
	 */
	public static boolean isCard(String card) {
		if (null == card || card.length() > 10) {
			return false;
		}
		return card.matches("[0-9]{4,10}");
	}

	/**
	 * 检查地址是否完整http/https地址
	 * 
	 * @param url
	 *            地址
	 * @return true: http/https
	 */
	public static boolean isHttp(String url) {
		if (null != url && url.length() > 6) {
			String s = url.substring(0, 6).toLowerCase();
			return s.equals("https:") || s.equals("http:/");
		}
		return false;
	}

	/**
	 * 检查手机号码是否正确
	 * 
	 * @param phone
	 *            手机号码
	 * @return true:格式正确
	 */
	public static boolean isPhone(String phone) {
		if (null == phone || phone.length() != 11) {
			return false;
		}
		return phone.matches("1[0-9]{10}");
	}

	/**
	 * 格式化手机号码并检查手机号码
	 * 
	 * @param phone
	 *            需要格式化的号码
	 * @return 格式化后正确的手机号码,不符合格式的返回null
	 */
	public static String formatPhone(String phone) {
		if (null != phone) {
			phone = phone.trim();
			int len = phone.length();
			if (len >= 11) {
				if (len > 11) {
					phone = phone.substring(len - 11);
				}
				if (phone.matches("1[0-9]{10}")) {
					return phone;
				}
			}
		}
		return null;
	}

	/**
	 * 隐藏电话号码中间4位
	 * 
	 * @param phone
	 *            电话号码
	 * @return 隐藏中间4位后的号码
	 */
	public static String hidePhone(String phone) {
		if (null != phone && phone.length() > 7) {
			return phone.substring(0, 3) + "****" + phone.substring(7);
		}
		return null;
	}

	/**
	 * 移除http特殊字符
	 * 
	 * @param str
	 *            字符串
	 * @return 移除特殊字符后的字符串
	 */
	public static String htmoXssClean(String value) {
		if (value != null) {
			value = value.replaceAll("", "");
			// Avoid anything between script tags
			Pattern scriptPattern = Pattern.compile("<script>(.*?)</script>", Pattern.CASE_INSENSITIVE);
			value = scriptPattern.matcher(value).replaceAll("");
			// Avoid anything in a src="http://www.yihaomen.com/article/java/..." type of e­xpression
			scriptPattern = Pattern.compile("src[\r\n]*=[\r\n]*\\\'(.*?)\\\'",
					Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
			value = scriptPattern.matcher(value).replaceAll("");
			scriptPattern = Pattern.compile("src[\r\n]*=[\r\n]*\\\"(.*?)\\\"",
					Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
			value = scriptPattern.matcher(value).replaceAll("");
			// Remove any lonesome </script> tag
			scriptPattern = Pattern.compile("</script>", Pattern.CASE_INSENSITIVE);
			value = scriptPattern.matcher(value).replaceAll("");
			// Remove any lonesome <script ...> tag
			scriptPattern = Pattern.compile("<script(.*?)>",
					Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
			value = scriptPattern.matcher(value).replaceAll("");
			// Avoid eval(...) e­xpressions
			scriptPattern = Pattern.compile("eval\\((.*?)\\)",
					Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
			value = scriptPattern.matcher(value).replaceAll("");
			// Avoid e­xpression(...) e­xpressions
			scriptPattern = Pattern.compile("e­xpression\\((.*?)\\)",
					Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
			value = scriptPattern.matcher(value).replaceAll("");
			// Avoid javascript:... e­xpressions
			scriptPattern = Pattern.compile("javascript:", Pattern.CASE_INSENSITIVE);
			value = scriptPattern.matcher(value).replaceAll("");
			// Avoid vbscript:... e­xpressions
			scriptPattern = Pattern.compile("vbscript:", Pattern.CASE_INSENSITIVE);
			value = scriptPattern.matcher(value).replaceAll("");
			// Avoid onload= e­xpressions
			scriptPattern = Pattern.compile("onload(.*?)=",
					Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
			value = scriptPattern.matcher(value).replaceAll("");

		}
		return value;
	}
	 
	/**
	 * 移除http特殊字符(",&gt;,&lt;)
	 * 
	 * @param str
	 *            字符串
	 * @return 移除特殊字符后的字符串
	 */
	public static String htmlRmv(String str) {
		if (null != str) {
			str = str.replaceAll("\"", "").replaceAll("<", "").replaceAll(">", "");
			return htmoXssClean(str);
		} else {
			return str;
		}
	}

	/**
	 * 转义http特殊字符(",&gt;,&lt;)
	 * 
	 * @param str
	 *            字符串
	 * @return 转义特殊字符后的字符串
	 */
	public static String htmlEsc(String str) {
		if (null != str && !str.isEmpty()) {
			return str.replaceAll("\"", "&quot;").replaceAll("<", "&lt;").replaceAll(">", "&gt;");
		} else {
			return str;
		}
	}

	/**
	 * 转义http特殊字符(",&gt;,&lt;)
	 * 
	 * @param str
	 *            字符串
	 * @param quotation
	 *            是否转义双引号"
	 * @return 转义特殊字符后的字符串
	 */
	public static String htmlEsc(String str, boolean quotation) {
		if (null != str && !str.isEmpty()) {
			if (quotation) {
				return str.replaceAll("\"", "&quot;").replaceAll("<", "&lt;").replaceAll(">", "&gt;");
			} else {
				return str.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
			}
		} else {
			return str;
		}
	}

	public static final char[] CHAR_ARRS = new char[] { 'q', 'w', 'e', 'r', 't', 'y', 'u', 'i', 'o', 'p', 'a', 's', 'd', 'f', 'g', 'h', 'j', 'k', 'l', 'z', 'x', 'c', 'v', 'b', 'n', 'm', '1', '2',
			'3', '4', '5', '6', '7', '8', '9' };
	public static final int CHAR_ARRS_LENGTH = CHAR_ARRS.length;

	public static final char[] LETTER_ARRS = new char[] { 'q', 'w', 'e', 'r', 't', 'y', 'u', 'i', 'o', 'p', 'a', 's', 'd', 'f', 'g', 'h', 'j', 'k', 'l', 'z', 'x', 'c', 'v', 'b', 'n', 'm' };
	public static final int LETTER_ARRS_LENGTH = LETTER_ARRS.length;

	public static final char[] NC_ARRS = new char[] { '1', '2', '3', '4', '5', '6', '7', '8', '9', '0' };
	public static final int NC_ARRS_LENGTH = NC_ARRS.length;

	static final Random random = new Random();

	/**
	 * 生成随机字符串
	 * 
	 * @param len
	 *            字符串长度
	 * @return
	 */
	public static String ranStr(int len) {
		if (len < 1) {
			return "";
		}
		StringBuffer str = new StringBuffer();
		for (int i = 0; i < len; i++) {
			str.append(CHAR_ARRS[random.nextInt(CHAR_ARRS_LENGTH)]);
		}
		return str.toString();
	}

	/**
	 * 生成随机字母字符串
	 * 
	 * @param len
	 *            字符串长度
	 * @return
	 */
	public static String ranLetter(int len) {
		if (len < 1) {
			return "";
		}
		StringBuffer str = new StringBuffer();
		for (int i = 0; i < len; i++) {
			str.append(LETTER_ARRS[random.nextInt(LETTER_ARRS_LENGTH)]);
		}
		return str.toString();
	}

	/**
	 * 生成随机数字字符串
	 * 
	 * @param len
	 *            字符串长度
	 * @return
	 */
	public static String ranNum(int len) {
		if (len < 1) {
			return "";
		}
		StringBuffer str = new StringBuffer();
		for (int i = 0; i < len; i++) {
			str.append(NC_ARRS[random.nextInt(NC_ARRS_LENGTH)]);
		}
		return str.toString();
	}

	/**
	 * trim字符串
	 * 
	 * @param s
	 *            字符串
	 * @return 非空返回trim后的字符串，否则返回null
	 */
	public static String trim(String s) {
		if (null != s) {
			s = s.trim();
			if (!s.isEmpty()) {
				return s;
			}
		}
		return null;
	}

	/**
	 * 字符串转换为Integer
	 * 
	 * @param s
	 *            字符串
	 * @return 非法字符串返回null
	 */
	public static Integer toInt(String s) {
		s = trim(s);
		if (null != s && s.matches("^[0-9-]{1,11}")) {
			try {
				return Integer.parseInt(s);
			} catch (NumberFormatException e) {
			}
		}
		return null;
	}

	/**
	 * 字符串转换为Long
	 * 
	 * @param s
	 *            字符串
	 * @return 非法字符串返回null
	 */
	public static Long toLong(String s) {
		s = trim(s);
		if (null != s && s.matches("^[0-9-]{1,20}")) {
			try {
				return Long.parseLong(s);
			} catch (NumberFormatException e) {
			}
		}
		return null;
	}

	/**
	 * 字符串转换为Double
	 * 
	 * @param s
	 *            字符串
	 * @return 非法字符串返回null
	 */
	public static Double toDouble(String s) {
		s = trim(s);
		if (null != s && s.matches("^[0-9.-]{1,19}")) {
			try {
				return Double.parseDouble(s);
			} catch (NumberFormatException e) {
			}
		}
		return null;
	}

	/**
	 * 将数组转换为英文逗号分割的字符串
	 * 
	 * @param arr
	 *            数组(String.valueOf(...))
	 * @return 字符串,arr为null或者长度为0时返回null
	 */
	public static String arrToStr(Object arr[]) {
		return arrToStr(arr, ",", null);
	}

	/**
	 * 将数组转换为字符串
	 * 
	 * <pre>
	 * int[] rr = new int[]{1,2,3};
	 * String s = arrToStr(rr,",","'");
	 * s is '1','2','3'
	 * </pre>
	 * 
	 * @param arr
	 *            数组(String.valueOf(...))
	 * @param separator
	 *            分隔符，如 ,
	 * @param quotation
	 *            引号，如 " '
	 * @return 字符串,arr为null或者长度为0时返回null
	 */
	public static String arrToStr(Object arr[], String separator, String quotation) {
		if (null != arr && arr.length > 0) {
			StringBuilder sb = new StringBuilder();
			int i = 0;
			for (Object obj : arr) {
				if (null != separator) {
					if (i > 0) {
						sb.append(separator);
					}
					i++;
				}
				if (null != quotation) {
					sb.append(quotation).append(String.valueOf(obj)).append(quotation);
				} else {
					sb.append(String.valueOf(obj));
				}
			}
			return sb.toString();
		}
		return null;
	}

	/**
	 * 转移json特殊字符
	 * 
	 * @param str
	 *            字符串
	 * @return 转义特殊字符后的字符串
	 */
	public static String jsonEsc(String str) {
		if (null != str && !str.isEmpty()) {
			return str.replaceAll("\"", "&quot;")// 双引号
					.replaceAll("<", "&lt;")// 尖括号
					.replaceAll(">", "&gt;")// 尖括号
					.replaceAll("\n", "")// 换行符
			;
		} else {
			return str;
		}
	}

	/** Json转换Mapper */
	public static final ObjectMapper MAPPER = new ObjectMapper();

	/**
	 * 将对象转换为json
	 * 
	 * @param obj
	 *            对象
	 * @return 对象为null返回null，否则返回json
	 */
	public static String json(Object obj) {
		if (null != obj) {
			try {
				return MAPPER.writeValueAsString(obj);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 将对象转换为json(去除值为null的属性)
	 * 
	 * @param obj
	 *            对象
	 * @return 对象为null返回null，否则返回json
	 */
	public static String jsonNotNULL(Object obj) {
		if (null != obj) {
			try {
				MAPPER.setSerializationInclusion(Include.NON_NULL);
				return MAPPER.writeValueAsString(obj);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 字符串转换为int型集合
	 * 
	 * @param str
	 *            字符串
	 * @return
	 */
	public static List<Integer> intList(String str) {
		if (null != str && str.length() > 0) {
			List<Integer> list = new ArrayList<Integer>();
			for (String s : str.split(",")) {
				s = s.trim();
				if (!s.isEmpty()) {
					try {
						list.add(Integer.parseInt(s));
					} catch (NumberFormatException e) {
					}
				}
			}
			if (!list.isEmpty()) {
				return list;
			}
		}
		return null;
	}

	/**
	 * 字符串转换为int型数组
	 * 
	 * @param str
	 *            字符串
	 * @return
	 */
	public static Integer[] intArr(String str) {
		if (null != str && str.length() > 0) {
			List<Integer> list = new ArrayList<Integer>();
			for (String s : str.split(",")) {
				s = s.trim();
				if (s.length() > 0) {
					try {
						list.add(Integer.parseInt(s));
					} catch (NumberFormatException e) {
					}
				}
			}
			if (!list.isEmpty()) {
				return list.toArray(new Integer[list.size()]);
			}
		}
		return null;
	}

	/**
	 * 字符串转换为long型集合
	 * 
	 * @param str
	 *            字符串
	 * @return
	 */
	public static List<Long> longList(String str) {
		if (null != str && str.length() > 0) {
			List<Long> list = new ArrayList<Long>();
			for (String s : str.split(",")) {
				s = s.trim();
				if (!s.isEmpty()) {
					try {
						list.add(Long.parseLong(s));
					} catch (NumberFormatException e) {
					}
				}
			}
			if (!list.isEmpty()) {
				return list;
			}
		}
		return null;
	}

	/**
	 * 字符串转换为long型数组
	 * 
	 * @param str
	 *            字符串
	 * @return
	 */
	public static Long[] longArr(String str) {
		if (null != str && str.length() > 0) {
			List<Long> list = new ArrayList<Long>();
			for (String s : str.split(",")) {
				s = s.trim();
				if (s.length() > 0) {
					try {
						list.add(Long.parseLong(s));
					} catch (NumberFormatException e) {
					}
				}
			}
			if (!list.isEmpty()) {
				return list.toArray(new Long[list.size()]);
			}
		}
		return null;
	}

	/**
	 * 字符串转换为String型数组
	 * 
	 * @param str
	 *            字符串
	 * @return
	 */
	public static String[] strArr(String str) {
		if (null != str && str.length() > 0) {
			List<String> list = new ArrayList<String>();
			for (String s : str.split(",")) {
				s = s.trim();
				if (s.length() > 0) {
					try {
						list.add(s);
					} catch (Exception e) {
					}
				}
			}
			if (!list.isEmpty()) {
				return list.toArray(new String[list.size()]);
			}
		}
		return null;
	}

	/** 100 */
	private static final BigDecimal HUNDRED = new BigDecimal(100);

	/**
	 * 数字转百分数
	 * 
	 * @param d
	 *            双精度数字
	 * @return 小于等于0时返回0%
	 */
	public static String percent(double d) {
		return percent(new BigDecimal(d), 2);
	}

	/**
	 * 数字转百分数
	 * 
	 * @param d
	 *            BigDecimal数字对象
	 * @return 小于等于0时返回0%
	 */
	public static String percent(BigDecimal d) {
		return percent(d, 2);
	}

	/**
	 * 数字转百分数
	 * 
	 * @param d
	 *            BigDecimal数字对象
	 * @param scale
	 *            小数精度
	 * @return 小于等于0时返回0%
	 */
	public static String percent(BigDecimal d, int scale) {
		if (null != d && d.compareTo(BigDecimal.ZERO) == 1) {
			BigDecimal b = d.multiply(HUNDRED).setScale(scale, RoundingMode.HALF_UP);
			return b.toPlainString() + "%";
		}
		return "0%";
	}

	/** IP地址格式(IPv4) */
	private static final String IPADDRESS_PATTERN = "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
			+ "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";

	/**
	 * 检查IP地址是否正确
	 * 
	 * @param ip
	 *            IP地址
	 * @return 正确true，错误false
	 */
	public static boolean isIP(String ip) {
		ip = trim(ip);
		if (null != ip) {
			Matcher matcher = Pattern.compile(IPADDRESS_PATTERN).matcher(ip);
			return matcher.matches();
		}
		return false;
	}

	/**
	 * 长整型转换为字符串
	 * 
	 * @param number
	 *            数字
	 * @param len
	 *            字符串长度，不够前补0
	 * @return 字符串
	 */
	public static String longToString(Long number, Integer len) {
		if (null == number) {
			return "";
		}
		String s = number.toString();
		if (null != len) {
			int l = len - s.length();
			if (l > 0) {
				StringBuilder sb = new StringBuilder();
				for (int i = 0; i < l; i++) {
					sb.append('0');
				}
				sb.append(s);
				return sb.toString();
			}
		}
		return s;
	}

	/**
	 * 整型转换为字符串
	 * 
	 * @param number
	 *            数字
	 * @param len
	 *            字符串长度，不够前补0
	 * @return 字符串
	 */
	public static String intToString(Integer number, Integer len) {
		if (null == number) {
			return "";
		}
		String s = number.toString();
		if (null != len) {
			int l = len - s.length();
			if (l > 0) {
				StringBuilder sb = new StringBuilder();
				for (int i = 0; i < l; i++) {
					sb.append('0');
				}
				sb.append(s);
				return sb.toString();
			}
		}
		return s;
	}

	/**
	 * 数字转换成字符串
	 * 
	 * @param number
	 *            数字
	 * @param scale
	 *            保留小数位
	 * @return 保留scale位的字符串
	 */
	public static String numberToString(Number number, int scale) {
		return numberToString(number, scale, false);
	}

	/**
	 * 数字转换成字符串
	 * 
	 * @param number
	 *            数字
	 * @param scale
	 *            保留小数位
	 * @param comma
	 *            是否根据统计金钱格式增加“,”
	 * @return 保留scale位的字符串
	 */
	public static String numberToString(Number number, int scale, boolean comma) {
		if (null != number) {
			if (scale < 0) {
				scale = 0;
			}
			BigDecimal bigDecimal = new BigDecimal(number.doubleValue()).setScale(scale, RoundingMode.HALF_UP);
			if (!comma) {
				return bigDecimal.toPlainString();
			}
			boolean minus = false;// 是否负数
			if (bigDecimal.compareTo(BigDecimal.ZERO) == -1) {
				minus = true;
				bigDecimal = bigDecimal.abs();
			}
			String s = bigDecimal.toPlainString();// 转换为字符串
			int l = s.length();
			if (l <= 3 + scale) {
				return minus ? "-" : "" + s;// 字符串长度<=3+小数位，直接返回
			}
			StringBuilder sb = new StringBuilder();
			if (minus) {
				sb.append("-");
			}
			char[] cs = s.toCharArray();
			if (scale > 0) {
				int bl = l - 1 - scale;
				for (int i = 0; i < l; i++) {
					if (i < bl) {
						sb.append(cs[i]);
						int n = bl - i;
						if (n > 1 && n % 3 == 1) {
							sb.append(',');
						}
					} else {
						sb.append(cs[i]);
					}
				}
			} else {
				for (int i = 0; i < l; i++) {
					sb.append(cs[i]);
					int n = l - i;
					if (n > 1 && n % 3 == 1) {
						sb.append(',');
					}
				}
			}
			cs = null;
			s = null;
			return sb.toString();
		}
		return BigDecimal.ZERO.setScale(scale, RoundingMode.HALF_UP).toPlainString();
	}

	/**
	 * 格式化IC卡号
	 * 
	 * <pre>
	 * 1、移除所有非数字字符;
	 * 2、移除前置字符"0".
	 * Exp：cardFmt("000 123A00B4C56") is "12300456".
	 * </pre>
	 * 
	 * @param card
	 *            卡号
	 * @return 格式化后的卡号
	 */
	public static String cardFmt(String card) {
		if (null != card && !card.isEmpty()) {
			card = card.replaceAll("[^0-9]", "");
			if (!card.isEmpty()) {
				if (card.charAt(0) == '0') {
					return card.replaceFirst("[0]+", "");
				} else {
					return card;
				}
			}
		}
		return null;
	}

	/**
	 * 获取编码
	 * 
	 * @param charsetName
	 *            编码名称
	 * @return 编码/NULL
	 */
	public static Charset character(String charsetName) {
		if (null != charsetName) {
			if (Charset.isSupported(charsetName)) {
				try {
					return Charset.forName(charsetName);
				} catch (UnsupportedCharsetException x) {
					throw new Error(x);
				}
			}
		}
		return null;
	}

	/**
	 * 字符转Unicode编码
	 * 
	 * @param charArray
	 *            char数组("".toCharArray())
	 * @return Unicode编码
	 */
	public static String unicode(char[] charArray) {
		StringBuffer sb = new StringBuffer();
		for (char c : charArray) {
			String hexS = Integer.toHexString(c);
			String unicode = "\\u" + hexS;
			sb.append(unicode.toLowerCase());
		}
		return sb.toString();
	}

	public static Double decFormat(Double dob, String format) {
		if(format==null){
			format = "######0.00";
		}
		if (null != dob) {
			DecimalFormat df = new DecimalFormat(format);
			df.format(dob);
		}
		return dob;
	}
	
	public static String decFormatStr(Double dob, String format) {
		if (null != dob) {
			return String.valueOf(decFormat(dob, format));
		}
		return "";
	}
	
	/** UTF-8编码名称(UTF-8) */
	public static final String UTF8_NAME = "UTF-8";
	/** UTF-8编码 */
	public static final Charset UTF8 = StringUtils.character(UTF8_NAME);

	/**
	 * 转义(html)MAP中所有String类型数据
	 * 
	 * @param map
	 *            Map<String, Object>
	 * @see #htmlEsc(String)
	 */
	public static void esc(Map<String, Object> map) {
		if (null != map && !map.isEmpty()) {
			for (String key : map.keySet()) {
				Object obj = map.get(key);
				if (null != obj && obj instanceof String) {
					map.put(key, htmlEsc((String) obj));
				}
			}
		}
	}

	/**
	 * 转义(html)对象中所有String类型数据
	 * 
	 * <pre>
	 * 仅处理同时包含get和set方法的String属性
	 * get方法符合public String get属性名(){... return String;}
	 * set方法服务public void set属性名(String){...}
	 * get和set方法忽略大小写,如果存在忽略大小写后同名同参数同返回方法,有可能出现非预期结果
	 * </pre>
	 * 
	 * @param obj
	 *            对象
	 * @param ignore
	 *            忽略的字段名称(忽略大小写)
	 * @see #htmlEsc(String)
	 */
	public static void esc(Object obj, String... ignore) {
		if (null != obj) {
			Class<?> c = obj.getClass();
			Map<String, EscMethod> ms = OBJ_METHODS.get(obj.getClass());
			if (null == ms) {
				escMethods(c);
				ms = OBJ_METHODS.get(obj.getClass());
			}
			if (null != ms) {
				boolean hasIgnore = false;// 是否有忽略字段
				Set<String> igs = null;
				if (null != ignore && ignore.length > 0) {
					igs = new HashSet<String>();
					for (String s : ignore) {
						s = s.trim();
						if (!s.isEmpty()) {
							igs.add(s.toLowerCase());
						}
					}
					hasIgnore = !igs.isEmpty();
				}
				for (String p : ms.keySet()) {
					if (hasIgnore && igs.contains(p)) {
						continue;// 忽略
					}
					EscMethod em = ms.get(p);
					if (null != em && em.yes()) {
						try {
							String val = htmlEsc((String) em.getGet().invoke(obj));
							if (null != val) {
								em.getSet().invoke(obj, val);
							}
						} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
						}
					}
				}
			}
		}
	}

	/**
	 * 获取类所有String属性的Get/Set方法
	 * 
	 * @param c
	 *            类
	 */
	synchronized private static void escMethods(Class<?> c) {
		Map<String, Method> ms = new HashMap<String, Method>();
		for (Method m : c.getMethods()) {
			String rt = m.getReturnType().getName();// 返回类型
			Class<?>[] pts = m.getParameterTypes();// 参数类型
			String name = m.getName().toLowerCase();
			if (rt.equals("java.lang.String") && pts.length == 0 && name.startsWith("get")) {
				ms.put(name, m);// GET方法
			} else if (rt.equals("void") && pts.length == 1 && pts[0].equals(String.class) && name.startsWith("set")) {
				ms.put(name, m);// SET方法
			}
		}
		Map<String, EscMethod> m = new HashMap<String, EscMethod>();
		for (String key : ms.keySet()) {
			if (key.startsWith("get")) {
				String n = key.substring(3);
				Method gm = ms.get("get" + n);
				Method sm = ms.get("set" + n);
				if (null != gm && null != sm) {
					m.put(n, new EscMethod(gm, sm));
				}
			}
		}
		ms = null;
		OBJ_METHODS.put(c, m);
	}

	/** Get/Set方法缓存 */
	private static final Map<Class<?>, Map<String, EscMethod>> OBJ_METHODS = new HashMap<Class<?>, Map<String, EscMethod>>();

	/** String类型属性的Get/Set方法 */
	private static class EscMethod {
		/** Get方法 */
		private Method get;
		/** Set方法 */
		private Method set;

		public EscMethod(Method get, Method set) {
			this.get = get;
			this.set = set;
		}

		/**
		 * 获取Get方法
		 */
		public Method getGet() {
			return get;
		}

		/**
		 * 获取Set方法
		 */
		public Method getSet() {
			return set;
		}

		/** 是否正常 */
		public boolean yes() {
			return null != get && null != set;
		}
	}

}
