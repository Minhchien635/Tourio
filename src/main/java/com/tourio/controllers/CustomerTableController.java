package com.tourio.controllers;

import com.tourio.dao.CustomerDAO;
import com.tourio.enums.SexType;
import com.tourio.models.Customer;
import com.tourio.utils.AlertUtils;
import com.tourio.utils.StageBuilder;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class CustomerTableController extends BaseTableController<Customer> {
    @FXML
    private TableView<Customer> table;

    @FXML
    private TableColumn<Customer, String> customerNameColumn,
            customerIDNumberColumn,
            customerAddressColumn,
            customerPhoneColumn,
            customerSexColumn,
            customerNationalityColumn;

    @FXML
    private ComboBox<String> optionComboBox;

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

    private void initOptionComboBox() {
        String[] optionList = {
                "Họ và tên",
                "CMND",
                "Địa chỉ"
        };

        ObservableList<String> options = FXCollections.observableArrayList();

        options.addAll(optionList);

        // Bind data
        optionComboBox.setItems(options);
    }

    @Override
    public void onSearchListener() {
        searchTextField.setDisable(true);

        try {
            optionComboBox.valueProperty().addListener((observableValue, oldValue, newValue) -> {
                if (!newValue.isEmpty()) {
                    searchTextField.setDisable(false);
                }
            });

            searchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
                int option = optionComboBox.getSelectionModel().getSelectedIndex();

                Predicate<Customer> predicate = switch (option) {
                    // Họ tên
                    case 0 -> o -> o.getName().toLowerCase().contains(newValue.toLowerCase());

                    // CMND
                    case 1 -> o -> o.getCccd().toLowerCase().contains(newValue.toLowerCase());

                    // Địa chỉ
                    case 2 -> o -> o.getAddress().toLowerCase().contains(newValue.toLowerCase());

                    default -> null;
                };

                if (predicate != null) {
                    ArrayList<Customer> arrayList = arrList.stream()
                            .filter(predicate)
                            .collect(Collectors.toCollection(ArrayList::new));
                    observableList.clear();
                    observableList.addAll(arrayList);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
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

        // Customer ID number column render
        customerIDNumberColumn.setCellValueFactory(data -> {
            SimpleStringProperty property = new SimpleStringProperty();
            property.setValue(data.getValue().getCccd());
            return property;
        });

        // Customer address column render
        customerAddressColumn.setCellValueFactory(data -> {
            SimpleStringProperty property = new SimpleStringProperty();
            property.setValue(data.getValue().getAddress());
            return property;
        });

        // Customer phone column render
        customerPhoneColumn.setCellValueFactory(data -> {
            SimpleStringProperty property = new SimpleStringProperty();
            property.setValue(data.getValue().getPhone());
            return property;
        });

        // Customer sex column render
        customerSexColumn.setCellValueFactory(data -> {
            SimpleStringProperty property = new SimpleStringProperty();
            SexType customerSexType = data.getValue().getSex();
            String sexTypeString = customerSexType.equals(SexType.MALE) ? "Nam" : customerSexType.equals(SexType.FEMALE) ? "Nữ" : "Khác";
            property.setValue(sexTypeString);
            return property;
        });

        // Customer nationality column render
        customerNationalityColumn.setCellValueFactory(data -> {
            SimpleStringProperty property = new SimpleStringProperty();
            property.setValue(data.getValue().getNationality());
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initTable();
        loadData();
        initOptionComboBox();
        onSearchListener();
    }
}