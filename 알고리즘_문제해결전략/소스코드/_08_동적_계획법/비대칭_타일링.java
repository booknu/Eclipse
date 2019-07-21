package _08_동적_계획법;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/************
 * <주소>		: p259
 * **********
 * <해결방안>	: 
 * 
 * 전체 타일링 수 - 대칭인 타일링 수
 * 
 * 대칭인 타일링 수의 경우
 * syn[i] = i개인 대칭인 타일링 경우의 수
 * i가 홀수일 때: 중앙에 2x1타일을 기준으로 대칭
 * i가 짝수일 때: 중앙에 2x2타일을 기준으로 대칭 or 반반으로 대칭
 * 
 * **********
 * <오답노트>	: 
 * 
 * dp[i] - syn[i]에서 DIV로 나눈 나머지를 구하는 부분을 잘 봐야함
 * 
 * **********
 * @author LENOVO
 *
 */
public class 비대칭_타일링 {

	static final int DIV = 1000000007;
	static final int MAX = 101;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FastScanner sc = new FastScanner();
		int[] ans = solveBySubSymmetric();
		int T = sc.nextInt();
		for (int testCase = 0; testCase < T; testCase++) {
			int N = sc.nextInt();
			System.out.println(ans[N]);
		}
	}
	
	/**
	 * 대칭 - 비대칭
	 * @return
	 */
	public static int[] solveBySubSymmetric() {
		int[] ans = new int[MAX];
		int[] dp = new int[MAX];
		int[] sym = new int[MAX];
		dp[0] = 1;
		dp[1] = 1;
		sym[0] = 1;
		sym[1] = 1;
		for(int i = 2; i < MAX; i++) {
			dp[i] = dp[i - 1] + dp[i - 2];
			if((i & 1) == 1) {
				sym[i] = dp[(i - 1) / 2];
			} else {
				sym[i] = dp[(i - 2) / 2] + dp[i / 2];
			}
			dp[i] %= DIV;
			sym[i] %= DIV;
			ans[i] = (dp[i] - sym[i] + DIV) % DIV;
		}
//		print(sym);
		return ans;
	}
	
	public static void print(int[] arr) {
		for(int i = 0; i < arr.length; i++) {
			System.out.println(i + ": " + arr[i]);
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
