package com.tourio.models;

import javafx.beans.property.*;

public class Tour {
    private StringProperty id;
    private StringProperty name;
    private StringProperty description;
    private FloatProperty price;
    private StringProperty typeID;

    public Tour(String id, String name, String description, float price, String typeID) {
        this.id = new SimpleStringProperty(id);
        this.name = new SimpleStringProperty(name);
        this.description = new SimpleStringProperty(description);
        this.price = new SimpleFloatProperty(price);
        this.typeID = new SimpleStringProperty(typeID);
    }

    public StringProperty getId() {
        return id;
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public StringProperty getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public FloatProperty priceProperty() {
        return price;
    }

    public void setPrice(float price) {
        this.price.set(price);
    }

    public StringProperty getTypeID() {
        return typeID;
    }

    public void setTypeID(String typeID) {
        this.typeID.set(typeID);
    }

    public StringProperty getName() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

}
