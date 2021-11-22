package com.tourio.controllers;

import com.tourio.dao.LocationDAO;
import com.tourio.models.Location;
import com.tourio.models.Tour;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;

import java.io.IOException;

public class LocationTableController extends BaseTableController<Tour> {
    ObservableList<Location> locations = FXCollections.observableArrayList();

    @FXML
    private TableView<Location> table;

    @FXML
    private TableColumn<Location, String> locationNameColumn;

    public void onCreateClick(ActionEvent event) throws IOException {

    }

    public void onEditClick(ActionEvent event) throws IOException {

    }

    public void onDeleteClick(ActionEvent event) {

    }

    public void initTable() {
        // On row double click
        table.setRowFactory(tv -> {
            TableRow<Location> row = new TableRow<>();

            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    Location location = row.getItem();

                }
            });

            return row;
        });

        // Location name column render
        locationNameColumn.setCellValueFactory(data -> {
            SimpleStringProperty property = new SimpleStringProperty();
            property.setValue(data.getValue().getName());
            return property;
        });

        // Bind table with locations observable list
        table.setItems(locations);
    }

    public void loadData() {
        // Get all locations and set to location observable list
        locations.setAll(LocationDAO.getAll());
        table.refresh();
    }
}