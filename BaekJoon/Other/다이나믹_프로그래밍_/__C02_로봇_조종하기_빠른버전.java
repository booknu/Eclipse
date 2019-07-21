package 다이나믹_프로그래밍_;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/************
 * <주소>		: https://www.acmicpc.net/problem/2169
 * **********
 * <해결방안>	: 
 * 
 * ???
 * 
 * **********
 * <오답노트>	: 
 * 
 * **********
 * @author LENOVO
 *
 */
public class __C02_로봇_조종하기_빠른버전 {

	static int MAX_N = 1000;
	static int N, M;
	static int LDP, RDP;
	static int[][] COST = new int[MAX_N][MAX_N];
	static int[][] DP = new int[MAX_N][MAX_N];

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FastScanner scan = new FastScanner();
		N = scan.nextInt();
		M = scan.nextInt();
		for(int row = 0; row < N; row++) {
			for(int col = 0; col < M; col++) {
				COST[row][col] = scan.nextInt();
			}
		}

		// Fist Row
		for(int col = 0; col < M; col++) {
			LDP = DP[0][col] = COST[0][col] + LDP;
		}

		for(int row = 1; row < N; row++) {
			LDP = -1000;
			for(int col = 0; col < M; col++) {
				LDP = DP[row][col] = COST[row][col] + Math.max(LDP, DP[row-1][col]);
			}
			if(row < N-1) {
				RDP = Integer.MIN_VALUE;
				for(int col = M-1; col >= 0; col--) {
					RDP = COST[row][col] + Math.max(RDP, DP[row-1][col]);
					DP[row][col] = Math.max(DP[row][col], RDP);
				}
			}
		}
		System.out.println(DP[N-1][M-1]);
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
