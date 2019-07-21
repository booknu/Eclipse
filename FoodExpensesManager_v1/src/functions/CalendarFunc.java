package functions;

import java.util.Calendar;

import objects.time.Date;

public class CalendarFunc {

	/**
	 * 해당 연도가 윤년인지?
	 * @param year 연도
	 * @return true = 윤년, false = 윤년 아님
	 */
	public static boolean isLeapYear(int year)
	{
		// 4의 배수가 되는 해는 윤년이다.
		// 여기서 100의 배수가 되는 해는 제외한다.
		// 400의 배수가 되는 해는 무조건 윤년이다
		if(year % 400 == 0 || (year % 4 == 0 && year % 100 != 0))
			return true;
		return false;
	}

	/**
	 * 해당 년, 월의 날짜 개수를 반환
	 * @param year 년
	 * @param month 월 (0 ~ 11)
	 * @return 날짜의 개수 (ex 1월 = 31)
	 */
	public static int numberOfDays(int year, int month)
	{
		boolean leapYear = isLeapYear(year);
		switch(month){
		case 0: return 31;
		case 1:
			if(!leapYear)	return 28;
			else	return 29; 	// 윤달이면 29 반환
		case 2: return 31;
		case 3: return 30;
		case 4: return 31;
		case 5: return 30;
		case 6: return 31;
		case 7: return 31;
		case 8: return 30;
		case 9: return 31;
		case 10: return 30;
		case 11: return 31;
		default: return -1;
		}
	}

	/**
	 * 해당 날짜의 요일을 찾아냄
	 * @param year 년
	 * @param month 월
	 * @param date 일
	 * @return 요일 (1 = 일, 7 = 토)
	 */
	public static int getDay(int year, int month, int date)
	{
		Calendar cal = Calendar.getInstance();
		cal.set(year, month, date);
		return cal.get(Calendar.DAY_OF_WEEK);
	}

	/**
	 * 요일(int) 에 해당하는 문자열 (일, 월, 화 ... ) 를 반환
	 * @param day 요일 (1 = 일, 7 = 토)
	 * @return 해당하는 문자열
	 */
	public static String getStringOfDay(int day)
	{
		switch(day)
		{
		case 1: return "일";
		case 2: return "월";
		case 3: return "화";
		case 4: return "수";
		case 5: return "목";
		case 6: return "금";
		case 7: return "토";
		default: return "error";
		}
	}

	public static int getCurrentYear()
	{
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		return year;
	}

	public static int getCurrentMonth()
	{
		Calendar cal = Calendar.getInstance();
		int month = cal.get(Calendar.MONTH);
		return month;
	}

	public static int getCurrentDate()
	{
		Calendar cal = Calendar.getInstance();
		int date = cal.get(Calendar.DAY_OF_MONTH);
		return date;
	}
	
	public static Date getCurrentDateForm()
	{
		return new Date(getCurrentYear(), getCurrentMonth() + 1, getCurrentDate());
	}

	public static boolean isSameYear(Date date, Date c_date)
	{
		if(date.getYear() == c_date.getYear())
			return true;
		return false;
	}

	public static boolean isSameMonth(Date date, Date c_date)
	{
		if(date.getYear() == c_date.getYear() && date.getMonth() == c_date.getMonth())
			return true;
		return false;
	}

	public static boolean isSameDate(Date date, Date c_date)
	{
		if(date.getYear() == c_date.getYear() && date.getMonth() == c_date.getMonth() 
				&& date.getDate() == c_date.getDate())
			return true;
		return false;
	}

	/**
	 * 앞의 날짜가 뒤의 날짜보다 먼저 오는지
	 * @param year
	 * @param month
	 * @param date
	 * @param c_year
	 * @param c_month
	 * @param c_date
	 * @return	앞의 날짜가 더 이름 = true ( ex> 6 / 24 , 7 / 1 )
	 * 			앞의 날짜가 더 늦음 = false ( ex> 7 / 1 , 6 / 24 )
	 * 			두 날짜가 같을 경우 false
	 */
	public static boolean isEarlierThan(Date date, Date c_date)
	{
		if(isSameYear(date, c_date))
		{
			if(date.getMonth() < c_date.getMonth())
				return true;
			else if(date.getMonth() > c_date.getMonth())
				return false;
			else	// 월이 같은 경우
			{
				if(date.getDate() < c_date.getDate())
					return true;
				else
					return false;
			}
		}	
		else
		{
			if(date.getYear() < c_date.getYear())
				return true;
			else
				return false;
		}

	}
	
	public static String getTwoDigitMonth(int month)
	{
		if(month < 10)
			return "0" + month;
		else
			return ""+month;
	}
}
