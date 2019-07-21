package 다이나믹_프로그래밍;
import java.util.Scanner;

/************
 * <주소>		: https://www.acmicpc.net/problem/1904
 * **********
 * <해결방안>	: 
 * 
 * - N 길이의 이진수를 만들려면 N-1에 1을 붙이거나, N-2에 00을 붙여야 한다.
 * 
 * ==> F(N) = F(N-1) + F(N-2)
 *            1붙임   00붙임
 * **********
 * <오답노트>	: 
 * 
 * - 마지막에 15746으로 나누지 않음
 * 
 * **********
 * @author LENOVO
 *
 */
public class F01_01타일 {

	static int DIV = 15746;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		
		int N = sc.nextInt();
		int dp[] = new int[N];
		
		dp[0] = 1;
		if(N > 1) {
			dp[1] = 2;
		}
		
		
		for(int i = 2; i < N; i++) {
			dp[i] = (dp[i - 1] + dp[i - 2]) % DIV;
		}
		
		System.out.println(dp[N - 1]);
	}

}
