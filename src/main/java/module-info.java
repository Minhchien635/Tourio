module com.example.tourio {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires javafx.graphics;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires java.sql;
    requires java.persistence;
    requires org.hibernate.orm.core;
    requires java.naming;
    requires lombok;

    exports com.tourio;
    opens com.tourio to javafx.fxml;
    exports com.tourio.controllers;
    opens com.tourio.controllers to javafx.fxml;
    exports com.tourio.dao;
    opens com.tourio.dao to javafx.fxml;
    exports com.tourio.models;
    opens com.tourio.models to org.hibernate.orm.core;
    exports com.tourio.utils;
    opens com.tourio.utils to javafx.fxml;
    exports com.tourio.enums;
    opens com.tourio.enums to javafx.fxml;
}