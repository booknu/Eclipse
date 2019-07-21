/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2004, by Object Refinery Limited and Contributors.
 *
 * Project Info:  http://www.jfree.org/jfreechart/index.html
 *
 * This library is free software; you can redistribute it and/or modify it under the terms
 * of the GNU Lesser General Public License as published by the Free Software Foundation;
 * either version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License along with this
 * library; if not, write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330,
 * Boston, MA 02111-1307, USA.
 *
 * [Java is a trademark or registered trademark of Sun Microsystems, Inc. 
 * in the United States and other countries.]
 *
 * -------------------
 * XYBarChartDemo.java
 * -------------------
 * (C) Copyright 2002-2004, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * $Id: XYBarChartDemo.java,v 1.11 2004/05/11 14:56:17 mungady Exp $
 *
 * Changes
 * -------
 * 20-Jun-2002 : Version 1 (DG);
 * 02-Jul-2002 : Removed unnecessary imports (DG);
 * 24-Aug-2002 : Set preferred size for ChartPanel (DG);
 * 11-Oct-2002 : Fixed issues reported by Checkstyle (DG);
 *
 */

package graphics.charts;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.DateTickMarkPosition;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.StandardXYToolTipGenerator;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.time.Day;
import org.jfree.data.time.Month;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

import objects.time.Date;

/**
 * A simple demonstration application showing how to create a vertical bar chart.
 *
 */
public class XYBarChartDemo {

	ChartPanel chartPanel;
	ArrayList<TimeSeries> series_list;
	private boolean isMonthChart;

	/**
	 * Constructs the demo application.
	 *
	 * @param title  the frame title.
	 */
	@SuppressWarnings("deprecation")
	public XYBarChartDemo(ArrayList<TimeSeries> series_list, String title, String x_axis, String y_axis, boolean isMonth) {
		this.isMonthChart = isMonth;
		this.series_list = series_list;
		TimeSeriesCollection dataset = new TimeSeriesCollection();

		for(TimeSeries s : series_list)
			dataset.addSeries(s);

		dataset.setDomainIsPointsInTime(false);
		
		JFreeChart chart = createChart(dataset, title, x_axis, y_axis);

		//// 한글 깨짐 해결 ////////////////////////////////////////////////////////////////	
		// 제목
		chart.getTitle().setFont(new Font("굴림", Font.BOLD, 20));
		chart.getTitle().setPaint(new Color(166,0,0));
		// 범례
		chart.getLegend().setItemFont(new Font("나눔손글씨 펜", Font.PLAIN, 15));
		
		// add the chart to a panel...
		chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new java.awt.Dimension(500, 300));

	}
	
	@SuppressWarnings("deprecation")
	private JFreeChart createChart(TimeSeriesCollection dataset, String title, String x_axis, String y_axis)
	{
		JFreeChart chart = ChartFactory.createXYBarChart(
				title,
				x_axis,
				true,
				y_axis,
				dataset,
				PlotOrientation.VERTICAL,
				true,
				false,
				false
				);

		// then customise it a little...
		chart.setBackgroundPaint(new GradientPaint(0, 0, Color.white, 1000, 0, Color.blue));

		final XYItemRenderer renderer = chart.getXYPlot().getRenderer();
		final StandardXYToolTipGenerator generator = new StandardXYToolTipGenerator(
				"{1} = {2}", new SimpleDateFormat("yyyy"), new DecimalFormat("0.00")
				);
		renderer.setToolTipGenerator(generator);

		XYPlot plot = chart.getXYPlot();
		DateAxis axis = (DateAxis) plot.getDomainAxis();
		axis.setTickMarkPosition(DateTickMarkPosition.MIDDLE);
		if(isMonthChart)
			axis.setDateFormatOverride(new SimpleDateFormat("yy년 M월"));
		else
			axis.setDateFormatOverride(new SimpleDateFormat("yy-M/d"));
		// 추가

		//// 한글 깨짐 해결 ////////////////////////////////////////////////////////////////
		Font font = plot.getDomainAxis().getLabelFont();
		// X축 라벨
		plot.getDomainAxis().setLabelFont(new Font("나눔손글씨 펜", font.getStyle(), 20));
		// X축 도메인
		plot.getDomainAxis().setTickLabelFont(new Font("나눔손글씨 펜", font.getStyle(), 15));

		font = plot.getRangeAxis().getLabelFont();
		// Y축 라벨
		plot.getRangeAxis().setLabelFont(new Font("나눔손글씨 펜", font.getStyle(), 20));
		// Y축 범위
		plot.getRangeAxis().setTickLabelFont(new Font("나눔손글씨 펜", font.getStyle(), 15));

		//plot.setOutlinePaint(Color.black);
		plot.setBackgroundPaint(Color.lightGray);
		plot.setForegroundAlpha(0.65f);
		plot.setDomainGridlinePaint(Color.white);
		plot.setRangeGridlinePaint(Color.white);
		plot.setDomainCrosshairVisible(true);
		plot.setRangeCrosshairVisible(true);

		ValueAxis domainAxis = plot.getDomainAxis();
		domainAxis.setTickMarkPaint(Color.black);
		domainAxis.setLowerMargin(0.0);
		domainAxis.setUpperMargin(0.0);

		ValueAxis rangeAxis = plot.getRangeAxis();
		rangeAxis.setTickMarkPaint(Color.black);
		
		chart.setBackgroundPaint(Color.WHITE);
		
		return chart;
	}

	/**
	 * 월별 데이터를 파라미터로 입력받아
	 * TimeSeries를 만들어 반환
	 * @param name TimeSeries 이름
	 * @param data HashMap<Date 날짜, Integer 값> 형태의 데이터
	 * @return 만들어진 TimeSeries
	 */
	public static TimeSeries makeMonthSeries(String name, HashMap<Date, Integer> data)	
	{
		@SuppressWarnings("deprecation")
		TimeSeries s1 = new TimeSeries(name, Month.class);

		Set<Date> keyData = data.keySet();

		for(Date date : keyData)
			s1.add(new Month(date.getMonth(), date.getYear()), (int)data.get(date));

		return s1;
	}
	
	public static TimeSeries makeDateSeries(String name, HashMap<Date, Integer> data)
	{
		@SuppressWarnings("deprecation")
		TimeSeries s1 = new TimeSeries(name, Day.class);

		Set<Date> keyData = data.keySet();

		for(Date date : keyData)
			s1.add(new Day(date.getDate(), date.getMonth(), date.getYear()), (int)data.get(date));

		return s1;
	}
	
	public ChartPanel getChart()
	{
		return chartPanel;
	}
}