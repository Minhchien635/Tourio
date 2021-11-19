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

public class AddTourLocationController implements Initializable {
    @FXML
    public ComboBox<Location> locationComboBox;

    public AddTourController addTourController;

    public void onSaveClick(ActionEvent e) {
        Location location = locationComboBox.getValue();

        addTourController.locations.add(location);

        onCancelClick(e);
    }

    public void onCancelClick(ActionEvent e) {
        Node node = (Node) e.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Load all locations
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
    }
}
