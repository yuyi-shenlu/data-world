package com.worldwar;

import java.awt.Color;

public class Player {
    private String name;
    private Color color;
    private boolean isAI;
    private int money;
    private boolean infiniteMoney;
    
    public Player(String name, Color color, boolean isAI) {
        this.name = name;
        this.color = color;
        this.isAI = isAI;
        this.money = 1000; // 初始金钱
        this.infiniteMoney = false;
    }
    
    public String getName() {
        return name;
    }
    
    public Color getColor() {
        return color;
    }
    
    public boolean isAI() {
        return isAI;
    }
    
    public boolean hasInfiniteMoney() {
        return infiniteMoney;
    }
    
    public int getMoney() {
        return money;
    }
    
    public void setMoney(int money) {
        this.money = money;
    }
    
    public void setInfiniteMoney(boolean infiniteMoney) {
        this.infiniteMoney = infiniteMoney;
    }
    
    // Getters and setters
    // TODO: 添加更多玩家相关的方法
}
