package 다이나믹_프로그래밍;

import java.util.Scanner;

/************
 * <주소>		: https://www.acmicpc.net/problem/11726
 * **********
 * <해결방안>	: 
 * 
 * (전제)
 * 1. 1x2 타일은 절대 엇갈려서 놓을 수 없다
 * 2. 1에 따라 2x1 공간을 채우는 방법 -> 세로 타일 (1가지)
 *              2x2 공간을 채우는 방법 -> 세로 타일 2개, 가로 타일 2개 (2가지)
 * 
 * (전개)
 * N번째까지 채우는 방법은
 * 1. N-1까지 채우고 세로 타일로 마저 채운다.
 * 2. N-2까지 채우고 2x2의 공간을 세로 타일 2개로 채운다.
 * 3. N-2까지 채우고 2x2의 공간을 가로 타일 2개로 채운다.
 *    이 때 2번 방법은 1번에 포함되니 세지 않는다.
 * 
 * ==> F(N) = F(N - 1) + F(N - 2)		(n > 2)
 * 
 * **********
 * <오답노트>	: 
 * 
 * 1. 10007로 나누어야 하는 것을 안 나눔
 * 2. N = 1일때 배열 범위 밖을 나가는 오류가 생김
 * 
 * **********
 * @author LENOVO
 *
 */
public class F01_2xN_타일링 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		
		int n = sc.nextInt();
		int[] dp = new int[n];
		
		dp[0] = 1;
		if(n >= 2)
			dp[1] = 2;
		
		for(int i = 2; i < n; i++) {
			dp[i] = (dp[i - 1] + dp[i - 2]) % 10007;
		}
		
		System.out.println(dp[n - 1]);
	}

}
