package Clock;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**********************************************************************************
 * 게임이 끝난 후 초수 게시판
 * @author Administrator
 *
 */
public class ScoreBoard extends JFrame implements ActionListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final int PRINTRANK = 10;
	private int currentPage = 0;
	private int line = 9;
	private int numberOfPages = 0;
	JLabel rankInfo[] = new JLabel[3];
	JLabel title = new JLabel("RANKING");
	JLabel rank[][] = new JLabel[PRINTRANK][3];
	JButton ok = new JButton("CLOSE");
	JButton restart = new JButton("RE?");
	JButton next = new JButton("NEXT▶");
	JButton back = new JButton("◀BACK");
	JPanel panel = new JPanel();
	
	public ScoreBoard(int second, boolean is4X4) throws IOException
	{
		
		// 4 X 4 게임이면 랭킹에 기록 가능
		if(is4X4)
		{
			String name = null;
			
			Functions ft= new Functions();
			
			Scanner in = new Scanner(new File("puzzleRank"));
			PrintWriter out = new PrintWriter(new File("temp"));
			in.nextLine();	// 맨 위에 한 줄 제외
			
			StringBuilder sb = new StringBuilder("");
			int[] temp = new int[1];
			int rank = 1;
			boolean inRank = false;
			while(in.hasNext())
			{
				temp = ft.returnNumbers(sb.append(in.nextLine()).toString());
				
				if(second < temp[temp.length - 1])
				{
					inRank = true;
					break;
				}
				else
				{
					sb.delete(0, sb.length());
					rank++;
				}
			}
			
			boolean end = false;
			
			while(!end)
			{
				name = JOptionPane.showInputDialog("Enter Name");
				
				// 이름을 입력하지 않고 확인을 눌렀을 때
				if(name == null)
				{
					int select = JOptionPane.showConfirmDialog(null, "Really exit this record?");
					// 확인 입력시 랭킹 기록 안 함
					if(select == 0)
						end = true;
				}
				
				else
				{
					if(name.equals(""))
						JOptionPane.showMessageDialog(null, "Please enter your name");
					else
					{
						if(name.length() > 5)
							JOptionPane.showMessageDialog(null, "please input less than 5 characters", "WARNING_TOO_LONG_NAME", JOptionPane.WARNING_MESSAGE);
						else
						{
							JOptionPane.showMessageDialog(null, "Your Rank :  " + rank);
							end = true;
							
							// 랭킹에 들어갔을 때
							if(inRank)
							{
								Scanner input = new Scanner(new File("puzzleRank"));
								for(int i=0; i<rank; i++)
									out.println(input.nextLine());
								out.println(rank + "\t" + name + "\t" + second + "초");	// 기록을 세운 현재 사용자의 정보를 파일에 기록
								// 현재 자리에 있던 사용자를 한칸 밀어서 기록
								String nameInFile = sb.toString().substring(ft.numberOfDigit(temp[0])+1, sb.length() - ft.numberOfDigit(temp[temp.length - 1]) - 2);
								out.println(temp[0]+1 + "\t" + nameInFile+ "\t" + temp[temp.length - 1] + "초"); 
								
								sb.delete(0, sb.length());
								// 뒤에 있던 사용자들의 랭크를 하나씩 밀어서 기록
								while(in.hasNextLine())
								{
									temp = ft.returnNumbers(sb.append(in.nextLine()).toString());
									nameInFile = sb.toString().substring(ft.numberOfDigit(temp[0])+1, sb.length() - ft.numberOfDigit(temp[temp.length - 1]) - 2);
									out.println(temp[0]+1 + "\t" + nameInFile+ "\t" + temp[temp.length - 1] + "초");
									
									sb.delete(0, sb.length());
								}
								
								input.close();
							}
						
							// 랭크에 들어가지 못했을 때
							else
							{
								in = new Scanner(new File("puzzleRank"));
								while(in.hasNextLine())
									out.println(in.nextLine());
								out.println(rank + "\t" + name+ "\t" + second + "초");
							}
							
							in.close();
							out.close();
							
							in = new Scanner(new File("temp"));
							out = new PrintWriter(new File("puzzleRank"));
							
							while(in.hasNextLine())
								out.println(in.nextLine());
							
							in.close();
							out.close();
						}
					}
				}	
			}
			
			
		}
		
		// 4 X 4 게임이 아니면 기록이 안 됨
		else
			JOptionPane.showMessageDialog(null, "Only  4 X 4  can be Ranked", "WARNING_NOT_RANKED", JOptionPane.WARNING_MESSAGE);
	}
	
	/* 구성자 완료 **********************************************************************************/
	
	public void display() throws IOException
	{
		this.setLocation(700, 300);
		
		rankInfo[0] = new JLabel("Rank");
		rankInfo[1] = new JLabel("Name");
		rankInfo[2] = new JLabel("Score");
		rankInfo[0].setBorder(new EmptyBorder(10, 20, 10, 70));
		rankInfo[1].setBorder(new EmptyBorder(10, 70, 10, 10));
		rankInfo[2].setBorder(new EmptyBorder(10, 10, 10, 20));
		
		 GridBagLayout gbl = new GridBagLayout();
         GridBagConstraints gbc = new GridBagConstraints();
         gbc.fill = GridBagConstraints.BOTH;
         setLayout(gbl);

		Scanner in = new Scanner(new File("puzzleRank"));
		in.nextLine();
		
		for(int i=0; i<3; i++)
			rankInfo[i].setFont(rankInfo[i].getFont().deriveFont(30f));		
		
		addGrid(gbl, gbc, title, 1, 0, 1, 1, 0, 0);
		title.setFont(title.getFont().deriveFont(40f));	
		title.setBorder(new EmptyBorder(0, 50, 0, 0));
		
		for(int i=0; i<3; i++)
			addGrid(gbl, gbc, rankInfo[i], i, 1, 1, 1, 0, 0);	
		
		for(int i=0; i<PRINTRANK; i++)
			for(int j=0; j<3; j++)
			{
					rank[i][j] = new JLabel(in.next());
					rank[i][j].setFont(rank[i][j].getFont().deriveFont(20f));
					addGrid(gbl, gbc, rank[i][j], j, i+2, 1, 1, 0, 0);	
					
					if(j == 0)
						rank[i][j].setBorder(new EmptyBorder(0, 50, 3, 0));
					else if(j == 1)
						rank[i][j].setBorder(new EmptyBorder(0, 80, 3, 0));
					else
						rank[i][j].setBorder(new EmptyBorder(0, 10, 3, 0));
			}
		
		while(in.hasNextLine())
		{
			in.nextLine();
			line++;
			
			if(line % PRINTRANK == 9)
				numberOfPages++;
		}
		if(line % PRINTRANK != 0)
			numberOfPages++;
		
		panel.add(restart);
		panel.add(back);
		panel.add(next);
		panel.add(ok);
		addGrid(gbl, gbc, panel, 1, PRINTRANK + 2, 1, 1, 0, 0);
		panel.setBorder(new EmptyBorder(0, 0, 0, 40));

		restart.addActionListener(this);
		back.addActionListener(this);
		next.addActionListener(this);
		ok.addActionListener(this);
		
		pack();
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		in.close();
		
	}

	 private void addGrid(GridBagLayout gbl, GridBagConstraints gbc, Component c,  
             int gridx, int gridy, int gridwidth, int gridheight, int weightx, int weighty) {
       gbc.gridx = gridx;
       gbc.gridy = gridy;
       gbc.gridwidth = gridwidth;
       gbc.gridheight = gridheight;
       gbc.weightx = weightx;
       gbc.weighty = weighty;
       gbl.setConstraints(c, gbc);
       
       add(c);
	}
	 
	 
	 
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if((e.getSource().equals(restart)))
		{
			int select = JOptionPane.showConfirmDialog(restart, "Restart?", "Confirm Message", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

			if(select == 0)
			{
				this.dispose();
				this.setVisible(false);
				new Puzzle();
			}
		}
		
		else if((e.getSource().equals(ok)))
		{
			int select = JOptionPane.showConfirmDialog(ok, "Are you sure to close this program and return to previous page?", "Confirm Message", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

			if(select == 0)
			{
				this.dispose();
				this.setVisible(false);
				new FirstPage();
			}
		}
		
		else if((e.getSource().equals(back)))
		{
			// 현재 페이지가 첫 페이지면 명령 거부 ( 0 페이지부터 시작 )
			if(currentPage == 0)
				JOptionPane.showMessageDialog(null, "This is first page of the Ranking", "WARNING_FIRST_PAGE", JOptionPane.WARNING_MESSAGE);
			else
			{
				for(int i=0; i<PRINTRANK; i++)
					for(int j=0; j<3; j++)
						rank[i][j].setVisible(true);
				Scanner in;
				try {
					in = new Scanner(new File("puzzleRank"));
					in.nextLine();
					currentPage --;
					
					// 해당 위치까지 이동함
					for(int i=0; i<currentPage * PRINTRANK; i++)
						in.nextLine();
					
					// 해당 위치에서 출력
					for(int i=0; i<PRINTRANK; i++)
						for(int j=0; j<3; j++)
								rank[i][j].setText(in.next());
					
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		}
		
		else if((e.getSource().equals(next)))
		{
			// 현재 페이지가 마지막 페이지면 명령 거부 ( 0 페이지부터 시작 )
			if(currentPage == numberOfPages)
				JOptionPane.showMessageDialog(null, "This is last page of the Ranking", "WARNING_LAST_PAGE", JOptionPane.WARNING_MESSAGE);
			else
			{
				Scanner in;
				try {
					in = new Scanner(new File("puzzleRank"));
					in.nextLine();
					currentPage ++;
					
					// 해당 위치까지 이동함
					for(int i=0; i<currentPage * PRINTRANK; i++)
						in.nextLine();
					
					// 만약 현재 페이지가 마지막 페이지 바로 이전일 경우 마지막 페이지를 10개 출력하면 오류나니까
					if(currentPage == numberOfPages)
					{
						for(int i=0; i<PRINTRANK; i++)
							for(int j=0; j<3; j++)
							{
								if(i<=(line-1)%10)
									rank[i][j].setText(in.next());
								else
									rank[i][j].setVisible(false);
							}
								
					}
					
					// 해당 위치에서 출력
					else
						for(int i=0; i<PRINTRANK; i++)
							for(int j=0; j<3; j++)
									rank[i][j].setText(in.next());
					
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		}
	}
	
	public static void main(String[] args) throws IOException{
		// TODO Auto-generated method stub
		
		ScoreBoard sc = new ScoreBoard(100000, false);
		sc.display();
		
	}
}
