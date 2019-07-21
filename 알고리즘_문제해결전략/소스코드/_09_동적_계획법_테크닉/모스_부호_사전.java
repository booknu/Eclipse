package _09_동적_계획법_테크닉;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/************
 * <주소>		: p293 모스 부호 사전 (MORSE)
 * **********
 * <해결방안>	: 
 * 
 * 현재 n개의 '-'와 m개의 'o'로 skip 개의 신호를 스킵한 신호를 구하기 위해서
 * 신호의 head를 '-'로 선택할 경우 0개의 신호를 스킵한 것이고
 * 신호의 head를 'o'로 선택할 경우 n+m-1 C n-1 개의 신호를 스킵한 것이다
 * (head '-'를 선택한 신호를 스킵한 것인데,
 *  head '-'를 제외한 n+m-1개 중에서 '-'가 들어갈 자리 n-1개를 고르는 것)
 * 
 * **********
 * <오답노트>	: 
 * 
 * bino를 구할 때 overflow를 막기 위해 큰 값을 구하지 않았더니
 * 정답처리됨
 * 
 * **********
 * @author LENOVO
 *
 */
public class 모스_부호_사전 {

	public static long[][] bino;
	public static final int BINO_SIZE = 201;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FastScanner sc = new FastScanner();
		int T = sc.nextInt();
		setBino();
		for(int testCase = 0; testCase < T; testCase++) {
			int N = sc.nextInt(); // number of '-' (0 to order of dictionary)
			int M = sc.nextInt(); // number of 'o' (1 to order of dictionary)
			int K = sc.nextInt(); // Kth signal
			
			// to make Kth signal, we should skip K-1 signals 
			System.out.println(generate(K - 1, N, M));
		}
	}
	
	/**
	 * by using n of '-' and m of 'o' to make signals,
	 * set a priority to signals '-' comes first.
	 * if signals sorted by order, 
	 * skip number of 'skip' signals, return signal after skipped.
	 * 
	 * @param skip number of signals to skip
	 * @param n rest number of '-'
	 * @param m rest number of 'o'
	 * @return signal
	 */
	public static String generate(int skip, int n, int m) {
		// if don't have '-', all of the rest part of signal consisted of 'O' 
		if(n == 0) {
			StringBuilder ret = new StringBuilder("");
			for(int i = 0; i < m; i++) {
				ret.append('o');
			}
			return ret.toString();
		}
		
		// if head of signal is '-' (can't skip to 'o')
		// to make head of signal is 'o',
		// we should skip number of signals n+m-1 C n-1
		if(skip < bino[n + m - 1][n - 1]) {
			return "-" + generate(skip, n - 1, m);
		}
		// if head of signal is 'o' (skipped all of '-')
		// skip '-' and add head of 'o' + rest part of signal
		return "o" + generate(skip - (int)bino[n + m - 1][n - 1], n, m - 1);
	}
	
	/**
	 * set bino
	 */
	public static void setBino() {
		int max = 1000000000 + 100;
		bino = new long[BINO_SIZE][BINO_SIZE];
		for(int n = 0; n < BINO_SIZE; n++) {
			bino[n][0] = bino[n][n] = 1;
			for(int k = 1; k < n; k++) {
				bino[n][k] = Math.min(bino[n-1][k-1] + bino[n-1][k], max);
			}
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
