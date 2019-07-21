package 다이나믹_프로그래밍;
import java.util.Scanner;

public class 숫자삼각형 {

	public static void main(String args[])
	{
		Scanner cs = new Scanner(System.in);

		int N = cs.nextInt();
		int[][] triangle = new int[N][];
		for(int i = 0; i < N; i++)
		{
			triangle[i] = new int[i + 1];
			for(int j = 0; j <= i; j++)
				triangle[i][j] = cs.nextInt();
		}
		
		int[][] DP = new int[N][];
		DP[0] = new int[1];
		DP[0][0] = triangle[0][0];

		for(int i = 1; i < N; i++)
		{
			DP[i] = new int[i + 1];
			
			DP[i][0] = DP[i - 1][0] + triangle[i][0];
			for(int j = 1; j < i; j++)
				DP[i][j] = Math.max(DP[i - 1][j - 1], DP[i - 1][j]) + triangle[i][j];
			DP[i][i] = DP[i - 1][i - 1] + triangle[i][i];
		}


		int max = 0;
		for(int i = 0; i < N; i++)
			max = Math.max(max, DP[N - 1][i]);

		System.out.println(max);
	}
}
