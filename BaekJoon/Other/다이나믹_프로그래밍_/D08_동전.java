package 다이나믹_프로그래밍_;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/************
 * <주소>		: https://www.acmicpc.net/problem/9084
 * **********
 * <해결방안>	: 
 * 
 * (https://www.acmicpc.net/source/3459672 참조함)
 * 
 * dp[i] = i원을 만드는 경우의 수
 * 
 * 처음에는 1개의 동전만을 사용해 dp[i]를 만들고,
 * 그 뒤에 다른 동전을 추가한 경우를 구하고,
 * ...
 * 
 * **********
 * <오답노트>	: 
 * 
 * - 나는 돈을 coins[0]부터 M까지 1씩 올려가며
 *   dp[i] += dp[i - coins[가능한경우]]에 coins[]를 더하는 식으로 구했는데, 
 *   testCase (1 2로 10000원 만들기) 에서
 *   1: 1
 *   2: 1 1, 2
 *   3: 1 2, 1 1 1, 2 1
 *   이렇게 중복되는 경우도 세게 되었다.
 *   
 * - 바꾼 코드는 동전을 추가하는 순서를 강제하게 되는 효과가 있음
 * 
 * **********
 * @author LENOVO
 *
 */
public class D08_동전 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FastScanner sc = new FastScanner();
		int T = sc.nextInt();
		for(int testCase = 0; testCase < T; testCase++) {
			int N = sc.nextInt();
			int coins[] = new int[N];
			for(int i = 0; i < N; i++) {
				coins[i] = sc.nextInt();
			}
			int M = sc.nextInt();

			int dp[] = new int[M + 1];
			dp[0] = 1;
			for(int j = 0; j < N; j++) {
				for(int i = coins[j]; i <= M; i++) {
					dp[i] += dp[i - coins[j]];
				}
			}
			
			// 내가 짠 잘못된 코드
//			for(int i = coins[0]; i <= M; i++) {
//				for(int j = 0; j < N && i - coins[j] >= 0; j++) {
//					dp[i] += dp[i - coins[j]];
//				}
//			}

			System.out.println(dp[M]);
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
