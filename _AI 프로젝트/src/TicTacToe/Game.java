package TicTacToe;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import TicTacToe.Function.Choice;

public class Game {

	/* variables ********/
	public int board;
	public int turn; // 현재 턴
	public int computer; // 컴퓨터의 턴은?
	public int computerWins;
	public boolean gameStarted = false;
	public int mode; // 0 = pvp, 1 = auto cvp
	
	/* graphics *******/
	JFrame frame;
	JPanel boardPanel, topPanel, botPanel;
	Cell[][] boardCell = new Cell[Function.SIZE][Function.SIZE];
	JLabel statusLabel;
	JButton reset;
	JButton doAIBtn;
	
	public Game() {
		/* set variables *******/
		board = 0;
		turn = Function.X;
		computer = Function.X;
		mode = 0;
		
		/* set graphic ****/
		// frame setting //
		frame = new JFrame("Tic Tac Toe");
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.setSize(500, 500);
		
		// top //
		topPanel = new JPanel();
		frame.add(topPanel, BorderLayout.NORTH);
		doAIBtn = new JButton("AI Process");
		topPanel.add(doAIBtn);
		doAIBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(!gameStarted) {
					JOptionPane.showMessageDialog(null, "Please Start Game First");
					return;
				}
				computer = turn;
				AIProcess.processAI();
			}
		});
		reset = new JButton("Game Start");
		topPanel.add(reset);
		reset.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!gameStarted) {
					startGame();
				}
				resetGame();
			}
		});
		
		// board //
		boardPanel = new JPanel();
		boardPanel.setLayout(new GridLayout(3, 3));
		frame.add(boardPanel, BorderLayout.CENTER);
		// 보드판에 셀 추가
		for(int y = 0; y < Function.SIZE; y++) {
			for(int x = 0; x < Function.SIZE; x++) {
				boardCell[y][x] = new Cell(y, x);
				boardCell[y][x].setBackground(Color.WHITE);
				boardPanel.add(boardCell[y][x]);
				boardCell[y][x].addActionListener(new CellListener(boardCell[y][x]));
			}
		}

		// board //
		botPanel = new JPanel();
		frame.add(botPanel, BorderLayout.SOUTH);
		statusLabel = new JLabel("Expected result: draw");
		botPanel.add(statusLabel);
		
		//////// refresh frame
		frame.setSize(frame.getWidth() - 1, frame.getHeight());
		frame.setSize(frame.getWidth() + 1, frame.getHeight());
	}
	
	public void setBoard(int y, int x, int state) {
		board = Function.setArray(y, x, state, board);
		boardCell[y][x].setState(state);
	}
	
	public boolean isEmptyCell(int y, int x) {
		return Function.getArray(y, x, board) == Function.EMPTY;
	}
	
	/**
	 * 현재 턴인 사람이 y, x에 수를 둔다.
	 * 만약 y, x가 이미 차있다면 아무것도 안 하고 false 반환
	 * 
	 * @param y
	 * @param x
	 * @return
	 */
	public boolean placeToken(int y, int x) {
		// can't place the token here
		if(!isEmptyCell(y, x)) {
			return false;
		}
		
		// can place the token here
		setBoard(y, x, turn);
		turn ^= 3; // change turn
		
		// set expect String
		Choice best = AIProcess.bestChoice(Main.game.board, Main.game.turn);
		if(best.win == 0) {
			Main.game.statusLabel.setText("Expected result: draw");
		} else if(best.win == Function.WIN){
			Main.game.statusLabel.setText("Expected result: player " + (turn == Function.X? "X wins" : "O wins"));
		} else {
			Main.game.statusLabel.setText("Expected result: player " + (turn == Function.X? "O wins" : "X wins"));
		}
		
		// process is Finished
		if(Function.isFinished(turn ^ 3, board)) {
			JOptionPane.showMessageDialog(null, "player " 
					+ ((turn^3) == Function.X? "X" : "O")
					+ " wins!");
			endGame();
		} else if(isAllFilled()){
			JOptionPane.showMessageDialog(null, "draw...");
			endGame();
		} else {
			if(mode == 1) {
				// can continue the game
				AIProcess.processAI();
			}
		}
		return true;
	}
	
	public boolean isAllFilled() {
		for(int i = 0; i < Function.SIZE; i++) {
			for(int j = 0; j < Function.SIZE; j++) {
				if(boardCell[i][j].state == Function.EMPTY) {
					return false;
				}
			}
		}
		return true;
	}
	
	public void startGame() {
		reset.setText("Reset");
		gameStarted = true;
	}
	
	public void endGame() {
		reset.setText("Game Start");
		gameStarted = false;
	}
	
	public void resetGame() {
		// select mode 0 = pvp, 1 = cvp
		mode = JOptionPane.showConfirmDialog(null
				, "다른 플레이어와 두시겠습니까?"
				, "Select PvP", JOptionPane.YES_NO_OPTION
				, JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION? 0 : 1;
		
		
		// select first turn
		if(mode == 1) {
			computer = JOptionPane.showConfirmDialog(null
					, "플레이어가 선수를 둡니까?"
					, "Select First Turn", JOptionPane.YES_NO_OPTION
					, JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION? Function.O : Function.X;
		}

		// set all graphic cells to EMPTY
		for(int i = 0; i < Function.SIZE; i++) {
			for(int j = 0; j < Function.SIZE; j++) {
				boardCell[i][j].setState(Function.EMPTY);
			}
		}
		
		// set trinary board clear
		board = 0;
		
		// set first turn to player X
		turn = Function.X;
		
		// reset status label
		statusLabel.setText("Expected result: draw");
		
		if(mode == 1) {
			AIProcess.processAI();	
		}
		
		// frame renew
		frame.setSize(frame.getWidth() - 1, frame.getHeight());
		frame.setSize(frame.getWidth() + 1, frame.getHeight());
	}
	
	class Cell extends JButton {
		int y, x;
		int state; // 0: empty, 1: x, 2: o
		public Cell(int y, int x) {
			this(y, x, 0);
		}
		public Cell(int y, int x, int state) {
			super(Function.TOKEN[state]);
			this.y = y;
			this.x = x;
		}
		
		public void setState(int state) {
			this.state = state;
			this.setText(Function.TOKEN[state]);
		}
		
		public int getState() {
			return state;
		}
		
	}
	
	class CellListener implements ActionListener{
		Cell cell;
		public CellListener(Cell c) {
			cell = c;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			if(!gameStarted) {
				JOptionPane.showMessageDialog(null, "Please Start Game First");
				return;
			}
			// can't place the token here
			if(!placeToken(cell.y, cell.x)) {
				/* */
			} else {
				
			}
		}
	}
}
