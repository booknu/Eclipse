package _08_동적_계획법;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/************
 * <주소>		: p258 (256 업그레이드 버전)
 * **********
 * <해결방안>	: 
 * 
 * dp[j] = j까지 기어오를 확률인데,
 * 바깥에 for문을 M에 대해 돌림으로서
 * dp[j]를 i일에 j까지 기어오르는 확률로 사용했다.
 * dp[j]는 2만큼 전에 0.75를 곱한 것과 (비올 확률) 1만큼 전에 0.25를 곱한 것(안 올 확률)
 * 을 더한것과 같다. (모두 1일 전을 기준)
 * 
 * **********
 * <오답노트>	: 
 * 
 * 1. N 이상의 높이로 올라갈 수 있는데 그걸 간과함 (최대 2N 까지 가능)
 * 2. 1일차 ~ M일차까지 모든 경우를 세려고 했었음
 * 
 * **********
 * @author LENOVO
 *
 */
public class 달팽이 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FastScanner sc = new FastScanner();
		int T = sc.nextInt();
		for (int testCase = 0; testCase < T; testCase++) {
			int N = sc.nextInt(); // 깊이
			int M = sc.nextInt(); // 날짜
			int limit = 2*N + 1;
			
			double dp[] = new double[limit]; // i일에 j까지 기어오르는 확률
			dp[0] = 0;
			dp[1] = 0.25;
			dp[2] = 0.75;
			for(int day = 2; day <= M; day++) {
				for(int height = limit - 1; height >= day; height--) {
					dp[height] = dp[height - 2] * 0.75 + dp[height - 1] * 0.25;
				}
				for(int i = 0; i < day; i++) {
					dp[i] = 0;
				}
				
//				System.out.println("day: " + day);
//				print(dp);
			}
			double sum = 0;
			for(int height = N; height < limit; height++) {
				sum += dp[height];
			}
			System.out.println(sum);
		}
	}
	
	public static void print(double[] dp) {
		for(int i = 0; i < dp.length; i++) {
			System.out.print(dp[i] + "  ");
		}
		System.out.println("\n------");
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
