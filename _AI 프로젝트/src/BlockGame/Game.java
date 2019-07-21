package BlockGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.Stack;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

public class Game {

	/* constants ********/
	public static final int EMPTY = 0;
	public static final int P1 = 1;
	public static final int P2 = 2;
	public static final Color[] COLOR = {Color.WHITE, Color.BLUE, Color.RED};
	public static final Position[][] BLOCK = {
			{new Position(0, 0), new Position(0, 1)},
			{new Position(0, 0), new Position(1, 0)},
			{new Position(0, 0), new Position(1, 0), new Position(0, 1)}, // ┏
			{new Position(0, 0), new Position(1, 0), new Position(0, -1)}, // ┓
			{new Position(0, 0), new Position(-1, 0), new Position(0, -1)}, // ┛ 
			{new Position(0, 0), new Position(-1, 0), new Position(0, 1)} // ┗
	};

	/* variables ********/
	public int board;
	public int turn;
	public int computer;
	public boolean gameStarted = false;
	public int mode; // 0 = pvp, 1 = auto cvp
	public int selectedBlock = 2; // 0 ~ 5
	public int mouseOnX, mouseOnY;
	public Stack<Integer> prevPlacedMask;
 
	/* graphics ********/
	// cotainers //
	JFrame frame;
	JPanel topPanel, boardPanel, botPanel, sidePanel; 

	// topPanel //
	JButton reset, AIBtn;

	// boardPanel //
	Cell[][] boardCell;

	// botPanel //
	JLabel turnLabel;
	JLabel expectLabel;

	// sidePanel //
	JLabel info[] = {new JLabel("<< INFO >>")
			, new JLabel("D: change")
			, new JLabel("S, F: lotate")
			, new JLabel("BS: restore")};

