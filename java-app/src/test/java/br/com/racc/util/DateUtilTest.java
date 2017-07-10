package br.com.racc.util;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

public class DateUtilTest {

	@Test
	public void testZeroDay() throws Exception {
		Calendar calendarToday = Calendar.getInstance();
		calendarToday.setTime(DateUtil.zeroDay());

		Calendar calendarNewDate = Calendar.getInstance();
		calendarNewDate.setTime(new Date());
		removeTimeFromDate(calendarNewDate);
		calendarNewDate.set(Calendar.DAY_OF_MONTH, 1);
		calendarNewDate.set(Calendar.MONTH, 1);
		calendarNewDate.set(Calendar.YEAR, 1970);

		assertThat(calendarNewDate.getTime(), equalTo(calendarToday.getTime()));
	}

	@Test
	public void testToday() throws Exception {
		Calendar calendarToday = Calendar.getInstance();
		calendarToday.setTime(DateUtil.today());

		Calendar calendarNewDate = Calendar.getInstance();
		calendarNewDate.setTime(new Date());
		removeTimeFromDate(calendarNewDate);

		assertThat(calendarNewDate.getTime(), equalTo(calendarToday.getTime()));
	}

	@Test
	public void testYesterday() throws Exception {
		Calendar calendarToday = Calendar.getInstance();
		calendarToday.setTime(DateUtil.yesterday());

		Calendar calendarNewDate = Calendar.getInstance();
		calendarNewDate.setTime(new Date());
		calendarNewDate.add(Calendar.DATE, -1);
		removeTimeFromDate(calendarNewDate);

		assertThat(calendarNewDate.getTime(), equalTo(calendarToday.getTime()));
	}

	@Test
	public void testRemoveTimeFromDateDate() throws Exception {
		Calendar calendarToday = Calendar.getInstance();
		calendarToday.setTime(DateUtil.removeTimeFromDate(new Date()));

		Calendar calendarNewDate = Calendar.getInstance();
		calendarNewDate.setTime(new Date());
		removeTimeFromDate(calendarNewDate);

		assertThat(calendarNewDate.getTime(), equalTo(calendarToday.getTime()));
	}

	private void removeTimeFromDate(Calendar calendarNewDate) {
		calendarNewDate.set(Calendar.HOUR_OF_DAY, 0);
		calendarNewDate.set(Calendar.MINUTE, 0);
		calendarNewDate.set(Calendar.SECOND, 0);
		calendarNewDate.set(Calendar.MILLISECOND, 0);
	}
}
