package Clock;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class TimeClock extends JFrame implements ActionListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// 버튼 생성
	JPanel jPanel = new JPanel();
	JButton btn1 = new JButton("12 : 60 Clock");
	JButton btn2 = new JButton("24 : 60 Clock");
	JButton btn3 = new JButton("Custom Clock");
	JButton back = new JButton("Back");
	JButton logout = new JButton("Logout");

	//생성자 설정
	public TimeClock()
	{
		this.setLocation(700, 400);
		back.setOpaque(false);
		back.setMargin(new Insets(0, 0, 0, 0));
        back.setBackground(Color.black);
        back.setBorder(BorderFactory.createEmptyBorder());
        back.setBorderPainted( false );
        
        logout.setOpaque(false);
		logout.setMargin(new Insets(0, 0, 0, 0));
        logout.setBackground(Color.black);
        logout.setBorder(BorderFactory.createEmptyBorder());
        logout.setBorderPainted( false );
		
        btn1.setPreferredSize(new Dimension(120,100));
        btn2.setPreferredSize(new Dimension(120,100));
        
		 GridBagLayout gbl = new GridBagLayout();
         GridBagConstraints gbc = new GridBagConstraints();
         gbc.fill = GridBagConstraints.BOTH;
         setLayout(gbl);
		
         addGrid(gbl, gbc, back, 0, 0, 1, 1, 0, 0);
         addGrid(gbl, gbc, logout, 1, 0, 1, 1, 0, 0);
         addGrid(gbl, gbc, btn1, 0, 1, 1, 1, 0, 0);
         addGrid(gbl, gbc, btn2, 1, 1, 1, 1, 0, 0);
         addGrid(gbl, gbc, btn3, 0, 2, 2, 1, 0, 0);
		
		// 크기 및 보기 설정
		pack();
        setVisible(true);
		
		// 이벤트 등록하기
		btn1.addActionListener(this);
		btn2.addActionListener(this);
		btn3.addActionListener(this);
		back.addActionListener(this);
		logout.addActionListener(this);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
	
	/**
	 * 입력받은 문자열이 양수인지 판단해서 맞을 때 까지 반복해 양수를 return
	 * @param message 입력받을 때 보여줄 메세지
	 * @return 양수
	 */
	@SuppressWarnings("unused")
	private int returnPositiveNumber(String message)
	{
		boolean end = false;
		String str = JOptionPane.showInputDialog(message);
		
		while(!end)
		{
			try{
				   int n = Integer.parseInt(str);
				   
				   if(n>0)
					   return n;
				   else
				   {
					   JOptionPane.showMessageDialog(null, "잘못된 입력입니다. (양수를 입력해주세요!)");
					   str = JOptionPane.showInputDialog(message);
					   end = false;
				   }
					   
			}
			catch(Exception e){
				JOptionPane.showMessageDialog(null, "잘못된 입력입니다. (양수를 입력해주세요!)");
				str = JOptionPane.showInputDialog(message);
				end = false;
			}
		}
		
		return -1;
	}
	
	

	@Override
	public void actionPerformed(ActionEvent e)
	{
		//액션 리스너 재정의
		if (e.getSource().equals(btn1))
		{
			int select = JOptionPane.showConfirmDialog(btn1, "현재 시간을 불러올까요? (아니오를 선택하면 0부터 시작)", "현재시간 불러오기", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

			if(select == 0)
			{
				new WallClock("12 : 60 clock", true, true, 12, 60, 60);
			}
			
			else if(select == 1)
			{
				new WallClock("12 : 60 clock", true, 12, 60, 60);
			}
				
		}
		else if(e.getSource().equals(btn2))
		{
			int select = JOptionPane.showConfirmDialog(btn1, "현재 시간을 불러올까요? (아니오를 선택하면 0부터 시작)", "현재시간 불러오기", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

			if(select == 0)
			{
				new WallClock("12 : 60 clock", true, true, 24, 60, 60);
			}
			
			else if(select == 1)
			{
				new WallClock("12 : 60 clock", true, 24, 60, 60);
			}
		}
		
		else if(e.getSource().equals(btn3))
		{
			new SetTime();
		}
		
		else if(e.getSource().equals(back))
		{
			this.setVisible(false);
			new ClockApplication();
			this.dispose();
		}
		
		else if(e.getSource().equals(logout))
		{
			int select = JOptionPane.showConfirmDialog(btn1, "로그아웃 하시겠습니까?", "로그아웃", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

			if(select == 0)
			{
				this.setVisible(false);
				Login login = new Login();
				login.display();
				this.dispose();
			}
		}
	}
	
	private class SetTime extends Frame implements ActionListener
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		JButton ok = new JButton("설정 완료");
		JTextField hour = new JTextField("최대 시간",10);
		JTextField minute = new JTextField("최대 분", 10);
		JTextField second = new JTextField("최대 초", 10);
		JTextField currentH = new JTextField("현재 시간",10);
		JTextField currentM = new JTextField("현재 분", 10);
		JTextField currentS = new JTextField("현재 초", 10);
		
		JPanel panel1 = new JPanel();
		JPanel panel2 = new JPanel();
		JPanel panel3 = new JPanel();
		JFrame jFrame = new JFrame("시간 설정");
		public SetTime()
		{
			this.setLocation(700, 400);
			panel1.add(hour);
			panel1.add(minute);
			panel1.add(second);
			panel2.add(currentH);
			panel2.add(currentM);
			panel2.add(currentS);
			
			panel3.setLayout(new GridLayout(2,1));
			panel3.add(panel1);
			panel3.add(panel2);
			
			jFrame.add(panel3);
			jFrame.add(ok, BorderLayout.EAST);
			
			hour.addActionListener(this);
			minute.addActionListener(this);
			second.addActionListener(this);
			ok.addActionListener(this);
			
			jFrame.setSize(500, 200);
			jFrame.setVisible(true);
			pack();
		}
		/** 구성자 완료 **/
		
		private boolean isStringInteger(String s) {
		    try {
		        Integer.parseInt(s);
		        return true;
		    } catch (NumberFormatException e) {
		        return false;
		    }
		 }
		
		public void actionPerformed(ActionEvent e)
		{
			
			if(e.getSource().equals(ok))
			{
				String str;
				String[] texts = {hour.getText(), minute.getText(), second.getText(), currentH.getText(), currentM.getText(), currentS.getText()};
				boolean allInt = true;
				
				for(int i=0; i<texts.length; i++)
				{
					str = texts[i];
					if(!isStringInteger(str))
						allInt = false;
				}
				
				if(allInt)
				{
					int[] textsToInt = new int[6];
					for(int i=0; i<texts.length; i++)
						textsToInt[i] = Integer.parseInt(texts[i]);
					jFrame.setVisible(false);
					new WallClock("Custom Clock", false, textsToInt[0], textsToInt[1], textsToInt[2], textsToInt[3], textsToInt[4], textsToInt[5]);
					jFrame.dispose();
				}
				
				else
					JOptionPane.showMessageDialog(null, "제대로 된 입력을 해주세요");
			}
		}
	}

}