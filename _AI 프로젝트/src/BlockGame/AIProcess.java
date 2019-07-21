package BlockGame;

import java.util.ArrayList;
import java.util.Random;

import BlockGame.Function.Choice;

public class AIProcess {
	public static void processAI() {
		// if computer's turn
		if(Main.game.turn == Main.game.computer) {
			/* 같은 승률일 때 다양한 배치를 위한 코드 *******/
			int board = Main.game.board;
			ArrayList<Integer> moves = Function.moves;
			boolean gameDone = true;
			int winCount = 0;
			Choice bestWin = null;
			Choice bestLose = null;
			
			ArrayList<Choice> bestWins = null;
			ArrayList<Choice> bestLoses = null;
			
			for(int i = 0; i < moves.size(); i++) {
				// can place this block
				if((board & moves.get(i)) == 0) {
					gameDone = false;
					Choice next = bestChoice(board + moves.get(i));
					winCount += next.winCount;
					// we has at least 1 case to win the game
					// next player will be defeated
					if(!next.canWin) {
						bestWin = new Choice(moves.get(i), true);
						if(bestWins == null) {
							bestWins = new ArrayList<Choice>();
						}
						bestWins.add(bestWin);
					} else {
						if(bestLose == null) {
							bestLose = new Choice(moves.get(i), next.winCount, false);
							bestLoses = new ArrayList<Choice>();
							bestLoses.add(bestLose);
						} else if(next.winCount < bestLose.winCount) {
							// the better lose way is exists
							bestLose = new Choice(moves.get(i), next.winCount, false);
							bestLoses = new ArrayList<Choice>();
							bestLoses.add(bestLose);
						} else if(next.winCount == bestLose.winCount){
							bestLoses.add(new Choice(moves.get(i), next.winCount, false));
						}
					}
				}
			}
			Random rand = new Random();
			
			Choice best;
			
			if(bestWin != null) {
				best = bestWins.get(rand.nextInt(bestWins.size()));
				// 가능한 수 개수
				System.out.println("WIN: " + bestWins.size());
				if(bestWins.size() == 1) {
					System.out.println("(신의 한 수!)");
				}
			} else if(gameDone) {
				best = null;
				System.out.println("null");
			} else {
				best = bestLoses.get(rand.nextInt(bestLoses.size()));
				// 가능한 수 개수, 이 수들을 뒀을 때 상대방이 이기는 수의 수
				System.out.println("LOSE: " + bestLoses.size() + " " + best.winCount);
			}
			
			if(best != null) {
				Main.game.placeBlock(best.block);
			}
			
			// 원본 코드 //
//			Choice best = bestChoice(Main.game.board);
//			if(!Main.game.placeBlock(best.block)) {
//				System.out.println("ERROR: 컴퓨터가 이상한 수를 뒀습니다.");
//			}
		}
	}
	
	public static Choice bestChoice(int board) {
		return Function.bestChoice(board);
	}
}
