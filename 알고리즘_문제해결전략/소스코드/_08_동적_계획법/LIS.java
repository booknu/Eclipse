package _08_동적_계획법;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/************
 * <주소>		: p230
 * **********
 * <해결방안>	: 
 * 
 * **********
 * <오답노트>	: 
 * 
 * **********
 * @author LENOVO
 *
 */
public class LIS {

	public static int INF = 1000001;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FastScanner sc = new FastScanner();
		int C = sc.nextInt();
		for(int testCase = 0; testCase < C; testCase++) {
			int N = sc.nextInt();
			int[] seq = new int[N];
			for(int i = 0; i < N; i++) {
				seq[i] = sc.nextInt();
			}
			
			int[] dp = new int[N + 1]; // size가 i개인 수열 중 마지막 원소가 dp[i]인 것
			dp[1] = seq[0];
			int max = 1;
			for(int i = 1; i < N; i++) {
				// 현재 원소가 현재 LIS에 포함될 수 있음
				if(seq[i] > dp[max]) {
					dp[++max] = seq[i];
				} else {
					// 아니면 dp[max]까지의 정보를 갱신함
					for(int j = 1; j <= max; j++) {
						if(seq[i] < dp[j]) {
							dp[j] = seq[i];
							break; // 왜 break?
						}
					}
				}
			}
//			print(dp);
			System.out.println(max);
		}
	}
	
	public static void print(int[] dp) {
		for(int i = 0; i < dp.length; i++) {
			System.out.print(dp[i] + " ");
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
