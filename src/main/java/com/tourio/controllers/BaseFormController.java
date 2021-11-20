package com.tourio.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.net.URL;
import java.util.ResourceBundle;

public abstract class BaseFormController implements Initializable {
    @FXML
    public Button saveButton, cancelButton;

    public boolean readOnly = false;

    public abstract void onSaveClick(ActionEvent e);

    public void onCancelClick(ActionEvent e) {
        Node source = (Node) e.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    protected abstract void initReadOnly();

    protected abstract void initDefaultValues();

    @Override
    public abstract void initialize(URL url, ResourceBundle resourceBundle);
}
