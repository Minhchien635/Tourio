package com.tourio.models;

import com.tourio.enums.SexType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity(name = "customer")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String cccd;
    private String address;
    private String phone;
    private String nationality;

    @Enumerated(EnumType.STRING)
    private SexType sex;
}
