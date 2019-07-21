package Clock;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class FindID extends JFrame implements ActionListener{
	
	JLabel name;
	JLabel email;
	JLabel blank1 = new JLabel("   ");
	JLabel blank2 = new JLabel("   ");
	JTextField inputName;
	JTextField inputEmail;
	JButton ok;
	JButton cancle;
	
	JPanel panel1 = new JPanel();
	JPanel panel2 = new JPanel();
	
	public FindID()
	{
		this.setLocation(700, 400);
		name = new JLabel("    Input Name    ");
		email = new JLabel("    Input E-Mail     ");
		inputName = new JTextField("Name", 15);
		inputEmail = new JTextField("example@****.com", 15);
		ok = new JButton("Find ID");
		cancle = new JButton("Cancle");
	
	}
	
	public void display()
	{	
		name.setFont(name.getFont().deriveFont(25f));
		inputName.setFont(inputName.getFont().deriveFont(20f));
		email.setFont(name.getFont().deriveFont(25f));
		inputEmail.setFont(name.getFont().deriveFont(20f));
		ok.setFont(ok.getFont().deriveFont(15f));
		cancle.setFont(cancle.getFont().deriveFont(15f));
		
		GridBagLayout gbl = new GridBagLayout();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        setLayout(gbl);
        
        blank1.setBorder(BorderFactory.createEmptyBorder(0 , 0 , 0, 0)); //상하좌우 10씩 띄우기
        blank2.setBorder(BorderFactory.createEmptyBorder(0 , 0 , 10 , 0)); //상하좌우 10씩 띄우기
        
        panel1.add(ok);
        panel1.add(cancle);
        
        addGrid(gbl, gbc, name, 0, 0, 2, 1, 0, 0);
        addGrid(gbl, gbc, inputName, 2, 0, 1, 1, 0, 0);
        addGrid(gbl, gbc, blank1, 0, 1, 1, 1, 0, 0);
        
        addGrid(gbl, gbc, email, 0, 2, 2, 1, 0, 0);
        addGrid(gbl, gbc, inputEmail, 2, 2, 1, 1, 0, 0);
        addGrid(gbl, gbc, blank2, 0, 3, 1, 1, 0, 0);

        addGrid(gbl, gbc, panel1, 1, 4, 3, 1, 0, 0);
        
        pack();
        setVisible(true);
        
        ok.addActionListener(this);
        cancle.addActionListener(this);
        
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
	
	 class Exit extends WindowAdapter
	   {
	      public void windowClosing(WindowEvent e) //WindowAdapter class Overriding
	      {
	        dispose(); //내가 사용하던 자원(memory)을 해제하는 method
	        System.exit(0); //프로그램 종료 하기
	      }
	   }
	 
	 public void actionPerformed(ActionEvent e)
		{
			// ok 버튼을 눌렀을 때
			if(e.getSource().equals(ok))
			{
				GetClientData gcd;
				
				try {
					gcd = new GetClientData("email", inputEmail.getText());
					
					if(gcd.isError())
						JOptionPane.showMessageDialog(null, "There is no corresponding ID");
					
					else if(gcd.getData("name").equals(inputName.getText()))
					{
						this.setVisible(false);
						this.dispose();
						JOptionPane.showMessageDialog(null, "Your ID is : " + gcd.getData("id"));
						Login login = new Login();
						login.display();
					}
						
					else
						JOptionPane.showMessageDialog(null, "There is no corresponding ID");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}			
			}
			
			// cancle 버튼 눌렀을 떄
			if(e.getSource().equals(cancle))
			{
				this.setVisible(false);
				Login li = new Login();
				li.display();
				this.dispose();
			}
		}

}
