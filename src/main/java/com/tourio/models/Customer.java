package com.tourio.models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Customer {
    private int id;
    private StringProperty name;
    private StringProperty idNumber;
    private StringProperty address;
    private StringProperty sex;
    private StringProperty phone;
    private StringProperty nationality;

    public Customer(int id, String name, String idNumber, String address, String sex, String phone, String nationality) {
        this.id = id;
        this.name = new SimpleStringProperty(name);
        this.idNumber = new SimpleStringProperty(idNumber);
        this.address = new SimpleStringProperty(address);
        this.sex = new SimpleStringProperty(sex);
        this.phone = new SimpleStringProperty(phone);
        this.nationality = new SimpleStringProperty(nationality);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getIdNumber() {
        return idNumber.get();
    }

    public StringProperty idNumberProperty() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber.set(idNumber);
    }

    public String getAddress() {
        return address.get();
    }

    public StringProperty addressProperty() {
        return address;
    }

    public void setAddress(String address) {
        this.address.set(address);
    }

    public String getSex() {
        return sex.get();
    }

    public StringProperty sexProperty() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex.set(sex);
    }

    public String getPhone() {
        return phone.get();
    }

    public StringProperty phoneProperty() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone.set(phone);
    }

    public String getNationality() {
        return nationality.get();
    }

    public StringProperty nationalityProperty() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality.set(nationality);
    }
}
