package gui;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import java.awt.*;
import java.awt.image.BufferedImage;

import contoller.server_controller;



public class chartGenerator {

    private Double[] data;
    private String symbol;

    public chartGenerator(String symbol) throws Exception {
        server_controller controller = new server_controller();
        this.data = controller.getHistPriceData(symbol);
        this.symbol = symbol;
    }
   
    public BufferedImage createChart() {
        // Create a dataset
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (int i = 0; i < data.length; i++) {
            dataset.addValue(data[i], "Grafik", "Bune lan" + (i + 1));
        }

        // Create a line chart
        JFreeChart chart = ChartFactory.createLineChart(
                symbol,   // Chart title
                "time",       // X-axis label
                "price",          // Y-axis label
                dataset           // Dataset
        );

        return chart.createBufferedImage(600, 300);
    }
    
}
