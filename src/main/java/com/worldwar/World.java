package com.worldwar;

import javax.swing.JPanel;
import java.awt.*;
import java.util.*;

public class World {
    private Territory[][] territories;
    private int width;
    private int height;
    private Random random = new Random();
    private JPanel mapPanel;
    private Map<Player, Point> headquarters;
    
    public World(int width, int height, JPanel mapPanel) {
        this.width = width;
        this.height = height;
        this.mapPanel = mapPanel;
        territories = new Territory[width][height];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                territories[x][y] = new Territory();
            }
        }
        headquarters = new HashMap<>();
    }
    
    public void initializeHeadquarters(java.util.List<Player> players) {
        for (Player player : players) {
            Point hq;
            do {
                hq = new Point(random.nextInt(width), random.nextInt(height));
            } while (territories[hq.x][hq.y].getOwner() != null);
            territories[hq.x][hq.y].setOwner(player);
            headquarters.put(player, hq);
        }
    }
    
    public void updateTerritories(Map<Player, Integer> playerValues) {
        for (Map.Entry<Player, Integer> entry : playerValues.entrySet()) {
            Player player = entry.getKey();
            int value = entry.getValue();
            Point hq = headquarters.get(player);
            
            // 清除该玩家所有领土
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    if (territories[x][y].getOwner() == player && (x != hq.x || y != hq.y)) {
                        territories[x][y].setOwner(null);
                    }
                }
            }
            
            // 从总部开始扩张
            expandTerritory(player, hq.x, hq.y, value - 1);
        }
    }
    
    private void expandTerritory(Player player, int x, int y, int remainingValue) {
        if (remainingValue <= 0) return;
        
        int[][] directions = {{0,1}, {1,0}, {0,-1}, {-1,0}};
        for (int[] dir : directions) {
            int nx = x + dir[0], ny = y + dir[1];
            if (nx >= 0 && nx < width && ny >= 0 && ny < height && territories[nx][ny].getOwner() == null) {
                territories[nx][ny].setOwner(player);
                expandTerritory(player, nx, ny, remainingValue - 1);
                if (remainingValue <= 1) return;
            }
        }
    }
    
    public void draw(Graphics g) {
        int cellWidth = mapPanel.getWidth() / width;
        int cellHeight = mapPanel.getHeight() / height;
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (territories[x][y].getOwner() != null) {
                    g.setColor(territories[x][y].getOwner().getColor());
                    g.fillRect(x * cellWidth, y * cellHeight, cellWidth, cellHeight);
                }
                g.setColor(Color.BLACK);
                g.drawRect(x * cellWidth, y * cellHeight, cellWidth, cellHeight);
                
                // 标记总部
                if (headquarters.containsValue(new Point(x, y))) {
                    g.setColor(Color.BLACK);
                    g.fillOval(x * cellWidth + cellWidth/4, y * cellHeight + cellHeight/4, cellWidth/2, cellHeight/2);
                }
            }
        }
    }

    public void addPlayer(Player player) {
        try {
            Point hq;
            do {
                hq = new Point(random.nextInt(width), random.nextInt(height));
            } while (territories[hq.x][hq.y].getOwner() != null);
            territories[hq.x][hq.y].setOwner(player);
            headquarters.put(player, hq);
            System.out.println("Player added to world: " + player.getName());
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error adding player to world: " + e.getMessage());
        }
    }
}
