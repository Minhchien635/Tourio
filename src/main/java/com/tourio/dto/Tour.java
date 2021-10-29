package com.tourio.dto;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tour")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Tour implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String name;

    @Column
    private String description;

    @Column(name = "type_id")
    private long typeId;

    @OneToMany(mappedBy = "tour", fetch = FetchType.EAGER)
    private List<TourLocationRel> tourRels;

    @ManyToOne
    @JoinColumn(name = "type_id", nullable = false, insertable = false, updatable = false)
    private TourType type;

    @OneToMany(mappedBy = "tour")
    private List<TourPrice> prices;


    public Tour(long id, String name, TourType type, ArrayList<TourPrice> tourPrices, String description, List<TourLocationRel> tourLocationRels) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.prices = tourPrices;
        this.description = description;
        this.tourRels = tourLocationRels;
    }
}
