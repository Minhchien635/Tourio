package com.tourio.controllers;

import com.tourio.utils.WindowUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.EventObject;
import java.util.ResourceBundle;

public abstract class BaseFormController implements Initializable {
    @FXML
    public Button saveButton;

    public boolean read_only;

    public void closeWindow(EventObject event) {
        WindowUtils.closeWindow(event);
    }

    public abstract void onSaveClick(ActionEvent event);

    public abstract void initReadOnly();

    public abstract void initDefaultValues();

    public void onCancelClick(ActionEvent event) {
        closeWindow(event);
    }

    @Override
    public abstract void initialize(URL url, ResourceBundle resourceBundle);
}
