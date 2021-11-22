package com.tourio.controllers;

import com.tourio.Main;
import com.tourio.dao.TourDAO;
import com.tourio.models.Tour;
import com.tourio.utils.AlertUtils;
import com.tourio.utils.StageBuilder;
import com.tourio.utils.WindowUtils;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;

public class TourTableController extends BaseTableController<Tour> {
    ObservableList<Tour> tours = FXCollections.observableArrayList();

    @FXML
    private TableView<Tour> table;

    @FXML
    private TableColumn<Tour, String> tourNameColumn;

    public void onCreateClick(ActionEvent event) throws IOException {
        // Init controller
        TourCreateController controller = new TourCreateController();
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
        TourCreateController controller = new TourCreateController();
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
                        TourCreateController controller = new TourCreateController();
                        controller.tour = tour;

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