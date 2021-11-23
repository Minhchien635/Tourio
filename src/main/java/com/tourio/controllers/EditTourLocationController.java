package com.tourio.controllers;

import com.tourio.dao.LocationDAO;
import com.tourio.models.Location;
import com.tourio.models.TourLocationRel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.net.URL;
import java.util.ResourceBundle;

public class EditTourLocationController implements Initializable {
    @FXML
    public ComboBox<Location> locationComboBox;

    public TourLocationRel tourLocationRel;

    public TourCreateController tourFormController;

    ObservableList<Location> locations = FXCollections.observableArrayList();

    public void onSaveClick(ActionEvent e) {
        Location location = locationComboBox.getValue();
        tourLocationRel.setLocation(location);
        onCancelClick(e);
    }

    // Close window
    public void onCancelClick(ActionEvent e) {
        Node source = (Node) e.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    public void initLocationComboBox() {
        // Render
        Callback<ListView<Location>, ListCell<Location>> factory = lv -> new ListCell<Location>() {

            @Override
            protected void updateItem(Location item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? "" : item.getName());
            }

        };
        locationComboBox.setCellFactory(factory);
        locationComboBox.setButtonCell(factory.call(null));

        // Data binding
        locationComboBox.setItems(locations);
    }

    public void initData() {
        // Load locations
        locations.setAll(LocationDAO.getAll());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initLocationComboBox();
        initData();

        // Set combo box default value as initial location
        long id = tourLocationRel.getLocation().getId();
        Location location = locations.filtered(loc -> loc.getId() == id).get(0);
        locationComboBox.setValue(location);
    }
}
