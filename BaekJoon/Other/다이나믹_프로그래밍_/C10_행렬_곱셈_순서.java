package 다이나믹_프로그래밍_;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/************
 * <주소>		: https://www.acmicpc.net/problem/11049
 * **********
 * <해결방안>	: 
 * 
 * dp[i][j] = i~j 행렬까지의 곱 중 가장 적은 연산을 할 경우 연산 수
 * dp[i][j] = dp[i][k] + dp[k+1][j] + 두 행렬 연산수
 * 
 * **********
 * <오답노트>	: 
 * 
 * **********
 * @author LENOVO
 *
 */
public class C10_행렬_곱셈_순서 {

	static int INF = 2147483647;
	static long INF_LONG = 9223372036854775807L;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FastScanner sc = new FastScanner();
		int N = sc.nextInt();
		int mat[][] = new int[555][2];
		for(int i = 1; i <= N; i++) {
			for(int j = 0; j < 2; j++) {
				mat[i][j] = sc.nextInt();
			}
		}
		
		long[][] dp = new long[N + 1][N + 1];
		for(int i = N; i >= 1; i--) {
			for(int j = i + 1; j <= N; j++) {
				dp[i][j] = INF_LONG;
				for(int k = i; k < j; k++) {
					dp[i][j] = Math.min(dp[i][j], 
							dp[i][k] + dp[k + 1][j] + (mat[i][0] * mat[k][1] * mat[j][1]));
				}
			}
		}
//		printArr(dp, N + 1);
		System.out.println(dp[1][N]);
	}
	
	public static void printArr(long[][] arr, int n) {
		for(int i = 1; i < n; i++) {
			for(int j = 1; j < n; j++) {
				System.out.print("(" + i + ", " + j + ": " + arr[i][j] + ") ");
			}
			System.out.println();
			
		}
		System.out.println();
	}
	
	static class FastScanner {
		BufferedReader br;
		StringTokenizer st;

		public FastScanner(String s) {
			try {
				br = new BufferedReader(new FileReader(s));
			} catch (FileNotFoundException e) {
				throw new RuntimeException(e);
			}
		}

		public FastScanner() {
			br = new BufferedReader(new InputStreamReader(System.in));
		}

		String nextToken() {
			while (st == null || !st.hasMoreElements()) {
				try {
					st = new StringTokenizer(br.readLine());
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
			return st.nextToken();
		}

		int nextInt() {
			return Integer.parseInt(nextToken());
		}

		long nextLong() {
			return Long.parseLong(nextToken());
		}

		double nextDouble() {
			return Double.parseDouble(nextToken());
		}

		String nextLine() {
			String str = "";
			try {
				str = br.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return str;
		}
	}
}
