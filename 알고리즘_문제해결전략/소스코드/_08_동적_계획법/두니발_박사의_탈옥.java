package _08_동적_계획법;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/************
 * <주소>		: p269
 * **********
 * <해결방안>	: 
 * 
 * 1. memo[D][N] = d+1일 후 n 마을에 있을 확률 
 *    우선 1일차에 교도소가 있는 곳에서 연결된 마을까지 갈 수 있는 확률을 구해놓음
 *    (memo[0][i]를 채워놓음)
 *    
 * 2. 전 날의 memo[day - 1]을 기반으로 
 *    오늘의 memo[day]를 채워나감
 *    memo[day][n] = sum( memo[day - 1][연결된 마을들] / 연결된 마을들에 연결된 마을 수)
 * 
 * 3. 따라서 연결된 마을들의 수를 linkNum[n]으로 표현해놓음 
 * 
 * **********
 * <오답노트>	: 
 * 
 * **********
 * @author LENOVO
 *
 */
public class 두니발_박사의_탈옥 {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FastScanner sc = new FastScanner();
		int C = sc.nextInt();
		for (int testCase = 0; testCase < C; testCase++) {
			int N = sc.nextInt(); // 마을 개수
			int D = sc.nextInt(); // 며칠이 지났는지
			int P = sc.nextInt(); // 교도소가 있는 마을의 번호
			boolean[][] link = new boolean[N][N];
			for(int i = 0; i < N; i++) {
				for(int j = 0; j < N; j++) {
					if(sc.nextInt() == 1) {
						link[i][j] = true;
					} else {
						link[i][j] = false;
					}
				}
			}
			int T = sc.nextInt();
			int[] quest = new int[T];
			for(int i = 0; i < T; i++) {
				quest[i] = sc.nextInt();
			}
			
			double[][] dp = new double[D][N]; // i일 후 N마을에 있을 확률
			int[] linkNum = new int[N]; // n 마을에 연결된 마을의 수
			
			// 각 마을에 연결된 마을 수 초기화
			for(int i = 0; i < N; i++) {
				for(int j = 0; j < N; j++) {
					if(link[i][j]) {
						linkNum[i]++;
					}
				}
			}
			
			// 0일차 확률 초기화
			for(int i = 0; i < N; i++) {
				if(link[P][i]) {
					dp[0][i] = 1.0 / linkNum[P];
				}
			}
			// 1일차부터 계산 시작
			for(int day = 1; day < D; day++) {
				for(int n = 0; n < N; n++) {
					for(int l = 0; l < N; l++) {
						if(link[n][l]) {
							dp[day][l] += dp[day - 1][n] / linkNum[n];
						}
					}
				}
			}
			
//			print(memo);
			
			for(int i = 0; i < T; i++) {
				System.out.print(dp[D - 1][quest[i]] + " ");
			}
			System.out.println();
		}
	}
	
	public static void print(double[][] memo) {
		System.out.println();
		for(int i = 0; i < memo.length; i++) {
			for(int j = 0; j < memo[i].length; j++) {
				System.out.print(memo[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println("-----------------------------");
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
