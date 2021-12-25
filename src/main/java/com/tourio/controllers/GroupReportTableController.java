package com.tourio.controllers;

import com.tourio.dao.GroupDAO;
import com.tourio.models.Group;
import com.tourio.utils.DateUtils;
import com.tourio.utils.PriceFormatter;
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

public class GroupReportTableController extends BaseTableController<Group> {
    @FXML
    private TableView<Group> table;

    @FXML
    private TableColumn<Group, String> groupIdColumn, tourNameColumn, groupNameColumn,
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
        String[] optionList = {
                "Mã đoàn",
                "Tên tour",
                "Tên đoàn",
                "Giá tour (VND)",
                "Ngày bắt đầu",
                "Ngày kết thúc",
                "Số lượng khách",
                "Số lượng nhân viên",
                "Tổng doanh thu (VND)",
                "Tổng chi phí (VND)",
                "Lợi nhuận (%)",
                "Ngày tạo đoàn"
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

                Predicate<Group> predicate = switch (option) {
                    // Mã đoàn
                    case 0 -> o -> o.getId().toString().toLowerCase().contains(newValue.toLowerCase());

                    // Tên tour
                    case 1 -> o -> o.getTour().getName().toLowerCase().contains(newValue.toLowerCase());

                    // Tên đoàn
                    case 2 -> o -> o.getName().toLowerCase().contains(newValue.toLowerCase());

                    // Giá tour (VND)
                    case 3 -> o -> o.getTourPrice().toString().contains(newValue);

                    // Ngày bắt đầu
                    case 4 -> o -> DateUtils.format(o.getDateStart()).contains(newValue);

                    // Ngày kết thúc
                    case 5 -> o -> DateUtils.format(o.getDateEnd()).contains(newValue);

                    // Số lượng khách
                    case 6 -> o -> String.valueOf(o.getCustomers().size()).contains(newValue);

                    // Số lượng nhân viên
                    case 7 -> o -> String.valueOf(o.getGroupEmployeeRels().size()).contains(newValue);

                    // Tổng doanh thu (VND)
                    case 8 -> o -> PriceFormatter.format(o.getTotalSale()).contains(newValue);

                    // Tổng chi phí (VND)
                    case 9 -> o -> PriceFormatter.format(o.getTotalCost()).contains(newValue);

                    // Lợi nhuận (%)
                    case 10 -> o -> {
                        float revenue = 0;
                        float totalSale = (float) o.getTotalSale();

                        if (totalSale > 0) {
                            revenue = (totalSale - o.getTotalCost()) / (totalSale);
                        }

                        return PriceFormatter.format((int) (revenue * 100)).contains(newValue);
                    };

                    // Ngày tạo đoàn
                    case 11 -> o -> DateUtils.format(o.getCreatedAt()).contains(newValue);

                    default -> null;
                };

                if (predicate != null) {
                    ArrayList<Group> arrayList = arrList.stream()
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
        groupIdColumn.setCellValueFactory(data -> {
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
            property.setValue(PriceFormatter.format(data.getValue().getTourPrice()));
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
            property.setValue(PriceFormatter.format(data.getValue().getTotalSale()));
            return property;
        });

        totalCostColumn.setCellValueFactory(data -> {
            SimpleStringProperty property = new SimpleStringProperty();
            property.setValue(PriceFormatter.format(data.getValue().getTotalCost()));
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
            property.setValue(PriceFormatter.format(revenue));
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