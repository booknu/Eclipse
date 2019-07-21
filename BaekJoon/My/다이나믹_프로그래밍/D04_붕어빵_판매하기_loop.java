package 다이나믹_프로그래밍;

/************
 * <주소>		: https://www.acmicpc.net/problem/11052
 * **********
 * <해결방안>	: 
 * 
 * - 재귀적 방법으로 한 것의 해결방안을 쓰다가 생각남
 * 
 * - F(n)은 n개의 붕어빵을 판매할 때 남길 수 있는 최대 이익
 *   즉, 가능한 경우를 하나씩 빼보면서 검사
 * 	 k = 0 ~ n 일 때 (뺄 붕어빵 1개)
 *   F(n) = F(n - k) + bread[k] 중 최대값
 * **********
 * <오답노트>	: 
 * 
 * **********
 * @author LENOVO
 *
 */
public class D04_붕어빵_판매하기_loop {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FastScanner sc = new FastScanner();
		int N = sc.nextInt();
		int[] dp = new int[N + 1];
		int[] bread = new int[N + 1];
		for(int i = 1; i <= N; i++) {
			bread[i] = sc.nextInt();
		}
		for(int i = 1; i <= N; i++) {
			for(int j = 0; j <= i; j++) {
				dp[i] = Math.max(dp[i], dp[i - j] + bread[j]);
			}
		}
		System.out.println(dp[N]);
	}

}
//class FastScanner {
//	BufferedReader br;
//	StringTokenizer st;
//
//	public FastScanner(String s) {
//
//		try {
//
//			br = new BufferedReader(new FileReader(s));
//		} catch (FileNotFoundException e) {
//			throw new RuntimeException(e);
//		}
//	}
//
//	public FastScanner() {
//		br = new BufferedReader(new InputStreamReader(System.in));
//
//	}
//
//	String nextToken() {
//		while (st == null || !st.hasMoreElements()) {
//			try {
//				st = new StringTokenizer(br.readLine());
//			} catch (IOException e) {
//				throw new RuntimeException(e);
//			}
//		}
//		return st.nextToken();
//	}
//
//	int nextInt() {
//
//		return Integer.parseInt(nextToken());
//	}
//
//	long nextLong() {
//		return Long.parseLong(nextToken());
//	}
//
//	double nextDouble() {
//		return Double.parseDouble(nextToken());
//	}
//
//	String nextLine() {
//		String str = "";
//		try {
//			str = br.readLine();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return str;
//	}
//
//}
//
