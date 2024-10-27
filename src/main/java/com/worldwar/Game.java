package com.worldwar;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import org.jfree.chart.ChartPanel;

public class Game extends JFrame {
    private List<Player> players;
    private StockMarket stockMarket;
    private Timer timer;
    private JLabel timeLabel;
    private long elapsedSeconds = 0;
    private static final int MAX_PLAYER_COUNT = 10;

    public Game() {
        setTitle("Stock Market Simulation");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        this.players = new ArrayList<>();
        players.add(new Player("Human", Color.BLUE, false));

        JPanel controlPanel = new JPanel();
        timeLabel = new JLabel("时间: 0秒");
        controlPanel.add(timeLabel);

        JButton addPlayerButton = new JButton("添加AI玩家");
        addPlayerButton.addActionListener(e -> addPlayer());
        controlPanel.add(addPlayerButton);

        add(controlPanel, BorderLayout.NORTH);

        this.stockMarket = new StockMarket(1);  // 初始只有人类玩家的公司
        updateChartPanel();

        timer = new Timer(100, e -> updateGame());  // 更新频率为每100毫秒一次
        timer.start();
    }

    private void updateGame() {
        elapsedSeconds++;
        updateTimeLabel();
        stockMarket.updateMarket();
        updateChartPanel();
    }

    private void updateTimeLabel() {
        long days = elapsedSeconds / 86400;
        long hours = (elapsedSeconds % 86400) / 3600;
        long minutes = (elapsedSeconds % 3600) / 60;
        long seconds = elapsedSeconds % 60;
        timeLabel.setText(String.format("时间: %d天 %02d:%02d:%02d", days, hours, minutes, seconds));
    }

    private void addPlayer() {
        if (players.size() < MAX_PLAYER_COUNT) {
            Player newPlayer = new Player("AI " + players.size(), Color.getHSBColor((float) Math.random(), 0.8f, 0.8f), true);
            players.add(newPlayer);
            stockMarket.addCompany("Company " + players.size());
            System.out.println("新玩家已添加: " + newPlayer.getName());
        } else {
            System.out.println("已达到最大玩家数量");
        }
    }

    private void updateChartPanel() {
        ChartPanel chartPanel = stockMarket.createChartPanel();
        // 移除旧的图表面板（如果存在）
        for (java.awt.Component comp : getContentPane().getComponents()) {
            if (comp instanceof ChartPanel) {
                remove(comp);
                break;
            }
        }
        // 添加新的图表面板
        add(chartPanel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Game game = new Game();
            game.setVisible(true);
        });
    }
}
