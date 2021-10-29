package com.tourio.dto;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "group_cost")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class GroupCost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "type_id")
    private long costTypeId;

    @Column(name = "group_id")
    private long groupId;

    @Column
    private float amount;
}
