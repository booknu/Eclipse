package 다이나믹_프로그래밍;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/************
 * <주소>		: https://www.acmicpc.net/problem/11054
 * **********
 * <해결방안>	: 
 * 
 * dp[i][0] = A[i]를 최종 원소로 갖는 가장 긴 증가중인 수열
 * dp[i][1] = A[i]를 최종 원소로 갖는 가장 긴 증가중인 수열
 * 
 * 바이토닉 부분 수열 중에
 * 
 * 증가중인 수열은
 * 그 이전의 증가중인 수열에 현재 원소를 포함시켜야 하므로
 * 현재 원소는 이전 증가중인 수열의 마지막 원소보다 커야한다.
 * k = 0 ~ i-1 중 A[k] < A[i]인 수
 * dp[i][0] = max( dp[k][0] ) + 1
 * 
 * 감소중인 수열은 그 이전에 감소중인 수열에 현재 원소를 포함시키거나,
 * 증가중인 수열에 현재 원소를 포함시켜 이번 원소부터 감소시켜야 하므로
 * 현재 원소는 이전 수열의 마지막 원소보다 작아야한다.
 * k = 0 ~ i-1 중 A[k] < A[i]인 수
 * dp[i][1] = max( dp[k][0], dp[k][1] ) + 1
 * 
 * 이 때 dp 중 가장 큰 수를 출력하면 된다.
 * 
 * **********
 * <오답노트>	: 
 * 
 * - 처음에 생각없이 코드를 짜다가 다 짜고보니 단순 LIS 문제에 대한 코드였음
 * 
 * **********
 * @author LENOVO
 *
 */
public class D04_가장_긴_바이토닉_부분_수열 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FastScanner sc = new FastScanner();
		int N = sc.nextInt();
		int[] seq = new int[N];
		for(int i = 0; i < N; i++) {
			seq[i] = sc.nextInt();
		}
		
		// dp[i][0] = A[i]를 최종 원소로 갖는 가장 긴 증가중인 수열
		// dp[i][1] = A[i]를 최종 원소로 갖는 가장 긴 감소중인 수열
		int[][] dp = new int[N][2];
		dp[0][0] = 1;
		dp[0][1] = 1;
		int max = 1;
		for(int i = 1; i < N; i++) {
			for(int j = 0; j < i; j++) {
				if(seq[i] > seq[j]) {
					dp[i][0] = Math.max(dp[j][0], dp[i][0]);
				} else if(seq[i] < seq[j]) {
					dp[i][1] = Math.max(dp[j][0], dp[i][1]);
					dp[i][1] = Math.max(dp[j][1], dp[i][1]);
				}
			}
			dp[i][0]++;
			dp[i][1]++;
			max = Math.max(dp[i][0], max);
			max = Math.max(dp[i][1], max);
		}
		
		System.out.println(max);
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
