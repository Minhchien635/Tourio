package com.tourio.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "tour_location_rel")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class TourLocationRel {
    @EmbeddedId
    private TourLocationRelId tourLocationRelId;

    @ManyToOne
    @MapsId("tourId")
    private Tour tour;

    @ManyToOne
    @MapsId("locationId")
    private Location location;

    @Column
    private int sequence;
}