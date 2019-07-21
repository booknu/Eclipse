package 다이나믹_프로그래밍;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/************
 * <주소>		: https://www.acmicpc.net/problem/2225
 * **********
 * <해결방안>	: 
 * 
 * (문제를 볼 때부터 감을 못 잡았음.
 *  http://wootool.tistory.com/105 를 참조해서 품)
 * 
 * n을 k개의 숫자 합으로 만들 수 있는 방법
 * = 0 ~ n을 k-1개의 숫자 합으로 만들 수 있는 방법
 * 
 * ==> dp[n][k] = sum( dp[0~n][k-1] )
 * 
 * prev = 0 ~ n 일 때
 * 그 이유는, dp[prev][k-1] 에 (n - prev)를 하나 더 더해주면
 * dp[n][k]가 되기 때문이다.
 * 
 * **********
 * <오답노트>	: 
 * 
 * **********
 * @author LENOVO
 *
 */
public class D10_합분해 {

	static int DIV = 1000000000;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FastScanner sc = new FastScanner();
		int N = sc.nextInt();
		int K = sc.nextInt();
		
		// dp[n][k] = n을 k개의 숫자로 만들 수 있는 방법
		int[][] dp = new int[N + 1][K + 1];
		// 0을 0개로 만드는 방법: 1가지, n을 0개로 만드는 방법: 0가지
		dp[0][0] = 1;
		
		for(int i = 0; i <= N; i++) {
			for(int j = 1; j <= K; j++) {
				for(int prev = 0; prev <= i; prev++) {
					dp[i][j] += dp[prev][j - 1];
					dp[i][j] %= DIV;
				}
			}
		}
		
		System.out.println(dp[N][K]);
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
