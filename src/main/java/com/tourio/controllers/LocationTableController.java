package com.tourio.controllers;

import com.tourio.dao.LocationDAO;
import com.tourio.models.Location;
import com.tourio.models.Tour;
import com.tourio.utils.AlertUtils;
import com.tourio.utils.StageBuilder;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.IOException;
import java.util.Optional;

public class LocationTableController extends BaseTableController<Tour> {
    ObservableList<Location> locations = FXCollections.observableArrayList();

    @FXML
    private TableView<Location> table;

    @FXML
    private TableColumn<Location, String> locationNameColumn;//costTypeNameColumn

    public void onCreateClick(ActionEvent event) throws IOException {
        LocationFormController controller = new LocationFormController();
        controller.locationTableController = this;

        new StageBuilder("location_form", controller, "Thêm địa điểm")
                .setModalOwner(event)
                .setDimensionsAuto()
                .build()
                .showAndWait();
    }

    public void onEditClick(ActionEvent event) throws IOException {
        Location location = table.getSelectionModel().getSelectedItem();

        if (location == null) {
            AlertUtils.showWarning("Hãy chọn địa điểm cần sửa");
            return;
        }

        LocationFormController controller = new LocationFormController();
        controller.locationTableController = this;
        controller.location = location;

        new StageBuilder("location_form", controller, "Sửa địa điểm")
                .setModalOwner(event)
                .setDimensionsAuto()
                .build()
                .showAndWait();
    }

    public void onDeleteClick(ActionEvent event) {
        Location location = table.getSelectionModel().getSelectedItem();

        if (location == null) {
            AlertUtils.showWarning("Hãy chọn địa điểm cần xóa");
            return;
        }

        Alert alert = AlertUtils.alert(Alert.AlertType.CONFIRMATION, "Chắc chắn xóa");
        Optional<ButtonType> option = alert.showAndWait();

        if (option.get() == ButtonType.OK) {
            LocationDAO.delete(location);
            loadData();
            return;
        }
        if (option.get() == ButtonType.CANCEL) {
            return;
        }
    }

    public void initTable() {
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