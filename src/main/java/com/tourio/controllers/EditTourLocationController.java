package com.tourio.controllers;

import com.tourio.dao.TourDAO;
import com.tourio.models.Location;
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
import javafx.util.StringConverter;

import java.net.URL;
import java.util.ResourceBundle;

public class EditTourLocationController implements Initializable {
    @FXML
    public ComboBox<Location> locationComboBox;

    // Selected location index from AddTour's location table
    public int index;

    public AddTourController addTourController;

    public void onSaveClick(ActionEvent e) {
        // Create new location
        Location newLocation = new Location();
        newLocation.setId(locationComboBox.getValue().getId());
        newLocation.setName(locationComboBox.getValue().getName());

        // Replace old location with new location
        addTourController.locations.set(index, newLocation);

        onCancelClick(e);
    }

    // Close window
    public void onCancelClick(ActionEvent e) {
        Node source = (Node) e.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Load all locations (except selected ones) into combo box
        ObservableList<Location> locations = FXCollections.observableArrayList();
        locations.setAll(TourDAO.getLocations());
        locationComboBox.setItems(locations);

        // Render combo box
        Callback<ListView<Location>, ListCell<Location>> factory = lv -> new ListCell<Location>() {

            @Override
            protected void updateItem(Location item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? "" : item.getName());
            }

        };
        locationComboBox.setCellFactory(factory);
        locationComboBox.setButtonCell(factory.call(null));

        // Set combo box default value as initial location
        long id = addTourController.locations.get(index).getId();
        Location initialLocation = locations.filtered(location -> location.getId().equals(id)).get(0);
        locationComboBox.setValue(initialLocation);
    }
}
