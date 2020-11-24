package com.gdev.watermonitor;

import java.util.Date;

public class WaterItem {
    double amount;
    Date date;
    long id;

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public WaterItem(double amount, Date date, long id) {
        this.amount = amount;
        this.date = date;
        this.id = id;
    }
}
