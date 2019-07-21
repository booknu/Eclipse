package 다이나믹_프로그래밍;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/************
 * <주소>		: https://www.acmicpc.net/problem/2169
 * **********
 * <해결방안>	: 
 * 
 * **********
 * <오답노트>	: 
 * 
 * 1. 재귀를 통한 풀이로 시간 초과
 * 
 * **********
 * @author LENOVO
 *
 */
public class C02_로봇_조종하기_recur_시간초과 {

	static int INF = -1000000000;

	static int dy[] = {0, 0, 1};
	static int dx[] = {-1, 1, 0};

	static int N, M, map[][];
	static int memo[][][];

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FastScanner sc = new FastScanner();
		N = sc.nextInt();
		M = sc.nextInt();
		map = new int[N][M];
		memo = new int[N][M][3];
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < M; j++) {
				map[i][j] = sc.nextInt();
			}
		}
		for(int[][] arr2D : memo) {
			for(int[] arr : arr2D) {
				Arrays.fill(arr, -1);
			}
		}
		System.out.println(maxValue(0, 0, 2) + map[0][0]);
 	}

	public static int maxValue(int y, int x, int prevDir) {
		if(memo[y][x][prevDir] != -1) {
			return memo[y][x][prevDir];
		}
		if(y == N - 1 && x == M - 1) {
			return 0;
		}

		int max = INF;
		for(int direction = 0; direction < 3; direction++) {
			// 오른쪽으로 왔다가 왼쪽, 왼쪽으로 왔다가 오른쪽으로 갈 수 없음
			// 왼 = 00 오른 = 01 아래 = 10  ==>  오른 ^ 왼 = 1
			if((prevDir ^ direction) == 1) {
				continue;
			}
			int ny = y + dy[direction];
			int nx = x + dx[direction];
			if(ny >= 0 && ny < N && nx >= 0 && nx < M) {
				max = Math.max(maxValue(ny, nx, direction) + map[ny][nx], max);
			}
		}
		memo[y][x][prevDir] = max;
		return memo[y][x][prevDir];
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
