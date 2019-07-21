package 다이나믹_프로그래밍;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/************
 * <주소>		: https://www.acmicpc.net/problem/2631
 * **********
 * <해결방안>	: 
 * 
 * 증가하는 최대의 부분수열을 찾아 N에서 뺀다.
 * 그 이유는 가장 긴 완성된 줄을 건드리기 보다는
 * 다른 것을 옮기는게 낫기 떄문이다.
 * 
 * **********
 * <오답노트>	: 
 * 
 * - 다른 사람 아이디어를 듣고 짬
 * 
 * **********
 * @author LENOVO
 *
 */
public class D04_줄세우기 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FastScanner sc = new FastScanner();
		int N = sc.nextInt();
		int line[] = new int[N];
		for(int i = 0; i < N; i++) {
			line[i] = sc.nextInt();
		}
		
		int dp[] = new int[N];
		dp[0] = 1;
		int max = 0;
		for(int i = 1; i < N; i++) {
			for(int j = 0; j < i; j++) {
				if(line[i] > line[j]) {
					dp[i] = Math.max(dp[i], dp[j]);
				}
			}
			dp[i]++;
			max = Math.max(max, dp[i]);
		}
		
		System.out.println(N - max);
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
