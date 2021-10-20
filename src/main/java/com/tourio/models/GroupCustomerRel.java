package com.tourio.models;

public class GroupCustomerRel {
    private int customerId;
    private int groupId;

    public GroupCustomerRel(int customerId, int groupId) {
        this.customerId = customerId;
        this.groupId = groupId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }
}
