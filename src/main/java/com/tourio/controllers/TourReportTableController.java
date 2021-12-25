package com.tourio.controllers;

import com.tourio.dao.TourDAO;
import com.tourio.models.Group;
import com.tourio.models.Tour;
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

public class TourReportTableController extends BaseTableController<Tour> {
    @FXML
    private TableView<Tour> table;

    @FXML
    private TableColumn<Tour, String> tourIdColumn,
            tourNameColumn,
            tourTypeColumn,
            groupCountColumn,
            totalSaleColumn,
            totalCostColumn,
            revenueColumn;

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
                "Mã tour",
                "Tên tour",
                "Loại tour",
                "Số đoàn đã đi",
                "Tổng doanh thu (VND)",
                "Tổng chi phí (VND)",
                "Lợi nhuận (%)",
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

                Predicate<Tour> predicate = switch (option) {
                    // Mã tour
                    case 0 -> o -> o.getId().toString().toLowerCase().contains(newValue.toLowerCase());

                    // Tên tour
                    case 1 -> o -> o.getName().toLowerCase().contains(newValue.toLowerCase());

                    // Loại tour
                    case 2 -> o -> o.getTourType().getName().toLowerCase().contains(newValue.toLowerCase());

                    // Số đoàn đã đi
                    case 3 -> o -> String.valueOf(o.getGroups().size()).contains(newValue);

                    // Tổng doanh thu (VND)
                    case 4 -> o -> PriceFormatter.format(o.getGroups()
                                    .stream()
                                    .map(Group::getTotalSale)
                                    .reduce(0L, Long::sum))
                            .contains(newValue);

                    // Tổng chi phí (VND)
                    case 5 -> o -> PriceFormatter.format(o.getGroups()
                                    .stream()
                                    .map(Group::getTotalCost)
                                    .reduce(0L, Long::sum))
                            .contains(newValue);
                    // Lợi nhuận (%)
                    case 6 -> o -> {
                        long totalSale = o.getGroups()
                                .stream()
                                .map(Group::getTotalSale)
                                .reduce(0L, Long::sum);
                        long totalCost = o.getGroups()
                                .stream()
                                .map(Group::getTotalCost)
                                .reduce(0L, Long::sum);

                        float revenue = 0;
                        if (totalSale > 0) {
                            revenue = (totalSale - totalCost) / (float) (totalSale);
                        }

                        return PriceFormatter.format((int) revenue * 100).contains(newValue);
                    };
                    default -> null;
                };

                if (predicate != null) {
                    ArrayList<Tour> arrayList = arrList.stream()
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
        tourIdColumn.setCellValueFactory(data -> {
            SimpleStringProperty property = new SimpleStringProperty();
            property.setValue(data.getValue().getId().toString());
            return property;
        });

        tourNameColumn.setCellValueFactory(data -> {
            SimpleStringProperty property = new SimpleStringProperty();
            property.setValue(data.getValue().getName());
            return property;
        });


        tourTypeColumn.setCellValueFactory(data -> {
            SimpleStringProperty property = new SimpleStringProperty();
            property.setValue(data.getValue().getTourType().getName());
            return property;
        });

        groupCountColumn.setCellValueFactory(data -> {
            SimpleStringProperty property = new SimpleStringProperty();
            property.setValue(String.valueOf(data.getValue().getGroups().size()));
            return property;
        });

        totalSaleColumn.setCellValueFactory(data -> {
            SimpleStringProperty property = new SimpleStringProperty();
            property.setValue(PriceFormatter.format(data.getValue()
                    .getGroups()
                    .stream()
                    .map(Group::getTotalSale)
                    .reduce(0L, Long::sum)));
            return property;
        });

        totalCostColumn.setCellValueFactory(data -> {
            SimpleStringProperty property = new SimpleStringProperty();
            property.setValue(PriceFormatter.format(data.getValue().getGroups()
                    .stream()
                    .map(Group::getTotalCost)
                    .reduce(0L, Long::sum)));
            return property;
        });

        revenueColumn.setCellValueFactory(data -> {
            SimpleStringProperty property = new SimpleStringProperty();

            long totalSale = data.getValue()
                    .getGroups()
                    .stream()
                    .map(Group::getTotalSale)
                    .reduce(0L, Long::sum);

            long totalCost = data.getValue()
                    .getGroups()
                    .stream()
                    .map(Group::getTotalCost)
                    .reduce(0L, Long::sum);

            float revenue = 0;
            if (totalSale > 0) {
                revenue = (totalSale - totalCost) / (float) (totalSale);
            }

            property.setValue(String.valueOf((int) (revenue * 100)));
            return property;
        });

        // Bind table with observableList observable list
        table.setItems(observableList);
    }

    @Override
    public void loadData() {
        // Get all observableList
        List<Tour> allTours = TourDAO.getAll();

        // Add employees -> arrList of BaseTableController
        arrList.clear();
        arrList.addAll(allTours);

        // Set data
        observableList.setAll(allTours);
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
