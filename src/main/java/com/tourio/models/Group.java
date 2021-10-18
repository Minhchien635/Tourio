package com.tourio.models;

import javafx.beans.property.*;

import java.sql.Date;

public class Group {
    private int id;
    private int tourId;
    private StringProperty name;
    private FloatProperty tourPrice;
    private StringProperty description;
    private ObjectProperty<Date> dateStart;
    private ObjectProperty<Date> dateEnd;

    public Group(int id, int tourId, String name, float tourPrice, String description, Date dateStart, Date dateEnd) {
        this.id = id;
        this.tourId = tourId;
        this.name = new SimpleStringProperty(name);
        this.tourPrice = new SimpleFloatProperty(tourPrice);
        this.description = new SimpleStringProperty(description);
        this.dateStart = new SimpleObjectProperty<>(dateStart);
        this.dateEnd = new SimpleObjectProperty<>(dateEnd);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTourId() {
        return tourId;
    }

    public void setTourId(int tourId) {
        this.tourId = tourId;
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public float getTourPrice() {
        return tourPrice.get();
    }

    public FloatProperty tourPriceProperty() {
        return tourPrice;
    }

    public void setTourPrice(float tourPrice) {
        this.tourPrice.set(tourPrice);
    }

    public String getDescription() {
        return description.get();
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public void setDescription(String description) {
        this.description.set(description);
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
