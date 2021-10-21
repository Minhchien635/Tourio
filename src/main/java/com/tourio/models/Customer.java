package com.tourio.models;

public class Customer {
    private String name;
    private String idNumber;
    private String address;
    private String sex;
    private String phone;
    private String nationality;

    public Customer(String name, String idNumber, String address, String sex, String phone, String nationality) {
        this.name = name;
        this.idNumber = idNumber;
        this.address = address;
        this.sex = sex;
        this.phone = phone;
        this.nationality = nationality;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }
}
