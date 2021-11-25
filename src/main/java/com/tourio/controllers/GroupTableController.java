package com.tourio.controllers;

import com.tourio.dao.GroupDAO;
import com.tourio.models.Group;
import com.tourio.utils.AlertUtils;
import com.tourio.utils.StageBuilder;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.util.Optional;

public class GroupTableController extends BaseTableController<Group> {
    ObservableList<Group> groups = FXCollections.observableArrayList();

    @FXML
    private TableView<Group> table;

    @FXML
    private TableColumn<Group, String> groupNameColumn;

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
        controller.group = group;

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

        Alert alert = AlertUtils.alert(Alert.AlertType.CONFIRMATION, "Chắc chắn xóa");
        Optional<ButtonType> option = alert.showAndWait();

        if (option.get() == ButtonType.OK) {
            GroupDAO.delete(group);
            loadData();
            return;
        }
        if (option.get() == ButtonType.CANCEL) {
            return;
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
                        controller.group = group;
                        controller.read_only = true;
                        controller.groupTableController = this;

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

        // Bind table with groups observable list
        table.setItems(groups);
    }

    @Override
    public void loadData() {
        // Get all groups and set to group observable list
        groups.setAll(GroupDAO.getAll());
        table.refresh();
    }
}