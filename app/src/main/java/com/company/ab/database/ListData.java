package com.company.ab.database;

public class ListData {
    private String name;
    private int coins;

    public ListData(String name, int coins) {
        this.name = name;
        this.coins = coins;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }
}
