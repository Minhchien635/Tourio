package com.example.tourio.models;

public class GoupCost {
    private CostType type;
    private float amount;

    public GoupCost(CostType type, float amount) {
        this.type = type;
        this.amount = amount;
    }

    public CostType getType() {
        return type;
    }

    public void setType(CostType type) {
        this.type = type;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }
}
