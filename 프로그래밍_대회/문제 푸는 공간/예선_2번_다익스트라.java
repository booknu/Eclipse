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
public class 예선_2번_다익스트라 {

	static int N, M;
	static ArrayList<Edge>[] graph;
	static int[][] dp;
	static boolean visited[][];
	static int s, t;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FastScanner sc = new FastScanner("input.txt");
		int T = sc.nextInt();
		for (int testCase = 0; testCase < T; testCase++) {
			N = sc.nextInt(); // 정점 수 10,000
			M = sc.nextInt(); // 간선 수 500,000
			graph = new ArrayList[N + 1];
			visited = new boolean[N + 1][N + 1];
			dp = new int[N + 1][N + 1];
			int a, b, e;
			for(int i = 1; i <= N; i++) {
				graph[i] = new ArrayList<Edge>();
			}
			for(int i = 0; i < M; i++) {
				a = sc.nextInt();
				b = sc.nextInt();
				e = sc.nextInt();
				graph[a].add(new Edge(b, e));
				graph[b].add(new Edge(a, e));
			}
			
			int Q = sc.nextInt(); // 질문 수 100,000
			long sum = 0;
			for(int q = 0; q < Q; q++) {
				s = sc.nextInt();
				t = sc.nextInt();
				
				// dp 초기화
				for(int i = 1; i <= N; i++) {
					if(!visited[s][i])
						dp[s][i] = -1;
				}
				dp[s][s] = Integer.MAX_VALUE;
				solve(s);
				
				// test
//				for(int i = 1; i <= N; i++) {
//					System.out.print(dp[s][i] + " ");
//				}
//				System.out.println();
				sum += dp[s][t];
			}
			System.out.println(sum);
		}
	}

	public static void solve(int curr) {
		if(visited[s][curr]) {
			return;
		}
		
		for(int i = 0; i < graph[curr].size(); i++) {
			Edge e = graph[curr].get(i);
			dp[s][e.to] = Math.max(dp[s][e.to], Math.min(dp[s][curr], e.weight));
		}
		visited[s][curr] = true;
		
		for(int i = 0; i < graph[curr].size(); i++) {
			int to = graph[curr].get(i).to;
			if(!visited[curr][to])
				solve(to);
		}
	}
	
	static class Edge implements Comparable<Edge>{
		int to; // 어디로 가는지?
		int weight; // 가중치
		public Edge(int t, int w) {
			this.to = t;
			this.weight = w;
		}

		@Override
		/**
		 * 정렬을 위해 규칙과 반대되는 값을 반환함
		 */
		public int compareTo(Edge o) {
			// TODO Auto-generated method stub
			return o.weight - this.weight;
		}
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
