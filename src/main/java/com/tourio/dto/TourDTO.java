package com.tourio.dto;

import javafx.beans.property.*;

public class TourDTO {
    private int tourId;
    private int typeId;
    private StringProperty name;
    private StringProperty description;
    private StringProperty type;
    private FloatProperty price;

    public TourDTO(int tourId, String name, String type, float price, String description) {
        this.tourId = tourId;
        this.typeId = typeId;
        this.name = new SimpleStringProperty(name);
        this.description = new SimpleStringProperty(description);
        this.type = new SimpleStringProperty(type);
        this.price = new SimpleFloatProperty(price);
    }

    public TourDTO(int id, String name) {
        this.tourId = id;
        this.name = new SimpleStringProperty(name);
    }

    public FloatProperty getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price.set(price);
    }

    public int getTourId() {
        return tourId;
    }

    public void setTourId(int tourId) {
        this.tourId = tourId;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public StringProperty getName() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public StringProperty getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public StringProperty getType() {
        return type;
    }

    public void setType(String type) {
        this.type.set(type);
    }
}
