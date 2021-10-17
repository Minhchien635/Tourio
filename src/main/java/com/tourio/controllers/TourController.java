package com.tourio.controllers;

import com.tourio.dao.TourDAO;
import com.tourio.models.Tour;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class TourController implements Initializable {
    @FXML
    private Label welcomeText;

    @FXML
    private TableView tableViewTour = new TableView<>();

    @FXML
    private TableColumn<Tour, String> tableColumnId;

    @FXML
    private TableColumn<Tour, String> tableColumnName;

    @FXML
    private TableColumn<Tour, String> tableColumnType;

    public void show() throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/com/tourio/fxml/tour.fxml"));
        Scene scene = new Scene(root, 600, 400);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.initStyle(StageStyle.DECORATED);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    private void loadData() {
        ObservableList<Tour> tours = FXCollections.observableArrayList(TourDAO.getTours());
        tableViewTour.getItems().clear();
        tableViewTour.getItems().addAll(tours);
    }

    private void initCol() {
        tableColumnId.setCellValueFactory(data -> data.getValue().getId());
        tableColumnName.setCellValueFactory(data -> data.getValue().getName());
        tableColumnType.setCellValueFactory(data -> data.getValue().getTypeID());
    }

    private void setup() {
        initCol();
        loadData();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setup();
    }
}