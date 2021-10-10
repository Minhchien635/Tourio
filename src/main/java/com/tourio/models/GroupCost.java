package com.tourio.models;

public class GroupCost {
    private Integer id;
    private CostType type;
    private float amount;

    public GroupCost(Integer id, CostType type, float amount) {
        this.id = id;
        this.type = type;
        this.amount = amount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
