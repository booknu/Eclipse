package lgCodemonster;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

/************
 * <주소>		: 예선 1번
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
public class 예선_1번 {

	static final int MAX_BIT = 62;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FastScanner sc = new FastScanner("in.txt");
		int T = sc.nextInt();
		for (int testCase = 0; testCase < T; testCase++) {
			long from = sc.nextLong();
			long to = sc.nextLong();
			
			long dp[] = countSetBit();
			
//			for(int i = 0; i < 60; i++) {
//				System.out.println(dp[i]);
//			}
			
			long f = countFromZero(dp, from);
			long t = countFromZero(dp, to);
			
			System.out.println(t - f);
		}
	}
	
	/**
	 * dp[i]는 i번째 bit가 1이 될 때까지 바뀐 비트 수
	 *
	 * @return
	 */
	public static long[] countSetBit() {		
		long dp[] = new long[MAX_BIT];
		dp[0] = 1;
		for(int i = 1; i < MAX_BIT; i++) {
			dp[i] = dp[i - 1] * 2 + 1;
		}
		return dp;
	}
	
	/**
	 * 0 ~ 이진수까지의 바뀐 비트 수를 센다
	 * arr은 이진수에서 1인 비트의 자리수를 뜻한다.
	 * 
	 * @param arr
	 * @return
	 */
	public static long countFromZero(long dp[], long num) {
		ArrayList<Integer> arr = convert(num);
//		System.out.println("num: " + num);
//		for(int a : arr) {
//			System.out.println(a);
//		}
		long sum = 0;
		for(int a : arr) {
			sum += dp[a];
		}
		return sum;
	}
	
	/**
	 * num을 countFromZero 메소드에 쓰기 알맞게 변환한다.
	 * @param num 숫자
	 * @return
	 */
	public static ArrayList<Integer> convert(long num) {
		ArrayList<Integer> list = new ArrayList<Integer>();
		for(int i = 0; i < MAX_BIT; i++) {
			if((num & (1L << i)) != 0) {
				list.add(i);
			}
		}
		return list;
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
