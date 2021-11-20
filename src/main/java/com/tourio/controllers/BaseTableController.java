package com.tourio.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

public abstract class BaseTableController<T> implements Initializable {
    @FXML
    private TableView<T> table;

    public abstract void initTable();
    public abstract void loadData();
    public abstract void onCreateClick(ActionEvent e) throws Exception;
    public abstract void onEditClick(ActionEvent e) throws Exception;
    public abstract void onDeleteClick(ActionEvent e);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initTable();
        loadData();
    }
}
