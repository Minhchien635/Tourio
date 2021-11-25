package com.tourio.controllers;

import com.tourio.dao.EmployeeDAO;
import com.tourio.dao.JobDAO;
import com.tourio.models.Employee;
import com.tourio.models.Group;
import com.tourio.models.GroupEmployeeRel;
import com.tourio.models.Job;
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

public class GroupEmployeeFormController extends BaseFormController {
    @FXML
    public ComboBox<Employee> employeeComboBox;

    @FXML
    public ComboBox<Job> jobComboBox;

    public GroupFormController groupFormController;

    public ObservableList<Employee> employees = FXCollections.observableArrayList();

    public ObservableList<Job> jobs = FXCollections.observableArrayList();

    public void onSaveClick(ActionEvent e) {
        Employee employee = employeeComboBox.getValue();
        if (employee == null) {
            AlertUtils.showWarning("Hãy chọn nhân viên");
            return;
        }

        Job job = jobComboBox.getValue();
        if (job == null) {
            AlertUtils.showWarning("Hãy chọn công việc");
            return;
        }

        Group group = groupFormController.group;
        GroupEmployeeRel tourLocationRel = new GroupEmployeeRel(group, employee, job);
        groupFormController.groupEmployeeRels.add(tourLocationRel);

        closeWindow(e);
    }

    @Override
    public void initReadOnly() {

    }

    @Override
    public void initDefaultValues() {

    }

    public void initEmployeeComboBox() {
        // Render combo box
        Callback<ListView<Employee>, ListCell<Employee>> factory = lv -> new ListCell<Employee>() {

            @Override
            protected void updateItem(Employee item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? "" : item.getName());
            }

        };
        employeeComboBox.setCellFactory(factory);
        employeeComboBox.setButtonCell(factory.call(null));

        // Bind data
        employeeComboBox.setItems(employees);

        // Load data
        employees.setAll(EmployeeDAO.getAll());

        // Remove already chosen employees
        Set<Long> chosenEmployeeIds = groupFormController.groupEmployeeRels
                .stream()
                .map(rel -> rel.getEmployee().getId())
                .collect(Collectors.toSet());
        employees.removeIf(employee -> chosenEmployeeIds.contains(employee.getId()));
    }

    public void initJobComboBox() {
        // Render combo box
        Callback<ListView<Job>, ListCell<Job>> factory = lv -> new ListCell<Job>() {

            @Override
            protected void updateItem(Job item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? "" : item.getName());
            }

        };
        jobComboBox.setCellFactory(factory);
        jobComboBox.setButtonCell(factory.call(null));

        // Bind data
        jobComboBox.setItems(jobs);

        // Load data
        jobs.setAll(JobDAO.getAll());

        // Remove already chosen jobs
        Set<Long> chosenJobIds = groupFormController.groupEmployeeRels
                .stream()
                .map(rel -> rel.getJob().getId())
                .collect(Collectors.toSet());
        jobs.removeIf(job -> chosenJobIds.contains(job.getId()));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initEmployeeComboBox();
        initJobComboBox();
    }
}