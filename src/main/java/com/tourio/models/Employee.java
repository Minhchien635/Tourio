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
public class Employee extends BaseModel {
    @OneToMany(mappedBy = "employee")
    @ToString.Exclude
    private List<GroupEmployeeRel> groupEmployeeRels;
}