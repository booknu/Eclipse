package Clock;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
 
public class StopWatch extends JFrame implements ActionListener
{
 
		
		/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		JButton btn1 = new JButton("Start");
		JButton reset = new JButton("Reset");
        private JLabel clockDisplay;
        private Clock clock;
        private Timer timer;
        private final int DELAY = 1000;
        
        JPanel jpanel = new JPanel();
        JPanel panel = new JPanel();
 
        /**
         * Create a WallClock.
         */
        public StopWatch()
        {
        	this.setLocation(700, 400);
        	
               setTitle("StopWatch");
               clock = new Clock(10000, 60, 60);
               clock.setTime(0, 0, 0);
               clockDisplay = new JLabel(clock.getTime());
               clockDisplay.setFont(clockDisplay.getFont().deriveFont(64f));
               clockDisplay.setBorder(new EmptyBorder(10, 20, 10, 20));
               
               btn1.setPreferredSize(new Dimension(150,50));
               btn1.setFont(clockDisplay.getFont().deriveFont(15f));
               reset.setFont(clockDisplay.getFont().deriveFont(15f));
               
               jpanel.add(clockDisplay);
               panel.setLayout(new GridLayout(2,1));

               panel.add(btn1);
               panel.add(reset);
               
               jpanel.add(panel);
               add(jpanel);
               btn1.addActionListener(this);
               reset.addActionListener(this);
               
               timer = new Timer(DELAY, this);
               pack();
               setLocationRelativeTo(null);
               setVisible(true);
               
        }
        @Override
        public void actionPerformed(ActionEvent e)
        {
        	if(e.getSource().equals(timer))
        	{
        		clock.timeTick();
        		clockDisplay.setText(clock.getTime()); 
        	}
        	   
        	if(e.getSource().equals(btn1))
            {
            	if(btn1.getText().equals("Start"))
            	{
            		btn1.setText("Pause");
            		timer.start();
            	}
            	
            	else if(btn1.getText().equals("Pause"))
            	{
            		btn1.setText("Start");
            		timer.stop();
            	}
            }
        	
        	if(e.getSource().equals(reset))
            {
            	clock.setTime(0, 0, 0);
            	clockDisplay.setText(clock.getTime());
            }
        }
 
}
 
