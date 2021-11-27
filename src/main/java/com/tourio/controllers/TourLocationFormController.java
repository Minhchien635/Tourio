package com.tourio.controllers;

import com.tourio.dao.LocationDAO;
import com.tourio.models.Location;
import com.tourio.models.Tour;
import com.tourio.models.TourLocationRel;
import com.tourio.utils.AlertUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;

public class TourLocationFormController extends BaseFormController {
    @FXML
    public ComboBox<Location> locationComboBox;

    public TourFormController tourFormController;

    public ObservableList<Location> locations = FXCollections.observableArrayList();

    public void onSaveClick(ActionEvent e) {
        Location location = locationComboBox.getValue();
        if (location == null) {
            AlertUtils.showWarning("Hãy chọn địa điểm");
            return;
        }

        Tour tour = tourFormController.tour;
        Long sequence = (long) tourFormController.tourLocationRels.size() + 1;
        TourLocationRel tourLocationRel = new TourLocationRel(tour, location, sequence);
        tourFormController.tourLocationRels.add(tourLocationRel);

        closeWindow(e);
    }

    @Override
    public void initReadOnly() {

    }

    @Override
    public void initDefaultValues() {

    }

    public void initLocationComboBox() {
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

        // Bind data
        locationComboBox.setItems(locations);

        // Load data
        locations.setAll(LocationDAO.getAll());

        // Remove already chosen locations
        Set<Long> chosenLocationIds = tourFormController.tourLocationRels
                .stream()
                .map(rel -> rel.getLocation().getId())
                .collect(Collectors.toSet());
        locations.removeIf(location -> chosenLocationIds.contains(location.getId()));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initLocationComboBox();
    }
}
