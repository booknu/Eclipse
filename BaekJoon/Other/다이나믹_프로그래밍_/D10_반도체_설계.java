package 다이나믹_프로그래밍_;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/************
 * <주소>		: https://www.acmicpc.net/problem/2352
 * **********
 * <해결방안>	: 
 * 
 * 내 코드보다 10배 빠름
 * 
 * dp[size]를 사용했는데
 * dp[size] = 길이가 size인 증가수열 중 마지막 원소
 * 
 * ex) 2 3 5 6 7이라는 수열이 있으면
 *     dp[5] = 7
 *     
 * 1. 현재까지 구해진 수열 중 가장 긴 것에 포함될 수 있으면 포함시킴
 * 2. 그럴 수 없다면 그 이전 수열들에 현재 원소를 넣어 갱신시킬 수 있으면 갱신
 * 
 * **********
 * <오답노트>	: 
 * 
 * **********
 * @author LENOVO
 *
 */
public class D10_반도체_설계 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FastScanner sc = new FastScanner();
		int	N = sc.nextInt();
		int conn[] = new int[N];
		for(int i = 0; i < N; i++) {
			conn[i] = sc.nextInt();
		}

		int input;
		int size = 0;
		// 길이가 size + 1인 증가수열의 마지막 값
		int dp[] = new int[N];
		dp[0] = conn[0];
		for(int i = 1; i < N; i++) {
			input = conn[i];
			// 가장 긴 것에 포함될 수 있는 경우
			if(dp[size] < input) {
				dp[++size] = input;
			} else {
				for(int j = 0; j < i; j++) {
					// j만큼의 길이를 가지고 있는 수열이 있고,
					// 그 수열의 마지막이 input보다 크면
					// 그 수열의 마지막을 input으로 바꾸는게 이득
					// ex) 2 3 5 --> 2 3 4 (이득)
					if(dp[j] > input) {
						dp[j] = input;
						break;
					}
				}
			}
		}
		System.out.println(size + 1);
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
