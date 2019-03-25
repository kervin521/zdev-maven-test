package com.dev.share.util;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * 描述:日期工具类
 * 说明:[例如:公元2016年07月31日 上午 10时15分15秒361毫秒 CST+0800(当前日期为:本年第32周,本月第6周,本年第213天,本月第5星期的星期日)]
 * (
 * yyyy:年,MM:月,dd:日,a:上午/下午[am/pm],HH:时,mm:分,ss:秒,SSS:毫秒,
 * w:年中周数[例如:本年第17周],W:月周数[例如:本月第2周],D:年中天数[例如:本年第168天],
 * F:星期数[例如:2],E:星期文本[例如:星期二],
 * zzz:时区标识[CST/GMT],Z:时区[例如:+0800],G:年限标识(公元)
 * )
 * 作者:ZhangYi
 * 时间:2018年3月15日 下午1:30:11
 * 版本:wrm_v4.0
 * JDK:1.7.80
 */
public class DateUtils {

	private static Logger logger = LoggerFactory.getLogger(DateUtils.class);

	/**
	 * 日期分隔符(-)
	 */
	public static final String	SPLIT_DATE						= "-";
	/**
	 * 时间分隔符(:)
	 */
	public static final String	SPLIT_TIME						= ":";
	/**
	 * 默认日期时间格式[时间戳(yyyy/MM/dd HH:mm:ss)]
	 */
	public static final String	DEFAULT_ISO_FORMAT_DATE_TIME	= "yyyy/MM/dd HH:mm:ss";
	/**
	 * 默认日期时间格式[时间戳(yyyy-MM-dd HH:mm:ss)]
	 */
	public static final String	DEFAULT_FORMAT_DATE_TIME		= "yyyy-MM-dd HH:mm:ss";
	/**
	 * 默认日期时间格式[日期型(yyyy-MM-dd)]
	 */
	public static final String	DEFAULT_FORMAT_DATE				= "yyyy-MM-dd";
	/**
	 * 默认日期时间格式[日期型(yyyy-MM)]
	 */
	public static final String	DEFAULT_FORMAT_MONTH			= "yyyy-MM";
	/**
	 * 默认日期时间格式[时刻型(HH:mm:ss)]
	 */
	public static final String	DEFAULT_FORMAT_TIME				= "HH:mm:ss";
	/**
	 * 日期时间格式[时间戳(yyyy-MM-dd HH:mm)]
	 */
	public static final String	FORMAT_DATE_TIME				= "yyyy-MM-dd HH:mm";
	/**
	 * 日期时间格式[时间戳(MM-dd HH:mm)]
	 */
	public static final String	FORMAT_PATTERN_DATE_TIME		= "MM-dd HH:mm";
	/**
	 * 日期时间格式[日期型(MM-dd)]
	 */
	public static final String	FORMAT_PATTERN_DATE				= "MM-dd";
	/**
	 * 日期时间格式[时刻型(HH:mm)]
	 */
	public static final String	FORMAT_PATTERN_TIME				= "HH:mm";
	/**
	 * 日期时间格式[时间戳(MM/dd/yyyy)]
	 */
	public static final String	ISO_FORMAT_DATE					= "MM/dd/yyyy";
	/**
	 * 日期时间格式[时间戳(MM/dd/yyyy HH:mm:ss)]
	 */
	public static final String	ISO_FORMAT_DATE_TIME			= "MM/dd/yyyy HH:mm:ss";

	/**
	 * 日期时间格式[时间戳(yyyy-MM-dd'T'HH:mm:ss.SSSZ)]
	 */
	public static final String	UTC_FORMAT_DATE_TIME			= "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
	/**
	 * 一天毫秒数
	 */
	public static final long	ONE_DAY							= 1000l * 60 * 60 * 24;

