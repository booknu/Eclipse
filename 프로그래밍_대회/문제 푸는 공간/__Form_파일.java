import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
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
public class __Form_파일 {
	static final int MAXN = 20001, MAXM = 50002;
	static int N, K;
	static long A[], psum[];
	static BigDecimal dp[];
	static long sum(int s, int e) {
		if(s > e) return 0;
		if(s == 0) return psum[e];
		return psum[e] - psum[s - 1];
	}
	// A[i-1] <= x < A[i] 인 i를 반환
	static int upper_bound(long A[], long x) {
		int lo = -1, hi = A.length;
		while(lo + 1 < hi) {
			int mid = (lo + hi) / 2;
			if(x < A[mid]) hi = mid; 
			else  lo = mid;
		}
		return hi;
	}
	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		FastScanner cs= new FastScanner("D:\\다운로드\\_______IO\\input.txt");
		PrintWriter out = new PrintWriter(new File("D:\\다운로드\\_______IO\\out.txt"));
		/////////////// code start
		int TT = cs.nextInt();
		for(int tt = 1; tt <= TT; ++tt) {
			System.out.println("tc " + tt);
			N = cs.nextInt(); K = cs.nextInt();
			A = new long[N];
			psum = new long[N];
			for(int i = 0; i < N; ++i) A[i] = cs.nextInt();
			Arrays.sort(A);
			for(int i = 0; i < N; ++i) psum[i] += A[i] + (i != 0? psum[i-1] : 0);
			dp = new BigDecimal[K + 10];
			for(int i = 0; i < dp.length; ++i) dp[i] = new BigDecimal(0);
			for(int i = 1; i < K + 2; ++i) {
				int k = upper_bound(A, dp[i-1].longValue());
				BigDecimal bigrat = new BigDecimal(1.0), smarat = new BigDecimal(1.0);
				bigrat = bigrat.multiply(new BigDecimal(N - k)).divide(new BigDecimal(N), 13, RoundingMode.HALF_UP);
				smarat = smarat.multiply(new BigDecimal(k)).divide(new BigDecimal(N), 13, RoundingMode.HALF_UP);
				if(k != N) dp[i] = dp[i].add(bigrat.multiply(new BigDecimal(sum(k, N-1))).divide(new BigDecimal(N - k), 13, RoundingMode.HALF_UP));
				dp[i] = dp[i].add(dp[i-1].multiply(smarat));
				dp[i] = dp[i].setScale(7, RoundingMode.HALF_UP);
			}
			System.out.println(dp[K+1]);
			out.println("Case #" + tt + ": " + dp[K+1]);
		}
		/////////////// code end
		cs.close();
		out.close();
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
		void close() {
			try {
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
