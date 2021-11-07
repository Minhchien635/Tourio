package com.tourio.controllers;

import com.tourio.Main;
import com.tourio.dao.TourDAO;
import com.tourio.models.Tour;
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

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TourController implements Initializable {

    @FXML
    private TableView<Tour> table;

    @FXML
    private TableColumn<Tour, String> tourNameColumn;

    ObservableList<Tour> tours = FXCollections.observableArrayList();

    private void initTable() {
        // On row double click
        table.setRowFactory(tv -> {
            TableRow<Tour> row = new TableRow<>();

            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    Tour tour = row.getItem();

                    try {
                        // Create view window
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/tourio/fxml/tour-detail.fxml"));
                        Parent root = loader.load();
                        Scene scene = new Scene(root, Main.WIDTH, Main.HEIGHT);
                        Stage stage = new Stage();
                        stage.setResizable(false);
                        stage.setScene(scene);
                        stage.setTitle("Chi tiết tour");
                        stage.initModality(Modality.APPLICATION_MODAL);

                        // Pass tour into controller
                        TourDetailController controller = loader.getController();
                        controller.initData(tour);

                        // Show window
                        stage.showAndWait();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

            return row;
        });

        // Tour name column render
        tourNameColumn.setCellValueFactory(data -> {
            SimpleStringProperty property = new SimpleStringProperty();
            property.setValue(data.getValue().getName());
            return property;
        });

        // Bind table with tours observable list
        table.setItems(tours);
    }

    private void initData() {
        // Get all tours and set to tours observable list
        tours.setAll(TourDAO.getAll());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initTable();
        initData();
    }
}