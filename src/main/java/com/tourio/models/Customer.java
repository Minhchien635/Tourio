package com.tourio.models;

import com.tourio.enums.SexType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer extends BaseModel {
    private String cccd;

    private String address;

    private String phone;

    private String nationality;

    @Enumerated(EnumType.STRING)
    private SexType sex;
}
