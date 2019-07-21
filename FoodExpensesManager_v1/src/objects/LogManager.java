package objects;

import java.util.ArrayList;

import functions.CalendarFunc;
import functions.CommonSources;
import objects.time.Time;

public class LogManager {

	public static ArrayList<Log> log_list;
	public static int maxSize = 200;

	/**
	 * log_list에 log를 추가한다.
	 * 만약 리스트에 넣으려는 log와 중복되는 시간대의 log가 있다면 넣지 않고 false 반환
	 * 리스트에 넣는다면 시간별로 정렬해서 넣는다.
	 * @param log
	 * @return
	 */
	public static void addLog(int log_type, String note)
	{
		Log log =new Log(CalendarFunc.getCurrentDateForm(), Time.getCurrentTimeForm(), log_type, note);
		log_list.add(log);
		maintainSize();
		CommonSources.mainFrame.logRefresh(log.toString());
	}

	public static void addLog(Log log)
	{
		log_list.add(log);
		maintainSize();
	}

	public static String displayAll()
	{
		String s = "";
		for(Log log : log_list)
			s += log + "\n";
		return s;
	}

	/**
	 * 이진 탐색 기법으로 target과 시간이 같은 Log의 index를 찾는다.
	 * 만약 중복되는 시간이 없다면 -1을 반환한다.
	 * @param target 찾으려는 Log
	 * @return 중복되는 시간의 Log의 index
	 * 					없으면 -1
	 */
	@SuppressWarnings("unused")
	private static int isOverlapped(Log target)
	{
		int first = 0;
		int last = log_list.size() - 1;
		int mid;

		while(first <= last)
		{
			mid = (first + last) / 2;

			Log midLog = log_list.get(mid);
			if(target.isSameTime(midLog) && target.isSameType(midLog))	// 중복 됨
				return mid;
			else
			{
				if(target.isEarlierThan(midLog))
					last = mid - 1;
				else
					first = mid + 1;
			}
		}
		return -1;

	}

	/**
	 * 일정 숫자 이상만큼 로그가 많아지면 옛날 로그를 지워가며
	 * 사이즈 유지
	 */
	private static void maintainSize()
	{
		if(log_list.size() > 200)
			log_list.remove(0);
	}
}
