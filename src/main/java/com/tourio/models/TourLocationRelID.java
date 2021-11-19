package com.tourio.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TourLocationRelID implements Serializable {
    @Column(name = "tour_id")
    private Long tourId;

    @Column(name = "location_id")
    private Long locationId;
}