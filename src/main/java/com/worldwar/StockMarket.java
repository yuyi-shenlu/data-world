package com.worldwar;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class StockMarket {
    private Map<String, XYSeries> companySeries;
    private Random random = new Random();
    private int day = 0;
    private static final int MAX_ITEMS_PER_SERIES = 1000;

    public StockMarket(int numberOfCompanies) {
        companySeries = new HashMap<>();
        for (int i = 1; i <= numberOfCompanies; i++) {
            XYSeries series = new XYSeries("Company " + i);
            series.add(0, 100);  // 初始股价
            companySeries.put("Company " + i, series);
        }
    }

    public void updateMarket() {
        day++;
        for (XYSeries series : companySeries.values()) {
            double lastValue = series.getY(series.getItemCount() - 1).doubleValue();
            double change = (random.nextDouble() - 0.5) * 10;  // -5 到 5 之间的随机变化
            double newValue = Math.max(0, lastValue + change);  // 确保股价不会低于0
            series.add(day, newValue);

            if (series.getItemCount() > MAX_ITEMS_PER_SERIES) {
                series.remove(0);
            }
        }
    }

    public void addCompany(String companyName) {
        XYSeries series = new XYSeries(companyName);
        series.add(day, 100);  // 初始股价
        companySeries.put(companyName, series);
    }

    public ChartPanel createChartPanel() {
        XYSeriesCollection dataset = new XYSeriesCollection();
        for (XYSeries series : companySeries.values()) {
            dataset.addSeries(series);
        }

        JFreeChart chart = ChartFactory.createXYLineChart(
                "Stock Market Simulation",
                "Day",
                "Stock Price",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        XYPlot plot = chart.getXYPlot();
        NumberAxis domainAxis = (NumberAxis) plot.getDomainAxis();
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();

        domainAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        rangeAxis.setAutoRangeIncludesZero(false);

        return new ChartPanel(chart);
    }
}
