package com.tourio.models;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.sql.Date;

public class TourPrice {
    private int id;
    private DoubleProperty amount;
    private ObjectProperty<Date> dateStart;
    private ObjectProperty<Date> dateEnd;

    public TourPrice(int id, double amount, Date dateStart, Date dateEnd) {
        this.id = id;
        this.amount = new SimpleDoubleProperty(amount);
        this.dateStart = new SimpleObjectProperty<>(dateStart);
        this.dateEnd = new SimpleObjectProperty<>(dateEnd);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getAmount() {
        return amount.get();
    }

    public DoubleProperty amountProperty() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount.set(amount);
    }

    public Date getDateStart() {
        return dateStart.get();
    }

    public ObjectProperty<Date> dateStartProperty() {
        return dateStart;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart.set(dateStart);
    }

    public Date getDateEnd() {
        return dateEnd.get();
    }

    public ObjectProperty<Date> dateEndProperty() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd.set(dateEnd);
    }
}
