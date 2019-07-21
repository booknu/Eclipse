package Clock;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Clock {

	private RolloverCounter hours, minutes, seconds;
	private int years, months, dates;
	private RolloverCounter amPm = new RolloverCounter(2);; // 0 = AM, 1 = PM
	private String StringAmPm;
	
	/**
	 * Clock 오브젝트의 구성자
	 * 12시간 60분짜리 시계를 만들어 00:00으로 설정한다.
	 */
	public Clock()
	{
		hours = new RolloverCounter(12); minutes = new RolloverCounter(60); seconds = new RolloverCounter(60);
		hours.setValue(0);
		minutes.setValue(0);
		seconds.setValue(0);
		amPm.setValue(0);
	}
	
	/**
	 * Clock 오브젝트의 구성자
	 * 주어진 값의 시간과 분 시스템의 시계를 만들어 00:00:00으로 설정한다.
	 * @param hour 시간
	 * @param minute 분
	 * @param second 초
	 */
	public Clock(int hour, int minute, int second)
	{
		hours = new RolloverCounter(hour);
		minutes = new RolloverCounter(minute);
		seconds = new RolloverCounter(second);
		hours.setValue(0);
		minutes.setValue(0);
		seconds.setValue(0);
		amPm.setValue(0);
	}
	
	/**
	 * 이 메소드는 매 분 한 번씩 호출되야한다.
	 * 시계 display가 1초 앞으로 간다.
	 */
	public void timeTick()
	{
		seconds.increment();
			
		if(seconds.getValue() == 0)
		{
			minutes.increment();
			
			if(minutes.getValue() == 0)
			{
				hours.increment();	// 0분이 되면 1시간 올림
				
				if(hours.getValue() == 12)
				{
					amPm.increment();
				}
			}
		}
		
		
	}
	
	/**
	 * display의 시간을 설정한다.
	 * @param hour 시간
	 * @param minute 분
	 * @param second 초
	 */
	public void setTime(int hour, int minute, int second)
	{
		if(isAmPm() && hour>=hours.getLimit())
		{
			amPm.setValue(1);
			hours.setValue(hour - 12);
		}
		
		if(isAmPm() && hour == 12)
		{
			amPm.setValue(1);
		}
		
		hours.setValue(hour);
		minutes.setValue(minute);
		seconds.setValue(second);
	}
	
	/**
	 * "년 월 일" 을 현재 날짜로 설정한다. 
	 * @param year 년
	 * @param month 월
	 * @param date 일
	 */
	public void setDate()
	{
		SimpleDateFormat sdf = new SimpleDateFormat("YYYY년 M월 d일 a h시 m분 s");
		Date today = new Date();
		String time = sdf.format(today);
		
		Functions ft = new Functions();
		int i [] = ft.returnNumbers(time);
		years = i[0]; months = i[1]; dates = i[2];
	}

	/**
	 * 현재 날짜를 YYYY년 MM월 DD일 형식으로 반환한다.
	 * @return 현재 날짜 (YYYY년 MM월 DD일)
	 */
	public String getDate()
	{
		return years + "년 " + months + "월 " + dates + "일 ";
	}
	
	public boolean isAmPm()
	{
		if(hours.getLimit()==12 && minutes.getLimit()==60 && seconds.getLimit() == 60)
			return true;
		else
			return false;
	}
	
	/**
	 * 현재 시간을 HH : MM 형식으로 반환한다
	 * @return 현재 시간 (HH : MM)
	 */
	public String getTime()
	{
		if(amPm.getValue() == 0)
			StringAmPm = "AM";
		else
			StringAmPm = "PM";
		if(hours.getLimit()==12 && minutes.getLimit()==60 && seconds.getLimit() == 60)	// 12시간 60분 짜리 시계일 경우 AM PM 표시
			return hours.getTwoDigitString() + " : " + minutes.getTwoDigitString() + " : " + seconds.getTwoDigitString() + " " + StringAmPm;
		else	// 아닐 경우 시,분 만 표시
			return hours.getTwoDigitString() + " : " + minutes.getTwoDigitString() + " : " + seconds.getTwoDigitString();
	}

	public int getHour()
	{
		return hours.getValue();
	}
	
	public int getMinute()
	{
		return minutes.getValue();
	}
	
	public int getSecond()
	{
		return seconds.getValue();
	}

}
