package _09_동적_계획법_테크닉;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/************
 * <주소>		: p340
 * **********
 * <해결방안>	: 
 * 
 * 1. [[ 아이디어 ]]
 *    play 메소드는 플레이어당 번갈아가며 해당 플레이어가 할 수 있는
 *    최고의 선택을 하게 해준다. (미니맥스 알고리즘)
 *    따라서 현재 플레이어는 행동을 하고 난 후 다음 턴에서의 최선의 값을
 *    뺸 값이 max가 되어야 (차이가 많이 나야) 최선의 선택이다.
 *    
 *    
 *    먼저 뽑는 사람 = a라고 할 때
 *    a의 차례에서는 a - b를 최대화하려고 할 것이고,
 *    b의 차례에서는 a - b를 최소화하려고 할 것이다.
 *    이런 알고리즘을 구현할 때
 *    player에 따라 반환 값이 달라지고, state만 가지고는 이번 차례가 누군지
 *    알 수 없기 때문에 부분 문제의 수가 두 배로 늘어나게 된다.
 * 
 * 
 * 2. [[ play(left, right) 메소드 ]]
 *    board가 [left, right] 만큼 남았을 때
 *    현재 플레이어 점수 - 다른 플레이어 점수가 최대가 되는 값을 반환한다.
 *    (다른 플레이어도 현재 플레이어의 선택에 따라 최선의 플레이를 한다)
 *    
 *    행동 4가지 (left, right에서 1개 가져가기 or 2개 지우기)를 각각 해보고
 *    ret은 (현재 플레이어 - 다른 플레이어) 점수차 일 때
 *    해당 행동을 하고 난 후 얻은 점수에 (다른 플레이어 - 현재 플레이어)의 점수를
 *    빼면 행동을 하고 난 후 상대가 최선의 플레이를 했을 때의 점수차가 나오게 된다.
 *   
 *    
 * 3. [[ 주의사항 ]]
 *    앞의 "틱택토" 문제에서도 그렇고 현재의 문제에서도 그렇고
 *    모두 "현재 플레이어"가 누군지는 별로 중요하지 않다.
 *    다만 현재 턴에서 현재 플레이어가 가지고 있는 승리 가중치
 *    ("틱택토" 에서는 win, lose, draw
 *     "숫자 게임" 에서는 현재플레이어 - 다른플레이어 점수차)
 *    가 중요할 뿐이다. 
 * 
 * **********
 * <오답노트>	: 
 * 
 * **********
 * @author LENOVO
 *
 */
public class 숫자_게임 {

	public static final int EMPTY = -987654321;
	
	public static int[] board;
	
	public static int[][] memo;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FastScanner sc = new FastScanner();
		int T = sc.nextInt();
		for(int testCase = 0; testCase < T; testCase++) {
			int N = sc.nextInt();
			board = new int[N];
			for(int i = 0; i < N; i++) {
				board[i] = sc.nextInt();
			}
			
			memo = new int[N][N];
			for(int i = 0; i < N; i++) {
				Arrays.fill(memo[i], EMPTY);
			}
			System.out.println(play(0, N - 1));
		}
	}
	
	/**
	 * get best case (curr - another = max)
	 * 
	 * @param left remaining number's index of start left
	 * @param right remaining number's index of start left
	 * @return current player's score - another player's score
	 */
	public static int play(int left, int right) {
		if(left > right) {
			return 0;
		}
		if(memo[left][right] != EMPTY) {
			return memo[left][right];
		}
		
		// case1: pick a number
		int ret = Math.max(board[left] - play(left + 1, right)
				, board[right] - play(left, right - 1));
		// case2: remove two number
		if(right - left + 1 >= 2) {
			ret = Math.max(ret, -play(left + 2, right));
			ret = Math.max(ret, -play(left, right - 2));
		}
		memo[left][right] = ret;
		return ret;
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
