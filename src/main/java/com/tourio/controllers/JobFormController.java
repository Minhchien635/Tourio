package com.tourio.controllers;

import com.tourio.dao.JobDAO;
import com.tourio.models.Job;
import com.tourio.utils.AlertUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class JobFormController extends BaseFormController {
    @FXML
    public TextField nameTextField;

    public JobTableController jobTableController;

    public Job job = new Job();

    @Override
    public void initReadOnly() {
        nameTextField.setDisable(true);
        saveButton.setManaged(false);
    }

    @Override
    public void initFormValues() {
        nameTextField.setText(job.getName());
    }

    @Override
    public void onSaveClick(ActionEvent e) {
        String name = nameTextField.getText();
        if (name == null || name.trim().isEmpty()) {
            AlertUtils.showWarning("Hãy nhập tên công việc");
            return;
        }

        job.setName(name);

        JobDAO.save(job);

        jobTableController.loadData();
        closeWindow(e);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (job.getId() != null) {
            initFormValues();
        }
    }
}