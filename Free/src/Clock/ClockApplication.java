package Clock;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class ClockApplication extends Frame implements ActionListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// 버튼 생성
	JFrame jFrame = new JFrame("Clock Application ");
	JButton btn1 = new JButton("Normal Clock");
	JButton btn2 = new JButton("Stop Watch");
	JButton back = new JButton("Back");
	JButton logout = new JButton("Logout");
	JPanel panel1 = new JPanel();
	JPanel panel2 = new JPanel();

	//생성자 설정
	public ClockApplication()
	{
		jFrame.setLocation(700, 400);
		
		// 레이아웃 설정
		panel1.setLayout(new GridLayout(1,2));
		panel2.setLayout(new GridLayout(1,2));

		// 버튼 추가
		panel1.add(btn1);
		panel1.add(btn2);
		
		panel2.add(back);
		panel2.add(logout);

		jFrame.add(panel2, "North");
		jFrame.add(panel1);
		
		// 글씨체, 크기
		btn1.setFont(btn1.getFont().deriveFont(15f));
		btn2.setFont(btn2.getFont().deriveFont(15f));
		back.setFont(back.getFont().deriveFont(12f));
		logout.setFont(logout.getFont().deriveFont(12f));
		btn1.setPreferredSize(new Dimension(200,150));
		
		// 바탕 투명
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
		
		// 크기 및 보기 설정
		jFrame.pack();
		jFrame.setVisible(true);
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


		// 이벤트 등록하기
		btn1.addActionListener(this);
		btn2.addActionListener(this);
		back.addActionListener(this);
		logout.addActionListener(this);
	}
	@Override
	public void actionPerformed(ActionEvent e)
	{
		//액션 리스너 재정의
		if (e.getSource().equals(btn1))
		{
			new TimeClock();
			jFrame.setVisible(false);
			jFrame.dispose();
		}
		else if(e.getSource().equals(btn2))
		{
			new StopWatch();
		}
		
		else if(e.getSource().equals(back))
		{
			jFrame.setVisible(false);
			new FirstPage();
			jFrame.dispose();
		}
		
		else if(e.getSource().equals(logout))
		{
			int select = JOptionPane.showConfirmDialog(btn1, "로그아웃 하시겠습니까?", "로그아웃", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

			if(select == 0)
			{
				jFrame.setVisible(false);
				Login login = new Login();
				login.display();
				jFrame.dispose();
			}
		}
	}
}
