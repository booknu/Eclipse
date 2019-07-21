package _09_동적_계획법_테크닉;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

/************
 * <주소>		: p344 블록 게임
 * **********
 * <해결방안>	: 
 * 
 * 1. [[ 아이디어 ]]
 *    현재 판의 상태는 각 셀당 블록이 있고, 없고 두 가지 밖에 없으니
 *    2진수로 bitmasking을 하면 board의 상태를 표현할 수 있고
 *    메모이제이션도 적용할 수 있다.
 *    
 *    현재 판에서 다음 가능한 블록을 놓았을 때 다음 플레이어가 최선을
 *    다해 플레이해서 결과가 win인지, lost인지에 따라 현재 플레이어의
 *    승패는 그에 반대가 된다.
 *    모든 블록의 경우에 대해 다음 플레이어가 하나라도 lose이면, 현재
 *    플레이어는 무조건 이기는 방법이 존재하기 때문에 win이고,
 *    다음 플레이어가 모두 win이면 현재 플레이어는 이길 수 있는 방법이
 *    없기 때문에 lose이다.
 *    
 *    가능한 블록의 경우를 세기 위해 비어있는 판 위에 1개의 블록을 올릴 수 있는
 *    모든 가능한 경우를 moves에 저장해두었다.
 *    
 * 
 * 2. [[ setMoves() 메소드 ]]
 *    블록을 놓는 모든 경우의 수를 미리 저장해둔다.
 *    이것을 기반으로 다음 블록을 놓을 수 있는지 판별할 수 있고,
 *    실제로 그것을 놓은 다음 board 상태를 만드는데도 쓰일 수 있다.
 *    moves는 각 블록의 경우를 저장하는데, 이것도 마찬가지로 bitmask로 표현한다.
 *    
 *    우선 L자 모양의 블록의 경우를 생성하기 위해 2x2사각형의 경우를 모두 만든 후
 *    각 사각형에서 하나의 셀씩만을 빼면 4개의 L자 블록이 나온다.
 *    
 *    I자 모양의 블록의 경우를 생성하기 위해서는 가로 블록의 경우
 *    행은 1~5행까지 loop를 도는데, 열은 1~4행까지만 loop를 돌며
 *    현재 열과 다음 열을 묶어 하나의 I자 모양의 블록을 만든다.
 *    세로 블록의 경우는
 *    열은 1~5행까지 loop를 도는데, 행은 1~4행까지만 loop를 돌며
 *    현재 행과 다음 행을 묶어 하나의 I자 모양의 블록을 만든다.
 *    
 *    
 * 3. [[ canWin(board) 메소드 ]]
 *    현재 board 상태일 때 현재 플레이어가 이길 수 있는지를 반환한다.
 *    
 *    우선 가능한 모든 경우에 블록을 놓아보며 canWin을 재귀호출하고,
 *    하나라도 다음 플레이어가 lose하는 경우가 있다면 현재 플레이어는
 *    무조건 win 하는 경우가 있는 것이다.
 *    만약 모든 블록을 놓아봐도 다음 플레이어가 win하는 경우 밖에 없다면
 *    현재 플레이어는 lose 할 수 밖에 없다.
 *    또한 블록의 놓는 방법이 없다면 이미 판이 꽉 찬 상태라는 뜻이므로
 *    현재 플레이어는 이미 lose한 것이다.
 * 
 * **********
 * <오답노트>	: 
 * 
 * 답안 제출을 했는데 런타임 에러가 발생함.
 * 랜덤 케이스를 만들어 돌려봐도 왜 그런지 모르겠음.
 * 메모리를 너무 많이 사용해서 그런 것으로 추정
 * 
 * **********
 * @author LENOVO
 *
 */
public class 블록_게임_런타임에러 {

	public static final int SIZE = 5;
	
	public static ArrayList<Integer> moves; // all of the possibility to put the blocks on the board
											// each of array's cell means only one block is on the board
	public static int memo[];
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FastScanner sc = new FastScanner();
		memo = new int[(1 << 25)];
		setMoves();
		int T = sc.nextInt();
		for(int testCase = 0; testCase < T; testCase++) {
			int board = 0;
			for(int i = 0; i < SIZE; i++) {
				String row = sc.nextToken();
				for(int j = 0; j < SIZE; j++) {
					if(row.charAt(j) == '#')
					board = setArray(i, j, board);
				}
			}
			System.out.println(canWin(board)? "WINNING" : "LOSING");
		}
	}
	
	/**
	 * in this board status, can current player wins?
	 * 
	 * @param board board 
	 * @return can current player wins?
	 */
	public static boolean canWin(int board) {
		if(memo[board] != 0) {
			return memo[board] == 1;
		}
		boolean ret = false;
		for(int i = 0; i < moves.size(); i++) {
			// can place this block
			if((board & moves.get(i)) == 0) {
				// we has at least 1 case to win the game
				// next player will be defeated
				if(!canWin(board + moves.get(i))) {
					ret = true;
					break;
				}
			}
		}
		// if ret = false
		// every combination is lose
		// or the game already ended
		// so this player always defeated
		memo[board] = ret? 1 : 2;
		return ret;
	}
	
	public static void setMoves() {
		moves = new ArrayList<Integer>();
		// set L shaped blocks
		// bind 4 L blocks to 2x2 square, and just remove one cell in square
		for(int y = 0; y < 4; y++) {
			for(int x = 0; x < 4; x++) {
				int square = 0;
				int cells[] = new int[4];
				int cellInd = 0;
				for(int dy = 0; dy < 2; dy++) {
					for(int dx = 0; dx < 2; dx++) {
						cells[cellInd++] = setArray(y + dy, x + dx, 0);
					}
				}
				square += cells[0] + cells[1] + cells[2] + cells[3];
				for(int i = 0; i < 4; i++) {
					moves.add(square - cells[i]);
				}
			}
		}
		
		// set I shaped blocks
		for(int i = 0; i < 5; i++) {
			for(int j = 0; j < 4; j++) {
				// be careful for i, j was reversed
				moves.add(setArray(i, j, 0) + setArray(i, j+1, 0));
				moves.add(setArray(j, i, 0) + setArray(j+1, i, 0));
			}
		}
	}
	
	public static int setArray(int y, int x, int trinary) {
		return setBit(y * SIZE + x, trinary);
	}
	
	/**
	 * set mask bit to 1
	 * 
	 * @param n n bit
	 * @param mask mask
	 * @return changed mask
	 */
	public static int setBit(int n, int mask) {
		// set 1
		return mask | (1 << n);
	}

	/**
	 * get mask bit
	 * 
	 * @param n n bit
	 * @param mask mask
	 * @return true / false
	 */
	public static boolean getBit(int n, int mask) {
		return (mask & (1 << n)) > 0;
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
