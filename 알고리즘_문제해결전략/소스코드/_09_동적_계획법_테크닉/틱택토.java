package _09_동적_계획법_테크닉;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/************
 * <주소>		: p337 틱택토
 * **********
 * <해결방안>	: 
 * 
 * 1. [[ board의 표현 방식 ]]
 *    3진수의 자리수 9개를 사용한 int를 사용한다.
 *    밑의 getArray, setArray는 이 board에 접근하기 위해 만들었다.
 *
 *    
 * 2. [[ 입력 ]]
 *    입력을 받을 때 setArray를 활용해 각 문자에 해당하는 숫자를
 *    int 3진수 배열에 채워넣었다.
 *    이 때 o, x가 총 몇 번 나왔는지도 세어서
 *    그 다음 수를 누가 둘 것인지도 알아내었다.
 *    
 *    
 * 3. [[ isFinished(turn, board) 메소드 ]]
 *    현재 board 상황에서 turn이 이겼는지, 아니면 아직 끝나지 않았는지를
 *    판단한다.
 *    각 row, col, 대각선을 검사하여 하나라도 완성된 turn의 줄이 있는지 확인하고
 *    없다면 false를 반환한다.
 *    
 *    
 * 4. [[ canWin(board, turn) 메소드 ]]
 *    현재 board 상황에서 앞으로 계속 두 사람이 번갈아가며 최선을 다해 두었을 때
 *    현재 turn의 사람이 이길 수 있는지 여부를 반환한다.
 *    -1: lose, 0: draw, 1: win
 *    
 *    우선 현재 board에서 이전 사람이 마지막으로 둔 수가 게임을 끝나게 했는지 확인해
 *    만약 그렇다면 현재 사람은 LOSE한 것이고, 아니라면 다시 여러 조합을 만들어봐야 한다.
 *    
 *    보드가 꽉 차있는지 확인용으로도 쓰이는 minValue = 2로 선언하고
 *    남은 자리에 각각 두어보면서 하나라도 lose가 있으면 -1, 그건 아니고
 *    하나라도 draw가 있으면 0, 둘 다 없고 win만 있으면 1로 minValue 값을 바꾼다.
 *    만약 남은 자리가 하나도 없었다면 minValue는 바뀌지 않고 2를 유지했을 것이다.
 *    따라서 minVal = 0, 2면 비긴 것이고, 다른 경우는 그대로 승부가 난 것이다.
 * 
 * **********
 * <오답노트>	: 
 * 
 * 1. 처음에는 함수의 인자로 board, turn, win을 주어
 *    함수 호출시 이미 게임이 끝났는지를 확인해
 *    게임이 끝났는지를 확인하는 작업의 수를 줄이려고 했으나,
 *    코드가 복잡해질 것 같아서 재귀호출 때마다 모든 자리를 확인하는 것으로
 *    바꿈
 * 
 * 
 * 2. 처음에는 return 값을 0: 비김, 1: x 이김, 2: o 이김으로 하려고 했으나,
 *    가능한 모든 수를 모두 시도해보며 현재 상태가 어떤지 찾을 때
 *    minValue = 2로 놓고 -1 = lose, 0 = draw, 1 = win을 return한다면
 *    각각 수를 시도해보며 하나라도 lose가 있으면 miValue = -1,
 *    하나라도 draw가 있으면 minValue = 0, 둘 다 없는데 win만 있으면
 *    minValue = 1, 판을 모두 채워 minValue가 변하지 않으면 minValue = 2로 두어
 *    일일이 이겼는지 비겼는지를 확인하는 코드가 없어도 min하나면
 *    판별할 수 있어서 코드가 짧아진다는 것을 깨닫고 코드를 바꿈
 *
 * **********
 * @author LENOVO
 *
 */
public class 틱택토 {

