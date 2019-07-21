package graphics;

import functions.CalendarFunc;
import functions.CommonSources;
import functions.NormalFunc;
import interfaces.RefreshablePanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;

import objects.Expenses;
import objects.time.Date;

/**
 * 오늘 지출한 식비를 자세히 보여주는 패널
 * @author Administrator
 *
 */
public class DateDetailPanel extends RefreshablePanel{


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// 변수
	private Date date;
	private boolean allChecked = false;
	ArrayList<Expenses> exp_list;
	ArrayList<ExpCheckBox> exp_box_list = new ArrayList<ExpCheckBox>();

	// 컴포넌트들
	JPanel datePanel;
	GBPanel contentPanel;
	JPanel buttonPanel;

	public DateDetailPanel(Date date)
	{
		this.date = date.getCopyOfDate();

		initializeComps();
		addMouseWheelListener(new WheelListener());
	}

	/* 여러가지 설정 메소드들 *****************************************************/
	/**
	 * 여러가지 것들을 초기화, 설정
	 */
	private void initializeComps()
	{
		// 컴포넌트들 설정
		setLayout(new BorderLayout());
		setDatePanel();
		setContentPanel();
		setButtonPanel();
		setBorder(new LineBorder(new Color(231, 231, 231)));

		if(!isToday())
			datePanel.setBackground(Color.WHITE);
		contentPanel.setBackground(Color.WHITE);
		buttonPanel.setBackground(Color.WHITE);

		add(datePanel, BorderLayout.NORTH);
		add(contentPanel, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);
	}

	/**
	 * datePanel 설정
	 */
	private void setDatePanel()
	{
		datePanel = new JPanel();
		JLabel dateLabel = new JLabel(date.getYear() + "년 " + date.getMonth() + "월 " + date.getDate() + "일  ");
		JLabel dayLabel = new JLabel(CalendarFunc.getStringOfDay(CalendarFunc.getDay(date.getYear(), date.getMonth() - 1, date.getDate())));

		dayLabel.setHorizontalAlignment(JLabel.CENTER); // 가운데 정렬
		dayLabel.setFont(new Font("Serif", Font.BOLD, 20));
		setDayLabel(date.getYear(), date.getMonth() - 1, date.getDate(), dayLabel);

		datePanel.add(dateLabel);
		datePanel.add(dayLabel);

		if(isToday())
		{
			datePanel.setBackground(new Color(248, 251, 215));
		}

	}

