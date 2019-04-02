package com.example.common.util;

import java.util.Date;
import java.util.UUID;

/**
 * ID工具
 * 
 * @author swh
 * @date 2017-05-01
 * 
 */
public class IdUtils {

	private IdUtils() {
	}

	/**
	 * 获取UUID
	 * 
	 * @return 36位UUID字符串
	 */
	public static String uuid() {
		return UUID.randomUUID().toString();
	}

	/**
	 * ID 生成 - 按月生成,4位年月+(len-4)位随机数字
	 * 
	 * @param len
	 *            (>=4有效,超过4的填充随机数字)
	 * @return 指定长度的数字字符串
	 */
	public static String monthId(int len) {
		StringBuilder id = new StringBuilder();
		id.append(DateUtils.now("yyMM"));
		if (len > 4) {
			id.append(StringUtils.ranNum(len - 4));
		}
		return id.toString();
	}

	/**
	 * ID 生成 - 按日生成,6位年月日+(len-6)位随机数字
	 * 
	 * @param len
	 *            (>=6有效,超过6的填充随机数字)
	 * @return 指定长度的数字字符串
	 */
	public static String dayId(int len) {
		StringBuilder id = new StringBuilder();
		id.append(DateUtils.now("yyMMdd"));
		if (len > 6) {
			id.append(StringUtils.ranNum(len - 6));
		}
		return id.toString();
	}

	/**
	 * ID 生成 - 按时间生成,yyyyMMddHHmmssSSS(17位)+(len-17)位随机数字
	 * 
	 * @param len
	 *            长度,超过17的填充随机数字
	 * @return 指定长度的数字字符串
	 */
	public static String secId(int len) {
		return secId(len, new Date());
	}

	/**
	 * ID 生成 - 按时间生成,yyyyMMddHHmmssSSS(17位)+(len-17)位随机数字
	 * 
	 * @param len
	 *            长度,超过17的填充随机数字
	 * @param date
	 *            指定时间
	 * @return 指定长度的数字字符串
	 */
	public static String secId(int len, Date date) {
		StringBuilder id = new StringBuilder();
		id.append(DateUtils.DF_FULL.format(null != date ? date : new Date()));
		int l = len - 17;
		if (l > 0) {
			id.append(StringUtils.ranNum(l));
			return id.toString();
		} else {
			return id.substring(0, len);
		}
	}

	/**
	 * 根据当前时间生成20位ID
	 * 
	 * @see #id20()
	 * @return yyyyMMddHHmmssSSS+ranNum(3)
	 */
	public static String nowId20() {
		return secId(20);
	}

	/**
	 * 根据当前时间生成32位ID
	 * 
	 * @see #id32()
	 * @return yyyyMMddHHmmssSSS+ranNum(15)
	 */
	public static String nowId32() {
		return secId(32);
	}

	/**
	 * 根据当前时间生成20位ID
	 * 
	 * @return yyyyMMddHHmmssSSS+ranNum(3)
	 */
	public static String id20() {
		return secId(20);
	}

	/**
	 * 根据当前时间生成20位ID
	 * 
	 * @param date
	 *            指定时间
	 * @return yyyyMMddHHmmssSSS+ranNum(3)
	 */
	public static String id20(Date date) {
		return secId(20, date);
	}

	/**
	 * 根据当前时间生成32位ID
	 * 
	 * @return yyyyMMddHHmmssSSS+ranNum(15)
	 */
	public static String id32() {
		return secId(32);
	}

	/**
	 * 根据当前时间生成32位ID
	 * 
	 * @param date
	 *            指定时间
	 * @return yyyyMMddHHmmssSSS+ranNum(15)
	 */
	public static String id32(Date date) {
		return secId(32, date);
	}

	/**
	 * 是否根据id20/nowId20规则生产的ID
	 * 
	 * <pre>
	 * match 20[0-9]{18}
	 * </pre>
	 * 
	 * @see #id20()
	 * @param id
	 *            ID
	 * @return true：符合id20/nowId20生成的ID规则
	 */
	public static boolean isNowId20(String id) {
		return null != id && id.matches("20[0-9]{18}");
	}

	private static final char[] ARR0_9 = new char[] { 'z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i' };

	/**
	 * ID 生成 - 根据时间戳生成字母字符串ID
	 * 
	 * @param len
	 *            ID长度(>=13有效,超过13的填充随机字母)
	 * @return 指定长度的字符字符串
	 */
	public static String abc(int len) {
		StringBuilder id = new StringBuilder();
		for (char c : String.valueOf(System.currentTimeMillis()).toCharArray()) {
			id.append(ARR0_9[new Integer(c) - 48]);
		}
		if (len > 13) {
			id.append(StringUtils.ranLetter(len - 13));
		}
		return id.toString();
	}

}
