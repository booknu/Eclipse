package _08_동적_계획법;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/************
 * <주소>		: p254
 * **********
 * <해결방안>	: 
 * 
 * - 일단 각 삼각형의 자리마다 그 곳까지 오는 최대합을 dp에 저장하고,
 *   (i, j)까지의 최대합 경로의 수는 (i-1, j), (i-1)(j-1)이 (i, j)까지의
 *   최대합 경로에 속하면 그것을 더한다.
 * 
 * **********
 * <오답노트>	: 
 * 
 * **********
 * @author LENOVO
 *
 */
public class 삼각형_위의_최대_경로_수_세기 {

	static int dy[] = {1, 1};
	static int dx[] = {0, 1};
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FastScanner sc = new FastScanner();
		int T = sc.nextInt();
		for (int testCase = 0; testCase < T; testCase++) {
			int N = sc.nextInt();
			int[][] tri = new int[N][];
			for(int i = 0; i < N; i++) {
				int length = i + 1;
				tri[i] = new int[length];
				for(int j = 0; j < length; j++) {
					tri[i][j] = sc.nextInt();
				}
			}
			
			// dp 채우기
			int[][] dp = new int[N][N + 1]; // i, j까지의 경로합의 최대값
			int[][] maxWays = new int[N][N + 1]; // i, j까지 합이 최대가 되도록 오는 방법의 수
			dp[0][0] = tri[0][0];
			maxWays[0][0] = 1;
			int max = 0;
			for(int y = 1; y < N; y++) {
				int length = y + 1;
				for(int x = 0; x < length; x++) {
					for(int direct = 0; direct < 2; direct++) {
						int py = y - dy[direct];
						int px = x - dx[direct];
						if(px >= 0 && px < length - 1) {
							dp[y][x] = Math.max(dp[y][x], dp[py][px]);
						}
					}
					dp[y][x] += tri[y][x];
					max = Math.max(max, dp[y][x]);
				}
			}
//			print(dp);
			
			// maxWays 채우기
			for(int y = 1; y < N; y++) {
				int length = y + 1;
				for(int x = 0; x < length; x++) {
					int prev = dp[y][x] - tri[y][x]; // 이전 max값
					for(int direct = 0; direct < 2; direct++) {
						int py = y - dy[direct];
						int px = x - dx[direct];
						if(px >= 0 && px < length - 1) {
							// py, px가 max 경로 중 하나이면
							if(dp[py][px] == prev) {
								maxWays[y][x] += maxWays[py][px];
							}
						}
					}
				}
			}
			int ways = 0;
			for(int i = 0; i <= N; i++) {
				if(dp[N-1][i] == max) {
					ways += maxWays[N - 1][i];
				}
			}
//			print(maxWays);
			System.out.println(ways == 0? 1 : ways);
		}
	}
	
	public static void print(int[][] dp) {
		System.out.println();
		for(int i = 0; i < dp.length; i++) {
			for(int j = 0; j < i + 1; j++) {
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
