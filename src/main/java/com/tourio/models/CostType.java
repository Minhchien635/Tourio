package com.tourio.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity(name = "cost_type")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CostType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "costType")
    @ToString.Exclude
    private List<GroupCostRel> groupCostRels;
}
