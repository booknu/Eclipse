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
public class 예선_2번 {

	static int N, M;
	static ArrayList<Edge>[] graph;
	static boolean visited[];
	static int s, t;
	static int min; // 현재까지 온 경로 중 가장 작은 가중치 임시저장
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FastScanner sc = new FastScanner("input.txt");
		int T = sc.nextInt();
		for (int testCase = 0; testCase < T; testCase++) {
			N = sc.nextInt(); // 정점 수 10,000
			M = sc.nextInt(); // 간선 수 500,000
			graph = new ArrayList[N + 1];
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
			visited = new boolean[N + 1];
			min = Integer.MAX_VALUE;
			sortGraph();
			int Q = sc.nextInt(); // 질문 수 100,000
			long sum = 0;
			for(int q = 0; q < Q; q++) {
				s = sc.nextInt();
				t = sc.nextInt();
				sum += MBP(s);
				
			}
			System.out.println(sum);
		}
	}
	
	public static void sortGraph() {
		for(int i = 1; i <= N; i++) {
			Collections.sort(graph[i]);
//			for(Edge e : graph[i]) {
//				System.out.print(e.w + " ");
//			}
//			System.out.println();
		}
	}
	
	/**
	 * s ~ t로 가는 경로 중 min 대역폭이 다른 경로들과 비교해 max인 대역폭을 반환
	 * 메모이제이션 해도 되나? (안 될듯)
	 * 
	 * @param curr 현재 위치
	 * @return
	 */
	public static int MBP(int curr) {
		if(visited[curr]) {
			return 0;
		}
		if(curr == t) {
			return min;
		}
		
		visited[curr] = true;
		int tempMin = min;
		int ret = 0;
		for(Edge e : graph[curr]) {
			if(e.weight < min) {
				min = e.weight;
			}
			ret = Math.max(ret, MBP(e.to));
			min = tempMin;
		}
		visited[curr] = false;
		return ret;
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
