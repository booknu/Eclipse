package graphics;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import functions.CommonSources;
import graphics.statisticPanel.MonthDetailPanel;
import graphics.statisticPanel.TermPanel;
import interfaces.RefreshablePanel;

public class StatisticPanel extends RefreshablePanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private boolean generating = true; // 처음 실행 때 생성중일 때
	
	JPanel menu_panel = new JPanel();
	RefreshablePanel content_panel = new RefreshablePanel();
	
	// 탭 패널들

	public StatisticPanel()
	{
		initializeComps();
		setMenuPanel();
		setContentPanel(0);
	}

	private void initializeComps()
	{
		setLayout(new BorderLayout());

		add(menu_panel, BorderLayout.NORTH);
		
	}

	private void setMenuPanel()
	{
		menu_panel.setLayout(new GridLayout(1,4));
		menu_panel.setBackground(new Color(200, 200, 200));

		/**
		 * 클릭할 수 있는 라벨
		 * 마우스를 올려놓으면 색깔이 바뀜
		 * @author Administrator
		 *
		 */
		class CLabel extends JLabel
		{
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			private boolean clicked = false; // 이 라벨이 체크 되었는지? (default = false)
			
			private Color clicked_bg = Color.WHITE;
			private Color unclicked_bg = new Color(220, 220, 220);
			private Color mouseOn_bg = new Color(235, 235, 235);
			private Color clicking_bg = new Color(200, 200, 200); // 클릭하는 도중의 색깔
			
			private Color border_cl = Color.WHITE;
			private int border_size = 1;
			
			public CLabel(String text)
			{
				super(text);
				setBorder(new LineBorder(border_cl, border_size));
				setOpaque(true);
				setBackground(unclicked_bg);
				addMouseListener(new MyMouseListener(this));
			}
			
			/* set 메소드 **************************************************************/
			public void setClicked(boolean click)
			{
				// 클릭 된 상태로 만듬
				if(click)
				{
					if(!clicked)
					{
						clicked = true;
						setBackground(clicked_bg);
					}
				}
				
				// 클릭 되지 않은 상태로 만듬
				else
				{
					if(clicked)
					{
						clicked = false;
						setBackground(unclicked_bg);
					}
				}
			}
			
			/* is 메소드 **********************************************************************/
			public boolean isClicked()
			{
				return clicked;
			}
			
			/* get 메소드 ************************************************************/
			public Color getClickingColor()
			{
				return clicking_bg;
			}

			/* 액션 리스너 **************************************************************/
			/**
			 * 라벨에 마우스가 올려져있으면 색깔이 바뀜
			 * 단, 이미 클릭 된 라벨이라면 아무 작동도 안 함
			 * @author Administrator
			 *
			 */
			class MyMouseListener implements MouseListener
			{
				private boolean entered;
				private CLabel lb;
				
				public MyMouseListener(CLabel lb)
				{
					this.lb = lb;
				}
				
				@Override
				public void mouseClicked(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void mouseEntered(MouseEvent e) {
					// TODO Auto-generated method stub
					if(e.getSource().equals(lb))
						if(!entered)	// 마우스가 나간 상태에서 들어왔을 경우 색깔 바꿈
							if(!lb.isClicked())	// 클릭이 안 됐을 경우에만 색깔 바뀜
							{
								lb.setBackground(mouseOn_bg);
								entered = true;
							}
				}

				@Override
				public void mouseExited(MouseEvent e) {
					// TODO Auto-generated method stub
					if(e.getSource().equals(lb))
						if(entered)	// 마우스가 들어온 상태에서 나갔을 경우 색깔 바꿈
							if(!lb.isClicked())
							{
								lb.setBackground(unclicked_bg);
								entered = false;
							}
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
		
		CLabel b1 = new CLabel("월별 통계");
		CLabel b2 = new CLabel("기간별 통계");
		CLabel b3 = new CLabel("Developing...");
		CLabel b4 = new CLabel("Developing...");
		
		/**
		 * CLabel 클릭시 마우스 리스너
		 * @author Administrator
		 *
		 */
		class CButtonMouseListener implements MouseListener
		{
			private CLabel pressed_label = null;
			private CLabel entered_label = null;
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				entered_label = (CLabel) e.getSource();
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				entered_label = null;
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				pressed_label = (CLabel) e.getSource();
				
				if(!pressed_label.isClicked())
					pressed_label.setBackground(pressed_label.getClickingColor());
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				if(e.getSource().equals(entered_label))
				{
					if(!pressed_label.isClicked())
					{
						pressed_label.setClicked(true);
						if(b1.equals(pressed_label))
						{
							b2.setClicked(false);
							b3.setClicked(false);
							b4.setClicked(false);
							
							setContentPanel(0);
						}
						if(b2.equals(pressed_label))
						{
							b1.setClicked(false);
							b3.setClicked(false);
							b4.setClicked(false);
							
							setContentPanel(1);
						}
						if(b3.equals(pressed_label))
						{
							b1.setClicked(false);
							b2.setClicked(false);
							b4.setClicked(false);
							
							setContentPanel(2);
						}
						if(b4.equals(pressed_label))
						{
							b1.setClicked(false);
							b2.setClicked(false);
							b3.setClicked(false);
							
							setContentPanel(3);
						}
					}
				}
				
				pressed_label = null;
			}

		}
		
		MouseListener ms_listener = new CButtonMouseListener();
		
		b1.setHorizontalAlignment(JLabel.CENTER); // 가운데 정렬
		b1.setFont(new Font("Serif", Font.BOLD, 15));
		b1.setClicked(true);
		b1.addMouseListener(ms_listener);
		
		b2.setHorizontalAlignment(JLabel.CENTER); // 가운데 정렬
		b2.setFont(new Font("Serif", Font.BOLD, 15));
		b2.addMouseListener(ms_listener);
		
		b3.setHorizontalAlignment(JLabel.CENTER); // 가운데 정렬
		b3.setFont(new Font("Serif", Font.BOLD, 15));
		b3.addMouseListener(ms_listener);
		
		b4.setHorizontalAlignment(JLabel.CENTER); // 가운데 정렬
		b4.setFont(new Font("Serif", Font.BOLD, 15));
		b4.addMouseListener(ms_listener);

		menu_panel.add(b1);
		menu_panel.add(b2);
		menu_panel.add(b3);
		menu_panel.add(b4);
	}

	/**
	 * 컨텐츠가 들어갈 패널을 설정
	 * @param panel
	 */
	@SuppressWarnings("deprecation")
	private void setContentPanel(int panel)
	{
		content_panel.removeAll();
		remove(content_panel);
		
		if(panel == 0)
		{
			content_panel = new MonthDetailPanel(MonthDetailPanel.current_selected_date);
		}
		
		else if(panel == 1)
		{
			content_panel = new TermPanel(TermPanel.current_from, TermPanel.current_to);
		}
		
		add(content_panel, BorderLayout.CENTER);
		
		if(!generating)
		{
			int w = CommonSources.mainFrame.window_width;
			int h = CommonSources.mainFrame.window_height;
			CommonSources.mainFrame.resize(w, h+1);
			CommonSources.mainFrame.resize(w, h);
		}
		else
			generating = false;
	}

	public void refresh()
	{
		content_panel.refresh();
	}
	
	/* 액션 리스너 **********************************************************************/


}
