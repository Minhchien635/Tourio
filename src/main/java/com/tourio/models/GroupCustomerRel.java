package com.tourio.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "group_customer_rel")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class GroupCustomerRel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "customer_id")
    private long customerId;

    @Column(name = "group_id")
    private long groupId;
}
