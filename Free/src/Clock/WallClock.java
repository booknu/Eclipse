package Clock;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
 
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
 
public class WallClock extends JFrame implements ActionListener
{
 
        /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		private JLabel timeDisplay;
        private JLabel dateDisplay;
        private Clock clock;
        private Timer timer;
        private final int DELAY = 1000;
 
        /**
         * Create a WallClock.
         */
        public WallClock(String appTitle, boolean date)
        {
        	this.setLocation(700, 400);
               setTitle(appTitle);
               clock = new Clock(12, 60, 60);
               clock.setTime(0, 0, 0);
               timeDisplay = new JLabel(clock.getTime());
               timeDisplay.setFont(timeDisplay.getFont().deriveFont(64f));
               timeDisplay.setBorder(new EmptyBorder(10, 20, 10, 20));
               add(timeDisplay);
 
               timer = new Timer(DELAY, this);
               timer.start();
               pack();
               setLocationRelativeTo(null);
               setVisible(true);
               
               if(date && clock.isAmPm())
               {
            	   clock.setDate();
	               dateDisplay = new JLabel(clock.getDate());
	               dateDisplay.setFont(timeDisplay.getFont().deriveFont(32f));
	               dateDisplay.setBorder(new EmptyBorder(100, 115, 10, 115));
	               add(dateDisplay);
	               pack();
               }
              
        }
        
        public WallClock(String appTitle, boolean date, int hour, int minute, int second)
        {
        	this.setLocation(700, 400);
               setTitle(appTitle);
               clock = new Clock(hour, minute, second);
               clock.setTime(0, 0, 0);
               timeDisplay = new JLabel(clock.getTime());
               timeDisplay.setFont(timeDisplay.getFont().deriveFont(64f));
               timeDisplay.setBorder(new EmptyBorder(10, 20, 10, 20));
               add(timeDisplay);
 
               timer = new Timer(DELAY, this);
               timer.start();
               pack();
               setLocationRelativeTo(null);
               setVisible(true);
               
               if(date && clock.isAmPm())
               {
            	   clock.setDate();
	               dateDisplay = new JLabel(clock.getDate());
	               dateDisplay.setFont(timeDisplay.getFont().deriveFont(32f));
	               dateDisplay.setBorder(new EmptyBorder(100, 115, 10, 115));
	               add(dateDisplay);
	               pack();
               }
               
               if(date && !clock.isAmPm())
               {
            	   clock.setDate();
	               dateDisplay = new JLabel(clock.getDate());
	               dateDisplay.setFont(timeDisplay.getFont().deriveFont(32f));
	               dateDisplay.setBorder(new EmptyBorder(100, 57, 10, 57));
	               add(dateDisplay);
	               pack();
               }
        }
        
        public WallClock(String appTitle, boolean date, boolean currentTime, int hour, int minute, int second)
        {	
        	this.setLocation(700, 400);
               setTitle(appTitle);
               clock = new Clock(hour, minute, second);
               setCurrentTime();
               timeDisplay = new JLabel(clock.getTime());
               timeDisplay.setFont(timeDisplay.getFont().deriveFont(64f));
               timeDisplay.setBorder(new EmptyBorder(10, 20, 10, 20));
               add(timeDisplay);
 
               timer = new Timer(DELAY, this);
               timer.start();
               pack();
               setLocationRelativeTo(null);
               setVisible(true);
               
               if(date && clock.isAmPm())
               {
            	   clock.setDate();
	               dateDisplay = new JLabel(clock.getDate());
	               dateDisplay.setFont(timeDisplay.getFont().deriveFont(32f));
	               dateDisplay.setBorder(new EmptyBorder(100, 115, 10, 115));
	               add(dateDisplay);
	               pack();
               }
               
               if(date && !clock.isAmPm())
               {
            	   clock.setDate();
	               dateDisplay = new JLabel(clock.getDate());
	               dateDisplay.setFont(timeDisplay.getFont().deriveFont(32f));
	               dateDisplay.setBorder(new EmptyBorder(100, 57, 10, 57));
	               add(dateDisplay);
	               pack();
               }
        }
 
        public WallClock(String appTitle, boolean date, int hour, int minute,int second, int currentHour, int currentMinute, int currentSecond)
        {
        		this.setLocation(700, 400);
               setTitle(appTitle);
               clock = new Clock(hour, minute, second);
               clock.setTime(currentHour%hour, currentMinute%hour, currentSecond%hour);
               timeDisplay = new JLabel(clock.getTime());
               timeDisplay.setFont(timeDisplay.getFont().deriveFont(64f));
               timeDisplay.setBorder(new EmptyBorder(10, 20, 10, 20));
               add(timeDisplay);
 
               timer = new Timer(DELAY, this);
               timer.start();
               pack();
               setLocationRelativeTo(null);
               setVisible(true);
               
               if(date && clock.isAmPm())
               {
            	   clock.setDate();
	               dateDisplay = new JLabel(clock.getDate());
	               dateDisplay.setFont(timeDisplay.getFont().deriveFont(32f));
	               dateDisplay.setBorder(new EmptyBorder(100, 115, 10, 115));
	               add(dateDisplay);
	               pack();
               }
               
               if(date && !clock.isAmPm())
               {
            	   clock.setDate();
	               dateDisplay = new JLabel(clock.getDate());
	               dateDisplay.setFont(timeDisplay.getFont().deriveFont(32f));
	               dateDisplay.setBorder(new EmptyBorder(100, 57, 10, 57));
	               add(dateDisplay);
	               pack();
               }
        }
        
        private void setCurrentTime()
        {
        	SimpleDateFormat sdf = new SimpleDateFormat("YYYY년 M월 d일 k시 m분 s초");
    		Date today = new Date();
    		String time = sdf.format(today);
    		
    		Functions ft = new Functions();
    		int i [] = ft.returnNumbers(time);
    		
    		clock.setTime(i[3], i[4], i[5]);
        }
        
        public void actionPerformed(ActionEvent e)
        {
               clock.timeTick();
               timeDisplay.setText(clock.getTime());
        }
 
}
 
