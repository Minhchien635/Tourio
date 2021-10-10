module com.example.tourio {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires java.sql;

    opens com.example.tourio to javafx.fxml;
    exports com.example.tourio;
    exports com.example.tourio.Jdbc;
    opens com.example.tourio.Jdbc to javafx.fxml;
    exports com.example.tourio.Dao;
    opens com.example.tourio.Dao to javafx.fxml;
    exports com.example.tourio.Controller;
    opens com.example.tourio.Controller to javafx.fxml;
}