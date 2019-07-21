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
 * dp[y][x][direct]
 * = y, x에서 이전에서 direct 방향으로 온 경우 최대값
 * 
 * direction은 
 * 0 = ↓,  1 = →,  2 = ←
 * 
 * y, x 지점까지 가는데 얻을 수 있는 최대 가치는
 * 그 위쪽 (y-1, x)
 * 그 왼쪽 (y, x-1) ==> 이 때, 간 곳은 다시 갈 수 없으므로 (y, x)에서 왼쪽으로 온 (y, x-1)값은 무시해야함
 * 그 오른쪽 (y, x+1) ==> 이것도 마찬가지로 위와 같이 처리함
 * 중 최대값에 map[y][x]를 더한 것과 같다.
 * 
 * 그런데 문제는, 그림 1과 같이 위쪽에서의 dp는 구해졌는데 왼쪽과 오른쪽은 안 구해졌을 경우이다.
 * 하지만 이런 문제는 dp[][][direction]을 구할 때 direction의 방향을 강제하는 것으로 해결 가능
 * 
 * 먼저 dp[y][x][위]는 무조건 구할 수 있다.
 * 따라서 이걸 먼저 하고,
 * 
 * dp[y][x][왼쪽]의 경우는 필요한 것이 dp[y][x-1][위], dp[y][x-1][왼쪽]이므로
 * 앞서 위를 먼저 구했고, 왼쪽은 -> 방향으로 진행하면 알아서 채워지므로 구할 수 있다.
 * 
 * dp[y][x][오른쪽]의 경우에는 필요한 것이 dp[y][x+1][위], dp[y][x+1][오른쪽]이므로
 * 앞서 위를 먼저 구했고, 왼쪽은 <- 방향으로 진행하면 채울 수 있다.
 * 
 * 따라서 결과는 dp[N-1][M-1]중 최대값을 출력하면 된다.
 * 
 * **********
 * <오답노트>	: 
 * 
 * 1. dy = -1이 아래쪽으로 가는줄 알고 실수를 함
 * 2. 재귀로 풀었더니 300 x 300 맵에서도 스택 오버플로 발생
 * 3. dp[y][x][2] ( <- 의 경우 )에서는 오른쪽에서 왼쪽으로 탐색해야 제대로된 결과가 나옴
 * 
 * **********
 * @author LENOVO
 *
 */
public class C02_로봇_조종하기 {

	static int INF = -1000000000;

	static int dy[] = {-1, 0, 0};
	static int dx[] = {0, -1, 1};

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FastScanner sc = new FastScanner();
		int N = sc.nextInt();
		int M = sc.nextInt();
		int[][] map = new int[N][M];
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < M; j++) {
				map[i][j] = sc.nextInt();
			}
		}

		int[][][] dp = new int[N][M][3];
		for(int[][] arr2D : dp) {
			for(int[] arr : arr2D) {
				Arrays.fill(arr, INF);
			}
		}
		dp[0][0][0] = map[0][0];
		dp[0][0][1] = map[0][0];
		dp[0][0][2] = map[0][0];
		for(int y = 0; y < N; y++) {
			for(int direction = 0; direction < 3; direction++) {
				int py, px;
				// dp[][][오른쪽]을 찾아야 하는 경우
				if(direction == 2) {
					for(int x = M - 1; x >= 0; x--) {
						if(x == 0 && y == 0) {
							continue;
						}
						py = y + dy[direction];
						px = x + dx[direction];
						int max = INF;
						if(py >= 0 && py < N && px >= 0 && px < M) {
							for(int prevDirect = 0; prevDirect < 3; prevDirect++) {
								// L = 01, R = 10 이므로 L ^ R == 3이 되는데
								// 이 경우 간 곳을 또 가는 것이므로 불가
								if((prevDirect ^ direction) == 3) {
									continue;
								}
								max = Math.max(dp[py][px][prevDirect], max);
							}
							max += map[y][x];
						}
						dp[y][x][direction] = max;
					}
				} else {
					for(int x = 0; x < M; x++) {
						if(x == 0 && y == 0) {
							continue;
						}
						py = y + dy[direction];
						px = x + dx[direction];
						int max = INF;
						if(py >= 0 && py < N && px >= 0 && px < M) {
							for(int dir = 0; dir < 3; dir++) {
								if((dir ^ direction) == 3) {
									continue;
								}
								max = Math.max(dp[py][px][dir], max);
							}
							max += map[y][x];
						}
						dp[y][x][direction] = max;
					}
				}
			}
		}
//		for(int i = 0; i < N; i++) {
//			for(int j = 0; j < M; j++) {
//				System.out.print("( ");
//				for(int k = 0; k < 3; k++) {
//					System.out.print(dp[i][j][k] + " ");
//				}
//				System.out.print(")");
//			}
//			System.out.println();
//		}
		System.out.println(Math.max(dp[N-1][M-1][0], dp[N-1][M-1][1]));
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
