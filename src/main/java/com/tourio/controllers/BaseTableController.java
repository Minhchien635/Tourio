package com.tourio.controllers;

import com.tourio.models.BaseModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public abstract class BaseTableController<T extends BaseModel> implements Initializable {
    @FXML
    public TextField searchTextField;

    ObservableList<T> observableList = FXCollections.observableArrayList();

    ArrayList<T> arrList = new ArrayList<>();

    @FXML
    private TableView<T> table;

    // Initialize how to render table columns and rows
    public abstract void initTable();

    // Load data for table
    public abstract void loadData();

    public void onSearchListener() {
        try {
            searchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
                ArrayList<T> arrayList = new ArrayList<>();

                arrayList.addAll(arrList.stream().filter(
                                o -> o.getName().toLowerCase()
                                        .contains(newValue.toLowerCase()))
                        .collect(Collectors.toList()));

                observableList.clear();
                observableList.addAll(arrayList);
            });
        } catch (Exception e) {
            return;
        }
    }

    // On create button click
    public abstract void onCreateClick(ActionEvent e) throws Exception;

    // On edit button click
    public abstract void onEditClick(ActionEvent e) throws Exception;

    // On delete button click
    public abstract void onDeleteClick(ActionEvent e);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initTable();
        loadData();
        onSearchListener();
    }
}
