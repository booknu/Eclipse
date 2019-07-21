package 다이나믹_프로그래밍;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/************
 * <주소>		: https://www.acmicpc.net/problem/10164
 * **********
 * <해결방안>	: 
 * 
 * 우선 K = 0일 경우 (꼭 지나야 할 곳이 없는 경우)는
 * N x M 의 사각형에서 (0, 0) ~ (M-1, N-1) 로 가는 경우의 수를 그냥 구하면 된다.
 * 
 * K != 0인 경우 (꼭 지나야 할 곳이 있는 경우)
 * K의 장소를 기준으로 사각형을 두 개로 나눈다.
 * 우선 K는 1부터 시작하므로 K - 1을 하고,
 * 그 뒤는 2차원 배열의 주소를 찾는 형식으로 하면 
 * 맨 처음 사각형의 크기가 구해진다.
 * 
 * 그 다음 사각형은 M과 N에서 그 전 사각형만큼 뺀 뒤 1을 더하면 된다.
 * 
 * 두 사각형의 경우의 수를 곱해 답을 구한다.
 * 
 * **********
 * <오답노트>	: 
 * 
 * - 꼭 지나야 할 지점이 있을 때
 *   사각형을 두 개로 나누는데 이것을 잘못 구해서 오류가 많이 남
 *   (firstH, W 구하는 것)
 * 
 * **********
 * @author LENOVO
 *
 */
public class F05_격자상의_경로 {

	static int dy[] = {-1, 0};
	static int dx[] = {0, -1};
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FastScanner sc = new FastScanner();
		int N = sc.nextInt(); // 높이
		int M = sc.nextInt(); // 길이
		int K = sc.nextInt();
		
		if(K == 0) {
			System.out.println(countWays(N, M));
		} else {
			int firstH = (K - 1) / M + 1;
			int firstW = (K - 1) % M + 1;
			int secondH = N - firstH + 1;
			int secondW = M - firstW + 1;
			int result = countWays(firstH, firstW) * countWays(secondH, secondW);
			System.out.println(result);
		}
		
		
	}

	/**
	 * x * y 크기의 직사각형 격자판에서
	 * (0, 0) ~ (x-1, y-1) 까지 갈 수 있는 경우의 수를 구한다.
	 * 
	 * @return
	 */
	public static int countWays(int h, int w) {
		// dp[y][x]: (y, x)까지 가는 경우의 수
		int[][] dp = new int[h][w];
		dp[0][0] = 1;		
		
		for(int y = 0; y < h; y++) {
			for(int x = 0; x < w; x++) {
				for(int direction = 0; direction < 2; direction++) {
					int ny = y + dy[direction];
					int nx = x + dx[direction];
					if(ny >= 0 && nx >= 0 && ny < h && nx < w) {
						dp[y][x] += dp[ny][nx];
					}
				}
			}
		}
		
		return dp[h - 1][w - 1];
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
