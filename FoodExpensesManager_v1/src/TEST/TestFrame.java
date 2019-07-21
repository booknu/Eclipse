package TEST;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import functions.CommonSources;
import objects.Expenses;
import objects.Log;
import objects.LogManager;
import objects.time.Date;
import objects.time.Time;

public class TestFrame extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JButton displayButton = new JButton("모든 계획 보기");
	JButton addRandomButton = new JButton("랜덤 계획 추가");
	JButton resetButton = new JButton("초기화");
	JButton addRandomLog = new JButton("랜덤 로그 추가");
	JButton logReset = new JButton("로그 초기화");

	
	public TestFrame()
	{
		setTitle("개발자 테스트용");
		setLayout(new GridLayout(5, 1));
		setSize(240,220);
		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		add(displayButton);
		add(addRandomButton);
		add(resetButton);
		add(addRandomLog);
		add(logReset);
		
		class ButtonListener implements ActionListener
		{
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getSource().equals(displayButton))
				{
					CommonSources.displayAllPlan();
					CommonSources.mainFrame.refresh();
				}
				
				else if(e.getSource().equals(resetButton))
				{
					CommonSources.resetAllPlan();
					CommonSources.mainFrame.refresh();
				}
				
				else if(e.getSource().equals(addRandomButton))
				{
					int howmuch = Integer.parseInt(JOptionPane.showInputDialog("몇 개나?"));
					int year = 2016; int month; int date; int expense; int content;
					String cont;
					
					Random ran = new Random();
					for(int i=0; i < howmuch; i++)
					{
						month = ran.nextInt(12) + 1;
						date = ran.nextInt(21) + 1;
						expense = ran.nextInt(11);
						content = ran.nextInt(3);
						
						expense = expense * 500 + 3500;
						
						switch(content)
						{
						case 0: cont = "유나인"; break;
						case 1: cont = "학식"; break;
						case 2: cont = "치킨"; break;
						default: cont = "";
						}

						Expenses exp = new Expenses(year, month, date, expense, cont);
						CommonSources.addExpenses(exp);
					}
					CommonSources.mainFrame.refresh();
				}
				
				else if(e.getSource().equals(addRandomLog))
				{
					int howmuch = Integer.parseInt(JOptionPane.showInputDialog("몇 개나?"));
					int year = 2016; int month; int date; int type;
					int hour; int min;
					Random ran = new Random();
					for(int i=0; i < howmuch; i++)
					{
						month = ran.nextInt(12) + 1;
						date = ran.nextInt(21) + 1;
						type = ran.nextInt(Log.TYPE_NUM);
						
						hour = ran.nextInt(24);
						min = ran.nextInt(60);
						
						LogManager.addLog(new Log(new Date(year, month, date), new Time(hour,min), type, ""));
					}
				}
				
				else if(e.getSource().equals(logReset))
				{
					CommonSources.resetLog();
					CommonSources.mainFrame.refresh();
				}
			}
			
		}
		
		ActionListener btnListener = new ButtonListener();
		displayButton.addActionListener(btnListener);
		addRandomButton.addActionListener(btnListener);
		resetButton.addActionListener(btnListener);
		addRandomLog.addActionListener(btnListener);
		logReset.addActionListener(btnListener);
	}

}
