package com.tourio.controllers;

import com.tourio.Main;
import com.tourio.dao.TourDAO;
import com.tourio.models.Tour;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TourController implements Initializable {

    ObservableList<Tour> tours = FXCollections.observableArrayList();

    @FXML
    private TableView<Tour> table;

    @FXML
    private TableColumn<Tour, String> tourNameColumn;

    @FXML
    private Button addTourBtn;

    @FXML
    private Button editTourBtn;

    @FXML
    private Button deleteTourBtn;

    public void onCreateTourClick() throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/tourio/fxml/tour_detail.fxml"));
        fxmlLoader.setController(new AddTourController());
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, Main.WIDTH, Main.HEIGHT);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Tạo tour");
        stage.initModality(Modality.APPLICATION_MODAL);

        TourController tourController = this;
        AddTourController addTourController = fxmlLoader.getController();
        addTourController.init(tourController);

        stage.showAndWait();
    }

    public void onEditTourClick() throws IOException {
        Tour tour = table.getSelectionModel().getSelectedItem();
        if (tour != null) {
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/tourio/fxml/tour_detail.fxml"));
            fxmlLoader.setController(new UpdateTourController());
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root, Main.WIDTH, Main.HEIGHT);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("Sửa tour");
            stage.initModality(Modality.APPLICATION_MODAL);

            TourController tourController = this;
            UpdateTourController updateTourController = fxmlLoader.getController();
            updateTourController.initData(this, tour);

            stage.showAndWait();
        }
    }

    public void onDeleteTourClick() {
        Tour tour = table.getSelectionModel().getSelectedItem();
        if (tour != null) {
            TourDAO.deleteTour(tour.getId());
            initData();
        }
    }

    private void initTable() {
        // On row double click
        table.setRowFactory(tv -> {
            TableRow<Tour> row = new TableRow<>();

            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    Tour tour = row.getItem();

                    try {
                        // Create view window
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/tourio/fxml/tour_detail.fxml"));
                        loader.setController(new TourDetailController());
                        Parent root = loader.load();
                        Scene scene = new Scene(root, Main.WIDTH, Main.HEIGHT);
                        Stage stage = new Stage();
                        stage.setResizable(false);
                        stage.setScene(scene);
                        stage.setTitle("Chi tiết tour");
                        stage.initModality(Modality.APPLICATION_MODAL);

                        // Pass tour into controller
                        TourDetailController controller = loader.getController();
                        controller.initData(tour);

                        // Show window
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

    public void initData() {
        // Get all tours and set to tours observable list
        tours.setAll(TourDAO.getAll());
        table.refresh();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initTable();
        initData();
    }
}