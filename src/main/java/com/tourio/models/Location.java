package com.tourio.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Location extends BaseModel {
    @OneToMany(mappedBy = "location")
    @ToString.Exclude
    private List<TourLocationRel> tourLocationRels;
}