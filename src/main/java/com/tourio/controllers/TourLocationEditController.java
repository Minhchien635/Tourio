package com.tourio.controllers;

import com.tourio.models.Location;
import com.tourio.models.TourLocationRel;
import javafx.event.ActionEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class TourLocationEditController extends TourLocationCreateController {
    public TourLocationRel tourLocationRel;

    @Override
    public void onSaveClick(ActionEvent e) {
        if (isInvalid()) {
            return;
        }

        Location location = locationComboBox.getValue();
        tourLocationRel.setLocation(location);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initLocationComboBox();
    }
}
