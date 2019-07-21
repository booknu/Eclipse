package _08_동적_계획법;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/************
 * <주소>		: p236
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
public class JLIS {

	static int N, M, A[], B[];
	static int memo[][];
	static int INF = Integer.MIN_VALUE;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FastScanner sc = new FastScanner();
		int T = sc.nextInt();
		for (int testCase = 0; testCase < T; testCase++) {
			N = sc.nextInt();
			M = sc.nextInt();
			A = new int[N];
			B = new int[M];
			for(int i = 0; i < N; i++) {
				A[i] = sc.nextInt();
			}
			for(int i = 0; i < M; i++) {
				B[i] = sc.nextInt();
			}
			memo = new int[N + 1][M + 1];
			for(int[] arr : memo) {
				Arrays.fill(arr, -1);
			}
			System.out.println(solve(-1, -1) - 2);
		}
	}
	
	public static int solve(int iA, int iB) {
		// 메모이제이션
		if(memo[iA + 1][iB + 1] != -1) {
			return memo[iA + 1][iB + 1];
		}

		int ret = 2;
		int a = iA == -1? INF : A[iA];
		int b = iB == -1? INF : B[iB];
		int max = Math.max(a, b);
		for(int nA = iA + 1; nA < N; nA++) {
			if(max < A[nA]) {
				ret = Math.max(ret, solve(nA, iB) + 1);
			}
		}
		for(int nB = iB + 1; nB < M; nB++) {
			if(max < B[nB]) {
				ret = Math.max(ret, solve(iA, nB) + 1);
			}
		}
		memo[iA + 1][iB + 1] = ret;
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
