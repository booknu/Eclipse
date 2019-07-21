package graphics;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import functions.ProcessCommand;
import interfaces.RefreshablePanel;
import objects.Log;
import objects.LogManager;

@SuppressWarnings("serial")
public class ConsolePanel extends RefreshablePanel{

	@SuppressWarnings("unused")
	private int fontSize = 15;	// showPanel의 폰트 크
	JPanel showPanel = new JPanel();
	JPanel inputPanel = new JPanel();
	JTextArea logShow = new JTextArea();
	JScrollPane scroll;

	public ConsolePanel()
	{
		initializeComps();
		setShowPanel();
		setInputPanel();
	}

	private void initializeComps()
	{
		setLayout(null);
		showPanel.setSize(500,240);
		add(showPanel);
		inputPanel.setLocation(0, 240);
		inputPanel.setSize(500,30);
		add(inputPanel, BorderLayout.SOUTH);
	}

	private void setShowPanel()
	{
		logShow.setEditable(false);
//		logShow.setFont(new Font("HY동녘B", Font.PLAIN, 20));

		scroll = new JScrollPane(logShow);
		scroll.setLocation(10, 10);
		scroll.setSize(480, 220);
		scroll.setViewportView(logShow);
		showPanel.add(scroll);
		scroll.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {
			private boolean first = true;
			public void adjustmentValueChanged(AdjustmentEvent e) {
				if(first)
				{
					JScrollBar src = (JScrollBar) e.getSource();
					src.setValue(src.getMaximum());
					first = false;
				}
				

			}     
		});


		showPanel.setLayout(null);
		showPanel.setBackground(Color.WHITE);

		boolean first = true;
		for(Log log : LogManager.log_list)
		{
			if(first)
			{
				logShow.setText(log.toString());
				first = false;
			}
			else
				logShow.setText(logShow.getText() + "\n" + log);
		}
			
	}

	private void setInputPanel()
	{
		
		inputPanel.setBackground(Color.WHITE);
		inputPanel.setLayout(null);
		
		JTextField inputField = new JTextField("명령어 입력");
		inputField.setLocation(10, 0);
		inputField.setSize(480, 25);
//		inputField.setFont(new Font("", Font.BOLD, 10));
		inputField.addMouseListener(new MouseListener(){

			private boolean first = true;
			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				if(first) {
					inputField.setText("");
					first = false;
				}
			}
			@Override
			public void mouseEntered(MouseEvent e) {}
			@Override
			public void mouseExited(MouseEvent e) {}
			@Override
			public void mousePressed(MouseEvent e) {}
			@Override
			public void mouseReleased(MouseEvent e) {}
		});

		inputPanel.add(inputField);
		
		inputField.addKeyListener(new KeyAdapter() {
				public void keyPressed(KeyEvent e) {
					int keyCode = e.getKeyCode();
					switch(keyCode)
					{
					case KeyEvent.VK_ENTER:
						String txt = inputField.getText();
						inputField.setText("");
						ProcessCommand.input(txt);
					case KeyEvent.VK_ESCAPE:
						inputField.setText("");
					}
				}
		});
	}
	
	public void refresh(String log_text)
	{
		if(!logShow.getText().equals(""))
			logShow.setText(logShow.getText() + "\n" + log_text);
		else
			logShow.setText(log_text);
		JScrollBar src = scroll.getVerticalScrollBar();
		src.setValue(src.getMaximum());
	}
	
	public void terminateLog()
	{
		logShow.setText("");
	}
}
