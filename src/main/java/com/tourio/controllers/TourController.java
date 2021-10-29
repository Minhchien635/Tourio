package com.tourio.controllers;

import com.tourio.dao.TourDAO;
import com.tourio.dto.Tour;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TourController implements Initializable {

    @FXML
    private TableView tableViewTour = new TableView<>();

    @FXML
    private TableColumn<Tour, String> tableColumnName;

    private void loadData() {
        ObservableList<Tour> tours = FXCollections.observableArrayList(TourDAO.getTours());
        tableViewTour.getItems().clear();
        tableViewTour.getItems().addAll(tours);
    }

    private void initCol() {
        tableColumnName.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getName()));
    }

    private void eventListenerTable() {
        tableViewTour.setRowFactory(tv -> {
            TableRow<Tour> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    Tour rowData = row.getItem();
                    try {
                        Stage stage = new Stage();
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/tourio/fxml/tour-detail.fxml"));
                        Parent root = fxmlLoader.load();
                        TourDetailController tourDetailController = fxmlLoader.getController();
                        tourDetailController.setTourId(rowData.getId());
                        Scene scene = new Scene(root, 631, 596);
                        stage.setResizable(false);
                        stage.setScene(scene);
                        stage.initStyle(StageStyle.DECORATED);
                        stage.setTitle("Chi tiết tour");
                        stage.initModality(Modality.APPLICATION_MODAL);
                        stage.showAndWait();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            return row;
        });
    }

    private void setup() {
        initCol();
        loadData();
        eventListenerTable();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setup();
    }
}