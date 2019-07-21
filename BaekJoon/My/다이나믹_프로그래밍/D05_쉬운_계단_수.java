package 다이나믹_프로그래밍;

import java.util.Scanner;

/************
 * <주소>		: https://www.acmicpc.net/problem/10844
 * **********
 * <해결방안>	: 
 * 
 * (아이디어)
 * 1. 앞의 수열이 어찌 됐든 가장 끝자리에 따라 달라짐
 * 2. F(N)의 끝 자리가 1 ~ 8인 경우에는 F(N - 1) 끝자리에 +1 OR -1 두 가지 경우를 모두 할 수 있음
 * 3. F(N)의 끝 자리가 0인 경우는 -1, 1인 경우는 +1만 가능
 * 
 * ==> F1~8(N) += Fj+1(N - 1) + Fj+1(N - 1)
 *     F0(N) += F1(N - 1)
 *     F9(N) += F8(N - 1)
 * 
 * **********
 * <오답노트>	: 
 * 
 * 1. 처음에는 1차원 배열로 0 ~ 9를 각각 나누지 않고 그냥 풀어보려고 했음
 *    (더 작은 문제로 나누지 않으니 너무 복잡함)
 * 2. dp[0][0]을 0으로 시작하는 수열은 안 된다는 조건을 무시하고 1로 초기화함   
 * 2. 다른 사람의 코드를 참조해 아이디어를 얻음
 * **********
 * @author LENOVO
 *
 */
public class D05_쉬운_계단_수 {

	public static final int DIV = 1000000000;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();
		int dp[][] = new int[N][10];

		dp[0][0] = 0;
		for(int i = 1; i < 10; i++) {
			dp[0][i] = 1;
		}

		for(int i = 1; i < N; i++) {
			for(int j = 0; j < 10; j++) {
				if(j == 0) {
					dp[i][j] += dp[i - 1][j + 1];
				} else if(j == 9) {
					dp[i][j] += dp[i - 1][j - 1];
				} else {
					dp[i][j] += dp[i - 1][j - 1] + dp[i - 1][j + 1];
				}
				dp[i][j] %= DIV;
			}
		}
		
		int res = 0;
		for(int j = 0; j < 10; j++) {
			res += dp[N - 1][j];
			res %= DIV;
		}
		System.out.println(res);		
	}

}
