package com.tourio.controllers;

import com.tourio.dto.TourDTO;
import com.tourio.dao.TourDAO;
import com.tourio.models.Location;
import com.tourio.models.TourPrice;
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

    private long tourId;

    private static NumberFormat formatter = NumberFormat.getCurrencyInstance();

    private TourDTO tourDTO;

    ObservableList observableLocationList = FXCollections.observableArrayList();

    ObservableList observablePriceList = FXCollections.observableArrayList();

    @FXML
    private TextArea textAreaName;

    @FXML
    private TextArea textAreaType;

    @FXML
    private ListView<String> listViewPrice;

    @FXML
    private TextArea textAreaDescription;

    @FXML
    private ListView<String> listViewLocation;

    public void setTourId(long tourId) {
        this.tourId = tourId;
    }

    private void loadData(long tourId) {
        tourDTO = TourDAO.getTourDetail(tourId);
    }

    private void setView(TourDTO tourDTO) {
        ArrayList<String> locationList = new ArrayList<>();
        ArrayList<Float> priceList = new ArrayList<>();

        textAreaName.setEditable(false);
        textAreaType.setEditable(false);
        textAreaDescription.setEditable(false);
        textAreaName.setText(tourDTO.getName().getValue());
        textAreaType.setText(tourDTO.getType().getValue());
        textAreaDescription.setText(tourDTO.getDescription().getValue());

        for (TourPrice tourPrice : tourDTO.getPrices()) {
            priceList.add((tourPrice.getAmount()));
        }
        for (Location location : tourDTO.getLocations()) {
            locationList.add(location.getName());
        }

        observableLocationList.setAll(locationList);
        observablePriceList.setAll(priceList);

        listViewPrice.setItems(observablePriceList);
        listViewLocation.setItems(observableLocationList);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(() -> {
            loadData(tourId);
            setView(tourDTO);
        });
    }
}
