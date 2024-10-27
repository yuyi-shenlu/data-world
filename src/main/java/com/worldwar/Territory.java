package com.worldwar;

public class Territory {
    private Player owner;

    public Territory() {
        this.owner = null;
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }
}
