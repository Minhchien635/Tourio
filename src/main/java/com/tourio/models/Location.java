package com.tourio.models;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "location")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String name;

    @OneToMany(mappedBy = "location", fetch = FetchType.LAZY)
    private List<TourLocationRel> locationRels;
}
