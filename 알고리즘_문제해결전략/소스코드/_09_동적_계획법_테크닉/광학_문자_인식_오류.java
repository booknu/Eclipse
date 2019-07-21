package _09_동적_계획법_테크닉;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/************
 * <주소>		: p288
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
public class 광학_문자_인식_오류 {

	public static int m, n;
	public static String[] words;
	public static double[] B;
	public static double[][] T, M;
	public static int[] target;

	public static double[][] memo;
	public static int[][] choice;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FastScanner sc = new FastScanner();
		m = sc.nextInt(); // 원문에 출현할 수 있는 단어 수
		int Q = sc.nextInt(); // 처리해야할 문장 수
		words = new String[m + 1];
		B = new double[m]; // i번 단어가 첫 단어로 출현할 확률
		T = new double[m + 1][m + 1]; // i의 다음이 j일 확률
		M = new double[m + 1][m + 1]; // i를 j로 생각할 확률
		for(int i = 1; i <= m; i++) {
			words[i] = sc.nextToken();
		}
		for(int i = 0; i < m; i++) {
			B[i] = sc.nextDouble();
		}
		for(int i = 1; i <= m; i++) {
			for(int j = 1; j <= m; j++) {
				T[i][j] = sc.nextDouble();
			}
		}
		for(int i = 1; i <= m; i++) {
			for(int j = 1; j <= m; j++) {
				M[i][j] = sc.nextDouble();
			}
		}
		
		for(int i = 0; i < Q; i++) {
			n = sc.nextInt();
			target = new int[n]; // words의 index를 사용한 문장 저장
								 // 분류기가 반환한 문장
			for(int j = 0; j < n; j++) {
				String temp = sc.nextToken();
				for(int k = 0; k < m; k++) {
					if(temp.equals(words[k])) {
						target[j] = k;
					}
				}
			}
			solve();
		}
	}

	public static void solve() {
		memo = new double[n][m];
		for(int i = 0; i < memo.length; i++) {
			Arrays.fill(memo[i], 1.0);
		}
		choice = new int[n][m];
		
		double max = 0.0;
		int index = 0;
		for(int i = 1; i <= m; i++) {
			T[0][i] = B[i - 1];
			double ret = recognize(0, i - 1);
			if(ret > max) {
				max = ret;
				index = i;
			}
		}
		System.out.println(reconstruct(0, index - 1));
	}
	
	/**
	 * Q[seg] 이후를 채워서 얻을 수 있는 최대 g() 곱의 로그 값을 반환
	 * Q[seg-1] == prevMatch라고  가정
	 * 
	 * @param seg
	 * @param prevMatch Q[seg-1]
	 * @return
	 */
	public static double recognize(int seg, int prevMatch) {
		if(seg == n) {
			return 0;
		}
		if(memo[seg][prevMatch] != 1.0) {
			return memo[seg][prevMatch];
		}
		int choose = choice[seg][prevMatch];
		double ret = -1e200; // 음의 무한대
		for(int thisMatch = 0; thisMatch < m; thisMatch++) {
			double cand = T[prevMatch][thisMatch] 
					+ M[thisMatch][target[seg]]
					+ recognize(seg + 1, thisMatch);
			if(ret < cand) {
				ret = cand;
				choose = thisMatch;
			}
		}
		memo[seg][prevMatch] = ret;
		choice[seg][prevMatch] = choose;
		return ret;
	}
	
	public static String reconstruct(int seg, int prevMatch) {
		int choose = choice[seg][prevMatch];
		String ret = words[choose];
		if(seg < n-1) {
			ret = ret + " " + reconstruct(seg + 1, choose);
		}
		return ret;
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
