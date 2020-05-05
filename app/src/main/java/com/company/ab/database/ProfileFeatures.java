package com.company.ab.database;

public class ProfileFeatures {
    private String name;
    private String email;
    private int coins;

    public ProfileFeatures() {

    }

    public ProfileFeatures(String name, String email, int coins) {
        this.name = name;
        this.email = email;
        this.coins = coins;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }
}