	/**
	 * 
	 * 描述:[日期型]日期转化指定格式字符串
	 * 作者:ZhangYi
	 * 时间:2018年3月15日 下午3:25:25
	 * 参数：(参数列表)
	 * @param dateTime	日期时间
	 * @param format	日期格式(默认:yyyy-MM-dd HH:mm:ss)
	 * @return
	 * 
	 */
	public static Date formatDateTime(String dateTime, String format) {
		try {
			if (StringUtils.isEmpty(format)) format = DEFAULT_FORMAT_DATE_TIME;
			SimpleDateFormat dateFormat = new SimpleDateFormat(format);
			if (dateTime != null) {
				return dateFormat.parse(dateTime);
			}
		} catch (Exception e) {
			logger.error("--日期转化指定格式[" + format + "]字符串失败!", e);
		}
		return null;
	}
   /**
	 * 
	 * 描述:[日期型]日期转化指定格式字符串
	 * 作者:ZhangYi
	 * 时间:2018年3月15日 下午3:25:25
	 * 参数：(参数列表)
	 * @param dateTime	日期时间
	 * @param format	日期格式(默认:yyyy-MM-dd HH:mm:ss)
	 * @return
	 * 
	 */
	public static LocalDateTime formatLocalDateTime(String dateTime, String format) {
		try {
			if (StringUtils.isEmpty(format)) format = DEFAULT_FORMAT_DATE_TIME;
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
			if (dateTime != null) {
				return LocalDateTime.parse(dateTime, formatter);
			}
		} catch (Exception e) {
			logger.error("--日期转化指定格式[" + format + "]字符串失败!", e);
		}
		return null;
	}
	/**
	 * 
	 * 描述:[字符串型]日期转化指定格式字符串
	 * 作者:ZhangYi
	 * 时间:2018年3月15日 下午3:25:25
	 * 参数：(参数列表)
	 * @param dateTime	日期时间
	 * @param format	日期格式(默认:yyyy-MM-dd HH:mm:ss)
	 * @return
	 * 
	 */
	public static String formatDateTime(Date dateTime, String format) {
		try {
			if (StringUtils.isEmpty(format)) format = DEFAULT_FORMAT_DATE_TIME;
			SimpleDateFormat dateFormat = new SimpleDateFormat(format);
			if (dateTime != null) {
				return dateFormat.format(dateTime);
			}
		} catch (Exception e) {
			logger.error("--日期转化指定格式[" + format + "]字符串失败!", e);
		}
		return null;
	}
/**
	 * 
	 * 描述:[字符串型]日期转化指定格式字符串
	 * 作者:ZhangYi
	 * 时间:2018年3月15日 下午3:25:25
	 * 参数：(参数列表)
	 * @param dateTime	日期时间
	 * @param format	日期格式(默认:yyyy-MM-dd HH:mm:ss)
	 * @return
	 * 
	 */
	public static String formatDateTime(LocalDateTime dateTime, String format) {
		try {
			if (StringUtils.isEmpty(format)) format = DEFAULT_FORMAT_DATE_TIME;
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
			if (dateTime != null) {
				ZonedDateTime zone = ZonedDateTime.of(dateTime, ZoneId.systemDefault());
				return zone.format(formatter);
			}
		} catch (Exception e) {
			logger.error("--日期转化指定格式[" + format + "]字符串失败!", e);
		}
		return null;
	}
	/**
	 * 
	 * 描述:[字符串型]日期转化指定格式字符串
	 * 作者:ZhangYi
	 * 时间:2018年3月15日 下午3:25:25
	 * 参数：(参数列表)
	 * @param dateTime	日期时间
	 * @param format	日期格式(默认:yyyy-MM-dd HH:mm:ss)
	 * @return
	 * 
	 */
	public static String formatDateTime(LocalDate dateTime, String format) {
		try {
			if (StringUtils.isEmpty(format)) format = DEFAULT_FORMAT_DATE_TIME;
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
			if (dateTime != null) {
				ZonedDateTime zone = ZonedDateTime.of(dateTime.atStartOfDay(), ZoneId.systemDefault());
				return zone.format(formatter);
			}
		} catch (Exception e) {
			logger.error("--日期转化指定格式[" + format + "]字符串失败!", e);
		}
		return null;
	}
	/**
	 * 
	 * 描述:字符串转日期型(日期格式[yyyy-MM-dd HH:mm:ss])
	 * 作者:ZhangYi
	 * 时间:2016年4月15日 上午10:34:57
	 * 参数：(参数列表)
	 * @param dateTime	日期字符串(格式:'yyyy-MM-dd HH:mm:ss'或毫秒时间戳值或'yyyy-MM-dd HH:mm')
	 * @return
	 * 
	 */
	public static Date formatDateTime(String dateTime) {
		if (StringUtils.isEmpty(dateTime)) return null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_FORMAT_DATE_TIME);
			if (NumberUtils.isNumber(dateTime)) {
				Date date = new Date(Long.parseLong(dateTime));
				dateTime = sdf.format(date);
			} else {
				if (!dateTime.contains(":")) {
					dateTime = dateTime + " 00:00:00";
				} else {
					if (dateTime.length() < 19) {
						dateTime = dateTime + ":00";
					}
				}
			}
			return sdf.parse(dateTime);
		} catch (Exception e) {
			logger.error("日期时间格式转换错误：", e);
			return null;
		}
	}
   /**
	 * 
	 * 描述:字符串转日期型(日期格式[yyyy-MM-dd HH:mm:ss])
	 * 作者:ZhangYi
	 * 时间:2016年4月15日 上午10:34:57
	 * 参数：(参数列表)
	 * @param dateTime	日期字符串(格式:'yyyy-MM-dd HH:mm:ss'或毫秒时间戳值或'yyyy-MM-dd HH:mm')
	 * @return
	 * 
	 */
	public static LocalDateTime formatLocalDateTime(String dateTime) {
		if (StringUtils.isEmpty(dateTime)) return null;
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DEFAULT_FORMAT_DATE_TIME);
			if (NumberUtils.isNumber(dateTime)) {
		        LocalDateTime localDateTime = dateToDateTime(new Date(Long.parseLong(dateTime)));
				ZonedDateTime zone = ZonedDateTime.of(localDateTime, ZoneId.systemDefault());
				dateTime = zone.format(formatter);
			} else {
				if (!dateTime.contains(":")) {
					dateTime = dateTime + " 00:00:00";
				} else {
					if (dateTime.length() < 19) {
						dateTime = dateTime + ":00";
					}
				}
			}
			return LocalDateTime.parse(dateTime, formatter);
		} catch (Exception e) {
			logger.error("日期时间格式转换错误：", e);
			return null;
		}
	}
	/**
	  * 描述: 毫秒转LocalDateTime
	  * 作者: ZhangYi
	  * 时间: 2019年3月21日 下午3:59:21
	  * 参数: (参数列表)
	  * @param time 毫秒
	  * @return
	 */
	public static LocalDateTime dateToDateTime(long time) {
		Instant instant = Instant.ofEpochMilli(time);
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDateTime dateTime = instant.atZone(zoneId).toLocalDateTime();
        return dateTime;
	}
	/**
	 * 描述: 毫秒转LocalDate
	 * 作者: ZhangYi
	 * 时间: 2019年3月21日 下午3:59:21
	 * 参数: (参数列表)
	 * @param time 毫秒
	 * @return
	 */
	public static LocalDate dateToLocalDate(long time) {
		Instant instant = Instant.ofEpochMilli(time);
		ZoneId zoneId = ZoneId.systemDefault();
		LocalDate dateTime = instant.atZone(zoneId).toLocalDate();
		return dateTime;
	}
	/**
	 * 描述: Date转LocalDateTime
	 * 作者: ZhangYi
	 * 时间: 2019年3月21日 下午3:59:21
	 * 参数: (参数列表)
	 * @param date 转换日期
	 * @return
	 */
	public static LocalDateTime dateToDateTime(Date date) {
		Instant instant = date.toInstant();
		ZoneId zoneId = ZoneId.systemDefault();
		LocalDateTime dateTime = instant.atZone(zoneId).toLocalDateTime();
		return dateTime;
	}
	/**
	 * 描述: Date转LocalDate
	 * 作者: ZhangYi
	 * 时间: 2019年3月21日 下午3:59:21
	 * 参数: (参数列表)
	 * @param date 转换日期
	 * @return
	 */
	public static LocalDate dateToLocalDate(Date date) {
		Instant instant = date.toInstant();
		ZoneId zoneId = ZoneId.systemDefault();
		LocalDateTime dateTime = instant.atZone(zoneId).toLocalDateTime();
		return dateTime.toLocalDate();
	}
	/**
	 * 描述: 毫秒转Date
	 * 作者: ZhangYi
	 * 时间: 2019年3月21日 下午3:59:21
	 * 参数: (参数列表)
	 * @param time 毫秒
	 * @return
	 */
	public static Date dateTimeToDate(long time) {
		Instant instant = Instant.ofEpochMilli(time);
		Date dateTime = Date.from(instant);
		return dateTime;
	}
	/**
	  * 描述: LocalDateTime转Date
	  * 作者: ZhangYi
	  * 时间: 2019年3月21日 下午3:59:21
	  * 参数: (参数列表)
	  * @param dateTime 转换日期
	  * @return
	 */
	public static Date dateTimeToDate(LocalDateTime dateTime) {
		ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zdt = dateTime.atZone(zoneId);
        Date date = Date.from(zdt.toInstant());
        return date;
	}
	/**
	 * 描述: LocalDate转Date
	 * 作者: ZhangYi
	 * 时间: 2019年3月21日 下午3:59:21
	 * 参数: (参数列表)
	 * @param dateTime 转换日期
	 * @return
	 */
	public static Date dateTimeToDate(LocalDate dateTime) {
		ZoneId zoneId = ZoneId.systemDefault();
		ZonedDateTime zdt = dateTime.atStartOfDay().atZone(zoneId);
		Date date = Date.from(zdt.toInstant());
		return date;
	}
	/**
	 * 
	 * 描述:字符串转日期型(日期格式[yyyy-MM-dd])
	 * 作者:ZhangYi
	 * 时间:2016年4月15日 上午10:34:57
	 * 参数：(参数列表)
	 * @param dateTime	日期字符串(格式:'yyyy-MM-dd HH:mm:ss'或毫秒时间戳值或'yyyy-MM-dd')
	 * @return
	 * 
	 */
	public static Date formatDate(String dateTime) {
		if (StringUtils.isEmpty(dateTime)) return null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_FORMAT_DATE);
			if (NumberUtils.isNumber(dateTime)) {
				Date date = new Date(Long.parseLong(dateTime));
				dateTime = sdf.format(date);
			}
			return sdf.parse(dateTime);
		} catch (Exception e) {
			logger.error("日期时间格式转换错误：", e);
			return null;
		}
	}
   /**
	 * 
	 * 描述:字符串转日期型(日期格式[yyyy-MM-dd])
	 * 作者:ZhangYi
	 * 时间:2016年4月15日 上午10:34:57
	 * 参数：(参数列表)
	 * @param dateTime	日期字符串(格式:'yyyy-MM-dd HH:mm:ss'或毫秒时间戳值或'yyyy-MM-dd')
	 * @return
	 * 
	 */
	public static LocalDate formatLocalDate(String dateTime) {
		if (StringUtils.isEmpty(dateTime)) return null;
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DEFAULT_FORMAT_DATE);
			if (NumberUtils.isNumber(dateTime)) {
				LocalDateTime localDateTime = dateToDateTime(new Date(Long.parseLong(dateTime)));
				ZonedDateTime zone = ZonedDateTime.of(localDateTime, ZoneId.systemDefault());
				dateTime = zone.format(formatter);
			}
			return LocalDate.parse(dateTime,formatter);
		} catch (Exception e) {
			logger.error("日期时间格式转换错误：", e);
			return null;
		}
	}
	/**
	 * 
	 * 描述:转化指定日期(时分秒置为0)
	 * 作者:ZhangYi
	 * 时间:2018年3月15日 下午4:14:15
	 * 参数：(参数列表)
	 * @param dateTime	日期时间
	 * @return
	 * 
	 */
	public static Date formatToDate(Date dateTime) {
		String date = formatDate(dateTime);
		dateTime = formatDate(date);
		return dateTime;
	}

	/**
	 * 
	 * 描述:日期型转字符串(日期格式[yyyy-MM-dd HH:mm:ss])
	 * 作者:ZhangYi
	 * 时间:2016年4月15日 上午10:34:57
	 * 参数：(参数列表)
	 * @param dateTime	日期时间
	 * @return
	 * 
	 */
	public static String formatDateTime(Date dateTime) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_FORMAT_DATE_TIME);
			return sdf.format(dateTime);
		} catch (Exception e) {
			logger.error("转化日期格式错误：", e);
			return null;
		}
	}
   /**
	 * 
	 * 描述:日期型转字符串(日期格式[yyyy-MM-dd HH:mm:ss])
	 * 作者:ZhangYi
	 * 时间:2016年4月15日 上午10:34:57
	 * 参数：(参数列表)
	 * @param dateTime	日期时间
	 * @return
	 * 
	 */
	public static String formatDateTime(LocalDateTime dateTime) {
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DEFAULT_FORMAT_DATE_TIME);
			ZonedDateTime zone = ZonedDateTime.of(dateTime, ZoneId.systemDefault());
			return zone.format(formatter);
		} catch (Exception e) {
			logger.error("转化日期格式错误：", e);
			return null;
		}
	}
	/**
	 * 
	 * 描述:日期型转字符串(日期格式[yyyy-MM-dd HH:mm])
	 * 作者:ZhangYi
	 * 时间:2016年4月15日 上午10:34:57
	 * 参数：(参数列表)
	 * @param dateTime	日期时间
	 * @return
	 * 
	 */
	public static String formatDateHMTime(Date dateTime) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_DATE_TIME);
			return sdf.format(dateTime);
		} catch (Exception e) {
			logger.error("--日期型转字符串(日期格式[yyyy-MM-dd HH:mm])失败!", e);
			return null;
		}
	}
   /**
	 * 
	 * 描述:日期型转字符串(日期格式[yyyy-MM-dd HH:mm])
	 * 作者:ZhangYi
	 * 时间:2016年4月15日 上午10:34:57
	 * 参数：(参数列表)
	 * @param dateTime	日期时间
	 * @return
	 * 
	 */
	public static String formatDateHMTime(LocalDateTime dateTime) {
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern(FORMAT_DATE_TIME);
			ZonedDateTime zone = ZonedDateTime.of(dateTime, ZoneId.systemDefault());
			return zone.format(formatter);
		} catch (Exception e) {
			logger.error("--日期型转字符串(日期格式[yyyy-MM-dd HH:mm])失败!", e);
			return null;
		}
	}
	/**
	 * 
	 * 描述:日期型转字符串(日期格式[yyyy-MM-dd])
	 * 作者:ZhangYi
	 * 时间:2016年4月15日 上午10:34:57
	 * 参数：(参数列表)
	 * @param dateTime	日期时间
	 * @return
	 * 
	 */
	public static String formatDate(Date dateTime) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_FORMAT_DATE);
			return sdf.format(dateTime);
		} catch (Exception e) {
			logger.error("--日期型转字符串(日期格式[yyyy-MM-dd])失败!", e);
			return null;
		}
	}
   /**
	 * 
	 * 描述:日期型转字符串(日期格式[yyyy-MM-dd])
	 * 作者:ZhangYi
	 * 时间:2016年4月15日 上午10:34:57
	 * 参数：(参数列表)
	 * @param dateTime	日期时间
	 * @return
	 * 
	 */
	public static String formatDate(LocalDateTime dateTime) {
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DEFAULT_FORMAT_DATE);
			ZonedDateTime zone = ZonedDateTime.of(dateTime, ZoneId.systemDefault());
			return zone.format(formatter);
		} catch (Exception e) {
			logger.error("--日期型转字符串(日期格式[yyyy-MM-dd])失败!", e);
			return null;
		}
	}
	/**
	 * 
	 * 描述:日期型转字符串(日期格式[yyyy-MM-dd])
	 * 作者:ZhangYi
	 * 时间:2016年4月15日 上午10:34:57
	 * 参数：(参数列表)
	 * @param dateTime	日期时间
	 * @return
	 * 
	 */
	public static String formatDate(LocalDate dateTime) {
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DEFAULT_FORMAT_DATE);
			ZonedDateTime zone = ZonedDateTime.of(dateTime.atStartOfDay(), ZoneId.systemDefault());
			return zone.format(formatter);
		} catch (Exception e) {
			logger.error("--日期型转字符串(日期格式[yyyy-MM-dd])失败!", e);
			return null;
		}
	}
	/**
	 * 
	 * 描述:日期型转字符串(日期格式[HH:mm:ss])
	 * 作者:ZhangYi
	 * 时间:2016年4月15日 上午10:34:57
	 * 参数：(参数列表)
	 * @param dateTime	日期时间
	 * @return
	 * 
	 */
	public static String formatTime(Date dateTime) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_FORMAT_TIME);
			return sdf.format(dateTime);
		} catch (Exception e) {
			logger.error("--日期型转字符串(日期格式[HH:mm:ss])失败!", e);
			return null;
		}
	}
   /**
	 * 
	 * 描述:日期型转字符串(日期格式[HH:mm:ss])
	 * 作者:ZhangYi
	 * 时间:2016年4月15日 上午10:34:57
	 * 参数：(参数列表)
	 * @param dateTime	日期时间
	 * @return
	 * 
	 */
	public static String formatTime(LocalDateTime dateTime) {
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DEFAULT_FORMAT_TIME);
			ZonedDateTime zone = ZonedDateTime.of(dateTime, ZoneId.systemDefault());
			return zone.format(formatter);
		} catch (Exception e) {
			logger.error("--日期型转字符串(日期格式[HH:mm:ss])失败!", e);
			return null;
		}
	}
	/**
	 * 
	 * 描述:日期型转字符串(日期格式[HH:mm])
	 * 作者:ZhangYi
	 * 时间:2016年4月15日 上午10:34:57
	 * 参数：(参数列表)
	 * @param dateTime	日期时间
	 * @return
	 * 
	 */
	public static String formatHMStr(Date dateTime) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_PATTERN_TIME);
			return sdf.format(dateTime);
		} catch (Exception e) {
			logger.error("--日期型转字符串(日期格式[HH:mm])失败!", e);
			return null;
		}
	}
	/**
	 * 
	 * 描述:日期型转字符串(日期格式[HH:mm])
	 * 作者:ZhangYi
	 * 时间:2016年4月15日 上午10:34:57
	 * 参数：(参数列表)
	 * @param dateTime	日期时间
	 * @return
	 * 
	 */
	public static String formatHMStr(LocalDateTime dateTime) {
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern(FORMAT_PATTERN_TIME);
			ZonedDateTime zone = ZonedDateTime.of(dateTime, ZoneId.systemDefault());
			return zone.format(formatter);
		} catch (Exception e) {
			logger.error("--日期型转字符串(日期格式[HH:mm])失败!", e);
			return null;
		}
	}

	/**
	 * 
	 * 描述:日期格式校验(日期格式[yyyy-MM-dd HH:mm:ss])
	 * 作者:ZhangYi
	 * 时间:2018年3月15日 下午4:27:42
	 * 参数：(参数列表)
	 * @param dateTime	日期时间
	 * @return
	 * 
	 */
	public static boolean isDateTime(String dateTime) {
		if (dateTime == null) return false;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_FORMAT_DATE_TIME);
			sdf.parse(dateTime);
			return true;
		} catch (Exception e) {
			logger.error("日期时间格式校验错误：", e);
			return false;
		}
	}

	/**
	 * 
	 * 描述:间隔指定分钟后日期(例如:每30分钟)
	 * 作者:ZhangYi
	 * 时间:2018年3月15日 下午4:29:07
	 * 参数：(参数列表)
	 * @param dateTime	指定日期
	 * @param interval	间隔分钟
	 * @return
	 * 
	 */
	public static Date handleDateTimeByMinute(Date dateTime, int interval) {
		try {
			GregorianCalendar calendar = new GregorianCalendar();
			calendar.setTime(dateTime);
			calendar.add(Calendar.MINUTE, interval);
			dateTime = calendar.getTime();
		} catch (Exception e) {
			logger.error("--间隔指定分钟后日期异常!", e);
		}
		return dateTime;
	}
	/**
	 * 
	 * 描述:间隔指定分钟后日期(例如:每30分钟)
	 * 作者:ZhangYi
	 * 时间:2018年3月15日 下午4:29:07
	 * 参数：(参数列表)
	 * @param dateTime	指定日期
	 * @param interval	间隔分钟
	 * @return
	 * 
	 */
	public static LocalDateTime handleDateTimeByMinute(LocalDateTime dateTime, int interval) {
		try {
			dateTime= interval>0?dateTime.plusMinutes(interval):dateTime.minusMinutes(Math.abs(interval));
		} catch (Exception e) {
			logger.error("--间隔指定分钟后日期异常!", e);
		}
		return dateTime;
	}

	/**
	 * 
	 * 描述:间隔指定小时后日期(例如:每3小时)
	 * 作者:ZhangYi
	 * 时间:2018年3月15日 下午4:29:07
	 * 参数：(参数列表)
	 * @param dateTime	指定日期
	 * @param interval	间隔小时
	 * @return
	 * 
	 */
	public static Date handleDateTimeByHour(Date dateTime, int interval) {
		try {
			GregorianCalendar calendar = new GregorianCalendar();
			calendar.setTime(dateTime);
			calendar.add(Calendar.HOUR, interval);
			dateTime = calendar.getTime();
		} catch (Exception e) {
			logger.error("--间隔指定小时后日期异常!", e);
		}
		return dateTime;
	}
	/**
	 * 
	 * 描述:间隔指定小时后日期(例如:每3小时)
	 * 作者:ZhangYi
	 * 时间:2018年3月15日 下午4:29:07
	 * 参数：(参数列表)
	 * @param dateTime	指定日期
	 * @param interval	间隔小时
	 * @return
	 * 
	 */
	public static LocalDateTime handleDateTimeByHour(LocalDateTime dateTime, int interval) {
		try {
			dateTime= interval>0?dateTime.plusHours(interval):dateTime.minusHours(Math.abs(interval));
		} catch (Exception e) {
			logger.error("--间隔指定小时后日期异常!", e);
		}
		return dateTime;
	}

	/**
	 * 
	 * 描述:间隔指定天数后日期(例如:每3天)
	 * 作者:ZhangYi
	 * 时间:2018年3月15日 下午4:29:07
	 * 参数：(参数列表)
	 * @param dateTime	指定日期
	 * @param interval	间隔天数
	 * @return
	 * 
	 */
	public static Date handleDateTimeByDay(Date dateTime, int interval) {
		try {
			GregorianCalendar calendar = new GregorianCalendar();
			calendar.setTime(dateTime);
			calendar.add(Calendar.DAY_OF_MONTH, interval);
			dateTime = calendar.getTime();
		} catch (Exception e) {
			logger.error("--间隔指定天数后日期异常!", e);
		}
		return dateTime;
	}
	/**
	 * 
	 * 描述:间隔指定天数后日期(例如:每3天)
	 * 作者:ZhangYi
	 * 时间:2018年3月15日 下午4:29:07
	 * 参数：(参数列表)
	 * @param dateTime	指定日期
	 * @param interval	间隔天数
	 * @return
	 * 
	 */
	public static LocalDateTime handleDateTimeByDay(LocalDateTime dateTime, int interval) {
		try {
			dateTime= interval>0?dateTime.plusDays(interval):dateTime.minusDays(Math.abs(interval));
		} catch (Exception e) {
			logger.error("--间隔指定天数后日期异常!", e);
		}
		return dateTime;
	}

	/**
	 * 
	 * 描述:间隔指定月数的指定天数后日期(例如:每月1日)
	 * 作者:ZhangYi
	 * 时间:2018年3月15日 下午4:29:07
	 * 参数：(参数列表)
	 * @param dateTime	指定日期
	 * @param interval	间隔月数(间隔几个月)
	 * @param day		指定天数
	 * @return
	 * 
	 */
	public static Date handleDateTimeByMonth(Date dateTime, int interval, int day) {
		try {
			GregorianCalendar calendar = new GregorianCalendar();
			calendar.setTime(dateTime);
			calendar.add(Calendar.MONTH, interval);
			calendar.set(Calendar.DAY_OF_MONTH, day);
			dateTime = calendar.getTime();
		} catch (Exception e) {
			logger.error("--间隔指定月数的指定天数后日期异常!", e);
		}
		return dateTime;
	}
	/**
	 * 
	 * 描述:间隔指定月数的指定天数后日期(例如:每月1日)
	 * 作者:ZhangYi
	 * 时间:2018年3月15日 下午4:29:07
	 * 参数：(参数列表)
	 * @param dateTime	指定日期
	 * @param interval	间隔月数(间隔几个月)
	 * @param day		指定天数
	 * @return
	 * 
	 */
	public static LocalDateTime handleDateTimeByMonth(LocalDateTime dateTime, int interval, int day) {
		try {
			dateTime= interval>0?dateTime.plusMonths(interval):dateTime.minusMonths(Math.abs(interval));
			dateTime= dateTime.withDayOfMonth(day);
		} catch (Exception e) {
			logger.error("--间隔指定月数的指定天数后日期异常!", e);
		}
		return dateTime;
	}

	/**
	 * 
	 * 描述:间隔指定月数的指定周数指定星期数后日期(例如:每3个月第一个星期一)
	 * 作者:ZhangYi
	 * 时间:2018年3月15日 下午4:29:07
	 * 参数：(参数列表)
	 * @param dateTime	指定日期
	 * @param interval	间隔月数(间隔几个月)
	 * @param num		指定周数(1-4:第几个星期)
	 * @param week		指定周几(1-7:周一至周日,-1:不指定周几(JDK默认星期一))
	 * @return
	 * 
	 */
	public static Date handleDateTimeByMonth(Date dateTime, int interval, int num, int week) {
		try {
			GregorianCalendar calendar = new GregorianCalendar();
			calendar.setTime(dateTime);
			calendar.add(Calendar.MONTH, interval);
			if (num < 0) {// 最后一个星期
				calendar.set(Calendar.DAY_OF_WEEK_IN_MONTH, -1);
			} else {
				calendar.set(Calendar.DAY_OF_WEEK_IN_MONTH, num);
			}
			if (week < 0) {// [默认星期一]
				calendar.set(Calendar.DAY_OF_WEEK, 1 % 7 + 1);
			} else {
				calendar.set(Calendar.DAY_OF_WEEK, week % 7 + 1);
			}
			dateTime = calendar.getTime();
		} catch (Exception e) {
			logger.error("--间隔指定月数的指定周数指定星期数后日期异常!", e);
		}
		return dateTime;
	}
	/**
	 * 
	 * 描述:间隔指定月数的指定周数指定星期数后日期(例如:每3个月第一个星期一)
	 * 作者:ZhangYi
	 * 时间:2018年3月15日 下午4:29:07
	 * 参数：(参数列表)
	 * @param dateTime	指定日期
	 * @param interval	间隔月数(间隔几个月)
	 * @param num		指定周数(1-4:第几个星期)
	 * @param week		指定周几(1-7:周一至周日,-1:不指定周几(JDK默认星期一))
	 * @return
	 * 
	 */
	public static LocalDateTime handleDateTimeByMonth(LocalDateTime dateTime, int interval, int num, int week) {
		try {
			dateTime= interval>0?dateTime.plusMonths(interval):dateTime.minusMonths(Math.abs(interval));
			dateTime= dateTime.with(TemporalAdjusters.dayOfWeekInMonth(num, DayOfWeek.of(week)));
		} catch (Exception e) {
			logger.error("--间隔指定月数的指定周数指定星期数后日期异常!", e);
		}
		return dateTime;
	}

	/**
	 * 
	 * 描述:间隔指定年数的指定月份指定天数后日期(例如:每年1月1日)
	 * 作者:ZhangYi
	 * 时间:2018年3月15日 下午4:29:07
	 * 参数：(参数列表)
	 * @param dateTime	指定日期
	 * @param interval	间隔年(间隔几年)
	 * @param month		指定月份(1-12:月份)
	 * @param day		指定天数
	 * @return
	 * 
	 */
	public static Date handleDateTimeByYear(Date dateTime, int interval, int month, int day) {
		try {
			GregorianCalendar calendar = new GregorianCalendar();
			calendar.setTime(dateTime);
			calendar.add(Calendar.YEAR, interval);
			calendar.set(Calendar.MONTH, month - 1);
			calendar.set(Calendar.DAY_OF_MONTH, day);
			dateTime = calendar.getTime();
		} catch (Exception e) {
			logger.error("--间隔指定年数的指定月份指定天数后日期异常!", e);
		}
		return dateTime;
	}
	/**
	 * 
	 * 描述:间隔指定年数的指定月份指定天数后日期(例如:每年1月1日)
	 * 作者:ZhangYi
	 * 时间:2018年3月15日 下午4:29:07
	 * 参数：(参数列表)
	 * @param dateTime	指定日期
	 * @param interval	间隔年(间隔几年)
	 * @param month		指定月份(1-12:月份)
	 * @param day		指定天数
	 * @return
	 * 
	 */
	public static LocalDateTime handleDateTimeByYear(LocalDateTime dateTime, int interval, int month, int day) {
		try {
			dateTime= interval>0?dateTime.plusYears(interval):dateTime.minusYears(Math.abs(interval));
			dateTime= dateTime.withMonth(month).withDayOfMonth(day);
		} catch (Exception e) {
			logger.error("--间隔指定年数的指定月份指定天数后日期异常!", e);
		}
		return dateTime;
	}
	/**
	 * 
	 * 描述:间隔指定年数的指定月份指定周数指定星期数后日期(例如:每年1月份第一个星期一)
	 * 作者:ZhangYi
	 * 时间:2018年3月15日 下午4:29:07
	 * 参数：(参数列表)
	 * @param dateTime	指定日期
	 * @param interval	间隔年(间隔几年)
	 * @param month		指定月份(1-12:月份)
	 * @param num		指定周数(1-4:第几个星期)
	 * @param week		指定周几(1-7:周一至周日,-1:不指定周几[默认星期一])
	 * @return
	 * 
	 */
	public static Date handleDateTimeByYear(Date dateTime, int interval, int month, int num, int week) {
		try {
			GregorianCalendar calendar = new GregorianCalendar();
			calendar.setTime(dateTime);
			calendar.add(Calendar.YEAR, interval);
			calendar.set(Calendar.MONTH, month - 1);
			if (num < 0) {// 最后一个星期
				calendar.set(Calendar.DAY_OF_WEEK_IN_MONTH, -1);
			} else {
				calendar.set(Calendar.DAY_OF_WEEK_IN_MONTH, num);
			}
			if (week < 0) {// [默认星期一]
				calendar.set(Calendar.DAY_OF_WEEK, 1 % 7 + 1);
			} else {
				calendar.set(Calendar.DAY_OF_WEEK, week % 7 + 1);
			}
			dateTime = calendar.getTime();
		} catch (Exception e) {
			logger.error("--间隔指定年数的指定月份指定周数指定星期数后日期异常!", e);
		}
		return dateTime;
	}
	/**
	 * 
	 * 描述:间隔指定年数的指定月份指定周数指定星期数后日期(例如:每年1月份第一个星期一)
	 * 作者:ZhangYi
	 * 时间:2018年3月15日 下午4:29:07
	 * 参数：(参数列表)
	 * @param dateTime	指定日期
	 * @param interval	间隔年(间隔几年)
	 * @param month		指定月份(1-12:月份)
	 * @param num		指定周数(1-4:第几个星期)
	 * @param week		指定周几(1-7:周一至周日,-1:不指定周几[默认星期一])
	 * @return
	 * 
	 */
	public static LocalDateTime handleDateTimeByYear(LocalDateTime dateTime, int interval, int month, int num, int week) {
		try {
			dateTime= interval>0?dateTime.plusYears(interval):dateTime.minusYears(Math.abs(interval));
			dateTime= dateTime.withMonth(month).with(TemporalAdjusters.dayOfWeekInMonth(num, DayOfWeek.of(week)));
		} catch (Exception e) {
			logger.error("--间隔指定年数的指定月份指定周数指定星期数后日期异常!", e);
		}
		return dateTime;
	}

	/**
	 * 
	 * 描述:获取当前时间的星期数
	 * 作者:ZhangYi
	 * 时间:2018年3月15日 下午4:31:06
	 * 参数：(参数列表)
	 * @param date	指定日期
	 * @return
	 * 
	 */
	public static String formatWeek(Date date) {
		String[] weeks = { "7", "1", "2", "3", "4", "5", "6" };
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int week = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (week < 0) week = 0;
		return weeks[week];
	}

	/**
	 * 
	 * 描述:获取中英文星期数
	 * 作者:ZhangYi
	 * 时间:2018年3月15日 下午4:32:32
	 * 参数：(参数列表)
	 * @param date	指定日期
	 * @param lang	语言(中文:zh/zh_CN,英文:en/en_US)
	 * @return
	 * 
	 */
	public static String formatWeek(Date date, String lang) {
		String[] weeks = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
		if (!StringUtils.isEmpty(lang) && (lang.contains("en") || lang.contains("EN"))) {
			weeks = new String[] { "Sun.", "Mon.", "Tues.", "Wed.", "Thur.", "Fri.", "Sat." };
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int week = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (week < 0) week = 0;
		return weeks[week];
	}

	/**
	 * 
	 * 描述:获取中英文星期数
	 * 作者:ZhangYi
	 * 时间:2018年3月15日 下午4:32:32
	 * 参数：(参数列表)
	 * @param week	指定星期
	 * @param lang	语言(中文:zh/zh_CN,英文:en/en_US)
	 * @return
	 * 
	 */
	public static String formatWeek(int week, String lang) {
		String[] weeks = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
		if (!StringUtils.isEmpty(lang) && (lang.contains("en") || lang.contains("EN"))) {
			weeks = new String[] { "Sun.", "Mon.", "Tues.", "Wed.", "Thur.", "Fri.", "Sat." };
		}
		if (week < 0) week = 0;
		return weeks[week];
	}

	/**
	 * 
	 * 描述:获取日期间隔分钟数(同一分钟间隔为0)
	 * 作者:ZhangYi
	 * 时间:2018年3月15日 下午4:39:08
	 * 参数：(参数列表)
	 * @param from	起始时间
	 * @param to	结束时间
	 * @return
	 * 
	 */
	public static int intervalMinutes(String from, String to) {
		try {
			Date startTime = formatDateTime(from);
			Date endTime = formatDateTime(to);
			double interval = (endTime.getTime() - startTime.getTime()) / (double) (1000 * 60);
			return (int) Math.floor(interval);
		} catch (Exception e) {
			logger.error("--获取日期间隔分钟数失败!", e);
		}
		return 0;
	}

	/**
	 * 
	 * 描述:获取日期间隔分钟数(同一分钟间隔为0)
	 * 作者:ZhangYi
	 * 时间:2018年3月15日 下午4:39:08
	 * 参数：(参数列表)
	 * @param startTime	起始时间
	 * @param endTime	结束时间
	 * @return
	 * 
	 */
	public static int intervalMinutes(Date startTime, Date endTime) {
		try {
			double interval = (endTime.getTime() - startTime.getTime()) / (double) (1000 * 60);
			return (int) Math.floor(interval);
		} catch (Exception e) {
			logger.error("--获取日期间隔分钟数失败!", e);
		}
		return 0;
	}
	/**
	 * 
	 * 描述:获取日期间隔分钟数(同一分钟间隔为0)
	 * 作者:ZhangYi
	 * 时间:2018年3月15日 下午4:39:08
	 * 参数：(参数列表)
	 * @param startTime	起始时间
	 * @param endTime	结束时间
	 * @return
	 * 
	 */
	public static long intervalMinutes(LocalDateTime startTime, LocalDateTime endTime) {
		try {//Duration ==> day,hour,minute,second
			Duration duration = Duration.between(startTime, endTime);
			return duration.toMinutes();
		} catch (Exception e) {
			logger.error("--获取日期间隔分钟数失败!", e);
		}
		return 0;
	}
	/**
	 * 
	 * 描述:获取日期间隔分钟数(同一分钟间隔为0)
	 * 作者:ZhangYi
	 * 时间:2018年3月15日 下午4:39:08
	 * 参数：(参数列表)
	 * @param startTime	起始时间
	 * @param endTime	结束时间
	 * @return
	 * 
	 */
	public static long intervalMinutes(LocalDate startTime, LocalDate endTime) {
		try {//Duration ==> day,hour,minute,second
			Duration duration = Duration.between(startTime.atStartOfDay(), endTime.atStartOfDay());
			return duration.toMinutes();
		} catch (Exception e) {
			logger.error("--获取日期间隔分钟数失败!", e);
		}
		return 0;
	}
	/**
	 * 
	 * 描述:获取日期间隔小时数(同一小时间隔为0)
	 * 作者:ZhangYi
	 * 时间:2018年3月15日 下午4:39:08
	 * 参数：(参数列表)
	 * @param from	起始时间
	 * @param to	结束时间
	 * @return
	 * 
	 */
	public static int intervalHours(String from, String to) {
		try {
			Date startTime = formatDateTime(from);
			Date endTime = formatDateTime(to);
			double interval = (endTime.getTime() - startTime.getTime()) / (double) (1000 * 60 * 60);
			return (int) Math.floor(interval);
		} catch (Exception e) {
			logger.error("--获取日期间隔小时数失败!", e);
		}
		return 0;
	}

	/**
	 * 
	 * 描述:获取日期间隔小时数(同一小时间隔为0)
	 * 作者:ZhangYi
	 * 时间:2018年3月15日 下午4:39:08
	 * 参数：(参数列表)
	 * @param start	起始时间
	 * @param end	结束时间
	 * @return
	 * 
	 */
	public static int intervalHours(Date start, Date end) {
		try {
			double interval = (end.getTime() - start.getTime()) / (double) (1000 * 60 * 60);
			return (int) Math.floor(interval);
		} catch (Exception e) {
			logger.error("--获取日期间隔小时数失败!", e);
		}
		return 0;
	}
	/**
	 * 
	 * 描述:获取日期间隔小时数(同一小时间隔为0)
	 * 作者:ZhangYi
	 * 时间:2018年3月15日 下午4:39:08
	 * 参数：(参数列表)
	 * @param start	起始时间
	 * @param end	结束时间
	 * @return
	 * 
	 */
	public static long intervalHours(LocalDateTime startTime, LocalDateTime endTime) {
		try {//Duration ==> day,hour,minute,second
			Duration duration = Duration.between(startTime, endTime);
			return duration.toHours();
		} catch (Exception e) {
			logger.error("--获取日期间隔小时数失败!", e);
		}
		return 0;
	}
	/**
	 * 
	 * 描述:获取日期间隔小时数(同一小时间隔为0)
	 * 作者:ZhangYi
	 * 时间:2018年3月15日 下午4:39:08
	 * 参数：(参数列表)
	 * @param start	起始时间
	 * @param end	结束时间
	 * @return
	 * 
	 */
	public static long intervalHours(LocalDate startTime, LocalDate endTime) {
		try {//Duration ==> day,hour,minute,second
			Duration duration = Duration.between(startTime.atStartOfDay(), endTime.atStartOfDay());
			return duration.toHours();
		} catch (Exception e) {
			logger.error("--获取日期间隔小时数失败!", e);
		}
		return 0;
	}

	/**
	 * 
	 * 描述:获取日期间隔天数(同一天间隔为1)
	 * 作者:ZhangYi
	 * 时间:2018年3月15日 下午4:39:08
	 * 参数：(参数列表)
	 * @param from	起始时间
	 * @param to	结束时间
	 * @return
	 * 
	 */
	public static int intervalDays(String from, String to) {
		try {
			Date startTime = formatDateTime(from);
			Date endTime = formatDateTime(to);
			double interval = (endTime.getTime() - startTime.getTime()) / (double) (1000 * 60 * 60 * 24);
			return (int) Math.ceil(interval);
		} catch (Exception e) {
			logger.error("--获取日期间隔天数失败!", e);
		}
		return 0;
	}

	/**
	 * 
	 * 描述:获取日期间隔天数(同一天间隔为1)
	 * 作者:ZhangYi
	 * 时间:2018年3月15日 下午4:39:08
	 * 参数：(参数列表)
	 * @param start	起始时间
	 * @param end	结束时间
	 * @return
	 * 
	 */
	public static int intervalDays(Date start, Date end) {
		try {
			double interval = (end.getTime() - start.getTime()) / (double) (1000 * 60 * 60 * 24);
			return (int) Math.ceil(interval);
		} catch (Exception e) {
			logger.error("--获取日期间隔天数失败!", e);
		}
		return 0;
	}
	/**
	 * 
	 * 描述:获取日期间隔天数(同一天间隔为1)
	 * 作者:ZhangYi
	 * 时间:2018年3月15日 下午4:39:08
	 * 参数：(参数列表)
	 * @param start	起始时间
	 * @param end	结束时间
	 * @return
	 * 
	 */
	public static long intervalDays(LocalDateTime startTime, LocalDateTime endTime) {
		try {//Duration ==> day,hour,minute,second
			Duration duration = Duration.between(startTime, endTime);
			return duration.toDays();
		} catch (Exception e) {
			logger.error("--获取日期间隔天数失败!", e);
		}
		return 0;
	}
	/**
	 * 
	 * 描述:获取日期间隔天数(同一天间隔为1)
	 * 作者:ZhangYi
	 * 时间:2018年3月15日 下午4:39:08
	 * 参数：(参数列表)
	 * @param start	起始时间
	 * @param end	结束时间
	 * @return
	 * 
	 */
	public static long intervalDays(LocalDate startTime, LocalDate endTime) {
		try {//Duration ==> day,hour,minute,second
			Duration duration = Duration.between(startTime.atStartOfDay(), endTime.atStartOfDay());
			return duration.toDays();
		} catch (Exception e) {
			logger.error("--获取日期间隔天数失败!", e);
		}
		return 0;
	}

	/**
	 * 
	 * 描述:获取日期间隔月数(同一月间隔为0)
	 * 作者:ZhangYi
	 * 时间:2018年3月15日 下午4:39:08
	 * 参数：(参数列表)
	 * @param start	起始时间
	 * @param end	结束时间
	 * @return
	 * 
	 */
	public static int intervalMonths(Date start, Date end) {
		try {
			int interval = (Integer.valueOf(formatYear(end)) - Integer.valueOf(formatYear(start))) * 12 + ((Integer.valueOf(formatMonth(end)) - Integer.valueOf(formatMonth(start))));
			return interval;
		} catch (Exception e) {
			logger.error("--获取日期间隔月数失败!", e);
		}
		return 0;
	}
	/**
	 * 
	 * 描述:获取日期间隔月数(同一月间隔为0)
	 * 作者:ZhangYi
	 * 时间:2018年3月15日 下午4:39:08
	 * 参数：(参数列表)
	 * @param start	起始时间
	 * @param end	结束时间
	 * @return
	 * 
	 */
	public static long intervalMonths(LocalDateTime startTime, LocalDateTime endTime) {
		try {
			Period period = Period.between(startTime.toLocalDate(),endTime.toLocalDate());
			return period.toTotalMonths();
		} catch (Exception e) {
			logger.error("--获取日期间隔月数失败!", e);
		}
		return 0;
	}
	/**
	 * 
	 * 描述:获取日期间隔月数(同一月间隔为0)
	 * 作者:ZhangYi
	 * 时间:2018年3月15日 下午4:39:08
	 * 参数：(参数列表)
	 * @param start	起始时间
	 * @param end	结束时间
	 * @return
	 * 
	 */
	public static long intervalMonths(LocalDate startTime, LocalDate endTime) {
		try {
			Period period = Period.between(startTime,endTime);
			return period.toTotalMonths();
		} catch (Exception e) {
			logger.error("--获取日期间隔月数失败!", e);
		}
		return 0;
	}
	/**
	 * 
	 * 描述:获取日期的日数
	 * 作者:ZhangYi
	 * 时间:2018年3月15日 下午4:42:19
	 * 参数：(参数列表)
	 * @param date	指定日期
	 * @return
	 * 
	 */
	public static String formatDay(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd");
		String ctime = formatter.format(date);
		return ctime;
	}
	/**
	 * 
	 * 描述:获取日期的日数
	 * 作者:ZhangYi
	 * 时间:2018年3月15日 下午4:42:19
	 * 参数：(参数列表)
	 * @param date	指定日期
	 * @return
	 * 
	 */
	public static String formatDay(LocalDateTime dateTime) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd");
		ZonedDateTime zone = ZonedDateTime.of(dateTime, ZoneId.systemDefault());
		return zone.format(formatter);
	}
	/**
	 * 
	 * 描述:获取日期的日数
	 * 作者:ZhangYi
	 * 时间:2018年3月15日 下午4:42:19
	 * 参数：(参数列表)
	 * @param date	指定日期
	 * @return
	 * 
	 */
	public static String formatDay(LocalDate dateTime) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd");
		ZonedDateTime zone = ZonedDateTime.of(dateTime.atStartOfDay(), ZoneId.systemDefault());
		return zone.format(formatter);
	}
	/**
	 * 
	 * 描述:获取日期的月数
	 * 作者:ZhangYi
	 * 时间:2018年3月15日 下午4:42:19
	 * 参数：(参数列表)
	 * @param date	指定日期
	 * @return
	 * 
	 */
	public static String formatMonth(Date dateTime) {
		SimpleDateFormat formatter = new SimpleDateFormat("MM");
		String ctime = formatter.format(dateTime);
		return ctime;
	}
	/**
	 * 
	 * 描述:获取日期的月数
	 * 作者:ZhangYi
	 * 时间:2018年3月15日 下午4:42:19
	 * 参数：(参数列表)
	 * @param date	指定日期
	 * @return
	 * 
	 */
	public static String formatMonth(LocalDateTime dateTime) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM");
		ZonedDateTime zone = ZonedDateTime.of(dateTime, ZoneId.systemDefault());
		return zone.format(formatter);
	}
	/**
	 * 
	 * 描述:获取日期的月数
	 * 作者:ZhangYi
	 * 时间:2018年3月15日 下午4:42:19
	 * 参数：(参数列表)
	 * @param date	指定日期
	 * @return
	 * 
	 */
	public static String formatMonth(LocalDate dateTime) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM");
		ZonedDateTime zone = ZonedDateTime.of(dateTime.atStartOfDay(), ZoneId.systemDefault());
		return zone.format(formatter);
	}

	/**
	 * 
	 * 描述:获取日期的年
	 * 作者:ZhangYi
	 * 时间:2018年3月15日 下午4:42:19
	 * 参数：(参数列表)
	 * @param date	指定日期
	 * @return
	 * 
	 */
	public static String formatYear(Date dateTime) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
		String ctime = formatter.format(dateTime);
		return ctime;
	}
	/**
	 * 
	 * 描述:获取日期的年
	 * 作者:ZhangYi
	 * 时间:2018年3月15日 下午4:42:19
	 * 参数：(参数列表)
	 * @param date	指定日期
	 * @return
	 * 
	 */
	public static String formatYear(LocalDateTime dateTime) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy");
		ZonedDateTime zone = ZonedDateTime.of(dateTime, ZoneId.systemDefault());
		return zone.format(formatter);
	}
	/**
	 * 
	 * 描述:获取日期的年
	 * 作者:ZhangYi
	 * 时间:2018年3月15日 下午4:42:19
	 * 参数：(参数列表)
	 * @param date	指定日期
	 * @return
	 * 
	 */
	public static String formatYear(LocalDate date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy");
		ZonedDateTime zone = ZonedDateTime.of(date.atStartOfDay(), ZoneId.systemDefault());
		return zone.format(formatter);
	}

	/**
	 * 
	 * 描述:获取指定日期开始时间(格式:yyyy-MM-dd 00:00:00)
	 * 作者:ZhangYi
	 * 时间:2018年3月15日 下午4:49:04
	 * 参数：(参数列表)
	 * @param date	指定日期
	 * @return
	 * 
	 */
	public static Date formatFirstTime(Date date) {
		String dateTime = formatDate(date) + " 00:00:00";
		date = formatDateTime(dateTime);
		return date;
	}
	/**
	 * 
	 * 描述:获取指定日期开始时间(格式:yyyy-MM-dd 00:00:00)
	 * 作者:ZhangYi
	 * 时间:2018年3月15日 下午4:49:04
	 * 参数：(参数列表)
	 * @param date	指定日期
	 * @return
	 * 
	 */
	public static LocalDateTime formatFirstTime(LocalDateTime dateTime) {
		dateTime = dateTime.toLocalDate().atStartOfDay();
		return dateTime;
	}
	/**
	 * 
	 * 描述:获取指定日期开始时间(格式:yyyy-MM-dd 00:00:00)
	 * 作者:ZhangYi
	 * 时间:2018年3月15日 下午4:49:04
	 * 参数：(参数列表)
	 * @param date	指定日期
	 * @return
	 * 
	 */
	public static LocalDateTime formatFirstTime(LocalDate dateTime) {
		return dateTime.atStartOfDay();
	}

	/**
	 * 
	 * 描述:获取指定日期最后时间(格式:yyyy-MM-dd 23:59:59)
	 * 作者:ZhangYi
	 * 时间:2018年3月15日 下午4:49:04
	 * 参数：(参数列表)
	 * @param date	指定日期
	 * @return
	 * 
	 */
	public static Date formatLastTime(Date date) {
		String dateTime = formatDate(date) + " 23:59:59";
		return formatDateTime(dateTime);
	}
	/**
	 * 
	 * 描述:获取指定日期最后时间(格式:yyyy-MM-dd 23:59:59)
	 * 作者:ZhangYi
	 * 时间:2018年3月15日 下午4:49:04
	 * 参数：(参数列表)
	 * @param date	指定日期
	 * @return
	 * 
	 */
	public static LocalDateTime formatLastTime(LocalDateTime dateTime) {
		dateTime = LocalDateTime.of(dateTime.toLocalDate(), LocalTime.MAX);
		return dateTime;
	}
	/**
	 * 
	 * 描述:获取指定日期最后时间(格式:yyyy-MM-dd 23:59:59)
	 * 作者:ZhangYi
	 * 时间:2018年3月15日 下午4:49:04
	 * 参数：(参数列表)
	 * @param date	指定日期
	 * @return
	 * 
	 */
	public static LocalDateTime formatLastTime(LocalDate date) {
		LocalDateTime dateTime = LocalDateTime.of(date, LocalTime.MAX);
		return dateTime;
	}

	
	/**
	 * 
	 * 描述:获取指定周的第一天(格式:yyyy-MM-dd 00:00:00)
	 * 作者:ZhangYi
	 * 时间:2018年3月15日 下午4:49:04
	 * 参数：(参数列表)
	 * @param date	指定日期
	 * @return
	 * 
	 */
	public static Date formatWeekFirstTime(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_WEEK, 1);
		String dateTime = formatDate(calendar.getTime()) + " 00:00:00";
		date = formatDateTime(dateTime);
		return date;
	}
	/**
	 * 
	 * 描述:获取指定周的第一天(格式:yyyy-MM-dd 00:00:00)
	 * 作者:ZhangYi
	 * 时间:2018年3月15日 下午4:49:04
	 * 参数：(参数列表)
	 * @param date	指定日期
	 * @return
	 * 
	 */
	public static LocalDateTime formatWeekFirstTime(LocalDateTime dateTime) {
		dateTime = dateTime.with(DayOfWeek.of(1));
		return dateTime.toLocalDate().atStartOfDay();
	}
	/**
	 * 
	 * 描述:获取指定周的第一天(格式:yyyy-MM-dd)
	 * 作者:ZhangYi
	 * 时间:2018年3月15日 下午4:49:04
	 * 参数：(参数列表)
	 * @param date	指定日期
	 * @return
	 * 
	 */
	public static LocalDate formatWeekFirstTime(LocalDate dateTime) {
		dateTime = dateTime.with(DayOfWeek.of(1));
		return dateTime;
	}
	/**
	 * 
	 * 描述:获取指定周的最后一天(格式:yyyy-MM-dd 23:59:59)
	 * 作者:ZhangYi
	 * 时间:2018年3月15日 下午4:49:04
	 * 参数：(参数列表)
	 * @param date	指定日期
	 * @return
	 * 
	 */
	public static Date formatWeekLastTime(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_WEEK, 7);
		String dateTime = formatDate(calendar.getTime()) + " 23:59:59";
		return formatDateTime(dateTime);
	}
	/**
	 * 
	 * 描述:获取指定周的最后一天(格式:yyyy-MM-dd 23:59:59)
	 * 作者:ZhangYi
	 * 时间:2018年3月15日 下午4:49:04
	 * 参数：(参数列表)
	 * @param date	指定日期
	 * @return
	 * 
	 */
	public static LocalDateTime formatWeekLastTime(LocalDateTime dateTime) {
		dateTime = dateTime.with(DayOfWeek.of(7));
		dateTime = LocalDateTime.of(dateTime.toLocalDate(), LocalTime.MAX);
		return dateTime;
	}
	/**
	 * 
	 * 描述:获取指定周的最后一天(格式:yyyy-MM-dd)
	 * 作者:ZhangYi
	 * 时间:2018年3月15日 下午4:49:04
	 * 参数：(参数列表)
	 * @param date	指定日期
	 * @return
	 * 
	 */
	public static LocalDate formatWeekLastTime(LocalDate dateTime) {
		dateTime = dateTime.with(DayOfWeek.of(7));
		dateTime = LocalDateTime.of(dateTime, LocalTime.MAX).toLocalDate();
		return dateTime;
	}
	/**
	 * 
	 * 描述:获取指定月的第一天(格式:yyyy-MM-dd 00:00:00)
	 * 作者:ZhangYi
	 * 时间:2018年3月15日 下午4:49:04
	 * 参数：(参数列表)
	 * @param date	指定日期
	 * @return
	 * 
	 */
	public static Date formatMonthFirstTime(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		String dateTime = formatDate(calendar.getTime()) + " 00:00:00";
		date = formatDateTime(dateTime);
		return date;
	}
	/**
	 * 
	 * 描述:获取指定月的第一天(格式:yyyy-MM-dd 00:00:00)
	 * 作者:ZhangYi
	 * 时间:2018年3月15日 下午4:49:04
	 * 参数：(参数列表)
	 * @param date	指定日期
	 * @return
	 * 
	 */
	public static LocalDateTime formatMonthFirstTime(LocalDateTime dateTime) {
		dateTime = dateTime.withDayOfMonth(1);
		dateTime = dateTime.toLocalDate().atStartOfDay();
		return dateTime;
	}
	/**
	 * 
	 * 描述:获取指定月的第一天(格式:yyyy-MM-dd 00:00:00)
	 * 作者:ZhangYi
	 * 时间:2018年3月15日 下午4:49:04
	 * 参数：(参数列表)
	 * @param date	指定日期
	 * @return
	 * 
	 */
	public static LocalDate formatMonthFirstTime(LocalDate dateTime) {
		dateTime = dateTime.withDayOfMonth(1);
		return dateTime;
	}

	/**
	 * 
	 * 描述:获取指定月的最后一天(格式:yyyy-MM-dd 23:59:59)
	 * 作者:ZhangYi
	 * 时间:2018年3月15日 下午4:49:04
	 * 参数：(参数列表)
	 * @param date	指定日期
	 * @return
	 * 
	 */
	public static Date formatMonthLastTime(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, 1);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		date.setTime(calendar.getTimeInMillis() - 1 * 24 * 60 * 60 * 1000l);
		String dateTime = formatDate(date) + " 23:59:59";
		return formatDateTime(dateTime);
	}
	/**
	 * 
	 * 描述:获取指定月的最后一天(格式:yyyy-MM-dd 23:59:59)
	 * 作者:ZhangYi
	 * 时间:2018年3月15日 下午4:49:04
	 * 参数：(参数列表)
	 * @param date	指定日期
	 * @return
	 * 
	 */
	public static LocalDateTime formatMonthLastTime(LocalDateTime dateTime) {
		dateTime = dateTime.with(TemporalAdjusters.lastDayOfMonth());
		dateTime = LocalDateTime.of(dateTime.toLocalDate(), LocalTime.MAX);
		return dateTime;
	}
	/**
	 * 
	 * 描述:获取指定月的最后一天(格式:yyyy-MM-dd)
	 * 作者:ZhangYi
	 * 时间:2018年3月15日 下午4:49:04
	 * 参数：(参数列表)
	 * @param date	指定日期
	 * @return
	 * 
	 */
	public static LocalDate formatMonthLastTime(LocalDate dateTime) {
		dateTime = dateTime.with(TemporalAdjusters.lastDayOfMonth());
		return dateTime;
	}

	/**
	 * 
	 * 描述:获取指定年的第一天(格式:yyyy-01-01 00:00:00)
	 * 作者:ZhangYi
	 * 时间:2018年3月15日 下午4:49:04
	 * 参数：(参数列表)
	 * @param date	指定日期
	 * @return
	 * 
	 */
	public static Date formatYearFirstTime(Date date) {
		String dateTime = formatYear(date) + "-01-01 00:00:00";
		return formatDateTime(dateTime);
	}
	/**
	 * 
	 * 描述:获取指定年的第一天(格式:yyyy-01-01 00:00:00)
	 * 作者:ZhangYi
	 * 时间:2018年3月15日 下午4:49:04
	 * 参数：(参数列表)
	 * @param date	指定日期
	 * @return
	 * 
	 */
	public static LocalDateTime formatYearFirstTime(LocalDateTime dateTime) {
		dateTime = dateTime.with(TemporalAdjusters.firstDayOfYear());
		dateTime = LocalDateTime.of(dateTime.toLocalDate(), LocalTime.MAX);
		return dateTime;
	}
	/**
	 * 
	 * 描述:获取指定年的第一天(格式:yyyy-01-01 00:00:00)
	 * 作者:ZhangYi
	 * 时间:2018年3月15日 下午4:49:04
	 * 参数：(参数列表)
	 * @param date	指定日期
	 * @return
	 * 
	 */
	public static LocalDate formatYearFirstTime(LocalDate dateTime) {
		dateTime = dateTime.with(TemporalAdjusters.firstDayOfYear());
		return dateTime;
	}

	/**
	 * 
	 * 描述:获取指定年的最后一天(格式:yyyy-12-31 23:59:59)
	 * 作者:ZhangYi
	 * 时间:2018年3月15日 下午4:49:04
	 * 参数：(参数列表)
	 * @param date	指定日期
	 * @return
	 * 
	 */
	public static Date formatYearLastTime(Date date) {
		String dateTime = formatYear(date) + "-12-31 23:59:59";
		return formatDateTime(dateTime);
	}
	/**
	 * 
	 * 描述:获取指定年的最后一天(格式:yyyy-12-31 23:59:59)
	 * 作者:ZhangYi
	 * 时间:2018年3月15日 下午4:49:04
	 * 参数：(参数列表)
	 * @param date	指定日期
	 * @return
	 * 
	 */
	public static LocalDateTime formatYearLastTime(LocalDateTime dateTime) {
		dateTime = dateTime.with(TemporalAdjusters.lastDayOfYear());
		dateTime = LocalDateTime.of(dateTime.toLocalDate(), LocalTime.MAX);
		return dateTime;
	}
	/**
	 * 
	 * 描述:获取指定年的最后一天(格式:yyyy-12-31)
	 * 作者:ZhangYi
	 * 时间:2018年3月15日 下午4:49:04
	 * 参数：(参数列表)
	 * @param date	指定日期
	 * @return
	 * 
	 */
	public static LocalDate formatYearLastTime(LocalDate dateTime) {
		dateTime = dateTime.with(TemporalAdjusters.lastDayOfYear());
		return dateTime;
	}

	/**
	 * 
	 * 描述:获取中文日期字符串
	 * 作者:ZhangYi
	 * 时间:2018年3月15日 下午4:50:26
	 * 参数：(参数列表)
	 * @param date	指定日期
	 * @return
	 * 
	 */
	public static String formatChinaDate(Date date) {
		String format = "yyyy年MM月dd日";
		return formatDateTime(date, format);
	}

	/**
	 * 
	 * 描述:[字符串型]日期转化指定UTF格式字符串
	 * 作者:ZhangYi
	 * 时间:2018年3月15日 下午3:25:25
	 * 参数：(参数列表)
	 * @param date		日期时间
	 * @param format	日期格式(默认:yyyy-MM-dd'T'HH:mm:ss.SSSZ)
	 * @return
	 * 
	 */
	public static String formatUTCDateTime(Date date, String format) {
		if (StringUtils.isEmpty(format)) format = UTC_FORMAT_DATE_TIME;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int zoneOffset = calendar.get(Calendar.ZONE_OFFSET);// 时间偏移量
		int dstOffset = calendar.get(Calendar.DST_OFFSET);// 夏令时差
		calendar.add(Calendar.MILLISECOND, -(zoneOffset + dstOffset));// UTC时间算法
		return formatDateTime(calendar.getTime(), format);
	}
	/**
	 * 
	 * 描述:[字符串型]日期转化指定UTF格式字符串
	 * 作者:ZhangYi
	 * 时间:2018年3月15日 下午3:25:25
	 * 参数：(参数列表)
	 * @param date		日期时间
	 * @param format	日期格式(默认:yyyy-MM-dd'T'HH:mm:ss.SSSZ)
	 * @return
	 * 
	 */
	public static String formatUTCDateTime(LocalDateTime dateTime, String format) {
		if (StringUtils.isEmpty(format)) format = UTC_FORMAT_DATE_TIME;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
		ZonedDateTime zone = ZonedDateTime.of(dateTime, ZoneId.systemDefault());
		return zone.format(formatter);
	}
	/**
	 * 
	 * 描述:[字符串型]日期转化指定UTF格式字符串
	 * 作者:ZhangYi
	 * 时间:2018年3月15日 下午3:25:25
	 * 参数：(参数列表)
	 * @param date		日期时间
	 * @param format	日期格式(默认:yyyy-MM-dd'T'HH:mm:ss.SSSZ)
	 * @return
	 * 
	 */
	public static String formatUTCDateTime(LocalDate dateTime, String format) {
		if (StringUtils.isEmpty(format)) format = UTC_FORMAT_DATE_TIME;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
		ZonedDateTime zone = ZonedDateTime.of(dateTime.atStartOfDay(), ZoneId.systemDefault());
		return zone.format(formatter);
	}

	/**
	 * 
	 * 描述:格式化合并日期时间(格式:yyyy-MM-dd ~ MM-dd 或 yyyy-MM-dd HH:mm ~ yyyy-MM-dd HH:mm)
	 * 作者:ZhangYi
	 * 时间:2018年3月15日
	 * 参数:参数列表
	 * @param start 	开始时间
	 * @param end 		结束时间
	 * @param showTime 	显示方式(false:仅显示日期[格式:yyyy-MM-dd],true:显示时间[格式:yyyy-MM-dd HH:mm])
	 * @return
	 * 
	 */
	public static String formatRangeDateTime(Date start, Date end, boolean showTime) {
		String startTime = formatDateTime(start, DEFAULT_FORMAT_DATE);
		String endTime = formatDateTime(end, DEFAULT_FORMAT_DATE);
		if (!showTime) {
			if (formatYear(start) == formatYear(end)) {
				if (startTime == endTime) {
					return startTime;
				}
				return startTime + " ~ " + formatDateTime(end, FORMAT_PATTERN_DATE);
			}
			return startTime + " ~ " + endTime;
		} else {
			if (startTime == endTime) {
				return formatDateTime(start, FORMAT_DATE_TIME) + " ~ " + formatDateTime(end, FORMAT_PATTERN_TIME);
			}
			if (formatYear(start) == formatYear(end)) {
				return formatDateTime(start, FORMAT_DATE_TIME) + " ~ " + formatDateTime(end, FORMAT_PATTERN_DATE_TIME);
			}
			return formatDateTime(start, FORMAT_DATE_TIME) + " ~ " + formatDateTime(end, FORMAT_DATE_TIME);
		}
	}
	/**
	 * 
	 * 描述:格式化合并日期时间(格式:yyyy-MM-dd ~ MM-dd 或 yyyy-MM-dd HH:mm ~ yyyy-MM-dd HH:mm)
	 * 作者:ZhangYi
	 * 时间:2018年3月15日
	 * 参数:参数列表
	 * @param start 	开始时间
	 * @param end 		结束时间
	 * @param showTime 	显示方式(false:仅显示日期[格式:yyyy-MM-dd],true:显示时间[格式:yyyy-MM-dd HH:mm])
	 * @return
	 * 
	 */
	public static String formatRangeDateTime(LocalDateTime start, LocalDateTime end, boolean showTime) {
		String startTime = formatDateTime(start, DEFAULT_FORMAT_DATE);
		String endTime = formatDateTime(end, DEFAULT_FORMAT_DATE);
		if (!showTime) {
			if (formatYear(start) == formatYear(end)) {
				if (startTime == endTime) {
					return startTime;
				}
				return startTime + " ~ " + formatDateTime(end, FORMAT_PATTERN_DATE);
			}
			return startTime + " ~ " + endTime;
		} else {
			if (startTime == endTime) {
				return formatDateTime(start, FORMAT_DATE_TIME) + " ~ " + formatDateTime(end, FORMAT_PATTERN_TIME);
			}
			if (formatYear(start) == formatYear(end)) {
				return formatDateTime(start, FORMAT_DATE_TIME) + " ~ " + formatDateTime(end, FORMAT_PATTERN_DATE_TIME);
			}
			return formatDateTime(start, FORMAT_DATE_TIME) + " ~ " + formatDateTime(end, FORMAT_DATE_TIME);
		}
	}
	/**
	 * 
	 * 描述:格式化合并日期时间(格式:yyyy-MM-dd ~ MM-dd 或 yyyy-MM-dd HH:mm ~ yyyy-MM-dd HH:mm)
	 * 作者:ZhangYi
	 * 时间:2018年3月15日
	 * 参数:参数列表
	 * @param start 	开始时间
	 * @param end 		结束时间
	 * @param showTime 	显示方式(false:仅显示日期[格式:yyyy-MM-dd],true:显示时间[格式:yyyy-MM-dd HH:mm])
	 * @return
	 * 
	 */
	public static String formatRangeDateTime(LocalDate start, LocalDate end, boolean showTime) {
		String startTime = formatDate(start);
		String endTime = formatDate(end);
		if (!showTime) {
			if (formatYear(start) == formatYear(end)) {
				if (startTime == endTime) {
					return startTime;
				}
				return startTime + " ~ " + formatDateTime(end, FORMAT_PATTERN_DATE);
			}
			return startTime + " ~ " + endTime;
		} else {
			if (startTime == endTime) {
				return formatDateTime(formatFirstTime(start), FORMAT_DATE_TIME) + " ~ " + formatDateTime(formatLastTime(end), FORMAT_PATTERN_TIME);
			}
			if (formatYear(start) == formatYear(end)) {
				return formatDateTime(formatFirstTime(start), FORMAT_DATE_TIME) + " ~ " + formatDateTime(formatLastTime(end), FORMAT_PATTERN_DATE_TIME);
			}
			return formatDateTime(formatFirstTime(start), FORMAT_DATE_TIME) + " ~ " + formatDateTime(formatLastTime(end), FORMAT_DATE_TIME);
		}
	}
	/**
	  * 描述: 合并日期(格式:MM-dd HH:mm~HH:mm)
	  * 作者: ZhangYi
	  * 时间: 2018年3月15日 下午1:31:58
	  * 参数: (参数列表)
	 * @param start 	开始时间
	 * @param end 		结束时间
	 * @return
	 */
	public static String formatRangeDateEng(Date start, Date end) {
		String rangeTime = formatDateTime(start, FORMAT_PATTERN_DATE_TIME);
		rangeTime += "~" + formatDateTime(end, FORMAT_PATTERN_TIME);
		return rangeTime;
	}
	/**
	 * 描述: 合并日期(格式:MM-dd HH:mm~HH:mm)
	 * 作者: ZhangYi
	 * 时间: 2018年3月15日 下午1:31:58
	 * 参数: (参数列表)
	 * @param start 	开始时间
	 * @param end 		结束时间
	 * @return
	 */
	public static String formatRangeDateEng(LocalDateTime start, LocalDateTime end) {
		String rangeTime = formatDateTime(start, FORMAT_PATTERN_DATE_TIME);
		rangeTime += "~" + formatDateTime(end, FORMAT_PATTERN_TIME);
		return rangeTime;
	}
	/**
	 * 描述: 合并日期(格式:MM-dd HH:mm~HH:mm)
	 * 作者: ZhangYi
	 * 时间: 2018年3月15日 下午1:31:58
	 * 参数: (参数列表)
	 * @param start 	开始时间
	 * @param end 		结束时间
	 * @return
	 */
	public static String formatRangeDateEng(LocalDate start, LocalDate end) {
		String rangeTime = formatDateTime(start, FORMAT_PATTERN_DATE_TIME);
		rangeTime += "~" + formatDateTime(end, FORMAT_PATTERN_TIME);
		return rangeTime;
	}

	public static void main(String[] args) {
		// String date = "1970-01-01 00:00:00";
		// String time = "1461032462000";
		// String to = "1462032000000";
		// long t1 = Long.valueOf(time) / (24 * 60 * 60 * 1000);
		// System.out.println(t1);
		// System.out.println(DateUtil.getDate(date).getTime());
		// System.out.println(DateUtil.formatDateTime(time));
		// System.out.println(DateUtil.getDate("2016-05-01"));
		// System.out.println(DateUtil.getChinaDateYMD(new Date()));
		// System.out.println(DateUtil.formatDateTime(getLastTime(new Date())));
		// Calendar calender1 = Calendar.getInstance();
		// calender1.setTime(new Date());
		// calender1.add(Calendar.DATE, 30);
		// String dateTime1 = formatDateTime(calender1.getTime());
		// System.out.println(dateTime1);
		// GregorianCalendar calendar = new GregorianCalendar();
		// calendar.setTime(new Date());
		// calendar.add(Calendar.MONTH, 2);
		// calendar.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
		// calendar.set(Calendar.DAY_OF_WEEK_IN_MONTH, 4);
		String CHINA_FORMAT_DATE_TIME =
				"G yyyy年MM月dd日 a HH时mm分ss秒SSS毫秒 zZ(本年第w周,本月第W周,本年第D天,本月第F星期的E)";
		String dateTime =
				formatDateTime(formatDateTime("2016-07-31 13:26:50"), CHINA_FORMAT_DATE_TIME);
		System.out.println(dateTime);
		Date today = new Date();
		System.out.println(dateTimeToDate(today.getTime()));
		System.out.println(dateToLocalDate(today.getTime()));
		System.out.println(dateToDateTime(today.getTime()));
		System.out.println("===================================================================");
		System.out.println(formatFirstTime(today));
		System.out.println(formatLastTime(today));
		System.out.println(formatWeekFirstTime(today));
		System.out.println(formatWeekLastTime(today));
		System.out.println(formatMonthFirstTime(today));
		System.out.println(formatMonthLastTime(today));

		Date start = formatDate("2016-11-01");
		Date end = formatDate("2016-11-23");
		System.out.println(intervalDays(start, end));
		System.out.println("========================================================================");
		LocalDateTime now = LocalDateTime.now();
		System.out.println(formatFirstTime(now));
		System.out.println(formatLastTime(now));
		System.out.println(formatWeekFirstTime(now));
		System.out.println(formatWeekLastTime(now));
		System.out.println(formatMonthFirstTime(now));
		System.out.println(formatMonthLastTime(now));

		LocalDate startTime = formatLocalDate("2016-11-01");
		LocalDate endTime = formatLocalDate("2016-11-23");
		System.out.println(intervalDays(startTime, endTime));
	}
}

