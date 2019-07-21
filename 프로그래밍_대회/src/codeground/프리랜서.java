package codeground;
import java.util.Scanner;

/* 사용하는 클래스명이 Solution 이어야 하며, 가급적 Solution.java 를 사용할 것을 권장합니다.
   이러한 상황에서도 동일하게 java Solution 명령으로 프로그램을 수행해 볼 수 있습니다. */

class 프리랜서 {

	static final int MIN = -99999999;

	static int N;
	static int P[], Q[];
	static int memo[][];
	public static void main(String args[]) throws Exception	{
		/* 아래 메소드 호출은 앞으로 표준입력(키보드) 대신 sample_input.txt 파일로 부터 읽어오겠다는 의미의 코드입니다.
		   만약 본인의 PC 에서 테스트 할 때는, 입력값을 sample_input.txt에 저장한 후 이 코드를 첫 부분에 사용하면,
		   표준입력 대신 sample_input.txt 파일로 부터 입력값을 읽어 올 수 있습니다.
		   또한, 본인 PC에서 아래 메소드를 사용하지 않고 표준입력을 사용하여 테스트하셔도 무방합니다.
		   단, Codeground 시스템에서 "제출하기" 할 때에는 반드시 이 메소드를 지우거나 주석(//) 처리 하셔야 합니다. */
		//Scanner sc = new Scanner(new FileInputStream("sample_input.txt"));

		Scanner sc = new Scanner(System.in);

		int T;
		int test_case;

		T = sc.nextInt();        
		for(test_case = 1; test_case <= T; test_case++) {
			// 이 부분에서 알고리즘 프로그램을 작성하십시오. 기본 제공된 코드를 수정 또는 삭제하고 본인이 코드를 사용하셔도 됩니다.
			N = sc.nextInt();
			P = new int[N];
			Q = new int[N];
			memo = new int[N][2];
			
			for(int i = 0; i < N; i++)
				P[i] = sc.nextInt();
			for(int i = 0; i < N; i++)
				Q[i] = sc.nextInt();

			
			int res[] = new int[N + 1];
			res[0] = 0;
			res[1] = Math.max(P[0], Q[0]);
			for(int i = 2; i <= N; i++) {
				res[i] = Math.max(res[i - 1] + P[i - 1], res[i - 2] + Q[i - 1]);
			}
			

			// 이 부분에서 정답을 출력하십시오.
			System.out.println("Case #" + test_case);
			System.out.println(res[N]);
		}
	}

//	public static int countIncome(int week, boolean isP) {
//		try{
//			if(week == N - 1) {
//				return isP? P[week] : Q[week];
//			}
//			else if(week >= N) {
//				return MIN;
//			}
//			
//			int memoIsP = isP? 0 : 1;
//			if(memo[week][memoIsP] != 0)
//				return memo[week][memoIsP];
//
//			// 첫 주인 경우
//			memo[week][memoIsP] = Math.max(countIncome(week + 1, false) + P[week], 
//					countIncome(week + 2, true) + Q[week]);
//			return memo[week][memoIsP];
//		} catch(Exception e) {
//			return 0;
//		}
//	}
}