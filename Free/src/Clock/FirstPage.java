package Clock;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class FirstPage extends JFrame implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JButton clockApp = new JButton("Clock App");
	JButton puzzle = new JButton("Puzzle Game");
	JButton back = new JButton("Back");
	JButton logout = new JButton("Logout");
	JPanel panel1 = new JPanel();
	JPanel panel2 = new JPanel();
	
	public FirstPage()
	{
		panel1.setLayout(new GridLayout(1,2));
		panel1.add(clockApp);
		panel1.add(puzzle);
		puzzle.setFont(puzzle.getFont().deriveFont(20f));
		clockApp.setFont(clockApp.getFont().deriveFont(20f));
		back.setFont(back.getFont().deriveFont(15f));
		logout.setFont(logout.getFont().deriveFont(15f));
		puzzle.setPreferredSize(new Dimension(200,150));
		
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
		
		panel2.setLayout(new GridLayout(1,2));
		panel2.add(back);
		panel2.add(logout);
		
		add(panel2, "North");
		add(panel1);
		
		pack();
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		back.addActionListener(this);
		logout.addActionListener(this);
		puzzle.addActionListener(this);
		clockApp.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource().equals(clockApp))
		{
			this.dispose();
			this.setVisible(false);
			new ClockApplication();
		}
		
		if(e.getSource().equals(puzzle))
		{
			this.dispose();
			this.setVisible(false);
			new Puzzle();
		}
		
		if(e.getSource().equals(back))
		{
			JOptionPane.showMessageDialog(null, "There is no page to go back", "WARNING_PAGE_NOT_EXIST", JOptionPane.WARNING_MESSAGE);
		}
		
		else if(e.getSource().equals(logout))
		{
			int select = JOptionPane.showConfirmDialog(logout, "로그아웃 하시겠습니까?", "로그아웃", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

			if(select == 0)
			{
				this.setVisible(false);
				Login login = new Login();
				login.display();
				this.dispose();
			}
		}
		
	}


}
