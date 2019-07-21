package _01_문제해결과_프로그래밍대회;

import java.util.Scanner;

/**
 * --------해결 방안--------
 * N이 1000이하의 수이므로
 * 그냥 가능한 모든 조합(평균)을 해서
 * 최소값을 찾음
 * 
 * @author Administrator
 *
 */

/**
 * --------오답 노트--------
 * L이상의 개수가 될 때만 경우에 넣었어야 했는데
 * 그것을 처리하지 않아 실수를 함
 * 
 * @author Administrator
 *
 */
public class 록_페스티벌 {

	static int N, L;
	static int cost[];
	
	static double min;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		int T = sc.nextInt();
		for(int testCase = 1; testCase <= T; testCase++){
			N = sc.nextInt();
			L = sc.nextInt();
			cost = new int[N];
			for(int i = 0; i < N; i++)
				cost[i] = sc.nextInt();
			
			// i: 시작, j: 끝
			for(int i = 0; i < N; i++)
			{
				int sum = 0, n = 1;
				double result = 0;
				double tempMin = Integer.MAX_VALUE;
				
				for(int j = i; j < N; j++)
				{
					sum += cost[j];
					if(n >= L)
					{
						result = (double)sum / n;
						// 초기 상태 지정
						if(n == L)
							tempMin = result;
						if(result < tempMin)
							tempMin = result;
					}
					n++;
				}
				// 초기 상태 지정
				if(i == 0)
					min = tempMin;
				if(tempMin < min)
					min = tempMin;
			}
			System.out.println(min);
		}
	}

}
