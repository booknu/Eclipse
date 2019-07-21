package objects;

import java.io.Serializable;

import functions.CalendarFunc;
import objects.time.Date;
import objects.time.Time;

/**
 * 콘솔창에 나타나는 로그
 * @author Administrator
 *
 */
public class Log implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final int TYPE_NUM = 4;
	
	private Date date;
	private Time time;

	private String content;
	private String note;
	private int type;
	/**
	 * 콘솔창에 나타나는 로그
	 * @param date 날짜
	 * @param time 시간
	 * @param type 타입
	 *	 	0: 저장
	 * 	1: 초기화
	 * 	2: 백업
	 * 	3: 복원
	 * 	4: 저장하지 않고 종료
	 * 	5: 로그 초기화
	 * 	6: 계획 추가
	 * 	7: 기본 목표액 변경
	 * 	8: 목표액 변경
	 */
	public Log(Date date, Time time, int type, String note)
	{
		this.date = date;
		this.time = time;
		this.note = note;
		setType(type);
	}
	
	public Log(int type, String note)
	{
		this(CalendarFunc.getCurrentDateForm(), Time.getCurrentTimeForm(), type	, note);
	}

	public Date getDate() { return date; }
	public Time getTime() { return time; }
	public String getContent() { return content; }
	public int getType() { return type; }

	public void setDate(Date date) { this.date = date; }
	public void setTime(Time time) {this.time = time; }
	public void setType(int type) { this.type = type; setContent();}
	private void setContent() 
	{
		if(type == 0)
			content = "저장 완료.";
		else if(type == 1)
			content = "초기화 완료.";
		else if(type == 2)
			content = "백업 완료.";
		else if(type == 3)
			content = "복원 완료.";
		else if(type == 4)
			content = "저장하지 않고 종료";
		else if(type == 5)
			content = "로그 초기화.";
		else if(type == 6)
			content = "지출 추가 완료.";
		else if(type == 7)
			content = "기본 목표액 변경 완료.";
		else if(type == 8)
			content = "목표액 변경 완료.";
		if(!note.equals(""))
			content = content + "  (" + note + ")";
	}

	public boolean isEarlierThan(Log log)
	{
		if(date.isEarlierThan(log.getDate())) // 날짜가 더 빠르면
			return true; // 더 이른 날
		else if(log.getDate().isEarlierThan(date)) // 날짜가 더 늦으면
			return false;
		else // 날짜가 똑같으면
		{
			if(time.isEarlierThan(log.getTime()))	// 시간이 더 이르면
				return true; // 더 이른 날
			else // 시간이 더 늦거나 같으면
				return false; // 이른 날이 아님
		}
	}

	public boolean isSameTime(Log log)
	{
		if(date.isSameDate(log.getDate()) && time.equals(log.getTime()))
			return true;
		return false;
	}
	
	public boolean isSameType(Log log) { return type == log.getType(); }

	public String toString()
	{
		return "[ " + date + " " + time + " ] " + content;
	}
}
