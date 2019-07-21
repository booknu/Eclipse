package graphics;

import functions.CalendarFunc;
import functions.MainFrameWindowAdapter;
import interfaces.RefreshablePanel;

import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.JFrame;

/**
 * 전체 프로그램을 관리함
 * @author Administrator
 *
 */
@SuppressWarnings("serial")
public class MainFrame extends JFrame{

	/* 기본 설정 *****************************************************************************************/
	public final double FRAME_RATIO = 2.0 / 3.0;	// 모니터 해상도 대비 창의 비율
	public int window_width;
	public int window_height;
	public int window_x;
	public int window_y;
	
	/* 패널들 *************************************************************************************/
	public ArrayList<RefreshablePanel> panels = new ArrayList<RefreshablePanel>();
	public CalendarPanel calendarPanel;
	public DateDetailPanel dateDetailPanel;
	public StatisticPanel statisticPanel;
	public ConsolePanel consolePanel;
	
	@SuppressWarnings("deprecation")
	public MainFrame()
	{
		initializeFrameConstant();	// 프레임 관련 변수 초기화
		
		// 프레임 관련 설정
		setTitle("식비 관리");
		
		setLayout(null);
		setLocation(window_x, window_y);
		setSize(window_width, window_height);
		addWindowListener(new MainFrameWindowAdapter());
		setVisible(true);
		
		// CalendarPanel 추가
		calendarPanel = new CalendarPanel(CalendarFunc.getCurrentDateForm());
		calendarPanel.setSize(500, 500);
		add(calendarPanel);
		panels.add(calendarPanel);
		
		// DateDetailPanel 추가
		dateDetailPanel = new DateDetailPanel(CalendarFunc.getCurrentDateForm());
		dateDetailPanel.setLocation(calendarPanel.getX() + calendarPanel.getWidth() + 15,
				calendarPanel.getY());
		add(dateDetailPanel);
		panels.add(dateDetailPanel);
		
		// StaticPanel 추가
		statisticPanel = new StatisticPanel();
		statisticPanel.setLocation(dateDetailPanel.getX() + dateDetailPanel.getWidth() + 20, dateDetailPanel.getY());
		statisticPanel.setSize(500, 700);
		add(statisticPanel);
		panels.add(statisticPanel);
		
		// ConsolePanel 추가
		consolePanel = new ConsolePanel();
		consolePanel.setLocation(0, calendarPanel.getY() + calendarPanel.getHeight() + 20);
		consolePanel.setSize(500, 275);
		add(consolePanel);
		
		
		resize(window_width, window_height + 1);
		resize(window_width, window_height);
		
		setResizable(false);

	}
	
	public void refresh()
	{
		for(RefreshablePanel rp : panels)
			rp.refresh();
	}
	
	public void logRefresh(String log_text)
	{
		consolePanel.refresh(log_text);
	}
	
	public void terminateLog()
	{
		consolePanel.terminateLog();
	}
	
	/**
	 * 프레임 관련 변수들을 설정
	 * 
	 * 
	 * window_width   window_height   window_x   window_y
	 */
	private void initializeFrameConstant()
	{
		int moniterWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
		int moniterHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
		
		window_width = 1360;
		window_height = 840;
		window_x = moniterWidth / 2 - window_width / 2;
		window_y = moniterHeight / 2 - window_height / 2;
	}

}
