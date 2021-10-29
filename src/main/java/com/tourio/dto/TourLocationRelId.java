package com.tourio.dto;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class TourLocationRelId implements Serializable {
    @Column(name = "tour_id")
    private long tourId;

    @Column(name = "location_id")
    private long locationId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TourLocationRelId that = (TourLocationRelId) o;
        return tourId == that.tourId && locationId == that.locationId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(tourId, locationId);
    }
}