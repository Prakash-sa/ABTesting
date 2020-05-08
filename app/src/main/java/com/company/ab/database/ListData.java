package com.company.ab.database;

public class ListData {
    private String name;
    private int coins;
    private int rank;

    public ListData(String name, int coins,int rank) {
        this.name = name;
        this.coins = coins;
        this.rank=rank;
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

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }
}
