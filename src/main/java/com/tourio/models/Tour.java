package com.tourio.models;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
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

    @OneToMany(mappedBy = "tour")
    private List<TourLocationRel> tourRels;

    @ManyToOne
    @JoinColumn(name = "type_id", nullable = false, insertable = false, updatable = false)
    private TourType tourType;

    @OneToMany(mappedBy = "tour")
    private List<TourPrice> tourPrices;
}
