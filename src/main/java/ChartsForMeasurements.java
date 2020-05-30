import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CombinedDomainXYPlot;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ChartsForMeasurements {
    private static ChartsForMeasurements chartsForTrip;
    private static ArrayList<XYSeriesCollection> datasetsAnalog = new ArrayList<XYSeriesCollection>();
    private static CombinedDomainXYPlot plot;
    private static JFreeChart chart;
    private static JFrame frame;
    private static XYSeries tempSeries;
    private static double timeStep = 0.00025;
    private static double currentTime = 0.0;

    public ChartsForMeasurements(){
        plot = new CombinedDomainXYPlot(new NumberAxis("Time,s"));

        chart = new JFreeChart("Measurements", plot);
        chart.setBorderPaint(Color.black);
        chart.setBorderVisible(true);
        chart.setBackgroundPaint(Color.white);

        frame = new JFrame("");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new ChartPanel(chart));
        frame.setSize(1024,768);
        frame.show();
    }

    public static void createAnalogChart(String name, int number){
        if(chartsForTrip ==null) chartsForTrip = new ChartsForMeasurements();

        XYSeriesCollection dataset = new XYSeriesCollection();
        NumberAxis rangeAxis = new NumberAxis(name);
        rangeAxis.setAutoRangeIncludesZero(false);
        XYPlot subplot = new XYPlot(dataset, null, rangeAxis, new StandardXYItemRenderer() );
        subplot.setBackgroundPaint(Color.BLACK);

        plot.add(subplot);
        subplot.setWeight(7);
        datasetsAnalog.add(dataset);
    }

    public static void addSeries(String name, int chartNumber, int number){
        XYSeries series = new XYSeries(name); series.add(0.0, 0.0);
        datasetsAnalog.get(chartNumber).addSeries(series);
    }

    public static void addAnalogData(int chart, int series, double data){
        tempSeries = (XYSeries) datasetsAnalog.get(chart).getSeries().get(series);
        currentTime = tempSeries.getMaxX() + timeStep;
        tempSeries.add(currentTime, data);
    }
}
