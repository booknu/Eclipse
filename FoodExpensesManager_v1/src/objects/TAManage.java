package objects;

import java.util.ArrayList;
import java.util.Iterator;

import functions.CommonSources;
import objects.time.Date;

public class TAManage {

	private static ArrayList<TargetAmount> ta_list = CommonSources.targetAmount_list;

	/**
	 * 날짜에 해당하는 target amount를 가져옴
	 * 없을 경우 default에 해당하는 target amount를 가져옴
	 * @param year
	 * @param month 1 ~ 12
	 * @return
	 */
	public static TargetAmount getTargetAmount(Date date)
	{
		for(TargetAmount ta : ta_list)
			if(ta.getYear() == date.getYear() && ta.getMonth() == date.getMonth())
				return ta;

		return new TargetAmount(date.getCopyOfDate(), CommonSources.default_targetAmount);
	}
	
	/**
	 * 날짜에 해당하는 target amount 값을 가져옴
	 * @param date
	 * @return
	 */
	public static int getTargetAmountValue(Date date) { return getTargetAmount(date).getTargetAmount(); }

	public static void modTargetAmount(Date date, int amount)
	{
		Iterator<TargetAmount> it = ta_list.iterator();
		while(it.hasNext())
		{
			TargetAmount ta = it.next();
			if(ta.getYear() == date.getYear() && ta.getMonth() == date.getMonth())
				it.remove();
		}

		if(amount != CommonSources.default_targetAmount)
			ta_list.add(new TargetAmount(date.getCopyOfDate(), amount));
	}
}
