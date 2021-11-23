package com.tourio.controllers;

import com.tourio.dao.TourDAO;
import com.tourio.models.Tour;
import com.tourio.utils.AlertUtils;
import com.tourio.utils.StageBuilder;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;

import java.io.IOException;

public class TourTableController extends BaseTableController<Tour> {
    ObservableList<Tour> tours = FXCollections.observableArrayList();

    @FXML
    private TableView<Tour> table;

    @FXML
    private TableColumn<Tour, String> tourNameColumn;

    public void onCreateClick(ActionEvent event) throws IOException {
        // Init controller
        TourFormController controller = new TourFormController();
        controller.tourTableController = this;

        // Show modal
        new StageBuilder("add_tour", controller, "Tạo tour")
                .setModalOwner(event)
                .build()
                .showAndWait();
    }

    public void onEditClick(ActionEvent event) throws IOException {
        Tour tour = table.getSelectionModel().getSelectedItem();

        if (tour == null) {
            AlertUtils.showWarning("Hãy chọn tour để sửa");
            return;
        }

        // Init controller
        TourFormController controller = new TourFormController();
        controller.tourTableController = this;
        controller.tour = tour;

        // Show modal
        new StageBuilder("add_tour", controller, "Sửa tour")
                .setModalOwner(event)
                .build()
                .showAndWait();
    }

    public void onDeleteClick(ActionEvent event) {
        Tour tour = table.getSelectionModel().getSelectedItem();

        if (tour == null) {
            AlertUtils.showWarning("Hãy chọn tour để xóa");
            return;
        }

        TourDAO.delete(tour);
        loadData();
    }

    public void initTable() {
        // On row double click
        table.setRowFactory(tv -> {
            TableRow<Tour> row = new TableRow<>();

            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    Tour tour = row.getItem();

                    try {
                        // Init controller
                        TourFormController controller = new TourFormController();
                        controller.tour = tour;
                        controller.read_only = true;

                        // Show modal
                        new StageBuilder("add_tour", controller, "Chi tiết tour")
                                .setModalOwner(event)
                                .build()
                                .showAndWait();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

            return row;
        });

        // Tour name column render
        tourNameColumn.setCellValueFactory(data -> {
            SimpleStringProperty property = new SimpleStringProperty();
            property.setValue(data.getValue().getName());
            return property;
        });

        // Bind table with tours observable list
        table.setItems(tours);
    }

    public void loadData() {
        // Get all tours and set to tour observable list
        tours.setAll(TourDAO.getAll());
        table.refresh();
    }
}