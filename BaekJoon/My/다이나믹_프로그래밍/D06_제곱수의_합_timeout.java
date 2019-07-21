package 다이나믹_프로그래밍;
import java.util.Arrays;
import java.util.Scanner;

/************
 * <주소>		: https://www.acmicpc.net/source/4106136
 * **********
 * <해결방안>	: 
 * 
 * **********
 * <오답노트>	: 
 * 
 * - O(N^2) 시간이 걸리는 시간초과 버전
 * 
 * **********
 * @author LENOVO
 *
 */
public class D06_제곱수의_합_timeout {

	static int INF = 9999999;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		
		int N = sc.nextInt();
		
		
//		int pow[] = new int[(int)Math.sqrt(N) + 1];
//		for(int i = 1; i < pow.length; i++) {
//			pow[i] = i * i;
//		}
		
		int dp[] = new int[N + 1];
		Arrays.fill(dp, INF);
		dp[1] = 1;
		for(int i = 2; i <= N; i++) {
			double sqr = Math.sqrt(i);
			if(sqr - (int)sqr == 0) {
				dp[i] = 1;
			}
			for(int j = i - 1; j >= 1; j--) {
				dp[i] = Math.min(dp[i], dp[j] + dp[i - j]);
			}
		}
		System.out.println(dp[N]);
	}

}
