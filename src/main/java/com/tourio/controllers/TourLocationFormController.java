package com.tourio.controllers;

import com.tourio.dao.LocationDAO;
import com.tourio.models.Location;
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

    public TourLocationRel tourLocationRel = new TourLocationRel();

    public ObservableList<Location> locations = FXCollections.observableArrayList();

    public void onSaveClick(ActionEvent e) {
        Location location = locationComboBox.getValue();

        if (location == null) {
            AlertUtils.showWarning("Hãy chọn địa điểm");
            return;
        }

        // TODO
        Long locationId = location.getId();
        TourLocationRel existed = tourFormController.tour
                .getTourLocationRels()
                .stream()
                .filter(x -> x.getLocation().getId().equals(locationId))
                .findFirst()
                .orElse(null);

        if (existed == null) {
            tourLocationRel.setTour(tourFormController.tour);
            tourLocationRel.setLocation(location);
        } else {
            tourLocationRel = existed;
        }

        if (tourLocationRel.getId() == null) {
            tourLocationRel.setTour(tourFormController.tour);
            tourFormController.tourLocationRels.add(tourLocationRel);
        }

        closeWindow(e);
    }

    @Override
    protected void initReadOnly() {
    }

    @Override
    public void initDefaultValues() {
        long id = tourLocationRel.getLocation().getId();

        int size = locations.size();
        for (int i = 0; i < size; i++) {
            if (locations.get(i).getId() == id) {
                locationComboBox.getSelectionModel().select(i);
                return;
            }
        }
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

        // Load data
        locations.setAll(LocationDAO.getAll());

        // Remove already chosen locations
        Set<Long> chosenLocationIds = tourFormController.tourLocationRels
                .stream()
                .map(rel -> rel.getLocation().getId())
                .collect(Collectors.toSet());
        locations.removeIf(location -> chosenLocationIds.contains(location.getId()));

        // Bind data
        locationComboBox.setItems(locations);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initLocationComboBox();

        if (tourLocationRel.getId() != null) {
            initDefaultValues();
        }
    }
}
