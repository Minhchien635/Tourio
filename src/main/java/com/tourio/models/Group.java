package com.tourio.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity(name = "group_tour")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Long tourPrice;

    private String description;

    @Temporal(TemporalType.DATE)
    private Date dateStart;

    @Temporal(TemporalType.DATE)
    private Date dateEnd;

    @ManyToOne()
    private Tour tour;

    @ManyToMany()
    @JoinTable(name = "group_tour_customers")
    private List<Customer> customers;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<GroupEmployeeRel> groupEmployeeRels;

    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private List<GroupCostRel> groupCostRels;

    private Date createdAt;

    public Long getTotalCost() {
        return this.getGroupCostRels().stream().map(GroupCostRel::getAmount).reduce(0L, Long::sum);
    }

    public Long getTotalSale() {
        return this.getCustomers().size() * this.getTourPrice();
    }

}
