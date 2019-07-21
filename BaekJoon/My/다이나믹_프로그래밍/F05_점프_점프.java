package 다이나믹_프로그래밍;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/************
 * <주소>		: https://www.acmicpc.net/problem/2098
 * **********
 * <해결방안>	: 
 * 
 * dp[i] = i까지 오는데 걸리는 최소 점프 수
 * 
 * 미로의 0번째부터 해당하는 곳에서 갈 수 있는 곳까지의 최소값을 구하는 방식
 * 
 * i = 1 ~ N
 * dp[i + jump] = min(dp[i] + 1, dp[i + jump])
 * 
 * **********
 * <오답노트>	: 
 * 
 * **********
 * @author LENOVO
 *
 */
public class F05_점프_점프{

	static int INF = 1000000;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FastScanner sc = new FastScanner();
		int N = sc.nextInt();
		int[] maze = new int[N];
		for(int i = 0; i < N; i++) {
			maze[i] = sc.nextInt();
		}
		
		int[] dp = new int[N];
		Arrays.fill(dp, INF);
		dp[0] = 0;
		for(int i = 0; i < N; i++) {
			if(maze[i] == 0) {
				continue;
			}
			for(int j = 1; j <= maze[i]; j++) {
				int next = i + j;
				if(next < N) {
					dp[next] = Math.min(dp[i] + 1, dp[next]);
				} else {
					break;
				}
			}
		}
//		for(int i = 0; i < N; i++) {
//			System.out.println(i + 1 + ": " + dp[i]);
//		}
		System.out.println(dp[N - 1] == INF? -1 : dp[N - 1]);
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
