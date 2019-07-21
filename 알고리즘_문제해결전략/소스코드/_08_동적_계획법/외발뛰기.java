package _08_동적_계획법;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/************
 * <주소>		: p215
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
public class 외발뛰기 {

	static int[] dy = {0, 1};
	static int[] dx = {1, 0};
	
	static int[][] memo;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FastScanner sc = new FastScanner();
		int C = sc.nextInt();
		for (int testCase = 0; testCase < C; testCase++) {
			int N = sc.nextInt();
			int[][] board = new int[N][N];
			for(int i = 0; i < N; i++) {
				for(int j = 0; j < N; j++) {
					board[i][j] = sc.nextInt();
				}
			}
			
			System.out.println(solveByLoop(N, board)? "YES" : "NO");
		}
	}
	
	/**
	 * 재귀로 해결
	 * 
	 * @param N
	 * @param board
	 * @param y
	 * @param x
	 * @return y, x에서 맨 마지막 칸까지 갈 수 있는지
	 */
	public static boolean solveByRecur(int N, int[][] board, int y, int x) {
		// 기저 사례
		if(y >= N || x >= N) {
			return false;
		}
		if(y == N-1 && x == N-1) {
			return true;
		}
		
		// 메모이제이션
		if(memo[y][x] != 0) {
			return memo[y][x] == 1;
		}
		
		int jump = board[y][x];
		boolean ret =
				solveByRecur(N, board, y + jump, x) || solveByRecur(N, board, y, x + jump);
		memo[y][x] = ret? 1 : 2;
		return ret;
	}
	/**
	 * 반복문으로 해결
	 * O(N^2)
	 * 
	 * @param N
	 * @param board
	 * @return
	 */
	public static boolean solveByLoop(int N, int[][] board) {
		boolean[][] dp = new boolean[N][N];
		dp[0][0] = true;
		for(int y = 0; y < N; y++) {
			for(int x = 0; x < N; x++) {
				if(dp[y][x]) {
					for(int direction = 0; direction < 2; direction++) {
						int ny = y + dy[direction] * board[y][x];
						int nx = x + dx[direction] * board[y][x];
						if(ny < N && nx < N) {
							dp[ny][nx] = true;
						}
					}
				}
			}
		}
		return dp[N-1][N-1];
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
