package 다이나믹_프로그래밍_;
import java.util.Scanner;

/************
 * <주소>		: https://www.acmicpc.net/problem/2133
 * **********
 * <해결방안>	: 
 * 
 * 이해를 못하겠음
 * 
 * **********
 * <오답노트>	: 
 * 
 * **********
 * @author LENOVO
 *
 */
public class D10_타일_채우기 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();

		// 홀수이면 0가지
		if((N & 1) > 0) {
			System.out.println(0);
			System.exit(0);
		}

		int [] dp = new int [N + 1];
		
		dp[2] = 3;
		dp[0] = 1;
		
		for(int i = 4; i <= N; i ++) {
			dp[i] = dp[i - 2] * 4 - dp[i - 4];
		}
		
		System.out.println(dp[N]);
	}

}
