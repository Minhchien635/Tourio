package com.tourio.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "customer")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String name;

    @Column(name = "id_number")
    private String idNumber;

    @Column
    private String address;

    @Column
    private String sex;

    @Column
    private String phone;

    @Column
    private String nationality;
}
