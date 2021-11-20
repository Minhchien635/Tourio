package com.tourio.controllers;

import com.tourio.Main;
import com.tourio.dao.TourDAO;
import com.tourio.models.Tour;
import com.tourio.utils.AlertUtils;
import com.tourio.utils.WindowUtils;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
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
        TourFormController controller = new TourFormController();
        controller.tourTableController = this;

        // Load view
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/tourio/fxml/add_tour.fxml"));
        loader.setController(controller);

        // Render view window
        Parent root = loader.load();
        Scene scene = new Scene(root, Main.WIDTH, Main.HEIGHT);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Tạo tour");
        stage.initOwner(WindowUtils.getOwner(event));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
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

        // Load view
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/tourio/fxml/add_tour.fxml"));
        loader.setController(controller);

        // Render view window
        Parent root = loader.load();
        Scene scene = new Scene(root, Main.WIDTH, Main.HEIGHT);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Sửa tour");
        stage.initOwner(WindowUtils.getOwner(event));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }

    public void onDeleteClick(ActionEvent event) {
        Tour tour = table.getSelectionModel().getSelectedItem();

        if (tour == null) {
            AlertUtils.showWarning("Hãy chọn tour để xóa");
            return;
        }

        TourDAO.deleteTour(tour.getId());
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
                        controller.readOnly = true;

                        // Load view
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/tourio/fxml/add_tour.fxml"));
                        loader.setController(controller);

                        // Create view window
                        Parent root = loader.load();
                        Scene scene = new Scene(root, Main.WIDTH, Main.HEIGHT);
                        Stage stage = new Stage();
                        stage.setResizable(false);
                        stage.setScene(scene);
                        stage.setTitle("Chi tiết tour");
                        stage.initOwner(WindowUtils.getOwner(event));
                        stage.initModality(Modality.APPLICATION_MODAL);
                        stage.showAndWait();
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