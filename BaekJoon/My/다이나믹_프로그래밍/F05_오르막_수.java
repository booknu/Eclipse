package 다이나믹_프로그래밍;
import java.util.Scanner;

/************
 * <주소>		: https://www.acmicpc.net/problem/11057
 * **********
 * <해결방안>	: 
 * 
 * - "D05_쉬운_계단_수" 와 비슷한 경우이다.
 * 
 * - n-1자리까지의 경우의 수가 구해져 있다고 가정하면
 *   F(length)(끝자리수) 일 때
 *   끝자리수가 0일 때는 그 이전까지 구한 수들 중 끝자리수가 0인 경우이고,
 *   끝자리수가 1일 때는 그 이전까지 구한 수들 중 끝자리수가 0, 1인 경우이고,
 *   끝자리수가 2일 때는 그 이전까지 구한 수들 중 끝자리수가 0, 1, 2인 경우이고,
 *   ...
 *   
 *   따라서
 *   F(n)(k) = sum( F(n - 1)(0 ~ k) ) 이다.
 * 
 * **********
 * <오답노트>	: 
 * 
 * - 마지막에 10007로 나눠야 하는 것을 안 나눔
 * 
 * **********
 * @author LENOVO
 *
 */
public class F05_오르막_수 {

	static int DIV = 10007;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Scanner sc = new Scanner(System.in);
		
		int N = sc.nextInt();
		int dp[][] = new int[N][10];
		
		for(int i = 0; i < 10; i++) {
			dp[0][i] = 1;
		}
		
		for(int i = 1; i < N; i++) {
			for(int j = 0; j < 10; j++) {
				for(int k = 0; k <= j; k++) {
					dp[i][j] += dp[i - 1][k];
					dp[i][j] %= DIV;
				}
			}
		}
		
		int sum = 0;
		for(int i = 0; i < 10; i++) {
			sum += dp[N - 1][i];
			sum %= DIV;
		}
		System.out.println(sum);
	}

}
