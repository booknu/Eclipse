package _09_동적_계획법_테크닉;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/************
 * <주소>		: p355 회전초밥
 * **********
 * <해결방안>	: 
 * 
 * **********
 * <오답노트>	: 
 * 
 * dp[i]를 선호도의 합으로 사용한 버전
 * 당연히 오류가 있고 시간초과 발생
 * 
 * **********
 * @author LENOVO
 *
 */
public class 회전초밥_i를_선호도로_사용 {

	static int INF = 987654321;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FastScanner sc = new FastScanner();
		int T = sc.nextInt();
		for(int testCase = 0; testCase < T; testCase++) {
			// (n = [1, 20], m = [1, Integer.max], price = [0, 20000] (multiple of 100)
			int N = sc.nextInt();
			int M = sc.nextInt() / 100; // because price is multiple of 100, use price / 100 to price
			int[] price = new int[N]; // use price / 100 to price
			int[] prefer = new int[N];
			for(int i = 0; i < N; i++) {
				price[i] = sc.nextInt() / 100;
				prefer[i] = sc.nextInt();
			}
			
			int dp[] = new int[10000000]; // i: prefer, value: least price
			dp[0] = INF;
			int ans = 0;
			for(int i = 0; i < dp.length; i++) {
				dp[i] = INF;
				for(int j = 0; j < N; j++) {
					// try to add food[j]
					int prev = i - prefer[j];
					if(prev == 0) {
						dp[i] = Math.min(dp[i], price[j]);
					} else if(prev >= 0 && dp[prev] != INF) {
						dp[i] = Math.min(dp[i], dp[prev] + price[j]);
					}
				}
				if(dp[i] != INF && dp[i] <= M) {
					ans = i;
				}
			}
			System.out.println(ans);
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
