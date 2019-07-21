package 백트래킹2_줄_Queue;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/************
 * <주소>		:
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
public class __Form {

	public static long[][] bino;
	public static long perm[];
	public static long[][] memo;
	public static int BINO_SIZE;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FastScanner sc = new FastScanner();
		
		int N = sc.nextInt();
		int P = sc.nextInt();
		int R = sc.nextInt();
		
		BINO_SIZE = N+1;
		setBino();
		setPerm();
		
		memo = new long[N][N];
		for(int i = 0; i < memo.length; i++) {
			Arrays.fill(memo[i], -1);
		}
		
		/* visit fast ***************/
		System.out.println("----visit fast----");
		long ans = 0;
		long executeTime = System.currentTimeMillis();
		// i: 왼쪽에 배치할 사람의 수
		for(int i = P - 1; i <= N - R; i++) {
			ans += visitFast(i, P - 1) * visitFast(N - i - 1, R - 1) * bino[N-1][i];
		}
		System.out.println("ans: " + ans);
		executeTime = System.currentTimeMillis() - executeTime;
		System.out.println("time: " + executeTime);
		
		/* visit slow ***************/
		System.out.println("----visit slow----");
		executeTime = System.currentTimeMillis();
		ans = 0;
		for(int i = P - 1; i < N - R + 1; i++) {
			ans += visitSlow(i, P-1, 0, 0, i) * visitSlow(N-i-1, R-1, 1, i+1, N) * bino[N-1][i];
		}
		System.out.println("ans: " + ans);
		executeTime = System.currentTimeMillis() - executeTime;
		System.out.println("time: " + executeTime);
			System.out.println("==================");
	}
	
	/**
	 * 메모이제이션을 사용할 수 있는 수정된 방법을 사용하면
	 * 시간초과X
	 * 단, n이 10000에 가까우면 메모리 초과는 뜰 수 있음
	 * 
	 * n이 2500만 되도 시간은 10초 가까이 걸림
	 * n이 3000정도 되면 메모리초과
	 * 
	 * @param n 남아있는 사람 수
	 * @param k 보여야하는 사람 수
	 * @return 가능한 조합 수
	 */
	public static long visitFast(int n, int k) {
		long ret = 0;
		if(k == 0) {
			if(n == 0) {
				return 1;
			} else {
				return 0;
			}
		}
		
		if(memo[n][k] != -1) {
			return memo[n][k];
		}
		
		// i: max보다 왼쪽 or 오른쪽에 있을 수
		for(int i = k - 1; i < n; i++) {
			ret += visitFast(i, k - 1) * bino[n-1][i] * perm[n - i - 1];
		}
		memo[n][k] = ret;
		return ret;
	}
	
	/**
	 * 원래 방법대로 하면 시간 초과 뜸
	 * 
	 * P, R이 커질수록 시간이 기하급수적으로 늘어나고,
	 * n = 250, P = 4, R = 4 같이 P, R이 작을 때도
	 * 1.5초가 걸릴 정도로 느림
	 * 
	 * @param n
	 * @param k
	 * @param s
	 * @param x
	 * @param y
	 * @return
	 */
	public static long visitSlow(int n, int k, int s, int x, int y) {
		long ret = 0;
		if(k == 0) {
			if(n == 0) {
				return 1;
			} else {
				return 0;
			}
		}
		
		if(s == 0) {
			for(int i = x + k - 1; i < y; i++) {
				ret += visitSlow(i - x, k - 1, 0, x, i) * bino[n-1][i-x] * perm[y-i-1];
			}
		} else {
			for(int i = x; i < y - k; i++) {
				ret += visitSlow(n - (i - x) - 1, k - 1, 1, i + 1, y) * bino[n-1][i-x] * perm[i-x];
			}
		}
		return ret;
	}
	
	public static void setPerm() {
		perm = new long[BINO_SIZE];
		perm[0] = 1;
		for(int i = 1; i < BINO_SIZE; i++) {
			perm[i] = perm[i - 1] * i;
		}
	}
	
	public static void setBino() {
		int max = 1000000000 + 100;
		bino = new long[BINO_SIZE][BINO_SIZE];
		for(int n = 0; n < BINO_SIZE; n++) {
			bino[n][0] = bino[n][n] = 1;
			for(int k = 1; k < n; k++) {
				bino[n][k] = Math.min(bino[n-1][k-1] + bino[n-1][k], max);
			}
		}
		
//		for(int n = 0; n < 10; n++) {
//			for(int k = 0; k <= n; k++) {
//				System.out.print(bino[n][k] + " ");
//			}
//			System.out.println();
//		}
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
