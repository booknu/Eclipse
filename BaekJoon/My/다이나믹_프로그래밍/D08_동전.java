package 다이나믹_프로그래밍;
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
 * (Other 폴더에 다른 풀이와, 내가 잘못했던점 적혀 있음)
 * 
 * dp[i][j] = i원을 j까지의 동전만을 사용해 만드는 경우의 수
 * 
 * dp[0][모두] = 1 이고
 * 
 * dp[i][j] += dp[i - coins[j]][j] + dp[i][j]
 * 
 * Other에 있는 소스와 비슷한 원리인데,
 * 나는 dp[i][j]같이 2차원 배열로 만들어 메모리 낭비가 생김
 * Other 소스는 이전 계산 결과에 덮어씌우는 식
 * 
 * **********
 * <오답노트>	: 
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
			int coins[] = new int[N + 1];
			for(int i = 1; i <= N; i++) {
				coins[i] = sc.nextInt();
			}
			int M = sc.nextInt();

			int dp[][] = new int[M + 1][N + 1];
			for(int i = 0; i <= N; i++) {
				dp[0][i] = 1;
			}

			for(int i = 1; i <= M; i++) {
				for(int j = 1; j <= N; j++) {
					if(i - coins[j] >= 0) {
						dp[i][j] += dp[i - coins[j]][j];
					}
					dp[i][j] += dp[i][j - 1];
				}
			}


//			for(int i = 0; i <= M; i++) {
//				for(int j = 0; j <= N; j++) {
//					System.out.println("[" + i + "]" + "[" + j + "] " + dp[i][j]);
//				}
//			}
			
			System.out.println(dp[M][N]);
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
