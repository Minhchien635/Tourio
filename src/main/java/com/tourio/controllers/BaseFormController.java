package com.tourio.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.net.URL;
import java.util.EventObject;
import java.util.ResourceBundle;

public abstract class BaseFormController implements Initializable {
    @FXML
    public Button saveButton, cancelButton;

    public boolean readOnly = false;

    public abstract void initDefaultValues();

    public void closeWindow(EventObject event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    public abstract void onSaveClick(ActionEvent event);

    public void onCancelClick(ActionEvent event) {
        closeWindow(event);
    }

    protected abstract void initReadOnly();

    @Override
    public abstract void initialize(URL url, ResourceBundle resourceBundle);
}
