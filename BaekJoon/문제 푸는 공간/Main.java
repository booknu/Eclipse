import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/************
 * <주소>		: https://www.acmicpc.net/problem/2163
 * **********
 * <해결방안>	: 
 * 
 * **********
 * <오답노트>	: 
 * 
 * **********
 * @author LENOVO
 *
 */
public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FastScanner sc = new FastScanner();
		int N = sc.nextInt();
		int M = sc.nextInt();
	
		int dp[][] = new int[N+1][M+1];
		dp[1][2] = 1;
		dp[2][1] = 1;
		for(int i = 2; i <= N; i++) {
			for(int j = 2; j <= N; j++) {
				int halfVer = i / 2;
				int halfHor = j / 2;
				dp[i][j] = Math.min(dp[halfVer][j] + dp[i - halfVer][j]
						, dp[i][halfHor] + dp[i][j - halfHor]);
				dp[i][j]++;
			}
		}
		for(int i = 0; i <= N; i++) {
			for(int j = 0; j <= M; j++) {
				System.out.print(dp[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println(dp[N][M]);
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
