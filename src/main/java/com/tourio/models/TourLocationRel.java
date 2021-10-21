package com.tourio.models;

public class TourLocationRel {
    private int tourId;
    private int locationId;
    private int sequence;

    public TourLocationRel(int tourId, int locationId, int sequence) {
        this.tourId = tourId;
        this.locationId = locationId;
        this.sequence = sequence;
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

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }
}
