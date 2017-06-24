package br.com.racc.util;

import java.util.Calendar;
import java.util.Date;

public class DateUtil {

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
	
	private static void removeTimeFromDate(final Calendar calendar) {
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
	}
}