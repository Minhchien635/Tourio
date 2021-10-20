package com.tourio.models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class TourLocationRel {
    private int tourId;
    private int locationId;
    private IntegerProperty sequence;

    public TourLocationRel(int tourId, int locationId, int sequence) {
        this.tourId = tourId;
        this.locationId = locationId;
        this.sequence = new SimpleIntegerProperty(sequence);
    }

    public int getTourId() {
        return tourId;
    }

    public void setTourId(int tourId) {
        this.tourId = tourId;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public IntegerProperty sequenceProperty() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence.set(sequence);
    }
}
