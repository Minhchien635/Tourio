package com.tourio.controllers;

import com.tourio.Main;
import com.tourio.dao.TourDAO;
import com.tourio.dto.TourDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TourController implements Initializable {

    @FXML
    private TableView<TourDTO> table;

    @FXML
    private TableColumn<TourDTO, String> columnTourName;

    private void initColumn() {
        columnTourName.setCellValueFactory(data -> data.getValue().getName());
    }

    private void loadData() {
        ObservableList<TourDTO> tours = FXCollections.observableArrayList(TourDAO.getAll());
        table.setItems(tours);
    }

    private void setRowDoubleClick() {
        table.setRowFactory(tv -> {
            TableRow<TourDTO> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    TourDTO rowData = row.getItem();
                    try {
                        Stage stage = new Stage();
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/tourio/fxml/tour-detail.fxml"));
                        Parent root = fxmlLoader.load();

                        TourDetailController tourDetailController = fxmlLoader.getController();
                        tourDetailController.setTourId(rowData.getTourId());

                        Scene scene = new Scene(root, Main.WIDTH, Main.HEIGHT);
                        stage.setResizable(false);
                        stage.setScene(scene);
                        stage.setTitle("Chi tiết tour");
                        stage.initModality(Modality.APPLICATION_MODAL);
                        stage.showAndWait();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            return row;
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initColumn();
        loadData();
        setRowDoubleClick();
    }
}