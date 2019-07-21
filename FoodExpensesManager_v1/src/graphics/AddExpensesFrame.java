package graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import functions.CommonSources;
import functions.NormalFunc;
import objects.Expenses;

public class AddExpensesFrame extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// 구성요소
	JPanel datePanel = new JPanel();			// 실행 날짜, 시간을 정하는 것을 담는 패널
	JComboBox<Integer>  yearBox;
	JComboBox<Integer> monthBox;
	JComboBox<Integer> dateBox;
	JLabel yearLabel = new JLabel("년");
	JLabel monthLabel = new JLabel("월");
	JLabel dateLabel = new JLabel("일");
	JLabel dayLabel;

	JPanel contentPanel = new JPanel();	// 내용을 담는 패널
	JLabel expenseLabel = new JLabel("지출");
	JTextField expenseField;
	JComboBox<String> monetaryUnit;
	JLabel expenseValueLabel = new JLabel("0 원");
	JLabel contentLabel = new JLabel("비고");
	JTextField contentArea;

	JButton okButton;
	JButton cancleButton;

	/**
	 * 구성자
	 * @param year 년 default값
	 * @param month 월 default값
	 * @param date 일 default값
	 * @param from 몇 시 부터의 default값
	 * @param to 몇 시 까지의 default값
	 */
	public AddExpensesFrame(int year, int month, int date)
	{
		// 구성요소
		yearBox = new JComboBox<Integer>();
		monthBox = new JComboBox<Integer>();
		dateBox = new JComboBox<Integer>();
		monetaryUnit = new JComboBox<String>();
		dayLabel = new JLabel();
		setDayLabel(year, month - 1, date);


		expenseField = new JTextField("0");
		contentArea = new JTextField("상세 내역");

		okButton = new JButton("추가");
		cancleButton = new JButton("취소");

		// datePanel 설정
		datePanel.setBackground(Color.WHITE);
		datePanel.setLayout(null);
		datePanel.setSize(280, 150);
		datePanel.setLocation(0,0);
		datePanel.setBorder(new TitledBorder(new LineBorder(Color.RED,2), "언제"));
		add(datePanel);

		datePanel.add(yearBox);
		datePanel.add(yearLabel);
		datePanel.add(monthBox);
		datePanel.add(monthLabel);
		datePanel.add(dateBox);
		datePanel.add(dateLabel);
		datePanel.add(dayLabel);

		// contentPanel 설정
		contentPanel.setBackground(Color.WHITE);
		contentPanel.setLayout(null);
		contentPanel.setSize(datePanel.getWidth(), 130);
		contentPanel.setLocation(0,datePanel.getY() + datePanel.getHeight() + 10);
		contentPanel.setBorder(new TitledBorder(new LineBorder(Color.RED, 2), "내용"));
		add(contentPanel);

		contentPanel.add(expenseLabel);
		contentPanel.add(expenseField);
		contentPanel.add(monetaryUnit);
		contentPanel.add(expenseValueLabel);
		contentPanel.add(contentLabel);
		contentPanel.add(contentArea);


		// Frame 설정
		setTitle("지출 내역 추가");
		setLayout(null);
		setVisible(true);
		setSize(287,380);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		// ComboBox 초기화
		initializeBoxes(year, month, date);

		// 구성요소  설정
		setCompsInDatePanel();
		setCompsInContentPanel();
		setCompsInThisFrame();
		
		expenseField.requestFocus();
	}

	/* 초기화 정리용 메소드 **********************************************************/
	/**
	 * ComboBox 초기화
	 * @param year 년
	 * @param month 월
	 * @param date 일
	 */
	private void initializeBoxes(int year, int month, int date)
	{
		// comboBox 내용을 채움
		for(int i = CalendarPanel.DATE_FROM.getYear(); i <= CalendarPanel.DATE_TO.getYear(); i++)
			yearBox.addItem(i);
		for(int i = CalendarPanel.DATE_FROM.getMonth(); i <= CalendarPanel.DATE_TO.getMonth(); i++)
			monthBox.addItem(i);
		dateBox.addItem(1);
		setDateBoxes(year, month-1);

		monetaryUnit.addItem("원");
		monetaryUnit.addItem("00 원");
		monetaryUnit.addItem("000 원");
		monetaryUnit.addItem("만원");

		// comboBox를 기본값으로 설정
		yearBox.setSelectedItem(year);
		monthBox.setSelectedItem(month);
		dateBox.setSelectedItem(date);
		monetaryUnit.setSelectedIndex(1);
	}

	/**
	 * DatePanel에 들어가는 구성요소들 설정
	 */
	private void setCompsInDatePanel()
	{
		// 상수 <전체>
		final int START_X = 15;		// 첫 박스 (yearBox)가 시작되는 X
		final int START_Y = 20;		// 첫 박스 (yearBox)가 시작되는 Y
		final int DATE_HEIGHT = 20; // 날짜 관련 부분의 높이
		final int DAY_TIME_DISTANCE = 20; // 날짜와 요일 사이의 세로 간격

		// 상수 	<맨 위의 년, 월, 일> 
		final int YEAR_BOX_WIDTH = 80; // year 박스의 너비
		final int LABEL_WIDTH = 13; // 년, 월, 일 라벨의 너비
		final int SMALL_BOX_WIDTH = 50; // month, date 박스의 너비

		final int BOX_LABEL_DISTANCE = 5; // 박스와 라벨 사이의 간격 (박스 크기는 무시하고)
		final int LABEL_BOX_DISTANCE = 10; // 라벨과 박스 사이의 간격 (라벨 크기는 무시하고)

		// 상수 <그 아래의 몇 시~ 몇 시>
		final int DAY_LABEL_WIDTH = 70;
		final int DAY_LABEL_HEIGHT = 70;

		// 설정 <년, 월, 일 부분>
		yearBox.setSize(YEAR_BOX_WIDTH, DATE_HEIGHT);
		yearBox.setLocation(START_X, START_Y);
		yearBox.setFont(new Font("Serif", Font.BOLD, 15));

		yearLabel.setSize(LABEL_WIDTH, DATE_HEIGHT);
		yearLabel.setLocation(yearBox.getX() + yearBox.getWidth() + BOX_LABEL_DISTANCE, START_Y);

		monthBox.setSize(SMALL_BOX_WIDTH, DATE_HEIGHT);
		monthBox.setLocation(yearLabel.getX() + yearLabel.getWidth() + LABEL_BOX_DISTANCE,  START_Y);
		monthBox.setFont(new Font("Serif", Font.BOLD, 15));

		monthLabel.setSize(LABEL_WIDTH, DATE_HEIGHT);
		monthLabel.setLocation(monthBox.getX() + monthBox.getWidth() + BOX_LABEL_DISTANCE, START_Y);

		dateBox.setSize(SMALL_BOX_WIDTH, DATE_HEIGHT);
		dateBox.setLocation(monthLabel.getX() + monthLabel.getWidth() + BOX_LABEL_DISTANCE, START_Y);
		dateBox.setFont(new Font("Serif", Font.BOLD, 15));

		dateLabel.setSize(LABEL_WIDTH, DATE_HEIGHT);
		dateLabel.setLocation(dateBox.getX() + dateBox.getWidth() + BOX_LABEL_DISTANCE, START_Y);

		// 설정 <몇 시 ~ 몇 시 부분>		
		dayLabel.setSize(DAY_LABEL_WIDTH, DAY_LABEL_HEIGHT);
		dayLabel.setLocation(datePanel.getWidth() / 2 - DAY_LABEL_WIDTH / 2, START_Y + DATE_HEIGHT + DAY_TIME_DISTANCE);
		dayLabel.setHorizontalAlignment(JLabel.CENTER); // 가운데 정렬
		dayLabel.setFont(new Font("Serif", Font.BOLD, 45));

		// 액션리스너 추가
		yearBox.addActionListener(new BoxListener());
		monthBox.addActionListener(new BoxListener());
		dateBox.addActionListener(new BoxListener());
		monetaryUnit.addActionListener(new BoxListener());
		expenseField.addKeyListener(new FieldKeyListener());

		monetaryUnit.addActionListener(new ButtonListener());
		okButton.addActionListener(new ButtonListener());
		cancleButton.addActionListener(new ButtonListener());

		expenseField.addMouseListener(new MyMouseListener());
		contentArea.addMouseListener(new MyMouseListener());
		contentArea.addKeyListener(new ContentFieldKeyListener());
		datePanel.addKeyListener(new ContentFieldKeyListener());
	}

	/**
	 * ContentPanel에 들어가는 구성요소 설정
	 */
	private void setCompsInContentPanel()
	{
		// 상수 <전체>
		final int START_X = 15;		// 첫 컴포넌트(subjectLabel)가 시작되는 X
		final int START_Y = 20;		// 첫 컴포넌트(subjectLabel)가 시작되는 Y
		final int EXPENSE_HEIGHT = 25; // 비고 관련 부분의 높이
		final int EXPENSE_CONTENT_DISTANCE = 20; // 지출 부분 - 비고 부분 사이의 간격

		// 상수 <지출 부분>
		final int LABEL_WIDTH = 26;	// expenseLabel의 너비
		final int FIELD_WIDTH = 60;	// expenseField의 너비
		final int BOX_WIDTH = 62;	// monetaryUnit 버튼의 너비
		final int VALUE_LABEL_WIDTH = 80; // expenseValueLabel의 너비

		final int LABEL_FIELD_DISTANCE = 10; // 라벨과 텍스트필드 사이의 간격
		final int FIELD_BUTTON_DISTANCE = 10; // 텍스트필드와 버튼 사이의 간격
		final int BOX_LABEL_DISTANCE = 10; // 박스와 라벨 사이 간격

		// 상수 <비고 부분>
		final int CONTENT_START_Y = START_Y + EXPENSE_HEIGHT + EXPENSE_CONTENT_DISTANCE; // 안 건드려도 됨

		final int CONTENT_LABEL_WIDTH = 26;	// 라벨 너비
		final int CONTENT_LABEL_HEIGHT = 12;	// 라벨 높이
		final int CONTENT_AREA_WIDTH = 250;	// 텍스트Area 너비
		final int CONTENT_AREA_HEIGHT = 25;	// 텍스트Area 높이

		final int CONTENT_LABEL_FIELD_DISTANCE = 10; // 라벨과 Area 사이의 간격

		// 설정 <지출 부분>
		expenseLabel.setSize(LABEL_WIDTH, EXPENSE_HEIGHT);
		expenseLabel.setLocation(START_X, START_Y);
		expenseField.setSize(FIELD_WIDTH, EXPENSE_HEIGHT);
		class ex extends PlainDocument{ // 정수만 입력받게 함
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			public ex() {super();}
			public void insertString(int offset, String str, AttributeSet attr)throws BadLocationException{
				if (str == null)return;
				if(str.charAt(0) >= '0' && str.charAt(0) <= '9')
					super.insertString(offset, str, attr);
			}
		}
		expenseField.setDocument(new ex());
		expenseField.setText("0");

		expenseField.setLocation(expenseLabel.getX() + expenseLabel.getWidth() + LABEL_FIELD_DISTANCE, START_Y);

		monetaryUnit.setSize(BOX_WIDTH, EXPENSE_HEIGHT);
		monetaryUnit.setLocation(expenseField.getX() + expenseField.getWidth() + FIELD_BUTTON_DISTANCE, START_Y);

		expenseValueLabel.setSize(VALUE_LABEL_WIDTH, EXPENSE_HEIGHT);
		expenseValueLabel.setLocation(monetaryUnit.getX() + monetaryUnit.getWidth() + BOX_LABEL_DISTANCE, START_Y);

		// 설정 <비고 부분>
		contentLabel.setSize(CONTENT_LABEL_WIDTH, CONTENT_LABEL_HEIGHT);
		contentLabel.setLocation(START_X, CONTENT_START_Y);

		contentArea.setSize(CONTENT_AREA_WIDTH, CONTENT_AREA_HEIGHT);
		contentArea.setLocation(START_X, contentLabel.getY() + contentLabel.getHeight() + CONTENT_LABEL_FIELD_DISTANCE);

	}

	/**
	 * 이 프레임에 들어가는 구성요소 설정
	 */
	private void setCompsInThisFrame()
	{
		JPanel btnPanel = new JPanel();
		btnPanel.add(okButton);
		btnPanel.add(cancleButton);

		btnPanel.setLocation(0, contentPanel.getY() + contentPanel.getHeight() + 10);
		btnPanel.setSize(contentPanel.getWidth(), 50);
		add(btnPanel);
	}

	/* 기능 메소드 *******************************************************************/
	/**
	 * 년, 월에 맞게 dateBox의 내용을 바꿈
	 * @param year 년
	 * @param month 월 (0~11)
	 */
	private void setDateBoxes(int year, int month)
	{
		int count = dateBox.getItemCount();	
		int maxDate = functions.CalendarFunc.numberOfDays(year, month);

		for(int i = 1; i <= maxDate; i++)
			dateBox.addItem(i);

		if(dateBox.getSelectedItem() instanceof Integer)
		{
			int selected = (int)dateBox.getSelectedItem();
			for(int i = 0; i < count; i++)
				dateBox.removeItemAt(0);

			// 현재 선택된 date가 범위를 넘어갈 경우
			if(selected > maxDate)
				dateBox.setSelectedItem(maxDate);
			else
				dateBox.setSelectedItem(selected);
		}
	}

	/**
	 * DayLabel의 Text를 설정한다.
	 * @param year 년
	 * @param month 월 (0~11)
	 * @param date일
	 */
	private void setDayLabel(int year, int month, int date)
	{
		int day = functions.CalendarFunc.getDay(year, month, date);
		dayLabel.setText(functions.CalendarFunc.getStringOfDay(day));
		if(day == 1)
		{
			dayLabel.setBorder(new LineBorder(Color.RED, 5));
			dayLabel.setForeground(Color.RED);
		}
		else if(day == 7)
		{
			dayLabel.setBorder(new LineBorder(Color.BLUE, 5));
			dayLabel.setForeground(Color.BLUE);
		}
		else if(day > 1 && day < 7)
		{
			dayLabel.setBorder(new LineBorder(Color.BLACK, 5));
			dayLabel.setForeground(Color.BLACK);
		}
	}



	/**
	 * 지출에 적힌 대로 라벨에 값을 표시한다.
	 */
	private void setExpenseLabel()
	{
		if(expenseField.getText().equals(""))
			expenseValueLabel.setText("0 원");
		else
		{
			long value = getExpense();
			expenseValueLabel.setText(NormalFunc.getCompartedInt(value) + " 원");
		}
	}

	/**
	 * 지출한 값을 long 형태로 반환한다.
	 * @return 지출한 값
	 */
	private long getExpense()
	{
		long value = 0;
		if(expenseField.getText().equals(""))
			return value;
		else if(monetaryUnit.getSelectedIndex() == 0)
			value = Integer.parseInt(expenseField.getText());
		else if(monetaryUnit.getSelectedIndex() == 1)
			value = Integer.parseInt(expenseField.getText()) * 100;
		else if(monetaryUnit.getSelectedIndex() == 2)
			value = Integer.parseInt(expenseField.getText()) * 1000;
		else if(monetaryUnit.getSelectedIndex() == 3)
			value = Integer.parseInt(expenseField.getText()) * 10000;
		return value;
	}
	
	private void pressOK()
	{
		int year = (int)yearBox.getSelectedItem();
		int month = (int)monthBox.getSelectedItem();
		int date = (int)dateBox.getSelectedItem();
		long expense = getExpense();
		String content = contentArea.getText();
		if(functions.CommonSources.addExpenses(new Expenses(year, month, date, expense, content)))
		{
			CommonSources.mainFrame.refresh();
			this.dispose();
		}
	}
	
	private void pressCANCLE()
	{
		this.dispose();
	}
	/* 액션 리스너 *************************************************************************/
	class BoxListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if(e.getSource().equals(yearBox) || e.getSource().equals(monthBox))
			{
				int year = (int)yearBox.getSelectedItem();
				int month = (int)monthBox.getSelectedItem() - 1;

				setDateBoxes(year, month);
				setDayLabel(year, month, (int)dateBox.getSelectedItem());
			}

			else if(e.getSource().equals(dateBox))
				setDayLabel((int)yearBox.getSelectedItem(), (int)monthBox.getSelectedItem() - 1, (int)dateBox.getSelectedItem());
			else if(e.getSource().equals(monetaryUnit))
				setExpenseLabel();

		}
	}

	class ButtonListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if(e.getSource().equals(okButton))
				pressOK();

			else if(e.getSource().equals(cancleButton))
				pressCANCLE();
		}
	}

	/**
	 * expenseField 키설정
	 * @author Administrator
	 *
	 */
	class FieldKeyListener extends KeyAdapter 
	{
		public void keyPressed(KeyEvent e)
		{
			if(e.getKeyCode() == KeyEvent.VK_ENTER)
			{
				contentArea.requestFocus();
				if(contentArea.getText().equals("상세 내역"))
					contentArea.setText("");
			}
			if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
				pressCANCLE();
			if(expenseField.getText().equals("0"))
				expenseField.setText("");
		}

		public void keyReleased(KeyEvent e) {
			setExpenseLabel();
			if(expenseField.getText().equals(""))
				expenseField.setText("0");
		}
	}
	
	/**
	 * 비고 키설정
	 * @author Administrator
	 *
	 */
	class ContentFieldKeyListener extends KeyAdapter
	{
		public void keyPressed(KeyEvent e)
		{
			if(e.getKeyCode() == KeyEvent.VK_ENTER)
				pressOK();
			if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
				pressCANCLE();
		}
	}

	class MyMouseListener implements MouseListener
	{
		private boolean firstClick = true;

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			if(firstClick)
			{
				if(e.getSource() instanceof JTextField)
				{
					JTextField txta= (JTextField) e.getSource();
					if(txta.getText().equals("상세 내역"))
						txta.setText("");
					firstClick = false;
				}
			}
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub

		}

	}
}
