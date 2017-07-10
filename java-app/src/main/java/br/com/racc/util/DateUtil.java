package br.com.racc.util;

import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	
	public static Date zeroDay() {
		final Calendar calendar = Calendar.getInstance();
		removeTimeFromDate(calendar);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.MONTH, 1);
		calendar.set(Calendar.YEAR, 1970);
		return calendar.getTime();
	}

	public static Date today() {
		final Calendar calendar = Calendar.getInstance();
		removeTimeFromDate(calendar);
		return calendar.getTime();
	}

	public static Date yesterday() {
		final Calendar calendar = Calendar.getInstance();
		removeTimeFromDate(calendar);
		calendar.add(Calendar.DATE, -1);
		return calendar.getTime();
	}

	public static Date removeTimeFromDate(final Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		remoteTimeFromCalendar(calendar);
		return calendar.getTime();
	}

	private static void removeTimeFromDate(final Calendar calendar) {
		remoteTimeFromCalendar(calendar);
	}

	private static void remoteTimeFromCalendar(Calendar calendar) {
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
	}
}
