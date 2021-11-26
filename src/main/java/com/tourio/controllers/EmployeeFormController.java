package com.tourio.controllers;

import com.tourio.dao.EmployeeDAO;
import com.tourio.models.Employee;
import com.tourio.utils.AlertUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class EmployeeFormController extends BaseFormController {
    @FXML
    public TextField nameTextField;

    public EmployeeTableController employeeTableController;

    public Employee employee = new Employee();

    @Override
    public void initReadOnly() {
        nameTextField.setDisable(true);
        saveButton.setManaged(false);
    }

    @Override
    public void initDefaultValues() {
        nameTextField.setText(employee.getName());
    }

    @Override
    public void onSaveClick(ActionEvent e) {
        String name = nameTextField.getText();
        if (name == null || name.trim().isEmpty()) {
            AlertUtils.showWarning("Hãy nhập tên nhân viên");
            return;
        }

        employee.setName(name);

        EmployeeDAO.save(employee);

        employeeTableController.loadData();
        closeWindow(e);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (employee.getId() != null) {
            initDefaultValues();
        }
    }
}