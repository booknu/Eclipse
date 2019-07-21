package objects.time;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Time implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private RolloverCounter hour;
	private int ampmHour;
	private RolloverCounter minute;
	private boolean am;

	/**
	 * 구성자
	 * @param h hour의 default값
	 * @param m minute의 default값
	 */
	public Time(int h, int m)
	{
		hour = new RolloverCounter(1, 24, h);
		minute = new RolloverCounter(0, 59, m);
	}

	/* 비교 메소드 ************************************************************/
	
	public boolean isEarlierThan(Time anotherTime)
	{ return this.getConvertedMinute() < anotherTime.getConvertedMinute(); }
	
	public boolean isLaterThan(Time anotherTime)
	{ return this.getConvertedMinute() > anotherTime.getConvertedMinute(); }
	
	public boolean equals(Time anotherTime)
	{ return this.getConvertedMinute() == anotherTime.getConvertedMinute(); }
	
	/* set 메소드 ****************************************************/
	/**
	 * hour을 설정 (범위를 벗어나면 설정을 취소하고 false 반환)
	 * @param h 시간
	 * @return hour이 범위를 벗어나지 않았는지 (true = 올바름, false = 오류)
	 */
	public boolean setHour(int h) { 
		return hour.setValue(h);
	}

	/**
	 * minute을 설정 (범위를 벗어나면 설정을 취소하고 false 반환)
	 * @param m 시간
	 * @return minute가 범위를 벗어나지 않았는지 (true = 올바름, false = 오류)
	 */
	public boolean setMinute(int m) {
		return minute.setValue(m);
	}
	
	/**
	 * minute를 1 만큼 조절한다.
	 * minute이 최대가 되면 0 으로 변하고 hour이 1 증가한다.
	 * minute이 최소가 되면 59 로 변하고 hour이 1 감소한다.
	 * @param up 증가시킬지 감소시킬지 (true = 증가, false = 감소)
	 */
	public void scrollMinute(boolean up)
	{
		// minute을 올리라는 신호면
		if(up)
		{
			if(minute.increment())	// minute 1 증가
				scrollHour(true); // 만약 minute이 0으로 초기화 되면 hour 1 증가
		}

		// minute을 내리라는 신호면
		else
		{
			if(minute.decrement()) // minute 1 감소
				scrollHour(false); // 만약 minute이 0 -> 59가 되면 hour 1 감소
		}
	}

	/**
	 * hour를 1 만큼 조절한다.
	 * hour이 최대 혹은 최소가 되면 am_pm 이 바뀐다.
	 * @param up 증가시킬지 감소시킬지 (true = 증가, false = 감소)
	 */
	public void scrollHour(boolean up)
	{
		// hour을 올리라는 신호면
		if(up)
			hour.increment();

		// hour을 내리라는 신호면
		else
			hour.decrement(); // hour 1 감소
	}

	/**
	 * 현재 am_pm 값을 바꾼다.
	 */
	public void switchAmPm()
	{
		hour.setValue((hour.getValue() + 12) % 24);
	}

	/* get 메소드 ***************************************/
	public int get24Hour() { return hour.getValue(); }
	public int get12Hour() { convertToAmPm(); return ampmHour; }
	public boolean getAmPm() { convertToAmPm(); return am; }
	public String getAmPmText()
	{
		if(getAmPm())
			return "AM";
		else
			return "PM";
	}
	public int getMinute() { return minute.getValue(); }
	/** 시간을 분으로 변환할 것을 반환 */
	public int getConvertedMinute() { return hour.getValue() * 60 + minute.getValue(); }
	public String getTwoDigitMinute() { return minute.getTwoDigitString(); }
	public String toString() { return get12Hour() + ":" + getTwoDigitMinute() + " " +  getAmPmText(); }
	public String toStringNoAMPM() { return get24Hour() + ";" + getTwoDigitMinute(); }
	public static Time getCurrentTimeForm()
	{
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat("H");
		int hour = Integer.parseInt(dateFormat.format(cal.getTime()));
		dateFormat = new SimpleDateFormat("m");
		int min =  Integer.parseInt(dateFormat.format(cal.getTime()));
		return new Time(hour, min);
	}
	/* private 메소드 *******************************************************/
	/**
	 * boolean am, int ampmHour을 24시간제인 hour을 기준으로 설정한다.
	 * @param h
	 */
	private void convertToAmPm()
	{
		// 오전일 경우
		if(hour.getValue() <= 12)
		{
			am = true;
			ampmHour = hour.getValue();
		}
		// 오후일 경우
		else
		{
			am = false;
			ampmHour = hour.getValue() - 12;
		}
	}
}
