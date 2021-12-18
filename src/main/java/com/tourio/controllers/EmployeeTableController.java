package com.tourio.controllers;

import com.tourio.dao.EmployeeDAO;
import com.tourio.dao.GroupDAO;
import com.tourio.models.Employee;
import com.tourio.models.Group;
import com.tourio.utils.AlertUtils;
import com.tourio.utils.StageBuilder;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class EmployeeTableController extends BaseTableController<Employee> {
    @FXML
    private TableView<Employee> table;

    @FXML
    private TableColumn<Employee, String> employeeNameColumn;

    public void onCreateClick(ActionEvent event) throws IOException {
        EmployeeFormController controller = new EmployeeFormController();
        controller.employeeTableController = this;

        new StageBuilder("employee_form", controller, "Thêm nhân viên")
                .setModalOwner(event)
                .setDimensionsAuto()
                .build()
                .showAndWait();
    }

    public void onEditClick(ActionEvent event) throws IOException {
        Employee employee = table.getSelectionModel().getSelectedItem();

        if (employee == null) {
            AlertUtils.showWarning("Hãy chọn nhân viên cần sửa");
            return;
        }

        EmployeeFormController controller = new EmployeeFormController();
        controller.employeeTableController = this;
        controller.employee = employee;

        new StageBuilder("employee_form", controller, "Sửa nhân viên")
                .setModalOwner(event)
                .setDimensionsAuto()
                .build()
                .showAndWait();
    }

    public void onDeleteClick(ActionEvent event) {
        Employee employee = table.getSelectionModel().getSelectedItem();

        if (employee == null) {
            AlertUtils.showWarning("Hãy chọn nhân viên cần xóa");
            return;
        }

        List<Group> groups = GroupDAO.getAll();
        long index = groups.stream()
                .filter(t -> t.getGroupEmployeeRels()
                        .stream()
                        .anyMatch(p -> p.getEmployee().getId() == employee.getId()))
                .count();

        if (index != 0) {
            AlertUtils.showWarning("Không thể xóa. Đã có đoàn khách chọn nhân viên này");
            return;
        }

        Alert alert = AlertUtils.alert(Alert.AlertType.CONFIRMATION, "Chắc chắn xóa");
        Optional<ButtonType> option = alert.showAndWait();

        if (option.get() == ButtonType.OK) {
            EmployeeDAO.delete(employee);
            loadData();
            return;
        }
        if (option.get() == ButtonType.CANCEL) {
            return;
        }
    }

    public void initTable() {
        // Employee name column render
        employeeNameColumn.setCellValueFactory(data -> {
            SimpleStringProperty property = new SimpleStringProperty();
            property.setValue(data.getValue().getName());
            return property;
        });

        // Bind table with observableList observable list
        table.setItems(observableList);
    }

    public void loadData() {
        List<Employee> employees = EmployeeDAO.getAll();

        // Add employees -> arrList of BaseTableController
        arrList.clear();
        arrList.addAll(employees);

        // Get all observableList and set to employee observable list
        observableList.setAll(employees);
        table.refresh();
    }
}