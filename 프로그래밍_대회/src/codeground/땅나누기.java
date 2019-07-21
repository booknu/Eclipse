package codeground;
import java.util.Scanner;

/* 사용하는 클래스명이 Solution 이어야 하며, 가급적 Solution.java 를 사용할 것을 권장합니다.
   이러한 상황에서도 동일하게 java Solution 명령으로 프로그램을 수행해 볼 수 있습니다. */

class 땅나누기 {
	
	static int N;
	static int numC;
	static boolean xMines[];	// true: I, false: C (0 ~ N - 1)
	static int yMines[];	// 양수: I, 음수: C ==> 숫자는 x좌표 의미 (x좌표 1 ~ N)
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
			xMines = new boolean[N];
			yMines = new int[N];
			numC = 0;
			for(int i = 0; i < N; i++) {
				int x = sc.nextInt();
				int y = sc.nextInt();
				boolean isIron = sc.nextInt() == 1;
				
				xMines[x - 1] = isIron;
				yMines[y - 1] = isIron? x : -x;
				
				if(!isIron)
					numC++;
			}
			
			// 전부 C에서 I를 추가하는 방식
			int horI = 0, horC = numC;
			int max = horI + horC;
			
			for(int i = -1; i < N; i++) {
				if(i != -1) {
					// I일 경우
					if(xMines[i])
						horI++;
					// C일 경우
					else
						horC--;
				}
				
				int verI = horI, verC = horC;
				for(int j = -1; j < N; j++) {
					if(j != -1) {
						if(i != -1) {
							// I면서 오른쪽에 위치 ++
							if(yMines[j] > 0 && yMines[j] > i + 1)
								verI++;
							// I면서 왼쪽에 위치 --
							else if(yMines[j] > 0 && yMines[j] <= i + 1)
								verI--;
							// C면서 왼쪽에 위치 ++
							else if(yMines[j] < 0 && -yMines[j] <= i + 1) {
								verC++;
							}
							// C면서 오른쪽에 위치 --
							else if(yMines[j] < 0 && -yMines[j] > i + 1)
								verC--;
						} else {
							if(yMines[j] > 0)
								verI++;
							else
								verC--;
						}
						
					}
					
					max = Math.max(max, verI + verC);
				}
			}
			
			// 이 부분에서 정답을 출력하십시오.
			System.out.println("Case #" + test_case);
			System.out.println(max);
		}
	}
}