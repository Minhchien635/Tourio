package com.tourio.controllers;

import com.tourio.dao.TourTypeDAO;
import com.tourio.models.TourType;
import com.tourio.utils.AlertUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class TourTypeFormController extends BaseFormController {
    @FXML
    public TextField nameTextField;

    public TourTypeTableController tourTypeTableController;

    public TourType tourType = new TourType();

    @Override
    public void initReadOnly() {
        nameTextField.setDisable(true);
        saveButton.setManaged(false);
    }

    @Override
    public void initDefaultValues() {
        nameTextField.setText(tourType.getName());
    }

    @Override
    public void onSaveClick(ActionEvent e) {
        String name = nameTextField.getText();
        if (name == null || name.trim().isEmpty()) {
            AlertUtils.showWarning("Hãy nhập tên tên loại phí");
            return;
        }

        tourType.setName(name);

        TourTypeDAO.save(tourType);

        tourTypeTableController.loadData();
        closeWindow(e);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (tourType.getId() != null) {
            initDefaultValues();
        }
    }
}