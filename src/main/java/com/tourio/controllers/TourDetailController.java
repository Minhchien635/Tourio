package com.tourio.controllers;

import com.tourio.dao.TourDAO;
import com.tourio.dto.TourDTO;
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
import java.util.ArrayList;
import java.util.ResourceBundle;

public class TourDetailController implements Initializable {

    private long tourId;

    private TourDTO tourDTO;

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
        tourDTO = TourDAO.getDetails(tourId);
    }

    private void setView(TourDTO tourDTO) {
        ArrayList<String> locationList = new ArrayList<>();
        ArrayList<String> priceList = new ArrayList<>();

        textAreaName.setEditable(false);
        textAreaType.setEditable(false);
        textAreaDescription.setEditable(false);
        textAreaName.setText(tourDTO.getName().getValue());
        textAreaType.setText(tourDTO.getType().getValue());
        textAreaDescription.setText(tourDTO.getDescription().getValue());

        for (TourPrice tourPrice : tourDTO.getPrices()) {
            priceList.add(String.valueOf(tourPrice.getAmount()));
        }
        for (Location location : tourDTO.getLocations()) {
            locationList.add(location.getName());
        }

        ObservableList<String> locations = FXCollections.observableArrayList(locationList);
        ObservableList<String> prices = FXCollections.observableArrayList(priceList);

        listViewPrice.setItems(prices);
        listViewLocation.setItems(locations);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(() -> {
            loadData(tourId);
            setView(tourDTO);
        });
    }
}
