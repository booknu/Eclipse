package 다이나믹_프로그래밍;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/************
 * <주소>		: https://www.acmicpc.net/problem/2240
 * **********
 * <해결방안>	: 
 * 
 * 재귀 호출을 통해 해결
 * 각 초마다 현재 나무에 있을건지, 다른 나무로 갈건지를 모두 시도해 봄
 * 
 * **********
 * <오답노트>	: 
 * 
 * 1. 첫 번째 나무가 꼭 1이어야 하는줄 알고 재귀 처음 호출을 getMaxPlum(0, 0, 1)로만 했는데
 *    나중에 생각해보니 이동을 1 소모하고 2번 나무에서 시작할 수 있다는걸 알게 됨
 * 
 * **********
 * @author LENOVO
 *
 */
public class D02_자두나무{

	static int T, W, fallTree[];
	static int memo[][][];
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FastScanner sc = new FastScanner();
		T = sc.nextInt();
		W = sc.nextInt();
		fallTree = new int[T];
		for(int i = 0; i < T; i++) {
			fallTree[i] = sc.nextInt();
		}
		memo = new int[T][W + 1][3];
		
		System.out.println(Math.max(getMaxPlum(0, 0, 1), getMaxPlum(0, 1, 2)));
	}
	
	public static int getMaxPlum(int sec, int move, int tree) {
		if(sec == T) {
			return 0;
		}
		if(memo[sec][move][tree] != 0) {
			return memo[sec][move][tree];
		}
		
		int result = 0;
		if(move < W) {
			if(fallTree[sec] == tree) {
				result ++;
			}
			// tree ^ 0x3은 나무를 바꾸라는 뜻
			result += Math.max(getMaxPlum(sec + 1, move + 1, tree ^ 0x3), getMaxPlum(sec + 1, move, tree));
		} else {
			for(int i = sec; i < T; i++) {
				if(fallTree[i] == tree) {
					result++;
				}
			}
		}
		memo[sec][move][tree] = result;
		return result;
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
