package com.tourio.controllers;

import com.tourio.dao.TourDAO;
import javafx.event.ActionEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class TourEditController extends TourCreateController {
    public void initDefaultValues() {
        nameTextField.setText(tour.getName());
        descriptionTextArea.setText(tour.getDescription());
        typeComboBox.setValue(tour.getTourType());
        tourPrices.setAll(tour.getTourPrices());
        tourLocationRels.setAll(tour.getTourLocationRels());
    }

    @Override
    public void onSaveValid() {
        tour.setName(nameTextField.getText());
        tour.setTourType(typeComboBox.getValue());
        tour.setDescription(descriptionTextArea.getText());

        TourDAO.update(tour, tourPrices, tourLocationRels);

        tourTableController.loadData();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);

        initDefaultValues();
    }
}
