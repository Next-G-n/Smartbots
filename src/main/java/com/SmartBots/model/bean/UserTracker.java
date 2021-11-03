package com.SmartBots.model.bean;

public class UserTracker {
    int total;
    int position;

    public UserTracker(int total, int position) {
        this.total = total;
        this.position = position;
    }

    public int getTotal() {
        return total;
    }

    public int getPosition() {
        return position;
    }
}
