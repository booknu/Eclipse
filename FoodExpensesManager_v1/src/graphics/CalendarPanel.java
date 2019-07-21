package graphics;

import functions.CalendarFunc;
import functions.CommonSources;
import functions.NormalFunc;
import interfaces.RefreshablePanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolTip;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;
import javax.swing.plaf.metal.MetalToolTipUI;

import objects.Expenses;
import objects.time.Date;

/**
 * 캘린더 패널
 * 
 * ※주의사항
 * month는 0 ~ 11 로 1월 ~ 12월을 표현함
 * 요일은 1 ~ 7 로 일요일 ~ 토요일을 표현함
 * @author Administrator
 *
 */
public class CalendarPanel extends RefreshablePanel implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// 상수
	public static final int ROW = 6; // 줄의 개수 (실제로 들어가는 날짜들의)
	public static final int COL =  7; // 칸의 개수
	public static final Date DATE_FROM = new Date(1900, 1, 1);
	public static final Date DATE_TO = new Date(2300, 12, 31);


	// 변수
	private Calendar cal = Calendar.getInstance();
	private boolean unvisibled = false;
	private CButton lastClicked;
	public Date last_date;

	// 컴포넌트들
	JPanel yearMonthSetPanel = new JPanel();	// 년, 월을 조작하는 패널 (North)
	JPanel dayPanel= new JPanel(); // 요일을 보여주는 패널
	JPanel datePanel = new JPanel(); // 날짜를 보여주는 패널
	JPanel dataPanel = new JPanel(); // 실제 캘린더를 보여주는 패널 (day, date 패널을 포함) (Center)
	JPanel pagingPanel = new JPanel(); // 버튼으로 달력을 넘길 수 있게 조작하는 패널 (South)

	// 구성요소들
	CButton[] dates = new CButton[ROW * COL];	// 날짜가 들어가는 셀들
	JLabel[] days = new JLabel[COL]; // 요일이 들어가는 라벨

	JComboBox<Integer> yearBox = new JComboBox<Integer>();
	JComboBox<Integer> monthBox = new JComboBox<Integer>();

	/* 구성자 **************************************************************************************************/
	/**
	 * 아무 인자 없이 줄 때 
	 * default: y = 2015, m = 1
	 */
	public CalendarPanel()
	{
		this(new Date(2015, 1));
	}

	/**
	 * 처음 이 패널을 실행할 때 보여줄 년, 월을 인자로 줘야함
	 * @param year 년
	 * @param month 월 (0 ~ 11)
	 */
	public CalendarPanel(Date date)
	{
		last_date = date;
		initializeComps();
		setYearMonthPanel();
		setPagingPanel();

		// 패널에 하위 패널들을 추가
		add(yearMonthSetPanel, BorderLayout.NORTH);
		add(dataPanel, BorderLayout.CENTER);
		dataPanel.add(dayPanel, BorderLayout.NORTH);
		dataPanel.add(datePanel, BorderLayout.CENTER);
		add(pagingPanel, BorderLayout.SOUTH);

		// 해당 년, 월을 선택한 것 처럼 설정
		yearBox.setSelectedItem(date.getYear()); 
		monthBox.setSelectedItem(date.getMonth());

		addKeyListener(new MyKeyListener());
		addMouseWheelListener(new WheelListener());
		requestFocus();
	}

	/* 내부 클래스 *******************************************************************************/
	/**
	 * 달력의 날짜에 사용되는 버튼
	 * 
	 * @author Administrator
	 *
	 */
	class CButton extends JButton
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private ArrayList<Expenses> expenses = new ArrayList<Expenses>();	// 이 버튼에 들어가는 계획들
		private boolean activate = false; // 실질적으로 날짜가 들어가는 버튼인지
		private boolean today = false;
		private boolean clicked = false;

		public CButton()
		{
			super();
		}

		/* set 메소드 **************************************************************/
		public void setToday(boolean value) { 
			today = value; 
			if(today)
				setBackground(new Color(248, 251, 215));
			else
				setBackground(Color.WHITE);
		}
		public void setActivate(boolean value) { activate = value; }
		public void setClicked(boolean value) {
			clicked = value; 
			if(clicked)
				this.setBorder(new LineBorder(new Color(165, 160, 245), 3));
			else
				this.setBorder(new LineBorder(new Color(231, 231, 231), 1));
		}

		/**
		 * 날짜를 설정
		 * 설정하며 버튼을 활성화, 툴팁도 활성화
		 */
		public void setText(String text)
		{
			super.setText(text);
			activate = true;
		}
		/**
		 * 툴팁을 사용하도록 설정
		 */
		public void setDefaultToolTip()
		{
			String tooltip = "";
			int total = 0;
			for(Expenses exp : expenses)
			{
				tooltip += exp.getTooltipText() + "\n";
				total += exp.getExpense();
			}
			if(tooltip.equals(""))
				tooltip = "지출 없음";
			else
				tooltip += "Total: " + NormalFunc.getCompartedInt(total) + " 원";
			setToolTipText(tooltip);
		}

		public void setExpensesList(ArrayList<Expenses> exps)
		{
			expenses = exps;
		}

		/**
		 * 버튼을 초기 상태로 만든다.
		 */
		public void resetButton()
		{
			if(today)
			{
				setToday(false);
				setBorder(new LineBorder(new Color(231, 231, 231), 1));
			}
			setClicked(false);
			setActivate(false);
			super.setText("");
			expenses.clear();
			setToolTipText(null);
		}

		/* is 메소드 ********************************************************/
		public boolean isToday() { return today; }
		public boolean isActivate() { return activate; }
		public boolean isClicked() { return clicked; }

		/* get 메소드 ***********************************************************/
		public ArrayList<Expenses> getExpensesList()
		{
			return expenses;
		}
	}

	/* 여러 기본 설정 메소드 ******************************************************************************************************/
	/**
	 * 여러가지 것들을 초기화, 설정
	 */
	private void initializeComps()
	{
		// days 초기화
		days[0] = new JLabel("일");dayPanel.add(days[0]);
		days[1] = new JLabel("월");	dayPanel.add(days[1]);
		days[2] = new JLabel("화");	dayPanel.add(days[2]);
		days[3] = new JLabel("수");	dayPanel.add(days[3]);
		days[4] = new JLabel("목");	dayPanel.add(days[4]);
		days[5] = new JLabel("금");	dayPanel.add(days[5]);
		days[6] = new JLabel("토");	dayPanel.add(days[6]);

		for(JLabel label : days)
		{
			label .setHorizontalAlignment(JLabel.CENTER); // 가운데 정렬
			label.setOpaque(true); // background로 설정한 색깔을 볼 수 있게 함
			label.setForeground(new Color(73, 73, 73));	// 요일 색깔
			label.setBackground(Color.WHITE);	// 요일 색깔
			label.setBorder(new LineBorder(new Color(231, 231, 231), 1));
		}



		// cells 초기화
		ActionListener dateListener = new DateButtonListener();
		for(int i = 0; i < ROW * COL; i++)
		{
			dates[i] = new CButton() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				public JToolTip createToolTip() {
					MultiLineToolTip tip = new MultiLineToolTip();
					tip.setComponent(this);
					return tip;
				}
			};

			dates[i].setBackground(Color.WHITE); // 날짜 색깔
			dates[i] .setHorizontalAlignment(JLabel.CENTER); // 가운데 정렬
			// 토, 일요일에는 파랑, 빨강으로
			if(i % COL == 6)
				dates[i].setForeground(Color.BLUE);
			else if(i % COL == 0)
				dates[i].setForeground(Color.RED);
			datePanel.add(dates[i]);
			dates[i].addActionListener(dateListener);
		}

		// 컴포넌트들 설정
		yearMonthSetPanel.setLayout(new FlowLayout());
		dataPanel.setLayout(new BorderLayout());
		pagingPanel.setLayout(new FlowLayout());
		dayPanel.setLayout(new GridLayout(1, COL));
		datePanel.setLayout(new GridLayout(ROW, COL));
		setLayout(new BorderLayout());
	}

	/**
	 * yearMonthPanel에 들어갈 것들을 설정
	 */
	private void setYearMonthPanel()
	{
		// 이 메소드에서만 필요한 라벨을 지역변수로 선언
		JLabel yearLabel = new JLabel("년");
		JLabel monthLabel = new JLabel("월");

		// comboBox 내용을 채움
		for(int i = DATE_FROM.getYear(); i <= DATE_TO.getYear(); i++)
			yearBox.addItem(i);
		for(int i = DATE_FROM.getMonth(); i <= DATE_TO.getMonth(); i++)
			monthBox.addItem(i);

		yearMonthSetPanel.add(yearBox);
		yearMonthSetPanel.add(yearLabel);
		yearMonthSetPanel.add(monthBox);
		yearMonthSetPanel.add(monthLabel);

		yearBox.addActionListener(this);
		monthBox.addActionListener(this);
	}

	/**
	 * pagingPanel에 들어갈 것들을 설정
	 */
	private void setPagingPanel()
	{
		// 이 메소드에서만 필요한 버튼을 지역변수로 선언
		JButton goPrevious = new JButton("◀");
		JButton goNext = new JButton("▶");

		pagingPanel.add(goPrevious);
		pagingPanel.add(goNext);

		// 내부 액션리스너 클래스
		class PagingButtonListener implements ActionListener{
			@Override
			public void actionPerformed(ActionEvent e) {
				// ◀ 버튼이 눌린 경우
				if(e.getSource().equals(goPrevious))
					pagingCalendar(true);
				// ▶ 버튼이 눌린 경우
				else if(e.getSource().equals(goNext))
					pagingCalendar(false);
			}
		}

		ActionListener pagingListener = new PagingButtonListener();
		goPrevious.addActionListener(pagingListener);
		goNext.addActionListener(pagingListener);

	}

	/* 내부(private) 메소드 ***************************************************************************************************/
	/**
	 * 해당 년, 월에 맞게 캘린더를 설정함
	 * @param year 년
	 * @param month 월 (0 ~ 11)
	 */
	public void setCalendar(int year, int month)
	{
		// 이전 상태가 끝 부분을 잘라냈으면 복구
		if(unvisibled)
		{
			for(int i = 35; i < ROW * COL; i++)
				dates[i].setVisible(true);
			unvisibled = false;
		}

		// 모든 버튼들 클리어
		for(CButton btn : dates)
			btn.resetButton();

		cal.set(year, month, 1);
		int startPosition = cal.get(Calendar.DAY_OF_WEEK) - 1;
		int dateNum = functions.CalendarFunc.numberOfDays(year, month);

		int count = 1;
		for(int i = startPosition; i < (startPosition + dateNum); i++)
			dates[i].setText(count++ + "");

		// 달력의 끝 부분이 필요 없으면 잘라냄
		if(startPosition + dateNum <= 35)
		{
			for(int i = 35; i < ROW * COL; i++)
				dates[i].setVisible(false);
			unvisibled = true;
		}

		// 오늘 날짜가 있으면 강조함
		if(year == CalendarFunc.getCurrentYear() && month == CalendarFunc.getCurrentMonth())
		{
			for(CButton date : dates)
				if(date.getText().equals(""+CalendarFunc.getCurrentDate()))
					date.setToday(true);
		}
		
		// 최근 클릭한 날짜에 해당하는 버튼이 있으면 강조
		if(year == last_date.getYear() && month + 1 == last_date.getMonth())
		{
			for(CButton btn : dates)
				if(btn.getText().equals(""+last_date.getDate()))
					btn.setClicked(true);
		}

		// 각 날짜에 해당하는 expenses들을 버튼에 넣음
		for(CButton btn : dates)
		{
			if(!btn.getText().equals(""))
			{
				btn.setExpensesList(CommonSources.expManager.getCertainDateExpenses(new Date(year, month + 1, Integer.parseInt(btn.getText()))));
				btn.setDefaultToolTip();
			}
		}

		requestFocus();
	}

	/**
	 * 이전 달 or 다음 달로 달력을 넘김
	 * @param goPrevious 이전 달로 갈지
	 * 										true = 이전 달로, false = 다음 달로
	 */
	private void pagingCalendar(boolean goPrevious)
	{
		int curYear = (Integer)yearBox.getSelectedItem();
		int curMonth = (Integer)monthBox.getSelectedItem();
		Date tempDate = new Date(curYear, curMonth);

		// 이전으로
		if(goPrevious)
		{
			if(tempDate.isSameMonth(CalendarPanel.DATE_FROM))
				JOptionPane.showMessageDialog(null, "더 이상 이전으로 넘길 수 없습니다.");
			else
				tempDate.downMonth(1);
		}

		// 다음으로
		else
		{
			if(tempDate.isSameMonth(CalendarPanel.DATE_TO))
				JOptionPane.showMessageDialog(null, "더 이상 다음으로 넘길 수 없습니다.");
			else
				tempDate.upMonth(1);
		}
		yearBox.setSelectedItem(tempDate.getYear());
		monthBox.setSelectedItem(tempDate.getMonth());
	}

	/* public 메소드 *******************************************************/
	/**
	 * 업데이트 된 정보를 새로고침해서 보여줌
	 */
	@Override
	public void refresh() {
		// TODO Auto-generated method stub
		int year = (int)yearBox.getSelectedItem();
		int month = (int)monthBox.getSelectedItem();
		setCalendar(year, month - 1);
	}

	/* 키보드 제어 *************************************************************************************************/
	class MyKeyListener extends KeyAdapter {
		public void keyPressed(KeyEvent e) {
			int keyCode = e.getKeyCode();
			switch(keyCode)
			{
			case KeyEvent.VK_UP:
				pagingCalendar(true);
				break;
			case KeyEvent.VK_DOWN:
				pagingCalendar(false);
				break;
			case KeyEvent.VK_LEFT:
				pagingCalendar(true);
				break;
			case KeyEvent.VK_RIGHT:
				pagingCalendar(false);
				break;
			}
		}
	}

	/* 멀티 라인 툴팁 ***********************************************************/
	/**
	 * @version 1.0 11/09/98
	 */
	class MultiLineToolTipExample extends JFrame {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public MultiLineToolTipExample() {
			super("Multi-Line ToolTip Example");
			JButton button = new JButton("Hello, world") {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				public JToolTip createToolTip() {
					MultiLineToolTip tip = new MultiLineToolTip();
					tip.setComponent(this);
					return tip;
				}
			};
			button.setToolTipText("Hello\nworld");
			getContentPane().add(button);
		}
	}

	class MultiLineToolTip extends JToolTip {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public MultiLineToolTip() {
			setUI(new MultiLineToolTipUI());
		}
	}

	class MultiLineToolTipUI extends MetalToolTipUI {
		private String[] strs;

		@SuppressWarnings("unused")
		private int maxWidth = 0;

		public void paint(Graphics g, JComponent c) {
			@SuppressWarnings("deprecation")
			FontMetrics metrics = Toolkit.getDefaultToolkit().getFontMetrics(
					g.getFont());
			Dimension size = c.getSize();
			g.setColor(c.getBackground());
			g.fillRect(0, 0, size.width, size.height);
			g.setColor(c.getForeground());
			if (strs != null) {
				for (int i = 0; i < strs.length; i++) {
					g.drawString(strs[i], 3, (metrics.getHeight()) * (i + 1));
				}
			}
		}

		@SuppressWarnings({ "unchecked", "rawtypes" })
		public Dimension getPreferredSize(JComponent c) {
			@SuppressWarnings("deprecation")
			FontMetrics metrics = Toolkit.getDefaultToolkit().getFontMetrics(
					c.getFont());
			String tipText = ((JToolTip) c).getTipText();
			if (tipText == null) {
				tipText = "";
			}
			BufferedReader br = new BufferedReader(new StringReader(tipText));
			String line;
			int maxWidth = 0;
			Vector v = new Vector();
			try {
				while ((line = br.readLine()) != null) {
					int width = SwingUtilities.computeStringWidth(metrics, line);
					maxWidth = (maxWidth < width) ? width : maxWidth;
					v.addElement(line);
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			int lines = v.size();
			if (lines < 1) {
				strs = null;
				lines = 1;
			} else {
				strs = new String[lines];
				int i = 0;
				for (Enumeration e = v.elements(); e.hasMoreElements(); i++) {
					strs[i] = (String) e.nextElement();
				}
			}
			int height = metrics.getHeight() * lines;
			this.maxWidth = maxWidth;
			return new Dimension(maxWidth + 6, height + 4);
		}
	}

	/* 액션리스너 ***************************************************************************************************************/
	@Override
	public void actionPerformed(ActionEvent e) {

		// 콤보 박스를 건드릴 경우
		if(e.getSource().equals(yearBox) || e.getSource().equals(monthBox))
		{
			Integer year = (Integer)yearBox.getSelectedItem();
			Integer month = (Integer)monthBox.getSelectedItem();

			setCalendar(year, month - 1);

		}

		requestFocus(); // 액션 리스닝이 끝나면 CalendarPanel에 포커스를 맞춤
	}

	class DateButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e) {
			for(int i=0; i < ROW * COL; i++)
				if(e.getSource().equals(dates[i]))
					if(dates[i].isActivate())
					{
						int year = (int)yearBox.getSelectedItem();
						int month = (int)monthBox.getSelectedItem();
						int date = Integer.parseInt(dates[i].getText());
						CommonSources.mainFrame.dateDetailPanel.setCertainDate(new Date(year, month, date));

						if(lastClicked != null)
							lastClicked.setClicked(false);
						dates[i].setClicked(true);
						lastClicked = dates[i];
						last_date.setYear((Integer)yearBox.getSelectedItem());
						last_date.setMonth((Integer)monthBox.getSelectedItem());
						last_date.setDate(Integer.parseInt(lastClicked.getText()));
						
					}
		}
	}

	class WheelListener implements MouseWheelListener{
		@Override
		public void mouseWheelMoved(MouseWheelEvent e) {

			// 마우스가 위쪽으로 스크롤
			if(e.getWheelRotation() < 0)
				pagingCalendar(true);

			// 마우스가 아래쪽으로 스크롤
			else if(e.getWheelRotation() > 0)
				pagingCalendar(false);
		}
	}

}
