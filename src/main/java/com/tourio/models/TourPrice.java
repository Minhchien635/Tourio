package com.tourio.models;

import java.sql.Date;

public class TourPrice {
    private double amount;
    private Date dateStart;
    private Date dateEnd;

    public TourPrice(double amount, Date dateStart, Date dateEnd) {
        this.amount = amount;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getDateStart() {
        return dateStart;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }
}
