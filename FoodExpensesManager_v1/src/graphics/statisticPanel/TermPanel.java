package graphics.statisticPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import org.jfree.chart.ChartPanel;
import org.jfree.data.time.TimeSeries;

import functions.CalendarFunc;
import functions.CommonSources;
import graphics.CalendarPanel;
import graphics.charts.TimeSeriesChart;
import graphics.charts.XYBarChartDemo;
import interfaces.RefreshablePanel;
import objects.Expenses;
import objects.ExpensesManager;
import objects.TAManage;
import objects.time.Date;

public class TermPanel extends RefreshablePanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static Date current_from = new Date(CalendarFunc.getCurrentYear(), CalendarFunc.getCurrentMonth() + 1);
	public static Date current_to = new Date(CalendarFunc.getCurrentYear(), CalendarFunc.getCurrentMonth() + 1);
	public static int current_selected_graph = 0; // 최근 선택된 그래프 종류 (0 = 지출, 1 = 남은 돈)
	public static boolean first = true;

	private Date from;
	private Date to;

	JPanel datePanel = new JPanel();
	JPanel graphPanel = new JPanel();
	JPanel contentPanel = new JPanel();
	private HashMap<Date, ArrayList<Expenses>> month_exp_map;	// key: 월 value: 해당 월에 있는 지출들의 ArrayList
	private HashMap<Date, Integer> month_expValue_map;	 // key: 월 value: 해당 월의 총 지출
	private HashMap<Date, Integer> month_ta_map;	// key: 월 value: 해당 월의 지출 목표
	private HashMap<Date, Integer> month_remain_map; // key: 월 value: 해당 월의 남은 돈

	public TermPanel(Date dateFrom, Date dateTo)
	{
		if(first)
		{
			current_from.downMonth(6);
			first = false;
		}

		setVariables(dateFrom, dateTo);

		initializeComps();
		setDatePanel();
		setGraphPanel();
		setContentPanel();
	}

	private void setVariables(Date dateFrom, Date dateTo)
	{
		this.from = dateFrom;
		this.to = dateTo;	

		current_from = from.getCopyOfDate();
		current_to = to.getCopyOfDate();

		
		// month_exp_map 채우기
		month_exp_map =
				new HashMap<Date, ArrayList<Expenses>>();
		Date tempDate = dateFrom.getCopyOfDate();
		while(!dateTo.isEarlierThan(tempDate))	// map에 넣음
		{
			month_exp_map.put(tempDate.getCopyOfDate(), 
					CommonSources.expManager.getCertainMonthExpenses(tempDate));
			tempDate.upMonth(1);
		}
		
		// month_expValue_map 채우기
		month_expValue_map = 
				new HashMap<Date, Integer>();
		Set<Date> keyData = month_exp_map.keySet();
		for(Date date : keyData)
			month_expValue_map.put(date.getCopyOfDate(), ExpensesManager.getSumOfExpenses(month_exp_map.get(date)));
		
		// month_ta_map 채우기
		month_ta_map = new HashMap<Date, Integer>();
		tempDate = dateFrom.getCopyOfDate();
		while(!dateTo.isEarlierThan(tempDate))	// map에 넣음
		{
			month_ta_map.put(tempDate.getCopyOfDate(), TAManage.getTargetAmountValue(tempDate));
			tempDate.upMonth(1);
		}
		
		// month_remain_map 채우기
		month_remain_map = new HashMap<Date, Integer>();
		 keyData = month_exp_map.keySet();
		for(Date date : keyData)
			month_remain_map.put(date.getCopyOfDate(),  TAManage.getTargetAmountValue(date)	
						- ExpensesManager.getSumOfExpenses(month_exp_map.get(date)));
	}

	private void initializeComps()
	{
		JPanel tempPanel = new JPanel();	// 그래프와 컨텐츠 패널을 넣을 임시 패널

		setLayout(new BorderLayout());
		add(datePanel, BorderLayout.NORTH);
		add(tempPanel, BorderLayout.CENTER);

		tempPanel.setLayout(null);
		graphPanel.setSize(500, 500);
		tempPanel.add(graphPanel);
		tempPanel.add(contentPanel);

		datePanel.setBackground(Color.WHITE);
		graphPanel.setBackground(Color.WHITE);
		contentPanel.setBackground(Color.WHITE);
	}

	private void setDatePanel()
	{
		datePanel.setLayout(new FlowLayout());
		JComboBox<Integer> yearFromBox = new JComboBox<Integer>();
		JLabel yearFromLabel = new JLabel("년");
		JComboBox<Integer> monthFromBox = new JComboBox<Integer>();
		JLabel monthFromLabel = new JLabel("월");
		JLabel wave = new JLabel(" ~ ");
		JComboBox<Integer> yearToBox = new JComboBox<Integer>();
		JLabel yearToLabel = new JLabel("년");
		JComboBox<Integer> monthToBox = new JComboBox<Integer>();
		JLabel monthToLabel = new JLabel("월");
		JButton ok = new JButton("확인");

		// comboBox 내용을 채움
		for(int i = CalendarPanel.DATE_FROM.getYear(); i <= CalendarPanel.DATE_TO.getYear(); i++)
			yearFromBox.addItem(i);
		for(int i = CalendarPanel.DATE_FROM.getMonth(); i <= CalendarPanel.DATE_TO.getMonth(); i++)
			monthFromBox.addItem(i);
		for(int i = CalendarPanel.DATE_FROM.getYear(); i <= CalendarPanel.DATE_TO.getYear(); i++)
			yearToBox.addItem(i);
		for(int i = CalendarPanel.DATE_FROM.getMonth(); i <= CalendarPanel.DATE_TO.getMonth(); i++)
			monthToBox.addItem(i);

		yearFromBox.setSelectedItem(from.getYear());
		monthFromBox.setSelectedItem(from.getMonth());
		yearToBox.setSelectedItem(to.getYear());
		monthToBox.setSelectedItem(to.getMonth());

		datePanel.add(yearFromBox);
		datePanel.add(yearFromLabel);
		datePanel.add(monthFromBox);
		datePanel.add(monthFromLabel);
		datePanel.add(wave);
		datePanel.add(yearToBox);
		datePanel.add(yearToLabel);
		datePanel.add(monthToBox);
		datePanel.add(monthToLabel);
		datePanel.add(ok);
		
		ok.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Date from = new Date((int)yearFromBox.getSelectedItem(), (int)monthFromBox.getSelectedItem());
				Date to = new Date((int)yearToBox.getSelectedItem(), (int)monthToBox.getSelectedItem());
				
				if(from.isEarlierThan(to))	//	날짜가 제대로 된 형식인 경우
				{
					setVariables(from, to);
					setGraphPanel();
				}
				else
					JOptionPane.showMessageDialog(null, "잘못된 날짜 형식 입니다.");
			}
			
		});

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
					TimeSeriesChart.makeMonthSeries("지출", new HashMap<Date, Integer>(month_expValue_map));
			TimeSeries s2 = 
					TimeSeriesChart.makeMonthSeries("목표액", new HashMap<Date, Integer>(month_ta_map));
			
			ArrayList<TimeSeries> ts_list = new ArrayList<TimeSeries>();
			ts_list.add(s1); ts_list.add(s2);
			
			TimeSeriesChart t = new TimeSeriesChart(ts_list, "월별 지출", "날짜", "원", true);
			chart = t.getChart();
			graphPanel.add(chart);
			CommonSources.resizeMainFrame();
			
		}
		// 남은 돈 그래프 //
		else
		{	
			TimeSeries s1 = 
					TimeSeriesChart.makeMonthSeries("잔액", new HashMap<Date, Integer>(month_remain_map));
			
			ArrayList<TimeSeries> ts_list = new ArrayList<TimeSeries>();
			ts_list.add(s1);
			
			XYBarChartDemo t = new XYBarChartDemo(ts_list, "월별 잔액", "날짜", "원", true);
			chart = t.getChart();
			graphPanel.add(chart);
			CommonSources.resizeMainFrame();
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
				setGraphPanel();
			}
		});
		rb2.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				current_selected_graph = 1;
				setGraphPanel();
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

	private void setContentPanel()
	{
		
	}
	
	public void refresh()
	{
		setVariables(from, to);
		setGraphPanel();
	}
}
