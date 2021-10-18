package com.tourio.models;

public class GroupEmployeeRel {
    private int groupId;
    private int employeeId;
    private int jobId;

    public GroupEmployeeRel(int groupId, int employeeId, int jobId) {
        this.groupId = groupId;
        this.employeeId = employeeId;
        this.jobId = jobId;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public int getJobId() {
        return jobId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }
}
