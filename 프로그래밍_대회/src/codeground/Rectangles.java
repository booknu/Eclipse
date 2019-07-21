package codeground;
import java.util.ArrayList;
import java.util.Scanner;

/* 사용하는 클래스명이 Solution 이어야 하며, 가급적 Solution.java 를 사용할 것을 권장합니다.
   이러한 상황에서도 동일하게 java Solution 명령으로 프로그램을 수행해 볼 수 있습니다. */

class Rectangles {
	
	static final int MIN = -99999;
	static int N, M, K;
	static Rectangle rects[];
	static ArrayList<Integer>[] contains;
	static int memo[]; // rects[i]에 하위 포함되어 연결된 개수의 최대
	
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
			M = sc.nextInt();
			K = sc.nextInt();
			rects = new Rectangle[K];
			
			contains = new ArrayList[K];
			memo = new int[K];
			
			for(int i = 0; i < K; i++) {
				// 현재 사각형 정보 입력
				rects[i] = new Rectangle(sc.nextInt(),sc.nextInt(),sc.nextInt(),sc.nextInt());
				contains[i] = new ArrayList<Integer>();
			}
			
			// 포함 관계 형성
			for(int i = 0; i < K; i++) {
				for(int j = i + 1; j < K; j++) {
						if(rects[i].contains(rects[j]))
							contains[i].add(j);
						if(rects[j].contains(rects[i]))
							contains[j].add(i);

				}
			}
        
			int result = MIN;
			for(int i = 0; i < K; i++) {
				result = Math.max(result, countLinked(i));
			}
			

			// 이 부분에서 정답을 출력하십시오.
			System.out.println("Case #" + test_case);
			System.out.println(result);
		}
	}
	
	static int countLinked(int rect) {
		if(memo[rect] != 0)
			return memo[rect];
		if(contains[rect].isEmpty())
		{
			memo[rect] = 1;
			return 1;
		}
			
		
		int max = MIN;
		for(int next : contains[rect]) {
			max = Math.max(max, countLinked(next) + 1);
		}
		memo[rect] = max;
		return max;
	}
	
	static class Rectangle {
		
		public int bx, by, tx, ty;
		
		public Rectangle(int bx, int by, int tx, int ty) {
			this.bx = bx;
			this.by = by;
			this.tx = tx;
			this.ty = ty;
		}
		
		public boolean contains(Rectangle r) {
			return bx <= r.bx && r.tx <= tx && by <= r.by && r.ty <= ty;
		}
	}
}