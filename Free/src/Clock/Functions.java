package Clock;


public class Functions {

	/**
	 * 입력받은 str 안에 들어있는 숫자들을 배열 형태로 반환한다. (문자열의 마지막은 숫자가 아니어야한다)
	 * @param str 문자열
	 * @param numbersInString str 안의 숫자 개수(만들 배열의 크기)
	 * @return 문자열 안의 숫자들이 저장된 배열
	 */
	public int[] returnNumbers (String str)
	{
		boolean string = true;
		int [] numbers = new int [10000];
		int index=0;	// 다음에 저장할 numbers 배열의 방 번호
		int from = 0;
		int to;
		
		for(int i=0; i<numbers.length; i++)
			numbers[i] = -1;
		
		/* 루프를 통해 str 안의 숫자를 times[]에 저장 */
		for(int i=0; i<str.length(); i++)
		{
			char a = str.charAt(i);
			
			// 전이 문자일 때
			if(string){
				if(Character.isDigit(a)) {
					string = false;
					from = i;
				}
			}
			
			// 전이 숫자일 때
			if(!string){
				// 마지막 문자가 숫자일 때
				if(i == str.length())
				{
					numbers[index] = Integer.parseInt(str.substring(from));
				}
				
				else if(!Character.isDigit(a)) {
					if(from == -1);
					
					else
					{
						to = i;
						numbers[index] = Integer.parseInt(str.substring(from, to));
						index++;
						
						string = true;
					}
				}
			}
		}
		/* 루프 끝 */
		
		int[] returnNumbers = new int[index];
		
		for(int i=0; i<index; i++)
			returnNumbers[i] = numbers[i];
		
		return returnNumbers;
	}
	
	/**
	 * n의 자릿수를 구한다 (n >= 0)
	 * @param n 0 이상 정수
	 * @return n의 자릿수
	 */
	public int numberOfDigit(int n)
	{
		int count = 1;
		while(n >= 10)
		{
			n /= 10;
			count++;
		}
		
		return count;
	}

}