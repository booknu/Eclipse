package 다이나믹_프로그래밍;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/************
 * <주소>		: https://www.acmicpc.net/problem/11051
 * **********
 * <해결방안>	: 
 * 
 * 이항계수의 성질
 * C(n, k) + C(n, k+1) = C(n+1, k+1)을 이용해 dp로 해결
 * 
 * **********
 * <오답노트>	: 
 * 
 * 1. 처음에는 이항계수가 뭔지 까먹음
 * 2. 팩토리얼을 계산하는 방식으로 했는데, 숫자가 커지면 오류가 생김
 * 3. k = 0일때 처리를 하지 않아 런타임 오류가 생김
 * 
 * **********
 * @author LENOVO
 *
 */
public class F10_이항계수2 {

	static int DIV = 678639151;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FastScanner sc = new FastScanner();
		int N = sc.nextInt();
		int K = sc.nextInt();
		
		int dp[][] = new int[N + 1][K + 1];
		dp[1][0] = 1;
		if(K > 0) {
			dp[1][1] = 1;
		}
		for(int n = 2; n <= N; n++) {
			dp[n][0] = 1;
			for(int k = 1; k <= K; k++) {
				dp[n][k] = (dp[n - 1][k - 1] + dp[n - 1][k]) % DIV;
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
