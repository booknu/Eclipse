package graphics.statisticPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;

import org.jfree.chart.ChartPanel;
import org.jfree.data.time.TimeSeries;

import functions.CalendarFunc;
import functions.CommonSources;
import functions.NormalFunc;
import graphics.CalendarPanel;
import graphics.charts.TimeSeriesChart;
import graphics.charts.XYBarChartDemo;
import interfaces.RefreshablePanel;
import objects.Expenses;
import objects.ExpensesManager;
import objects.TAManage;
import objects.time.Date;

public class MonthDetailPanel extends RefreshablePanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static Date current_selected_date = new Date(CalendarFunc.getCurrentYear()
			, CalendarFunc.getCurrentMonth() + 1);
	public static int current_selected_graph = 0;
	public static int current_avr_exp;

	private Date date;
	private int ta_value;
	private long exp_value;
	private ArrayList<Expenses> exp_list;

	JPanel date_panel = new JPanel();
	JPanel content_panel = new JPanel();
	JPanel button_panel = new JPanel();
	JPanel graphPanel = new JPanel();
	private HashMap<Date, ArrayList<Expenses>> date_exp_map;	// key: 일 value: 해당 일에 있는 지출들의 ArrayList
	private HashMap<Date, Integer> date_expValue_map;	 // key: 일 value: 해당 일의 총 지출
	private HashMap<Date, Integer> date_remain_map; // key: 일 value: 해당 일의 남은 돈
	private HashMap<Date, Integer> date_ta_map;


	public MonthDetailPanel(Date date)
	{
		setVariables(date);

		initializeComps();
		setDatePanel();
		setContentPanel();
		setButtonPanel();
	}

	private void initializeComps()
	{
		setLayout(new BorderLayout());

		add(date_panel, BorderLayout.NORTH);
		add(content_panel, BorderLayout.CENTER);
		add(button_panel, BorderLayout.SOUTH);

		date_panel.setBackground(Color.WHITE);
		content_panel.setBackground(Color.WHITE);
		button_panel.setBackground(Color.WHITE);
	}

	/**
	 * 해당 년, 월에 해당하는 ta_value, exp_list를 재설정한다.
	 * @param date 날짜
	 */

	private void setVariables(Date date)
	{
		this.date = date;

		ta_value = TAManage.getTargetAmount(date.getCopyOfDate()).getTargetAmount();
		exp_list = CommonSources.expManager.getCertainMonthExpenses(date);

		exp_value = 0;
		for(Expenses exp : exp_list)
			exp_value += exp.getExpense();

		current_selected_date = new Date(date.getYear(), date.getMonth());

		// current_avr_exp 
		current_avr_exp = TAManage.getTargetAmountValue(date) /
				CalendarFunc.numberOfDays(date.getYear(), date.getMonth() - 1);
		
		// month_exp_map 채우기
		Date dateFrom = date.getCopyOfDate();
		Date dateTo = date.getCopyOfDate();
		dateFrom.setDate(1);
		if(CalendarFunc.getCurrentDateForm().isSameMonth(date))		// 이번달인 경우 현재 날짜까지만
			dateTo.setDate(CalendarFunc.getCurrentDateForm());
		else
			dateTo.setDate(CalendarFunc.numberOfDays(date.getYear(), date.getMonth() - 1));
		
		date_exp_map =
				new HashMap<Date, ArrayList<Expenses>>();
		Date tempDate = dateFrom.getCopyOfDate();
		while(!dateTo.isEarlierThan(tempDate))	// map에 넣음
		{
			date_exp_map.put(tempDate.getCopyOfDate(), 
					CommonSources.expManager.getCertainDateExpenses(tempDate));
			tempDate.upDate(1);
		}
		
		// month_expValue_map 채우기
		date_expValue_map = 
				new HashMap<Date, Integer>();
		Set<Date> keyData = date_exp_map.keySet();
		for(Date t_date : keyData)
			date_expValue_map.put(t_date.getCopyOfDate(), ExpensesManager.getSumOfExpenses(date_exp_map.get(t_date)));

		// ta_map 채우기
		date_ta_map = new HashMap<Date, Integer>();
		tempDate = dateFrom.getCopyOfDate();
		while(!dateTo.isEarlierThan(tempDate))	// map에 넣음
		{
			date_ta_map.put(tempDate.getCopyOfDate(), current_avr_exp);
			tempDate.upDate(1);
		}
		
		// month_remain_map 채우기
		date_remain_map = new HashMap<Date, Integer>();
		keyData = date_exp_map.keySet();
		for(Date t_date : keyData)
			date_remain_map.put(t_date.getCopyOfDate(),  current_avr_exp
					- ExpensesManager.getSumOfExpenses(date_exp_map.get(t_date)));
	}

	private void setDatePanel()
	{
		date_panel.setLayout(new FlowLayout());
		if(CalendarFunc.isSameMonth(date, 
				new Date(CalendarFunc.getCurrentYear(), CalendarFunc.getCurrentMonth() + 1)))
			date_panel.setBackground(new Color(248, 251, 215));
		JComboBox<Integer> yearBox = new JComboBox<Integer>();
		JLabel yearLabel = new JLabel("년");
		JComboBox<Integer> monthBox = new JComboBox<Integer>();
		JLabel monthLabel = new JLabel("월");

		// comboBox 내용을 채움
		for(int i = CalendarPanel.DATE_FROM.getYear(); i <= CalendarPanel.DATE_TO.getYear(); i++)
			yearBox.addItem(i);
		for(int i = CalendarPanel.DATE_FROM.getMonth(); i <= CalendarPanel.DATE_TO.getMonth(); i++)
			monthBox.addItem(i);

		yearBox.setSelectedItem(date.getYear());
		monthBox.setSelectedItem(date.getMonth());
		class BoxListener implements ActionListener
		{

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int year = (Integer)yearBox.getSelectedItem();
				int month = (Integer)monthBox.getSelectedItem();
				setDate(new Date(year, month));

				if(CalendarFunc.isSameMonth(date, 
						new Date(CalendarFunc.getCurrentYear(), CalendarFunc.getCurrentMonth() + 1)))
					date_panel.setBackground(new Color(248, 251, 215));
				else
					date_panel.setBackground(Color.WHITE);
			}
		}
		ActionListener box_listener = new BoxListener();
		yearBox.addActionListener(box_listener);
		monthBox.addActionListener(box_listener);

		date_panel.add(yearBox);
		date_panel.add(yearLabel);
		date_panel.add(monthBox);
		date_panel.add(monthLabel);

		// 액션 리스너
		class WheelListener implements MouseWheelListener{
			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {

				// 마우스가 위쪽으로 스크롤
				if(e.getWheelRotation() < 0)
					pagingCalendar(true, yearBox, monthBox);

				// 마우스가 아래쪽으로 스크롤
				else if(e.getWheelRotation() > 0)
					pagingCalendar(false, yearBox, monthBox);
			}
		}

		addMouseWheelListener(new WheelListener());
	}

	private void setContentPanel()
	{
		/* 모든 공통 사항 **/
		content_panel.setLayout(null);

		// JProgressBar
		JProgressBar remaining_bar = new JProgressBar();
		remaining_bar.setLocation(30,20);
		remaining_bar.setSize(200, 20);

		remaining_bar.setMaximum(ta_value);
		remaining_bar.setValue(ta_value - (int)exp_value);
		if(ta_value - exp_value < 0)
			remaining_bar.setString(NormalFunc.getCompartedInt(ta_value - exp_value) + "원");
		else
			remaining_bar.setString(NormalFunc.getCompartedInt(remaining_bar.getValue()) + "원 / " 
					+ NormalFunc.getCompartedInt(remaining_bar.getMaximum()) + "원");
		remaining_bar.setStringPainted(true);

		// 목표 Label
		JLabel targetAmount = new JLabel("목표:   " + NormalFunc.getCompartedInt(ta_value) + "원");
		targetAmount.setLocation(30,60);
		targetAmount.setSize(400, 20);
		targetAmount.setFont(new Font("나눔손글씨 펜", Font.PLAIN, 23));
		targetAmount.setForeground(new Color(68, 131, 164));
		// 지출 Label
		JLabel expense = new JLabel("지출:   " + NormalFunc.getCompartedInt(exp_value) + "원");
		expense.setLocation(30,92);
		expense.setSize(400, 20);
		expense.setFont(new Font("나눔손글씨 펜", Font.PLAIN, 23));
		expense.setForeground(new Color(173, 58, 62));
		// 남은 돈 Label
		JLabel remain = new JLabel();
		remain.setText("남은 돈:   " + NormalFunc.getCompartedInt(ta_value - exp_value) + "원");
		remain.setLocation(30,124);
		remain.setSize(400, 20);
		remain.setFont(new Font("나눔손글씨 펜", Font.PLAIN, 23));
		remain.setForeground(new Color(100, 147, 96));

		content_panel.add(remaining_bar);
		content_panel.add(targetAmount);
		content_panel.add(expense);
		content_panel.add(remain);

		// 선택된 것이 이번 달인 경우
		if(CalendarFunc.isSameMonth(date, 
				new Date(CalendarFunc.getCurrentYear(), CalendarFunc.getCurrentMonth() + 1)))
		{
			// JProgressBar
			JProgressBar dateBar = new JProgressBar();
			dateBar.setLocation(285,92);
			dateBar.setSize(150, 17);

			dateBar.setMaximum(CalendarFunc.numberOfDays(date.getYear(), date.getMonth() - 1));
			dateBar.setValue(CalendarFunc.getCurrentDate());
			dateBar.setStringPainted(true);

			dateBar.setString(CalendarFunc.getCurrentDate() + "일 / " 
					+ CalendarFunc.numberOfDays(date.getYear(), date.getMonth() - 1) + "일");



			// 남은 날짜 Label
			JLabel remainDate = new JLabel((CalendarFunc.numberOfDays(date.getYear(), date.getMonth() - 1) 
					- CalendarFunc.getCurrentDate()) + 1 + "일 남음");
			remainDate.setLocation(320,60);
			remainDate.setSize(400, 20);
			remainDate.setFont(new Font("양재난초체M", Font.BOLD, 18));


			// 권하는 하루 평균 사용 비용
			JLabel expDaySpend = new JLabel();
			long rm_v = ta_value - exp_value;
			int rmDay = CalendarFunc.numberOfDays(date.getYear(), date.getMonth() - 1) 
					- CalendarFunc.getCurrentDate() + 1;
			double sp;
			if(rmDay > 5)
				sp = 1.0 * rm_v / (rmDay * (25.0 / 30.0));
			else
				sp = 1.0 * rm_v / rmDay;
			expDaySpend.setText("권장 일일 지출:  " + NormalFunc.getCompartedInt((long)sp) + "원");
			expDaySpend.setLocation(285,124);
			expDaySpend.setSize(400, 20);
			expDaySpend.setFont(new Font("나눔손글씨 펜", Font.PLAIN, 21));
			if(rm_v < 0)
				expDaySpend.setText("권장 일일 지출:  " + 0 + "원");

			// 남은 돈 Bar 색깔 관리
			double exp_rm = ta_value - (1.0 * ta_value / CalendarFunc.numberOfDays(date.getYear(), date.getMonth() - 1)) 
					* CalendarFunc.getCurrentDate();	//	예상 남은 돈
			double rm = ta_value - exp_value; // 실제 남은 돈
			if(rm > exp_rm * 1.1 || CalendarFunc.numberOfDays(date.getYear(), date.getMonth() - 1) - CalendarFunc.getCurrentDate() < 3)	// 양호 (실제 > 1.2 예상 or 현재 날짜가 1,2일)
				remaining_bar.setForeground(new Color(49, 232, 17));
			else if(rm > exp_rm * 1.0)
				remaining_bar.setForeground(new Color(79, 151, 240));
			else if(rm > exp_rm * 0.9)
				remaining_bar.setForeground(new Color(250, 158, 5));
			else
				remaining_bar.setForeground(new Color(230, 121, 121));

			content_panel.add(remainDate);
			content_panel.add(dateBar);
			content_panel.add(expDaySpend);
			
			// 그래프 패널 추가
			graphPanel.setBackground(Color.WHITE);
			graphPanel.setLocation(0, 185);
			graphPanel.setSize(500, 500);
			content_panel.add(graphPanel);
			setGraphPanel();
		}

		// 이미 지나간 달이면
		else if(CalendarFunc.isEarlierThan(date, 
				new Date(CalendarFunc.getCurrentYear(), CalendarFunc.getCurrentMonth() + 1)))
		{
			double rm_ratio = 1.0 * (ta_value - exp_value) / ta_value;
			if(rm_ratio >= 0.3)	// 양호 (실제 > 1.2 예상 or 현재 날짜가 1,2일)
				remaining_bar.setForeground(new Color(49, 232, 17));
			else if(rm_ratio >= 0.2)
				remaining_bar.setForeground(new Color(79, 151, 240));
			else if(rm_ratio >= 0.1)
				remaining_bar.setForeground(new Color(250, 158, 5));
			else
				remaining_bar.setForeground(new Color(230, 121, 121));
			
			// 그래프 패널 추가
			graphPanel.setBackground(Color.WHITE);
			graphPanel.setLocation(0, 200);
			graphPanel.setSize(500, 500);
			content_panel.add(graphPanel);
			setGraphPanel();
		}

		else
		{
			remaining_bar.setForeground(new Color(49, 232, 17));
		}
		

	}

	private void setButtonPanel()
	{
		button_panel.setLayout(new FlowLayout());

		JButton edit_target_amount = new JButton("목표액 수정");
		edit_target_amount.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				boolean ok = false;
				while(!ok)
				{
					String str = JOptionPane.showInputDialog(date.getYear() + "년 " + date.getMonth() + "월 목표액을 적어주세요.");
					if(str == null)
						ok = true;
					else
					{
						if(NormalFunc.isStringInt(str))
						{
							TAManage.modTargetAmount(date.getCopyOfDate(), Integer.parseInt(str));
							ok = true;
							refresh();
						}
						else
							JOptionPane.showMessageDialog(null, "잘못된 형식입니다.");
					}
				}
			}

		});

		JButton edit_default_ta = new JButton("목표 기본값 수정");
		edit_default_ta.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				boolean ok = false;
				while(!ok)
				{
					String str = JOptionPane.showInputDialog("설정하실 기본 목표액을 적어주세요.");
					if(str == null)
						ok = true;
					else
					{
						if(NormalFunc.isStringInt(str))
						{
							CommonSources.default_targetAmount = Integer.parseInt(str);
							ok = true;
							refresh();
						}
						else
							JOptionPane.showMessageDialog(null, "잘못된 형식입니다.");
					}
				}
			}

		});



		button_panel.add(edit_target_amount);
		button_panel.add(edit_default_ta);
	}

	private void setGraphPanel()
	{
		ChartPanel chart;
		// 그래프가 그려지는 곳 관리
		// 공통 부분 //
		graphPanel.removeAll();
		// 지출 그래프 //
		if(current_selected_graph == 0)
		{
			
			TimeSeries s1 = 
					TimeSeriesChart.makeDateSeries("지출", new HashMap<Date, Integer>(date_expValue_map));
			TimeSeries s2 = 
					TimeSeriesChart.makeDateSeries("목표액", new HashMap<Date, Integer>(date_ta_map));

			ArrayList<TimeSeries> ts_list = new ArrayList<TimeSeries>();
			ts_list.add(s1); ts_list.add(s2);

			TimeSeriesChart t = new TimeSeriesChart(ts_list, "이 달의 지출", "날짜", "원", false);
			chart = t.getChart();
			graphPanel.add(chart);
		}
		// 남은 돈 그래프 //
		else
		{
			TimeSeries s1 = 
					TimeSeriesChart.makeDateSeries("잔액", new HashMap<Date, Integer>(date_remain_map));

			ArrayList<TimeSeries> ts_list = new ArrayList<TimeSeries>();
			ts_list.add(s1);

			XYBarChartDemo t = new XYBarChartDemo(ts_list, "이 달의 잔액", "날짜", "원", false);
			chart = t.getChart();
			graphPanel.add(chart);
		}

		// 그래프 종류 고르는 곳 관리
		JRadioButton rb1 = new JRadioButton("지출 그래프");
		rb1.setBackground(Color.WHITE);
		JRadioButton rb2 = new JRadioButton("잔액 그래프");
		rb2.setBackground(Color.WHITE);

		ButtonGroup bg = new ButtonGroup();
		bg.add(rb1); bg.add(rb2);
		if(current_selected_graph == 0)
			bg.setSelected(rb1.getModel(), true);
		else if(current_selected_graph == 1)
			bg.setSelected(rb2.getModel(), true);

		rb1.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				current_selected_graph = 0;
				refresh();
			}
		});
		rb2.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				current_selected_graph = 1;
				refresh();
			}
		});

		JButton b = new JButton("크게 보기");
		b.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new LargeChartFrame(chart);
			}
		});

		graphPanel.add(rb1);
		graphPanel.add(rb2);
		graphPanel.add(b);
	}

	/**
	 * 이전 달 or 다음 달로 달력을 넘김
	 * @param goPrevious 이전 달로 갈지
	 * 										true = 이전 달로, false = 다음 달로
	 */
	private void pagingCalendar(boolean goPrevious, JComboBox<Integer> yearBox, JComboBox<Integer> monthBox)
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

	/* public 메소드 *****************************************************************************/
	public void setDate(Date date)
	{
		setVariables(date);

		content_panel.removeAll();
		graphPanel.removeAll();
		setContentPanel();
		CommonSources.resizeMainFrame();
	}

	public void refresh()
	{
		setDate(current_selected_date);
	}
}
