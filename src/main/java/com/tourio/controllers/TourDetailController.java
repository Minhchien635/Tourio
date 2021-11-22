package com.tourio.controllers;

import javafx.event.ActionEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class TourDetailController extends TourEditController {
    public void initReadOnly() {
        nameTextField.setDisable(true);
        typeComboBox.setDisable(true);
        descriptionTextArea.setDisable(true);
        priceActionButtons.getChildren().clear();
        locationActionButtons.getChildren().clear();
        saveButton.setManaged(false);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url, resourceBundle);

        initDefaultValues();
        initReadOnly();
    }
}
