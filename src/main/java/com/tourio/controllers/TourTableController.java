package com.tourio.controllers;

import com.tourio.dao.TourDAO;
import com.tourio.models.Tour;
import com.tourio.utils.AlertUtils;
import com.tourio.utils.StageBuilder;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class TourTableController extends BaseTableController<Tour> {
    @FXML
    private TableView<Tour> table;

    @FXML
    private TableColumn<Tour, String> tourNameColumn;

    @Override
    public void onCreateClick(ActionEvent event) throws IOException {
        // Init controller
        TourFormController controller = new TourFormController();
        controller.tourTableController = this;

        // Show modal
        new StageBuilder("tour_form", controller, "Tạo tour")
                .setModalOwner(event)
                .build()
                .showAndWait();
    }

    @Override
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
        new StageBuilder("tour_form", controller, "Sửa tour")
                .setModalOwner(event)
                .build()
                .showAndWait();
    }

    @Override
    public void onDeleteClick(ActionEvent event) {
        Tour tour = table.getSelectionModel().getSelectedItem();

        if (tour == null) {
            AlertUtils.showWarning("Hãy chọn tour để xóa");
            return;
        }

        Alert alert = AlertUtils.alert(Alert.AlertType.CONFIRMATION, "Việc xóa tour sẽ xóa tất cả các đoàn khách. Chắc chắn xóa");
        Optional<ButtonType> option = alert.showAndWait();

        if (option.get() == ButtonType.OK) {
            TourDAO.delete(tour);
            loadData();
        }
    }

    @Override
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
                        controller.tourTableController = this;
                        controller.tour = tour;
                        controller.read_only = true;

                        // Show modal
                        new StageBuilder("tour_form", controller, "Chi tiết tour")
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

        // Bind table with observableList observable list
        table.setItems(observableList);
    }

    @Override
    public void loadData() {
        // Get all observableList
        List<Tour> allTours = TourDAO.getAll();

        // Sort tour location rels by sequence
        if (allTours != null) {
            for (Tour tour : allTours) {
                tour.getTourLocationRels().sort((c1, c2) -> (int) (c1.getSequence() - c2.getSequence()));
            }
        }

        // Add tours -> arrList of BaseTableController
        arrList.clear();
        arrList.addAll(allTours);

        // Set data
        observableList.setAll(allTours);
        table.refresh();
    }
}