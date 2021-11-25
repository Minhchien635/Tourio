package com.tourio.controllers;

import com.tourio.dao.TourDAO;
import com.tourio.dao.TourTypeDAO;
import com.tourio.models.Tour;
import com.tourio.models.TourType;
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
import java.util.List;
import java.util.Optional;

public class TourTypeTableController extends BaseTableController<Tour> {
    ObservableList<TourType> tourTypes = FXCollections.observableArrayList();

    @FXML
    private TableView<TourType> table;

    @FXML
    private TableColumn<TourType, String> tourTypeNameColumn;

    public void onCreateClick(ActionEvent event) throws IOException {
        TourTypeFormController controller = new TourTypeFormController();
        controller.tourTypeTableController = this;

        new StageBuilder("cost_type_form", controller, "Thêm loại phí")
                .setModalOwner(event)
                .setDimensionsAuto()
                .build()
                .showAndWait();
    }

    public void onEditClick(ActionEvent event) throws IOException {
        TourType tourType = table.getSelectionModel().getSelectedItem();

        if (tourType == null) {
            AlertUtils.showWarning("Hãy chọn loại phí cần sửa");
            return;
        }

        TourTypeFormController controller = new TourTypeFormController();
        controller.tourTypeTableController = this;
        controller.tourType = tourType;

        new StageBuilder("cost_type_form", controller, "Sửa loại phí")
                .setModalOwner(event)
                .setDimensionsAuto()
                .build()
                .showAndWait();
    }

    public void onDeleteClick(ActionEvent event) {
        TourType tourType = table.getSelectionModel().getSelectedItem();

        if (tourType == null) {
            AlertUtils.showWarning("Hãy chọn loại phí cần xóa");
            return;
        }

        List<Tour> tours = TourDAO.getAll();
        long index = tours.stream().filter(p -> p.getTourType().equals(tourType)).count();

        if (index != 0) {
            AlertUtils.showWarning("Không thể xóa. Đã có tour chọn loại tour này");
            return;
        }

        Alert alert = AlertUtils.alert(Alert.AlertType.CONFIRMATION, "Chắc chắn xóa");
        Optional<ButtonType> option = alert.showAndWait();

        if (option.get() == ButtonType.OK) {
            TourTypeDAO.delete(tourType);
            loadData();
            return;
        }
        if (option.get() == ButtonType.CANCEL) {
            return;
        }
    }

    public void initTable() {
        // TourType name column render
        tourTypeNameColumn.setCellValueFactory(data -> {
            SimpleStringProperty property = new SimpleStringProperty();
            property.setValue(data.getValue().getName());
            return property;
        });

        // Bind table with tourTypes observable list
        table.setItems(tourTypes);
    }

    public void loadData() {
        // Get all tourTypes and set to tourType observable list
        tourTypes.setAll(TourTypeDAO.getAll());
        table.refresh();
    }
}