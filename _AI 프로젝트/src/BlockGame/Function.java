package BlockGame;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * 첫 수를 찾는데 너무 오래걸리면 
 * bestChoice의 bestWin을 찾았을 때 break를 넣을 것
 * 단, 이렇게 되면 WinCount를 세지 못해 이 수를 뒀을 때 상대방의 다음 수에서
 * 무조건 이기는 수를 세지 못해 그 수를 최소화하는 방면으로 수를 두지 못하게 됨
 * 
 * @author LENOVO
 *
 */
public class Function {

	public static final int SIZE = 5;
	
	public static ArrayList<Integer> moves; // all of the possibility to put the blocks on the board
											// each of array's cell means only one block is on the board
	
	public static Choice memo[];
	
	public static void initialize() {
		memo = new Choice[(1 << 25)];
		setMoves();
	}
	
	/**
	 * in this board status, can current player wins?
	 * 
	 * @param board board 
	 * @return can current player wins?
	 */
	public static Choice bestChoice(int board) {
		if(memo[board] != null) {
			return memo[board];
		}
		boolean gameDone = true;
		int winCount = 0;
		Choice bestWin = null;
		Choice bestLose = null;
		for(int i = 0; i < moves.size(); i++) {
			// can place this block
			if((board & moves.get(i)) == 0) {
				gameDone = false;
				Choice next = bestChoice(board + moves.get(i));
				// we has at least 1 case to win the game
				// next player will be defeated
				if(!next.canWin) {
					bestWin = new Choice(moves.get(i), true);
					// 첫 수를 찾는데 너무 오래 걸리면 여기에 break 넣기
					winCount++;
				} else {
					if(bestLose == null) {
						bestLose = new Choice(moves.get(i), next.winCount, false);
					} else if(next.winCount < bestLose.winCount) {
						// 다음 수에 상대방이 이기는 수가 더 적다면
						// bestLose를 그 수로 갱신
						bestLose = new Choice(moves.get(i), next.winCount, false);
					}
				}
			}
		}
		// if ret = false
		// every combination is lose
		// or the game already ended
		// so this player always defeated
		
		// this player can win
		if(bestWin != null) {
			bestWin.winCount = winCount;
			memo[board] = bestWin;
			return bestWin;
		} else if(gameDone) {
			memo[board] = new Choice(0, false);
			return memo[board];
		} else {
			// defeated
			bestLose.winCount = 0;
			memo[board] = bestLose;
			return bestLose;
		}
	}
	
	public static boolean isFinished(int board) {
		for(int i = 0; i < moves.size(); i++) {
			// can place this block
			if((board & moves.get(i)) == 0) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 블록을 놓을 수 있는 모든 경우를 미리 구함
	 */
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
	
	public static int setArray(int y, int x, int binary) {
		return setBit(y * SIZE + x, binary);
	}
	
	public static boolean getArray(int y, int x, int binary) {
		return getBit(y * SIZE + x, binary);
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
	
	static class Choice {
		int block; // bit masked block
		int winCount; // number of ways to win against all combinations
		boolean canWin; // 현재 플레이어의 이기는 경우의 수
		                // 따라서 상위 재귀에서 무조건 Lose인 경우
		                // 이것이 최대한 작은 경우로 수를 둬야함
		public Choice(int block, int winCount, boolean canWin) {
			this.block = block;
			this.winCount = winCount;
			this.canWin = canWin;
		}
		public Choice(int block, boolean canWin) {
			this(block, 0, canWin);
		}
	}
}
