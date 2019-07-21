package 다이나믹_프로그래밍;
/************
 * <주소>		: https://www.acmicpc.net/problem/11048
 * **********
 * <해결방안>	: 
 * 
 * (아이디어)
 * 
 * - 움직임에 제한이 있어 (오른쪽, 아래, 대각선아래) 지난 곳을 지나갈 수 없고
 *   아래쪽으로 내려갈 수 밖에 없음
 * 
 * - 따라서 dp[][]를 생성해 현재 위치(x, y)에서 목적지까지 갈 때 얻을 수 있는
 *   최대 캔디 개수를 저장함
 * 
 * - dp를 목적지에서부터 거슬러 올라가며 계산하는데, 
 *   왼쪽 <-- 오른쪽 위 <-- 아래    순서대로 거슬러 올라감.
 *   dp[y][x] 에서 갈 수 있는 모든 곳을 조사하는데
 *   다음에 갈 수 있는 곳들 중 가장 캔디를 많이 얻을 수 있는 곳을 선택한다.
 *   
 *   ==> dp[y][x] = max(dp[dy][dx] + candy[y][x])
 * 
 * **********
 * <오답노트>	: 
 * 
 * - 갈 수 있는지 없는지를 검사할 때 nextX < N 으로 변수 이름을 잘못 적음
 * 
 * **********
 * @author LENOVO
 *
 */
public class D03_이동하기 {

	static int[] dx = {0, 1, 1};
	static int[] dy = {1, 0, 1};
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		FastScanner sc = new FastScanner();
		
		int N = sc.nextInt();
		int M = sc.nextInt();
		int[][] maze = new int[N][M];
		
		for(int i = 0; i < N; i++) {
			for(int j = 0; j < M; j++) {
				maze[i][j] = sc.nextInt();
			}
		}
		
		int[][] dp = new int[N][M];
		for(int y = N - 1; y >= 0; y--) {
			for(int x = M - 1; x >= 0; x--) {
				for(int direction = 0; direction < 3; direction++) {
					int nextY = y + dy[direction], nextX = x + dx[direction];
					// 갈 수 있는 곳이면
					if(nextY >= 0 && nextX >= 0 && nextY < N && nextX < M) {
						dp[y][x] = Math.max(dp[y][x], dp[nextY][nextX]);
					}
				}
				dp[y][x] += maze[y][x];
			}
		}
	
		System.out.println(dp[0][0]);
	}

	public static void test(int[][] dp) {
		for(int i = 0; i < dp.length; i++) {
			for(int j = 0; j < dp[0].length; j++) {
				System.out.print(dp[i][j] + " ");
			}
			System.out.println();
		}
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

