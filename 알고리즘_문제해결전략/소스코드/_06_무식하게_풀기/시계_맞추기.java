package _06_무식하게_풀기;

/************
 * <주소>		: 6.8 시계 맞추기
 * **********
 * <해결방안>	: 
 * 
 * (조건)
 * 1. 한 버튼이 4번 이상 눌릴리 없다.
 * 2. 버튼을 누르는 순서는 상관이 없다.
 * ==> 총 가능한 경우의 수: 4^10
 * 
 * (전개)
 * 재귀를 활용해 가능한 모든 경우를 탐색하여 최소값을 찾는다.
 * 
 * **********
 * <오답노트>	: 
 * 
 * **********
 * @author LENOVO
 *
 */
public class 시계_맞추기 {

	static final int INF = 9999;
	static final int SWITCHES = 10;
	static final int CLOCKS = 16;
	static char linked[][] = {
			"xxx.............".toCharArray(),
			"...x...x.x.x....".toCharArray(),
			"....x.....x...xx".toCharArray(),
			"x...xxxx........".toCharArray(),
			"......xxx.x.x...".toCharArray(),
			"x.x...........xx".toCharArray(),
			"...x..........xx".toCharArray(),
			"....xx.x......xx".toCharArray(),
			".xxxxx..........".toCharArray(),
			"...xxx...x...x..".toCharArray()
	};
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FastScanner sc = new FastScanner();
		int C = sc.nextInt();
		for(int testCase = 0; testCase < C; testCase++) {
			int[] clocks = new int[CLOCKS];
			for(int i = 0; i < CLOCKS; i++) {
				clocks[i] = sc.nextInt();
			}
			
			int result = minSwitch(clocks, 0);
			// 방법이 없으면 -1을 출력
			if(result >= INF) {
				result = -1;
			}
			System.out.println(result);
		}
	}

	/**
	 * 모든 시계가 12시를 가르키는지 확인
	 * @param clocks 시계
	 * @return
	 */
	public static boolean areAligned(int[] clocks) {
		for(int i = 0; i < CLOCKS; i++) {
			if(clocks[i] != 12) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 해당 스위치를 누른다.
	 * @param clocks 시계
	 * @param swtch 스위치 번호
	 */
	public static void push(int[] clocks, int swtch) {
		for(int i = 0; i < CLOCKS; i++) {
			if(linked[swtch][i] == 'x') {
				clocks[i] += 3;
				if(clocks[i] == 15) clocks[i] = 3;
			}
		}
	}
	
	/**
	 * 재귀.
	 * 최소로 스위치를 누르는 방법을 찾아냄
	 * @param clocks
	 * @param swtch
	 * @return
	 */
	public static int minSwitch(int[] clocks, int swtch) {
		if(swtch >= SWITCHES) {
			return areAligned(clocks)? 0 : INF;
		}
		
		int ret = INF;
		for(int i = 0; i < 4; i++) {
			ret = Math.min(ret, minSwitch(clocks, swtch + 1) + i);
			push(clocks, swtch);
		}
		return ret;
	}
}