	public static final int LOSE = -1, DRAW = 0, WIN = 1;
	public static final int EMPTY = 0, X = 1, O = 2;
	public static final int SIZE = 3;
	public static final int[][] CHECK_LIST = 
			{{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6},
            {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};
	
	public static int board; // masked by trinary
	public static int pow3[] = new int[11];
	
	public static int memo[];
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		FastScanner sc = new FastScanner();
		setPow3();
		memo = new int[pow3[9]];
		Arrays.fill(memo, -2);
		
		int T = sc.nextInt();
		for(int testCase = 0; testCase < T; testCase++) {
			board = 0;
			int numX = 0;
			int numO = 0;
			for(int i = 0; i < SIZE; i++) {
				String input = sc.nextToken();
				for(int j = 0; j < SIZE; j++) {
					int value;
					switch(input.charAt(j)) {
						case '.': value = EMPTY; break;
						case 'x': value = X; numX++; break;
						case 'o': value = O; numO++; break;
						default: throw new Exception("input error (" + input.charAt(j) + ")");
					}
					board = setArray(i, j, value, board);
				}
			}
			
			int firstTurn = numX <= numO? X : O;
			int res = canWin(board, firstTurn);
			if(res == 0) {
				// draw
				System.out.println("TIE");
			} else if(res == 1) {
				// firstTurn's win
				System.out.println(firstTurn == X? "x" : "o");
			} else {
				// firstTurn's lose
				System.out.println(firstTurn == X? "o" : "x");
			}
		}
	}
	
	/**
	 * is current turn player wins?
	 * 
	 * @param board board state
	 * @param turn who's turn?
	 * @return -1: lose,  0: draw,  1: win
	 */
	public static int canWin(int board, int turn) {
		// The game is decided by previous player
		if(isFinished((turn ^ 3), board)){
			// The game is already finished, so this turn's player is defeated
			// if player 'x' loses return -1
			return LOSE;
		}
		if(memo[board] != -2) {
			return memo[board];
		}
		
		int minValue = 2;
		for(int i = 0; i < SIZE; i++) {
			for(int j = 0; j < SIZE; j++) {
				// if (i, j) is empty
				if(getArray(i, j, board) == EMPTY) {
					// set (i, j) with current turn
					minValue = Math.min(minValue
							, canWin(setArray(i, j, turn, board)
							, (turn ^ 3)));
				}
			}
		}
		// if all of the board has been occupied or all combination is draw
		if(minValue == 2 || minValue == DRAW) {
			memo[board] = DRAW;
			return DRAW;
		}
		// next player's win is current player's lose
		memo[board] = -minValue;
		return -minValue;
	}	
	
	/**
	 * is game already finished by turn's win?  
	 * 
	 * @param turn player's turn
	 * @param board board status
	 * @return true: turn's win,  false: not finished
	 */
	public static boolean isFinished(int turn, int board) {
		// check all of the row, col, diagonal
		for(int i = 0; i < CHECK_LIST.length; i++) {
			boolean finished = true;
			for(int j = 0; j < SIZE; j++) {
				if(getDigitOfTri(CHECK_LIST[i][j], board) != turn) {
					finished = false;
				}
			}
			if(finished) return true;
		}

		// if 'turn' doesn't win, it is not finished
		return false;
	}
	
	public static void printBoard(int board) {
		for(int i = 0; i < SIZE; i++) {
			for(int j = 0; j < SIZE; j++) {
				switch(getArray(i, j, board)) {
					case EMPTY: System.out.print("."); break;
					case X: System.out.print("x"); break;
					case O: System.out.print("o"); break;
				}
			}
			System.out.println();
		}
	}
	
	public static int getArray(int y, int x, int trinary) {
		return getDigitOfTri(y * SIZE + x, trinary);
	}
	
	public static int setArray(int y, int x, int value, int trinary) {
		return setDigitOfTri(y * SIZE + x, value, trinary);
	}
	
	/**
	 * get nth digit of trinary
	 * 
	 * @param n digit
	 * @param trinary 
	 * @return nth digit
	 */
	public static int getDigitOfTri(int n, int trinary) {
		return (trinary % pow3[n + 1]) / pow3[n];
	}
	
	/**
	 * set nth digit of trinary to value
	 * 
	 * @param n digit
	 * @param value replace value
	 * @param trinary number
	 * @return replaced trinary
	 */
	public static int setDigitOfTri(int n, int value, int trinary) {
		int prev = getDigitOfTri(n, trinary);
		return trinary + (value - prev) * pow3[n];
	}
	
	public static void setPow3() {
		pow3[0] = 1;
		for(int i = 1; i < pow3.length; i++) {
			pow3[i] = pow3[i-1] * 3;
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
