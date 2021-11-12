package com.tourio.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "tour_type")
@Getter
@Setter
@RequiredArgsConstructor
public class TourType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String name;

    @OneToMany(mappedBy = "type", fetch = FetchType.LAZY)
    private List<Tour> tours;
}
