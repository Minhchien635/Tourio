package com.tourio.controllers;

import com.tourio.dao.CustomerDAO;
import com.tourio.models.Customer;
import com.tourio.utils.AlertUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;

public class GroupCustomerFormController extends BaseFormController {
    @FXML
    public ComboBox<Customer> customerComboBox;

    public GroupFormController groupFormController;

    public ObservableList<Customer> customers = FXCollections.observableArrayList();

    public void onSaveClick(ActionEvent e) {
        Customer customer = customerComboBox.getValue();
        if (customer == null) {
            AlertUtils.showWarning("Hãy chọn khách du lịch");
            return;
        }

        groupFormController.customers.add(customer);

        closeWindow(e);
    }

    @Override
    public void initReadOnly() {

    }

    @Override
    public void initDefaultValues() {

    }

    public void initCustomerComboBox() {
        // Render combo box
        Callback<ListView<Customer>, ListCell<Customer>> factory = lv -> new ListCell<Customer>() {
            @Override
            protected void updateItem(Customer item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? "" : item.getName());
            }
        };
        customerComboBox.setCellFactory(factory);
        customerComboBox.setButtonCell(factory.call(null));

        // Bind data
        customerComboBox.setItems(customers);

        // Load data
        customers.setAll(CustomerDAO.getAll());

        // Remove already chosen customers
        Set<Long> chosenCustomerIds = groupFormController.customers
                .stream()
                .map(c -> c.getId())
                .collect(Collectors.toSet());
        customers.removeIf(customer -> chosenCustomerIds.contains(customer.getId()));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initCustomerComboBox();
    }
}