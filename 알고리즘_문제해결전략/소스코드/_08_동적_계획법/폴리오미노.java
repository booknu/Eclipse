package _08_동적_계획법;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/************
 * <주소>		: p264
 * **********
 * <해결방안>	: 
 * 
 * 책 참조
 * 
 * 1. n개인 폴리오미노를 만들 때는 첫 줄이 1 ~ n개이고,
 *    그 밑에 그것을 뺀 나머지 폴리오미노가 달리는 방식으로 만들 수 있음.
 * 
 * 2. 하지만 밑에 달리는 폴리오미노의 첫 번째 줄의 개수에 따라 그것을 붙일 수 있는
 *    경우의 수가 달라짐
 *    ex) 2개짜리 밑에 첫 줄 2개짜리 달면 ==> 3 * poly(remain)
 *        2개짜리 밑에 첫 줄 3개짜리 달면 ==> 4 * poly(remain)
 * 
 * 3. 따라서 dp[i][j] = i개의 사각형으로 만들 수 있는 첫 줄이 j개인 경우의 수
 *    
 *    i = 3에서 시작해서 N까지 가며 dp를 채우는데, (i)
 *    
 *    우선 첫 줄에 1개를 넣어서 구해보고, 2개를 넣어서 구해보고.... (first)
 *    이 때, 첫 줄이 i개이면 1가지 경우밖에 없다고 예외 처리를 하고
 *    첫 줄을 채우고 남은 사각형으로 만들 수 있는 poly를 밑에 붙이는데,
 *    
 *    그 poly들 또한 첫 줄이 1개인 경우, 2개인 경우...., remain개인 경우를 나눠
 *    dp[i][first]에 더한다.
 * 
 * **********
 * <오답노트>	: 
 * 
 * 첫 줄에 모든 사각형을 넣는 경우의 예외처리를 안 해서 경우의 수를 덜 셈
 * 
 * **********
 * @author LENOVO
 *
 */
public class 폴리오미노 {

	static final int DIV = 10000000;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FastScanner sc = new FastScanner();
		int T = sc.nextInt();
		for (int testCase = 0; testCase < T; testCase++) {
			int N = sc.nextInt();
			
			int[][] dp = new int[N + 1][N + 1]; // i개의 사각형으로 만들 수 있는 경우의 수
												// 단, 첫 번째 줄에는 j개의 사각형이 들어감
			dp[1][1] = 1;
			dp[2][1] = 1;
			dp[2][2] = 1;
			for(int n = 3; n <= N; n++) {
				for(int first = 1; first <= n; first++) {
					// 첫 줄에 모든 사각형이 들어가는 경우면 1가지
					if(first == n) {
						dp[n][first] = 1;
					}
					
					int remain = n - first; // 첫 줄을 채우고 남은 사각형 수
					// 첫 줄이 1 ~ remain개인 폴리오미노들로 채워봄
					for(int prevFirst = 1; prevFirst <= remain; prevFirst++) {
						dp[n][first] += dp[remain][prevFirst] * (first + prevFirst - 1);
						dp[n][first] %= DIV;
					}
				}
			}
			
			int sum = 0;
			for(int i = 1; i <= N; i++) {
				sum += dp[N][i];
				sum %= DIV;
			}
			System.out.println(sum);
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
