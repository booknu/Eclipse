package _08_동적_계획법;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/************
 * <주소>		: p226
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
public class 삼각형_위의_최대_경로 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FastScanner sc = new FastScanner();
		int C = sc.nextInt();
		for(int testCase = 0; testCase < C; testCase++) {
			int N = sc.nextInt();
			int[][] arr = new int[N][];
			for(int i = 0; i < N; i++) {
				int size = i + 1;
				arr[i] = new int[size];
				for(int j = 0; j < size; j++) {
					arr[i][j] = sc.nextInt();
				}
			}
			
			solveByLoop(N, arr);
		}
	}
	
	/**
	 * loop를 통하여 해결
	 * 
	 * @param N
	 * @param arr
	 */
	public static void solveByLoop(int N, int[][] arr) {
		// dp에 prev, curr 두 경우만 저장하면 메모리 절약 가능
		// 슬라이딩 윈도
		int[][] dp = new int[N][N];
		dp[0][0] = arr[0][0];
		for(int i = 1; i < N; i++) {
			int size = i + 1;
			for(int j = 0; j < size; j++) {
				if(j - 1 >= 0) {
					dp[i][j] = dp[i - 1][j - 1];
				}
				dp[i][j] = Math.max(dp[i][j], dp[i - 1][j]);
				dp[i][j] += arr[i][j];
			}
		}
		int max = 0;
		for(int i = 0; i < N; i++) {
			max = Math.max(max, dp[N - 1][i]);
		}
//		print(dp);
		System.out.println(max);
	}
	
	static void print(int[][] arr) {
		for(int i = 0; i < arr.length; i++) {
			for(int j = 0; j < arr[0].length; j++) {
				System.out.print(arr[i][j] + " ");
			}
			System.out.println();
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
