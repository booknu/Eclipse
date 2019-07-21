package objects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import objects.time.Date;

public class ExpensesManager implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Expenses> expenses_list = new ArrayList<Expenses>();

	/**
	 * 구성자
	 */
	public ExpensesManager()
	{

	}

	/* static 메소드 **************************************************************/
	public static int getSumOfExpenses(ArrayList<Expenses> list)
	{
		int sum = 0;
		for(Expenses exp : list)
			sum += exp.getExpense();
		return sum;
	}

	/* add메소드 *******************************************************************************************/
	/**
	 * 계획 추가
	 * @param plan
	 * @return 제대로 계획이 추가됐는지?
	 */
	public boolean addPlan(Expenses newExpenses)
	{
		expenses_list.add(newExpenses);
		return true;
	}

	/* get메소드 ****************************************************************************************/
	public ArrayList<Expenses> getAllExpenses() { return new ArrayList<Expenses>(expenses_list); } // expenses_list의 복제본을 반환

	/**
	 * 특정 날짜에 해당하는 지출 리스트를 가져온다.
	 * @param date 날짜
	 * @return
	 */
	public ArrayList<Expenses> getCertainDateExpenses(Date date)
	{
		ArrayList<Expenses> certain_exp_list = new ArrayList<Expenses>();
		for(Expenses exp : expenses_list)
		{
			if(exp.getYear() == date.getYear() && exp.getMonth() == date.getMonth()
					&& exp.getDate() == date.getDate())
				certain_exp_list.add(exp);
		}
		return certain_exp_list;
	}
	
	/**
	 * 특정 월에 해당하는 지출 리스트를 가져온다.
	 * @param date 날짜
	 * @return
	 */
	public ArrayList<Expenses> getCertainMonthExpenses(Date date)
	{
		ArrayList<Expenses> certain_exp_list = new ArrayList<Expenses>();
		for(Expenses exp : expenses_list)
		{
			if(exp.getYear() == date.getYear() && exp.getMonth() == date.getMonth())
				certain_exp_list.add(exp);
		}
		return certain_exp_list;
	}
	
	/* public 메소드 ***************************************************************/
	public void removeExpenses(Expenses exp)
	{
		expenses_list.remove(exp);
	}
}
