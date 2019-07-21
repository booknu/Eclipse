package _16_비트마스크;

/**
 * A, B, C, D 라는 물질이 있음
 * ex) {A, B} {C, D} 가 함께 있으면 폭발한다.
 * 
 * 극대 안정 집합: 물질을 하나라도 추가하면 폭발이 일어나는 집합들
 * ex) {A, C} {A, D} {B, C} {B, D}
 * 
 * 물질 조합에 대한 집합은
 * int set = 0000 0000 0000 0000 0000 0000 0000 DCBA (2)  로 표현한다.
 * @author Administrator
 *
 */
public class 극대_안정_집합 {

	public static int n = 4; // 총 원소의 개수
	public static int explodes[] = {2, 1, 8, 4};	// i번째 원소와 같이 두면
	// "폭발하는 물질 조합"에 대한 집합


	public static void main(String[] args) {
		// TODO Auto-generated method stub

		System.out.println("극대 안정 집합 개수: " + countStableSet());
	}

	/**
	 * set의 원소 하나 하나를 검사하며 안전한지 확인.
	 * 
	 * (set & (1 << i)) != 0: i번째 원소가 조사하려는 집합에 있으면
	 * (set & explodes[i]) != 0: 해당 set에 그 원소와 같이 넣으면 폭발하는게 있는지 확인
	 * @param set 조사할 집합 (0000 .... DCBA (2) 형태)
	 * @return 안전한지?
	 */
	public static boolean isStable(int set)
	{
		for(int i = 0; i < n; i++)
			if((set & (1 << i)) != 0 && (set & explodes[i]) != 0)
				return false;
		return true;
	}

	public static int countStableSet()
	{
		int count = 0;

		// for(): 모든 집합을 만들어 조사.
		// set < (1 << n) 은 1 0000 (2) 미만의 모든 수를 조사 (n = 4일 때)
		for(int set = 1; set < (1 << n); set++)
		{
			if(!isStable(set))	// 이미 터지는 조합이면 세지 않음
				continue;
			
			// (set & (1 << add)) == 0: add가 set에 없는 원소일 경우
			// (explodes[add] & set) == 0: add를 추가할 경우 안정적이면
			// 이 집합은 극대 안정 집합이 아니다.
			boolean canExtend = false;
			for(int add = 0; add < n; add++)
				if((set & (1 << add)) == 0 && (explodes[add] & set) == 0)
				{
					canExtend = true;
					break;
				}
			if(!canExtend)
			{
				count++;
				System.out.println(setToString(set));
			}
		}
		return count;
	}

	/**
	 * set에 있는 모든 원소를 String 형태로 반환한다.
	 * ==> p50에 있는 방법처럼 코드와 변수를 분리하면 좋을듯
	 * @param set
	 * @return
	 */
	public static String setToString(int set)
	{
		StringBuilder strb = new StringBuilder("");
		for(int i = 0; i < n; i++)
			if((set & (1 << i)) != 0)
			{
				switch(i)
				{
				case 0: strb.append("A, "); break; 
				case 1: strb.append("B, "); break; 
				case 2: strb.append("C, "); break; 
				case 3: strb.append("D, "); break; 
				}
			}
		if(!strb.equals(""))
			strb.delete(strb.length() - 2, strb.length() - 1);

		return strb.toString();
	}
}
