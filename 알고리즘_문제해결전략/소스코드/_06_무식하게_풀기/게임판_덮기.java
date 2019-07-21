package _06_무식하게_풀기;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/************
 * <주소>		: 6.5 게임판 덮기
 * **********
 * <해결방안>	: 
 * 
 * 재귀를 활용해 할 수 있는 모든 방법으로 게임판을 덮는다.
 * 
 * set은 게임판을 덮는 기능을 하는데, 덮을 때는 대상 위치에 +1을 하여 만약
 * 덮을 수 없는 곳에 덮었다고 해도 나중에 지울 때 그 곳이 원래 덮을 수 없는 곳인지
 * 원래는 아무것도 없는 곳인지 구분할 수 있게 해준다.
 * 
 * **********
 * <오답노트>	: 
 * 
 * 
 * 
 * **********
 * @author LENOVO
 *
 */
public class 게임판_덮기 {

	static int H, W; // 세로, 가로

	static int[][][] coverType = {
			{ { 0, 0 }, { 0, 1 }, { 1, 0 } },	// ┌
			{ { 0, 0 }, { 0, 1 }, { 1, 1 } },	// ┐
			{ { 0, 0 }, { 1, 0 }, { 1, 1 } },	// └
			{ { 0, 0 }, { 1, 0 }, { 1, -1 } }	// ┘
	};

	public static void main(String[] args) throws NumberFormatException, IOException {
		// TODO Auto-generated method stub
		BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
		int C = Integer.parseInt(sc.readLine().trim());
		for(int testCase = 0; testCase < C; testCase++) {
			StringTokenizer st = new StringTokenizer(sc.readLine());
			H = Integer.parseInt(st.nextToken());
			W = Integer.parseInt(st.nextToken());
			st = null;
			
			int[][] board = new int[H][W]; // 보드 (0 = empty, 1 = covered)

			int emptyCount = 0;
			for(int i = 0; i < H; i++) {
				String line = sc.readLine().trim();
				for(int j = 0; j < W; j++) {
					board[i][j] = line.charAt(j) == '.'? 0 : 1;
					if(board[i][j] == 0) emptyCount++;
				}
			}

			if(emptyCount % 3 != 0) {
				System.out.println(0);
				continue;
			}
			
			System.out.println(countCover(board));
		}
	}
	
	/**
	 * 보드를 덮거나, 덮었던걸 지운다.
	 * 
	 * @param board 보드판
	 * @param y 현재 y좌표
	 * @param x 현재 x좌표
	 * @param type 덮을 방법 (coverType 4가지 중 하나)
	 * @param delta 1: 덮음, -1: 없앰
	 * @return 게임판으로 나가거나, 겹치거나, 검은 칸을 덮을 때 false
	 */
	public static boolean set(int[][] board, int y, int x, int type, int delta) {
		boolean ret = true;
		for(int i = 0; i < 3; i++) {
			int ny = y + coverType[type][i][0];
			int nx = x + coverType[type][i][1];
			// 범위를 벗어나면
			if(ny < 0 || nx < 0 || ny >= H || nx >= W) {
				ret = false;
			} else if((board[ny][nx] += delta) > 1){
				ret = false;
			}
		}
		return ret;
	}
	
	public static int countCover(int[][] board) {
		int y = -1;
		int x = -1;
		for(int i = 0; i < H; i++) {
			for(int j = 0; j < W; j++) {
				if(board[i][j] == 0) {
					y = i;
					x = j;
					break;
				}
			}
			if(y != -1) {
				break;
			}
		}
		
		// 모든 보드가 채워짐
		if(y == -1) {
			return 1;
		}
		
		int ret = 0;
		for(int type = 0; type < 4; type++) {
			if(set(board, y, x, type, 1)) {
				ret += countCover(board);
			}
			set(board, y, x, type, -1);
		}
		
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
//
