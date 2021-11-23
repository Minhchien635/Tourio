package com.tourio.controllers;

import com.tourio.dao.LocationDAO;
import com.tourio.dao.TourDAO;
import com.tourio.dao.TourTypeDAO;
import com.tourio.models.*;
import com.tourio.utils.AlertUtils;
import com.tourio.utils.DateUtils;
import com.tourio.utils.PriceFormatter;
import com.tourio.utils.StageBuilder;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class LocationFormController extends BaseFormController {
    @FXML
    public TextField nameTextField;

    public LocationTableController locationTableController;

    public Location location = new Location();

    @Override
    public void initReadOnly() {
        nameTextField.setDisable(true);
        saveButton.setManaged(false);
    }

    @Override
    public void initDefaultValues() {
        nameTextField.setText(location.getName());
    }

    @Override
    public void onSaveClick(ActionEvent e) {
        String name = nameTextField.getText();
        if (name == null || name.trim().isEmpty()) {
            AlertUtils.showWarning("Hãy nhập tên địa điểm");
            return;
        }

        location.setName(nameTextField.getText());

        LocationDAO.save(location);

        locationTableController.loadData();
        closeWindow(e);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (location.getId() != null) {
            initDefaultValues();
        }
    }
}