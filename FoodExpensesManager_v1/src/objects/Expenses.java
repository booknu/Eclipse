package objects;

import java.io.Serializable;

import functions.NormalFunc;

public class Expenses implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int year;
	private int month; // 1 ~ 12
	private int date;
	private long expense;
	private String remarks; // 비고

	public Expenses(int year, int month, int date, long expense, String remarks)
	{
		this.year = year;
		this.month = month;
		this.date = date;
		this.expense = expense;
		this.remarks = remarks;
	}

	/* is 메소드 **********************************************************************/
	/**
	 * 다른 계획과 중복되는 시간대인지 체크
	 * @param anotherExp
	 * @return
	 */
	public boolean isOverlapped (Expenses anotherExp)
	{
		if(isSameDate(anotherExp))
		{
			return true;
		}
		return false;
	}
	public boolean isSameDate (Expenses anotherExp) { return (year == anotherExp.getYear() && month == anotherExp.getMonth() && date == anotherExp.getDate()); }

	/* set 메소드 *****************************************************************************/
	public void setYear(int year) { this.year = year; }
	public void setMonth(int month) { this.month = month; }
	public void setDate(int date) { this.date = date; }
	public void setExpense(long spended) { this.expense = spended; }
	public void setRemarks(String remarks) { this.remarks = remarks; }

	/* get 메소드 ************************************************************************/
	public int getYear() { return year; }
	public int getMonth() { return month; }
	public int getDate() { return date; }
	public long getExpense() { return expense; }
	public String getRemarks() { return remarks; }
	public String getTooltipText()
	{
		return remarks + ": " + NormalFunc.getCompartedInt(expense) + " 원";
	}

	public String toString()
	{
		return "[ date = " + year + "년 " + month + "월 " + date + "일 " + "\n"
				+ "  expense = " + expense + "\n" + "  remarks = " + remarks + " ]";
	}
}