	public Game() {
		/* set variables *************/
		board = 0;
		turn = P1;
		computer = P2;
		mode = 0;

		/* set graphics **************/
		// frame //
		frame = new JFrame("Block Game");
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.setSize(1000, 700);

		// topPanel //
		topPanel = new JPanel();
		frame.add(topPanel, BorderLayout.NORTH);
		AIBtn = new JButton("AI");
		topPanel.add(AIBtn);
		AIBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!gameStarted) {
					JOptionPane.showMessageDialog(null, "Please Start Game First");
					return;
				}
				computer = turn;
				AIProcess.processAI();
				frame.requestFocus();
			}
		});
		reset = new JButton("Game Start");
		topPanel.add(reset);
		reset.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if(!gameStarted) {
					startGame();
				}
				reset();
				frame.requestFocus();
			}
		});

		// boardPanel //
		boardPanel = new JPanel();
		frame.add(boardPanel, BorderLayout.CENTER);
		boardPanel.setLayout(new GridLayout(Function.SIZE, Function.SIZE));
		boardCell = new Cell[Function.SIZE][Function.SIZE];
		for(int i = 0; i < Function.SIZE; i++) {
			for(int j = 0; j < Function.SIZE; j++) {
				boardCell[i][j] = new Cell(i, j);
				boardPanel.add(boardCell[i][j]);
				boardCell[i][j].addMouseListener(new MouseAdapter() {
					public void mouseEntered(MouseEvent e) {
						Cell cell = (Cell)e.getSource();
						mouseOnY = cell.y;
						mouseOnX = cell.x;
						for(Position p : cell.blocks[selectedBlock]) {
							// 해당 블록을 호버할 수 없으니 그냥 무시
							if(p.y < 0 || p.x < 0 || p.y >= Function.SIZE || p.x >= Function.SIZE) {
								return;
							}
						}
						for(Position p : cell.blocks[selectedBlock]) {
							boardCell[p.y][p.x].hover();
						}
					}
					public void mouseExited(MouseEvent e) {
						Cell cell = (Cell)e.getSource();
						for(Position p : cell.blocks[selectedBlock]) {
							if(p.y < 0 || p.x < 0 || p.y >= Function.SIZE || p.x >= Function.SIZE) {
								return;
							}
						}
						for(Position p : cell.blocks[selectedBlock]) {
							boardCell[p.y][p.x].unhover();
						}
					}
				});
				
				boardCell[i][j].addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						if(!gameStarted) {
							JOptionPane.showMessageDialog(null, "Please Start Game First");
							return;
						}
						Cell cell = (Cell)e.getSource();
						if(placeBlock(cell.y, cell.x, selectedBlock) && mode == 1) {
							AIProcess.processAI();
						}
						frame.requestFocus();
					}
				});
			}
		}

		// botPanel //
		botPanel = new JPanel();
		frame.add(botPanel, BorderLayout.SOUTH);
		turnLabel = new JLabel();
		turnLabel.setBorder(new CompoundBorder(turnLabel.getBorder()
				, new EmptyBorder(0, 100, 0, 100)));
		botPanel.add(turnLabel);
		setTurnLabel(turn);
		expectLabel = new JLabel("Expect result: none");
		expectLabel.setBorder(new CompoundBorder(turnLabel.getBorder()
				, new EmptyBorder(0, 100, 0, 100)));
		botPanel.add(expectLabel);

		// sidePanel //
		sidePanel = new JPanel();
		frame.add(sidePanel, BorderLayout.EAST);
		Box box = Box.createVerticalBox();
		for(int i = 0; i < info.length; i++) {
			box.add(info[i]);
			box.createVerticalStrut(10);
		}
		sidePanel.add(box);

		// frame key event //
		frame.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				// 블록 바꾸기
				if(e.getKeyCode() == KeyEvent.VK_D) {
					blockChange();
				} else if(e.getKeyCode() == KeyEvent.VK_F) {
					blockRotate(true);
				} else if(e.getKeyCode() == KeyEvent.VK_S) {
					blockRotate(false);
				} else if(e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
					if(!gameStarted) {
						JOptionPane.showMessageDialog(null, "Please Start Game First");
						return;
					}
					if(!prevPlacedMask.isEmpty()) {
						if(!removeBlock(prevPlacedMask.pop())) {
							System.out.println("ERROR: return to prev error");
						}
					} else {
						JOptionPane.showMessageDialog(null, "No more previous turn");
					}
				}
			}
		});
		
		frame.addMouseWheelListener(new MouseWheelListener() {
			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				// 위쪽 스크롤
				if(e.getWheelRotation() < 0) {
					blockRotate(false);
				} else if(e.getWheelRotation() > 0) {
					blockRotate(true);
				}
			}
		});
		
		frame.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				blockChange();
				System.out.println("Clicked");
				if(e.getButton() == MouseEvent.BUTTON3_DOWN_MASK) {
					blockChange();
				}
			}
		});

		// resize //
		resize();
	}

	/* normal method ****************/
	/**
	 * 블록을 놓는다
	 * 
	 * @param y
	 * @param x
	 * @param block 블록 종류
	 * @return 블록이 제대로 놓였는지?
	 */
	public boolean placeBlock(int y, int x, int block) {
		// check can we place the block
		for(Position p : boardCell[y][x].blocks[selectedBlock]) {
			// index out of bound
			if(p.y < 0 || p.x < 0 || p.y >= Function.SIZE || p.x >= Function.SIZE) {
				return false;
			}
			// can't place block because the cell has been occupied
			if(boardCell[p.y][p.x].state != EMPTY) {
				return false;
			}
		}
		
		// can place the block //
		for(Position p : boardCell[y][x].blocks[selectedBlock]) {
			boardCell[p.y][p.x].setState(turn);
		}
		turn ^= 3;
		board |= boardCell[y][x].blockMasks[selectedBlock];
		
		// set turn label
		setTurnLabel(turn);
		
		// if game finished
		if(Function.isFinished(board)) {
			JOptionPane.showMessageDialog(null, "player " + ((turn ^ 3) == P1? "1" : "2") + " wins!");
			endGame();
		}
		
		// renew expect label
		if(Function.bestChoice(board).canWin) {
			expectLabel.setText("Expect result: player " + (turn == P1?
					"1" : "2") + " wins");
		}
		
		// put into the stack
		prevPlacedMask.push(boardCell[y][x].blockMasks[selectedBlock]);
		
		return true;
	}
	
	/**
	 * 비트마스킹 된 블록을 놓는다.
	 * 위의 placeBlock 메소드와 같은 단계를 거쳐 만들어야 한다.
	 * 
	 * @param block
	 * @return
	 */
	public boolean placeBlock(int block) {
		if((board & block) != 0) return false;

		// place the block
		for(int y = 0; y < Function.SIZE; y++) {
			for(int x = 0; x < Function.SIZE; x++) {
				if(Function.getArray(y, x, block)) {
					boardCell[y][x].setState(turn);
				}
			}
		}
		turn ^= 3;
		board |= block;
		
		// set turn label
		setTurnLabel(turn);
		
		// if game finished
		if(Function.isFinished(board)) {
			JOptionPane.showMessageDialog(null, "player " + ((turn ^ 3) == P1? "1" : "2") + " wins!");
			endGame();
		}
		
		// renew expect label
		if(Function.bestChoice(board).canWin) {
			expectLabel.setText("Expect result: player " + (turn == P1?
					"1" : "2") + " wins");
		}
		
		// put into the stack
		prevPlacedMask.push(block);
		
		return true;
	}
	
	/**
	 * 비트마스킹 된 블록을 지운다.
	 * 
	 * @param block
	 * @return
	 */
	public boolean removeBlock(int block) {
		// remove the block
		for(int y = 0; y < Function.SIZE; y++) {
			for(int x = 0; x < Function.SIZE; x++) {
				if(Function.getArray(y, x, block)) {
					boardCell[y][x].setState(EMPTY);
				}
			}
		}
		turn ^= 3; // change turn to prev turn
		board &= (~block); // remove from mask
		
		// set turn label
		setTurnLabel(turn);
		
		// renew expect label
		if(Function.bestChoice(board).canWin) {
			expectLabel.setText("Expect result: player " + (turn == P1?
					"1" : "2") + " wins");
		}
		
		return true;
	}
	
	
	
	public void blockChange() {
		Cell cell = boardCell[mouseOnY][mouseOnX];
		// 이전 것 언호버
		boolean canDo = true;
		for(Position p : cell.blocks[selectedBlock]) {
			if(p.y < 0 || p.x < 0 || p.y >= Function.SIZE || p.x >= Function.SIZE) {
				canDo = false;
				break;
			}
		}
		if(canDo) {
			for(Position p : cell.blocks[selectedBlock]) {
				boardCell[p.y][p.x].unhover();
			}
		}
		
		if(selectedBlock < 2) {
			selectedBlock = 2;
		} else {
			selectedBlock = 0;
		}
		
		// 현재 것 호버
				canDo = true;
				for(Position p : cell.blocks[selectedBlock]) {
					// 해당 블록을 호버할 수 없으니 그냥 무시
					if(p.y < 0 || p.x < 0 || p.y >= Function.SIZE || p.x >= Function.SIZE) {
						canDo = false;
						break;
					}
				}
				if(canDo) {
					for(Position p : cell.blocks[selectedBlock]) {
						boardCell[p.y][p.x].hover();
					}
				}
	}
	
	public void blockRotate(boolean clockwise) {
		Cell cell = boardCell[mouseOnY][mouseOnX];
		// 이전 것 언호버
		boolean canDo = true;
		for(Position p : cell.blocks[selectedBlock]) {
			if(p.y < 0 || p.x < 0 || p.y >= Function.SIZE || p.x >= Function.SIZE) {
				canDo = false;
				break;
			}
		}
		if(canDo) {
			for(Position p : cell.blocks[selectedBlock]) {
				boardCell[p.y][p.x].unhover();
			}
		}
		
		// 정방향 회전 (마우스휠 아래로)
		if(clockwise) {
			if(selectedBlock < 2) {
				selectedBlock ^= 1;
			} else {
				selectedBlock++;
				if(selectedBlock == 6) {
					selectedBlock = 2;
				}
			}
		} else {
			if(selectedBlock < 2) {
				selectedBlock ^= 1;
			} else {
				selectedBlock--;
				if(selectedBlock == 1) {
					selectedBlock = 5;
				}
			}
		}
		
		// 현재 것 호버
		canDo = true;
		for(Position p : cell.blocks[selectedBlock]) {
			// 해당 블록을 호버할 수 없으니 그냥 무시
			if(p.y < 0 || p.x < 0 || p.y >= Function.SIZE || p.x >= Function.SIZE) {
				canDo = false;
				break;
			}
		}
		if(canDo) {
			for(Position p : cell.blocks[selectedBlock]) {
				boardCell[p.y][p.x].hover();
			}
		}
	}

	/* graphic method ****************/
	public void setTurnLabel(int turn) {
		turnLabel.setForeground(COLOR[turn]);
		turnLabel.setText("Current Player: " + (turn == P1? "player 1" : "player 2"));
	}

	public void resize() {
		frame.setSize(frame.getWidth() - 1, frame.getHeight());
		frame.setSize(frame.getWidth() + 1, frame.getHeight());
	}
	
	public void startGame() {
		reset.setText("Reset");
		gameStarted = true;
	}
	
	public void endGame() {
		reset.setText("Game Start");
		gameStarted = false;
	}
	
	public void reset() {
		mode = JOptionPane.showConfirmDialog(null
				, "다른 플레이어와 두시겠습니까?"
				, "Select PvP", JOptionPane.YES_NO_OPTION
				, JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION? 0 : 1;
		if(mode == 1) {
			computer = JOptionPane.showConfirmDialog(null
					, "플레이어가 선수를 둡니까?"
					, "Select First Turn", JOptionPane.YES_NO_OPTION
					, JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION? P2 : P1;
		}
		board = 0;
		turn = P1;
		selectedBlock = 0;
		setTurnLabel(turn);
		expectLabel.setText("Expect result: none");
		for(int i = 0; i < Function.SIZE; i++) {
			for(int j = 0; j < Function.SIZE; j++) {
				boardCell[i][j].setState(EMPTY);
			}
		}
		
		prevPlacedMask = new Stack<Integer>();
		
		if(mode == 1) {
			AIProcess.processAI();	
		}
		resize();
	}

	/* private method *****************/
	private static Position[] copy(Position[] origin) {
		Position[] ret = new Position[origin.length];
		for(int i = 0; i < ret.length; i++) {
			ret[i] = new Position(origin[i].y, origin[i].x);
		}
		return ret;
	}

	/**
	 * (x, y) 지점에서 블록의 종류에 따라 있을 수 있는 지점들을
	 * Position 배열로 반환한다.
	 * 
	 * @param y
	 * @param x
	 * @param block 블록 종류
	 * @return 블록의 Position 배열
	 */
	private static Position[] getBlockPosition(int y, int x, int block) {
		// 약간 비효율적인 것 같음
		Position[] ret = copy(BLOCK[block]);
		for(int i = 0; i < ret.length; i++) {
			ret[i].y += y;
			ret[i].x += x;
		}
		return ret;
	}

	/**
	 * (x, y) 지점에서 블록의 종류에 따라 있을 수 있는 지점들을
	 * 비트마스크로 반환한다.
	 * 
	 * @param y
	 * @param x
	 * @param block 블록 종류
	 * @return 블록의 비트마스크
	 */
	private static int getBlockMask(int y, int x, int block) {
		Position[] blocks = BLOCK[block];
		int ret = 0;
		for(int kind = 0; kind < blocks.length; kind++) {
			ret = Function.setArray(blocks[kind].y + y, blocks[kind].x + x, ret);
		}
		return ret;
	}


	/* inner classes ************************/
	static class Cell extends JButton {
		int x, y;
		int state;
		Position[][] blocks = new Position[BLOCK.length][];
		int[] blockMasks = new int[BLOCK.length];
		public Cell(int y, int x) {
			this(y, x, EMPTY);
		}
		public Cell(int y, int x, int state) {
			super();
			this.x = x;
			this.y = y;
			setState(state);
			// 미리 블록의 종류에 대해 계산을 해둠
			for(int kind = 0; kind < BLOCK.length; kind++) {
				blocks[kind] = getBlockPosition(y, x, kind);
				blockMasks[kind] = getBlockMask(y, x, kind);
			}
		}
		public void setState(int newState) {
			state = newState;
			this.setBackground(COLOR[state]);
		}
		/**
		 * hover this cell
		 */
		public void hover() {
			this.setBackground(COLOR[state].darker());
		}
		/**
		 * unhover this cell
		 */
		public void unhover() {
			this.setBackground(COLOR[state]);
		}
	}

	static class Position {
		int y, x;
		public Position() {
			this(0, 0);
		}
		public Position(int y, int x) {
			this.x = x;
			this.y = y;
		}
	}
	
	public class Remember {
		Position pos;
		int block;
		public Remember(int y, int x, int b) {
			this(new Position(y, x), b);
		}
		public Remember(Position p, int b) {
			pos = p;
			block = b;
		}
	}
}
