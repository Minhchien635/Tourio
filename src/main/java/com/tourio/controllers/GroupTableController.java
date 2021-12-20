package com.tourio.controllers;

import com.tourio.dao.GroupDAO;
import com.tourio.models.Group;
import com.tourio.utils.AlertUtils;
import com.tourio.utils.DateUtils;
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

public class GroupTableController extends BaseTableController<Group> {
    @FXML
    private TableView<Group> table;

    @FXML
    private TableColumn<Group, String> groupNameColumn, groupStartDateColumn, groupEndDateColumn, groupTourPriceColumn;

    @FXML
    private ComboBox<String> optionComboBox;

    @Override
    public void onCreateClick(ActionEvent event) throws IOException {
        // Init controller
        GroupFormController controller = new GroupFormController();
        controller.groupTableController = this;

        // Show modal
        new StageBuilder("group_form", controller, "Tạo đoàn")
                .setModalOwner(event)
                .build()
                .showAndWait();
    }

    @Override
    public void onEditClick(ActionEvent event) throws IOException {
        Group group = table.getSelectionModel().getSelectedItem();

        if (group == null) {
            AlertUtils.showWarning("Hãy chọn đoàn để sửa");
            return;
        }

        // Init controller
        GroupFormController controller = new GroupFormController();
        controller.groupTableController = this;
        controller.group_id = group.getId();

        // Show modal
        new StageBuilder("group_form", controller, "Sửa đoàn")
                .setModalOwner(event)
                .build()
                .showAndWait();
    }

    @Override
    public void onDeleteClick(ActionEvent event) {
        Group group = table.getSelectionModel().getSelectedItem();

        if (group == null) {
            AlertUtils.showWarning("Hãy chọn đoàn để xóa");
            return;
        }

        Alert alert = AlertUtils.alert(Alert.AlertType.CONFIRMATION, "Bạn có chắc là muốn xóa đoàn khách này?");
        Optional<ButtonType> option = alert.showAndWait();

        if (option.isPresent() && option.get() == ButtonType.OK) {
            GroupDAO.delete(group);
            loadData();
        }
    }

    private void initOptionComboBox() {
        String[] optionList = {
                "Tên đoàn",
                "Ngày bắt đầu",
                "Ngày kết thúc",
                "Giá tour (VND)"
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
                    // Tên đoàn
                    case 0 -> o -> o.getName().toLowerCase().contains(newValue.toLowerCase());

                    // Ngày bắt đầu
                    case 1 -> o -> DateUtils.format(o.getDateStart()).contains(newValue);

                    // Ngày kết thúc
                    case 2 -> o -> DateUtils.format(o.getDateEnd()).contains(newValue);

                    // Giá tour (VND)
                    case 3 -> o -> o.getTourPrice().toString().contains(newValue);

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
        // On row double click
        table.setRowFactory(tv -> {
            TableRow<Group> row = new TableRow<>();

            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    Group group = row.getItem();

                    try {
                        // Init controller
                        GroupFormController controller = new GroupFormController();
                        controller.groupTableController = this;
                        controller.group_id = group.getId();
                        controller.read_only = true;


                        // Show modal
                        new StageBuilder("group_form", controller, "Chi tiết đoàn")
                                .setModalOwner(event)
                                .build()
                                .showAndWait();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

            return row;
        });

        // Group name column render
        groupNameColumn.setCellValueFactory(data -> {
            SimpleStringProperty property = new SimpleStringProperty();
            property.setValue(data.getValue().getName());
            return property;
        });

        // Group start date column render
        groupStartDateColumn.setCellValueFactory(data -> {
            SimpleStringProperty property = new SimpleStringProperty();
            property.setValue(DateUtils.format(data.getValue().getDateStart()));
            return property;
        });

        // Group end date column render
        groupEndDateColumn.setCellValueFactory(data -> {
            SimpleStringProperty property = new SimpleStringProperty();
            property.setValue(DateUtils.format(data.getValue().getDateEnd()));
            return property;
        });

        // Group tour price column render
        groupTourPriceColumn.setCellValueFactory(data -> {
            SimpleStringProperty property = new SimpleStringProperty();
            property.setValue(data.getValue().getTourPrice().toString());
            return property;
        });

        // Bind table with observableList observable list
        table.setItems(observableList);
    }

    @Override
    public void loadData() {
        List<Group> groups = GroupDAO.getAll();

        // Add groups -> arrList of BaseTableController
        arrList.clear();
        arrList.addAll(groups);

        // Get all observableList and set to group observable list
        observableList.setAll(groups);
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