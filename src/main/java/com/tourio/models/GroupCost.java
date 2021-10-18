package com.tourio.models;

import javafx.beans.property.FloatProperty;
import javafx.beans.property.SimpleFloatProperty;

public class GroupCost {
    private int id;
    private int costTypeId;
    private FloatProperty amount;

    public GroupCost(int id, int costTypeId, float amount) {
        this.id = id;
        this.costTypeId = costTypeId;
        this.amount = new SimpleFloatProperty(amount);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getAmount() {
        return amount.get();
    }

    public FloatProperty amountProperty() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount.set(amount);
    }

    public int getCostTypeId() {
        return costTypeId;
    }

    public void setCostTypeId(int costTypeId) {
        this.costTypeId = costTypeId;
    }
}
