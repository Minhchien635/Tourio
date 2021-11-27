package com.tourio.controllers;

import com.tourio.dao.CostTypeDAO;
import com.tourio.models.CostType;
import com.tourio.utils.AlertUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class CostTypeFormController extends BaseFormController {
    @FXML
    public TextField nameTextField;

    public CostTypeTableController costTypeTableController;

    public CostType costType = new CostType();

    @Override
    public void initReadOnly() {
        nameTextField.setDisable(true);
        saveButton.setManaged(false);
    }

    @Override
    public void initDefaultValues() {
        nameTextField.setText(costType.getName());
    }

    @Override
    public void onSaveClick(ActionEvent e) {
        String name = nameTextField.getText();
        if (name == null || name.trim().isEmpty()) {
            AlertUtils.showWarning("Hãy nhập tên tên loại phí");
            return;
        }

        costType.setName(name);

        CostTypeDAO.save(costType);

        costTypeTableController.loadData();
        closeWindow(e);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (costType.getId() != null) {
            initDefaultValues();
        }
    }
}