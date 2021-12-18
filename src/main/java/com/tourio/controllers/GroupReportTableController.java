package com.tourio.controllers;

import com.tourio.dao.GroupDAO;
import com.tourio.models.Group;
import com.tourio.utils.DateUtils;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import java.util.stream.Collectors;

public class GroupReportTableController extends BaseTableController<Group> {
    @FXML
    private TableView<Group> table;

    @FXML
    private TableColumn<Group, String> IdGroupColumn, tourNameColumn, groupNameColumn,
            tourPriceColumn, dateStartColumn, dateEndColumn, customersColumn, employeesColumn,
            totalSaleColumn, totalCostColumn, revenueColumn, createdAtColumn;

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
        String[] optionList = {"Mã đoàn", "Tour", "Đoàn", "Giá tour(VND)",
                "Ngày bắt đầu", "Ngày kết thúc", "Số lượng khách", "Số lượng nhân viên",
                "Tổng doanh thu(VND)", "Tổng chi phí(VND)", "Lợi nhuận(%)", "Ngày tạo đoàn"};

        ObservableList<String> options = FXCollections.observableArrayList();

        options.addAll(optionList);

        // Bind data
        optionComboBox.setItems(options);
    }

    @Override
    public void onSearchListener() {
        searchTextField.setDisable(true);
        try {
            optionComboBox.valueProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
                    if (!newValue.isEmpty()) {
                        searchTextField.setDisable(false);
                    }
                }
            });

            searchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
                ArrayList<Group> arrayList = new ArrayList<>();

                int option = optionComboBox.getSelectionModel().getSelectedIndex();

                switch (option) {
                    case 0:
                        arrayList.addAll(arrList.stream().filter(
                                        o -> o.getId().toString().toLowerCase()
                                                .contains(newValue.toLowerCase())
                                )
                                .collect(Collectors.toList()));
                        break;
                    case 1:
                        arrayList.addAll(arrList.stream().filter(
                                        o -> o.getTour().getName().toLowerCase()
                                                .contains(newValue.toLowerCase())
                                )
                                .collect(Collectors.toList()));
                        break;
                    case 2:
                        arrayList.addAll(arrList.stream().filter(
                                        o -> o.getName().toLowerCase()
                                                .contains(newValue.toLowerCase())
                                )
                                .collect(Collectors.toList()));
                        break;
                    case 3:
                        arrayList.addAll(arrList.stream().filter(
                                        o -> o.getTourPrice().toString()
                                                .contains(newValue.toLowerCase())
                                )
                                .collect(Collectors.toList()));
                        break;
                    case 4:
                        arrayList.addAll(arrList.stream().filter(
                                        o -> DateUtils.format(o.getDateStart())
                                                .contains(newValue)
                                )
                                .collect(Collectors.toList()));
                        break;
                    case 5:
                        arrayList.addAll(arrList.stream().filter(
                                        o -> DateUtils.format(o.getDateEnd())
                                                .contains(newValue)
                                )
                                .collect(Collectors.toList()));
                        break;
                    case 6:
                        arrayList.addAll(arrList.stream().filter(
                                        o -> String.valueOf(o.getCustomers().size())
                                                .contains(newValue)
                                )
                                .collect(Collectors.toList()));
                        break;
                    case 7:
                        arrayList.addAll(arrList.stream().filter(
                                        o -> String.valueOf(o.getGroupEmployeeRels().size())
                                                .contains(newValue)
                                )
                                .collect(Collectors.toList()));
                        break;
                    case 8:
                        arrayList.addAll(arrList.stream().filter(
                                        o -> String.valueOf(o.getTotalSale())
                                                .contains(newValue)
                                )
                                .collect(Collectors.toList()));
                        break;
                    case 9:
                        arrayList.addAll(arrList.stream().filter(
                                        o -> String.valueOf(o.getTotalCost())
                                                .contains(newValue)
                                )
                                .collect(Collectors.toList()));
                        break;
                    case 10:
                        arrayList.addAll(arrList.stream().filter(
                                        o -> String.valueOf((int) ((((float) o.getTotalSale() - (float) o.getTotalCost()) / (float) o.getTotalSale()) * 100))
                                                .contains(newValue)
                                )
                                .collect(Collectors.toList()));
                        break;
                    case 11:
                        arrayList.addAll(arrList.stream().filter(
                                        o -> DateUtils.format(o.getCreatedAt())
                                                .contains(newValue)
                                )
                                .collect(Collectors.toList()));
                        break;
                }

                observableList.clear();
                observableList.addAll(arrayList);
            });
        } catch (Exception e) {
            return;
        }
    }

    @Override
    public void initTable() {
        // Column render
        IdGroupColumn.setCellValueFactory(data -> {
            SimpleStringProperty property = new SimpleStringProperty();
            property.setValue(data.getValue().getId().toString());
            return property;
        });

        tourNameColumn.setCellValueFactory(data -> {
            SimpleStringProperty property = new SimpleStringProperty();
            property.setValue(data.getValue().getTour().getName());
            return property;
        });

        groupNameColumn.setCellValueFactory(data -> {
            SimpleStringProperty property = new SimpleStringProperty();
            property.setValue(data.getValue().getName());
            return property;
        });

        tourPriceColumn.setCellValueFactory(data -> {
            SimpleStringProperty property = new SimpleStringProperty();
            property.setValue(data.getValue().getTourPrice().toString());
            return property;
        });

        dateStartColumn.setCellValueFactory(data -> {
            SimpleStringProperty property = new SimpleStringProperty();
            property.setValue(DateUtils.format(data.getValue().getDateStart()));
            return property;
        });

        dateEndColumn.setCellValueFactory(data -> {
            SimpleStringProperty property = new SimpleStringProperty();
            property.setValue(DateUtils.format(data.getValue().getDateEnd()));
            return property;
        });

        customersColumn.setCellValueFactory(data -> {
            SimpleStringProperty property = new SimpleStringProperty();
            property.setValue(String.valueOf(data.getValue().getCustomers().size()));
            return property;
        });

        employeesColumn.setCellValueFactory(data -> {
            SimpleStringProperty property = new SimpleStringProperty();
            property.setValue(String.valueOf(data.getValue().getGroupEmployeeRels().size()));
            return property;
        });

        totalSaleColumn.setCellValueFactory(data -> {
            SimpleStringProperty property = new SimpleStringProperty();
            property.setValue(data.getValue().getTotalSale().toString());
            return property;
        });

        totalCostColumn.setCellValueFactory(data -> {
            SimpleStringProperty property = new SimpleStringProperty();
            property.setValue(data.getValue().getTotalCost().toString());
            return property;
        });

        revenueColumn.setCellValueFactory(data -> {
            int revenue = 0;
            if (data.getValue().getTotalSale() > 0) {
                float totalSale = data.getValue().getTotalSale();
                float totalCost = data.getValue().getTotalCost();
                revenue = (int) (((totalSale - totalCost) / totalSale) * 100);
            }
            SimpleStringProperty property = new SimpleStringProperty();
            property.setValue(String.valueOf(revenue));
            return property;
        });

        createdAtColumn.setCellValueFactory(data -> {
            SimpleStringProperty property = new SimpleStringProperty();
            property.setValue(DateUtils.format(data.getValue().getCreatedAt()));
            return property;
        });

        // Bind table with observableList observable list
        table.setItems(observableList);
    }

    @Override
    public void loadData() {
        // Get all observableList
        List<Group> allGroups = GroupDAO.getAll();

        // Add groups -> arrList of BaseTableController
        arrList.clear();
        arrList.addAll(allGroups);

        // Set data
        observableList.setAll(allGroups);
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