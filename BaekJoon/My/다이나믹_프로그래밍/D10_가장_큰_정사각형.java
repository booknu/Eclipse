package 다이나믹_프로그래밍;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/************
 * <주소>		: https://www.acmicpc.net/problem/1915
 * **********
 * <해결방안>	: 
 * 
 * - 질문 검색에서 아이디어를 얻음
 * 
 * - 맨 오른쪽 하단 (x, y)가 1이고
 *   그곳에서 왼쪽, 오른쪽, 대각선위가 1이면
 *   그것을 포함시켜도 정사각형이다.
 *   
 * - 따라서 board에서 모든 (x, y)를 검사하는데,
 *   dp[y][x] = 오른쪽 하단이 (x, y)인 정사각형 중 가장 큰 변의 길이
 *   
 *   py, px가 왼쪽, 오른쪽, 대각선 위라고 할 때
 *   dp[y][x] = min( dp[py][px] ) + 1
 *   왜냐하면 그 중 (위, 오른, 대각) 가장 작은 경우는 나머지가 모두 포함하기 때문 
 *    
 * **********
 * <오답노트>	: 
 * 
 * (설명 사진 참조)
 * 
 * - 처음에는 설명 사진에 나와 있는 것처럼
 *   dp[y-1][x-1]을 검사하고 그 양 옆이 모두 1이면
 *   dp[y][x] = dp[y-1][x-1] + 1 이라고 생각했음
 *   
 *   하지만 두 정사각형이 일부분만 겹치는 경우에 버그가 발생함
 * 
 * **********
 * @author LENOVO
 *
 */
public class D10_가장_큰_정사각형 {

	static final int INF = 1000001;
	
	static int[] dy = {0, -1, -1};
	static int[] dx = {-1, 0, -1};
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FastScanner sc = new FastScanner();
		int n = sc.nextInt();
		int m = sc.nextInt();
		boolean[][] board = new boolean[n][m];

		for(int i = 0; i < n; i++) {
			String line = sc.nextLine();
			for(int j = 0; j < m; j++) {
				board[i][j] = line.charAt(j) == '1';
			}
		}

		int max = 0;
		int[][] dp = new int[n][m];
		for(int i = 0; i < n; i++) {
			dp[i][0] = board[i][0]? 1 : 0;
			max = Math.max(max, dp[i][0]);
		}
		for(int j = 0; j < m; j++) {
			dp[0][j] = board[0][j]? 1 : 0;
			max = Math.max(max, dp[0][j]);
		}
		
		for(int y = 1; y < n; y++) {
			for(int x = 1; x < m; x++) {
				if(board[y][x]) {
					int min = INF;
					for(int direction = 0; direction < 3; direction++) {
						int py = y + dy[direction];
						int px = x + dx[direction];
						min = Math.min(min, dp[py][px]);
					}
					dp[y][x] = min + 1;
					max = Math.max(max, dp[y][x]);
				}
			}
		}
		
//		for(int i = 0; i < n; i++) {
//			for(int j = 0; j < m; j++) {
//				System.out.print(dp[i][j] + " ");
//			}
//			System.out.println();
//		}
		
		System.out.print(max * max);
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
