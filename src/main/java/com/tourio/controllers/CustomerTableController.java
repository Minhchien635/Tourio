package com.tourio.controllers;

import com.tourio.dao.CustomerDAO;
import com.tourio.models.Customer;
import com.tourio.utils.AlertUtils;
import com.tourio.utils.StageBuilder;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class CustomerTableController extends BaseTableController<Customer> {
    @FXML
    private TableView<Customer> table;

    @FXML
    private TableColumn<Customer, String> customerNameColumn;

    @Override
    public void onCreateClick(ActionEvent event) throws IOException {
        CustomerFormController controller = new CustomerFormController();
        controller.customerTableController = this;

        new StageBuilder("customer_form", controller, "Thêm khách du lịch")
                .setModalOwner(event)
                .setDimensionsAuto()
                .build()
                .showAndWait();
    }

    @Override
    public void onEditClick(ActionEvent event) throws IOException {
        Customer customer = table.getSelectionModel().getSelectedItem();

        if (customer == null) {
            AlertUtils.showWarning("Hãy chọn khách du lịch cần sửa");
            return;
        }

        CustomerFormController controller = new CustomerFormController();
        controller.customerTableController = this;
        controller.customer = customer;

        new StageBuilder("customer_form", controller, "Sửa thông tin khách du lịch")
                .setModalOwner(event)
                .setDimensionsAuto()
                .build()
                .showAndWait();
    }

    @Override
    public void onDeleteClick(ActionEvent event) {
        Customer customer = table.getSelectionModel().getSelectedItem();

        if (customer == null) {
            AlertUtils.showWarning("Hãy chọn khách du lịch cần xóa");
            return;
        }

        Alert alert = AlertUtils.alert(Alert.AlertType.CONFIRMATION, "Chắc chắn xóa");
        Optional<ButtonType> option = alert.showAndWait();

        if (option.get() == ButtonType.OK) {
            CustomerDAO.delete(customer);
            loadData();
            return;
        }
        if (option.get() == ButtonType.CANCEL) {
            return;
        }
    }

    @Override
    public void initTable() {
        // On row double click
        table.setRowFactory(tv -> {
            TableRow<Customer> row = new TableRow<>();

            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    Customer customer = row.getItem();

                    try {
                        // Init controller
                        CustomerFormController controller = new CustomerFormController();
                        controller.customer = customer;
                        controller.customerTableController = this;
                        controller.read_only = true;

                        // Show modal
                        new StageBuilder("customer_form", controller, "Chi tiết khách du lịch")
                                .setModalOwner(event)
                                .setDimensionsAuto()
                                .build()
                                .showAndWait();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

            return row;
        });

        // Customer name column render
        customerNameColumn.setCellValueFactory(data -> {
            SimpleStringProperty property = new SimpleStringProperty();
            property.setValue(data.getValue().getName());
            return property;
        });

        // Bind table with observableList observable list
        table.setItems(observableList);
    }

    @Override
    public void loadData() {
        List<Customer> customers = CustomerDAO.getAll();

        // Add customers -> arrList of BaseTableController
        arrList.clear();
        arrList.addAll(customers);

        // Get all observableList and set to customer observable list
        observableList.setAll(customers);
        table.refresh();
    }
}