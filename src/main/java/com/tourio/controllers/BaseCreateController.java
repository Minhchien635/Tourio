package com.tourio.controllers;

import com.tourio.utils.WindowUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.EventObject;
import java.util.ResourceBundle;

public abstract class BaseCreateController implements Initializable {
    @FXML
    public Button saveButton;

    public void closeWindow(EventObject event) {
        WindowUtils.closeWindow(event);
    }

    // Check if values are invalid before saving
    public abstract boolean isInvalid();

    // Runs after values are validated successfully
    public abstract void onSaveValid();

    public void onSaveClick(ActionEvent event) {
        if (isInvalid()) {
            return;
        }

        onSaveValid();

        closeWindow(event);
    }

    public void onCancelClick(ActionEvent event) {
        closeWindow(event);
    }

    @Override
    public abstract void initialize(URL url, ResourceBundle resourceBundle);
}
