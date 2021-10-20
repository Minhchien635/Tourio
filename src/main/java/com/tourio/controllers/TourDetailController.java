package com.tourio.controllers;

import com.tourio.dto.TourDTO;
import com.tourio.dao.TourDAO;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class TourDetailController implements Initializable {

    private int tourId;

    private static NumberFormat formatter = NumberFormat.getCurrencyInstance();

    private TourDTO tourDTO;

    ArrayList<String> locations = new ArrayList<>();

    ObservableList observableList = FXCollections.observableArrayList();

    @FXML
    private TextArea textAreaName;

    @FXML
    private TextArea textAreaType;

    @FXML
    private TextArea textAreaPrice;

    @FXML
    private TextArea textAreaDescription;

    @FXML
    private ListView<String> listViewLocation;

    public void setTourId(int tourId) {
        this.tourId = tourId;
    }

    private void loadData(int tourId) {
        tourDTO = TourDAO.getTourDetail(tourId);
        locations = TourDAO.getTourLocation(tourId);
    }

    private void setView(TourDTO tourDTO, ArrayList<String> locations) {
        textAreaName.setEditable(false);
        textAreaType.setEditable(false);
        textAreaPrice.setEditable(false);
        textAreaDescription.setEditable(false);
        textAreaName.setText(tourDTO.getName().getValue());
        textAreaType.setText(tourDTO.getType().getValue());
        textAreaPrice.setText(formatter.format(tourDTO.getPrice().getValue()));
        textAreaDescription.setText(tourDTO.getDescription().getValue());
        observableList.setAll(locations);
        listViewLocation.setItems(observableList);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(() -> {
            loadData(tourId);
            setView(tourDTO, locations);
        });
    }
}
