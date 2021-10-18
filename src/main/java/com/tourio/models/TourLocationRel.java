package com.tourio.models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class TourLocationRel {
    private int groupId;
    private int tourId;
    private IntegerProperty sequence;

    public TourLocationRel(int groupId, int tourId, int sequence) {
        this.groupId = groupId;
        this.tourId = tourId;
        this.sequence = new SimpleIntegerProperty(sequence);
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getTourId() {
        return tourId;
    }

    public void setTourId(int tourId) {
        this.tourId = tourId;
    }

    public int getSequence() {
        return sequence.get();
    }

    public IntegerProperty sequenceProperty() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence.set(sequence);
    }
}
