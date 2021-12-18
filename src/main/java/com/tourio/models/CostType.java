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
public class CostType extends BaseModel {
    @OneToMany(mappedBy = "costType")
    @ToString.Exclude
    private List<GroupCostRel> groupCostRels;
}
