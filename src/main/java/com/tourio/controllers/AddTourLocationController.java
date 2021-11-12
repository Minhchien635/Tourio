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

public class AddTourLocationController implements Initializable {

    ObservableList<Location> locations = FXCollections.observableArrayList();
    private AddTourController addTourController;
    @FXML
    private Button okBtn;
    @FXML
    private ComboBox<Location> locationComboBox;

    public void onOkClick() throws IOException {
        Location location = locationComboBox.getValue();

        addTourController.getLocationList().add(location);

        addTourController.addDataLocation();

        Stage stage = (Stage) okBtn.getScene().getWindow();
        stage.close();
    }

    public void init(AddTourController addTourController) {
        this.addTourController = addTourController;
    }

    private void initLocations() {
        locations.setAll(TourDAO.getLocations());

        locationComboBox.setItems(locations);

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
        initLocations();
    }
}
