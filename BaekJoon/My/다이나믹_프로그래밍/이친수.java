package 다이나믹_프로그래밍;
import java.util.Arrays;
import java.util.Scanner;

public class 이친수 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner cs = new Scanner(System.in);
		
		int N = cs.nextInt();
		long DP[][] = new long[N + 1][2];
		
		DP[1][0] = 0;
		DP[1][1] = 1;
		
		for(int i = 2; i <= N; i++)
		{
			DP[i][0] = DP[i - 1][0] + DP[i - 1][1];
			DP[i][1] = DP[i - 1][0];
		}
		
		System.out.println(DP[N][0] + DP[N][1]);
	}
}
