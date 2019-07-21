package 다이나믹_프로그래밍;
import java.util.Arrays;

/************
 * <주소>		: https://www.acmicpc.net/problem/1520
 * **********
 * <해결방안>	: 
 * 
 * - 반복문으로 해결해보려 했으나 아이디어가 떠오르지 않아 재귀로 함
 * 
 * - (0, 0)에서부터 시작해 해당 지점 (x, y)에서
 *   상, 하, 좌, 우로 갈 수 있는지 확인하고
 *   갈 수 있으면 그 곳에서의 갈 수 있는 길의 수의 합을 구한다.
 *   
 *   F(Origin) = Sum(F(이동한지점));
 * 
 * **********
 * <오답노트>	: 
 * 
 * **********
 * @author LENOVO
 *
 */
public class F03_내리막_길 {

	static int M, N;
	static int[][] board;

	static int memo[][];
	
	static int[] dx = {0, 1, 0, -1};
	static int[] dy = {1, 0, -1, 0};
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		FastScanner sc = new FastScanner();
		M = sc.nextInt();
		N = sc.nextInt();
		board = new int[M][N];
		memo = new int[M][N];
		for(int[] arr : memo) {
			Arrays.fill(arr, -1);
		}
		for(int i = 0; i < M; i++) {
			for(int j = 0; j < N; j++) {
				board[i][j] = sc.nextInt();
			}
		}

		System.out.println(countWays(0, 0));
	}

	public static int countWays(int y, int x) {
		// 마지막까지 온 경우
		if(y == M - 1 && x == N - 1) return 1;
		// 메모이제이션
		if(memo[y][x] != -1) return memo[y][x];
			
		int ret = 0;
		for(int direction = 0; direction < 4; direction++) {
			int nextY = y + dy[direction], nextX = x + dx[direction];
			if(nextY >= 0 && nextX >= 0 && nextY < M && nextX < N) {
				if(board[y][x] > board[nextY][nextX]) {
					ret += countWays(nextY, nextX);
				}
			}
		}
		memo[y][x] = ret;
		return ret;
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