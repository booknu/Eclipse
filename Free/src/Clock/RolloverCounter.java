package Clock;

public class RolloverCounter {

	private int limit;
	private int value;
	
	/***
	 * RolloverCounter 클래스의 오브젝트를 위한 구성자
	 * 카운터의 한계를 정한다.
	 * 주어진 값이 되면 0으로 돌아간다.
	 * @param rollOverLimit 한계값
	 */
	public RolloverCounter(int rollOverLimit)
	{
		limit = rollOverLimit;
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
		return limit;
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
	 * 만약 새로운 value가 0 미만 limit 초과일 경우 아무것도 하지 않는다
	 * @param replacementValue
	 */
	public void setValue(int replacementValue)
	{
		if(!(replacementValue < 0 || replacementValue > limit))
			value = replacementValue;
	}
	
	/**
	 * 카운터 value 를 1 증가시킨다.
	 * 만약 limit에 다다르면 0 으로 돌아간다.
	 */
	public void increment()
	{
		if(value < limit - 1)
			value++;
		
		else
			value = 0;	
	}

}
