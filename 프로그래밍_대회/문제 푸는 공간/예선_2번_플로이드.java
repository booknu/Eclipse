import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

/************
 * <주소>		: 예선 2번
 * **********
 * <해결방안>	: 
 * 
 * small 시간초과
 * 
 * **********
 * <오답노트>	: 
 * 
 * **********
 * @author LENOVO
 *
 */
public class 예선_2번_플로이드 {

	static int N, M, graph[][];
	static int s, t;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FastScanner sc = new FastScanner("in.txt");
		int T = sc.nextInt();
		for (int testCase = 0; testCase < T; testCase++) {
			N = sc.nextInt(); // 정점 수 10,000
			M = sc.nextInt(); // 간선 수 500,000
			graph = new int[N][N];
			int a, b, e;
			for(int i = 0; i < M; i++) {
				a = sc.nextInt() - 1;
				b = sc.nextInt() - 1;
				e = sc.nextInt();
				graph[a][b] = e;
				graph[b][a] = e;
			}
			int[][] dp = floyd();
			int Q = sc.nextInt(); // 질문 수 100,000
			long sum = 0;
			for(int q = 0; q < Q; q++) {
				s = sc.nextInt() - 1;
				t = sc.nextInt() - 1;
				sum += dp[s][t];
//				System.out.println(s + " " + t);
			}
			System.out.println(sum);
		}
	}
	
	public static void initiateDP(int[][] dp) {
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < N; j++) {
				if(graph[i][j] == 0) {
					dp[i][j] = -1;
				} else {
					dp[i][j] = graph[i][j];
				}
			}
		}
	}
	
	public static int[][] floyd() {
		int dp[][] = new int[N][N]; // i, j 경로 중 MBP
		initiateDP(dp);
		
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < N; j++) {
				for(int k = 0; k < N; k++) {
					if(k != i && k != j && i != j) {
						dp[i][j] = Math.max(dp[i][j], 
								Math.min(dp[i][k], dp[k][j]));
					}
				}
			}
		}
		
		print(dp);
		
		return dp;
	}
	
	public static void print(int[][] dp) {
		System.out.println();
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < N; j++) {
				System.out.print(dp[i][j] + " ");
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
