package com.example.tourio.models;

public class Tour {
    private String name;
    private String description;
    private float price;
    private TourType type;

    public Tour(String name, String description, float price, TourType type) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public TourType getType() {
        return type;
    }

    public void setType(TourType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
