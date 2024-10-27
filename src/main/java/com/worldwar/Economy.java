package com.worldwar;

import java.util.HashMap;
import java.util.Map;

public class Economy {
    private static final int INVESTMENT_COST = 100;
    private Map<Player, Integer> playerMoney;

    public Economy() {
        playerMoney = new HashMap<>();
    }

    public void addPlayer(Player player) {
        playerMoney.put(player, 1000);  // 给新玩家初始资金
    }

    public boolean canInvest(Player player) {
        return playerMoney.getOrDefault(player, 0) >= INVESTMENT_COST;
    }

    public void invest(Player player) {
        if (canInvest(player)) {
            playerMoney.put(player, playerMoney.get(player) - INVESTMENT_COST);
            // 这里可以添加投资后的逻辑，比如增加玩家的某些属性或资源
        }
    }

    public int getPlayerMoney(Player player) {
        return playerMoney.getOrDefault(player, 0);
    }
}
