package 다이나믹_프로그래밍_;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/************
 * <주소>		: https://www.acmicpc.net/problem/2616
 * **********
 * <해결방안>	: 
 * 
 * 여기서 sum을 미리 구해놓는 아이디어를 떠올림
 * 
 * 내 코드랑 비슷한데 왜 더 빠른지 모르겠음
 * 
 * **********
 * <오답노트>	: 
 * 
 * **********
 * @author LENOVO
 *
 */
public class D10_소형기관차 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FastScanner sc = new FastScanner();
		int N = sc.nextInt();
		int[] people = new int[N + 1];
		for(int i = 1; i <= N; i++) {
			people[i] = sc.nextInt();
		}
		int capacity = sc.nextInt();
		
		int sum[] = new int[N + 1];
		// 첫 번째 sum 구함
		for(int i = 1; i <= capacity; i++) {
			sum[capacity] += people[i];
		}
		// 그걸 이용해 나머지 sum 구함
		for(int i = capacity + 1; i <= N; i++) {
			sum[i] = sum[i - 1] + people[i] - people[i - capacity];
		}
		int[][] dp = new int[4][N + 1];
		for(int i = 1; i <= 3; i++) {
			for(int j = i * capacity; j <= N; j++) {
				dp[i][j] = Math.max(sum[j] + dp[i - 1][j - capacity], dp[i][j-1]);
			}
		}
		System.out.println(dp[3][capacity]);
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
