package 다이나믹_프로그래밍;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/************
 * <주소>		: https://www.acmicpc.net/problem/2302
 * **********
 * <해결방안>	: 
 * 
 * (아이디어)
 * 
 * 좌석이 바뀌어봐야 3칸 이내로 바뀜
 * 좌석의 개수가 1인 경우부터 시작하면 결국 바뀔 수 있는 경우는
 * (n, n-1) 이 경우 하나임 
 * 
 * (해결)
 * 
 * n-1번 좌석까지의 경우의 수가 구해져있다고 가정하고
 * n번째 좌석까지의 경우의 수를 구할 때
 * 
 * F[n][0] = n번째 좌석까지의 경우의 수인데, 마지막 좌석이 안 바뀐 경우
 * F[n][1] = n번째 좌석까지의 경우의 수인데, 마지막 좌석이 바뀐 경우
 * 일때,
 * 
 * 
 * [case 1: 이전 좌석 or 현재 좌석이 고정석]
 * 
 * F[n][0] = 이전 좌석 or 현재 좌석이 고정되어 있으니 바뀌는 경우가 없음.
 * 따라서 n-1까지의 경우의 수가 곧 이것의 경우의 수임
 * ==> F[n][0] = F[n-1][0] + F[n-1][1]
 * 
 * F[n][1] = 좌석이 고정되야 하는데 바뀌는 경우는 없으니까 0
 * ==> F[n][1] = 0
 * 
 * 
 * [case2: 이전 좌석 or 현재 좌석이 Free]
 * 
 * F[n][0] = 좌석이 안 바뀌는 경우니까 n-1까지의 경우의 수가 곧 이것의 경우의 수
 * ==> F[n][0] = F[n-1][0] + F[n-1][1]
 * 
 * F[n][1] = 좌석이 바뀌는 경우니까 n-1번째 좌석이 안 바뀐 경우가 곧 이것의 경우의 수
 * ==> F[n][1] = F[n-1][0]
 * 
 * **********
 * <오답노트>	: 
 * 
 * **********
 * @author LENOVO
 *
 */
public class D03_극장_좌석 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FastScanner sc = new FastScanner();
		int N = sc.nextInt();
		int M = sc.nextInt();
		boolean fixed[] = new boolean[N + 1];
		for(int i = 0; i < M; i++) {
			fixed[sc.nextInt()] = true;
		}
		
		// dp[n][k] = n번 좌석까지 앉는 개수
		// k = 0: 마지막 좌석이 안 바뀜, k = 1: 마지막 좌석이 바뀜
		int[][] dp = new int[N + 1][2];
		dp[1][0] = 1;
		dp[1][1] = 0;
		for(int i = 2; i <= N; i++) {
			if(fixed[i] || fixed[i - 1]) {
				dp[i][0] = dp[i - 1][0] + dp[i - 1][1];
				dp[i][1] = 0;
			} else {
				dp[i][0] = dp[i - 1][0] + dp[i - 1][1];
				dp[i][1] = dp[i - 1][0];
			}
		}
//		for(int i = 1; i <= N; i++) {
//			System.out.print("좌석[" + i + "]: ");
//			for(int j = 0; j < 2; j++) {
//				System.out.print(dp[i][j] + " ");
//			}
//			System.out.println();
//		}
 		System.out.println(dp[N][0] + dp[N][1]);
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
