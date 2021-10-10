package com.tourio.models;

public class Customer {
    private Integer id;
    private String name;
    private String id_number;
    private String address;
    private String sex;
    private int phone;

    public Customer(Integer id, String name, String id_number, String address, String sex, int phone) {
        this.id = id;
        this.name = name;
        this.id_number = id_number;
        this.address = address;
        this.sex = sex;
        this.phone = phone;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId_number() {
        return id_number;
    }

    public void setId_number(String id_number) {
        this.id_number = id_number;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }
}
