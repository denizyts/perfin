package gui;
import org.knowm.xchart.*;
import org.knowm.xchart.style.Styler;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class XChartExample {
    public static void main(String[] args) {
        // Sample data
        double[] dates = {1.0 , 2.0 , 3.0 , 4.0 };
        double[] values = {10.5, 15.3, 12.8, 18.6};

        // Convert dates to LocalDate objects
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        double[] xData = dates;
     

        // Create a chart
        XYChart chart = new XYChartBuilder()
                .width(800)
                .height(600)
                .title("Sample Chart")
                .xAxisTitle("Date")
                .yAxisTitle("Values")
                .build();

        // Customize chart
        chart.getStyler().setDatePattern("yyyy-MM-dd");
        chart.getStyler().setChartTitleVisible(true);
        chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNW);

        // Add data to the chart
        chart.addSeries("Sample Data", xData, values);

        // Show chart
        new SwingWrapper<>(chart).displayChart();
    }
}
