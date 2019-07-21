package Clock;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

public class Puzzle extends JFrame implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Clock clock;
	private Timer timer;
	private final int DELAY = 1000;
	private int row;
	private int col;
	private int count = 0; // 버튼을 움직인 횟수
	private int second = 0;
	private boolean programExit = false;
	JButton btn[][];
	JLabel watch;
	JLabel score = new JLabel("Move : Start!");
	JButton exit = new JButton("EXIT");
	JPanel panel = new JPanel();
	JPanel panel2 = new JPanel();
	
	public Puzzle() 
	{
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// 가로 세로 몇 줄의 퍼즐을 만들지 결정함
		boolean end = false;
		while(!end)
		{
			String rowStr = "";
			String colStr = "";
			boolean fin = false;
			while(!fin)	// cancle을 눌렀을떄 대비
			{
				rowStr = JOptionPane.showInputDialog("Create Row");
				if(rowStr == null)
				{
					int select = JOptionPane.showConfirmDialog(null, "Exit puzzle game and go back to previous page?");
					// 확인 입력시 프로그램 종료
					if(select == 0)
					{
						programExit = true;
						fin = true;
						end = true;
					}
				}
				
				else
					fin = true;
			}
			
			fin = false;
			while(!fin && !programExit) // cancle을 눌렀을떄 대비
			{
				colStr = JOptionPane.showInputDialog("Create Col");
				if(colStr == null)
				{
					int select = JOptionPane.showConfirmDialog(null, "Exit puzzle game and go back to previous page?");
					// 확인 입력시 프로그램 종료
					if(select == 0)
					{
						programExit = true;
						fin = true;
						end = true;
					}
				}
				
				else
					fin = true;
			}
			
			// 잘못된 문자열을 입력했을 경우 경고
			if(!programExit)
			{
				if(isStringInteger(rowStr) && isStringInteger(colStr))
				{
					if(Integer.parseInt(rowStr)>1 && Integer.parseInt(colStr)>1)
					{
						
						row = Integer.parseInt(rowStr);
						col = Integer.parseInt(colStr);
						end = true;
					}
					
					else
						JOptionPane.showMessageDialog(null, "Please Input bigger than \"1\"", "WARNING_OUT_OF_RANGE", JOptionPane.WARNING_MESSAGE);
				}
				else
					JOptionPane.showMessageDialog(null, "Wrong Input", "WARNING_STR_IN_SENTENCE", JOptionPane.WARNING_MESSAGE);
			}
		}
		
		// 앞서 cancle을 눌러 프로그램을 종료시켰을 경우
		if(programExit)
		{
			JOptionPane.showMessageDialog(null, "Go to previous page", "EXIT_PUZZLE_GAME", JOptionPane.WARNING_MESSAGE);
			this.dispose();
			this.setVisible(false);
			new FirstPage();
		}
			
		
		// 2이상의 정수로 된 행렬을 입력받고 정상적인 작동을 해야할 경우
		else
		{
			btn = new JButton[row][col];
			
			// 타이머 설정
			clock = new Clock(10000000, 60, 60);
		    clock.setTime(0, 0, 0);      
			watch = new JLabel("Timer   " + clock.getTime());
	        watch.setFont(watch.getFont().deriveFont(20f));
	        watch.setBorder(new EmptyBorder(10, 20, 10, 20));
	        score.setFont(watch.getFont().deriveFont(30f));
	        score.setBorder(new EmptyBorder(10, 20, 10, 20));
	        exit.setFont(exit.getFont().deriveFont(20f));
	        
			exit.setMargin(new Insets(0, 0, 0, 0));
	        exit.setBackground(Color.red);
	        exit.setBorder(BorderFactory.createEmptyBorder());
	        exit.setBorderPainted( false );
	        
	        timer = new Timer(DELAY, this);
	        timer.start();
	        
	        // 타이머와 스코어를 추가
	        panel2.add(watch);
	        panel2.add(score);
	        panel2.add(exit);
	        this.add(panel2, "North");
			
			// 버튼 row * col 개를 만든다 (이름 없음)
			for(int i=0; i<btn.length; i++)
				for(int j=0; j<btn[i].length; j++)
					btn[i][j] = new JButton();
			panel.setLayout(new GridLayout(row,col));	// ROW x COL 패널을 만든다
			
			// 버튼들을 panel에 추가한다. 버튼들의 폰트를 조절한다
			// 버튼의 이름을 랜덤하게 짓는다
			// 15번 버튼을 투명하게 만든다.
			int[] order = makeRandom(row * col - 1);	// 마지막 칸은 숫자를 따로 정해줘야함
			for(int i=0; i<row; i++)
				for(int j=0; j<col; j++)
				{
					// 마지막 칸은 랜덤으로 숫자를 정하면 안 되니까
					if(!(i == row-1 && j == col-1))
						btn[i][j].setText(""+order[row * i + j]); // 텍스트를 랜덤으로 설정
					panel.add(btn[i][j]);
					btn[i][j].setFont(btn[i][j].getFont().deriveFont(50f));
					btn[i][j].addActionListener(this); // 액션리스너 추가
				}
					
			
			panel.add(btn[row-1][col-1]);
			btn[row-1][col-1].setText("" + (row * col - 1));
			btn[row-1][col-1].setVisible(false);
			
			// 화면 크기를 조절하고, 나타낸다
			panel.setPreferredSize(new Dimension(col * 100, row * 100));
			this.add(panel);
			
			this.setLocation(700, 400);
			pack();
			setVisible(true);
			
			exit.addActionListener(this);
		}
		
	}
	
	/**
	 * 0부터 차례로 number개의 숫자를 섞는다 (0 ~ number-1)
	 * 
	 * @param number 몇 개의 숫자를 섞을지
	 * @return 섞은 배열
	 */
	private int[] makeRandom(int number)
	{
		Random random = new Random();
		int[] numbers = new int[number];
		
		// numbers 배열을 -1으로 초기화
		for(int i=0; i<number; i++)
			numbers[i] = -1;
		
		// 랜덤을 돌려 나온 방이 비어있을 경우 그 방을 채우고
		// 비어있지 않는 경우 다시 랜덤을 돌린다.
		int temp;
		for(int i=0; i<number; i++)		
		{
			boolean ok = false;
			while(!ok)
			{
				temp = random.nextInt(number);
				if(numbers[temp] == -1)
				{
					numbers[temp] = i;
					ok = true;
				}
			}
		}
		
		return numbers;
	}
	
	/**
	 * 입력받은 문자열이 정수인지?
	 * @param s 문자열
	 * @return true 정수	false 정수 아님
	 */
	private boolean isStringInteger(String s) {
	    try {
	        Integer.parseInt(s);
	        return true;
	    } catch (NumberFormatException e) {
	        return false;
	    }
	 }

	@Override
	public void actionPerformed(ActionEvent e){
		// TODO Auto-generated method stub

		new Functions();
		
		if(e.getSource().equals(timer))
    	{
    		clock.timeTick();
    		watch.setText("Timer   " + clock.getTime()); 
    	}
		
		for(int i=0; i<row; i++)
			for(int j=0; j<col; j++)
			{
				// 만약 i행 j열 버튼이 눌렸을 때
				if(e.getSource().equals(btn[i][j]))
				{
					
					// 왼쪽이 공백일 때 && 버튼이 왼쪽 가장자리에 위치하지 않았을 때
					// 클릭된 버튼과 왼쪽의 버튼을 바꾼다 (이름만)
					if(j != 0 && !btn[i][j-1].isVisible())
					{
						String tmp = btn[i][j].getText();
						btn[i][j].setText(btn[i][j-1].getText());
						btn[i][j-1].setText(tmp);
						btn[i][j].setVisible(false);
						btn[i][j-1].setVisible(true);
						count++;	// 버튼을 움직일 때마다 횟수 증가
						score.setText("Move : " + count);
					}
					
					// 오른쪽이 공백일 때 && 버튼이 오른쪽 가장자리에 위치하지 않았을 때
					// 클릭된 버튼과 오른쪽의 버튼을 바꾼다 (이름만)
					else if(j != col-1 && !btn[i][j+1].isVisible())
					{
						String tmp = btn[i][j].getText();
						btn[i][j].setText(btn[i][j+1].getText());
						btn[i][j+1].setText(tmp);
						btn[i][j].setVisible(false);
						btn[i][j+1].setVisible(true);
						count++;	// 버튼을 움직일 때마다 횟수 증가
						score.setText("Move : " + count);
					}
					
					// 위쪽이 공백일 때 && 버튼이 위쪽 가장자리에 위치하지 않았을 때
					// 클릭된 버튼과 위쪽의 버튼을 바꾼다 (이름만)
					else if(i != 0 && !btn[i-1][j].isVisible())
					{
						String tmp = btn[i][j].getText();
						btn[i][j].setText(btn[i-1][j].getText());
						btn[i-1][j].setText(tmp);
						btn[i][j].setVisible(false);
						btn[i-1][j].setVisible(true);
						count++;	// 버튼을 움직일 때마다 횟수 증가
						score.setText("Move : " + count);
					}
					
					// 아래쪽이 공백일 때 && 버튼이 아래쪽 가장자리에 위치하지 않았을 때
					// 클릭된 버튼과 아래쪽의 버튼을 바꾼다 (이름만)
					else if(i != row-1 && !btn[i+1][j].isVisible())
					{
						String tmp = btn[i][j].getText();
						btn[i][j].setText(btn[i+1][j].getText());
						btn[i+1][j].setText(tmp);
						btn[i][j].setVisible(false);
						btn[i+1][j].setVisible(true);
						count++;	// 버튼을 움직일 때마다 횟수 증가
						score.setText("Move : " + count);
					}
					
					
					
					// 게임이 끝났는지 판단한다
					boolean gameEnd = true;
					for(int l=0; l<row; l++)
						for(int k=0; k<col; k++)
							if(!(Integer.parseInt(btn[l][k].getText()) == l * row + k))
								gameEnd = false;
					if(gameEnd) 
					{
						try {
							timer.stop();
							score.setText("Move : " + count);
							JOptionPane.showMessageDialog(null, "Victory !");
							JOptionPane.showMessageDialog(null, "MOVE : " + count);
						
							second = clock.getHour() * 3600 + clock.getMinute() * 60 + clock.getSecond();
							JOptionPane.showMessageDialog(null, "SECOND : " + second);
							
							this.setVisible(false);
							this.dispose();
							if(row == 4 && col == 4)
							{
								ScoreBoard sc = new ScoreBoard(second, true);
								sc.display();
							}
							
							else
							{
								ScoreBoard sc = new ScoreBoard(second, false);
								sc.display();
							}
							
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
					}
					
				}
			}
		
		if(e.getSource().equals(exit))
		{
			int select = JOptionPane.showConfirmDialog(exit, "Really exit this game? your data should be deleted");
			if(select == 0)
			{
				this.dispose();
				this.setVisible(false);
				new FirstPage();
			}
		}
		
			
	}

}

