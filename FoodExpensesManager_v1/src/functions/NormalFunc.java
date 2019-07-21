package functions;

public class NormalFunc {

	/**
	 * 정수를 , 로 구분된 값으로 변환한다.
	 * 예)
	 * 10000 => 10,000
	 * 1000000 => 1,000,000
	 * @param value 바꿀 값
	 * @return 바꿔진 값
	 */
	public static String getCompartedInt(long value)
	{
		if(value == 0)
			return "0";
		String converted = "";

		if(value >= 0)
		{
			long temp = value;
			int i = 0;
			while(temp != 0)
			{
				if(i == 3)
				{
					converted = "," + converted;
					i = 0;
				}
				converted = temp % 10 + converted;
				temp /= 10;
				i++;
			}
			return converted;
		}

		else
		{
			value = -value;
			long temp = value;
			int i = 0;
			while(temp != 0)
			{
				if(i == 3)
				{
					converted = "," + converted;
					i = 0;
				}
				converted = temp % 10 + converted;
				temp /= 10;
				i++;
			}
			return "-" + converted;
		}


	}

	public static boolean isStringInt(String s) {
		try {
			Integer.parseInt(s);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
}
