package TicTacToe;

import TicTacToe.Function.Choice;

public class AIProcess {
	
	public static void processAI() {
		// if computer's turn
		if(Main.game.turn == Main.game.computer) {
			Choice best = bestChoice(Main.game.board, Main.game.turn);
			if(!Main.game.placeToken(best.y, best.x)) {
				System.out.println("ERROR: 컴퓨터가 이상한 수를 뒀습니다.");
			}
		}
	}
	
	public static Choice bestChoice(int board, int turn) {
		return Function.bestChoice(board, turn, 0);
	}
	
}
