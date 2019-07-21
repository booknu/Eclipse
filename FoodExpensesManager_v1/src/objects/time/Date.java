package objects.time;

import java.io.Serializable;

import functions.CalendarFunc;

/**
 * 날짜를 관리
 * 만약 month, date가 -1이라면 month나 date는 사용하지 않는 날짜
 * @author Administrator
 *
 */
public class Date implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int year;
	private int month;	// 1 ~ 12
	private int date;

	public Date(int y, int m, int d)
	{
		year = y;
		month = m;
		date = d;
	}

	public Date(int y, int m)
	{
		this(y, m, -1);
	}

	public Date(int y)
	{
		this(y, -1, -1);
	}

	public int getYear() { return year; }
	public int getMonth() { return month; }
	public int getDate() { return date; }
	/**
	 * 이 객체의 복사본을 반환
	 * @return 이 객체의 복사본 (reference 아님)
	 */
	public Date getCopyOfDate() { return new Date(year, month, date); }

	public void setYear(int y) { year = y; }
	public void setMonth(int m) { month = m;}
	public void setDate(int d) { date = d; }
	public void setDate(Date date) { year = date.getYear(); month = date.getMonth(); this.date = date.getDate(); }

	/**
	 * howMuch 만큼 년 수를 늘린다.
	 * @param howMuch 얼만큼?
	 */
	public void upYear(int howMuch) { year += howMuch; }
	/**
	 * howMuch 만큼 년 수를 줄인다.
	 * @param howMuch 얼만큼?
	 */
	public void downYear(int howMuch) { year -= howMuch; }

	/**
	 * howMuch만큼 달 수를 늘린다.
	 * @param howMuch 얼만큼?
	 */
	public void upMonth(int howMuch)
	{
		for(int i = 0; i < howMuch; i++)
		{
			if(month == 12) // 12월 달이면 1년 증가시키고 1월달로 설정
			{
				year ++;
				month = 1;
			}
			else 
				month ++;
		}
	}

	/**
	 * howMuch만큼 달 수를 줄인다.
	 * @param howMuch 얼만큼?
	 */
	public void downMonth(int howMuch)
	{
		for(int i = 0; i < howMuch; i++)
		{
			if(month == 1) // 1월 달이면 1년 감소시키고 12월달로 설정
			{
				year --;
				month = 12;
			}
			else 
				month --;
		}
	}

	/**
	 * howMuch만큼 일 수를 늘린다.
	 * @param howMuch 얼만큼?
	 */
	public void upDate(int howMuch)
	{
		for(int i = 0; i < howMuch; i++)
		{
			if(date == CalendarFunc.numberOfDays(year, month - 1)) // 그 월의 마지막 날
			{
				if(month == 12) // 마지막 월
				{
					year ++;
					month = 1;
					date = 1;
				}
				else // 마지막 날에서 다음 월로 넘길 경우
				{
					month ++;
					date = 1;
				}
			}

			else // 마지막 날이 아닌 경우 평범하게 date++
				date ++;
		}
	}

	/**
	 * howMuch만큼 일 수를 줄인다.
	 * @param howMuch 얼만큼?
	 */
	public void downDate(int howMuch)
	{
		for(int i = 0; i < howMuch; i++)
		{
			if(date == 1) // 1일의 경우
			{
				if(month == 1) // 1월 1일의 경우
				{
					year --;
					month = 12;
					date = CalendarFunc.numberOfDays(year - 1, 11);
				}

				else // 1일에서 월을 이전으로 넘길 경우
				{
					month --;
					date = CalendarFunc.numberOfDays(year, month - 1);
				}

			}
			else // 1일이 아닌 경우 평범하게 date--
				date --;
		}
	}


	public boolean isSameYear(Date date)
	{
		if(year == date.getYear())
			return true;
		return false;
	}

	public boolean isSameMonth(Date date)
	{
		if(year == date.getYear() && month == date.getMonth())
			return true;
		return false;
	}

	public boolean isSameDate(Date c_date)
	{
		if(year == c_date.getYear() && month == c_date.getMonth() && date == c_date.getDate())
			return true;
		return false;
	}

	/**
	 * 이 객체의 날짜가
	 * 파라미터로 입력받은 c_date 보다 이를 경우 true를 반환한다
	 * @param c_date 비교할 날짜
	 * @return	이 객체가 더 이름 = true ( ex> 6 / 24 , 7 / 1 )
	 * 			이 객체가 더 늦음 = false ( ex> 7 / 1 , 6 / 24 )
	 * 			두 날짜가 같을 경우 false
	 */
	public boolean isEarlierThan(Date c_date)
	{
		if(isSameYear(c_date))
		{
			if(month < c_date.getMonth())
				return true;
			else if(month > c_date.getMonth())
				return false;
			else	// 월이 같은 경우
			{
				if(date < c_date.getDate())
					return true;
				else
					return false;
			}
		}	
		else
		{
			if(year < c_date.getYear())
				return true;
			else
				return false;
		}

	}
	
	public String toString()
	{
		return year + "-" + month + "/" + date;
	}
}
