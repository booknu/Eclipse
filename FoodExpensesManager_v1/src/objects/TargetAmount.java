package objects;

import java.io.Serializable;

import objects.time.Date;

public class TargetAmount implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static int default_targetAmount = 200000;
	
	private Date date;
	private int target_amount;
	
	public TargetAmount(Date date, int target_amount)
	{
		this.date = date; this.target_amount = target_amount;
		
	}
	
	/* get 메소드 *********************************************************************/
	public int getYear() { return date.getYear(); }
	public int getMonth() { return date.getMonth(); }
	public Date getDateForm() { return date.getCopyOfDate(); }
	public int getTargetAmount() { return target_amount; }
	
}
