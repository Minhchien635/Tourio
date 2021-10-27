package com.tourio.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tour_price")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class TourPrice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "tour_id")
    private long tourId;

    @Column
    private float amount;

    @Column(name = "date_start")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateStart;

    @Column(name = "date_end")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateEnd;

    @ManyToOne
    @JoinColumn(name = "tour_id", nullable = false, insertable = false, updatable = false)
    private Tour tour;
}
