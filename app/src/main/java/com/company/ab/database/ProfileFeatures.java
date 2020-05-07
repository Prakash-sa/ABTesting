package com.company.ab.database;

import java.util.ArrayList;
import java.util.List;

public class ProfileFeatures {
    private String name;
    private String email;
    private List<String> uuid=new ArrayList<>();
    private int coins;

    public ProfileFeatures() {

    }

    public ProfileFeatures(String name, String email, int coins, List<String> uuid) {
        this.name = name;
        this.email = email;
        this.coins = coins;
        this.uuid=uuid;
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

    public List<String> getUuid() {
        return uuid;
    }

    public void setUuid(List<String> uuid) {
        this.uuid = uuid;
    }
}
