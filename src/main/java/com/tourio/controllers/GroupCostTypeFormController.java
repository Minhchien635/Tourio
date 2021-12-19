package com.tourio.controllers;

import com.tourio.dao.CostTypeDAO;
import com.tourio.models.CostType;
import com.tourio.models.Group;
import com.tourio.models.GroupCostRel;
import com.tourio.utils.AlertUtils;
import com.tourio.utils.NumberUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.util.Callback;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;

public class GroupCostTypeFormController extends BaseFormController {
    @FXML
    public ComboBox<CostType> costTypeComboBox;

    @FXML
    public TextField amountTextField;

    public GroupFormController groupFormController;

    public ObservableList<CostType> costTypes = FXCollections.observableArrayList();

    public void onSaveClick(ActionEvent e) {
        CostType costType = costTypeComboBox.getValue();
        if (costType == null) {
            AlertUtils.showWarning("Hãy chọn loại phí");
            return;
        }

        String price = amountTextField.getText();
        if (price == null || price.trim().isEmpty()) {
            AlertUtils.showWarning("Hãy nhập giá");
            return;
        }

        if (!NumberUtils.isLong(price)) {
            AlertUtils.showWarning("Giá không hợp lệ");
            return;
        }

        Group group = groupFormController.group;
        GroupCostRel groupCostRel = new GroupCostRel(group, costType, Long.parseLong(price));
        groupFormController.groupCostRels.add(groupCostRel);

        closeWindow(e);
    }

    @Override
    public void initReadOnly() {

    }

    @Override
    public void initFormValues() {

    }

    public void initCostTypeComboBox() {
        // Render combo box
        Callback<ListView<CostType>, ListCell<CostType>> factory = lv -> new ListCell<CostType>() {

            @Override
            protected void updateItem(CostType item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? "" : item.getName());
            }

        };
        costTypeComboBox.setCellFactory(factory);
        costTypeComboBox.setButtonCell(factory.call(null));

        // Bind data
        costTypeComboBox.setItems(costTypes);

        // Load data
        costTypes.setAll(CostTypeDAO.getAll());

        // Remove already chosen costTypes
        Set<Long> chosenCostTypeIds = groupFormController.groupCostRels
                .stream()
                .map(rel -> rel.getCostType().getId())
                .collect(Collectors.toSet());
        costTypes.removeIf(costType -> chosenCostTypeIds.contains(costType.getId()));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initCostTypeComboBox();
    }
}