	private void setContentPanel()
	{
		contentPanel = new GBPanel();
		exp_list = CommonSources.expManager.getCertainDateExpenses(date);
		// 아무 지출도 없을 경우
		if(exp_list.size() == 0)
		{
			contentPanel.setLayout(new BorderLayout());

			JLabel alert = new JLabel("지출 없음");
			alert.setHorizontalAlignment(JLabel.CENTER); // 가운데 정렬
			alert.setFont(new Font("나눔고딕 Light", Font.BOLD, 20));
			contentPanel.add(alert, BorderLayout.CENTER);

			setSize(300, 200);
		}

		else
		{
			int history_number = exp_list.size();
			GridBagLayout gbl = new GridBagLayout();
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.fill = GridBagConstraints.BOTH;
			contentPanel.setLayout(gbl);

			// 패널 사이즈 결정
			int height = 150 + 22 * history_number;
			setSize(300, height);

			// contentPanel의 맨 위 ( ㅁ 전체선택 )
			JLabel empty1 = new JLabel("  ");
			JLabel empty2 = new JLabel("  ");
			JCheckBox all_check = new JCheckBox("전체 선택");

			all_check.setBackground(Color.WHITE);

			contentPanel.addGrid(gbl, gbc, empty1, 0, 0, 1, 1, 1, 0);
			contentPanel.addGrid(gbl, gbc, empty2, 1, 0, 1, 1, 1, 0);
			contentPanel.addGrid(gbl, gbc, all_check, 2, 0, 2, 1, 1, 0);

			all_check.addActionListener(new ActionListener() {	
				public void actionPerformed(ActionEvent e)
				{
					JCheckBox box = (JCheckBox) e.getSource();
					if(box.isSelected())
					{
						for(ExpCheckBox b : exp_box_list)
							b.setSelected(true);
						allChecked = true;
					}

					else
					{
						for(ExpCheckBox b : exp_box_list)
							b.setSelected(false);
						allChecked = false;
					}
				}
			});

			// contentPanel의 중간 ( 내용: ~~~~원 ㅁ 변경 )
			int i = 1;
			int total = 0;
			exp_box_list.clear();
			for(Expenses exp : exp_list)
			{
				JLabel content = new JLabel(exp.getRemarks());
				JLabel expense = new JLabel(NormalFunc.getCompartedInt(exp.getExpense()) + " 원");
				ExpCheckBox check_box = new ExpCheckBox(exp);
				exp_box_list.add(check_box);
				ExpButton modify = new ExpButton(exp);
				modify.setText("변경");

				modify.setBackground(Color.WHITE);
				modify.setBorder(new EtchedBorder(EtchedBorder.RAISED));

				contentPanel.addGrid(gbl, gbc, content, 0, i, 1, 1, 1, 0);
				contentPanel.addGrid(gbl, gbc, expense, 1, i, 1, 1, 1, 0);
				contentPanel.addGrid(gbl, gbc, check_box, 2, i, 1, 1, 1, 0);
				contentPanel.addGrid(gbl, gbc, modify, 3, i, 1, 1, 1, 0);

				check_box.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e)
					{
						ExpCheckBox box = (ExpCheckBox) e.getSource();
						if(allChecked)
						{
							if(!box.isSelected())
							{
								allChecked = false;
								all_check.setSelected(false);
							}
						}
						else
						{
							boolean allC = true;
							for(ExpCheckBox b : exp_box_list)
								if(!b.isSelected())
									allC = false;
							if(allC)
							{
								all_check.setSelected(true);
								allChecked = true;
							}	
						}
					}
				});

