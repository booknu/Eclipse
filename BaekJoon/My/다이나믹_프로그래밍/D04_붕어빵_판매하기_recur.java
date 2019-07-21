package 다이나믹_프로그래밍;

/************
 * <주소>		: https://www.acmicpc.net/problem/11052
 * **********
 * <해결방안>	: 
 * 
 * - 재귀를 이용함
 * - N이 남아있는 빵 수라고 할 때
 *   F(N) = F(N - 1~N) + bread[1~N] 으로
 *   brute-force로 현재 남아있는 빵 수에서 가능한 경우를 하나하나 해보면서 max를 구함
 * 
 * **********
 * <오답노트>	: 
 * 
 * - 해결방안을 쓰던 도중 반복문으로 해결할 수 있는 방법을 찾아냄
 * **********
 * @author LENOVO
 *
 */
public class D04_붕어빵_판매하기_recur {

	static int N, P[];
	static int memo[];
	static final int INF = -1000000000;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FastScanner sc = new FastScanner();
		N = sc.nextInt();
		P = new int[N + 1];
		memo = new int[N + 1];
		for(int i = 1; i <= N; i++)
			P[i] = sc.nextInt();
		
		
		
		System.out.println(func(N));
	}

	public static int func(int remain) {
		if(memo[remain] != 0) {
			return memo[remain];
		}
		if(remain < 0) {
			return INF;
		}
		if(remain == 0) {
			return 0;
		}
			
		
		int max = 0;
		for(int i = 1; i <= remain; i++) {
			max = Math.max(max, func(remain - i) + P[i]);
		}
		memo[remain] = max;
		return max;
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
