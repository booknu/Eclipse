package graphics.charts;

import java.awt.Color;
import java.awt.Font;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.Day;
import org.jfree.data.time.Month;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.RectangleInsets;

import objects.time.Date;

/*  *********************/
/**
 * An example of a time series chart.  For the most part, default settings are 
 * used, except that the renderer is modified to show filled shapes (as well as 
 * lines) at each data point.
 */
public class TimeSeriesChart {

	ChartPanel chartPanel;
	ArrayList<TimeSeries> series_list;
	private boolean isMonthChart;

	/**
	 * A demonstration application showing how to create a simple time series 
	 * chart.  This example uses monthly data.
	 *
	 * @param title  the frame title.
	 */
	public TimeSeriesChart(ArrayList<TimeSeries> series_list, String title, String x_axis, String y_axis, boolean isMonthChart) {
		// 다른 부분
		this.series_list = series_list;
		this.isMonthChart = isMonthChart;

		XYDataset dataset = createDataset();
		//// 한글 깨짐 해결 ////////////////////////////////////////////////////////////////	
		JFreeChart chart = createChart(dataset, title, x_axis, y_axis);
		// 제목
		chart.getTitle().setFont(new Font("굴림", Font.BOLD, 20));
		chart.getTitle().setPaint(new Color(166,0,0));
		// 범례
		chart.getLegend().setItemFont(new Font("나눔손글씨 펜", Font.PLAIN, 15));

		chartPanel = new ChartPanel(chart, false);

		chartPanel.setPreferredSize(new java.awt.Dimension(500, 300));
		chartPanel.setMouseZoomable(true, true);
	}

	public TimeSeriesChart(TimeSeries series, String title, String x_axis, String y_axis)
	{
		// 다른 부분
		series_list = new ArrayList<TimeSeries>();
		series_list.add(series);


		XYDataset dataset = createDataset();
		//// 한글 깨짐 해결 ////////////////////////////////////////////////////////////////	
		JFreeChart chart = createChart(dataset, title, x_axis, y_axis);
		// 제목
		chart.getTitle().setFont(new Font("굴림", Font.BOLD, 20));
		chart.getTitle().setPaint(new Color(166,0,0));
		// 범례
		chart.getLegend().setItemFont(new Font("나눔손글씨 펜", Font.PLAIN, 15));

		chartPanel = new ChartPanel(chart, false);

		chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
		chartPanel.setMouseZoomable(true, false);
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
		{
			s1.add(new Day(date.getDate(), date.getMonth(), date.getYear()), (int)data.get(date));
		}
			

		return s1;
	}

	/**
	 * Creates a chart.
	 * 
	 * @param dataset  a dataset.
	 * 
	 * @return A chart.
	 */
	private JFreeChart createChart(XYDataset dataset, String title, String x_axis, String y_axis) {

		JFreeChart chart = ChartFactory.createTimeSeriesChart(
				title,  // title
				x_axis,             // x-axis label
				y_axis,   // y-axis label
				dataset,            // data
				true,               // create legend?
				true,               // generate tooltips?
				false               // generate URLs?
				);

		chart.setBackgroundPaint(Color.white);

		XYPlot plot = (XYPlot) chart.getPlot();
		plot.setBackgroundPaint(Color.lightGray);
		plot.setDomainGridlinePaint(Color.white);
		plot.setRangeGridlinePaint(Color.white);
		plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));
		plot.setDomainCrosshairVisible(true);
		plot.setRangeCrosshairVisible(true);


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


		XYItemRenderer r = plot.getRenderer();
		if (r instanceof XYLineAndShapeRenderer) {
			XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) r;
			// 점마다 있는 네모
			renderer.setBaseShapesVisible(true);
			renderer.setBaseShapesFilled(true);
		}

		DateAxis axis = (DateAxis) plot.getDomainAxis();
		if(isMonthChart)
			axis.setDateFormatOverride(new SimpleDateFormat("yy년 M월"));
		else
			axis.setDateFormatOverride(new SimpleDateFormat("yy-M/d"));

		return chart;

	}

	/**
	 * Creates a dataset, consisting of two series of monthly data.
	 *
	 * @return The dataset.
	 */
	@SuppressWarnings("deprecation")
	private XYDataset createDataset() {

		TimeSeriesCollection dataset = new TimeSeriesCollection();

		for(TimeSeries s : series_list)
		{
			dataset.addSeries(s);
		}

		dataset.setDomainIsPointsInTime(true);

		return dataset;

	}

	public ChartPanel getChart()
	{
		return chartPanel;
	}


}
