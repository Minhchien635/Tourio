package com.example.tourio.models;

public class Customer {
    private String name;
    private String id_number;
    private String address;
    private String sex;
    private int phone;

    public Customer(String name, String id_number, String address, String sex, int phone) {
        this.name = name;
        this.id_number = id_number;
        this.address = address;
        this.sex = sex;
        this.phone = phone;
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
