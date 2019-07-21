package graphics.statisticPanel;

import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class LargeChartFrame extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public LargeChartFrame(JPanel chart)
	{
		setLayout(new GridLayout(1, 1));
		setVisible(true);
		setSize(700, 700);
		add(chart);
	}
}
