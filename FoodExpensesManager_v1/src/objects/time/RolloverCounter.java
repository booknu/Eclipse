package objects.time;

import java.io.Serializable;

/**
 * 1씩 증감할 수 있는 한계가 있는 카운터.
 * 
 * @author Administrator
 *
 */
public class RolloverCounter implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int max;
	private int min;
	private int value;
	
	/***
	 * RolloverCounter 클래스의 오브젝트를 위한 구성자
	 * 카운터의 한계를 정한다.
	 * 주어진 값이 되면 0으로 돌아간다.
	 * @param rollOverLimit 한계값
	 */
	public RolloverCounter(int min, int max)
	{
		this(max, min, 0);
	}
	
	/**
	 * 구성자
	 * @param rollOverLimit 한계값
	 * @param initialValue 시작값
	 */
	public RolloverCounter(int min, int max,  int initialValue)
	{
		this.min = min;
		this.max = max;
		if(!setValue(initialValue))
			value = 0;
	}
	
	/**
	 * 현재 value를 반환한다.
	 * @return value
	 */
	public int getValue()
	{
		return value;
	}
	
	/**
	 * 현재 limit을 반환한다.
	 * @return limit
	 */
	public int getLimit()
	{
		return max;
	}
	
	/**
	 * 카운터 value 를 2자리 문자열 형태로 반환한다.
	 * (만약 value가 10 보다 작으면, ?????)
	 * @return "XX" 형태로 현재 값을 반환
	 */
	public String getTwoDigitString()
	{
		if(value < 10)
			return "0" + value;
		return "" + value;
	}
	
	/**
	 * 카운터의 value를 새로운 특정 값으로 설정한다.
	 * 만약 새로운 value가 min 미만 max 초과일 경우 false를 반환한다.
	 * @param replacementValue
	 * @return true = 성공, false = 실패
	 */
	public boolean setValue(int replacementValue)
	{
		if(!(replacementValue < min || replacementValue > max))
		{
			value = replacementValue;
			return true;
		}
		else
			return false;
			
	}
	
	/**
	 * 카운터 value 를 1 증가시킨다.
	 * 만약 max를 넘으면 min 으로 돌아간다.
	 * @return 카운터가 limit을 넘어서 min이 됐는지?
	 */
	public boolean increment()
	{
		if(value == max)
		{
			value = min;
			return true;
		}
		
		
		else
			value++;	
		return false;
	}
	
	/**
	 * 카운터 value를 1 감소시킨다.
	 * 만약 min 이라면 max 로 돌아간다.
	 * @return 카운터가 min 에서 max로 됐는지?
	 */
	public boolean decrement()
	{
		if(value == min)
		{
			value = max;
			return true;
		}
		else
			value--;
		return false;
	}

}
