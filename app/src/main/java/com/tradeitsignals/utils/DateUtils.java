package com.tradeitsignals.utils;

import com.tradeitsignals.logging.TILogger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

	public final static String FORMAT_DEFAULT = "yyyy/MM/dd HH:mm:ss z";
	public final static String FORMAT_GLOBAL_DATE = "MMM-dd-yyyy HH:mm:ss";
	public final static String FORMAT_LOG_FILE_NAME = "MMM-dd-yyyy";
	public final static String FORMAT_PARSE_COM = "MMM dd, yyyy, HH:mm";
	public final static String FORMAT_PARSE_DATE_OBJECT = "YYYY-MM-DDTHH:MM:SS.MMMZ";
	public final static String FORMAT_PREFERENCES_TIME = "h:mm a";

	// Currency feed pubDate: "Tue, 21 Jul 2015 15:53:54 GMT"
	public final static String FORMAT_CURRENCY_FEED = "EEE, d MMM yyyy HH:mm:ss z";

	public static String formatToString(Date date) {
		return formatToString(FORMAT_DEFAULT, date);
	}

	public static String formatToString(String format, Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}

	public static String formatToString(String format, long time) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(time);
	}

	/**
	 * Uses the Default format of yyyy/MM/dd HH:mm:ss z.
	 *
	 * Caution!! Can return null.
	 * @param dateString The String to be turned into a Date Object.
	 * @return The Date Object of the date String param.
	 */
	public static Date formatToDate(String dateString) {
		return formatToDate(FORMAT_DEFAULT, dateString);
	}

	/**
	 * Caution!! Can return null.
	 * @param format String indicating to which format the dateString should be formatted to.
	 * @param dateString The date String to be formatted into a Date Object.
	 * @return The Date Object of the date String param.
	 */
	public static Date formatToDate(String format, String dateString) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			return sdf.parse(dateString);
		} catch (ParseException e) {
			TILogger.getLog().e("DateUtils", "Could not format: " + dateString + " to the format of: " + format + " in DateUtils.formatToDate()", e);
			return null;
		}
	}

	public static void resetTimeOfDay(Calendar calendar) {
		calendar.set(Calendar.HOUR, calendar.getActualMinimum(Calendar.HOUR));
		calendar.set(Calendar.MINUTE, calendar.getActualMinimum(Calendar.MINUTE));
		calendar.set(Calendar.SECOND, calendar.getActualMinimum(Calendar.SECOND));
		calendar.set(Calendar.MILLISECOND, calendar.getActualMinimum(Calendar.MILLISECOND));
	}

}
