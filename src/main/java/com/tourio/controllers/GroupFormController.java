package com.tourio.controllers;

import com.tourio.dao.GroupDAO;
import com.tourio.dao.TourDAO;
import com.tourio.models.*;
import com.tourio.utils.AlertUtils;
import com.tourio.utils.DateUtils;
import com.tourio.utils.PriceFormatter;
import com.tourio.utils.StageBuilder;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

public class GroupFormController extends BaseFormController {
    @FXML
    public TableColumn<GroupCostRel, String> costTypeNameColumn, costTypeAmountColumn;
    @FXML
    public TableColumn<GroupEmployeeRel, String> employeeNameColumn, employeeJobColumn;

    @FXML
    public HBox costTypeActionButtons, customerActionButtons, employeeActionButtons;

    @FXML
    public TextField nameTextField;

    @FXML
    public ComboBox<Tour> tourComboBox;

    @FXML
    public ComboBox<TourPrice> tourPriceComboBox;

    @FXML
    public DatePicker startDatePicker, endDatePicker;

    @FXML
    public TextArea descriptionTextArea;

    @FXML
    public TableView<GroupCostRel> costTypeTableView;

    @FXML
    public ListView<Customer> customerListView;

    @FXML
    public TableView<GroupEmployeeRel> employeeTableView;

    public GroupTableController groupTableController;

    public Group group = new Group();

    public ObservableList<GroupCostRel> groupCostRels = FXCollections.observableArrayList();

    public ObservableList<Customer> customers = FXCollections.observableArrayList();

    public ObservableList<GroupEmployeeRel> groupEmployeeRels = FXCollections.observableArrayList();

    public ObservableList<TourPrice> tourPrices = FXCollections.observableArrayList();

    public ObservableList<Tour> tours = FXCollections.observableArrayList();

    private void initCostTypeTable() {
        // Cost type name column render
        costTypeNameColumn.setCellValueFactory(data -> {
            SimpleStringProperty property = new SimpleStringProperty();
            property.setValue(data.getValue().getCostType().getName());
            return property;
        });

        // GroupCostRel amount column render
        costTypeAmountColumn.setCellValueFactory(data -> {
            SimpleStringProperty property = new SimpleStringProperty();
            property.setValue(PriceFormatter.format(data.getValue().getAmount()));
            return property;
        });

        // Bind data
        costTypeTableView.setItems(groupCostRels);
    }

    private void initEmployeeTable() {
        // Employee name column render
        employeeNameColumn.setCellValueFactory(data -> {
            SimpleStringProperty property = new SimpleStringProperty();
            property.setValue(data.getValue().getEmployee().getName());
            return property;
        });

        // Job name column render
        employeeJobColumn.setCellValueFactory(data -> {
            SimpleStringProperty property = new SimpleStringProperty();
            property.setValue(data.getValue().getJob().getName());
            return property;
        });

        // Bind data
        employeeTableView.setItems(groupEmployeeRels);
    }