				modify.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e)
					{
						ExpButton b = (ExpButton) e.getSource();
						new ModExpensesFrame(date.getYear(), date.getMonth(), date.getDate(), b.getExp());
					}
				});

				total += exp.getExpense();
				i++;
			}

			// contentPanel의 맨 아래 ( Total: ~~~~원 )
			JLabel total_label = new JLabel("Total : ");
			JLabel total_value = new JLabel(NormalFunc.getCompartedInt(total) + " 원");

			total_label.setForeground(new Color(189, 0, 0));
			total_value.setForeground(new Color(189, 0, 0));

			contentPanel.addGrid(gbl, gbc, total_label, 0, history_number + 1, 1, 1, 1, 0);
			contentPanel.addGrid(gbl, gbc, total_value, 1, history_number + 1, 2, 1, 1, 0);
		}

	}

	private void setButtonPanel()
	{
		buttonPanel = new JPanel();
		JButton add = new JButton("추가");
		JButton del = new JButton("삭제");

		buttonPanel.add(add);
		buttonPanel.add(del);

		add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				new AddExpensesFrame(date.getYear(), date.getMonth(), date.getDate());
			}
		});

		del.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e)
			{
				Iterator<ExpCheckBox> it = exp_box_list.iterator(); 
				while(it.hasNext())
				{
					ExpCheckBox box = it.next();
					if(box.isSelected())
					{
						Expenses exp = box.getExp();
						CommonSources.expManager.removeExpenses(exp);
					}
				}
				CommonSources.mainFrame.refresh();
			}
		});
	}

	/* private 메소드 **********************************************************************/
	/**
	 * DayLabel의 Text를 설정한다.
	 * @param year 년
	 * @param month 월 (0~11)
	 * @param date일
	 */
	private void setDayLabel(int year, int month, int date, JLabel dayLabel)
	{
		int day = functions.CalendarFunc.getDay(year, month, date);
		dayLabel.setText(functions.CalendarFunc.getStringOfDay(day));
		if(day == 1)
		{
			dayLabel.setBorder(new LineBorder(Color.RED, 2));
			dayLabel.setForeground(Color.RED);
		}
		else if(day == 7)
		{
			dayLabel.setBorder(new LineBorder(Color.BLUE, 2));
			dayLabel.setForeground(Color.BLUE);
		}
		else if(day > 1 && day < 7)
		{
			dayLabel.setBorder(new LineBorder(Color.BLACK, 2));
			dayLabel.setForeground(Color.BLACK);
		}
	}

	private boolean isToday()
	{
		if(date.getYear() == CalendarFunc.getCurrentYear() && date.getMonth() - 1 == CalendarFunc.getCurrentMonth())
			if(date.getDate()==CalendarFunc.getCurrentDate())
				return true;
		return false;
	}

	/* public 메소드 ********************************************************/
	/**
	 * 특정 날짜로 설정함
	 * @param year 년 
	 * @param month 월 (1 ~ 12)
	 * @param date 일
	 */
	@SuppressWarnings("deprecation")
	public void setCertainDate(Date date)
	{
		this.date = date.getCopyOfDate();

		this.removeAll();
		initializeComps();
		int w = CommonSources.mainFrame.window_width;
		int h = CommonSources.mainFrame.window_height;

		CommonSources.mainFrame.calendarPanel.last_date.setYear(date.getYear());
		CommonSources.mainFrame.calendarPanel.last_date.setMonth(date.getMonth());
		CommonSources.mainFrame.calendarPanel.last_date.setDate(date.getDate());
		CommonSources.mainFrame.calendarPanel.yearBox.setSelectedItem(date.getYear());
		CommonSources.mainFrame.calendarPanel.monthBox.setSelectedItem(date.getMonth());


		CommonSources.mainFrame.resize(w, h+1);
		CommonSources.mainFrame.resize(w, h);

	}
	public void refresh()
	{
		setCertainDate(date);
	}

	/**
	 * 날짜를 +- 1만큼 조절
	 * @param goPrevious 이전으로 갈지?
	 * 					(true = 이전으로, false = 다음으로)
	 */
	public void pagingDate(boolean goPrevious)
	{
		Date tempDate = date.getCopyOfDate();

		// 이전으로
		if(goPrevious)
		{
			if(tempDate.isSameDate(CalendarPanel.DATE_FROM))
				JOptionPane.showMessageDialog(null, "더 이상 이전으로 넘길 수 없습니다.");
			else
			{
				tempDate.downDate(1);
				setCertainDate(tempDate);
			}
		}

		// 다음으로
		else
		{
			if(tempDate.isSameDate(CalendarPanel.DATE_TO))
				JOptionPane.showMessageDialog(null, "더 이상 다음으로 넘길 수 없습니다.");
			else
			{
				tempDate.upDate(1);
				setCertainDate(tempDate);
			}
		}
	}

	/* get 메소드 ************************************************************/
	public int getYear() { return date.getYear(); }
	public int getMonth() { return date.getMonth(); }
	public int getDate() { return date.getDate(); }

	/* class ******************************************************/
	class GBPanel extends JPanel
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		/**
		 *  GridBagLayout을 쉽게 사용할 수 있게 한다.
		 */
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
	}

	class ExpCheckBox extends JCheckBox
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private Expenses exp;

		public ExpCheckBox(Expenses exp)
		{
			this.exp = exp;
			setBackground(Color.WHITE);
		}

		public Expenses getExp()
		{
			return exp;
		}
	}

	class ExpButton extends JButton
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private Expenses exp;

		public ExpButton(Expenses exp)
		{
			this.exp = exp;
		}

		public Expenses getExp()
		{
			return exp;
		}
	}

	/* 액션 리스너 ***********************************************/
	class WheelListener implements MouseWheelListener{
		@Override
		public void mouseWheelMoved(MouseWheelEvent e) {

			// 마우스가 위쪽으로 스크롤
			if(e.getWheelRotation() < 0)
				pagingDate(true);

			// 마우스가 아래쪽으로 스크롤
			else if(e.getWheelRotation() > 0)
				pagingDate(false);

		}
	}

}
