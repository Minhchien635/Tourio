package com.tourio.controllers;

import com.tourio.dao.LocationDAO;
import com.tourio.dao.TourDAO;
import com.tourio.models.Location;
import com.tourio.models.Tour;
import com.tourio.utils.AlertUtils;
import com.tourio.utils.StageBuilder;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class LocationTableController extends BaseTableController<Location> {
    @FXML
    private TableView<Location> table;

    @FXML
    private TableColumn<Location, String> locationNameColumn;

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

        List<Tour> tours = TourDAO.getAll();
        long index = tours.stream()
                .filter(t -> t.getTourLocationRels()
                        .stream()
                        .anyMatch(p -> p.getLocation().getId() == location.getId()))
                .count();

        if (index != 0) {
            AlertUtils.showWarning("Không thể xóa. Đã có tour chọn điểm đi này");
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

        // Bind table with observableList observable list
        table.setItems(observableList);
    }

    public void loadData() {
        List<Location> locations = LocationDAO.getAll();

        // Add locations -> arrList of BaseTableController
        arrList.clear();
        arrList.addAll(locations);

        // Get all observableList and set to location observable list
        observableList.setAll(locations);
        table.refresh();
    }
}