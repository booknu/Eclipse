package Clock;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class Login extends JFrame implements ActionListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JLabel label1 = new JLabel("      ID");
	JLabel label2 = new JLabel("      PW");
	JLabel blank1 = new JLabel("  ");
	JLabel blank2 = new JLabel("  ");
	JPanel panel1 = new JPanel();
	JPanel panel2 = new JPanel();
	JPanel panel3 = new JPanel();
	JPanel panel4 = new JPanel();
	JButton loginBtn = new JButton("Login"); // 로그인 버튼
	JButton signBtn = new JButton("SignIn");
	JButton findIdBtn = new JButton("      Forgot Your ID?   ");
	JButton findPwBtn = new JButton("   Forgot Yout Password?");
	LoginProcess login;
	
	 JTextField id;	// id입력란
	 TextArea textArea; // 텍스트 입력란
	 JScrollPane jp;
	 JPasswordField pwd; // pw입력란
	
	public Login()
	{
		super("Login");
		//텍스트 입력
		id= new JTextField("ID", 10);

		//스크롤바가 기본적으로 있는 AWT 텍스트 필드
		textArea = new TextArea("내용을 입력하세요.", 5, 10); //스크롤바 기본 있음.

		//Swing에서 스크롤바를 넣기 위해 아래와 같이 사용한다.
		jp  = new JScrollPane(textArea);//스클롤바 만들기

		//패스워드를 위한 필드
		pwd = new JPasswordField("PASSWORD", 10);
		
	}
	
	public void display()
	{
		this.setLocation(700, 400);
		signBtn.setOpaque(false);
		signBtn.setMargin(new Insets(0, 0, 0, 0));
        signBtn.setBackground(Color.black);
        signBtn.setBorder(BorderFactory.createEmptyBorder());
        signBtn.setBorderPainted( false );
		
		findIdBtn.setOpaque(false);
		findIdBtn.setMargin(new Insets(0, 0, 0, 0));
        findIdBtn.setBackground(Color.black);
        findIdBtn.setBorder(BorderFactory.createEmptyBorder());
        findIdBtn.setBorderPainted( false );
        
        findPwBtn.setOpaque(false);
        findPwBtn.setMargin(new Insets(0, 0, 0, 0));
        findPwBtn.setBackground(Color.white);
        findPwBtn.setBorder(BorderFactory.createEmptyBorder());
        findPwBtn.setBorderPainted( false );
		
        label1.setFont(label1.getFont().deriveFont(30f));
        id.setFont(id.getFont().deriveFont(30f));
        label2.setFont(label1.getFont().deriveFont(30f));
        pwd.setFont(pwd.getFont().deriveFont(30f));
        findIdBtn.setFont(id.getFont().deriveFont(15f));
        loginBtn.setFont(id.getFont().deriveFont(40f));
        findPwBtn.setFont(id.getFont().deriveFont(15f));
        signBtn.setFont(id.getFont().deriveFont(15f));

        blank2.setBorder(BorderFactory.createEmptyBorder(10 , 0, 0, 0)); //상하좌우 10씩 띄우기
		
		GridBagLayout gbl = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        setLayout(gbl);
		
        addGrid(gbl, gbc, label1, 0, 0, 1, 1, 0, 0);
        addGrid(gbl, gbc, label2, 0, 1, 1, 1, 0, 0);
		addGrid(gbl, gbc, id, 1, 0, 1, 1, 0, 0);
		addGrid(gbl, gbc, pwd, 1, 1, 1, 1, 0, 0);
		addGrid(gbl, gbc, loginBtn, 3, 0, 1, 2, 1, 0);
		addGrid(gbl, gbc, blank2, 0, 2, 3, 1, 0, 0);
		addGrid(gbl, gbc, findIdBtn, 0, 3, 1, 1, 1, 0);
		addGrid(gbl, gbc, findPwBtn, 1, 3, 1, 1, 1, 0);
		addGrid(gbl, gbc, signBtn, 2, 3, 2, 1, 1, 0);
		setVisible(true);
		pack();
		loginBtn.addActionListener(this);
		id.addActionListener(this);
		pwd.addActionListener(this);
		findIdBtn.addActionListener(this);
		findPwBtn.addActionListener(this);
		
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
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Login md = new Login();
		
		md.display();
		
	}
	
	
	public void actionPerformed(ActionEvent e)
	{
		
		// 로그인 버튼 눌렀을 때 
		if(e.getSource().equals(loginBtn))
		{
			try {
				login = new LoginProcess(id.getText(), pwd.getPassword());

			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			if(login.getResult())
			{
				this.setVisible(false);
				GetClientData gd;
				try {
					gd = new GetClientData("id", id.getText());
					JOptionPane.showMessageDialog(null, "Welcome " + gd.getData("name")+" !");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				@SuppressWarnings("unused")
				FirstPage clock = new FirstPage();
				this.dispose();
			}
			
			else
			{
				JOptionPane.showMessageDialog(null, "Login Failed...");
			}
		}
		
		// 아이디 찾기 버튼 눌렀을 때
		if(e.getSource().equals(findIdBtn))
		{
			FindID fid = new FindID();
			fid.display();
			
			// 화면 넘어감 (이 메소드는 종료)
			this.setVisible(false);
			this.dispose();
		}
	}
}
