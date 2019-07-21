package 다이나믹_프로그래밍;
import java.util.Arrays;
import java.util.Scanner;

public class _2차원_배열_부분합 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner cs = new Scanner(System.in);

		int N = cs.nextInt(), M = cs.nextInt();
		int[][] arr = new int[N][M];
		for(int i = 0; i < N; i++)
			for(int j = 0; j < M; j++)
				arr[i][j] = cs.nextInt();
		int K = cs.nextInt();
		int[] i, j, x, y;
		i = new int[K];
		j = new int[K];
		x = new int[K];
		y = new int[K];

		for(int k = 0; k < K; k++)
		{
			i[k] = cs.nextInt();
			j[k] = cs.nextInt();
			x[k] = cs.nextInt();
			y[k] = cs.nextInt();
		}

		int[][] psum = partialSum(arr);
		
		for(int k = 0; k < K; k++)
			System.out.println(getSum(psum, i[k], j[k], x[k], y[k]));
	}

	/**
	 * i, j까지의 배열의 부분 합을 구해 반환
	 * @param arr 배열
	 * @return 부분합 배열
	 */
	public static int[][] partialSum(int arr[][])
	{
		int[][] ret = new int[arr.length + 1][arr[0].length + 1];

		for(int a[] : ret)
			Arrays.fill(a, 0);
		
		for(int i = 1; i <= arr.length; i++)
		{
			int sum = 0;

			if(i != 0)
				for(int j = 1; j <= arr[0].length; j++)
				{
					sum += arr[i - 1][j - 1];
					ret[i][j] = ret[i - 1][j] + sum;
				}
			else
				for(int j = 1; j <= arr[0].length; j++)
				{
					sum += arr[0][j - 1];
					ret[i][j] = sum;
				}
					
		}
		
		return ret;
	}
	
	public static int getSum(int psum[][], int i, int j, int x, int y)
	{
		return psum[x][y] - psum[x][j - 1] - psum[i - 1][y] + psum[i - 1][j - 1];
	}
}
