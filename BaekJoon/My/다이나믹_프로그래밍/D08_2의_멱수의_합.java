package 다이나믹_프로그래밍;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/************
 * <주소>		: https://www.acmicpc.net/problem/2410
 * **********
 * <해결방안>	: 
 * 
 * 먼저 2의 i제곱승을 저장해놓는 sq[i] 배열을 만들어 놓는다.
 * sq[i] = sq[i - 1] * 2로 구현한다.
 * 
 * 그리고 나서 각각의 sq[i]에 대해 dp[j]를 채우는데,
 * 일단 sq[1]만을 이용해서 채워보고,
 * 그 다음 sq[2]를 이용해 dp를 채워보고
 * ...
 * dp[j] += dp[j - sq[i]]
 * 
 * **********
 * <오답노트>	: 
 * 
 * sq[i]의 각각에 대해
 * dp[j]를 구할 때 이전 dp가 수정될까봐 N에서부터 거꾸로 돌렸는데
 * 그것이 j - sq[i] * 2이상의 수 를 경우에 추가하지 않는 경우를 초래했다.
 * dp를 정방향으로 돌리니 위 경우를 포함하게 되어 해결됐다.
 * 
 * **********
 * @author LENOVO
 *
 */
public class D08_2의_멱수의_합 {

	static final int DIV = 1000000000;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FastScanner sc = new FastScanner();
		int N = sc.nextInt();
		int sq[] = new int[21];
		sq[0] = 1;
		for(int i = 1; i < 21; i++) {
			sq[i] = sq[i - 1] * 2;
		}

		int dp[] = new int[N + 1];
		Arrays.fill(dp, 1);
		dp[0] = 1;
		for(int j = 1; sq[j] <= N; j++) {
			int curr = sq[j];
			for(int i = curr; i <= N; i++) {
				dp[i] += dp[i - curr];
				dp[i] %= DIV;
			}
		}
		System.out.println(dp[N]);
	}
	
	public static void print(int[] arr, int limit) {
		for(int i = 0; i < limit; i++) {
			System.out.print(arr[i] + " ");
		}
		System.out.println("\n");
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
