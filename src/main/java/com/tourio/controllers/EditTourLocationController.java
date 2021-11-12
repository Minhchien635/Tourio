package com.tourio.controllers;

import com.tourio.dao.TourDAO;
import com.tourio.models.Location;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EditTourLocationController implements Initializable {
    ObservableList<Location> locations = FXCollections.observableArrayList();

    // Index item, location, addTourController of addTourController
    private int indexItem;
    private Location location;
    private AddTourController addTourController;

    @FXML
    private Button okBtn;

    @FXML
    private ComboBox<Location> locationComboBox;

    public void onOkClick() throws IOException {
        // Set location item of location list view
        Location location = addTourController.getLocationList().get(indexItem);
        location.setId(locationComboBox.getValue().getId());
        location.setName(locationComboBox.getValue().getName());

        // Set data locations o
        addTourController.initDataLocation();

        Stage stage = (Stage) okBtn.getScene().getWindow();
        stage.close();
    }

    // Receive index, location, controller of addTourController
    public void init(int indexItem, Location location, AddTourController addTourController) {
        this.indexItem = indexItem;
        this.location = location;
        this.addTourController = addTourController;
        initLocations();
    }

    // Location render
    private void initLocations() {
        locations.setAll(TourDAO.getLocations());
        locationComboBox.setItems(locations);

        // Set value location into location comboBox
        int size = locations.size();
        int index = 0;
        for (int i = 0; i < size; i++) {
            if (locations.get(i).getName().equals(location.getName())) {
                index = i;
                break;
            }
        }
        locationComboBox.setValue(locations.get(index));

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