    private void initCustomerList() {
        // Render
        customerListView.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Customer item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setText(null);
                    return;
                }
                setText(item.getName());
            }
        });

        // Bind data
        customerListView.setItems(customers);
    }

    private void initTourComboBox() {
        // Render
        Callback<ListView<Tour>, ListCell<Tour>> factory = (lv) ->
                new ListCell<>() {
                    @Override
                    protected void updateItem(Tour item, boolean empty) {
                        super.updateItem(item, empty);
                        setText(empty ? "" : item.getName());
                    }
                };
        tourComboBox.setCellFactory(factory);
        tourComboBox.setButtonCell(factory.call(null));

        // Load data
        tours.setAll(TourDAO.getAll());

        // Bind data
        tourComboBox.setItems(tours);
    }


    private void initTourPriceComboBox() {
        // Render
        Callback<ListView<TourPrice>, ListCell<TourPrice>> factory = (lv) ->
                new ListCell<>() {
                    @Override
                    protected void updateItem(TourPrice item, boolean empty) {
                        super.updateItem(item, empty);
                        setText(empty ? "" : PriceFormatter.format(item.getAmount()) + " ( " + item.getDateStart() + "  -  " + item.getDateEnd() + " )");
                    }
                };
        tourPriceComboBox.setCellFactory(factory);
        tourPriceComboBox.setButtonCell(factory.call(null));

        // Listener tourComboBox
        tourComboBox.valueProperty().addListener(new ChangeListener<Tour>() {
            @Override
            public void changed(ObservableValue ov, Tour oldTour, Tour currentTour) {
                // Load data
                tourPrices.setAll(currentTour.getTourPrices());
            }
        });

        // Bind data
        tourPriceComboBox.setItems(tourPrices);
    }

    public void onAddCostTypeClick(ActionEvent event) throws IOException {
        // Init controller
        GroupCostTypeFormController controller = new GroupCostTypeFormController();
        controller.groupFormController = this;

        // Show modal
        new StageBuilder("group_cost_type_form", controller, "Thêm loại phí")
                .setModalOwner(event)
                .setDimensionsAuto()
                .build()
                .showAndWait();//costTypeComboBox
    }

    public void onDeleteCostTypeClick() {
        int index = costTypeTableView.getSelectionModel().getSelectedIndex();

        if (index == -1) {
            AlertUtils.showWarning("Hãy chọn loại phí muốn xóa");
            return;
        }

        groupCostRels.remove(index);
    }

    public void onAddCustomerClick(ActionEvent event) throws IOException {
        // Init controller
        GroupCustomerFormController controller = new GroupCustomerFormController();
        controller.groupFormController = this;

        // Show modal
        new StageBuilder("group_customer_form", controller, "Thêm khách du lịch")
                .setModalOwner(event)
                .setDimensionsAuto()
                .build()
                .showAndWait();
    }

    public void onDeleteCustomerClick() {
        int index = customerListView.getSelectionModel().getSelectedIndex();

        if (index == -1) {
            AlertUtils.showWarning("Hãy chọn khách du lịch muốn xóa");
            return;
        }

        customers.remove(index);
    }

    public void onAddEmployeeClick(ActionEvent event) throws IOException {
        // Init controller
        GroupEmployeeFormController controller = new GroupEmployeeFormController();
        controller.groupFormController = this;

        // Show modal
        new StageBuilder("group_employee_form", controller, "Thêm nhân viên")
                .setModalOwner(event)
                .setDimensionsAuto()
                .build()
                .showAndWait();
    }

    public void onDeleteEmployeeClick() {
        int index = employeeTableView.getSelectionModel().getSelectedIndex();

        if (index == -1) {
            AlertUtils.showWarning("Hãy chọn nhân viên muốn xóa");
            return;
        }

        groupEmployeeRels.remove(index);
    }

    @Override
    public void initReadOnly() {
        nameTextField.setDisable(true);
        tourComboBox.setDisable(true);
        tourPriceComboBox.setDisable(true);
        startDatePicker.setDisable(true);
        endDatePicker.setDisable(true);
        descriptionTextArea.setDisable(true);
        costTypeActionButtons.getChildren().clear();
        customerActionButtons.getChildren().clear();
        employeeActionButtons.getChildren().clear();
        saveButton.setManaged(false);
    }

    @Override
    public void initDefaultValues() {
        nameTextField.setText(group.getName());
        tourComboBox.setValue(group.getTour());

        Optional<TourPrice> tourPrice = group.getTour().getTourPrices()
                                             .stream()
                                             .filter(p -> p.getAmount().equals(group.getTourPrice()) &&
                                                     group.getDateStart().after(p.getDateStart()) &&
                                                     group.getDateEnd().before(p.getDateEnd()))
                                             .findFirst();
        tourPriceComboBox.setValue(tourPrice.orElse(null));

        startDatePicker.setValue(DateUtils.parseLocalDate(group.getDateStart()));
        endDatePicker.setValue(DateUtils.parseLocalDate(group.getDateEnd()));
        descriptionTextArea.setText(group.getDescription());
        groupCostRels.setAll(group.getGroupCostRels());
        customers.setAll(group.getCustomers());
        groupEmployeeRels.setAll(group.getGroupEmployeeRels());
    }

    @Override
    public void onSaveClick(ActionEvent e) {
        String name = nameTextField.getText();
        if (name == null || name.trim().isEmpty()) {
            AlertUtils.showWarning("Hãy nhập tên đoàn");
            return;
        }

        Tour tour = tourComboBox.getValue();
        if (tour == null) {
            AlertUtils.showWarning("Hãy chọn tour");
            return;
        }

        TourPrice tourPrice = tourPriceComboBox.getValue();
        if (tourPrice == null) {
            AlertUtils.showWarning("Hãy chọn giá của tour");
            return;
        }

        LocalDate startDate = startDatePicker.getValue();
        if (startDate == null) {
            AlertUtils.showWarning("Hãy chọn ngày đi");
            return;
        }

        LocalDate endDate = endDatePicker.getValue();
        if (endDate == null) {
            AlertUtils.showWarning("Hãy chọn ngày kết thúc chuyến đi");
            return;
        }

        if (startDate.isAfter(endDate)) {
            AlertUtils.showWarning("Ngày đi phải trước ngày kết thúc");
            return;
        }

        Date dateStart = DateUtils.parseDate(startDate);
        Date dateEnd = DateUtils.parseDate(endDate);
        if (dateStart.before(tourPrice.getDateStart()) || dateEnd.after(tourPrice.getDateEnd())) {
            AlertUtils.showWarning("Ngày đã chọn không nằm trong khoảng thời gian của giá tour");
            return;
        }

        String description = descriptionTextArea.getText();
        if (description == null || description.trim().isEmpty()) {
            AlertUtils.showWarning("Hãy nhập thông tin đoàn");
            return;
        }

        if (groupEmployeeRels.isEmpty()) {
            AlertUtils.showWarning("Hãy thêm ít nhất 1 nhân viên");
            return;
        }

        group.setName(nameTextField.getText());
        group.setTour(tour);
        group.setTourPrice(tourPrice.getAmount());
        group.setCreatedAt(new Date());
        group.setDateStart(dateStart);
        group.setDateEnd(dateEnd);
        group.setDescription(description);

        if (group.getGroupCostRels() == null) {
            group.setGroupCostRels(groupCostRels);
        } else {
            group.getGroupCostRels().clear();
            group.getGroupCostRels().addAll(groupCostRels);
        }

        if (group.getCustomers() == null) {
            group.setCustomers(customers);
        } else {
            group.getCustomers().clear();
            group.getCustomers().addAll(customers);
        }

        if (group.getGroupEmployeeRels() == null) {
            group.setGroupEmployeeRels(groupEmployeeRels);
        } else {
            group.getGroupEmployeeRels().clear();
            group.getGroupEmployeeRels().addAll(groupEmployeeRels);
        }

        GroupDAO.save(group);

        groupTableController.loadData();
        closeWindow(e);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initTourComboBox();
        initTourPriceComboBox();
        initCostTypeTable();
        initCustomerList();
        initEmployeeTable();

        if (group.getId() != null) {
            initDefaultValues();
        }

        if (read_only) {
            initReadOnly();
        }
    }
}