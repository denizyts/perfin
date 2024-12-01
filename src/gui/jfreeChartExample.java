package gui;

import javax.swing.*;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import java.awt.*;
import java.awt.image.BufferedImage;

public class jfreeChartExample {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Chart in JLabel Example");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);

            // Example data
            double[] data = {1.0, 2.3, 3.5, 2.7, 4.8};
            JLabel chartLabel = new JLabel();

            JFreeChart chart = createChart(data , "isctr");

            BufferedImage chartImage = chart.createBufferedImage(400, 200);

            chartLabel.setIcon(new ImageIcon(chartImage));

            frame.add(chartLabel, BorderLayout.WEST);
            

            frame.setVisible(true);
        });
    }

    private static JFreeChart createChart(double[] data , String symbol) {
        // Create a dataset
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (int i = 0; i < data.length; i++) {
            dataset.addValue(data[i], "Series1", "Category" + (i + 1));
        }

        // Create a line chart
        return ChartFactory.createLineChart(
                symbol,   // Chart title
                "Category",       // X-axis label
                "Value",          // Y-axis label
                dataset           // Dataset
        );
    }
}
