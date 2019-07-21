package _08_동적_계획법;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/************
 * <주소>		: p252
 * **********
 * <해결방안>	: 
 * 
 * dp[i] = dp[i-2]에 가로 사각형 1개 추가 하는 것
 *         + dp[i-1]에 세로 사각형 1개 추가하는 것
 * 
 * **********
 * <오답노트>	: 
 * 
 * **********
 * @author LENOVO
 *
 */
public class 타일링_방법의_수_세기 {

	public static final int MAX = 101;
	public static final int DIV = 1000000007;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] dp = solve();
		for(int i = 0; i < MAX; i++) {
			System.out.println(i + ": " + dp[i]);
		}
		FastScanner sc = new FastScanner();
		int T = sc.nextInt();
		for(int testCase = 0; testCase < T; testCase++) {
			System.out.println(dp[sc.nextInt()]);
		}
	}
	
	public static int[] solve() {
		int[] dp = new int[MAX];
		dp[0] = 1;
		dp[1] = 1;
		for(int i = 2; i < MAX; i++) {
			dp[i] = dp[i - 1] + dp[i - 2];
			dp[i] %= DIV;
		}
		return dp;
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
