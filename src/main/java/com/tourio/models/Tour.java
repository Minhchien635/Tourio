package com.tourio.models;

public class Tour {
    private String name;
    private String description;
    private float price;
    private int typeId;

    public Tour(String name, String description, float price, int typeId) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.typeId = typeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }
}
