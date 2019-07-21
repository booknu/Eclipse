package 다이나믹_프로그래밍_;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/************
 * <주소>		: https://www.acmicpc.net/problem/2666
 * **********
 * <해결방안>	: 
 * 
 * 나는 열려있는 문이 서로를 뛰어넘고 가면 안 된다고 생각했는데,
 * 이 코드에서는 단순하게 o1을 옮겨보거나, o2를 옮겨보거나 둘 중
 * 작은 값을 반환하는 식으로 짬.
 * 이 코드는 자동으로 서로를 뛰어넘고 가는 것을 비효율로 판단하기 때문에
 * 괜찮음
 * 
 * **********
 * <오답노트>	: 
 * 
 * **********
 * @author LENOVO
 *
 */
public class D10_벽장문의_이동 {

	static int N, M; // 벽장 수, 열고 싶은 벽장 수
	static int[] A; // 열고 싶은 벽장 순서
	static int[][][] memo;
	static boolean[][][] hit;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FastScanner sc = new FastScanner();
		N = sc.nextInt();
		int o1, o2; // 열려있는 문
		o1 = sc.nextInt();
		o2 = sc.nextInt();
		M = sc.nextInt();
		A = new int[M];
		for(int i = 0; i < M; i++) {
			A[i] = sc.nextInt();
		}
		memo = new int[M][N + 1][N + 1];
		System.out.println(recur(0, o1, o2));
	}
	
	public static int recur(int i, int o1, int o2) {
		// 기저 사례: 모든 벽장을 사용함
		if(i >= M) {
			return 0;
		}
		// 메모이제이션
		if(hit[i][o1][o2]) {
			return memo[i][o1][o2];
		}
		
		// o1과 o2를 각각 옮겨봄
		int c1 = recur(i + 1, A[i], o2) + Math.abs(A[i] - o1);
		int c2 = recur(i + 1, o1, A[i]) + Math.abs(A[i] - o2);
		hit[i][o1][o2] = true;
		return memo[i][o1][o2] = Math.min(c1, c2);
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
