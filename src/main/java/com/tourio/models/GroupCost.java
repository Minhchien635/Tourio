package com.tourio.models;

public class GroupCost {
    private int costTypeId;
    private float amount;

    public GroupCost(int costTypeId, float amount) {
        this.costTypeId = costTypeId;
        this.amount = amount;
    }

    public int getCostTypeId() {
        return costTypeId;
    }

    public void setCostTypeId(int costTypeId) {
        this.costTypeId = costTypeId;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }
}
