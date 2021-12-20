package com.tourio.controllers;

import com.tourio.dao.EmployeeDAO;
import com.tourio.models.Employee;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class EmployeeReportTableController extends BaseTableController<Employee> {
    @FXML
    private TableView<Employee> table;

    @FXML
    private TableColumn<Employee, String> employeeIdColumn, employeeNameColumn, groupCountColumn, employeeJobsColumn;

    @FXML
    private ComboBox<String> optionComboBox;

    @Override
    public void onCreateClick(ActionEvent event) throws IOException {
    }

    @Override
    public void onEditClick(ActionEvent event) throws IOException {
    }

    @Override
    public void onDeleteClick(ActionEvent e) {
    }

    private void initOptionComboBox() {
        String[] optionList = {
                "Mã nhân viên",
                "Tên nhân viên",
                "Số đoàn đã tham gia",
                "Những công việc đã làm",
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

                Predicate<Employee> predicate = switch (option) {
                    // Mã đoàn
                    case 0 -> o -> o.getId().toString().toLowerCase().contains(newValue.toLowerCase());

                    // Tên tour
                    case 1 -> o -> o.getName().toLowerCase().contains(newValue.toLowerCase());

                    // Số đoàn đã tham gia
                    case 2 -> o -> String.valueOf(o.getGroupEmployeeRels().size()).contains(newValue.toLowerCase());

                    // Những công việc đã làm
                    case 3 -> o -> o.getGroupEmployeeRels()
                            .stream()
                            .map(x -> x.getJob().getName().toLowerCase())
                            .distinct()
                            .collect(Collectors.joining(", "))
                            .contains(newValue.toLowerCase());
                    default -> null;
                };

                if (predicate != null) {
                    ArrayList<Employee> arrayList = arrList.stream()
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
        employeeIdColumn.setCellValueFactory(data -> {
            SimpleStringProperty property = new SimpleStringProperty();
            property.setValue(data.getValue().getId().toString());
            return property;
        });

        employeeNameColumn.setCellValueFactory(data -> {
            SimpleStringProperty property = new SimpleStringProperty();
            property.setValue(data.getValue().getName());
            return property;
        });

        groupCountColumn.setCellValueFactory(data -> {
            SimpleStringProperty property = new SimpleStringProperty();
            property.setValue(String.valueOf(data.getValue().getGroupEmployeeRels().size()));
            return property;
        });

        employeeJobsColumn.setCellValueFactory(data -> {
            SimpleStringProperty property = new SimpleStringProperty();
            property.setValue(data.getValue()
                    .getGroupEmployeeRels()
                    .stream()
                    .map(x -> x.getJob().getName())
                    .distinct()
                    .collect(Collectors.joining(", ")));
            return property;
        });

        // Bind table with observableList observable list
        table.setItems(observableList);
    }

    @Override
    public void loadData() {
        // Get all observableList
        List<Employee> allEmployees = EmployeeDAO.getAll();

        // Add employees -> arrList of BaseTableController
        arrList.clear();
        arrList.addAll(allEmployees);

        // Set data
        observableList.setAll(allEmployees);
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
