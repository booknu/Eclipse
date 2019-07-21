package 그래프_알고리즘;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

/************
 * <주소>		: https://www.acmicpc.net/source/4835489
 * **********
 * <해결방안>	: 
 * 1. 연결 성분을 찾는 문제와 같음.
 * 2. 1로 표시된 점을 잡고 BFS를 하면서 연결된 부분은 모두 0으로 바꾸고,
 *    그게 하나의 섬이니까 ans++을 함.
 * 3. 2번 과정을 모든 Node에 대해서 시작해봄
 * 
 * **********
 * <오답노트>	: 
 * 1. 실수로 while문에서 BFS를 할 때 현재 Node를 q에서 뽑지 않고]]
 *    그냥 y, x를 갖다 씀. (무한 루프)
 *    ==> curr을 추가해 사용해서 해결
 * 
 * 
 * **********
 * @author LENOVO
 *
 */
public class F01_섬의_개수 {

	static final int dy[] = {-1, -1, 0, 1, 1, 1, 0, -1};
	static final int dx[] = {0, 1, 1, 1, 0, -1, -1, -1};
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FastScanner sc = new FastScanner();
		while(true) {
			int W = sc.nextInt();
			int H = sc.nextInt();
			if(W == 0 && H == 0) {
				break;
			}
			int[][] map = new int[H][W];
			for(int i = 0; i < H; i++) {
				for(int j = 0; j < W; j++) {
					map[i][j] = sc.nextInt();
				}
			}
			
			int ans = 0;
			for(int y = 0; y < H; y++) {
				for(int x = 0; x < W; x++) {
					if(map[y][x] == 1) {
						Queue<Pos> q = new LinkedList<Pos>();
						q.offer(new Pos(y, x));
						map[y][x] = 0;
						while(!q.isEmpty()) {
							Pos curr = q.poll();
							for(int direct = 0; direct < 8; direct++) {
								int ny = curr.y + dy[direct];
								int nx = curr.x + dx[direct];
								if(ny >= 0 && nx >= 0 && ny < H && nx < W) {
									if(map[ny][nx] == 1) {
										q.offer(new Pos(ny, nx));
										map[ny][nx] = 0;
									}
								}
							}
						}
						ans++;
					}
				}
			}
			System.out.println(ans);
		}
	}
	
	static class Pos {
		int y, x;
		public Pos(int y, int x) {
			this.y = y;
			this.x = x;
		}
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
