package com.tourio.controllers;

import com.tourio.dao.TourDAO;
import com.tourio.models.Tour;
import com.tourio.models.TourLocationRel;
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

    private Tour tour;

    private static NumberFormat formatter = NumberFormat.getCurrencyInstance();

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
        tour = TourDAO.getDetails(tourId);
    }

    private void setView(Tour tour) {
        ArrayList<String> locationList = new ArrayList<>();
        ArrayList<String> priceList = new ArrayList<>();

        textAreaName.setEditable(false);
        textAreaType.setEditable(false);
        textAreaDescription.setEditable(false);
        textAreaName.setText(tour.getName());
        textAreaType.setText(tour.getType().getName());
        textAreaDescription.setText(tour.getDescription());

        for (TourPrice tourPrice : tour.getPrices()) {
            priceList.add(formatter.format(tourPrice.getAmount()));
        }
        for (TourLocationRel tourLocationRel : tour.getTourRels()) {
            locationList.add(tourLocationRel.getLocation().getName());
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
            setView(tour);
        });
    }
}
